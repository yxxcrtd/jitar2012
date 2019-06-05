<#list group_list as group>
<dl class="coopList">
<dt><a href="${SiteUrl}go.action?groupId=${group.groupId}"><img src='${Util.url(group.groupIcon!SiteUrl + "images/group_default.gif")}' width='120' height='100' border='0' /></a></dt>
<dd>
  <h4><a href="${SiteUrl}go.action?groupId=${group.groupId}">${group.groupTitle!?html}</a></h4>
  <p>创建时间 <span>${group.createDate?string('yyyy/MM/dd')}</span> 成员 <span>${group.userCount}</span> 访问 <span>${group.visitCount}</span> 文章 <span>${group.articleCount}</span> 主题 <span>${group.topicCount}</span>  资源 <span>${group.resourceCount}</span></p>
  <p>学段学科 ${group.XKXDName!?html}</p>
  <p class="coopTag">标签
    <#list Util.tagToList(group.groupTags!) as tag>
        <a href='${SiteUrl}showTag.action?tagName=${tag?url("UTF-8")}'>${tag!?html}</a>
      </#list>                         
  </p>
  <p>
      <span class="coopDes">
         ${group.groupIntroduce!}
          <span class="comma1"></span>
          <span class="comma2"></span>
      </span>
  </p>
</dd>
</dl>
</#list>