package cn.edustar.jitar.service;

import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.User;

public interface DeleteComplexObjectService {

	public void deleteArticle(Article article);
	public void deleteUser(User user);
	public void deleteUnit(Unit unit);
	
}
