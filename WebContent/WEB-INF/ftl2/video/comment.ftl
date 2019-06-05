<div class="comment mt13">
<h4>全部评论（<span id="commentCount">${video.commentCount}</span>）</h4>
<div class="commentCont clearfix">
  <dl id="cmt">
      <dt>
      <#if loginUser??>
      <a target="_blank" href="${ContextPath}go.action?loginName=${loginUser.loginName}"><img src="${SSOServerUrl +'upload/'+ loginUser.userIcon!""}" width="50" onerror="this.src='${ContextPath}images/default.gif'" /></a>          
      <#else>
      <img src="${ContextPath}images/default.gif" width="50" />
      </#if>
      </dt>
      <dd>
        <div class="textArea">
            <span class="textAreaLeft"></span>
              <textarea count="300" id="textAreaCenter${video.videoId}" class="textAreaCenter" placeholder="我要评论" ondrop="return false;" onkeypress="return charLimit(this)" onkeyup="return characterCount(this)"></textarea>
              <span class="textAreaRight"></span>
          </div>
          <p class="textAreaTips mt10"><a href="javascript:void(0);" class="publishBtn">发表</a>还可以输入<span id="textNumtextAreaCenter${video.videoId}">300</span>个字</p>
      </dd>
    </dl>
    <#include "showMoreComment.ftl" />
    <a href="javascript:void(0);" id="showMoreComment"<#if !(video.commentCount &gt; Util.JitarConst.SHOW_COMMENT_LIST_COUNT?int)> style="display:none"</#if>>更多评论</a>
</div>
</div>