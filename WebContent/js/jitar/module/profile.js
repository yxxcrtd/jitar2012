/**
 * 个人信息 模块实现.
 * .name = 'profile'
 */
(function() {
  logger.log('profile module js is loading...');
  var m = App._module_infos.profile; 
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
  	//var url = CommonUtil.getCurrentRootUrl() + 'u/' + user.name + '/module/profile?tmp=' + Date.parse(new Date());
  	var url = CommonUtil.getCurrentRootUrl() + 'u/' + user.name + '/py/user_profile.py?tmp=' + (new Date()).getTime();
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
      logger.log('profile module.load() is called widget = ' + w);
      w.setContent($L('加载中...'));      
      _load_profile(widget);
  };
    
  delete m.loading;
  m.loaded = true;
  logger.log('profile module js is loaded');
  App.onModuleLoaded('profile');
}) (); // decl and execute init func
