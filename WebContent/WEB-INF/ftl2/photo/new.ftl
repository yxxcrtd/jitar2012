<div class="contentRelation">
	<h3><a href="${ContextPath}photos.action?type=new" class="more">更多</a>最新图片</h3>
    <div class="videoListWrap videoW1">
     <#if new_photo_list?? && new_photo_list?size &gt;0 >
            <ul class="videoList">
               <#list new_photo_list as photo>
                 <#if (photo_index &lt; 6)>
	            	<li>
	                	<a href="${ContextPath}photos.action?cmd=detail&photoId=${photo.photoId}"><img src="${Util.GetimgUrl(photo.href!,'125x100')!'images/s12_default.jpg'}" width="125" height="100" onerror="this.src='images/s12_default.jpg'"></a>
	                	<p class="videoListBg" style="opacity: 0.6;"></p>
	                    <a href="${ContextPath}photos.action?cmd=detail&photoId=${photo.photoId}" class="imageListText" title="${photo.title}">${Util.getCountedWords(photo.title,14)}</a>
	                </li>
	             </#if>
               </#list>
            </ul>
            <ul class="videoList">
            <#list new_photo_list as photo>
                 <#if (photo_index &gt;5 && photo_index &lt; 12 )>
	            	<li>
	                	<a href="${ContextPath}photos.action?cmd=detail&photoId=${photo.photoId}"><img src="${Util.GetimgUrl(photo.href!,'125x100')!'images/s12_default.jpg'}" width="125" height="100" onerror="this.src='images/s12_default.jpg'"></a>
	                	<p class="videoListBg" style="opacity: 0.6;"></p>
	                    <a href="${ContextPath}photos.action?cmd=detail&photoId=${photo.photoId}" class="imageListText" title="${photo.title}">${Util.getCountedWords(photo.title,14)}</a>
	                </li>
	             </#if>
           </#list>
           </ul>
           <ul class="videoList">
           <#list new_photo_list as photo>
                 <#if (photo_index &gt;11 && photo_index &lt; 18 )>
	            	<li>
	                	<a href="${ContextPath}photos.action?cmd=detail&photoId=${photo.photoId}"><img src="${Util.GetimgUrl(photo.href!,'125x100')!'images/s12_default.jpg'}" width="125" height="100" onerror="this.src='images/s12_default.jpg'"></a>
	                	<p class="videoListBg" style="opacity: 0.6;"></p>
	                    <a href="${ContextPath}photos.action?cmd=detail&photoId=${photo.photoId}" class="imageListText" title="${photo.title}">${Util.getCountedWords(photo.title,14)}</a>
	                </li>
	             </#if>
           </#list>
           </ul>
    </div>
    <p class="videoSlide videoS1">
       <#if new_photo_list?size &lt; 6 || new_photo_list?size == 6>
        <#list new_photo_list[0..0] as photo>
    	   <a href="javascript:;" <#if photo_index==0>class="active"</#if> >${photo_index+1}</a>
        </#list>
       <#elseif new_photo_list?size &gt; 6 && new_photo_list?size &lt; 13>
         <#list new_photo_list[0..1] as photo>
    	   <a href="javascript:;" <#if photo_index==0>class="active"</#if> >${photo_index+1}</a>
         </#list>
       <#else>
         <#list new_photo_list[0..2] as photo>
	       <a href="javascript:;" <#if photo_index==0>class="active"</#if> >${photo_index+1}</a>
	     </#list>
       </#if>
    </p>
  </#if>
</div>