<!doctype html>
<title>候选主题列表</title>
<link rel="stylesheet" href="${SiteThemeUrl}css/index.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/gradesubject_blogs.js" type="text/javascript"></script>
<script type="text/javascript">
function msg() {
	if (confirm("确定要删除选中的候选专题吗？")) {
		document.newsform.submit();
		return true;
	} else {
		return false;
	}
}
</script>
<#include "../site_head.ftl">

<div class="secMain mt25 clearfix">
    <div class="moreList border">
    	<h3 class="h3Head textIn"><span class="moreHead"><a href='../index.action'>首页</a> &gt; 候选专题列表</span></h3>
        <div class="moreContent">
        	<form method='post' name="newsform" onsubmit="return msg()">
        	<table class="moreTable" cellpadding="0" cellspacing="0" border="0">
            	<thead>
                	<tr class="moreThead">
                		<th width="5%"><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid")' /></th>
                        <th width="27%">候选专题名称</th>
                        <th width="50%">候选专题内容</th>
                        <th width="18%">创建时间/提交人</th>
                    </tr>
                </thead>
                <tbody>
					<#if newSpecialSubjectList??>
						<#list newSpecialSubjectList as s>		  
						  <tr>
						  		<td height="50"><input type='checkbox' name='guid' value='${s.newSpecialSubjectId}' /></td>
						  		<td style="text-align: left;">${s.newSpecialSubjectTitle?html}</td>
						  		<td style="text-align: left;">${s.newSpecialSubjectContent}</td>
						  		<td><a href='${SiteUrl}go.action?userId=${s.createUserId}' target='_blank'>${s.createUserName!?html}</a><br />${s.createDate?string('yyyy-MM-dd HH:mm:ss')}</td>
						  </tr>
						</#list>
					</#if>
                </tbody>
            </table>
			<div style='text-align:center'><input type='submit' value=' 删  除 ' class="specialSubmit" /></div>
            </form>
        </div>
        <div style='height:15px;font-size:0;' style="background-color:white;"></div>
        <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize9" /></div>
    </div>
</div>
<#include "../footer.ftl">
