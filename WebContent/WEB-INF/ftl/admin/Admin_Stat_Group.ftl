<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <title>协作组统计</title>
    <script type="text/javascript" src="../js/stat.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/manage.css" />
    <link rel="stylesheet" type="text/css" href="../js/datepicker/calendar.css" />  
   <script src='../js/datepicker/calendar.js' type="text/javascript"></script>
   <script type="text/javascript">
    <!--
    function stat(search_form, type) {
        if (type == 1) {
            search_form.cmd.value = "list";
        } else {
            search_form.cmd.value = "stat";
        }
        search_form.submit();
    }
    //-->
    </script>
</head>

<body>
    <h2>协作组统计</h2>

	<div id="tips" style="position: absolute; border: 1px solid #ccc; padding: 0px 3px; color: #FF0000; display: none; height: 20px; line-height: 20px; background: #fcfcfc"></div>
    <@s.form name="search_form" action="admin_stat_group" method="post">
        <input type="hidden" name="cmd" value="list" />            
        &nbsp;关键字：<input type="text" id="k" name="k" value="${k!?html}" style="width: 88px;" onFocus="this.select(); tips('k', '关键字可以输入：协作组Id,协作组标题,协作组标签或协作组介绍')" onMouseover="this.focus();" onBlur="showTips();" />
开始日期:<input type="text" id="beginDate" name="beginDate" value="${beginDate!?html}" style="width: 88px;" maxLength="10" readonly="readonly" />
结束日期:<input type="text" id="endDate" name="endDate" value="${endDate!?html}" style="width: 88px;" maxLength="10" readonly="readonly" />
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
        <#if grade_list??>
            <select name="gradeId">
                <option value=''>所属学段</option>
                    <#list grade_list as grade>
                    <option value="${grade.gradeId}" <#if grade.gradeId==(gradeId!0)>selected</#if>>
                        <#if grade.isGrade>
                            ${grade.gradeName!?html}
                        <#else>
                            &nbsp;&nbsp;${grade.gradeName!?html}
                        </#if>
                    </option>
                </#list>
            </select>
        </#if>
        <#if group_catetory_list??>
            <select name="categoryId">
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
            </select>
        </#if>
        <input type="submit" class="button" value="统&nbsp;计" />
        <input type="button" class="button" value="导&nbsp;出" onClick="stat(search_form, 2);" />

        <table class="listTable" cellSpacing="1">
            <thead>
                <tr>
                    <th width="10%">
                        &nbsp;协作组ID&nbsp;
                    </th>
                    <th width="25%">
                        &nbsp;协作组名称&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </th>
                    <th width="15%">
                        &nbsp;创建者&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </th>
                    <th width="20%">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;创建日期&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </th>
                    <th width="18%">
                        &nbsp;协作组分类&nbsp;&nbsp;&nbsp;
                    </th>
                    <th width="20%">
                        &nbsp;学段学科&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </th>
                    <th width="10%">
                        &nbsp;&nbsp;&nbsp;访问量&nbsp;&nbsp;&nbsp;
                    </th>
                    <th width="10%">
                        &nbsp;&nbsp;&nbsp;成员数&nbsp;&nbsp;&nbsp;
                    </th>
                    <th width="10%">
                        &nbsp;&nbsp;&nbsp;文章数&nbsp;&nbsp;&nbsp;
                    </th>
                    <th width="10%">
                        &nbsp;&nbsp;&nbsp;资源数&nbsp;&nbsp;&nbsp;
                    </th>
                    <th width="10%">
                        &nbsp;&nbsp;&nbsp;主题数&nbsp;&nbsp;&nbsp;
                    </th>
                    <th width="10%">
                        &nbsp;&nbsp;&nbsp;讨论数&nbsp;&nbsp;&nbsp;
                    </th>
                    <th width="10%">
                        &nbsp;&nbsp;&nbsp;活动数&nbsp;&nbsp;&nbsp;
                    </th>
                    <th width="10%">
                        &nbsp;&nbsp;协作组状态&nbsp;&nbsp;
                    </th>
                </tr>
            </thead>                
            <tbody>
            <#if groupList??>
                <#if groupList?size = 0>
                    <tr>
                        <td colSpan="10" style="color: #FF0000; font-weight: bold; text-align: center; padding: 10px;">
                            &nbsp;对不起，没有符合条件的协作组信息！&nbsp;
                        </td>
                    </tr>
                </#if>
                
                <#list groupList as group>
                    <tr bgColor="#FFFFFF" onMouseOver="changeBgColor(this,'#E6DBC0')" onMouseOut="changeBgColor(this,'#FFFFFF')">
                        <td style="text-align: center;">
                            ${group.groupId}
                        </td>
                        <td style="padding-left: 5px;">
                            <a href='${SiteUrl!}g/${group.groupName}' target='_blank' title='${group.groupIntroduce!?html}'>${group.groupTitle!}</a>
                        </td>
                        <td style="padding-left: 5px;">
                            <a href="${SiteUrl}go.action?loginName=${Util.userById(group.createUserId).loginName!?html}" title="点击访问该工作室" target="_blank">${Util.userById(group.createUserId).trueName!?html}</a>
                        </td>
                        <td style="text-align: center;">
                            ${group.createDate?string('yyyy-MM-dd HH:mm:ss')}
                        </td>
                        <td style="padding-left: 5px;">
                            <#if group_catetory_list??>
                                <#list group_catetory_list.all as c>
                                    <#if group.categoryId??>
                                        <#if c.categoryId == group.categoryId>
                                            ${c.name}
                                        </#if>
                                    </#if>
                                </#list>
                            </#if>
                        </td>
                        <td style="padding-left: 5px;">
                            ${group.XKXDName!?html}
                        </td>
                        <td style="text-align: center;">
                            ${group.visitCount}
                        </td>
                        <td style="text-align: center;">
                            ${group.userCount}
                        </td>
                        <td style="text-align: center;">
                            ${group.articleCount}
                        </td>
                        <td style="text-align: center;">
                            ${group.resourceCount}
                        </td>
                        <td style="text-align: center;">
                            ${group.topicCount}
                        </td>
                        <td style="text-align: center;">
                            ${group.discussCount}
                        </td>
                        <td style="text-align: center;">
                            ${group.actionCount}
                        </td>
                        <td style="text-align: center;">
                            <#if group.groupState == 0>正常
                                <#elseif group.groupState == 1><font style="color: #FF0000;">待审核</font>
                                <#elseif group.groupState == 2><font style="color: #FF0000;">已锁定</font>
                                <#elseif group.groupState == 3><font style="color: #FF0000;">已删除</font>
                                <#elseif group.groupState == 4><font style="color: #FF0000;">已隐藏</font>
                                <#else><font style="color: #FF0000;">未知状态</font>
                            </#if>
                        </td>
                    </tr>
                </#list>
             <#else>
              <tr>
				         <td colSpan="10" style="color: #FF0000; font-weight: bold; text-align: center; padding: 10px;">
				         <#if init??>
				         请输入查询条件进行统计查询。
				         <#else>               
				                          对不起，没有符合条件的群组信息！
				         </#if>
                  </td>
              </tr>
             </#if>
            </tbody>
        </table>
        
        <#if groupList?? && groupList?size != 0>
            <div style="width: 100%; text-align: right; margin: 3px auto 3px;">
                <#include "../inc/pager.ftl">
            </div>
        </#if>
    </@s.form>
<script>
calendar.set("beginDate");
calendar.set("endDate");
</script>
</body>
</html>
