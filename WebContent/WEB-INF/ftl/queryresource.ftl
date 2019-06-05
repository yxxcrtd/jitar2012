  <#if resource_list??>
      <#list resource_list as r>
        <table border="0" cellspacing="1" cellpadding="1" style="width:100%;" id="tr${r.resourceId}">
        <tr>
            <td>
            <a href='showResource.action?resourceId=${r.resourceId}' target='_blank'><img src='${Util.iconImage(r.href!)}' border='0' align='absmiddle' /> ${Util.getCountedWords(r.title!?html,16)}</a>
            <input type="hidden" name="resId" value="${r.resourceId}">
            </td>
            <td style="width:100px;text-align:center">
            <#if r.auditState!=0>
                                待审核
            </#if>
            </td>
            <td style="width:60px;text-align:center"><a href="#" onclick="removeResource(${r.resourceId});">删除</a></td>
        </tr>   
        </table> 
      </#list>
  </#if>     
     