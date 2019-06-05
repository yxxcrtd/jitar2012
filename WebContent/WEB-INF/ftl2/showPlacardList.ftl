<!doctype html>
<html>
 <head>
  <meta charset="utf-8">
  <title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 公告</title>
  
	<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
	<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
	<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/gradesubject_blogs.js" type="text/javascript"></script>
 </head>
 <body>

<#include 'site_head.ftl'>
<div style='height:20px;font-size:0;'></div>
<div class="rightWidth border" style="width:1000px;margin:0 auto;background-color:white;">
    <h3 class="h3Head textIn"><a class="more"></a>公&nbsp;&nbsp;&nbsp;告</h3>
     <div class="listCont">
      <#if card_list??>
         <ul>
           <#list card_list as placard>
            <li style="line-height:35px;" <#if (placard_index%2)!=0>class='liBg'</#if> ><div style="float:right;width:100px">[${placard.createDate?string('MM-dd hh:mm')}]</div>
                <a href='showPlacard.action?placardId=${placard.id}'  style="margin-left:15px;font-family:Microsoft YaHei,宋体;" target='_blank'>
                	${Util.getCountedWords(placard.title!?html,40)}
                </a>
            </li> 
          </#list> 
        </ul>
     </#if>
    </div>
   	<#include 'pager.ftl'>
    <div style='height:15px;font-size:0;' style="background-color:white;"></div>
    <div class="imgShadow" style="background-color:white;"><img  src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize9" /></div>
</div>
<#include 'footer.ftl'>
 </body>
</html>