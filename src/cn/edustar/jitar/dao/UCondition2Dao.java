package cn.edustar.jitar.dao;
import java.util.List;
import cn.edustar.jitar.pojos.UCondition2;
public interface UCondition2Dao {
	/**
	 * 得到全部UCondition2的记录
	 * @return
	 */
	public List<UCondition2> getUCondition2();
	/**
	 * 得到UCondition2的记录
	 * @param teacherTypeKeyword
	 * @return
	 */
	public UCondition2 getUCondition2(String teacherTypeKeyword);
	
	/**
	 * 保存
	 */
	public void Save(UCondition2 uCondition2);
	/**
	 * 检查用户是否存在本条件中
	 * @param groupId
	 * @param userId
	 * @return
	 */
	public boolean FindUser(int groupId,int userId);	
}
