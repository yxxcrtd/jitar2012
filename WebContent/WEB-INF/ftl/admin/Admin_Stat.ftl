<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>个人统计</title>
<link rel="stylesheet" type="text/css" href="../css/manage.css" />
<link rel="stylesheet" type="text/css" href="../js/datepicker/calendar.css" />  
<script src='../js/datepicker/calendar.js' type="text/javascript"></script>
<script type="text/javascript" src="../js/stat.js"></script>
<script type="text/javascript">
<!--
function stat(search_form, type) {
    if (type == 1) {
        search_form.cmd.value = "list";
    } else {
        document.getElementById("btnInit").disabled=true;
        search_form.cmd.value = "stat";
    }
    search_form.submit();
}
function submitData() {
	document.getElementById("content").style.display = "block";
	document.getElementById("realCont").style.display = "none";
	document.search_form.submit();
}
//-->
</script>
</head>

<body>
<h2>个人统计</h2>
<div id="tips" style="position: absolute; border: 1px solid #ccc; padding: 0px 3px; color: #FF0000; display: none; height: 20px; line-height: 20px; background: #fcfcfc"></div>
<@s.form name="search_form" action="admin_stat" method="post">
<input type="hidden" name="cmd" value="list" />
<input type="hidden" name="statGuid" value="${statGuid!}" />
&nbsp;关键字：<input type="text" id="k" name="k" value="${k!?html}" style="width: 85px;" onFocus="this.select(); tips('k','关键字可以输入：用户Id、登录名、呢称或真实姓名')" onMouseover="this.focus();" onBlur="showTips();" />
开始日期:<input type="text" id="beginDate" name="beginDate" value="${beginDate!?html}" style="width: 83px;" maxLength="10" readonly="readonly"/>
结束日期:<input type="text" id="endDate" name="endDate" value="${endDate!?html}" style="width: 83px;" maxLength="10"  readonly="readonly" /><select name="subjectId">
    <option value="">所属学科</option>
        <#if subject_list??>
            <#list subject_list as subj>
                <option value="${subj.msubjId}" ${(subj.msubjId==(subjectId!0))?string('selected','')}>
                    ${subj.msubjName!?html}
                </option>
            </#list>
        </#if>
</select><select name="gradeId">
    <option value=''>所属学段</option>
    <#if grade_list??>
        <#list grade_list as grade>
            <option value="${grade.gradeId}" <#if grade.gradeId==(gradeId!0)>selected</#if>>
                <#if grade.isGrade>
                    ${grade.gradeName!?html}
                <#else>
                    &nbsp;&nbsp;${grade.gradeName!?html}
                </#if>
            </option>
        </#list>
    </#if>
</select>
<#if unitId??>
<input type='hidden' name='unitId' value='${unitId}' />
<#if from??>
<input type='hidden' name='from' value='${from}' />
</#if>
<#else>
<#if unit_list??><select name="unitId">
    <option value="">所属机构</option>
        <#list unit_list as unit>
            <option value="${unit.unitId}" ${(unit.unitId==(unitId!0))?string('selected', '')}>
                ${unit.unitTitle!?html}
            </option>
        </#list>
</select></#if>
   </#if> 
   <#if group_catetory_list??><select name='categoryId'>
    <option value="">所有群组分类</option>
        <#list group_catetory_list.all as c>
            <#if categoryId??>
                <#if c.categoryId==categoryId>
                    <option selected value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
                <#else>
                    <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
                </#if>
            <#else>
                <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
            </#if> 
        </#list>
</select></#if><#if teachertype??><select name='teachertype'>
	<option value="0" ${(teachertype==0)?string('selected', '')}>全部教师</option>
	<#if UserType_List??>
	<#list UserType_List as ut>
	<option value="${ut.typeId}" ${(teachertype==ut.typeId)?string('selected', '')}>${ut.typeName}</option>
	</#list>
	</#if>            	
</select></#if>
<input type="button" class="button" value=" 统  计 " onclick="submitData()" id="btnInit" />
<input type="button" class="button" value=" 导  出 " onClick="stat(search_form, 2);" />
</@s.form>

<div id="content" style="text-align: center; display: none;">
<br />
<img src="../images/load.gif" />
<br />
<br />
<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;正在统计，请稍等 ......</div>
</div>

<div id="realCont">
<table class="listTable" cellSpacing="1">
    <thead>
        <tr>
            <th>用户ID</th>
            <th>登录名</th>
            <th>真实姓名 </th>
            <th>工作室名称</th>
            <th>学段</th>
            <th>学科</th>
            <th>机构</th>
            <th>教师类型</th>
            <th>工作室访问量</th>
            <th>我的文章被访问量</th>
            <th>我的资源被访问量</th>
            <th>原创文章数</th>
            <th>转载文章数</th>
            <th>推荐文章数</th>
            <th>我的文章被评论数</th>
            <th>我发出的文章评论数</th>
            <th>资源数</th>
            <th>推荐资源数</th>
            <th>资源被评论数</th>
            <th>评论资源数</th>
            <th>资源下载数</th>
            <th>创建协作组数</th>
            <th>加入协作组数</th>
            <th>图片数</th>
            <th>视频数</th>                        
            <th>文章得分</th>
            <th>资源得分</th>
            <th>照片得分</th>
            <th>视频得分</th>
            <th>评论得分</th>
            <th>文章奖罚分</th>
            <th>资源奖罚分</th>
            <th>评论奖罚分</th>
            <th>图片奖罚分</th>
            <th>视频奖罚分</th>
            <th>总积分</th>
            <th>用户状态</th>
        </tr>
    </thead>                
    <tbody>
    <#if userList??>
        <#if userList?size == 0>
            <tr>
                <td colSpan="36" style="color: #FF0000; font-weight: bold; text-align: left; padding: 10px;">
                     &nbsp;对不起，没有符合条件的用户信息！&nbsp;
                </td>
            </tr>
      </#if>
        <#list userList as user>
        <tr bgColor="#FFFFFF" onMouseOver="changeBgColor(this,'#E6DBC0')" onMouseOut="changeBgColor(this,'#FFFFFF')">
            <td style="text-align: center;">
                ${user.userId!}
            </td>
            <td style="padding-left: 7px;">
               <nobr> <a href="${SiteUrl}go.action?loginName=${user.loginName!}" title="点击访问该工作室" target="_blank">${user.loginName!}</a></nobr>
            </td>
            <td style="padding-left: 7px;">
                <nobr>${user.trueName!}</nobr>
            </td>
            <td style="padding-left: 7px;">
                <nobr>${user.blogName!}</nobr>
            </td>
            <td style="text-align: center;">
              <nobr>
              <#assign sg = ""/>
              <#assign ss = ""/>
              <#assign usgList = Util.getSubjectGradeListByUserId(user.userId!)>
                <#if usgList?? && (usgList?size> 0) >
                <#list usgList as usg>
                <#if usg.gradeId??><#assign sg= sg+ Util.gradeById(usg.gradeId).gradeName!?html + "<br/>"><#else><#assign sg = sg + "未设置<br/>"/></#if>
                <#if usg.subjectId??><#assign ss= ss+ Util.subjectById(usg.subjectId).msubjName!?html + "<br/>"><#else><#assign ss = ss+ "未设置<br/>" /></#if>
                </#list>
                </#if>
                ${sg!}
             </nobr>
            </td>
            <td style="text-align: center;">
              <nobr>${ss!}</nobr>
            </td>
            <td style="text-align: center;">
            <nobr>
              <#if user.unitId??>
                  ${Util.unitById(user.unitId).unitTitle!?html}
              </#if>
             </nobr>
            </td>
            <td style="padding-left: 7px;">
            <nobr>
				<#if user.userType??>
                <#assign showTypeName = Util.typeIdToName(user.userType) >
                    <#if showTypeName??>
                        <#list showTypeName?split("/") as x>
                        <#if (x?length) &gt; 0 >${x} </#if>
                        </#list> 
                    </#if>
                </#if>
             </nobr>
            </td>
            <td style="text-align: center;">
                ${user.visitCount!}
            </td>
            <td style="text-align: center;">
                ${user.visitArticleCount!}
            </td>
            <td style="text-align: center;">
                ${user.visitResourceCount!}
            </td>
            <td style="text-align: center;">
                ${user.myArticleCount!}
            </td>
            <td style="text-align: center;">
                ${user.otherArticleCount!}
            </td>
            <td style="text-align: center;">
                ${user.recommendArticleCount!}
            </td>
            <td style="text-align: center;">
                ${user.articleCommentCount!}
            </td>
            <td style="text-align: center;">
                ${user.articleICommentCount!}
            </td>
            <td style="text-align: center;">
                ${user.resourceCount!}
            </td>
            <td style="text-align: center;">
                ${user.recommendResourceCount!}
            </td>
            <td style="text-align: center;">
                ${user.resourceCommentCount!}
            </td>
            <td style="text-align: center;">
                ${user.resourceICommentCount!}
            </td>
            <td style="text-align: center;">
                ${user.resourceDownloadCount!}
            </td>
            <td style="text-align: center;">
                ${user.createGroupCount!}
            </td>
            <td style="text-align: center;">
                ${user.jionGroupCount!}
            </td>
            <td style="text-align: center;">
                ${user.photoCount!}
            </td>
            <td style="text-align: center;">
                ${user.videoCount!}
            </td>	                    
            <td style="text-align: center;">${user.articleScore!}</td>
            <td style="text-align: center;">${user.resourceScore!}</td>
            <td style="text-align: center;">${user.photoScore!}</td>
            <td style="text-align: center;">${user.videoScore!}</td>
            <td style="text-align: center;">${user.commentScore!}</td>
            
            <td style="text-align: center;">
                <#if user.articlePunishScore??>
                	${-1*user.articlePunishScore}
                <#else>
                    ${user.articlePunishScore!}
                </#if>
            </td>
            <td style="text-align: center;">
                <#if user.resourcePunishScore??>
                	${-1*user.resourcePunishScore}
                <#else>
                    ${user.resourcePunishScore!}
                </#if>
            </td>
            <td style="text-align: center;">
                <#if user.commentPunishScore??>
                	${-1*user.commentPunishScore}
                <#else>
                    ${user.commentPunishScore!}
                </#if>
            </td>
            <td style="text-align: center;">
                <#if user.photoPunishScore??>
                	${-1*user.photoPunishScore}
                <#else>
                    ${user.photoPunishScore!}
                </#if>
            </td>
            <td style="text-align: center;">
                <#if user.videoPunishScore??>
                	${-1*user.videoPunishScore}
                <#else>
                    ${user.videoPunishScore!}
                </#if>
            </td>
            <td style="text-align: center;">
                ${user.userScore!}
            </td>
            <td style="text-align: center;">
            <nobr>
            	<#if user.userStatus??>
                    <#if user.userStatus! == 0>正常
                        <#elseif user.userStatus == 1><font style="color: #FF0000;">待审核</font>
                        <#elseif user.userStatus == 3><font style="color: #FF0000;">已锁定</font>
                        <#elseif user.userStatus == 2><font style="color: #FF0000;">已删除</font>
                        <#else><font style="color: #FF0000;">未知状态</font>
                    </#if>
                </#if>
             </nobr>
            </td>
        </tr>
        </#list>
     <#else>
      <tr>
        <td colSpan="36" style="color: #FF0000; font-weight: bold; text-align: left; padding: 10px;">
                     
      <#if init??>
      	请输入查询条件统计查询。
         <#else>
       &nbsp;对不起，没有符合条件的用户信息！&nbsp;
               
     </#if>
      </td>
            </tr>
     </#if>
    </tbody>
</table>

<#if userList?? && userList?size != 0>
    <div style="width: 100%; text-align: right; margin: 3px auto 3px;">
        <#include "../inc/pager.ftl">
    </div>
</#if>
</div>
<script>
calendar.set("beginDate");
calendar.set("endDate");
</script>
</body>
</html>
