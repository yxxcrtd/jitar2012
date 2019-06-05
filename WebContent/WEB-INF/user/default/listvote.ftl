<#if vote?? >
<form method='post' action='${SiteUrl}mod/vote/getcontent.action?guid=${parentGuid}&type=${parentType}&voteId=${vote.voteId}'>
<h3>${vote.title}</h3>
<#if vote.description?? >
<div>${vote.description}</div>
</#if>
<#if vote_question_answer_list??>
<#list vote_question_answer_list as q>
<input type='hidden' name='q_list' value='${q.question.voteQuestionId}' />
<input type='hidden' id='q_type_${q.question.voteQuestionId}' value='${q.question.questionType}' />
<input type='hidden' id='q_max_${q.question.voteQuestionId}' value='${q.question.maxSelectCount}' />
<div style='font-weight:bold' id='q_c_${q.question.voteQuestionId}'>${q_index+1}：${q.question.title}</div>
<#if q.answer?? >
<div style='padding-left:20px'>
<#list q.answer as a>
<div>
<#if q.question.questionType == 1 >
<input type='radio' name='q_${q.question.voteQuestionId}' value='${a.voteQuestionAnswerId}' />
<#else>
<input type='checkbox' name='q_${q.question.voteQuestionId}' value='${a.voteQuestionAnswerId}' />
</#if>
${a.answerContent}
</div>
</#list>
</div>
</#if>
</#list>
</#if>
<input type='button' value=' 投  票 ' onclick='validateForm(this.form)' />
<input type='button' value='查看结果' onclick='window.location.href="${SiteUrl}mod/vote/showresult.action?guid=${parentGuid}&type=${parentType}&voteId=${vote.voteId}&unitId=${unitId!}"' />
</form>
<#else>
当前没有投票。
</#if>
<div style='padding:4px;text-align:right;'>
<a href='${SiteUrl}mod/vote/editform.action?guid=${parentGuid}&amp;type=${parentType}&unitId=${unitId!}'>发起投票</a> | 
<a href='${SiteUrl}mod/vote/manage_list.action?guid=${parentGuid}&amp;type=${parentType}&unitId=${unitId!}'>管理投票</a> | 
<a href='${UserSiteUrl}html/vote_0.html'>更多投票</a>
</div>