<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>屏蔽选项</title>
        <link rel="stylesheet" type="text/css" href="../css/manage.css" />
    </head>
    
    <body>
        <#include "admin_header.ftl">
        
        <h2>
            屏蔽选项
        </h2>
        
        <form name="site_screen_form" action="?" method="post">
			<input type="hidden" name="cmd" value="save" />
            <table class="listTable" cellSpacing="1">
				<tr>
					<td style="text-align: right; width: 25%; height: 30px;">
						<strong>是否屏蔽系统中的非法词汇：</strong>
					</td>
					<td>
						<input type="radio" name="site.screen.enalbed" value="true" <#if config['site.screen.enalbed']!?html == 'true'>checked</#if> />是
						<input type="radio" name="site.screen.enalbed" value="false" <#if config['site.screen.enalbed']!?html == 'false'>checked</#if> />否
					</td>
				</tr>
				<tr>
					<td style="text-align: right; height: 30px;">
						<strong>替换的字符：</strong>
					</td>
					<td>
						<input type="text" name="site.screen.replace" value="${config['site.screen.replace']!?html}">
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">
						<strong>非法词汇：</strong>
						<br /><br />(非法词汇用英文的逗号分割)
					</td>
					<td>
						<textarea name="site.screen.keyword" style="width: 100%; height: 300px;">${config['site.screen.keyword']!?html}</textarea>
					</td>
				</tr>
				<tr>
					<td colSpan="2" style="text-align: center;">
						<input type="submit" class="button" value="  修  改  " />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value=" 返 回 " onClick="window.history.back()" />
					</td>
				</tr>
            </table>
        </form>
        
        <#include "admin_footer.ftl">
    </body>
</html>
