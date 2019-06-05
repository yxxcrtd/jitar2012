<!--优秀名师 start-->
<div class="leftWidth msWrap border mt3">
    <h3 class="h3Head">
        <a href="blogList.action?type=1" class="more">更多</a>
        <a href="blogList.action?type=3" class="more none">更多</a>
        <a href="blogList.action?type=5" class="more none">更多</a>
        <a href="blogList.action?type=4" class="more none">更多</a>
        <a href="blogList.action?type=1" class="sectionTitle active">优秀名师<span></span></a>
        <a href="blogList.action?type=3" class="sectionTitle">学科带头人<span></span></a>
        <a href="blogList.action?type=5" class="sectionTitle">教研之星<span></span></a>
        <a href="blogList.action?type=4" class="sectionTitle">教研员<span></span></a>
    </h3>
    
    <div class="stadio" style="display: block;">
    <#if famous_teachers?? && (famous_teachers?size &gt; 0)>
        <ul class="imgList">
        	<#list  famous_teachers as u>
        	  <#if u_index &lt; 3>
	              <li>
	                  <a href="go.action?loginName=${u.loginName}" target='_blank' class="imgLi"><img width='116' height="116" src="${(SSOServerUrl +'upload/'+ (u.userIcon))!"images/default.gif"}" onerror="this.src='images/default.gif'"/></a>
	                  <a href="go.action?loginName=${u.loginName}"  target='_blank' title="${u.trueName!}">${Util.getCountedWords(u.trueName!?html,8,1)}</a>
	                  <div class ="imgListBg">
	                     <a class="imgLi" href="go.action?loginName=${u.loginName}"></a>
	                  </div>
	              </li>
             </#if>
            </#list>
            <li class="tag">
               <#list  famous_teachers as u>
                 <#if u_index &gt; 2 && u_index &lt; 9>
                   <a href="go.action?loginName=${u.loginName}" target='_blank' title="${u.trueName!}">${Util.getCountedWords(u.trueName!?html,4,1)}</a>
                 </#if>
               </#list>
            </li>
        </ul>
    </#if>
    </div>
    
      <div class="stadio ms none" style="display: none;">
      <#if expert_list?? && (expert_list?size &gt; 0) >
          <ul class="imgList">
             <#list expert_list as u>
              <#if u_index &lt; 3>
	              <li>
	                  <a href="go.action?loginName=${u.loginName}" target='_blank' class="imgLi"><img width='116' height="116" src="${(SSOServerUrl +'upload/'+ (u.userIcon))!"images/default.gif"}" onerror="this.src='${SiteUrl}images/default.gif'"/></a>
	                  <a href="go.action?loginName=${u.loginName}" target='_blank' title="${u.trueName!}">${Util.getCountedWords(u.trueName!?html,8,1)}</a>
	                  <div class ="imgListBg">
	                     <a class="imgLi" href="go.action?loginName=${u.loginName}"></a>
	                  </div>
	              </li>
              </#if>
             </#list>
              <li class="tag">
                 <#list expert_list as u>
                  <#if u_index &gt; 2 && u_index &lt; 9>
                  	<a href="go.action?loginName=${u.loginName}" target='_blank' title="${u.trueName!}">${Util.getCountedWords(u.trueName!?html,4,1)}</a>
                  </#if>
                 </#list>
              </li>
          </ul>
        </#if>
      </div>
    
    
    <div class="stadio none"  style="display: none;">
    <#if teacher_star?? && (teacher_star?size>0)>
          <ul class="imgList">
             <#list teacher_star as teacher_star1>
              <#if teacher_star1_index &lt; 3>
                <li>
                    <a href="go.action?userId=${teacher_star1.userId}" class="imgLi">
                    	<img target='_blank' width='116' height="116" onerror="this.src='images/default.gif'" src="${(SSOServerUrl +'upload/'+ (teacher_star1.userIcon))!"images/default.gif"}" />
                    </a>
                    <a target='_blank' href="go.action?userId=${teacher_star1.userId}" title="${teacher_star1.blogName!}">${Util.getCountedWords(teacher_star1.blogName!?html,8,1)}</a>
                    <div class ="imgListBg">
                     		<a class="imgLi" href="go.action?userId=${teacher_star1.userId}"></a>
                  	</div>
                </li>
               </#if>
             </#list>
              <li class="tag">
                 <#list teacher_star as teacher_star2>
                  <#if teacher_star2_index &gt; 2 && teacher_star2_index &lt; 9 >
                  	<a target='_blank' href="go.action?userId=${teacher_star2.userId}" title="${teacher_star2.blogName!}">${Util.getCountedWords(teacher_star2.blogName!?html,4,1)}</a>
                  </#if>
                 </#list>
              </li>
          </ul>
    </#if>
    </div>
    
   <div class="stadio none" class="stadio none"  style="display: none;">
      <#if instructor_list?? && (instructor_list?size>0)>
        <ul class="imgList">
           <#list instructor_list as u>
             <#if u_index &lt; 3>
              <li>
                  <a href="${SiteUrl}go.action?loginName=${u.loginName}" class="imgLi"><img width='116' height="116" src="${(SSOServerUrl +'upload/'+ (u.userIcon))!"images/default.gif"}" onerror="this.src='${SiteUrl}images/default.gif'" /></a>
                  <a href="${SiteUrl}go.action?loginName=${u.loginName}" title="${u.trueName!}">${Util.getCountedWords(u.trueName!?html,8,1)}</a>
                  <div class ="imgListBg">
                     	<a class="imgLi" href="${SiteUrl}go.action?loginName=${u.loginName}"></a>
                  </div>
              </li>
             </#if>
         </#list>
         <li class="tag">
         <#list instructor_list as u>
            <#if u_index &gt;2 && u_index &lt; 9>
              <a href="${SiteUrl}go.action?loginName=${u.loginName}"  title="${u.trueName!}">${Util.getCountedWords(u.trueName!?html,4,1)}</a>
            </#if>
           </#list>
           </li>
        </ul>
    </#if>
  </div>
<div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize2" /></div>
</div>
<!--优秀名师 End-->