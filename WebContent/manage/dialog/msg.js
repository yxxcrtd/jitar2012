function $(str){
	return document.getElementById(str);
}
function _(str){
	return document.getElementsByTagName(str);
}
function msg(boxtitle,boxtype,boxwidth,msg,url){
	$("msg_div_main").style.width = boxwidth + "px";
    var w = boxwidth;
    var h = 180;
	var si = document.body.clientWidth;
	var he = document.documentElement.clientHeight||document.body.clientHeight;
    var LeftPosition = (si) ? (si-w)/2 : 0  ;
    var TopPosition = (he) ? (he-h)/2 : 0 ;
    $("msg_div_main").style.left = LeftPosition + "px";
	$("msg_div_main").style.top  = TopPosition + "px";
	var msg_div_main_but_tmp = "<br /><br />"
				+ "<button class='msg_div_main_but' id='msg_div_main_but' "
				+ "onclick=\"msg_close_tmp_biyuan();" + url + "\">确 定</button>";
	switch(boxtype * 1){
		case 1:
			$("msg_div_main_content").innerHTML = msg + msg_div_main_but_tmp;
			//$("msg_div_main_but").focus();
		break;
		case 2:
			$("msg_div_main_content").innerHTML =  msg + msg_div_main_but_tmp
							 + "&nbsp;&nbsp;<button class='msg_div_main_but' "
							 + "onclick='msg_close_tmp_biyuan();'>取 消</button>";
			//$("msg_div_main_but").focus();
		break;
		case 3:
			$("msg_div_main_content").innerHTML =  msg;
		break;
		defualt:
			$("msg_div_main_content").innerHTML =  msg;
		break;
	}
	$("msg_div_main_title").innerHTML =  boxtitle;
	$("msg_div_main").style.zIndex = 200;
	$("msg_div_main").style.display = "";
	if(document.all){	//IE
		if(!$("msg_div_all_Iframe"))
		{
			//document.body.appendChild(document.createElement("<iframe id='msg_div_all_Iframe' style='display:none;'></iframe>"));
	        var o = document.createElement("IFRAME");
	        o.id = "msg_div_all_Iframe";
	        o.style.display = "none";
	        document.body.appendChild(o);
		}
		$("msg_div_all").style.zIndex  = 100;
		$("msg_div_all").style.display = "none";
		$("msg_div_all").oncontextmenu = function()
		{
			return false;
		}
		$("msg_div_all_Iframe").style.zIndex  = 99;
		$("msg_div_all_Iframe").style.display = "none";
		$("msg_div_all_Iframe").oncontextmenu = function()
		{
			return false;
		}
	}
	else
	{
		$("msg_div_all").style.zIndex  = 100;
		$("msg_div_all").style.display = "";
		$("msg_div_all").oncontextmenu = function()
		{
			return false;
		}
	}
	$("msg_div_main").oncontextmenu = function(){
		return false;
	}
}
function msg_close_tmp_biyuan(){
	$('msg_div_all').style.display='none';
	$('msg_div_main').style.display='none';
	if(document.all)
	{
		$('msg_div_all_Iframe').style.display='none';
	}
}
//加入对话框移动代码
/* 鼠标拖动 */
var oDrag = "";
var ox,oy,nx,ny,dy,dx;
function drag(e,o){
	var e = e ? e : event;
	var mouseD = document.all ? 1 : 0;
	if(e.button == mouseD)
	{
		if (o.parentNode)
		{
			oDrag = o.parentNode;
		}
		else
		{
			oDrag = o;
		}
		ox = e.clientX;
		oy = e.clientY;		
	}
}
function dragPro(e){
	if(oDrag != "")
	{	
		var obj=oDrag;//拖动的id
		var e = e ? e : event;
		obj.style.position = 'absolute';
		dx = parseInt(obj.style.left);
		dy = parseInt(obj.style.top);
		if(isNaN(dx)){dx=0;}
		if(isNaN(dy)){dy=0;}
		nx = e.clientX;
		ny = e.clientY;
		obj.style.left = (dx + ( nx - ox )) + "px";
		obj.style.top = (dy + ( ny - oy )) + "px";
		ox = nx;
		oy = ny;
	}
}
document.onmouseup = function(){oDrag = "";}
document.onmousemove = function(event){dragPro(event);}
document.writeln("<style type='text/css'>"
	+ "#msg_div_all,#msg_div_all_Iframe{width:100%;height:100%;position:absolute;filter:Alpha(opacity=70);opacity:0.7;background:#EFEFEF;}"
	+ "#msg_div_main {position:absolute;}"
	+ "#msg_div_main_title {font-size:12px;color:#2C71AF;font-family:verdana;cursor:default;}"
	+ "#msg_div_main_content {font-size:14px;color:#2C71AF;padding-left:8px;}"
	+ ".msg_div_main_but {background:url(dialog/images/buttonbg.gif);width:65px;heigt:20px;border:none;padding-top:3px;font-size:12px;}"
	+ "</style>"
	+ "<div id='msg_div_all' style='display:none;'></div>"
	+""
	+ "<div id='msg_div_main' style='display:none;'>"
	+ "<table width='100%' height='29' border='0' cellspacing='0' cellpadding='0'  onmousedown='drag(event,this)'>"
	+ "<tr>"
	+ "<td width='25'><img src='dialog/images/bg_01.gif' width='25' height='29' alt='' /></td><td background='dialog/images/bg_02.gif' width='3'></td>"
	+ "<td background='dialog/images/bg_02.gif'  msg_forid='msg_div_main' id='msg_div_main_title'></td>"
	+ "<td background='dialog/images/bg_02.gif' align='right' style='padding-top:4px'>"
	+ "<img src='dialog/images/bg_05.gif' width='21' height='21' alt='关闭' "
	+ "onMouseover=\"this.src='dialog/images/bg_13.gif'\" "
	+ "onMouseout=\"this.src='dialog/images/bg_05.gif'\" onMouseup='msg_close_tmp_biyuan();' "
	+ "onMousedown=\"this.src='dialog/images/bg_18.gif'\"></td>"
	+ "<td width='6'><img src='dialog/images/bg_06.gif' width='6' height='29' alt='' /></td>"
	+ "</tr>"
	+ "</table>"
	+ "<table width='100%' border='0' cellspacing='0' cellpadding='0'>"
	+ "<tr>"
	+ "<td width='3' background='dialog/images/bg_07.gif'></td>"
	+ "<td bgcolor='#F7F7F7' align='center'><br /><span id='msg_div_main_content'></span><br /><br /></td>"
	+ "<td width='3' background='dialog/images/bg_08.gif'></td>"
	+ "</tr>"
	+ "<tr>"
	+ "<td width='3' height='3'><img src='dialog/images/bg_09.gif' width='3' height='3' alt='' /></td>"
	+ "<td background='dialog/images/bg_11.gif'></td>"
	+ "<td width='3' height='3'><img src='dialog/images/bg_10.gif' width='3' height='3' /></td>"
	+ "</tr>"
	+ "</table>"
	+ "</div>");