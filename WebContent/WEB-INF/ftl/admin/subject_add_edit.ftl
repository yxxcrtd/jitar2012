<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>学科管理</title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css" />
	</head>
	
	<body>
		<h2>
			<#if subject.subjectId == 0>添加学科
			<#else>修改学科
			</#if>
		</h2>
		<table align="center">
			<tr>
				<td colspan="2">
					<@s.actionerror cssStyle="color:#FF0000; font-weight:bold;" />
				</td>
			</tr>
		</table>
		<form name="theForm" action="admin_subject.action" method="post">
			<table class="listTable" cellspacing="1">
				<tbody>
				   
					<tr>
						<td align="right">
							<b>元学科名称：</b>
						</td>
						<td>
	                        <select name="msubjId" style="width: 133px;">
	                        	<#if subject.subjectId != 0>
		                            <option value="">所属元学科</option>
		                            <#list msubj_list as msub>
			                            	<option value="${msub.msubjId!}" 
			                            	<#if msub.msubjId == subject.metaSubject.msubjId >selected</#if>>
			                            	&nbsp;&nbsp;${msub.msubjName!}
										</#list>
									</option>
								<#else>
									<option value="">所属元学科</option>
		                            	<#list msubj_list as msub>
			                            	<option value="${msub.msubjId!}" >&nbsp;&nbsp;${msub.msubjName!}
										</#list>
									</option>
								</#if>
							</select>
							<font style="color: #FF0000;">*</font> 必须选择一个元学科
                        </td>
					</tr>
					
					<tr>
						<td align="right">
							<b>元学段名称：</b>
						</td>
						<td>
							<select name="gradeId" style="width: 133px;">
								<option value=''>所属元学段</option>
									<#if subject.subjectId != 0>
										<#if grade_list?? >
											<#list grade_list as grade>
												<#if grade.isGrade>
													<option value="${grade.gradeId}" <#if grade.gradeId == subject.metaGrade.gradeId>selected</#if>>
										            	<#if grade.isGrade>
											            &nbsp;&nbsp;${grade.gradeName!?html}
										                <#else>
										                &nbsp;${grade.gradeName!?html}
										                </#if>
										            </option>
										        </#if>
										    </#list>
										 </#if>
									<#else> 	
										<#if grade_list?? >
											<#list grade_list as grade>
												<#if grade.isGrade>
													<option value="${grade.gradeId}" >
										            	<#if grade.isGrade>
										                &nbsp;${grade.gradeName!?html}
										                <#else>
										                &nbsp;${grade.gradeName!?html}
										                </#if>
										            </option>
										        </#if>
										    </#list>
										 </#if>
									</#if>
							</select>
							<font style="color: #FF0000;">*</font> 必须选择一个元学段
						</td>
					</tr>
					
					<tr>
						<td align="right" width="30%">
							<b>学科名称：</b>
						</td>
						<td>
							<input type="text" name="subjectName" value="${subject.subjectName!?html}" onMouseOver="this.select();" /> <font style="color: #FF0000;">*</font> 学科的中文名字，必须给出，且不能和别的学科名字重复。
						</td>
					</tr>
					<tr>
						<td align="right">
							<b>学科代码：</b>
						</td>
						<td>
							<input type="text" name="subjectCode" value="${subject.subjectCode!?html}" /> <font style="color: #FF0000;">*</font> 学科代码，必须给出，输入字母，且不能和别的学科代码重复。 
						</td>
					</tr>
						<tr>
						<td align="right">
							<b>资源库学科ID：</b>
						</td>
						<td>
							<input type="text" name="reslibCId" value="${subject.reslibCId!?html}" /> 
						</td>
					</tr>
				<tr>
						<td align="right">
							<b>自定义链接地址：</b>
						</td>
						<td>
							<input type="text" name="shortcutTarget" value="${subject.shortcutTarget!?html}" /> 
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="4" align="center" height="25">
							<input type="hidden" name="cmd" value="save" />
							<input type="hidden" name="subjectId" value="${subject.subjectId}" />
							<input type="hidden" name="__referer" value="${__referer!?html}" />
							<input type="submit" class='button' value=" 确定${(subject.subjectId==0)?string('添加', '修改')} " />
							<input type="button" class='button' value="取消返回" onclick="window.history.back()" />
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
	</body>
</html>
