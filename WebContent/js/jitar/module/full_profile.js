/**
 * 用户档案 模块实现.
 * .name = 'full_profile'
 */
(function() {
  logger.log('full_profile module js is loading...');
  var m = App._module_infos.full_profile; 
  // 如果已经加载过了，则不要重复加载
  if (m.loaded) return;

  // ajax success handler
  function _load_success(w, xhr) {
  	// xhtml format
    var xhtml = xhr.responseText;
    w.setContent(xhtml);
  }
  
  // load profile content
  function _load_profile(/*widget*/w) {
  	//var url = CommonUtil.getCurrentRootUrl() + 'js/jitar/module/full_profile.py?loginName=' + user.name + '&tmp=' + Date.parse(new Date());
	var url = CommonUtil.getCurrentRootUrl() + 'u/' + user.name + '/py/user_full_profile.py?tmp=' + (new Date()).getTime();
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
      w.setContent($L('加载中...'));
      
      _load_profile(widget);
  };
    
  delete m.loading;
  m.loaded = true;
  logger.log('full_profile module js is loaded');
  App.onModuleLoaded('full_profile');
}) (); // decl and execute init func
