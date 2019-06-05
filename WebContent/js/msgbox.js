//创建通用的拖动层.
var MessageBox = !!window.MessageBox || {};
MessageBox.targetElement = null;
MessageBox.dragObj = new Object();
MessageBox.dragObj.zIndex = 0;
MessageBox.dragStart = function(event) {
  if(MessageBox.targetElement == null)
  {
    return;
  }
  var el;
  var x, y;
  MessageBox.dragObj.elNode = MessageBox.targetElement; 
  if (window.event) {
    x = window.event.clientX + document.documentElement.scrollLeft
      + document.body.scrollLeft;
    y = window.event.clientY + document.documentElement.scrollTop
      + document.body.scrollTop;
  }
  else{
    x = event.clientX + window.scrollX;
    y = event.clientY + window.scrollY;
  }

  MessageBox.dragObj.cursorStartX = x;
  MessageBox.dragObj.cursorStartY = y;
  MessageBox.dragObj.elStartLeft  = parseInt(MessageBox.dragObj.elNode.style.left, 10);
  MessageBox.dragObj.elStartTop   = parseInt(MessageBox.dragObj.elNode.style.top,  10);

  if (isNaN(MessageBox.dragObj.elStartLeft)) MessageBox.dragObj.elStartLeft = 0;
  if (isNaN(MessageBox.dragObj.elStartTop))  MessageBox.dragObj.elStartTop  = 0;

  if (window.event) {
    document.attachEvent("onmousemove", MessageBox.dragGo);
    document.attachEvent("onmouseup",   MessageBox.dragStop);
    window.event.cancelBubble = true;
    window.event.returnValue = false;
  }
  else {
    document.addEventListener("mousemove", MessageBox.dragGo,   true);
    document.addEventListener("mouseup",   MessageBox.dragStop, true);
    event.preventDefault();
  }
}

MessageBox.dragGo = function(event) {
  var x, y;
  if (window.event) {
    x = window.event.clientX + document.documentElement.scrollLeft
      + document.body.scrollLeft;
    y = window.event.clientY + document.documentElement.scrollTop
      + document.body.scrollTop;
  }
  else{
    x = event.clientX + window.scrollX;
    y = event.clientY + window.scrollY;
  }

  MessageBox.dragObj.elNode.style.left = (MessageBox.dragObj.elStartLeft + x - MessageBox.dragObj.cursorStartX) + "px";
  MessageBox.dragObj.elNode.style.top  = (MessageBox.dragObj.elStartTop  + y - MessageBox.dragObj.cursorStartY) + "px";

  if (window.event) {
    window.event.cancelBubble = true;
    window.event.returnValue = false;
  }
  else
    event.preventDefault();
}

MessageBox.dragStop = function(event) {
  if (window.event) {
    document.detachEvent("onmousemove", MessageBox.dragGo);
    document.detachEvent("onmouseup",   MessageBox.dragStop);
  }
  else {
    document.removeEventListener("mousemove", MessageBox.dragGo,   true);
    document.removeEventListener("mouseup",   MessageBox.dragStop, true);
  }
}

MessageBox.getViewportWidth = function(){
  var width=0;
  if(document.documentElement && document.documentElement.clientWidth){
  width=document.documentElement.clientWidth;}
  else if(document.body && document.body.clientWidth){
  width=document.body.clientWidth;}
  else if(window.innerWidth){
  width=window.innerWidth-18;}
  return width;
}

MessageBox.getContentHeight = function()
{
  if(document.body&&document.body.offsetHeight){
  return document.body.offsetHeight;
  }
}

MessageBox.getViewportHeight = function() {
  var height=0;
  if(window.innerHeight){
  height=window.innerHeight-18;}
  else if(document.documentElement&&document.documentElement.clientHeight){
  height=document.documentElement.clientHeight;}
  else if(document.body&&document.body.clientHeight){
  height=document.body.clientHeight;}
  return height;
}

MessageBox.getViewportScrollX = function(){
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
}

MessageBox.getViewportScrollY=function() {
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
}

MessageBox.getViewportScrollHeight=function() {
  var scrollH=0;
  if(document.documentElement && document.documentElement.scrollHeight){
  scrollH=document.documentElement.scrollHeight;}
  else{
  scrollH=document.body.scrollHeight;}  
  return scrollH;
}

MessageBox.centerDiv = function(div)
{
  var top=((MessageBox.getViewportHeight()-div.offsetHeight)/2);
  if(top<0)top=10;
  div.style.left=((MessageBox.getViewportWidth()-div.offsetWidth)/2)+"px";
  div.style.top=top+MessageBox.getViewportScrollY()+"px";
}

MessageBox.Show = function(moveEleId)
{
  MessageBox.targetElement = document.getElementById(moveEleId);
  MessageBox.targetElement.className = "popup container";
  MessageBox.targetElement.style.display="block";
  MessageBox.centerDiv(MessageBox.targetElement);
  document.getElementById("blockUI").style.display="block";
  document.getElementById("blockUI").style.height = MessageBox.getViewportScrollHeight() + MessageBox.getViewportScrollY() + "px";
}

MessageBox.Close = function()
{
  document.getElementById("blockUI").style.display="none";
  if(MessageBox.targetElement != null)
  {
   MessageBox.targetElement.style.display="none";
   MessageBox.targetElement = null;
  }
  return false;
}
