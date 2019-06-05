<style type="text/css">
.body_title_container {
    text-align:left;
    FONT-SIZE: 1.6em; 
    HEIGHT: 32px; 
    FONT-FAMILY:黑体; 
    BACKGROUND: url(../images/unit/ball.gif) no-repeat 10px 0px; FONT-WEIGHT: bold; COLOR: #000000; PADDING-LEFT: 50px; LINE-HEIGHT: 32px
}

.school_container {
    PADDING-BOTTOM: 14px; PADDING-TOP: 14px; PADDING-LEFT: 12px; PADDING-RIGHT: 12px
}
.school_info_container {
    HEIGHT: 313px
}
.school_info_container .school_pic_content {
    WIDTH: 480px;HEIGHT: 310px;
    BORDER-LEFT: #CCCCCC 1px solid; BORDER-TOP: #CCCCCC 1px solid;  BORDER-RIGHT: #CCCCCC 1px solid; BORDER-BOTTOM: #CCCCCC 1px solid; 
    FLOAT: left; 
    PADDING-BOTTOM: 2px; PADDING-TOP: 2px; PADDING-LEFT: 2px;  PADDING-RIGHT: 2px; 
}
.school_info_container .school_pic_content IMG {
    HEIGHT: 296px; WIDTH: 487px
}
.school_info_container .school_intro_content {
    HEIGHT: 330px; FLOAT: right; WIDTH: 440px; FONT-SIZE: 1.3em;
}

.school_info_container .school_intro_content .left{
    BACKGROUND: url(../images/unit/schoolinfoleftbg.jpg) repeat-x left bottom;
    MIN-WIDTH:200px;
    BORDER:0px;
    HEIGHT:30px;
    TEXT-ALIGN:center;
}
.school_info_container .school_intro_content .right{
    BACKGROUND: url(../images/unit/shoolinforight.gif) repeat-x left bottom;
    BORDER:0px;
}


.school_info_container .school_intro_content .intro_content {
    TEXT-ALIGN:left;OVERFLOW: hidden; WORD-WRAP: break-word; FONT-SIZE: 12px; HEIGHT: 280px; POSITION: relative; WORD-BREAK: break-all; PADDING-BOTTOM: 20px; PADDING-TOP: 12px; PADDING-LEFT: 18px; LINE-HEIGHT: 26px; PADDING-RIGHT: 10px; TEXT-INDENT: 2em
}

.school_list_container {
    MIN-HEIGHT: 300px;
    BORDER-LEFT: #CCCCCC 1px solid; BORDER-TOP: #CCCCCC 0px solid;  BORDER-RIGHT: #CCCCCC 1px solid; BORDER-BOTTOM: #CCCCCC 1px solid;
}
.school_list_container .school_list_caption{
    margin-top:10px;
    HEIGHT:30px;
    BACKGROUND: url(../images/unit/schoolistbg.jpg) repeat-x;    
}
.school_list_container .school_list_caption .left{
    BACKGROUND: url(../images/unit/flag.jpg) no-repeat left top; 
    border:0 0 0 0px;
    TEXT-ALIGN:right;
    WIDTH:130px;
}
.shoollist{
    LIST-STYLE-TYPE: none; OVERFLOW: hidden; HEIGHT: auto; PADDING-BOTTOM: 14px; PADDING-TOP: 14px; PADDING-LEFT: 0px; CLEAR: both; PADDING-RIGHT: 0px; _height: 20px; _overflow: visible
}
.shoollist LI {
    OVERFLOW: hidden; HEIGHT: 32px; BACKGROUND: url(../images/unit/dotx.gif) no-repeat 1px 4px; 
    WHITE-SPACE: nowrap; TEXT-OVERFLOW: ellipsis; 
    PADDING-LEFT: 40px; 
    MARGIN: 0px 0px 0px 10px; 
    DISPLAY: inline; 
    MIN-WIDTH: 180px;
    FLOAT: left;
    TEXT-ALIGN:LEFT;
    BORDER-LEFT: #FFFFFF 1px solid; 
    BORDER-TOP: #FFFFFF 1px solid;  
    BORDER-RIGHT: #FFFFFF 1px solid; 
    BORDER-BOTTOM: #FFFFFF 1px solid;    
}
.shoollist LI A {
    FONT-SIZE: 1.2em; COLOR: #000000; TEXT-DECORATION: none; LINE-HEIGHT: 32px
}
.shoollist LI.selected {
    BORDER-LEFT: #A1D6FF 1px solid; 
    BORDER-TOP: #A1D6FF 1px solid;  
    BORDER-RIGHT: #A1D6FF 1px solid; 
    BORDER-BOTTOM: #A1D6FF 1px solid;
}
</style>
<script type="text/javascript" src="${SiteUrl}js/jquery.js"></script>
<script type="text/javascript" src="${SiteUrl}js/json2.js"></script>
<script type="text/javascript">
var selectUnitId="";
<#if unlitList?? >
<#if unlitList?size&gt;0>
<#assign firstUnit = unlitList[0]><!--默认显示的第一个学校-->
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
<div>
    <div>
        <div>
<#if unlitList?? >
        <div class="school_container">
            <#if firstUnit??>
            <div class="school_info_container">
                <div class="school_pic_content">
                
                <a href='${SiteUrl!}go.action?unitId=${firstUnit.unitId}' target='_blank'>
                    <img id="schoolPhoto" src='${Util.url(firstUnit.unitPhoto!SiteUrl + "images/unit/SchoolLogo.jpg")}' width='480' height='310' border='0' />
                </a>
                
            </div>
            
                <div class="school_intro_content">
                    <div>
                        <table border="0" cellpadding="0" cellspacing="0" style="width:100%" style="table-layout:fixed;">
                        <tr>    
                            <td class="left" id="unitTitle">${firstUnit.unitTitle!?html}</td>
                            <td class="right"></td>
                        </tr>
                        </table>
                    </div>
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
                    <div class="left"><img style="margin-top:-1px;" border="0" src="${SiteUrl}images/unit/schoollistname.jpg"></div>
                    <div class="right"></div>
                </div>
                <ul class="shoollist">
                    <#list unlitList as unit> 
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