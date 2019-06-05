if (!Util)
	var Util = {};
if (!Drag)
	var Drag = {};
Drag.rootElement = null;
Drag.hide = function() {
	Drag.rootElement.style.display = "none"
};
Drag.show = function() {
	Drag.rootElement.style.display = ""
};
Util.getOffset = function(el, isLeft) {
	var retValue = 0;
	while (el != null) {
		retValue += el["offset" + (isLeft ? "Left" : "Top")];
		el = el.offsetParent
	}
	return retValue
};
Util.re_calcOff = function(el) {
	for ( var i = 0; i < Util.dragArray.length; i++) {
		var ele = Util.dragArray[i];
		ele.elm.pagePosLeft = Util.getOffset(ele.elm, true);
		ele.elm.pagePosTop = Util.getOffset(ele.elm, false)
	}
	var nextSib = el.elm.nextSibling;
	while (nextSib) {
		nextSib.pagePosTop -= el.elm.offsetHeight;
		nextSib = nextSib.nextSibling
	}
};
Drag.ghostElement = null;
Drag.getGhostElement = function() {
	if (!Drag.ghostElement) {
		Drag.ghostElement = document.createElement("DIV");
		Drag.ghostElement.className = "toolbox";
		Drag.ghostElement.backgroundColor = "";
		Drag.ghostElement.style.border = "2px dashed #aaa";
		Drag.ghostElement.innerHTML = " "
	}
	return Drag.ghostElement
};
Drag.draggable = function(el) {
	this._dragStart = Drag.start_Drag;
	this._drag = Drag.when_Drag;
	this._dragEnd = Drag.end_Drag;
	this._afterDrag = Drag.after_Drag;
	this.isDragging = false;
	this.elm = el;
	this.header = document.getElementById(el.id + "_h");
	this.hasIFrame = this.elm.getElementsByTagName("IFRAME").length > 0;
	if (this.header) {
		this.header.style.cursor = "move";
		Drag.init(this.header, this.elm);
		this.elm.onDragStart = Function.bindFunction(this, "_dragStart");
		this.elm.onDrag = Function.bindFunction(this, "_drag");
		this.elm.onDragEnd = Function.bindFunction(this, "_dragEnd")
	}
};
Drag.start_Drag = function() {
	Util.re_calcOff(this);
	this.origNextSibling = this.elm.nextSibling;
	var _ghostElement = Drag.getGhostElement();
	var offH = this.elm.offsetHeight;
	if (Platform.isGecko) {
		offH -= parseInt(_ghostElement.style.borderTopWidth) * 2
	}
	var offW = this.elm.offsetWidth - 4;
	var offLeft = Util.getOffset(this.elm, true);
	var offTop = Util.getOffset(this.elm, false);
	Drag.hide();
	this.elm.style.width = offW + 2 + "px";
	_ghostElement.style.height = offH - 4 + "px";
	this.elm.parentNode.insertBefore(_ghostElement, this.elm.nextSibling);
	this.elm.style.position = "absolute";
	this.elm.style.zIndex = 100;
	this.elm.style.left = offLeft + "px";
	this.elm.style.top = offTop + "px";
	Drag.show();
	this.isDragging = false;
	return false
};
Drag.when_Drag = function(clientX, clientY) {
	if (!this.isDragging) {
		this.elm.style.filter = "alpha(opacity=60)";
		this.elm.style.opacity = 0.6;
		this.isDragging = true
	}
	;
	var found = null;
	var max_distance = 100000000;
	for ( var i = 0; i < Util.dragArray.length; i++) {
		var ele = Util.dragArray[i];
		var distance = Math.sqrt(Math.pow(clientX - ele.elm.pagePosLeft, 2)
				+ Math.pow(clientY - ele.elm.pagePosTop, 2));
		if (ele == this) {
			continue
		}
		if (isNaN(distance)) {
			continue
		}
		if (distance < max_distance) {
			max_distance = distance;
			found = ele
		}
	}
	;
	var _ghostElement = Drag.getGhostElement();
	if (found != null && _ghostElement.nextSibling != found.elm) {
		found.elm.parentNode.insertBefore(_ghostElement, found.elm);
		if (Platform.isOpera) {
			document.body.style.display = "none";
			document.body.style.display = ""
		}
	}
};
Drag.end_Drag = function() {
	if (this._afterDrag()) {
		logger.log('Drag.end_Drag this = ' + this);
		logger.log('  this.elm = ' + this.elm + ', ' + this.elm.id);
		wid = this.elm.id;
		nextwid = this.elm.nextSibling.id;
		colid = this.elm.parentNode.id;
		if (wid != "") {
			wid = wid.substr(wid.indexOf("_") + 1)
		}
		if (nextwid != "") {
			nextwid = nextwid.substr(nextwid.indexOf("_") + 1)
		}
		if (colid != "") {
			colid = colid.substr(colid.indexOf("_") + 1)
		}
		Drag.dragComplete(colid, wid, nextwid)
	}
	return true
};
Drag.dragComplete = function(col, currentWidgitId, nextWidgitId) {
	var url = CommonUtil.getCurrentRootUrl()
			+ "manage/page.action?cmd=move_widget&widgetId=" + currentWidgitId
			+ "&col=" + col + "&wbi=" + nextWidgitId;
	new Ajax.Request(url, {
		method : 'get',
		onSuccess : function(xport) {
		},
		onException : function(xport, ex) {
		}
	})
};
Drag.after_Drag = function() {
	var returnValue = false;
	Drag.hide();
	this.elm.style.position = "";
	this.elm.style.width = "";
	this.elm.style.zIndex = "";
	this.elm.style.filter = "";
	this.elm.style.opacity = "";
	var ele = Drag.getGhostElement();
	if (ele.nextSibling != this.origNextSibling) {
		ele.parentNode.insertBefore(this.elm, ele.nextSibling);
		returnValue = true
	}
	ele.parentNode.removeChild(ele);
	Drag.show();
	if (Platform.isOpera) {
		document.body.style.display = "none";
		document.body.style.display = ""
	}
	return returnValue
};
Object.extend(Drag, {
	obj : null,
	init : function(elementHeader, element) {
		elementHeader.onmousedown = Drag.start;
		elementHeader.obj = element;
		if (isNaN(parseInt(element.style.left))) {
			element.style.left = "0px"
		}
		if (isNaN(parseInt(element.style.top))) {
			element.style.top = "0px"
		}
		element.onDragStart = new Function();
		element.onDragEnd = new Function();
		element.onDrag = new Function()
	},
	start : function(event) {
		var element = Drag.obj = this.obj;
		event = Drag.fixE(event);
		if (event.which != 1) {
			return true
		}
		element.onDragStart();
		element.lastMouseX = event.clientX;
		element.lastMouseY = event.clientY;
		document.onmouseup = Drag.end;
		document.onmousemove = Drag.drag;
		return false
	},
	drag : function(event) {
		event = Drag.fixE(event);
		if (event.which == 0) {
			return Drag.end()
		}
		var element = Drag.obj;
		var _clientX = event.clientY;
		var _clientY = event.clientX;
		if (element.lastMouseX == _clientY && element.lastMouseY == _clientX) {
			return false
		}
		var _lastX = parseInt(element.style.top);
		var _lastY = parseInt(element.style.left);
		var newX, newY;
		newX = _lastY + _clientY - element.lastMouseX;
		newY = _lastX + _clientX - element.lastMouseY;
		element.style.left = newX + "px";
		element.style.top = newY + "px";
		element.lastMouseX = _clientY;
		element.lastMouseY = _clientX;
		element.onDrag(newX, newY);
		return false
	},
	end : function(event) {
		event = Drag.fixE(event);
		document.onmousemove = null;
		document.onmouseup = null;
		var _onDragEndFuc = Drag.obj.onDragEnd();
		Drag.obj = null;
		return _onDragEndFuc
	},
	fixE : function(ig_) {
		if (typeof ig_ == "undefined") {
			ig_ = window.event
		}
		if (typeof ig_.layerX == "undefined") {
			ig_.layerX = ig_.offsetX
		}
		if (typeof ig_.layerY == "undefined") {
			ig_.layerY = ig_.offsetY
		}
		if (typeof ig_.which == "undefined") {
			ig_.which = ig_.button
		}
		return ig_
	}
});
Drag.chg = function(elm) {
	var inner = elm.parentNode.nextSibling;
	if (inner.style.display == "block") {
		inner.style.display = "none"
	} else {
		inner.style.display = "block"
	}
};
var _Drag_init = function(el) {
	Drag.rootElement = el;
	Util.column = Drag.getElementsByClassName(Drag.rootElement);
	Util.dragArray = new Array();
	var counter = 0;
	for ( var i = 0; i < Util.column.length; i++) {
		var ele = Util.column[i];
		for ( var j = 0; j < ele.childNodes.length; j++) {
			var ele1 = ele.childNodes[j];
			if (ele1.tagName == "DIV") {
				Util.dragArray[counter] = new Drag.draggable(ele1);
				counter++
			}
		}
	}
};
Drag.getElementsByClassName = function(root) {
	var arr = new Array();
	var elems = root.getElementsByTagName("td");
	for ( var i = 0; (elem = elems[i]); i++) {
		if (elem.className == "col") {
			arr[arr.length] = elem
		}
	}
	return arr
};