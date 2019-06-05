var ToolTip = !!window.ToolTip || {};

ToolTip.UserDataBase = {}; //如果不是ajax请求，就不用这个了

ToolTip.getScrollXY = function()
{
	var x=0,y=0;
    if(document.documentElement.scrollTop){
        x=document.documentElement.scrollLeft;
        y=document.documentElement.scrollTop;
    }
    else{
        x=document.body.scrollLeft;
        y=document.body.scrollTop;
    }
    return {"x":x,"y":y};
};

ToolTip.getEventXY = function(e)
{
	var posx=0,posy=0;
    if(e==null) e = window.event;
    if(e.pageX || e.pageY)
    {
        posx=e.pageX; posy=e.pageY;
    }
    else if(e.clientX || e.clientY)
    {
       posx=e.clientX+ToolTip.getScrollXY().x;
       posy=e.clientY+ToolTip.getScrollXY().y;
    }
    return {"x":posx, "y":posy};
};

ToolTip.getRealOffset =  function(o)
{
  if(!o) return null; var e=o, x=y=l=t=0;
  do{l+=e.offsetLeft||0; t+=e.offsetTop||0; e=e.offsetParent;}while(e);
  do{x+=o.scrollLeft||0; y+=o.scrollTop||0; o=o.parentNode;}while(o);
  var xy=ToolTip.getScrollXY();
  return {"x":l-x+xy.x, "y":t-y+xy.y};
};

ToolTip._stopMouseOver = function(e){
	( window.event || e ).cancelBubble = true;
};
ToolTip.hideUserCard = function()
{
	$("UserCard_Layer").style.display = "none";
};

ToolTip.hideCalendarCard = function()
{
	$("CalendarCard_Layer").style.display = "none";
};

ToolTip.showCalendarCard = function(e)
{
	e = window.event || e;
	e.cancelBubble = true;
	var img = e.srcElement || e.target; 
	var layer = $("CalendarCard_Layer"); 
	if (!layer) { return; }
	layer.style.display = "";
	var xy; 
	if (navigator.userAgent.toLowerCase().indexOf("opera") < 0) {
		 xy = ToolTip.getRealOffset(img); 
		 layer.style.left = (xy.x + img.offsetWidth - 6) + "px"; 
		 layer.style.top = xy.y + "px"; 
	 } 
	 else { 
		 xy = ToolTip.getEventXY(e); 
		 layer.style.left = xy.x + "px"; 
		 layer.style.top = (xy.y - 6) + "px"; 
	 }	
};
ToolTip.showUserCard = function(e, uid,uName)
{
	e = window.event || e;
	e.cancelBubble = true; 
	var img = e.srcElement || e.target; 
	/*if (!user) { return; }*/ 
	var layer = $("UserCard_Layer"); 
	if (!layer) { return; } 
	layer.style.display = ""; 
	var xy; 
	if (navigator.userAgent.toLowerCase().indexOf("opera") < 0) {
		 xy = ToolTip.getRealOffset(img); 
		 layer.style.left = (xy.x + img.offsetWidth - 6) + "px"; 
		 layer.style.top = xy.y + "px"; 
	 } 
	 else { 
		 xy = ToolTip.getEventXY(e); 
		 layer.style.left = xy.x + "px"; 
		 layer.style.top = (xy.y - 6) + "px"; 
	 }
	 var qq=""; 
	 //得到用户信息
	 var userIcon = JITAR_ROOT + 'images/default.gif';
	 if(arguments.length > 3)
	 {
	 	userIcon = arguments[3];
	 }
	 if(arguments.length > 4)
	 {
	 	qq = arguments[4];
	 }
	 var leftContent = "<a target='_blank' href='"+ JITAR_ROOT + "go.action?loginName=" + uid +"'><img width='32' height='32' border='0' src='" + userIcon + "' onerror='this.src=JITAR_ROOT + \"images/default.gif\"' /></a>";
	 var rightContent = "<a target='_blank' href='"+ JITAR_ROOT + "go.action?loginName=" + uid +"'>" + uName + "</a>";
	 if(qq!="")
	 {
	    rightContent=rightContent+"<a href='tencent://Message/?Uin="+ qq +"&websitName=ss&Menu=yes'>";
		rightContent=rightContent+"<img title='"+qq+"' src='"+JITAR_ROOT+"images/qq_01.gif' width='16' height='16'  border='0'/>";
		rightContent=rightContent+"</a>";
	 }
	 var contentData = "<a href='"+ JITAR_ROOT + "show_profile.py?loginName=" + uid + "'>查看档案</a> <a href='"+ JITAR_ROOT + uid +"' onclick='DivUtil.addFriend(\"" + uid + "\");ToolTip.hideUserCard();return false;'>加为好友</a><br /><a href='"+ JITAR_ROOT + uid +"' onclick='DivUtil.sendUserMessage(\"" + uid + "\");ToolTip.hideUserCard();return false;'>发送消息</a> <a href='"+ JITAR_ROOT + "go.action?loginName=" + uid +"'>访问博客</a> <a style='display:none' href='"+ JITAR_ROOT + uid +"'>访问相册</a> ";
	 $("UserCard_left").innerHTML = leftContent; 
	 $("UserCard_right").innerHTML = rightContent; 
	 $("UserCard_Data").innerHTML = contentData;
};

//复制页面地址
function copyToClipBoard()
{
	var meintext="";
	meintext+=this.location.href; //获取地址
	
	if (window.clipboardData){
	   window.clipboardData.setData("Text", meintext);
	   }
	else if (window.netscape) { 
	   netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
	   var clip = Components.classes['@mozilla.org/widget/clipboard;1']
	                 .createInstance(Components.interfaces.nsIClipboard);
	   if (!clip) return;
	   var trans = Components.classes['@mozilla.org/widget/transferable;1']
	                  .createInstance(Components.interfaces.nsITransferable);
	   if (!trans) return;
	   trans.addDataFlavor('text/unicode');
	   var str = new Object();
	   var len = new Object();
	   var str = Components.classes["@mozilla.org/supports-string;1"]
	                .createInstance(Components.interfaces.nsISupportsString);
	   var copytext=meintext;
	   str.data=copytext;
	   trans.setTransferData("text/unicode",str,copytext.length*2);
	   var clipid=Components.interfaces.nsIClipboard;
	   if (!clip) return false;
	   clip.setData(trans,null,clipid.kGlobalClipboard);
	}
	

	alert("复制成功");
}

//增加到收藏夹
var sub_favorites=0;
function add_favorites(id,uuid,type,title){
	if(!confirm("您要收藏这篇文章吗？"))return;
	if(sub_favorites==1)return;
	sub_favorites==1;
	if(title==null || title=='')
	{
		title=document.title;
	}
	var url = JITAR_ROOT+'Favorite.py?cmd=add&objectId=' + id + '&objectUuid='+ uuid +'&objectType='+ type +'&url='+ this.location.href +'&title='+encodeURI(title)+'&tmp=' + Math.random();
  new Ajax.Request(url, {
    method: 'get',
    onSuccess: function(xport) { 
        var result = xport.responseText;
			if(result=='logon'){
				alert('您需要登录后才能收藏。');
			}else if(result=='ok'){
				alert('收藏成功。');
			}else if(result=='exist'){
				alert('这篇文章已经收藏在您的收藏夹了。');
			}else{
				alert('出錯!'+result);
			}
        
      }
  });	
	sub_favorites=0;
}

