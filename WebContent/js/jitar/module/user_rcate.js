/**
 * 资源分类.
 */
(function() {
  logger.log('user_rcate module is loading...');
  var m = App._module_infos.user_rcate; 
  // 如果已经加载过了，则不要重复加载
  if (m.loaded) return;

  function _load_success(w, xhr) {
  	// xhtml format
    var xhtml = xhr.responseText;
    w.setContent(xhtml);
  }
  
  function _load_widget(/*widget*/w) {
  	//var url = CommonUtil.getCurrentRootUrl() + 'u/' + user.name + '/module/user_rcate?tmp=' + Date.parse(new Date());
	  var url = CommonUtil.getCurrentRootUrl() + 'u/' + user.name + '/py/user_rcate.py?tmp=' + (new Date()).getTime();

    logger.log('Ajax.Request to url = ' + url);
    new Ajax.Request(url, { 
          method: 'get',
          onSuccess: function(xhr) { _load_success(w, xhr); },
          onException: App.Module.on_load_ex.bind(w),
          onFailure: App.Module.on_fail.bind(w)
        }
      );
  }
  
  /**
   * 绑定并初始化一个 widget.
   */
  m.load = function(widget) {
      var w = widget;     
      w.setContent($('加载中...'));
      _load_widget(w);
  };
  // 设置已经加载标志.
  delete m.loading;
  m.loaded = true;
  logger.log('user_rcate module js is loaded');
  App.onModuleLoaded('user_rcate');
}) (); // decl and execute init func
 