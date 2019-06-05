<#if unit_list??>
	<table border="1">
		<tr>
			<th>机构ID</th>
			<th>机构名称</th>
			<th>工作室数</th>
			<th>文章数</th>
			<th>推荐文章数</th>
			<th>资源数</th>
      <th>推荐资源数</th>
      <th>图片数</th>
      <th>视频数</th>
      <th>当前积分</th>
		</tr>
		
		<#list unit_list as unit>
    	 	<tr>
                <td>${unit.unitId}</td>
               
                <td>
                    ${unit.unitTitle!}
                </td>

    			<td>
                    ${unit.userCount}
    			</td>
                <td>
                    ${unit.articleCount}
    			</td>
    			<td>
                    ${unit.recommendArticleCount}
    			</td>
    			<td>
                    ${unit.resourceCount}
    			</td>
                <td>
                    ${unit.recommendResourceCount}
                </td>
                <td>
                    ${unit.photoCount}
                </td>
                <td>
                    ${unit.videoCount}
                </td>
                <td>
                    ${unit.totalScore}
                </td>
    		</tr>
		</#list>
	</table>
</#if>
