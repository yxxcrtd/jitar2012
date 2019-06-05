package cn.edustar.jitar.action;

import cn.edustar.jitar.service.MessageService;

@SuppressWarnings("serial")
public class SubjectGetLoginUserNewMessageCountAction extends ManageBaseAction {
	private MessageService messageService = null;

	@Override
	protected String execute(String cmd) throws Exception {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", -1);
		if (super.getLoginUser() == null) {
			response.getWriter().println("");
		} else {
			if (messageService == null) {
				response.getWriter().println("");
			} else {
				int newMessageCount = messageService.getUnreadMessages(super
						.getLoginUser().getUserId());
				if (newMessageCount > 0) {
					response.getWriter().println(newMessageCount);
				} else {
					response.getWriter().println("");
				}
			}
		}
		return null;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	
}
