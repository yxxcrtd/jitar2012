<div class='b1' style='height:auto'>
  <div class='b1_head'>
    <div class='b1_head_right'></div>
    <div class='b1_head_left'>&nbsp;<img src='${SiteThemeUrl}j.gif' />&nbsp;区县平台</div>
  </div>
  <div class='b1_content' id="mashup_content">
  </div>
<script>
var mushup_url = '${SiteUrl}mashup/get_mashup_content.py?tmp=' + Date.parse(new Date());
new Ajax.Request(mushup_url, { 
  method: 'get',
  onSuccess: function(xhr){load_success(xhr); },
  onException: function(xhr){load_exception(xhr);},
  onFailure: function(){load_exception(xhr);}
}
);

function load_success(xhr)
{
  document.getElementById('mashup_content').innerHTML = xhr.responseText;
}

function load_exception(xhr)
{
  document.getElementById('mashup_content').innerHTML = '加载数据出现错误：' + xhr.responseText;
}
</script>
</div>