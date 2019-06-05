<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>显示信息</title>
<script>
var request = null;
var lastDate="";
var _tmp = 0;
//var __TimerHanlder = window.setInterval("GetChatmsg()",5000);

function GetChatmsg() {
	request = getXMLHttpRequest();
	var isPrivateShow = 0;	//self.parent.isPrivateShow;  //0:private , 1: public //&private="+ isPrivateShow +"
	var url = "groupTalkRoom.action?cmd=publicinfo&roomId=${chatroom.roomId}&from=0&groupId=${groupId}&lastDate="+lastDate+ "&d=" +(Date.parse(new Date()));
	//window.open(url,"_blank");
	request.open("GET", url, true);
	request.send();
	request.onreadystatechange=process;
}
function getXMLHttpRequest() {
	var request = null;
	if(window.XMLHttpRequest) { 
		request = new XMLHttpRequest();
	} else if(typeof ActiveXObject!= "undefined") {
		request = new ActiveXObject("Microsoft.XMLHTTP");
	}
	return request; 
}
function process() {
	if(request.readyState==4) {
		if(request.responseText) {
			var sHtml = request.responseText;
			var sTemp = sHtml;
			sTemp = sTemp.replace(" ","");
			sTemp = sTemp.replace("\r","");
			sTemp = sTemp.replace("\n","");
			sTemp = sTemp.replace("\t","");
			sTemp = sTemp.replace("\s","");
			if(sTemp=="") return;
			var lpos = sTemp.indexOf("|");
			if(lpos) {
				var lid = sHtml.substring(0,lpos);
				sHtml = sHtml.substring(lpos+1);
				if(lid != "") lastDate = lid;
			}
			document.getElementById("newmsg").outerHTML = "<br/>" + sHtml + "<div id=\"newmsg\"></div>";
			scrollToMsg();
		}
	}
}
function scrollToMsg() {
	window.scrollTo(0,document.body.scrollHeight);
}

function clearWin(){
	showchatInfo.innerHTML="<div id=\"newmsg\"></div>";
}
function selectuser(userTD){

	var uid=userTD.userid;
	var uName=userTD.username;
	AddSelectChatUser(uName,uid);
}
function AddSelectChatUser(uName,uid){

	self.parent.parent.chatsend.AddUser(uid,uName);
}
</script>
</head>
<body onload="GetChatmsg()" bgcolor="#ffffff" leftmargin="0" topmargin="0"  marginheight="0" marginwidth="0">
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="30%" style="table-layout:fixed">
	<tr>
		<td valign="top">
			<div id="showchatInfo" style="border:0px" class="divstyle">
				<div id="newmsg" style="border:0px" class="divstyle"></div>
			</div>
		</td>
	</tr>
</table>
</body> 
</html>   
