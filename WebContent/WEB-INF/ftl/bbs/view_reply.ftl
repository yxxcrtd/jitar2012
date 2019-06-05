<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>编辑回复</title>
  <link rel="stylesheet" href="../css/manage.css" type="text/css" />
</head>
  
  <body>
  	<h2>编辑回复</h2>
  	
 	<form action="topic.action" method="post">
 	<input type='hidden' name='cmd' value='inser'/>
 	<input type='hidden' name='topicId' value='${topicId}' />
 	<input type='hidden' name='boardId' value='${boardId}' />
    <table border='0'>
    	<tr>
    		<td>标题:</td>
    		<td><input  type="text" name="title" size="75" value='${reply.title!?html}' /></td>
    	</tr>												
    	<tr>
    		<td>内容:</td>
    		<td> 
    			<textarea name='content' rows="10" cols="60">${reply.content!?html}</textarea>
			</td>
    	</tr>
    	<tr>
    		<td colspan='2'>
    		  <input type="submit" value="修改" />
    		  <input type='reset' />
    		</td>
    	</tr>
    </table>
  </form>
  </body>
</html>
