<div class="contentRelation">
	<h3><a href="${ContextPath}photos.action?type=hot" class="more">更多</a>热门图片</h3>
	<#if hot_photo_list?? && hot_photo_list?size &gt;0 >
	  <#list hot_photo_list as photo>
	   <#if photo_index &lt; 3 >
		    <dl class="hotVideo">
		    	<dt><a href="${ContextPath}photos.action?cmd=detail&photoId=${photo.photoId}"><img src="${Util.GetimgUrl(photo.href,'70x70')!'images/s11_default.jpg'}" onerror="this.src='images/s11_default.jpg'"></a></dt>
		        <dd>
		        	<p><a href="${ContextPath}photos.action?cmd=detail&photoId=${photo.photoId}" title="${photo.title}">${Util.getCountedWords(photo.title,20)}</a></p>
		            <span>上传时间:${photo.createDate?string("MM-dd")}</span>
		        </dd>
		    </dl>
	   </#if>
	   </#list>
	    <ul class="hotImageNews">
	      <#list hot_photo_list as photo>
           <#if photo_index &lt; 6 && photo_index &gt;2>	       
	    	 <li><a href="${ContextPath}photos.action?cmd=detail&photoId=${photo.photoId}" title="${photo.title}">${Util.getCountedWords(photo.title,14)}</a></li>
	       </#if>
	      </#list>
	    </ul>
   </#if>
</div>