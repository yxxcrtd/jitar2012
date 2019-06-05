function SetRowColor(evt)
{
  var oCheckBox = Platform.isIE ? window.event.srcElement : evt.target;
  var oTr = oCheckBox;
  while(oTr.tagName && oTr.tagName != "TR")
  oTr = oTr.parentNode;
  if(oTr)
  {  
	oTr.style.backgroundColor=oCheckBox.checked?'#e6f4b2':'#FFFFFF';
  }  
}

function SetRowColorByName()
{
  var oCheckBoxs = document.getElementsByName('guid');
  for(i=0;i<oCheckBoxs.length;i++)
  {
     var oTr = oCheckBoxs[i];
     while(oTr.tagName && oTr.tagName != "TR")
     oTr = oTr.parentNode;
     if(oTr)
	  {
		oTr.style.backgroundColor=oCheckBoxs[i].checked?'#e6f4b2':'#FFFFFF';
	  }  
  }  
}

function colorTable()
{
  var t = document.getElementById('listTable');
  for(var i = 1;i<t.rows.length;i++)
  {
  	addEventHandler(t.rows[i],"mouseover",Mouse_Over);
  	addEventHandler(t.rows[i],"mouseout",Mouse_Out);
  }  
}

function Mouse_Over(evt)
{
  var oTr = Platform.isIE ? window.event.srcElement : evt.target;
  while(oTr.tagName && oTr.tagName != "TR")
  oTr = oTr.parentNode;
  if(oTr)
  {
  	oTr.setAttribute('oldColor',oTr.style.backgroundColor);
  	oTr.style.backgroundColor='#dec8f1';
  }
}

function Mouse_Out(evt)
{
  var oTr = Platform.isIE ? window.event.srcElement : evt.target;
  while(oTr.tagName && oTr.tagName != "TR")
  oTr = oTr.parentNode;
  if(oTr)
  {
  	oTr.style.backgroundColor = oTr.getAttribute('oldColor');
  }
}

function addEventHandler(oTarget, sEventType, fnHandler) {
if (oTarget.addEventListener) {
	oTarget.addEventListener(sEventType, fnHandler, false);
} else if (oTarget.attachEvent) {
	oTarget.attachEvent("on" + sEventType, fnHandler);
	} else {
		oTarget["on" + sEventType] = fnHandler;
	}
};