function ajaxGetContent(pageNo) {
    //重新获取参数数据。
    getUrl();
    k = $("#k").val();
    //永远记住当前页的页码。
    var page = pageNo;    
    //替换page=xx为当前的
    url = urlPattern.replace(/page={page}/,"page=" + page);
    //alert(url);
    //先删除旧的内容，再填充ajax返回的内容。
    $("#showContent").html("正在加载数据…………");
    //$(".listCont ul").find("li").not($(".listCont ul li").first()).remove();
    //$("#__pager").remove();
    $(".listPage").remove();
    //ajax请求内容，包含各种查询条件。
    //var url = "actions.action?cmd=ajax&ownerType=" + ownerType + "&showType=" + showType + "&filter=" + filter + "&page=" + pageNo + "&k=" + encodeURIComponent(k) + "&t=" + (new Date()).getTime();
    //alert(url)
    $.ajax({ url: url }).done(function (html) {
        // 完成之后，填充内容
        //$(".listCont ul").append(html);
        $("#showContent").html(html);
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
        
        reHeight();
        
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