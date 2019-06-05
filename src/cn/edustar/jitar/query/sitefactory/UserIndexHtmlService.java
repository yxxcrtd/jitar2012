package cn.edustar.jitar.query.sitefactory;

import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.Widget;

public interface UserIndexHtmlService {

	//得到用户某模块的Widget对象，如果页面中有多个，则只取第一个
	public Widget getWidget(User user,String moduleName);
	
	public void genEntriesList(User user);
	public void genResourceList(User user);
	public void genPhotoList(User user);
}
