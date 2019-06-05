
<script>
function showCalendar() {	
	var url = "${UserSiteUrl}py/user_calendar.py";
	new Ajax.Request(
			url,
			{
				method : 'get',
				onSuccess : function(xport) {
					document.getElementById("webpart_${widget.id}_c").innerHTML = xport.responseText;
				},
				onException : function(xport, ex) {
					document.getElementById("webpart_${widget.id}_c").innerHTML = "在加载内容的时候发生错误:' + ex + xport.statusText + xport.responseText";
				},
				onFailure : function(xport, ex) {
					document.getElementById("webpart_${widget.id}_c").innerHTML = "在加载内容的时候发生错误:' + ex + xport.statusText + xport.responseText";
				}
			});
}
showCalendar();
</script>