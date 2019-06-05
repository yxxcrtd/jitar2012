<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${subject.subjectName} <#include ('/WEB-INF/ftl/webtitle.ftl') ></title>
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/index.css" />  
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/dtree.css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/dtree.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
 </head>
<body>
<#include "/WEB-INF/subjectpage/subject_header.ftl">
<#include "/WEB-INF/subjectpage/subject_nav.ftl">

<div style="height:8px;font-size:0"></div>

<!-- 资源搜索表单 -->
<div class='orange_border bg1' style='padding:4px;'>
<form method='get' style='text-align:center;'>
  <input type='hidden' name='subjectId' value='${subject.metaSubject.msubjId!}' />  
  <input type='hidden' name='gradeId' value='${subject.metaGrade.gradeId!}' />
  <input type='hidden' name='type' value="${type!?html}" />
  关键字：<input class='s_input2' name='k' />   
<#if res_cates?? >
  资源类型：<select name='categoryId'>
      <option value=''>所有类型</option>
    <#list res_cates.all as c >
      <#if categoryId?? && categoryId == c.categoryId >
        <option value='${c.categoryId}' selected='selected'>${c.treeFlag2 + c.name}</option>
      <#else>
      <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
      </#if>
    </#list >
    </select>
</#if >
  <input type='image' src='${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/b_s.gif' align='absmiddle' />
</form>
</div>

<div style='height:8px;font-size:0;'></div>
<table style='width:100%;' cellpadding='0' cellspacing='0'>
<tr style='vertical-align:top;'>
<td style='width:25%'>
    <!-- 资源分类 -->
	<#if outHtml?? >
	<div class='orange_border'>
	  <div class='tree'>
	    <script type="text/javascript">
	        d = new dTree("d");
	        d.add(0,-1,"<b>本站资源</b>","resource.py?id=${subject.subjectId}&amp;subjectId=${subject.metaSubject.msubjId}&amp;gradeId=${grade.gradeId}&amp;target=child&amp;unitId=${unitId!}");
	        ${outHtml}
	        document.write(d);
	        d.openAll();
	  </script>
	  </div>
	</div>
	</#if>
<div style='height:8px;font-size:0;'></div>
	<!-- 排行榜 -->
		<div class='orange_border'>
		  <div id="rank_" class='tab2'>
		    <label style='width:46px;'>排行榜</label>            
		    <div class="cur" onmouseover="TabUtil.changeTab('rank_',0)" style='padding-left:4px;padding-right:3px;'><a href='#'>资源上载排行</a></div>
		    <div class="" onmouseover="TabUtil.changeTab('rank_',1)" style='padding-left:4px;padding-right:3px;'><a href='#'>资源下载排行</a></div>
		    <div class="spacer"></div>
		  </div>    
		
		  <div class='tab_content' style='padding:10px;'>
		    <!-- 资源上载排行 -->
		    <div id='rank_2' style='display:none'></div>
		    <div id='rank_0' style='display:block'>
			<#if upload_sorter?? >
				<table  cellpadding='1' cellpadding='1' width='100%'>
				  <#list upload_sorter as user>
				  	<tr>
				  	<td class="rank_left">${user_index + 1}</td>
				  	<td class="rank_right">
				  	<span style='float:right'>${user.resourceCount!}</span>
				  	<span style='float:leftt'>
				  	<a href="${SiteUrl}go.action?loginName=${user.loginName}" target="_blank">${user.nickName}</a>
				  	</span>
				  	</td>
				  	</tr>
				  </#list>
				</table>
			</#if>
			</div>
			    
		    <!-- 资源下载排行 -->
		    <div id='rank_1' style='display:none'>
		    <!-- 资源下载排行 -->
		      <#if download_resource_list??>
		      <table border='0' cellpadding='1' cellpadding='1' width='100%'>
		      <#list download_resource_list as r>
		        <tr valign='top'>
		        <td class="rank_left">${r_index + 1}</td>
		        <td class="rank_right">
		        <a title='${r.title!?html}' href='${SiteUrl}showResource.py?resourceId=${r.resourceId}'>${Util.getCountedWords(r.title!?html,12)}</a>
		        </td>
		        <td align='right'>${r.downloadCount!}</td>
		        </tr>
		      </#list>
		       </table>
		      <#else>
		       暂无排行
		      </#if>
		    </div>
		    
		  </div>
		</div>			
</td>
<td style='padding-left:10px;'>

    <!-- 资源列表 -->
    <div class='orange_border'>
    	<div id="article_" class='tab2' style='font-weight:bold;'>		
			<#if !(type??) ><#assign type = "new" ></#if >
			<div class="${(type == 'rcmd')?string('cur', '') }" style='border-left:0'><a href='resource.py?type=rcmd&amp;subjectId=${subject.metaSubject.msubjId}&gradeId=${grade.gradeId!}&categoryId=${categoryId!}&unitId=${unitId!}'>网编推荐</a></div>
      		<div class="${(type == 'new')?string('cur', '') }"><a href='resource.py?type=new&amp;subjectId=${subject.metaSubject.msubjId}&gradeId=${grade.gradeId!}&categoryId=${categoryId!}&unitId=${unitId!}'>最新发布</a></div>
      		<div class="${(type == 'hot')?string('cur', '') }"><a href='resource.py?type=hot&amp;subjectId=${subject.metaSubject.msubjId}&gradeId=${grade.gradeId!}&categoryId=${categoryId!}&unitId=${unitId!}'>最高人气</a></div>
      		<div class="${(type == 'cmt')?string('cur', '') }"><a href='resource.py?type=cmt&amp;subjectId=${subject.metaSubject.msubjId}&gradeId=${grade.gradeId!}&categoryId=${categoryId!}&unitId=${unitId!}'>评论最多</a></div>
 		</div>
    		
	  <div class='tab_content'>
	    <div id="jitar_res_0"  style="display: block;">		    
	    	<table border="0" cellspacing='0' class='res_table'>
	    	<thead>
		  	<tr>
		  	<td class='td_left' style='padding-left:10px;'><nobr>标题</nobr></td>
		  	<td class='td_middle'><nobr>年级</nobr></td>
		  	<td class='td_middle'><nobr>类型</nobr></td>
		  	<td class='td_middle'><nobr>大小</nobr></td>
		  	<td class='td_middle'><nobr>上传者</nobr></td>
		  	<td class='td_right'><nobr>上传日期</nobr></td>
		  	</tr>
		  	</thead>
		  	<tbody>
		  	<!-- 资源列表 -->
		  	   <#if resource_list??>
		  		<#list resource_list as r>
			    <tr>
				  	<td style='padding-left:10px;width:100%'><a href='${SiteUrl}showResource.py?resourceId=${r.resourceId}'<#if r.title?length &gt; 30> title="${r.title}"</#if>><img src='${Util.iconImage(r.href!)}' border='0' align='absmiddle' /> ${Util.getCountedWords(r.title!?html,30)}</a></td>
				  	<td><nobr>${r.gradeName!?html}</nobr></td>
				  	<td><nobr>${r.scName!?html}</nobr></td>
				  	<td align='right'><nobr>${Util.fsize(r.fsize!)}</nobr></td>
				  	<td><nobr><a href='${SiteUrl}go.action?loginName=${r.loginName}' target='_blank'>${r.nickName!?html}</a></nobr></td>
				  	<td><nobr>${r.createDate?string('yyyy-MM-dd')}</nobr></td>
				  	</tr>
			    </#list> 
			   </#if> 
		  	</tbody>
	      </table>
	      <div class='pgr'><#include '/WEB-INF/ftl/inc/pager.ftl' ></div>
	    </div>
	  </div>
	</div>
</td>

</tr>
</table>


<div style='clear:both;'></div>
<#include "/WEB-INF/subjectpage/subject_footer.ftl">
</body>
</html>