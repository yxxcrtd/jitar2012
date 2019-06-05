/**
 * 用户相册 模块实现.
 * .name = 'flash_photo'
 */
(function() {
  logger.log('flash_photo module js is loading...');
  var m = App._module_infos.flash_photo; 
  // 如果已经加载过了，则不要重复加载
  if (m.loaded) return;

  // ajax success handler
  function _load_success(w, xhr) {
  	// xhtml format
    var xhtml = xhr.responseText;
    w.setContent(xhtml);
  }
  
  // load flash_photo content
  function _load_flash_photo(/*widget*/w) {
  	var count = 4;
  	var fwidth = 200;
    var fheight = 200;
    var ftxtheight = 36;
    var fbgcolor= '#E5ECF4';
  	if(typeof w.data != 'undefined' && typeof w.data.count != 'undefined' )
  	{
  		count = w.data.count;
  	}
  	if(typeof w.data != 'undefined' && typeof w.data.fwidth != 'undefined' )
  	{
  		fwidth = w.data.fwidth;
  	}
  	if(typeof w.data != 'undefined' && typeof w.data.fheight != 'undefined' )
  	{
  		fheight = w.data.fheight;
  	}
  	if(typeof w.data != 'undefined' && typeof w.data.ftxtheight != 'undefined' )
  	{
  		ftxtheight = w.data.ftxtheight;
  	}
  	if(typeof w.data != 'undefined' && typeof w.data.fbgcolor != 'undefined' )
  	{
  		fbgcolor = w.data.fbgcolor;
  	}
  	  	
  	var url = CommonUtil.getCurrentRootUrl() + 'js/jitar/module/flash_photo.py?userId=' + user.id + '&count=' + count + 
  			 '&fwidth=' + fwidth + '&fheight=' + fheight + '&ftxtheight=' + ftxtheight + '&fbgcolor=' + encodeURIComponent(fbgcolor) +
  			 '&tmp=' + (new Date()).getTime();
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
      logger.log('flash_photo module.load() is called widget = ' + w);
      w.setTitle(w.title);
      w.setContent($L('加载中...'));
       if(w._btn_edit)
      {
        w._btn_edit.onclick = function() {
         m.edit(w);
        }
      }      
      _load_flash_photo(widget);
  };
    
   
  m.edit = function(widget) {
    var w = widget;   
    var t = DivUtil.getElementByClassName($('webpart_' + w.id),'widgetEditor');   
    if(t)
    {
      var count = 4;
      var fwidth = 200;
      var fheight = 200;
      var ftxtheight = 36;
      var fbgcolor= '#E5ECF4';
      if(typeof w.data.count != 'undefined')
      {
        count = w.data.count;
      }
      
      if(typeof w.data.fwidth != 'undefined')
      {
        fwidth = w.data.fwidth;
      }
      
      if(typeof w.data.fheight != 'undefined')
      {
        fheight = w.data.fheight;
      }
      
      
      if(typeof w.data.ftxtheight != 'undefined')
      {
        ftxtheight = w.data.ftxtheight;
      }
      
      if(typeof w.data.fbgcolor != 'undefined')
      {
        fbgcolor = w.data.fbgcolor;
      }
      
      
      t.style.display = '';
      t.cells[1].innerHTML = "<div style='text-align:center;'>" +
              "显示张数：<input id='FlashPhotoListCount_" + w.id + "' value='" + count + "' style='width:60%;' /><br/>" + 
              "显示宽度：<input id='FlashPhotoWidth_" + w.id + "' value='" + fwidth + "' style='width:60%;' /><br/>" + 
              "显示高度：<input id='FlashPhotoHeight_" + w.id + "' value='" + fheight + "' style='width:60%;' /><br/>" + 
              "文字高度：<input id='FlashPhotoTextHeight_" + w.id + "' value='" + ftxtheight + "' style='width:60%;' /><br/>" + 
              "背景颜色：<input id='FlashPhotoBgColor_" + w.id + "' value='" + fbgcolor + "' style='width:60%;' /><br/>" +
              "<input type='button' class='button' value=' 保  存 ' onclick='saveFlashPhoto(" + w.id + ");DivUtil.hideWidgetEditor(" + w.id + ");' />" +
              " <input type='button' class='button' value=' 关  闭 ' onclick='DivUtil.hideWidgetEditor(" + w.id + ")' />" +
              "</div>";     
    }   
  };
  
  delete m.loading;
  m.loaded = true;
  logger.log('flash_photo module js is loaded');
  App.onModuleLoaded('flash_photo');
}) (); // decl and execute init func

function saveFlashPhoto(wid)
{
 var w = App.getWidgetById(wid);
 if(w)
 {
 	w.setContent('正在进行保存……');
 	var count = $("FlashPhotoListCount_"+wid).value;
    var fwidth = $("FlashPhotoWidth_"+wid).value;
    var fheight = $("FlashPhotoHeight_"+wid).value;
    var ftxtheight = $("FlashPhotoTextHeight_"+wid).value;
    var fbgcolor= $("FlashPhotoBgColor_"+wid).value;
	if(isNaN(count)) count = 4;
	if(isNaN(fwidth)) fwidth = 200;
	if(isNaN(fheight)) fheight = 200;
	if(isNaN(ftxtheight)) ftxtheight = 36;
	if(fbgcolor == '') fbgcolor = '#E5ECF4';
	
	var widgetData = {"count":count,"fwidth":fwidth,"fheight":fheight,"ftxtheight":ftxtheight,"fbgcolor":fbgcolor};
	var postData = 'data=' + JSON.stringify(widgetData);
	var url = JITAR_ROOT + 'manage/page.action?cmd=update_data&widgetId=' + wid;
	new Ajax.Request(url, { 
	    method: 'post',
	    parameters:postData,
	    onSuccess:function(xhr){
		   	if(xhr.responseText.replace(/(^\s*)|(\s*$)/g, "") == '200 OK')
		   	{
		      w.data = widgetData;
		      App._module_infos[w.module].load(w);
		    }
		    else
		    {
		      alert('修改数据失败：'+ xhr.responseText);
		      App._module_infos[w.module].load(w);
		    }
	    },
	    onFailure:function(xhr){alert('修改数据失败。' + xhr.responseText);
	    App._module_infos[w.module].load(w);
	    }
	    }
	  );		
	}
  else
  {
	alert('没有找到该内容块。');
  }
}