<#assign grpName="协作组">
<#assign grpShowName="小组">
<#assign trDisplay="none">
<#if isKtGroup??>
    <#if isKtGroup=="1">
        <#assign grpName="课题组"> 
        <#assign grpShowName="课题">
        <#assign trDisplay="">
    <#elseif isKtGroup=="2">
        <#assign grpName="备课组"> 
        <#assign grpShowName="小组">
        <#assign trDisplay="none">
    <#else>
        <#assign grpName="协作组">
        <#assign grpShowName="小组">
        <#assign trDisplay="none">
    </#if>
</#if>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title></title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css">
    <script src="${SiteUrl}js/calendar/WdatePicker.js" type="text/javascript"></script>
    <script type="text/javascript">
    var subjectoption="";
    var gradeoption="";
    
    gradeoption="<option value='0'>选择学段</option>";
                <#if grade_list?? > <#list grade_list as grade>
    gradeoption=gradeoption+"<option value='${grade.gradeId}'><#if grade.isGrade>${grade.gradeName!?html}<#else>&nbsp; &nbsp;${grade.gradeName!?html}</#if></option>";
                </#list> 
                </#if>
                
    subjectoption="<option value=''>选择学科</option>";
                <#if subject_list?? > <#list subject_list as subject>
    subjectoption=subjectoption+"<option value='${subject.msubjId}'>${subject.msubjName!?html}</option>";
                </#list> </#if>
                    
    function selectUsers()
    {
        //singleuser=0 多选用户  =1单选用户
        //inputUser_Id 和 inputUserName_Id 是表单中的 用户ID和用户Name   必须使用id
        //showgroup=0 不显示群组 =1显示群组
        //单选用户时可以不设置showgroup，肯定不显示群组；多选用户可以设置是否显示群组
        var vReturnValue=""; 
        vReturnValue = window.showModalDialog('../selectUser.action?showgroup=0&singleuser=0&more=1','','dialogWidth:700px;dialogHeight:450px;scroll:yes;status:no');
        //vReturnValue = window.open("${SiteUrl}manage/common/user_select.action?type=multi&idTag=uid&titleTag=utitle");
        if(vReturnValue==undefined){
            return;
        }
        if(vReturnValue==""){
            return;
        }        
        getUserList(vReturnValue);
    }
    function getUserList(userids)
    {
        var arr=userids.split("|");
        document.getElementById("teacherId").value=arr[0];
        document.getElementById("teacherName").value=arr[1];
        var names=arr[1].split(",");
        var genders=arr[3].split(",");
        var units=arr[4].split(",");
        var _html="";
         var us=arr[0].split(",");
        for(i=0;i<us.length;i++)
        {     
            _html=_html+"<table border='0' cellspacing='0' style='width:100%' id='ktTable"+us[i]+"'>";
            _html=_html+"    <tr>";
            _html=_html+"        <td align='right' valign='top'>姓名:</td>";
            _html=_html+"        <td>"+names[i]+"</td>";    
            _html=_html+"    </tr>";
            _html=_html+"    <tr>";
            _html=_html+"        <td align='right' valign='top'>性别:</td>";
            _html=_html+"        <td>"+ genders[i] +"</td>";    
            _html=_html+"    </tr>";
            _html=_html+"    <tr>";
            _html=_html+"        <td align='right' valign='top'>所在单位:</td>";
            _html=_html+"        <td>"+ units[i] +"</td>";    
            _html=_html+"    </tr>";            
            _html=_html+"    <tr>";
            _html=_html+"        <td align='right' valign='top'>行政职务:</td>";
            _html=_html+"        <td><input type='text' name='gktXZZW"+us[i]+"' value=''/></td>";    
            _html=_html+"    </tr>";
            _html=_html+"    <tr>";
            _html=_html+"        <td align='right' valign='top'>专业职务:</td>";
            _html=_html+"        <td><input type='text' name='gktZYZW"+us[i]+"' value=''/></td>";    
            _html=_html+"    </tr>";
            _html=_html+"    <tr>";
            _html=_html+"        <td align='right' valign='top'>学历:</td>";
            _html=_html+"        <td><input type='text' name='gktXL"+us[i]+"' value=''/></td>";    
            _html=_html+"    </tr>";
            _html=_html+"    <tr>";
            _html=_html+"        <td align='right' valign='top'>学位:</td>";
            _html=_html+"        <td><input type='text' name='gktXW"+us[i]+"' value=''/></td>";    
            _html=_html+"    </tr>";
            _html=_html+"    <tr>";
            _html=_html+"        <td align='right' valign='top'>研究专长:</td>";
            _html=_html+"        <td><textarea name='gktYJZC"+us[i]+"' style='width:500px;height:80px'></textarea></td>";    
            _html=_html+"    </tr>";
            _html=_html+"</table>";
            _html=_html+"<input type='hidden' name='xkxdNum"+us[i]+"' id='xkxdNum"+us[i]+"' value=1>";
            _html=_html+"<input type='hidden' name='KTteacherId' value="+us[i]+">";
            _html=_html+"<input type='hidden' name='KTteacherName"+us[i]+"' value='"+names[i] +"'>";
            _html=_html+"<input type='hidden' name='KTteacherGender"+us[i]+"' value='"+genders[i] +"'>";
            _html=_html+"<input type='hidden' name='KTteacherUnit"+us[i]+"' value='"+units[i] +"'>";
            _html=_html+"<br/><hr/><br/>";
        }     
        document.getElementById("ktUserInfo").innerHTML=_html;
    }
    
function appendXKXD(btn)
{
        var sHtml;
       xdIndex=document.getElementById("xkxdNum").value;
       if(xdIndex==""){xdIndex="1";}
       sHtml="";
       sHtml=sHtml+"  <table border='0' cellspacing='0' style='width:100%;' id='xkxdTable_'"+xdIndex+">";
       sHtml=sHtml+"  <tr>";
       sHtml=sHtml+"  <td>";
        sHtml=sHtml+  "<select name='gradeId_"+ xdIndex +"'  id='gradeId_"+ xdIndex +"'  onchange='grade_changed(this,\"subjectId_"+xdIndex+"\",\"loading_"+xdIndex+"\")'>";
        sHtml=sHtml+  gradeoption;
        sHtml=sHtml+"</select>";
        sHtml=sHtml+"  <select name='subjectId_"+xdIndex+"' id='subjectId_"+xdIndex+"'>";
        sHtml=sHtml+ subjectoption;
        sHtml=sHtml+"   </select>";
        sHtml=sHtml+"   <span id='loading_"+xdIndex+"' style='display:none'>";
        sHtml=sHtml+"   <img src='images/loading.gif' align='absmiddle' hsapce='3' />正在加载学科信息...";
        sHtml=sHtml+"   </span>";
       sHtml=sHtml+"  </td>";
       sHtml=sHtml+"  </tr>";      
       sHtml=sHtml+"  </table>";
       sHtml=sHtml+"  <div id='xkxdselectHtml'></div>";
        document.getElementById("xkxdNum").value=(xdIndex+1);
        document.getElementById("xkxdselectHtml").outerHTML=sHtml;
        
}
    
    </script>		
	</head>

	<body>
		<#if group.groupId != 0> <#include 'group_title.ftl' >
		<div class='pos'>
			您现在的位置：
			<a href='group.py?cmd=home&amp;groupId=${group.groupId}'><span>${grpName}</span>管理首页</a>
			&gt;&gt;
			<span>${grpName}</span>信息
		</div>
		<#if !(canManage??)><#assign canManage = false></#if>
		<br />
		<#else >
		<h2>
			创建<span>${grpName}</span>
		</h2>
		<#assign canManage = true> </#if>

		<form name='groupForm' method='post' action='group.action'>
			<input type='hidden' name='cmd' value='save' />
			<input type='hidden' name='groupId' value='${group.groupId}' />
			<input type='hidden' name='parentId' value='${group.parentId}' />
			<table class='listTable' cellspacing='1'>
				<tbody>
                    <tr>
                        <td class='title' width='25%' align='right' valign='top'>
                            <b>${grpName}类别<font color='red'>*</font>:</b>
                        </td>
                        <td>
                            <#if isKtGroup=="0">
                                <select name="categoryId" id="categoryId" onchange="selectCategory(this)">
                                    <option value=''>
                                                                                        选择协作组分类
                                    </option>
                                    <#if syscate_tree?? > <#list syscate_tree.all as category>
                                    <option value='${category.id}' ${(category.id==(group.categoryId!0))?string('selected', '')}>${category.treeFlag2} ${category.name}</option>
                                    </#list> </#if>
                                </select>
                            <#else>
                                <input type="hidden" name="categoryId" id="categoryId" value="${group.categoryId!0}"/>
                                <#if isKtGroup=="1">
                                                                                    课题研究组
                                <#else>
                                                                                    集体备课组
                                </#if>                                
                            </#if>
                        </td>
                    </tr>
                    <tr>
                        <td class='title' align='right' valign='top'>
                            <b><span>协作组</span>分类说明：</b>
                            <br />
                            (重要)
                        </td>
                        <td>
                           <li><b>普通协作组</b> :此种类型协作组一般用于各种兴趣型协作组，具有一般功能;</li>
                           <li><b>集体备课组</b> :此种类型协作组支持制定<b>集体备课</b>计划，组织开展<b>集体备课</b>活动;</li>
                           <li><b>课题研究组</b> :此种类型协作组支持开展<b>课题研究</b>活动;</li>
                            <br/><br/>
                            请先确定合适的协作组类型，协作组一旦创建，普通协作组类型可以后期更改为集体备课组或课题研究组类型，集体备课组或课题研究组则不允许更改。创建子课题组请由主课题管理人在主课题组后台创建。
                        </td>
                    </tr>   				
					<tr>
						<td class='title' align='right' valign='top'>
							<b><span>${grpName}</span>中文标题<font color='red'>*</font>：</b>
						</td>
						<td>
							<input type="text" name="groupTitle" value="${group.groupTitle!?html}" size="32"/>
							<span>${grpName}</span>的中文标题不能和别的<span>协作组</span>重复
						</td>
					</tr>
					<tr>
						<td class='title' align='right' valign='top'>
							<b><span>${grpName}</span>英文名<font color='red'>*</font>：</b>
						</td>
						<td>
							<#if group.groupId == 0>
							<input type='text' name='groupName' value='${group.groupName!?html}' size='32' maxlength='32' />
							<span>${grpName}</span>的英文名字，也用于访问群组时候作为路径，4-16个字符，可以是英文、数字、下划线，第一个必须是英文.
							<span>${grpName}</span>创建之后名字就不能更改了, 请仔细考虑好名字并填写. 
							<#else >
							<input type='text' name='groupName' value='${group.groupName!?html}' disabled='disabled' />
							(创建之后不能修改<span>${grpName}</span>英文名字)
							</#if>
						</td>
					</tr>
                    <tr id="tr1" style="display:${trDisplay}">
                        <td class='title' align='right' valign='top'>
                            <b>立项号：</b>
                        </td>
                        <td>
                            <input type="text" name="groupKtNo" value="${group.ktNo!?html}"/>
                        </td>
                    </tr>    					
                    <tr id="tr2" style="display:${trDisplay}">
                        <td class='title' align='right' valign='top'>
                            <b>课题级别<font color='red'>*</font>：</b>
                        </td>
                        <td>
                            <select name="groupKtLevel">
                            <option value='' ${(""==(group.ktLevel!""))?string('selected', '')}></option>
                            <option value='国家级' ${("国家级"==(group.ktLevel!''))?string('selected', '')}>国家级</option>
                            <option value='省级' ${("省级"==(group.ktLevel!''))?string('selected', '')}>省级</option>
                            <option value='市级' ${("市级"==(group.ktLevel!''))?string('selected', '')}>市级</option>
                            <option value='区县级' ${("区县级"==(group.ktLevel!''))?string('selected', '')}>区县级</option>
                            <option value='校级' ${("校级"==(group.ktLevel!''))?string('selected', '')}>校级</option>
                            <option value='其他' ${("其他"==(group.ktLevel!''))?string('selected', '')}>其他</option>
                            </select>
                        </td>
                    </tr>                       
                    <tr id="tr3" style="display:${trDisplay}">
                        <td class='title' align='right' valign='top'>
                            <b>课题负责人<font color='red'>*</font>：</b>
                        </td>
                        <td>  
                        <input type="button" name="selectuser" value="点此选择课题负责人..." onClick="selectUsers();"/>  
                        <input type="hidden" name="teacherId" id="teacherId" value=""/>
                        <input type="hidden" name="teacherName" id="teacherName" value=""/>
                        <!--显示原来的课题负责人，并可以删除-->
                        <#if ktuser_list??>
                            <#list ktuser_list as ktuser>
                                <table border='0' cellspacing='0' style='width:100%'>
                                    <tr>
                                        <td align='right' valign='top' style="width:150px">姓名:</td>
                                        <td>${ktuser.teacherName!?html}</td>    
                                    </tr>
                                    <tr>
                                        <td align='right' valign='top'>性别:</td>
                                        <td>${ktuser.teacherGender!?html}</td>    
                                    </tr>
                                    <tr>
                                        <td align='right' valign='top'>所在单位:</td>
                                        <td>${ktuser.teacherUnit!?html}</td>    
                                    </tr>            
                                    <tr>
                                        <td align='right' valign='top'>行政职务:</td>
                                        <td>${ktuser.teacherXZZW!?html}</td>    
                                    </tr>
                                    <tr>
                                        <td align='right' valign='top'>专业职务:</td>
                                        <td>${ktuser.teacherZYZW!?html}</td>    
                                    </tr>
                                    <tr>
                                        <td align='right' valign='top'>学历:</td>
                                        <td>${ktuser.teacherXL!?html}</td>    
                                    </tr>
                                    <tr>
                                        <td align='right' valign='top'>学位:</td>
                                        <td>${ktuser.teacherXW!?html}</td>    
                                    </tr>
                                    <tr>
                                        <td align='right' valign='top'>研究专长:</td>
                                        <td>${ktuser.teacherYJZC!?html}</td>    
                                    </tr>
                                </table>
                                            
                            </#list>
                        </#if>
                        <span id="ktUserInfo">
                        
                        </span>
                        </td>
                    </tr>       
                    <tr id="tr4" style="display:${trDisplay}">
                        <td class='title' align='right' valign='top'>
                            <b>课题研究周期：</b>
                        </td>
                        <td>    
                            &nbsp;&nbsp;从&nbsp;&nbsp;<input name="startDate" id="startDate" class="Wdate" onClick="WdatePicker()" style="width:120px" value="<#if group.ktStartDate??>${group.ktStartDate?string("yyyy-MM-dd")}</#if>"/>&nbsp;&nbsp;到&nbsp;&nbsp;
                            <input name="endDate" id="endDate"  class="Wdate" onClick="WdatePicker()" style="width:120px" value="<#if group.ktEndDate??>${group.ktEndDate?string("yyyy-MM-dd")}</#if>"/>                        
                        </td>
                    </tr>       
                                    
					<tr>
						<td class='title' align='right' valign='top'>
							<b><span>${grpName}</span>图标：</b>
						</td>
						<td>
							<#if default_icons?? >
							<select onchange='javascript:change_icon(this)'>
								<option value=''>
									选择系统图标
								</option>
								<#list default_icons as ic>
								<option value='${ic}' ${(ic == group.groupIcon!)?string('selected', '')}>
									${Util.fileName(ic)}
								</option>
								</#list>
							</select>
							</#if>
							<img id='group_icon' src='${Util.url(group.groupIcon!"images/group_default.gif")}' width='64' height='64' border='0' />
							<input type='hidden' name='groupIcon' value='${group.groupIcon!}' />
						</td>
					</tr>
					<tr>
						<td align='right' valign='top'>
							<b>学段／学科：</b>
						</td>
						<td>
						  <table border='0' cellspacing='0' style='width:100%;'>
						  <tr>
						  <td style="width:300px">
						      
						      <#assign arrId=[]>
						      <#assign arrName=[]>
						      <#if group.XKXDId??>
						          <#if group.XKXDId?size&gt;0>
						              <#assign xxIds=group.XKXDId>
						              <#assign xxNames=group.XKXDName>
						              <#assign arrId=xxIds.split(",")>
						              <#assign arrName=xxNames.split(",")>
						          </#if>
						      </#if>
						      <#assign xkxdNum=arrId?size!0>
						      <#if xkxdNum=0>
						          <#assign xkxdNum=1>
						      </#if>
						      <input type="hidden" name="xkxdNum" id="xkxdNum" value="${xkxdNum}"/>
						      <#list arrId as xxid>
						          <#assign arrxkxuId=xxid.split("/")>
						          <#assign xdId=arrxkxuId[0]!'0'>
						          <#assign xkId=arrxkxuId[1]!'0'>
    						      <table border='0' cellspacing='0' style='width:100%;' id="xkxdTable_${xxid_index}">
    						      <tr>
    						      <td>
                                  <select name="gradeId_${xxid_index}" id="gradeId_${xxid_index}" onchange="grade_changed(this,'subjectId_${xxid_index}','subject_loading_${xxid_index}')">
                                    <option value='0'>
                                                                                        选择学段
                                    </option>
                                    <#if grade_list?? > 
                                    <#list grade_list as grade>
                                    <option value="${grade.gradeId}" ${((""+grade.gradeId)==(xdId))?string('selected', '') } >
                                    <#if grade.isGrade>${grade.gradeName!?html}<#else>&nbsp; &nbsp;${grade.gradeName!?html}</#if>
                                    </option>
                                    </#list> 
                                    </#if>
                                  </select>
                      						
        							<select name="subjectId_${xxid_index}" id="subjectId_${xxid_index}">
        								<option value=''>
        									选择学科
        								</option>
        								<#if subject_list?? > <#list subject_list as subject>
        								<option value='${subject.msubjId}' ${((""+subject.msubjId)==(xkId))?string('selected', '')} >${subject.msubjName!?html}</option>
        								</#list> </#if>
        							</select>
                                    <span id='subject_loading_${xxid_index}' style='display:none'>
                                      <img src='images/loading.gif' align='absmiddle' 
                                        hsapce='3' />正在加载学科信息...
                                    </span>
                                   </td>
                                   </tr>
                                   </table> 
                               </#list>
                               <div id="xkxdselectHtml"></div>
                            </td>
                            <td style="vertical-align:bottom">
                                <input type='button' name='addxkxd' onclick='appendXKXD(this)' value='增加学科学段'>
                            </td>
                            </tr>
                            </table>
						</td>
						
					</tr>
					<tr>
						<td class='title' align='right' valign='top'>
							<b><span>${grpName}</span>标签：</b>
						</td>
						<td>
							<input type='text' name='groupTags'
								value='${group.groupTags!?html}' size='32' />
							(多个标签请以逗号分隔)
						</td>
					</tr>
					<tr>
						<td class='title' align='right' valign='top'>
							<b><span>${grpName}</span>加入条件：</b>
						</td>
						<td>
							<input type='radio' name='joinLimit' value='0'
								${(group.joinLimit== 0)?string('checked', '')} />
							任意加入
							<input type='radio' name='joinLimit' value='1'
								${(group.joinLimit== 1)?string('checked', '')} />
							申请加入
							<input type='radio' name='joinLimit' value='2'
								${(group.joinLimit== 2)?string('checked', '')} />
							仅可邀请
						</td>
					</tr>
					<tr>
						<td class='title' align='right' valign='top'>
							<b><span>${grpName}</span>说明(<font color='red'>* 至少${Util.JitarConst.MIN_GROUP_DESC}个字</font>)：</b>
							<br />
							<br />
							正确、清晰的说明有助于用户找到您的<span>${grpName}</span>，也有助于管理员的审核！
						</td>
						<td>
							<textarea rows='4' id = "create_button" cols='50' name='groupIntroduce'>${group.groupIntroduce!?html}</textarea>
						</td>
					</tr>
			
                 </tbody>
				<tfoot>
					<tr>
						<td></td>
						<td>
							<#if canManage??>
							<input type='button'  class='button' value='${(group.groupId == 0)?string("  创 建  ", "  修   改  ")}' onclick="saveData()"/>
							</#if>
							<input type='button' class='button' value=' 返 回 ' onclick='window.history.back();' />
						</td>
					</tr>
				</tfoot>
			</table>
		</form>

		<script>
function change_icon(sel) {
  var icon = sel.options[sel.selectedIndex].value;
  if (icon == null || icon == '') return;
  
  var img = document.getElementById('group_icon');
  img.src = '../' + icon;
  
  document.groupForm.groupIcon.value = icon;
}
</script>

		<div>
			<!-- 放一些说明在这里 -->
		</div>

	</body>
</html>
<script src='../js/jitar/core.js'></script>

<script>
var gupName=0;
function selectCategory(cateSel)
{
    gupName=0;
    if(cateSel.selectedIndex==0 || cateSel.selectedIndex==-1)
    {
        return;
    }
    var cateTitle=cateSel.options[cateSel.selectedIndex].text;
    if(cateTitle.indexOf("课题研究组")>=0){
        gupName=1;
        StranBody(); 
        document.getElementById("tr1").style.display="";
        document.getElementById("tr2").style.display="";
        document.getElementById("tr3").style.display="";
        document.getElementById("tr4").style.display="";
    }else if(cateTitle.indexOf("集体备课组")>=0){
        gupName=2;
        StranBody();
        document.getElementById("tr1").style.display="none";
        document.getElementById("tr2").style.display="none";
        document.getElementById("tr3").style.display="none";
        document.getElementById("tr4").style.display="none";
    }else{
        gupName=0;
        StranBody();
        document.getElementById("tr1").style.display="none";
        document.getElementById("tr2").style.display="none";
        document.getElementById("tr3").style.display="none";
        document.getElementById("tr4").style.display="none";
    }
    
    
}

function StranBody(fobj)
{
    if(typeof(fobj)=="object"){var obj=fobj.childNodes}
    else
    {
        var obj=document.body.childNodes
    }
    for(var i=0;i<obj.length;i++)
    {
        var OO=obj.item(i)
        if("||BR|HR|TEXTAREA|".indexOf("|"+OO.tagName+"|")>0)continue;
        //if(OO.title!=""&&OO.title!=null)OO.title=StranText(OO.title);
        //if(OO.alt!=""&&OO.alt!=null)OO.alt=StranText(OO.alt);
        //if(OO.tagName=="INPUT"&&OO.value!=""&&OO.type!="text"&&OO.type!="hidden")OO.value=StranText(OO.value);
        if(OO.tagName=="SPAN"){
           OO.innerHTML=StranText(OO.innerHTML);
        }
        //if(OO.nodeType==3){OO.data=StranText(OO.data)}
        //else 
        StranBody(OO)
    }
}
function StranText(txt)
{
    if (txt==undefined)
    {
        return "";
    } 
    var s=txt;
    if(gupName==1)
    {
        s=txt.replace(/协作组/g,"课题组");
        s=s.replace(/备课组/g,"课题组");
    }
    else if (gupName==2)
    {
        s=txt.replace(/课题组/g,"备课组");
        s=s.replace(/协作组/g,"备课组");
    }
    else{
        s=txt.replace(/课题组/g,"协作组");
        s=s.replace(/备课组/g,"协作组");
    }
    
    return s;
}
function test(){
    
}
function saveData()
{    
    var min_group_desc = ${Util.JitarConst.MIN_GROUP_DESC};
    var text_len = document.getElementById('create_button').value.length;
    if(text_len<min_group_desc){
      alert("协作组说明长度最少为"+min_group_desc);
      return;
    }
    var cateId=document.groupForm.categoryId.value;
    if (cateId == null || cateId == '')
    {
        alert("请选择分类");
        return;
    }
    var gTitle=document.groupForm.groupTitle.value;
    if(gTitle=="")
    {
        alert("请输入名称");
        return;
    }
    var gName=document.groupForm.groupName.value;
    if(gName=="")
    {
        alert("请输入英文名称");
        return;
    }
    if(gName.length<4 || gName.length>16){
        alert("英文名称长度是4-16个字符");
        return;
    } 
     var chkdate = 0;
    <#if isKtGroup=="1">
        chkdate = 1;
       var level=document.groupForm.groupKtLevel.value;
        if (level == null || level == '')
        {
            alert("请选择课题级别");
            return;
        }
        <#if group.groupId == 0>
            var uIds=document.groupForm.teacherId.value;
            if (uIds== null || uIds== '')
            {
                alert("请选择课题负责人");
                return;
            }  
        </#if>
    <#else>
    if(gupName==1)
    {
       chkdate = 1;
       var level=document.groupForm.groupKtLevel.value;
        if (level == null || level == '')
        {
            alert("请选择课题级别");
            return;
        }  
        var uIds=document.groupForm.teacherId.value;
        if (uIds== null || uIds== '')
        {
            alert("请选择课题负责人");
            return;
        }  
    }
    </#if>
    
      if(chkdate==1)
      {
          
          if (document.groupForm.startDate.value != "" && document.groupForm.endDate.value != "")
          {
             var datePattern = /^(\d{4})-(\d{1,2})-(\d{1,2})$/; 
              if (! datePattern.test(document.groupForm.startDate.value)) { 
                window.alert("请填写正确的开始时间"); 
                return false; 
              }
              
              if (! datePattern.test(document.groupForm.endDate.value)) { 
                window.alert("请填写正确的结束时间"); 
                return false; 
              }    
              var d1 = new Date(document.groupForm.startDate.value.replace(/-/g, "/")); 
              var d2 = new Date(document.groupForm.endDate.value.replace(/-/g, "/"));
            
              if (Date.parse(d2) - Date.parse(d1) < 0) { 
                window.alert("开始日期必须早于结束日期"); 
                return false;
              }  
          }
      }  
    document.groupForm.submit();
}

function grade_changed(sel,subId,loadingId)
{
  // 得到所选学科.
  var gradeId = sel.options[sel.selectedIndex].value;
  var subject_sel = document.getElementById(subId);

  if (gradeId == null || gradeId == '' || gradeId == 0) {
    clear_options(subject_sel);
    add_option(subject_sel, '', '选择学科');
    return;
  } 
  subject_sel.disabled = true;
  var img = document.getElementById(loadingId);
  img.style.display = '';
  
  // 用 AJAX 请求该区县下的机构, 并填充到 unitId select 中.
  url = '${SiteUrl}manage/admin_subject.py?cmd=subject_options&gradeId=' + gradeId + '&tmp=' + Math.random();
  new Ajax.Request(url, {
    method: 'get',
    onSuccess: function(xport) { 
        var options = eval(xport.responseText);
        clear_options(subject_sel);
        add_option(subject_sel, '', '选择学科');
        for (var i = 0; i < options.length; ++i)
          add_option(subject_sel, options[i][0], options[i][1]);
        img.style.display = 'none';
        subject_sel.disabled = false;
      }
  });
}
function clear_options(sel) {
  sel.options.length = 0;
}
function add_option(sel, val, text) {
    sel.options[sel.options.length] = new Option(text,val)
    
}
</script>