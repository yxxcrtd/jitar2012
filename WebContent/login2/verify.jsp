<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.UUID"%>
<%@page import="cn.edustar.jitar.util.CommonUtil"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String returnurl =  request.getParameter("returnurl");
    String sp = "?";
    if(returnurl == null ){
    	returnurl = path +"/index.action";
    }
    else{        
        if(returnurl.contains("?")){
            sp = "&";
        }
    }
    //System.out.println("returnurl=" + returnurl);
    //清除浏览器缓存的临时方法
    
    returnurl += sp + "t=" + UUID.randomUUID().toString(); 
    if(returnurl.contains("#")){
        //得到锚点，放到页面的最后
        returnurl = CommonUtil.moveHashToEnd(returnurl);
    }
%>
<script>
window.open("<%=returnurl%>","_top");
</script>