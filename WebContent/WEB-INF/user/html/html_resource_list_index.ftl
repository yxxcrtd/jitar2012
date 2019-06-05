<div id="_content">
<#include "html_resource_list.ftl">
</div>
<div id="__pager" class="pagination"></div>
<script type="text/javascript">
function getContent(pageNo)
{
 var url = "${UserSiteUrl}html/resource_${cateId}_";
 url = url + pageNo + ".html?seed=" + (new Date()).valueOf();
 new Ajax.Request(url, { 
      method: 'get',
      onSuccess: function(xhr) {
       document.getElementById("_content").innerHTML = xhr.responseText;
      },
      onException: function(xhr){
      document.getElementById("_content").innerHTML = xhr.responseText;
      },
      onFailure: function(){
        document.getElementById("_content").innerHTML = xhr.responseText;
      }
    }
  );
}

function pageInit()
{
 var p = new Pager("__pager",${pageCount},10,getContent);
 p.build();
}

var oldOnload = window.onload;
if (typeof(window.onload) != "function")
{
 window.onload = pageInit;
}
else
{
 window.onload = function()
 {
  oldOnload();
  pageInit();
 }
}
</script>