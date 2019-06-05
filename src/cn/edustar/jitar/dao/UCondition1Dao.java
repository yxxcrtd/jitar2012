package cn.edustar.jitar.dao;
import java.util.List;
import cn.edustar.jitar.pojos.UCondition1;

public interface UCondition1Dao {
	public List<UCondition1> getUCondition1();
	public List<UCondition1> getUCondition1(Integer score1,Integer score2,Integer conditionType);
	/**
	 * 检查用户是否存在本条件中
	 * @param groupId
	 * @param userId
	 * @return
	 */
	public boolean FindUser(int groupId,int userId);
	/**
	 * 保存
	 * @param list:全部成绩范围的条件,因为保存是将全部删除原来的记录
	 */
	public void Save(List<UCondition1> list);
}
