var DEBUG_rss_response = null;
(function() {
  logger.log('简单文字内容加载……');

  var mi = App._module_infos.simple_text; 

  // 如果已经加载过了，则不要重复加载
  if (mi.loaded) return;

  /**
   * 当从服务器加载 rss.xml 成功之后的处理

   * @param w - widget 对象
   * @param xport - 传输对象
   */
  function _load_success(w, xport) {
  	/* json 格式的返回结果 */
    var textdoc = xport.responseText;
    w.setTitle(w.title);
    w.setContent(textdoc);
  }
  
  /**
   * 当传输失败的时候被调用
   */
  function _load_exception(widget, /*XMLHttp*/xport, /*Error*/ex) {
  	widget.setContent("处理数据时失败：" + '_load_exception ex = ' + ex + ', widget = ' + widget + ', xport = ' + xport);
  	logger.log('_load_exception ex = ' + ex + ', widget = ' + widget + ', xport = ' + xport);
  }
  
  
  function _load_widget(widget) {
  	var w = App.getWidgetById(widget.id);
  	if(w)
  	{  		
  		w.setTitle( w.title);
  		if(w.data)
  		{ 	
  			if(typeof w.data.content == 'undefined' || w.data.content == '')
  			{
  				w.setContent("自写内容模块，但暂时没有输入内容。");
  			}
  			else
  			{
	  			w.setContent( w.data.content);
  			}
  		}
  		else
  		{  			
  			w.setContent("自写内容模块，但暂时没有输入内容。");
  		}
  	}
  	else
  	{
  		w.setContent("没有找到该模块的内容。");
  	}
  }
  
  /**
   * 绑定并初始化一个 widget
   */
  mi.load = function(widget) {
      var w = widget;
      w.setTitle(w.title);
      w.setContent('正在加载……');
      
      // 添加其 edit button 的事件处理
      if(w._btn_edit)
      {
	      w._btn_edit.onclick = function() {
	     	mi.edit(w);
	      };
      }
      
      _load_widget(widget);
  };
    
  mi.edit = function(widget) {
  	var w = widget;  	
  	window.open(CommonUtil.getCurrentRootUrl() + 'js/jitar/divcontent/edit.jsp?widgetId=' + w.id + '&t=' + (new Date()).getTime(),'_blank','width=900,height=650,resizable=1');
  };
  
  mi.refresh = function(widget)
  {
  	 _load_widget(widget);
  };
        
    
  mi.unload = function(widget) {
      logger.log('rss module.unload() is called widget = ' + widget);
  };
  
  
  // 设置已经加载标志
  mi.loading = false;
  mi.loaded = true;
  logger.log('自写内容模块 is loaded');
  App.onModuleLoaded('simple_text');
}) (); // decl and execute init func
 