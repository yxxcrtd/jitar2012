/**
 * 博客统计
 */
(function() {
  logger.log('user_stats module is loading...');
  var m = App._module_infos.user_stats; 
  // 如果已经加载过了，则不要重复加载
  if (m.loaded) return;

  /**
   * 当从服务器加载 data 成功之后的处理.
   * @param w - widget 对象.
   * @param xhr - 传输对象.
   */
  function _load_success(w, xhr) {
  	// xhtml format
    var xhtml = xhr.responseText;
    w.setContent(xhtml);
  }
  
  function _load_widget(/*widget*/w) {
  	//var url = CommonUtil.getCurrentRootUrl() + 'u/' + user.name + '/module/user_stats?tmp=' + (new Date());
  	var url = CommonUtil.getCurrentRootUrl() + 'u/' + user.name + '/py/user_stats.py?tmp=' + (new Date()).getTime();

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
      _load_widget(w);
  };
    
  m.refresh = function(w) {
  	 _load_widget(w);
  }    
    
  // 设置已经加载标志
  delete m.loading;
  m.loaded = true;
  logger.log('user_stats module is loaded');
  App.onModuleLoaded('user_stats');
}) (); // decl and execute init func
 