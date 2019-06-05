<html>
	<head>
		<title><@s.text name="groups.friend.title" /></title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css">
        <link rel="stylesheet" type="text/css" href="../css/msgbox.css" />
        <script type="text/javascript" src="../js/msgbox.js"></script>
        <script type="text/javascript" src="../js/jitar/core.js"></script>
		<script type="text/javascript">
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
   	 	function addSel(list_form) {
    		if (hasChecked(list_form) == false) {
    			alert("<@s.text name="groups.friend.moveToBlack" />");
    			return false;
    		} else {
	  			if (confirm("确定要将选择的好友添加进黑名单吗？") == false) {
	  				return false;
	  			}
  			}
  			list_form.cmd.value = "move_to_black";
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
        function close_dialog(event) {
            if (event.keyCode == 27) {
                MessageBox.Close();
            }
        }
        function show_send(list_form) {
            if (hasChecked(list_form) == false) {
                window.alert("没有选择要发送的好友！");
                return false;
            }
            MessageBox.Show('MessageTip');
        }
        function send_message(vform) {
            if (vform.messageTitle.value == "") {
                window.alert(" 请输入短消息标题！");
                vform.messageTitle.focus();
                return false;
            }
            if (vform.messageContent.value == "") {
                window.alert(" 请输入短消息内容！");
                vform.messageContent.focus();
                return false;
            }
            fs = document.getElementsByName('myFriendId');
            var frId = "";
            for (i = 0; i < fs.length; i++) {
                if (fs[i].checked) {
                    frId += fs[i].value + ","
                }
            }
            postData = "friendId=" + frId + "&cmd=send_message&messageTitle=" + vform.messageTitle.value + "&messageContent=" + vform.messageContent.value;
            
            url = "friend.action";
            new Ajax.Request(url, { 
            method: 'post',
            parameters: postData,
            onSuccess: function(xhr) {
            // alert(xhr.responseText)
            if(xhr.responseText.replace(/(^\s*)|(\s*$)/g, "") == "OK") {
                window.alert("    发送成功！");
            } else {
                window.alert("    发送失败！");
            }
            }, onFailure:function(xhr){alert('出现意外(如：服务器关闭，数据库连接异常，断电等)，发送失败！' + xhr.responseText);             
            }
            });
            MessageBox.Close();
        }
		//-->
		</script>
	</head>
	
	<body onKeyDown="close_dialog(event);">
	 	<h2>我的好友</h2>
	 	
        <div class='funcButton'>
          您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a> &gt;&gt; <a href='friend.action?cmd=list'>好友管理</a>
        </div>
	 	
        <form name="list_form" action="friend.action" method="post">
            <input type="hidden" name="cmd" value="" />
            <table class="listTable" cellspacing="1" cellpadding="0">
            	<thead>
            		<tr>
            			<th width="5%">选择</th>
            			<th width="5%">头像</th>
            			<th width="12%">好友</th>
            			<th width="7%">工作室</th>
            			<th width="8%">添加时间</th>
            			<th width="5%">操作</th>
            		</tr>
            	</thead>
	
            	<tbody>
            		<#list friend_list as friend>
            			<tr>
            				<td align="center">
            					<input type="hidden" name="myFriendId" value="${friend.friendId}" />
            					<input type="checkbox" id="FId" name="friendId" value="${friend.id}" />${friend.friendId}
            				</td>
            				<td align="center">
            					<a href="${SiteUrl}go.action?loginName=${friend.loginName!}" target="_blank"><img src="${SSOServerUrl +'upload/'+ friend.userIcon!'images/default.gif'}" width="48" height="48" border="0" onerror="this.src='${ContextPath}images/default.gif'" alt="${friend.nickName!}" /></a>
            				</td>
            				<td align="center">
            					<a href="${SiteUrl}go.action?loginName=${friend.loginName!}" target="_blank">${friend.loginName!}(${friend.nickName!})</a>
            				</td>
            				<td align="center">
            					<a href="${SiteUrl}go.action?loginName=${friend.loginName!}" target="_blank">${friend.blogName!}</a>
            				</td>
            				<td align="center">
            					${friend.addTime?string("yyyy-MM-dd HH:mm:ss")}
            				</td>
            				<td align="center">
            					<a href="message.action?cmd=write&friendId=${friend.friendId}">发短消息</a>
            					<a href="?cmd=del&friendId=${friend.id}" onClick="return confirm('确定要删除该好友吗？');">删除</a>
            				</td>
            			</tr>
            		</#list>			
            	</tbody>
            </table>
	
            <div class="pager">
              <#include "../inc/pager.ftl">
            </div>		
					
            <div class='funcButton'>
            	<input class="button" id="AddStudent" name="AddStudent" onClick="javascript:document.add_friend_form.submit();" type="button" value="添加好友">&nbsp;&nbsp;
            	
            	<#if friend_list?size != 0>
            	   <input class="button" id="selAll" name="sel_All" onClick="on_checkAll(list_form, 1)" type="button" value="全部选择">&nbsp;&nbsp;
            	   <input class="button" id="DelAll" name="Del_All" onClick="delSel(list_form)" type="button" value="删除选择">&nbsp;&nbsp;
            	</#if>
            	
            	<input class="button" id="blackList" name="black_List" onClick="javascript:window.location='friend.action?cmd=list_black'" type="button" value="黑名单">&nbsp;&nbsp;
            	
            	<#if friend_list?size != 0>
            	   <input class="button" id="AddAll" name="Add_All" onClick="addSel(list_form)" type="button" value="添加黑名单">&nbsp;&nbsp;
                   <input class="button" type="button" value="发短消息" onClick="show_send(list_form);">
            	</#if>
            </div>
        
            <div id="blockUI" onClick="return false" onMousedown="return false" onMousemove="return false" onMouseup="return false" onDblclick="return false">&nbsp;</div>
            <div id="MessageTip" class="hidden">
                <div class="boxHead">
                    <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src="../images/dele.gif" /></div>
                    <div class="boxTitle" onmousedown="MessageBox.dragStart(event)">发送短消息：</div>
                </div>
                <div>
                    <br />
                    <table class="listTable" cellspacing="1">
                        <tbody>
                            <tr>
                                <td align="right" width="22%"><b>短消息标题：</b></td>
                                <td><input type="text" name="messageTitle" style="width: 100%;" maxLength="50" /></td>
                            </tr>
                            <tr>
                                <td align="right" valign="center"><b>短消息内容：</b></td>
                                <td><textarea style="width: 100%; height: 150px;" name="messageContent"></textarea></td>
                            </tr>
                        </tbody>                
                        <tfoot>
                            <tr>
                                <td></td>
                                <td>
                                    <div class="funcButton">                            
                                        <input class="button" type="submit" value=" 发送短消息 " onClick="send_message(list_form); return false;" />&nbsp;&nbsp;
                                        <input class="button" type="button" value=" 取  消 "  onClick="return MessageBox.Close();" />
                                  </div>
                                </td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </form>
    	
        <div style='display:none'>
        	<form name="add_friend_form" action="friend.action" method="get" >
        		<input type="hidden" name="cmd" value="add" />
        	</form>	
        </div>
    </body>
</html>
