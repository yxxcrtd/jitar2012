<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>系统配置</title>
        <link rel="stylesheet" type="text/css" href="../css/manage.css" />
        <script type="text/javascript">
        <!--
        function isAudit(value, name) {
            if (value == "true") {
                document.admin_sys_config_form.action = "admin_sys.action?cmd=saveAudit&value=true&name=" + name;
            } else if (value == "false") {
                document.admin_sys_config_form.action = "admin_sys.action?cmd=saveAudit&value=false&name=" + name;
            }
            document.admin_sys_config_form.submit();
        }
		// 改变背景颜色
		function changeBgColor(obj, colors) {
			obj.style.backgroundColor = colors;
		}
        -->
        </script>
    </head>
    
    <body>
        <#include "admin_header.ftl">
        
        <h2>系统配置</h2>
  		
  		<#if (actionErrors?? && actionErrors?size > 0)>
			<table align="center">
				<tr>
					<td>
						<table class="ytable" cellPadding="5" width="100%" cellSpacing="1px" border="0" align="left">
							<tr>
								<td height="20">
									<img src="../images/ok.gif"/>&nbsp;<@s.actionerror cssClass="actionError" />											
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</#if>
        
        <form name="admin_sys_config_form" method="post">
            <table class="listTable" cellSpacing="1">
                <thead>
                    <tr>
                        <th width="6%">
                            ID
                        </th>
                        <th width="35%">
							配置项标题
                        </th>
                        <th width="47%">
							配置说明
                        </th>
                        <th width="12%">
							操&nbsp;&nbsp;作
                        </th>
                    </tr>
                </thead>
                
                <tbody>
                    <#list sysConfigList as sc>
                    <tr bgColor="#FFFFFF" onMouseOver="changeBgColor(this,'#E6DBC0')" onMouseOut="changeBgColor(this,'#FFFFFF')">
                        <td style="text-align: center;">
                            ${sc.id}
                        </td>
                        <td style="padding-left: 10px;">
                            ${sc.title!}
                        </td>
                        <td style="padding-left: 10px;">
                            ${sc.description?if_exists}
                        </td>
                        <td style="text-align: center;">
                            <input type="radio" name="${sc.id}" value="${sc.value}" <#if sc.value?string=="true">checked</#if> onClick="javascript:isAudit('true', '${sc.name}')" />是
                            <input type="radio" name="${sc.id}" value="${sc.value}" ${(sc.value?string=='false')?string('checked', '')} onClick="javascript:isAudit('false', '${sc.name}')" />否
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </form>        
        <#include "admin_footer.ftl" >
        <br/> <br/>
    </body>
</html>
