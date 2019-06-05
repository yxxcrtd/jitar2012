<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
        <title><@s.text name="groups.friend.title" /></title>
        <link rel="stylesheet" type="text/css" href="../css/manage.css" />
        <script type="text/javaScript">
        <!--
        var blnIsChecked = true;
        function on_checkAll(oForm) {
            for (var i = 0; i < oForm.elements.length; i++) {
                if (oForm.elements[i].type == "checkbox" && !oForm.elements[i].disabled) {
                }
                oForm.elements[i].checked = blnIsChecked;
            }
            if (oForm.elements["selAll"]) {
                if (blnIsChecked) {
                    oForm.elements["selAll"].value = "<@s.text name="groups.public.unSelectAll" />";
                } else {
                    oForm.elements["selAll"].value = "<@s.text name="groups.public.selectAll" />";
                }
            }
            blnIsChecked = !blnIsChecked;
        }
        function delSel(list_form) {
            if (hasChecked(list_form) == false) {
                alert("<@s.text name="groups.public.noSelect" />");
                return false;
            } else {
                if (confirm("<@s.text name="groups.public.delSelConfirm" />") == false) {
                    return false;
                }
            }
            list_form.cmd.value = "del";
            list_form.submit();
        }
        function hasChecked(vform) {
            for (var i = 0; i < vform.elements.length; i++) {
                var e = vform.elements[i];
                if (e.checked) {
                    return true;
                }
            }
            return false;
        }
        //-->
        </script>
    </head>
    
    <body style="margin-top: 20px;">
        <h2>相册分类管理</h2>
        
        <table align="center">
            <tr>
                <td colspan="2">
                    <@s.actionerror cssStyle="color:#FF0000; font-weight:bold;" />
                </td>
            </tr>
        </table>
        
        <form name="list_form" action="photostaple.action" method="post">
            <table class="listTable" cellSpacing="1">
                <thead>
                    <tr>
                        <th width="3%">
                            标识
                        </th>
                        <th width="12%">
                            分类名称(子分类数)
                        </th>
                        <th width="5%">
                            是否隐藏
                        </th>
                        <th width="5%">
                            操 作
                        </th>
                    </tr>
                </thead>
                <tbody>         
                    <#list category_tree.all as category>
                        <tr>
                            <td align="center">
                                ${category.id}
                            </td>
                            <td>
                                ${category.treeFlag2!} ${category.name} 
                                <#if (category.childNum >   0)>(${category.childNum}) </#if>
                            </td>
                            <td align="center">
                                ${category.extendedObject.getIsHide()?string("是", "否")}
                            </td>
                            <td align="center">
                                <a href="?cmd=upd&photoStapleId=${category.id}">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                <a href="?cmd=del&photoStapleId=${category.id}" onClick="return confirm('删除该分类即将该分类下的所有照片转移到系统默认分类下！\n\n确定要删除该分类吗？');">删除</a>
                            </td>
                        </tr>
                    </#list>
                        <tr>
                            <td align="center">
                                
                            </td>
                            <td>
                                <font style="color: GRAY;">默认分类</font>
                            </td>
                            <td align="center">
                                -
                            </td>
                            <td align="center">
                                -
                            </td>
                        </tr>
                </tbody>
            </table>
                
            <tr>
                <td colspan="8">
                    <input type="hidden" name="cmd" value="" />                                         
                    <div class="pager" style="text-align: right;">
                        <#include "../inc/pager.ftl">
                    </div>
                    
                    <div style="width: 98%; margin-top: 3px; height: 26px;">
                        <input class="button" id="AddPhotoStaple" name="AddPhotoStaple" onClick="javascript:window.location='photostaple.action?cmd=add'" type="button" value="添加分类">&nbsp;&nbsp;
                    </div>
                </td>
            </tr>
        </form>     
    </body>
</html>
