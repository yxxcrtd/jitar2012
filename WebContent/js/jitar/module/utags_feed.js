/**
 * 我的聚合圈
 */

(function() {
  logger.log('utags_feed is loading...');

  var m = App._module_infos.utags_feed; 

  // 如果已经加载过了，则不要重复加载
  if (m.loaded) return;

  function _load_success(w, xhr) {
    // xhtml format
    var xhtml = xhr.responseText;
    w.setContent(xhtml);
  }
  
  function _load_widget(/*widget*/w) {
    var url = JITAR_ROOT + 'u/' + user.name + '/module/utags_feed?tmp=' + (new Date()).getTime();

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
  logger.log('utags_feed module js is loaded');
  App.onModuleLoaded('utags_feed');
}) (); // decl and execute init func
 