<html>
<head>
<title>选择组员</title>
<script type='text/javascript'>
function getUser()
{
  var hasChecked = false
  r = document.getElementsByName('mem')
  for(i = 0;i<r.length;i++)
  {
   if(r[i].checked)
   {
    hasChecked = true;
    <#if inputUser_Id??>
    window.opener.document.getElementById("${inputUser_Id}").value = r[i].value
    <#else>
    window.opener.document.getElementById("pc_id").value = r[i].value
    </#if>
    <#if inputUserName_Id??>
    window.opener.document.getElementById("${inputUserName_Id}").value = document.getElementById("u_"+r[i].value).innerHTML
    <#else>
    window.opener.document.getElementById("pc_name").value = document.getElementById("u_"+r[i].value).innerHTML
    </#if>    
    window.close();
   }
  }
  if(!hasChecked)
  {
   alert('请选择一个小组成员。')
  }
}
</script>
</head>
<form>
<#if group_member??>
<#list group_member as m>
<div>
<label><input type='radio' name='mem' value='${m.userId}' /> <span id='u_${m.userId}'>${m.trueName?html}</span></label>
</div>
</#list>
<input type='button' value=' 确  定 ' onclick='getUser()' />
<input type='button' value='关闭窗口' onclick='window.close()' />
<#else>
<div>
没有可以添加的用户。<br/><br/>
<input type='button' value='关闭窗口' onclick='window.close()' />
</div>
</#if>

</form>
</body>
</html>
