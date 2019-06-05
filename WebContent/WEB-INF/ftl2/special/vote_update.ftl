<#if vote?? >
<#assign q_count = 0>
<#assign max_order_number = 0>
<form method='post' id='fff'>
<table class="moreTable">
<tr>
<th style='width:80px;'>调查名称：</th><td><input name='vote_title' style='width: 500px; height: 25px; outline: none; border: 1px solid #000000;' value='${vote.title?html}' /></td>
</tr>
<tr>
<th style='vertical-align:top'>调查描述：</th><td><textarea name='vote_desc' style='width: 500px; height: 80px; outline: none; border: 1px solid #000000;'>${vote.description?html}</textarea></td>
</tr>
<tr>
<th>截止日期：</th><td style="text-align: left;"><input name='vote_enddate' id='v_enddate' value='${vote.endDate?string('yyyy-MM-dd')}' style='width:20px; height: 25px; outline: none; border: 1px solid #000000;' /></td>
</tr>
<tr>
<th colspan='2' style="text-align: left; height: 30px; padding-top: 30px;">调查选项：</th>
</tr>
</table>
<div id='q'>
<table class="moreTable">
<#if vote_question_answer_list??>
<#list vote_question_answer_list as q>
<#assign q_count = q_count + 1>
<#if q.question.itemIndex &gt; max_order_number >
  <#assign max_order_number = q.question.itemIndex >
</#if>
<tr>
<th style='width:80px;'>问题${q_count}：</th>
<td>
<input type='hidden' name='old_q' value='${q.question.voteQuestionId}' />
<input name='vote_q_${q.question.voteQuestionId}' value='${q.question.title?html}' style='width: 500px; height: 25px; outline: none; border: 1px solid #000000;' /></td>
</tr>
<tr>
<td>问题类型：</td>
<td style="text-align: left; height: 30px;">
<input type='radio' name='vote_t_${q.question.voteQuestionId}' value='1'<#if q.question.questionType == 1 > checked = 'checked'</#if> />单选 
<input type='radio' name='vote_t_${q.question.voteQuestionId}' value='0'<#if q.question.questionType == 0 > checked = 'checked'</#if> />多选</td>
</tr>
<tr>
<td>必选个数：</td><td style="text-align: left;"><input  name='vote_max_${q.question.voteQuestionId}' value='${q.question.maxSelectCount}' style='width: 20px; height: 25px; outline: none; border: 1px solid #000000;' />（多选的时候使用，0表示不限制。）</td>
</tr>
<tr>
<td>显示顺序：</td><td><input  name='vote_order_${q.question.voteQuestionId}' value='${q.question.itemIndex}' /></td>
</tr>
<#if q.answer?? >
<tr>
<td style='vertical-align:top'>选项：</td><td id='td_${q.question.voteQuestionId}'>
	
<#list q.answer as a>
	<input type='hidden' name='q_${q.question.voteQuestionId}_a' value='${a.voteQuestionAnswerId}' />
	<div><textarea name='q_a_${a.voteQuestionAnswerId}' style='width:500px;height:50px;border: 1px solid #000000;'>${a.answerContent?html}</textarea></div>
</#list>
	<div><textarea name='new_answer_${q.question.voteQuestionId}' style='width:500px;height:40px;border: 1px solid #000000;' onclick='addQuestionItem(event,${q.question.voteQuestionId})' onkeydown='addQuestionItem(event,${q.question.voteQuestionId})'></textarea></div>
</td>
</tr>
</#if>
</#list>
</#if>
</table>
</div>
<div style=''>
<input type='hidden' id='qnum' name='question_num' value='0' />
<input class="specialSubmit" type='button' value='增加调查问题' onclick='addQuestion()' /> 
<input class="specialSubmit" type='submit' value=' 保存修改 ' />
<span style='color:red'>【说明：】问题名称和选项如果为空，则将删除本条目。保存前请仔细检查各项输入。</span>
</div>
</form>
<script type='text/javascript'>
function addQuestionItem(evt,questionId)
{
 var ele = Platform.isIE ? window.event.srcElement:evt.target;
 if(!ele) return;
 var f = document.getElementById('fff');

 var answer_old = document.getElementsByName('new_answer_' + questionId )

 var hasBlank = false;
 for(var i = 0;i<answer_old.length;i++)
 {
 	if(answer_old[i].value == '')
 	{
 		hasBlank = true;
 		return;
 	}
 }
 
	if(!hasBlank)
	{
		var d;
	    var t;
		d = document.createElement('DIV');
		if(Platform.isIE)
		{
			t = document.createElement('<TEXTAREA name="new_answer_' + questionId + '"></TEXTAREA>');
		}
		else
		{
		 	t = document.createElement('textarea');
			//t.className = '';
			t.setAttribute('name',elename);	
			t.name = elename;
		}
		t.style.width = '500px';
		t.style.height = '40px';
		t.style.border = '1px solid #000000';

		t.onclick = t.onkeydown = function(){addQuestionItem(evt,questionId)};
		d.appendChild(t)
		document.getElementById('td_' + questionId).appendChild(d) 		
	}
}

var max_order_number = ${max_order_number};
var oldQuestionCount = ${q_count};
function addQuestion()
{
  oldQuestionCount++;
  var q_count = parseInt(document.getElementById('qnum').value, 10);
  q_count += 1;
  var strHtml = "<table style='width:100%'>";
  	  strHtml += "<tr><th style='width:66px;'>问题" + oldQuestionCount + "：</th><td><input name='new_vote_q_" + q_count + "' style='width:500px' /></td></tr>";
  	  strHtml += "<tr><td>问题类型：</td><td><input type='radio' name='new_vote_t_" + q_count + "' value='1' checked='checked' />单选 <input type='radio' name='new_vote_t_" + q_count + "' value='0' />多选</td></tr>";
  	  strHtml += "<tr><td>必选个数：</td><td><input  name='new_vote_max_" + q_count + "' value='0' />（多选的时候使用，0表示不限制。）</td></tr>";
  	  strHtml += "<tr><td>显示顺序：</td><td><input  name='new_vote_order_" + q_count + "' value='" + (max_order_number + q_count) + "' /></td>";
  	  strHtml += "<tr><td style='vertical-align:top'>选项：</td><td id='new_td_" + q_count + "'>";
  	  strHtml += "<div><textarea name='new_vote_a_" + q_count + "' style='width:500px;height:40px'></textarea></div>";
   	  strHtml += "</td></tr></table>";
  	  var d = document.createElement('div');
  	  d.innerHTML = strHtml;
  document.getElementById("q").appendChild(d)
  document.getElementsByName('new_vote_a_' + q_count)[0].onclick = document.getElementsByName('new_vote_a_' + q_count)[0].onkeydown = addNewQuestionItem;
  document.getElementById('qnum').value = q_count;
}

function addNewQuestionItem(evt)
{
 var ele = Platform.isIE ? window.event.srcElement:evt.target;
 if(!ele) return;
 var elename=ele.name;
 var eleindex = elename.split('_')[3]
 var eles = document.getElementsByName(elename)
 var hasBlank = false;
 for(var i = 0;i<eles.length;i++)
 {
 	if(eles[i].value == '')
 	{
 		hasBlank = true;
 		return;
 	} 	
 }
if(!hasBlank)
{
	var d;
    var t;
	d = document.createElement('DIV');
	if(Platform.isIE)
	{
		t = document.createElement('<TEXTAREA name="' + elename + '"></TEXTAREA>');
		//t.className = '';
		//t.setAttribute('name',elename);	
		//t.name = elename;
	}
	else
	{
	 	t = document.createElement('textarea');
		//t.className = '';
		t.setAttribute('name',elename);	
		t.name = elename;
	}
	t.style.width = '500px';
	t.style.height = '40px';
	d.appendChild(t)
	document.getElementById('new_td_' + eleindex).appendChild(d) 		
}
}

window.setTimeout('calendar.set("v_enddate")',2000);
</script>

<#else>
  没有可以修改的投票。
</#if>