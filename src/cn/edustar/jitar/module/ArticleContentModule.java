package cn.edustar.jitar.module;

import java.io.IOException;

/**
 * 显示文章内容的模块
 * 
 *
 */
public class ArticleContentModule extends AbstractModule {
	
	/** 模块名 */
	public static final String MODULE_NAME = "article_content";
	
	/** 模块标题 */
	public static final String MODULE_TITLE = "文章内容";

	/**
	 * 构造
	 */
	public ArticleContentModule() {
		super(MODULE_NAME, MODULE_TITLE);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response) throws IOException {
		// 实际此模块内容直接在页面输出了，这里暂时不处理什么东西
	}
	
}
