<#if (customSkin??) >
<style type='text/css'>
<#if ((customSkin.logo?? && customSkin.logo != '' ) || ( customSkin.logoheight?? && customSkin.logoheight != '')) >
#header { 
<#if (customSkin.logo??) && (customSkin.logo != '')  >
<#if customSkin.logo?substring(0,6)=='/user/'>
background:url('${SiteUrl}${customSkin.logo?substring(1)}') repeat-x top center;
<#else>
background:url('${customSkin.logo!}') repeat-x top center;
</#if>
</#if>
<#if (customSkin.logoheight??) && (customSkin.logoheight != '') >
height:${customSkin.logoheight!}px;
</#if>
}
</#if>
<#if (customSkin.bgcolor??) && (customSkin.bgcolor != '')  >
html,body{ background:${customSkin.bgcolor!} }
</#if>
<#if (customSkin.titleleft??) && (customSkin.titleleft != '') >
#blog_name { padding-left:${customSkin.titleleft!}px; }
</#if>
<#if (customSkin.titletop??) && (customSkin.titletop != '') >
#blog_name { padding-top:${customSkin.titletop!}px; }
</#if>
<#if (customSkin.titledisplay??) && customSkin.titledisplay == 'none' >
#blog_name {display:none;}
</#if>
</style>
</#if>