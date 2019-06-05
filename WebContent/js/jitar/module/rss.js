var DEBUG_rss_response = null;

// 每个模块的 js 的要求是创建一个模块对象, 并设置到 App 中.
(function() {
  var m = App._module_infos.rss;
  // 如果已经加载过了，则不要重复加载.
  if (m.loaded) return;
  logger.log('rss module js is loading...');

  /**
   * 当从服务器加载 rss.xml 成功之后的处理.
   * @param w - widget 对象.
   * @param xport - 传输对象.
   */
  function _load_success(w, xport) {
    var xmldoc = xport.responseXML;
    if(xmldoc == null)
    {	
    	w.setContent('不是 xml 数据，一般是您提供的地址不正确，或者返回的数据不是 xml 格式导致的。\r\n' + xport.responseText);
    	return;
    }
    
    var channel = xmldoc.documentElement.getElementsByTagName('channel')[0];

    var title = Xml.getChildNodeText(channel, 'title');   // 该频道的标题
    w.setTitle(title);
    
    var count = w.data.count == null?4:parseInt( w.data.count,10);
    var x = '<ul class="listul">';
    // 该频道的所有项目, TODO: 为了简化现在先只显示了4条
    var items = Xml.getChildrenByTagName(channel, 'item');
    for (var i = 0; i < items.length && i < count; ++i) {
    	x += '<li><a href="' + Xml.getChildNodeText(items[i], 'link') + 
    	 '" target="_blank">' + Xml.getChildNodeText(items[i], 'title') + '</a></li>';
    }
    x += '</ul>';
    
    // 显示出内容
    w.setContent(x);
  }
  
  /**
   * 当传输失败的时候被调用
   */
  function _load_exception(widget, /*XMLHttpRequest*/xport, /*Error*/ex) {
  	logger.error('_load_exception ex = ' + ex + ', widget = ' + widget + ', xport = ' + xport);
  	widget.setContent('在加载内容的时候发生错误:' + ex + xport.statusText + xport.responseText);
  }
  
  // 测试读取一个 rss
  function _load_rss(widget) {
	if(typeof(widget.data.feedUrl) == "undefined")
	{
		widget.setContent("没有设置RSS地址。");
		return;
	}
  	var proxy = CommonUtil.getCurrentRootUrl() + 'manage/proxy/rss';
  	// TODO: ENCODE widget.data.feedUrl
  	var url = proxy + '?url=' + encodeURIComponent(widget.data.feedUrl) ;
    logger.log('Ajax.Request to ' + widget.data.feedUrl + ' by proxy:' + proxy);
    new Ajax.Request(url, { 
          method: 'get',
          onSuccess: function(xport) { _load_success(widget, xport); },
          onException: function(xport, ex) { _load_exception(widget, xport, ex); },
          onFailure: function(xport, ex) { _load_exception(widget, xport, ex); }
          // ?? onTimeout ?? onFail
        }
      );
  }
  
  /**
   * 初始化一个 widget
   */
  m.load = function(widget) {
      var w = widget;
      logger.log('rss module.load() is called widget = ' + w);
      w.setTitle(m.title);
      
      // 添加其 edit button 的事件处理.
      if(w._btn_edit)
      {
	      w._btn_edit.onclick = function() {
	      	m.edit(w);
	      };
      }     
      
      _load_rss(widget);
  };
    
  m.close = function(widget)
  {
  	if(window.confirm("你真的要删除这个模块吗？"))
  	{
  		var url = CommonUtil.getCurrentRootUrl() + 'widget.action?cmd=del&wid=' + widget.id + '&tmp=' + (new Date()).getTime();
	    new Ajax.Request(url, { 
	          method: 'get',
	          onSuccess:function(xhr){
	          	if(xhr.responseText.replace(/(^\s*)|(\s*$)/g, "") == 'ok')
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
  
  m.edit = function(widget) {
  	var w = widget; 
  	var t = DivUtil.getElementByClassName($('webpart_' + w.id),'widgetEditor');  	
  	if(t)
  	{
  		var _url = "";
  		if(w.data.feedUrl) _url= w.data.feedUrl;
  		var _count = "";
  		if(w.data.count) _count = w.data.count;
  		t.style.display = '';
  		t.cells[1].innerHTML = "<div style='text-align:center;'>地址：<input style='width:80%;' id='RssUrl_" + w.id + "' value='" + _url + "' /> <br/>" +
  					  "条数：<input style='width:80%;' id='RssListCount_" + w.id + "' value='" + _count + "' /><br/>" +   					  
  					  " <select id='RssSel_" + w.id + "' onchange='DivUtil.selectRss(" + w.id + ")'><option value=''>选择聚合源</option>" + 
  					  "<option value='http://blog.eduol.cn/rssfeed.asp'>教育在线</option>" + 
  					  "<option value='http://www.jeast.net/teacher/jiahou/rss2.xml'>东行记－黎加厚的教育网志</option>" + 
  					  "<option value='http://feed.feedsky.com/xiuli'>xiuli\'s blog</option>" + 
  					  "<option value='http://www.being.org.cn/blog/rss.xml'>惟存教育网志</option>" + 
  					  "<option value='http://www.etc.edu.cn/blog/ysqetc/index.rdf'>余胜泉教育网志</option>" + 
   					  "<option value='http://blog.ci123.com/weiyu/rss'>韦珏院士个人BLOG</option>" + 
  					  "<option value='http://blog.people.com.cn/blog/pages/logbodylist_rss.html?site_id=2915'>朱永新教育随笔</option>" + 
  					  "<option value='http://jiangminghe.spaces.live.com/feed.rss'>蒋鸣和-云游大侠</option>" + 
  					  "</select> " +
  					  "<input type='button' value=' 保  存 ' onclick='DivUtil.saveRssData(" + w.id + ")' />" +
              " <input type='button' value=' 关  闭 ' onclick='DivUtil.hideWidgetEditor(" + w.id + ")' />" +
  					  "</div>";  		
  	}  	
  };
    
  m.unload = function(widget) {
  };
  
  
  // 设置已经加载标志
  delete m.loading;
  m.loaded = true;
  App.onModuleLoaded('rss');
}) ();
