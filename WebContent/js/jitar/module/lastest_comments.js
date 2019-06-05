/**
 * 最新评论
 * */
(function() {
	var mod_name = 'lastest_comments';
  var m = App._module_infos[mod_name]; 
  if (m.loaded) return;

  function _load_success(w, xhr) {
    // xhtml format
    var xhtml = xhr.responseText;
    w.setContent(xhtml);
  }
  
  function _load_widget(/*widget*/w) {
  	var count = 8;
    if(typeof w.data.count != 'undefined')
    {
      count = w.data.count;
    }
    var url = CommonUtil.getCurrentRootUrl() + 'js/jitar/module/lastest_comments.py?userId=' + user.id + '&userName=' + user.name + '&count=' + count + '&tmp=' + (new Date()).getTime();
    logger.log('Ajax.Request to url=' + url);
    new Ajax.Request(url, { 
          method: 'get',
          onSuccess: function(xhr) { _load_success(w, xhr); },
          onException: App.Module.on_load_ex.bind(w),
          onFailure: App.Module.on_fail.bind(w)
        }
      );
  }
  
  m.load = function(widget) {
      var w = widget;     
      // 添加其 edit button 的事件处理.
      if(w._btn_edit)
      {
	      w._btn_edit.onclick = function() {
	      	m.edit(w);
	      }
      }
      w.setContent($('加载中...'));
      _load_widget(w);
  };
  m.edit = function(widget) {
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
  m.edit = function(widget) {
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
  // 设置已经加载标志
  // 设置已经加载标志
  delete m.loading;
  m.loaded = true;
  logger.log(mod_name + ' module is loaded');
  App.onModuleLoaded(mod_name);
}) (); // decl and execute init func
