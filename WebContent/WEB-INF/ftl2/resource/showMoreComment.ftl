<#if comment_list??>
<#list comment_list as comment>
<#if comment.userId??>
<#assign senderUser = Util.userById(comment.userId)>
<dl cmtid="${comment.id}">
  <dt><a target="_blank" href="${ContextPath}go.action?userId=${comment.userId}"><img src="${SSOServerUrl +'upload/'+ senderUser.userIcon!""}" width="50" onerror="this.src='${ContextPath}images/default.gif'"/></a></dt>
    <dd>
      <div class="replyContent" id="replyContent${comment.id}">
          <div id="r${comment.id}">
                <h4><em class="emDate">${comment.createDate?string("yyyy-MM-dd HH:mm:ss")}</em><a target="_blank" href="${ContextPath}go.action?userId=${comment.userId}">${comment.userName!"未知"}</a></h4>
                <p class="mt10">${comment.content}</p>
                <p class="clearfix">
                <a href="javascript:void(0);" class="replyBtn" onclick="replay(${comment.id},${comment.id})">回复</a>
                <#if loginUser?? && loginUser.userId == comment.userId>
                  <a href="javascript:void(0);" class="removeBtn" commentid="${comment.id}" isreply="0">删除</a>
                </#if>
                </p>
            </div>
            <!--回复中的回复内容 Start-->
            <#if comment.replyList??>
            <#list comment.replyList as reply>
            <#if reply.userId??>
            <#assign senderUser = Util.userById(reply.userId)>
            <#assign aboutUser = Util.userById(reply.aboutUserId)>
            <dl id="r${reply.id}">
                <dt><a href="${ContextPath}go.action?loginName=${senderUser.loginName!}" target="_blank"><img src="${SSOServerUrl +'upload/'+ senderUser.userIcon!""}" width="50" border="0" onerror="this.src='${ContextPath}images/default.gif'"/></a></dt>
                <dd>
                    <h4><em class="emDate">${reply.createDate?string("yyyy-MM-dd HH:mm:ss")}</em><a target="_blank" href="${ContextPath}go.action?loginName=${senderUser.loginName!}">${senderUser.trueName!"未知"}</a></h4>
                    <p class="mt10">${reply.content}</p>
                    <p class="clearfix">
                      <a href="javascript:void(0);" class="replyBtn" id="rb2" onclick="replay(${reply.id},${comment.id})">回复</a>
                      <#if loginUser?? && loginUser.userId == reply.userId>
                        <a href="javascript:void(0);" class="removeBtn" commentid="${reply.id}" isreply="1">删除</a>
                      </#if>
                      <span class="replyText">回复给</span> <a target="_blank" href="${ContextPath}go.action?loginName=${aboutUser.loginName}" class="replyYou">${aboutUser.trueName!"未知"}</a>
                    </p>
                </dd>
                <span class="replyArrow"></span>
            </dl>
            <#else>
            <#assign aboutUser = Util.userById(reply.aboutUserId)>
            <dl id="r${reply.id}">
                <dt><img width="50" src="${ContextPath}images/default.gif"/></dt>
                <dd>
                    <h4><em class="emDate">${reply.createDate?string("yyyy-MM-dd HH:mm:ss")}</em>匿名用户</h4>
                    <p class="mt10">${reply.content}</p>
                    <p class="clearfix">
                      <a href="javascript:void(0);" class="replyBtn" id="rb2" onclick="replay(${reply.id},${comment.id})">回复</a>                      
                      <span class="replyText">回复给</span> <a target="_blank" href="${ContextPath}go.action?loginName=${aboutUser.loginName!}" class="replyYou">${aboutUser.trueName!"未知"}</a>
                    </p>
                </dd>
                <span class="replyArrow"></span>
            </dl>
            </#if>
            </#list>
            </#if>
            <!--回复中的回复内容 End-->
        </div>
    </dd>
</dl>
<#else>
<dl cmtid="${comment.id}">
  <dt><img width="50" src="${ContextPath}images/default.gif"/></dt>
    <dd>
      <div class="replyContent" id="replyContent${comment.id}">
          <div id="r${comment.id}">
                <h4><em class="emDate">${comment.createDate?string("yyyy-MM-dd HH:mm:ss")}</em>匿名用户</h4>
                <p class="mt10">${comment.content}</p>
                <p class="clearfix">
                <a href="javascript:void(0);" class="replyBtn" onclick="replay(${comment.id},${comment.id})">回复</a>               
                </p>
            </div>
            <!--回复中的回复内容 Start-->
            <#if comment.replyList??>
            <#list comment.replyList as reply>
            <#if reply.userId??>
            <#assign senderUser = Util.userById(reply.userId)>
            <#assign aboutUser = Util.userById(reply.aboutUserId)>
            <dl id="r${reply.id}">
                <dt><a href="${ContextPath}go.action?loginName=${senderUser.loginName!}" target="_blank"><img src="${SSOServerUrl +'upload/'+ senderUser.userIcon!""}" width="50" border="0" onerror="this.src='${ContextPath}images/default.gif'"/></a></dt>
                <dd>
                    <h4><em class="emDate">${reply.createDate?string("yyyy-MM-dd HH:mm:ss")}</em><a target="_blank" href="${ContextPath}go.action?loginName=${senderUser.loginName!}">${senderUser.trueName!"未知"}</a></h4>
                    <p class="mt10">${reply.content}</p>
                    <p class="clearfix">
                      <a href="javascript:void(0);" class="replyBtn" id="rb2" onclick="replay(${reply.id},${comment.id})">回复</a>
                      <#if loginUser?? && loginUser.userId == reply.userId>
                        <a href="javascript:void(0);" class="removeBtn" commentid="${reply.id}" isreply="1">删除</a>
                      </#if>
                      <span class="replyText">回复给</span> <a target="_blank" href="${ContextPath}go.action?loginName=${aboutUser.loginName}" class="replyYou">${aboutUser.trueName!"未知"}</a>
                    </p>
                </dd>
                <span class="replyArrow"></span>
            </dl>
            <#else>
            <#assign aboutUser = Util.userById(reply.aboutUserId)>
            <dl id="r${reply.id}">
                <dt><img width="50" src="${ContextPath}images/default.gif"/></dt>
                <dd>
                    <h4><em class="emDate">${reply.createDate?string("yyyy-MM-dd HH:mm:ss")}</em>匿名用户</h4>
                    <p class="mt10">${reply.content}</p>
                    <p class="clearfix">
                      <a href="javascript:void(0);" class="replyBtn" id="rb2" onclick="replay(${reply.id},${comment.id})">回复</a>                    
                      <span class="replyText">回复给</span> <a target="_blank" href="${ContextPath}go.action?loginName=${aboutUser.loginName}" class="replyYou">${aboutUser.trueName!"未知"}</a>
                    </p>
                </dd>
                <span class="replyArrow"></span>
            </dl>
            </#if>
            </#list>
            </#if>
            <!--回复中的回复内容 End-->
        </div>
    </dd>
</dl>
</#if>
</#list>
</#if>