var LoginUI = !!window.LoginUI || {};

LoginUI.getViewportWidth = function(){
	var width=0;
	if(document.documentElement && document.documentElement.clientWidth){
	width=document.documentElement.clientWidth;}
	else if(document.body && document.body.clientWidth){
	width=document.body.clientWidth;}
	else if(window.innerWidth){
	width=window.innerWidth-18;}
	return width;
};

LoginUI.getContentHeight = function()
{
	if(document.body&&document.body.offsetHeight){
	return document.body.offsetHeight;
	}
};

LoginUI.getViewportHeight = function() {
	var height=0;
	if(window.innerHeight){
	height=window.innerHeight-18;}
	else if(document.documentElement&&document.documentElement.clientHeight){
	height=document.documentElement.clientHeight;}
	else if(document.body&&document.body.clientHeight){
	height=document.body.clientHeight;}
	return height;
};

LoginUI.getViewportScrollX = function(){
	var scrollX=0;
	if(document.documentElement&&document.documentElement.scrollLeft){
	scrollX=document.documentElement.scrollLeft;}
	else if(document.body&&document.body.scrollLeft){
	scrollX=document.body.scrollLeft;}
	else if(window.pageXOffset){
	scrollX=window.pageXOffset;}
	else if(window.scrollX){
	scrollX=window.scrollX;}
	return scrollX;
};

LoginUI.getViewportScrollY=function() {
	var scrollY=0;
	if(document.documentElement&&document.documentElement.scrollTop){
	scrollY=document.documentElement.scrollTop;}
	else if(document.body&&document.body.scrollTop){
	scrollY=document.body.scrollTop;}
	else if(window.pageYOffset){
	scrollY=window.pageYOffset;}
	else if(window.scrollY){
	scrollY=window.scrollY;}
	return scrollY;
};

LoginUI.getViewportScrollHeight=function() {
	var scrollH=0;
	if(document.documentElement && document.documentElement.scrollHeight){
	scrollH=document.documentElement.scrollHeight;}
	else{
	scrollH=document.body.scrollHeight;}	
	return scrollH;
};

LoginUI.centerDiv = function(div)
{
	var top=((LoginUI.getViewportHeight()-div.offsetHeight)/2);
	if(top<0)top=10;
	div.style.left=((LoginUI.getViewportWidth()-div.offsetWidth)/2)+"px";
	div.style.top=top+LoginUI.getViewportScrollY()+"px";
};

/* 此方法应该已经不再使用了 */
LoginUI.showLogin = function()
{	
	$("_userAuthImg").src = USERMGR_ROOT + "authimg?tmp=" + (new Date()).getTime();
	$("Login").className = "popup container";
	$("Login").style.display="block";
	LoginUI.centerDiv($("Login"));
	$("blockUI").style.display="block";
	$("blockUI").style.height = LoginUI.getViewportScrollHeight() + LoginUI.getViewportScrollY() + "px";
	var returnedUrl = document.getElementsByName("redUrl");
	if(returnedUrl && returnedUrl[0].tagName == "INPUT")
	{
		returnedUrl[0].value = window.location.href;
	}
};

LoginUI.hideLogin = function()
{
	//$("Login").className = "popup container";
	//LoginUI.centerDiv($("Login"));
	$("blockUI").style.display="none";
	$("Login").style.display="none";
	//$("blockUI").style.height = LoginUI.getViewportScrollHeight() + LoginUI.getViewportScrollY() + "px";
};

LoginUI.checkLogin = function(userName,type)
{
	var url = USERSITE_ROOT+"py/check_login_status.py";
	new Ajax.Request(
			url,
			{
				method : 'get',
				onSuccess : function(xport) {
					if(xport.responseText=="1")
					{  
						type=="friend"?DivUtil.addFriend(userName):DivUtil.sendUserMessage(userName);
					}
					else{
						window.location.href=JITAR_ROOT + "login.jsp?redUrl=" + encodeURIComponent(window.location.href);
					}
						
				},
				onException : function(xport, ex) {
					window.location.href=JITAR_ROOT + "login.jsp?redUrl=" + encodeURIComponent(window.location.href);
				},
				onFailure : function(xport, ex) {
					window.location.href=JITAR_ROOT + "login.jsp?redUrl=" + encodeURIComponent(window.location.href);
				}
			});
};