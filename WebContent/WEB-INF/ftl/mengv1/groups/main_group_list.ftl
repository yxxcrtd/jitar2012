<#if group_list?? >
<div class='q_r'>
  <div class='q_r_head'>
    <table border='0' style='width:630px;' align='right'>
      <tr>
        <td class='r1 bold'>协作组名称</td>
        <td class='r2 bold'>创建时间</td>
        <td class='r3 bold'>成员数</td>
        <td class='r4 bold'>访问数</td>
        <td class='r5 bold'>文章数</td>
        <td class='r6 bold'>主题数</td>
        <td class='r7 bold'>活动数</td>
        <td class='r8 bold'>资源数</td>
      </tr>
    </table>
  </div>
  <#list group_list as group>
    <div style='padding:4px;'>
      <table style='width:100%' border='0'>
      <tr valign='top'>
        <td>
        <span class='border_img'><a href='${SiteUrl}go.action?groupId=${group.groupId}'><img src='${Util.url(group.groupIcon!SiteUrl + "images/group_default.gif")}' width='64' height='64' border='0' /></a></span>
        </td>
        <td>
          <table border='0' style='width:630px;' align='right'>
            <tr>
              <td class='r1 bold'><a href='${SiteUrl}go.action?groupId=${group.groupId}' style='font-size:14px'>${group.groupTitle!?html}</a></td>
              <td class='r2'>${group.createDate?string('yyyy-MM-dd')}</td>
              <td class='r3'>${group.userCount}</td>
              <td class='r4'>${group.visitCount}</td>
              <td class='r5'>${group.articleCount}</td>
              <td class='r6'>${group.topicCount}</td>
              <td class='r7'>0</td>
              <td class='r8'>${group.resourceCount}</td>
            </tr>
          </table>
          <div style='clear:both;padding:6px;padding-left:16px;'>
            <div style='margin-bottom:2px;'>
                学段学科: ${group.XKXDName!?html}<br/>
                标签: 
              <#list Util.tagToList(group.groupTags!) as tag>
			      <a href='${SiteUrl}showTag.action?tagName=${tag?url("UTF-8")}'>${tag!?html}</a>
			    </#list>
            </div>
            <div>${group.groupIntroduce!}</div>
          </div>
        </td>
      </tr>
      </table>
    </div>
    <#if group_has_next><div class='spr'></div></#if>
  </#list>
</div>

<div class='pgr'><#include '../../inc/pager.ftl'></div>
</#if>