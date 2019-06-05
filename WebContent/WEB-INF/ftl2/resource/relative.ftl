<#assign iCount = 0>
<div class="contentRelation">
    <h3><!--<a href="#" class="more">更多</a>-->相关资源</h3>
    <div class="textWrap">
        <#if cate_resource_list?? && cate_resource_list?size &gt;0 >
            <#list cate_resource_list as resource>
                <#if resource_index == 0 || resource_index == 10 || resource_index == 20>
                    <ul class="ulList">
                    <#assign iCount = iCount+1>
                </#if>
                <li class="${Util.iconCss(resource.href!)}"><em class="emDate">${resource.createDate?string("MM-dd")}</em><a title="${resource.title}" href="${ContextPath}showResource.action?resourceId=${resource.resourceId}">${Util.getCountedWords(resource.title,14)}</a></li>
                <#if resource_index == 9 || resource_index == 19>
                    </ul>
                </#if>
            </#list>
            <#if cate_resource_list?size &lt; 31 && cate_resource_list?size != 10 && cate_resource_list?size != 20>
                </ul>
            </#if>
        </#if>
    </div>
    <p class="textSlide">
    <#if iCount &gt; 1>  
        <#list 0..iCount-1 as i>
        <a href="javascript:;"<#if i==0> class="active"</#if>>${i+1}</a>
        </#list>
    </#if>    
    </p>
</div>