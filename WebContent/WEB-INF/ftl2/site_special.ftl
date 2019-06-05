<!doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 专题</title>
		<#include "/WEB-INF/ftl2/common/favicon.ftl" />
		<link rel="stylesheet" href="${SiteThemeUrl}css/index.css" type="text/css">
		<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
		<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
		<link rel="stylesheet" href="${SiteThemeUrl}css/fancybox/jquery.fancybox.css" type="text/css">
		<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
		<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
		<script src="${ContextPath}js/new/index.js" type="text/javascript" charset="utf-8"></script>
		<script src="${ContextPath}js/new/show_photo.js" type="text/javascript" charset="utf-8"></script>
		<script src="${ContextPath}js/new/jquery.fancybox.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
		$(document).ready(function() {
			$("#current").fancybox({
				"titlePosition"		: "inside",
				"transitionIn"		: "none",
				"transitionOut"		: "none"
			});
			$("#specialSubmit").click(function(e) {
				$.ajax({
					type: "POST",
					url: "${SiteUrl}jython/saveNewSpecialSubject.py",
					data: "stitle=" + $("#stitle").val() + "&scontent=" + $("#scontent").val(),
					success: function(msg) {
						if (msg = 1) {
							alert("提交成功！");
							$("#stitle").val("");
							$("#scontent").val("");
							location.reload();
						}
					}
				});
			});
			
			$("form").submit(function(e) {
				alert("Submitted");
			});
		});
		</script>
	</head>
	<body>
		<#include "site_head.ftl">
		<div class="secMain mt25 clearfix">
			<#if (specialSubject?? && specialSubject.logo??)>
				<div class="speciaWrap border">
					<a href="#" class="specialBanner">
						<img src="${specialSubject.logo!}" />
					</a>
					<div class="imgShadow">
						<img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize9" />
					</div>
				</div>
			</#if>
			<#if loginUser??>
			    <ul class="specialNav clearfix mt3">
					<li><a href="${ContextPath}manage/?url=photo.action?cmd=upload&specialSubjectId=${specialSubjectId!}" target="_blank"><span class="sN1"></span>上传图片</a></li>
					<li><a href="${ContextPath}manage/?url=article.action?cmd=input&specialSubjectId=${specialSubjectId!}" target="_blank"><span class="sN2"></span>发布文章</a></li>
	        		<li><a href="${ContextPath}new_topic.action?guid=${parentGuid!}&amp;type=specialsubject&amp;returl=${returl!?url}&unitId=${unitId!}&specialSubjectId=${specialSubjectId!}"><span class="sN3"></span>发起讨论</a></li>
			        <li><a href="${ContextPath}topic_manage_list.action?guid=${parentGuid!}&type=specialsubject&unitId=${unitId!}"><span class="sN4"></span>管理讨论</a></li>
			        <li><a href="${ContextPath}manage/?url=specialsubject/admin_specialsubject_add.py" target="_blank"><span class="sN5"></span>创建专题</a></li>
			        <li><a href="editform.action?guid=${parentGuid!}&type=specialsubject"><span class="sN6"></span>发起投票</a></li>
			        <li><a href="manage_list.action?guid=${parentGuid!}&type=specialsubject"><span class="sN7"></span>管理投票</a></li>
			        <li class="last"><a href="<#if superAdmin?? && superAdmin == "1">${ContextPath}jython/showNewSpecialSubject.py<#else>#</#if>"><span class="sN8"></span>候选专题</a></li>
				</ul>
			</#if>
    
		    <div class="specialScWrap border mt10">
		        <div class="specialNews clearfix">
		        	<div class="specialScroll">
						<div class="specialContainer" id="pic">
			        		<#if (specialSubjectPhotoList?? && 0 &lt; specialSubjectPhotoList?size)>
			                    <ul class="indImg">
			                      	<#list specialSubjectPhotoList as ssp>
				                      	<#assign link="">
            										<#assign icon="">
            										<#assign txt="">
				                        <li>
        											<#assign link= ContextPath + "photos.action?cmd=detail&photoId=" + ssp.photoId>
        					        		<#assign icon=icon + (ssp.href!"")>
        											<#assign txt=txt + (ssp.title!?html)>
        											<input id="picture${ssp_index}" type="hidden" value="${icon}"/>        											
        											<a id="imgAdd${ssp_index}" href="${link}">
        											<img src="${Util.GetimgUrl(Util.thumbNails(ssp.href!),'565x280')}" />        											
        											</a>
				                            <div>
				                                <a href="${link}">${Util.getCountedWords(txt!?html,10)}</a>
				                                <p></p>
				                            </div>
				                        </li>
									</#list>
			                    </ul>
		                	</#if>
						</div>
						<div class="teachScrollBtn" id="tip">
							<ul class="pagination">
								<#if (specialSubjectPhotoList?? && 0 &lt; specialSubjectPhotoList?size)>
			                    	<#list specialSubjectPhotoList as ssp>
				                        <li onclick="change(${ssp_index + 1})" id="smallimg_${ssp_index + 1}" class="<#if (1 == (ssp_index + 1))>active<#else></#if>">${ssp_index + 1}</li>
			                    	</#list>
			                    </#if>
							</ul>
						</div>
		                <#if loginUser??>
		                	<a href="${ContextPath}manage/?url=photo.action?cmd=upload&specialSubjectId=${specialSubjectId!}" class="specialAdd">添加图片</a>
		                </#if>
		            </div>
		            <div class="specialHot">
		                <h4>
		                	<a href="specialSubject.action?specialSubjectId=${specialSubject.specialSubjectId}" title="${specialSubject.title!}">
		                		${Util.drawoffHTML(specialSubject.title, 24)}
		                	</a>
		                </h4>
		                <p>
		                	<#if specialSubject.description??>
		                		${Util.drawoffHTML(specialSubject.description, 210)}
		                	</#if>
		                </p>
		                <p><a id="current" href="#current1" class="more1" target="_blank">更多详情</a></p>
						<div style="display: none;">
							<div id="current1" style="width: 700px; height: 400px; overflow: auto;">
								${specialSubject.description!}
							</div>
						</div>
		            </div>
		        </div>
		        <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize9" /></div>
		    </div>
		</div>


<div class="secMain mt3 clearfix">
	<!--专题左侧 Start-->
    <div class="left">
    	<!--全部专题-->
    	<div class="specialLeft border">
        	<h3 class="h3Head textIn"><a href="specialSubjectList.action?type=specialsubject&amp;specialSubjectId=${specialSubject.specialSubjectId}" class="more">更多</a>全部专题</h3>
            <div class="specialList">
                <ul class="ulList secList">
                	<#if specialSubjectList??>
                		<#list specialSubjectList as sl>
	                		<li>
	                			<a href="specialSubject.action?specialSubjectId=${sl.specialSubjectId}" title="${sl.title!}">
	                				<#if (15 &lt; sl.title?length)>${sl.title[0..14]!}...<#else>${sl.title!}</#if>
	                			</a>
	                		</li>
                		</#list>
                	</#if>
                </ul>
            </div>
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize5" /></div>
        </div>
        <!--提交候选专题-->
        <div class="specialLeft border mt3">
        	<h3 class="h3Head textIn"><a href="<#if (superAdmin?? && "1" == superAdmin)>${SiteUrl}jython/showNewSpecialSubject.py<#else>#</#if>" class="more">更多</a>提交候选专题</h3>
            <div class="specialList">
            	<form id="form1"><!-- method="post" action="${SiteUrl}jython/saveNewSpecialSubject.py"-->
	            	<p><label>*</label>专题名称</p>
	                <p class="specialInput"><input type="text" name="stitle" id="stitle" value="" placeholder="输入专题名称" class="specialText" /></p>
	                <p class="mt10"><label>*</label>提交说明</p>
	                <p class="specialInput"><textarea class="specialArea" name="scontent" id="scontent" placeholder="输入说明"></textarea></p>
	                
					<#if loginUser??>
						<input type="button" id="specialSubmit" value="提交" class="specialSubmit" />
						<#if (superAdmin?? && "1" == superAdmin)>
							<input type="button" value="查看候选专题" onclick="window.open('${SiteUrl}jython/showNewSpecialSubject.py', '_blank')" class="specialSubmit" />
						</#if>
					<#else>
						<a href="login?ruu=/specialSubject.action" class="specialSubmit">先登录后提交</a>
					</#if>
					<#--
	                <p><a href="#" class="specialSubmit">登录并提交</a></p>
					-->
                </form>
            </div>
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize5" /></div>
        </div>
        
        <#--下期备选专题投票开始-->
        <div class="specialLeft border mt3">
        	<h3 class="h3Head textIn"><a href="<#if (superAdmin?? && "1" == superAdmin)>manage_list.action?guid=${parentGuid!}&type=specialsubject<#else>#</#if>" class="more">更多</a>下期备选专题投票</h3>
            <div class="specialList specialTop">
                <ul class="ulList">
                	<#if voteList??>
	                	<#list voteList as v>
	                		<li>
	                			<em class="emDate">${v.createDate?string('MM/dd')}</em>
	                			<span class="<#if (3 > v_index)>numIcon numRed<#else>numIcon</#if>">${v_index + 1}</span>
	                			<a href="mod/vote/getcontent.action?guid=${parentGuid!}&type=specialsubject&voteId=${v.voteId!}" title="${v.title!}">
	                				<#if (11 &lt; v.title?length)>${v.title[0..10]!}...<#else>${v.title!}</#if>
	                			</a>
	                		</li>
	                    </#list>
                    </#if>
                </ul>
            </div>
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize5" /></div>
        </div>
        <#--下期备选专题投票结束-->
    </div>
    
    
		    <div class="right">
		    	<div class="specialRight border">
		            <h3 class="h3Head textIn"><a href="?type=article&article&specialSubjectId=${specialSubjectId!}" class="more">更多</a>专题文章</h3>
		            <div class="specialRightCont">
		               	<#if specialSubjectArticleList?? && 0 < specialSubjectArticleList?size>
		               		<#list specialSubjectArticleList as ssa>
		               			<#if (0 == ssa_index || 1 == ssa_index)>
					                <div class="specialRightContList clearfix">
					                    <h4>
					                    	<a href="${SiteUrl!}showArticle.action?articleId=${ssa.articleId}" target="_blank" title="${ssa.title!}">
					                    		${Util.drawoffHTML(ssa.title, 35)}
					                    	</a>
					                    </h4>
					                    <p>
					                    	<#if (ssa.typeState)><#-- DOC类型 -->
				                    			${Util.drawoffHTML(ssa.articleAbstract, 97)}
					                    	<#else><#-- web类型 -->
					                    		${Util.drawoffHTML(ssa.articleContent, 97)}
					                    	</#if>
					                    </p>
					                    <p class="more1P"><a href="${SiteUrl!}showArticle.action?articleId=${ssa.articleId}" class="more1" target="_blank">更多详情</a></p>
					                </div>
		               			</#if>
		               		</#list> 		
							<ul class="ulList bt">
			               		<#list specialSubjectArticleList as ssa>
			               			<#if (1 < ssa_index)>
				                		<li>
				                			<span class="specialTime">${ssa.createDate?string('yyyy/MM/dd')}</span>
				                			<span class="specialName"><a href='${SiteUrl}go.action?userId=${ssa.userId!}'>${ssa.userTrueName!?html}</a></span>
				                			<em>[<#if (ssa.typeState == false)>原创<#else>转载</#if>]</em>
				                			<a href="${SiteUrl!}showArticle.action?articleId=${ssa.articleId}" target="_blank" title="${ssa.title!}">
							                	<#if (35 &lt; ssa.title?length)>${ssa.title[0..34]!} ...<#else>${ssa.title!}</#if>
				                			</a>
				                		</li>
			               			</#if>
			               		</#list>
		                	</ul>
		                <#else>
		                	<center>暂无文章！</center>
						</#if>
		            </div>
		            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize6" /></div>
		        </div>
		        <div class="specialRight border mt3">
		            <h3 class="h3Head textIn"><#if specialSubject??><a href="specialSubjectList.action?type=topic&amp;specialSubjectId=${specialSubject.specialSubjectId}" class="more">更多</#if></a>专题讨论</h3>
		            <div class="specialRightCont">
		                <ul class="ulList">
		                	<#if topic_list??>
		                		<#if (0 < topic_list?size)>
			                		<#list topic_list as t>
					                    <li>
					                    	<span class="specialTime">${t.createDate?string('yyyy/MM/dd')}</span>
					                    	<span class="specialName"><a href='${SiteUrl}go.action?userId=${t.createUserId!}'>${t.createUserName!?html}</a></span>
					                    	<a href="${ContextPath}show_topic.action?guid=${parentGuid!}&amp;type=specialsubject&amp;topicId=${t.plugInTopicId!}&unitId=${unitId!}" target="_blank" title="${t.title!}">
					                    		${Util.drawoffHTML(t.title, 39)}
					                    	</a>
					                    </li>
			                		</#list>
		                		<#else>
		                			<center>暂无讨论！</center>
		                		</#if>
		                	</#if>
		                </ul>
		            </div>
		            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize6" /></div>
		        </div>
		    </div>
		</div>

		<#include "footer.ftl">
		<script src="${ContextPath}js/new/imgScroll.js"></script>
		<!--[if IE 6]>
		<script src="${ContextPath}js/new/ie6.js" type="text/javascript"></script>
		<script src="${ContextPath}js/new/DD_belatedPNG.js" type="text/javascript"></script>
		<script type="text/javascript">
		    DD_belatedPNG.fix('.topSearch,.login a,.videoPlay,.videoPlayBg,.tx,.liW,.liX,.liP,.leftNavS li,.leftNavIcon,.leftNavIconH,.folder,.liFolder,.secList li,.secVideoPlay');
		</script>
		<![endif]-->
	</body>
</html>
