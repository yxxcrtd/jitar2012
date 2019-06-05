<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}article.css" />
  <style type="text/css">
  #t1 tr td{background:#fff;vertical-align:top;}
  .txt {width:80%;}
  .txt:hover{outline:solid orange 1px}
  .div_title { TEXT-ALIGN: center; MARGIN: 10px 0px; FONT-SIZE: 12pt; FONT-WEIGHT: bold }
  .contenttitle {font-size:14px; background:#eff5fc;FONT-SIZE: 12pt; padding:0px 0px 0px 0px; width:100% ;FONT-WEIGHT: bold }
  .contenttext{MARGIN:10px 0px 10px 10px; }
  HR { BORDER-BOTTOM: #000000 1px solid; BORDER-LEFT: 0px; PADDING-BOTTOM: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; BACKGROUND: #fff; COLOR: #000; BORDER-TOP: 0px; BORDER-RIGHT: 0px;}
</style>
  
  <title>评课 <#include '/WEB-INF/ftl/webtitle.ftl'></title>
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jquery.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jquery.form.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/calendar/WdatePicker.js"></script>
    <script type="text/javascript">

  function checkInput(ff)
  {
       if(ff.tempId.value=="")
       {
            alert("请选择评课模板");
            return false;
       }
        return true;
  }
  function inputContent()
  {
     document.getElementById("_pingkeDiv").style.display="block";
  }
  function selectTemp(id)
  {
    //选择了模板
    //alert(id);
      var url = 'evaluations.action?cmd=fieldcontent&templateId=' + id + '&tmp=' + Math.random();
      new Ajax.Request(url, {
        method: 'get',
        onSuccess: function(xport) { 
            var html = xport.responseText;
            document.getElementById("_contentinputDiv").innerHTML=html;
          }
      });    
  }
  
  </script>
 </head>
 <body>
<#include '/WEB-INF/ftl/site_head.ftl'>
<div style='height:8px;font-size:0;'></div>
<div>
    <form method="POST" id="ecform" action="evaluations.action">
    <input type="hidden" name="evaluationPlanId" value="${evaluationPlan.evaluationPlanId}"/>
    <input type="hidden" name="act" value="save"/> 
    <input type="hidden" name="cmd" value="savecontent"/>
    <div id="content">   
    <table id="t1" border="0" cellspacing="1" cellpadding="2" style="background:#B0BEC7;margin:auto;width:900px;table-layout:fixed;">
    <tr>
    <td style="width:140px">授课人：</td><td>${evaluationPlan.teacherName}</td>
    </tr>    
    <tr>
    <td>课题名称：</td><td>${evaluationPlan.evaluationCaption}</td>
    </tr>
    <tr>
    <td>学科/学段：</td><td>${msubjName!?html}-${gradeName!?html}</td>
    </tr>
    <tr>
    <td>授课时间：</td><td>${evaluationPlan.teachDate?string("yyyy-MM-dd")}</td>
    </tr>    
    <tr>
    <td>评课开放时段：</td>
    <td>
        ${evaluationPlan.startDate?string("yyyy-MM-dd")}&nbsp;&nbsp;到&nbsp;&nbsp;${evaluationPlan.endDate?string("yyyy-MM-dd")}
    </td>
    </tr>   
    <tr>
    <td>相关视频：</td>
    <td>
        <#if video_list??>
          <#list video_list as p>
            <table border="0" cellspacing="1" cellpadding="1" style="width:100%;" id="tv${p.videoId}">
            <tr>
                <td>
                <a href='${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}' target='_blank'><img border=0 src="${p.flvThumbNailHref!?html}"/>
                </a>
                <a href='${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}' target='_blank'>${p.videoTitle!?html}</a>    
                <input type="hidden" name="videoId" value="${p.videoId}">        
                </td>
            </tr>   
            </table> 
          </#list>
        </#if>
    </td>
    </tr> 
      
    <tr>
    <td>相关资源：</td>
    <td>
        <#if resource_list??>
          <#list resource_list as r>
            <table border="0" cellspacing="1" cellpadding="1" style="width:100%;" id="tr${r.resourceId}">
            <tr>
                <td>
                <a href='showResource.action?resourceId=${r.resourceId}' target='_blank'><img src='${Util.iconImage(r.resourceHref!)}' border='0' align='absmiddle' /> ${r.resourceTitle!?html}</a>
                <input type="hidden" name="resId" value="${r.resourceId}">
                </td>
            </tr>   
            </table> 
          </#list>            
        </#if>
    </td>
    </tr>
    <#if finish==0>
    <tr>
        <td colspan="2">
            <div>
                <input type="button" name="btnAddContent" value="参与评课" onClick="inputContent()"/>
            </div>
            <div id="_pingkeDiv" style="display:none">
            <table id="t2" border="0" cellspacing="1" cellpadding="0" style="width:100%">
             <tr>
                <td style="width:100px">选择评课模板:</td>
                <td>
                  <#if plantemplate_list??>
                        <#list plantemplate_list as t>
                            <li><input type="radio" name="tempId" onClick="selectTemp(${t.evaluationTemplateId})" value="${t.evaluationTemplateId}">${t.evaluationTemplateName!?html}
                        </#list>
                  </#if>              
                </td>
             </tr>       
            </table>
            </div>
            <div id="_contentinputDiv"></div>          
        </td>
    </tr>  
    </#if>
    <tr>
    <td colspan="2">
    <!--显示评课内容-->
       <div id="_contentlist"></div> 
       <#if content_list??>
            <#list content_list as content>
                <table border="0" cellspacing="1" cellpadding="0" style="width:100%;table-layout:fixed;margin-top:10px;margin-bottom:10px">
                    <tr>
                        <td style="width:180px;text-align:center; vertical-align:middle;">
                            ${content.publishUserName!?html}<br/>
                            ${content.createDate?string("yyyy-MM-dd")}
                            <br/><br/>
                        </td>
                        <td>
                            <script>
                            <#if content.publishContent?? && content.publishContent != "">
                            var content = ${content.publishContent};
                            <#else>
                            var content = [];
                            </#if>
                              for(i=0;i<content.length;i++)
                              {
                               field = content[i];
                               for(x in field)
                               {
                                  document.write("<span class='contenttitle'>"+x+"</span>");
                                  document.write("<span class='contenttext'>"+field[x]+"</span>");
                               }
                              }
                            </script>
                        </td>
                    </tr>
                </table>
                <div style="height:1px;margin-left:20px;margin-right:20px;background:#65ee87"></div>
            </#list> 
       </#if>    
    </td>
    </tr>
    </table>
    </div>
    </form>
</div>
<#include '/WEB-INF/ftl/footer.ftl' >
</body>
</html>