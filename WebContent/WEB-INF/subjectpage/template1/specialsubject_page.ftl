<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${subject.subjectName!?html} - <#include '/WEB-INF/ftl/webtitle.ftl' ></title>
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />   
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/dtree.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/index.css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/dtree.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
 </head>
<body>
<#include "/WEB-INF/subjectpage/subject_header.ftl">
<#include "/WEB-INF/subjectpage/subject_nav.ftl">
<div style="height:4px;font-size:0"></div>
<#if specialSubject?? && specialSubject.logo?? >
<div><img src='${specialSubject.logo}' /></div>
<#else>
<#if specialSubject??>
<div class='sp_logo'><div class='sp_inner'>${specialSubject.title}</div></div>
<#else>
<div class='sp_logo'><div class='sp_inner'>没有专题</div></div>
</#if>
</#if>
<div style='height:8px;font-size:0;'></div>

<table style='width:100%' cellspacing='0' cellpadding='0' border='0'>
<tr style='vertical-align:top'>
<td style='width:308px;'>

<!-- 专题图片 -->
<div class='panel'>
  <div class='panel_head'>
    <div class='panel_head_right'><a href='${SubjectRootUrl}py/show_photo_list.py?id=${subject.subjectId}&amp;specialSubjectId=${specialSubject.specialSubjectId}&unitId=${unitId!}'>更多…</a></div>
    <div class='panel_head_left'>专题图片</div>
  </div>
  <div class='panel_content'>
    <#if specialSubjectPhotoList??>				
	<div id="c_flash">
	<script language='javascript'>
	linkarr = new Array();
	picarr = new Array();
	textarr = new Array();
	var focus_width=300;
	var focus_height=240;
	var text_height=16;
	var pics = "";
	var links = "";
	var texts = "";
	var swf_height = focus_height+text_height;
	
	<#list specialSubjectPhotoList as ps>
	<#if UserUrlPattern??>
	linkarr[${ps_index+1}]="${UserUrlPattern.replace('{loginName}',ps.loginName)}photo/${ps.photoId}.html";
    <#else>
	linkarr[${ps_index+1}]="${SiteUrl}${ps.loginName}/py/user_photo_show.py?photoId=${ps.photoId}";
	</#if>
	picarr[${ps_index+1}] ="${SiteUrl}${ps.href!}";
	textarr[${ps_index+1}]="${ps.title!?html}";
	</#list>
	for(i=1;i<picarr.length;i++){
	  if(pics=="") pics = picarr[i];
	  else pics += "|"+picarr[i];
	}
	
	for(i=1;i<linkarr.length;i++){
	  if(links=="") links = linkarr[i];
	  else links += "|"+linkarr[i];
	}
	
	for(i=1;i<textarr.length;i++){
	  if(texts=="") texts = textarr[i];
	  else texts += "|"+textarr[i];
	}
	
	document.write('<object type="application/x-shockwave-flash" data="${SiteUrl}images/slide.swf" width="' + focus_width + '" height="' + swf_height + '">');
	document.write('<param name="movie" value="${SiteUrl}images/slide.swf" />');
	document.write('<param name="allowScriptAcess" value="sameDomain" />');
	document.write('<param name="quality" value="best" />');
	document.write('<param name="bgcolor" value="#FFFFFF" />');
	document.write('<param name="scale" value="noScale" />');
	document.write('<param name="menu" value="false">');
	document.write('<param name="wmode" value="opaque" />');
	document.write('<param name="FlashVars" value="playerMode=embedded&pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height+'" />');
	document.write('</object>');
	</script>
	</div>

	</#if>
	</div>
	<#if specialSubject?? &&  !specialSubject.expired>
   		<div style='font-weight:bold;text-align:right;padding:2px;'><a title='Falsh 显示只支持jpg（小写）格式的' href='${SiteUrl}manage/user_manage.py?url=photo.action%3Fcmd=upload%26specialSubjectId=${specialSubject.specialSubjectId!?default('0')}' target='_blank'>上传专题图片</a></div>
   	</#if>

  </div>
</div>

<#if video_list??>
		<div style='height:8px;font-size:0;'></div>
		<div class='panel'>
		 <div class='panel_head'>
		    <#if specialSubject?? >
		    <div class="panel_head_right"><a href="${SubjectRootUrl}py/show_video_list.py?id=${subject.subjectId}&amp;type=video&amp;specialSubjectId=${specialSubject.specialSubjectId}&unitId=${unitId!}">更多…</a></div>
		    </#if>
		    <div class="panel_head_left"> 专题视频</div>
		  </div>
		  <div class="panel_content">
		 
			<ul class="item_ul">
			<#list video_list as v>
			<li><a href='${SiteUrl}manage/video.action?cmd=show&videoId=${v.videoId}' target="_blank">${v.title!?html}</a></li>
			</#list>
			</ul>
	
		  </div>
		</div>
		</#if>
		
<div style='height:8px;font-size:0;'></div>

<!-- 全部专题 -->
<div class='panel'>
  <div class='panel_head'>
    <div class='panel_head_right'><a href='${SubjectRootUrl}py/show_more_specialsubject_list.py?id=${subject.subjectId}&amp;specialSubjectId=${specialSubject.specialSubjectId}&unitId=${unitId!}'>更多…</a></div>
    <div class="panel_head_left"> 全部专题</div>
  </div>
  <div class='panel_content'>  
  	<#if specialSubjectList??>
	<ul class="item_ul">
	<#list specialSubjectList as sl>
	  <li><a href='${SubjectRootUrl}py/specialsubject.py?id=${subject.subjectId}&specialSubjectId=${sl.specialSubjectId}'>${sl.title}</a></li>
	</#list>
	</ul>
	</#if>  
 </div>
</div>
</td>
<td style='padding-left:10px'>
<!-- 专题导读 -->
<#if specialSubject?? && specialSubject.description?? >
  <div class='panel'>
    <div class='panel_head'>
    	<div class='panel_head_left'>专题导读</div>
    </div>
  	<div class='panel_content'>
	     ${specialSubject.description}
	</div>
  </div>
  <div style='height:8px;font-size:0;'></div>
</#if>

<div class='panel'>
	  <div class='panel_head'>
	  <#if specialSubject?? >
	    <div class='panel_head_right'><a href='${SubjectRootUrl}py/show_article_list.py?id=${subject.subjectId}&amp;type=article&amp;specialSubjectId=${specialSubject.specialSubjectId}&unitId=${unitId!}'>更多…</a></div>
	   </#if>
	    <div class='panel_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' /> 专题文章</div>
	  </div>
	  <div class='panel_content'>
		<#if specialSubjectArticleList??>
		<ul class='item_ul'>		  
		<#list specialSubjectArticleList as sa>		  
		  <li><span><a href='${ContextPath}go.action?userId=${sa.userId}'>${sa.userTrueName!?html}</a> ${sa.createDate?string('yyyy-MM-dd')}</span>
			<#if sa.typeState == false>[原创]<#else>[转载]</#if> <a href='${ContextPath}go.action?articleId=${sa.articleId}' target='_blank'>${sa.title!?html}</a>
		  </li>
		</#list>
		  </ul>
		</#if>
		<#if specialSubject?? &&  !specialSubject.expired>
		<div style='font-weight:bold;text-align:right;padding:2px;'><a href='${SiteUrl}manage/user_manage.py?url=article.action%3Fcmd=input%26specialSubjectId=${specialSubject.specialSubjectId!?default('0')}' target='_blank'>发布专题文章</a></div>
		</#if>
	  </div>
	</div>
	<div style='height:8px;font-size:0;'></div>
	
	<!-- 专题讨论 -->
	<div class='panel'>
	  <div class='panel_head'>	  	
	    <div class='panel_head_right'><a href='${SubjectRootUrl}py/show_topic_list.py?id=${subject.subjectId}&amp;type=article&amp;specialSubjectId=${specialSubject.specialSubjectId}&unitId=${unitId!}'>更多…</a></div>
	    <div class='panel_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' /> 专题讨论</div>
	  </div>
	  <div class='panel_content'>
		<div id='topic'>正在加载数据……</div>  
	  </div>
	</div>
	<div style='height:8px;font-size:0;'></div>
	
	<!-- 下期备选专题投票 -->
	<div class='panel'>
	  <div class='panel_head'>
	    <div class='panel_head_right'>
	    <a href='${SiteUrl}subject/manage/createspecialsubject.py?id=${subject.subjectId}' style='font-weight:bold;color:#f00'>创建新专题</a>	   
	    &nbsp;&nbsp;<a href='${SiteUrl}mod/vote/morevote.py?guid=${specialSubject.objectGuid}&type=specialsubject'>更多…</a></div>
	    <div class='panel_head_left' style='width:160px'>&nbsp;<img src='${ContextPath}css/index/j.gif' /> 下期备选专题投票</div>
	  </div>
	  <div class='panel_content' >  	
	  	<div id='vote'>正在加载数据……</div>
	  </div>
	</div>
	<div style='height:8px;font-size:0;'></div>	
	
	<div class='panel'>
	  <div class='panel_head'>
	  	<div class='panel_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' /> 提交候选专题</div>
	  </div>
	  <div class='panel_content'>
		<form method='post' action='${SiteUrl}jython/saveNewSpecialSubject.py'>
		<table style='width:100%'>
		<tr>
		<td style='width:68px;font-weight:bold;'>专题名称<span style='color:#f00'>*</span>:</td><td><input name='stitle' style='width:100%;' /></td>
		</tr>
		<tr>
		<td style='font-weight:bold;'>提交说明<span style='color:#f00'>*</span>:</td><td>
			<textarea name='scontent' style='width:100%;height:60px'></textarea>
		</td>
		</tr>
		<tr>
		<td></td><td>
		<#if loginUser??>
			<input type='submit' value=' 提  交 ' />
			<#if loginUser.loginName == 'admin'>
			<input type='button' value='查看候选主题' onclick='window.open("${SiteUrl}jython/showNewSpecialSubject.py","_blank")' />
			</#if>
		<#else>
			请<a href='login.jsp' style='font-weight:bold;'>登录</a>后提交候选专题。
		</#if>
		</td>
		</tr>
		</table>
		</form>
	  </div>
	</div>	

</td>
</tr>
</table>
<script type='text/javascript'>
var JITAR_ROOT = "${SiteUrl}";
var vote_url = SubjectUrl + 'mod/topic/listview.py?guid=${specialSubject.objectGuid}&type=specialsubject&returl=' + encodeURIComponent(JITAR_ROOT + 'specialSubject.py?specialSubjectId=${specialSubject.specialSubjectId}') + '&tmp=' + Date.parse(new Date());
new Ajax.Request(vote_url, { 
  method: 'get',
  onSuccess: function(xhr){load_topic_success(xhr); },
  onException: function(xhr){load_topic_exception(xhr);},
  onFailure: function(){load_topic_exception(xhr);}
}
);

function load_topic_success(xhr)
{
  document.getElementById('topic').innerHTML = xhr.responseText;
}

function load_topic_exception(xhr)
{
  document.getElementById('topic').innerHTML = '加载数据出现错误：' + xhr.responseText;
}

vote_url = SubjectUrl + 'mod/vote/listview.py?guid=${specialSubject.objectGuid}&type=specialsubject&tmp=' + Date.parse(new Date());
new Ajax.Request(vote_url, { 
  method: 'get',
  onSuccess: function(xhr){load_success(xhr); },
  onException: function(xhr){load_exception(xhr);},
  onFailure: function(){load_exception(xhr);}
}
);

function load_success(xhr)
{
  document.getElementById('vote').innerHTML = xhr.responseText;
}

function load_exception(xhr)
{
  document.getElementById('vote').innerHTML = '加载数据出现错误：' + xhr.responseText;
}
</script>
<#include "/WEB-INF/subjectpage/subject_footer.ftl">
</body>
</html>