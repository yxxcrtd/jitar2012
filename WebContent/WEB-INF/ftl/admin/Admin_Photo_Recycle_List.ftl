<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>系统后台相册管理</title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css" />
		<script type="text/javascript">
		<!--
		var isChecked = true;
		function selectAll(oForm) {
			for (var i = 0; i < oForm.elements.length; i++) {
				if (oForm.elements[i].type == "checkbox" && !oForm.elements[i].disabled) {
				}
				oForm.elements[i].checked = isChecked;
			}
			if (oForm.elements["selAll"]) {
				if (isChecked) {
					oForm.elements["selAll"].value = "全部不选";
				} else {
					oForm.elements["selAll"].value = "全部选择";
				}
			}
			isChecked = !isChecked;
		}
    	function delSel(photo_form) {
    		if (hasChecked(photo_form) == false) {
    			alert("您还没有选择任何信息！");
    			return false;
    		} else {
	  			if (confirm("确定要删除选择的信息吗？") == false) {
	  				return false;
	  			}
  			}
  			photo_form.cmd.value = "crash";
  			photo_form.submit();
   	 	}
    	function recoverSel(photo_form) {
    		if (hasChecked(photo_form) == false) {
    			alert("您还没有选择任何信息！");
    			return false;
    		} else {
	  			if (confirm("确定要恢复选择的信息吗？") == false) {
	  				return false;
	  			}
  			}
  			photo_form.cmd.value = "recover";
  			photo_form.submit();
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
		<#include "admin_header.ftl" >
		<h2>
			相册回收站
		</h2>
		
		<div style="text-align: right; width: 98%; margin-top: 5px; height: 26px;">
			<form name="searchForm" action="admin_photo.py" method="get">
				<input type="hidden" name="cmd" value="recycle_list" />
				关键字：
				<input type="text" size="16" name="k" value="${k!?html}" onMouseOver="this.select();" />
		  		<input type="submit" class="button" value="检  索" />
			</form>
		</div>

		<form name="photo_form" action="admin_photo.py" method="post">
			<input type="hidden" name="cmd" value="recycle_list" />
			<table class="listTable" cellspacing="1">
				<thead>
                    <tr>
                        <th width="6%">
                            选&nbsp;&nbsp;择
                        </th>
                        <th width="7%">
                            照&nbsp;&nbsp;片
                        </th>
                        <th width="20%">
                                照片标题
                        </th>
                        <th width="9%">
                                登录名/&nbsp;呢称
                        </th>
                        <th width="15%">
                                上传日期/ <strong>IP</strong>
                        </th>
                        <th width="6%">
                                系统分类
                        </th>
                        <th width="6%">
                            审核状态
                        </th>
                        <th width="7%">
                            操&nbsp;&nbsp;作
                        </th>
                    </tr>
				</thead>
				
				<tbody>
					<#if photoList?size == 0>
					<tr>
						<td colSpan="10" style="color: #FF0000; font-weight: bold; text-align: center; padding: 10px;">
							没有符合条件的照片信息！
						</td>
					</tr>
					</#if>
					
					<#list photoList as photo>
					<tr>
                        <td style="text-align: center;">
                            <input type="checkbox" name="photoId" value="${photo.photoId}" />
                        </td>
                        <td style="text-align: center;">
                            <a href="${Util.url(photo.href!'images/default.gif')}" target="_blank"><img src="${Util.thumbNails(photo.href!'images/default.gif')}" width="64" border="0" title="${photo.title}" /></a>
                        </td>
                        <td style="padding-left: 10px;">
                            ${photo.title}
                        </td>
                        <td style="padding-left: 10px;">
                            <a href="${SiteUrl}go.action?loginName=${photo.loginName!}" title="访问 ${photo.loginName!} 的个人空间" target="_blank">${photo.loginName}</a><br>
                            <div style="line-height: 6px;">
                                <br />
                            </div>
                            ${photo.nickName}
                        </td>
                        <td style="padding-left: 10px;">
                            [${photo.createDate}]<br>
                            <div style="line-height: 5px;">
                                <br />
                            </div>
                            [${photo.addIp}]
                        </td>
                        <td style="text-align: center;">
                            ${photo.sysPhotoName!}
                        </td>
                        <td style="text-align: center;">
                            <#if photo.auditState == 0>
                                通过
                            <#else>
                                <font style="color: #FF0000;">
                                    未通过
                                </font>
                            </#if>
                        </td>
						<td style="text-align: center;">
        					<a href="?cmd=recover&amp;photoId=${photo.photoId}" onClick="return confirm('确定要恢复该照片吗？');">恢复</a>
        					<a href="?cmd=crash&amp;photoId=${photo.photoId}" onClick="return confirm('确定要彻底删除该照片吗？');">彻底删除</a>
						</td>
					</tr>
					</#list>
				</tbody>
			</table>
						
			<#if photoList?size != 0>
				<div style="width: 98%; text-align: right; margin: 3px auto 3px;">
					<#include "../inc/pager.ftl">
				</div>
			</#if>
            
            <#if photoList?size != 0>
                <div class="funcButton">
                    <input class="button" type="button" id="selAll" value="全部选择" onClick="selectAll(photo_form);" />
                    <input class="button" type="button" value="彻底删除" onClick="delSel(photo_form);" />
                    <input class="button" type="button" value="恢复选择" onClick="recoverSel(photo_form);" />
                </div>
            </#if>
		</form>
	</body>
</html>
