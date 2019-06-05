<#if photo_list?? >
<#assign str = ''>
<#list photo_list as p >
<#if p_has_next>
<#assign str = str +  "img" + (p_index + 1) + "=" + Util.thumbNails(p.href!) + "&amp;" >
<#else>
<#assign str = str +  "img" + (p_index + 1) + "=" + Util.thumbNails(p.href!) >
</#if>
</#list>
<embed allowscriptaccess="never" allownetworking="internal" src="http://flash.picturetrail.com/pflicks/acrobatcube_r.swf" loop="false" quality="high" flashvars="backopacity=100&amp;cubecroptofit=1&amp;enlargecroptofit=0&amp;logopath=http://flash.picturetrail.com/pflicks/ptlogo1.swf&amp;ptdim=50.10&amp;ptxy=500.16&amp;faceopacity=80&amp;${str}" wmode="transparent" bgcolor="transparent" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" width="500" align="middle" height="480"></embed>
</#if>