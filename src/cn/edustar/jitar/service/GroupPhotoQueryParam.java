	package cn.edustar.jitar.service;

	import cn.edustar.data.hql.QueryHelper;

	/**
	 * 
	 * 一般为了查询指定群组下的图片列表，将限定条件 groupId.
	 */
	public class GroupPhotoQueryParam implements QueryParam {
		public int count = 10;
		public String k = null;
		public Integer groupCateId = null;
		public int orderType = 1;
		
		/** 要查询的图片的所属群组, 缺省 = null 表示不限制 */
		public Integer groupId;
		
		/** 图片标识, 缺省 = null 表示不限制 */
		public Integer photoId;

		/** 是否是群组精华图片, 缺省 = null 表示不限制 */
		public Boolean isGroupBest = null;

		/** 要查询的字段列表, 缺省有图片的部分属性和群组图片部分属性 */
		public String selectFields = "p.photoId, p.title, p.tags, p.userId, p.createDate, " +
				"p.viewCount, p.userStaple,p.href,p.size " +
				"gp.groupId, gp.isGroupBest ";	
		/*
		 * (non-Javadoc)
		 * @see cn.edustar.jitar.service.QueryParam#createQuery()
		 */
		public QueryHelper createQuery() {
			// 先创建原来的查询对象, 原查询只获取 Article a.
			QueryHelper query = new QueryHelper();		
			query.selectClause = "SELECT " + this.selectFields;
			query.fromClause = "FROM Photo p,GroupPhoto gp ";
			query.addAndWhere("v.photoId = gp.photoId");
			
			if (this.groupId != null)
				query.addAndWhere("gp.groupId = " + this.groupId);
			if (this.isGroupBest != null)
				query.addAndWhere("gp.isGroupBest = " + this.isGroupBest);
			if(this.groupCateId != null)
			{
				query.addAndWhere("gp.groupCateId = " + this.groupCateId);
			}
			if (this.k != null && this.k.length() > 0) {
				query.addAndWhere("p.title LIKE :likeTitle");
				query.setString("likeTitle", "%" + this.k + "%");
			}
			
			String order_hql = "";
			switch (this.orderType) {
			case 1:
				order_hql = "gp.photoId DESC";
				break;
			default:
				order_hql = "gp.id DESC";
				break;
			}
			query.addOrder(order_hql);
			return query;
		}
	}
	