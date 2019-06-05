/**
 * 群组信息 模块. name = 'group_info'
 */
(function() {
  logger.log('group_stat module js is loading...');
  var m = App._module_infos.group_stat; 
  // 如果已经加载过了，则不要重复加载
  if (m.loaded) return;

  // ajax success handler
  function _load_success(w, xhr) {
    // xhtml format
    var xhtml = xhr.responseText;
    w.setContent(xhtml);
  }
  
  // load group_info content
  function _load_content(/*widget*/w) {
    var url = JITAR_ROOT + 'g/'+ group.name + '/module/group_stat?tmp=' + (new Date()).getTime();
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
      logger.log('group_stat module.load() is called widget = ' + w);
      w.setContent($L('加载中...'));
      
      _load_content(w);
  };
    
  delete m.loading;
  m.loaded = true;
  logger.log('group_stat module js is loaded');
  App.onModuleLoaded('group_stat');
}) (); // decl and execute init func
