<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.edustar.jitar.util.*"%>
<%@ page import="cn.edustar.jitar.pojos.User"%>
<%
User u = WebUtil.getLoginUser(session);
if(u == null)
{
 response.sendRedirect("/login.jsp");
 return;
}
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!--
 * FCKeditor - The text editor for Internet - http://www.fckeditor.net
 * Copyright (C) 2003-2007 Frederico Caldeira Knabben
 *
 * == BEGIN LICENSE ==
 *
 * Licensed under the terms of any of the following licenses at your
 * choice:
 *
 *  - GNU General Public License Version 2 or later (the "GPL")
 *    http://www.gnu.org/licenses/gpl.html
 *
 *  - GNU Lesser General Public License Version 2.1 or later (the "LGPL")
 *    http://www.gnu.org/licenses/lgpl.html
 *
 *  - Mozilla Public License Version 1.1 or later (the "MPL")
 *    http://www.mozilla.org/MPL/MPL-1.1.html
 *
 * == END LICENSE ==
 *
 * Main page that holds the editor.
-->
<html>
<head>
	<title>FCKeditor</title>
	<meta name="robots" content="noindex, nofollow">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<!-- @Packager.RemoveLine
	<meta http-equiv="Cache-Control" content="public" />
	@Packager.RemoveLine -->
	<script type="text/javascript">

// Instead of loading scripts and CSSs using inline tags, all scripts are
// loaded by code. In this way we can guarantee the correct processing order,
// otherwise external scripts and inline scripts could be executed in an
// unwanted order (IE).

function LoadScript( url )
{
	document.write( '<scr' + 'ipt type="text/javascript" src="' + url + '"><\/scr' + 'ipt>' ) ;
}

function LoadCss( url )
{
	document.write( '<link href="' + url + '" type="text/css" rel="stylesheet" />' ) ;
}

// Main editor scripts.
var sSuffix = ( /*@cc_on!@*/false ) ? 'ie' : 'gecko' ;

/* @Packager.RemoveLine
LoadScript( 'js/fckeditorcode_' + sSuffix + '.js' ) ;
@Packager.RemoveLine */
// @Packager.Remove.Start

LoadScript( '_source/fckconstants.js' ) ;
LoadScript( '_source/fckjscoreextensions.js' ) ;

if ( sSuffix == 'ie' )
	LoadScript( '_source/classes/fckiecleanup.js' ) ;

LoadScript( '_source/internals/fckbrowserinfo.js' ) ;
LoadScript( '_source/internals/fckurlparams.js' ) ;
LoadScript( '_source/classes/fckevents.js' ) ;
LoadScript( '_source/classes/fckdataprocessor.js' ) ;
LoadScript( '_source/internals/fck.js' ) ;
LoadScript( '_source/internals/fck_' + sSuffix + '.js' ) ;
LoadScript( '_source/internals/fckconfig.js' ) ;

LoadScript( '_source/internals/fckdebug.js' ) ;
LoadScript( '_source/internals/fckdomtools.js' ) ;
LoadScript( '_source/internals/fcktools.js' ) ;
LoadScript( '_source/internals/fcktools_' + sSuffix + '.js' ) ;
LoadScript( '_source/fckeditorapi.js' ) ;
LoadScript( '_source/classes/fckimagepreloader.js' ) ;
LoadScript( '_source/internals/fckregexlib.js' ) ;
LoadScript( '_source/internals/fcklistslib.js' ) ;
LoadScript( '_source/internals/fcklanguagemanager.js' ) ;
LoadScript( '_source/internals/fckxhtmlentities.js' ) ;
LoadScript( '_source/internals/fckxhtml.js' ) ;
LoadScript( '_source/internals/fckxhtml_' + sSuffix + '.js' ) ;
LoadScript( '_source/internals/fckcodeformatter.js' ) ;
LoadScript( '_source/internals/fckundo.js' ) ;
LoadScript( '_source/classes/fckeditingarea.js' ) ;
LoadScript( '_source/classes/fckkeystrokehandler.js' ) ;

LoadScript( 'dtd/fck_xhtml10transitional.js' ) ;
LoadScript( '_source/classes/fckstyle.js' ) ;
LoadScript( '_source/internals/fckstyles.js' ) ;

LoadScript( '_source/internals/fcklisthandler.js' ) ;
LoadScript( '_source/classes/fckelementpath.js' ) ;
LoadScript( '_source/classes/fckdomrange.js' ) ;
LoadScript( '_source/classes/fckdocumentfragment_' + sSuffix + '.js' ) ;
LoadScript( '_source/classes/fckw3crange.js' ) ;
LoadScript( '_source/classes/fckdomrange_' + sSuffix + '.js' ) ;
LoadScript( '_source/classes/fckdomrangeiterator.js' ) ;
LoadScript( '_source/classes/fckenterkey.js' ) ;

LoadScript( '_source/internals/fckdocumentprocessor.js' ) ;
LoadScript( '_source/internals/fckselection.js' ) ;
LoadScript( '_source/internals/fckselection_' + sSuffix + '.js' ) ;

LoadScript( '_source/internals/fcktablehandler.js' ) ;
LoadScript( '_source/internals/fcktablehandler_' + sSuffix + '.js' ) ;
LoadScript( '_source/classes/fckxml.js' ) ;
LoadScript( '_source/classes/fckxml_' + sSuffix + '.js' ) ;

LoadScript( '_source/commandclasses/fcknamedcommand.js' ) ;
LoadScript( '_source/commandclasses/fckstylecommand.js' ) ;
LoadScript( '_source/commandclasses/fck_othercommands.js' ) ;
LoadScript( '_source/commandclasses/fckshowblocks.js' ) ;
LoadScript( '_source/commandclasses/fckspellcheckcommand_' + sSuffix + '.js' ) ;
LoadScript( '_source/commandclasses/fcktextcolorcommand.js' ) ;
LoadScript( '_source/commandclasses/fckpasteplaintextcommand.js' ) ;
LoadScript( '_source/commandclasses/fckpastewordcommand.js' ) ;
LoadScript( '_source/commandclasses/fcktablecommand.js' ) ;
LoadScript( '_source/commandclasses/fckfitwindow.js' ) ;
LoadScript( '_source/commandclasses/fcklistcommands.js' ) ;
LoadScript( '_source/commandclasses/fckjustifycommands.js' ) ;
LoadScript( '_source/commandclasses/fckindentcommands.js' ) ;
LoadScript( '_source/commandclasses/fckblockquotecommand.js' ) ;
LoadScript( '_source/commandclasses/fckcorestylecommand.js' ) ;
LoadScript( '_source/commandclasses/fckremoveformatcommand.js' ) ;
LoadScript( '_source/internals/fckcommands.js' ) ;

LoadScript( '_source/classes/fckpanel.js' ) ;
LoadScript( '_source/classes/fckicon.js' ) ;
LoadScript( '_source/classes/fcktoolbarbuttonui.js' ) ;
LoadScript( '_source/classes/fcktoolbarbutton.js' ) ;
LoadScript( '_source/classes/fckspecialcombo.js' ) ;
LoadScript( '_source/classes/fcktoolbarspecialcombo.js' ) ;
LoadScript( '_source/classes/fcktoolbarstylecombo.js' ) ;
LoadScript( '_source/classes/fcktoolbarfontformatcombo.js' ) ;
LoadScript( '_source/classes/fcktoolbarfontscombo.js' ) ;
LoadScript( '_source/classes/fcktoolbarfontsizecombo.js' ) ;
LoadScript( '_source/classes/fcktoolbarpanelbutton.js' ) ;
LoadScript( '_source/internals/fcktoolbaritems.js' ) ;
LoadScript( '_source/classes/fcktoolbar.js' ) ;
LoadScript( '_source/classes/fcktoolbarbreak_' + sSuffix + '.js' ) ;
LoadScript( '_source/internals/fcktoolbarset.js' ) ;
LoadScript( '_source/internals/fckdialog.js' ) ;
LoadScript( '_source/internals/fckdialog_' + sSuffix + '.js' ) ;
LoadScript( '_source/classes/fckmenuitem.js' ) ;
LoadScript( '_source/classes/fckmenublock.js' ) ;
LoadScript( '_source/classes/fckmenublockpanel.js' ) ;
LoadScript( '_source/classes/fckcontextmenu.js' ) ;
LoadScript( '_source/internals/fck_contextmenu.js' ) ;
LoadScript( '_source/classes/fckplugin.js' ) ;
LoadScript( '_source/internals/fckplugins.js' ) ;

// @Packager.Remove.End

// Base configuration file.
LoadScript( '../fckconfig.js' ) ;

	</script>
	<script type="text/javascript">

if ( FCKBrowserInfo.IsIE )
{
	// Remove IE mouse flickering.
	try
	{
		document.execCommand( 'BackgroundImageCache', false, true ) ;
	}
	catch (e)
	{
		// We have been reported about loading problems caused by the above
		// line. For safety, let's just ignore errors.
	}

	// Create the default cleanup object used by the editor.
	FCK.IECleanup = new FCKIECleanup( window ) ;
	FCK.IECleanup.AddItem( FCKTempBin, FCKTempBin.Reset ) ;
	FCK.IECleanup.AddItem( FCK, FCK_Cleanup ) ;
}

// The first function to be called on selection change must the the styles
// change checker, because the result of its processing may be used by another
// functions listening to the same event.
FCK.Events.AttachEvent( 'OnSelectionChange', function() { FCKStyles.CheckSelectionChanges() ; } ) ;

// The config hidden field is processed immediately, because
// CustomConfigurationsPath may be set in the page.
FCKConfig.ProcessHiddenField() ;

// Load the custom configurations file (if defined).
if ( FCKConfig.CustomConfigurationsPath.length > 0 )
	LoadScript( FCKConfig.CustomConfigurationsPath ) ;

	</script>
	<script type="text/javascript">

// Load configurations defined at page level.
FCKConfig_LoadPageConfig() ;

FCKConfig_PreProcess() ;

// Load the active skin CSS.
LoadCss( FCKConfig.SkinPath + 'fck_editor.css' ) ;

// Load the language file.
FCKLanguageManager.Initialize() ;
LoadScript( 'lang/' + FCKLanguageManager.ActiveLanguage.Code + '.js' ) ;

	</script>
	<script type="text/javascript">

// Initialize the editing area context menu.
FCK_ContextMenu_Init() ;

FCKPlugins.Load() ;

	</script>
	<script type="text/javascript">

// Set the editor interface direction.
window.document.dir = FCKLang.Dir ;

	</script>
	<script type="text/javascript">

window.onload = function()
{
	InitializeAPI() ;

	if ( FCKBrowserInfo.IsIE )
		FCK_PreloadImages() ;
	else
		LoadToolbarSetup() ;
}

function LoadToolbarSetup()
{
	FCKeditorAPI._FunctionQueue.Add( LoadToolbar ) ;
}

function LoadToolbar()
{
	var oToolbarSet = FCK.ToolbarSet = FCKToolbarSet_Create() ;

	if ( oToolbarSet.IsLoaded )
		StartEditor() ;
	else
	{
		oToolbarSet.OnLoad = StartEditor ;
		oToolbarSet.Load( FCKURLParams['Toolbar'] || 'Default' ) ;
	}
}

function StartEditor()
{
	// Remove the onload listener.
	FCK.ToolbarSet.OnLoad = null ;

	FCKeditorAPI._FunctionQueue.Remove( LoadToolbar ) ;

	FCK.Events.AttachEvent( 'OnStatusChange', WaitForActive ) ;

	// Start the editor.
	FCK.StartEditor() ;
}

function WaitForActive( editorInstance, newStatus )
{
	if ( newStatus == FCK_STATUS_ACTIVE )
	{
		if ( FCKBrowserInfo.IsGecko )
			FCKTools.RunFunction( window.onresize ) ;

		_AttachFormSubmitToAPI() ;

		FCK.SetStatus( FCK_STATUS_COMPLETE ) ;

		// Call the special "FCKeditor_OnComplete" function that should be present in
		// the HTML page where the editor is located.
		if ( typeof( window.parent.FCKeditor_OnComplete ) == 'function' )
			window.parent.FCKeditor_OnComplete( FCK ) ;
	}
}

// Gecko browsers doens't calculate well that IFRAME size so we must
// recalculate it every time the window size changes.
if ( FCKBrowserInfo.IsGecko )
{
	function Window_OnResize()
	{
		if ( FCKBrowserInfo.IsOpera )
			return ;

		var oCell = document.getElementById( 'xEditingArea' ) ;

		var eInnerElement = oCell.firstChild ;
		if ( eInnerElement )
		{
			eInnerElement.style.height = 0 ;
			eInnerElement.style.height = oCell.scrollHeight - 2 ;
		}
	}
	window.onresize = Window_OnResize ;
}

	</script>
</head>
<body>
	<table width="100%" cellpadding="0" cellspacing="0" style="height: 100%; table-layout: fixed">
		<tr id="xToolbarRow" style="display: none">
			<td id="xToolbarSpace" style="overflow: hidden">
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr id="xCollapsed" style="display: none">
						<td id="xExpandHandle" class="TB_Expand" colspan="3">
							<img class="TB_ExpandImg" alt="" src="images/spacer.gif" width="8" height="4" /></td>
					</tr>
					<tr id="xExpanded" style="display: none">
						<td id="xTBLeftBorder" class="TB_SideBorder" style="width: 1px; display: none;"></td>
						<td id="xCollapseHandle" style="display: none" class="TB_Collapse" valign="bottom">
							<img class="TB_CollapseImg" alt="" src="images/spacer.gif" width="8" height="4" /></td>
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
