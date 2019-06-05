<script>
function showRSS() {
	var proxy = CommonUtil.getCurrentRootUrl() + 'manage/proxy/rss';
	var url = proxy + '?url=' + encodeURIComponent("${feedUrl}");
	new Ajax.Request(
			url,
			{
				method : 'get',
				onSuccess : function(xport) {
					var xmldoc = xport.responseXML;
					if (xmldoc == null) {
						document.getElementById("webpart_${widgetId}_c").innerHTML = "不是 xml 数据，一般是您提供的地址不正确，或者返回的数据不是 xml 格式导致的。\r\n"	+ xport.responseText;
						return;
					}
					var channel = xmldoc.documentElement.getElementsByTagName('channel')[0];
					var title = Xml.getChildNodeText(channel, 'title'); // 该频道的标题
					document.getElementById("t_${widgetId}").innerHTML = title;
					var count = ${showCount};
					var x = '<ul class="listul">';
					var items = Xml.getChildrenByTagName(channel, 'item');
					for ( var i = 0; i < items.length && i < count; ++i) {
						x += '<li><a href="'
								+ Xml.getChildNodeText(items[i], 'link')
								+ '" target="_blank">'
								+ Xml.getChildNodeText(items[i], 'title')
								+ '</a></li>';
					}
					x += '</ul>';
					document.getElementById("webpart_${widgetId}_c").innerHTML = x;
				},
				onException : function(xport, ex) {
					document.getElementById("webpart_${widgetId}_c").innerHTML = "在加载内容的时候发生错误:' + ex + xport.statusText + xport.responseText";
				},
				onFailure : function(xport, ex) {
					document.getElementById("webpart_${widgetId}_c").innerHTML = "在加载内容的时候发生错误:' + ex + xport.statusText + xport.responseText";
				}
			});
}
showRSS();
</script>