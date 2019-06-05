var DEBUG_rss_response = null;

(function() {
  var mi = App._module_infos.placeholder; 
  if(mi.loaded) return;
  
  function _load_success(w, xport)
  {
  	w.setContent(xport.responseText);
  }
  function _load_exception(widget,xport, /*Error*/ex)
  {
  	w.setContent('在加载内容的时候发生错误:' + ex);
  }

  mi.load = function(widget) {
      var w = widget;       
      var ct = $(w.id)
      if(w.data && w.data.callbackurl)
      {
        //传递过来的参数都需要进行编码传递，在此进行解码，否则，多余的参数将丢失。
        
        var url_tmp = decodeURIComponent(w.data.callbackurl);
      	var dot = url_tmp.indexOf("?") > -1?"&":"?";
      	var url;
      	logger.log("插件模块进行测试地址传递参数：解码后的参数 = " + url_tmp)
      	
      	//注意：删除多余的tmp
      	var _arr = url_tmp.split("&")
      	var _arr2 = [];
      	url_tmp = "";
      	for(var i = 0;i<_arr.length;i++)
      	{      	 
      	 if(_arr[i].substr(0,4) != 'tmp=')
      	 {
      	   _arr2.push(_arr[i])
      	 }
      	}
      	url_tmp = _arr2.join('&')
      	logger.log("分解后的地址：" + url_tmp )
      	if(url_tmp.indexOf("parentGuid")>-1)
      	{
      	  url = JITAR_ROOT + url_tmp + dot + 'tmp=' + (new Date()).getTime();
      	}
      	else
      	{
      	  url = JITAR_ROOT + url_tmp + dot + 'parentGuid=' + ContainerObject.guid + '&tmp=' + (new Date()).getTime();
      	}
      	
      	logger.log("插件模块进行测试地址传递参数：最后请求的地址 = " + url)
	    new Ajax.Request(url, { 
	          method: 'get',
	          onSuccess: function(xport) { _load_success(widget, xport); },
	          onException: function(xport, ex) { _load_exception(widget, xport, ex); }
	        }
	      );
      }
      else
      {
	    w.setTitle(ct.getAttribute("title"));
	    ct.style.display='';
	    w.setContentObject(ct);
      }
      //ct.parentNode.removeChild(ct)
  };
  mi.unload = function(widget) { };
  
  // 设置已经加载标志
  mi.loading = false;
  mi.loaded = true;
  logger.log('placeholder is loaded');
  App.onModuleLoaded('placeholder');
}) (); // decl and execute init func
 