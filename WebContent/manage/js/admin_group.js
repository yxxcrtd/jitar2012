function hasChecked(vform) {
  for (var i = 0; i < vform.elements.length; i++) {
    var e = vform.elements[i];
    if (e.checked) {
      return true;
    }
  }
  return false;
}

function select_all() {
  var ids = document.getElementsByName('groupId');
  if (ids == null || ids.length == 0) return;
  var set_checked = false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked == false) {
      ids[i].checked = true;
      set_checked = true;
    }
  }
  
  if (set_checked == false) {
    for (var i = 0; i < ids.length; ++i) {
      ids[i].checked = false;
    }
  }
}
function confirm_delete() {
  return confirm('您是否确定要删除指定的协作组??');
}
function audit_s() {
  submit_command('audit');
}
function open_s() {
	submit_command("open");
}
function close_s() {
	submit_command("close");
}
function delete_s() {
	if (hasChecked(document.forms['theForm']) == false) {
		alert('没有选择要操作的协作组.');
		return;
	}
  if (confirm_delete() == false) return;
  submit_command('delete');
}
function crash_s() {
  if (hasChecked(document.forms['theForm']) == false) {
    alert('没有选择要操作的协作组.');
    return;
  }
  if (confirm('您是否确定要 "彻底删除" 所选的协作组, 协作组彻底删除将导致其所有的' +
      '\n主题、文章、资源、成员、访问记录等相关信息都被删除??') == false) return;
  if (confirm('请再次确定您是否确定要 "彻底删除" 所选的协作组??' +
      '\n\n警告: 彻底删除协作组之后, 其信息将无法恢复.') == false) return;
	submit_command('crash');
}
function execmd_s(cmd) {
	if (cmd == null || cmd == '') return;
  submit_command(cmd)
}
function submit_command(cmd) {
  var the_form = document.forms['theForm'];
  if (the_form == null) {
    alert('Can\'t find theForm form.');
    return;
  }
  if (hasChecked(the_form) == false) {
  	alert('没有选择要操作的协作组.');
  	return false;
  }
  the_form.cmd.value = cmd;
  the_form.submit();
}
