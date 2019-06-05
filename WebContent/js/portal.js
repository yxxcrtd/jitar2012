/*
 为 portal 页面提供服务的 js, 在页面 head 区域调用.
 此文件要尽量的小, 因此不可以随意添加功能, 最好经过讨论论证.
 文件的功能函数来自于 json, prototype 等开源著名 js 库, 选用我们所需的部分,
   如果这些库升级了, 尽量保证此文件也能够升级.
 注意：使用 jitar.register(), process() 的时候必须保证已经定义了 ajax_url 变量。
 
*/
function $(id) {
  return document.getElementById(id);
}

/* 调试用，正式版本应该去掉 */
if (!this.logger) {
	logger = {
		log : function(msg) {
			var div_debug = $('div_debug');
			if (div_debug === null) { return; }
			var new_div = document.createElement('li');
			new_div.innerText = msg;
			div_debug.appendChild(new_div);
		}
	};
}

/*global JSON, from json2.js */
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

/* ajax request create function TYPE: 'text/html' or 'text/xml'. from: AjaxRequest.js */
function createAjaxRequest(TYPE) {
	var http_handle = null;
	if (window.XMLHttpRequest) {
		http_handle = new XMLHttpRequest();
		if (http_handle.overrideMimeType)
			http_handle.overrideMimeType(TYPE);
	} else if (window.ActiveXObject) {
		var progIDs = ["Msxml2.XMLHTTP.6.0", "Msxml2.XMLHTTP.5.0", "Msxml2.XMLHTTP.4.0", "MSXML2.XMLHTTP.3.0", "MSXML2.XMLHTTP", "Microsoft.XMLHTTP"];
		for (var i = 0; i < progIDs.length; ++i) {
			var progID = progIDs[i];
			try {
				return new ActiveXObject(progID);
			} catch (e) {}    
		}
	}
	return http_handle;
}

/* portal service object declaration */
if (!this.jitar) {
	jitar = function() {
		return {
			// registred ajax content load set
			reqcol : [],
			// toString() make object output pretty
			toString : function() {
				return 'jitar ajax service object';
			},
			// register an ajax content load service, req must an obj, optional has id,type,dbid etc property
			register : function (req) {
				this.reqcol.push(req);
			},
			// process registered request, 此方法假定已经定了变量 ajax_url 
			process : function () {
				if (this.reqcol.length == 0) return;		// nothing need to request
				this.ajax = createAjaxRequest('text/html');
				if (this.ajax == null) {
					alert('您的浏览器不支持 Ajax 功能，请升级到 IE6、IE7、IE8及其以上、Firefox 2.0以上、mozilla 1.5以上、Opera 8.0、Safri 2.0以上版本的浏览器。');
					return;
				}
				var url = ajax_url;
				var req_text = JSON.stringify(this.reqcol);
				logger.log('ajax_url = ' + url + ', JSON.stringify req = ' + req_text);
				// send request
				var dataBody = "data=" + req_text;
				this.ajax.open("POST", url, true);
				this.ajax.onreadystatechange = this.callback;

				// POST 方法必须设置正确的 Headers！
				this.ajax.setRequestHeader("If-Modified-Since", "0");
				this.ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				this.ajax.setRequestHeader("Content-length", dataBody.length);
				this.ajax.setRequestHeader("Connection", "close");
				this.ajax.send(dataBody);
			},
			ajax : null,
			// called when ajax statechanged
			callback : function () {
				try {
					// logger.log('this.reqcol = ' + jitar.reqcol);
					// logger.log('this.ajax = ' + jitar.ajax);
					logger.log('readyState = ' + jitar.ajax.readyState +
						(jitar.ajax.readyState == 4 ? ', status = ' + jitar.ajax.status : '')); 
					if (jitar.ajax.readyState != 4) return;
					if (jitar.ajax.status != 200) { jitar.onloadfail(); return; }
					var resp_text = jitar.ajax.responseText;
					// the content is response from server, parse it
					logger.log('responseText = ' + resp_text);
					var respcol = JSON.parse(resp_text);
					jitar.onsuccess(respcol);
				} catch (e) {
					logger.log('callback catch an exception: ' + e);
				}
			},
			// called when request failed
			onloadfail : function () {
				alert('request failed: readyState = ' + jitar.ajax.readyState + 
					', status = ' + jitar.ajax.status);
				for (i = 0; i < jitar.reqcol.length; i++) {
					var req = jitar.reqcol[i];
					// TODO:
					$(req.id).innerHTML = '加载失败'; 
				}
			},
			onsuccess : function(respcol) {
				for (i = 0; i < respcol.length; ++i) {
					var resp = respcol[i];
					if (resp.content === null) continue;
					logger.log('onsuccess resp.content = ' + resp.content);
					//if (resp.callback)
					//	resp.callback(resp);
					//else
					var resp_id = $(resp.id);
					if (resp_id === null) continue;
					// TODO: get component_content inner resp_id
					resp_id.all('component_content').innerHTML = resp.content;
				}
			}
		};
	}();
}

