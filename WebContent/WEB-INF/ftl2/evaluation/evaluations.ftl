<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 观课评课</title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/gradesubject1.js" type="text/javascript" charset="utf-8"></script>

<script>
var ownerType = ""; //左边tree选择
var k = ""; //关键字
var type = "${type?default('doing')}"; //标签类别
var page = 1; //当前页数
var kperson = "";//授课人
var subjectId = "";
var gradeId = "";

function ajaxGetContent(pageNo) {
	//alert(type);
    k = $("#evaluKey").val();
    kperson = $('#evalpass').val();
    //永远记住当前页的页码。
    page = pageNo;
    //先删除旧的内容，再填充ajax返回的内容。
    //$(".listCont ul").find("li").not($(".listCont ul li").first()).remove();
    //$("#__pager").remove();
    var flag = true;
    var url = "";
    if('tree'==arguments[1]){
       flag = false;
    }
    //ajax请求内容，包含各种查询条件。
    if(flag){
       url = "evaluations.action?cmd=ajax&type=" + type + "&page=" + pageNo + "&k=" + encodeURIComponent(k) + "&kperson="+kperson+"&gradeId="+gradeId+"&subjectId="+subjectId;
    }else if(0==gradeId){
       url = "evaluations.action?cmd=ajax&type=" + type + "&page=" + pageNo +"&subjectId="+subjectId;
    }else if(/^[1-9]0{3}$/g.test(gradeId)){
       url = "evaluations.action?cmd=ajax&type=" + type + "&page=" + pageNo +"&subjectId="+subjectId+"&gradeId="+gradeId+"&target=child";
    }else {
       url = "evaluations.action?cmd=ajax&type=" + type + "&page=" + pageNo +"&gradeId="+gradeId+"&subjectId="+subjectId+"&level=1";
    }
    
    //alert(url);
    $.ajax({ url: url }).done(function (html) {
       //alert(html);
        // 完成之后，填充内容
        $('#evalu_list').html(html);
        //绑定分页事件。
        $(".listPageC").one("click", function () {
            page = parseInt($(this).text(), 10);
            ajaxGetContent(page);
        });

        //绑定分页事件。
        $(".listPagePre").one("click", function () {
            next = $(this).text() == ">" || $(this).text() == "&gt;";
            page = next ? page + 1 : page - 1;
            ajaxGetContent(page);
        });

        //绑定分页事件。
        $("input[value='GO']").one("click", function () {
            var ele = $(this).parent().prev().find('input');
            var minValue = ele.attr("minValue");
            var maxValue = ele.attr("maxValue");
            minValue = parseInt(minValue, 10);
            maxValue = parseInt(maxValue, 10);
            if (isNaN(minValue)) minValue = 1;
            if (isNaN(maxValue)) maxValue = 1;
            var goPage = parseInt(ele.val(), 10);
            if (isNaN(goPage)) {
                goPage = 1;
            }
            if (goPage > maxValue) {
                goPage = maxValue;
            }
            if (goPage < minValue) {
                goPage = minValue;
            }
            ajaxGetContent(goPage);
        });
    });
}

//页面首次加载，绑定一些事件
$(document).ready(function () {
    //搜索部分事件
    //$(".secSearchSelect a").each(function () {
      //  $(this).bind("click", function () { filter = $(this).attr("filter"); });
    //});
    $(".secSearchBtn").bind("click", function () {
        keyword = $("#evaluKey").val();
        page = 1;
        gradeId = 'undefined'!=typeof($('#secInput2').attr("gradeId"))?$('#secInput2').attr("gradeId"):'';
		subjectId = 'undefined'!=typeof($('#secInput3').attr("subjectId"))?$('#secInput3').attr("subjectId"):'';
	    
	    //在输入框无数据的时候清空
	    if(""==$.trim($('#secInput3').val())){
	       subjectId='';
	    }
	    
	    if(""==$.trim($('#secInput2').val())){
	       gradeId ='';
	    }
	    
        ajaxGetContent(page);
    });

    //左边Tree事件
    $(".leftNav a").each(function () {
        $(this).bind("click", function () {
            subjectId = $(this).attr('metaSubjectId');
            gradeId = $(this).attr('metaGradeId');
            //alert('subjectId='+subjectId+":"+"gradeId="+gradeId);
            //ownerType = $(this).attr("ownerType");
            page = 1;
            ajaxGetContent(page,'tree');
        });
    });

    //tab 分类事件
    $(".sectionTitle").each(function () {
        $(this).bind("click", function () {
            $(".sectionTitle").each(function () {$(this).removeClass("active"); });
            page = 1;
            $(this).addClass("active");
            type = $(this).attr("type");
            gradeId = 'undefined'!=typeof($('#secInput2').attr("gradeId"))?$('#secInput2').attr("gradeId"):'';
		    subjectId = 'undefined'!=typeof($('#secInput3').attr("subjectId"))?$('#secInput3').attr("subjectId"):'';
		    
		    //在输入框无数据的时候清空
		    if(""==$.trim($('#secInput3').val())){
		       subjectId='';
		    }
		    
		    if(""==$.trim($('#secInput2').val())){
		       gradeId ='';
		    }
	        ajaxGetContent(page);
	     });
    });

    //加载第一页数据
    ajaxGetContent(1);
});
</script>
</head>
<body>
<#include '../site_head.ftl'>
<div class="main mt25 clearfix">
    <#include 'tree.ftl'>
    <div class="right">
	   <#include 'search.ftl'>
        <!--列表内容 Start-->
        <div class="secRightW mt3 border">
            <h3 class="h3Head">
                <#if loginError??><span style="font-family:黑体;font-size:15px;color:red;margin-left:70px;">${loginError}</span><#else><#if templateError??><span style="font-family:黑体;font-size:15px;color:red;margin-left:30px;" title="${templateError!}">${Util.getCountedWords(templateError!?html,8,1)}</span><#else>
                <#if loginUser??>
                <a href="evaluations.action?cmd=add&evaluationPlanId=0" class="initiate">发起评课</a>
                <#else>
                <span class="initiate">请先登录</span>
                </#if>                
                </#if></#if>
                <a id = "finished_subject" href="javascript:void(0)" class="sectionTitle <#if type="finished"> active</#if>" type="finished">已完成的评课<span></span></a>
                <a id = "doing_subject" href="javascript:void(0)" class="sectionTitle active <#if type="doing"> active</#if>" type="doing">进行中的评课<span></span></a>
                <#if loginUser??><a id = "mine_subject" href="javascript:void(0)" class="sectionTitle <#if type="mine"> active</#if>" type="mine">我发起的评课<span></span></a>
                <a id = "done_subject" href="javascript:void(0)" class="sectionTitle <#if type="done"> active</#if>" type="done">我参与的评课<span></span></a></#if>
            </h3>
            <!--循环-->
	            <div id="evalu_list" class="listCont">
	            </div>
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize6" /></div>
        </div>
    </div>
</div>
<#include '../footer.ftl'>