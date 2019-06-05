package cn.edustar.jitar.service.impl;

import java.util.List;
import java.util.Map;

import cn.edustar.data.DataSchema;
import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.dao.BbsDao;
import cn.edustar.jitar.pojos.Reply;
import cn.edustar.jitar.pojos.Topic;
import cn.edustar.jitar.service.BbsReplyQueryParam;
import cn.edustar.jitar.service.BbsService;
import cn.edustar.jitar.service.BbsTopicQueryParam;

/**
 * 协作组论坛服务实现.
 * @author 
 *
 */
public class BbsServiceImpl implements BbsService {
	/** 论坛对象 */
	private BbsDao bbs_dao;
	
	/** 论坛对象的set方法 */
	public void setBbsDao(BbsDao bbsDao) {
		this.bbs_dao = bbsDao;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#getTopicList(int, cn.edustar.data.Pager)
	 */
	public DataTable getTopicDataTable(int groupId, Pager pager) {
		List<Object[]> list = bbs_dao.getTopicList(groupId, pager);
		DataTable datatable = new DataTable(new DataSchema(
				BbsService.GET_TOPIC_LIST), list);
		return datatable;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#getRecentTopicList(int, int)
	 */
	public DataTable getRecentTopicDataTable(int groupId, int count) {
		List<Object[]> list = bbs_dao.getRecentTopicList(groupId, count);
		DataTable datatable = new DataTable(new DataSchema(
				BbsService.GET_TOPIC_LIST), list);
		return datatable;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#getTopTopicList(int)
	 */
	public DataTable getTopTopicList(int groupId) {
		List<Object[]> list = bbs_dao.getTopTopicList(groupId);
		DataTable datatable = new DataTable(new DataSchema(
						BbsService.GET_TOPIC_LIST), list);
		return datatable;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#getReplyList(int, int)
	 */
	public DataTable getReplyDataTable(int topicId, Pager pager) {
		List<Object[]> list = bbs_dao.getReplyList(topicId, pager);
		DataTable datatable = new DataTable(new DataSchema(
				 BbsService.GET_REPLY_LIST), list);
		
		return datatable;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#getTopicById(int)
	 */
	public Topic getTopicById(int topicId) {
		return bbs_dao.getTopicById(topicId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#addTopic(cn.edustar.jitar.pojos.Topic)
	 */
	public void createTopic(Topic topic) {
		bbs_dao.createTopic(topic);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#updateTopic(cn.edustar.jitar.pojos.Topic)
	 */
	public void updateTopic(Topic topic) {
		bbs_dao.updateTopic(topic);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#deleteTopicById(int)
	 */
	public void deleteTopic(Topic topic) {
		bbs_dao.deleteTopic(topic);
	}

	public Reply getReplyById(int replyId) {
		return bbs_dao.getReplyById(replyId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#addReply(cn.edustar.jitar.pojos.Reply)
	 */
	public void createReply(Reply reply) {
		bbs_dao.createReply(reply);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#updateReply(cn.edustar.jitar.pojos.Reply)
	 */
	public void updateReply(Reply reply) {
		bbs_dao.updateReply(reply);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#deleteReply(cn.edustar.jitar.pojos.Reply)
	 */
	public void deleteReply(Reply reply) {
		bbs_dao.deleteReply(reply);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#getTopicDataTable(int)
	 */
	@SuppressWarnings("unchecked")
	public Map getTopicAndUser(int topicId) {
		return bbs_dao.getTopicAndUser(topicId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#bestGroupTopic(cn.edustar.jitar.pojos.Topic)
	 */
	public void bestGroupTopic(Topic topic) {
		bbs_dao.updateGroupTopicState(topic.getTopicId(), true);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#unbestGroupTopic(cn.edustar.jitar.pojos.Topic)
	 */
	public void unbestGroupTopic(Topic topic) {
		bbs_dao.updateGroupTopicState(topic.getTopicId(), false);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#bestGroupReply(cn.edustar.jitar.pojos.Reply)
	 */
	public void bestGroupReply(Reply reply) {
		bbs_dao.updateGroupReplyState(reply.getReplyId(), true);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#unbestGroupReply(cn.edustar.jitar.pojos.Reply)
	 */
	public void unbestGroupReply(Reply reply) {
		bbs_dao.updateGroupReplyState(reply.getReplyId(), false);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#top(cn.edustar.jitar.pojos.Topic)
	 */
	public void topTopic(Topic topic) {
		bbs_dao.updateTopicTopState(topic.getTopicId(), true);
		
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#unTop(cn.edustar.jitar.pojos.Topic)
	 */
	public void untopTopic(Topic topic) {
		bbs_dao.updateTopicTopState(topic.getTopicId(), false);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#deleteTopicRef(cn.edustar.jitar.pojos.Topic)
	 */
	public void deleteTopicRef(Topic topic) {
		bbs_dao.deleteTopicRef(topic.getTopicId(), true);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#topicRef(cn.edustar.jitar.pojos.Topic)
	 */
	public void topicRef(Topic topic) {
		bbs_dao.deleteTopicRef(topic.getTopicId(), false);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#deleteReplyRef(cn.edustar.jitar.pojos.Reply)
	 */
	public void deleteReplyRef(Reply reply) {
		bbs_dao.deleteReplyRef(reply.getReplyId(), true);
	}

	public void ReplyRef(Reply reply) {
		bbs_dao.deleteReplyRef(reply.getReplyId(), false);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#getTopicList(cn.edustar.jitar.service.BbsTopicQueryParam, cn.edustar.data.Pager)
	 */
	public List<Topic> getTopicList(BbsTopicQueryParam param, Pager pager) {
		return bbs_dao.getTopicList(param, pager);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#getReplyList(cn.edustar.jitar.service.BbsReplyQueryParam, cn.edustar.data.Pager)
	 */
	public List<Reply> getReplyList(BbsReplyQueryParam param, Pager pager) {
		return bbs_dao.getReplyList(param, pager);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#getTopicDataTable(cn.edustar.jitar.service.BbsTopicQueryParam, cn.edustar.data.Pager)
	 */
	public DataTable getTopicDataTable(BbsTopicQueryParam param, Pager pager) {
		List<Object[]> list = bbs_dao.getTopicDataTable(param, pager);
		
		DataTable dataTable = new DataTable(new DataSchema(param.selectFields));
		dataTable.addList(list);
		return dataTable;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#getReplyDataTable(cn.edustar.jitar.service.BbsReplyQueryParam, cn.edustar.data.Pager)
	 */
	public DataTable getReplyDataTable(BbsReplyQueryParam param, Pager pager) {
		List<Object[]> list = bbs_dao.getReplyDataTable(param, pager);

		DataTable dataTable = new DataTable(new DataSchema(param.selectFields));
		dataTable.addList(list);
		return dataTable;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.BbsService#incTopicViewCount(cn.edustar.jitar.pojos.Topic, int)
	 */
	public void incTopicViewCount(Topic topic, int count) {
		bbs_dao.incTopicViewCount(topic, count);
	}
}
