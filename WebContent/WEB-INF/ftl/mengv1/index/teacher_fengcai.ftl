<div class='r1' style='height:376px;overflow:hidden'>
<div class='r1_head'>
<div class='r1_head_right'><a href='blogList.action?type=6'>更多…</a></div>
<div class='r1_head_left'> ${Util.typeIdToName(6)}</div>
  </div>
  <div style='padding:6px 0'>
  <#if (teacher_show??) >
  <table border='0' align='center' cellspacing='2' cellpadding='0' width='100%'>
    <#assign rowCount = 4 >
    <#assign columnCount = 2 >
    <#list 0..rowCount-1 as row>
    <tr valign='top' align='center'>
        <#list 0..columnCount-1 as cell>
        <td width='50%'>
        <#if teacher_show[(row ) * columnCount + cell]?? >
        <#assign u = teacher_show[(row) * columnCount + cell]>
          <div>
	      <table border='0' cellpadding='0' cellspacing='0' class='border_img_a'>
	      <tr><td><a href='go.action?loginName=${u.loginName}'><img src='${u.userIcon!}' width='56' height='56' border='0' onerror="this.src='${SiteUrl}images/default.gif';" /></a></td></tr>
	      </table>
	      </div>
	      <div style='text-align:center;padding-bottom:6px;'><a href='go.action?loginName=${u.loginName}'>${u.trueName!?html}</a></div>
        <#else>
        &nbsp;
        </#if>
        </td>
        </#list>
    </tr>
    </#list>
  </table>
 <#else>
  没有数据
  </#if>
  </div>        
</div>
