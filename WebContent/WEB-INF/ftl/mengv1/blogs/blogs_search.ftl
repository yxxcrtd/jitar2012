<div class='b3'>    
  <div class='b1_head_c'>
    <div class='b1_head_right'></div>
    <div class='b1_head_left'> 搜索</div>
  </div>
  <div class='b1_content' style='background:url(${SiteThemeUrl}sbg.jpg) no-repeat 1px '>
    <div>
      <div class='tc1'>
          <form method="get" action='blogList.action' style="margin:0;padding:0"  name="profile_form">
          <input type='hidden' name='type' value='search' />
          <table border='0' cellspacing='2' cellpadding='0'>
          <tr>
          	<td>关 键 字：</td><td><input name="k" style="width:172px" /></td>
          </tr>
         
         <tr>
          <td> 选择学段：</td><td>         
          <select name='gradeId' onchange='grade_changed(this)'>
          <option value="">全部学段</option>
          
        <#if grade_list?? >
        <#--
        <#list grade_list as grade>
        <#if grade.isGrade>
            <option value="${grade.gradeId}" <#if grade.gradeId==(gradeId!0)>selected</#if>>
                <#if grade.isGrade>
                  ${grade.gradeName!?html}
                <#else>
                  &nbsp;&nbsp;${grade.gradeName!?html}
                </#if>
              </option>
        </#if>
        </#list>
         -->
        <#list grade_list as grade >
            <option value="${grade.gradeId}" ${(grade.gradeId == (gradeId!0))?string('selected', '') } >${grade.isGrade?string(grade.gradeName!?html, '&nbsp;&nbsp;' + grade.gradeName!?html) }</option>
        </#list>
         
        </#if>
          </select>
          </td>
         </tr>                       
          <tr>
          <td>选择学科：</td><td><select name="subjectId">
            <option value=''>所有学科</option>
        <#if subject_list?? >
          <#list subject_list as subj >
            <#if subjectId?? >
            <#if subj.msubjId == subjectId >
            <option selected value='${subj.msubjId}'>${subj.msubjName}</option>
            <#else >
            <option value='${subj.msubjId}'>${subj.msubjName}</option>
            </#if>
            <#else >
            <option value='${subj.msubjId}'>${subj.msubjName}</option>
            </#if>
          </#list>
        </#if>
        </select>
            <span id='subject_loading' style='display:none'>
             <br/>
              <img src='images/loading.gif' align='absmiddle' 
                hsapce='3' />正在加载学科信息...
            </span>        
          </td>
         </tr>

          
          <tr>
          <td> </td><td style='padding-top:4px'>
            <input type="image" src='${SiteThemeUrl}b_s.gif' />
           </td>
           </tr>
           </table>
            </form>
                
      </div>
    </div>
  </div>
</div>
<script>
function grade_changed(sel)
{
  // 得到所选学科.
  var gradeId = sel.options[sel.selectedIndex].value;
  var subject_sel = document.profile_form.subjectId;
  subject_sel.disabled = true;
  var img = document.getElementById('subject_loading');
  img.style.display = '';
  
  // 用 AJAX 请求该区县下的机构, 并填充到 unitId select 中.
  url = 'manage/admin_subject.py?cmd=subject_options&gradeId=' + gradeId + '&tmp=' + Math.random();
  new Ajax.Request(url, {
    method: 'get',
    onSuccess: function(xport) { 
        var options = eval(xport.responseText);
        clear_options(subject_sel);
        add_option(subject_sel, '', '所有学科');
        for (var i = 0; i < options.length; ++i)
          add_option(subject_sel, options[i][0], options[i][1]);
        img.style.display = 'none';
        subject_sel.disabled = false;
      }
  });
}

function clear_options(sel) {
  sel.options.length = 0;
}
function add_option(sel, val, text) {
  opt = document.createElement("OPTION");
  opt.value = val;
  opt.text = text;
  sel.options.add(opt);
}
</script>
