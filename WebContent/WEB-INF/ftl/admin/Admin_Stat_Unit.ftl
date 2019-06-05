<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>机构统计</title>
<script type="text/javascript" src="../js/stat.js"></script>
<link rel="stylesheet" type="text/css" href="../css/manage.css" />
<link rel="stylesheet" type="text/css" href="../js/datepicker/calendar.css" />  
<script src='../js/datepicker/calendar.js' type="text/javascript"></script>
<script type="text/javascript">
<!--
function stat(search_form, type) {   
  if (type == 1) {  
      document.getElementById("content").style.display="block";     
      search_form.cmd.value = "list";
  } else {
      search_form.cmd.value = "stat";
  }
  search_form.submit();
}
//-->
</script>
</head>

<body>
<h2>机构统计</h2>
<div id="tips" style="position: absolute; border: 1px solid #ccc; padding: 0px 3px; color: #FF0000; display: none; height: 20px; line-height: 20px; background: #fcfcfc"></div>
<@s.form name="search_form" action="admin_stat_unit" method="post">
    <input type="hidden" name="cmd" value="list" />
    <#if unitId??>
    <input type='hidden' name='unitId' value='${unitId}' />
    </#if>
    <#if from??>
    <input type='hidden' name='from' value='${from}' />
    <#else>
    &nbsp;关键字：<input type="text" id="k" name="k" value="${k!?html}" style="width: 88px;" onFocus="this.select(); tips('k', '关键字可以输入：机构Id、机构名称')" onMouseover="this.focus();" onBlur="showTips();" />
    </#if>
开始日期:<input type="text" id="beginDate" name="beginDate" value="${beginDate!?html}" style="width: 88px;" maxLength="10" readonly="readonly" />
结束日期:<input type="text" id="endDate" name="endDate" value="${endDate!?html}" style="width: 88px;" maxLength="10"  readonly="readonly" />
    
    <input type="submit" class="button" value="统&nbsp;计" />
    <input type="button" class="button" value="导&nbsp;出" onClick="stat(search_form, 2);" />
    
    <table class="listTable" cellSpacing="1">
        <thead>
            <tr>
                <th width="7%">
					<@s.label value="机构ID" />
                </th><#--
                <th width="10%" style="text-align: left; padding-left: 10px;">
                    <@s.label value="区县名称" />
                </th>-->
                <th width="15%" style="text-align: left; padding-left: 10px;">
                    <@s.label value="机构名称" />
                </th>
                <th width="7%">
                    <@s.label value="工作室数" />
                </th>
                <th width="7%">
                    <@s.label value="文章数" />
                </th>
                <th width="7%">
                    <@s.label value="推荐文章数" />
                </th>
                <th width="7%">
                    <@s.label value="资源数" />
                </th>
                <th width="7%">
                    <@s.label value="推荐资源数" />
                </th>
                <th width="7%">
                    <@s.label value="图片数" />
                </th>
                <th width="7%">
                    <@s.label value="视频数" />
                </th>
                <th width="7%">
                    <@s.label value="当前积分" />
                </th>
            </tr>
        </thead>
        <tbody>
        <#if unitList??>
            <#if unitList?size == 0>
                <tr>
                    <td colSpan="10" style="color: #FF0000; font-weight: bold; text-align: center; padding: 10px;">
                        &nbsp;对不起，没有符合条件的机构信息！&nbsp;
                    </td>
                </tr>
            </#if>
            
            <#list unitList as unit>
                <tr bgColor="#FFFFFF" onMouseOver="changeBgColor(this,'#E6DBC0')" onMouseOut="changeBgColor(this,'#FFFFFF')">
                    <td style="text-align: center;">
                        ${unit.unitId}
                    </td>
                    <td style="padding-left: 10px;">
                        ${unit.unitTitle!?html}
                    </td>
                    <td style="text-align: center;">
                        ${unit.userCount}
                    </td>
                    <td style="text-align: center;">
                        ${unit.articleCount}
                    </td>
                    <td style="text-align: center;">
                        ${unit.recommendArticleCount}
                    </td>
                    <td style="text-align: center;">
                        ${unit.resourceCount}
                    </td>
                    <td style="text-align: center;">
                        ${unit.recommendResourceCount}
                    </td>
                    <td style="text-align: center;">
                        ${unit.photoCount}
                    </td>
                    <td style="text-align: center;">
                        ${unit.videoCount}
                    </td>
                    <td style="text-align: center;">
                        ${unit.totalScore}
                    </td>
                </tr>
            </#list>
        <#else>
       	<tr>
            <td colSpan="10" style="color: #FF0000; font-weight: bold; text-align: center; padding: 10px;">
               <#if init??>
               	请输入查询条件进行查询。
               <#else>
                &nbsp;对不起，没有符合条件的机构信息！&nbsp;
               </#if>
            </td>
          </tr>  
        </#if>
        </tbody>
    </table>
    <#if unitList?? && unitList?size != 0>
        <div style="width: 100%; text-align: right; margin: 3px auto 3px;">
            <#include "../inc/pager.ftl">
        </div>
    </#if>
</@s.form>
<div id="content" style="text-align: center; display: none;">
	<br />
	<img src="${SiteUrl}images/load.gif" />
	<br />
	<br />
	<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;正在统计，请稍等 ......</div>
</div>
<script>
calendar.set("beginDate");
calendar.set("endDate");
</script>
</body>
</html>
