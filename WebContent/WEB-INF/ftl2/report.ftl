<div class="report">
    <h3 class="textIn"><a href="javascript:;" class="close"></a>举报</h3>
    <div class="reportCont" style="height:auto">
        <p>您举报的理由是?</p>
        <p>
        <#if reportTypeList?? && reportTypeList?size &gt;0 >
        <#list reportTypeList as r>
          <span>
            <label class="<#if r_index == 0>reportChecked<#else>reportUnCheck</#if>" data="${r!?html}"></label>${r!?html}
          </span>
        </#list>
        </#if>
        </p>
    </div>
    <div class="reportSubmit">
        <a href="javascript:void(0);" class="reportSub">提交</a>
        <a href="javascript:void(0);" class="reportCancel">取消</a>
    </div>
</div>
<!--删除警告弹出　Start-->
<div class="deleteTips">
    <h3 class="textIn"><a href="javascript:void(0);" class="close"></a>提示</h3>
    <div class="reportCont">
        <p class="deleteCo nt">确定要删除本条回复吗？</p>
    </div>
    <div class="reportSubmit">
        <a href="javascript:void(0);" class="delSub">删除</a>
        <a href="javascript:void(0);" class="reportCancel">取消</a>
    </div>
</div>
<!--删除警告弹出　End-->
<!--提交成功提示 start-->
<div class="reportTips">
  <h3><a href="#" class="closeTips"></a></h3>
    <div class="reportTipsCont">
      <span class="tipsIcon"></span>提交成功
    </div>
</div>
<!--提交成功提示 End-->

<!-- 通用提示框 -->
<div id="commonMessageBoxBack"></div>
<div id="commonMessageBox">
  <h3><a href="javascript:void(0);" class="closeTips"></a></h3>
  <div id="messageText">内容</div>
  <div><a href="javascript:void(0);" class="okBtn">确定</a></div>
</div>