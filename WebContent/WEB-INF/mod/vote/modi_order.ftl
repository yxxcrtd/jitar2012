<form method='post'>
<h3>${vote.title}</h3>
<#if vote_question_answer_list??>
<#list vote_question_answer_list as q>
<input type='hidden' name='q_list' value='${q.question.voteQuestionId}' />
<div style='font-weight:bold' id='q_c_${q.question.voteQuestionId}'>
问题顺序：<input name='question_${q.question.voteQuestionId}' value='${q.question.itemIndex}' style='width:50px' />${q.question.title}
</div>
<#if q.answer?? >
<div style='padding-left:20px'>
<#list q.answer as a>
<div>
<input type='hidden' name='answer_list' value='${a.voteQuestionAnswerId}' />
选项顺序：<input name='question_answer_${a.voteQuestionAnswerId}' value='${a.itemIndex}'  style='width:50px' />
${a.answerContent}
</div>
</#list>
</div>
</#if>
</#list>
<div style='padding:4px'>
<input type='submit' value='保存调整顺序'  />
<input type='button' value='返回管理页面' onclick='window.location.href="${SiteUrl}mod/vote/manage_list.action?guid=${parentGuid}&type=${parentType}"' />
</#if>
</div>
</form>