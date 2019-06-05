<div>
  <div><b>${group.groupTitle!}</b></div>
  <div>
    <a href='${SiteUrl}g/${group.groupName}'><img class='group_icon' src='${Util.url(group.groupIcon!"images/group_default.gif")}' 
      onerror="this.onerror=null;this.src='${SiteUrl}images/group_default.gif';" border='0' width='128' /></a>
  </div>
  <div>成员:${group.userCount!}人</div>
  <div>${group.groupIntroduce!}</div>
  <div>
    标签: <#list Util.tagToList(group.groupTags!) as tag>
      <a href='${SiteUrl}showTag.action?tagName=${tag?url("UTF-8")}'>${tag!?html}</a>
    </#list>
  </div>
  <div>学段学科: ${group.GetXKXDNameEx()!?html}</div>
  <div>
    加入: <#if group.joinLimit == 0>任意加入<#elseif group.joinLimit == 1>申请加入<#elseif group.joinLimit == 2>仅可邀请<#else>未知${group.joinLimit}</#if>
  </div>
  <div>
    <span><a href='${SiteUrl}manage/groupMember.action?cmd=join&amp;groupId=${group.groupId}'>加入协作组</a></span>
    <span><a href='${SiteUrl}manage/group.py?cmd=manage&amp;groupId=${group.groupId}'>进入协作组</a></span>
    <span><a href='${SiteUrl}chat/chat.py?groupId=${group.groupId}'>进入组聊天室</a></span>
    <br/>
    <span><a href='${SiteUrl}manage/groupMember.action?cmd=invite&amp;groupId=${group.groupId}'>邀请成员</a></span>
    <span style='display:none'><a href='${SiteUrl}g/${group.groupName}/py/group_addaction.py'>发起活动</a></span>
    <span><a href='${SiteUrl}g/${group.groupName!}/rss/article.xml'>RSS</a></span>
    <br />
    <#if ("0" != Util.isOpenMeetings())>
		<#if (1 == Util.IsVideoGroup(group.groupId))>
			<a class="linkButton icoVideo" href="${Util.isOpenMeetings()}&amp;groupid=${group.groupId!}" target="_blank">进入协作组会议室</a>
		</#if>
	</#if>
  </div>
</div>
