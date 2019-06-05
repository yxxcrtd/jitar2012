<div style='height:8px;font-size:0;'></div>
<div class="placard">
 <div style='padding:0 20px'>
 <#if placard??>
  <div style='padding-top:10px;text-align:center;font-size:16px;font-weight:bold;'>
    ${placard.title!?html}</div>
  <div style='text-align:center;padding:10px;'>发布时间：${placard.createDate!?string("yyyy-MM-dd HH:mm:ss")}</div>
  <div style='padding:10px 40px;font-size:14px;line-height:150%;'>
    ${placard.content!}
  </div>
  </#if>
 </div>
</div>