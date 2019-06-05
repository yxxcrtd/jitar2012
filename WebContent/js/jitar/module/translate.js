/*
 * 简单文字内容
 * */
 
  var DEBUG_rss_response = null;

(function() {

  var mi = App._module_infos.translate; 

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
    var w = App.getWidgetById(widget.id)
    if(w)
    {
        w.setContent('<div>' +
                    '<form action="http://translate.google.cn/translate"><input type=text style="width:100%" name=u value="http://"><input type=hidden name=hl value="zh-CN"><input type=hidden name=ie value="UTF8">' + 
										'<br /><select name=sl id=old_web_sl>' +
										'<option class=line-below  value="auto">检测语言</option>' +
										'<option  value="ar">阿拉伯文</option>' +
										'<option  value="bg">保加利亚文</option>' +
										'<option  value="pl">波兰语</option>' +
										'<option  value="ko">朝鲜语</option>' +
										'<option  value="da">丹麦语</option>' +
										'<option  value="de">德语</option>' +
										'<option  value="ru">俄语</option>' +
										'<option  value="fr">法语</option>' +
										'<option  value="fi">芬兰语</option>' +
										'<option  value="nl">荷兰语</option>' +
										'<option  value="cs">捷克语</option>' +
										'<option  value="hr">克罗地亚文</option>' +
										'<option  value="ro">罗马尼亚语</option>' +
										'<option  value="no">挪威语</option>' +
										'<option  value="pt">葡萄牙语</option>' +
										'<option  value="ja">日语</option>' +
										'<option  value="sv">瑞典语</option>' +
										'<option  value="es">西班牙语</option>' +
										'<option  value="el">希腊语</option>' +
										'<option  value="it">意大利语</option>' +
										'<option  value="hi">印度文</option>' +
										'<option  value="en">英语</option>' +
										'<option  value="zh-CN" SELECTED="SELECTED">中文</option>' +
										'</select>翻译成' + 
										'<input name=sl type=hidden value="en">' +
										'<select name=tl><option  value="ar">阿拉伯文</option><option  value="bg">保加利亚文</option><option  value="pl">波兰语</option><option  value="ko">朝鲜语</option><option  value="da">丹麦语</option><option  value="de">德语</option><option  value="ru">俄语</option><option  value="fr">法语</option><option  value="fi">芬兰语</option><option  value="nl">荷兰语</option><option  value="cs">捷克语</option><option  value="hr">克罗地亚文</option><option  value="ro">罗马尼亚语</option><option  value="no">挪威语</option><option  value="pt">葡萄牙语</option><option  value="ja">日语</option><option  value="sv">瑞典语</option><option  value="es">西班牙语</option><option  value="el">希腊语</option><option  value="it">意大利语</option><option  value="hi">印度文</option><option  value="en" SELECTED="SELECTED">英语</option><option  value="zh-TW">中文(繁体)</option><option value="zh-CN">中文(简体)</option></select>'+
										'<input name=tl type=hidden value="zh-CN"><br /><input type=submit value="在线翻译"></form></div>');
    }
    else
    {
      w.setContent("没有找到该模块的内容。")
    }
  }
  
  /**
   * 绑定并初始化一个 widget
   */
  mi.load = function(widget) {
      var w = widget;     
      w.setTitle( '页面翻译');
      w.setContent('正在加载……');
      
      // 添加其 edit button 的事件处理
      if(w._btn_edit)
      {
        w._btn_edit.onclick = function() {
        mi.edit(w);
        }
      }
      
      _load_widget(widget);
  };
    
  mi.edit = function(widget) {
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
  logger.log('自写内容模块 is loaded');
  App.onModuleLoaded('translate');
}) (); // decl and execute init func
 