<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>系统后台管理</title>
	<link rel="stylesheet" type="text/css" href="../css/manage/left.css" />
	<script>
	if(window.name=='main')
	{
	  window.top.location = 'admin.py';
	}
	
	if (window.top == window) {
    // 如果自己在顶部窗口打开, 显示是返回链接哪里不正确, 此时去打开 'admin.py'.
    window.top.location = 'admin.py';
	}
	</script>
</head>

<body>
<div class="panel">
<#if isSystemUserAdmin??>
	<#-- 用户管理：系统管理员和系统用户管理员可以管理  -->
	<div id='Title_1' class="panelName_cur"><a href='javascript:void(0);' onclick='return toggle(1);'>用户管理</a></div>
  	<div id='Menu_1' class="panelItems" style='display:block'>
    	<ul>
      	<li><a href="admin_user_list.py?cmd=list" target='main' onclick='ac(this)'>用户管理</a></li>
      	<li><a href="admin_systemadmin_manager.py?cmd=list" target='main' onclick='ac(this)'>系统管理员</a></li>
      	<li><a href='admin_user_manager.py' target='main' onclick='ac(this)'>整站用户管理员</a></li>
        <li><a href='admin_content_manager.py' target='main' onclick='ac(this)'>整站内容管理员</a></li>
        <li><a href="admin_unit_manager_by_unit_main.py" target='main' onclick='ac(this)'>机构管理员</a></li>
        <li><a href="admin_subject_manager_by_subject_main.py" target='main' onclick='ac(this)'>学科管理员</li>
        <#if hasChennels??>
        <li><a href="admin_channel_manager_by_channel_main.py" target='main' onclick='ac(this)'>频道系统管理员</li>
        </#if>        
        <li><a href="admin_user_list.py?cmd=list&type=unaudit" target='main' onclick='ac(this)'>待审核</a>|<a href="admin_user_list.py?cmd=list&type=locked" target='main' onclick='ac(this)'>已锁定</a>|<a href="admin_user_list.py?cmd=list&type=deleted" target='main' onclick='ac(this)'>已删除</a></li>
        <#if userTypeList??>
        <hr/>
        <#list userTypeList as ut>
        <li><a href="admin_user_list.py?typeId=${ut.typeId}" target='main' onclick='ac(this)'>${ut.typeName!?html}</a></li>
        </#list>
        </#if>
        <#--
        <li><a href="admin_user.py?cmd=list&type=famous" target='main' onclick='ac(this)'>${mingshiName}</a>
            | <a href="admin_user.py?cmd=list&type=recommend" target='main' onclick='ac(this)'>推荐</a> 
            | <a href="admin_user.py?cmd=list&type=expert" target='main' onclick='ac(this)'>${subjectStarName}</a>
            | <a href="admin_user.py?cmd=list&type=comissioner" target='main' onclick='ac(this)'>教研员</a>
        </li>
        <li><a href='admin_user.py?cmd=list&type=star' target='main' onclick='ac(this)'>研修之星</a>
            | <a href='admin_user.py?cmd=list&type=show' target='main' onclick='ac(this)'>教师风采</a>
        </li>
        -->
        <#if columnlist??>
        <hr/>
        <li><a href='column_user.py' target='main' onclick='ac(this)'>特定栏目管理员</a></li>
        </#if>
      </ul>
    </div>
  </#if>
  
  <#if isSystemAdmin?? >
    <#-- 系统管理：系统管理员  -->
    <div id='Title_2' class="panelName_cur"><a href='javascript:void(0);' onclick='return toggle(2);'>系统设置</a></div>
    <div id='Menu_2' class="panelItems" style='display:'>
      <ul>		
		    <li><a href="admin_site.py" target="main" onclick='ac(this)'>站点配置</a></li>
        <li><a href="admin_sys.action?cmd=list" target="main" onclick='ac(this)'>系统配置</a></li>
        <li><a href="site_screen.py" target="main" onclick='ac(this)'>屏蔽选项</a></li>
        <li><a href="setupUpload.py?cmd=list" target="main" onclick='ac(this)'>资源上载设置</a></li>
        <#--<li><a href="site_theme.py" target="main" onclick='ac(this)'>网站样式配置</a></li>-->
        <li><a href="site_nav.py" target="main" onclick='ac(this)'>定制网站导航</a></li>
        <li><a href="usercate.py" target="main" onclick='ac(this)'>自定义内容分类</a></li>
        <li><a href="usercate_article.py" target="main" onclick='ac(this)'>自定义分类文章</a></li>
        <li><a href="site_index.py" target="main" onclick='ac(this)'>定制网站首页</a></li>
        <li><a href="system_plugin.py" target="main" onclick='ac(this)'>插件模块配置</a></li>
		    <li><a href="admin_category.py?cmd=list&type=blog" target="main" onclick='ac(this)'>工作室分类</a></li>
		    <li><a href="admin_category.py?cmd=list&type=group" target="main" onclick='ac(this)'>协作组分类</a></li>
        <li><a href="admin_category.py?cmd=list&type=default" target="main" onclick='ac(this)'>文章分类</a></li>
    		<li><a href="admin_category.py?cmd=list&type=resource" target="main" onclick='ac(this)'>资源分类</a></li>
    		<li><a href="admin_category.py?cmd=list&type=video" target="main" onclick='ac(this)'>视频分类</a></li>
    		<li><a href="admin_category.py?cmd=list&type=photo" target="main" onclick='ac(this)'>图片分类</a></li>
    		<!--<li><a href="admin_school_link.py?cmd=list" target="main" onclick='ac(this)'>机构风采管理</a></li>-->
    		<#if platformType?? && platformType == '2'>
    		<li><a href="${SiteUrl}push/admin_push_article_list.py" target="main" onclick='ac(this)'>文章推送</a></li>
    		<li><a href="${SiteUrl}push/admin_push_resource_list.py" target="main" onclick='ac(this)'>资源推送</a></li>
    		<li><a href="${SiteUrl}push/admin_push_famous_list.py" target="main" onclick='ac(this)'>名师推送</a></li>
    		</#if>
    		<#if platformType?? && platformType == '1'>
    		<li><a href="${SiteUrl}mashup/admin_mashup_article_list.py" target="main" onclick='ac(this)'>推送文章审核</a></li>
    		<li><a href="${SiteUrl}mashup/admin_mashup_resource_list.py" target="main" onclick='ac(this)'>推送资源审核</a></li>
    		<li><a href="${SiteUrl}mashup/admin_mashup_famous_list.py" target="main" onclick='ac(this)'>推送名师审核</a></li>
    	    <li><a href="${SiteUrl}mashup/admin_mashup_platform.py" target="main" onclick='ac(this)'>更改区县域名</a></li>
    		</#if>
    		<#--		    
    		<li><a href="admin_rankfield.py?cmd=show" target="main" onclick='ac(this)'>排行榜项目</a></li>
        <li><a href="admin_site_update.py" target="main" onclick='ac(this)'>网站升级公告</a></li>
    		-->
    		<li><a href="channel.action" target="_top" onclick='ac(this)'>自定义频道管理</a></li>
    		<li><a href="admin_js_genernate.py" target="main" onclick='ac(this)'>外站调用代码生成器</a></li>
    		<hr/>
        <li><a href="split_article_table.py" target="main" onclick='ac(this)'>文章数据库分表</a></li>
        <li><a href="count_history_article.py" target="main" onclick='ac(this)'>重新统计历史文章数</a></li>
        <#--<li><a href="count_history_article_subject.py" target="main" onclick='ac(this)'>重新统计学科历史文章数</a></li>-->
      	<li><a href="admin_history_article_subject.py" target="main" onclick='ac(this)'>统计学科历史文章数</a></li>
      	<li><a href="admin_history_article_unit.py" target="main" onclick='ac(this)'>统计机构历史文章数</a></li>
      </ul>
    </div>
  </#if>
  <#if isSystemContentAdmin?? || isSystemAdmin?? >
   <#-- 系统管理员可以管理新闻公告管理 -->
   <div id='Title_8' class="panelName_cur"><a href='javascript:void(0);' onclick='return toggle(8);'>新闻公告管理</a></div>
    <div id='Menu_8' class="panelItems" style='display:'>
     <ul>
      <li><a href="admin_news.action?cmd=list&subjectId=-1&type=image" target="main" onclick='ac(this)'>图片新闻管理</a></li>
      <li><a href="admin_news.action?cmd=list&subjectId=-1" target="main" onclick='ac(this)'>教研动态管理</a></li>
      <li><a href="admin_placard.action?cmd=list&subjectId=-1&type=0" target="main" onclick='ac(this)'>公告管理</a></li>
    </ul>
   </div>
  </#if>
  <#if isSystemContentAdmin?? >
   <div id='Title_20121015' class="panelName_cur"><a href='javascript:void(0);' onclick='return toggle(20121015);'>评课管理</a></div>
    <div id='Menu_20121015' class="panelItems" style='display:'>
      <ul>
        <li><a href="evaluation/evaluation_plan.py" target="main" onclick='ac(this)'>评课活动</a></li>
        <li><a href="evaluation/evaluation_template.py" target="main" onclick='ac(this)'>评课模板</a></li>
        <#--
        <li><a href="evaluation/evaluation.action?cmd=list&listtype=plan" target="main" onclick='ac(this)'>评课活动</a></li>
        <li><a href="evaluation/evaluation.action?cmd=list&listtype=template" target="main" onclick='ac(this)'>评课模板</a></li>
        
        <li><a href="evaluation/evaluation_stats.py" target="main" onclick='ac(this)'>评课统计</a></li>
        -->
      </ul>
    </div>
    
    <#-- 系统管理员和系统用户管理员可以管理 协作组管理 -->
    <div id='Title_3' class="panelName_cur"><a href='javascript:void(0);' onclick='return toggle(3);'>协作组管理</a></div>
    <div id='Menu_3' class="panelItems" style='display:'>
      <ul>
        <li><a href="admin_group.py?cmd=list" target="main" onclick='ac(this)'>协作组管理</a></li>
        <li><a href="admin_group.py?cmd=list&amp;type=best" target="main" onclick='ac(this)'>优秀团队</a>
           | <a href="admin_group.py?cmd=list&amp;type=rcmd" target="main" onclick='ac(this)'>推荐</a>
          </li>
		    <li><a href="admin_group.py?cmd=list&amp;type=unaudit" target="main" onclick='ac(this)'>待审</a>
		       | <a href="admin_group.py?cmd=list&amp;type=deleted" target="main" onclick='ac(this)'>删除</a>
           | <a href="admin_group.py?cmd=list&amp;type=locked" target="main" onclick='ac(this)'>锁定</a>
           | <a href="admin_group.py?cmd=list&amp;type=hided" target="main" onclick='ac(this)'>隐藏</a>
		      </li>
      </ul>
    </div>
  <#-- 文章管理 -->
  <div id='Title_4' class="panelName_cur"><a href='javascript:void(0);' onclick='return toggle(4);'>文章管理</a></div>
  <div id='Menu_4' class="panelItems" style='display:'>
    <ul>
      <li><a href="admin_article.py?cmd=list" target="main" onclick='ac(this)'>文章管理</a></li>
      <li><a href="usercate_article.py" target="main" onclick='ac(this)'>首页自定义分类文章</a></li>
      <li><a href="admin_article.py?cmd=recycle_list" target="main" onclick='ac(this)'>文章回收站</a></li>
      <li><#-- <a href="admin_article.py?cmd=list&amp;type=best" target="main" onclick='ac(this)'>精华</a>
        | --> <a href="admin_article.py?cmd=list&amp;type=rcmd" target="main" onclick='ac(this)'>推荐</a>
        | <a href="admin_article.py?cmd=list&amp;type=unaudit" target="main" onclick='ac(this)'>待审核</a><#-- 
        | <a href="admin_article.py?cmd=list&amp;type=invalid" target="main" onclick='ac(this)'>非法</a>-->
        </li>
      <#if bklist??>
      <hr/>
      <#list bklist as byYear>
      <li><a href="admin_history_article.py?backYear=${byYear.backYear}" target="main" onclick='ac(this)'>管理${byYear.backYear}年历史文章</a></li>
      </#list>
      </#if>
    </ul>
  </div>
    
  <div id="Title_5" class="panelName_cur"><a href="javascript:void(0);" onclick="return toggle(5);">资源管理</a></div>
  <div id="Menu_5" class="panelItems" style="display:">
	<ul>
	  <li><a href="admin_resource.py?cmd=list" target="main" onclick="ac(this)">资源管理</a></li>
      <li><a href="admin_resource.py?cmd=recycle_list" target="main" onclick="ac(this)">资源回收站</a></li>
      <li><a href="admin_resource.py?cmd=list&amp;type=rcmd" target="main" onclick="ac(this)">推荐</a> | 
          <a href="admin_resource.py?cmd=list&amp;type=unaudit" target="main" onclick="ac(this)">待审核</a>
      </li>
	</ul>
  </div>
  
  <div id="Title_13" class="panelName_cur"><a href="javascript:void(0);" onclick="return toggle(13);">视频管理</a></div>
  <div id="Menu_13" class="panelItems" style="display:">
	<ul>
	  <li><a href="video.action?cmd=admin_list" target="main" onclick="ac(this)">视频管理</a></li>
      <li><a href="video.action?cmd=admin_list&amp;type=unaudit" target="main" onclick="ac(this)">待审核</a></li>
	</ul>        
  </div>

  <div id="Title_10" class="panelName_cur"><a href="javascript:void(0);" onclick="return toggle(10);">评论管理</a></div>
  <div id="Menu_10" class="panelItems" style="display:">
	<ul>
		<li><a href="admin_Comment.py?cmd=list1" target="main" onclick="ac(this)">文章评论管理</a></li>
		<li><a href="admin_Comment.py?cmd=list2" target="main" onclick="ac(this)">资源评论管理</a></li>
		<li><a href="video.action?cmd=comment_admin_list" target="main" onclick="ac(this)">视频评论管理</a></li>
  	</ul>
  </div>  
  
  <div id="Title_11" class="panelName_cur"><a href="javascript:void(0);" onclick="return toggle(11);">活动管理</a></div>
  <div id="Menu_11" class="panelItems" style="display:">
	<ul>
		<li><a href="action/admin_action_list.py" target="main" onclick="ac(this)">活动管理</a></li>
		<li><a href="action/admin_action_comment_list.py" target="main" onclick="ac(this)">讨论管理</a></li>
  	</ul>
  </div>

  <div id="Title_12" class="panelName_cur"><a href="javascript:void(0);" onclick="return toggle(12);">集体备课管理</a></div>
  <div id="Menu_12" class="panelItems" style="display:">
	<ul>
    	<li><a href="course/admin_course_list.py" target="main" onclick="ac(this)">集备管理</a></li>
  	</ul>
  </div>
  
  <div id="Title_1001" class="panelName_cur"><a href="javascript:void(0);" onclick="return toggle(1001);">专题管理</a></div>
  <div id="Menu_1001" class="panelItems" style="display:">
	<ul>
		<li><a href="specialsubject/admin_specialsubject_add.py" target="main" onclick="ac(this)">创建专题</a></li>
    	<li><a href="specialsubject/admin_specialsubject_list.py" target="main" onclick="ac(this)">专题管理</a></li>            
  	</ul>
  </div>

  <div id="Title_6" class="panelName_cur"><a href="javascript:void(0);" onclick="return toggle(6);">相册管理</a></div>
  <div id="Menu_6" class="panelItems" style="display:">
	<ul>
		<li><a href="admin_photo.py?cmd=list" target="main" onclick="ac(this)">相册管理</a></li>
	    <li><a href="admin_photo.py?cmd=recycle_list" target="main" onclick="ac(this)">相册回收站</a></li>
	</ul>
  </div>
</#if>
<#if isSystemAdmin?? >
    <div id="Title_7" class="panelName_cur"><a href="javascript:void(0);" onclick="return toggle(7);">其它</a></div>
    <div id="Menu_7" class="panelItems" style="display:">
      <ul>
        <li><a href="admin_subject.action" target="main" onclick="ac(this)">学科</a> | 
          <a href="admin_msubject.action" target="main" onclick="ac(this)">元学科</a> | 
          <a href="admin_grade.action" target="main" onclick="ac(this)">元学段</a>
        </li>
        <li><a href="admin_resType.action" target="main" onclick="ac(this)">元数据类型管理</a></li>
		<li><a href="admin_usertype_manage.py" target="main" onclick="ac(this)">用户头衔名称维护</a></li>
		<li><a href="admin_unit_manage.py" target="main" onclick="ac(this)">组织机构管理</a></li>
		<li><a href="punish.py?cmd=list" target="main" onclick="ac(this)">加、罚分管理</a></li>
		<li><a href="usergroup.py?cmd=list" target="main" onclick="ac(this)">角色组管理</a></li>
		<li><a href="adminReport.action?cmd=list" target="main" onclick="ac(this)">举报内容管理</a></li>
	  </ul>
    </div>

    <#if plugin_list?? >
    <div id="Title_1002" class="panelName_cur"><a href="javascript:void(0);" onclick="return toggle(1002);">插件对象管理</a></div>
    <div id="Menu_1002" class="panelItems" style="display:">
	  <ul>
        <#list plugin_list as p >
        <li><a target='main' href='${SiteUrl}mod/${p.pluginName}/admin_list.py'>${p.pluginTitle}</a></li>
        </#list> 
      </ul>
    </div>
    </#if>
    <div id="Title_9" class="panelName_cur"><a href="javascript:void(0);" onclick="return toggle(9);">站点统计</a></div>
    <div id="Menu_9" class="panelItems" style="display:">
	  <ul>
		<li><a href="admin_stat.action?cmd=init&statGuid=${Util.uuid()}" target="main" onclick="ac(this)">个人统计</a></li>
        <li><a href="admin_stat_group.action?cmd=init" target="main" onclick="ac(this)">协作组统计</a></li>
        <li><a href="admin_stat_unit.action?cmd=init" target="main" onclick="ac(this)">机构统计</a></li>
        <li><a href="admin_stat_subject.action?cmd=init" target="main" onclick="ac(this)">学科统计</a></li>
      </ul>
    </div>
  <div id="Title_1003" class="panelName_cur"><a href="javascript:void(0);" onclick="return toggle(1003);">页面静态化</a></div>
  <div id="Menu_1003" class="panelItems" style="display:">
	<ul><#--<li><a href="admin_user_html.py" target="main" onclick='ac(this)'>生成全部用户静态首页</a></li>-->
	    <#if autoHtml>
    	<#--<li><a href="admin_site_html.py" target="main" onclick='ac(this)'>生成网站静态文件</a></li> 	-->
    	<li><a href="admin_unit_html.py" target="main" onclick='ac(this)'>生成全部机构静态首页</a></li>
    	</#if>
        <li><a href="clearall_cache.py?cachetype=index" target="main" onclick='ac(this)'>清空首页缓存</a></li>
        <li><a href="clearall_cache.py?cachetype=user" target="main" onclick='ac(this)'>清空所有用户页面缓存</a></li>
        <li><a href="clearall_cache.py?cachetype=unit" target="main" onclick='ac(this)'>清空所有机构页面缓存</a></li>
  	</ul>
  </div>
</#if>
</div>
<script>
function toggle(num) {
  var elem = document.getElementById('Menu_' + num);
  if (elem != null)
    elem.style.display = (elem.style.display == 'none') ? '' : 'none';
    
  elem = document.getElementById('Title_' + num);
  if (elem != null)
    elem.className = (elem.className == 'panelName') ? 'panelName_cur' : 'panelName';
  return false;
}
function ac(a) {
  var lis = document.getElementsByTagName('li');
  for (var i = 0; i < lis.length; ++i) {
    if (lis[i].className != '') {
      lis[i].className = '';
      var children = lis[i].children;
      for (var j = 0; j < children.length; ++j)
        if (children[j].tagName.toUpperCase() == 'A')
          children[j].className = '';
    }
  }
  var li = a.parentElement;
  if (li)
    li.className = 'active';
  a.className = 'abold';
}
</script>

</body>
</html>