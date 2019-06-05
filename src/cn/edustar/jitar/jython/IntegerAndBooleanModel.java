package cn.edustar.jitar.jython;

import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;

/**
 * 即可以当整数数字看待，又可以当 Boolean 看待的模型.
 *   Jython 用 0 表示 false, 1 表示 true， 所以要处理一下包装问题.
 *   
 * 问题: 对数字包装了之后, freemarker 又不支持 a == b 的比较了, 这点很烦.
 *   现在暂时没有好的解决方法, 只能是 "程序员保证" 在 Jython 中不要放 boolean 
 *   型值到 request 中, 否则其被当成数字 0 1 了.
 * 
 *
 *
 */
public class IntegerAndBooleanModel implements TemplateBooleanModel, TemplateNumberModel {
	private Integer value;
	public IntegerAndBooleanModel(int value) {
		this.value = value;
	}
	public boolean getAsBoolean() throws TemplateModelException {
		return value.intValue() != 0;
	}
	public Number getAsNumber() throws TemplateModelException {
		return value;
	}
}
