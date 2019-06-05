function select_all() {
  var ids = document.getElementsByName('commentId');
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
  var ids = document.getElementsByName('commentId');
  if (ids == null || ids.length == 0) return false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked) return true;
  }
  return false;
}
function delete_r() {
  if (has_selected() == false) {
    alert('没有选择要操作的评论.');
    return;
  }
  if (confirm('您是否确定要删除所选的评论??') == false) return;
  submit_command('delete');
}
function audit_r() {
  submit_command('audit');
}
function unaudit_r() {
  submit_command('unaudit');
}
function crash_r() {
  if (has_selected() == false) {
    alert('没有选择要操作的评论.'); return;
  }
  if (confirm_crash() == false) return;
  submit_command('crash');
}
function recover_r() {
  submit_command('recover');
}
function submit_command(cmd) {
  if (has_selected() == false) {
    alert('没有选择要操作的评论.');
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
