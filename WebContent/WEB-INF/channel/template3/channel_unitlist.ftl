<script type="text/javascript" src="${SiteUrl}js/jquery.js"></script>
<script type="text/javascript" src="${SiteUrl}js/json2.js"></script>
<script type="text/javascript">
var selectUnitId="";
<#if unlitList?? >
<#if unlitList?size&gt;0>
<#if unlitList[0][0]??>
<#assign firstUnit = unlitList[0][0] ><!--默认显示的第一个学校-->
</#if>
</#if>
</#if>

<#if firstUnit??>
    selectUnitId = "${firstUnit.unitId}";
</#if>
$(function() {
    $(".shoollist a").mouseover(function() {
        var unitId = $(this).attr("unitId");
        //alert("unitId=" + unitId );
        $("#link_" + selectUnitId).removeClass('selected');
        var url = "${SiteUrl}units/manage/unitgetinfo.action?unitId=" + unitId + "&r=" + Math.random();
        $.get(url, function (data) {
            var s = eval(data);
            var unitTitle = s[0][1];
            var photoimg = s[0][2];
            var info = s[0][3];
            if (photoimg == "")
            {
                photoimg = "${SiteUrl}images/unit/SchoolLogo.jpg"
            } 
            //alert(unitTitle);
            //alert(photoimg);
            //alert(info);
            if(info.length > 300)
            {
                info = info.substring(0,290) + "......" 
            }
            selectUnitId = unitId;
            document.getElementById('schoolPhoto').src = photoimg;
            $('#unitInfo').html(info);
            $('#unitTitle').html(unitTitle);
            $("#link_" + unitId).addClass('selected');
        });
        return false;
    });
})
</script>
<div style="height:10px;"></div>
<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
<tr style="vertical-align:top;">
<td>
<div>
<div class="sec_body_content">
    <div class="body_title_container">${unitType}信息</div>
    <div>
        <div class="top">
            <div class="top_left">
            </div>
            <div class="top_right">
            </div>
        </div>
        <div class="middle">
<#if unlitList?? >
        <div class="school_container">
            <#if firstUnit??>
            <div class="school_info_container">
                <div class="school_pic_content">
                
                <a href='${SiteUrl!}go.action?unitId=${firstUnit.unitId}' target='_blank'>
                    <img id="schoolPhoto" src='${Util.url(firstUnit.unitPhoto!SiteUrl + "images/unit/SchoolLogo.jpg")}' border='0' />
                </a>
                
            </div>
            
                <div class="school_intro_content">
                    <table border="0" cellpadding="0" cellspacing="0" style="width:100%;">
                    <tr>    
                        <td class="left" id="unitTitle">${firstUnit.unitTitle!?html}</td>
                        <td class="right"></td>
                    </tr>
                    </table>
                    <div class="intro_content">
                        <div id="unitInfo">
                        <#if firstUnit.unitInfo??>
                            <#if firstUnit.unitInfo?size&gt;300>
                               &nbsp;&nbsp;${firstUnit.unitInfo?substring(0,290)}
                            <#else>
                               &nbsp;&nbsp;${firstUnit.unitInfo}   
                            </#if>
                        </#if>
                        </div>
                    </div>
                </div>
            </#if>    
        </div>
            <div class="school_list_container">
                <div class="school_list_caption">
                    <div class="left"><div>&nbsp;</div></div>
                </div>
                <ul class="shoollist">
                    <#list unlitList as unlits> 
                    <#list unlits as unit>
                    <#if unit==firstUnit>
                        <li class="selected" id="link_${unit.unitId}"><a unitId="${unit.unitId}" 
                            title="${unit.unitTitle!}" href='${SiteUrl!}go.action?unitId=${unit.unitId}'
                            target="_blank">${unit.unitTitle!}</a> 
                        </li>
                    <#else>
                        <li id="link_${unit.unitId}"><a unitId="${unit.unitId}" title="${unit.unitTitle!}"
                            href='${SiteUrl!}go.action?unitId=${unit.unitId}'
                            target="_blank">${unit.unitTitle!}</a> 
                        </li>
                    </#if>
                    </#list>
                   </#list> 
                </ul>
            </div>
                 
<#else>
没有找到单位
</#if>
</div>
</div>
</div>
</div>
</td>
</tr>
</table>