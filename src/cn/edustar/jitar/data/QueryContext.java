package cn.edustar.jitar.data;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.web.context.ContextLoader;

import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.JitarContext;

/**
 * 保存一次查询的环境, 并进行一次查询.
 *
 *
 */
public class QueryContext extends QueryHelper {
	/** Query 对象 */
	private BaseQuery query_object;
	
	/** 待查询字段列表. */
	private List<SelectField> select_fields = new ArrayList<SelectField>();
	
	/** 从什么实体获取. */
	private List<FromEntity> from_entitys = new ArrayList<FromEntity>();
	
	/** 附加的 where 条件 */
	private String where_cond = "";
	

	private Session session;
	
	public Session getSession()
	{
		return this.session;
	}
	/**
	 * 使用指定的 query_object 构造一个 QueryContext 的新实例.
	 * @param query_object
	 */
	public QueryContext(BaseQuery query_object) {
		this.query_object = query_object;
		//2013年10月31号修改
		JitarContext jtar_ctxt = JitarContext.getCurrentJitarContext();		
		this.session = jtar_ctxt.getSessionFactory().getCurrentSession();// ContextLoader.getCurrentWebApplicationContext().getBean("sessionFactory",SessionFactory.class).getCurrentSession();
	}
	
	
	/**
	 * 分解 SELECT 语句为 List<SelectField>.
	 */
	public void parseSelectFields(String selectFields) {
		// 我们当前仅支持非常简单的 selectFields 形式.
		String[] fields = selectFields.split(",");
		
		List<SelectField> result = new ArrayList<SelectField>();
		for (String field : fields) {
			result.add(new SelectField(field));
		}
		
		this.select_fields = result;
	}

	/**
	 * 构造 from 查询部分.
	 * from = List<x>
	 * x = Single Entity |
	 * 	Entity + ([INNER, LEFT]Joined Entity)*
	 */
	public void buildFrom() {
		query_object.initFromEntities(this);
		
		// 根据 select 构建关联的实体.
		for (SelectField f : this.select_fields) {
			String entity = f.getEntityName();
			if (entity.length() == 0) continue;		// 没有实体就认为是缺省的.
			// 如果实体存在则继续.
			if (entityExists(entity)) continue;
			// 让 BaseQuery 去决定该 entity 是谁, 应该如何关联.
			query_object.resolveEntity(this, entity);
		}
	}

	/**
	 * 添加一个 WHERE 关联的实体.
	 * @param entityName
	 * @param entityAlias
	 * @param whereCond
	 * 例子: Resource r, User u WHERE r.userId = u.userId
	 * 则 entityName = "User"
	 *   entityAlias = "u"
	 *   whereCond = "r.userId = u.userId" 
	 */
	public void addEntity(String entityName, String entityAlias, String whereCond) {
		// 如果实体已经存在, 则不重复添加.
		if (entityExists(entityName)) return;
		
		if (whereCond != null && whereCond.length() > 0) {
			if (where_cond.length() == 0)
				this.where_cond = whereCond;
			else
				this.where_cond += " AND " + whereCond;
		}
		FromEntity fe = new FromEntity();
		fe.entityName = entityName;
		fe.entityAlias = entityAlias;
		this.from_entitys.add(fe);
	}
	
	/**
	 * 添加一个实体 join.
	 * @param joinTo - 关联到的实体名.
	 * Resource r LEFT JOIN Subject subj 则
	 *  joinTo = "r"
	 *  entityName = "Subject"
	 *  entityAlias = "subj"
	 *  joinMethod = "LEFT JOIN"
	 */
	public void addJoinEntity(String joinTo, String entityName, String entityAlias, String joinMethod) {
		// 如果实体已经存在, 则不重复添加.
		if (entityExists(entityName)) return;
		
		FromEntity fe = findFromEntity(joinTo);
		if (fe == null) throw new RuntimeException("无法找到别名为 " + joinTo + " 的实体.");
		fe.addJoin(entityName, entityAlias, joinMethod);
	}

	/**
	 * 根据 select,from 等构造 QueryHelper.
	 * @return
	 */
	public void createQueryHelperForList() {
		super.selectClause = "SELECT " + calcSelectClauseForList();
		super.fromClause = "FROM " + calcFromClause();
		setupWhereClause();
		query_object.applyOrderCondition(this);
	}
	
	/**
	 * 根据 select,from 等构造 QueryHelper.
	 * @return
	 */
	public void createQueryHelperForMap() {
		super.selectClause = "SELECT new Map(" + calcSelectClauseForMap() + ")";
		super.fromClause = "FROM " + calcFromClause();
		setupWhereClause();
		query_object.applyOrderCondition(this);
	}

	/**
	 * 根据 select,from 构造查询数量的 QueryHelper.
	 */
	public void createQueryHelperForCount() {
		super.selectClause = "SELECT COUNT(*) ";
		super.fromClause = "FROM " + calcFromClause();
		setupWhereClause();
	}
	
	/**
	 * 计算 selectClause.
	 * @return
	 */
	protected String calcSelectClauseForList() {
		StringBuilder strbuf = new StringBuilder();
		// 每个字段均返回原始形式以用于 map 查询.
		for (SelectField f : this.select_fields) {
			strbuf.append(f.field).append(", ");
		}
		strbuf.setLength(strbuf.length()-2);		// 必须 = ", ".length().
		return strbuf.toString();
	}

	/**
	 * 计算 selectClause.
	 * @return
	 */
	protected String calcSelectClauseForMap() {
		StringBuilder strbuf = new StringBuilder();
		// 每个字段均返回 'x as y' 形式以用于 map 查询.
		for (SelectField f : this.select_fields) {
			strbuf.append(f.getAsMapField()).append(", ");
		}
		strbuf.setLength(strbuf.length()-2);		// 必须 = ", ".length().
		return strbuf.toString();
	}
	
	/**
	 * 计算 fromClause.
	 * @return
	 */
	protected String calcFromClause() {
		StringBuilder strbuf = new StringBuilder();
		for (FromEntity fe : from_entitys) {
			strbuf.append(fe.toString()).append(", ");
		}
		strbuf.setLength(strbuf.length()-2);		// 必须 = ", ".length().
		return strbuf.toString();
	}

	/**
	 * 计算 whereClause.
	 * @return
	 */
	protected void setupWhereClause() {
		// 添加 QueryContext 需要添加的 where 部分.
		if (where_cond != null && where_cond.length() > 0) {
			super.addAndWhere(where_cond);
		}
		query_object.applyWhereCondition(this);
	}
	
	/**
	 * From 一个实体的描述.
	 */
	public static class FromEntity {
		/** 实体名, 如 Resource */
		public String entityName;
		/** 实体别名, 如 r */
		public String entityAlias;
		/** 要 JOIN 的实体 */
		public List<JoinEntity> joinEntities;
		public String toString() {
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(entityName).append(" ").append(entityAlias);
			if (joinEntities != null && joinEntities.size() > 0) {
				for (JoinEntity je : joinEntities) {
					strbuf.append(" ").append(je.toString());
				}
			}
			return strbuf.toString();
		}
		// 判断指定实体是否存在于此 FromEntity 中.
		public boolean entityExists(String entity) {
			if (entityAlias.equals(entity)) return true;
			if (joinEntities == null || joinEntities.size() == 0) return false;
			for (JoinEntity je : joinEntities) {
				if (je.entityAlias.equals(entity))
					return true;
			}
			return false;
		}
		public void addJoin(String entityName, String entityAlias, String joinMethod) {
			if (joinEntities == null) joinEntities = new ArrayList<JoinEntity>();
			JoinEntity je = new JoinEntity();
			je.entityName = entityName;
			je.entityAlias = entityAlias;
			je.joinMethod = joinMethod;
			joinEntities.add(je);
		}
	}
	
	/**
	 * JOIN 一个实体的描述.
	 */
	public static class JoinEntity {
		/** 实体名, 如 Resource */
		public String entityName;
		/** 实体别名, 如 r */
		public String entityAlias;
		/** 关联方法, 如 LEFT JOIN, INNER JOIN */
		public String joinMethod = "INNER JOIN";
		public String toString() {
			return joinMethod + " " + entityName + " AS " + entityAlias;
		}
	}

	/**
	 * 表示一个要查询的字段.
	 *
	 *
	 */
	public static class SelectField {
		/** 字段名 */
		private String field;
		
		/**
		 * 使用指定的字符串构造一个 SelectField.
		 * @param field
		 * 支持的语法: 'f' - 单个字段名; 't.f' - 表+字段名; 'SUM(*)' - 函数调用; 
		 *   '[x.]y AS z' - 带有别名.
		 */
		public SelectField(String field) {
			this.field = field.trim();
		}
		
		/**
		 * 得到形式为 'r.resourceId AS resourceId' 的字符串, 其用于 list_map() 
		 *   方法提供给 Hibernate 'SELECT r.x AS x', 以返回一个 List<Map> 形结果.   
		 * @return
		 */
		public String getAsMapField() {
			String[] a = field.split("\\s");
			if (a.length == 0) return field;		// bad format.
			if (a.length == 1) {
				// 只对 t.f, f 两种形式添加了 AS 部分.
				String nof = nameOfField(field);
				if (nof.length() == 0) return field;
				return field + " AS " + nof;
			}
			return this.field;
		}
		
		// 计算 field 的名字部分, 也就是 't.f' 的 f 部分.
		private static String nameOfField(String field) {
			int pos = field.lastIndexOf('.');
			if (pos >= 0) return field.substring(pos + 1);
			
			return "";
		}
		
		/**
		 * 得到查询的实体名字或实体别名, 如 't.f' 返回 't', 如果没有指定实体则返回 "".
		 * @return
		 */
		public String getEntityName() {
			String[] a = field.split("\\.");
			if (a.length <= 1) return "";		// 没有实体.
			
			return a[0];				// 把 a[0] 做为实体部分.
		}
	}

	// 查找指定别名的实体记录.
	protected FromEntity findFromEntity(String entityAlias) {
		for (FromEntity fe : from_entitys) {
			if (fe.entityExists(entityAlias)) return fe;
		}
		return null;
	}
	
	// 判断实体是否已经存在于 from 里面了.
	protected boolean entityExists(String entityAlias) {
		return findFromEntity(entityAlias) != null;
	}
}
