<#if article_list??>
<#if article_list.size()&gt;0>
文章：
</#if>
<ul class="listul">
<table border="0" style="width:100%">
<#list article_list as article>
    <tr>
    <td>
    <a href="${SiteUrl}showArticle.action?articleId=${article.articleId}" target="_blank">${article.title!?html}</a>
    &nbsp;&nbsp;<#if article.typeState==true>[转载]<#elseif  article.typeState==false>[原创]</#if>
    </td>
    <td style="width:80px">
        ${article.createDate?string("yyyy-MM-dd")}
    </td>
    </tr>
</#list>
</table>
</ul>
</#if>
<#if resource_list??>
<#if resource_list.size()&gt;0>
资源：
</#if>
<ul class="listul">
<table border="0" style="width:100%">
<#list resource_list as resource>
    <tr>
    <td>
    <a href='../showResource.action?resourceId=${resource.resourceId}' target='_blank'><img src='${Util.iconImage(resource.href!)}' border='0' align='absmiddle' hspace='3' />${resource.title!?html}</a>
    </td>
    <td style="width:80px">
        ${resource.createDate?string("yyyy-MM-dd")}
    </td>
    </tr>
</#list>
</table>
</ul>
</#if>
<#if photo_list??>
<#if photo_list.size()&gt;0>
图片：
</#if>
<ul class="listul">
  <#if photo_list.size() &gt; 0 >
    <#-- 定义要显示的列数 columnCount -->
    <#assign columnCount = 3>
    <#-- 计算显示当前记录集需要的表格行数 rowCount -->
    <#if photo_list.size() % columnCount == 0>
    <#assign rowCount = (photo_list.size() / columnCount) - 1>
    <#else>
    <#assign rowCount = (photo_list.size() / columnCount)>
    </#if>
     
    <#-- 输出表格 -->
    <table  cellSpacing="10" align="center" style='width:100%'>                            
    <#-- 外层循环输出表格的 tr -->
    <#list 0..rowCount as row >
    <tr>
    <#-- 内层循环输出表格的 td  -->
    <#list 0..columnCount - 1 as cell >
        <td align="center" width='${100 / columnCount}%' style='padding:8px'><br />
        <#-- 判断是否存在当前对象：存在就输出；不存在就输出空格 -->
        <#if photo_list[row * columnCount + cell]??>                     
            <#assign photo = photo_list[row * columnCount + cell]>
            <#if photo.isPrivateShow==false>
                <a href="${SiteUrl}photos.action?cmd=detail&photoId=${photo.photoId}"><img onload="CommonUtil.reFixImg(this,120,100)" src="${Util.thumbNails(photo.href!'images/default.gif')}" vspace='4' border='0' /></a><br />
                <a href="${SiteUrl}photos.action?cmd=detail&photoId=${photo.photoId}">${photo.title!?html}</a>
            <#else>
                <#assign photouser = Util.userById(photo.userId)>
                <a href='${SiteUrl}${photouser.loginName}/photo/${photo.photoId}.html'><img onload="CommonUtil.reFixImg(this,120,100)" src="${Util.thumbNails(photo.href!'images/default.gif')}" vspace='4' border='0' /></a><br />
                <a href='${SiteUrl}${photouser.loginName}/photo/${photo.photoId}.html'>${photo.title!?html}</a>
            </#if>
        </#if>
        </td>
    </#list>
    </tr>
    </#list>
    </table>
</#if>
</ul>
</#if>
<#if video_list??>
<#if video_list.size()&gt;0>
视频：
</#if>
<ul class="listul">
 <#if video_list.size() &gt; 0 >
    <#-- 定义要显示的列数 columnCount -->
    <#assign columnCount = 3>
    <#-- 计算显示当前记录集需要的表格行数 rowCount -->
    <#if video_list.size() % columnCount == 0>
    <#assign rowCount = (video_list.size() / columnCount) - 1>
    <#else>
    <#assign rowCount = (video_list.size() / columnCount)>
    </#if>
     
    <#-- 输出表格 -->
    <table  cellSpacing="10" align="center">                            
    <#-- 外层循环输出表格的 tr -->
    <#list 0..rowCount as row >
    <tr valign='top'>
    <#-- 内层循环输出表格的 td  -->
    <#list 0..columnCount - 1 as cell >
        <td align="center" width='${100 / columnCount}%'><br />
        <#-- 判断是否存在当前对象：存在就输出；不存在就输出空格 -->
        <#if video_list[row * columnCount + cell]??>                     
            <#assign video = video_list[row * columnCount + cell]>                  
            <a href='${SiteUrl}manage/video.action?cmd=show&videoId=${video.videoId}'><img border=0 src="${video.flvThumbNailHref!}" /></a><br />
            <a href='${SiteUrl}manage/video.action?cmd=show&videoId=${video.videoId}'>${video.title!?html}</a>
        <#else>
            &nbsp;
        </#if>
        </td>
    </#list>
    </tr>
    </#list>
    </table>
</#if>
</ul>
</#if>
<div style='text-align:right'>
    <#if loginUser??>
        <#assign can_manage = (group_member?? && group_member.status == 0 && group_member.groupRole >= 800) >
        <#if can_manage >
        <a href='javascript:void(0);' onclick='document.getElementById("div_w_${widgetId}").style.display="block";document.getElementById("div_c_${widgetId}").style.display="none";return false;'>修改标题</a> |
        <a href='javascript:void(0);' onclick='document.getElementById("div_w_${widgetId}").style.display="none";document.getElementById("div_c_${widgetId}").style.display="block";return false;'>设置分类</a> |
        </#if>
    </#if> 
    <a href='${SiteUrl}g/${group.groupName}/py/group_mutilcates_list.py?groupId=${group.groupId}&widgetId=${widgetId}'>全部</a>
</div>
<div id="div_w_${widgetId}" style="display:none">
    <form name="div_fw_${widgetId}" action="${SiteUrl}jython/group_mutilcates_update.py" method="post">
    <input type="text" id="wtitle_${widgetId}" name="title" value="${widget.title}"/>
    <input type="hidden" name="widgetId" value="${widget.id}"/>
    <input type="hidden" name="cmd" value="savetitle"/>
    <input type="hidden" name="groupId" value="${group.groupId}"/>
    <br/>
    <!--DivUtil.saveGroupWidgetTitle(${widget.id})-->
    <input type="submit" name="saveBtn" value="确定"/>
    <input type='button' name='cancelBtn' value='关闭' onclick='document.getElementById("div_w_${widgetId}").style.display="none";'/>
    </form>
</div>
<div id="div_c_${widgetId}" style="display:none">
    <form name="div_fc_${widgetId}" action="${SiteUrl}jython/group_mutilcates_update.py" method="post">
    <input type="hidden" name="cmd"  value="savecate"/>
    <input type="hidden" name="groupId" value="${group.groupId}"/>
    <li>选择文章分类：<#if articleCate_tree??>
                        <select id="cate_art_${widget.id}" name="cate_art_${widget.id}" style="width:100%">
                        <option value="">请选择一个文章分类</option>
                        <#list articleCate_tree.all as c> 
                         <option value="${c.id}"<#if article_cateId == c.id > selected='selected'</#if>><#if c.treeFlag2??>${c.treeFlag2 + c.name?html}<#else>${c.name?html}</#if></option>
                        </#list>
                        </select>
                <#else>
                                                请先创建群组文章分类
                </#if>    
    <#if groupMutil??>
        <li>显示文章条数：<input style='width:40px' type='text' id='articlenum_${widget.id}' name='articlenum' value='${groupMutil.articleNumShow}'/>
    <#else>
        <li>显示文章条数：<input style='width:40px' type='text' id='articlenum_${widget.id}' name='articlenum' value='10'/>
    </#if>
    <li>选择资源分类：<#if resourceCate_tree??>
                        <select id="cate_res_${widget.id}" name="cate_res_${widget.id}" style="width:100%">
                        <option value="">请选择一个资源分类</option>
                        <#list resourceCate_tree.all as c> 
                         <option value="${c.id}"<#if resource_cateId == c.id > selected='selected'</#if>><#if c.treeFlag2??>${c.treeFlag2 + c.name?html}<#else>${c.name?html}</#if></option>
                        </#list>
                        </select>
                <#else>
                                                请先创建群组资源分类
                </#if>        
    <#if groupMutil??>
        <li>显示资源个数：<input style='width:40px' type='text' id='resourcenum_${widget.id}' name='resourcenum' value='${groupMutil.resourceNumShow}'/>
    <#else>
        <li>显示资源个数：<input style='width:40px' type='text' id='resourcenum_${widget.id}' name='resourcenum' value='10'/>
    </#if>
    <li>选择图片分类：<#if photoCate_tree??>
                        <select id="cate_pho_${widget.id}" name="cate_pho_${widget.id}" style="width:100%">
                        <option value="">请选择一个图片分类</option>
                        <#list photoCate_tree.all as c> 
                         <option value="${c.id}"<#if photo_cateId == c.id > selected='selected'</#if>><#if c.treeFlag2??>${c.treeFlag2 + c.name?html}<#else>${c.name?html}</#if></option>
                        </#list>
                        </select>
                <#else>
                                                请先创建群组图片分类
                </#if>            
    <#if groupMutil??>
        <li>显示图片个数：<input style='width:40px'  type='text' id='photonum_${widget.id}' name='photonum' value='${groupMutil.photoNumShow}'/>
    <#else>
        <li>显示图片个数：<input style='width:40px'  type='text' id='photonum_${widget.id}' name='photonum' value='3'/>
    </#if>    
    <li>选择视频分类：<#if videoCate_tree??>
                        <select id="cate_vio_${widget.id}" name="cate_vio_${widget.id}" style="width:100%">
                        <option value="">请选择一个视频分类</option>
                        <#list videoCate_tree.all as c> 
                         <option value="${c.id}"<#if video_cateId == c.id > selected='selected'</#if>><#if c.treeFlag2??>${c.treeFlag2 + c.name?html}<#else>${c.name?html}</#if></option>
                        </#list>
                        </select>
                <#else>
                                                请先创建群组视频分类
                </#if>      
    <#if groupMutil??>
        <li>显示视频个数：<input style='width:40px'  type='text' id='videonum_${widget.id}' name='videonum' value='${groupMutil.videoNumShow}'/>
    <#else>
        <li>显示视频个数：<input style='width:40px'  type='text' id='videonum_${widget.id}' name='videonum' value='3'/>
    </#if>    
    <input type="hidden" name="widgetId" value="${widget.id}"/><br/>
    <input type="submit" name="saveBtn" value="确定"/>
    <input type='button' name='cancelBtn' value='关闭' onclick='document.getElementById("div_c_${widgetId}").style.display="none";'/>
    </form>
</div>