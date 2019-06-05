/**
 * 好友列表(用户). 
 */
 
(function() {
  logger.log('friendlinks is loading...');
  var m = App._module_infos.friendlinks; 

  // 如果已经加载过了，则不要重复加载.
  if (m.loaded) return;

  function _load_success(w, xhr) {
  	// xhtml format
    var xhtml = xhr.responseText;
    w.setContent(xhtml);
  }
  
  function _load_widget(/*widget*/w) {
    var count = 6;
    if(typeof w.data.count != 'undefined') {
      count = w.data.count;
    }
  	//var url = CommonUtil.getCurrentRootUrl() + 'u/' + user.name + '/module/friendlinks?count=' + count + '&tmp=' + (new Date());
  	var url = CommonUtil.getCurrentRootUrl() + 'u/' + user.name + '/py/user_friendlinks.py?count=' + count + '&tmp=' + (new Date()).getTime();

    logger.log('Ajax.Request to ' + url);
    new Ajax.Request(url, { 
          method: 'get',
          onSuccess: function(xhr) { _load_success(w, xhr); },
          onException: App.Module.on_load_ex.bind(w),
          onFailure: App.Module.on_fail.bind(w)
        }
      );
  }
  
  /**
   * 绑定并初始化一个 widget
   */
  m.load = function(widget) {
      var w = widget;     
      w.setContent($('加载中...'));
      // 添加其 edit button 的事件处理.
      if(w._btn_edit)
      {
	      w._btn_edit.onclick = function() {
	      	m.edit(w);
	      }
      }
      _load_widget(w);
  };
  
  m.edit = function(widget) {
  	var w = widget;  	
  	var t = DivUtil.getElementByClassName($('webpart_' + w.id),'widgetEditor');  	
  	if(t)
  	{
  		var count = 6;
  		if(typeof w.data.count != 'undefined')
  		{
  			count = w.data.count;
  		}
  		
  		t.style.display = '';
  		t.cells[1].innerHTML = "<div style='text-align:center;'>" +
  					  "显示条数：<input id='ListCount_" + w.id + "' value='" + count + "' style='width:60%;' /><br/>" + 
  					  "<input type='button' value=' 保  存 ' onclick='DivUtil.saveListData(" + w.id + ");DivUtil.hideWidgetEditor(" + w.id + ")' />" +
  					  " <input type='button' value=' 关  闭 ' onclick='DivUtil.hideWidgetEditor(" + w.id + ")' />" +
  					  "</div>";  		
  	}  	
  };
  
  // 设置已经加载标志
  delete m.loading;
  m.loaded = true;
  logger.log('friendlinks module js is loaded');
  App.onModuleLoaded('friendlinks');
}) (); // decl and execute init func
 