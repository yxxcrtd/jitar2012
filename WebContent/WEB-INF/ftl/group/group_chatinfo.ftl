<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>聊天信息</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
<style>
	.frameTop{
		border-color:#ffffff; 
		border-bottom-style: solid; 
		border-bottom-color:#ffffff;
		border-bottom-width:0pt;	
		border-left-style:solid; 
		border-left-color:#ffffff;
		border-left-width:0pt;	
		border-top-style:solid; 
		border-top-color:#ffffff;
		border-top-width:0pt;		
		border-right-style:solid; 
		border-right-color:#ffffff;
		border-right-width:0pt;	
		}
	.frameBottom{
		border-color:#ffffff; 
		border-bottom-style: solid; 
		border-bottom-color:#ffffff;
		border-bottom-width:0pt;	
		border-left-style:solid; 
		border-left-color:#ffffff;
		border-left-width:0pt;	
		border-top-style:solid; 
		border-top-color:#AD3A3B;
		border-top-width:1pt;		
		border-right-style:solid; 
		border-right-color:#ffffff;
		border-right-width:0pt;	
		}
</style>		  
  
<script type="text/javascript">
	var isPrivateShow=0;
	var lastID="";
function AddSelectChatUser(uName,uid){
	self.parent.chatsend.AddUser(uid,uName);
}
function slitWin(){
	showFrame.rows="*,200";
//	showinfo1.style.display="";
	isPrivateShow=1;
}
function meargeWin(){
	showFrame.rows="*,0";
//	showinfo1.style.display="none";
	isPrivateShow=0;
}
function clearWin(){
	showinfo.clearWin();
	showinfo1.clearWin();
}
function GetChatmsg() {
	//alert(11111111111);
	//showpubinfo.GetChatmsg();
	//alert(2222222222222);
	showprivateinfo.GetChatmsg();
}
</script>

</head>
<frameset rows="100,*">
	<frame  frameborder="1"  id="showpubinfo"  name="showpubinfo" frameheight="50%"
		src="groupTalkRoom.action?cmd=showpubinfo&amp;roomId=${chatroom.roomId}&amp;groupId=${groupId}"
		scrolling="auto"/>
	
	<frame frameborder="1"   id="showprivateinfo" name="showprivateinfo" frameheight="50%"
		src="groupTalkRoom.action?cmd=showprivateinfo&amp;roomId=${chatroom.roomId}&amp;groupId=${groupId}" 
		scrolling="auto"/>
</frameset>

<noframes>
<body>
	您的浏览器不支持框架！！！
</body>
</noframes>
</html>



