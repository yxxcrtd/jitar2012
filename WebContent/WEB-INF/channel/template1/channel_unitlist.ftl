<style type="text/css">
.body_title_container {
    FONT-SIZE: 1.6em; HEIGHT: 32px; FONT-FAMILY:黑体; BACKGROUND: url(../css/liaoning/icon_4.png) no-repeat 10px 0px; FONT-WEIGHT: bold; COLOR: #000000; PADDING-LEFT: 50px; LINE-HEIGHT: 32px
}
.school_container {
    PADDING-BOTTOM: 14px; PADDING-TOP: 14px; PADDING-LEFT: 12px; PADDING-RIGHT: 12px
}
.school_info_container {
    HEIGHT: 313px
}
.school_info_container .school_pic_content {
    BORDER-TOP: #dbdbdb 1px solid; HEIGHT: 296px; BORDER-RIGHT: #dbdbdb 1px solid; BORDER-BOTTOM: #dbdbdb 1px solid; FLOAT: left; PADDING-BOTTOM: 2px; PADDING-TOP: 2px; PADDING-LEFT: 2px; BORDER-LEFT: #dbdbdb 1px solid; PADDING-RIGHT: 2px; WIDTH: 487px
}
.school_info_container .school_pic_content IMG {
    HEIGHT: 296px; WIDTH: 487px
}
.school_info_container .school_intro_content {
    HEIGHT: 313px; FLOAT: right; WIDTH: 428px; FONT-SIZE: 1.3em;
}
.school_info_container .school_intro_content .intro_content {
    OVERFLOW: hidden; WORD-WRAP: break-word; FONT-SIZE: 1.3em; HEIGHT: 226px; POSITION: relative; WORD-BREAK: break-all; PADDING-BOTTOM: 20px; PADDING-TOP: 12px; PADDING-LEFT: 18px; LINE-HEIGHT: 26px; PADDING-RIGHT: 10px; TEXT-INDENT: 2em
}
.school_info_container .school_intro_content .intro_content .more_content {
    HEIGHT: 20px; RIGHT: 0px; POSITION: absolute; TEXT-ALIGN: right; WIDTH: 100px; BOTTOM: 0px
}
.school_info_container .school_intro_content .intro_content .more_content A {
    HEIGHT: 19px; BACKGROUND: url(../css/liaoning/more_1.gif); DISPLAY: inline-block; WIDTH: 44px
}
.school_list_container_1 {
    MIN-HEIGHT: 300px; _height: 300px
}
.school_list_container_1 UL {
    LIST-STYLE-TYPE: none; OVERFLOW: hidden; HEIGHT: auto; PADDING-BOTTOM: 14px; PADDING-TOP: 14px; PADDING-LEFT: 0px; CLEAR: both; PADDING-RIGHT: 0px; _height: 20px; _overflow: visible
}
.school_list_container_1 UL LI {
    OVERFLOW: hidden; HEIGHT: 32px; BACKGROUND: url(../css/liaoning/icon_13.gif) no-repeat 11px 5px; WHITE-SPACE: nowrap; TEXT-OVERFLOW: ellipsis; FLOAT: left; PADDING-LEFT: 36px; MARGIN: 5px 0px; DISPLAY: inline; WIDTH: 156px
}
.school_list_container_1 UL LI A {
    FONT-SIZE: 1.2em; COLOR: #000000; TEXT-DECORATION: none; LINE-HEIGHT: 32px
}
.school_list_container_1 UL LI.selected {
    HEIGHT: 32px; BACKGROUND: url(../css/liaoning/school_list_item_selected.gif); FLOAT: left; PADDING-LEFT: 30px; DISPLAY: inline; WIDTH: 162px
}
</style>
<div style="height:10px;"></div>
<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
<tr style="vertical-align:top;">
<td>
<div class="rightcontainer">
<div class="sec_body_content">
            <div class="body_title_container">${unitType}信息</div>
            <div class="box_1">
                <div class="top">
                    <div class="top_left">
                    </div>
                    <div class="top_right">
                    </div>
                </div>
                <div class="middle">
<#if unlitList?? >
<#if unlitList.size()&gt;0>
<#assign firstUnit = unlitList[0] ><!--默认显示的第一个学校-->
</#if>
                    <div class="school_container">
                        <#if firstUnit??>
                        <div class="school_info_container">
                            <div class="school_pic_content">
                            
                            <a href='${SiteUrl!}go.action?unitId=${firstUnit.unitId}' target='_blank'>
                                <img src='${Util.url(firstUnit.unitPhoto!SiteUrl + "css/liaoning/SchoolLogo.jpg")}' width='510' height='320' border='0' />
                            </a>
                            
                        </div>
                        
                            <div class="school_intro_content">
                                <div class="box_2">
                                    <div class="left">
                                    </div>
                                    <div class="center">学校名称</div>
                                    <div class="right">${firstUnit.unitTitle!?html}</div>
                                </div>
                                <div class="intro_content">
                                    <div>
                                        &nbsp;&nbsp;${firstUnit.unitInfo!?html}
                                    </div>
                                </div>
                            </div>
                        </#if>    
                    </div>
                        <div class="school_list_container_1">
                            <div class="box_2">
                                <div class="left">
                                </div>
                                <div class="center">单位列表</div>
                                <div class="right"></div>
                            </div>
                            <ul>
                                <#list unlitList as unit>
                                <#if unit==firstUnit>
                                    <li class="selected"><a onmouseover="GetInfo('${unit.unitId}','${unit.unitTitle!}')" id="link_${unit.unitId}"
                                        title="${unit.unitTitle!}" href='${SiteUrl!}go.action?unitId=${unit.unitId}'
                                        target="_blank">${unit.unitTitle!}</a> 
                                    </li>
                                <#else>
                                    <li><a onmouseover="GetInfo('${unit.unitId}','${unit.unitTitle!}')" id="link_${unit.unitId}" title="${unit.unitTitle!}"
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