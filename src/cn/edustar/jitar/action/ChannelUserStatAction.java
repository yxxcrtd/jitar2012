package cn.edustar.jitar.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Channel;
import cn.edustar.jitar.service.ChannelUserStatQuery;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 频道个人统计
 * @author baimindong
 *
 */
public class ChannelUserStatAction extends BaseChannelManage{
	private static final long serialVersionUID = 946218006375278515L;
	private Channel channel = null;
	protected String execute(String cmd) throws Exception{
        Integer channelId = param_util.safeGetIntParam("channelId");
        this.channel = channelPageService.getChannel(channelId);
        if (channel == null){
            addActionError("不能加载频道对象。");
            return ERROR;
        }
        String AdminType = GetAdminType(channel);
        if(AdminType.length() == 0 ){
            addActionError("你无权管理频道。");
            return ERROR;        
        }
        String k = param_util.safeGetStringParam("k");
        String f = param_util.safeGetStringParam("f");
        String guid = param_util.safeGetStringParam("guid");
        String startDate = param_util.safeGetStringParam("startDate");
        String endDate = param_util.safeGetStringParam("endDate");
        if("POST".equals(request.getMethod())){
            if("export".equals(cmd)){
            	ChannelUserStatQuery qry = new ChannelUserStatQuery("cus.loginName, cus.userTrueName, cus.unitTitle, cus.articleCount, cus.resourceCount, cus.photoCount, cus.videoCount");
            	qry.setRequest(request);
                qry.channelId = this.channel.getChannelId();
                qry.statGuid = guid;
                List stat_list = qry.query_map(qry.count());
                request.setAttribute("k", param_util.safeGetStringParam("k"));
                request.setAttribute("f", param_util.safeGetStringParam("f"));
                request.setAttribute("startDate", startDate);
                request.setAttribute("endDate", endDate);
                request.setAttribute("channel",this.channel);
                request.setAttribute("stat_list", stat_list);
                request.setCharacterEncoding("utf-8"); 
                response.reset();
                //#response.setContentType("application/vnd.ms-excel")
                response.setHeader("Content-Type", "application/vnd.ms-excel; charset=GB2312");
                response.addHeader("Content-Disposition", "attachment;" + CommonUtil.encodeContentDisposition(request, this.channel.getTitle() + ".xls"));
                return "excel";
            }else{      
                if(startDate.length() > 0  && endDate.length() > 0 ){
                   	if(checkDate(startDate)==false){
                        startDate = "";
                   	}
                   	if(checkDate(endDate)==false){
                   		endDate = "";
                   	}
                }
                if(endDate.length() == 0 || startDate.length() == 0 ){
                    startDate = "";endDate = "";
                }
                if(f.length() >0 ){
                    if (f.equals("0")){
                        f = "loginName";
                    }else if (f.equals("1")){
                        f = "trueName";
                    }else if(f.equals("2")){
                        f = "unitTitle";
                    }else{
                        f = "";
                    }
                }        
                String s = "";
                String e = "";
                if(startDate.length() > 0  &&  endDate.length() >0 ){
                    s = startDate + " 00:00:00";
                    e = endDate + " 23:59:59";
                }
                channelPageService.statUserData(channel.getChannelId(), guid, k, f, s, e);
                //String reUrl = "channeluserstat.action?cmd=show&guid=" + guid + "&channelId=" + channel.getChannelId() + "&f=" + CommonUtil.urlUtf8Encode(param_util.safeGetStringParam("f")) + "&k=" + CommonUtil.urlUtf8Encode(param_util.safeGetStringParam("k")) + "&startDate=" + CommonUtil.urlUtf8Encode(startDate) + "&endDate=" + CommonUtil.urlUtf8Encode(endDate);
                //response.sendRedirect(reUrl);
                //return null;
                if(guid.length() == 0){
                    guid = UUID.randomUUID().toString();
                }
                request.setAttribute("k", k);
                request.setAttribute("f", f);
                request.setAttribute("startDate", startDate);
                request.setAttribute("endDate", endDate);
                request.setAttribute("channel",channel);
                request.setAttribute("guid",guid);
               	show(guid);
                return "list";
            }
        }else{
            if(guid.length() == 0){
                guid = UUID.randomUUID().toString();
            }
            request.setAttribute("k", param_util.safeGetStringParam("k"));
            request.setAttribute("f", param_util.safeGetStringParam("f"));
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("channel",channel);
            request.setAttribute("guid",guid);
            if("show".equals(cmd)){
            	show(guid);
            }
            return "list";
        }
        
	}
	private void show(String guid){
    	ChannelUserStatQuery qry = new ChannelUserStatQuery("cus.loginName, cus.userTrueName, cus.unitTitle, cus.articleCount, cus.resourceCount,cus.photoCount,cus.videoCount");
    	qry.setRequest(request);
        qry.channelId = channel.getChannelId();
        qry.statGuid = guid;
        
		Pager pager = param_util.createPager();
		pager.setItemName("记录");
		pager.setItemUnit("条");
		pager.setPageSize(20);
        pager.setTotalRows(qry.count());
        List stat_list = (List)qry.query_map(pager);
        request.setAttribute("stat_list",stat_list);
        request.setAttribute("pager",pager);
	}
	private boolean checkDate(String str) {
		boolean bol = true;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setLenient(false);
			bol = df.format(df.parse(str)).equals(str);
		} catch (ParseException e) {
			//System.out.println("e="+e.getMessage());
			bol = false;
		}
		return bol;
	}	
}
