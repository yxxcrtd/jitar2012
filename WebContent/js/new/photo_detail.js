
function send(){
	$(".publishBtn").each(
			function() {
				$(this)
						.bind(
								"click",
								function() {
									var cmtType = $(this).attr(
											"cmtType");
									var photoId = $(this).attr(
											"photoId");
									if (cmtType == "cmt") {
										var content = $(
												"#textAreaCenter"
														+ photoId)
												.val();
										var url = $('#JITAR_ROOT')
												.val()
												+ "photos.action";
										$
												.post(
														url,
														{
															photoId : photoId,
															cmd : "comment",
															content : content
														},
														function(data) {
															if (data.code == 0) {
																window.location.href = "photos.action?cmd=detail&photoId="
																		+ photoId;
															} else {
																alert(data.message);
															}
														});
									}
								});
			}
	);
	
}


// 初始化
$(function() {
	send();
});

	

function replyComment(cmtId, parentId) {
	var content = $("#replyAreaCenter" + cmtId).val();
	var url = $('#JITAR_ROOT').val() + "photos.action";
	var photoId = $('#photoId').val();
	$.post(url, {
		photoId : photoId,
		cmtId : cmtId,
		cmd : "replyCmt",
		parentId : parentId,
		content : content
	}, function(data) {
		if (data.code == 0) {
			window.location.href = "photos.action?cmd=detail&photoId="
					+ photoId;
		} else {
			alert(data.message);
		}
	});

}

function deleteComment(cmtId) {
	var url = $('#JITAR_ROOT').val() + "photos.action";
	var photoId = $('#photoId').val();
	$(".delSub").attr("cmtId", cmtId);
	$(".delSub").bind("click", function() {
		$.post(url, {
			photoId : photoId,
			cmtId : cmtId,
			cmd : "deleteCmt"
		}, function(data) {
			if (data.code == 0) {
				window.location.href = url + "?cmd=detail&photoId=" + photoId;
			} else {
				alert(data.message);
			}
		});
	});
}