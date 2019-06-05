<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - ${edustar_action.title!""}</title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
<!-- 配置上载路径 -->
<script type="text/javascript">
    window.UEDITOR_UPLOAD_URL = "${SiteUrl}";
    window.UEDITOR_USERLOGINNAME = "<#if loginUser??>${loginUser.loginName}</#if>";
</script>
<!-- 配置文件 -->
<script type="text/javascript" src="${ContextPath}manage/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="${ContextPath}manage/ueditor/ueditor.all.js"></script>
<!-- 语言包文件(建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败) -->
<script type="text/javascript" src="${ContextPath}manage/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
<#include "/WEB-INF/ftl2/site_head.ftl" />
<#if edustar_action ?? >
<#assign a = edustar_action >
</#if>
<!--活动 Start-->
<div class="secMain mt25 clearfix">
<div class="moreList border">
      <h3 class="h3Head textIn"><span class="moreHead">活动详细信息</span></h3>
        <div class="moreContent">
          <table class="moreTable activityTable" cellpadding="0" cellspacing="0" style="table-layout:fixed">
              <#if !loginUser?? >
              <thead>
                    <tr class="moreThead">
                        <th class="textIn" width="23%">要参与本活动，请先<a href="${ContextPath}">登录系统</a>。</th>
                        <th class="textIn">&nbsp;</th>
                    </tr>
                </thead>
           </#if>
                <tbody>
                  <tr>
                      <td class="activityTdBg" style="width:120px">
                          <div class="activityP"><strong>活动名称：</strong></div>
                        </td>
                        <td>
                          <div class="activityP">${a.title?html}</div>
                        </td>
                    </tr>
                    <tr>
                      <td class="activityTdBg">
                          <div class="activityP"><strong>活动类型：</strong></div>
                        </td>
                        <td>
                          <div class="activityP">
<#if a.actionType == 0 >
        任何人都可以参加。
    <#elseif a.actionType == 1 >
        <#if a.ownerType == 'user' >
          只有创建者的好友才能参加
        <#elseif a.ownerType == 'group' >
          只有该群组内成员才能参加
        <#elseif a.ownerType == 'course' >
         只有该备课组内成员才能参加
        <#elseif a.ownerType == 'subject' >
         只有属于该学科的成员才能参加
        <#else>
         未知 
        </#if>
    <#elseif a.actionType == 2 >
        只能邀请参加
    <#else>
        未定
</#if>
                          </div>
                        </td>
                    </tr>
                    <tr>
                      <td class="activityTdBg">
                          <div class="activityP"><strong>活动方式：</strong></div>
                        </td>
                        <td>
                          <div class="activityP">
<#if a.visibility == 0 >
    完全公开
<#elseif  a.visibility == 1 >
    保密
<#else>
    未定
</#if>
                          </div>
                        </td>
                    </tr>
                    <tr>
                      <td class="activityTdBg">
                          <div class="activityP"><strong>状态：</strong></div>
                        </td>
                        <td>
                          <div class="activityP">
<#if a.status == 0 >
    正常
<#elseif a.status == 1 >
    待审核
<#elseif a.status == 2 >
    已关闭
<#elseif a.status == 3 >
    锁定
 <#elseif a.status == -1 >
    待删除（回收站）
<#else>
    未定
</#if> 
                          </div>
                        </td>
                    </tr>
                    <tr>
                      <td class="activityTdBg">
                          <div class="activityP"><strong>活动描述：</strong></div>
                        </td>
                        <td>
                          <div class="activityP">${a.description!}</div>
                        </td>
                    </tr>
                    <tr>
                      <td class="activityTdBg">
                          <div class="activityP"><strong>活动人数限制：</strong></div>
                        </td>
                        <td>
                          <div class="activityP">${a.userLimit}</div>
                        </td>
                    </tr>
                    <tr>
                      <td class="activityTdBg">
                          <div class="activityP"><strong>已经报名人数：</strong></div>
                        </td>
                        <td>
                          <div class="activityP">${usercount!}</div>
                        </td>
                    </tr>
                    <tr>
                      <td class="activityTdBg">
                          <div class="activityP"><strong>活动开始时间：</strong></div>
                        </td>
                        <td>
                          <div class="activityP">${a.startDateTime?string('yyyy年MM月dd日 HH点mm分')}</div>
                        </td>
                    </tr>
                    <tr>
                      <td class="activityTdBg">
                          <div class="activityP"><strong>活动结束时间：</strong></div>
                        </td>
                        <td>
                          <div class="activityP">${a.finishDateTime?string('yyyy年MM月dd日 HH点mm分')}</div>
                        </td>
                    </tr>
                    <tr>
                      <td class="activityTdBg">
                          <div class="activityP"><strong>报名截止时间：</strong></div>
                        </td>
                        <td>
                          <div class="activityP">
${a.attendLimitDateTime?string('yyyy年MM月dd日 HH点mm分')}（当前时间：<span style='color:#F00'>${Util.today()?string('yyyy年MM月dd日 HH点mm分')}</span>）
 <#if isDeadLimit?? >
 <span style='color:red'>该活动的报名时间已经截止。</span>
 </#if>
                          </div>
                        </td>
                    </tr>
                    <tr>
                      <td class="activityTdBg">
                          <div class="activityP"><strong>活动地点：</strong></div>
                        </td>
                        <td>
                          <div class="activityP">${a.place!}</div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize9" /></div>
    </div>
    <div class="moreList border mt3">
      <h3 class="h3Head textIn"><span class="moreHead">本活动的讨论</span></h3>
        <div class="moreContent">
        <#if action_reply_list??>
          <table class="moreTable activityTable" cellpadding="0" cellspacing="0">
              <thead>
                    <tr class="moreThead">
                        <th class="textIn" width="23%">用户信息</th>
                        <th class="textIn">讨论信息</th>
                    </tr>
                </thead>
                <tbody>
              <#if action_reply_list?? && action_reply_list?size &gt; 0>
              <#list action_reply_list as ar >
              <#assign u = Util.userById(ar.userId)>
              <#if u?? && u != ""> 
                  <tr>
                      <td class="activityTdBg">
                          <div class="activityP">
                                <p><a href="#"><img width="116" src="${SSOServerUrl}upload/${u.userIcon!""}" onerror="this.src='${ContextPath}images/default.gif'" /></a></p>
                                <p>用户名：<a href="${ContextPath}u/${u.loginName}">${u.trueName!""}</a></p>
                                <p>文章数：${u.articleCount}</p>
                                <p>资源数：${u.resourceCount}</p>
                                <p>图片数：${u.photoCount}</p>
                                <p>主题数：${u.topicCount}</p>
                                <p>评论数：${u.commentCount}</p>
                                <p>注册时间：${u.createDate?string('yyyy-MM-dd HH:mm')}</p>
                                <p><a href="${ContextPath}u/${u.loginName}" class="iconHome">个人主页</a> <a href="${ContextPath}u/${u.loginName}/profile" class="iconText">查看文档</a></p>
                                <#--<p><a href="#" class="iconFriend">加为好友</a> <a href="#" class="iconMesseng">发送消息</a></p>-->
                            </div>
                        </td>
                        <td>
                          <div class="activityP activityCont">
                              <h4>${ar.topic?html}</h4>
                                <div class="activityRep">
                                  ${ar.content!}
                                </div>
                                <p class="activityTime">
                                <a href="#replay">回复</a>
                               <#if can_manage == '1'>
                                <a href='${ContextPath}showAction.action?actionId=${a.actionId}&cmd=deleteReplay&actionReplyId=${ar.actionReplyId}'>删除回复</a>
                               </#if>                                
                               &nbsp;&nbsp;发表于：${ar.createDate?string("yyyy-MM-dd HH:mm:ss")}
                                </p>
                            </div>
                        </td>
                    </tr>
                  </#if>
                  </#list>
                  </#if>
                </tbody>
            </table>
            <div class="listPage clearfix">
              <#include '/WEB-INF/ftl2/pager.ftl'>
            </div>
          </#if>
        </div>
        <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize9" /></div>
    </div>
    
  <#assign caninput = 0>
  <#if can_comment?? && can_comment == '1' >
      <#assign caninput = 1>
  </#if>

    <div class="moreList border mt3">
      <h3 class="h3Head textIn"><span class="moreHead">发表讨论</span></h3>
        <div class="moreContent">
         <#if caninput == 1 >
         <form method='post' action='${ContextPath}showAction.action?actionId=${a.actionId}' name='re' onsubmit="return checkData(this);">
         <input type='hidden' name='cmd' value='comment' />
         <input type='hidden' name='pageSize' value='${pager.pageSize?default(1)}' />
         <input type='hidden' name='totalRows' value='${pager.totalRows?default(1)}' />
          <table class="activityTable1" cellpadding="0" cellspacing="0">
              <tbody>
                  <tr>
                      <td width="15%">标题：</td>
                        <td><a name='replay'></a>
<input name='title' size="80" value='回复：<#if edustar_action??>${edustar_action.title?html}</#if>' /> <font color='red'>*</font> 必须填写讨论标题</td>
                    </tr>
                    <tr>
                      <td>内容：<font color='red'>*</font> 必须填写讨论内容<br/></td>
                       <td>
                        <script id="DHtml" name="actionComment" type="text/plain" style="width:840px;height:400px;">
                        </script>                          
                        <script type="text/javascript">
                            var editor = UE.getEditor('DHtml');
                        </script>
                        </td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                        <td><a href="javascript:void(0);" class="activityBtn" onclick="document.re.submit();">发表回复</a></td>
                    </tr>
                </tbody>
            </table>
         <#else>
                                    你目前无权参与讨论。
        </#if>
        </div>
        <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize9" /></div>
    </div>
</div>

<!--协作组 End-->
<!--公共尾部 Start-->
<#include '/WEB-INF/ftl2/footer.ftl'>
<!--公共尾部 End-->

<!--[if IE 6]>
<script src="${ContextPath}js/new/DD_belatedPNG.js" type="text/javascript"></script>
<script type="text/javascript">
  DD_belatedPNG.fix('.topSearch,.login a,.videoPlay,.videoPlayBg,.tx,.liW,.liX,.liP,.leftNavS li,.leftNavIcon,.leftNavIconH,.folder,.liFolder,.coopTag,.comma1,.comma2');
</script>
<![endif]-->
</body>
</html>