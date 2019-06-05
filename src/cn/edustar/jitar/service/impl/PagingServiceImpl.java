package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.data.paging.PagingQuery;
import cn.edustar.jitar.dao.hibernate.BaseDaoHibernate;
import cn.edustar.jitar.service.PagingService;

@SuppressWarnings("rawtypes")
public class PagingServiceImpl extends BaseDaoHibernate implements PagingService {

	public List getPagingList(PagingQuery pagingQuery) {
		return pagingQuery.getPagedList(this.getSession());
	}

	public List getPagingList(PagingQuery pagingQuery, Pager pager) {
		return pagingQuery.getPagedList(this.getSession(), pager);
	}

	public Integer getRowsCount(PagingQuery pagingQuery) {
		return pagingQuery.getRowsCount(this.getSession());
	}
}
