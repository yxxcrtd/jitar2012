<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>角色组条件设置</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <link rel="stylesheet" href="css/flora.all.css" type="text/css" title="Flora (Default)" />
  <script type="text/javascript">
    function returnx()
    {
		self.document.location.href="?cmd=list";	    
    }
    
    function Save()
    {
	  var the_form = document.forms['listForm'];
	  if (the_form == null) {
	    alert('Can\'t find listForm form.');
	    return;
	  }
  	  var nums=the_form.num.value;
  	  if(nums==""){
  	  	return;
  	  }
  	  var num=parseInt(nums);
  	  var score="";
  	  var i=0;
	  for(i=0;i<num;i++)
	  {
	  	if(i==0)
	  	{
	  		score=the_form.score_2_0.value;
	  		if(score=="")
	  		{
	  			alert("请输入分数段中的分数");
	  			return;
	  		}
	  	}
	  	else if(i==(num-1))
	  	{
	  		score=document.getElementById("score_1_"+(num-1)).value;
	  		if(score=="")
	  		{
	  			alert("请输入分数段中的分数");
	  			return;
	  		}
	  	}
	  	else
	  	{
	  		score=document.getElementById("score_1_"+i).value;
	  		if(score=="")
	  		{
	  			alert("请输入分数段中的分数");
	  			return;
	  		}
	  		score=document.getElementById("score_2_"+i).value;
	  		if(score=="")
	  		{
	  			alert("请输入分数段中的分数");
	  			return;
	  		}
	  	}
	  }		  
	  the_form.submit();
    }
	function addduan()
	{
	  var the_form = document.forms['listForm'];
	  if (the_form == null) {
	    alert('Can\'t find listForm form.');
	    return;
	  }
  	  var nums=the_form.num.value;
  	  if(nums==""){
  	  	return;
  	  }
  	  var shtml="";
  	  var num=parseInt(nums);
  	  shtml=shtml+"<table border='0' width='100%'>";
  	  for(i=0;i<num;i++)
  	  {
  	  	if(i==0)
  	  	{
      		shtml=shtml+"<tr>";
      		shtml=shtml+"<td width='30%' align='right'>分数小于<input type='text' name='score_2_"+i+"' id='score_2_"+i+"' size=5 value=''>的教师属于:</td>";
      		shtml=shtml+"<td>";
      		shtml=shtml+getGroupSelecthtml(i);
      		shtml=shtml+"</td>";
      		shtml=shtml+"</tr>";
      	}
      	else if(i==(num-1))
      	{
      		shtml=shtml+"<tr>";
      		shtml=shtml+"<td width='30%' align='right'>分数大于<input type='text' name='score_1_"+i+"' id='score_1_"+i+"' size=5 value=''>的教师属于:</td>";
      		shtml=shtml+"<td>";
      		shtml=shtml+getGroupSelecthtml(i);
      		shtml=shtml+"</td>";
      		shtml=shtml+"</tr>";
      	}
      	else
      	{
      		shtml=shtml+"<tr>";
      		shtml=shtml+"<td width='30%' align='right'>分数从<input type='text' name='score_1_"+i+"' id='score_1_"+i+"' size=5 value=''>到<input type='text' name='score_2_"+i+"' id='score_2_"+i+"' size=5 value=''>的教师属于:</td>";
      		shtml=shtml+"<td>";
      		shtml=shtml+getGroupSelecthtml(i);
      		shtml=shtml+"</td>";
      		shtml=shtml+"</tr>";
      	
      	}
  	  }
  	  shtml=shtml+"</table>";
  	  document.getElementById("scoreduan").innerHTML=shtml;
	}
	
	function getGroupSelecthtml(index)
	{
		var shtml="";
		shtml=shtml+"<select name='group1_"+index+"'>";
		<#list groups as group>
			shtml=shtml+"<option value='${group.groupId}'>${group.groupName}</option>";
		</#list>
		shtml=shtml+"</select>";
		return shtml;
	}
  </script>
  
</head>

<body>
<#assign typeTitle = '角色组条件设置'>
<h2>${typeTitle}</h2>
<form name='listForm' action='?' method='post'>
  <input type='hidden' name='cmd' value='savecondition' />
<table class='listTable' cellspacing='1'>
  <tbody>
    <tr>  
      <td>按分数设置:</td>
      <td valign="top">
      	<table border=0 width="100%">
      	  <tr>
      	  	<td>
      	  		分数段：<input type="text" name="num" value="${scorenum}" size=4>&nbsp;<input type="button" onclick="addduan()" name="a" value="设置"/>
      	  	</td>
      	  </tr>	
      	  <tr>
      	  <td>
      	  <div id="scoreduan">
      	  <table border='0' width='100%'>	
		  <#if condition1list??>
		  <#list condition1list as condition1>
		    <#if condition1.conditionType==-1>
	      		<tr>
	      			<td width="30%" align="right">分数小于<input type="text" name="score_2_${condition1_index}" id="score_2_${condition1_index}" size=5 value="${condition1.score2}">的教师属于:</td>
	      			<td>
	      				<select name="group1_${condition1_index}">
	      				<#list groups as group>
	      					<#if group.groupId==condition1.groupId>
	      						<option selected value="${group.groupId}">${group.groupName}</option>
	      					<#else>
	      						<option value="${group.groupId}">${group.groupName}</option>
	      					</#if>
	      				</#list>
	      				</select>
	      			</td>
	      		</tr>		    
		    <#else>
		    	<#if condition1.conditionType==1>
		      		<tr>
		      			<td width="30%" align="right">分数大于<input type="text" name="score_1_${condition1_index}" id="score_1_${condition1_index}" size=5 value="${condition1.score1}">的教师属于:</td>
		      			<td>
		      				<select name="group1_${condition1_index}">
		      				<#list groups as group>
		      					<#if group.groupId==condition1.groupId>
		      						<option selected value="${group.groupId}">${group.groupName}</option>
		      					<#else>
		      						<option value="${group.groupId}">${group.groupName}</option>
		      					</#if>
		      				</#list>
		      				</select>
		      			</td>
		      		</tr>		    
		    	<#else>
		      		<tr>
		      			<td width="30%" align="right">分数从<input type="text" name="score_1_${condition1_index}" id="score_1_${condition1_index}" size=5 value="${condition1.score1}">到<input type="text" name="score_2_${condition1_index}" id="score_2_${condition1_index}" size=5 value="${condition1.score2}">的教师属于:</td>
		      			<td>
		      				<select name="group1_${condition1_index}">
		      				<#list groups as group>
		      					<#if group.groupId==condition1.groupId>
		      						<option selected value="${group.groupId}">${group.groupName}</option>
		      					<#else>
		      						<option value="${group.groupId}">${group.groupName}</option>
		      					</#if>
		      				</#list>
		      				</select>
		      			</td>
		      		</tr>		    	
		    	</#if>	
		    </#if>

      	  </#list>
      	  </#if>
      	  </table>
      	  </div>
      	  </td>
      	  </tr>
      	</table>
      </td>
    </tr>
    <tr>  
      <td>按教师设置:</td>
      <td valign="top">
      	<table border=0 width="100%">
		  <#if condition2list??>
		  <#list condition2list as condition2>
      		<tr>
      			<td width="30%" align="right">${condition2.teacherType}&nbsp;&nbsp;属于:</td>
      			<td>
      				<select name="group2_${condition2.id}">
      				<#list groups as group>
      					<#if group.groupId==condition2.groupId>
      						<option selected value="${group.groupId}">${group.groupName}</option>
      					<#else>
      						<option value="${group.groupId}">${group.groupName}</option>
      					</#if>
      				</#list>
      				</select>
      			</td>
      	    </tr>
      	  </#list>
      	  </#if>
      	</table>
      </td>
    </tr>
  </tbody>
</table>

<div class='funcButton'>
    <input type='button'  class='button' value=' 保 存 ' onclick='Save();'/>
	<input type='button' class='button' value=' 返 回 ' onclick='returnx();' />
</div>
</form>

</body>
</html>
