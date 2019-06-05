<div style="height:10px;"></div>
<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
<tr style="vertical-align:top;">
<td class="right" style="padding-left:0">
<div class="rightcontainer" style="padding-left:0">
<#if group_list??>
<table border="0" cellspacing='1' class="datalist bordertable">
<tr>
  <th width='30%'>协作组名称</th>
  <th width='20%'>创建人<br/>(创建时间)</th>
  <th width='12%'>学科<br/>(学段)</th>
  <th width='8%'>成员数<br/>访问量</th>   
</tr>
<#list group_list as group>
<tr>
    <td style="border:0">
        <table width="100%">
            <tr>
                <td width="64" style="border:0">
                    <img src='${Util.url(group.groupIcon!SiteUrl + "images/group_default.gif")}' width='48' height='48' border='0' />
                </td>
                <td align='left' valign='top' style="border:0">
                    <a href='${SiteUrl!}g/${group.groupName}' target='_blank' title='${group.groupIntroduce!?html}'>${group.groupTitle!}</a>
                      <#if group.isBestGroup><img src='${SiteUrl}manage/images/ico_you.gif' align='absmiddle' border='0' hspace='2' 
                        title='优秀团队' /></#if><#if group.isRecommend><img src='${SiteUrl}manage/images/ico_rcmd.gif' align='absmiddle' border='0' title='推荐协作组' /></#if>
                      <br/>英文名: ${group.groupName!}
                      <br/>标签: [ 
                        <#list Util.tagToList(group.groupTags!'') as t>
                          <a href='${SiteUrl}showTag.action?tagName=${t?url("UTF-8")}' target='_blank'>${t!?html}</a><#if t_has_next>,</#if>
                        </#list> ]
                </td>
            </tr>
        </table>
     </td>
     <td style="border:0">
        <a href='${SiteUrl}go.action?loginName=${group.user.loginName}' target='_blank'>${group.user.trueName}</a>
        <br/><nobr>${group.createDate?string('yyyy-MM-dd HH:mm:ss')}</nobr>
     </td>
     <td style="text-align: center;border:0">
        <#if group.subjectId??>
            ${Util.subjectById(group.subjectId).msubjName!?html}
        </#if>
        <br />
        <#if group.gradeId??>
            (${Util.gradeById(group.gradeId).gradeName!?html})
        </#if>     
     </td>
     <td style="text-align: center;border:0">
      ${group.userCount!}
      /
      ${group.visitCount!}     
     </td>
    </tr>
</#list>
</table>
    
<#if pager??>
<div class='pager'><#include '/WEB-INF/ftl/inc/pager.ftl' ></div>
</#if>
</#if>
</div>
</td>
</tr>
</table>