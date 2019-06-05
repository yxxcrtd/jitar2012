<div style='clear:both;height:8px;'></div>
<#if unit.footerContent??>
${unit.footerContent}
<#else>
<div class='footer'>
<div style='padding:20px;padding-left:160px;'>
 <div style='float:left;padding:2px 0;line-height:100%;'>
 <strong> &copy; 版权所有：</strong>${unit.unitTitle?html}<br />
 </div>
</div>
</div>
<div style='height:4px;font-size:0px'></div>
</#if>