<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <script type='text/javascript'>
  //<![CDATA[
  var toolbarSty = "dialogwidth=720px;dialogheight=540px;center=yes;scrolling:no;border=thick;status=no;help=no";
  function getColor()
  {    
    var url = "colorpicker/colorpicker.py?color=${color!}&tmp=" + Date.parse(new Date());    
    if (/Firefox[\/\s](\d+\.\d+)/.test(navigator.userAgent)){
     var ffversion=new Number(RegExp.$1)
     if(ffversion < 3)
     {
      window.open(url,'_blank','width=800,height=600,resizable=1,scrollbars=1')
      return;
     }
    }
    
    var res = window.showModalDialog(url,window,toolbarSty);
    if(res){
        document.forms[0].bgcolor.value = res;
    }
  }
  
  function openUpload()
  {
    var url = "${SiteUrl}manage/userfm/index.jsp?Type=Image"
    window.open(url,'_blank','width=800,height=600,resizable=1,scrollbars=1')
  }
  
  function SetUrl( url, width, height )
  {
    if(url)
    {
        document.forms[0].logo.value = url;
    }
  }
  //]]>
  </script>
</head>
<body>
<h2>自定义样式</h2>
<div class='funcButton'>您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
&gt;&gt; <a href='user_customskin.py'>自定义样式</a>
</div>
<form method='post'>
<table class='listTable' cellspacing='1'>
<tr>
<td>页面背景颜色：</td><td><input name='bgcolor' style='width:60px' value='${customSkin.bgcolor!}' /> <input type='button' class='button' value='选择颜色' onclick='getColor()' /></td>
</tr>
<tr>
<td style='width:100px'>页眉图片地址：</td>
<td><input name='logo' style='width:99%' value='${customSkin.logo!}' /> <br />
<input type='button' value='上传图片…' onclick='openUpload()' />
</td>
</tr>
<tr>
<td>页眉图片高度：</td><td><input name='logoheight' style='width:66px' value='${customSkin.logoheight!}' />px （不同的样式需要设置不同的高度，请进行试验设置）</td>
</tr>
<tr>
<td>工作室名称Top：</td><td><input name='titletop' style='width:66px' value='${customSkin.titletop!}' />px （指工作室离页眉顶部边框的高度，请进行试验设置，为空系统自动按默认值显示。）</td>
</tr>
<tr>
<td>工作室名称Left：</td><td><input name='titleleft' style='width:66px' value='${customSkin.titleleft!}' />px （指工作室离页眉左边边框的高度，请进行试验设置，为空系统自动按默认值显示。）</td>
</tr>
<#if customSkin.titledisplay?? >
<#assign d = customSkin.titledisplay >
<#else>
<#assign d = "" >
</#if>
<tr>
<td>显示工作室名称：</td><td><input name='titledisplay' type='radio' value=''<#if d != 'none'> checked='checked'</#if> />显示 <input name='titledisplay' type='radio' value='none'<#if d == 'none'> checked='checked'</#if> />隐藏</td>
</tr>
<tr>
<td></td><td><input class='button' type='submit' value='保存修改' /> （为兼容性考虑，页眉图片地址最好设置为绝对地址。）</td>
</tr>
</table>
</form>
</body>
</html>