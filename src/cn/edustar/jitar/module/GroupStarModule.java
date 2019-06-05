package cn.edustar.jitar.module;

import java.io.IOException;

/**
 * 群组之星
 * 
 *
 */
public class GroupStarModule extends AbstractModuleWithTP {
	
	/**
	 * 构造
	 */
	public GroupStarModule() {
		super("group_star", "协作组之星");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response) throws IOException {
		// 小组之星从 GroupStar 表格中读取信息, 并显示出来 
	}
	
}
