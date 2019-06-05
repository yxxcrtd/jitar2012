/**
 * 文章标签
 */
(function() {
  logger.log('简单文字内容加载……');

  var mi = App._module_infos.tag_blogs; 

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
  	var proxy = JITAR_ROOT + 'js/jitar/_source/_simple_text.html';
  	var url = proxy;

    logger.log('Ajax.Request to ' + widget.data.feedUrl + ' by proxy:' + proxy);
    // TODO: 当前这里使用的是 prototype.js 的 Ajax.Request, 考虑封装
    new Ajax.Request(url, { 
          method: 'get',
          onSuccess: function(xport) { _load_success(widget, xport); },
          onException: function(xport, ex) { _load_exception(widget, xport, ex); }
          // ?? onTimeout ?? onFail
        }
      );
  }
  
  /**
   * 绑定并初始化一个 widget
   */
  mi.load = function(widget) {
      var w = widget;     
      w.title = '文章分类';
      w.setContent('正在加载……');
      
      // 添加其 edit button 的事件处理
      w._btn_edit.onclick = function() {
     	mi.edit(w);
      }
		
      // TODO: 再测试使用 ajax 获取一些动态的数据吧

      _load_widget(widget);
  };
    
 mi.edit = function(widget) {
  	var w = widget;  	
  	var t = DivUtil.getElementByClassName($('webpart_' + w.id),'widgetEditor');  	
  	if(t)
  	{
  		var count = 8;
  		if(typeof w.data.count != 'undefined')
  		{
  			count = w.data.count;
  		}
  		
  		t.style.display = '';
  		t.cells[1].innerHTML = "<div style='text-align:center;'>" +
  					  "显示条数：<input id='ListCount_" + w.id + "' value='" + count + "' style='width:60%;' /><br/>" + 
  					  "<input type='button' value=' 保  存 ' onclick='DivUtil.saveListData(" + w.id + ")' />" +
  					  " <input type='button' value=' 关  闭 ' onclick='DivUtil.hideWidgetEditor(" + w.id + ")' />" +
  					  "</div>";  		
  	}  	
  };
  
  mi.refresh = function(widget)
  {
  	 _load_widget(widget);
  }    
        
    
  mi.unload = function(widget) {
      logger.log('rss module.unload() is called widget = ' + widget);
  };
  
  
  // 设置已经加载标志
  mi.loading = false;
  mi.loaded = true;
  logger.log('文章分类 module js is loaded');
  App.onModuleLoaded('tag_blogs');
}) (); // decl and execute init func
 