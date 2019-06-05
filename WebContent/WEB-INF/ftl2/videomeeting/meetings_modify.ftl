<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 视频教研</title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${SiteUrl}js/calendar/WdatePicker.js" type="text/javascript"></script>
<link type="text/css" rel="styleSheet" href="${SiteUrl}css/uploadify.css" />
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
<script type="text/javascript" src="${SiteUrl}js/jquery.js"></script>
<script type="text/javascript" src="${SiteUrl}js/swfobj.js"></script>
<script type="text/javascript" src="${SiteUrl}js/uploadify.js"></script>
<script type="text/javascript">
<!--
jQuery(function() {

        jQuery('#upload').uploadify({
            'uploader'          : '${SiteUrl}images/uploadify.swf',
            'script'            : 'sitevideomeeting.action',
            'scriptData'        : {
                'cmd'           : 'uploadify',
                'Id'           : '${meetings.id}',
                'userId'        :'${loginUser.userId}'
            },
            'buttonImg'         : '${SiteUrl}images/browse.gif',
            'cancelImg'         : '${SiteUrl}images/dele.gif',
            'height'            : '20',
            'width'             : '80',
            'auto'              : true,
            'sizeLimit'         : 1024 * 1024 * 10000,
            'fileDesc'          : '支持的文件类型是：*.asf',
            'fileExt'           : '*.asf',
            onComplete          : function(event, queueId, fileObj, response, data) {
                
            }
        });
});
//-->
</script>  
  <style type="text/css">
  tr td{background:#fff;height:30px;}
  .txt:hover{outline:solid orange 1px;}
  .div_title { TEXT-ALIGN: center; MARGIN: 10px 0px; FONT-SIZE: 12pt; FONT-WEIGHT: bold }
  .txt { BORDER-BOTTOM: #000000 1px solid; BORDER-LEFT: 0px; PADDING-BOTTOM: 2px; PADDING-LEFT: 2px; PADDING-RIGHT: 2px; BACKGROUND: #fff; COLOR: #000; FONT-SIZE: 12px; BORDER-TOP: 0px; BORDER-RIGHT: 0px; PADDING-TOP: 2px; }
  HR { BORDER-BOTTOM: #000000 1px solid; BORDER-LEFT: 0px; PADDING-BOTTOM: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; BACKGROUND: #fff; COLOR: #000; BORDER-TOP: 0px; BORDER-RIGHT: 0px;}
  #v0{margin-left:195px;}
  .alertmsg {font-size:12px;}
  
 </style>
</head>
<body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<!--活动正文 Start-->
<div class="main mt25 clearfix">

  <table class='listTable' cellspacing='0' style="width:100%;">
    <tr style="height:30px">
      <td align="right" width='20%'><strong>视频会议主题：</strong></td>
      <td align="left">
        ${meetings.subject!?html}
      </td>
    </tr>
    <tr style="height:30px">
      <td align="right" width='20%'><strong>会议编号：</strong></td>
      <td align="left">
        ${meetings.confKey!?html}
      </td>
    </tr>    
    <tr style="height:30px">
      <td align="right">
          <strong>开始时间：</strong>
        </td>
        <td align="left">
            ${meetings.startTime!?string("yyyy-MM-dd HH:mm")}
        </td>
    </tr>
    <tr style="height:30px">
      <td align="right">
          <strong>结束时间：</strong>
        </td>
        <td align="left">
            ${meetings.endTime!?string("yyyy-MM-dd HH:mm")}
        </td>
    </tr>
    <tr style="height:30px">
      <td align="right"><strong>提前入会时间：</strong></td>
      <td align="left">
        ${meetings.beforehandTime!}
      </td>
    </tr>
    <tr style="height:30px">
      <td align="right"><strong>参会人数：</strong></td>
      <td align="left">
        ${meetings.attendeeAmount}
      </td>
    </tr>       
    <tr style="height:30px">
      <td align="right"><strong>主持人：</strong></td>
      <td align="left">
      ${meetings.hostName}
      </td>
    </tr>
    <tr style="height:30px">
      <td align="right"><strong>会议描述：</strong></td>
      <td align="left">
        ${meetings.agenda!?html}
      </td>
    </tr>    

    <tr style="height:50px">
      <td align="right"><strong>上载会议视频：</strong></td>
      <td align="left">
        <#if meetings.href??>
            <#if meetings.href.length() &gt; 0>
                <a href='sitevideomeeting.action?cmd=show&id=${meetings.id}' target="_blank">访问视频文件 </a> &nbsp;&nbsp;<a href="#" onclick="deletmeetingfile()">删除</a>
            </#if>
        </#if>   
        <br/>
        <div id="uploaddiv" style="display:block">
            <input type="file" id="upload" name="file"/>
        </div>
      </td>
    </tr>    
    <tr style="height:30px">
      <td align="right"><strong>上载会议资料：</strong></td>
      <td align="left">
        <div style="width:600px">
            <form name="saveform" id="saveform" action="sitevideomeeting.action" method="post">
            <input type="hidden" name="cmd" value="saveresource"/>
            <input type='hidden' name='Id' value='${meetings.id}' />
            <table border="0" cellspacing="1" cellpadding="1" style="width:100%">
            <tr style="background-color:#eeeeee;height:22px;">
                <th>标题</th>
                <th style="width:100px">状态</th>
                <th style="width:60px">删除</th>
            </tr>    
            </table> 
        <#if resource_list??>
          <#list resource_list as r>
            <table border="0" cellspacing="1" cellpadding="1" style="width:100%;" id="tr${r.resourceId}">
            <tr>
                <td>
                <a href='showResource.action?resourceId=${r.resourceId}' target='_blank'><img src='${Util.iconImage(r.resourceHref!)}' border='0' align='absmiddle' /> ${r.title!?html}</a>
                <input type="hidden" name="resId" value="${r.resourceId}">
                </td>
                <td style="width:100px;text-align:center">    
                <#if r.auditState!=0>
                未审核
                </#if>
                </td>
                <td style="width:60px;text-align:center"><a href="#" onclick="removeResource(${r.resourceId});">删除</a></td>
            </tr>   
            </table> 
          </#list>            
        </#if>            
            <div id="_resourceList"></div>
            </form>
        </div>
        <br/>
        <input type="button" name="uploadR" value="上传资源" onClick="uploadresource();"/>
        <input type="button" name="saveUpl" value="保存资源" onClick="saveMeetingResource();"/>
      </td>
    </tr>

    <tr style="height:60px">
      <td></td>
      <td align="left">
<form action='sitevideomeeting.action' method='post' name="theForm">
<#if __referer??>
  <input type='hidden' name='__referer' value='${__referer}' />
</#if>
        <input type='hidden' name='cmd' value='edit' />
        <input type='hidden' name='Id' value='${meetings.id}' />
        <#if bManage>
        <#if meetings.status!=2>
            <input class="button" type="button" value=" 修改会议信息  " onClick="theForm.submit();"/>
        </#if>
        </#if>
        <input class="button" type="button" value=" 返 回  " onClick="window.document.location.href='sitevideomeeting.action';" />
</form>
        
      </td>
    </tr>
  </table>
<form name="theDeletForm" action="sitevideomeeting.action" method='post'>
<input type='hidden' name='cmd' value='deletefile' />
<input type='hidden' name='Id' value='${meetings.id}' />
</form>
<script type="text/javascript">
    function deletmeetingfile(){
        if(!confirm("确认删除会议视频文件?")){
            return false;
        }
        theDeletForm.submit();
    }
    function showUpload(){
       if(document.getElementById("uploaddiv").style.display=="none"){
            document.getElementById("uploaddiv").style.display = "block";
       }else{
            document.getElementById("uploaddiv").style.display = "none";
       } 
    }
    function showupdown(i){
        if(i==1){
            document.getElementById("showdown").style.display = "none";
            document.getElementById("showup").style.display = "block";
        }else{
            document.getElementById("showdown").style.display = "block";
            document.getElementById("showup").style.display = "none";
        }
    }
    
    function uploadMeetingResource(){
        //window.showModelessDialog(url,"","dialogHeight:540px;dialogWidth:700px;dialogLeft:50px;dialogTop:50px;resizable:yes;");
        //window.showModalDialog(url,"","dialogHeight:540px;dialogWidth:700px;dialogLeft:50px;dialogTop:50px;resizable:yes;");
    }
    
  function uploadresource()
  {
    var vReturnValue = window.showModalDialog('selectuploadresource.py','','dialogWidth:780px;dialogHeight:600px;scroll:yes;status:no');
    if(vReturnValue==undefined){
        return;
    }
    if(vReturnValue==""){
        return;
    }      
    //alert("上载的资源Id="+vReturnValue);
     var url = '${SiteUrl}resources.action?cmd=query&id=' + vReturnValue + '&tmp=' + Math.random();
      new Ajax.Request(url, {
        method: 'get',
        onSuccess: function(xport) {
            var html = xport.responseText;
            //alert(html);
            html=html+"<div id=\"_resourceList\"></div>";
            document.getElementById("_resourceList").outerHTML=html;
          }
      });    
  }
  function selectresource()
  {
    var vReturnValue = window.showModalDialog('${SiteUrl}manage/resource.action?cmd=userresource','','dialogWidth:780px;dialogHeight:600px;scroll:yes;status:no');
    if(vReturnValue==undefined){
        return;
    }
    if(vReturnValue==""){
        return;
    }      
    //alert("选择的资源Id="+vReturnValue);
     var url = '${SiteUrl}resources.action?cmd=query&id=' + vReturnValue + '&tmp=' + Math.random();
     //alert(url);
      new Ajax.Request(url, {
        method: 'get',
        onSuccess: function(xport) {
            var html = xport.responseText;
            //alert(html);
            html=html+"<div id=\"_resourceList\"></div>";
            document.getElementById("_resourceList").outerHTML=html;
          }
      });      
  }
  function removeResource(id)
  {
     if(confirm("确认去除这个资源吗?")==false){return;}
     document.getElementById("tr"+id).outerHTML="";
  }  
  function saveMeetingResource(){
    document.getElementById("saveform").submit();
  }      
</script>
</div>
<!--活动 End-->
<#include '/WEB-INF/ftl2/footer.ftl'>
</body>
</html>
