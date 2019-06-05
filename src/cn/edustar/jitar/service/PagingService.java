package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.data.paging.PagingQuery;

@SuppressWarnings("rawtypes")
public interface PagingService {	
	public List getPagingList(PagingQuery pagingQuery);

	public List getPagingList(PagingQuery pagingQuery, Pager pager);

	public Integer getRowsCount(PagingQuery pagingQuery);
}
