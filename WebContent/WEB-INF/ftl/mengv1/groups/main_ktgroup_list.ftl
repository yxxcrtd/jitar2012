<#if group_list?? >
<div class='q_r'>
  <div class='q_r_head'>
    <table border='0' style='width:630px;' align='right'>
      <tr>
        <td class='kr1 bold'>课题名称</td>
        <td class='kr2 bold'>课题负责人</td>
        <td class='r9 bold'>成员数</td>
      </tr>
    </table>
  </div>
  <#list group_list as group>
    <div style='padding:4px;'>
      <table style='width:100%' border='0'>
      <tr valign='top'>
        <td>
        <span class='border_img'>
        <a href='${SiteUrl}go.action?groupId=${group.groupId}'><img src='${Util.url(group.groupIcon!'images/group_default.gif')}' width='64' height='64' border='0' /></a>
        </span>
        </td>
        <td>
          <table border='0' style='width:630px;' align='right'>
            <tr>
              <td class='kr1 bold'><a href='${SiteUrl}go.action?groupId=${group.groupId}' style='font-size:14px'>${group.groupTitle!?html}</a></td>
              <td class='kr2'>
              <#assign userlist = Util.getKtUserById(group.groupId)>
              <#if userlist??>
                <#list userlist as ktuser>
                    ${ktuser.teacherName}[${ktuser.teacherUnit}]<br/>
                </#list>
              </#if>              
              </td>
              <td class='r9'>${group.userCount}</td>
            </tr>
            <!--
            <tr>
                <td colspan="3" style="height:1px;">
                    <hr style="width:98%;height:1px"/>
                </td>
            </tr>
            -->   
            <#if group.groupIntroduce.length()&gt;0> 
            <tr>
                <td colspan="3" style="font-size:11px">
                 课题介绍：   ${group.groupIntroduce!}
                </td>
            </tr>
            </#if>
          </table>
        </td>
      </tr>
      </table>
      <#if subgroups??>
        <#assign subgroup_list = subgroups[group_index]>
        <#if subgroup_list??>
        <#if subgroup_list?size != 0>
         <table border='0' style='width:100%;' cellSpacing="0">
         <tr>
         <td style="width:0px">
         </td>
         <td> 
         <table style='width:100%;' border='0' align="center" class='q_r_head' style="height:22px"> 
              <tr>
                <td class='bold' style="width:150px">子课题名称</td>
                <td class='bold' style="width:170px">负责人</td>
                <td class='bold'>介绍</td>
                <td class='bold' style="width:70px">成员数</td>
              </tr>
          </table>
          <table style='width:100%;background-color:#eeeeee' border='0' cellSpacing="1" align="center" >    
            <#list subgroup_list as subgroup>
            <tr>
              <td style="background-color:#ffffff;width:150px"><a href='${SiteUrl}go.action?groupId=${subgroup.groupId}' style='font-size:12px'>${subgroup.groupTitle!?html}</a></td>
              <td style="background-color:#ffffff;width:170px">
              <#assign subuserlist = Util.getKtUserById(subgroup.groupId)>
              <#if subuserlist??>
                <#list subuserlist as ktsubuser>
                    ${ktsubuser.teacherName}[${ktsubuser.teacherUnit}]<br/>
                </#list>
              </#if>              
              </td>
              <td style="background-color:#ffffff">${subgroup.groupIntroduce!}</td>
              <td style="background-color:#ffffff;width:70px">${subgroup.userCount}</td>
            </tr>            
            </#list>
        </table>
        </td>
        </tr>
        </table>                   
        </#if>
        </#if>
      </#if>
    </div>
    <#if group_has_next><div class='spr'></div></#if>
  </#list>
</div>

<div class='pgr'><#include '../../inc/pager.ftl'></div>
</#if>