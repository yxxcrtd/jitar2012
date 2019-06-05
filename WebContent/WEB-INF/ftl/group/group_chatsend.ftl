<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <title>协作组管理</title>
    <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>

<body  bgcolor="#ffffff" leftmargin="0" topmargin="0"  marginheight="0" marginwidth="0">
	<iframe name="hiddenframe" style="height:40px;width:500px;display:none"></iframe><!--信息提交到此窗口-->
	
 <form action="groupTalkRoom.action" name="messageForm" method="post" target="hiddenframe" >
 	<input type="hidden" name="message" value=""/>
	<input type="hidden" name="roomId" value="${chatroom.roomId}"/>
	<input type="hidden" name="groupId" value="${groupId}"/>
	<input type="hidden" name="cmd" value="saveMessage"/>
	
	<table  border="1" cellpadding="0" cellspacing="0" width="100%" height="100% ">
	<tr width="60%">
		<input type="hidden" name="talkto2" value="everyone"/>
		<td>&nbsp;&nbsp;
		<font style="font-size: 12px">对象</font>
			<select name="talkto" width=88 onchange="aaa()">
				<option value="所有人">所有人</option>
			</select>
			<select name="act" onchange="aaa()">
				<option value="talk">说话</option>
				<option value="ask">请问</option>
				<option value='chant'>歌颂</option>
				<option value='cheer'>喝采</option>
				<option value='chuckle'>轻笑</option>
				<option value='demand'>要求</option>
				<option value='groan'>呻吟</option>
				<option value='grumble'>牢骚</option>
				<option value='hum'>自语</option>
				<option value='moan'>悲叹</option>
				<option value='notice'>注意</option>
				<option value='order'>命令</option>
				<option value='ponder'>沉思</option>
				<option value='pout'>撅嘴</option>
				<option value='pray'>祈祷</option>
				<option value='request'>恳求</option>
				<option value='shout'>大叫</option>
				<option value='sing'>唱歌</option>
				<option value='smile'>微笑</option>
				<option value='swear'>发誓</option>
				<option value='smirk'>假笑</option>
				<option value='sob'>哭啼</option>
				<option value='tease'>嘲笑</option>
				<option value='whimper'>呜咽</option>
				<option value='yawn'>哈欠</option>
				<option value='yell'>大喊</option>
			</select>
			
			<select name="color" onchange="aaa()">
				<option value="0" style="color:black" selected>颜色</option>
				<option style="background:#000088" value="000088"></option>
				<option style="background:#0000ff" value="0000ff"></option>
				<option style="background:#008800" value="008800"></option>
				<option style="background:#008888" value="008888"></option>
				<option style="background:#0088ff" value="0088ff"></option>
				<option style="background:#00a010" value="00a010"></option>
				<option style="background:#1100ff" value="1100ff"></option>
				<option style="background:#111111" value="111111"></option>
				<option style="background:#333333" value="333333"></option>
				<option style="background:#50b000" value="50b000"></option>
				<option style="background:#880000" value="880000"></option>
				<option style="background:#8800ff" value="8800ff"></option>
				<option style="background:#888800" value="888800"></option>
				<option style="background:#888888" value="888888"></option>
				<option style="background:#8888ff" value="8888ff"></option>
				<option style="background:#aa00cc" value="aa00cc"></option>
				<option style="background:#aaaa00" value="aaaa00"></option>
				<option style="background:#ccaa00" value="ccaa00"></option>
				<option style="background:#ff0000" value="ff0000"></option>
				<option style="background:#ff0088" value="ff0088"></option>
				<option style="background:#ff00ff" value="ff00ff"></option>
				<option style="background:#ff8800" value="ff8800"></option>
				<option style="background:#ff0005" value="ff0005"></option>
				<option style="background:#ff88ff" value="ff88ff"></option>
				<option style="background:#ee0005" value="ee0005"></option>
				<option style="background:#ee01ff" value="ee01ff"></option>
				<option style="background:#3388aa" value="3388aa"></option>
				<option style="background:#000000" value="000000"></option>
			</select>
			<select name="face" onchange="msg.focus();">
				<option value="0" selected>贴图</option>
			    <option value="smile01.gif">憨笑</option>
				<option value="smile02.gif">惊奇</option>
				<option value="smile03.gif">闭嘴</option>
				<option value="smile04.gif">吐舌</option>
				<option value="smile05.gif">感动</option>
				<option value="smile06.gif">唱歌</option>
				<option value="smile07.gif">晕</option>
				<option value="smile08.gif">羡慕</option>
				<option value="smile09.gif">不说</option>
				<option value="smile10.gif">困了</option>
				<option value="smile11.gif">呐喊</option>
				<option value="smile12.gif">生病</option>
				<option value="smile13.gif">牛</option>
				<option value="smile14.gif">地雷</option>
				<option value="smile15.gif">吃饭</option>
				<option value="smile16.gif">诅咒</option>
				<option value="smile17.gif">衰</option>
				<option value="smile18.gif">受伤</option>
			    <option value="smile19.gif">骷髅</option>
			    <option value="smile20.gif">扮酷</option>
				<option value="smile21.gif">难过</option>
				<option value="smile22.gif">不屑</option>
				<option value="smile23.gif">怀疑</option>
				<option value="smile24.gif">悲伤</option>
				<option value="smile25.gif">西瓜</option>
				<option value="smile26.gif">委屈</option>
				<option value="smile27.gif">折磨</option>
				<option value="smile28.gif">高兴</option>
				<option value="smile29.gif">微笑</option>
			    <option value="smile30.gif">痛苦</option>
				<option value="smile31.gif">不信</option>
				<option value="smile32.gif">黑子</option>
				<option value="smile33.gif">PK</option>
				<option value="smile34.gif">抽烟</option>
				<option value="smile35.gif">板砖</option>
				<option value="smile36.gif">生气</option>
				<option value="smile37.gif">难受</option>
				<option value="smile38.gif">惊恐</option>
				<option value="smile39.gif">发火</option>
				<option value="smile40.gif">害羞</option>
				<option value="smile41.gif">流汗</option>
				<option value="smile42.gif">欢呼</option>
			</select>
			<select	name="font"	onchange="aaa()">
				<option value=0>字体</option>
				<option value=0>====</option>
				<option	value="宋体">宋体</option>
				<option	value="仿宋_GB2312">仿宋</option>
				<option	value="楷体_GB2312">楷体</option>
				<option	value="黑体">黑体</option>
				<option	value="隶书">隶书</option>
				<option	value="幼圆">幼圆</option>
				<option value=0>====</option>
				<option	value="09pt">9pt</option>
				<option	value="010pt">10pt</option>
				<option	value="010.5pt">10.5pt</option>
				<option	value="011pt">11pt</option>
				<option	value="012pt">12pt</option>
				<option	value="013pt">13pt</option>
			</select>&nbsp;&nbsp;&nbsp;
		
				<input type="checkbox" name="privateChat" value="1" onclick="aaa()">
				<a href="#" onclick="aaa()">密谈</a>
				<input type="checkbox" name="half" onclick="aaa()">
				<a href="#" onclick="aaa()">分屏</a>
	<tr>
	<td>&nbsp;&nbsp;<font style="font-size: 12px">信息</font>
		<input type="text" name="msg" />
		<input type="button" value='<' name="last_s" name="last_sentence"   onclick="gP(TODO);" ondblclick="gP();" />
		<input type="button" value='>' name="next_s" title="nxt_sentence" onclick="gN(TODO);" ondblclick="gN();" />
		<input type="submit" value="发送" name="sendbutton" />
		&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:clearWin();inputform.msg.focus();">清屏</a>&nbsp;&nbsp;
		<a href="javascript:exitChat();">退出</a>
	
	</td>
	</tr>
		</td>
	</tr>
	
 </table>
</form>
</body>

<script type="text/javascript">
	function aaa()
	{} 
</script>
</html>

