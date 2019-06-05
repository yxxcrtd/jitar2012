<div class='panel'>
  <div class='panel_head'>    
    <div class='panel_head_left'>${webpart.displayName!?html}</div>
  </div>
  <div class='panel_content'>
    <div id='topic'>正在加载数据……</div>
	<script type='text/javascript'>
	(function(){
	var vote_questionanswer_url = SubjectUrl + 'mod/topic/listview.py?guid=${subject.subjectGuid}&type=subject&unitId=${unitId!}&tmp=' + Date.parse(new Date());
	new Ajax.Request(vote_questionanswer_url, { 
	  method: 'get',
	  onSuccess: function(xhr){load_success(xhr); },
	  onException: function(xhr){load_exception(xhr);},
	  onFailure: function(){load_exception(xhr);}
	}
	);
	
	function load_success(xhr)
	{
	  document.getElementById('topic').innerHTML = xhr.responseText;
	}
	
	function load_exception(xhr)
	{
	  document.getElementById('topic').innerHTML = '加载数据出现错误：' + xhr.responseText;
	}
	})()
	</script>
  </div></div>