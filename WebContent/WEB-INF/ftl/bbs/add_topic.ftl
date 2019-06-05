<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>发表主題</title>
  <link rel="stylesheet" href="../css/manage.css" type="text/css" />
</head>
  
  <body>
  	<h2>发表主題</h2>
  	
 	<form action="topic.action" method="post">
 	  <input type='hidden' name='cmd' value='save' />
 	  <input type='hidden' name='boardId' value='${boardId}' />
    <table border='0'>
    	<tr>
    		<td>标题:</td>
    		<td><input  type="text" name="title" size="75" /></td>
    	</tr>
    	<tr>
    		<td>内容:</td>
    		<td>
    			<textarea name='content' rows="10" cols="60"></textarea>
			</td>
    	</tr>
    	<tr>
    		<td>标签:</td>
    		<td><input type="text" name="tags" ></td>
    	</tr>
    	<tr>
    		<td colspan=2>
    		  <input type="submit" value="发表" />
    		  <input type='reset' />
    		  <input type='button' value="返回" onclick="history.back()" />
    		</td>
    	</tr>
    </table>
  </form>
  </body>
</html>
