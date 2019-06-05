document.writeln("<div onmouseover='ToolTip._stopMouseOver(event)' id='UserCard_Layer' class='UserCard' style='display:none;'>");
document.writeln("<table border='0' cellspacing='0' cellpadding='0'><tr>");
document.writeln("<td class='left' id='UserCard_left'></td><td class='right' id='UserCard_right'></td>");
document.writeln("</tr><tr><td colspan='2' id='UserCard_Data' class='totalCont'></td>");
document.writeln("</tr></table></div>");
document.writeln("<div id='shareDiv'></div>");

 if(!window.attachEvent && window.addEventListener)
{
  window.attachEvent = HTMLElement.prototype.attachEvent= document.attachEvent = function(en, func, cancelBubble)
  {
    var cb = cancelBubble ? true : false;
    this.addEventListener(en.toLowerCase().substr(2), func, cb);
  };
  window.detachEvent = HTMLElement.prototype.detachEvent= document.detachEvent = function(en, func, cancelBubble)
  {
    var cb = cancelBubble ? true : false;
    this.removeEventListener(en.toLowerCase().substr(2), func, cb);
  };
}

document.attachEvent("onmouseover", function(){
	try{
		$("UserCard_Layer").style.display="none";
	}
	catch(ex){}
	}
);
