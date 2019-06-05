/*function setImgAddr(){
	 var index = arguments[0];
	 var pictureId = arguments[1];
	 var imgBeforeId = arguments[2];
	 var type = arguments[3];
	 var pic = $('#'+pictureId+''+index+'').val();
	 var k = pic.lastIndexOf('/')+1;
	 var result = '';
	 var def = '';
	 if((type=='320*240')){
		 result = pic.substring(0,k)+'s_'+pic.substring(k);
		 def = 'images/s_default.jpg';
	 }else if(type=='200*240'){
		 result = pic.substring(0,k)+'s1_'+pic.substring(k);
		 def = 'images/s1_default.jpg';
	 }else if(type=='200*120'){
		 result = pic.substring(0,k)+'s2_'+pic.substring(k);
		 def = 'images/s2_default.jpg';
	 }else if(type=='160*120'){
		 result = pic.substring(0,k)+'s3_'+pic.substring(k);
		 def = 'images/s3_default.jpg';
	 }else if(type=="video"){
		 result = pic.substring(0,k)+'s_'+pic.substring(k);
		 def = 'images/default_video.png'
	 }
	 $("<img src="+ result +" />").appendTo($('#'+imgBeforeId+''+index+'')).error(function(){
		 $(this).attr('src',def);
	 })
}
*/
/**
 * 
 */

$.extend({
	/**
	 * 
	 */
	//取缩略图
	setImgAddr:function(){
		 var index = arguments[0];
		 var pictureId = arguments[1];
		 var imgBeforeId = arguments[2];
		 var type = arguments[3]; //为image时表示图片频道的幻灯片展示
		 var pic = $('#'+pictureId+''+index+'').val();
		 var k = pic.lastIndexOf('/')+1;
		 var result = '';
		 var result1 =''; //幻灯片对应的大图
		 var def = '';
		 var def1 = '';//幻灯片缩略图默认图
		 if((type=='320x240')){
			 result = pic.substring(0,k)+'320x240_'+pic.substring(k);
			 def = 'images/s_default.jpg';
		 }else if(type=='200x240'){
			 result = pic.substring(0,k)+'200x240_'+pic.substring(k);
			 def = 'images/s1_default.jpg';
		 }else if(type=='200x120'){
			 result = pic.substring(0,k)+'200x120_'+pic.substring(k);
			 def = 'images/s2_default.jpg';
		 }else if(type=='160x120'){
			 result = pic.substring(0,k)+'160x120_'+pic.substring(k);
			 def = 'images/s3_default.jpg';
		 }else if(type=='568x280'){
			 result = pic.substring(0,k)+'568x280_'+pic.substring(k);
			 def = 'images/s4_default.jpg';
		 }else if(type=="video"){
			 result = pic.substring(0,k)+'s_'+pic.substring(k);
			 def = 'images/default_video.png';
		 }else if(type=="image"){ //155x100幻灯片缩略图  
			 result = pic.substring(0,k)+'155x100_'+pic.substring(k); //幻灯片缩略图
			 result1 = pic.substring(0,k)+'690x400_'+pic.substring(k); //幻灯片大图 690x400
			 def = 'images/s5_default.jpg';
			 def1 = 'images/s6_default.jpg';
		 }else if(type=='230x250'){
			 result = pic.substring(0,k)+'230x250_'+pic.substring(k); //分类图片第一种大小
			 def = 'images/s7_default.jpg';
		 }else if(type=='230x136'){
			 result = pic.substring(0,k)+'230x136_'+pic.substring(k); //分类图片第一种大小
			 def = 'images/s8_default.jpg';
		 }else if(type='230x100'){
			 result = pic.substring(0,k)+'230x100_'+pic.substring(k); //分类图片第一种大小
			 def = 'images/s9_default.jpg';
		 }
		 $((type=="image")?"<img src="+ result +" rel="+result1+"/>":"<img src="+ result +" />").appendTo($('#'+imgBeforeId+''+index+'')).error(function(){
			 if(type=="image"){
				 $(this).attr({'src':def,'rel':def1,'alt':'图片没找到显示默认图片'});
			 }else{
				 $(this).attr({'src':def,"alt":'图片没找到显示默认图片'});
			 }
		 })
	},
	setImgAddr1:function(){
		var url = arguments[0];
		var id = arguments[1];
		var k = url.lastIndexOf('/')+1;
		$("<img src = "+url.substring(0,k)+'690x400_'+url.substring(k)+" />").appendTo($('#'+id+'')).error(function(){
			$(this).attr({'src':'images/s6_default.jpg','alt':'图片没找到显示默认图片'});
		});
	},
	setSearch:function(){
		var text = $('.topSearchInput').val();//搜索关键字
		var flag = true;
		if(null==text||text.replace(/\ /g,'').length==0){
			flag = false;
			alert('请输入查询关键字');
			$('.topSearchInput').siblings("a:last").removeAttr('href');
		}
		if(flag){
			var $option = $('.topSearchOption');
			var $topSearchBtn = $('.topSearchBtn');
			var text = $option.children("em").text();
			var $topSearchInput = $('.topSearchInput').val();//搜索关键字
			if(text=='协作组'){
				$topSearchBtn.attr('href','groups.action?type=search&k='+$topSearchInput+"&oc=g");
			}else if(text=='工作室'){
				$topSearchBtn.attr('href','blogList.action?type=search&k='+$topSearchInput+"&oc=b");
			}else if(text=='文章'){
				$topSearchBtn.attr('href','articles.action?type=search&k='+$topSearchInput+"&oc=a");
			}else if(text=='资源'){
				$topSearchBtn.attr('href','resources.action?type=search&k='+$topSearchInput+"&oc=r");
			}else if(text=='图片'){
        $topSearchBtn.attr('href','showPhotoSearch.action?type=search&k='+$topSearchInput+"&oc=r");
      }else if(text=='视频'){
        $topSearchBtn.attr('href','video_list.action?type=search&k='+$topSearchInput+"&oc=r");
      }
		}
	},
	setActive:function(){
		 var href = arguments[0];
         if(href.indexOf('type')!=-1){
            var begin  = href.indexOf('type=')+5;
            var end = href.indexOf('&');
            var type = href.substring(begin,end);
            if('finished' == type){
               if($('#finished_subject').hasClass('sectionTitle')){
            	   $('#finished_subject').removeClass('sectionTitle').addClass('sectionTitle active');
            	   $.removeOtherClass($('#doing_subject'),$('#mine_subject'),$('#done_subject'));
               }else if(!$('#finished_subject').hasClass('sectionTitle')&&!$('#finished_subject').hasClass('sectionTitle active')){
            	   $('#finished_subject').addClass('sectionTitle active');
            	   $.removeOtherClass($('#doing_subject'),$('#mine_subject'),$('#done_subject'));
               }
            }else if('doing'==type){
            	if($('#doing_subject').hasClass('sectionTitle')){
             	   $('#doing_subject').removeClass('sectionTitle').addClass('sectionTitle active');
             	   $.removeOtherClass($('#finished_subject'),$('#mine_subject'),$('#done_subject'));
                }else if(!$('#doing_subject').hasClass('sectionTitle')&&!$('#doing_subject').hasClass('sectionTitle active')){
             	   $('#doing_subject').addClass('sectionTitle active');
             	   $.removeOtherClass($('#finished_subject'),$('#mine_subject'),$('#done_subject'));
                }
            }else if('mine'==type){
            	if($('#mine_subject').hasClass('sectionTitle')){
             	   $('#mine_subject').removeClass('sectionTitle').addClass('sectionTitle active');
             	   $.removeOtherClass($('#finished_subject'),$('#doing_subject'),$('#done_subject'));
                }else if(!$('#mine_subject').hasClass('sectionTitle')&&!$('#mine_subject').hasClass('sectionTitle active')){
             	   $('#mine_subject').addClass('sectionTitle active');
             	   $.removeOtherClass($('#finished_subject'),$('#doing_subject'),$('#done_subject'));
                }
            }else if('done'==type){
            	if($('#done_subject').hasClass('sectionTitle')){
             	   $('#done_subject').removeClass('sectionTitle').addClass('sectionTitle active');
             	   $.removeOtherClass($('#finished_subject'),$('#doing_subject'),$('#mine_subject'));
                }else if(!$('#done_subject').hasClass('sectionTitle')&&!$('#done_subject').hasClass('sectionTitle active')){
             	   $('#done_subject').addClass('sectionTitle active');
             	   $.removeOtherClass($('#finished_subject'),$('#doing_subject'),$('#mine_subject'));
                }
            }
         }
         var as  = $('#listPage_clearfix').find('a');
         
         for(var i = 0 ; i < as.length ; i++){
        	 if($(as[i]).not('.listPagePre').text()==arguments[1]){
        		 $(as[i]).addClass('listPageC active');
			 }
         }
	},
	removeOtherClass:function(){
		if(arguments[0].hasClass('sectionTitle active')){
			arguments[0].removeClass('sectionTitle active').addClass('sectionTitle');
		}
		if(arguments[1].hasClass('sectionTitle active')){
			arguments[1].removeClass('sectionTitle active').addClass('sectionTitle');
		}
		if(arguments[2].hasClass('sectionTitle active')){
			arguments[2].removeClass('sectionTitle active').addClass('sectionTitle');
		}
	},
	/**
	 * id 容器id
	 * totalPage 总页数
	 * offset 偏移量
	 * callback 回调函数
	 */
	getPage:function(){
		var id = arguments[0];
		var totalPage = doAjax(1);
		var offset = arguments[2];
		var callback = arguments[3];
		var pager = new Pager(id,totalPage,offset,callback);
		pager.build();
		callback(1);
	}
});

/**
 * 设置传递的参数 和url
 */
function invokeAjax(){
	var data = arguments[0];
	var url = arguments[1];
	$('#hidden_data').val(data);
	$('#hidden_url').val(url);
	
	var id = arguments[2];
	var totalPage = arguments[3];
	var offset = arguments[4];
	var callback = arguments[5];
	
	$.getPage(id,totalPage,offset,callback);
	
}

/**
 *
 */
 function doAjax(){
	 if(-1!=arguments[0]){
		 $.ajax({
			 url:'photos.action?cmd=detail&photoId='+arguments[0]+'',
			 dataType:'html',
			 success:function(msg){
				 $('#operate_mark').html($(msg).find('#operate_mark').html());//观看投诉刷新
				 operator();//从新绑定事件
				 inform();
				 $('#contentRelationHistory').html($(msg).find('#contentRelationHistory').html());//观看历史刷新
				 $('#contentInfo_label').html($(msg).find('#contentInfo_label').html());//标签刷新
				 $('#summary_introduce').html($(msg).find('#summary_introduce').html());
			 }
		 });
	 }
   }
 
 
 function doAjax_1(){
	 if(-1!=arguments[0]){
		 $.ajax({
			 url:'photos.action?cmd=detail_comment&photoId='+arguments[0]+'',
			 success:function(msg){
				$('.comment').html(msg);
				$('.publishBtn').click(send());//注册发表评论事件
				delCom($(".removeBtn"),$(".deleteTips"),$(".close,.reportCancel"));
			 }
		 });
	 }
   }
 

   function searchEval(){
	   var $key = $('#evaluKey').val();
	   var $pass = $('#evalpass').val();
	   var $gradeId = $('#secInput2').attr("gradeId");
	   var $subjectId = $('#secInput3').attr("subjectId");
	   $('#evalSearch').attr('href','evaluations.action?type='+arguments[0]+'&k='+$key+'&kperson='+$pass+'&gradeId='+$gradeId+'&subjectId='+$subjectId+'');
	   //window.open(arguments[1]+'evaluations.action?type='+arguments[0]+'&k='+$key+'&kperson='+$pass+'&gradeId='+$gradeId+'&subjectId='+$subjectId+'');
   }
   
	function blogssearch(){
		var type='search';
		var search = $('#blogsSearch');
		var $secSearchInput = $('.secSearchInput'); //
		var $secSearchText3 = $('#secInput3'); //
		search.attr('href',arguments[0]+'blogList.action?type='+type+'&k='+$secSearchInput.val()+(typeof($('#secInput1').attr("gradeId"))!='undefined'?'&gradeId='+$('#secInput1').attr("gradeId"):'&gradeId=-1')+('undefined'!=typeof($('#secInput2').attr("subjectId"))?'&subjectId='+$('#secInput2').attr("subjectId"):'&subjectId=-1')+('undefined'!=typeof($secSearchText3.attr("categoryId"))?'&categoryId='+$secSearchText3.attr("categoryId"):'&categoryId=-1'));
		$('#secInput2').val("");
		$('#secInput1').val("");
		$('#secInput3').val("");
		$secSearchInput.val("");
		//$('#secSearchText3').removeAttr("subjectId").removeAttr('gradeId').removeAttr('categoryId');
	}

