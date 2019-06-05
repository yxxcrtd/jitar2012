<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.edustar.jitar.util.*"%>
<%@ page import="cn.edustar.jitar.pojos.User"%>
<%@ page import="cn.edustar.jitar.util.CommonUtil"%>
<%

String path = request.getContextPath();
String basePath = CommonUtil.getSiteUrl(request);

User u = WebUtil.getLoginUser(session);
if(u == null)
{
 //response.sendRedirect(basePath+"login.jsp");
 //return;
}
String photoFile ="";
if(request.getParameter("file")!=null){
    String file = request.getParameter("file");
    photoFile = basePath + "images/headImg/" + file; 
}else{
	if(u!=null){
	    photoFile = basePath +  u.getUserIcon();
	}
}
if(photoFile.length() == 0){
    photoFile = basePath + "images/default.gif";
}
%>
<html>
    <head>
        <meta http-equiv="expires" content="0">
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <script type="text/javascript" src="../js/Jcrop/js/jquery.min.js"></script>
        <script type="text/javascript" src="../js/Jcrop/js/jquery.Jcrop.js"></script>
        <link rel="stylesheet" type="text/css" href="../css/style.css">
        <link rel="stylesheet" href="../js/Jcrop/css/jquery.Jcrop.css" type="text/css"/>
        <link rel="stylesheet" href="../js/Jcrop/css/demos.css" type="text/css" />
        <script type="text/javascript">
        var jcrop_api;
        function init(){
            jQuery(function($) {
                // Create variables (in this scope) to hold the API and image size
                var boundx, boundy;

                $('#target').Jcrop({
                    onChange: updatePreview,
                    onSelect: updatePreview,
                    aspectRatio: 1,
                    minSize: [48, 48],
                    boxWidth: 500,
                    boxHeight: 500,
                    setSelect: [0, 0, 200, 200]  
                }, function() {
                    // Use the API to get the real image size
                    var bounds = this.getBounds();
                    boundx = bounds[0];
                    boundy = bounds[1];
                    // Store the API in the jcrop_api variable
                    jcrop_api = this;
                    jcrop_api.setOptions({ aspectRatio: 1 });
                    jcrop_api.focus();
                });
                function updatePreview(c) {
                    showCoords(c);
                    if (parseInt(c.w) > 0) {
                        var rx = 75 / c.w;
                        var ry = 75 / c.h;

                        $('#preview').css({
                            width: Math.round(rx * boundx) + 'px',
                            height: Math.round(ry * boundy) + 'px',
                            marginLeft: '-' + Math.round(rx * c.x) + 'px',
                            marginTop: '-' + Math.round(ry * c.y) + 'px'
                        });
                    }
                };

            });        	
        }
        
            // Simple event handler, called from onChange and onSelect
            // event handlers, as per the Jcrop invocation above
            function showCoords(c) {
                $('#x1').val(parseInt(c.x,10));
                $('#y1').val(parseInt(c.y,10));
                $('#x2').val(parseInt(c.x2,10));
                $('#y2').val(parseInt(c.y2,10));
                $('#w').val(parseInt(c.w,10));
                $('#h').val(parseInt(c.h,10));
            };

            function clearCoords() {
                $('#coords input').val('');
                $('#h').css({ color: 'red' });
                window.setTimeout(function() {
                    $('#h').css({ color: 'inherit' });
                }, 500);
            };
            
            function uploadfile() {
                var filename = document.getElementById("file1").value;
                if (filename == ""){
                	 alert("请选择上传文件");
                	return;
                }
                var ipos = filename.lastIndexOf(".", filename.length);
                if(ipos<0)
                {
                    alert("请选择照片文件");
                    return;
                }
                var hz = filename.substring(ipos, filename.length);
                hz = hz.toLowerCase();
                if (hz != ".jpg" && hz != ".gif" && hz != ".png") {
                    alert("请选择图片文件(jpg gif png)");
                    return;
                }
                document.getElementById("form1").submit();
            }
            function uploadfile2() {
                var filename = document.getElementById("file1").value;
                if (filename == "") return;
                var ipos = filename.lastIndexOf(".", filename.length);
                if (ipos < 0) {
                    alert("请选择照片文件");
                    return;
                }
                var hz = filename.substring(ipos, filename.length);
                hz = hz.toLowerCase();
                if (hz != ".jpg" && hz != ".gif" && hz != ".png") {
                    alert("请选择图片文件(jpg gif png)");
                    return;
                }
                document.getElementById("target").src = filename;
            }

            function showPhoto() {
            /*
                var img = document.getElementByid("target"); //通过ID获取IMG元素
                var image = new Image(); //new一个image对象
                image.src = img.src;
                //获取尺寸
                image.width; //宽
                image.height; //高
                //获取大小
                image.onreadystatechange = function() {
                    if (image.readyState == "complete") {
                        initFileSize = image.fileSize;
                        var fileSize = Math.ceil(initFileSize / 1024);
                        imgSize.innerHTML = "尺寸：" + img.height + "×" + img.width + " " + fileSize + "k";
                    }
                }
               */
            }

            function save() {
                document.getElementById("width").value = document.getElementById("target").width;
                document.getElementById("height").value = document.getElementById("target").height;
                document.getElementById("form2").submit();
            }
            function ImageOnLoad(img) {
                if (img.width > 800 || img.height > 800) {
                    if (img.width > img.height) { img.width = 800; } else { img.height = 800; }
                }
            }   
            
            function setUserIcon(imgFile,filename){
            	//imgFile = imgFile+"?ttt="+(new Date()).getTime(); 
            	//document.getElementById("target").src = imgFile;
            	//jcrop_api.setImage(imgFile);
            	//init();
            	document.location.href = "UploadPhoto.jsp?file="+filename;
            }
            
            function CropImageUploadSuccess(imgFile){
            	window.opener.setPhotoFile(imgFile);
            	window.close();
            }
            
            function setMinMax(minsize,maxsize){
                jcrop_api.setOptions({minSize: [ minsize, minsize ],maxSize: [ maxsize, maxsize ]});
            }
            init();
  </script>        
    </head>
	<body>
    <div id="outer">
    <div class="jcExample">
    <div class="article">
        <b>上载您的图片文件，剪切为头像照片</b>
        <table border="0">
            <tr>
                <td>
                    <img src="<%=photoFile%>" onload="ImageOnLoad(this);" onerror="this.src='<%=path + "/images/default.gif"%>'" id="target"/>
                </td>
                <td>
                    <div style="width:75px;height:75px;overflow:hidden;position:relative;left:20px;">
                        <img src="<%=photoFile%>" onerror="this.src='<%=path + "/images/default.gif"%>'" id="preview"/>
                    </div>
                    <div style="height:45px;position:relative;left:20px;top:10px">
                        <input type="button" name="savePhoto" value="保存头像" onclick="save();" />
                    </div>
                        <form id = "form2" name = "form2" target = "hiddenframe" action="user.action" method="POST" style = "display:inline">
                        <input name="rd" type="hidden" value="180*180"/>
                        <input type="hidden" name="cmd" value="savephotofile"/>
                        <input type="hidden" name="photofile" id="photofile" value="<%=photoFile%>"/>
                        <input type="hidden" id="x1" name="x1" value="0" />
                        <input type="hidden" id="y1" name="y1" value="0" />
                        <input type="hidden" id="x2" name="x2" value="0" />
                        <input type="hidden" id="y2" name="y2" value="0" />
                        <input type="hidden" id="w" name="w" value="0" />
                        <input type="hidden" id="h" name="h" value="0" />
                        <input type="hidden" name="ww" value="75" />
                        <input type="hidden" name="hh"  value="75"/>
                        <input type="hidden" id="width" name="width" value="0" />
                        <input type="hidden" id="height" name="height" value="0" />
                        </form>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="height:40px;">
			            <form id="form1" name="form1" target="hiddenframe" action="user.action" method="POST" enctype="multipart/form-data">
			            <input type="hidden" name="cmd" value="uploadphotofile"/>
			            <input type="file" id="file1" name="file" size="80"/>
			            <input type="button" name="btnUpload" id="btnUpload" value="上载" onclick="uploadfile();" />
			            <input type="button" name="btnClose" id="btnClose" value="关闭" onclick="window.close();" />
			            </form>
                </td>
            </tr>
        </table>
    </div>
    </div>
    </div>
    
    <iframe name="hiddenframe" style="display:none"></iframe>	
	</body>    
</html>