                <table border="0" cellspacing="1" cellpadding="0" style="width:100%;table-layout:fixed;margin-top:10px;margin-bottom:10px">
                    <tr>
                        <td style="width:180px;text-align:center; vertical-align:middle;">
                            ${content.publishUserName!?html}<br/>
                            ${content.createDate?string("yyyy-MM-dd")}
                            <br/><br/>
                        </td>
                        <td>
                            <script>
                            <#if content.publishContent?? && content.publishContent != "">
                            var content = ${content.publishContent};
                            <#else>
                            var content = [];
                            </#if>
                              for(i=0;i<content.length;i++)
                              {
                               field = content[i];
                               for(x in field)
                               {
                                  document.write("<span class='contenttitle'>"+x+"</span>");
                                  document.write("<span class='contenttext'>"+field[x]+"</span>");
                               }
                              }
                            </script>
                        </td>
                    </tr>
                </table>
                <div style="height:1px;margin-left:20px;margin-right:20px;background:#65ee87"></div>
