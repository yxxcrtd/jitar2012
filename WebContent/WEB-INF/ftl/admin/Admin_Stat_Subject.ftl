<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
        <title>学科统计</title>
        <script type="text/javascript" src="../js/stat.js"></script>
        <link rel="stylesheet" type="text/css" href="../css/manage.css" />
        <script type="text/javascript">
        <!--
        function stat(search_form, type) {
          document.getElementById("nocontent").style.display="none";
          if (type == 1) {
              search_form.cmd.value = "list";
              document.getElementById("content").style.display="block";
          } else {
                <#if subjectList??>
                    <#if subjectList?size == 0>
                            alert("请先进行统计");
                            return;
                    </#if>
                <#else>
                            alert("请先进行统计");
                            return;
                </#if>
              search_form.cmd.value = "stat";
          }
          search_form.submit();
        }
        //-->
        </script>
    </head>

    <body>
        <h2>学科统计</h2>
        <div id="tips" style="position: absolute; border: 1px solid #ccc; padding: 0px 3px; color: #FF0000; display: none; height: 20px; line-height: 20px; background: #fcfcfc"></div>
        <@s.form name="search_form" action="admin_stat_subject" method="post">
            <input type="hidden" name="cmd" value="list" />
            <#if from??>
            <input type='hidden' name='from' value='${from}' />
            <#else>
            &nbsp;关键字：<input type="text" id="k" name="k" value="${k!?html}" style="width: 88px;" onFocus="this.select(); tips('k', '关键字输入学科名称')" onMouseover="this.focus();" onBlur="showTips();" />
            </#if>
            From:<input type="text" id="beginDate" name="beginDate" value="${beginDate!?html}" style="width: 88px;" maxLength="10" onFocus="this.select(); tips('beginDate', '开始日期格式示例：2008-01-01')" onMouseover="this.focus();" onBlur="showTips();" />
            to:<input type="text" id="endDate" name="endDate" value="${endDate!?html}" style="width: 88px;" maxLength="10" onFocus="this.select(); tips('endDate', '结束日期格式示例：2008-01-01')" onMouseover="this.focus();" onBlur="showTips();" />
            
            <input type="button" class="button" value="统&nbsp;计" onClick="stat(search_form, 1);" />
            <input type="button" class="button" value="导&nbsp;出" onClick="stat(search_form, 2);" />
            
            <table class="listTable" cellSpacing="1">
                <thead>
                    <tr>
                        <th width="7%">
                            <@s.label value="学科ID" />
                        </th>
                        <th width="7%">
                            <@s.label value="学科名称" />
                        </th>
                        <th width="7%">
                            <@s.label value="工作室总数" />
                        </th>
                        <th width="7%">
                            <@s.label value="原创文章数" />
                        </th>
                        <th width="7%">
                            <@s.label value="原创文章积分" />
                        </th>   
                        <th width="7%">
                            <@s.label value="转载文章数" />
                        </th>
                        <th width="7%">
                            <@s.label value="转载文章积分" />
                        </th> 
                        <th width="7%">
                            <@s.label value="推荐文章数" />
                        </th>
                        <th width="7%">
                            <@s.label value="推荐文章积分" />
                        </th> 
                        <th width="7%">
                            <@s.label value="资源数" />
                        </th>
                        <th width="7%">
                            <@s.label value="资源积分" />
                        </th>                        
                        <th width="7%">
                            <@s.label value="推荐资源数" />
                        </th>
                        <th width="7%">
                            <@s.label value="推荐资源积分" />
                        </th>                        
                        <th width="7%">
                            <@s.label value="评论数" />
                        </th>
                        <th width="7%">
                            <@s.label value="评论积分" />
                        </th>
                        <th width="7%">
                            <@s.label value="协作组数" />
                        </th>
                        <th width="7%">
                            <@s.label value="集备数" />
                        </th>
                        <th width="7%">
                            <@s.label value="活动数" />
                        </th>
                    </tr>
                </thead>
                <tbody>
                <#if subjectList??>
                    <#list subjectList as sub>
                        <tr bgColor="#FFFFFF" onMouseOver="changeBgColor(this,'#E6DBC0')" onMouseOut="changeBgColor(this,'#FFFFFF')">
                            <td style="text-align: center;">
                                ${sub.subjectId}
                            </td>
                            <td style="padding-left: 10px;">
                                ${sub.subjectName!?html}
                            </td>
                            <td style="text-align: center;">
                                ${sub.userCount}
                            </td>
                            <td style="text-align: center;">
                                ${sub.originalArticleCount}
                            </td>
                            <td style="text-align: center;">
                                ${sub.originalArticleScore}
                            </td>
                            <td style="text-align: center;">
                                ${sub.referencedArticleCount}
                            </td>
                            <td style="text-align: center;">
                                ${sub.referencedArticleScore}
                            </td>
                            <td style="text-align: center;">
                                ${sub.recommendArticleCount}
                            </td>
                            <td style="text-align: center;">
                                ${sub.recommendArticleScore}
                            </td>
                            <td style="text-align: center;">
                                ${sub.resourceCount}
                            </td>
                            <td style="text-align: center;">
                                ${sub.resourceScore}
                            </td>                            
                            <td style="text-align: center;">
                                ${sub.recommendResourceCount}
                            </td>
                            <td style="text-align: center;">
                                ${sub.recommendResourceScore}
                            </td>    
                            <td style="text-align: center;">
                                ${sub.commentCount}
                            </td>
                            <td style="text-align: center;">
                                ${sub.commentScore}
                            </td>     
                            <td style="text-align: center;">
                                ${sub.groupCount}
                            </td>
                            <td style="text-align: center;">
                                ${sub.prepareCourseCountCount}
                            </td>     
                            <td style="text-align: center;">
                                ${sub.actionCount}
                            </td> 
                        </tr>
                    </#list>
                </#if>
                </tbody>
            </table>
            <#if subjectList?? && subjectList?size != 0>
                <div style="width: 100%; text-align: right; margin: 3px auto 3px;">
                    <#include "../inc/pager.ftl">
                </div>
            </#if>
        </@s.form>
<div id="nocontent" style="width:100%;display:block;">
        <#if subjectList??>
            <#if subjectList?size == 0>
                <span width="50%" style="color: #FF0000; font-weight: bold; text-align: center; padding: 10px;">
                    &nbsp;对不起，没有符合条件的学科信息！&nbsp;
                </span>
            </#if>
        <#else>
                <span width="50%" style="color: #FF0000; font-weight: bold; text-align: center; padding: 10px;">
                       <#if init??>
                                                    请输入查询条件进行查询。
                       <#else>
                        &nbsp;对不起，没有符合条件的学科信息！&nbsp;
                       </#if>
                </span>
        </#if>
</div>        
    <div id="content" style="text-align: center; display: none;">
            <br />
            <img src="${SiteUrl}images/load.gif" />
            <br />
            <br />
            <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;正在统计各个学科，每个学科大约需要4-5秒,请稍等 ......</div>
        </div>
        
    </body>
</html>
