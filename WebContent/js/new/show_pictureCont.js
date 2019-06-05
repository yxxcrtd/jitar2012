$(document).ready(function(){
	//$('.img_ul li img').LoadImage(true, 70,70);
	//获取锚点即当前图片id
	var picid = location.hash;
	picid = picid.substring(1);
	
	if(isNaN(picid) || picid=='' || picid==null) {
		picid = 1;
	}
	picid = parseInt(picid);

	//图集图片总数
	var totalnum = $("#pictureurls li").length;
	//如果当前图片id大于图片数，显示第一张图片
	if(picid > totalnum || picid < 1) {
		picid = 1;
		next_picid = 1;	//下一张图片id
	} else {
		next_picid = picid + 1;
	}

	url = $("#pictureurls li:nth-child("+picid+") img").attr("rel");
	$("#big-pic").html("<img src='"+url+"' onload='loadpic("+next_picid+")'>");
	//$('#big-pic img').LoadImage(true, 665,5000,$("#load_pic").attr('rel'));
	$("#picnum").html("("+picid+"/"+totalnum+")");
	$("#picinfo").html($("#pictureurls li:nth-child("+picid+") img").attr("alt"));
	
	//wp
	wTime = $("#pictureurls li:nth-child("+picid+")").attr("photoTime");
	wTit = $("#pictureurls li:nth-child("+picid+")").attr("photoTitle");
	$("#upTime").html(wTime);
	$("#imgTit").html(wTit);
		
		
    //页面加载完后对按钮photoID重新赋值
	$.extend({
        rePhotoId: function (){
        var nextId,prevId;
        var picNext = arguments[0] +1;
        var picPrev = arguments[0] -1;
        var nowId = arguments[0];
        nowId = $("#pictureurls li:nth-child("+nowId+")").attr("photoid");
        if(picNext<=totalnum && picNext >1){
            nextId = $("#pictureurls li:nth-child("+picNext+")").attr("photoid");
        }else if(picNext == 1){
            nextId = $("#pictureurls li:nth-child(2)").attr("photoid");
        }else{
            nextId = -1;
        }
        if(picPrev>=1 && picPrev <totalnum){
            prevId = $("#pictureurls lithisindex:nth-child("+picPrev+")").attr("photoid");
        }else if(picPrev >=totalnum){
            picPrev = $("#pictureurls li:nth-child("+(totalnum-1)+")").attr("photoid");
        }else{
            prevId = -1;
        }
        $(".photoBtnLeft").attr("photoid",prevId);
        $(".photoBtnRight").attr("photoid",nextId);
        $("#nowPhotoId").attr("photoid",nowId);
        }
    });
    $.rePhotoId(picid);
	$("#pictureurls li").click(function(){
		i = $(this).index() + 1;
		showpic(i);
        $.rePhotoId(picid);
	});

	//加载时图片滚动到中间
	var _w = $('.img_c li').width()*$('.img_c li').length;
	if(picid>6) {
		movestep = picid - 7;		
	} else {
		movestep = 0;
	}
	$(".img_c ul").css({"left":-+$('.img_c li').width()*movestep});
	//点击图片滚动
	$('.img_c ul').width(_w);
	$(".img_c li").click( function (e) {
		e.stopPropagation();
		var thisindex = $(this).index();
        $.rePhotoId(thisindex+1);
	    if(thisindex>6&&thisindex<(totalnum-1)){
			movestep = $(this).index() - 6;
		}else if(thisindex <=6){
			movestep = 0;
		}
		$(".img_c ul").css({"left":-+$('.img_c li').width()*movestep});
		if(thisindex==0){
			$(".photoBtnLeft").addClass("photoBtnLeft_over");
			$(".photoBtnRight").removeClass("photoBtnRight_over");
			$(".photoPBtn").removeClass("photoPBtn_on");
			$(".photoNBtn").addClass("photoNBtn_on");
		}else if(thisindex == (totalnum-1)){
			$(".photoBtnLeft").removeClass("photoBtnLeft_over");
			$(".photoBtnRight").addClass("photoBtnRight_over");
			$(".photoPBtn").addClass("photoPBtn_on");
			$(".photoNBtn").removeClass("photoNBtn_on");
		}else{
			$(".photoBtnLeft").removeClass("photoBtnLeft_over");
			$(".photoBtnRight").removeClass("photoBtnRight_over");
			$(".photoPBtn").addClass("photoPBtn_on");
			$(".photoNBtn").addClass("photoNBtn_on");
		}
		//点击刷新评论...
		doAjax($('#nowPhotoId').attr('photoid'));
		doAjax_1($('#nowPhotoId').attr('photoid'));
	});
	//当前缩略图添加样式
	$("#pictureurls li:nth-child("+picid+")").addClass("on");
	$(".cont_page_c a:nth-child("+picid+")").addClass("on");
});

$(document).keyup(function(e) {     
	var currKey=0,e=e||event;
	currKey=e.keyCode||e.which||e.charCode;
	switch(currKey) {     
		case 37: // left
			showpic('pre');
			break;
		case 39: // up
			showpic('next');
			break;
	}   
});


function showpic(type, replay) {
	//隐藏重复播放div
	//$("#endSelect").hide();
	$(".blur_left").removeClass("blur_left_on");
	$(".blur_right").addClass("blur_right_on");
	//图集图片总数
	var totalnum = $("#pictureurls li").length;
	var totalnum1 = $(".cont_page_c a").length;
	var pic_type =  type;
	if(type=='next' || type=='pre') {
		//获取锚点即当前图片id
		var picid = location.hash;
		picid = picid.substring(1);
		if(isNaN(picid) || picid=='' || picid==null) {
			picid = 1;
		}
		picid = parseInt(picid);
		
		
  
		if(type=='next') {
			i = picid + 1;
            $.rePhotoId(i);
			//如果是最后一张图片，指针指向第一张
			if(i > totalnum) {
				$(".photoBtnLeft").removeClass("photoBtnLeft_over");
				$(".photoBtnRight").addClass("photoBtnRight_over");
				$(".photoPBtn").addClass("photoPBtn_on");
				$(".photoNBtn").removeClass("photoNBtn_on");
				i=1;
				next_picid=1;
				//重新播放
				if(replay!=1) {
					return false;
				} else {
					$(".photoBtnLeft").removeClass("photoBtnLeft_over");
					$(".photoBtnRight").addClass("photoBtnRight_over");
					$(".photoPBtn").addClass("photoPBtn_on");
					$(".photoNBtn").removeClass("photoNBtn_on");
				}
			} else {
				next_picid = parseInt(i) + 1;
				$(".photoBtnLeft").removeClass("photoBtnLeft_over");
				$(".photoBtnRight").removeClass("photoBtnRight_over");
				$(".photoPBtn").addClass("photoPBtn_on");
				$(".photoNBtn").addClass("photoNBtn_on");
			}

		} else if (type=='pre') {
			i = picid - 1;
            $.rePhotoId(i);
			//如果是第一张图片，指针指向最后一张
			$(".photoBtnLeft").removeClass("photoBtnLeft_over");
			$(".photoPBtn").addClass("photoPBtn_on");
			if(i < 1) {
				$(".photoBtnLeft").addClass("photoBtnLeft_over");
				$(".photoBtnRight").removeClass("photoBtnRight_o");
				$(".photoPBtn").removeClass("photoPBtn_on");
				$(".photoNBtn").addClass("photoNBtn_on");
				i=1;
				next_picid=1;
				//重新播放
				if(replay!=1) {
					return false;
				} else {
					$(".photoBtnLeft").addClass("photoBtnLeft_over");
					$(".photoBtnRight").removeClass("photoBtnRight_over");
					$(".photoPBtn").removeClass("photoPBtn_on");
					$(".photoNBtn").addClass("photoNBtn_on");
				}
			}else if(i<totalnum){
				$(".photoBtnRight").removeClass("photoBtnRight_over");
				$(".photoNBtn").addClass("photoNBtn_on");
			} else {
				next_picid = parseInt(i) - 1;
			}
		}
		url = $("#pictureurls li:nth-child("+i+") img").attr("rel");
		title = $("#pictureurls li:nth-child("+i+") img").attr("title");
		$("#big-pic").html("<img src='"+url+"' onload='loadpic("+next_picid+")'>");
		$("#big-imgtitle").html(title)
		//$('#big-pic img').LoadImage(true, 665,5000,$("#load_pic").attr('rel'));
		$("#picnum").html("("+i+"/"+totalnum+")");
		$("#picinfo").html($("#pictureurls li:nth-child("+i+") img").attr("alt"));
		//wp
		wTime = $("#pictureurls li:nth-child("+i+")").attr("photoTime");
		wTit = $("#pictureurls li:nth-child("+i+")").attr("photoTitle");
		$("#upTime").html(wTime);
		$("#imgTit").html(wTit);
		
		//更新锚点
		location.hash = i;
		type = i;

		//点击图片滚动
		var _w = $('.img_c li').width()*$('.img_c li').length;
		if(i>6&&i<totalnum) {
			movestep = i - 7;
			var lassetp = movestep;//防止最后一张显示出现空白
		}else if(i==totalnum){
			movestep = lassetp;
		} else {
			movestep = 0;
		}
		$(".img_c ul").css({"left":-+$('.img_c li').width()*movestep});
	} else if(type=='big') {
		//获取锚点即当前图片id
		var picid = location.hash;
		picid = picid.substring(1);
		if(isNaN(picid) || picid=='' || picid==null) {
			picid = 1;
		}
		picid = parseInt(picid);

		url = $("#pictureurls li:nth-child("+picid+") img").attr("rel");
		window.open(url);
	} else {
		url = $("#pictureurls li:nth-child("+type+") img").attr("rel");
		$("#big-pic").html("<img src='"+url+"'>");
		//wp
		wTime = $("#pictureurls li:nth-child("+type+")").attr("photoTime");
		wTit = $("#pictureurls li:nth-child("+type+")").attr("photoTitle");
		$("#upTime").html(wTime);
		$("#imgTit").html(wTit);
		
		//$('#big-pic img').LoadImage(true, 665,5000,$("#load_pic").attr('rel'));
		$("#picnum").html("("+type+"/"+totalnum+")");
		$("#picinfo").html($("#pictureurls li:nth-child("+type+") img").attr("alt"));
		location.hash = type;
	}

	$("#pictureurls li,.cont_page_c a").each(function(i){
		j = i+1;
		if(j==type) {
			$("#pictureurls li:nth-child("+j+")").addClass("on");
			$(".cont_page_c a:nth-child("+j+")").addClass("on");
		} else {
			$("#pictureurls li:nth-child("+j+")").removeClass();
			$(".cont_page_c a:nth-child("+j+")").removeClass();
		}
	});
	if(pic_type=='next'){
		doAjax($('#nowPhotoId').attr('photoid'));
		doAjax_1($('#nowPhotoId').attr('photoid'));
	}else if(pic_type=="pre"){
		doAjax($('#nowPhotoId').attr('photoid'));
		doAjax_1($('#nowPhotoId').attr('photoid'));
	}
}

function getPhotoid(){
    objectId = $('#nowPhotoId').attr('photoid'); //为了评论共用变量
    operateUrl = "photos.action?photoId="+$('#nowPhotoId').attr('photoid')+"&t=" + (new Date()).getTime() + "&cmd=";
    pageUrl = "photos.action?photoId="+$('#nowPhotoId').attr('photoid');
}

//预加载图片
function loadpic(id) {
	url = $("#pictureurls li:nth-child("+id+") img").attr("rel");
	$("#load_pic").html("<img src='"+url+"'>");
}
