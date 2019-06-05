<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title></title>
  <link rel="stylesheet" href="../css/manage.css" type="text/css" />
  <SCRIPT src="../js/buttons.js" type=text/javascript></SCRIPT>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
  <script>  
  	function SaveMsg()
  	{
  		if(document.form1.txtword.value=="" && document.getElementById("face").value=="")
  		{
  			alert("请输入内容");
  			return;
  		}
  		document.form1.submit();
  	}
	function selectChatUser(userId,userName)
	{
		var user_sel = document.forms.form1.seluser;
		for (var i = 0; i < user_sel.options.length; ++i)
		{
			if(user_sel.options[i].value==userId)
			{
				user_sel.options[i].selected = true;
				return;
			}
		}
		add_option(user_sel,userId,userName);
	}
	
	function add_option(sel, val, text) {
	  opt = document.createElement("OPTION");
	  opt.value = val;
	  opt.text = text;
      opt.selected = true;
	  sel.options.add(opt);
	}
  	function refreshMsgList()
  	{
  		window.open("mychat.py?roomId=${roomId}&ttt="+Math.random(),"mychat");
  	}
  	function refreshMsgList2()
  	{
  		self.parent.mychat.__GetUserMsg();
  	}
  	function SelectFace()
  	{
  		var a=window.showModalDialog("showFaces.py",window,"dialogWidth=400px;dialogHeight=400px");
  		if(a)
  		{
  			document.getElementById("faceid").innerHTML+="<img border='0' src='"+a+"'>";
  			//document.getElementById("face").value+=a+"|";
  			var fs = a.substr(a.lastIndexOf("/")+1);
  			fs = fs.substr(0,fs.indexOf("."));
  			insertTextAtCursor(document.getElementById("txtword"),"[face_" + fs + "]");
  		}
  		//else
  		//{
  		//	document.getElementById("faceid").innerHTML="";
  		//	document.getElementById("face").value="";
  		//}
  	}
  	function SelectFontColor()
  	{
	    var url = "${SiteUrl}manage/colorpicker/colorpicker.py?color=${mycolor!}&tmp=" + Date.parse(new Date());    
	    var a = window.showModalDialog(url,window,"dialogWidth=500px;dialogHeight=340px");
  		//var a=window.showModalDialog("setColor.htm",window,"dialogWidth=500px;dialogHeight=340px");
  		if(a)
  		{
  			document.form1.selcolor.value=a;
  			document.getElementById("ExamleFontColor").innerHTML="<font color='"+a+"'>颜色</font>";
  		}
  	}
  	function ClearFace()
  	{
  			document.getElementById("faceid").innerHTML="";
  			document.getElementById("face").value="";
  	}
  	
  	function insertTextAtCursor(el, text) {
    var val = el.value, endIndex, range;
    if (typeof el.selectionStart != "undefined" && typeof el.selectionEnd != "undefined") {
        endIndex = el.selectionEnd;
        el.value = val.slice(0, endIndex) + text + val.slice(endIndex);
        el.selectionStart = el.selectionEnd = endIndex + text.length;
    } else if (typeof document.selection != "undefined" && typeof document.selection.createRange != "undefined") {
        el.focus();
        range = document.selection.createRange();
        range.collapse(false);
        range.text = text;
        range.select();
    }
}

  </script>

</head>
<body>
<form name="form1" method="post" action="saveMessage.py" target="hiddenframe">	
<input type="hidden" name="roomId" value="${roomId}"/>
<input type="hidden" name="selcolor" value="${mycolor}"/>
<input name="bgcolor" type="hidden" value='${mycolor!}' />
<input type="hidden" name="face" id="face" value="">
<div id='toolbar'>
	<div style='float:left;'>
	<div style="padding:2px;text-align:left;">
	&nbsp;对&nbsp;<select name="seluser" style="width:100px">
	     <option value=0 selected>大家</option>
	     </select>
    <img border="0" onclick="SelectFontColor();" title="颜色" src="${SiteUrl}images/font.gif" width="20" height="20" style="cursor:hand;position:relative;top:2px">
    <img border="0" onclick="SelectFace();" title="表情" src="${SiteUrl}images/face/000.gif" width="20" height="20" style="cursor:hand;position:relative;top:2px">
    </div>
    </div>
</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0" valign="top" style="height:60px;table-layout:fixed;">
   <tr>
      <td width="80" align="right">聊天内容：
	      <br/><br/>
	      <a href="chatMsgList.py?roomId=${roomId}" target="_blank">聊天记录</a>&nbsp;&nbsp;
      </td>
      <td width="500">
      	<textarea name="txtword" id="txtword" style="width:100%;height:100%"></textarea>      	
      </td>
      <td width="20">&nbsp;</td>
      <td width="100" align="left">
        <input type="button" name="cmdok" value="&nbsp;发&nbsp;送&nbsp;" onclick="SaveMsg();"><br/><br/>
        <a href="#" onclick="refreshMsgList();return false;">刷新</a>
      </td>
      <td>
      	<table width="100%" border="0" cellspacing="0" cellpadding="0" valign="top">
      		<tr>
      			<td width="80" align="right">字体颜色:</td>
      			<td>&nbsp;<span id="ExamleFontColor"><font color="${mycolor}">颜色</font></span></td>
      		</tr>
      		<tr>	
      			<td align="right">表情:</td>
      			<td>&nbsp;<span id="faceid"></span></td>
      		</tr>
      	</table>
      </td>
      </tr>
   </table>
   </form>   
   <iframe name="hiddenframe" style="display:none"></iframe>
   
</body>
</html>   	