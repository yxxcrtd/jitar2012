<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="alternate" type="application/rss+xml" title="Recent Changes" href="/rss.py?type=article" />
  <#if SiteConfig ??>
  <meta name="keywords" content="${SiteConfig.site.keyword!}" /> 
  </#if>
  <title><#include ('/WEB-INF/ftl/webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}index.css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
  <script type="text/javascript">
  function isLeap(theYear)
  {
    return (new Date(theYear,1,29).getDate() == 29);
  }
  
  function showMonth(y,m,d)
  {  
    f = document.getElementById("fAction")
    f.elements[m].options.length = 0;  
    for(i = 1; i< 13;i++)
    {
      f.elements[m].options[f.elements[m].options.length] = new Option(i,i)
    } 
     
    f.elements[d].options.length = 0;  
    for(i = 1; i< 32;i++)
    {
      f.elements[d].options[f.elements[d].options.length] = new Option(i,i)
    }     
  }
  
  function showDay(m,y,d)
  {
    monthDays = 0;
    f = document.getElementById("fAction")
    f.elements[d].options.length = 0;
    selectMonth = m.options[m.selectedIndex].value
    if(selectMonth == "1" || selectMonth == "3" || selectMonth == "5" || selectMonth == "7" || selectMonth == "8" ||
     selectMonth == "10" || selectMonth == "12")
    {
        monthDays = 31
    }
    else if( selectMonth == "4" || selectMonth == "6" || selectMonth == "9" || selectMonth == "11")
    {
       monthDays = 30   
    }
    else
    {
       Y = parseInt(f.elements[y].options[f.elements[y].selectedIndex].value,10)
       if(isLeap(Y))
       {
         monthDays = 29        
       }
       else
       {
        monthDays = 28
       }
    }
    for(i = 1; i <= monthDays;i++)
    {
        f.elements[d].options[f.elements[d].options.length] = new Option(i,i)
    }
  }
  

  function getUserList()
  {
      
  }
  
  </script>
 </head> 
 <body>
 <#include '/WEB-INF/ftl/site_head.ftl' >
 <#if action_list??>
 <h3>活动列表</h3>
 <table border='1'>
 <tr>
 <td>活动标题</td>
 <td>创建人</td>
 <td>活动限制人数</td>
 <td>创建时间</td>
<td>活动开始时间</td>
<td>活动结束时间</td>
<td>报名截止时间</td>
 </tr>
 <#list action_list as a>
 <tr>
 <td><a href='${SiteUrl}showAction.action?actionId=${a.actionId}'>${a.title!?html}</a></td>
 <td><a href='${SiteUrl}showAction.action?actionId=${a.actionId}'>${a.createUserId}</a></td>
 <td>${a.userLimit}</td>
 <td>${a.createDate}</td>
 <td>${a.startDateTime}</td>
 <td>${a.finishDateTime}</td>
 <td>${a.attendLimitDateTime}</td>
 </tr>
 </#list>
 </table>
 </#if>
<div style="clear: both;"></div>   
<#include '/WEB-INF/ftl/footer.ftl'>
 </body>
</html>