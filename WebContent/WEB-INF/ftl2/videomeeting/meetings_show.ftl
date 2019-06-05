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
   <!--详情页 Start-->
    <div class="content clearfix mt75">
        <!--详情页左侧 Start-->
        <div class="contentLeft">
            <h1 title="${meetings.subject!?html}">${meetings.subject!?html}
            <span class="uploadTime">开始时间： ${meetings.startTime?string('yyyy-MM-dd HH:mm')}</span>
            <span class="uploadTime">结束时间： ${meetings.endTime?string('yyyy-MM-dd HH:mm')}</span>
            </h1>
            <div class="detail">
            <#if error??>
              <div style="padding:200px;text-align:center;color:#f00;">${error}</div>
            <#else>
                <!--视频显示阅读区-->
                <object type="application/x-shockwave-flash" data="${SiteUrl}images/palyer.swf" width="664" height="498" id="v3" zIndex="-1">
                    <param name="movie" value="${SiteUrl}images/palyer.swf"/> 
                    <param name="allowFullScreen" value="true" />
                    <param name="wmode" value="opaque" /> 
                    <param name="FlashVars" value="xml=
                    <vcastr>
                        <channel>
                            <item>
                                <source>${meetings.flvHref!?lower_case?html}</source>
                                <title>${meetings.subject!}</title>
                            </item>
                        </channel>
                    </vcastr>
                    "/>
                </object>
              </#if>               
            </div>
        </div>
        <div class="contentRight">
         <div class="contentRelation">
          <h3>会议相关资料</h3>
            <ul class="ulList">
            <#if resource_list?? && resource_list?size &gt;0 >
              <#list resource_list as r>
              <li<#if r.title?size &gt; 13> title="${r.title?html}"</#if>>
              <em class="emDate">${r.createDate?string("MM-dd")}</em>
              <span class="numIcon<#if r_index<3> numRed</#if>">${r_index+1}</span>
              <a href="${ContextPath}showResource.action?resourceId=${r.resourceId}" target="_blank" title="${r.title}">${Util.getCountedWords(r.title,14)}</a>
              </li>
              </#list>
            </#if>
            </ul>
        </div>
        </div>
    </div>
    <!--详情页 End-->
</div>
<!--活动 End-->
<#include '/WEB-INF/ftl2/footer.ftl'>
</body>
</html>
