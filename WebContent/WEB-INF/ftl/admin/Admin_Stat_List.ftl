<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
        <title>站点统计</title>
        <link rel="stylesheet" type="text/css" href="../css/manage.css" />
        <script type="text/javascript">
        <!--
        function stat(search_form) {
            search_form.cmd.value = "stat";
            search_form.submit();
        }
        //-->
        </script>
    </head>

    <body>
        <h2>${typeName!'站点统计'}</h2>

        <form name="search_form" action="?" method="get">
            <div class='search'>
            	<input type="hidden" name="cmd" value="list" />
            	<input type="hidden" name="type" value="${type!?html}" />
            	&nbsp;关键字：<input type="text" name="k" value="${k!?html}" size="12" onFocus="this.select();" onmouseover="this.focus();" />
                <input type="text" name="beginDate" value="${beginDate!?html}" size="12" onFocus="this.select();" onmouseover="this.focus();" />
                <input type="text" name="endDate" value="${endDate!?html}" size="12" onFocus="this.select();" onmouseover="this.focus();" />
                <select name="subjectId">
            		<option value="">所属学科</option>
                        <#if subject_list??>
                            <#list subject_list as subj>
                                <option value="${subj.msubjId}" ${(subj.msubjId==(subjectId!0))?string('selected','')}>
                                    ${subj.msubjName!?html}
                                </option>
                            </#list>
                        </#if>
                </select>
                
                <select name="gradeId">
                    <option value=''>所属学段</option>
                    <#if grade_list??>
                        <#list grade_list as grade>
                            <#if grade.isGrade>
                                <option value="${grade.gradeId}" <#if grade.gradeId==(gradeId!0)>selected</#if>>
                                    <#if grade.isGrade>
                                        ${grade.gradeName!?html}
                                    <#else>
                                        &nbsp;&nbsp;${grade.gradeName!?html}
                                    </#if>
                                </option>
                            </#if>
                        </#list>
                    </#if>
                </select>
                
                <select name="unitId">
                    <option value="">所属机构</option>
                    <#if unit_list??>
                        <#list unit_list as unit>
                            <option value="${unit.unitId}" ${(unit.unitId==(unitId!0))?string('selected', '')}>
                                ${unit.unitName!?html}
                            </option>
                        </#list>
                    </#if>
                </select>
            	
            	<input type="submit" class="button" value="检&nbsp;&nbsp;索" />
                <input type="button" class="button" value="导&nbsp;&nbsp;出" onClick="stat(search_form);" />
            </div>
        </form>

        <form name="list_form" action="?" method="post">
            <input type="hidden" name="cmd" value='list' />
            <table class="listTable" cellSpacing="1">
                <thead>
                    <tr>
                        <th width="10%">
                            &nbsp;用户ID&nbsp;
                        </th>
                        <th width="10%">
                            &nbsp;登录名&nbsp;
                        </th>
                        <th width="15%">
                            &nbsp;&nbsp;真实姓名&nbsp;&nbsp;
                        </th>
                        <th width="20%">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;工作室名称&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </th>
                        <th width="10%">
                            &nbsp;&nbsp;&nbsp;&nbsp;学科&nbsp;&nbsp;&nbsp;&nbsp;
                        </th>
                        <th width="10%">
                            &nbsp;&nbsp;&nbsp;&nbsp;学段&nbsp;&nbsp;&nbsp;&nbsp;
                        </th>
                     
                        <th width="15%">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机构&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </th>
                        <th width="10%">
                            &nbsp;工作室访问量&nbsp;
                        </th>
                        <th width="10%">
                            &nbsp;文章数&nbsp;
                        </th>
                        <th width="10%">
                            &nbsp;推荐文章数&nbsp;
                        </th>
                        <th width="10%">
                            &nbsp;文章评论数&nbsp;
                        </th>
                        <th width="10%">
                            &nbsp;资源数&nbsp;
                        </th>
                        <th width="10%">
                            &nbsp;推荐资源数&nbsp;
                        </th>
                        <th width="10%">
                            &nbsp;资源评论数&nbsp;
                        </th>
                        <th width="10%">
                            &nbsp;资源下载数&nbsp;
                        </th>
                        <th width="10%">
                            &nbsp;创建协作组数&nbsp;
                        </th>
                        <th width="10%">
                            &nbsp;加入协作组数&nbsp;
                        </th>
                        <th width="10%">
                            &nbsp;照片数&nbsp;
                        </th>
                        <th width="10%">
                            &nbsp;积分&nbsp;
                        </th>
                        <th width="10%">
                            &nbsp;用户状态&nbsp;
                        </th>
                    </tr>
                </thead>
                
                <tbody>
                    <#if userList?size == 0>
                        <tr>
                            <td colSpan="10" style="color: #FF0000; font-weight: bold; text-align: center; padding: 10px;">
                                &nbsp;对不起，没有符合条件的用户信息！&nbsp;
                            </td>
                        </tr>
                    </#if>
                    
                    <#list userList as user>
                    <tr>
                        <td style="text-align: center;">
                            ${user.userId}
                        </td>
                        <td style="padding-left: 7px;">
                            <a href="${SiteUrl}go.action?loginName=${user.loginName!}" title="点击访问该工作室" target="_blank">${user.loginName!}</a>
                        </td>
                        <td style="padding-left: 7px;">
                            ${user.trueName!}
                        </td>
                        <td style="padding-left: 7px;">
                            ${user.blogName!}
                        </td>
                        <td style="text-align: center;">
                            <#if user.subjectId??>
                                ${Util.subjectById(user.subjectId).msubjName!?html}
                            </#if>
                        </td>
                        <td style="text-align: center;">
                            <#if user.gradeId??>
                                ${Util.gradeById(user.gradeId).gradeName!?html}
                            </#if>
                        </td>                        
                        <td style="text-align: center;">
                            <#if user.unitName??>
                                ${user.unitName?html}
                            </#if>
                        </td>
                        <td style="text-align: center;">
                            ${user.visitCount}
                        </td>
                        <td style="text-align: center;">
                            ${user.articleCount}
                        </td>
                        <td style="text-align: center;">
                            ${user.recommendArticleCount}
                        </td>
                        <td style="text-align: center;">
                            ${user.articleCommentCount}
                        </td>
                        <td style="text-align: center;">
                            ${user.resourceCount}
                        </td>
                        <td style="text-align: center;">
                            ${user.recommendResourceCount}
                        </td>
                        <td style="text-align: center;">
                            ${user.resourceCommentCount}
                        </td>
                        <td style="text-align: center;">
                            ${user.resourceDownloadCount}
                        </td>
                        <td style="text-align: center;">
                            ${user.createGroupCount}
                        </td>
                        <td style="text-align: center;">
                            ${user.jionGroupCount}
                        </td>
                        <td style="text-align: center;">
                            ${user.photoCount}
                        </td>
                        <td style="text-align: center;">
                            ${user.userScore}
                        </td>
                        <td style="text-align: center;">
                            <#if user.userStatus == 0>正常
                                <#elseif user.userStatus == 1><font style="color: #FF0000;">待审核</font>
                                <#elseif user.userStatus == 3><font style="color: #FF0000;">已锁定</font>
                                <#elseif user.userStatus == 2><font style="color: #FF0000;">已删除</font>
                                <#else><font style="color: #FF0000;">未知状态</font>
                            </#if>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>            
            <#if userList?size != 0>
                <div style="width: 100%; text-align: right; margin: 3px auto 3px;">
                    <#include "../inc/pager.ftl">
                </div>
            </#if>
        </form>
    </body>
</html>
