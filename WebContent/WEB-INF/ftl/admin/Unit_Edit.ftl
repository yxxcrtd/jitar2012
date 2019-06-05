<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>机构管理</title>
        <link rel="stylesheet" type="text/css" href="../css/manage.css" />
    </head>
    
    <body style="margin-top: 20px;" onLoad="javascript:unit_form.unitName.focus();">
        
        <#include "admin_header.ftl" >
        
        <h2>
            <#if unit.unitId == 0>添加机构<#else>修改机构</#if>
        </h2>
        
        <@s.form name="unit_form" action="admin_unit.py" method="post">
            <table class="listTable" cellSpacing="1">
                <tbody>
                    <tr>
						<td width="42%" style="text-align: right; height: 50px;">
                            <strong>机构名称：</strong>
                        </td>
                        <td>
                            <input type="text" name="unitName" value="${(unit.unitName == "")?string('请在此输入机构名称', unit.unitName!?html)}" maxLength="64" onFocus="this.select();" onMouseOver="this.focus();" /><font style="color: #FF0000; font-size: 15px; font-weight: bold;"> * </font>机构名称不能重复！
                        </td>
                    </tr>
                     <tr>
						<td width="42%" style="text-align: right; height: 50px;">
                            <strong>英文名称：</strong>
                        </td>
                        <td>
                            <input type="text" name="unitName" value="${(unit.unitName == "")?string('请在此输入机构英文名称', unit.unitName!?html)}" maxLength="64" onFocus="this.select();" onMouseOver="this.focus();" /><font style="color: #FF0000; font-size: 15px; font-weight: bold;"> * </font>英文名称字必须母或数字！
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            <strong>机构类型：</strong>
                        </td>
                        <td>
                            <select name="unitType" style="width: 134px;">
                                <option value="学校" ${(unit.unitType == "学校")?string('selected', '')}>学校</option>
                                <option value="机构" ${(unit.unitType == "机构")?string('selected', '')}>机构</option>
                                <option value="其它" ${(unit.unitType == "其它")?string('selected', '')}>其它</option>
                            </select>
                        </td>
                    </tr>
                   
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="4" align="center" height="25">
                        	<input type="hidden" name="unitId" value="${unit.unitId}" />
                            <input type="hidden" name="cmd" value="save" />
                            <input class="button" type="submit" value="确定${(unit.unitId == 0)?string('添加', '修改')}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input class="button" type="button" value="取消返回" onClick="javascript:window.location='?'" />
                        </td>
                    </tr>
                </tfoot>
            </table>
        </@s.form>
    </body>
</html>
