package cn.edustar.jitar.action;

import java.io.PrintWriter;

import cn.edustar.jitar.service.MessageService;

public class GetLoginUserNewMessageCountAction  extends ManageBaseAction{

	private static final long serialVersionUID = -1773012794012136107L;
	private MessageService messageService;
	@Override
	protected String execute(String cmd) throws Exception {
		//PrintWriter out = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);	
        if(getLoginUser() == null){
            response.getWriter().println("");
        }else{
            if(messageService == null){
                response.getWriter().println("");
            }else{
                int newMessageCount = messageService.getUnreadMessages(getLoginUser().getUserId());
                if(newMessageCount > 0){
                    response.getWriter().println(newMessageCount);
                }else{
                    response.getWriter().println("");
                }
            }
        }
		return NONE;
	}
	
	public void setMessageService(MessageService messageService){
		this.messageService = messageService;
	}
	public MessageService getMessageService(){
		return this.messageService ;
	}
}
