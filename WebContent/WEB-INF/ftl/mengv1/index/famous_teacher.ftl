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
            <#if famous_teachers?? >
            <div class="stadio ms">
                <ul class="imgList">
                    <#assign famous_teacher = famous_teachers[0..2]>
                	<#list  famous_teacher as u>
	                    <li>
	                        <a href="go.action?loginName=${u.loginName}" target='_blank' class="imgLi"><img width='116' height="116" src="${SSOServerUrl +"upload/"+u.userIcon!"images/default.gif"}" onerror="this.src='images/default.gif'"/></a>
	                        <a href="go.action?loginName=${u.loginName}"  target='_blank'>${Util.getCountedWords(u.trueName!?html,4)}</a>
	                        <div class ="imgListBg">
	                           <a class="imgLi" href="go.action?loginName=${u.loginName}"></a>
	                        </div>
	                    </li>
                    </#list>
                    <#assign famous_teacher1 = famous_teachers[3..8]>
                    <li class="tag">
                       <#list  famous_teacher1 as u>
                           <a href="go.action?loginName=${u.loginName}" target='_blank'>${Util.getCountedWords(u.trueName!html,4)}</a>
                       </#list>
                    </li>
                </ul>
            </div>
            </#if>
            
            <#if expert_list?? >
            <#assign expert_s = expert_list[0..2]>
            <#assign expert_s1 = expert_list[3..8]>
	            <div class="stadio ms none">
	                <ul class="imgList">
	                   <#list expert_s as u>
	                    <li>
	                        <a href="go.action?loginName=${u.loginName}" target='_blank' class="imgLi"><img width='116' height="116" src="${SiteThemeUrl}spacer1.gif" /></a>
	                        <a href="go.action?loginName=${u.loginName}" target='_blank'>${Util.getCountedWords(u.trueName!?html,4)}</a>
	                        <div class ="imgListBg">
	                           <a class="imgLi" href="go.action?loginName=${u.loginName}"></a>
	                        </div>
	                    </li>
	                   </#list>
	                    <li class="tag">
	                       <#list expert_s1 as u>
	                        	<a href="go.action?loginName=${u.loginName}" target='_blank'>${Util.getCountedWords(u.trueName!?html,4)}</a>
	                       </#list>
	                    </li>
	                </ul>
	            </div>
            </#if>
            
            <#if teacher_star??>
            <#assign teacher_3 = teacher_star[0..2]>
            <#assign teacher_6 = teacher_star[3..8]>
		        <div class="stadio ms none">
	                <ul class="imgList">
	                   <#list teacher_3 as teacher_star1>
		                    <li>
		                        <a href="go.action?userId=${teacher_star1.userId}" class="imgLi">
		                        	<img target='_blank' width='116' height="116" onerror="this.src='images/default.gif'" src="${SSOServerUrl +'upload/'+teacher_star1.userIcon!'images/default.gif'}" />
		                        </a>
		                        <a target='_blank' href="go.action?userId=${teacher_star1.userId}">${Util.getCountedWords(teacher_star1.blogName!?html,4)}</a>
		                        <div class ="imgListBg">
	                           		<a class="imgLi" href="go.action?userId=${teacher_star1.userId}"></a>
	                        	</div>
		                    </li>
	                   </#list>
	                    <li class="tag">
	                       <#list teacher_6 as teacher_star2>
	                        	<a target='_blank' href="go.action?userId=${teacher_star2.userId}">${Util.getCountedWords(teacher_star2.blogName!?html,4)}</a>
	                       </#list>
	                    </li>
	                </ul>
		        </div>
            </#if>
            
           <div class="stadio none">
              <#if comissioner_list??>
                <ul class="imgList">
                   <#list comissioner_list[0..2] as u>
	                    <li>
	                        <a href="${SiteUrl}go.action?loginName=${u.loginName}" class="imgLi"><img width='116' height="116" src="${SSOServerUrl +'upload/'+u.userIcon!"images/default.gif"}" this.src='${SiteUrl}images/default.gif' /></a>
	                        <a href="${SiteUrl}go.action?loginName=${u.loginName}">${Util.getCountedWords(u.trueName!?html,4)}</a>
	                        <div class ="imgListBg">
	                           	<a class="imgLi" href="${SiteUrl}go.action?loginName=${u.loginName}"></a>
	                        </div>
	                    </li>
	               </#list>
	               <li class="tag">
	               <#list comissioner_list[3..8] as u>
	                    <a href="${SiteUrl}go.action?loginName=${u.loginName}">${Util.getCountedWords(u.trueName!?html,4)}</a>
                   </#list>
                   </li>
                </ul>
	          </#if>
          </div>
        <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize2" /></div>
      </div>
        <!--优秀名师 End-->