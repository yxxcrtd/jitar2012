package cn.edustar.jitar.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.SubjectService;

/**
 * 学科列表模块(可用于任何页面), 该模块用处不大.
 *
 *
 */
public class SubjectListModule extends AbstractModuleWithTP {
	/** 学科服务 */
	private SubjectService subj_svc;
	
	/**
	 * 构造.
	 */
	public SubjectListModule() {
		super("subj_list", "学科列表");
	}
	
	/** 学科服务 */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.module.Module#handleRequest(cn.edustar.jitar.module.ModuleRequest, cn.edustar.jitar.module.ModuleResponse)
	 */
	public void handleRequest(ModuleRequest request, ModuleResponse response)
			throws IOException {
		// 得到学科列表.
		List<Subject> subject_list = subj_svc.getSubjectList();
		
		// 得到当前学科, 如果有的话.
		Subject current_subject = (Subject)request.getAttribute(ModuleRequest.SUBJECT_MODEL_KEY);
		
		outputHtml(subject_list, current_subject, response);
	}

	// 合成 html 输出.
	private void outputHtml(List<Subject> subject_list, Subject current_subject, 
			ModuleResponse response) throws IOException {
		HashMap<String, Object> root_map = new HashMap<String, Object>();
		root_map.put("current_subject", current_subject);
		root_map.put("subject_list", subject_list);
		
		String template_name = "/WEB-INF/system/default/subj_list.ftl";
		
		response.setContentType(Module.TEXT_HTML_UTF_8);
		processTemplate(root_map, response.getOut(), template_name);
	}
}
