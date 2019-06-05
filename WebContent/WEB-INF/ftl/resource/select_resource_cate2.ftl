<html>
<head>
</head>
<body>
		<script type="text/javascript"> 
		window.onload = function() { 
 	    var onmessage = function(e) {     
 	    	  var data = e.data;      
 	    	   var origin = e.origin;      
 	    	    publishZyk(data);
 	    	    };    
 	    	    
		    if (typeof window.addEventListener != 'undefined') {       
				  	window.addEventListener('message', onmessage, false);
		  	     }
    	  	else if (typeof window.attachEvent != 'undefined') { 
	  		      window.attachEvent('onmessage', onmessage);     
	  		      }
	  		          
		  		 };      
		</script>
		
			
		<script language="javascript">
			function publishZyk(cateIds)
			{
				alert(cateIds);
				a=cateIds.split('|');
				if(a.length!=2)
				{
					return;
				}
				if((a[0]=="") || (a[1]==""))
				{
					return;
				}
				form1.CID.value=a[0];
				form1.CID2.value=a[1];
				form1.submit();
				
				}
		</script>
		<form name="form1" id="form1" method="post" action="resource.action" target="_parent">
			<input type="hidden" name="cmd" value="publishtozyk"/>
			<input type="hidden" name="CID" value=""/>
			<input type="hidden" name="CID2" value=""/>
			<input type="hidden" name="resourceId" value="${resourceId}"/>
		</form>
</body>
</html>