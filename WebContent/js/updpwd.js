// CopyRight 2010, Yang XinXin

$(function() {
	$(document).bind("contextmenu", function(e) {
		return false;
	});
	$("#oldpassword").focus();
});

// 修改密码
function check() {
	var usernameNode = $("#username");
	var usernameValue = usernameNode.val();
	var oldpasswordNode = $("#oldpassword");
	var oldpasswordValue = oldpasswordNode.val();
	var newpasswordNode = $("#newpassword");
	var newpasswordValue = newpasswordNode.val();
	var renewpasswordNode = $("#renewpassword");
	var renewpasswordValue = renewpasswordNode.val();
	var oldpasswordTipNode = $("#oldpasswordTip");
	var oldpasswordTipValue = oldpasswordTipNode.val();
	var newpasswordTipNode = $("#newpasswordTip");
	var newpasswordTipValue = newpasswordTipNode.val();
	var renewpasswordTipNode = $("#renewpasswordTip");
	var renewpasswordTipValue = renewpasswordTipNode.val();
	
	if (oldpasswordValue == "") {
		oldpasswordNode.focus();
		oldpasswordTipNode.show().html("当前密码不能为空！");
		return;
	} else {
		oldpasswordTipNode.show().html("");
	}
	
	if (newpasswordValue == "") {
		newpasswordNode.focus();
		newpasswordTipNode.show().html("新密码不能为空！");
		return;
	} else {
		newpasswordTipNode.show().html("");
	}
	
	if (renewpasswordValue == "") {
		renewpasswordNode.focus();
		renewpasswordTipNode.show().html("确认新密码不能为空！");
		return;
	} else {
		renewpasswordTipNode.show().html("");
	}
	
	if (newpasswordValue.length < 5 || newpasswordValue.length > 25) {
		newpasswordNode.focus();
		newpasswordTipNode.show().html("新密码的长度范围必须在5-25之间！");
		return;
	}
	
	if (newpasswordValue != renewpasswordValue) {
		newpasswordNode.focus();
		newpasswordTipNode.show().html("两个密码不一致！");
		return;
	} else {
		$.post("user.action", {
			cmd : "xmlrpcUpdpwd",
			loginName : usernameValue,
			password : oldpasswordValue,
			newPassword : newpasswordValue
		}, function(response) {
			if (response != "") {
				alert("密码修改成功！请牢记您的新密码！");
				oldpasswordNode.val("");
				newpasswordNode.val("");
				renewpasswordNode.val("");
			} else {
				oldpasswordNode.focus();
				oldpasswordTipNode.show().html("当前密码不正确！");
			}
		});
	}
}
