var SelectUtil = !!window.SelectUtil || {};
SelectUtil.showDropdown =  function(selfObj,toShowObj,evt)
{ 
	var toShowObjDim = SelectUtil.getDim(selfObj)
  $(toShowObj).style.left = toShowObjDim.x + "px"
  $(toShowObj).style.top = toShowObjDim.y + selfObj.offsetHeight  + "px"
  $(toShowObj).className = $(toShowObj).className == "select_drop"?"select_drop_show":"select_drop";
  if(parseInt($(toShowObj).offsetWidth) < parseInt(selfObj.offsetWidth))
  {
    $(toShowObj).style.width = selfObj.offsetWidth + "px"
  }
  else
  {
  	//Firefox 的 Bug?
  	$(toShowObj).style.width = parseInt($(toShowObj).offsetWidth) + "px"
  }
  if(evt.target)
  {
    evt.preventDefault();
    evt.stopPropagation();
   }
  else
  {
  	window.event.cancelBubble = true;
    window.event.returnValue = false;
    return false;
  }
}

SelectUtil.getDim = function(el){
for (var lx=0,ly=0;el!=null;
  lx+=el.offsetLeft,ly+=el.offsetTop,el=el.offsetParent);
return {x:lx,y:ly}
}

SelectUtil.hideOption = function(e)
{	
	ele = Platform.isIE ? window.event.srcElement : e.target;
	if(ele.className == "select_head") return;
  var allDiv = document.forms[0].getElementsByTagName("DIV")
  for(i = 0;i<allDiv.length;i++)
  {  	
      if(allDiv[i].className == "select_drop_show")  allDiv[i].className = "select_drop";
  }  
}

SelectUtil.clickOption = function(selfObject,pEle)
{
	$("sForm").elements[pEle + 'Id'].value = selfObject.getAttribute('value');
	$(pEle + "_head").innerHTML = selfObject.innerHTML
}

window.onload = function()
{
	document.body.onclick = SelectUtil.hideOption;
}