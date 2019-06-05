
// 检查是否选择了头像地址
function check() {
	if(document.formupload.file.value == "") {
		window.alert("请填写或选择一个头像的地址！");
	    return false;
	}
	return true;
}

// 检查上传文件的格式
function checkheadimgFile(obj) {
	
	var sArray = new Array();
	sArray.push("gif");
	sArray.push("jpg");
	sArray.push("jpeg");
	var fileCheck=obj.value;
	if (fileCheck != "") {
		var s = fileCheck.match(/^(.*)(\.)(.{1,8})$/)[3];
		s = s.toLowerCase();
		var bM = false;
		for (var i = 0; i <= 2; i++) {
			if (s == sArray[i])
				bM = true;
		}
		if (!bM) {
			alert("请选择 jpg、jpeg、gif 格式的图片文件！");       
			return false;
		}
	}
}

// 头像缩略图
function resizeimg(ImgD, iwidth, iheight) {
	var image = new Image();
	image.src = ImgD.src;
	
	if (image.width > 0 && image.height > 0) {
		if (image.width / image.height >= iwidth / iheight) {
			if (image.width > iwidth) {
				ImgD.width = iwidth;
       			ImgD.height = (image.height * iwidth) / image.width;
   			}
   			else {
				ImgD.width = image.width;
				ImgD.height = image.height;
			}
			ImgD.alt = image.width + "×" + image.height;
	   	}
	   	else {
			if (image.height > iheight) {
				ImgD.height = iheight;
				ImgD.width = (image.width * iheight) / image.height;
			}
			else {
				ImgD.width = image.width;
				ImgD.height = image.height;
			}
			ImgD.alt = image.width + "×" + image.height;
		}
	}
}