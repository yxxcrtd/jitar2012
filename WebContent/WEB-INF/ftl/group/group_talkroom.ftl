<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>讨论室</title>
<style>
	BODY{FONT-SIZE:9pt;}
	TD{FONT-SIZE:9pt;}
	FONT{FONT-SIZE:9pt;}
	.titleFont{
		color:#2A6D97;
		font-size:13px;	
		position:relative;
		left:40px;		
	}	
	.groupImge{
		border-color:#000000; 
		border-bottom-style: solid; 
		border-bottom-color:#000000;
		border-bottom-width:1pt;	
		border-left-style:solid; 
		border-left-color:#000000;
		border-left-width:1pt;	
		border-top-style:solid; 
		border-top-color:#000000;
		border-top-width:1pt;		
		border-right-style:solid; 
		border-right-color:#000000;
		border-right-width:1pt;	
		}
	.divShowText{
		border-color:#000000; 
		border-bottom-style: solid; 
		border-bottom-color:#000000;
		border-bottom-width:1px;	
		border-left-style:solid; 
		border-left-color:#000000;
		border-left-width:1px;	
		border-top-style:solid; 
		border-top-color:#000000;
		border-top-width:1px;		
		border-right-style:solid; 
		border-right-color:#000000;
		border-right-width:1px;	
		background-color:transparent;
	}	
	.iframestyle{
		border-color:#2A6D97; 
		border-bottom-style: solid; 
		border-bottom-color:#2A6D97;
		border-bottom-width:1px;	
		border-left-style:solid; 
		border-left-color:#2A6D97;
		border-left-width:1px;	
		border-top-style:solid; 
		border-top-color:#2A6D97;
		border-top-width:1px;		
		border-right-style:solid; 
		border-right-color:#2A6D97;
		border-right-width:1px;	
		background-color:transparent;
	}	
	.tableCaption
	{	
		font-size:13px;
		background-color:#D0E2EC;
		text-align:center;
		text-shadow: black;
	}
	.tableText
	{
		font-size:12px;
		background-color:#FFFFFF;
		text-align:left;
	}
</style>
</head>


<body bgcolor="#add8e6" leftmargin="0" topmargin="0"  marginheight="0" marginwidth="0" style="border:1px">
<table border="1" cellpadding="2" cellspacing="2" width="755" height="97%">
  	<tr height="5%">
		<td width="755" style="background:url(/Groups/images/chatroom/titleDot.gif) left no-repeat;background-color:#A4C0C4">
			<font class="titleFont">${chatroom.roomName}讨论室</font>
		</td>
	</tr>
	
	<tr height="95%">
	<td  bgcolor="#fffff">
		<table border="0" cellpadding="0" cellspacing="1" width="755" height="100%" style="border:1px" bgColor="#ffffff"  bordercolor="blue">
			<tr  height="80%">
				<td width="600">
					<iframe marginheight="0" marginwidth="0" frameborder="0" class="iframestyle"  
					scrolling="auto" id="chatinfo" name="chatinfo" style="width:100%;height:100%" 
					src="groupTalkRoom.action?cmd=chatinfo&amp;roomId=${chatroom.roomId}&amp;groupId=${group.groupId}"></iframe>
				</td>
				<td width="155">
					<iframe marginheight="0" marginwidth="0" frameborder="0" class="iframestyle"   
					scrolling="auto" name="chatuser" style="width:100%;height:100%" 
					src="groupTalkRoom.action?cmd=chatuser&amp;roomId=${chatroom.roomId}&amp;groupId=${group.groupId}"></iframe>
				</td>
			</tr>
			<tr  height="20%">
				<td colspan="2">
					<iframe marginheight="0" marginwidth="0" frameborder="0" class="iframestyle"  
					 scrolling="auto" name="chatsend" style="width:100%;height:100%" 
					 src="groupTalkRoom.action?cmd=chatsend&amp;roomId=${chatroom.roomId}&amp;groupId=${group.groupId}"></iframe>
				</td>
			</tr>
		</table>	
	</td>
	</tr>
</table>
</body>
</html>