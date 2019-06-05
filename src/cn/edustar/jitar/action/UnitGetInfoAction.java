package cn.edustar.jitar.action;

import cn.edustar.jitar.service.UnitTypeService;

/**
 * 
 * units/manage/unit_getinfo.py
 * @author baimindong
 *
 */
public class UnitGetInfoAction extends UnitBasePage {
	private static final long serialVersionUID = 4950205174560813000L;
	
	@Override
	protected String execute(String cmd) throws Exception{
        request.setAttribute("unit", this.unit);
        return "unitinfo";
	}
}
