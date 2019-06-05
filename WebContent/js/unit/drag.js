// Prototype 定义.
var Prototype = {
  Version: '4.0',
  emptyFunction: function() {}
};

// 对象扩展属性 (Ajax 定义使用).
Object.extend = function(destination, source) {
  for (property in source) {
    destination[property] = source[property];
  };
  return destination;
};

var $ = function(id) {return document.getElementById(id);};

// 函数 $A() 定义.
var $A = Array.from = function(iterable) {
  if (!iterable) return [];
  if (iterable.toArray) {
    return iterable.toArray();
  } else {
    var results = [];
    for (var i = 0; i < iterable.length; i++)
      results.push(iterable[i]);
    return results;
  }
};


// 定义 Platform 全局容器，用于借鉴各个 js 库的代码.
if (typeof Platform == 'undefined') var Platform = {};

// 通用函数, 从 mootools 中借鉴过来.
Platform.$extend = function () {
  var args = arguments;
  if (!args[1]) args=[this,args[0]];
  for(var property in args[1])
    args[0][property] = args[1][property];
  return args[0];
};

// 浏览器类型获取.
Platform.getUserAgent = navigator.userAgent;
Platform.isGecko = Platform.getUserAgent.indexOf("Gecko") != -1;
Platform.isOpera = Platform.getUserAgent.indexOf("Opera") != -1;
Platform.isIE = Platform.getUserAgent.indexOf("MSIE") != -1;


/**
 * 返回绑定了 this=el el[fucName]函数 的函数. 
 */
Function.bindFunction = function (el, fucName) {
     return function () {
         return el[fucName].apply(el, arguments);
     };
 };
 
// Function bind() 扩展.
Function.prototype.bind = function() {
  var __method = this, args = $A(arguments), object = args.shift();
  return function() {
    return __method.apply(object, args.concat($A(arguments)));
  };
};


// 类创建 (Ajax 定义使用).
var Class = {
  create: function() {
    return function() {
      this.initialize.apply(this, arguments);
    };
  }
};

if (!this.JSON) {

    JSON = function () {

        function f(n) {    // Format integers to have at least two digits.
            return n < 10 ? '0' + n : n;
        }

        Date.prototype.toJSON = function () {

            return this.getUTCFullYear()   + '-' +
                 f(this.getUTCMonth() + 1) + '-' +
                 f(this.getUTCDate())      + 'T' +
                 f(this.getUTCHours())     + ':' +
                 f(this.getUTCMinutes())   + ':' +
                 f(this.getUTCSeconds())   + 'Z';
        };


        var m = {    // table of character substitutions
            '\b': '\\b',
            '\t': '\\t',
            '\n': '\\n',
            '\f': '\\f',
            '\r': '\\r',
            '"' : '\\"',
            '\\': '\\\\'
        };

        function stringify(value, whitelist) {
            var a,          // The array holding the partial texts.
                i,          // The loop counter.
                k,          // The member key.
                l,          // Length.
                r = /["\\\x00-\x1f\x7f-\x9f]/g,
                v;          // The member value.

            switch (typeof value) {
            case 'string':
                return r.test(value) ?
                    '"' + value.replace(r, function (a) {
                        var c = m[a];
                        if (c) {
                            return c;
                        }
                        c = a.charCodeAt();
                        return '\\u00' + Math.floor(c / 16).toString(16) +
                                                   (c % 16).toString(16);
                    }) + '"' :
                    '"' + value + '"';

            case 'number':
                return isFinite(value) ? String(value) : 'null';
            case 'boolean':
            case 'null':
                return String(value);
            case 'object':
                if (!value) {
                    return 'null';
                }
                if (typeof value.toJSON === 'function') {
                    return stringify(value.toJSON());
                }
                a = [];
                if (typeof value.length === 'number' &&
                        !(value.propertyIsEnumerable('length'))) {
                    l = value.length;
                    for (i = 0; i < l; i += 1) {
                        a.push(stringify(value[i], whitelist) || 'null');
                    }
                    return '[' + a.join(',') + ']';
                }
                if (whitelist) {

                    l = whitelist.length;
                    for (i = 0; i < l; i += 1) {
                        k = whitelist[i];
                        if (typeof k === 'string') {
                            v = stringify(value[k], whitelist);
                            if (v) {
                                a.push(stringify(k) + ':' + v);
                            }
                        }
                    }
                } else {

                    for (k in value) {
                        if (typeof k === 'string') {
                            v = stringify(value[k], whitelist);
                            if (v) {
                                a.push(stringify(k) + ':' + v);
                            }
                        }
                    }
                }

                return '{' + a.join(',') + '}';
            }
        }

        return {
            stringify: stringify,
            parse: function (text, filter) {
                var j;

                function walk(k, v) {
                    var i, n;
                    if (v && typeof v === 'object') {
                        for (i in v) {
                            if (Object.prototype.hasOwnProperty.apply(v, [i])) {
                                n = walk(i, v[i]);
                                if (n !== undefined) {
                                    v[i] = n;
                                } else {
                                    delete v[i];
                                }
                            }
                        }
                    }
                    return filter(k, v);
                }


                if (/^[\],:{}\s]*$/.test(text.replace(/\\./g, '@').
replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']').
replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {
                    j = eval('(' + text + ')');
                    return typeof filter === 'function' ? walk('', j) : j;
                }

                throw new SyntaxError('parseJSON');
            }
        };
    }();
}



// Try.these (Ajax 使用).
var Try = {
  these: function() {
    var returnValue;

    for (var i = 0; i < arguments.length; i++) {
      var lambda = arguments[i];
      try {
        returnValue = lambda();
        break;
      } catch (e) {}
    }

    return returnValue;
  }
};

// Ajax 对象定义.
var Ajax = {
  getTransport: function() {
    return Try.these(
      function() {return new XMLHttpRequest()},
      function() {return new ActiveXObject('Msxml2.XMLHTTP')},
      function() {return new ActiveXObject('Microsoft.XMLHTTP')}
    ) || false;
  },

  activeRequestCount: 0
};

// class Ajax.Base.
Ajax.Base = function(){};
Ajax.Base.prototype = {
  setOptions: function(options) {
    this.options = {
      method:       'post',
      asynchronous: true,
      parameters:   ''
    };
    Object.extend(this.options, options || {});
  },

  responseIsSuccess: function() {
    return this.transport.status == undefined
        || this.transport.status == 0
        || (this.transport.status >= 200 && this.transport.status < 300);
  },

  responseIsFailure: function() {
    return !this.responseIsSuccess();
  }
};

// Ajax 请求对象.
Ajax.Request = Class.create();
Ajax.Request.Events =['Uninitialized', 'Loading', 'Loaded', 'Interactive', 'Complete'];

Ajax.Request.prototype = Object.extend(new Ajax.Base(), {
  initialize: function(url, options) {
    this.transport = Ajax.getTransport();
    this.setOptions(options);
    this.request(url);
  },

  request: function(url) {
    var parameters = this.options.parameters || '';
    if (parameters.length > 0) parameters += '&_=';

    try {
      this.url = url;
      if (this.options.method == 'get' && parameters.length > 0)
        this.url += (this.url.match(/\?/) ? '&' : '?') + parameters;

      /// Ajax.Responders.dispatch('onCreate', this, this.transport);

      this.transport.open(this.options.method, this.url,this.options.asynchronous);

      if (this.options.asynchronous) {
        this.transport.onreadystatechange = this.onStateChange.bind(this);
        setTimeout((function() {this.respondToReadyState(1)}).bind(this), 10);
      }

      this.setRequestHeaders();

      var body = this.options.postBody ? this.options.postBody : parameters;
      this.transport.send(this.options.method == 'post' ? body : null);

    } catch (e) {
      this.dispatchException(e);
    }
  },

  setRequestHeaders: function() {
    var requestHeaders =['X-Requested-With', 'XMLHttpRequest','X-Prototype-Version', Prototype.Version];

    if (this.options.method == 'post') {
      requestHeaders.push('Content-type','application/x-www-form-urlencoded');

      /* Force "Connection: close" for Mozilla browsers to work around
       * a bug where XMLHttpReqeuest sends an incorrect Content-length
       * header. See Mozilla Bugzilla #246651.
       */
      if (this.transport.overrideMimeType)
        requestHeaders.push('Connection', 'close');
    }

    if (this.options.requestHeaders)
      requestHeaders.push.apply(requestHeaders, this.options.requestHeaders);

    for (var i = 0; i < requestHeaders.length; i += 2)
      this.transport.setRequestHeader(requestHeaders[i], requestHeaders[i+1]);
  },

  onStateChange: function() {
 
    var readyState = this.transport.readyState;
    if (readyState != 1)
      this.respondToReadyState(this.transport.readyState);
  },

  header: function(name) {
    try {
      return this.transport.getResponseHeader(name);
    } catch (e) {}
  },

  evalJSON: function() {
    try {
      return eval(this.header('X-JSON'));
    } catch (e) {}
  },

  evalResponse: function() {
    try {
      return eval(this.transport.responseText);
    } catch (e) {
      this.dispatchException(e);
    }
  },

  respondToReadyState: function(readyState) {
    var event = Ajax.Request.Events[readyState];
    var transport = this.transport, json = this.evalJSON();

    if (event == 'Complete') {
      try {
        (this.options['on' + this.transport.status]
         || this.options['on' + (this.responseIsSuccess() ? 'Success' : 'Failure')]
         || Prototype.emptyFunction)(transport, json);
      } catch (e) {
        this.dispatchException(e);
      }

      if ((this.header('Content-type') || '').match(/^text\/javascript/i))
        this.evalResponse();
    }

    try {
      (this.options['on' + event] || Prototype.emptyFunction)(transport, json);
      ///Ajax.Responders.dispatch('on' + event, this, transport, json);
    } catch (e) {
      this.dispatchException(e);
    }

    /* Avoid memory leak in MSIE: clean up the oncomplete event handler */
    if (event == 'Complete')
      this.transport.onreadystatechange = Prototype.emptyFunction;
  },

  dispatchException: function(exception) {
    (this.options.onException || Prototype.emptyFunction)(this, exception);
    ///Ajax.Responders.dispatch('onException', this, exception);
  }
});
 


var logger = {
	log : function(msg) {if (window.console) window.console.log(msg);},
	warn : function(msg) {if (window.console) window.console.warn(msg);},
	error : function(msg) {if (window.console) window.console.error(msg);}
	};
	
logger.log('日志记录器开始创建...');

if (!Util) var Util = {};
if (!Drag) var Drag = {};
Drag.rootElement = null;
Drag.hide = function () {
  Drag.rootElement.style.display = "none";
};
 
Drag.show = function () {
  Drag.rootElement.style.display = "";
};
 

/**
 * 获取元素的针对浏览器文档的偏移, isLeft == true 得到 offsetLeft, 
 *   == false 得到 offsetTop 
 */
Util.getOffset = function (el, isLeft) {
  var retValue = 0;
  while (el != null) {
    retValue += el["offset" + (isLeft ? "Left" : "Top")];
    el = el.offsetParent;
  }
  return retValue;
};

/**
 * 重新计算偏移 
 */ 
Util.re_calcOff = function (el) {
  for (var i = 0; i < Util.dragArray.length; i++) {
		var ele = Util.dragArray[i];
		ele.elm.pagePosLeft = Util.getOffset(ele.elm, true);
		ele.elm.pagePosTop = Util.getOffset(ele.elm, false);
  }
  var nextSib = el.elm.nextSibling;
  while (nextSib) {
    nextSib.pagePosTop -= el.elm.offsetHeight;
    nextSib = nextSib.nextSibling;
  }
};

// 拖放的阴影框.
Drag.ghostElement = null;

/**
 * 得到或创建拖放的阴影框.
 */
Drag.getGhostElement = function () {
  if (!Drag.ghostElement) {
    Drag.ghostElement = document.createElement("DIV");
    Drag.ghostElement.className = "toolbox";
    Drag.ghostElement.backgroundColor = "";
    Drag.ghostElement.style.border = "2px dashed #aaa";
    Drag.ghostElement.innerHTML = " ";
  }
  return Drag.ghostElement;
};

/**
 * 
 */ 
Drag.draggable = function (el) {
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
    this.elm.onDragEnd = Function.bindFunction(this, "_dragEnd");
  }
};


Drag.start_Drag = function () {
  Util.re_calcOff(this);
  this.origNextSibling = this.elm.nextSibling;
  var _ghostElement = Drag.getGhostElement();
  var offH = this.elm.offsetHeight;
  if (Platform.isGecko) {
    offH -= parseInt(_ghostElement.style.borderTopWidth) * 2;
  }
  var offW = this.elm.offsetWidth-4;
  var offLeft = Util.getOffset(this.elm, true);
  var offTop = Util.getOffset(this.elm, false);
  Drag.hide();
  this.elm.style.width = offW+2 + "px";
  _ghostElement.style.height = offH-4 + "px";
  this.elm.parentNode.insertBefore(_ghostElement, this.elm.nextSibling);
  this.elm.style.position = "absolute";
  this.elm.style.zIndex = 100;
  this.elm.style.left = offLeft + "px";
  this.elm.style.top = offTop + "px";
	Drag.show();
  this.isDragging = false;
  return false;
};
 

Drag.when_Drag = function(clientX, clientY) {
  if (!this.isDragging) {
    this.elm.style.filter = "alpha(opacity=60)";
    this.elm.style.opacity = 0.6;
    this.isDragging = true;
  };
  var found = null;
  var max_distance = 100000000;
  for (var i = 0; i < Util.dragArray.length; i++) {
    var ele = Util.dragArray[i];         
    //dragArray 数组保存了拖动的扩展对象,ele.elm 是 HTML DOM 对象本身
    var distance = Math.sqrt(Math.pow(clientX - ele.elm.pagePosLeft, 2) + Math.pow(clientY - ele.elm.pagePosTop, 2));
    if (ele == this) {
      //writeLog(" this.pagePosLeft = " + this.elm.pagePosLeft + " : this.pagePosTop = " + this.elm.pagePosTop)         
      continue;
    }
    if (isNaN(distance)) {
      continue;
    }
    if (distance < max_distance) {
      max_distance = distance;
      found = ele;
    }
  };
  
  var _ghostElement = Drag.getGhostElement();
  if(found != null && _ghostElement.nextSibling != found.elm) {
    found.elm.parentNode.insertBefore(_ghostElement, found.elm);
    if (Platform.isOpera) {
      document.body.style.display = "none";
      document.body.style.display = "";
    }
  }
};

Drag.end_Drag = function() {
  if (this._afterDrag()) {
  
    wid = this.elm.id;
    nextwid = this.elm.nextSibling.id;
    colid = this.elm.parentNode.id;
    
    logger.log('测试位置1：col = ' + colid + ", currentWidgitId =" + wid + ", nextWidgitId = " + nextwid );
    
    if(wid != "")
    {
    	wid = wid.substr(wid.indexOf("_") + 1);
    }
    
    
    if(nextwid != "")
    {
    	nextwid = nextwid.substr(nextwid.indexOf("_") + 1);    	
    }
   	if('header_zone' == colid)
   	 colid = 1;
   	else if('left_zone' == colid)
   	 colid = 3;
   	else if('middle_zone' == colid)
   	 colid = 4;
   	else if('bottom_zone' == colid)
   	 colid = 2;
   	else
   	 colid = 5;  
  
    Drag.dragComplete(colid, wid, nextwid);
  }
  return true;
};
 
Drag.dragComplete = function(col,currentWidgitId,nextWidgitId)
{	
	  logger.log('提交：col = ' + col + ", currentWidgitId =" + currentWidgitId + ", nextWidgitId = " + nextWidgitId );
	  var url = UnitUrl + "manage/unitWebpartAction.action?cmd=move&widgetId="+ currentWidgitId + "&col=" + col + "&wbi=" + nextWidgitId ;
	  logger.log('url = ' + url);
	  new Ajax.Request(url, { 
	      method: 'get',
	      onSuccess: function(xport) { },
	      onException: function(xport, ex) { }
	    }
	  );
};

/**
 * 
 */
Drag.after_Drag = function() {
  var returnValue = false;
  Drag.hide();
  this.elm.style.position = "";
  this.elm.style.width = "";
  this.elm.style.zIndex = "";
  this.elm.style.filter = "";
  this.elm.style.opacity = "";
  var ele = Drag.getGhostElement();
  
  //logger.log("this.elm = " + this.elm.innerHTML  + "ele.nextSibling = " + ele.nextSibling.innerHTML) 
 
  
  if (ele.nextSibling != this.origNextSibling) {
    ele.parentNode.insertBefore(this.elm, ele.nextSibling);
    returnValue = true;
  }
  ele.parentNode.removeChild(ele);
  Drag.show();
  if (Platform.isOpera) {
    document.body.style.display = "none";
    document.body.style.display = "";
  }
  return returnValue;
};


Object.extend(Drag, {
  obj: null,
  init: function (elementHeader, element) {
    elementHeader.onmousedown = Drag.start;
    elementHeader.obj = element;
    if (isNaN(parseInt(element.style.left))) {
      element.style.left = "0px";
    }
    if (isNaN(parseInt(element.style.top))) {
      element.style.top = "0px";
    }
    element.onDragStart = new Function();
    element.onDragEnd = new Function();
    element.onDrag = new Function();
  },
  start: function (event) {
    var element = Drag.obj = this.obj;
    event = Drag.fixE(event);
    if (event.which != 1) {
      return true;
    }
    element.onDragStart();
    element.lastMouseX = event.clientX;
    element.lastMouseY = event.clientY;
    document.onmouseup = Drag.end;
    document.onmousemove = Drag.drag;
    return false;
  }, 
  drag: function (event) {
    event = Drag.fixE(event);
    if (event.which == 0) {
        return Drag.end();
    }
    var element = Drag.obj;
    var _clientX = event.clientY;
    var _clientY = event.clientX;
    if (element.lastMouseX == _clientY && element.lastMouseY == _clientX) {
        return false;
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
    return false;
  },
  end: function (event) {
    event = Drag.fixE(event);
    document.onmousemove = null;
    document.onmouseup = null;
    var _onDragEndFuc = Drag.obj.onDragEnd();
    Drag.obj = null;
    return _onDragEndFuc;
  }, 
  fixE: function (ig_) {
    if (typeof ig_ == "undefined") {
      ig_ = window.event;
    }
    if (typeof ig_.layerX == "undefined") {
      ig_.layerX = ig_.offsetX;
    }
    if (typeof ig_.layerY == "undefined") {
      ig_.layerY = ig_.offsetY;
    }
    if (typeof ig_.which == "undefined") {
      ig_.which = ig_.button;
    }
    return ig_;
  }
});


/**
 * 
 */ 
Drag.chg = function(elm){ 
  if(!window.confirm('你真的要要删除该块吗？'))
  {
   return;
  }
  var w_id = elm.parentNode.parentNode.id;
  if(w_id)
  {
  	w_id = w_id.split("_")[1]
  	var url = UnitUrl + "manage/unitWebpartAction.action?cmd=delete&widgetId="+ w_id ;
	  logger.log('url = ' + url);
	  new Ajax.Request(url, { 
	      method: 'get',
	      onSuccess: function(xport) { window.location.href= window.location.href;},
	      onException: function(xport, ex) {alert(ex) }
	    }
	  );
  }
  
  
  return;
  var inner=elm.parentNode.nextSibling;
  alert(elm.parentNode.parentNode.id)
  if(inner.style.display=="block"){
    inner.style.display="none";
  } else{
    inner.style.display="block";
  }
};

/**
 * 初始化拖放, el 中特定 DIV 可以在 el 中拖放.
 */
var _Drag_init = function (/*HTMLElement*/el) {
  Drag.rootElement = el;
  Util.column = Drag.getElementsByClassName(Drag.rootElement);
  Util.dragArray = new Array();
  var counter = 0;
  for (var i = 0; i < Util.column.length; i++) {
    var ele = Util.column[i];
    for (var j = 0; j < ele.childNodes.length; j++) {
      var ele1 = ele.childNodes[j];
      if (ele1.tagName == "DIV") {
        Util.dragArray[counter] = new Drag.draggable(ele1);
        counter++;
      }
    }
  }
};
 

/**
 * 得到位于 root 元素下面的 className = col 的 DIV
 */ 
Drag.getElementsByClassName = function(root){    
  var arr = new Array();
  // 目前，容器设置为 div calssName = "col"，这些设置要一致

  var elems = root.getElementsByTagName("td");   
  for (var i = 0; (elem = elems[i]); i++) {   
    if (elem.className == "col") {
      arr[arr.length] = elem;
    }
  }
  return arr;
};

var TabUtil = !!window.TabUtil || {};
TabUtil.changeTab = function(commonID,curIndex)
{
	var o = $(commonID).getElementsByTagName("div");
	for(var i = 0;i<o.length;i++)
	{
		o[i].className = i == curIndex?"cur":"";
		$(commonID + i).style.display = i == curIndex?"":"none";
	};
};


var CommonUtil = !!window.CommonUtil || {};
CommonUtil.AddFav = function(u,t)
{
	if(Platform.isIE)
	{
		window.external.AddFavorite(u,t);
	}
	else
	{
		window.sidebar.addPanel(t, u, "");
	}
	return false;
};

CommonUtil.setHomepage = function(u)
{
	if(Platform.isIE)
    {
     document.body.style.behavior='url(#default#homepage)';
     document.body.setHomePage(u); 
    }
    else if (window.sidebar)
    {
    	if(window.netscape)
    	{
         try
   			{  
            netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");  
         	}
         catch(e)  
         {  
    		alert("此操作被浏览器拒绝！\n请在浏览器地址栏输入“about:config”并回车\n然后将[signed.applets.codebase_principal_support]设置为'true'"); 
         }
    	} 
    var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components. interfaces.nsIPrefBranch);
    prefs.setCharPref('browser.startup.homepage',u);
 }
};

/*
 * 验证留言输入表单项
 */
CommonUtil.checkClientLeaveWordForm = function(oF)
{
	if(oF.LeavewordTitle.value == '')
	{
		alert('请输入留言标题。');
		return false;
	}
	if(oF.LeavewordContent.value == '')
	{
		alert('请输入留言内容。');
		return false;
	}
	return true;
};

CommonUtil.reFixImg = function(oImg,ConstWidth,ConstHeight)
{	
	//如果宽度和高度都不超，就不进行缩放了。
	w = oImg.width;
	h = oImg.height;
	if( w > ConstWidth && h > ConstHeight)
	{		
		//alert('step1' + oImg.src)
		if(w / h > ConstWidth / ConstHeight )
		{
			oImg.width = ConstWidth;
		}
		else
		{
			oImg.height = ConstHeight;
		}
	}
	else if( w > ConstWidth )
	{	
		oImg.width = ConstWidth;
	}
	else if(h > ConstHeight)
	{
		oImg.height = ConstHeight;
	}
	else
	{
		//一切ok		
	}
	oImg.style.visibility = 'visible';
	return;
};


CommonUtil.setCookie = function(cookieName,cookieValue,nMinutes) {
	var today = new Date();
	var expire = new Date();
	if (nMinutes==null || nMinutes==0 ) {nMinutes=10;}
	expire.setTime(today.getTime() + nMinutes * 60 * 1000);
	document.cookie = cookieName+"="+escape(cookieValue)+ ";expires="+expire.toGMTString();
};

;CommonUtil.getCookie = function(c_name){
	if (document.cookie.length>0)
  {
  c_start=document.cookie.indexOf(c_name + "=");
  if (c_start!=-1){ 
    c_start=c_start + c_name.length+1; 
    c_end=document.cookie.indexOf(";",c_start);
    if (c_end==-1) c_end=document.cookie.length;
    return unescape(document.cookie.substring(c_start,c_end));
    } 
  }
  return "";
};

;CommonUtil.getQueryString = function(name){
  // 如果链接没有参数，或者链接中不存在我们要获取的参数，直接返回空
  if(location.href.indexOf("?")==-1 || location.href.indexOf(name+'=')==-1)
  {
	return '';
  }

  // 获取链接中参数部分
  var queryString = location.href.substring(location.href.indexOf("?")+1);

  // 分离参数对 ?key=value&key2=value2
  var parameters = queryString.split("&");
  var pos, paraName, paraValue;
  for(var i=0; i<parameters.length; i++)
  {
  // 获取等号位置
  pos = parameters[i].indexOf('=');
  if(pos == -1) { continue; }

  // 获取name 和 value
  paraName = parameters[i].substring(0, pos);
  paraValue = parameters[i].substring(pos + 1);

  // 如果查询的name等于当前name，就返回当前值，同时，将链接中的+号还原成空格
  if(paraName == name)
  {
    return unescape(paraValue.replace(/\+/g, " "));
  }
  }
  return '';
};
function validateForm(fm)
{
  var q = document.getElementsByName('q_list');
  for(var i = 0,c = q.length; i < c; i++)
  {
    var a = document.getElementsByName('q_' + q[i].value);
    var hasSelected = false;
    for(var j = 0,d = a.length; j < d;j++)
    {
      if(a[j].checked) hasSelected = true;
    }
    if(!hasSelected)
    {
     alert('选项【' + document.getElementById('q_c_'+q[i].value).innerHTML + ' 】请至少选择一项。');
     return;
    }
    if(document.getElementById('q_type_'+ q[i].value).value == '0')
    {
      if(document.getElementById('q_max_'+ q[i].value).value != '0')
      {
        var maxCount = parseInt(document.getElementById('q_max_'+ q[i].value).value,10);
        var selectedCount = 0;
        if(isNaN(maxCount)) maxCount = 1;
        for(var k = 0;k<a.length;k++)
        {
          if(a[k].checked) selectedCount++;
        }
        if(selectedCount > maxCount)
        {
         alert('选项【' +document.getElementById('q_c_'+q[i].value).innerHTML + '】 的选项不能超过 ' + maxCount + ' 个');
         return;
        }
      } 
    }
  }
  fm.submit();
}
;