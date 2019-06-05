<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title><#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}gallery.css" />  
<!--[if IE 6]>
  <style type='text/css'>
  .gly_main_left { float:left;width:660px; margin:0 10px;background:#ebf4ff;border:1px solid #859dbf;}
  </style>
<![endif]-->
  
  <script src='js/jitar/core.js'></script>  
  <script type="text/javascript" src="js/jitar/imgplayer.js"></script>
 </head>
 <body>
 <#include 'site_head.ftl'>

<div style='height:12px;font-size:0;'></div>
<div id='main'>
<div class='gly_main_left'>
  <div style='padding-top:20px;'>
       <div style='text-align:center;'>
        <h2 class='div_title'>
          <a target="_blank" href="" id='LinkHref2'></a>
        </h2>
        <div id='sub_title'></div>
         <div style='text-align:center;margin:auto;height:400px;overflow:hidden;'><a href='' id='LinkHref1'><img id='ImgSrc' src='images/index/pixel.gif' border='0' onload='CommonUtil.reFixImg(this,600,400)' /></a></div>           
          <table border="0" style='margin:auto;'>
          <tr>
          <td id='ImgNav'></td>
          <td>
           <div style="display:none" onmouseout="this.style.color='#7D98BF';" onmousemove="this.style.color='#c00';" onclick="ss.play(); document.getElementById('Pause').style.display='block'; this.style.display='none';" id="Play">自动播放</div>
           <div onmouseout="this.style.color='#7D98BF';" onmousemove="this.style.color='#c00';" onclick="ss.pause(); document.getElementById('Play').style.display='block'; this.style.display='none';" id="Pause">暂停播放</div>
          </td>
          </tr>
          </table>
       </div>
     </div>
     
      <script type="text/javascript">
      //<![CDATA[
      
      var ss = new ImagePlayer.slideshow("ss");
  <#-- 最新相片幻灯 -->
  <#if new_photo_list??>
    <#list new_photo_list as photo>
      s = new ImagePlayer.slide();
      s.src = "${Util.url(photo.href)}";
      s.title = "${photo.title!?js_string}";
      <#if UserUrlPattern??>
      s.link = "${UserUrlPattern.replace('{loginName}',photo.loginName)}photo/${photo.photoId}.html";
      <#else>
      s.link = "${SiteUrl}${photo.loginName}/photo/${photo.photoId}.html";
      </#if>
      s.con = "${photo.summary!?js_string}";
      s.username = "${photo.nickName?js_string}";
      s.createdate = "${photo.createDate}";
      ss.add_slide(s);
    </#list>
  </#if>      
      ss.play();
      
      //输出导航.
      str = ""
      for(i = 0;i<ss.slides.length;i++)
      {
        str += "<div class='itemOff' onclick='ss.goSlide(" + i + ")'>" + (i + 1) + "</div>";
      }       
      $('ImgNav').innerHTML = str
      delete str;
      //]]>
   </script>
   
</div>
<div class='gly_main_right'>
   <div class='gly_right'>
      <div class='gly_right_head'>图片排行</div>
        <div style='clear:both;height:10px;'></div>
      <#if hot_photo_list??>
      <table style='margin:4px;' border='0'>
        <#list hot_photo_list as ph >
        <tr>
        <td class='rank_left'>${ph_index + 1}&nbsp;</td><td class='rank_right'>
         <#if UserUrlPattern??>
		  <a href="${UserUrlPattern.replace('{loginName}',ph.loginName)}photo/${ph.photoId}.html" target="_blank">${ph.title!?html}</a>
		  <#else>
		  <a href="${SiteUrl}${ph.loginName}/photo/${ph.photoId}.html" target="_blank">${ph.title!?html}</a>
		 </#if>
        </td>        
        </tr>
        </#list>
        </table>
      </#if>
    </div>
</div>
</div>

<#if photo_list??>
<div style='clear:both;height:10px;'></div>
<div class='gly'>
  <div class='gly_head'>
    <div class='gly_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' />&nbsp;${category.name!?html}分类下的所有图片</div>
  </div>
  <div>
 <#if photo_list.size() &gt; 0 >
    <#-- 定义要显示的列数 columnCount -->
    <#assign columnCount = 6>
    <#-- 计算显示当前记录集需要的表格行数 rowCount -->
    <#if photo_list.size() % columnCount == 0>
    <#assign rowCount = (photo_list.size() / columnCount) - 1>
    <#else>
    <#assign rowCount = (photo_list.size() / columnCount)>
    </#if>
     
    <#-- 输出表格 -->
    <table  cellSpacing="10" align="center">                            
    <#-- 外层循环输出表格的 tr -->
    <#list 0..rowCount as row >
    <tr valign='top'>
    <#-- 内层循环输出表格的 td  -->
    <#list 0..columnCount - 1 as cell >
        <td align="center" width='${100 / columnCount}%'><br />
        <#-- 判断是否存在当前对象：存在就输出；不存在就输出空格 -->
        <#if photo_list[row * columnCount + cell]??>                     
            <#assign photo = photo_list[row * columnCount + cell]>                  
            <img onload="CommonUtil.reFixImg(this,128,128)" src="${Util.thumbNails(photo.href!'images/default.gif')}" vspace='4' /><br />
            <#if UserUrlPattern??>
            <a href='${UserUrlPattern.replace('{loginName}',photo.loginName)}photo/${photo.photoId}.html'>${photo.title!?html}</a>
            <#else>
            <a href='${SiteUrl}${photo.loginName}/photo/${photo.photoId}.html'>${photo.title!?html}</a>
            </#if>
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
  <div class='pgr' style='text-align:center'><#include 'inc/pager.ftl' ></div>       
</div>
</#if>

<div style='clear:both;height:8px;'></div>

<#include ('footer.ftl') >

</body>
</html>