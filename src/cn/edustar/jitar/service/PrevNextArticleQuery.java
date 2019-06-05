package cn.edustar.jitar.service;

import cn.edustar.jitar.data.QueryContext;

/**
 * 上一篇、下一篇文章查询
 * @author mxh
 *
 */
public class PrevNextArticleQuery extends ArticleQuery {

    public int articleId = 0;
    public Boolean prev = true;
    
    public PrevNextArticleQuery(String selectFields) {
        super(selectFields);
    }
    
    public void applyWhereCondition(QueryContext qctx){
        super.applyWhereCondition(qctx);
        if(prev){
          //# 前一篇表示比当前文章 '发表早' 的 '最新' 一篇文章
          qctx.addAndWhere("a.articleId < :articleId");
        }
        else{
          //# 后一篇表示比当前文章 '发表晚' 的 '最旧' 一篇文章
          qctx.addAndWhere("a.articleId > :articleId");
        }
        qctx.setInteger("articleId", articleId);
    }
    public void applyOrderCondition(QueryContext qctx){
        if (prev){
          //# 前一篇表示比当前文章 '发表早' 的 '最新' 一篇文章
          qctx.addOrder("a.articleId DESC");
        }
        else{
          //# 后一篇表示比当前文章 '发表晚' 的 '最旧' 一篇文章
          qctx.addOrder("a.articleId ASC");
        }
    }
}
