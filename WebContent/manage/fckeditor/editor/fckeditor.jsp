<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="cn.edustar.jitar.util.*"%>
<%@ page import="cn.edustar.jitar.pojos.User"%>
<%@ page import="cn.edustar.jitar.JitarConst" %>
<%
    int MIN_ARTICLE_LENGTH1 = JitarConst.MIN_ARTICLE_LENGTH;
	User u = WebUtil.getLoginUser(session);
	if (u == null) {
		response.sendRedirect(request.getContextPath() + "/login.jsp");
		return;
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>FCKeditor</title>
<meta name="robots" content="noindex, nofollow">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Cache-Control" content="public" />
<script type='text/javascript' src='${SiteUrl}js/jquery.js'></script>
<script type="text/javascript">
	// Instead of loading scripts and CSSs using inline tags, all scripts are
	// loaded by code. In this way we can guarantee the correct processing order,
	// otherwise external scripts and inline scripts could be executed in an
	// unwanted order (IE).

	function LoadScript(url) {
		document.write('<scr' + 'ipt type="text/javascript" src="' + url + '"><\/scr' + 'ipt>');
	}

	function LoadCss(url) {
		document.write('<link href="' + url + '" type="text/css" rel="stylesheet" />');
	}
	
	// Main editor scripts.
	var sSuffix = ( /*@cc_on!@*/false) ? 'ie' : 'gecko';
	LoadScript('js/fckeditorcode_' + sSuffix + '.js');
	// Base configuration file.
	LoadScript('../fckconfig.js');
</script>
<script type="text/javascript">
	if (FCKBrowserInfo.IsIE) {
		// Remove IE mouse flickering.
		try {
			document.execCommand('BackgroundImageCache', false, true);
		} catch (e) {
			// We have been reported about loading problems caused by the above
			// line. For safety, let's just ignore errors.
		}

		// Create the default cleanup object used by the editor.
		FCK.IECleanup = new FCKIECleanup(window);
		FCK.IECleanup.AddItem(FCKTempBin, FCKTempBin.Reset);
		FCK.IECleanup.AddItem(FCK, FCK_Cleanup);
	}

	// The first function to be called on selection change must the the styles
	// change checker, because the result of its processing may be used by another
	// functions listening to the same event.
	FCK.Events.AttachEvent('OnSelectionChange', function() {
		FCKStyles.CheckSelectionChanges();
	});

	// The config hidden field is processed immediately, because
	// CustomConfigurationsPath may be set in the page.
	FCKConfig.ProcessHiddenField();

	// Load the custom configurations file (if defined).
	if (FCKConfig.CustomConfigurationsPath.length > 0)
		LoadScript(FCKConfig.CustomConfigurationsPath);
</script>
<script type="text/javascript">
	// Load configurations defined at page level.
	FCKConfig_LoadPageConfig();

	FCKConfig_PreProcess();

	// Load the active skin CSS.
	LoadCss(FCKConfig.SkinPath + 'fck_editor.css');

	// Load the language file.
	FCKLanguageManager.Initialize();
	LoadScript('lang/' + FCKLanguageManager.ActiveLanguage.Code + '.js');
</script>
<script type="text/javascript">
	// Initialize the editing area context menu.
	FCK_ContextMenu_Init();

	FCKPlugins.Load();
</script>
<script type="text/javascript">
	// Set the editor interface direction.
	window.document.dir = FCKLang.Dir;
</script>
<script type="text/javascript">
	window.onload = function() {
		InitializeAPI();

		if (FCKBrowserInfo.IsIE)
			FCK_PreloadImages();
		else
			LoadToolbarSetup();
	}

	function LoadToolbarSetup() {
		FCKeditorAPI._FunctionQueue.Add(LoadToolbar);
	}

	function LoadToolbar() {
		var oToolbarSet = FCK.ToolbarSet = FCKToolbarSet_Create();

		if (oToolbarSet.IsLoaded)
			StartEditor();
		else {
			oToolbarSet.OnLoad = StartEditor;
			oToolbarSet.Load(FCKURLParams['Toolbar'] || 'Default');
		}
	}

	function StartEditor() {
		// Remove the onload listener.
		FCK.ToolbarSet.OnLoad = null;

		FCKeditorAPI._FunctionQueue.Remove(LoadToolbar);

		FCK.Events.AttachEvent('OnStatusChange', WaitForActive);

		// Start the editor.
		FCK.StartEditor();
	}

	function WaitForActive(editorInstance, newStatus) {

		if (newStatus == FCK_STATUS_ACTIVE) {
			if (FCKBrowserInfo.IsGecko)
				FCKTools.RunFunction(window.onresize);

			_AttachFormSubmitToAPI();

			FCK.SetStatus(FCK_STATUS_COMPLETE);

			// Call the special "FCKeditor_OnComplete" function that should be present in
			// the HTML page where the editor is located.
			//alert(typeof( window.parent.FCKeditor_OnComplete ));
			//if ( typeof( window.parent.FCKeditor_OnComplete ) == 'function' )
			//window.parent.FCKeditor_OnComplete( FCK ) ;
		}
	}

	// Gecko browsers doens't calculate well that IFRAME size so we must
	// recalculate it every time the window size changes.
	if (FCKBrowserInfo.IsGecko) {
		function Window_OnResize() {
			if (FCKBrowserInfo.IsOpera)
				return;

			var oCell = document.getElementById('xEditingArea');

			var eInnerElement = oCell.firstChild;
			if (eInnerElement) {
				eInnerElement.style.height = 0;
				eInnerElement.style.height = oCell.scrollHeight - 2;
			}
		}
		window.onresize = Window_OnResize;
	}
	//统计编辑器中内容的字数
	function getLength(){
		   var oEditor = FCKeditorAPI.GetInstance("HtmlEditor");
		   var oDOM = oEditor.EditorDocument;
		   var iLength ;
		   if(document.all){
		   iLength = oDOM.body.innerText.length;
		   }else{
		   var r = oDOM.createRange();
		   r.selectNodeContents(oDOM.body);
		   iLength = r.toString().length;
		   }
		   return iLength;
	}
	
	
	
</script>
</head>
<body>
    <input type='hidden' name='article_min_length' value=<%=MIN_ARTICLE_LENGTH1%> />
	<table width="100%" cellpadding="0" cellspacing="0"
		style="height: 100%; table-layout: fixed">
		<tr id="xToolbarRow" style="display: none">
			<td id="xToolbarSpace" style="overflow: hidden">
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr id="xCollapsed" style="display: none">
						<td id="xExpandHandle" class="TB_Expand" colspan="3"><img
							class="TB_ExpandImg" alt="" src="images/spacer.gif" width="8"
							height="4" /></td>
					</tr>
					<tr id="xExpanded" style="display: none">
						<td id="xTBLeftBorder" class="TB_SideBorder"
							style="width: 1px; display: none;"></td>
						<td id="xCollapseHandle" style="display: none" class="TB_Collapse"
							valign="bottom"><img class="TB_CollapseImg" alt=""
							src="images/spacer.gif" width="8" height="4" /></td>
						<td id="xToolbar" class="TB_ToolbarSet"></td>
						<td class="TB_SideBorder" style="width: 1px"></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td id="xEditingArea" valign="top" style="height: 100%"></td>
		</tr>
	</table>
</body>
</html>
