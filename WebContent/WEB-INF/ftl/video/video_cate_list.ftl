<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <title>用户个人视频分类管理</title>
    <link rel="stylesheet" type="text/css" href="../css/manage.css" />
    <script type="text/javascript">
    <!--
    function confirm_delete(name) {
        if (confirm("您确定要删除 " + name + " 分类吗？\r\n\r\n注意: 分类删除之后, 该分类所有视频将被设置为未分类的.")) {
          return confirm('请再次确定您要删除该分类, 分类删除之后, 该分类所有视频将被设置为未分类的.');
        } else 
          return false;
    }
    //-->
    </script>
</head>
<body>
  <h2>个人视频分类管理</h2>
<div class='funcButton'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='video.action?cmd=list'>视频管理</a>
  &gt;&gt; <a href='videocate.action?cmd=list'>个人视频分类管理</a>
</div>

<table class="listTable" cellspacing="1">
    <thead>
        <tr>
            <th width="10%">标识</th>
            <th width="60%">分类名称(子分类数)</th>
            <th width="20%">分类操作</th>
        </tr>
    </thead>
    <tbody>
        <#list category_tree.all as category>
        <tr>
            <td align="center">${category.id}</td>
            <td>
                ${category.treeFlag2!} ${category.name} 
                <#if (category.childNum >   0)>(${category.childNum}) </#if>
            </td>
            <td>
                <a href="?cmd=add&amp;categoryId=${category.id}">添加子分类</a>
                <a href="?cmd=edit&amp;categoryId=${category.id}">修改</a>
                <#if !category.hasChild>
                    <a onclick="return confirm_delete('${category.name!?js_string}')" href="?cmd=delete&amp;categoryId=${category.id}">删除</a>
                </#if>
            </td>
        </tr>
        </#list>
    </tbody>
</table>

<div class='funcButton'>
<form name='addCateForm' action='videocate.action' method='get'>
  <input type='hidden' name='cmd' value='add' />
  <input type='submit' class='button' value='添加一级分类' />
  <input type='button' class='button' value='分类排序' onclick='cateOrder();' />
</form>

<form name='setOrderForm' action='category_Order.py' method='post' style='display:none'>
  <input type='hidden' name='cmd' value='list' />
  <input type='hidden' name='type' value='user_video_${user.userId}' />
  <input type='hidden' name='parentId' value='' />
</form>
<script language="javascript">
  function cateOrder()
  {
    document.setOrderForm.submit();
  }
</script>

</div>

</body>
</html>
