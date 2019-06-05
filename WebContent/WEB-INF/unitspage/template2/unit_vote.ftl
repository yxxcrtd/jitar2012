<div class='gly'>
  <div class='gly_head'>
    <div class='gly_head_left'>&nbsp;调查投票</div>
  </div>
  <div style='padding:4px;'>
    <div id='vote'>正在加载数据……</div>
<script type='text/javascript'>
var vote_url = UnitUrl + 'mod/vote/listview.py?guid=${unit.unitGuid}&type=unit&tmp=' + Date.parse(new Date());
new Ajax.Request(vote_url, { 
  method: 'get',
  onSuccess: function(xhr){load_success(xhr); },
  onException: function(xhr){load_exception(xhr);},
  onFailure: function(){load_exception(xhr);}
}
);

function load_success(xhr)
{
  document.getElementById('vote').innerHTML = xhr.responseText;
}

function load_exception(xhr)
{
  document.getElementById('vote').innerHTML = '加载数据出现错误：' + xhr.responseText;
}
</script>

  </div>
</div>