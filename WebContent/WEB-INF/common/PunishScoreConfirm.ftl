<html>
  <head>
    <title>确认罚分</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}index.css" />
    <style>
  html,body {width:100%;text-align:left;color:#000;}
  </style>
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
<script language="javascript">
	function checkdata()
	{
  		var s=document.getElementById("score").value
  		if(s=="")
  		{
  			s="0"
  		}
  		v=parseInt(s);
  		
	  if(isNaN(v))
	  {
	   alert("请输入正确的罚分");
	   return;
	  }
  		if (v<0)
  		{
  			alert("请输入正确的罚分");
  			return;
  		}
  		var sReason=document.getElementById("reason").value
  		window.returnValue=s+"|"+sReason;
  		window.close();
	}

	function windowclose()
	{
		window.close();
	}
</script>   
  </head>
  <body marginleft=0 margintop=0>
  	<table border="0" cellspacing='0' width="100%">
  		<tr height="30">
  			<td width="60" align="right" style="color:red"><b>罚分分值:</b></td>
  			<td><input type="text" name="score" id="score" size=5 value="${score!0}"></td>
  		</tr>
  		<tr>
  			<td align="right" style="color:red"><b>原因:</b></td>
  			<td>
  			<textarea name="reason" id="reason" style="width:100%;height:40px"></textarea>
  			</td>
  		</tr>
  		<tr height="30">
  			<td colspan='2' align="center">
  				<button onclick="checkdata();">确定</button>&nbsp;&nbsp;
  				<button onclick="windowclose();">取消</button>
  			</td>
  		</tr>
  	</table>
  </body>
</html>
