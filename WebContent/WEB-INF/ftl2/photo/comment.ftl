<div class="comment mt13">
  <h4>全部评论（<span id="commentCount">${photo.commentCount}</span>）</h4>
    <div class="commentCont clearfix">
      <dl id="cmt">
          <dt>
          <#if loginUser??>
          <a target="_blank" href="${ContextPath}go.action?loginName=${loginUser.loginName}"><img src="${(SSOServerUrl +'upload/'+ loginUser.userIcon)!"${ContextPath}images/default.gif"}" width="50" onerror="this.src='${ContextPath}images/default.gif'" /></a>          
          <#else>
          <img src="${ContextPath}images/default.gif" width="50" />
          </#if>
          </dt>
          <dd>
            <div class="textArea">
                <span class="textAreaLeft"></span>
                  <textarea count="300" id="textAreaCenter${photo.photoId}" class="textAreaCenter" placeholder="我要评论" ondrop="return false;" onkeypress="return charLimit(this)" onkeyup="return characterCount(this)"></textarea>
                  <span class="textAreaRight"></span>
              </div>
              <p class="textAreaTips mt10"><a href="javascript:void(0);" class="publishBtn">发表</a>还可以输入<span id="textNumtextAreaCenter${photo.photoId}">300</span>个字</p>
          </dd>
        </dl>
        <#if cmt_list??>
        <#list cmt_list as comment>
        <#assign senderUser = Util.userById(comment.userId)>
        <dl>
          <dt><a target="_blank" href="${ContextPath}u/${senderUser.loginName}"><img src="${(SSOServerUrl +'upload/'+ senderUser.userIcon)!"${ContextPath}images/default.gif"}" width="50" onerror="this.src='${ContextPath}images/default.gif'"/></a></dt>
            <dd>
              <div class="replyContent" id="replyContent${comment.id}">
                  <div id="r${comment.id}">
                        <h4><em class="emDate">${comment.createDate?string("yyyy-MM-dd HH:mm:ss")}</em><a target="_blank" href="${ContextPath}u/${senderUser.loginName}">${senderUser.trueName}</a></h4>
                        <p class="mt10 hardBreak">${comment.content}</p>
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
                    <#assign senderUser = Util.userById(reply.userId)>
                    <#assign aboutUser = Util.userById(reply.aboutUserId)>
                    <dl id="r${reply.id}">
                        <dt><a href="${ContextPath}go.action?loginName=${senderUser.loginName}" target="_blank"><img src="${(SSOServerUrl +'upload/'+ senderUser.userIcon)!"images/default.gif"}" width="50" border="0" onerror="this.src='${ContextPath}images/default.gif'"/></a></dt>
                        <dd>
                            <h4><em class="emDate">${reply.createDate?string("yyyy-MM-dd HH:mm:ss")}</em><a target="_blank" href="${ContextPath}go.action?loginName=${senderUser.loginName}">${senderUser.trueName}</a></h4>
                            <p class="mt10 hardBreak">${reply.content}</p>
                            <p class="clearfix">
                              <a href="javascript:void(0);" class="replyBtn" id="rb2" onclick="replay(${reply.id},${comment.id})">回复</a>
                              <#if loginUser?? && loginUser.userId == reply.userId>
                                <a href="javascript:void(0);" class="removeBtn" commentid="${reply.id}" isreply="1">删除</a>
                              </#if>
                              <span class="replyText">回复给</span> <a target="_blank" href="${ContextPath}go.action?loginName=${aboutUser.loginName}" class="replyYou">${aboutUser.trueName}</a>
                            </p>
                        </dd>
                        <span class="replyArrow"></span>
                    </dl>
                    </#list>
                    </#if>
                    <!--回复中的回复内容 End-->
                </div>
            </dd>
        </dl>
        </#list>
        </#if>
    </div>
</div>