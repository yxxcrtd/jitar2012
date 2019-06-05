/**
 * 用户分类资源列表, 修改自 user_articles.js .
 */
(function() {
  var mod_name = 'user_reslist';
  var m = App._module_infos[mod_name]; 
  var widgetid = 0;
  if (m.loaded) return;

  function _load_success(w, xhr) {
    // xhtml format
    widgetid = w.id    
    var xhtml = xhr.responseText;  
    w.setContent(xhtml);
    w.setTitle(category.name + '列表');
    
    // 得到 id = 'article_list_pager' 的 div
    var pager_div = $('pager_list');         
    pager_div.id = pager_div.id + widgetid;
    pager_div = $('pager_list' + widgetid);    
    logger.log('pager_div = ' + pager_div.parentNode.innerHTML)
    if (pager_div)
      create_pager(pager_div);
  };
  
  // 生成分页.
  function create_pager(pager_div) {
  	// init pager data.
    var pager = {
    	currPage: parseInt(pager_div.getAttribute('currPage')),
    	pageSize: parseInt(pager_div.getAttribute('pageSize')),
    	totalRows: parseInt(pager_div.getAttribute('totalRows'))
    };
    
    //pager.totalPage = (pager.totalRows+1) / pager.pageSize;
    /*
    var str = '示例, 用 js 生成的分页: ';
    for (var i = 1; i < pager.totalPage; ++i) {
    	var href = 'page=' + i;
    	str += '<a href="#' + href + '">' + i + '</a> ';
    }
    */
    pager_div.innerHTML = ShowPage(pager.totalRows,pager.currPage,pager.pageSize);
  };
    
  function ShowPage(recCount, curPage, pageItem) {  	
		LeftNum = 0;
		RightNum = 0;
		OffsetStep = 4;
		PageCount = Math.ceil(recCount / pageItem);
		if (curPage > PageCount) {
			curPage = PageCount;
		}
		if (curPage - OffsetStep < 1) {
			LeftNum = 1;
		} else {
			LeftNum = curPage - OffsetStep;
		}
		if (curPage + OffsetStep > PageCount) {
			RightNum = PageCount;
		} else {
			RightNum = curPage + OffsetStep;
		}
		
		OutPut = "";
		for (i = LeftNum; i <= RightNum; i++) {
			if (i==curPage) {
				OutPut += "<font color=red>" + i.toString() + "</font> ";
			} else {
				OutPut += "<a onclick='App.loadPagedWidget(" + widgetid + "," + i + ");return false;' href=\"#\">" + i.toString() + "</a> ";
			}
		}
		if (curPage > OffsetStep) {		
			OutPut = "<a onclick='App.loadPagedWidget(" + widgetid + ",1);return false;' href=\"#\">首页</a> <a onclick='App.loadPagedWidget(" + widgetid + "," + (curPage - 1) + ");return false;'  href=\"#\">上一页</a> " + OutPut;
		}

		if (curPage < PageCount - OffsetStep) {
			OutPut = OutPut + " <a onclick='App.loadPagedWidget(" + widgetid + "," + "41" + "," + (curPage + 1) + ");return false;'  href=\"#\">下一页</a> <a onclick='App.loadPagedWidget(" + widgetid + "," + PageCount + ");return false;'  href=\"#\">末页</a>";
		}	  

		OutPut = "共 "
				+ PageCount
				+ " 页 "
				+ OutPut
				+ " 转到：<input class='pagingInput' id=\"GoPage" + widgetid + "\" value=\"\"/><input class=\"goButton\" type=\"button\" value=\"GO\" onclick=\"App.loadPagedWidget(" + widgetid + ",$('GoPage" + widgetid + "').value);\" />";
		return OutPut;
	};
    
  function _load_widget(/*widget*/w, page) {
  	var cid = category ? category.id : '';
    var url = CommonUtil.getCurrentRootUrl() + 'u/' + user.name + '/module/' + mod_name + 
      '?cid=' + cid + '&page=' + page + '&tmp=' + (new Date()).getTime();
    logger.log('Ajax.Request to url=' + url);
    new Ajax.Request(url, { 
          method: 'get',
          onSuccess: function(xhr) { _load_success(w, xhr); },
          onException: App.Module.on_load_ex.bind(w),
          onFailure: App.Module.on_fail.bind(w)
        }
      );
  };
  
  m.load = function(widget) {
      var w = widget;     
      w.setContent($('加载中...'));
      _load_widget(w, 1);
  };
  
  m.loadPage = function(widget,pIndex) {
      var w = widget;     
      w.setContent('加载中...');
      _load_widget(w, pIndex);
  };
    

  // 设置已经加载标志
  delete m.loading;
  m.loaded = true;
  logger.log(mod_name + ' module is loaded');
  App.onModuleLoaded(mod_name);
})()
 // decl and execute init func
