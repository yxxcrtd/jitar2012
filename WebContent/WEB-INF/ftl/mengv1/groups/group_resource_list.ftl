<div class='b1'>
  <div class='b1_head2'>
    <div class='b1_head_right'><a href='ktgroups.action?act=all&type=resource'>更多…</a></div>
    <div class='b1_head_left'>课题成果-资源</div>
  </div>
  <div class='b1_content'>
      <div class='tc1'>
      <#if resource_list?? >
      <table border='0' cellpadding='1' cellpadding='1' width='246'>
          <#list resource_list as resource>
            <tr>
              <td>
                <a href='../showResource.action?resourceId=${resource.resourceId}' target='_blank'><img src='${Util.iconImage(resource.href!)}' border='0' align='absmiddle' hspace='3' />${resource.title!?html}</a>
              </td>
            </tr>
          </#list>
       </table>
      <#else>
       暂无资源
      </#if>
    </div>
  </div>
</div>

