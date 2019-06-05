package cn.edustar.jitar.service;
import java.util.List;
import cn.edustar.jitar.pojos.UGroup;
public interface UGroupService {
	public List<UGroup> getUGroups();
	public UGroup getUGroup(int groupId);
	public UGroup getUGroup(String groupName);
	public void Save(UGroup uGroup);
	public void Delete(UGroup uGroup);
}
