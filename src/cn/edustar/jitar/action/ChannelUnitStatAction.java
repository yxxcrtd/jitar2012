package cn.edustar.jitar.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Channel;
import cn.edustar.jitar.service.ChannelUnitStatQuery;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 频道机构统计
 * @author baimindong
 *
 */
public class ChannelUnitStatAction extends BaseChannelManage{
	private static final long serialVersionUID = 2459328041470650899L;
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
        String guid = param_util.safeGetStringParam("guid");
        String startDate = param_util.safeGetStringParam("startDate");
        String endDate = param_util.safeGetStringParam("endDate");
        if("POST".equals(request.getMethod())){
            if("export".equals(cmd)){               
            	ChannelUnitStatQuery qry = new ChannelUnitStatQuery("cuns.unitTitle, cuns.userCount, cuns.articleCount, cuns.resourceCount, cuns.photoCount, cuns.videoCount");
                qry.channelId = channel.getChannelId();
                qry.statGuid = guid;
                List stat_list = qry.query_map(qry.count());
                request.setAttribute("startDate", startDate);
                request.setAttribute("endDate", endDate);
                request.setAttribute("channel",channel);
                request.setAttribute("stat_list", stat_list);
                request.setCharacterEncoding("utf-8");
                response.reset();
                //#response.setContentType("application/vnd.ms-excel")
                response.setHeader("Content-Type", "application/vnd.ms-excel; charset=GB2312");
                response.addHeader("Content-Disposition", "attachment;" + CommonUtil.encodeContentDisposition(request, channel.getTitle() + ".xls"));
                return "excel" ;
            }else{
                if(startDate.length() > 0 && endDate.length() > 0 ){
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
	            String s = "";
	            String e = "";
	            if(startDate.length() > 0  &&  endDate.length() >0 ){
	                s = startDate + " 00:00:00";
	                e = endDate + " 23:59:59";
	            }
                channelPageService.statUnitData(channel.getChannelId(), guid, s, e);
                //reUrl = "channelunitstat.asction?cmd=show&guid=" + self.guid + "&channelId=" + str(self.channel.channelId) + "&startDate=" + CommonUtil.urlUtf8Encode(startDate) + "&endDate=" + CommonUtil.urlUtf8Encode(endDate)
                //response.sendRedirect(reUrl)
                if(guid.length() == 0){
                    guid = UUID.randomUUID().toString();
                }
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
	private void show(String guid){
		ChannelUnitStatQuery qry = new ChannelUnitStatQuery("cuns.unitTitle, cuns.userCount, cuns.articleCount, cuns.resourceCount, cuns.photoCount, cuns.videoCount");
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
}
