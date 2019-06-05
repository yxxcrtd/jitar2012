<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 协作组</title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/gradesubject_group.js" type="text/javascript"></script>
<script type="text/javascript">
var categoryId = 0; //分类
var k = "${k!}"; //关键字
var gradeId = 0; //学段
var subjectId = 0; //学科
</script>   
</head>
<body>
<#include 'site_head.ftl'>
<!--协作组 Start-->
<div class="secMain mt25 clearfix">
<div class="left">
    <!--推荐协作组 Start-->
    <div class="secLeftW border">
        <h3 class="h3Head textIn"><a href="groups.action?type=rcmd" class="more">更多</a>推荐协作组</h3>
        <div class="cooperation clearfix">
        <#if (rcmd_group_list??) && (rcmd_group_list?size > 0) >
          <#assign g = rcmd_group_list[0] >
            <a href="${SiteUrl}go.action?groupId=${g.groupId}" class="coopImg"><img src='${Util.url(g.groupIcon!'images/group_default.gif')}' width='64' height='64' border='0' /></a>
            <h4><a href="${SiteUrl}go.action?groupId=${g.groupId}">${g.groupTitle!?html}</a></a></h4>
            <p>创建时间：${g.createDate?string('yyyy-MM-dd')}</p>
            <p>简介：<span>${g.groupIntroduce!}</span></p>
            <ul class="ulList mt10">
                <#list rcmd_group_list as g >
                  <#if (g_index > 0) >
                    <li><em class="emDate">${g.createDate?string('yyyy/MM/dd')}</em><a href="${SiteUrl}go.action?groupId=${g.groupId}">${g.groupTitle!?html}</a></li>
                </#if>
                </#list>
            </ul>
        </#if>   
        </div>
        <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize5" /></div>
    </div>
    <!--推荐协作组 End-->
    
    <!--热门协作组 Start-->
    <div class="secLeftW mt3 border">
        <h3 class="h3Head textIn"><a href="groups.action?type=hot" class="more">更多</a>热门协作组</h3>
        <div class="cooperation clearfix">
            <#if (hot_group_list??) && (hot_group_list?size > 0) >
              <#assign g = hot_group_list[0] >            
            <a href="${SiteUrl}go.action?groupId=${g.groupId}" class="coopImg"><img src='${Util.url(g.groupIcon!'images/group_default.gif')}' width='64' height='64' border='0' /></a>
            <h4><a href="${SiteUrl}go.action?groupId=${g.groupId}">${g.groupTitle!?html}</a></h4>
            <p>创建时间：${g.createDate?string('yyyy-MM-dd')}</p>
            <p>简介：<span>${g.groupIntroduce!}</span></p>
            </#if>
        </div>
        <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize5" /></div>
    </div>
    <!--推荐协作组 End-->
    <!--排行榜 Start-->
    <div class="secLeftW border coopTopWrap mt3">
        <h3 class="h3Head textIn">协作组活跃度排行</h3>
        <div class="coopTop">
            <ul class="ulList">
                <#if group_activity_list?? >
                <#list group_activity_list as group>
                <#if group_index<3>
                    <li><span class="numIcon numOrange">${group_index + 1}</span><a href="${SiteUrl}go.action?groupId=${group.groupId}">${group.groupTitle}</a></li>
                <#else>
                    <li><span class="numIcon">${group_index + 1}</span><a<#if group.groupTitle!?length &gt; 14> title="${group.groupTitle!}"</#if> href="${SiteUrl}go.action?groupId=${group.groupId}">${Util.getCountedWords(group.groupTitle!?html,14)}</a></li>
                </#if>
                </#list>
                </#if>
            </ul>
        </div>
        <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize5" /></div>
    </div>
    <!--排行榜 End-->
</div>
<!--页面右栏 Start-->
<div class="right">
    <!--搜索 Start-->
    <#include 'groups/search.ftl'>
    <!--搜索 End-->
    <!--列表内容 Start-->
    <div class="secRightW mt3 border">
        <h3 class="h3Head textIn">
        <#if type="rcmd">推荐协作组
        <#elseif type="hot">热门协作组
        <#elseif type="best">优秀协作组
        <#else>最新协作组
        </#if>
       </h3>
        <!--循环 Start-->
        <div class="cooperation clearfix" style="min-height:480px">
            <#if group_list?? >
            <#list group_list as group>
            <dl class="coopList">
                <dt><a href="${SiteUrl}go.action?groupId=${group.groupId}"><img src='${Util.url(group.groupIcon!SiteUrl + "images/group_default.gif")}' width='120' height='100' border='0' /></a></dt>
                <dd style="width:500px;">
                    <h4><a href="${SiteUrl}go.action?groupId=${group.groupId}">${group.groupTitle!?html}</a></h4>
                    <p>创建时间 <span>${group.createDate?string('yyyy/MM/dd')}</span> 成员 <span>${group.userCount}</span> 访问 <span>${group.visitCount}</span> 文章 <span>${group.articleCount}</span> 主题 <span>${group.topicCount}</span>  资源 <span>${group.resourceCount}</span></p>
                    <p>学段学科 ${group.XKXDName!?html}</p>
                    <p class="coopTag">标签
                      <#list Util.tagToList(group.groupTags!) as tag>
                          <a href='${SiteUrl}showTag.action?tagName=${tag?url("UTF-8")}'>${tag!?html}</a>
                        </#list>                         
                    </p>
                    <p>
                        <span class="coopDes">
                            ${group.groupIntroduce!}
                            <span class="comma1"></span>
                            <span class="comma2"></span>
                        </span>
                    </p>
                </dd>
            </dl>
            </#list>
            </#if>
            <#include 'pager.ftl'>
        </div>
        <!--循环 End-->
        <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize6" /></div>
    </div>
    <!--列表内容 End-->
</div>
<!--页面右栏 End-->
</div>
<!--协作组 End-->
<!--工作室 End-->
<#include '/WEB-INF/ftl2/footer.ftl'>
<!--[if IE 6]>
<script src="${ContextPath}js/new/DD_belatedPNG.js" type="text/javascript"></script>
<script type="text/javascript">
  DD_belatedPNG.fix('.topSearch,.login a,.videoPlay,.videoPlayBg,.tx,.liW,.liX,.liP,.leftNavS li,.leftNavIcon,.leftNavIconH,.folder,.liFolder');
</script>
<![endif]-->

</body>
</html>

