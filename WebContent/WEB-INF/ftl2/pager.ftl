<#-- 显示当前分页对象的分页 (需要pager变量) -->
<#if pager??>
<#if pager.totalPages &gt; 1>
<div class="listPage clearfix">
    <#assign offsetCount = 3>
    <#assign current = pager.currentPage>
    <#assign pageCount = pager.totalPages>
    <#assign MaxStart = pager.totalPages - offsetCount * 2>
    <#if MaxStart &lt; 1>
        <#assign MaxStart = 1>
    </#if>            
    <#assign MinEnd = (offsetCount * 2 + 1)>
    <#assign start = (current - offsetCount)>
    <#assign end = (current + offsetCount)>
    <#if start &lt; 1>
        <#assign start = 1>
        <#assign end = MinEnd>
    </#if>
    <#if end &gt; pageCount>
        <#assign end = pageCount>
        <#assign start = MaxStart>
    </#if>
    <#assign showGoInput = false>
    <#if current &gt; 1>
        <a href="javascript:goPage(${current-1});" class="listPagePre">&lt;</a>
    </#if>
    <#if start &gt; 1>
        <a href="javascript:goPage(1);" class="listPageC">1</a>
    </#if>            
    <#if start &gt; 2>
        <#assign showGoInput = true>
        <span>...</span>
    </#if>          
    <#list start..end - 1 as i >
        <#if i == current>
            <a href="javascript:goPage(${i});" class="listPageC active">${i}</a>
        <#else>
            <a href="javascript:goPage(${i});" class="listPageC">${i}</a>
        </#if>
    </#list> 
    <#if start &lt; MaxStart>
        <#assign showGoInput = true>
        <span>...</span>
    </#if>
    <#if current == pageCount>
        <a href='javascript:goPage(${current});' class='listPageC active'>${current}</a>
    <#else>
        <a href='javascript:goPage(${pageCount});' class='listPageC'>${pageCount}</a>
    </#if>
    <#if current != pageCount>
        <a href='javascript:goPage(${current+1});' class='listPagePre'>&gt;</a>
    </#if>
    <#if showGoInput == true>
        <span class='listPageText'>跳转至</span><span class='listPageInput'><input minValue='1' maxValue='${pageCount}' value='${current}' type='text' id='userpagenum'></span><span class='listPageBtn'><input type='button' value='GO' onclick='javascript:goPageEx();'></span>
    </#if>
</div>   
<script type="text/javascript">
    function goPageEx(){
        if(document.getElementById("userpagenum").value==""){
            
        }else{
            goPage(document.getElementById("userpagenum").value);
        }
    }
    function goPage(pageIndex){
        window.location='${pager.urlPattern}'.replace('{page}', pageIndex).replace('{total}', '${pager.totalPages}');
    }
</script> 
</#if>
</#if>

