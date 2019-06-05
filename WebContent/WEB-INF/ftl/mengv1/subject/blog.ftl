<div class='tab'>         
  <div id="gzs_" class='tab2'>
    <label class='titlecolor'>工作室</label>
    <div class="cur" onmouseover="TabUtil.changeTab('gzs_',0)"><a href='subjectBlogs.py?type=new&subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}'><span>最新</span></a></div>
    <div class="" onmouseover="TabUtil.changeTab('gzs_',1)"><a href='subjectBlogs.py?type=hot&subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}'><span>最热</span></a></div>
    <div class="" onmouseover="TabUtil.changeTab('gzs_',2)"><a href='subjectBlogs.py?type=rcmd&subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}'><span>推荐</span></a></div>
    <div class=""></div>         
  </div>
  <div class='tab_content' style='overflow:hidden;height:220px;'>
    <!-- 最新工作室 -->
    <div id="gzs_0" style="display: block">
  <#if new_blog_list?? >
    <#list new_blog_list as u >            
      <div>
      	<span class='border_img'>
        <a href='${SiteUrl}go.action?loginName=${u.loginName}'><img src="${SSOServerUrl +'upload/'+u.userIcon!'images/default.gif'}" width='64' height='64' border='0' /></a></span>
        <div style='font-weight:bold;'><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.blogName!?html}</a></div>
        <div>创建时间：${u.createDate?string('yyyy-MM-dd')}</div>   
        <div>简介：${u.blogIntroduce!}</div>
      </div>
      <#if u_has_next >
       <div style='height:4px;font-size:0;clear:both;'></div>
      </#if>
    </#list>
  </#if>
    </div>
    
    <!-- 最热工作室 -->
    <div id="gzs_1"  style="display: none">     
  <#if hot_blog_list?? >
    <#list hot_blog_list as u >            
      <div>
      	<span class='border_img'>
        <a href='${SiteUrl}go.action?loginName=${u.loginName}'><img src="${SSOServerUrl +'upload/'+u.userIcon!'images/default.gif'}" width='64' height='64' border='0' /></a></span>
        <div style='font-weight:bold;'><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.blogName!?html}</a></div>
        <div>创建时间：${u.createDate?string('yyyy-MM-dd')}</div>
        <div>简介：${u.blogIntroduce!}</div>
      </div>
      <#if u_has_next >
       <div style='height:4px;font-size:0;clear:both;'></div>
      </#if>
    </#list>
  </#if>
    </div>

    <!-- 推荐工作室 -->    
    <div id="gzs_2"  style="display: none">           
  <#if rcmd_blog_list?? >
    <#list rcmd_blog_list as u >            
      <div>
      	<span class='border_img'>
        <a href='${SiteUrl}go.action?loginName=${u.loginName}'><img src="${SSOServerUrl +'upload/'+u.userIcon!'images/default.gif'}" width='64' height='64' border='0' /></a></span>
        <div style='font-weight:bold;'><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.blogName!?html}</a></div>
        <div>创建时间：${u.createDate?string('yyyy-MM-dd')}</div>
        <div>简介：${u.blogIntroduce!}</div>
      </div>
      <#if u_has_next >
       <div style='height:4px;font-size:0;clear:both;'></div>
      </#if>
    </#list>
  </#if>
    </div>
    
	  <div id="gzs_3"  style="display: none"> 
	  </div>
  </div>
</div>
