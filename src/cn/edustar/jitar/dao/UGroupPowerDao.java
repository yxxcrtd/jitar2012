package cn.edustar.jitar.dao;
import cn.edustar.jitar.pojos.UGroupPower;
public interface UGroupPowerDao {
	public UGroupPower getUGroupPower(int groupId);
	public int getMaxUploadArticleNum(Integer[] groupId);
	public int getMaxUploadResourceNum(Integer[] groupId);
	public int getMaxUploadDiskNum(Integer[] groupId);
	public void Save(UGroupPower uGroupPower);
	public void Delete(UGroupPower uGroupPower);
	public void Delete(int groupId);
	public boolean getAllowVideoConferenceCreate(Integer[] groupId);
}
