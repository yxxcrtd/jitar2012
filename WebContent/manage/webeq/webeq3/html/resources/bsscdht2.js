//////////BSSCDHTML Section 2//////////
// RoboHELP� Dynamic HTML Effects Script
// Copyright � 1998-1999 Blue Sky Software Corporation.  All rights reserved.

// Version=3.53

// Warning:  Do not modify this file.  It is generated by RoboHELP� and changes will be overwritten.



function RemoveNavBar()
{
	// See if we are in a popup and if so remove the NavBar
	if (BSSCPopup_IsPopup()) {

		if (gbIE4) {
			var tempColl = document.all.tags("DIV");
			for (var iDiv = 0; iDiv < tempColl.length; iDiv++) {
				if (tempColl(iDiv).id == "NavBar") {
					tempColl(iDiv).style.visibility = gBsStyVisHide;
					if (gbIE5) {
						tempColl(iDiv).style.position = "absolute";
					}
					tempColl(iDiv).style.pixelTop = "-100px";
					break;
				}
			}
		} else if (gbNav4) {
			for (var iLayer = 0; iLayer < document.layers.length; iLayer++) {
				if (document.layers[iLayer].id == "NavBar") {
					document.layers[iLayer].visibility = gBsStyVisHide;
				}
			}
			if ((document.images.length > 0) && (document.images[0].src.indexOf('../resource/bsscnav1.gif') != -1)) {
				document.links[0].href = "javascript:void(null);";
			}
		}
	}
	
	return;
}



//////////////////////////////////////////////////////////////////////////////////////////////
//
//	Begin DHTML Popup Functions
//
//////////////////////////////////////////////////////////////////////////////////////////////
//variables used to isolate the browser type
var gBsDoc			= null;			
var gBsSty			= null;
var gBsHtm			= null;
var gBsStyVisShow	= null;
var gBsStyVisHide	= null;
var gBsClientWidth	= 640;
var gBsClientHeight = 480;
var gBsBrowser		= null;

//the browser information itself
function _BSPSBrowserItself()
{
	var agent  = navigator.userAgent.toLowerCase();
	this.major = parseInt(navigator.appVersion);
	this.minor = parseFloat(navigator.appVersion);
	this.ns    = ((agent.indexOf('mozilla') != -1) && ((agent.indexOf('spoofer') == -1) && (agent.indexOf('compatible') == -1)));
	this.ns2   = ((this.ns) && (this.major == 2));
	this.ns3   = ((this.ns) && (this.major == 3));
	this.ns4   = ((this.ns) && (this.major >= 4));
	this.ie	   = (agent.indexOf("msie") != -1);
	this.ie3   = ((this.ie) && (this.major == 2));
	this.ie4   = ((this.ie) && (this.major >= 4));
	this.op3   = (agent.indexOf("opera") != -1);

	if (this.ns4)
	{
		gBsDoc		= "document";
		gBsSty		= "";
		gBsHtm		= ".document";
		gBsStyVisShow	= "show";
		gBsStyVisHide	= "hide";

	}
	else if (this.ie4)
	{
		gBsDoc		 = "document.all";
		gBsSty 		= ".style";
		gBsHtm 		= "";
		gBsStyVisShow	= "visible";
		gBsStyVisHide	= "hidden";
	}
}

//Here is the browser type 
function _BSPSGetBrowserInfo()
{
	gBsBrowser	= new _BSPSBrowserItself();
}

//Get client size info
function _BSPSGetClientSize()
{
	if (gBsBrowser.ns4)
	{
		gBsClientWidth	= innerWidth;
		gBsClientHeight = innerHeight;

	}
	else if (gBsBrowser.ie4)
	{
		gBsClientWidth	= document.body.clientWidth;
		gBsClientHeight = document.body.clientHeight;
	}
}


var gstrPopupID = 'BSSCPopup';
var gstrPopupShadowID = 'BSSCPopupShadow';
var gstrPopupTopicID = 'BSSCPopupTopic';
var gstrPopupIFrameID = 'BSSCPopupIFrame';
var gstrPopupIFrameName = 'BSSCPopupIFrameName';

var gstrPopupSecondWindowName = 'BSSCPopup';

var	gPopupDiv = null;
var gPopupDivStyle = null;
var	gPopupShadow = null;
var	gPopupTopic = null;
var gPopupIFrame = null;
var gPopupIFrameStyle = null;
var gPopupWindow = null;
var gnPopupClickX = 0;
var gnPopupClickY = 0;

var gbPopupTimeoutExpired = false;

if (BSSCPopup_IsPopup()) {
	document.write("<base target=\"_parent\">");
}

function DHTMLPopupSupport()
{
	if ((gbIE4) && (!gbMac)) {
		return true;
	}
	return false;
}



function BSSCPopup_IsPopup()
{
	if (DHTMLPopupSupport() && (this.name == gstrPopupIFrameName)) {
		return true;
	} else if ((gbNav4 || gbIE4) && (this.name == gstrPopupID)) {
		return true;
	} else {
		return false;
	}
}

function _BSSCCreatePopupDiv()
{
	if (gPopupDiv == null) {
		if (DHTMLPopupSupport()) {
			document.write("<DIV ID='" + gstrPopupID + "' STYLE='position:absolute; width:0; height:0; top:-100; left:0; z-index:600; visibility:hidden;'>");
			document.write("<DIV ID='" + gstrPopupShadowID + "' STYLE=\"position:absolute;top:0; left:0;  width:0; height:0; background-color:#C0C0C0;\"></DIV>");
			document.write("<DIV ID='" + gstrPopupTopicID + "' STYLE=\"position:absolute;top:0; left:0;  width:0; height:0; background-color:#FFFFFF;border:1px #000000 outset;\">");
			document.write("<IFRAME ID='" + gstrPopupIFrameID + "' name='" + gstrPopupIFrameName + "' frameborder=0 scrolling=auto></IFRAME>");
			document.write("</DIV></DIV>");
			var tempColl = document.all.tags("DIV");
			for (var iDiv = 0; iDiv < tempColl.length; iDiv++) {
				if (tempColl(iDiv).id == gstrPopupID) {
					gPopupDiv = tempColl(iDiv);
				}
				if (tempColl(iDiv).id == gstrPopupShadowID) {
					gPopupShadow = tempColl(iDiv);
				}
				if (tempColl(iDiv).id == gstrPopupTopicID) {
					gPopupTopic = tempColl(iDiv);
				}
			}
			
			gPopupIFrame = eval("gPopupDiv.document.frames['" + gstrPopupIFrameName + "']");
			gPopupDivStyle = eval("gPopupDiv" + gBsSty);
			gPopupIFrameStyle = eval(gBsDoc + "['" + gstrPopupIFrameName + "']" + gBsSty);
		}
	}
}

function BSSCPopup_Timeout()
{
	if ((gPopupIFrame.document.readyState == "complete") &&
		(gPopupIFrame.document.body != null)) {
		BSSCPopup_TimeoutReal();
	} else {
		setTimeout("BSSCPopup_Timeout()", 100);
	}
}

function BSSCPopup_TimeoutReal()
{
	window.gbPopupTimeoutExpired = true;

	if (gPopupIFrame.document) {
		gPopupIFrame.document.body.onclick = BSSCPopupClicked;
	}
	document.onmousedown = BSSCPopupParentClicked;
}

function BSPSPopupTopicWinHelp(strURL)
{
	_BSSCPopup(strURL);
	return;
}

function _BSSCPopup(strURL)
{
	if (DHTMLPopupSupport()) {

		// If we are already in a popup, replace the contents
		if (BSSCPopup_IsPopup()) {

			location.href = strURL;
			parent.window.gbPopupTimeoutExpired = false;
			if (gbMac) {
				setTimeout("BSSCPopup_AfterLoad()", 400);
			} else {
				setTimeout("BSSCPopup_AfterLoad()", 100);
			}

		} else {

			// Load the requested URL into the IFRAME
			gPopupIFrame.location.href = strURL;
			window.gbPopupTimeoutExpired = false;
			if (gbMac) {
				setTimeout("BSSCPopup_AfterLoad()", 400);
			} else {
				setTimeout("BSSCPopup_AfterLoad()", 100);
			}
		}

	} else {

		if (window.name == gstrPopupSecondWindowName) {
			window.location = strURL;
		} else {

			BSSCHidePopupWindow();
			var nX = 0;
			var nY = 0;
			var nHeight = 300;
			var nWidth = 400;
			_BSPSGetClientSize();
			if (gBsBrowser.ns4) {
				nX = window.screenX + (window.outerWidth - window.innerWidth) + window.gnPopupClickX;
				nY = window.screenY + (window.outerHeight - window.innerHeight) + window.gnPopupClickY;
				if (nY + nHeight + 40 > screen.availHeight) {
					nY = screen.availHeight - nHeight - 40;
				}
				if (nX + nWidth + 40 > screen.availWidth) {
					nX = screen.availWidth - nWidth - 40;
				}
			} else {
				nX = window.gnPopupClickX;
				nY = window.gnPopupClickX;
			}

			// Launch a separate window
			var strParam = "titlebar=no,toolbar=no,status=no,location=no,menubar=no,resizable=yes,scrollbars=yes,";
			strParam += "height=" + nHeight + ",width=" + nWidth;
			strParam += ",screenX=" + nX + ",screenY=" + nY;
			window.gPopupWindow = window.open(strURL, gstrPopupSecondWindowName, strParam);
			if (gBsBrowser.ns4) {
				window.gPopupWindow.captureEvents(Event.CLICK | Event.BLUE);
				window.gPopupWindow.onclick = NonIEPopup_HandleClick;
				window.gPopupWindow.onblur = NonIEPopup_HandleBlur;
			}
			else if (gBsBrowser.ie4)
			{
				window.gPopupWindow.focus();
			}
		}
	}

	return;
}


function NonIEPopup_HandleBlur(e)
{
	window.gPopupWindow.focus();
}

function NonIEPopup_HandleClick(e)
{
	// Because navigator will give the event to the handler before the hyperlink, let's
	// first route the event to see if we are clicking on a Popup menu in a popup.
	document.routeEvent(e);

	// If a popup menu is active then don't do anything with the click
	if (window.gPopupWindow.gbInPopupMenu) {
		window.gPopupWindow.captureEvents(Event.CLICK);
		window.gPopupWindow.onclick = NonIEPopup_HandleClick;
		return false;
	}

	// Close the popup window
	if (e.target.href != null) {
		window.location.href = e.target.href;
		if (e.target.href.indexOf("BSSCPopup") == -1) {
			this.close();
		}
	} else {
		this.close();
	}
	return false;
}

function BSSCPopup_AfterLoad()
{
	if ((window.gPopupIFrame.document.readyState == "complete") &&
		(window.gPopupIFrame.document.body != null)) {
		BSSCPopup_ResizeAfterLoad();
	} else {
		setTimeout("BSSCPopup_AfterLoad()", 200);
	}
}


function BSSCPopup_ResizeAfterLoad()
{
	window.gPopupDivStyle.visibility = gBsStyVisHide;

	// Determine the width and height for the window
	var size = new BSSCSize(0, 0);
	BSSCGetContentSize(window.gPopupIFrame, size);
	var nWidth = size.x;
	var nHeight = size.y;
	_BSPSGetClientSize();
	if (nWidth > gBsClientWidth) {
		// Adjust the height by 1/3 of how much we are reducing the width
		var lfHeight = 1.0;
		lfHeight = (((nWidth / (gBsClientWidth - 20.0)) - 1.0) * 0.3333) + 1.0;
		lfHeight *= nHeight;
		nHeight = lfHeight;
		nWidth = gBsClientWidth - 20;
	}
	if (nHeight > gBsClientHeight * .67) {
		nHeight = gBsClientHeight / 2;
	}
	window.gPopupDivStyle.width = nWidth;
	window.gPopupDivStyle.height = nHeight;

	// Determine the position of the window
	var nClickX = window.gnPopupClickX;
	var nClickY = window.gnPopupClickY;
	var nTop = 0;
	var nLeft = 0;

	if (nClickY + nHeight + 20 < gBsClientHeight + document.body.scrollTop) {
		nTop = nClickY + 10;
	} else {
		nTop = (document.body.scrollTop + gBsClientHeight) - nHeight - 20;
	}
	if (nClickX + nWidth < gBsClientWidth + document.body.scrollLeft) {
		nLeft = nClickX;
	} else {
		nLeft = (document.body.scrollLeft + gBsClientWidth) - nWidth - 8;
	}
	if (nTop <0) nTop  = 1;
	if (nLeft<0) nLeft = 1;


	window.gPopupDivStyle.left = nLeft;
	window.gPopupDivStyle.top = nTop;

	// Set the location of the background blocks
	window.gPopupShadow.style.left = 6;
	window.gPopupShadow.style.top = 6;
	window.gPopupShadow.style.width = nWidth;
	window.gPopupShadow.style.height = nHeight;
	window.gPopupTopic.style.width = nWidth;
	window.gPopupTopic.style.height = nHeight;

	if (gbMac) {
		// Total hack on the iMac to get the IFrame to position properly
		window.gPopupIFrameStyle.pixelLeft = 100;
		window.gPopupIFrameStyle.pixelLeft = 0;
		// Explicitly call BSSCOnLoad because the Mac doesn't seem to do it
		window.gPopupIFrame.window.BSSCOnLoad();
	}
	window.gPopupIFrameStyle.width = nWidth;
	window.gPopupIFrameStyle.height = nHeight;

	window.gPopupDivStyle.visibility = gBsStyVisShow;

	setTimeout("BSSCPopup_Timeout();", 100);
	return false;
}


function	BSSCSize(x, y)
{
	this.x = x;
	this.y = y;
}

function BSSCGetContentSize(thisWindow, size)
{
	if (!((gBsBrowser.ie4) || (gBsBrowser.ns4)))
		return;

	if (gbMac) {
		size.x = 300;
		size.y = 300;
		return;
	}

	// Resize the width until it is wide enough to handle the content
	// The trick is to start wide and determine when the scrollHeight changes
	// because then we know a scrollbar is necessary. We can then go back
	// to the next widest size (for no scrollbar)
	size.x = 800;
	var y = 1;
	var x = 800;

	// This double resize causes the document to re-render (and we need it to)
	thisWindow.resizeTo(1, 1);
	thisWindow.resizeTo(1, 1);
	thisWindow.resizeTo(x, thisWindow.document.body.scrollHeight);
	thisWindow.resizeTo(x, thisWindow.document.body.scrollHeight);
	var miny = thisWindow.document.body.scrollHeight;
	size.y = miny;
//	alert('firstsizex :' + resizeInfo.RstX + "    firstsizey :" + resizeInfo.RstY);
	for (i = 7; i > 0; i--) {
		x = i * 100;
		thisWindow.resizeTo(x, miny);
		thisWindow.resizeTo(x, miny);
//		alert('testsizex :' + x + "    testsizey :" + miny);
		if ((thisWindow.document.body.scrollHeight > miny) ||
		    (thisWindow.document.body.scrollWidth > x)) {
			x = (i + 1) * 100;
			thisWindow.resizeTo(x, y);
			thisWindow.resizeTo(x, y);
			size.x = thisWindow.document.body.scrollWidth + 20;
			size.y = thisWindow.document.body.scrollHeight + 20;
			break;
		}
	}

	// Handle absurd cases just in case IE flakes
	if (size.y < 100) {
		size.y = 100;
	}
//	alert('sizex :' + size.x + "    sizey :" + size.y);
	thisWindow.resizeTo(size.x, size.y);
	thisWindow.resizeTo(size.x, size.y);

	return;
}



function BSSCPopupParentClicked()
{
	BSSCPopupClicked();
	return;
}


function BSSCPopupClicked()
{
	if (!window.gbPopupTimeoutExpired) {
		return false;
	}

	if (gPopupIFrame.window.gbInPopupMenu) {
		return false;
	}

	// Give the user a message about javascript calls through objects.
	if ((gPopupIFrame.window.event != null) &&
	    (gPopupIFrame.window.event.srcElement != null) &&
	    (gPopupIFrame.window.event.srcElement.tagName == "A") &&
	    (gPopupIFrame.window.event.srcElement.href.indexOf("javascript:") == 0) &&
	    (gPopupIFrame.window.event.srcElement.href.indexOf(".") != -1)) {
		gPopupIFrame.window.event.cancelBubble = true;
		alert('Hyperlinks to objects do not work in popups.');
		return false;
	}

	if (gPopupIFrame.document) {
		gPopupIFrame.document.body.onclick = null;
	}
	document.onclick = null;
	document.onmousedown = null;

	// Simply hide the popup
	gPopupDivStyle.visibility = gBsStyVisHide;

	return true;
}


//trace the mouse over's position for hotspot
function  BSPSPopupOnMouseOver(event)
{
	if (gBsBrowser.ie4) {
		window.gnPopupClickX = event.clientX + document.body.scrollLeft;
		window.gnPopupClickY = event.clientY + document.body.scrollTop;
	} else if (gBsBrowser.ns4) {
		window.gnPopupClickX = event.pageX;
		window.gnPopupClickY = event.pageY;
	}
}

function BSSCHidePopupWindow()
{
	if (window.gPopupWindow != null) {
		if (gBsBrowser.ns4) {
			if ((typeof window.gPopupWindow != "undefined") && (!window.gPopupWindow.closed)) {
				window.gPopupWindow.close();
				window.gPopupWindow = null;
			}
		}
	}

	return;
}

var gbPopupMenuTimeoutExpired = false;
var gbInPopupMenu = false;
var gbPopupMenuTopicList = null;

//////////////////////////////////////////////////////////////////////////////////////////
//
// Popup Menu code
//
//////////////////////////////////////////////////////////////////////////////////////////


function _WritePopupMenuLayer()
{
	document.write("<DIV ID='PopupMenu' STYLE='position:absolute; left:0px; top:0px; z-index:4; visibility:hidden;'></DIV>");
	document.write("<STYLE TYPE='text/css'>");
	if (gbNav4) {
//		document.write(".PopupOver {color:white; background:navy; font-size:9pt; text-decoration:none;}");
//		document.write(".PopupNotOver {color:black; background:#c0c0c0; font-size:9pt; text-decoration:none;}");
	} else if (gbIE4) {
		if (gbMac) {
			document.write(".PopupOver {font-family:'Arial'; color:white; background:navy; font-size:10pt; text-decoration:none;}");
			document.write(".PopupNotOver {font-family:'Arial'; color:black; background:#c0c0c0; font-size:10pt; text-decoration:none;}");
		} else {
			document.write(".PopupOver {font-family:'Arial'; color:white; background:navy; font-size:8pt; text-decoration:none;}");
			document.write(".PopupNotOver {font-family:'Arial'; color:black; background:#c0c0c0; font-size:8pt; text-decoration:none;}");
		}
	}
	document.write("</STYLE>");
}

//Define variable arguments as: strTitle, strUrl
function PopupMenuTopicEntry() 
{
	this.strTitle = PopupMenuTopicEntry.arguments[0];
	this.strURL = PopupMenuTopicEntry.arguments[1];
}

// If the topic list is set, it is an array of TopicEntry objects (defined in WebHelp3.js)
function PopupMenu_SetTopicList(aPopupTopicArray)
{
	gbPopupMenuTopicList = aPopupTopicArray;
}

function _PopupMenu_Invoke(fn_arguments)
{
	if ((!gbIE4 && !gbNav4) || ((gbMac) && (gbIE4) && (window.event.srcElement.tagName == "AREA"))) {
	
		var argLen 	= fn_arguments.length;

		// Create the window that the hyperlinks will go into
		var nHeight = argLen * 15;
		var nWidth = 400;
		var strParam = "titlebar=no,toolbar=no,status=no,location=no,menubar=no,resizable=yes,scrollbars=auto";
		strParam += ",height=" + nHeight + ",width=200";
		strParam += ",resizable";

		// Create a temporary window first to ensure the real popup comes up on top
		var wndTemp = window.open("", "temp", strParam);

		// Create the real popup window
		var wndPopupLinks = window.open("", "popuplinks", strParam);

		// Close the temporary
		wndTemp.close();

		wndPopupLinks.document.open("text/html");
		wndPopupLinks.document.write("<html><head></head>");
		wndPopupLinks.document.write("<body onBlur=\'self.focus();\'>");
		var strParaLine = "";
		for (var i = 0; i < (argLen - 2) / 2; i++) {
			strParaLine = "";
			strParaLine += "<a href=\"javascript:";
			if (gbIE) {
				strParaLine += "onBlur=null; ";
			}
			strParaLine += "opener.location=\'";
			strParaLine += fn_arguments[2 * i + 3];
			strParaLine += "\';close();\"";
			if (fn_arguments[1] != '') {
				strParaLine += " TARGET='" + fn_arguments[1] + "'";
			}
			strParaLine += ">";
			strParaLine += fn_arguments[2 * i + 2];
			strParaLine += "</a>";
			strParaLine += "<br>";
			wndPopupLinks.document.write(strParaLine);
		}
		wndPopupLinks.document.write("</body></html>");
		wndPopupLinks.document.close();
		window.gbInPopupMenu = true;
		if (!gbIE) {
			wndPopupLinks.focus();
		}

		return false;
	}

	// Make sure we have reasonable arguments
	var argLen = fn_arguments.length;
	if (argLen < 3) {
		return false;
	}

	// Check to see if we only have one target
	var strTarget = "";
	if (((argLen < 5) && (isNaN(fn_arguments[2]))) ||
		((argLen < 4) && (!isNaN(fn_arguments[2])))) {

		// Get the place that we will be putting the topic into
		var targetDoc = null;
		if (fn_arguments[1] == '') {
			targetDoc = window.document;
		} else {
			if (gbIE4) {
				targetDoc = eval("top.document.frames['" + fn_arguments[1] + "']");
			} else if (gbNav4) {
				targetDoc = eval("window.top." + fn_arguments[1] + ".document");
			}
			strTarget = "TARGET='" + fn_arguments[1] + "'";
		}
		if (isNaN(fn_arguments[2])) {
			targetDoc.location.href = fn_arguments[3];
		} else {
			targetDoc.location.href = gbPopupMenuTopicList[fn_arguments[2]].strURL;
		}
		return false;
	}
	
	var strMenu = "";
	if (gbNav4) {
		strMenu = '<TABLE BORDER="1" CELLSPACING=0 CELLPADDING=3 BGCOLOR="#c0c0c0">';
	} else {
		strMenu = '<TABLE STYLE="border:2px outset white;" CELLSPACING=0';
		if (gbMac) {
			strMenu += ' CELLPADDING=4';
		} else {
			strMenu += ' CELLPADDING=2';
		}	
		strMenu += ' BGCOLOR=#c0c0c0>';
	}

	// Add each of the items
	var i = 2;
	while (i <= argLen - 1) {
		strMenu += '<TR><TD><NOBR>'
		// If the destination is a number then look it up in the topic list
		if (isNaN(fn_arguments[i])) {
			strMenu += '<DIV STYLE="padding-left:3pt; padding-right:3pt;"><A HREF="' + fn_arguments[i + 1] + '"' + strTarget;
		} else {
			strMenu += '<DIV STYLE="padding-left:3pt; padding-right:3pt;"><A HREF="' + gbPopupMenuTopicList[fn_arguments[i]].strURL + '"' + strTarget;
		}
		strMenu += ' onclick="PopupMenu_HandleClick(event);"';
		strMenu += ' onmouseover="PopupMenu_Over(event);"';
		strMenu += ' onmouseout="PopupMenu_Out(event);"';
		strMenu += '>';
		if (isNaN(fn_arguments[i])) {
			strMenu += '<SPAN CLASS="PopupNotOver">' + fn_arguments[i] + '</SPAN>';
		} else {
			strMenu += '<SPAN CLASS="PopupNotOver">' + gbPopupMenuTopicList[fn_arguments[i]].strTitle + '</SPAN>';
		}
		strMenu += '</A></DIV></NOBR></TD></TR>';

		if (isNaN(fn_arguments[i])) {
			i += 2;
		} else {
			i += 1;
		}
	}
	strMenu += "</TABLE>";

	var layerPopup = null;
	var stylePopup = null;
	var nEventX = 0;
	var nEventY = 0;
	var nWindowWidth = 0;
	if (gbIE4) {

		layerPopup = document.all["PopupMenu"];
		layerPopup.innerHTML = strMenu;
		stylePopup = layerPopup.style;

		_BSPSGetClientSize();

		// Get the position of the item causing the event (relative to its parent)
		if (gbMac) {
			nEventX = window.event.clientX;
			nEventY = window.event.clientY;
		} else {
			nEventX = window.event.srcElement.offsetLeft - document.body.scrollLeft;
			nEventY = window.event.srcElement.offsetTop - document.body.scrollTop;

			// Get the location of the parent
			var nParentLocX = 0;
			var nParentLocY = 0;

			var ParentItem = window.event.srcElement.offsetParent;
			while (ParentItem != null) {
				if (ParentItem.offsetLeft)	{
					nParentLocX += ParentItem.offsetLeft;
					nParentLocY += ParentItem.offsetTop;
				}
				ParentItem = ParentItem.parentElement;
			}

			// Adjust the location of the item using the location of the parent(s)
			nEventX += nParentLocX;
			nEventY += nParentLocY;
		}

		if (nEventY + layerPopup.scrollHeight + 10 < gBsClientHeight) {
			nEventY += document.body.scrollTop + 10;
		} else {
			nEventY = (document.body.scrollTop + gBsClientHeight) - layerPopup.scrollHeight - 20;
		}
		stylePopup.top = nEventY;

		if (nEventX + layerPopup.scrollWidth + 20 > gBsClientWidth) {
			if (gBsClientWidth - layerPopup.scrollWidth < 5) {
				stylePopup.left = 5;
			} else {
				stylePopup.left = gBsClientWidth - layerPopup.scrollWidth - 5;
			}
		} else {
			stylePopup.left = nEventX + document.body.scrollLeft + 20;
		}

		stylePopup.visibility = "visible";
		document.onclick = PopupMenu_HandleClick;
	} else if (gbNav4) {
		layerPopup = document.layers.PopupMenu;
		layerPopup.visibility = "hide";
		stylePopup = layerPopup.document;
		stylePopup.write(strMenu);
		stylePopup.close();
		var e = fn_arguments[0];
		nEventX = e.pageX;
		nEventY = e.pageY;
		_BSPSGetClientSize();
		if (nEventY + layerPopup.clip.height + 20 < window.pageYOffset + gBsClientHeight) {
			nEventY += 20;
		} else {
			nEventY = gBsClientHeight + window.pageYOffset- layerPopup.clip.height - 20;
		}
		layerPopup.top = nEventY;

		if (nEventX + layerPopup.clip.width + 20 > gBsClientWidth + window.pageXOffset) {
			if (gBsClientWidth + window.pageXOffset - layerPopup.clip.width < 20) {
				nEventX = 5;
			} else {
				nEventX = gBsClientWidth + window.pageXOffset - layerPopup.clip.width - 20;
			}
		} else {
			nEventX += 20;
		}

		layerPopup.left = nEventX;

		layerPopup.visibility = "show";

//		window.captureEvents(Event.CLICK | Event.MOUSEDOWN);
		window.captureEvents(Event.MOUSEDOWN);
//		window.onclick = PopupMenu_HandleClick;
		window.onmousedown = PopupMenu_HandleClick;
	}

	window.gbInPopupMenu = true;
	window.gbPopupMenuTimeoutExpired = false;
	setTimeout("PopupMenu_Timeout();", 100);
	return false;
}


function PopupMenu_Timeout()
{
	window.gbPopupMenuTimeoutExpired = true;
}

function PopupMenu_Over(e)
{
    if (gbIE4) {
		e.srcElement.className = "PopupOver";
    } else if (gbNav4) {
//		this.bgColor = "red";
//        e.target.document.className = "PopupOver";
    }
	return;
}

function PopupMenu_Out(e)
{
    if (gbIE4) {
		e.srcElement.className = "PopupNotOver";
    } else if (gbNav4) {
        this.bgColor = "#f0f0f0";
    }
	return;
}


function PopupMenu_HandleClick(e)
{
	if (!window.gbPopupMenuTimeoutExpired) {
		return;
	}

	window.gbInPopupMenu = false;

	if (gbNav4) {
//		window.releaseEvents(Event.CLICK);
		window.releaseEvents(Event.MOUSEDOWN);
	}

	var layerPopup = null;
	var stylePopup = null;
	if (gbIE4) {
		layerPopup = document.all["PopupMenu"];
		stylePopup = layerPopup.style;
		stylePopup.visibility = "hidden";
	} else if (gbNav4) {
		layerPopup = document.layers.PopupMenu;
		layerPopup.visibility = "hide";
	}

	return;
}

// This function should be deleted when all old projects are cleaned up
function BSPSWritePopupFrameForIE4()
{
	return false;
}

/////////////////////////////////////////////////////////////////////
function BSSCPopup_ClickMac()
{
	if ((!DHTMLPopupSupport()) && (gbIE4))
	{	
		var bClickOnAnchor = false;
		var el;
		if ((window.event != null) &&
		    (window.event.srcElement != null))
		{
		    el = window.event.srcElement;
			while (el != null)
			{
				if ((el.tagName == "A") || (el.tagName == "AREA")) 	{
					bClickOnAnchor = true;
					break;
				}
				if (el.tagName == "BODY") {
					break;
				}
				el = el.parentElement;
			}
		}
		if (BSSCPopup_IsPopup())
		{
			if (!bClickOnAnchor) {
				parent.window.gPopupWindow = null;
				self.close();
			}
		}
		else
		{
			bClosePopupWindow = true;
			if ((bClickOnAnchor) &&
				(el.href) &&
			    (el.href.indexOf("javascript:BSSCPopup") != -1))
			{
				bClosePopupWindow = false;
			}
			if (bClosePopupWindow)
			{
				if (window.gPopupWindow != null)
				{
					var strParam = "titlebar=no,toolbar=no,status=no,location=no,menubar=no,resizable=yes,scrollbars=yes,height=300,width=400";
					window.gPopupWindow = window.open("", gstrPopupSecondWindowName,strParam);
					window.gPopupWindow.close();
					window.gPopupWindow = null;
				}
			}
		}
	}
 }

//////////////////////////////////////////////////////////////////////

_BSPSGetBrowserInfo();

function _BSSCOnLoad()
{
	if (!gbIE4 && !gbNav4)
		return;

	// Make everything visible in navigator
	if (gbNav4) {
		// Make some special effects items visible
		for (var iLayer = 0; iLayer < document.layers.length; iLayer++) {
			document.layers[iLayer].visibility = gBsStyVisShow;
			document.layers[iLayer].left = 0;
		}
	}

	// Remove the NavBar if necessary
	RemoveNavBar();
		
	// Don't continue without IE4
	if (gbIE4) {
		HHActivateComponents();
		doStaticEffects();
		startAnimationSet(0);
	}
}

function _BSSCOnUnload()
{
}

function _BSSCOnClick()
{
	if (!gbIE4)
		return;

	BSSCPopup_ClickMac();
	startNextAnimationSet();
}
