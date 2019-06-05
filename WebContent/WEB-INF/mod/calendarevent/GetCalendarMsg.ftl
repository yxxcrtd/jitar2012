<script language="javascript">
	var currentdateObj=null;
    //当天的提示信息
	var daymsg = new Array();
		daymsg[0]="";
	 	daymsg[1]="";
	  	daymsg[2]="";
	 	daymsg[3]="";
	 	daymsg[4]="";
	 	daymsg[5]="";
	 	daymsg[6]="";
	 	daymsg[7]="";
	 	daymsg[8]="";
	 	daymsg[9]="";
	 	daymsg[10]="";
	 	daymsg[11]="";
	 	daymsg[12]="";
	 	daymsg[13]="";
	 	daymsg[14]="";
		daymsg[15]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[16]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[17]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[18]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[19]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[20]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[21]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[22]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[23]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[24]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[25]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[26]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[27]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[28]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[29]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[30]=""; 	 	 	 	 	 	 	 	 	 	 	  	
		daymsg[31]=""; 	
	<#if calendars?? >
		<#list calendars as calendar>
			<#if calendar.eventTimeBegin??>
				<#assign d1 = calendar.eventTimeBegin?string("d")>
				daymsg[${d1}]=daymsg[${d1}]+"<li><a href='${calendar.url}'>${calendar.title}</a>";
			</#if>
			
			<#if calendar.eventTimeEnd??>
					<#assign d2 = calendar.eventTimeEnd?string("d")>
					<#if d1!=d2>
					daymsg[${d2}]=daymsg[${d2}]+"<li><a href='${calendar.url}'>${calendar.title}</a>";
				    </#if>
			</#if>
		</#list>
	</#if>
</script>	