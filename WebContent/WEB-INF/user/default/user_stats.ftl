<ul class="stats">
	<li>总浏览量: ${user.visitCount}</li>
	<li>文章数: ${user.myArticleCount + user.otherArticleCount + user.historyMyArticleCount + user.historyOtherArticleCount} 篇</li>
	<li>资源数: ${user.resourceCount} 个</li>
	<li>相片数: ${user.photoCount} 张</li>
	<li>视频数: ${user.videoCount} 个</li>
	<li>评论数: ${user.commentCount} 条</li>
	<li>文章积分: ${user.articleScore} </li>
	<li>资源积分: ${user.resourceScore} </li>
	<li>评论积分: ${user.commentScore} </li>
	<li>加分: ${addscore} </li>
	<li>罚分: ${minusscore} </li>
	<!--
	<li>罚分: ${user.articlePunishScore + user.resourcePunishScore + user.commentPunishScore} </li>
	-->
	<li>总积分: ${user.userScore} </li>
</ul>
