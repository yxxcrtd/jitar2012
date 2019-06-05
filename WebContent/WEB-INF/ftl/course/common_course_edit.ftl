<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>集体备课 <#include ('/WEB-INF/ftl/webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}precourse.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}dtree.css" />  
  <script src='${SiteUrl}js/jitar/core.js'></script>
       <!-- 配置上载路径 -->
    <script type="text/javascript">
        window.UEDITOR_UPLOAD_URL = "${SiteUrl}";
        window.UEDITOR_USERLOGINNAME = "<#if loginUser??>${loginUser.loginName!?js_string}</#if>";
    </script>
    <!-- 配置文件 -->
    <script type="text/javascript" src="${ContextPath}manage/ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="${ContextPath}manage/ueditor/ueditor.all.js"></script>
    <!-- 语言包文件(建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败) -->
    <script type="text/javascript" src="${ContextPath}manage/ueditor/lang/zh-cn/zh-cn.js"></script>      
</head>
<body>
<#include '/WEB-INF/ftl/site_head.ftl'>
<div class='course_title'><a href='showPrepareCourse.py?prepareCourseId=${prepareCourse.prepareCourseId}'>${prepareCourse.title!}</a> - 共案</div>
<div style='clear:both;height:8px;font-size:0'></div>
<form method='post' action='editCommonPreCourse.py?prepareCourseId=${prepareCourse.prepareCourseId}'>


<script id="DHtml" name="commonContent" type="text/plain" style="width:980px;height:800px;">
${prepareCourse.commonContent!}
</script>
<script type="text/javascript">
    var editor = UE.getEditor('DHtml');
</script> 

<input type='submit' value='保存共案' />
<input type='button' value=' 返  回 ' onclick='window.location.href="showPrepareCourse.py?prepareCourseId=${prepareCourse.prepareCourseId}"' />
</form>
<div style='clear:both;'></div>
<#include ('/WEB-INF/ftl/footer.ftl') >
</body>
</html>