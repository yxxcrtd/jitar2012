
// 每个模块的 js 的要求是创建一个模块对象, 并设置到 App 中.
(function() {
  var m = App._module_infos.category_photo;
  // 如果已经加载过了，则不要重复加载.
  if (m.loaded) return;
  // 加载成功，显示内容。
  function _load_success(w, xhr) {
	  var xhtml = xhr.responseText;
	  var title = "";
      if(w.data && w.data.title)
    	{
    		title = w.data.title;
    	}
      if(title == "") title = w.title;
      w.setTitle(unescape(unescape(title)));
	  w.setContent(xhtml);
  }
  
  /**
   * 当传输失败的时候被调用
   */
  function _load_exception(widget, /*XMLHttpRequest*/xport, /*Error*/ex) {
  	logger.error('_load_exception ex = ' + ex + ', widget = ' + widget + ', xport = ' + xport);
  	widget.setContent('在加载内容的时候发生错误:' + ex);
  }
  
  function _load_category_article(widget) {
	var url = CommonUtil.getCurrentRootUrl() + 'js/jitar/module/category_photo.py?loginName=' + user.name + '&userCateId=' + widget.data.categoryId + '&count=' + widget.data.count + '&title=' + encodeURIComponent(escape(widget.data.title)) + '&tmp=' + (new Date()).getTime();
 	new Ajax.Request(url, { 
          method: 'get',
          onSuccess: function(xport) { _load_success(widget, xport); },
          onException: function(xport, ex) { _load_exception(widget, xport, ex); }
        }
      );
  }
  
  /**
   * 初始化一个 widget
   */
  m.load = function(widget) {
      var w = widget;
      // 添加其 edit button 的事件处理.
      if(w._btn_edit)
      {
	      w._btn_edit.onclick = function() {
	      	m.edit(w);
	      };
      }
      _load_category_article(widget);
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
  	var oldCateId = 0;
  	var count = 10;
  	var title = "";
  	if(w.data && w.data.categoryId)
  	{
  		oldCateId = w.data.categoryId;
  	}
  	if(w.data && w.data.count)
  	{
  		count = w.data.count;
  	}
  	if(w.data && w.data.title)
  	{
  		title = w.data.title;
  	}
  	if(title == "") title = w.title;
  	title = unescape(title);
  	var t = DivUtil.getElementByClassName($('webpart_' + w.id),'widgetEditor');  	
  	if(t)
  	{
  		t.style.display = '';
  		t.cells[1].innerHTML = "正在加载分类……";
  		var url = CommonUtil.getCurrentRootUrl() + 'u/' + user.name + '/py/get_user_photocategory.py?wid='+ w.id +'&oldCateId=' + oldCateId + '&count='+ count +'&title='+ encodeURIComponent(escape(title)) +'&tmp=' + (new Date()).getTime();
  		new Ajax.Request(url, { 
	          method: 'get',
	          onSuccess:function(xhr){
	        	  t.cells[1].innerHTML = "<div style='text-align:center'>" + unescape(xhr.responseText) + "</div>";
	        	  t.cells[1].innerHTML += "<div style='text-align:center'><input type='button' value=' 保  存 ' onclick='DivUtil.saveUserCategory(" + w.id + ")' />" +
	              " <input type='button' value=' 关  闭 ' onclick='window.location.href=window.location.href;' /></div>";
	          	},
	          onFailure:function(xhr){alert('删除失败。' + xhr.responseText);
	          t.cells[1].innerHTML = "<div style='text-align:center'><input type='button' value=' 关  闭 ' onclick='DivUtil.hideWidgetEditor(" + w.id + ")' /></div>";
	          }
	        });  		  		
  	}  	
  };
    
  m.unload = function(widget) {
      logger.log('module.unload() is called widget = ' + widget);
  };
  
  
  // 设置已经加载标志
  delete m.loading;
  m.loaded = true;
  App.onModuleLoaded('category_photo');
}) ();
