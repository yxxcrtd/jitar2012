function select_all() {
  var ids = document.getElementsByName('resourceId');
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
function has_selected() {
  var ids = document.getElementsByName('resourceId');
  if (ids == null || ids.length == 0) return false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked) return true;
  }
  return false;
}
function delete_r() {
  if (has_selected() == false) {
    alert('没有选择要操作的资源.');
    return;
  }
  if (confirm('您是否确定要删除所选的资源??') == false) return;
  submit_command('delete');
}
function audit_r() {
  submit_command('audit');
}
function unaudit_r() {
  submit_command('unaudit');
}
function rcmd_r() {
  submit_command('rcmd');
}
function unrcmd_r() {
  submit_command('unrcmd');
}

function do_cmd(s)
{
  submit_command(s);
}

function move_cate_r() {
  submit_command('move_cate');
}
function confirm_crash() {
  return confirm('您是否确定要彻底删除资源??\n\n这些资源记录包括其物理文件都将被彻底删除, 无法再恢复了.');
}
function crash_r() {
  if (has_selected() == false) {
    alert('没有选择要操作的资源.'); return;
  }
  if (confirm_crash() == false) return;
  submit_command('crash');
}
function recover_r() {
	submit_command('recover');
}
function submit_command(cmd) {
  if (has_selected() == false) {
    alert('没有选择要操作的资源.');
    return;
  }
  var the_form = document.forms['listForm'];
  if (the_form == null) {
    alert('Can\'t find listForm form.');
    return;
  }
  the_form.cmd.value = cmd;
  the_form.submit();
}
