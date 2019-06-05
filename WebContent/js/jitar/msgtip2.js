document.write("<div style='visibility:hidden;position:absolute;top:0;left:0;width:300px;height:180px;border:3px double #AAA' id='msgtip'></div>");

function scrollMsgTip()
{
	var t = LoginUI.getViewportHeight() + LoginUI.getViewportScrollY() - $("msgtip").offsetHeight;
	if(!Platform.isIE)
	{
		t += 16;
	}
	
	$("msgtip").style.top = t + "px"; 
	$("msgtip").style.left = LoginUI.getViewportWidth() + LoginUI.getViewportScrollX() - $("msgtip").offsetWidth + "px";
}

function scrollTip()
{
	scrollMsgTip();
}

function preventEvents(event)
{
	try
	{
		if(Platform.isIE)
		{
			event.cancelBubble = true;
			event.returnValue = false;
		}
		else
		{
			event.stopPropagation();		
			event.preventDefault();
		}
	}
	catch(ex){}	
}

/*
 * 判断并返回消息数目
 */

var msgurl;
if(typeof HasDomain == 'undefined'){
	msgurl = JITAR_ROOT + 'manage/message.py?cmd=getnew&tmp=' + (new Date()).getTime();
}
else{
	msgurl = CommonUtil.getCurrentRootUrl() + 'manage/message.py?cmd=getnew&tmp=' + (new Date()).getTime();
}

function init()
{
if(visitor && visitor.id ){
	new Ajax.Request(msgurl, { 
	          method: 'get',
	          onSuccess: function(xport,jsonData) {
	          if(jsonData){
	          		var uid = jsonData[0].id;
	          		var msgcount = jsonData[0].msgcount;
	          		var _timerTip = null;
					$("msgtip").onmouseover = preventEvents;
					var _msg = "";
					_msg += "<div class='divCls2 fontBold' onclick='$(\"msgtip\").style.visibility = \"hidden\";'></div><div onmousedown=\"DivUtil.dragStart(event, 'msgtip')\" class=\"msgTitle\">网站信息提示器提示您：</div>";
					_msg += "<div style='padding-left:20px;line-height:160%;'>";
					_msg += "您的好友给您发来 <a href='" + JITAR_ROOT + "manage/?url=message.py%3Fcmd%3Dinbox'><font color='red'>" + msgcount + "</font></a> 条短消息 [<a target='_blank' href='" + JITAR_ROOT + "manage?url=message.py%3Fcmd%3Dinbox'>查看消息</a>]。<br />";
					_msg += "</div>";					
					
					$("msgtip").innerHTML = _msg;
					$("msgtip").style.visibility = "visible";
					scrollTip();
					window.onscroll = scrollTip;
				}		
	       }
	   }
	);
}
}