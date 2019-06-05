<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${unit.siteTitle?html}</title>
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/units/${unit.themeName?default('theme1')}/index.css" />
  <script type='text/javascript' src='${SiteUrl}units/drag.js'></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
 </head>
<body>
<#include "/WEB-INF/unitspage/unit_total_header.ftl">

<div id='container'>
<table style='width:100%' border='0'>
<tr style='vertical-align:top'>
<td style='width:25%'>
	<div class='gly'>
	  <div class='gly_head'>
	    <div class='gly_head_right'><a href='${UnitRootUrl}py/unit_blog_list.py?type=1'>查看全部</a></div>
	    <div class='gly_head_left' style="padding-left:4px">${Util.typeIdToName(1)}工作室</div>
	  </div>
	  <div style='padding:4px;'>
	    <#if famous_teachers??>
	    <#list famous_teachers as user>
		<table class='icontable' border='0' cellpadding='0' cellspacing='0'>
	    <tr>
	    <td class='iconleft'><span class='img_container'><a href='${SiteUrl}go.action?loginName=${user.loginName}'><img src="${SSOServerUrl +'upload/'+ (user.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" width='64' height='64' border='0' /></a></span></td>
	    <td class='iconright'>
	    <div><strong><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.blogName}</a></strong></div>
	    <div><span>真实姓名：</span><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a></div>
	    <div><span>访问数：</span>${user.visitCount}</div>
	    </td>
	    </tr>
	    </table>    
	    </#list>
		</#if>
	  </div>
	</div>
	<div style='font-size:0;height:8px;'></div>
	<div class='gly'>
	  <div class='gly_head'>
	    <div class='gly_head_right'><a href='${UnitRootUrl}py/unit_blog_list.py?type=3'>查看全部</a></div>
	    <div class='gly_head_left' style="padding-left:4px">${Util.typeIdToName(3)}工作室</div>
	  </div>
	  <div style='padding:4px;'>
	    <#if expert_list??>
	    <#list expert_list as user>
		<table class='icontable' border='0' cellpadding='0' cellspacing='0'>
	    <tr>
	    <td class='iconleft'><span class='img_container'><a href='${SiteUrl}go.action?loginName=${user.loginName}'><img src="${SSOServerUrl +'upload/'+ (user.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" width='64' height='64' border='0' /></a></span></td>
	    <td class='iconright'>
	    <div><strong><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.blogName}</a></strong></div>
	    <div><span>真实姓名：</span><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a></div>
	    <div><span>访问数：</span>${user.visitCount}</div>
	    </td>
	    </tr>
	    </table>    
	    </#list>
		</#if>
	  </div>
	</div>
	<div style='font-size:0;height:8px;'></div>
	<div class='gly'>
	  <div class='gly_head'>
	    <div class='gly_head_right'><a href='${UnitRootUrl}py/unit_blog_list.py?type=4'>查看全部</a></div>
	    <div class='gly_head_left' style="padding-left:4px">${Util.typeIdToName(4)}</div>
	  </div>
	  <div style='padding:4px;'>
	    <#if comissioner_list?? >	  	    
	    <table border='0' width='100%' cellpadding='0' cellspacing='0'>
	    <#assign columnCount = 3>
	    <#assign rowCount = 2>
	    <#list 0..rowCount -1 as row>
		    <tr valign='top'>
		    <#list 0..columnCount -1 as cell >   
			    <td style='text-align:center;width:33%;padding-bottom:4px;'>
				    <#if comissioner_list[row * columnCount + cell]?? >
				    <#assign u = comissioner_list[row * columnCount + cell] >
				      <div style='height:64px;'>
				      <table border='0' cellpadding='0' cellspacing='0' class='mimg_container_a'>
				      <tr><td><a href='${SiteUrl}go.action?loginName=${u.loginName}'><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" 
				            width='56' height='56' border='0' /></a></td></tr>
				      </table>
				      </div>
				      <div style='text-align:center'><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName!?html}</a></div>
				      <#else>
				      &nbsp;
				      </#if>
			      </td>
		      </#list>
	    </tr>
	    </#list>
	    </table>
	    </#if>
	    
	  </div>
	</div>	
</td>
<td>

<div class='gly'>
<div class='gly_head'>
<div class='gly_head_right'><a href='${UnitRootUrl}py/unit_blog_list.py?type=new'>查看全部</a></div>
<div class='gly_head_left' style="padding-left:4px">最新工作室</div>
  </div>
  <div style='padding:4px;'>
	  
<#if new_blog_list?? >

      <div class='tc1' style='text-align:center'>
           <#list new_blog_list as user>
              <#if user_index == 0>
              <table class='micontable1' border='0' cellpadding='0' cellspacing='0' width='99%'>
              <tr>
              <td class='miconleft'><span class='mimg_container'><a href='${SiteUrl}go.action?loginName=${user.loginName}'><img src="${SSOServerUrl +'upload/'+ (user.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" width='64' height='64' border='0' /></a></span></td>
              <td class='miconright'>
              <div><span><a href='${SiteUrl}go.action?loginName=${user.loginName}'>${user.blogName}</a></span></div>
              <div><span>真实姓名：</span><a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a></div>
              <div>简介: ${user.blogIntroduce!}</div>              
              </td>
              </tr>
              </table>
              <div style="height:4px;font-size:0"></div>
            <#else>
              <a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName}</a>
              <#if user_has_next> | </#if>
            </#if>
            </#list>
      </div>
</#if>
</div>
</div>
<div style='font-size:0;height:8px;'></div>
<div class='gly'>
<div class='gly_head'>
<div class='gly_head_right'><a href='${UnitRootUrl}py/unit_blog_list.py?type=hot'>查看全部</a></div>
<div class='gly_head_left' style="padding-left:4px">热门工作室</div>
  </div>
  <div style='padding:4px;'>
	  
<#if hot_blog_list?? >
<table border='0' cellspacing='6' class='m_2'>
<tr>
<!-- 循环td -->
<#list hot_blog_list as wr >
 <td valign='top'>
  <div style='margin:auto;'>
    <div style='text-align:center;'>
    <span class='img_container2'><a href='${SiteUrl}go.action?loginName=${wr.loginName}'><img src='${SSOServerUrl +"upload/"+wr.userIcon!"images/default.gif"}' width='48' height='48' border='0' onerror="this.src='${ContextPath}images/default.gif'" /></a></span>
    </div>
    <div style='text-align:center;padding-top:6px;'><a href='${SiteUrl}go.action?loginName=${wr.loginName}'>${wr.blogName!?html}</a></div>
  </div>       
 </td>
   </#list>
</tr>
   </table>
</#if>
</div>
</div>
<div style='font-size:0;height:8px;'></div>

<div class='gly'>
<div class='gly_head'>
<div class='gly_head_right'><a href='${UnitRootUrl}py/unit_blog_list.py?type=2'>查看全部</a></div>
<div class='gly_head_left' style="padding-left:4px">${Util.typeIdToName(2)}工作室</div>
  </div>
  <div style='padding:4px;'>
	  
<#if rcmd_list?? >
<table border='0' cellspacing='6' class='m_2'>
<tr>
<!-- 循环td -->
<#list rcmd_list as wr >
 <td valign='top'>
  <div style='margin:auto;'>
    <div style='text-align:center;'>
    <span class='img_container2'><a href='${SiteUrl}go.action?loginName=${wr.loginName}'><img src='${SSOServerUrl +"upload/"+wr.userIcon!"images/default.gif"}' width='48' height='48' border='0' onerror="this.src='${ContextPath}images/default.gif'" /></a></span>
    </div>
    <div style='text-align:center;padding-top:6px;'><a href='${SiteUrl}go.action?loginName=${wr.loginName}'>${wr.blogName!?html}</a></div>
  </div>       
 </td>
   </#list>
</tr>
   </table>
</#if>
</div>
</div>
<div style='font-size:0;height:8px;'></div>

<div class='gly'>
<div class='gly_head'>
<div class='gly_head_right'><a href='${UnitRootUrl}py/unit_blog_list.py?type=5'>查看全部</a></div>
<div class='gly_head_left' style="padding-left:4px">${Util.typeIdToName(5)}</div>
  </div>
  <div style='padding:4px;'>
	  
<#if teacher_star?? >
	   <#if teacher_star[0]?? >
	    <table border='0' cellspacing='0' cellpadding='0' width='99%'>
	    <tr>
	    <td style='vertical:top' width='68'>
	    <span class='img_container'><a href='${SiteUrl}go.action?loginName=${teacher_star[0].loginName}'><img src='${SSOServerUrl +"upload/"+teacher_star[0].userIcon!'images/default.gif'}' class='icon64' width='64' border='0' onerror="this.src='${ContextPath}images/default.gif'" /></a></span></td>
	    <td class='blog_top' valign='top' style='padding-left:4px;'>
	      <a href='${SiteUrl}go.action?loginName=${teacher_star[0].loginName}'><span>${teacher_star[0].blogName!?html}</span></a><br/>
	      ${teacher_star[0].blogIntroduce!}<br/>
	    </td>
	    </tr>
	    </table>
	    <#else>
	    暂时没有取得数据
	    </#if>
	<#else>
	暂时没有取得数据
	</#if> 
</div>
</div>

</td>
<td style='width:25%'>

<div class='gly'>
<div class='gly_head'>
<div class='gly_head_right'><a href='${UnitRootUrl}py/unit_blog_list.py?type=visit'>查看全部</a></div>
<div class='gly_head_left' style="padding-left:4px">工作室访问排行</div>
</div>
<div style='padding:4px;'>
	  
<#if blog_visit_charts??>
  <table  cellpadding='1' cellpadding='1' width='240'>
      <#list blog_visit_charts as user>
      	<tr valign='top'>
      	<td class="rank_left">${user_index + 1}</td>
      	<td class="rank_right">
      	<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.blogName}</a>
      	</td>
      	<td align='right'>${user.visitCount}
      	</td>
      	</tr>
      </#list>
  </table>
  </#if>
</div>
</div>
<div style='font-size:0;height:8px;'></div>
<div class='gly'>
<div class='gly_head'>
<div class='gly_head_right'><a href='${UnitRootUrl}py/unit_blog_list.py?type=score'>查看全部</a></div>
<div class='gly_head_left' style="padding-left:4px">积分排行榜</div>
  </div>
  <div style='padding:4px;'>
	  
<#if blog_score_charts??>
  <table  cellpadding='1' cellpadding='1' width='240'>
      <#list blog_score_charts as user>
      	<tr valign='top'>
      	<td class="rank_left">${user_index + 1}</td>
      	<td class="rank_right">
      	<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.blogName}</a>
      	</td>
      	<td align='right'>${user.userScore}
      	</td>
      	</tr>
      </#list>
  </table>
  </#if>
</div>
</div>
</td>
</tr>
</table>

</div>
<#include "/WEB-INF/unitspage/unit_footer.ftl">
</body>
</html>