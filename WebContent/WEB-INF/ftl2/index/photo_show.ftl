<!--热门图片 Start-->
<div class="main photoWrap border mt3">
    <h3 class="h3Head">
        <a href="photos.action?type=new" class="more">更多</a>
        <a href="photos.action?type=hot" class="more none">更多</a>
        <a href="photos.action?type=new" class="sectionTitle active">最新图片<span></span></a>
        <a href="photos.action?type=hot" class="sectionTitle">&nbsp;&nbsp;热门&nbsp;&nbsp;<span></span></a>
    </h3>
    <div class="photo">
	    <#if new_photo_list?? && new_photo_list?size &gt; 0>
	      <div class="photoStyle1">
	        <#list new_photo_list as photo>
			  	<#if photo_index==0> <!--第一个图片-->
			  	   <!--图片大小 200*240-->
		            <a class = "photoStyle1H"  href="${ContextPath}photos.action?cmd=detail&photoId=${photo.id}">
		              <img src="${Util.GetimgUrl(photo.href!"",'200x240')}" alt='${photo.title!?html}'/>
		            </a>
		            <div class="tx">
		              <p><a href="${ContextPath}photos.action?cmd=detail&photoId=${photo.id}">${photo.title!?html}</a></p>
		            </div>
	       		  <!--200*240 End-->
			  	</#if>
	    </#list>
	   </div>
	   <div class="photoRight">
		   <#list new_photo_list as photo>
		      	<#if (photo_index > 0)&&(6 > photo_index) > <!--上面的5涨 图片-->
				  	  <!--图片大小160*120-->
			            <div class="photoStyle2">
			                <a class="photoStyle2H" href="${ContextPath}photos.action?cmd=detail&photoId=${photo.id}">
			                 <img src="${Util.GetimgUrl(photo.href!"",'160x120')}" alt='${photo.title!?html}'/>
			                </a>
			                <div class="tx">
	                        <p>
	                          <a href="${ContextPath}photos.action?cmd=detail&photoId=${photo.id}">${photo.title!?html}</a>
	                        </p>
			                </div>
			            </div>
		            <!--160*120 End-->
		        <#elseif (photo_index >= 6)&&(10 > photo_index)><!--下面5张图片-->
		           <!--图片大小200*120-->
		            <div class="photoStyle3">
		                <a class="photoStyle3H"  href="${ContextPath}photos.action?cmd=detail&photoId=${photo.id}">
		                  <img src="${Util.GetimgUrl(photo.href!"",'200x120')}" alt='${photo.title!?html}'/>
		                </a>
		                <div class="tx">
		                  <p><a href="${ContextPath}photos.action?cmd=detail&photoId=${photo.id}">${photo.title!?html}</a></p>
		                </div>
		            </div>
	           	  <!--200*120 End-->
				</#if>
		   </#list>
	     </div>
	  </#if>
 </div>
    
 <div class="photo none">
     <#if hot_photo_list??>
        <div class="photoRight">
         <#list hot_photo_list as hot_photo>
           <input type="hidden" id="picture_show_right${hot_photo_index}" value="${hot_photo.href!}"/>
            <!--图片大小200*120-->
           <#if (4 > hot_photo_index) >
            <div class="photoStyle3">
                <a class="photoStyle3H" href="${ContextPath}photos.action?cmd=detail&photoId=${hot_photo.id}">
                  <img src="${Util.GetimgUrl(hot_photo.href!"",'200x120')}"  alt='${hot_photo.title!?html}'/>
                </a>
                <div class="tx">
                  <p><a href="${ContextPath}photos.action?cmd=detail&photoId=${hot_photo.id}">${hot_photo.title!?html}</a></p>
                </div>
            </div>
            <!--200*120 End-->
            <#elseif (hot_photo_index >= 4)&&(9 > hot_photo_index)>
            <!--图片大小160*120-->
            <div class="photoStyle2">
                <a  class="photoStyle2H"  href="${ContextPath}photos.action?cmd=detail&photoId=${hot_photo.id}.html">
                  <img src="${Util.GetimgUrl(hot_photo.href!"",'160x120')}" alt='${hot_photo.title!?html}'/>
                </a>
                <div class="tx">
                  <p><a href="${ContextPath}photos.action?cmd=detail&photoId=${hot_photo.id}">${hot_photo.title!?html}</a></p>
                </div>
            </div>
            <!--160*120 End-->
           </#if>
         </#list>
      </div>
      <#list hot_photo_list as hot_photo>
	    <#if hot_photo_index == 9>
	    	<!--图片大小 200*240-->
		    <div class="photoStyle1">
		        <a class="photoStyle1H" href="${ContextPath}photos.action?cmd=detail&photoId=${hot_photo.id}">
		          <img src="${Util.GetimgUrl(hot_photo.href!"",'200x240')}" alt='${hot_photo.title!?html}'/>
		        </a>
		        <div class="tx">
		          <p><a href="${ContextPath}photos.action?cmd=detail&photoId=${hot_photo.id}">${hot_photo.title!?html}</a></p>
		        </div>
		    </div>
	        <!--200*240 End-->
        </#if>
      </#list>
    </div>
 </#if>
</div>
<!--热门图片 End-->