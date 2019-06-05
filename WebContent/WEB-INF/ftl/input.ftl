<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
        <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" /> 
    </head>
    
    <body>  
        <center>
            <div style="border: 1px solid red; padding: 4px; width:75%; text-align: center; margin-top: 80px;">
                <div style="margin-top: 20px; text-align: center;">
                    <@s.fielderror cssStyle="color: #FF0000; font-weight:bold;" />
                </div>
                <div style="margin: 5px; text-align: center;">
                    [<a href="javascript:window.history.back();">返 回</a>]
                </div>
            </div>
        </center>       
    </body>
</html>
