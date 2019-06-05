/**
 * JS文件编写规则，语句后面要加分号，以进行去除空格
 *
 * 由于我们仅使用了部分 prototype.js 的功能，此文件实现一个 prototype 的简化版本.
 */
// Prototype 定义.
var Prototype = {
  Version: '1.4.0',
  emptyFunction: function() {}
};

// 对象扩展属性 (Ajax 定义使用).
Object.extend = function(destination, source) {
  for (property in source) {
    destination[property] = source[property];
  };
  return destination;
};

//判断对象是否有属性.
function hasProperty( /* 要判断的对象 */ o )
{
  for(var p in o)
  {
   if(o[p]){return true;}
  };
  return false;
};
  

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
      function() {return new ActiveXObject('Msxml2.XMLHTTP');},
      function() {return new ActiveXObject('Microsoft.XMLHTTP');},
      function() {return new XMLHttpRequest();}
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
        setTimeout((function() {this.respondToReadyState(1);}).bind(this), 10);
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
 

/**
 * 简单文章用于调试，在 Firefox+Firebug 中输出调试信息.
 */
if (!logger) {
	var logger = {
	  /**
	   * 使用 Firefox window.console 输出消息.
	   * @param msg - 要输出的文章消息.
	   * @debug
	   */
	  log : function(msg) {
	    if (window.console) 
	      window.console.log(msg);
	  },
	  warn : function(msg) {
	  	if (window.console)
	  	  window.console.warn(msg);
	  },
	  error : function(msg) {
      if (window.console)
        window.console.error(msg);
	  }
	};
	
	logger.log('simple logger is initialized...');
}

/// DEBUG function
function dumpObject(obj, name) {
  if (!name) name = obj.toString();
  document.write('<div>');
  document.write('<h3>' + name + '</h3><hr/><ul>');
  for (var prop in obj) {
    document.write('<li>' + prop + ' = ' + obj[prop] + '</li>');
  }
  document.write('</ul>');
  document.write('</div>');
};


/* ------------------------------------------------------------- */
/* ----- 下面的代码由 jsp 包含自 '_source/platform.js' ----- */


/// 定义 Platform 全局容器，用于借鉴各个 js 库的代码.
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

// extension for Function
/**
 * 定义一个空的函数.
 * @ref-by App.start()
 */
Function.empty = function(){};

/**
 * 返回绑定了 this=el el[fucName]函数 的函数. 
 */
Function.bindFunction = function (el, fucName) {
     return function () {
         return el[fucName].apply(el, arguments);
     };
 };
 
/**
 * 实现或覆盖 $() 函数: 通过 id 获得指定元素.
 */
if (!$) {
	var $ = function(id) {
		return document.getElementById(id);
	};
}


/* ------------------------------------------------------------- */
/* ----- 下面的代码由 jsp 包含自 '_source/xml_util.js' ----- */


/*
 * 提供教研系统使用的 Xml 常用辅助函数集合.
 */
 
var Xml = {
	/**
	 * 得到指定 xml node 的具有指定名字的所有子节点集合.
	 * (此函数摘自 UWA.Utils.getChildrenByTagName() )
	 */
	getChildrenByTagName : function(/*XmlNode*/node, /*String*/tagName) {
    var ln = (node && node.childNodes) ? node.childNodes.length : 0;
    var arr = [];
    for(var z = 0; z < ln; z++) {
      if (node.childNodes[z].nodeName == tagName) arr.push(node.childNodes[z]);
    }
    return arr;
  },
	
	/**
	 * 得到指定 xml node 的具有指定名字的第一个子节点.
	 * 和 getChildrenByTagName() 的区别在于只返回找到的第一个.
	 */
	getSingleChildByTagName : function(/*XmlNode*/node, /*String*/tagName) {
    var ln = (node && node.childNodes) ? node.childNodes.length : 0;
    var arr = [];
    for(var z = 0; z < ln; ++z) {
      if (node.childNodes[z].nodeName == tagName)
        return node.childNodes[z]; 
    }
    return null;
	},
	
	/**
	 * 得到指定节点下指定名字的子节点的文字内容，如果节点不存在则返回 ''.
	 */
	getChildNodeText : function(/*XmlNode*/node, /*String*/tagName) {
		var child = Xml.getSingleChildByTagName(node, tagName);
		if (child == null) return '';
		// Firefox, W3C: textContent;  IE: text
		return child.textContent ? child.textContent : child.text;
	}
};


/* ------------------------------------------------------------- */
/* ----- 下面的代码由 jsp 包含自 '_source/json2.js' ----- */


/*
    json2.js
    2008-02-14
    See http://www.JSON.org/js.html
*/

/*jslint evil: true */

/*global JSON */

/*members "\b", "\t", "\n", "\f", "\r", "\"", JSON, "\\", apply,
    charCodeAt, floor, getUTCDate, getUTCFullYear, getUTCHours,
    getUTCMinutes, getUTCMonth, getUTCSeconds, hasOwnProperty, join, length,
    parse, propertyIsEnumerable, prototype, push, replace, stringify, test,
    toJSON, toString
*/

if (!this.JSON) {

    JSON = function () {

        function f(n) {    // Format integers to have at least two digits.
            return n < 10 ? '0' + n : n;
        }

        Date.prototype.toJSON = function () {

// Eventually, this method will be based on the date.toISOString method.

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

// If the string contains no control characters, no quote characters, and no
// backslash characters, then we can safely slap some quotes around it.
// Otherwise we must also replace the offending characters with safe sequences.

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

// JSON numbers must be finite. Encode non-finite numbers as null.

                return isFinite(value) ? String(value) : 'null';

            case 'boolean':
            case 'null':
                return String(value);

            case 'object':

// Due to a specification blunder in ECMAScript,
// typeof null is 'object', so watch out for that case.

                if (!value) {
                    return 'null';
                }

// If the object has a toJSON method, call it, and stringify the result.

                if (typeof value.toJSON === 'function') {
                    return stringify(value.toJSON());
                }
                a = [];
                if (typeof value.length === 'number' &&
                        !(value.propertyIsEnumerable('length'))) {

// The object is an array. Stringify every element. Use null as a placeholder
// for non-JSON values.

                    l = value.length;
                    for (i = 0; i < l; i += 1) {
                        a.push(stringify(value[i], whitelist) || 'null');
                    }

// Join all of the elements together and wrap them in brackets.

                    return '[' + a.join(',') + ']';
                }
                if (whitelist) {

// If a whitelist (array of keys) is provided, use it to select the components
// of the object.

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

// Otherwise, iterate through all of the keys in the object.

                    for (k in value) {
                        if (typeof k === 'string') {
                            v = stringify(value[k], whitelist);
                            if (v) {
                                a.push(stringify(k) + ':' + v);
                            }
                        }
                    }
                }

// Join all of the member texts together and wrap them in braces.

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


// Parsing happens in three stages. In the first stage, we run the text against
// regular expressions that look for non-JSON patterns. We are especially
// concerned with '()' and 'new' because they can cause invocation, and '='
// because it can cause mutation. But just to be safe, we want to reject all
// unexpected forms.

// We split the first stage into 4 regexp operations in order to work around
// crippling inefficiencies in IE's and Safari's regexp engines. First we
// replace all backslash pairs with '@' (a non-JSON character). Second, we
// replace all simple value tokens with ']' characters. Third, we delete all
// open brackets that follow a colon or comma or that begin the text. Finally,
// we look to see that the remaining characters are only whitespace or ']' or
// ',' or ':' or '{' or '}'. If that is so, then the text is safe for eval.

                if (/^[\],:{}\s]*$/.test(text.replace(/\\./g, '@').
replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']').
replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {

// In the second stage we use the eval function to compile the text into a
// JavaScript structure. The '{' operator is subject to a syntactic ambiguity
// in JavaScript: it can begin a block or an object literal. We wrap the text
// in parens to eliminate the ambiguity.

                    j = eval('(' + text + ')');

// In the optional third stage, we recursively walk the new structure, passing
// each name/value pair to a filter function for possible transformation.

                    return typeof filter === 'function' ? walk('', j) : j;
                }

// If the text is not JSON parseable, then a SyntaxError is thrown.

                throw new SyntaxError('parseJSON');
            }
        };
    }();
}


/* ------------------------------------------------------------- */
/* ----- 下面的代码由 jsp 包含自 '_source/core_predecl.js' ----- */


/// 定义 App 全局容器，App 用于提供整个应用的信息
if (!App) { 
  var App = {};
}



/* ------------------------------------------------------------- */
/* ----- 下面的代码由 jsp 包含自 '_source/page.js' ----- */


/**
 * 定义 App.Page 类, 表示在界面上的一个页面 (netvibes: tab?)
 */
App.Page = function(props) {
  return this;
};



/* ------------------------------------------------------------- */
/* ----- 下面的代码由 jsp 包含自 '_source/module.js' ----- */


/**
 * 定义 App.Module 类，表示系统支持的模块。
 * 属性:
 *  @property name - 模块的名字, 如 'feed'
 *  @property title - 此模块的标题, 如 '订阅'
 *  @property path - 模块使用的 js 所在 url (可选)
 *  @property ico - 模块图标文件 url
 *  @property css - 模块使用的样式表 url (可选)
 * 
 *  @loaded: boolean - 是否已经加载了
 *  @loading: boolean - 是否正在加载
 * 
 * 模块接口，各个模块必须实现
 *  @method   load(widget) - 绑定并初始化一个 widget
 *  @method   unload(widget) - 卸载一个 widget
 */
App.Module = {
  // ajax error, 'this' MUST a widget
  on_load_ex : function (/*XMLHttpRequest*/xhr, /*Error*/ex) {
    logger.log('_load_exception ex = ' + ex + ', widget = ' + this + ', xhr = ' + xhr);
    this.setContent('加载数据时发生异常: ex = ' + ex);
  },
  
  // ajax load fail, 'this' must bind to widget
  on_fail : function(/*XMLHttpRequest*/xhr) {
  	this.setContent('加载数据失败: ' + xhr.status + ' ' + xhr.statusText);
  }
};


/* ------------------------------------------------------------- */
/* ----- 下面的代码由 jsp 包含自 '_source/widget.js' ----- */


/**
 * 定义 App.Widget 类, 表示在界面上显示的一个内容块
 *  @property id - widget 的唯一标识
 *  @property page - 所在页面标识
 *  @property column - 所在栏目标识
 *  @property module - 支持模块的名字, 如 'Feed'
 *  @property title - 此 widget 的标题
 *  @property ico - the icon for this widget

 *  @property data - 此 widget 拥有的数据, 不同的模块定义将不同
 * 
 *  @property _div_win - 运行时该 widget 的外部窗口显示的 div 容器元素
 *     参见文档 WidgetWindow 结构  
 */
App.Widget = function(props) {
  this.id = props.id;
  this.page = props.page;
  this.column = props.column;
  this.module = props.module;
  this.title = props.title;
  this.ico = props.ico;
  this.data = props.data || {};
  
  // 测试用，输出的信息好看一些
  this.toString = function() {
  	return 'Widget{id=' + this.id + ', title=' + this.title + ', module=' + this.module + '}';
  };
  
  return this;
};

// 定义 Widget 原形函数。
App.Widget.prototype = {
	/**
	 * 设置此 widget 的内容.
	 */
  setContent : function(content) {
  	if (this._div_content)
  	{
  		this._div_content.innerHTML = content ? content : '';  		
  	}
  },
  setContentObject : function(content) {
  	if (this._div_content)
  	{  	
  		if(typeof(content) == 'object')
  		{
  			this._div_content.innerHTML = '';
  			this._div_content.appendChild(content);
  		}
  		else
  		{
  	   		this._div_content.innerHTML = content ? content : '';
  		}
  	}
  },
  /**
   * 设置此 widget 的标题.
   */
  setTitle : function(title) {
  	if (this._div_title)
  	  this._div_title.innerHTML = title ? title : '';
  }
};


/* ------------------------------------------------------------- */
/* ----- 下面的代码由 jsp 包含自 '_source/app_decl.js' ----- */


/**
 * 当前支持的所有模块说明集合, 模块代码本身此时尚未加载进入.
 * 类型： Module
 */
App._module_infos = {
	// 用户页面模块.
  'rss' : { title: 'RSS 聚合', path: 'module/rss.js', ico:'ico_rss.gif', css: '', edit:'true' },
  'profile' : {title: '个人档案', path: 'module/profile.js', ico:'ico_profile.gif', css: '', edit:'false' },
  'user_stats' : {title: '统计信息', path: 'module/user_stats.js', ico:'ico_stats.gif', css: '', edit:'false' },
  'joined_groups' : {title: '我的协作组', path: 'module/joined_groups.js', ico:'ico_joinedgroup.gif', css: '', edit:'true' },
  'user_placard' : {title: '我的公告', path: 'module/user_placard.js', ico:'ico_placard.gif', css: '', edit:'false' },
  'ugroup_placard' : {title: '协作组公告', path: 'module/ugroup_placard.js', ico:'', css: '', edit:'false' },
  'user_cate' : {title: '文章分类', path: 'module/user_cate.js', ico:'ico_cate.gif', css:'', edit:'false' },
  'user_rcate' : {title: '资源分类', path: 'module/user_rcate.js', ico:'ico_cate.gif', css:'', edit:'false' },
  'blog_search' : {title: '博客搜索', path: 'module/blog_search.js', ico:'', css:'', edit:'false' },

  'utags_feed' : {title: '我的聚合圈', path: 'module/utags_feed.js', ico:'', css:'', edit:'true' },
  'friendlinks' : {title: '好友列表', path: 'module/friendlinks.js', ico:'ico_friend.gif', css: '', edit:'true' },
  'entries' : {title: '最新文章列表', path: 'module/entries.js', ico:'ico_article.gif', css: '', edit:'true' },
  'messages' : {title: '我的短消息', path: 'module/messages.js', ico:'', css: '', edit:'true' },
  'user_leaveword' : {title: '我的留言', path: 'module/user_leaveword.js', ico:'ico_leaveword.gif', css: '', edit:'true' },
  'ugm_online' : {title: '当前在线协作组成员', path: 'module/ugm_online.js', ico:'', css: '', edit:'true' },
  'friend_things' : {title: '好友新鲜事', path: 'module/friend_things.js', ico:'', css: '', edit:'true' },
  'user_photo' : {title: '最新照片', path: 'module/user_photo.js', ico:'', css: '', edit:'true' },
  'user_video' : {title: '最新视频', path: 'module/user_video.js', ico:'', css: '', edit:'true' },
//  'flash_photo' : {title: '我的最新照片', path: 'module/flash_photo.js', ico:'', css: '', edit:'true' },  
  'bj2008' : {title: '圣火传递路线图', path: 'module/bj2008.js', ico:'', css: '', edit:'false' },
  'translate' : {title: '网页翻译', path: 'module/translate.js', ico:'', css: '', edit:'false' },
  'photo_cate' : {title: '相册分类', path: 'module/photo_cate.js', ico:'', css: '', edit:'false' }, 
  'category_article' : {title: '特定分类文章', path: 'module/category_article.js', ico:'', css: '', edit:'true' },
  'category_photo' : {title: '特定分类图片', path: 'module/category_photo.js', ico:'', css: '', edit:'true' },
  'category_video' : {title: '特定分类视频', path: 'module/category_video.js', ico:'', css: '', edit:'true' },
  
  // 文章页面模块.  
  'article_content' : {title: '文章内容', path: '', ico:'', css: '' },  // 预加载的
  'article_comments' : {title: '文章评论', path: 'module/article_comments.js', ico:'ico_comment.gif', css: '' },


  // 群组页面模块.
  'group_info' : {title: '协作组信息', path: 'module/group_info.js', ico:'', css: '', edit:'false'},
  'group_stat' : {title: '统计信息', path: 'module/group_stat.js', ico:'', css: '', edit:'false'},
  'group_link' : {title: '友情链接', path: 'module/group_link.js', ico:'', css: '', edit:'false'},
  'group_children' : {title: '子课题', path: 'module/group_children.js', ico:'', css: '', edit:'false'},
  
  // 用户分类页面模块.
  'user_articles' : {title: '文章列表', path: 'module/user_articles.js', ico:'', css: '', edit:'true'},
  'user_reslist' : {title: '资源列表', path: 'module/user_reslist.js', ico:'', css: '', edit:'true'},

  // 用户完整档案.
  'full_profile' : {title: '用户档案', path: 'module/full_profile.js', ico:'', css:'', edit:'false'},   
  
  // 占位使用的widget
  'placeholder' : {title: 'placeholder', path: 'module/placeholder.js', ico:'content.gif', css:'', edit:'false'}, 
  
  /** 还未合成的 */
  'lastest_comments' : {title: '最新评论', path: 'module/lastest_comments.js', ico:'', css: '', edit:'true' },
  'fresh_blogs' : {title: '最近更新的博客', path: 'module/fresh_blogs.js', ico:'', css: '', edit:'true' },
  'manage_groups' : {title: '我管理的群组', path: 'module/manage_groups.js', ico:'', css: '', edit:'true' },
  'lastest_refers' : {title: '最新访客', path: 'module/lastest_refers.js', ico:'http://js1.pp.sohu.com.cn/ppp/blog/styles_ppp/images/icons/flag_yellow.gif', css: '', edit:'true' },
  'tag_blogs' : {title: '文章标签', path: 'module/tag_blogs.js', ico:'', css: '', edit:'true' },
  'photostaples' : {title: '相册分类', path: 'module/photostaples.js', ico:'', css: '', edit:'true' },
  'tag_photos' : {title: '相册标签', path: 'module/tag_photos.js', ico:'', css: '', edit:'true' },
  'lastest_photos' : {title: '最新相册列表', path: 'module/lastest_photos.js', ico:'', css: '', edit:'true' },
  'lastest_topics' : {title: '最新群组主题', path: 'module/lastest_topics.js', ico:'', css: '', edit:'true' },
  'lastest_actions' : {title: '最新群组活动', path: 'module/lastest_actions.js', ico:'', css: '', edit:'true' },
  'lastest_courses' : {title: '我新发布的网络课程', path: 'module/lastest_courses.js', ico:'', css: '', edit:'true' },
  'simple_text' : {title: '自写内容模块', path: 'module/simple_text.js', ico:'', css: '', edit:'true' },
  'uwa_widget' : {title: 'uwa_widget', path: 'module/uwa_widget.js', ico:'', css: '', edit:'true' },
  'uwa_widget2' : {title: 'uwa_widget2', path: 'module/uwa_widget2.js', ico:'', css: '', edit:'true' }
  
};



/**
 * 用户拥有的所有页面集合.
 */
App._pages = [];

/**
 * 当前页面加载的所有 Widget 集合.
 * Java type: List<Widget>
 */
App._widgets = [];


/* ------------------------------------------------------------- */
/* ----- 下面的代码由 jsp 包含自 '_source/pre_def_mod.js' ----- */


/**
 * 预定义一种统一模式的模块, 这些模块加载简单的 html 数据从服务器.
 * mod = {
 *   name: 'rss',          -- 模块名字
 *   title: 'rss reader',  -- 模块标题
 *   ico:'',     -- 图标
 *     path: '',   -- 预定义时候不使用
 *     css: ''     -- 预定义时候不使用
 *   ajaxUrl: function(mod, widget) -- 得到获取数据的 ajax url 地址 
 * }
*/
App.predefineModule = function(mod) {
	if (!mod.path) mod.path = '';
	if (!mod.css) mod.css = '';

	
	/** 数据加载成功的处理: 设置到 widget 中 */
	function _load_success(w, xhr) {
    // xhtml format
    var xhtml = xhr.responseText;
    w.setContent(xhtml);
  }
  
  function _load_widget(/*widget*/w) {
 
  	var url = mod.ajaxUrl(mod, w);
  	if(mod.name == 'group_newbie' 
  	|| mod.name == 'group_activist'
  	|| mod.name == 'group_cate_article'
  	|| mod.name == 'group_action'	 )
  	{
  		url = JITAR_ROOT + 'g/' + group.name + '/py/' + mod.name + '.py?tmp=' + (new Date()).getTime();
  		logger.log('Ajax.Py to url = ' + url);
  		 new Ajax.Request(url, { 
          method: 'get',
          onSuccess: function(xhr) { _load_success(w, xhr); },
          onException: App.Module.on_load_ex.bind(w),
          onFailure: App.Module.on_fail.bind(w)
        }
      );
  	}
  	else
  	{
  	// example: JITAR_ROOT + 'u/$username/module/$modname';
    var url = mod.ajaxUrl(mod, w);

    if(page_ctxt.type && (page_ctxt.type == 'preparecourse') && ( url.indexOf('/mod/') == -1))
    {
      url = JITAR_ROOT + 'p/' + preparecourse.id + '/'+ preparecourse_stage_id + '/py/' + mod.name + '.py?tmp=' + Date.parse(new Date());
    }
    else{
  		if(url.indexOf('?') == -1){
  			url=url+"?widgetId="+w.id;
  		}else{
  			url=url+"&widgetId="+w.id;
  		}
    }
    logger.log('Ajax.Request to url = ' + url);
    new Ajax.Request(url, { 
          method: 'get',
          onSuccess: function(xhr) { _load_success(w, xhr); },
          onException: App.Module.on_load_ex.bind(w),
          onFailure: App.Module.on_fail.bind(w)
        }
      );
  	}
  }
  
  /**
   * 绑定并初始化一个 widget
   */
  mod.load = function(/*widget*/w) {
      w.setContent($('加载中...'));
      _load_widget(w);
  };
  /* 刷新 */
  mod.refresh = function(w) {
     _load_widget(w);
  };
	
	mod.loaded = true;
	App._module_infos[mod.name] = mod;
	// 不触发 App.onModuleLoaded 事件.
};

/**
 * predefineModule 但提供 ajaxUrl() = '/s/module/$mod_name';
 */
App.predefineSystemModule = function(mod) {
  if (!mod.ajaxUrl) {
    // 设置缺省 ajaxUrl 计算方式.
    mod.ajaxUrl = function(mod) {
      return JITAR_ROOT + 's/module/' + mod.name + '?tmp=' + Math.random();
    };
  }
  App.predefineModule(mod);
};

/**
 * predefineModule 但提供 ajaxUrl() == user 计算方式
 */
App.predefineUserModule = function(mod) {
	//var url = JITAR_ROOT + user.name + '/py/' + mod.name + '.py?tmp=' + Math.random(); 
	if (!mod.ajaxUrl) {
		// 设置缺省 ajaxUrl 计算方式.		
		mod.ajaxUrl = function(mod) {
			if(typeof(HasDomain)=="undefined"){
				return JITAR_ROOT + user.name + '/py/' + mod.name + '.py?tmp=' + Math.random(); 
			}
			else{
				return CommonUtil.getCurrentRootUrl() + 'py/' + mod.name + '.py?tmp=' + Math.random();
			}
		};
	}
	App.predefineModule(mod);
};

/**
 * 提供 group ajaxUrl() 计算方式
 */
App.predefineGroupModule = function(mod) {
  if (!mod.ajaxUrl) {
  	mod.ajaxUrl = function(mod) {
  		return JITAR_ROOT + 'g/' + group.name + '/module/' + mod.name + '?tmp=' + Math.random();
  	};
  }
  App.predefineModule(mod);
};

App.predefineGroupPyModule = function(mod) {
  if (!mod.ajaxUrl) {
  	mod.ajaxUrl = function(mod) {
  		return JITAR_ROOT + 'g/' + group.name + '/py/' + mod.name + '.py?tmp=' + Date.parse(new Date());
  	};
  }
  App.predefineModule(mod);
};

App.predefinePrepareCourseModule = function(mod) {
  if (!mod.ajaxUrl) {
    mod.ajaxUrl = function(mod) {
      return JITAR_ROOT + 'p/' + preparecourse.id + '/py/' + mod.name + '.py?tmp=' + Math.random();
    };
  }
  App.predefineModule(mod);
};

/** 插件形式的模块 */
;App.predefinePluginModule = function(mod) {
  if (!mod.ajaxUrl) {
    mod.ajaxUrl = function(mod) {
      return CommonUtil.getCurrentRootUrl() + 'mod/' + mod.name + '/listview.py?guid=' + ContainerObject.guid + '&type=' + ContainerObject.type + '&tmp=' + Math.random();
    };
  }
  App.predefineModule(mod);
};

/** 插件形式的模块2 */
;App.predefinePlugin2Module = function(mod) {
  if (!mod.ajaxUrl) {
    mod.ajaxUrl = function(mod) {
      return JITAR_ROOT + 'mod/' + mod.name + '/listview2.py?guid=' + ContainerObject.guid + '&type=' + ContainerObject.type + '&tmp=' + Math.random();
    };
  }
  App.predefineModule(mod);
};


/** 定义简单的 subject 模块 */
App.predefineSubjectModule = function(mod) {
  if (!mod.ajaxUrl) {
    mod.ajaxUrl = function(mod) {
    	if (window.subject)
        return JITAR_ROOT + 's/subjajax/' + subject.code + '/' + mod.name + '?tmp=' + Math.random();
      else
        return JITAR_ROOT + 's/module/' + mod.name;
    };
  }
  App.predefineModule(mod);
};

/** 定义简单 tag 模块 */
App.predefineTagModule = function(mod) {
  if (!mod.ajaxUrl) {
    mod.ajaxUrl = function(mod) {
    	if(window.tag)  
      	return JITAR_ROOT + 's/module/' + mod.name + '?tagId=' + tag.id + '&tmp=' + Math.random();
      	else
      	return JITAR_ROOT + 's/module/' + mod.name + '?tmp=' + Math.random();
    };
  }
  App.predefineModule(mod);
};

// 预先定义一些简单的 module.
App.predefineSystemModule({name: 'new_tags', title:'最新标签', ico:'' });
App.predefineSystemModule({name: 'hot_tags', title:'热门标签', ico:'' });
App.predefineSystemModule({name: 'group_list', title:'群组列表', ico:'' });

App.predefineTagModule({name: 'tag_article', title:'标签文章', ico:'' });
App.predefineTagModule({name: 'tag_resource', title:'标签资源', ico:'' });
App.predefineTagModule({name: 'tag_blog', title:'标签博客', ico:'' });
App.predefineTagModule({name: 'tag_group', title:'标签协作组', ico:'' });

App.predefineUserModule({name: 'user_resources', title:'我的资源', ico:'' });
App.predefineUserModule({name: 'user_calendar', title:'当天日历', ico:'' });
App.predefineUserModule({name: 'user_createdaction', title:'我发起的活动', ico:'' });
App.predefineUserModule({name: 'user_joinedaction', title:'我参与的活动', ico:'' });
App.predefineUserModule({name: 'user_preparecourse', title:'我发起的备课', ico:'' });
App.predefineUserModule({name: 'user_joinedpreparecourse', title:'我参与的备课', ico:'' });

App.predefineGroupModule({name: 'group_article', title:'组内文章', ico:''});
App.predefineGroupModule({name: 'group_resource', title:'组内资源', ico:''});
App.predefineGroupModule({name: 'group_activist', title:'小组活跃成员', ico:''});
App.predefineGroupModule({name: 'group_cate', title:'群组分类', ico:''});
App.predefineGroupModule({name: 'group_cate_article', title:'文章分类', ico:''});
App.predefineGroupModule({name: 'group_manager', title:'协作组组长', ico:''});
App.predefineGroupModule({name: 'group_star', title:'小组之星', ico:''});
App.predefineGroupModule({name: 'group_newbie', title:'小组最新成员', ico:''});
App.predefineGroupModule({name: 'group_placard', title:'组内公告', ico:''});
App.predefineGroupModule({name: 'gm_online', title:'当前在线协作组成员', ico:''});
App.predefineGroupModule({name: 'group_keyword', title:'群组关键词', ico:''});
App.predefineGroupModule({name: 'relative_group', title:'相关协作组', ico:''});
App.predefineGroupModule({name: 'group_leaveword', title:'组内留言', ico:''});
App.predefineGroupModule({name: 'recent_topiclist', title:'组内论坛', ico:''});

App.predefineGroupModule({name: 'group_action', title:'组内论坛', ico:''});
App.predefineSubjectModule({name: 'subj_list', title:'学科列表', ico:''});
App.predefineGroupPyModule({name: 'group_preparecourse_plan', title:'备课计划', ico:''});
App.predefineGroupPyModule({name: 'group_mutilcates', title:'特定分类综合模块', ico:''});
App.predefinePrepareCourseModule({name: 'show_preparecourse_related', title:'', ico:''});

App.predefinePrepareCourseModule({name: 'show_preparecourse_info', title:'', ico:''});
App.predefinePrepareCourseModule({name: 'show_preparecourse_stage_article', title:'', ico:''});
App.predefinePrepareCourseModule({name: 'show_preparecourse_stage_resource', title:'', ico:''});
App.predefinePrepareCourseModule({name: 'show_preparecourse_stage_topic', title:'', ico:''});
App.predefinePrepareCourseModule({name: 'show_preparecourse_action', title:'', ico:''});
App.predefinePrepareCourseModule({name: 'show_preparecourse_statis', title:'', ico:''});
App.predefinePrepareCourseModule({name: 'show_preparecourse_stage', title:'', ico:''});
App.predefinePrepareCourseModule({name: 'show_preparecourse_member', title:'', ico:''});
App.predefinePrepareCourseModule({name: 'show_preparecourse_private_content', title:'', ico:''});
App.predefinePrepareCourseModule({name: 'show_preparecourse_video', title:'', ico:''});
App.predefinePrepareCourseModule({name: 'show_preparecourse_common_abstract', title:'', ico:''});
App.predefinePrepareCourseModule({name: 'show_preparecourse_related', title:'', ico:''});

/* 这里无需再写了
App.predefinePluginModule({name: 'questionanswer', title:'问与答模块', ico:'help.gif'});
App.predefinePlugin2Module({name: 'questionanswer2', title:'问与答模块（测试2）', ico:''});
App.predefinePluginModule({name: 'vote', title:'调查投票', ico:'ico_stats.gif' });
App.predefinePluginModule({name: 'calendarevent', title:'日历提醒', ico:'datetime.gif' });
App.predefinePluginModule({name: 'topic', title:'话题讨论', ico:'' });

*/

/* ------------------------------------------------------------- */

/**
 * 把一个 widget 和它的模块绑定起来，并使用模块对 widget 进行初始化.
 */
App.bindModuleWidget = function(/*widget*/w) {
  // 找到该 widget 对应的 module.
  var m = App._module_infos[w.module];
  if (!m) return;
  if (m.loaded) {
    m.load(w);
    logger.log("bind widget = " + w + ' to module ' + m);
  } else {
  	// 模块尚未加载，现在去加载模块.
  	App.insertScriptFile(m.path);
  }
};


/**
 * 当某个模块 js 加载执行完成初始化，调用 App 的这个函数.
 * 参见 rss.js
 */
App.onModuleLoaded = function(mod_name) {
  logger.log('App.onModuleLoaded mod_name = ' + mod_name);
  // 检查所有 widgets, 初始化该模块对应的 widget
  for (var i = 0; i < App._widgets.length; ++i) {
  	var w = App._widgets[i];
  	if (w.module == mod_name)
      App.bindModuleWidget(w);
  }
};


/**
 * 启动 App, 在页面加载的时候只需要执行一次.
 */
App.start = function() {
  logger.log('App.start() is called');
  
  // 创建 widget 集合.
  for (var i = 0; i< page_ctxt.widgets.length; ++i) {
    // 根据静态 html 中指定的 widget 信息构造 App.Widget 对象.
    
    var w = new App.Widget(page_ctxt.widgets[i]); 
    var c = $('column_' + w.column);
    logger.log('column container is ' + c + ', w.column = ' + w.column);
    if (!c) {
    	c = $('column_1'); // 布局没有这个列时候, 我们总是放在缺省第一列.
    }
    
    // 创建其包装窗口 HTMLElement 并附加到 HTML 容器元素中.
    var win = createWidgetWindow(w);
    if (c) {     
		  /* html页面中的列布局中必须有一个class=cm的元素，否则，不能拖动到最后元素的后面,
		   * 要是不拖动，可以不执行下面的代码. 
		   */      
		 if(window._Drag_init)
		 {      
		      var lastElement = getLastChild(c);
		      if(lastElement == null)
		      {
		      	alert("页面标记设置不正确，请检查页面。\r\n\r\n正确的格式：\r\n<div  class='col'>\r\n  <div class='dm'></div>\r\n</div>");
		      }
		      else
		      {
		      	c.insertBefore(win, lastElement);
		      }
		 }
		 else
		 {
		 	c.appendChild(win);
		 }
    }
    App._widgets.push(w);
  }
  
  // 把 widget 和模块绑定起来.
  for (var i = 0; i < App._widgets.length; ++i) {
    App.bindModuleWidget(App._widgets[i]);
  }
  
	// 得到一个元素的最后一个Element子节点.
	function getLastChild(ele) {
	  if(ele && ele.childNodes){
		  for(var i = ele.childNodes.length - 1; i > -1; i--) {
		    if(ele.childNodes[i].nodeType == 1) {
		      return ele.childNodes[i];
		    }
		  }
		} else {
	    return null;
	  }
	}
	
  // 新建一个 div 并附加到 parent_div 最后，设置其 className .
  function newDiv(parent_div, cls) {
  	var div = document.createElement('div');
  	div.className = cls;
  	if (parent_div)
  	  parent_div.appendChild(div);
  	return div;
  }

  //helper
  function getElementByClassName(fromEle,cssName)
  {
  	
  	var d = fromEle.childNodes[0].childNodes[0].getElementsByTagName("DIV");
  	for(i = 0;i<d.length;i++)
  	{
  		if(d[i].className == cssName) return d[i];
  	}  	
  
  }
  //绑定事件.
  function widgetMouseOverAction(wdgt)
  {
  	//根据widget的设置判断显示的内容.
  	var r = $('webpart_' + wdgt.id);
  	if(r)
  	{
  		getElementByClassName(r,'ico').style.display = 'none';
  		if(getElementByClassName(r,'showWidget')) getElementByClassName(r,'showWidget').style.display = 'block';
  		if(getElementByClassName(r,'hideWidget')) getElementByClassName(r,'hideWidget').style.display = 'block';
  		getElementByClassName(r,'close').style.display = 'block';
  		if(getElementByClassName(r,'edit')) getElementByClassName(r,'edit').style.display = 'block';
  		getElementByClassName(r,'refresh').style.display = 'block';
  	}
  	
  }
  function widgetMouseOutAction(wdgt)
  {
  	//根据widget的设置判断显示的内容.
  	var r = $('webpart_' + wdgt.id);
  	if(r)
  	{
  		getElementByClassName(r,'ico').style.display = '';
  		if(getElementByClassName(r,'showWidget')) getElementByClassName(r,'showWidget').style.display = 'none';
  		if(getElementByClassName(r,'hideWidget')) getElementByClassName(r,'hideWidget').style.display = 'none';
  		getElementByClassName(r,'close').style.display = 'none';
  		if(getElementByClassName(r,'edit')) getElementByClassName(r,'edit').style.display = 'none';
  		getElementByClassName(r,'refresh').style.display = 'none';

  	}
  }
    
  // 创建 widget 界面包装窗口, 返回创建的 div HTMLElement .
  function createWidgetWindow(/*widget*/w) {
    // 创建一个 div 做为此 widget 的表现元素, 插入到界面容器中.
    w._div_win = newDiv(null, 'widgetWindow');
    if(visitor && !page_ctxt.isSystemPage && visitor.role == 'admin')
    {
	    w._div_win.onmouseover = function(){ widgetMouseOverAction(w);};
	    w._div_win.onmouseout = function(){ widgetMouseOutAction(w);};
    }
   // w._div_win.className = 'toolbox';
    w._div_win.id = "webpart_" +  w.id;   
    var div_frame = newDiv(w._div_win, 'widgetFrame');
    
    //创建表格
    var w_t_tr,w_t_td;
    var w_table = document.createElement('table');
    w_table.className = 'widgetTable';
    w_table.cellSpacing = 0;
    w_table.cellPadding = 0;
    w_table.border = 0;
    
    var w_t_header = document.createElement('thead');   
    w_t_tr = document.createElement('tr');    
    w_t_td = document.createElement('td');
    w_t_td.className = 'widgetHead h_lt';
    w_t_tr.appendChild(w_t_td);
    
    w_t_td = document.createElement('td');
    w_t_td.className='widgetHead h_mt';    

 	w._div_header = newDiv(w_t_td, 'widgetHeader');
    var header = w._div_header;
    

    
    if(visitor && !page_ctxt.isSystemPage && visitor.role == 'admin')
    {
	    // 构造 div_header 中的各个元素
	    w._btn_showHide = newDiv(header, 'showWidget');
	    w._btn_showHide.onclick = function(event) {  	
	  	var ele = Platform.isIE ? window.event.srcElement : event.target;
	  	var t = DivUtil.getElementByClassName($('webpart_' + w.id),'widgetContent');  	
	    if(t){    
	  		if(ele.className == 'showWidget'){
	  			ele.className = "hideWidget";
	  			t.style.display = 'none';  			
	  		}
	  		else{ 
	  			ele.className = "showWidget";
	  			t.style.display = 'block';
	  		}
	  	 }
	  };
	    
	    w._btn_close = newDiv(header, 'close');
	    w._btn_close.onclick = function()
	    {    	
	    	if(window.confirm('您真的要删除此模块吗？'))
	    	{
	    	var url = CommonUtil.getCurrentRootUrl() + 'manage/page.action?cmd=delete_widget&widgetId=' + w.id + '&tmp=' + Math.random();
	    	new Ajax.Request(url, { 
		          method: 'get',
		          onSuccess:function(xhr){
		          	if(xhr.responseText.replace(/(^\s*)|(\s*$)/g, "") == '200 OK')
		          	{
		          		window.location.reload();
		          	}
		          	else
		          	{
		          		alert('删除失败：'+ xhr.responseText);
		          	}
		          	},
		          onFailure:function(xhr){alert('删除失败。' + xhr.responseText);}
		        });
	    	}       
	    };
	    
	  
	    if(App._module_infos[w.module] && App._module_infos[w.module].edit && App._module_infos[w.module].edit == 'true')
	    {
		   w._btn_edit = newDiv(header, 'edit');
		   w._btn_edit.innerHTML = $L('编辑');
	    }
	    
	    w._btn_options = newDiv(header, 'options');
	    w._btn_refresh = newDiv(header, 'refresh');
	   
	    w._btn_refresh.onclick = function()
	    { 
	    	App._module_infos[w.module].load(w);
	    	return false;
	    }; 	    
    }
    
    
    w._btn_ico = newDiv(header, 'ico');
    
    if(App._module_infos[w.module] && App._module_infos[w.module].ico)
    {
	    var micon = App._module_infos[w.module].ico;   
	    if (w.ico && w.ico != '')
	    {
	    	w._btn_ico.innerHTML = '<img class="mod_icon" src="' + w.ico + '" width="16" height="16" border="0" />';
	    }
	    else
	    {
	    	if(micon && micon != '')
	    	w._btn_ico.innerHTML = '<img class="mod_icon" src="' + JITAR_ROOT + 'js/jitar/moduleicon/' + micon + '" width="16" height="16" border="0" align="absmiddle" />';
	    }	    
    }
    if(w._btn_ico.innerHTML == '')
    {
    	w._btn_ico.innerHTML = '<img class="mod_icon" src="' + JITAR_ROOT + 'images/pixel.gif" width="16" height="16" border="0" />';
    }    
    
    w._div_title = newDiv(header, 'title');
    w._div_title.innerHTML = w.title;
    w._div_title.id = 'webpart_' + w.id + '_h';


    w_t_tr.appendChild(w_t_td);
    
    w_t_td = document.createElement('td');
    w_t_td.className='widgetHead h_rt'; 
    w_t_tr.appendChild(w_t_td);
    
    w_t_header.appendChild(w_t_tr);
    w_table.appendChild(w_t_header);
    
    
    var w_t_body = document.createElement('tbody');
    
    //编辑区域.
    w_t_tr = document.createElement('tr');
    w_t_tr.style.display = 'none';
    w_t_tr.className = 'widgetEditor';
    
    w_t_td = document.createElement('td');
    w_t_td.className = 'widgetEdit e_lt';
    w_t_tr.appendChild(w_t_td);
    
    w_t_td = document.createElement('td');
    w_t_td.className = 'widgetEdit e_mt';
    w_t_tr.appendChild(w_t_td);
    
    w_t_td = document.createElement('td');
    w_t_td.className = 'widgetEdit e_rt';
    w_t_tr.appendChild(w_t_td);
    
    w_t_body.appendChild(w_t_tr);
    w_table.appendChild(w_t_body);
    
    //内容区域.
    w_t_tr = document.createElement('tr');
    
    w_t_td = document.createElement('td');
    w_t_td.className = 'widgetContent c_lt';
    w_t_tr.appendChild(w_t_td);
    
    w_t_td = document.createElement('td');
    w_t_td.className = 'widgetContent c_mt';
    w._div_content = newDiv(w_t_td, 'widgetContent');
    w._div_content.id = 'webpart_' + w.id + '_c';
    w._div_content.innerHTML = '载入中...';
    
    w_t_tr.appendChild(w_t_td);
    
    w_t_td = document.createElement('td');
    w_t_td.className = 'widgetContent c_rt';
    w_t_tr.appendChild(w_t_td);
    
    w_t_body.appendChild(w_t_tr);
    w_table.appendChild(w_t_body);
    
    //表格尾部.
    var w_t_foot = document.createElement('tfoot');    
    w_t_tr = document.createElement('tr');
    w_t_td = document.createElement('td');
    w_t_td.className = 'widgetFoot f_lt';
    w_t_tr.appendChild(w_t_td);
    
    w_t_td = document.createElement('td');
    w_t_td.className = 'widgetFoot f_mt';
    w_t_tr.appendChild(w_t_td);
    
    w_t_td = document.createElement('td');
    w_t_td.className = 'widgetFoot f_rt';
    w_t_tr.appendChild(w_t_td);
    
    w_t_body.appendChild(w_t_tr);
    w_table.appendChild(w_t_foot);
    
    div_frame.appendChild(w_table);    
    
    // 创建编辑区域.
    //w._div_editor = newDiv(div_frame, 'widgetEditor');   
    
    return w._div_win;
  };
  
  //加载拖动的功能.
  if(window._Drag_init)
  {
  	if(visitor && !page_ctxt.isSystemPage && visitor.role == 'admin')
  	{
  	_Drag_init($("container"));
  	}
  }
};


/**
 * 动态加载 js 脚本, 脚本不是立刻生效的, 需要一些时间才能加载进入.
 *   脚本加载之后, 必须设置 loaded 标志.
 * @param js_path - 要插入的 javascript 脚本路径.
 */
App.insertScriptFile = function(js_path) {
  // 以下的方式可能只适用于 ie, firefox  
  // TODO: 检查是否该 script 已经加载过了, 如果没有加载过才加载.
  var el_head = document.getElementsByTagName('head')[0];
  var el_script = document.createElement('script');
  el_script.src = JITAR_ROOT + 'js/jitar/' + js_path;
  el_script.type = 'text/javascript';
  el_head.appendChild(el_script);
};

/* 从现有的Widget中检索特定的widget对象 */
App.getWidgetById = function(wid)
{
	var w = null;
	for(var i = 0;i<this._widgets.length;i++)
	{
		if(this._widgets[i].id == wid)
		{
			w = this._widgets[i];
		}
	}
	
	return w;
};

/* 带分页的Widget */
App.loadPagedWidget = function(wid,pIndex)
{
	if(pIndex == null || pIndex == "")
	{
		return;
	}
	
	//处理用户输入的分页数字，考虑异常.
	var pager_div = $('pager_list' + wid);
	if(pager_div)
	{
		var currPage = parseInt(pager_div.getAttribute('currPage'));	
		var pageSize = parseInt(pager_div.getAttribute('pageSize'));
		var	totalRows = parseInt(pager_div.getAttribute('totalRows'));
		
		if(isNaN(currPage)) currPage = 1;
		if(isNaN(pageSize)) pageSize = 10;
		if(isNaN(totalRows)) totalRows = 1;
		if(pageSize < 1) pageSize = 10;
		
		var totalPage = Math.ceil(totalRows / pageSize);
		var goPage = parseInt(pIndex,10);
		if(isNaN(goPage)) goPage = 1;
		if(goPage < 1) goPage = 1;
		if(goPage > totalPage) goPage = totalPage;
		
		w = this.getWidgetById(wid);	
		if(w)
		{
			this._module_infos[w.module].loadPage(w,goPage);
		}
	}
};



/* ------------------------------------------------------------- */
/* ----- 下面的代码由 jsp 包含自 '_source/article_content_preloaded.js' ----- */


/* 文章内容模块, 此模块不从服务器 ajax 数据, 而是从页面特定的 div 中获得数据.
 */
(function() {
  var m = App._module_infos.article_content; 
  // 如果已经加载过了，则不要重复加载.
  if (m.loaded) return;

  /**
   * 绑定并初始化一个 widget
   */
  m.load = function(widget) {
    var w = widget;
    var content = $('_article_content_preload');
    if (!content) {
    	w.setContent('没有找到 preload 的文章内容, 此模块可能没有用在正确的地方');
    	return;
    }
    // 把 content 节点直接附加到 w._div_content 下面, 设置其 display 属性.
    content.parentNode.removeChild(content);
    w._div_content.innerHTML = '';
    w._div_content.appendChild(content);
    content.style.display = '';
  };
    
  // 设置已经加载标志.
  delete m.loading;
  m.loaded = true;
  // App.onModuleLoaded('article_content');
})();


/* ------------------------------------------------------------- */
/* ----- 下面的代码由 jsp 包含自 '_source/div_util.js' ----- */


var DivUtil = !!window.DivUtil || {};

//显示布局.
DivUtil.showColumn = function(e)
{
	
	var evtX = window.event ? window.event.x:e.pageX;
	var evtY = window.event ? window.event.y:e.pageY;

	var d = $("subMenuDiv");
	d.style.display = "block";
	d.innerHTML = "正在加载……";
	d.style.left = (evtX - 200) + "px";
	d.style.top = (evtY + 10) + "px";
		
		
	var url = CommonUtil.getCurrentRootUrl() + 'js/jitar/divcontent/columndiv.html?tmp=' + Math.random();
	logger.log('ajax request' + url);
    new Ajax.Request(url, { 
          method: 'get',
          onSuccess:columnLoaded,
          onFailure: columnFailed
        }
      );

	function columnLoaded(xhrObject)
	{

		$('subMenuDiv').innerHTML = xhrObject.responseText.replace(/\${SiteUrl}/gi,JITAR_ROOT);
	}
	
	function columnFailed()
	{
		$('subMenuDiv').innerHTML = '加载信息错误';
	}
		
};


// 执行布局操作.
DivUtil.setLayout = function(layoutId)
{
	layoutId = parseInt(layoutId,10);
	if(isNaN(layoutId))
	{
		return false;
	}
	
	var url = CommonUtil.getCurrentRootUrl() + "manage/page.action?cmd=set_layout&pageId=" + page_ctxt.pages[0].id + "&layoutId=" + layoutId + "&tmp=" + Math.random();
	
	  new Ajax.Request(url, { 
          method: 'get',
          onSuccess: function(xport) { 
          	window.location.reload();          	
          	 },
          onException: function(xport, ex) { alert( xport.responseText + "\r\n" +  ex); }
          
        }
      );	
   
   //清空缓存
  if(typeof(preparecourse) != 'undefined')
  {
	  url = JITAR_ROOT + 'jython/clearCache.py?name=preparecourse&id=' + preparecourse.id;
	  new Ajax.Request(url,{
	  method: 'get',
	  onSuccess: function(xport){}
	  });
  }  	
  
};


//设置主题.
DivUtil.setSkin = function(skinName)
{		
  var url = CommonUtil.getCurrentRootUrl() + "manage/page.action?cmd=set_skin&pageId=" + page_ctxt.pages[0].id + "&skin=" + skinName + "&tmp=" + Math.random();
  new Ajax.Request(url, { 
          method: 'get',
          onSuccess: function(xport) { 
          	var k = document.getElementById("skin");
          	k.setAttribute("href",JITAR_ROOT + "css/skin/" + skinName + "/skin.css");     	
          	 },
          onException: function(xport, ex) { alert( xport.responseText + "\r\n" +  ex); }          
        }
      );
      
  //清空缓存
  if(typeof(preparecourse) != 'undefined')
  {
	  url = JITAR_ROOT + 'jython/clearCache.py?name=preparecourse&id=' + preparecourse.id;
	  new Ajax.Request(url,{
	  method: 'get',
	  onSuccess: function(xport){}
	  });
  }  	
};

//显示主题列表.
DivUtil.showSkin = function(evt)
{
	var evtX = window.event ? window.event.x:evt.pageX;
	var evtY = window.event ? window.event.y:evt.pageY;

	var d = $("subMenuDiv");
	d.style.display = "block";
	d.innerHTML = "正在加载……";
	d.style.left = (evtX - 200) + "px";
	d.style.top = (evtY + 10) + "px";
		
	var skin_type = "";
	if(arguments.length >1) {skin_type = arguments[1];}
	var url = CommonUtil.getCurrentRootUrl() + 'js/jitar/divcontent/' + skin_type + 'skindiv.html?tmp' + Math.random();
    new Ajax.Request(url, { 
          method: 'get',
          onSuccess:skinLoaded,
          onFailure: skinFailed
        }
      );

	function skinLoaded(xhrObject)
	{		
		$('subMenuDiv').innerHTML = xhrObject.responseText.replace(/\${SiteUrl}/gi,JITAR_ROOT);
	}
	
	function skinFailed(xhrObject)
	{
		$('subMenuDiv').innerHTML = '加载主题错误：' + xhrObject.responseText;
	}
};


// 处理加为好友.
DivUtil.addFriend = function (uid)
{
	$('shareDiv').style.display = 'block';
	$('shareDiv').innerHTML = "正在加载信息……";
	LoginUI.centerDiv($('shareDiv'));	
	var url = CommonUtil.getCurrentRootUrl() + 'js/jitar/divcontent/addfriend.html?tmp' + Math.random();
    new Ajax.Request(url, { 
          method: 'get',
          onSuccess:ajaxLoaded,
          onFailure: ajaxFailed
        }
      );
	
	function ajaxLoaded(xhrObject)
	{		
		$('shareDiv').innerHTML = xhrObject.responseText.replace(/\$\{loginName\}/gi,uid);
	}
	
	function ajaxFailed(xhrObject)
	{
		$('shareDiv').innerHTML = '加载错误：' + xhrObject.responseText;
	}
};


// 添加好友.
DivUtil.doAddFriend = function(addFriendForm) {
	addFriendForm.action = JITAR_ROOT + 'manage/friend.action';
	addFriendForm.cmd.value = "save";
	addFriendForm.submit();
};

// 显示发送消息对话框.
DivUtil.sendUserMessage = function(uid)
{
	//计算位置、居中显示.
	$('shareDiv').style.display = 'block';
	$('shareDiv').innerHTML = "正在加载信息……";
	LoginUI.centerDiv($('shareDiv'));	
	var url = CommonUtil.getCurrentRootUrl() + 'js/jitar/divcontent/sendmsg.html?tmp' + Math.random();
	logger.log('ajax request' + url);
    new Ajax.Request(url, { 
          method: 'get',
          onSuccess:ajaxLoaded,
          onFailure: ajaxFailed
        }
      );
	
	function ajaxLoaded(xhrObject) {		
		$('shareDiv').innerHTML = xhrObject.responseText.replace(/\$\{loginName\}/gi,uid);
	}
	
	function ajaxFailed(xhrObject) {
		$('shareDiv').innerHTML = '加载错误：' + xhrObject.responseText;
	}
};

// 发送短消息.
DivUtil.doSendMsg = function(sendMsgForm) {	
	sendMsgForm.action = JITAR_ROOT + 'manage/message.py';
	sendMsgForm.cmd.value = "send";
	sendMsgForm.submit();
};

//创建通用的拖动层.
DivUtil.dragObj = new Object();
DivUtil.dragObj.zIndex = 0;
DivUtil.dragStart = function(event, id) {
  var el;
  var x, y;

  DivUtil.dragObj.elNode = $(id); 

  if (window.event) {
    x = window.event.clientX + document.documentElement.scrollLeft+ document.body.scrollLeft;
    y = window.event.clientY + document.documentElement.scrollTop+ document.body.scrollTop;
  }
  else{
    x = event.clientX + window.scrollX;
    y = event.clientY + window.scrollY;
  }

  // Save starting positions of cursor and element.

  DivUtil.dragObj.cursorStartX = x;
  DivUtil.dragObj.cursorStartY = y;
  DivUtil.dragObj.elStartLeft  = parseInt(DivUtil.dragObj.elNode.style.left, 10);
  DivUtil.dragObj.elStartTop   = parseInt(DivUtil.dragObj.elNode.style.top,  10);

  if (isNaN(DivUtil.dragObj.elStartLeft)) DivUtil.dragObj.elStartLeft = 0;
  if (isNaN(DivUtil.dragObj.elStartTop))  DivUtil.dragObj.elStartTop  = 0;

  // Update element's z-index.

  DivUtil.dragObj.elNode.style.zIndex = ++DivUtil.dragObj.zIndex;

  // Capture mousemove and mouseup events on the page.

  if (window.event) {
    document.attachEvent("onmousemove", DivUtil.dragGo);
    document.attachEvent("onmouseup",   DivUtil.dragStop);
    window.event.cancelBubble = true;
    window.event.returnValue = false;
  }
  else {
    document.addEventListener("mousemove", DivUtil.dragGo,   true);
    document.addEventListener("mouseup",   DivUtil.dragStop, true);
    event.preventDefault();
  }
};

DivUtil.dragGo = function(event) {

  var x, y;

  // Get cursor position with respect to the page.

  if (window.event) {
    x = window.event.clientX + document.documentElement.scrollLeft+ document.body.scrollLeft;
    y = window.event.clientY + document.documentElement.scrollTop+ document.body.scrollTop;
  }
  else{
    x = event.clientX + window.scrollX;
    y = event.clientY + window.scrollY;
  }

  // Move drag element by the same amount the cursor has moved.

  DivUtil.dragObj.elNode.style.left = (DivUtil.dragObj.elStartLeft + x - DivUtil.dragObj.cursorStartX) + "px";
  DivUtil.dragObj.elNode.style.top  = (DivUtil.dragObj.elStartTop  + y - DivUtil.dragObj.cursorStartY) + "px";

  if (window.event) {
    window.event.cancelBubble = true;
    window.event.returnValue = false;
  }
  else
    event.preventDefault();
};

DivUtil.dragStop = function(event) {

  // Stop capturing mousemove and mouseup events.

  if (window.event) {
    document.detachEvent("onmousemove", DivUtil.dragGo);
    document.detachEvent("onmouseup",   DivUtil.dragStop);
  }
  else {
    document.removeEventListener("mousemove", DivUtil.dragGo,   true);
    document.removeEventListener("mouseup",   DivUtil.dragStop, true);
  }
};

//通过className找到第一个匹配的元素
DivUtil.getElementByClassName = function(fromEle,cssName)
{
  	
	var d = fromEle.getElementsByTagName('*');
	for(var i = 0;i<d.length;i++)
	{
		if(d[i].className == cssName) return d[i];
	}  	
};

/* 添加模块 */
DivUtil.showModuleList = function(evt,mType)
{
	var evtX = window.event ? window.event.x:evt.pageX;
	var evtY = window.event ? window.event.y:evt.pageY;

	var d = $("subMenuDiv");
	d.style.display = "block";
	d.innerHTML = "正在加载……";
	d.style.left = (evtX - 200) + "px";
	d.style.top = (evtY + 10) + "px";		
		
	var url = CommonUtil.getCurrentRootUrl() + 'mod/sys_module.py?type=' + mType + '&tmp=' + Math.random();

	/*
	if(mType == 'g')
	{
		url = JITAR_ROOT + 'js/jitar/divcontent/gmodule.html?tmp=' + Math.random();
	}
	if(mType == 'p')
    {
      url = JITAR_ROOT + 'js/jitar/divcontent/pmodule.html?tmp=' + Math.random();
    }
    */
    
    new Ajax.Request(url, { 
          method: 'get',
          onSuccess:getModuleList,
          onFailure:getModuleListFailed
        }
      );

	function getModuleList(xhrObject)
	{	
		$('subMenuDiv').innerHTML = xhrObject.responseText.replace(/\${SiteUrl}/gi,JITAR_ROOT);
		var mdlist = $('_moduleList');
		if(mdlist)
		{
			var mdlist_div = mdlist.getElementsByTagName("DIV");
		
			for(var i = 0;i < mdlist_div.length;i++)
			{
				mdlist_div[i].className = 'module_list';
				
				/* 不可使用window,event判断是否是IE? */
				if (Platform.isIE) {
				 mdlist_div[i].attachEvent("onmouseover", function(){this.className = 'module_list_over';});
				 mdlist_div[i].attachEvent("onmouseout", function(){this.className = 'module_list';});
				 mdlist_div[i].attachEvent("onclick", moduleClick);					 	   
				}
				else {
				 mdlist_div[i].addEventListener("mouseover",function(){this.className = 'module_list_over';},true);
				 mdlist_div[i].addEventListener("mouseout", function(event){this.className = 'module_list';}, true);
				 mdlist_div[i].addEventListener("click", moduleClick, true);			  
				}
			}
		}
	};
	
	function moduleClick(evt)
	{
		var ele = Platform.isIE ? window.event.srcElement:evt.target;
		var mdname = ele.getAttribute("ref");
		var url = CommonUtil.getCurrentRootUrl() + 'manage/page.action?cmd=add_widget&pageId=' + page_ctxt.pages[0].id + '&module=' + mdname + '&title=' + encodeURIComponent(escape(ele.innerHTML)) + '&tmp=' + Math.random();
		
	    new Ajax.Request(url, { 
	          method: 'get',
	          onSuccess:function(xhr){
	          	if(xhr.responseText.replace(/(^\s*)|(\s*$)/g, "") == '200 OK')
	          	{
	          		window.location.reload();
	          	}
	          	else
	          	{
	          		alert('添加失败：'+ xhr.responseText);
	          	}
	          	},
	          onFailure:function(xhr){alert('添加失败。' + xhr.responseText);}
	        }
	      );
	};
	
	function getModuleListFailed(xhrObject)
	{
		$('subMenuDiv').innerHTML = '加载主题错误：' + xhrObject.responseText;
	}
};

/* 添加页面 */
DivUtil.showPageList = function(evt)
{
	var evtX = window.event ? window.event.x:evt.pageX;
	var evtY = window.event ? window.event.y:evt.pageY;

	var d = $("subMenuDiv");
	d.style.display = "block";
	d.innerHTML = "正在加载......";
	d.style.left = (evtX - 200) + "px";
	d.style.top = (evtY + 10) + "px";		
		
	var url = JITAR_ROOT + 'js/jitar/divcontent/page.html?tmp=' + Math.random();
    new Ajax.Request(url, { 
          method: 'get',
          onSuccess:getPageListSuccess,
          onFailure: getModuleListFailed
        }
      );

	function getPageListSuccess(xhrObject)
	{		
		$('subMenuDiv').innerHTML = xhrObject.responseText.replace(/\${SiteUrl}/gi,JITAR_ROOT);
	};
	
	function getModuleListFailed(xhrObject)
	{
		$('subMenuDiv').innerHTML = '加载主题错误：' + xhrObject.responseText;
	}
};

/* 隐藏编辑的 widget */
DivUtil.hideWidgetEditor = function(wid)
{
	var t = DivUtil.getElementByClassName($('webpart_' + wid),'widgetEditor'); 
	if(t)
	{
		t.style.display = 'none';
	}
};

/* 保存RSS修改的内容 */
DivUtil.saveRssData = function(wid)
{
	var w = App.getWidgetById(wid);
	if(w)
	{
		w.setContent('正在进行保存……');
		var widgetData = {"feedUrl":encodeURIComponent($("RssUrl_"+wid).value), "count":$("RssListCount_"+wid).value};
		var postData = 'data=' + JSON.stringify(widgetData);
		var url = CommonUtil.getCurrentRootUrl() + 'manage/page.action?cmd=update_data&widgetId=' + wid;
	    new Ajax.Request(url, { 
	          method: 'post',
	          parameters:postData,
	          onSuccess:function(xhr){
	          	if(xhr.responseText.indexOf("OK") >-1)
	          	{
	          		w.data = widgetData;
	          		//App._module_infos[w.module].load(w);
	          	}
	          	else
	          	{
	          		alert('修改数据失败：'+ xhr.responseText);
	          		//App._module_infos[w.module].load(w);
	          	}
	          	window.location.reload();
	          	},
	          onFailure:function(xhr){alert('修改数据失败。' + xhr.responseText);
	          App._module_infos[w.module].load(w);
	          }
	        }
	      );	  
	}
	else
	{
		alert('没有找到该内容块。');
	}
};

DivUtil.saveUserCategory = function(wid)
{
	var w = App.getWidgetById(wid);
	if(w)
	{
		w.setContent('正在进行保存……');
		if($("cate_"+wid).value == "")
		{
			alert("请选择一个个人分类。");
			return;
		}
		var widgetData = {"categoryId":$("cate_"+wid).value,"count":$("count_"+wid).value,"title":encodeURIComponent(escape($("title_"+wid).value))};
		var postData = 'data=' + JSON.stringify(widgetData);
		var url = CommonUtil.getCurrentRootUrl() + 'manage/page.action?cmd=update_data&widgetId=' + wid;
	    new Ajax.Request(url, { 
	          method: 'post',
	          parameters:postData,
	          onSuccess:function(xhr){
	          	if(xhr.responseText.indexOf("OK") >-1)
	          	{
	          		w.data = widgetData;
	          		App._module_infos[w.module].load(w);
	          	}
	          	else
	          	{
	          		alert('修改数据失败：'+ xhr.responseText);
	          		App._module_infos[w.module].load(w);
	          	}
	          	},
	          onFailure:function(xhr){alert('修改数据失败。' + xhr.responseText);
	          App._module_infos[w.module].load(w);
	          }
	        }
	      );		
	}
	else
	{
		alert('没有找到该内容块。');
	}
};

DivUtil.saveGroupWidgetTitle = function(wid)
{
	var w = App.getWidgetById(wid);
	if(w)
	{
		w.setContent('正在进行保存……');
		if($("wtitle_"+wid).value == "")
		{
			alert("请输入标题。");
			return;
		}
		//var widgetData = {"widgetId":$("cate_"+wid).value,"count":$("count_"+wid).value,"title":encodeURIComponent(escape($("title_"+wid).value))};
		var postData = 'title=' + encodeURIComponent(escape($("wtitle_"+wid).value));
		alert(postData);
		var url = CommonUtil.getCurrentRootUrl() + 'WEB-INF/py/group_mutilcates_update.py?cmd=savetitle&widgetId=' + wid;
		alert(url);
	    new Ajax.Request(url, { 
	          method: 'post',
	          parameters:postData,
	          onSuccess:function(xhr){
	          	if(xhr.responseText.indexOf("OK") >-1)
	          	{
	          		//w.data = widgetData;
	          		App._module_infos[w.module].load(w);
	          	}
	          	else
	          	{
	          		alert('修改数据失败：'+ xhr.responseText);
	          		App._module_infos[w.module].load(w);
	          	}
	          	},
	          onFailure:function(xhr){alert('修改数据失败。' + xhr.responseText);
	          App._module_infos[w.module].load(w);
	          }
	        }
	      );		
	}
	else
	{
		alert('没有找到该内容块。');
	}
};

DivUtil.selectRss = function(wid)
{
	$("RssUrl_" + wid).value = $("RssSel_" + wid).options[$("RssSel_" + wid).selectedIndex].value;
	$("RssListCount_" + wid).value = 10;
};

/* 保存列表类型的修改的内容 */
DivUtil.saveListData = function(wid)
{
	var w = App.getWidgetById(wid);
	if(w)
	{
		w.setContent('正在进行保存……');
		var widgetData = {"count":$("ListCount_"+wid).value};
		var postData = 'data=' + JSON.stringify(widgetData);
		var url = CommonUtil.getCurrentRootUrl() + 'manage/page.action?cmd=update_data&widgetId=' + wid;
	    new Ajax.Request(url, { 
	          method: 'post',
	          parameters:postData,
	          onSuccess:function(xhr){
	          	if(xhr.responseText.replace(/(^\s*)|(\s*$)/g, "") == '200 OK')
	          	{
	          		w.data = widgetData;
	          		App._module_infos[w.module].load(w);
	          	}
	          	else
	          	{
	          		alert('修改数据失败：'+ xhr.responseText);
	          		App._module_infos[w.module].load(w);
	          	}
	          	},
	          onFailure:function(xhr){alert('修改数据失败。' + xhr.responseText);
	          App._module_infos[w.module].load(w);
	          }
	        }
	      );		
	}
	else
	{
		alert('没有找到该内容块。');
	}
};

/* 保存文本输入框类型的修改的内容 */
DivUtil.saveSimpleTextData = function(wid,widgetTitle,widgetContent,win)
{
	var w = App.getWidgetById(wid);
	if(w)
	{
	  var re = /<script\b[^>]*>([\s\S]*?)<\/script>/igm;
	  var contt = widgetContent;
	  contt = contt.replace(re,""); //删除所有的script标记，防止脚本出错。
		w.setContent('正在进行保存……');    
		var widgetData = {"content":contt};
		var postData = 'data=' + encodeURIComponent(JSON.stringify(widgetData)) + '&title=' + encodeURIComponent(widgetTitle);
		var url = CommonUtil.getCurrentRootUrl() + 'manage/page.action?cmd=update_data&widgetId=' + wid;
	    new Ajax.Request(url, { 
	          method: 'post',
	          parameters:postData,
	          onSuccess:function(xhr){
	          	if(xhr.responseText.replace(/(^\s*)|(\s*$)/g, "") == '200 OK')
	          	{
	          		w.data = widgetData;
	          		w.title = widgetTitle;
	          		App._module_infos[w.module].load(w);
	          		win.close();
	          	}
	          	else
	          	{
	          		alert('修改数据失败：'+ xhr.responseText);
	          		App._module_infos[w.module].load(w);
	          		win.focus();
	          	}
	          	},
	          onFailure:function(xhr){alert('修改数据失败。' + xhr.responseText);
	          App._module_infos[w.module].load(w);
	          win.focus();
	          }
	        }
	      );		
	}
	else
	{
		alert('没有找到该内容块。');
		win.focus();
	}
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
	else if(Platform.isGecko)
	{
		window.sidebar.addPanel(t, u, "");
	}
	else
	{
		alert('请同时按\r\n\r\n CTRL + D \r\n\r\n键加入收藏夹或者书签。');
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
	//此方法可以保证填满:)	
  if(oImg.width<=ConstWidth && oImg.height <=ConstHeight){
    oImg.style.visibility = 'visible';
    return;
  }
	var oldRatio = oImg.width/oImg.height;
	var newRatio = ConstWidth/ConstHeight;
	if(oldRatio>=newRatio)
		{
		oImg.width=ConstWidth;
		}
	else
		{
		oImg.height=ConstHeight;
		}
	oImg.style.visibility = 'visible';
	return;
	//以下是原来的方法，存在小图不缩放的问题。
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


//digg
CommonUtil.diggTrample = function(cmd,objId)
{
	if(CommonUtil.getCookie(cmd + objId) != '')
	{
		alert('请不要在 10 分钟内连续顶或者踩同一篇文章。');
		return ;		
	}
	var url;
	if(typeof HasDomain =='undefined'){
		url= JITAR_ROOT + 'jython/digg.py?cmd=' + cmd + '&id=' + objId + '&tmp=' + (new Date()).getTime();
	}
	else{
		url = CommonUtil.getCurrentRootUrl() + 'jython/digg.py?cmd=' + cmd + '&id=' + objId + '&tmp=' + (new Date()).getTime();
	}
	
	new Ajax.Request(url, { 
            method: 'get',
            onSuccess:function(xhr){
              if(xhr.responseText.replace(/(^\s*)|(\s*$)/g, "") != 'ERROR')
              {
                $(cmd + objId).innerHTML = xhr.responseText;
                CommonUtil.setCookie(cmd + objId,objId,10);
              }
              else
              {
                alert('获取数据发生错误。' + xhr.responseText );
              }
              },
            onFailure:function(xhr){alert('提交数据发生错误。' + xhr.responseText);
            }
          }
        );
};

/* 每隔一段时间自动向服务器提交撰写的文章 */
DivUtil.saveArticleContent = function(wid, widgetTitle, widgetContent, win) {
	var w = App.getWidgetById(wid);
	if(w)
	{
		w.setContent('正在进行保存……');    
		var widgetData = {"content":widgetContent};
		var postData = 'data=' + encodeURIComponent(JSON.stringify(widgetData)) + '&title=' + encodeURIComponent(widgetTitle);
		var url = JITAR_ROOT + 'manage/page.action?cmd=update_data&widgetId=' + wid;
	    new Ajax.Request(url, { 
	          method: 'post',
	          parameters:postData,
	          onSuccess:function(xhr){
	          	if(xhr.responseText.replace(/(^\s*)|(\s*$)/g, "") == '200 OK')
	          	{
	          		w.data = widgetData;
	          		w.title = widgetTitle;
	          		App._module_infos[w.module].load(w);
	          		win.close();
	          	}
	          	else
	          	{
	          		alert('修改数据失败：'+ xhr.responseText);
	          		App._module_infos[w.module].load(w);
	          		win.focus();
	          	}
	          	},
	          onFailure:function(xhr){alert('修改数据失败。' + xhr.responseText);
	          App._module_infos[w.module].load(w);
	          win.focus();
	          }
	        }
	      );		
	}
	else
	{
		alert('没有找到该内容块。');
		win.focus();
	}
};
;CommonUtil.SelectAll = function(chk,elename)
{
  var ele = document.getElementsByName(elename);
  for(var i = 0;i<ele.length;i++)
  {
   ele[i].checked = chk.checked;
  }
};
CommonUtil.getCurrentRootUrl = function()
{
  if(typeof HasDomain == 'undefined'){
	  return JITAR_ROOT;
  }
  else
  {
  url = window.location.protocol + "//" + window.location.host + "/";
  return url;
  }
};

//个人空间分类显示专用
CommonUtil.ExpandCollapse = function(ele)
{
	if(!ele) return;
	eleDiv = ele.parentNode.parentNode.parentNode;
	if(eleDiv.style.overflow=="hidden"){
		eleDiv.style.overflow='visible';
		eleDiv.style.height='auto';
		ele.innerHTML = "收起";
	}
	else{
		eleDiv.style.overflow='hidden';
		eleDiv.style.height='30px';
		ele.innerHTML = "展开";
	}	
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
CommonUtil.ExpandCollapseCustomPart = function(eleId)
{
	var ele = document.getElementById(eleId);
	if(ele.getAttribute("myHeight") == "")
		{
			ele.setAttribute('myHeight',ele.parentNode.style.height);
			ele.parentNode.style.height = ele.offsetHeight + 'px';
		}
	else
		{
			ele.parentNode.style.height = ele.getAttribute("myHeight");
			ele.setAttribute('myHeight',"");
		}
};