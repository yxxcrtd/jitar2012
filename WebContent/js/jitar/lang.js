/**
 * 定义使用的语言
 */
if (!App) var App = {};
App.Locale = {
	'载入中...': '',
	'编辑':''
};

function $L(str) {
	var s = App.Locale[str];
	return s ? s : str;
}


function submit_comment() {
	window.document.getElementById("btn_submit").disabled = "disabled"
	window.document.getElementById("btn_submit").value = "正在提交数据...";
	document.forms.comment_form.submit();
}

function replyComment(id)
{
	document.getElementById("comment_form").id.value=id;
}
