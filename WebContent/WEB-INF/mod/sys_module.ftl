<div style='width:420px;'>
<div style="padding: 6px; background: #DEE">
 <span style="cursor: pointer; float: right" onclick="document.getElementById('subMenuDiv').style.display='none';">X</span>
  添加模块
</div>
<div style='margin: 4px 0 4px 0; border-top: 2px solid green'></div>
<div id='_moduleList'>
<#if module_type?? >
  <#if module_type == 'g'>
  <div ref='group_info'>协作组信息</div>
  <div ref='group_article'>组内文章</div>
  <div ref='group_resource'>组内资源</div>
  <div ref='group_activist'>小组活跃成员</div>
  <div ref='group_cate'>资源分类</div>
  <div ref='group_cate_article'>文章分类</div>
  <div ref='group_manager'>协作组组长</div>
  <div ref='group_newbie'>小组最新成员</div>
  <div ref='group_placard'>组内公告</div>
  <div ref='group_leaveword'>组内留言</div>
  <div ref='recent_topiclist'>组内论坛</div>
  <div ref='group_action'>组内活动</div>
  <div ref='group_stat'>统计信息</div>
  <div ref='group_link'>友情链接</div>
  <div ref='group_mutilcates'>特定分类综合模块</div>
  <div ref='simple_text'>自写内容模块</div>
  <#elseif module_type == 'b'>
  <div ref='group_info'>备课组信息</div>
  <div ref='group_article'>组内文章</div>
  <div ref='group_resource'>组内资源</div>
  <div ref='group_preparecourse_plan'>备课计划</div>
  <div ref='group_activist'>小组活跃成员</div>
  <div ref='group_cate'>资源分类</div>
  <div ref='group_cate_article'>文章分类</div>
  <div ref='group_manager'>协作组组长</div>
  <div ref='group_newbie'>小组最新成员</div>
  <div ref='group_placard'>组内公告</div>
  <div ref='group_leaveword'>组内留言</div>
  <div ref='recent_topiclist'>组内论坛</div>
  <div ref='group_action'>组内活动</div>
  <div ref='group_stat'>统计信息</div>
  <div ref='group_link'>友情链接</div>
  <div ref='group_mutilcates'>特定分类综合模块</div>
  <div ref='simple_text'>自写内容模块</div>
  <#elseif module_type == 'k'>
  <div ref='group_info'>课题介绍</div>
  <div ref='group_article'>课题文章</div>
  <div ref='group_resource'>课题资源</div>
  <div ref='group_activist'>课题活跃成员</div>
  <div ref='group_cate'>资源分类</div>
  <div ref='group_cate_article'>文章分类</div>
  <div ref='group_manager'>负责人信息</div>
  <div ref='group_newbie'>参加者信息</div>
  <div ref='group_placard'>课题公告</div>
  <div ref='group_leaveword'>课题留言</div>
  <div ref='recent_topiclist'>课题研讨</div>
  <div ref='group_action'>课题活动</div>
  <div ref='group_stat'>统计信息</div>
  <div ref='group_link'>友情链接</div>
  <div ref='group_children'>子课题</div>
  <div ref='group_mutilcates'>特定分类综合模块</div>
  <div ref='simple_text'>自写内容模块</div>
  <#elseif module_type == 'p'>
  <div ref='show_preparecourse_info'>备课基本信息</div>
  <div ref='show_preparecourse_stage'>备课流程</div>
  <div ref='show_preparecourse_stage_article'>当前流程文章</div>
  <div ref='show_preparecourse_stage_resource'>当前流程资源</div>
  <div ref='show_preparecourse_stage_topic'>当前流程讨论</div>
  <div ref='show_preparecourse_action'>教研活动</div>
  <div ref='show_preparecourse_action'>备课成员</div>
  <div ref='show_preparecourse_statis'>统计信息</div>
  <div ref='show_preparecourse_private_content'>个案列表</div>
  <div ref='show_preparecourse_common_abstract'>共案摘要</div>
  <div ref='show_preparecourse_related'>相关集备</div>
  <div ref='show_preparecourse_video'>视频</div>
  <div ref='simple_text'>自写内容</div>
  <#else>
  <div ref='profile'>个人档案</div>
  <div ref='entries'>我的文章</div>
  <div ref='lastest_comments'>最新评论</div>
  <div ref='user_stats'>统计信息</div>
  <div ref='joined_groups'>我的协作组</div>
  <div ref='user_photo'>我的图片</div>
  <div ref='user_video'>我的视频</div>
  <!--<div ref='flash_photo'>最新照片</div>-->
  <div ref='user_placard'>我的公告</div>
  <div ref='user_cate'>文章分类</div>
  <div ref='user_rcate'>资源分类</div>
  <div ref='photo_cate'>相册分类</div>
  <div ref='friendlinks'>我的好友</div>
  <div ref='blog_search'>文章搜索</div>
  <div ref='user_leaveword'>我的留言</div>
  <div ref='simple_text'>自写内容模块</div>
  <div ref='rss'>RSS（聚合）</div>
  <div ref='user_resources'>我的资源</div>
  <div ref='user_calendar'>生活日历</div>
  <div ref='user_createdaction'>我发起的活动</div>
  <div ref='user_joinedaction'>我参与的活动</div>
  <div ref='user_preparecourse'>我发起的备课</div>
  <div ref='user_joinedpreparecourse'>我参与的备课</div>
  <div ref='category_article'>特定分类文章</div>
  <div ref='category_photo'>特定分类图片</div>
  <div ref='category_video'>特定分类视频</div>
  </#if>
</#if>
  <#if plugin_list?? >
	<#list plugin_list as p>
	  <div ref='${p.pluginName}'>${p.pluginTitle}</div>
	</#list>
	</#if>
</div>
</div>