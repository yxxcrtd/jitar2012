package cn.edustar.jitar.dao;
import java.util.List;
import cn.edustar.jitar.pojos.UGroup;
public interface UGroupDao {
	public List<UGroup> getUGroups();
	public UGroup getUGroup(int groupId);
	public UGroup getUGroup(String groupName);
	public void Save(UGroup uGroup);
	public void Delete(UGroup uGroup);
}
