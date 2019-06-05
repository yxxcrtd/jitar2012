<form name='searchForm' action='articles.py' method='get'>
<input type="hidden" name="type" value="search" />
<script>
function do_search() {
  var f = document.searchForm;
  var oc = f.oc.value;
  if (oc == 'r')
    f.action = 'resources.action';
  else if (oc == 'b')
    f.action = 'blogList.action';
  else if (oc == 'g')
    f.action = 'groups.action';
  else
    f.action = 'articles.action';
  f.submit();
}
</script>
<img src='${SiteThemeUrl}search.gif' align='absmiddle' /> 关键字：<input name='k' value='' /> 
 类型：<select name='oc'><option value='a'>文章</option><option value='r'>资源</option><option value='b'>工作室</option><option value='g'>协作组</option></select> 
<img src='${SiteThemeUrl}search2.gif' align='absmiddle' onclick='do_search();' />
</form>