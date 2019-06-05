<html>
   <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>系统后台相册管理</title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css" />
        <link rel="stylesheet" type="text/css" href="../css/msgbox.css" />
        <script type="text/javascript" src="../js/msgbox.js"></script>
        <script type="text/javascript" src="../js/jitar/core.js"></script>
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
    	function audit(photo_form) {
        	if (hasChecked(photo_form) == false) {
          		alert("您还没有选择任何信息！");
          		return false;
        	} else {
  			 	photo_form.cmd.value = "audit";
  			 	photo_form.submit();
  			}
		}
      	function unaudit(photo_form) {
        	if (hasChecked(photo_form) == false) {
          		alert("您还没有选择任何信息！");
          		return false;
        	} else {
         		photo_form.cmd.value = "unaudit";
         		photo_form.submit();
        	}
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
  			photo_form.cmd.value = "delete";
  			photo_form.submit();
   	 	}
    	function privateShow(photo_form) {
    		if (hasChecked(photo_form) == false) {
    			alert("您还没有选择任何信息！");
    			return false;
    		} else {
	  			if (confirm("确定将选择的照片只显示在其个人空间上吗？") == false) {
	  				return false;
	  			}
  			}
  			photo_form.cmd.value = "privateShow";
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
		function close_dialog(event) {
            if (event.keyCode == 27) {
                MessageBox.Close();
            }
        }
        function trans(vform) {
            if (vform.beginId.value == "") {
                window.alert("请输入开始ID！");
                vform.beginId.focus();
                return false;
            }
            if (vform.endId.value == "") {
                window.alert("请输入结束ID！");
                vform.endId.focus();
                return false;
            }
            
            postData = "cmd=tools&beginId=" + vform.beginId.value + "&endId=" + vform.endId.value;
            url = "photo.action";
            new Ajax.Request(url, {
            method: 'post',
            parameters: postData,
            onSuccess: function(xhr) {
            alert(xhr.responseText)
            if(xhr.responseText.replace(/(^\s*)|(\s*$)/g, "") == "OK") {
                window.alert("    操作成功！");
            } else {
                window.alert("    操作失败！");
            }
            }, onFailure:function(xhr){alert('修改数据失败。' + xhr.responseText);             
            }
            });
            MessageBox.Close();
        }
		//-->
		</script>
	</head>
	
	<body style="margin-top: 20px;" onKeyDown="close_dialog(event);">
		<#include "admin_header.ftl" >
		<h2>
			相册管理
		</h2>
		
		<div style="text-align: right; width: 100%;">
			<form name="searchForm" action="admin_photo.py" method="get">
				<input type="hidden" name="cmd" value="list" />
				关键字：
				<input type="text" size="16" name="k" value="${k!?html}" onMouseOver="this.select();" />
				<#if !(f??)><#assign f = 0></#if>
                <select name="f">
                    <option value="0" ${(f == '0')?string('selected', '')}>照片标题</option>
                    <option value="1" ${(f == '1')?string('selected', '')}>照片简介</option>
                    <option value="2" ${(f == '2')?string('selected', '')}>上传用户</option>
                </select>
                <#if !(sc??)><#assign sc = 0></#if>
                <select name="sc">
                    <option value="">系统分类</option>
                    <#list syscate_tree.all as c>
                        <option value="${c.categoryId}" ${(sc == c.categoryId)?string('selected', '')}>${c.treeFlag2} ${c.name!?html}</option>
                    </#list>
                </select>
                <select name="isPrivateShow">
                	<option value="" ${(pshow == '')?string('selected', '')}>全部照片</option>
                    <option value="0" ${(pshow == '0')?string('selected', '')}>系统空间照片</option>
                    <option value="1" ${(pshow == '1')?string('selected', '')}>个人空间照片</option>
                </select>
		  		<input type="submit" class="button" value="检  索" />
			</form>
		</div>

		<form name="photo_form" action="admin_photo.py" method="post">
			<input type="hidden" name="cmd" value="list" />
			<table class="listTable" cellSpacing="1">
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
							<a href="${Util.url(photo.href!)}" target="_blank">
								<img src="${Util.thumbNails(photo.href!'images/default.gif')}" width="64" border="0" title="${photo.title}" />
							</a>
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
                            [${photo.createDate!}]<br>
                            <div style="line-height: 5px;">
                                <br />
                            </div>
                            [${photo.addIp!}]
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
        					<a href="photo.action?cmd=edit&amp;photoId=${photo.photoId}">编辑</a>&nbsp;&nbsp;
        					<a href="?cmd=delete&amp;photoId=${photo.photoId}" onClick="return confirm('确定要将该照片放入回收站吗？');">删除</a>
						</td>
					</tr>
					</#list>
				</tbody>
			</table>
			
			<#if photoList?size != 0>
                <div style="width: 100%; text-align: right; margin: 3px auto 3px;">
                    <#include "../inc/pager.ftl">
                </div>
				<div class="funcButton">
					<input class="button" type="button" id="selAll" value="全部选择" onClick="selectAll(photo_form);" />
					<input class="button" type="button" value="审核通过" onClick="audit(photo_form);" />
					<input class="button" type="button" value="取消审核" onClick="unaudit(photo_form);" />
					<input class="button" type="button" value="删除选择" onClick="delSel(photo_form);" />
					<input class="button" type="button" value="只在个人空间显示" onClick="privateShow(photo_form);" />
					<!--<input class="button" type="button" value="生成缩略图" onClick="MessageBox.Show('MessageTip');return false;" />-->
				</div>
                <div id="blockUI" onClick="return false" onMousedown="return false" onMousemove="return false" onMouseup="return false" onDblclick="return false">&nbsp;</div>
                <div id="MessageTip" class="hidden">
                    <div class="boxHead">
                        <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src="../images/dele.gif" /></div>
                        <div class="boxTitle" onmousedown="MessageBox.dragStart(event)">请输入照片的ID段：(推荐的个数:50-100)</div>
                    </div>
                    <div style="padding: 10px; text-align: center;">
                            照片的开始ID：<input type="text" name="beginId" size="5" /> -
                            照片的结束ID：<input type="text" name="endId" size="5" />&nbsp;&nbsp;&nbsp;
                        <input type="button" class="button" value=" 转  换 " onClick="trans(photo_form);" />
                    </div>
                </div>
			</#if>
		</form>
	</body>
</html>
