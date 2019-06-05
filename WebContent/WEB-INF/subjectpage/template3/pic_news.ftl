<div class='panel'>
  <div class='panel_head'>
    <div class='panel_head_right'><a href='${SubjectRootUrl}py/newsList.py?id=${subject.subjectId}&amp;type=pic&unitId=${unitId!}&title=${webpart.displayName!?url}'>查看全部</a></div>
    <div class='panel_head_left'><img src='${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/j.gif' />&nbsp;${webpart.displayName!?html}</div>
  </div>
  <div class='panel_content' style='text-align:center;'>
  	
  <#if pic_news?? && (pic_news.size() > 0) >
	<#assign news = pic_news[0] >
	<div id="c_flash">
	<script language='javascript'>
	linkarr = new Array();
	picarr = new Array();
	textarr = new Array();
	var focus_width=160;
	var focus_height=136;
	var text_height=16;
	var pics = "";
	var links = "";
	var texts = "";
	var swf_height = focus_height+text_height;
	var defJpeg = "${news.picture!}";
	
	<#list pic_news as ps>
	linkarr[${ps_index+1}]="${SubjectRootUrl}py/showSubjectNews.py?id=${subject.subjectId}%26newsId=${ps.newsId}";
	picarr[${ps_index+1}] ="${ps.picture!}";
	textarr[${ps_index+1}]="${ps.title!?html}";
	</#list>
	for(i=1;i<picarr.length;i++){	  
	  if(pics=="") pics = picarr[i];
	  else pics += "|"+picarr[i];
	}
	
	for(i=1;i<linkarr.length;i++){
	  if(links=="") links = linkarr[i];
	  else links += "|"+linkarr[i];
	}
	
	for(i=1;i<textarr.length;i++){
	  if(texts=="") texts = textarr[i];
	  else texts += "|"+textarr[i];
	}
	
	document.write('<object type="application/x-shockwave-flash" data="${SiteUrl}images/slide.swf" width="' + focus_width + '" height="' + swf_height + '">');
	document.write('<param name="movie" value="${SiteUrl}images/slide.swf" />');
	document.write('<param name="allowScriptAcess" value="sameDomain" />');
	document.write('<param name="quality" value="best" />');
	document.write('<param name="bgcolor" value="#FFFFFF" />');
	document.write('<param name="scale" value="noScale" />');
	document.write('<param name="menu" value="false">');
	document.write('<param name="wmode" value="opaque" />');
	document.write('<param name="FlashVars" value="playerMode=embedded&pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height+'" />');
	document.write('</object>');
	</script>
	</div>
	
	
	</#if>
  </div>
</div>