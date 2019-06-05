<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 工作室</title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/gradesubject_blogs.js" type="text/javascript"></script>
</head>
<body>
<!--nav End-->
<!--工作室正文 Start-->
<#include 'site_head.ftl'>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript" charset="utf-8"></script>
<div class="main mt25 clearfix">
    <!--工作室左栏 Start-->
    <div class="left">
        <!--名师工作室 Start-->
        <div class="secLeftW border">
            <h3 class="h3Head textIn"><a href="blogList.action?type=1" class="more">更多</a>${Util.typeIdToName(1)}工作室</h3>
            <div class="studio">
               <#if famous_teachers?? && (famous_teachers?size>0)>
               <#list famous_teachers as user>
                  <#if user_index &lt; 3>
		                <dl class="dList">
		                    <dd><a href="${SiteUrl}go.action?loginName=${user.loginName}"><img width='64' height='64' src="${SSOServerUrl +'upload/'+ (user.userIcon!'${SiteUrl}images/default.gif')}" onerror="this.src='${SiteUrl}images/default.gif'" /></a></dd>
		                    <dt>
		                        <h4><a href="${SiteUrl}go.action?loginName=${user.loginName}" title="${user.blogName!}" target='_blank'>${Util.getCountedWords(user.blogName!?html,9)}</a></h4>
		                        <p title="${user.trueName!}" >真实姓名:${Util.getCountedWords(user.trueName!?html,5)}</p>
		                        <p>访问数:${user.visitCount}</p>
		                    </dt>
		                </dl>
	              </#if>
                </#list>
              </#if>
            </div>
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize5" /></div>
        </div>
        <!--名师工作室 End-->
        <!--学科带头人 Start-->
        <div class="secLeftW border mt3">
            <h3 class="h3Head textIn"><a href="blogList.action?type=3" class="more">更多</a>${Util.typeIdToName(3)}工作室</h3>
            <div class="takeLead">
              <#if expert_list?? && (expert_list?size>0)>
               <#list expert_list as user>
                <#if user_index &lt; 2>
	                <dl class="dList">
	                    <dd><a href="${SiteUrl}go.action?loginName=${user.loginName}"><img src='${SSOServerUrl +'upload/'+ (user.userIcon!'${SiteUrl}images/default.gif')}' onerror="this.src='${SiteUrl}images/default.gif'" width='64' height='64'/></a></dd>
	                    <dt>
	                        <h4><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank' title="${user.blogName!}">${Util.getCountedWords(user.blogName!?html,9)}</a></h4>
	                        <p title="${user.trueName!}">真实姓名:${Util.getCountedWords(user.trueName!?html,5)}</p>
	                        <p>访问数:${user.visitCount}</p>
	                    </dt>
	                </dl>
               </#if>
               </#list>
            <div class="secTagWrap">
                <p class="secTag">
                    <#list expert_list as user>
	                      <#if user_index &gt;1 && user_index &lt; 4>
	                    	 <a href="${SiteUrl}go.action?loginName=${user.loginName}" class="secTagC" title="${user.blogName!}">${Util.getCountedWords(user.blogName!?html,5)}</a>
	                      </#if>
                    </#list>
                </p>
            </div>
            </#if>
            </div>
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize5" /></div>
        </div>
        <!--学科带头人 End-->
        
        <!--教研员工作室 Start-->
        <div class="secLeftW border mt3">
            <h3 class="h3Head textIn"><a href="blogList.action?type=4" class="more">更多</a>${Util.typeIdToName(4)}工作室</h3>
            <div class="takeLead">
            <#if comissioner_list?? && comissioner_list?size &gt; 0 >  
               <#list comissioner_list as u>
               <#if u_index &lt; 2>
	                <dl class="dList">
	                    <dd><a href="${SiteUrl}go.action?loginName=${u.loginName}"><img  width='64' height='64' src="${SSOServerUrl +'upload/'+ (u.userIcon!'${SiteUrl}images/default.gif')}" onerror="this.src='${SiteUrl}images/default.gif'"/></a></dd>
	                    <dt>
	                        <h4><a href='${SiteUrl}go.action?loginName=${u.loginName}' title="${u.blogName}">${Util.getCountedWords(u.blogName!?html,7)}</a></h4>
	                        <p>真实姓名:${u.trueName!?html}</p>
	                        <p>访问数:${u.visitCount}</p>
	                    </dt>
	                </dl>
               </#if>
                </#list>
                <div class="secTagWrap">
                    <p class="secTag">
                       <#list comissioner_list as u >
                         <#if u_index &gt; 1 && u_index &lt; 4>
                           <a href="${SiteUrl}go.action?loginName=${u.loginName}" class="secTagC" title="${u.blogName!}">${Util.getCountedWords(u.blogName!?html,7)}</a>
                         </#if>
                       </#list>
                    </p>
                </div>
            </#if>
            </div>
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize5" /></div>
        </div>
        <!--教研员工作室 End-->
    </div>
    <!--工作室左栏 End-->
    
    <!--工作室右栏 Start-->
    <div class="right">
        <!--搜索 Start-->
        <div class="secRightW border">     
            <div class="secSearch">
                <b>关键字</b>
                <div class="secSearchBg secSearchBgW1">
                    <span class="secSearchBgR"></span>
                    <input type="text" id='k' class="secSearchInput" value="" placeholder="关键词" />
                </div>
                <b>学段</b>
                <div class="secSearchBg secSearchBgW1">
                    <span class="secSearchBgR"></span>
                    <span class="arrow4 secSearchBgArrow"></span>
                    <input  type="text" class="secSearchText"  placeholder="全部学段" id="secInput1"/>
                    <#if grade_list?? && grade_list?size &gt; 0>
                     <div class="secSearchSelectWrap" id="secSelectWrap1">
	                    <ul class="secSearchSelect">
	                        <li value='0'><a href="javascript:void(0);">全部学段</a></li>
	                    	<#list grade_list as grade >
	                        	<li value="${grade.gradeId!}" ><a>${grade.isGrade?string(grade.gradeName!?html, '&nbsp;&nbsp;' + grade.gradeName!?html) }</a></li>
	                        </#list>
	                    </ul>
	                </div>
                    </#if>
                </div>
                <b>学科</b>
                <div class="secSearchBg secSearchBgW1">
                    <span class="secSearchBgR"></span>
                    <span class="arrow4 secSearchBgArrow"></span>
                    <input type="text" class="secSearchText"  placeholder="所有学科" id="secInput2" />
                    <#if subject_list?? && subject_list?size &gt; 0>
                    <div class="secSearchSelectWrap" id="secSelectWrap2">
                    <ul class="secSearchSelect">
                      <li value='0'><a href="javascript:void(0);">全部学科</a></li>
                      <#list subject_list as subj >
                         <li value="${subj.msubjId!}"><a>${subj.msubjName}</a></li>
                      </#list>
                    </ul>
                    </div>
                    </#if>
                </div>
                <b>分类</b>
                <div class="secSearchBg secSearchBgW2">
                    <span class="secSearchBgR"></span>
                    <span class="arrow4 secSearchBgArrow"></span>
                    <input type="text" class="secSearchText"  placeholder="所有分类" id="secInput3" />
                    <#if syscate_tree??>
                    <div class="secSearchSelectWrap" id="secSelectWrap3">
	                    <ul class="secSearchSelect">
	                     <li value='0'><a href="javascript:void(0);">全部分类</a></li>
	                     <#list syscate_tree.all as category>
	                        <li value="${category.categoryId!}"><a>${category.treeFlag2} ${category.name}</a></li>
	                     </#list>
	                    </ul>
	                </div>
                    </#if>
                </div>
                <b><a id="blogsSearch" class="secSearchBtn" >搜索</a></b>
            </div>
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize6" /></div>
        </div>
        <!--搜索 End-->
        <div style="height:1px;font-size:0"></div>
        <div class="trainWrap mt3 clearfix">
            <!--最新工作室 Start-->
            <div class="secRightC1 border">
                <h3 class="h3Head textIn"><a href="blogList.action?type=new" class="more">更多</a>最新工作室</h3>
                <div class="train trainL">
                <#if new_blog_list?? && new_blog_list?size &gt; 0>
                    <#list new_blog_list[0..0] as user>
	                    <dl class="dList">
	                        <dd><a href="${SiteUrl}go.action?loginName=${user.loginName}"><img src="${SSOServerUrl +'upload/'+ (user.userIcon!'${SiteUrl}images/default.gif')}" onerror="this.src='${SiteUrl}images/default.gif'" width='116' height='116'/></a></dd>
	                        <dt>
	                            <h4><a href="${SiteUrl}go.action?loginName=${user.loginName}">${user.blogName}</a></h4>
	                            <p>真实姓名:${user.trueName}</p>
	                            <p>简介：<span>${Util.getCountedWords(user.blogIntroduce!?html,43)}</span></p>
	                        </dt>
	                    </dl>
                    </#list>
                   <div class="secTagWrap">
                      <#if new_blog_list?size &gt; 3 >
                      <#list new_blog_list[1..3] as user>
                        <a href="${SiteUrl}go.action?loginName=${user.loginName}" class="secTagC" title="${user.blogName!}">${Util.getCountedWords(user.blogName!?html,5)}</a>
                      </#list>
                      <#elseif new_blog_list?size &gt; 1>
                      <#list new_blog_list[1..new_blog_list?size-1] as user>
                       <a href="${SiteUrl}go.action?loginName=${user.loginName}" class="secTagC" title="${user.blogName!}">${Util.getCountedWords(user.blogName!?html,5)}</a>
                       </#list>
                      </#if>
                    </div>
                </#if>
                </div>
                <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize7" /></div>
            </div>
            <!--最新工作室 End-->
            <!--研修之星 Start-->
            <div class="secRightC2 border">
                <h3 class="h3Head textIn"><a href="blogList.action?type=5" class="more">更多</a>${Util.typeIdToName(5)}</h3>
                
	                <div class="train trainR">
	                <#if teacher_star?? >
	                   <#list teacher_star as teacher_star_0>
	                    <dl class="dList">
	                        <dd><a href="${SiteUrl}go.action?loginName=${teacher_star_0.loginName}"><img src="${SSOServerUrl +'upload/'+ (teacher_star_0.userIcon!'${SiteUrl}images/default.gif')}" onerror="this.src='${SiteUrl}images/default.gif'" height='64' width='64'/></a></dd>
	                        <dt>
	                            <h4><a href="${SiteUrl}go.action?loginName=${teacher_star_0.loginName}" title="${teacher_star_0.trueName!}">${Util.getCountedWords(teacher_star_0.trueName!?html,5)}</a></h4>
	                            <p>标签：${Util.getCountedWords(teacher_star_0.userTags!?html,7)}</p>
	                            <p>简介：<span>${Util.getCountedWords(teacher_star_0.blogIntroduce!?html,10)}</span></p>
	                        </dt>
	                    </dl>
	                   </#list>
                </#if>
	                </div>
                <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize8" /></div>
            </div>
            <!--研修之星 End-->
        </div>
        <div class="trainWrap mt3 clearfix">
            <!--热门工作室 Start-->
            <div class="secRightC1 border">
                <h3 class="h3Head textIn"><a href="blogList.action?type=hot" class="more">更多</a>热门工作室</h3>
	                <div class="hotStudio trainL">
	                <#if (hot_blog_list??) && (hot_blog_list?size > 0) >
	                  <#list hot_blog_list as user>
	                  <#if user_index == 0>
		                    <dl class="dList">
		                        <dd><a href="${SiteUrl}go.action?loginName=${user.loginName}"><img src='${SSOServerUrl +'upload/'+ (user.userIcon!'${SiteUrl}images/default.gif')}' onerror="this.src='${SiteUrl}images/default.gif'" width='116' height='116' /></a></dd>
		                        <dt>
		                            <h4><a href="${SiteUrl}go.action?loginName=${user.loginName}" title="${user.blogName!}">${Util.getCountedWords(user.blogName!?html,10)}</a></h4>
		                            <p>真实姓名:${user.trueName!?html}</p>
		                            <p>简介：<span>${user.blogIntroduce!}</span></p>
		                        </dt>
		                    </dl>
		              </#if>
	                  </#list>
	                <div class="secTagWrap">
	                <#if new_blog_list?size &gt; 6 >
	                   <#list hot_blog_list[1..6] as user>
	                   	 <a href="${SiteUrl}go.action?loginName=${user.loginName}" class="secTagC" target='_blank' title="${user.trueName!?html}">${Util.getCountedWords(user.trueName!?html,7)}</a>
	                   </#list>
	                 <#elseif new_blog_list?size &gt; 1>
	                 <#list hot_blog_list[1..new_blog_list?size-1] as user>
                       <a href="${SiteUrl}go.action?loginName=${user.loginName}" class="secTagC" target='_blank' title="${user.trueName!?html}">${Util.getCountedWords(user.trueName!?html,7)}</a>
                     </#list>
	                 </#if>
	                </div>
              </#if>
	              </div>
                <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize7" /></div>
            </div>
            <!--热门工作室 End-->
            <!--排行榜 Start-->
            <div class="secRightC2 border">
                <h3 class="h3Head">
                    <a href="blogList.action?type=hot" class="more">更多</a>
                    <a href="blogList.action?type=score" class="more none">更多</a>
                    <a href="blogList.action?type=hot" class="sectionTitle active">访问排行榜<span></span></a>
                    <a href="blogList.action?type=score" class="sectionTitle">积分排行榜<span></span></a>                    
                </h3>
                
                <div class="secTop">
                <#if blog_visit_charts??>
                    <ul class="ulList">
                      <#list blog_visit_charts as user>
                       <#if (user_index &lt; 3) >
                        	<li>
	                        	<em class="emDate">${user.visitCount}</em>
	                        	<span class="numIcon numRed">${user_index + 1}</span>
	                        	<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank' title="${user.blogName!}">
	                        	   ${Util.getCountedWords(user.blogName!?html,10)}
	                        	</a>
                        	</li>
					         <#else>
					       <li><em class="emDate">${user.visitCount}</em><span class="numIcon">${user_index + 1}</span><a href="${SiteUrl}go.action?loginName=${user.loginName}" target='_blank' title="${user.blogName!}">${Util.getCountedWords(user.blogName!?html,10)}</a></li>                        	
                       </#if>
                    </#list>
                  </ul>
                  
                </#if>
                </div>
                
	                <div class="secTop none">
	                <#if blog_score_charts??>
	                    <ul class="ulList">
	                        <#list blog_score_charts as user>
	                           <#if user_index &lt; 3>
	                        	<li><em class="emDate">${user.userScore}</em><span class="numIcon numRed">${user_index + 1}</span><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank' title="${user.blogName!}">${Util.getCountedWords(user.blogName!?html,10)}</a></li>
	                           <#else>
	                        	<li><em class="emDate">${user.userScore}</em><span class="numIcon">${user_index + 1}</span><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank' title="${user.blogName!}">${Util.getCountedWords(user.blogName!?html,10)}</a></li>
	                           </#if>
	                        </#list>
	                    </ul>
                </#if>
	                </div>
                <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize8" /></div>
            </div>
            <!--排行榜 End-->
        </div>
        <!--推荐工作室 Start-->
	        <div class="secRightW border mt3">
	            <h3 class="h3Head textIn"><a href="blogList.action?type=2" class="more">更多</a>${Util.typeIdToName(2)}工作室</h3>
	            <div class="secRecommend">
                <#if rcmd_list?? && rcmd_list?size &gt; 0 >
	                <ul class="secRecList">
	                  <#if rcmd_list?size &gt; 11>
	                  <#assign rcmd = rcmd_list[0..11] />
	                  <#else>
	                  <#assign rcmd = rcmd_list[0..rcmd_list?size-1] />
	                  </#if>
	                  <#list rcmd as wr>
	                    <li>
	                        <a href="${SiteUrl}go.action?loginName=${wr.loginName}"><img src='${SSOServerUrl +'upload/'+ (wr.userIcon!'${SiteUrl}images/default.gif')}' onerror="this.src='${SiteUrl}images/default.gif'" width='80' height='80' /></a>
	                        <a href="${SiteUrl}go.action?loginName=${wr.loginName}" title="${wr.blogName!}">${Util.getCountedWords(wr.blogName!?html,5)}</a>
	                    </li>
	                  </#list>
	                </ul>     
	                </#if>
	            </div>
	            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize6" /></div>
	        </div>  
        <!--推荐工作室 End-->
    </div>
    <!--工作室右栏 End-->
</div>
<script>
 $("#blogsSearch").bind("click",function(){ 
  k=$("#k").val();;
  gradeId='undefined'!=typeof($("#secInput1").attr("gradeId"))?$("#secInput1").attr("gradeId"):-1;
  subjectId='undefined'!=typeof($("#secInput2").attr("subjectId"))?$("#secInput2").attr("subjectId"):-1;
  categoryId='undefined'!=typeof($("#secInput3").attr("categoryId"))?$("#secInput3").attr("categoryId"):-1;
  //alert("gradeId: "+gradeId+" "+"subjectId:  "+subjectId+" categoryId: "+categoryId);
  var url='blogList.action?type=search&k='+k+'&gradeId='+gradeId+'&categoryId='+categoryId+'&subjectId='+subjectId+'';
  window.location.href=url;
  }).bind('mouseover',function(){
     $(this).css('cursor','pointer');
  });
</script>
<#include 'footer.ftl'>