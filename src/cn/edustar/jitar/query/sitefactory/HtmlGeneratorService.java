package cn.edustar.jitar.query.sitefactory;

import cn.edustar.jitar.pojos.Unit;

public interface HtmlGeneratorService {
	public void SiteIndex();
	public void Articles();
	public void UnitIndex();
	public String UnitIndex(Unit unit);
	public String UnitIndex(Unit unit,String tempTemplateName,String themeName);
}
