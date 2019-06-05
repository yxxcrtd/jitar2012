<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
</head>
<body>
<span style='float:right'>
<input type='button' value='选择并返回' onclick='setUnit()' />
<input type='button' value='关闭窗口' onclick='window.parent.close();' />
</span>
<span style='float:left'>
您选择的机构是：<span style='font-weight:bold;'>${unitTitle!}</span>
</span>
<script type='text/javascript'>
var uname = "${unitTitle!?js_string}";
var uid = "${unitId!?js_string}";
window.parent.focus();
function setUnit()
{
 window.parent.opener.document.getElementById("unitName").innerHTML = uname;
 window.parent.opener.document.getElementById("unit_id").value = uid;
 window.parent.close();
}
</script>
</body>
</html>