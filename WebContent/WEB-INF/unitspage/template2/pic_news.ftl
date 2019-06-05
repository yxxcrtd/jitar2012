<div class='gly'>
  <div class='gly_head'>
    <div class='gly_head_right'><a href='${UnitRootUrl}py/show_unit_news_list.py?type=0'>查看全部</a></div>
    <div class='gly_head_left'>&nbsp;${webpart.displayName}</div>
  </div>
  <div style='padding:4px;text-align:center'>
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
	linkarr[${ps_index+1}]="${UnitRootUrl}py/show_news.py?unitNewsId=${ps.unitNewsId}";
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
	document.write('<param name="bgcolor" value="#E5ECF4" />');
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