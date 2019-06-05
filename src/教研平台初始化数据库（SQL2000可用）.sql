/****** Object:  Table [dbo].[U_Favorites]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[U_Favorites](
  [favID] [int] IDENTITY(1,1) NOT NULL,
  [favUser] [int] NULL,
  [ObjectType] [int] NULL,
  [ObjectUuid] [varchar](50) NULL,
  [ObjectID] [int] NULL,
  [favDate] [datetime] NOT NULL,
  [favTitle] [varchar](500) NULL,
  [favInfo] [text] NULL,
  [favTypeID] [int] NULL,
  [favHref] [varchar](1000) NULL,
 CONSTRAINT [PK_U_Favorites] PRIMARY KEY CLUSTERED 
(
  [favID] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_U_Favorites_Obj] ON [dbo].[U_Favorites] 
(
  [ObjectID] ASC,
  [ObjectType] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[S_SiteStat]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[S_SiteStat](
  [Id] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [VisitCount] [int] NOT NULL,
  [UserCount] [int] NOT NULL,
  [GroupCount] [int] NOT NULL,
  [ArticleCount] [int] NOT NULL,
  [ResourceCount] [int] NOT NULL,
  [TopicCount] [int] NOT NULL,
  [PhotoCount] [int] NOT NULL,
  [TodayArticleCount] [int] NOT NULL,
  [YesterdayArticleCount] [int] NOT NULL,
  [TodayResourceCount] [int] NOT NULL,
  [YesterdayResourceCount] [int] NOT NULL,
  [CommentCount] [int] NOT NULL,
  [DateCount] [varchar](500) NULL,
  [HistoryArticleCount] [int] NOT NULL,
 CONSTRAINT [PK_S_SiteStat] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'唯一标识，因为设计只有一条记录，所以总是 = 1' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'S_SiteStat', @level2type=N'COLUMN',@level2name=N'Id'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'总访问量' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'S_SiteStat', @level2type=N'COLUMN',@level2name=N'VisitCount'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'注册用户数' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'S_SiteStat', @level2type=N'COLUMN',@level2name=N'UserCount'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'协作组数量' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'S_SiteStat', @level2type=N'COLUMN',@level2name=N'GroupCount'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'文章总数' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'S_SiteStat', @level2type=N'COLUMN',@level2name=N'ArticleCount'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'资源数量' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'S_SiteStat', @level2type=N'COLUMN',@level2name=N'ResourceCount'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'主题数量' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'S_SiteStat', @level2type=N'COLUMN',@level2name=N'TopicCount'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'相片数量' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'S_SiteStat', @level2type=N'COLUMN',@level2name=N'PhotoCount'
GO
/****** Object:  Table [dbo].[Jitar_GroupQuery]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jitar_GroupQuery](
  [id] [int] IDENTITY(1,1) NOT NULL,
  [ObjectGuid] [varchar](50) NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [BeginDate] [varchar](50) NOT NULL,
  [EndDate] [varchar](50) NOT NULL,
  [LoginName] [varchar](255) NOT NULL,
  [TrueName] [nvarchar](255) NULL,
  [UnitName] [varchar](255) NULL,
  [UnitTitle] [nvarchar](255) NULL,
  [MetaSubjectId] [int] NULL,
  [MetaSubjectTitle] [nvarchar](255) NULL,
  [GradeId] [int] NULL,
  [GradeTitle] [nvarchar](255) NULL,
  [ArticleCount] [int] NOT NULL,
  [BestArticleCount] [int] NOT NULL,
  [ResourceCount] [int] NOT NULL,
  [BestResourceCount] [int] NOT NULL,
  [TopicCount] [int] NOT NULL,
  [ReplyCount] [int] NOT NULL,
 CONSTRAINT [PK_Jitar_GroupQuery] PRIMARY KEY CLUSTERED 
(
  [id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Jitar_WeekCommentArticle]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS OFF
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jitar_WeekCommentArticle](
  [ArticleId] [int] NOT NULL,
  [Title] [nvarchar](256) NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [TypeState] [bit] NOT NULL,
  [UserId] [int] NOT NULL,
  [UserIcon] [varchar](256) NULL,
  [TrueName] [nvarchar](256) NOT NULL,
  [LoginName] [varchar](256) NOT NULL,
  [CommentCount] [int] NULL,
 CONSTRAINT [PK_Jitar_WeekCommentArticle] PRIMARY KEY CLUSTERED 
(
  [ArticleId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[U_Condition2]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[U_Condition2](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [GroupId] [int] NULL,
  [TeacherType] [varchar](50) NULL,
  [TeacherTypeKeyword] [varchar](50) NULL,
  [SQL_Condition] [varchar](5000) NULL,
 CONSTRAINT [PK_U_Condition2] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[S_ProductConfig]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[S_ProductConfig](
  [ID] [int] IDENTITY(1,1) NOT NULL,
  [ProductID] [varchar](50) NULL,
  [ProductName] [varchar](500) NULL,
  [InstallDate] [varchar](50) NULL,
 CONSTRAINT [PK_S_ProductConfig] PRIMARY KEY CLUSTERED 
(
  [ID] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Jitar_ColumnNews]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Jitar_ColumnNews](
  [ColumnNewsId] [int] IDENTITY(1,1) NOT NULL,
  [ColumnId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [Title] [nvarchar](256) NOT NULL,
  [Content] [ntext] NULL,
  [Picture] [nvarchar](256) NULL,
  [CreateDate] [datetime] NOT NULL,
  [ViewCount] [int] NOT NULL,
  [Published] [bit] NOT NULL,
 CONSTRAINT [PK_Jitar_ColumnNews] PRIMARY KEY CLUSTERED 
(
  [ColumnNewsId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_ColumnNews_ColumnId] ON [dbo].[Jitar_ColumnNews] 
(
  [ColumnId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Jitar_UserType]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Jitar_UserType](
  [TypeId] [int] IDENTITY(1,1) NOT NULL,
  [TypeName] [nvarchar](50) NOT NULL,
  [IsSystem] [bit] NOT NULL,
 CONSTRAINT [PK_Jitar_UserType] PRIMARY KEY CLUSTERED 
(
  [TypeId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[U_Condition1]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[U_Condition1](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [GroupId] [int] NULL,
  [Score1] [int] NULL,
  [Score2] [int] NULL,
  [ConditionType] [int] NULL,
 CONSTRAINT [PK_U_Condition1] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_U_Condition1_GroupId] ON [dbo].[U_Condition1] 
(
  [GroupId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_U_Condition1_Score1_Score2] ON [dbo].[U_Condition1] 
(
  [Score1] ASC,
  [Score2] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[S_Plugin]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[S_Plugin](
  [PluginId] [int] IDENTITY(1,1) NOT NULL,
  [PluginName] [varchar](50) NOT NULL,
  [PluginTitle] [nvarchar](50) NOT NULL,
  [PluginType] [varchar](50) NOT NULL,
  [ItemOrder] [int] NOT NULL,
  [Enabled] [int] NOT NULL,
  [Icon] [varchar](125) NULL,
 CONSTRAINT [PK_S_Plugin] PRIMARY KEY CLUSTERED 
(
  [PluginId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Jitar_Column]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jitar_Column](
  [ColumnId] [int] IDENTITY(1,1) NOT NULL,
  [ColumnName] [nvarchar](50) NOT NULL,
  [DisplayName] [varchar](50) NOT NULL,
  [HasPicture] [bit] NOT NULL,
  [Description] [nvarchar](512) NULL,
  [AnonymousShowContent] [bit] NOT NULL,
 CONSTRAINT [PK_Jitar_Column] PRIMARY KEY CLUSTERED 
(
  [ColumnId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Jitar_UserSubjectGrade]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Jitar_UserSubjectGrade](
  [UserSubjectGradeId] [int] IDENTITY(1,1) NOT NULL,
  [UserId] [int] NOT NULL,
  [SubjectId] [int] NULL,
  [GradeId] [int] NULL,
 CONSTRAINT [PK_Jitar_UserSubjectGrade] PRIMARY KEY CLUSTERED 
(
  [UserSubjectGradeId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_UserSubjectGrade_UserId] ON [dbo].[Jitar_UserSubjectGrade] 
(
  [UserId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[T_NewSpecialSubject]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[T_NewSpecialSubject](
  [NewSpecialSubjectId] [int] IDENTITY(1,1) NOT NULL,
  [NewSpecialSubjectTitle] [nvarchar](255) NOT NULL,
  [NewSpecialSubjectContent] [ntext] NULL,
  [CreateUserId] [int] NOT NULL,
  [CreateUserName] [nvarchar](50) NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [AddIp] [varchar](50) NULL,
 CONSTRAINT [PK_T_NewSpecialSubject] PRIMARY KEY CLUSTERED 
(
  [NewSpecialSubjectId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[S_Placard]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[S_Placard](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [ObjId] [int] NOT NULL,
  [ObjType] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [Hide] [bit] NOT NULL,
  [Title] [nvarchar](128) NOT NULL,
  [Content] [ntext] NULL,
 CONSTRAINT [PK_S_Placard] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_S_Placard_Obj] ON [dbo].[S_Placard] 
(
  [ObjId] ASC,
  [ObjType] ASC
) ON [PRIMARY]
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'用户标识或群组标识。' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'S_Placard', @level2type=N'COLUMN',@level2name=N'ObjId'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'对象类型' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'S_Placard', @level2type=N'COLUMN',@level2name=N'ObjType'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'是否隐藏。' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'S_Placard', @level2type=N'COLUMN',@level2name=N'Hide'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'公告内容。' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'S_Placard', @level2type=N'COLUMN',@level2name=N'Content'
GO
/****** Object:  Table [dbo].[Jitar_ChannelVideo]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jitar_ChannelVideo](
  [ChannelVideoId] [int] IDENTITY(1,1) NOT NULL,
  [ChannelId] [int] NOT NULL,
  [VideoId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [ChannelCate] [varchar](256) NULL,
  [ChannelCateId] [int] NULL,
  [UnitId] [int] NOT NULL,
  [ViewCount] [int] NOT NULL,
 CONSTRAINT [PK_Jitar_ChannelVideo] PRIMARY KEY CLUSTERED 
(
  [ChannelVideoId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_ChannelVideo_ChannelCateId] ON [dbo].[Jitar_ChannelVideo] 
(
  [ChannelCateId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_ChannelVideo_ChannelId] ON [dbo].[Jitar_ChannelVideo] 
(
  [ChannelId] ASC
) ON [PRIMARY]
GO
/****** Object:  StoredProcedure [dbo].[reNewUserStat]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[reNewUserStat]  

AS  

  BEGIN  

    DROP TABLE U_UserStat 

    SELECT * INTO U_UserStat FROM U_User 

     

    ALTER TABLE U_UserStat ADD [IsStar] int 

    ALTER TABLE U_UserStat ADD [IsShow] int 

  END
GO
/****** Object:  Table [dbo].[Jitar_Report]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Jitar_Report](
  [ReportId] [int] IDENTITY(1,1) NOT NULL,
  [ObjType] [int] NOT NULL,
  [ObjId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [ReportType] [nvarchar](16) NOT NULL,
  [ObjTitle] [nvarchar](128) NOT NULL,
  [ReportContent] [nvarchar](128) NULL,
  [Status] [bit] NOT NULL,
 CONSTRAINT [PK_Jitar_Report] PRIMARY KEY CLUSTERED 
(
  [ReportId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_Report_ObjIdType] ON [dbo].[Jitar_Report] 
(
  [ObjId] ASC,
  [ObjType] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_Report_ReportType] ON [dbo].[Jitar_Report] 
(
  [ReportType] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_Report_UserId] ON [dbo].[Jitar_Report] 
(
  [UserId] ASC
) ON [PRIMARY]
GO
/****** Object:  StoredProcedure [dbo].[PageView]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS OFF
GO
SET QUOTED_IDENTIFIER OFF
GO
CREATE PROCEDURE [dbo].[PageView]
@tbname     sysname,           --要分页显示的表名
@FieldKey   sysname,           --用于定位记录的主键(惟一键)字段,只能是单个字段
@PageCurrent int=1,             --要显示的页码
@PageSize   int=20,            --每页的大小(记录数)
@FieldShow  nvarchar(1000)='',  --以逗号分隔的要显示的字段列表,如果不指定,则显示所有字段
@FieldOrder  nvarchar(1000)='', --以逗号分隔的排序字段列表,可以指定在字段后面指定DESC/ASC
@Where     nvarchar(4000)=''  --查询条件
--@PageCount  int OUTPUT        --总页数 (--由于 Hibernate 处理这个参数可能有问题，现在不采用这种方法得到了。)
AS
DECLARE @sql nvarchar(4000)
SET NOCOUNT ON


--其他参数检查及规范
IF ISNULL(@PageCurrent,0)<1 SET @PageCurrent=1
IF ISNULL(@PageSize,0)<1 SET @PageSize=20
IF ISNULL(@FieldShow,N'')=N'' SET @FieldShow=N'*'
IF ISNULL(@FieldOrder,N'')=N''
SET @FieldOrder=N''
ELSE
SET @FieldOrder=N'ORDER BY '+LTRIM(@FieldOrder)
IF ISNULL(@Where,N'')=N''
SET @Where=N''
ELSE
SET @Where=N'WHERE ('+@Where+N')'

--如果@PageCount为NULL值,则计算总页数(这样设计可以只在第一次计算总页数,以后调用时,把总页数传回给存储过程,避免再次计算总页数,对于不想计算总页数的处理而言,可以给@PageCount赋值)
--IF @PageCount IS NULL
--BEGIN
--SET @sql=N'SELECT @PageCount=COUNT(*)'+N' FROM '+@tbname+N' '+@Where
--EXEC sp_executesql @sql,N'@PageCount int OUTPUT',@PageCount OUTPUT
--SET @PageCount=(@PageCount+@PageSize-1)/@PageSize
--END

--计算分页显示的TOPN值
DECLARE @TopN varchar(20),@TopN1 varchar(20)
SELECT @TopN=@PageSize,
@TopN1=@PageCurrent*@PageSize

--第一页直接显示
IF @PageCurrent=1
EXEC(N'SELECT TOP '+@TopN
+N' '+@FieldShow
+N' FROM '+@tbname
+N' '+@Where
+N' '+@FieldOrder)
ELSE
BEGIN
-- 下面的代码存在 Bug，在后面先临时处理了
SELECT @PageCurrent=@TopN1,
@sql=N'SELECT @x=@x-1,@s=CASE WHEN @x<'+@TopN
+N' THEN @s+N'',''+QUOTENAME(RTRIM(CAST('+@FieldKey
+N' as varchar(8000))),N'''''''') ELSE N'''' END FROM '+@tbname
+N' '+@Where
+N' '+@FieldOrder
SET ROWCOUNT @PageCurrent
EXEC sp_executesql @sql,N'@x int,@s nvarchar(4000) OUTPUT',@PageCurrent,@sql OUTPUT

SET ROWCOUNT 0
IF @sql=N''
EXEC(N'SELECT TOP 0'
+N' '+@FieldShow
+N' FROM '+@tbname)
ELSE
BEGIN
If PATINDEX('%@x%',@sql) > 0
 EXEC(N'SELECT TOP 0'+N' '+@FieldShow+N' FROM '+@tbname)
Else
BEGIN
SET @sql=STUFF(@sql,1,1,N'')
--执行查询
EXEC(N'SELECT TOP '+@TopN
+N' '+@FieldShow
+N' FROM '+@tbname
+N' WHERE '+@FieldKey
+N' IN('+@sql
+N') '+@FieldOrder)
END
END
END
GO
/****** Object:  Table [dbo].[Jitar_ChannelUserStat]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jitar_ChannelUserStat](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [ChannelId] [int] NOT NULL,
  [StatGuid] [varchar](50) NULL,
  [UserId] [int] NOT NULL,
  [LoginName] [varchar](50) NULL,
  [UserTrueName] [nvarchar](50) NULL,
  [UnitId] [int] NULL,
  [UnitTitle] [nvarchar](50) NULL,
  [ArticleCount] [int] NOT NULL,
  [ResourceCount] [int] NOT NULL,
  [PhotoCount] [int] NOT NULL,
  [VideoCount] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[S_Meetings]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[S_Meetings](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [Object] [nvarchar](10) NULL,
  [ObjectId] [int] NOT NULL,
 CONSTRAINT [PK_S_Video] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[G_ChatMessage]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[G_ChatMessage](
  [ChatMessageId] [int] IDENTITY(1,1) NOT NULL,
  [RoomId] [int] NOT NULL,
  [SenderId] [int] NOT NULL,
  [SenderName] [varchar](250) NOT NULL,
  [ReceiverId] [varchar](4) NULL,
  [ReceiverName] [varchar](250) NULL,
  [TalkContent] [varchar](8000) NULL,
  [SendDate] [datetime] NOT NULL,
  [IsSendAll] [bit] NOT NULL,
  [IsPrivate] [bit] NOT NULL,
  [ActText] [varchar](500) NULL,
  [FaceImg] [varchar](100) NULL,
  [SenderColor] [varchar](50) NULL,
  [ReceiverColor] [varchar](50) NULL,
 CONSTRAINT [PK_G_ChatMessage] PRIMARY KEY CLUSTERED 
(
  [ChatMessageId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_G_ChatMessage_RoomId] ON [dbo].[G_ChatMessage] 
(
  [RoomId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UnitNewsNotice]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[UnitNewsNotice](
  [UnitNewsId] [int] IDENTITY(1,1) NOT NULL,
  [Title] [nvarchar](255) NOT NULL,
  [CreateUserId] [int] NOT NULL,
  [OrganizationUnitId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [ViewCount] [int] NOT NULL,
  [Content] [ntext] NOT NULL,
  [Picture] [varchar](512) NULL,
  [ItemType] [int] NOT NULL,
 CONSTRAINT [PK_UnitNewsNotice] PRIMARY KEY CLUSTERED 
(
  [UnitNewsId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[B_Video]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[B_Video](
  [VideoId] [int] IDENTITY(1,1) NOT NULL,
  [ObjectUuid] [varchar](40) NOT NULL,
  [UserId] [int] NOT NULL,
  [V_Title] [nvarchar](256) NOT NULL,
  [V_Tags] [nvarchar](256) NULL,
  [CategoryId] [int] NULL,
  [V_Staple] [varchar](10) NULL,
  [V_Summary] [ntext] NULL,
  [V_AddIP] [varchar](50) NULL,
  [V_CreateDate] [datetime] NOT NULL,
  [V_LastModified] [datetime] NOT NULL,
  [V_ViewCount] [int] NOT NULL,
  [V_CommentCount] [int] NOT NULL,
  [V_DownloadCount] [int] NOT NULL,
  [V_Href] [nvarchar](256) NULL,
  [V_FlvHref] [nvarchar](256) NULL,
  [V_FlvThumbNailHref] [nvarchar](256) NULL,
  [V_DeleteState] [bit] NOT NULL,
  [V_AuditState] [bit] NOT NULL,
  [V_TypeState] [bit] NOT NULL,
  [UnitId] [int] NULL,
  [gradeId] [int] NULL,
  [subjectId] [int] NOT NULL,
  [SpecialSubjectId] [int] NULL,
  [UserCateId] [int] NULL,
  [Status] [smallint] NOT NULL,
 CONSTRAINT [PK_B_Video] PRIMARY KEY CLUSTERED 
(
  [VideoId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_B_Video_CategoryId] ON [dbo].[B_Video] 
(
  [CategoryId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_B_Video_UnitId] ON [dbo].[B_Video] 
(
  [UnitId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_B_Video_UserId] ON [dbo].[B_Video] 
(
  [UserId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[C_PrepareCoursePlan]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[C_PrepareCoursePlan](
  [PrepareCoursePlanId] [int] IDENTITY(1,1) NOT NULL,
  [Title] [nvarchar](255) NOT NULL,
  [StartDate] [datetime] NOT NULL,
  [EndDate] [datetime] NOT NULL,
  [GroupId] [int] NOT NULL,
  [GradeId] [int] NOT NULL,
  [SubjectId] [int] NOT NULL,
  [PlanContent] [ntext] NULL,
  [DefaultPlan] [bit] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [CreateUserId] [int] NOT NULL,
 CONSTRAINT [PK_C_PrepareCoursePlan] PRIMARY KEY CLUSTERED 
(
  [PrepareCoursePlanId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_C_PrepareCoursePlan_GroupId] ON [dbo].[C_PrepareCoursePlan] 
(
  [GroupId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[G_GroupKTUser]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[G_GroupKTUser](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [GroupId] [int] NOT NULL,
  [TeacherId] [int] NOT NULL,
  [TeacherName] [nvarchar](50) NOT NULL,
  [TeacherGender] [nvarchar](10) NOT NULL,
  [TeacherUnit] [nvarchar](100) NULL,
  [TeacherXZZW] [nvarchar](100) NULL,
  [TeacherZYZW] [nvarchar](100) NULL,
  [TeacherXL] [nvarchar](100) NULL,
  [TeacherXW] [nvarchar](100) NULL,
  [TeacherYJZC] [ntext] NULL,
  [TeacherXKXDId] [varchar](1024) NULL,
  [TeacherXKXDName] [nvarchar](1024) NULL,
 CONSTRAINT [PK_G_GroupKTUser] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_G_GroupKTUser_GroupId] ON [dbo].[G_GroupKTUser] 
(
  [GroupId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[EvaluationPlanTemplate]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[EvaluationPlanTemplate](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [EvaluationPlanId] [int] NOT NULL,
  [EvaluationTemplateId] [int] NOT NULL,
 CONSTRAINT [PK_EvaluationPlanTemplate] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_EvaluationPlanTemplate_EvaluationPlanId] ON [dbo].[EvaluationPlanTemplate] 
(
  [EvaluationPlanId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MetaSubject]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MetaSubject](
  [MsubjId] [int] IDENTITY(1,1) NOT NULL,
  [MsubjName] [nvarchar](32) NOT NULL,
  [MsubjCode] [nvarchar](32) NOT NULL,
  [OrderNum] [int] NOT NULL,
 CONSTRAINT [PK_MetaSubject] PRIMARY KEY CLUSTERED 
(
  [MsubjId] ASC
) ON [PRIMARY],
 CONSTRAINT [IX_MetaSubject] UNIQUE NONCLUSTERED 
(
  [MsubjName] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'元学科标识' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'MetaSubject', @level2type=N'COLUMN',@level2name=N'MsubjId'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'元学科名字, 如 ''语文'', ''数学''' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'MetaSubject', @level2type=N'COLUMN',@level2name=N'MsubjName'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'元学科标准代码, 当前可能不使用.' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'MetaSubject', @level2type=N'COLUMN',@level2name=N'MsubjCode'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'排序' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'MetaSubject', @level2type=N'COLUMN',@level2name=N'OrderNum'
GO
/****** Object:  Table [dbo].[Jtr_GroupResource]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Jtr_GroupResource](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [GroupId] [int] NOT NULL,
  [ResourceId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [GroupCateId] [int] NULL,
  [PubDate] [datetime] NOT NULL,
  [isGroupBest] [bit] NOT NULL,
 CONSTRAINT [PK_Jtr_GroupResource] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jtr_GroupResource_GroupId] ON [dbo].[Jtr_GroupResource] 
(
  [GroupId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[B_Staple]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[B_Staple](
  [StapleId] [int] IDENTITY(1,1) NOT NULL,
  [UserId] [int] NOT NULL,
  [StapleName] [nvarchar](50) NOT NULL,
  [OrderNum] [int] NOT NULL,
  [StapleType] [int] NOT NULL,
  [Invisible] [bit] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [BlogNum] [int] NOT NULL,
  [Views] [int] NOT NULL,
 CONSTRAINT [PK_B_Staple] PRIMARY KEY CLUSTERED 
(
  [StapleId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_B_Staple_UserId] ON [dbo].[B_Staple] 
(
  [UserId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[G_GroupRelation]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[G_GroupRelation](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [SrcGroup] [int] NOT NULL,
  [Relation] [varchar](32) NOT NULL,
  [DstGroup] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [SrcAudit] [bit] NOT NULL,
  [DstAudit] [bit] NOT NULL,
  [SrcDelete] [bit] NOT NULL,
  [DstDelete] [bit] NOT NULL,
  [Attribute] [ntext] NULL,
 CONSTRAINT [PK_G_GroupRelation] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_G_GroupRelation_SrcGroup] ON [dbo].[G_GroupRelation] 
(
  [SrcGroup] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[U_GroupPower]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[U_GroupPower](
  [GroupPowerId] [int] IDENTITY(1,1) NOT NULL,
  [GroupId] [int] NULL,
  [UploadArticleNum] [int] NULL,
  [UploadResourceNum] [int] NULL,
  [UploadDiskNum] [int] NULL,
 CONSTRAINT [PK_U_GroupPower] PRIMARY KEY CLUSTERED 
(
  [GroupPowerId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_U_GroupPower_GroupId] ON [dbo].[U_GroupPower] 
(
  [GroupId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ContentSpaceArticle]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ContentSpaceArticle](
  [ContentSpaceArticleId] [int] IDENTITY(1,1) NOT NULL,
  [Title] [nvarchar](50) NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [CreateUserId] [int] NOT NULL,
  [CreateUserLoginName] [varchar](50) NOT NULL,
  [ContentSpaceId] [int] NOT NULL,
  [Content] [ntext] NULL,
  [PictureUrl] [varchar](255) NULL,
  [OwnerType] [int] NOT NULL,
  [OwnerId] [int] NOT NULL,
  [ViewCount] [int] NOT NULL,
 CONSTRAINT [PK_ContentSpaceArticle] PRIMARY KEY CLUSTERED 
(
  [ContentSpaceArticleId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_ContentSpaceArticle_ContentSpaceId] ON [dbo].[ContentSpaceArticle] 
(
  [ContentSpaceId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_ContentSpaceArticle_Owner] ON [dbo].[ContentSpaceArticle] 
(
  [OwnerId] ASC,
  [OwnerType] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Jitar_UnitType]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Jitar_UnitType](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [UnitTypeGuid] [uniqueidentifier] NOT NULL,
  [UnitTypeName] [nvarchar](50) NOT NULL,
  [OrderNo] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Jitar_Praise]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Jitar_Praise](
  [PraiseId] [int] IDENTITY(1,1) NOT NULL,
  [ObjType] [int] NOT NULL,
  [ObjId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
 CONSTRAINT [PK_Jitar_Praise] PRIMARY KEY CLUSTERED 
(
  [PraiseId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_Praise_ObjIdType] ON [dbo].[Jitar_Praise] 
(
  [ObjId] ASC,
  [ObjType] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_Praise_UserId] ON [dbo].[Jitar_Praise] 
(
  [UserId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[U_GroupCondition]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[U_GroupCondition](
  [ConditionId] [int] IDENTITY(1,1) NOT NULL,
  [TableName] [varchar](50) NULL,
 CONSTRAINT [PK_U_GroupCondition] PRIMARY KEY CLUSTERED 
(
  [ConditionId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Jitar_UnitSubject]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Jitar_UnitSubject](
  [UnitSubjectId] [int] IDENTITY(1,1) NOT NULL,
  [UnitId] [int] NOT NULL,
  [SubjectId] [int] NOT NULL,
  [MetaSubjectId] [int] NOT NULL,
  [MetaGradeId] [int] NOT NULL,
  [DisplayName] [nvarchar](256) NOT NULL,
 CONSTRAINT [PK_Jitar_UnitSubject] PRIMARY KEY CLUSTERED 
(
  [UnitSubjectId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_UnitSubject_UnitId] ON [dbo].[Jitar_UnitSubject] 
(
  [UnitId] ASC
) ON [PRIMARY]
GO
/****** Object:  StoredProcedure [dbo].[CopyData]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS OFF
GO
SET QUOTED_IDENTIFIER OFF
GO
CREATE PROCEDURE [dbo].[CopyData]
@Year int --要创建的表格的年份，如果为0，则表示当前年份
As
-- 要操作的表的名称
declare @TableName varchar(50)

-- SQL 语句
declare @SQL varchar(256)

If @Year = 0 
Set @Year = Year(getdate())

-- 根据年份创建表
Set @TableName = 'HtmlArticle' + RTrim(Ltrim(str(@Year)))

-- 先删除表
SET @SQL = "if exists (select * from dbo.sysobjects where id = object_id('" + @TableName + "')  and OBJECTPROPERTY(id,N'IsUserTable') = 1)  drop table " + @TableName
Execute( @SQL)

-- 创建表并插入数据
SELECT @SQL = 'Select *  Into ' + @TableName + ' from Jitar_Article Where AuditState = 0 And DraftState = 0 And DelState = 0 And Year(createdate) = ' + Str( @Year)
exec(@SQL)

--创建索引、主键
SELECT @SQL = 'ALTER TABLE ' + @TableName + ' WITH NOCHECK ADD  CONSTRAINT PK_' + @TableName + '_ArticleId PRIMARY KEY CLUSTERED (ArticleId) '
exec(@SQL)

-- 创建索引
Select @SQL = 'CREATE  NONCLUSTERED  INDEX [IX_' + @TableName + '_SubjectId] ON [' + @TableName + ']([SubjectId]) ON [PRIMARY]'
exec(@SQL)

Select @SQL = 'CREATE  NONCLUSTERED  INDEX [IX_' + @TableName + '_UserId] ON [' + @TableName + ']([UserId]) ON [PRIMARY]'
exec(@SQL)

Select @SQL = 'CREATE  NONCLUSTERED  INDEX [IX_' + @TableName + '_UnitId] ON [' + @TableName + ']([UnitId]) ON [PRIMARY]'
exec(@SQL)

Select @SQL = 'CREATE  NONCLUSTERED  INDEX [IX_' + @TableName + '_SysCateId] ON [' + @TableName + ']([SysCateId]) ON [PRIMARY]'
exec(@SQL)

Select @SQL = 'CREATE UNIQUE  NONCLUSTERED  INDEX [IX_' + @TableName + '_ObjectUuid] ON [' + @TableName + ']([ObjectUuid]) ON [PRIMARY]'
exec(@SQL)

-- 得到本年度的文章数量
DECLARE @COUNT int
SET @COUNT = 0
DECLARE @COUNTSQL NVARCHAR(256)
SET @COUNTSQL = N'SELECT @count = COUNT(*) FROM ' + @tableName
EXECUTE sp_executesql @COUNTSQL,N'@count int OUTPUT',@COUNT OUTPUT
-- 插入到历史备份列表中
SET @SQL = 'INSERT INTO Jitar_BackYear(BackYear,BackYearType,BackYearCount) Values(' +  RTrim(Ltrim(str(@Year))) + ',''article'',' +  RTrim(Ltrim(str(@COUNT))) + ')'
exec(@SQL)

-- 删除已经备份的数据
SELECT @SQL = 'DELETE FROM Jitar_Article Where Year(createdate) = ' + Str(@Year)
exec(@SQL)

SELECT @SQL = 'UPDATE HtmlArticleBase SET TableName = ''' +  @tableName + '''  Where Year(createdate) = ' + Str(@Year)
exec(@SQL)
GO
/****** Object:  Table [dbo].[ContentSpace]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ContentSpace](
  [ContentSpaceId] [int] IDENTITY(1,1) NOT NULL,
  [SpaceName] [nvarchar](255) NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [CreateUserId] [int] NOT NULL,
  [OwnerType] [int] NOT NULL,
  [OwnerId] [int] NOT NULL,
  [ArticleCount] [int] NOT NULL,
  [parentId] [int] NULL,
  [ParentPath] [varchar](2048) NOT NULL,
 CONSTRAINT [PK_ContentSpace] PRIMARY KEY CLUSTERED 
(
  [ContentSpaceId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_ContentSpace_Owner] ON [dbo].[ContentSpace] 
(
  [OwnerId] ASC,
  [OwnerType] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[G_Link]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[G_Link](
  [LinkId] [int] IDENTITY(1,1) NOT NULL,
  [ObjectType] [int] NOT NULL,
  [ObjectId] [int] NOT NULL,
  [LinkName] [nvarchar](128) NOT NULL,
  [LinkAddress] [nvarchar](256) NOT NULL,
  [Description] [nvarchar](128) NULL,
  [LinkType] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [LinkIcon] [nvarchar](128) NOT NULL,
 CONSTRAINT [PK_G_Link] PRIMARY KEY CLUSTERED 
(
  [LinkId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_G_Link_Obj] ON [dbo].[G_Link] 
(
  [ObjectId] ASC,
  [ObjectType] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[U_Group]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[U_Group](
  [GroupId] [int] IDENTITY(1,1) NOT NULL,
  [GroupName] [varchar](500) NULL,
  [GroupInfo] [varchar](5000) NULL,
 CONSTRAINT [PK_U_Group] PRIMARY KEY CLUSTERED 
(
  [GroupId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[S_SpecialSubjectTopic]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[S_SpecialSubjectTopic](
  [SpecialSubjectTopicId] [int] IDENTITY(1,1) NOT NULL,
  [TopicId] [int] NOT NULL,
  [TopicTitle] [nvarchar](255) NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [SpecialSubjectId] [int] NOT NULL,
 CONSTRAINT [PK_S_SpecialSubjectTopic] PRIMARY KEY CLUSTERED 
(
  [SpecialSubjectTopicId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_S_SpecialSubjectTopic_SpecialSubjectId] ON [dbo].[S_SpecialSubjectTopic] 
(
  [SpecialSubjectId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Jitar_UnitCount]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Jitar_UnitCount](
  [CountId] [int] IDENTITY(1,1) NOT NULL,
  [UnitId] [int] NOT NULL,
  [SelfHistoryArticleCount] [int] NOT NULL,
  [ChildHistoryArticleCount] [int] NOT NULL,
  [SelfArticleCount] [int] NOT NULL,
  [ChildArticleCount] [int] NOT NULL,
  [SelfResourceCount] [int] NOT NULL,
  [SelfPhotoCount] [int] NOT NULL,
  [SelfVideoCount] [int] NOT NULL,
  [SelfUserCount] [int] NOT NULL,
  [ChildResourceCount] [int] NOT NULL,
  [ChildPhotoCount] [int] NOT NULL,
  [ChildVideoCount] [int] NOT NULL,
  [ChildUserCount] [int] NOT NULL,
 CONSTRAINT [PK_Jitar_UnitCount] PRIMARY KEY CLUSTERED 
(
  [CountId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_UnitCount_UnitId] ON [dbo].[Jitar_UnitCount] 
(
  [UnitId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[S_SpecialSubjectPhoto]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[S_SpecialSubjectPhoto](
  [SpecialSubjectPhotoId] [int] IDENTITY(1,1) NOT NULL,
  [PhotoId] [int] NOT NULL,
  [PhotoTitle] [nvarchar](255) NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [SpecialSubjectId] [int] NOT NULL,
  [PhotoUserName] [nvarchar](50) NOT NULL,
  [PhotoUserId] [int] NOT NULL,
 CONSTRAINT [PK_S_SpecialSubjectPhoto] PRIMARY KEY CLUSTERED 
(
  [SpecialSubjectPhotoId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_S_SpecialSubjectPhoto_SpecialSubjectId] ON [dbo].[S_SpecialSubjectPhoto] 
(
  [SpecialSubjectId] ASC
) ON [PRIMARY]
GO
/****** Object:  StoredProcedure [dbo].[GetIPAddressList]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--ALTER PROCEDURE [GetIPAddressList]

CREATE PROCEDURE [dbo].[GetIPAddressList]
@IPlist varchar(8000)
AS
BEGIN
  CREATE TABLE #T (ip nvarchar(15))
  Declare @sql varchar(8000)
  SET @sql = @IPlist
  SET @sql='Insert into #T select '''+ replace(@IPlist,',',''' union all select ''')+''''
  EXEC(@sql)
  DECLARE @IP varchar(15)
  DECLARE @searchaIP as bigint
  DECLARE IP_Cursor CURSOR FOR 
  SELECT ip FROM #T
  OPEN IP_Cursor
  FETCH NEXT FROM IP_Cursor INTO @IP
  WHILE @@FETCH_STATUS = 0
  BEGIN
    SELECT @searchaIP=CAST(PARSENAME(@IP,4) as BigInt)*256*256*256+CAST(PARSENAME(@IP,3) as BigInt)*256*256+CAST(PARSENAME(@IP,2) as BigInt)*256+ CAST(PARSENAME(@IP,1) as BigInt) 
    SET @sql= @sql + 'select ''' + @IP + ''' as 查询IP, * from [czipbk] Where IPbegin<=' + str(@searchaIP) + ' And IPend>'+ str(@searchaIP) +' union all '
    FETCH NEXT FROM IP_Cursor INTO @IP
  END
  CLOSE IP_Cursor
  DEALLOCATE IP_Cursor
  SET @sql = @sql + 'select top 0 '''' as 查询IP, * from [czipbk]'
  EXEC(@sql)
END
GO
/****** Object:  Table [dbo].[Jitar_ChannelUnitStat]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jitar_ChannelUnitStat](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [ChannelId] [int] NOT NULL,
  [UnitId] [int] NOT NULL,
  [UnitTitle] [nvarchar](256) NULL,
  [UserCount] [int] NOT NULL,
  [ArticleCount] [int] NOT NULL,
  [ResourceCount] [int] NOT NULL,
  [PhotoCount] [int] NOT NULL,
  [VideoCount] [int] NOT NULL,
  [StatGuid] [varchar](50) NOT NULL,
  [CreateDate] [datetime] NOT NULL,
 CONSTRAINT [PK_Jitar_ChannelUnitStat] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_ChannelUnitStat_ChannelId] ON [dbo].[Jitar_ChannelUnitStat] 
(
  [ChannelId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[S_Grade]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[S_Grade](
  [GradeId] [int] NOT NULL,
  [GradeName] [nvarchar](32) NOT NULL,
  [IsGrade] [bit] NOT NULL,
  [ParentId] [int] NULL,
 CONSTRAINT [PK_S_Grade] PRIMARY KEY CLUSTERED 
(
  [GradeId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SiteNews]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SiteNews](
  [NewsId] [int] IDENTITY(1,1) NOT NULL,
  [SubjectId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [Title] [nvarchar](255) NOT NULL,
  [Content] [ntext] NULL,
  [Picture] [nvarchar](255) NULL,
  [Status] [int] NOT NULL,
  [NewsType] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [ViewCount] [int] NOT NULL,
 CONSTRAINT [PK_SiteNews] PRIMARY KEY CLUSTERED 
(
  [NewsId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_SiteNews_NewsType] ON [dbo].[SiteNews] 
(
  [NewsType] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_SiteNews_SubjectId] ON [dbo].[SiteNews] 
(
  [SubjectId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UnitNews]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[UnitNews](
  [UnitNewsId] [int] IDENTITY(1,1) NOT NULL,
  [Title] [nvarchar](255) NOT NULL,
  [CreateUserId] [int] NOT NULL,
  [UnitId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [ViewCount] [int] NOT NULL,
  [Content] [ntext] NOT NULL,
  [Picture] [varchar](512) NULL,
  [ItemType] [int] NOT NULL,
 CONSTRAINT [PK_UnitNews] PRIMARY KEY CLUSTERED 
(
  [UnitNewsId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_UnitNews_UnitId] ON [dbo].[UnitNews] 
(
  [UnitId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Jitar_ChannelResource]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jitar_ChannelResource](
  [ChannelResourceId] [int] IDENTITY(1,1) NOT NULL,
  [ChannelId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [ResourceId] [int] NOT NULL,
  [ChannelCate] [varchar](256) NULL,
  [ChannelCateId] [int] NULL,
  [UnitId] [int] NOT NULL,
  [ViewCount] [int] NOT NULL,
 CONSTRAINT [PK_Jitar_ChannelResource] PRIMARY KEY CLUSTERED 
(
  [ChannelResourceId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_ChannelResource_ChannelCateId] ON [dbo].[Jitar_ChannelResource] 
(
  [ChannelCateId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_ChannelResource_ChannelId] ON [dbo].[Jitar_ChannelResource] 
(
  [ChannelId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[S_Config]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[S_Config](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [ItemType] [varchar](32) NOT NULL,
  [Name] [varchar](256) NOT NULL,
  [Value] [ntext] NULL,
  [Type] [varchar](32) NOT NULL,
  [DefVal] [ntext] NULL,
  [Title] [nvarchar](256) NULL,
  [Description] [ntext] NULL,
 CONSTRAINT [PK_S_Config] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[SiteNav]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SiteNav](
  [SiteNavId] [int] IDENTITY(1,1) NOT NULL,
  [SiteNavName] [nvarchar](50) NOT NULL,
  [SiteNavUrl] [varchar](512) NOT NULL,
  [CurrentNav] [varchar](50) NULL,
  [SiteNavIsShow] [bit] NOT NULL,
  [SiteNavItemOrder] [int] NOT NULL,
  [IsExternalLink] [bit] NOT NULL,
  [OwnerType] [int] NOT NULL,
  [OwnerId] [int] NOT NULL,
 CONSTRAINT [PK_SiteNav] PRIMARY KEY CLUSTERED 
(
  [SiteNavId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_SiteNav_Obj] ON [dbo].[SiteNav] 
(
  [OwnerId] ASC,
  [OwnerType] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UnitLinks]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[UnitLinks](
  [LinkId] [int] IDENTITY(1,1) NOT NULL,
  [UnitId] [int] NOT NULL,
  [LinkName] [nvarchar](100) NOT NULL,
  [LinkAddress] [varchar](512) NOT NULL,
  [LinkIcon] [varchar](512) NULL,
 CONSTRAINT [PK_UnitLinks] PRIMARY KEY CLUSTERED 
(
  [LinkId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_UnitLinks_UnitId] ON [dbo].[UnitLinks] 
(
  [UnitId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Jitar_ChannelUser]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Jitar_ChannelUser](
  [ChannelUserId] [int] IDENTITY(1,1) NOT NULL,
  [ChannelId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [UnitId] [int] NOT NULL,
  [UnitTitle] [nvarchar](50) NULL,
 CONSTRAINT [PK_Jitar_ChannelUser] PRIMARY KEY CLUSTERED 
(
  [ChannelUserId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_ChannelUser_ChannelId] ON [dbo].[Jitar_ChannelUser] 
(
  [ChannelId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[S_Calendar]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[S_Calendar](
  [ID] [int] IDENTITY(1,1) NOT NULL,
  [ObjectGuid] [varchar](40) NOT NULL,
  [ObjectType] [varchar](100) NOT NULL,
  [EventTimeBegin] [datetime] NULL,
  [EventTimeEnd] [datetime] NULL,
  [Title] [nvarchar](1000) NULL,
  [Url] [varchar](1000) NULL,
  [CreateTime] [datetime] NOT NULL,
 CONSTRAINT [PK_S_Calendar] PRIMARY KEY CLUSTERED 
(
  [ID] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  StoredProcedure [dbo].[Delete_Table_Column_Constraint]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:    <Author,,Name>
-- Create date: <Create Date,,>
-- Description: 删除字段的约束，以便进行DROP 字段
-- =============================================
CREATE PROCEDURE [dbo].[Delete_Table_Column_Constraint] 
@TableName NVARCHAR(256),
@ColumnName NVARCHAR(256)
AS
BEGIN
  DECLARE @TCC VARCHAR(256)
  DECLARE @SQL VARCHAR(2000)
  DECLARE Column_Cursor CURSOR FOR
  SELECT b.name AS CC FROM syscolumns a, sysobjects b WHERE a.id=object_id(@TableName) AND b.id=a.cdefault AND a.name=@ColumnName AND b.name LIKE 'DF_%'
  OPEN Column_Cursor
  FETCH NEXT FROM Column_Cursor INTO @TCC
  WHILE @@FETCH_STATUS = 0
    BEGIN
      SET @SQL = 'ALTER TABLE ' + @TableName + ' DROP CONSTRAINT ' + @TCC
      EXEC(@SQL)
      SET @SQL = 'ALTER TABLE ' + @TableName + ' DROP COLUMN ' + @ColumnName  
      EXEC(@SQL)
      FETCH NEXT FROM Column_Cursor INTO @TCC
    END
    CLOSE Column_Cursor
    DEALLOCATE Column_Cursor
END
GO
/****** Object:  Table [dbo].[S_SubjectWebpart]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[S_SubjectWebpart](
  [SubjectWebpartId] [int] IDENTITY(1,1) NOT NULL,
  [ModuleName] [nvarchar](255) NOT NULL,
  [DisplayName] [nvarchar](255) NOT NULL,
  [SystemModule] [bit] NOT NULL,
  [SubjectId] [int] NOT NULL,
  [WebpartZone] [int] NOT NULL,
  [RowIndex] [int] NOT NULL,
  [Content] [ntext] NULL,
  [Visible] [bit] NOT NULL,
  [SysCateId] [int] NULL,
  [ShowCount] [int] NULL,
  [PartType] [int] NOT NULL,
  [ShowType] [int] NOT NULL,
 CONSTRAINT [PK_S_SubjectWebpart] PRIMARY KEY CLUSTERED 
(
  [SubjectWebpartId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_S_SubjectWebpart_SubjectId] ON [dbo].[S_SubjectWebpart] 
(
  [SubjectId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Jitar_ChannelPhoto]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jitar_ChannelPhoto](
  [ChannelPhotoId] [int] IDENTITY(1,1) NOT NULL,
  [ChannelId] [int] NOT NULL,
  [PhotoId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [UnitId] [int] NOT NULL,
  [ViewCount] [int] NOT NULL,
  [ChannelCate] [varchar](256) NULL,
  [ChannelCateId] [int] NULL,
 CONSTRAINT [PK_Jitar_ChannelPhoto] PRIMARY KEY CLUSTERED 
(
  [ChannelPhotoId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_ChannelPhoto_ChannelCateId] ON [dbo].[Jitar_ChannelPhoto] 
(
  [ChannelCateId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_ChannelPhoto_ChannelId] ON [dbo].[Jitar_ChannelPhoto] 
(
  [ChannelId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[P_VoteUser]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[P_VoteUser](
  [VoteUserId] [int] IDENTITY(1,1) NOT NULL,
  [VoteId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [AddIp] [varchar](50) NULL,
 CONSTRAINT [PK_P_VoteUser] PRIMARY KEY CLUSTERED 
(
  [VoteUserId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_P_VoteUser_UserId] ON [dbo].[P_VoteUser] 
(
  [UserId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_P_VoteUser_VoteId] ON [dbo].[P_VoteUser] 
(
  [VoteId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[B_PhotoStaple]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[B_PhotoStaple](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [UserId] [int] NOT NULL,
  [OrderNum] [int] NOT NULL,
  [Title] [nvarchar](50) NOT NULL,
  [StapleDescribe] [nvarchar](500) NULL,
  [IsHide] [bit] NOT NULL,
  [parentId] [int] NULL,
  [ParentPath] [varchar](2048) NOT NULL,
 CONSTRAINT [PK_B_PhotoStaple] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_B_PhotoStaple_UserId] ON [dbo].[B_PhotoStaple] 
(
  [UserId] ASC
) ON [PRIMARY]
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'唯一标识' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'B_PhotoStaple', @level2type=N'COLUMN',@level2name=N'Id'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'分类标题' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'B_PhotoStaple', @level2type=N'COLUMN',@level2name=N'Title'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'是否隐藏(0:不隐藏,1:隐藏)' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'B_PhotoStaple', @level2type=N'COLUMN',@level2name=N'IsHide'
GO
/****** Object:  Table [dbo].[UnitWebpart]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UnitWebpart](
  [UnitWebpartId] [int] IDENTITY(1,1) NOT NULL,
  [ModuleName] [nvarchar](256) NOT NULL,
  [DisplayName] [nvarchar](256) NOT NULL,
  [SystemModule] [bit] NOT NULL,
  [UnitId] [int] NOT NULL,
  [WebpartZone] [int] NOT NULL,
  [RowIndex] [int] NOT NULL,
  [Content] [ntext] NULL,
  [Visible] [bit] NOT NULL,
  [PartType] [int] NOT NULL,
  [ShowType] [int] NOT NULL,
  [CateId] [int] NULL,
  [ShowCount] [int] NOT NULL,
 CONSTRAINT [PK_UnitWebpart] PRIMARY KEY CLUSTERED 
(
  [UnitWebpartId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_UnitWebpart_UnitId] ON [dbo].[UnitWebpart] 
(
  [UnitId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SiteLinks]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SiteLinks](
  [LinkId] [int] IDENTITY(1,1) NOT NULL,
  [ObjectType] [varchar](50) NOT NULL,
  [ObjectId] [int] NOT NULL,
  [LinkHref] [varchar](255) NOT NULL,
  [LinkTitle] [nvarchar](50) NOT NULL,
  [LinkIcon] [varchar](255) NULL,
 CONSTRAINT [PK_SiteLinks] PRIMARY KEY CLUSTERED 
(
  [LinkId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[P_VoteResult]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[P_VoteResult](
  [VoteResultId] [int] IDENTITY(1,1) NOT NULL,
  [VoteQuestionAnswerId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [UserId] [int] NOT NULL,
 CONSTRAINT [PK_P_VoteResult] PRIMARY KEY CLUSTERED 
(
  [VoteResultId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_P_VoteResult_QuestionAnswerId] ON [dbo].[P_VoteResult] 
(
  [VoteQuestionAnswerId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Jitar_ChannelModule]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jitar_ChannelModule](
  [ModuleId] [int] IDENTITY(1,1) NOT NULL,
  [DisplayName] [nvarchar](256) NOT NULL,
  [ChannelId] [int] NOT NULL,
  [ModuleType] [varchar](50) NOT NULL,
  [Content] [ntext] NULL,
  [ListItemCount] [int] NULL,
  [ContentTemplate] [ntext] NULL,
  [PageType] [varchar](50) NOT NULL,
  [ShowCount] [int] NULL,
  [Template] [ntext] NULL,
  [CateItemType] [varchar](50) NULL,
  [CateId] [int] NULL,
  [UnitType] [nvarchar](50) NULL,
 CONSTRAINT [PK_FamousChannelModule] PRIMARY KEY CLUSTERED 
(
  [ModuleId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_ChannelModule_ChannelId] ON [dbo].[Jitar_ChannelModule] 
(
  [ChannelId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[P_VoteQuestionAnswer]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[P_VoteQuestionAnswer](
  [VoteQuestionAnswerId] [int] IDENTITY(1,1) NOT NULL,
  [AnswerContent] [nvarchar](1000) NOT NULL,
  [VoteQuestionId] [int] NOT NULL,
  [ItemIndex] [int] NOT NULL,
  [VoteCount] [int] NOT NULL,
 CONSTRAINT [PK_P_VoteQuestionAnswer] PRIMARY KEY CLUSTERED 
(
  [VoteQuestionAnswerId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_P_VoteQuestionAnswer_VoteQuestionId] ON [dbo].[P_VoteQuestionAnswer] 
(
  [VoteQuestionId] ASC
) ON [PRIMARY]
GO

/******** SiteIndexPart  ****/

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SiteIndexPart](
  [SiteIndexPartId] [int] IDENTITY(1,1) NOT NULL,
  [ModuleName] [nvarchar](50) NOT NULL,
  [ModuleZone] [int] NOT NULL,
  [ModuleOrder] [int] NOT NULL,
  [ModuleDisplay] [int] NOT NULL,
  [ShowBorder] [int] NOT NULL,
  [ModuleHeight] [int] NOT NULL,
  [ModuleWidth] [int] NOT NULL,
  [TextLength] [int] NOT NULL,
  [MultiColumn] [smallint] NOT NULL,
  [Content] [ntext] NULL,
  [SysCateId] [int] NULL,
  [ShowCount] [int] NULL,
  [PartType] [int] NOT NULL,
  [ShowType] [int] NOT NULL,
 CONSTRAINT [PK_SiteIndexPart] PRIMARY KEY CLUSTERED 
(
  [SiteIndexPartId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[SiteIndexPart] ADD  CONSTRAINT [DF_SiteIndexPart_ModuleZone]  DEFAULT (6) FOR [ModuleZone]
GO
ALTER TABLE [dbo].[SiteIndexPart] ADD  CONSTRAINT [DF_SiteIndexPart_ModuleOrder]  DEFAULT (0) FOR [ModuleOrder]
GO
ALTER TABLE [dbo].[SiteIndexPart] ADD  CONSTRAINT [DF_SiteIndexPart_ModuleDisplay]  DEFAULT (1) FOR [ModuleDisplay]
GO
ALTER TABLE [dbo].[SiteIndexPart] ADD  CONSTRAINT [DF_SiteIndexPart_ShowBorder]  DEFAULT (1) FOR [ShowBorder]
GO
ALTER TABLE [dbo].[SiteIndexPart] ADD  CONSTRAINT [DF_SiteIndexPart_ModuleHeight]  DEFAULT (160) FOR [ModuleHeight]
GO
ALTER TABLE [dbo].[SiteIndexPart] ADD  CONSTRAINT [DF_SiteIndexPart_ModuleWidth]  DEFAULT (300) FOR [ModuleWidth]
GO
ALTER TABLE [dbo].[SiteIndexPart] ADD  CONSTRAINT [DF_SiteIndexPart_TextLength]  DEFAULT (0) FOR [TextLength]
GO
ALTER TABLE [dbo].[SiteIndexPart] ADD  CONSTRAINT [DF_SiteIndexPart_MultiColumn]  DEFAULT (1) FOR [MultiColumn]
GO
ALTER TABLE [dbo].[SiteIndexPart] ADD  CONSTRAINT [DF_SiteIndexPart_ShowCount]  DEFAULT (8) FOR [ShowCount]
GO
ALTER TABLE [dbo].[SiteIndexPart] ADD  CONSTRAINT [DF_SiteIndexPart_PartType_1]  DEFAULT (0) FOR [PartType]
GO
ALTER TABLE [dbo].[SiteIndexPart] ADD  CONSTRAINT [DF_SiteIndexPart_ShowType_1]  DEFAULT (0) FOR [ShowType]
GO




/****** Object:  Table [dbo].[P_VoteQuestion]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[P_VoteQuestion](
  [VoteQuestionId] [int] IDENTITY(1,1) NOT NULL,
  [Title] [nvarchar](2000) NOT NULL,
  [QuestionType] [bit] NOT NULL,
  [MaxSelectCount] [int] NOT NULL,
  [VoteId] [int] NOT NULL,
  [ItemIndex] [int] NOT NULL,
 CONSTRAINT [PK_P_VoteQuestion] PRIMARY KEY CLUSTERED 
(
  [VoteQuestionId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_P_VoteQuestion_VoteId] ON [dbo].[P_VoteQuestion] 
(
  [VoteId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Jitar_ChannelArticle]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS OFF
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jitar_ChannelArticle](
  [ChannelArticleId] [int] IDENTITY(1,1) NOT NULL,
  [ChannelId] [int] NOT NULL,
  [ArticleId] [int] NOT NULL,
  [ArticleGuid] [uniqueidentifier] NOT NULL,
  [UserId] [int] NOT NULL,
  [LoginName] [varchar](50) NOT NULL,
  [UserTrueName] [nvarchar](50) NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [Title] [nvarchar](256) NOT NULL,
  [ChannelCate] [varchar](256) NULL,
  [ArticleState] [smallint] NOT NULL,
  [TypeState] [bit] NOT NULL,
  [ChannelCateId] [int] NULL,
 CONSTRAINT [PK_Jitar_ChannelArticle] PRIMARY KEY CLUSTERED 
(
  [ChannelArticleId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_ChannelArticle_ChannelCateId] ON [dbo].[Jitar_ChannelArticle] 
(
  [ChannelCateId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_ChannelArticle_ChannelId] ON [dbo].[Jitar_ChannelArticle] 
(
  [ChannelId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[P_Vote]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[P_Vote](
  [VoteId] [int] IDENTITY(1,1) NOT NULL,
  [ObjectGuid] [varchar](40) NOT NULL,
  [Title] [nvarchar](256) NOT NULL,
  [Description] [ntext] NULL,
  [CreateDate] [datetime] NOT NULL,
  [CreateUserId] [int] NOT NULL,
  [CreateUserName] [nvarchar](50) NOT NULL,
  [SubjectId] [int] NULL,
  [GradeId] [int] NULL,
  [EndDate] [datetime] NOT NULL,
  [ParentGuid] [varchar](50) NULL,
  [ParentObjectType] [varchar](50) NULL,
 CONSTRAINT [PK_P_Vote] PRIMARY KEY CLUSTERED 
(
  [VoteId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO



/****** Object:  Table [dbo].[S_ViewCount]    Script Date: 09/26/2013 10:27:35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[S_ViewCount](
  [ID] [int] IDENTITY(1,1) NOT NULL,
  [objType] [int] NOT NULL,
  [objId] [int] NOT NULL,
  [viewDate] [datetime] NULL,
  [viewCount] [int] NOT NULL,
  [deled] [int] NOT NULL,
 CONSTRAINT [PK_S_ViewCount] PRIMARY KEY CLUSTERED 
(
  [ID] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_S_ViewCount_Obj] ON [dbo].[S_ViewCount] 
(
  [objId] ASC,
  [objType] ASC
) ON [PRIMARY]
GO
/****** Object:  Default [DF_S_ViewCount_viewDate]    Script Date: 09/26/2013 10:27:35 ******/
ALTER TABLE [dbo].[S_ViewCount] ADD  CONSTRAINT [DF_S_ViewCount_viewDate]  DEFAULT (getdate()) FOR [viewDate]
GO
/****** Object:  Default [DF_S_ViewCount_viewCount]    Script Date: 09/26/2013 10:27:35 ******/
ALTER TABLE [dbo].[S_ViewCount] ADD  CONSTRAINT [DF_S_ViewCount_viewCount]  DEFAULT (0) FOR [viewCount]
GO
/****** Object:  Default [DF_S_ViewCount_deled]    Script Date: 09/26/2013 10:27:35 ******/
ALTER TABLE [dbo].[S_ViewCount] ADD  CONSTRAINT [DF_S_ViewCount_deled]  DEFAULT (0) FOR [deled]
GO



/****** Object:  Table [dbo].[Jitar_UserDeleted]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jitar_UserDeleted](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [UserId] [int] NOT NULL,
  [UserGuid] [varchar](40) NOT NULL,
  [LoginName] [varchar](50) NOT NULL,
  [TrueName] [nvarchar](50) NULL,
  [NickName] [nvarchar](50) NULL,
  [Email] [varchar](255) NULL,
  [CreateDate] [datetime] NULL,
  [VirtualDirectory] [varchar](10) NULL,
  [UserFileFolder] [varchar](10) NULL,
  [Gender] [smallint] NOT NULL,
  [UnitId] [int] NULL,
  [BlogName] [nvarchar](255) NULL,
  [BlogIntroduce] [nvarchar](4000) NULL,
  [UserStatus] [int] NULL,
  [UserGroupID] [int] NULL,
  [VisitCount] [int] NULL,
  [VisitArticleCount] [int] NULL,
  [VisitResourceCount] [int] NULL,
  [ArticleCount] [int] NULL,
  [RecommendArticleCount] [int] NULL,
  [ArticleCommentCount] [int] NULL,
  [ArticleICommentCount] [int] NULL,
  [ResourceCount] [int] NULL,
  [RecommendResourceCount] [int] NULL,
  [ResourceCommentCount] [int] NULL,
  [ResourceICommentCount] [int] NULL,
  [ResourceDownloadCount] [int] NULL,
  [CreateGroupCount] [int] NULL,
  [JionGroupCount] [int] NULL,
  [PhotoCount] [int] NULL,
  [CourseCount] [int] NULL,
  [TopicCount] [int] NULL,
  [CommentCount] [int] NULL,
  [UsedFileSize] [int] NULL,
  [UserIcon] [varchar](255) NULL,
  [UserScore] [int] NULL,
  [UserClassID] [int] NULL,
  [PositionID] [int] NULL,
  [Usn] [int] NULL,
  [SubjectId] [int] NULL,
  [GradeId] [int] NULL,
  [UserTags] [nvarchar](256) NULL,
  [pwd] [varchar](50) NULL,
  [question] [varchar](500) NULL,
  [answer] [varchar](500) NULL,
  [ArticleScore] [int] NULL,
  [ResourceScore] [int] NULL,
  [CommentScore] [int] NULL,
  [MyArticleCount] [int] NULL,
  [OtherArticleCount] [int] NOT NULL,
  [IDcard] [varchar](18) NULL,
  [QQ] [varchar](20) NULL,
  [ArticlePunishScore] [int] NULL,
  [ResourcePunishScore] [int] NULL,
  [CommentPunishScore] [int] NULL,
  [PhotoPunishScore] [int] NULL,
  [videoCount] [int] NULL,
  [photoScore] [int] NULL,
  [videoPunishScore] [int] NULL,
  [videoScore] [int] NULL,
  [PrepareCourseCount] [int] NULL,
  [PushState] [int] NULL,
  [PushUserId] [int] NULL,
  [AccountId] [varchar](50) NULL,
  [UnitPathInfo] [varchar](1024) NULL,
  [UserType] [varchar](256) NULL,
  [HistoryMyArticleCount] [int] NULL,
  [HistoryOtherArticleCount] [int] NULL,
  [version] [int] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Jitar_Channel]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jitar_Channel](
  [ChannelId] [int] IDENTITY(1,1) NOT NULL,
  [ChannelGuid] [varchar](50) NOT NULL,
  [Title] [nvarchar](256) NOT NULL,
  [Skin] [varchar](256) NOT NULL,
  [HeaderTemplate] [ntext] NOT NULL,
  [FooterTemplate] [ntext] NOT NULL,
  [IndexPageTemplate] [ntext] NOT NULL,
  [Logo] [varchar](255) NULL,
  [CssStyle] [ntext] NULL,
  [Template] [varchar](512) NULL,
 CONSTRAINT [PK_FamousChannel] PRIMARY KEY CLUSTERED 
(
  [ChannelId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[P_QuestionAnswer]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[P_QuestionAnswer](
  [AnswerId] [int] IDENTITY(1,1) NOT NULL,
  [QuestionId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [AnswerUserId] [int] NOT NULL,
  [AnswerUserName] [nvarchar](50) NOT NULL,
  [AnswerContent] [ntext] NOT NULL,
  [AddIp] [varchar](50) NULL,
 CONSTRAINT [PK_P_QuestionAnswer] PRIMARY KEY CLUSTERED 
(
  [AnswerId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_P_QuestionAnswer_QuestionId] ON [dbo].[P_QuestionAnswer] 
(
  [QuestionId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[S_VdirMap]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[S_VdirMap](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [Vdir] [varchar](16) NOT NULL,
  [Path] [varchar](256) NOT NULL,
  [Enabled] [bit] NOT NULL,
 CONSTRAINT [PK_S_VdirMap] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'虚拟路径，必须是规范程序标识符，如 ''u'', ''ud1'' 等' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'S_VdirMap', @level2type=N'COLUMN',@level2name=N'Vdir'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'实际物理路径；可以采用多种写法；详细见程序' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'S_VdirMap', @level2type=N'COLUMN',@level2name=N'Path'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'是否启用' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'S_VdirMap', @level2type=N'COLUMN',@level2name=N'Enabled'
GO
/****** Object:  Table [dbo].[P_Question]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[P_Question](
  [QuestionId] [int] IDENTITY(1,1) NOT NULL,
  [ObjectGuid] [varchar](50) NOT NULL,
  [ParentGuid] [varchar](50) NOT NULL,
  [ParentObjectType] [nvarchar](50) NOT NULL,
  [Topic] [nvarchar](255) NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [CreateUserId] [int] NOT NULL,
  [CreateUserName] [nvarchar](50) NOT NULL,
  [QuestionContent] [ntext] NOT NULL,
  [AddIp] [varchar](50) NULL,
 CONSTRAINT [PK_P_Question] PRIMARY KEY CLUSTERED 
(
  [QuestionId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Jitar_BackYear]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jitar_BackYear](
  [BackYearId] [int] IDENTITY(1,1) NOT NULL,
  [BackYear] [int] NOT NULL,
  [BackYearCount] [int] NOT NULL,
  [BackYearType] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Jitar_BackYear] PRIMARY KEY CLUSTERED 
(
  [BackYearId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_BackYear_BackYearType] ON [dbo].[Jitar_BackYear] 
(
  [BackYearType] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Jitar_ViewHistory]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Jitar_ViewHistory](
  [ViewHistoryId] [int] IDENTITY(1,1) NOT NULL,
  [ObjType] [int] NOT NULL,
  [ObjId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
 CONSTRAINT [PK_Jitar_ViewHistory] PRIMARY KEY CLUSTERED 
(
  [ViewHistoryId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_ViewHistory_Obj] ON [dbo].[Jitar_ViewHistory] 
(
  [ObjId] ASC,
  [ObjType] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_ViewHistory_UserId] ON [dbo].[Jitar_ViewHistory] 
(
  [UserId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[S_Tags]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[S_Tags](
  [TagId] [int] IDENTITY(1,1) NOT NULL,
  [TagUuid] [varchar](40) NOT NULL,
  [TagName] [nvarchar](50) NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [RefCount] [int] NOT NULL,
  [ViewCount] [int] NOT NULL,
  [TagType] [smallint] NOT NULL,
  [Disabled] [bit] NOT NULL,
 CONSTRAINT [PK_S_Tags] PRIMARY KEY CLUSTERED 
(
  [TagId] ASC
) ON [PRIMARY],
 CONSTRAINT [IX_S_Tags] UNIQUE NONCLUSTERED 
(
  [TagUuid] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_S_Tags_TagName] ON [dbo].[S_Tags] 
(
  [TagName] ASC
) ON [PRIMARY]
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'创建日期' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'S_Tags', @level2type=N'COLUMN',@level2name=N'CreateDate'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'引用次数。' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'S_Tags', @level2type=N'COLUMN',@level2name=N'RefCount'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'是否禁用' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'S_Tags', @level2type=N'COLUMN',@level2name=N'Disabled'
GO
/****** Object:  Table [dbo].[P_PlugInTopicReply]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[P_PlugInTopicReply](
  [PlugInTopicReplyId] [int] IDENTITY(1,1) NOT NULL,
  [PlugInTopicId] [int] NOT NULL,
  [Title] [nvarchar](255) NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [CreateUserId] [int] NOT NULL,
  [CreateUserName] [nvarchar](255) NOT NULL,
  [AddIp] [varchar](50) NULL,
  [ReplyContent] [ntext] NULL,
 CONSTRAINT [PK_P_TopicReply] PRIMARY KEY CLUSTERED 
(
  [PlugInTopicReplyId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[Jitar_Browsing]    Script Date: 09/26/2013 09:21:27 ******/
IF  EXISTS (SELECT * FROM dbo.sysobjects WHERE id = OBJECT_ID(N'[dbo].[Jitar_Browsing]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)
DROP TABLE [dbo].[Jitar_Browsing]
GO
/****** Object:  Table [dbo].[Jitar_Browsing]    Script Date: 09/26/2013 09:21:27 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Jitar_Browsing](
  [BrowsingId] [int] IDENTITY(1,1) NOT NULL,
  [ObjType] [tinyint] NOT NULL,
  [ObjId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [BrowsingDate] [datetime] NOT NULL,
 CONSTRAINT [PK_Jitar_Browsing] PRIMARY KEY CLUSTERED 
(
  [BrowsingId] ASC
) ON [PRIMARY]
) ON [PRIMARY]


GO
IF NOT EXISTS (SELECT * FROM dbo.sysindexes WHERE id = OBJECT_ID(N'[dbo].[Jitar_Browsing]') AND name = N'IX_Jitar_Browsing_ObjId')
CREATE NONCLUSTERED INDEX [IX_Jitar_Browsing_ObjId] ON [dbo].[Jitar_Browsing] 
(
  [ObjId] ASC
) ON [PRIMARY]
GO
IF NOT EXISTS (SELECT * FROM dbo.sysindexes WHERE id = OBJECT_ID(N'[dbo].[Jitar_Browsing]') AND name = N'IX_Jitar_Browsing_ObjType')
CREATE NONCLUSTERED INDEX [IX_Jitar_Browsing_ObjType] ON [dbo].[Jitar_Browsing] 
(
  [ObjType] ASC
) ON [PRIMARY]
GO
IF NOT EXISTS (SELECT * FROM dbo.sysindexes WHERE id = OBJECT_ID(N'[dbo].[Jitar_Browsing]') AND name = N'IX_Jitar_Browsing_UserId')
CREATE NONCLUSTERED INDEX [IX_Jitar_Browsing_UserId] ON [dbo].[Jitar_Browsing] 
(
  [UserId] ASC
) ON [PRIMARY]
GO



/****** Object:  Table [dbo].[Jitar_Article]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jitar_Article](
  [ArticleId] [int] NOT NULL,
  [ObjectUuid] [varchar](40) NOT NULL,
  [Title] [nvarchar](256) NOT NULL,
  [UserId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [LastModified] [datetime] NOT NULL,
  [UserTrueName] [nvarchar](50) NOT NULL,
  [ArticleContent] [ntext] NULL,
  [ArticleAbstract] [nvarchar](2000) NULL,
  [ArticleTags] [nvarchar](256) NULL,
  [ViewCount] [int] NOT NULL,
  [CommentCount] [int] NOT NULL,
  [SubjectId] [int] NULL,
  [GradeId] [int] NULL,
  [UserCateId] [int] NULL,
  [SysCateId] [int] NULL,
  [HideState] [smallint] NOT NULL,
  [AuditState] [smallint] NOT NULL,
  [TopState] [bit] NOT NULL,
  [BestState] [bit] NOT NULL,
  [DraftState] [bit] NOT NULL,
  [DelState] [bit] NOT NULL,
  [RecommendState] [bit] NOT NULL,
  [CommentState] [bit] NOT NULL,
  [Href] [varchar](256) NULL,
  [AddIP] [varchar](50) NULL,
  [Trample] [int] NOT NULL,
  [Digg] [int] NOT NULL,
  [StarCount] [int] NOT NULL,
  [TypeState] [bit] NOT NULL,
  [UnitPath] [varchar](512) NULL,
  [UnitId] [int] NULL,
  [PushState] [int] NOT NULL,
  [PushUserId] [int] NULL,
  [OrginPath] [varchar](1024) NOT NULL,
  [UnitPathInfo] [varchar](1024) NOT NULL,
  [ApprovedPathInfo] [varchar](1024) NULL,
  [RcmdPathInfo] [varchar](1024) NULL,
  [ChannelId] [int] NULL,
  [ChannelCate] [varchar](255) NULL,
  [LoginName] [varchar](50) NOT NULL,
  [ArticleFormat] [int] NULL,
  [WordDownload] [bit] NULL,
  [WordHref] [varchar](1024) NULL,
 CONSTRAINT [PK_Jitar_Article_ArticleId] PRIMARY KEY CLUSTERED 
(
  [ArticleId] ASC
) ON [PRIMARY],
 CONSTRAINT [IX_B_Article] UNIQUE NONCLUSTERED 
(
  [ObjectUuid] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_Article_SysCateId] ON [dbo].[Jitar_Article] 
(
  [SysCateId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_Article_UnitId] ON [dbo].[Jitar_Article] 
(
  [UnitId] ASC
) ON [PRIMARY]
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'用户分类' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Jitar_Article', @level2type=N'COLUMN',@level2name=N'UserCateId'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'系统分类' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Jitar_Article', @level2type=N'COLUMN',@level2name=N'SysCateId'
GO
/****** Object:  Table [dbo].[P_PlugInTopic]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[P_PlugInTopic](
  [PlugInTopicId] [int] IDENTITY(1,1) NOT NULL,
  [Title] [nvarchar](255) NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [CreateUserId] [int] NOT NULL,
  [CreateUserName] [nvarchar](255) NOT NULL,
  [TopicContent] [ntext] NULL,
  [AddIp] [varchar](50) NULL,
  [ParentGuid] [varchar](50) NULL,
  [ParentObjectType] [varchar](50) NULL,
 CONSTRAINT [PK_P_Topic] PRIMARY KEY CLUSTERED 
(
  [PlugInTopicId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Jitar_User]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jitar_User](
  [UserId] [int] IDENTITY(1,1) NOT NULL,
  [UserGuid] [varchar](40) NOT NULL,
  [LoginName] [varchar](50) NOT NULL,
  [TrueName] [nvarchar](50) NULL,
  [NickName] [nvarchar](50) NULL,
  [Email] [varchar](255) NULL,
  [CreateDate] [datetime] NULL,
  [VirtualDirectory] [varchar](10) NULL,
  [UserFileFolder] [varchar](10) NULL,
  [Gender] [smallint] NOT NULL,
  [UnitId] [int] NULL,
  [BlogName] [nvarchar](255) NULL,
  [BlogIntroduce] [nvarchar](4000) NULL,
  [UserStatus] [int] NOT NULL,
  [UserGroupID] [int] NOT NULL,
  [VisitCount] [int] NOT NULL,
  [VisitArticleCount] [int] NOT NULL,
  [VisitResourceCount] [int] NOT NULL,
  [ArticleCount] [int] NOT NULL,
  [RecommendArticleCount] [int] NOT NULL,
  [ArticleCommentCount] [int] NOT NULL,
  [ArticleICommentCount] [int] NOT NULL,
  [ResourceCount] [int] NOT NULL,
  [RecommendResourceCount] [int] NOT NULL,
  [ResourceCommentCount] [int] NOT NULL,
  [ResourceICommentCount] [int] NOT NULL,
  [ResourceDownloadCount] [int] NOT NULL,
  [CreateGroupCount] [int] NOT NULL,
  [JionGroupCount] [int] NOT NULL,
  [PhotoCount] [int] NOT NULL,
  [CourseCount] [int] NOT NULL,
  [TopicCount] [int] NOT NULL,
  [CommentCount] [int] NOT NULL,
  [UsedFileSize] [int] NOT NULL,
  [UserIcon] [varchar](255) NULL,
  [UserScore] [int] NOT NULL,
  [UserClassID] [int] NULL,
  [PositionID] [int] NOT NULL,
  [Usn] [int] NOT NULL,
  [SubjectId] [int] NULL,
  [GradeId] [int] NULL,
  [UserTags] [nvarchar](256) NULL,
  [pwd] [varchar](50) NULL,
  [question] [varchar](500) NULL,
  [answer] [varchar](500) NULL,
  [ArticleScore] [int] NOT NULL,
  [ResourceScore] [int] NOT NULL,
  [CommentScore] [int] NOT NULL,
  [MyArticleCount] [int] NOT NULL,
  [OtherArticleCount] [int] NOT NULL,
  [IDcard] [varchar](18) NULL,
  [QQ] [varchar](20) NULL,
  [ArticlePunishScore] [int] NOT NULL,
  [ResourcePunishScore] [int] NOT NULL,
  [CommentPunishScore] [int] NOT NULL,
  [PhotoPunishScore] [int] NOT NULL,
  [videoCount] [int] NOT NULL,
  [photoScore] [int] NOT NULL,
  [videoPunishScore] [int] NOT NULL,
  [videoScore] [int] NOT NULL,
  [PrepareCourseCount] [int] NOT NULL,
  [PushState] [int] NOT NULL,
  [PushUserId] [int] NULL,
  [AccountId] [varchar](50) NULL,
  [UnitPathInfo] [varchar](1024) NOT NULL,
  [UserType] [varchar](256) NULL,
  [HistoryMyArticleCount] [int] NOT NULL,
  [HistoryOtherArticleCount] [int] NOT NULL,
  [version] [int] NOT NULL,
 CONSTRAINT [PK_U_User] PRIMARY KEY CLUSTERED 
(
  [UserId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_User_SubGrad] ON [dbo].[Jitar_User] 
(
  [GradeId] ASC,
  [SubjectId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_User_UnitId] ON [dbo].[Jitar_User] 
(
  [UnitId] ASC
) ON [PRIMARY]
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'用户博客访问量' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Jitar_User', @level2type=N'COLUMN',@level2name=N'VisitCount'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'上传的资源数' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Jitar_User', @level2type=N'COLUMN',@level2name=N'ResourceCount'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'组策略属性的更新序列号(Update Serial Number)' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Jitar_User', @level2type=N'COLUMN',@level2name=N'Usn'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'所属学科' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Jitar_User', @level2type=N'COLUMN',@level2name=N'SubjectId'
GO
/****** Object:  Table [dbo].[P_Page]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[P_Page](
  [PageId] [int] IDENTITY(1,1) NOT NULL,
  [Name] [varchar](50) NOT NULL,
  [Title] [nvarchar](255) NOT NULL,
  [ObjType] [int] NOT NULL,
  [ObjId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [Description] [nvarchar](4000) NULL,
  [ItemOrder] [smallint] NOT NULL,
  [LayoutId] [int] NULL,
  [Skin] [varchar](32) NOT NULL,
  [CustomSkin] [nvarchar](4000) NULL,
 CONSTRAINT [PK_P_Page] PRIMARY KEY CLUSTERED 
(
  [PageId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_P_Page_Obj] ON [dbo].[P_Page] 
(
  [ObjId] ASC,
  [ObjType] ASC
) ON [PRIMARY]
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'拥有此页面的对象类型' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'P_Page', @level2type=N'COLUMN',@level2name=N'ObjType'
GO
/****** Object:  Table [dbo].[B_Photo]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[B_Photo](
  [PhotoId] [int] IDENTITY(1,1) NOT NULL,
  [ObjectUuid] [varchar](40) NOT NULL,
  [Title] [nvarchar](256) NOT NULL,
  [UserId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [LastModified] [datetime] NOT NULL,
  [UserNickName] [nvarchar](50) NULL,
  [UserTrueName] [nvarchar](50) NULL,
  [Summary] [nvarchar](2000) NULL,
  [Tags] [nvarchar](256) NULL,
  [ViewCount] [int] NOT NULL,
  [CommentCount] [int] NOT NULL,
  [UserStaple] [int] NULL,
  [SysCate] [int] NULL,
  [AuditState] [smallint] NOT NULL,
  [DelState] [bit] NOT NULL,
  [Href] [varchar](256) NOT NULL,
  [Width] [int] NOT NULL,
  [Height] [int] NOT NULL,
  [FileSize] [int] NOT NULL,
  [AddIp] [varchar](16) NULL,
  [IsPrivateShow] [bit] NOT NULL,
  [SpecialSubjectId] [int] NOT NULL,
  [UnitId] [int] NULL,
 CONSTRAINT [PK_B_Photo] PRIMARY KEY CLUSTERED 
(
  [PhotoId] ASC
) ON [PRIMARY],
 CONSTRAINT [IX_B_Photo_Uuid] UNIQUE NONCLUSTERED 
(
  [ObjectUuid] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_B_Photo_SysCate] ON [dbo].[B_Photo] 
(
  [SysCate] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_B_Photo_UnitId] ON [dbo].[B_Photo] 
(
  [UnitId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_B_Photo_UserId] ON [dbo].[B_Photo] 
(
  [UserId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_B_Photo_UserStaple] ON [dbo].[B_Photo] 
(
  [UserStaple] ASC
) ON [PRIMARY]
GO
/****** Object:  StoredProcedure [dbo].[p_deletefield]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--删除具有约束关系的字段

CREATE proc [dbo].[p_deletefield]
@tbname sysname, --要处理的表名
@fdname sysname, --要处理的字段名
@delfield bit=1  --0只删除关系,1同时删除字段
as
declare hCForEach cursor global for
--默认值约束
select sql='alter table ['+b.name+'] drop constraint ['+d.name+']'
from syscolumns a
 join sysobjects b on a.id=b.id and a.name=@fdname and b.name=@tbname
 join syscomments c on a.cdefault=c.id
 join sysobjects d on c.id=d.id
union -- 外键引用
select s='alter table ['+c.name+'] drop constraint ['+b.name+']'
from sysforeignkeys a
 join sysobjects b on b.id=a.constid
 join sysobjects c on c.id=a.fkeyid
 join syscolumns d on d.id=c.id and a.fkey=d.colid and d.name=@fdname
 join sysobjects e on e.id=a.rkeyid and e.name=@tbname
 join syscolumns f on f.id=e.id and a.rkey=f.colid
union --主键/唯一键/索引
select case when e.xtype in('PK','UQ') then 'alter table ['+c.name+'] drop constraint ['+e.name+']'
  else 'drop index ['+c.name+'].['+a.name+']' end
from sysindexes a
 join sysindexkeys b on a.id=b.id and a.indid=b.indid
 join sysobjects c on b.id=c.id and c.xtype='U' and c.name=@tbname
 join syscolumns d on b.id=d.id and b.colid=d.colid and d.name=@fdname
 left join sysobjects e on e.id=object_id(a.name)
where a.indid not in(0,255)

exec sp_msforeach_worker '?'

if @delfield=1
 exec('alter table ['+@tbname+'] drop column ['+@fdname+']')
GO
/****** Object:  StoredProcedure [dbo].[ClearChatMsg]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[ClearChatMsg]  AS
delete  from G_ChatMessage Where DateDiff(d,SendDate,getdate())>30
GO
/****** Object:  UserDefinedFunction [dbo].[NWGetPYFirst]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[NWGetPYFirst]
(@str varchar(500) = '')
RETURNS varchar(500)
AS
BEGIN
 Declare @strlen int,
  @return varchar(500),
  @ii int,
  @c char(1),
  @chn nchar(1)
 --//初始化变量 
 Declare @pytable table(
 chn char(2) COLLATE Chinese_PRC_CS_AS NOT NULL,
 py char(1) COLLATE Chinese_PRC_CS_AS NULL,
 PRIMARY KEY (chn) 
   )
 insert into @pytable values('吖', 'a')
 insert into @pytable values('八', 'b')
 insert into @pytable values('嚓', 'c')
 insert into @pytable values('咑', 'd')
 insert into @pytable values('妸', 'e')
 insert into @pytable values('发', 'f')
 insert into @pytable values('旮', 'g')
 insert into @pytable values('铪', 'h')
 insert into @pytable values('丌', 'i')
 --insert into @pytable values('丌', 'J')
 insert into @pytable values('咔', 'k')
 insert into @pytable values('垃', 'l')
 insert into @pytable values('嘸', 'm')
 insert into @pytable values('拏', 'n')
 insert into @pytable values('噢', 'o')
 insert into @pytable values('妑', 'p')
 insert into @pytable values('七', 'q')
 insert into @pytable values('呥', 'r')
 insert into @pytable values('仨', 's')
 insert into @pytable values('他', 't')
 insert into @pytable values('屲', 'u')
 --insert into @pytable values('屲', 'V')
 --insert into @pytable values('屲', 'W')
 insert into @pytable values('夕', 'x')
 insert into @pytable values('丫', 'y')
 insert into @pytable values('帀', 'z')
 select @strlen = len(@str), @return = '', @ii = 0 
 --//循环整个字符串,用拼音的首字母替换汉字 
 while @ii < @strlen 
 begin
  select @ii = @ii + 1, @chn = substring(@str, @ii, 1)
  if @chn > 'z' --//检索输入的字符串中有中文字符
   SELECT @c = max(py)
   FROM @pytable
   where chn <= @chn
  else
   set @c=@chn
 
  set @return=@return+@c 
 end
 return @return
END
GO
/****** Object:  Table [dbo].[G_Group]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[G_Group](
  [GroupId] [int] IDENTITY(1,1) NOT NULL,
  [GroupGuid] [varchar](40) NOT NULL,
  [GroupName] [varchar](32) NOT NULL,
  [GroupTitle] [nvarchar](100) NOT NULL,
  [CategoryId] [int] NULL,
  [CreateDate] [datetime] NOT NULL,
  [CreateUserId] [int] NOT NULL,
  [GroupIcon] [varchar](256) NULL,
  [GroupTags] [nvarchar](256) NULL,
  [GroupIntroduce] [ntext] NULL,
  [Requisition] [ntext] NULL,
  [IsBestGroup] [bit] NOT NULL,
  [IsRecommend] [bit] NOT NULL,
  [GroupState] [smallint] NOT NULL,
  [UserCount] [int] NOT NULL,
  [ArticleCount] [int] NOT NULL,
  [TopicCount] [int] NOT NULL,
  [DiscussCount] [int] NOT NULL,
  [ActionCount] [int] NOT NULL,
  [ResourceCount] [int] NOT NULL,
  [CourseCount] [int] NOT NULL,
  [VisitCount] [int] NOT NULL,
  [JoinLimit] [int] NOT NULL,
  [JoinScore] [int] NOT NULL,
  [Attributes] [ntext] NULL,
  [SubjectId] [int] NULL,
  [GradeId] [int] NULL,
  [bestArticleCount] [int] NOT NULL,
  [bestResourceCount] [int] NOT NULL,
  [PushState] [int] NOT NULL,
  [PushUserId] [int] NULL,
  [KtNo] [varchar](100) NULL,
  [KtLevel] [nvarchar](50) NULL,
  [KtStartDate] [datetime] NULL,
  [KtEndDate] [datetime] NULL,
  [ParentId] [int] NOT NULL,
  [PhotoCount] [int] NOT NULL,
  [VideoCount] [int] NOT NULL,
  [XKXDName] [varchar](1024) NULL,
  [XKXDId] [varchar](1024) NULL,
 CONSTRAINT [PK_G_Group] PRIMARY KEY CLUSTERED 
(
  [GroupId] ASC
) ON [PRIMARY],
 CONSTRAINT [IX_G_Group_1] UNIQUE NONCLUSTERED 
(
  [GroupGuid] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'群组标识' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_Group', @level2type=N'COLUMN',@level2name=N'GroupId'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'群组全局唯一标识' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_Group', @level2type=N'COLUMN',@level2name=N'GroupGuid'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'群组英文名称，只能使用字母和数字' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_Group', @level2type=N'COLUMN',@level2name=N'GroupName'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'群组中文名称，中文限定在1-50长度之间' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_Group', @level2type=N'COLUMN',@level2name=N'GroupTitle'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'群组所属分类' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_Group', @level2type=N'COLUMN',@level2name=N'CategoryId'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'群组创建时间' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_Group', @level2type=N'COLUMN',@level2name=N'CreateDate'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'群组创建者标识' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_Group', @level2type=N'COLUMN',@level2name=N'CreateUserId'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'群组头像' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_Group', @level2type=N'COLUMN',@level2name=N'GroupIcon'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'群组介绍' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_Group', @level2type=N'COLUMN',@level2name=N'GroupIntroduce'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'推荐群组状态，0普通，1推荐群组' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_Group', @level2type=N'COLUMN',@level2name=N'IsBestGroup'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'群组状态：0正常，1待审核，2锁定，3待删除，4隐藏' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_Group', @level2type=N'COLUMN',@level2name=N'GroupState'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'该群组加入的用户数' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_Group', @level2type=N'COLUMN',@level2name=N'UserCount'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'该群组发表的主题（话题）数' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_Group', @level2type=N'COLUMN',@level2name=N'TopicCount'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'本群组举行的活动数' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_Group', @level2type=N'COLUMN',@level2name=N'ActionCount'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'该群组访问数(浏览量)' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_Group', @level2type=N'COLUMN',@level2name=N'VisitCount'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'加入该群组所需要的条件：0任意加入，1只能邀请，2积分大于N方可' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_Group', @level2type=N'COLUMN',@level2name=N'JoinLimit'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'如果需要积分才能加入，则设置需要的积分数' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_Group', @level2type=N'COLUMN',@level2name=N'JoinScore'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'所属学科' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_Group', @level2type=N'COLUMN',@level2name=N'SubjectId'
GO
/****** Object:  Table [dbo].[U_UserStat]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[U_UserStat](
  [UserId] [int] NOT NULL,
  [StatGuid] [varchar](40) NOT NULL,
  [LoginName] [varchar](50) NOT NULL,
  [TrueName] [nvarchar](50) NULL,
  [NickName] [nvarchar](50) NULL,
  [Email] [varchar](255) NULL,
  [CreateDate] [datetime] NULL,
  [Gender] [smallint] NOT NULL,
  [UnitId] [int] NULL,
  [BlogName] [nvarchar](255) NULL,
  [UserStatus] [int] NOT NULL,
  [UserGroupID] [int] NOT NULL,
  [VisitCount] [int] NOT NULL,
  [VisitArticleCount] [int] NOT NULL,
  [VisitResourceCount] [int] NOT NULL,
  [ArticleCount] [int] NOT NULL,
  [RecommendArticleCount] [int] NOT NULL,
  [ArticleCommentCount] [int] NOT NULL,
  [ArticleICommentCount] [int] NOT NULL,
  [ResourceCount] [int] NOT NULL,
  [RecommendResourceCount] [int] NOT NULL,
  [ResourceCommentCount] [int] NOT NULL,
  [ResourceICommentCount] [int] NOT NULL,
  [ResourceDownloadCount] [int] NOT NULL,
  [CreateGroupCount] [int] NOT NULL,
  [JionGroupCount] [int] NOT NULL,
  [PhotoCount] [int] NOT NULL,
  [CourseCount] [int] NOT NULL,
  [TopicCount] [int] NOT NULL,
  [CommentCount] [int] NOT NULL,
  [UsedFileSize] [int] NOT NULL,
  [UserScore] [int] NOT NULL,
  [UserClassID] [int] NULL,
  [PositionID] [int] NOT NULL,
  [SubjectId] [int] NULL,
  [GradeId] [int] NULL,
  [ArticleScore] [int] NOT NULL,
  [ResourceScore] [int] NOT NULL,
  [CommentScore] [int] NOT NULL,
  [MyArticleCount] [int] NOT NULL,
  [OtherArticleCount] [int] NOT NULL,
  [IDcard] [varchar](18) NULL,
  [QQ] [varchar](20) NULL,
  [ArticlePunishScore] [int] NOT NULL,
  [ResourcePunishScore] [int] NOT NULL,
  [CommentPunishScore] [int] NOT NULL,
  [PhotoPunishScore] [int] NOT NULL,
  [videoCount] [int] NOT NULL,
  [photoScore] [int] NOT NULL,
  [videoPunishScore] [int] NOT NULL,
  [videoScore] [int] NOT NULL,
  [PrepareCourseCount] [int] NOT NULL,
  [UserType] [varchar](256) NULL,
  [WorkDate] [datetime] NOT NULL,
  [PunishScore] [int] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[U_UserOnLineStat]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[U_UserOnLineStat](
  [Id] [int] NOT NULL,
  [Highest] [int] NOT NULL,
  [AppearTime] [varchar](20) NOT NULL,
 CONSTRAINT [PK_U_UserOnLineStat] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[BBS_Topic]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BBS_Topic](
  [TopicId] [int] IDENTITY(1,1) NOT NULL,
  [GroupId] [int] NOT NULL,
  [Title] [nvarchar](128) NOT NULL,
  [UserId] [int] NOT NULL,
  [Content] [ntext] NULL,
  [CreateDate] [datetime] NOT NULL,
  [IsBest] [bit] NOT NULL,
  [IsTop] [bit] NOT NULL,
  [IsDeleted] [bit] NOT NULL,
  [ReplyCount] [int] NOT NULL,
  [ViewCount] [int] NOT NULL,
  [Tags] [nvarchar](128) NOT NULL,
 CONSTRAINT [PK_BBS_Topic] PRIMARY KEY CLUSTERED 
(
  [TopicId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[U_UserOnLine]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[U_UserOnLine](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [UserId] [int] NOT NULL,
  [UserName] [nvarchar](50) NOT NULL,
  [OnLineTime] [bigint] NOT NULL,
 CONSTRAINT [PK_U_UserOnLine] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BBS_Reply]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BBS_Reply](
  [ReplyId] [int] IDENTITY(1,1) NOT NULL,
  [TopicId] [int] NOT NULL,
  [GroupId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [TargetReply] [int] NOT NULL,
  [Title] [nvarchar](128) NOT NULL,
  [Content] [ntext] NULL,
  [CreateDate] [datetime] NOT NULL,
  [IsBest] [bit] NOT NULL,
  [IsDeleted] [bit] NOT NULL,
 CONSTRAINT [PK_BBS_Reply] PRIMARY KEY CLUSTERED 
(
  [ReplyId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[U_PunishScore]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[U_PunishScore](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [UserId] [int] NOT NULL,
  [ObjType] [int] NULL,
  [ObjId] [int] NOT NULL,
  [ObjTitle] [varchar](1000) NULL,
  [PunishDate] [datetime] NOT NULL,
  [Score] [float] NOT NULL,
  [Reason] [varchar](2000) NULL,
  [CreateUserId] [int] NULL,
  [CreateUserName] [nvarchar](50) NULL,
 CONSTRAINT [PK_U_PunishScore] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[C_PrepareCourse]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[C_PrepareCourse](
  [PrepareCourseId] [int] IDENTITY(1,1) NOT NULL,
  [ObjectGuid] [varchar](40) NOT NULL,
  [Title] [nvarchar](255) NOT NULL,
  [CreateUserId] [int] NOT NULL,
  [LeaderId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [StartDate] [datetime] NOT NULL,
  [EndDate] [datetime] NOT NULL,
  [Description] [ntext] NULL,
  [CommonContent] [ntext] NULL,
  [MetaSubjectId] [int] NULL,
  [GradeId] [int] NULL,
  [Tags] [nvarchar](512) NULL,
  [MemberCount] [int] NOT NULL,
  [ArticleCount] [int] NOT NULL,
  [ResourceCount] [int] NOT NULL,
  [TopicReplyCount] [int] NOT NULL,
  [TopicCount] [int] NOT NULL,
  [ActionCount] [int] NOT NULL,
  [ViewCount] [int] NOT NULL,
  [Status] [int] NOT NULL,
  [LockedDate] [datetime] NOT NULL,
  [LockedUserId] [int] NOT NULL,
  [PrepareCourseEditId] [int] NOT NULL,
  [PrepareCoursePlanId] [int] NOT NULL,
  [PrepareCourseGenerated] [bit] NOT NULL,
  [ItemOrder] [int] NOT NULL,
  [RecommendState] [bit] NOT NULL,
  [ContentType] [smallint] NOT NULL,
 CONSTRAINT [PK_C_PrepareCourse] PRIMARY KEY CLUSTERED 
(
  [PrepareCourseId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[U_Message]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[U_Message](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [sendId] [int] NOT NULL,
  [receiveId] [int] NOT NULL,
  [title] [nvarchar](256) NULL,
  [content] [nvarchar](2000) NOT NULL,
  [sendtime] [datetime] NOT NULL,
  [isRead] [bit] NOT NULL,
  [isDel] [bit] NOT NULL,
  [isSenderDel] [bit] NOT NULL,
  [isReply] [bit] NOT NULL,
  [fileId] [int] NULL,
 CONSTRAINT [PK_B_Message] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_U_Message_receiveId] ON [dbo].[U_Message] 
(
  [receiveId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_U_Message_sendId] ON [dbo].[U_Message] 
(
  [sendId] ASC
) ON [PRIMARY]
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'对象标识' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_Message', @level2type=N'COLUMN',@level2name=N'Id'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'发送者' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_Message', @level2type=N'COLUMN',@level2name=N'sendId'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'接收者' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_Message', @level2type=N'COLUMN',@level2name=N'receiveId'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'发送标题' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_Message', @level2type=N'COLUMN',@level2name=N'title'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'发送内容' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_Message', @level2type=N'COLUMN',@level2name=N'content'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'发送时间' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_Message', @level2type=N'COLUMN',@level2name=N'sendtime'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'已/未读（0：未读，1：已读）' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_Message', @level2type=N'COLUMN',@level2name=N'isRead'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'是否删除（0：未删，1：已删）' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_Message', @level2type=N'COLUMN',@level2name=N'isDel'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'是否回复（0：未回复，1：已回复）' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_Message', @level2type=N'COLUMN',@level2name=N'isReply'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'对应上传Id' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_Message', @level2type=N'COLUMN',@level2name=N'fileId'
GO
/****** Object:  Table [dbo].[C_PrepareCourseTopic]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[C_PrepareCourseTopic](
  [PrepareCourseTopicId] [int] IDENTITY(1,1) NOT NULL,
  [PrepareCourseId] [int] NOT NULL,
  [PrepareCourseStageId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [Title] [nvarchar](250) NOT NULL,
  [UserId] [int] NOT NULL,
  [Content] [ntext] NOT NULL,
 CONSTRAINT [PK_C_PrepareCourseTopic] PRIMARY KEY CLUSTERED 
(
  [PrepareCourseTopicId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_C_PrepareCourseTopic_PrepareCourseId] ON [dbo].[C_PrepareCourseTopic] 
(
  [PrepareCourseId] ASC,
  [PrepareCourseStageId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[U_LeaveWord]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[U_LeaveWord](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [ObjType] [int] NOT NULL,
  [ObjId] [int] NOT NULL,
  [UserId] [int] NULL,
  [Title] [nvarchar](128) NOT NULL,
  [Content] [nvarchar](2000) NULL,
  [CreateDate] [datetime] NULL,
  [LoginName] [nvarchar](50) NULL,
  [NickName] [nvarchar](50) NULL,
  [Email] [nvarchar](50) NULL,
  [Ip] [nvarchar](15) NULL,
  [ReplyTimes] [int] NULL,
  [Reply] [nvarchar](2000) NULL,
  [IsAnon] [bit] NOT NULL,
 CONSTRAINT [PK_U_LeaveWord] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'被留言对象类型' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_LeaveWord', @level2type=N'COLUMN',@level2name=N'ObjType'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'被留言对象的标识' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_LeaveWord', @level2type=N'COLUMN',@level2name=N'ObjId'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'写留言的人的标识.' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_LeaveWord', @level2type=N'COLUMN',@level2name=N'UserId'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'留言时间' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_LeaveWord', @level2type=N'COLUMN',@level2name=N'CreateDate'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'呢称' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_LeaveWord', @level2type=N'COLUMN',@level2name=N'NickName'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_LeaveWord', @level2type=N'COLUMN',@level2name=N'Reply'
GO
/****** Object:  Table [dbo].[C_PrepareCourseTopicReply]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[C_PrepareCourseTopicReply](
  [PrepareCourseTopicReplyId] [int] IDENTITY(1,1) NOT NULL,
  [PrepareCourseStageId] [int] NOT NULL,
  [PrepareCourseTopicId] [int] NOT NULL,
  [PrepareCourseId] [int] NOT NULL,
  [Title] [nvarchar](255) NULL,
  [CreateDate] [datetime] NOT NULL,
  [UserId] [int] NOT NULL,
  [Content] [ntext] NULL,
 CONSTRAINT [PK_C_PrepareCourseStageReply] PRIMARY KEY CLUSTERED 
(
  [PrepareCourseTopicReplyId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_C_PrepareCourseTopicReply_PrepareCourseId] ON [dbo].[C_PrepareCourseTopicReply] 
(
  [PrepareCourseId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_C_PrepareCourseTopicReply_PrepareCourseTopicId] ON [dbo].[C_PrepareCourseTopicReply] 
(
  [PrepareCourseTopicId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[U_UserFile]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[U_UserFile](
  [FileID] [int] IDENTITY(1,1) NOT NULL,
  [FileName] [nvarchar](255) NOT NULL,
 CONSTRAINT [PK_U_File] PRIMARY KEY CLUSTERED 
(
  [FileID] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[C_PrepareCourseStage]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[C_PrepareCourseStage](
  [PrepareCourseStageId] [int] IDENTITY(1,1) NOT NULL,
  [PrepareCourseId] [int] NOT NULL,
  [Title] [nvarchar](255) NOT NULL,
  [BeginDate] [datetime] NOT NULL,
  [FinishDate] [datetime] NOT NULL,
  [Description] [ntext] NULL,
  [OrderIndex] [int] NOT NULL,
 CONSTRAINT [PK_C_PrepareCourseStage] PRIMARY KEY CLUSTERED 
(
  [PrepareCourseStageId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_C_PrepareCourseStage_PrepareCourseId] ON [dbo].[C_PrepareCourseStage] 
(
  [PrepareCourseId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[U_GroupUser]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[U_GroupUser](
  [GroupUserId] [int] IDENTITY(1,1) NOT NULL,
  [GroupId] [int] NULL,
  [UserId] [int] NULL,
  [Managed] [int] NULL,
 CONSTRAINT [PK_U_GroupUser] PRIMARY KEY CLUSTERED 
(
  [GroupUserId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[C_PrepareCourseResource]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[C_PrepareCourseResource](
  [PrepareCourseResourceId] [int] IDENTITY(1,1) NOT NULL,
  [ResourceId] [int] NOT NULL,
  [PrepareCourseStageId] [int] NOT NULL,
  [PrepareCourseId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [ResourceTitle] [nvarchar](256) NOT NULL,
 CONSTRAINT [PK_C_PrepareCourseResource] PRIMARY KEY CLUSTERED 
(
  [PrepareCourseResourceId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_C_PrepareCourseResource_PrepareCourseId] ON [dbo].[C_PrepareCourseResource] 
(
  [PrepareCourseId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_C_PrepareCourseResource_ResourceId] ON [dbo].[C_PrepareCourseResource] 
(
  [ResourceId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[G_ChatUser]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[G_ChatUser](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [UserId] [int] NOT NULL,
  [RoomId] [int] NOT NULL,
  [UserName] [varchar](50) NULL,
  [JoinDate] [datetime] NOT NULL,
  [IsSay] [bit] NOT NULL,
  [FontColor] [varchar](50) NOT NULL,
  [FontSize] [varchar](50) NOT NULL,
  [IsLeave] [bit] NOT NULL,
  [IsActived] [bit] NOT NULL,
  [SayDate] [datetime] NULL,
  [ActTime] [datetime] NULL,
  [last_change] [timestamp] NULL,
 CONSTRAINT [PK_G_ChatUser] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_G_ChatUser_RoomId] ON [dbo].[G_ChatUser] 
(
  [RoomId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[C_PrepareCourseArticle]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[C_PrepareCourseArticle](
  [PrepareCourseArticleId] [int] IDENTITY(1,1) NOT NULL,
  [ArticleId] [int] NOT NULL,
  [PrepareCourseStageId] [int] NOT NULL,
  [PrepareCourseId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [Title] [nvarchar](512) NOT NULL,
  [LoginName] [varchar](50) NOT NULL,
  [UserTrueName] [nvarchar](50) NOT NULL,
  [ArticleState] [bit] NOT NULL,
  [TypeState] [bit] NOT NULL,
 CONSTRAINT [PK_C_PrepareCourseArticle] PRIMARY KEY CLUSTERED 
(
  [PrepareCourseArticleId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_C_PrepareCourseArticle_PrepareCourseId] ON [dbo].[C_PrepareCourseArticle] 
(
  [PrepareCourseId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_C_PrepareCourseArticle_PrepareCourseStageId] ON [dbo].[C_PrepareCourseArticle] 
(
  [PrepareCourseStageId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Jitar_AccessControl]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Jitar_AccessControl](
  [AccessControlId] [int] IDENTITY(1,1) NOT NULL,
  [UserId] [int] NOT NULL,
  [ObjectType] [int] NOT NULL,
  [ObjectId] [int] NOT NULL,
  [ObjectTitle] [nvarchar](256) NOT NULL,
 CONSTRAINT [PK_Jitar_AccessControl] PRIMARY KEY CLUSTERED 
(
  [AccessControlId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_AccessControl_Obj] ON [dbo].[Jitar_AccessControl] 
(
  [ObjectId] ASC,
  [ObjectType] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_AccessControl_UserId] ON [dbo].[Jitar_AccessControl] 
(
  [UserId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[S_TagRef]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[S_TagRef](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [TagId] [int] NOT NULL,
  [ObjectId] [int] NOT NULL,
  [ObjectType] [int] NOT NULL,
  [OrderNum] [int] NOT NULL,
 CONSTRAINT [PK_S_TagRef] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_S_TagRef_ObjectId] ON [dbo].[S_TagRef] 
(
  [ObjectId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_S_TagRef_ObjectType] ON [dbo].[S_TagRef] 
(
  [ObjectType] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_S_TagRef_TagId] ON [dbo].[S_TagRef] 
(
  [TagId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[G_ChatRoom]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[G_ChatRoom](
  [RoomId] [int] IDENTITY(1,1) NOT NULL,
  [GroupId] [int] NOT NULL,
  [PrepareCourseId] [int] NOT NULL,
  [RoomName] [varchar](1000) NOT NULL,
  [CreaterName] [varchar](1000) NULL,
  [CreateDate] [datetime] NOT NULL,
  [RoomInfo] [varchar](1000) NULL,
  [IsClosed] [bit] NOT NULL,
 CONSTRAINT [PK_G_ChatRoom] PRIMARY KEY CLUSTERED 
(
  [RoomId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[A_ActionReply]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[A_ActionReply](
  [ActionReplyId] [int] IDENTITY(1,1) NOT NULL,
  [ActionId] [int] NOT NULL,
  [Topic] [nchar](512) NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [UserId] [int] NOT NULL,
  [Content] [ntext] NULL,
  [AddIP] [varchar](50) NULL,
 CONSTRAINT [PK_G_ActionReply] PRIMARY KEY CLUSTERED 
(
  [ActionReplyId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_A_ActionReply_ActionId] ON [dbo].[A_ActionReply] 
(
  [ActionId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HtmlArticleBase]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS OFF
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[HtmlArticleBase](
  [ArticleId] [int] IDENTITY(1,1) NOT NULL,
  [ArticleGuid] [varchar](50) NOT NULL,
  [Title] [nvarchar](256) NOT NULL,
  [UserId] [int] NOT NULL,
  [LoginName] [varchar](50) NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [ViewCount] [int] NOT NULL,
  [CommentCount] [int] NOT NULL,
  [UserCateId] [int] NULL,
  [SysCateId] [int] NULL,
  [TypeState] [bit] NOT NULL,
  [HideState] [smallint] NULL,
  [AuditState] [smallint] NULL,
  [DraftState] [bit] NOT NULL,
  [DelState] [bit] NOT NULL,
  [RecommendState] [bit] NOT NULL,
  [BestState] [bit] NOT NULL,
  [TableName] [varchar](50) NOT NULL,
 CONSTRAINT [PK_HtmlArticleBase_ArticleId] PRIMARY KEY CLUSTERED 
(
  [ArticleId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_HtmlArticleBase_UserId] ON [dbo].[HtmlArticleBase] 
(
  [UserId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[A_ActionUser]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[A_ActionUser](
  [ActionUserId] [int] IDENTITY(1,1) NOT NULL,
  [ActionId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [AttendUserCount] [int] NOT NULL,
  [Description] [ntext] NULL,
  [InviteUserId] [int] NULL,
  [IsApprove] [int] NOT NULL,
  [Status] [int] NOT NULL,
 CONSTRAINT [PK_G_ActionUser] PRIMARY KEY CLUSTERED 
(
  [ActionUserId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_A_ActionUser_ActionId] ON [dbo].[A_ActionUser] 
(
  [ActionId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_A_ActionUser_UserId] ON [dbo].[A_ActionUser] 
(
  [UserId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[G_ChatColor]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[G_ChatColor](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [ColorName] [varchar](100) NULL,
  [ColorValue] [varchar](50) NULL,
 CONSTRAINT [PK_G_ChatColor] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[G_GroupVideo]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[G_GroupVideo](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [GroupId] [int] NOT NULL,
  [VideoId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [GroupCateId] [int] NULL,
  [PubDate] [datetime] NOT NULL,
  [isGroupBest] [bit] NOT NULL,
 CONSTRAINT [PK_G_GroupVideo] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_G_GroupVideo_GroupId] ON [dbo].[G_GroupVideo] 
(
  [GroupId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[B_Comment]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[B_Comment](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [ObjType] [int] NOT NULL,
  [ObjId] [int] NOT NULL,
  [Title] [nvarchar](512) NOT NULL,
  [Content] [ntext] NULL,
  [Audit] [bit] NOT NULL,
  [Star] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [UserId] [int] NULL,
  [UserName] [nvarchar](50) NOT NULL,
  [Ip] [varchar](50) NOT NULL,
  [AboutUserId] [int] NULL,
 CONSTRAINT [PK_B_Comment] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_B_Comment_AboutUserId] ON [dbo].[B_Comment] 
(
  [AboutUserId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_B_Comment_ObjId_ObjType] ON [dbo].[B_Comment] 
(
  [ObjId] ASC,
  [ObjType] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_B_Comment_UserId] ON [dbo].[B_Comment] 
(
  [UserId] ASC
) ON [PRIMARY]
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'被评论的用户对象标识' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'B_Comment', @level2type=N'COLUMN',@level2name=N'AboutUserId'
GO
/****** Object:  Table [dbo].[S_Subject]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[S_Subject](
  [SubjectId] [int] IDENTITY(1,1) NOT NULL,
  [MetaSubjectId] [int] NOT NULL,
  [MetaGradeId] [int] NOT NULL,
  [SubjectName] [nvarchar](32) NOT NULL,
  [OrderNum] [int] NOT NULL,
  [SubjectCode] [nvarchar](32) NOT NULL,
  [VisitCount] [int] NOT NULL,
  [UserCount] [int] NOT NULL,
  [GroupCount] [int] NOT NULL,
  [ArticleCount] [int] NOT NULL,
  [ResourceCount] [int] NOT NULL,
  [reslibCId] [int] NOT NULL,
  [TodayArticleCount] [int] NOT NULL,
  [YesterdayArticleCount] [int] NOT NULL,
  [TodayResourceCount] [int] NOT NULL,
  [YesterdayResourceCount] [int] NOT NULL,
  [SubjectGuid] [varchar](50) NOT NULL,
  [HeaderContent] [ntext] NULL,
  [FooterContent] [ntext] NULL,
  [TemplateName] [varchar](50) NULL,
  [ThemeName] [varchar](50) NULL,
  [Logo] [varchar](255) NULL,
  [CustormTemplate] [ntext] NULL,
  [ShortcutTarget] [varchar](512) NULL,
  [HistoryArticleCount] [int] NOT NULL,
  [PhotoCount] [int] NOT NULL,
  [VideoCount] [int] NOT NULL,
  [PrepareCourseCount] [int] NOT NULL,
  [ActionCount] [int] NOT NULL,
 CONSTRAINT [PK_S_Subject] PRIMARY KEY CLUSTERED 
(
  [SubjectId] ASC
) ON [PRIMARY],
 CONSTRAINT [IX_S_Subject] UNIQUE NONCLUSTERED 
(
  [SubjectName] ASC
) ON [PRIMARY],
 CONSTRAINT [IX_S_Subject_1] UNIQUE NONCLUSTERED 
(
  [SubjectCode] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'所属元学科标识' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'S_Subject', @level2type=N'COLUMN',@level2name=N'MetaSubjectId'
GO
/****** Object:  Table [dbo].[Jitar_Unit]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jitar_Unit](
  [UnitId] [int] NOT NULL,
  [UnitGuid] [uniqueidentifier] NOT NULL,
  [UnitName] [varchar](256) NOT NULL,
  [UnitTitle] [nvarchar](256) NOT NULL,
  [SiteTitle] [nvarchar](256) NOT NULL,
  [ParentId] [int] NOT NULL,
  [UnitPathInfo] [varchar](1024) NOT NULL,
  [HasChild] [bit] NOT NULL,
  [UserCount] [int] NOT NULL,
  [ArticleCount] [int] NOT NULL,
  [HistoryArticleCount] [int] NOT NULL,
  [RecommendArticleCount] [int] NOT NULL,
  [ResourceCount] [int] NOT NULL,
  [RecommendResourceCount] [int] NOT NULL,
  [PhotoCount] [int] NOT NULL,
  [VideoCount] [int] NOT NULL,
  [TotalScore] [int] NOT NULL,
  [ItemIndex] [int] NOT NULL,
  [TemplateName] [varchar](50) NULL,
  [ThemeName] [varchar](50) NULL,
  [UnitLogo] [varchar](256) NULL,
  [HeaderContent] [ntext] NULL,
  [FooterContent] [ntext] NULL,
  [ShortcutTarget] [varchar](256) NULL,
  [Rank] [int] NOT NULL,
  [AveScore] [float] NOT NULL,
  [UnitType] [nvarchar](50) NULL,
  [UnitPhoto] [varchar](512) NULL,
  [UnitInfo] [ntext] NULL,
  [DelState] [bit] NOT NULL,
 CONSTRAINT [PK__Jitar_Unit_UnitId] PRIMARY KEY CLUSTERED 
(
  [UnitId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_Unit_ParentId] ON [dbo].[Jitar_Unit] 
(
  [ParentId] ASC
) ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[f_split]    Script Date: 09/16/2013 16:02:53 ******/
SET QUOTED_IDENTIFIER ON
GO
create function [dbo].[f_split](@SourceSql varchar(8000),@StrSeprate varchar(10))
returns @temp table(a varchar(100))
--实现split功能 的函数
--date    :2005-4-20
--Author :Domino
as
begin
    declare @i int
    set @SourceSql=rtrim(ltrim(@SourceSql))
    set @i=charindex(@StrSeprate,@SourceSql)
    while @i>=1
    begin
        insert @temp values(left(@SourceSql,@i-1))
        set @SourceSql=substring(@SourceSql,@i+1,len(@SourceSql)-@i)
        set @i=charindex(@StrSeprate,@SourceSql)
    end
    if @SourceSql<>'\'
       insert @temp values(@SourceSql)
    return
end
GO
/****** Object:  Table [dbo].[A_Action]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[A_Action](
  [ActionId] [int] IDENTITY(1,1) NOT NULL,
  [Title] [nvarchar](512) NOT NULL,
  [OwnerId] [int] NULL,
  [OwnerType] [varchar](50) NOT NULL,
  [CreateUserId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [ActionType] [int] NULL,
  [Description] [ntext] NULL,
  [UserLimit] [int] NULL,
  [StartDateTime] [datetime] NOT NULL,
  [FinishDateTime] [datetime] NOT NULL,
  [AttendLimitDateTime] [datetime] NOT NULL,
  [Place] [nvarchar](4000) NULL,
  [AttendCount] [int] NOT NULL,
  [AttendSuccessCount] [int] NOT NULL,
  [AttendQuitCount] [int] NOT NULL,
  [AttendFailCount] [int] NOT NULL,
  [Status] [int] NOT NULL,
  [Visibility] [int] NOT NULL,
  [IsLock] [int] NOT NULL,
 CONSTRAINT [PK_G_Action] PRIMARY KEY CLUSTERED 
(
  [ActionId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_A_Action_CreateUserId] ON [dbo].[A_Action] 
(
  [CreateUserId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_A_Action_OwnerId] ON [dbo].[A_Action] 
(
  [OwnerId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_A_Action_OwnerType] ON [dbo].[A_Action] 
(
  [OwnerType] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[G_GroupMember]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[G_GroupMember](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [GroupId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [Status] [int] NOT NULL,
  [GroupRole] [int] NOT NULL,
  [JoinDate] [datetime] NOT NULL,
  [ArticleCount] [int] NOT NULL,
  [ResourceCount] [int] NOT NULL,
  [CourseCount] [int] NOT NULL,
  [TopicCount] [int] NOT NULL,
  [ReplyCount] [int] NOT NULL,
  [ActionCount] [int] NOT NULL,
  [InviterId] [int] NULL,
  [TeacherUnit] [nvarchar](100) NULL,
  [TeacherZYZW] [nvarchar](100) NULL,
  [TeacherXL] [nvarchar](100) NULL,
  [TeacherXW] [nvarchar](100) NULL,
  [TeacherYJZC] [ntext] NULL,
 CONSTRAINT [PK_G_GroupMember] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_G_GroupMember_GroupId] ON [dbo].[G_GroupMember] 
(
  [GroupId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_G_GroupMember_UserId] ON [dbo].[G_GroupMember] 
(
  [UserId] ASC
) ON [PRIMARY]
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'标识' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_GroupMember', @level2type=N'COLUMN',@level2name=N'Id'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'群组标识' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_GroupMember', @level2type=N'COLUMN',@level2name=N'GroupId'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'成员标识' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_GroupMember', @level2type=N'COLUMN',@level2name=N'UserId'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'该成员在本群组内的状态和角色' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_GroupMember', @level2type=N'COLUMN',@level2name=N'Status'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'加入群组时间' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_GroupMember', @level2type=N'COLUMN',@level2name=N'JoinDate'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'该成员在本群组内发表的博文数量' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_GroupMember', @level2type=N'COLUMN',@level2name=N'ArticleCount'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'该成员在本群组内发表的主题数' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_GroupMember', @level2type=N'COLUMN',@level2name=N'TopicCount'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'该成员在本群组内回复主题的数量' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_GroupMember', @level2type=N'COLUMN',@level2name=N'ReplyCount'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'该成员在本群组内发起的活动数量' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_GroupMember', @level2type=N'COLUMN',@level2name=N'ActionCount'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'发送邀请的用户标识' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'G_GroupMember', @level2type=N'COLUMN',@level2name=N'InviterId'
GO
/****** Object:  Table [dbo].[EvaluationVideo]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[EvaluationVideo](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [EvaluationPlanId] [int] NOT NULL,
  [VideoId] [int] NOT NULL,
  [VideoTitle] [nvarchar](512) NULL,
  [FlvThumbNailHref] [varchar](1024) NULL,
  [CreateDate] [datetime] NOT NULL,
 CONSTRAINT [PK_EvaluationVideo] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_EvaluationVideo_EvaluationPlanId] ON [dbo].[EvaluationVideo] 
(
  [EvaluationPlanId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Jtr_Resource]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jtr_Resource](
  [ResourceId] [int] IDENTITY(1,1) NOT NULL,
  [ObjectUuid] [varchar](40) NOT NULL,
  [Title] [nvarchar](256) NOT NULL,
  [UserId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [LastModified] [datetime] NOT NULL,
  [Summary] [nvarchar](2000) NULL,
  [Tags] [nvarchar](256) NULL,
  [ViewCount] [int] NOT NULL,
  [CommentCount] [int] NOT NULL,
  [AuditState] [smallint] NOT NULL,
  [UserCateId] [int] NULL,
  [SysCateId] [int] NULL,
  [DelState] [bit] NOT NULL,
  [RecommendState] [bit] NOT NULL,
  [DownloadCount] [int] NOT NULL,
  [ShareMode] [int] NOT NULL,
  [ResTypeId] [int] NULL,
  [Author] [nvarchar](128) NULL,
  [Publisher] [nvarchar](128) NULL,
  [SubjectId] [int] NULL,
  [Href] [nvarchar](256) NOT NULL,
  [FileSize] [int] NOT NULL,
  [AddIp] [varchar](50) NULL,
  [GradeId] [int] NULL,
  [IsPublishToZyk] [bit] NOT NULL,
  [FlvHref] [nvarchar](256) NULL,
  [FlvThumbNailHref] [nvarchar](256) NULL,
  [UnitId] [int] NULL,
  [PushState] [int] NOT NULL,
  [PushUserId] [int] NULL,
  [OrginPathInfo] [varchar](1024) NOT NULL,
  [UnitPathInfo] [varchar](1024) NOT NULL,
  [ApprovedPathInfo] [varchar](1024) NULL,
  [RcmdPathInfo] [varchar](1024) NULL,
 CONSTRAINT [PK_Jtr_Resource] PRIMARY KEY CLUSTERED 
(
  [ResourceId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_Jtr_Resource_SysCateId] ON [dbo].[Jtr_Resource] 
(
  [SysCateId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[B_Category]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[B_Category](
  [CategoryId] [int] IDENTITY(1,1) NOT NULL,
  [ObjectUuid] [varchar](40) NOT NULL,
  [Name] [nvarchar](64) NULL,
  [ItemType] [varchar](32) NOT NULL,
  [ParentId] [int] NULL,
  [ParentPath] [varchar](255) NULL,
  [OrderNum] [int] NOT NULL,
  [ChildNum] [int] NOT NULL,
  [Description] [ntext] NULL,
  [ItemNum] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [LastModified] [datetime] NOT NULL,
  [IsSystem] [bit] NOT NULL,
 CONSTRAINT [PK_B_Category] PRIMARY KEY CLUSTERED 
(
  [CategoryId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_B_Category_ItemType] ON [dbo].[B_Category] 
(
  [ItemType] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[EvaluationPlan]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[EvaluationPlan](
  [EvaluationPlanId] [int] IDENTITY(1,1) NOT NULL,
  [EvaluationCaption] [nvarchar](1024) NULL,
  [MetaSubjectId] [int] NULL,
  [MetaGradeId] [int] NULL,
  [StartDate] [datetime] NULL,
  [EndDate] [datetime] NULL,
  [Enabled] [bit] NOT NULL,
  [TeacherId] [int] NULL,
  [TeacherName] [nvarchar](50) NULL,
  [TeachDate] [datetime] NULL,
  [CreateDate] [datetime] NOT NULL,
  [CreaterId] [int] NULL,
  [CreaterName] [nvarchar](50) NULL,
 CONSTRAINT [PK_EvaluationPlan] PRIMARY KEY CLUSTERED 
(
  [EvaluationPlanId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[P_Widget]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[P_Widget](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [Name] [varchar](50) NOT NULL,
  [Title] [nvarchar](255) NOT NULL,
  [Module] [varchar](50) NULL,
  [CreateDate] [datetime] NOT NULL,
  [PageId] [int] NOT NULL,
  [Data] [ntext] NULL,
  [IsHidden] [bit] NOT NULL,
  [ItemOrder] [smallint] NOT NULL,
  [ColumnIndex] [smallint] NOT NULL,
  [RowIndex] [smallint] NOT NULL,
  [CustomTemplate] [ntext] NULL,
  [Icon] [varchar](256) NULL,
 CONSTRAINT [PK_P_Widget] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_P_Widget_PageId] ON [dbo].[P_Widget] 
(
  [PageId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[EvaluationTemplate]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[EvaluationTemplate](
  [EvaluationTemplateId] [int] IDENTITY(1,1) NOT NULL,
  [EvaluationTemplateName] [nvarchar](50) NOT NULL,
  [TemplateFile] [varchar](1024) NULL,
  [Enabled] [bit] NOT NULL,
 CONSTRAINT [PK_EvaluationTemplate] PRIMARY KEY CLUSTERED 
(
  [EvaluationTemplateId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[EvaluationContent]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[EvaluationContent](
  [EvaluationContentId] [int] IDENTITY(1,1) NOT NULL,
  [PublishUserId] [int] NOT NULL,
  [PublishUserName] [nvarchar](50) NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [EvaluationPlanId] [int] NOT NULL,
  [EvaluationTemplateId] [int] NOT NULL,
  [PublishContent] [ntext] NULL,
 CONSTRAINT [PK_EvaluationContent] PRIMARY KEY CLUSTERED 
(
  [EvaluationContentId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_EvaluationContent_EvaluationPlanId] ON [dbo].[EvaluationContent] 
(
  [EvaluationPlanId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_EvaluationContent_EvaluationTemplateId] ON [dbo].[EvaluationContent] 
(
  [EvaluationTemplateId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[C_PrepareCourseVideo]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[C_PrepareCourseVideo](
  [PrepareCourseVideoId] [int] IDENTITY(1,1) NOT NULL,
  [VideoId] [int] NOT NULL,
  [PrepareCourseId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [VideoTitle] [nvarchar](125) NOT NULL,
 CONSTRAINT [PK_C_PrepareCourseVideo] PRIMARY KEY CLUSTERED 
(
  [PrepareCourseVideoId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_C_PrepareCourseVideo_PrepareCourseId] ON [dbo].[C_PrepareCourseVideo] 
(
  [PrepareCourseId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_C_PrepareCourseVideo_VideoId] ON [dbo].[C_PrepareCourseVideo] 
(
  [VideoId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[EvaluationTemplateFields]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[EvaluationTemplateFields](
  [FieldsId] [int] IDENTITY(1,1) NOT NULL,
  [EvaluationTemplateId] [int] NOT NULL,
  [FieldsCaption] [nvarchar](1024) NULL,
  [FieldsName] [varchar](1024) NULL,
 CONSTRAINT [PK_EvaluationTemplateFields] PRIMARY KEY CLUSTERED 
(
  [FieldsId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[C_PrepareCourseRelated]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[C_PrepareCourseRelated](
  [PrepareCourseRelatedId] [int] IDENTITY(1,1) NOT NULL,
  [PrepareCourseId] [int] NOT NULL,
  [RelatedPrepareCourseId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
 CONSTRAINT [PK_C_PrepareCourseRelated] PRIMARY KEY CLUSTERED 
(
  [PrepareCourseRelatedId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_C_PrepareCourseRelated_PrepareCourseId] ON [dbo].[C_PrepareCourseRelated] 
(
  [PrepareCourseId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[C_PrepareCourseEdit]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[C_PrepareCourseEdit](
  [PrepareCourseEditId] [int] IDENTITY(1,1) NOT NULL,
  [PrepareCourseId] [int] NOT NULL,
  [EditDate] [datetime] NOT NULL,
  [EditUserId] [int] NOT NULL,
  [Content] [ntext] NOT NULL,
  [LockStatus] [int] NOT NULL,
 CONSTRAINT [PK_C_PrepareCourseEdit] PRIMARY KEY CLUSTERED 
(
  [PrepareCourseEditId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_C_PrepareCourseEdit_EditUserId] ON [dbo].[C_PrepareCourseEdit] 
(
  [EditUserId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_C_PrepareCourseEdit_PrepareCourseId] ON [dbo].[C_PrepareCourseEdit] 
(
  [PrepareCourseId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BBS_Boards]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[BBS_Boards](
  [BoardId] [int] IDENTITY(1,1) NOT NULL,
  [Name] [varchar](50) NOT NULL,
  [Title] [varchar](128) NOT NULL,
  [VisitorCount] [int] NOT NULL,
  [TopicCount] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [BoardMaster] [char](50) NULL,
 CONSTRAINT [PK_BBS_Board] PRIMARY KEY CLUSTERED 
(
  [BoardId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[G_GroupNews]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[G_GroupNews](
  [NewsId] [int] IDENTITY(1,1) NOT NULL,
  [GroupId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [Title] [nvarchar](255) NOT NULL,
  [Content] [ntext] NULL,
  [Picture] [nvarchar](255) NULL,
  [Status] [int] NOT NULL,
  [NewsType] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [ViewCount] [int] NOT NULL,
 CONSTRAINT [PK_G_GroupNews] PRIMARY KEY CLUSTERED 
(
  [NewsId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_G_GroupNews_GroupId] ON [dbo].[G_GroupNews] 
(
  [GroupId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[C_PrepareCourseMember]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[C_PrepareCourseMember](
  [PrepareCourseMemberId] [int] IDENTITY(1,1) NOT NULL,
  [PrepareCourseId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [PrivateContent] [ntext] NULL,
  [ReplyCount] [int] NOT NULL,
  [JoinDate] [datetime] NOT NULL,
  [Status] [int] NOT NULL,
  [ContentLastupdated] [datetime] NOT NULL,
  [BestState] [bit] NOT NULL,
  [ContentType] [smallint] NOT NULL,
 CONSTRAINT [PK_C_PrepareCourseMember] PRIMARY KEY CLUSTERED 
(
  [PrepareCourseMemberId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_C_PrepareCourseMember_PrepareCourseId] ON [dbo].[C_PrepareCourseMember] 
(
  [PrepareCourseId] ASC
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_C_PrepareCourseMember_UserId] ON [dbo].[C_PrepareCourseMember] 
(
  [UserId] ASC
) ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[fn_IntTo36]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/*10进制转换成36进制*/
CREATE  FUNCTION [dbo].[fn_IntTo36](@i int) 
returns varchar(15)  
begin 
  declare @r varchar(10)   
  set @r=''
 
  while @i/36>0 
  begin
    set @r=(case when (@i % 36)<=9 then convert(varchar(1),@i % 36) 
           when (@i % 36)=10 then 'A' 
           when (@i % 36)=11 then 'B' 
           when (@i % 36)=12 then 'C' 
           when (@i % 36)=13 then 'D' 
           when (@i % 36)=14 then 'E' 
           when (@i % 36)=15 then 'F' 
           when (@i % 36)=16 then 'G'
           when (@i % 36)=17 then 'H'
           when (@i % 36)=18 then 'I'
           when (@i % 36)=19 then 'J'
           when (@i % 36)=20 then 'K'
           when (@i % 36)=21 then 'L'
           when (@i % 36)=22 then 'M'
           when (@i % 36)=23 then 'N'
           when (@i % 36)=24 then 'O'
           when (@i % 36)=25 then 'P'
           when (@i % 36)=26 then 'Q'
             when (@i % 36)=27 then 'R'
           when (@i % 36)=28 then 'S'
           when (@i % 36)=29 then 'T'
           when (@i % 36)=30 then 'U'
           when (@i % 36)=31 then 'V'
           when (@i % 36)=32 then 'W'
           when (@i % 36)=33 then 'X'
           when (@i % 36)=34 then 'Y'
           when (@i % 36)=35 then 'Z'
         end) +@r 

    set @i=@i/36
  end 

  if @i>0  
    set @r=(case when (@i % 36)<=9 then convert(varchar(1),@i % 36) 
           when (@i % 36)=10 then 'A' 
           when (@i % 36)=11 then 'B' 
           when (@i % 36)=12 then 'C' 
           when (@i % 36)=13 then 'D' 
           when (@i % 36)=14 then 'E' 
           when (@i % 36)=15 then 'F' 
           when (@i % 36)=16 then 'G'
           when (@i % 36)=17 then 'H'
           when (@i % 36)=18 then 'I'
           when (@i % 36)=19 then 'J'
           when (@i % 36)=20 then 'K'
           when (@i % 36)=21 then 'L'
           when (@i % 36)=22 then 'M'
           when (@i % 36)=23 then 'N'
           when (@i % 36)=24 then 'O'
           when (@i % 36)=25 then 'P'
           when (@i % 36)=26 then 'Q'
             when (@i % 36)=27 then 'R'
           when (@i % 36)=28 then 'S'
           when (@i % 36)=29 then 'T'
           when (@i % 36)=30 then 'U'
           when (@i % 36)=31 then 'V'
           when (@i % 36)=32 then 'W'
           when (@i % 36)=33 then 'X'
           when (@i % 36)=34 then 'Y'
           when (@i % 36)=35 then 'Z'
         end) +@r 

  return @r
 
end
GO
/****** Object:  Table [dbo].[C_PrepareCoursePrivateComment]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[C_PrepareCoursePrivateComment](
  [PrepareCoursePrivateCommentId] [int] IDENTITY(1,1) NOT NULL,
  [Title] [nvarchar](255) NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [PrepareCourseId] [int] NOT NULL,
  [CommentedUserId] [int] NOT NULL,
  [CommentUserId] [int] NOT NULL,
  [PrepareCourseMemberId] [int] NOT NULL,
  [Content] [ntext] NOT NULL,
  [ReferIP] [varchar](50) NULL,
 CONSTRAINT [PK_C_PrepareCoursePrivateComment] PRIMARY KEY CLUSTERED 
(
  [PrepareCoursePrivateCommentId] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_C_PrepareCoursePrivateComment_PrepareCourseId] ON [dbo].[C_PrepareCoursePrivateComment] 
(
  [PrepareCourseId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[G_GroupPhoto]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[G_GroupPhoto](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [GroupId] [int] NOT NULL,
  [PhotoId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [GroupCateId] [int] NULL,
  [PubDate] [datetime] NOT NULL,
  [isGroupBest] [bit] NOT NULL,
 CONSTRAINT [PK_G_GroupPhoto] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_G_GroupPhoto_GroupId] ON [dbo].[G_GroupPhoto] 
(
  [GroupId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[EvaluationResource]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[EvaluationResource](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [EvaluationPlanId] [int] NOT NULL,
  [ResourceId] [int] NOT NULL,
  [ResourceTitle] [nvarchar](512) NULL,
  [ResourceHref] [varchar](1024) NULL,
  [CreateDate] [datetime] NOT NULL,
 CONSTRAINT [PK_EvaluationResource] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_EvaluationResource_EvaluationPlanId] ON [dbo].[EvaluationResource] 
(
  [EvaluationPlanId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[G_GroupMutil]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[G_GroupMutil](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [WidgetId] [int] NOT NULL,
  [ArticleCateId] [int] NULL,
  [ArticleNumShow] [int] NULL,
  [ResourceCateId] [int] NULL,
  [ResourceNumShow] [int] NULL,
  [PhotoCateId] [int] NULL,
  [PhotoNumShow] [int] NULL,
  [VideoCateId] [int] NULL,
  [VideoNumShow] [int] NULL,
 CONSTRAINT [PK_G_GroupMutil_Id] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[G_GroupArticle]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[G_GroupArticle](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [GroupId] [int] NOT NULL,
  [ArticleId] [int] NOT NULL,
  [UserId] [int] NOT NULL,
  [PubDate] [datetime] NOT NULL,
  [IsGroupBest] [bit] NOT NULL,
  [GroupCateId] [int] NULL,
  [Title] [nvarchar](256) NULL,
  [LoginName] [varchar](256) NULL,
  [UserTrueName] [nvarchar](256) NULL,
  [CreateDate] [datetime] NULL,
  [ArticleState] [bit] NOT NULL,
  [TypeState] [bit] NOT NULL,
 CONSTRAINT [PK_G_GroupArticle] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_G_GroupArticle_GroupId] ON [dbo].[G_GroupArticle] 
(
  [GroupId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[U_FriendThings]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[U_FriendThings](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [UserId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [Title] [nvarchar](128) NOT NULL,
  [Url] [varchar](128) NULL,
  [Content] [nvarchar](512) NULL,
 CONSTRAINT [PK_U_FriendThings] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Jtr_ResType]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jtr_ResType](
  [TC_ID] [int] IDENTITY(1,1) NOT NULL,
  [TC_Title] [nvarchar](256) NOT NULL,
  [TC_Code] [varchar](256) NULL,
  [TC_Parent] [int] NOT NULL,
  [TC_Sort] [int] NULL,
  [TC_Flag] [smallint] NULL,
  [TC_Comments] [ntext] NULL,
 CONSTRAINT [PK_Jtr_ResType] PRIMARY KEY CLUSTERED 
(
  [TC_ID] ASC
) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Jitar_TimerCount]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jitar_TimerCount](
  [CountId] [int] NOT NULL,
  [UserCount] [int] NOT NULL,
  [GroupCount] [int] NOT NULL,
  [TotalArticleCount] [int] NOT NULL,
  [TotalResourceCount] [int] NOT NULL,
  [CommentCount] [int] NOT NULL,
  [PhotoCount] [int] NOT NULL,
  [VideoCount] [int] NOT NULL,
  [PrepareCourseCount] [int] NOT NULL,
  [TodayArticleCount] [int] NOT NULL,
  [YesterdayArticleCount] [int] NOT NULL,
  [HistoryArticleCount] [int] NOT NULL,
  [TodayResourceCount] [int] NOT NULL,
  [YesterdayResourceCount] [int] NOT NULL,
  [CountDate] [varchar](10) NOT NULL,
 CONSTRAINT [PK_Jitar_TimerCount] PRIMARY KEY CLUSTERED 
(
  [CountId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[S_SpecialSubject]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[S_SpecialSubject](
  [SpecialSubjectId] [int] IDENTITY(1,1) NOT NULL,
  [ObjectGuid] [varchar](40) NOT NULL,
  [Title] [nvarchar](255) NOT NULL,
  [Logo] [nvarchar](512) NULL,
  [Description] [nvarchar](3000) NULL,
  [CreateDate] [datetime] NOT NULL,
  [CreateUserId] [int] NOT NULL,
  [ExpiresDate] [datetime] NOT NULL,
  [ObjectType] [varchar](50) NULL,
  [ObjectId] [int] NULL,
 CONSTRAINT [PK_S_SpecialSubject] PRIMARY KEY CLUSTERED 
(
  [SpecialSubjectId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[U_Friend]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[U_Friend](
  [Id] [int] IDENTITY(1,1) NOT NULL,
  [UserId] [int] NOT NULL,
  [FriendId] [int] NOT NULL,
  [AddTime] [datetime] NOT NULL,
  [TypeId] [int] NULL,
  [Remark] [nvarchar](255) NULL,
  [IsBlack] [bit] NOT NULL,
 CONSTRAINT [PK_U_Friend] PRIMARY KEY CLUSTERED 
(
  [Id] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_U_Friend_UserId] ON [dbo].[U_Friend] 
(
  [UserId] ASC
) ON [PRIMARY]
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'对象标识。' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_Friend', @level2type=N'COLUMN',@level2name=N'Id'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'用户标识。' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_Friend', @level2type=N'COLUMN',@level2name=N'UserId'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'用户的好友标识。' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_Friend', @level2type=N'COLUMN',@level2name=N'FriendId'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'添加时间。' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_Friend', @level2type=N'COLUMN',@level2name=N'AddTime'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'好友分类标识。为空表示未分类。' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_Friend', @level2type=N'COLUMN',@level2name=N'TypeId'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'用户给此好友添加的额外说明。' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_Friend', @level2type=N'COLUMN',@level2name=N'Remark'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_Description', @value=N'是否是黑名单，这是特殊分类。' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'U_Friend', @level2type=N'COLUMN',@level2name=N'IsBlack'
GO
/****** Object:  Table [dbo].[S_SiteTheme]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[S_SiteTheme](
  [SiteThemeId] [int] IDENTITY(1,1) NOT NULL,
  [Title] [nvarchar](50) NOT NULL,
  [Folder] [varchar](50) NOT NULL,
  [Status] [bit] NOT NULL,
 CONSTRAINT [PK_S_SiteTheme] PRIMARY KEY CLUSTERED 
(
  [SiteThemeId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Jitar_SpecialSubjectArticle]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS OFF
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jitar_SpecialSubjectArticle](
  [SpecialSubjectArticleId] [int] IDENTITY(1,1) NOT NULL,
  [ArticleId] [int] NOT NULL,
  [ArticleGuid] [uniqueidentifier] NOT NULL,
  [Title] [nvarchar](256) NOT NULL,
  [UserId] [int] NOT NULL,
  [LoginName] [varchar](50) NOT NULL,
  [UserTrueName] [nvarchar](50) NOT NULL,
  [SpecialSubjectId] [int] NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [ArticleState] [bit] NOT NULL,
  [TypeState] [bit] NOT NULL,
 CONSTRAINT [PK_S_SpecialSubjectArticle] PRIMARY KEY CLUSTERED 
(
  [SpecialSubjectArticleId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_Jitar_SpecialSubjectArticle_SpecialSubjectId] ON [dbo].[Jitar_SpecialSubjectArticle] 
(
  [SpecialSubjectId] ASC
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Jitar_WeekViewCountArticle]    Script Date: 09/16/2013 16:02:52 ******/
SET ANSI_NULLS OFF
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Jitar_WeekViewCountArticle](
  [ArticleId] [int] NOT NULL,
  [Title] [nvarchar](256) NOT NULL,
  [CreateDate] [datetime] NOT NULL,
  [TypeState] [bit] NOT NULL,
  [UserId] [int] NOT NULL,
  [UserIcon] [varchar](256) NULL,
  [TrueName] [nvarchar](256) NOT NULL,
  [LoginName] [varchar](256) NOT NULL,
  [ViewCount] [int] NULL,
 CONSTRAINT [PK_Jitar_WeekViewCountArticle] PRIMARY KEY CLUSTERED 
(
  [ArticleId] ASC
) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  StoredProcedure [dbo].[UpdateUnitInfo]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:    <Author,,Name>
-- Create date: <Create Date,,>
-- Description: <Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[UpdateUnitInfo]
@UserId int,
@UnitId int,
@UnitPathInfo varchar(512)
AS
BEGIN

UPDATE Jitar_Article SET UnitId = @UnitId ,RecommendState = 0, OrginPath = @UnitPathInfo, UnitPathInfo = @UnitPathInfo, ApprovedPathInfo = @UnitPathInfo, RcmdPathInfo = null WHERE userId = @userId
UPDATE Jtr_Resource SET UnitId = @UnitId , RecommendState = 0, OrginPathInfo = @UnitPathInfo, UnitPathInfo = @UnitPathInfo, ApprovedPathInfo = @UnitPathInfo, RcmdPathInfo = null WHERE userId = @userId;
UPDATE B_Photo SET UnitId = @UnitId WHERE userId = @UserId
UPDATE B_Video SET UnitId = @UnitId WHERE userId = @UserId
--更新历史文章
DECLARE @SQL as varchar(8000)
DECLARE @BackYear int
DECLARE BackYear_Cursor CURSOR FOR
SELECT BackYear FROM Jitar_BackYear
OPEN BackYear_Cursor
-- 移动游标到第一行
FETCH NEXT FROM BackYear_Cursor INTO @BackYear
WHILE @@FETCH_STATUS = 0
 BEGIN
 SET @SQL = 'UPDATE HtmlArticle' + LTRIM(STR(@BackYear)) + ' SET UnitId = ' + STR(@UnitId) + ', RecommendState = 0, OrginPath = ''' + @UnitPathInfo + ''', UnitPathInfo = ''' + @UnitPathInfo + ''', ApprovedPathInfo = ''' + @UnitPathInfo + ''', RcmdPathInfo = null WHERE userId = ' + STR(@UserId)
 EXEC(@SQL)
 FETCH NEXT FROM BackYear_Cursor INTO @BackYear
END
CLOSE BackYear_Cursor
DEALLOCATE BackYear_Cursor
END
GO
/****** Object:  StoredProcedure [dbo].[ChannelUserStat]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:    mxh
-- Create date: <Create Date,,>
-- Description: 对频道进行用户查询
-- =============================================
CREATE PROCEDURE [dbo].[ChannelUserStat] 
@KeyWords NVARCHAR(256),
@Filter VARCHAR(50),
@StartDate VARCHAR(50),
@EndDate VARCHAR(50),
@StatGuid  VARCHAR(50),
@ChannelId int
AS
BEGIN
  print '开始执行存储过程'
  /* 先删除已经存在的查询，也就是1天前过期的查询结果 */
  DELETE FROM Jitar_ChannelUserStat WHERE DateDiff(day,CreateDate,getDate()) > 1
  /* 删除相同的查询记录，以便重新进行查询 */
  DELETE FROM Jitar_ChannelUserStat WHERE StatGuid = @StatGuid 
  print '删除结束'
  
  /* 开始新的一次查询 */
  /* 如果存在查询条件，则按查询条件进行查询，这样可以加快速度 */
  IF @KeyWords IS NOT NULL AND @Filter IS NOT NULL AND @KeyWords <> '' AND @Filter <> ''
  BEGIN
  /* 创建临时表，存储过滤出来的要查询的用户信息 */
  /* CREATE TABLE #ChannelUser(Id int IDENTITY(1,1) NOT NULL,UserId int,PRIMARY KEY (Id)) */
    IF @Filter = 'loginName'
      BEGIN
      INSERT INTO Jitar_ChannelUserStat(ChannelId,StatGuid,UserId,UnitId) 
      SELECT @ChannelId, @StatGuid, Jitar_ChannelUser.UserId, Jitar_ChannelUser.UnitId FROM Jitar_ChannelUser LEFT JOIN Jitar_User ON Jitar_ChannelUser.UserId = Jitar_User.UserId WHERE Jitar_User.LoginName = @KeyWords AND Jitar_ChannelUser.ChannelId=@ChannelId
      END
   IF @Filter = 'trueName'  
      BEGIN
      INSERT INTO Jitar_ChannelUserStat(ChannelId,StatGuid,UserId,UnitId) 
      SELECT @ChannelId, @StatGuid, Jitar_ChannelUser.UserId, Jitar_ChannelUser.UnitId FROM Jitar_ChannelUser LEFT JOIN Jitar_User ON Jitar_ChannelUser.UserId = Jitar_User.UserId WHERE Jitar_User.TrueName = @KeyWords AND Jitar_ChannelUser.ChannelId=@ChannelId
      END
   IF @Filter = 'unitTitle'  
      BEGIN
      INSERT INTO Jitar_ChannelUserStat(ChannelId,StatGuid,UserId,UnitId) 
      SELECT @ChannelId, @StatGuid, Jitar_ChannelUser.UserId, Jitar_ChannelUser.UnitId FROM Jitar_ChannelUser LEFT JOIN Jitar_Unit ON Jitar_ChannelUser.UnitId = Jitar_Unit.UnitId WHERE Jitar_Unit.UnitTitle = @KeyWords AND Jitar_ChannelUser.ChannelId=@ChannelId
      END
   END
  ELSE
   BEGIN
   /* 没有查询条件的情况，全部查询 */
   INSERT INTO Jitar_ChannelUserStat(ChannelId,StatGuid,UserId,UnitId)
   SELECT @ChannelId, @StatGuid, Jitar_ChannelUser.UserId, Jitar_ChannelUser.UnitId FROM Jitar_ChannelUser WHERE Jitar_ChannelUser.ChannelId = @ChannelId
   END
  print '要查询的用户已经生成'
  /* 要查询的用户已经生成，下面就开始进行查询 */
  DECLARE @UserId int
  DECLARE @ArticleCount int
  DECLARE @ResourceCount int
  DECLARE @PhotoCount int
  DECLARE @VideoCount int
  DECLARE @LoginName NVARCHAR(256)
  DECLARE @UserTrueName NVARCHAR(256)
  DECLARE @UnitTitle NVARCHAR(256)
  SET @ArticleCount = 0
  SET @ResourceCount = 0
  SET @PhotoCount = 0
  SET @VideoCount = 0   
  
  DECLARE ChannelUserStat_Cursor CURSOR FOR
  SELECT UserId FROM Jitar_ChannelUserStat WHERE ChannelId=@ChannelId And StatGuid=@StatGuid
  OPEN ChannelUserStat_Cursor
  FETCH NEXT FROM ChannelUserStat_Cursor INTO @UserId
  WHILE @@FETCH_STATUS = 0
    BEGIN
    print '正在执行' + str(@UserId)
    IF @StartDate IS NULL OR @StartDate = ''
      BEGIN
      print '执行  @StartDate IS NULL'
      SET @ArticleCount = (SELECT COUNT(*) FROM Jitar_ChannelArticle WHERE Jitar_ChannelArticle.ChannelId = @ChannelId AND UserId = @UserId AND ArticleState = 1)
      SET @ResourceCount = (SELECT COUNT(*) FROM Jitar_ChannelResource LEFT JOIN Jtr_Resource ON Jtr_Resource.ResourceId = Jitar_ChannelResource.ResourceId  WHERE Jitar_ChannelResource.ChannelId = @ChannelId AND Jitar_ChannelResource.UserId = @UserId AND Jtr_Resource.AuditState = 0 AND Jtr_Resource.DelState = 0 AND Jtr_Resource.ShareMode > 500)
      SET @PhotoCount = (SELECT COUNT(*) FROM Jitar_ChannelPhoto LEFT JOIN B_Photo ON B_Photo.PhotoId = Jitar_ChannelPhoto.PhotoId WHERE Jitar_ChannelPhoto.ChannelId = @ChannelId AND Jitar_ChannelPhoto.UserId = @UserId And B_Photo.AuditState = 0 AND B_Photo.DelState = 0 And B_Photo.IsPrivateShow = 0)
      SET @VideoCount = (SELECT COUNT(*) FROM Jitar_ChannelVideo LEFT JOIN B_Video ON B_Video.VideoId = Jitar_ChannelVideo.VideoId WHERE Jitar_ChannelVideo.ChannelId = @ChannelId AND Jitar_ChannelVideo.UserId = @UserId AND B_Video.V_AuditState = 0)
      END
    ELSE
      BEGIN
      SET @ArticleCount = (SELECT COUNT(*) FROM Jitar_ChannelArticle WHERE ChannelId = @ChannelId AND UserId = @UserId AND ArticleState = 1 AND CreateDate BETWEEN @StartDate AND @EndDate)
      SET @ResourceCount = (SELECT COUNT(*) FROM Jitar_ChannelResource LEFT JOIN Jtr_Resource ON Jtr_Resource.ResourceId = Jitar_ChannelResource.ResourceId WHERE Jitar_ChannelResource.ChannelId = @ChannelId AND Jitar_ChannelResource.UserId = @UserId And Jtr_Resource.CreateDate BETWEEN @StartDate AND @EndDate AND Jtr_Resource.AuditState = 0 AND Jtr_Resource.DelState = 0 AND Jtr_Resource.ShareMode > 500)
      SET @PhotoCount = (SELECT COUNT(*) FROM Jitar_ChannelPhoto LEFT JOIN B_Photo ON B_Photo.PhotoId = Jitar_ChannelPhoto.PhotoId WHERE Jitar_ChannelPhoto.ChannelId = @ChannelId AND Jitar_ChannelPhoto.UserId = @UserId AND B_Photo.CreateDate BETWEEN @StartDate AND @EndDate And B_Photo.AuditState = 0 AND B_Photo.DelState = 0 And B_Photo.IsPrivateShow = 0 )
      SET @VideoCount = (SELECT COUNT(*) FROM Jitar_ChannelVideo LEFT JOIN B_Video ON B_Video.VideoId = Jitar_ChannelVideo.VideoId WHERE Jitar_ChannelVideo.ChannelId = @ChannelId AND Jitar_ChannelVideo.UserId = @UserId AND B_Video.V_CreateDate BETWEEN @StartDate AND @EndDate AND B_Video.V_AuditState = 0)
      END
    print '@ArticleCount=' + STR(@ArticleCount)
    UPDATE Jitar_ChannelUserStat SET ArticleCount = @ArticleCount, ResourceCount = @ResourceCount,PhotoCount=@PhotoCount,VideoCount=@VideoCount WHERE ChannelId=@ChannelId AND StatGuid=@StatGuid AND UserId=@UserId
    FETCH NEXT FROM ChannelUserStat_Cursor INTO @UserId
    END
    CLOSE ChannelUserStat_Cursor
    DEALLOCATE ChannelUserStat_Cursor
  
  /* 更新单位名称，用户真实姓名 */ 
  UPDATE Jitar_ChannelUserStat SET LoginName = (SELECT LoginName FROM Jitar_User WHERE Jitar_ChannelUserStat.UserId = Jitar_User.UserId)
  UPDATE Jitar_ChannelUserStat SET UserTrueName = (SELECT TrueName FROM Jitar_User WHERE Jitar_ChannelUserStat.UserId = Jitar_User.UserId)
  UPDATE Jitar_ChannelUserStat SET UnitTitle = (SELECT UnitTitle FROM Jitar_Unit WHERE Jitar_ChannelUserStat.UnitId = Jitar_Unit.UnitId)
END
GO
/****** Object:  StoredProcedure [dbo].[ChannelUnitStat]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:    <Author,,Name>
-- Create date: <Create Date,,>
-- Description: <Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[ChannelUnitStat]
  @StartDate VARCHAR(50),
  @EndDate VARCHAR(50),
  @StatGuid  VARCHAR(50),
  @ChannelId int
AS
BEGIN
  /* 先删除已经存在的查询，也就是1天前过期的查询结果 */
  DELETE FROM Jitar_ChannelUnitStat WHERE DateDiff(day,CreateDate,getDate()) > 1
  /* 删除相同的查询记录，以便重新进行查询 */
  DELETE FROM Jitar_ChannelUnitStat WHERE StatGuid = @StatGuid
  
  /* 先找到所有单位 */  
  INSERT INTO Jitar_ChannelUnitStat(UnitId,ChannelId, StatGuid) SELECT DISTINCT UnitId,@ChannelId,@StatGuid  FROM Jitar_ChannelUser WHERE ChannelId=@ChannelId
  
  DECLARE @UnitId int
  DECLARE @UserCount int
  DECLARE @ArticleCount int
  DECLARE @ResourceCount int
  DECLARE @PhotoCount int
  DECLARE @VideoCount int
  DECLARE @LoginName NVARCHAR(256)
  DECLARE @UserTrueName NVARCHAR(256)
  DECLARE @UnitTitle NVARCHAR(256)
  SET @UserCount = 0
  SET @ArticleCount = 0
  SET @ResourceCount = 0
  SET @PhotoCount = 0
  SET @VideoCount = 0   
  
  /* 统计每单位的信息 */
  DECLARE ChannelUnitStat_Cursor CURSOR FOR
  SELECT UnitId FROM Jitar_ChannelUnitStat WHERE ChannelId=@ChannelId And StatGuid=@StatGuid
  OPEN ChannelUnitStat_Cursor
  FETCH NEXT FROM ChannelUnitStat_Cursor INTO @UnitId
  WHILE @@FETCH_STATUS = 0
    BEGIN   
    IF @StartDate IS NULL OR @StartDate = ''
      BEGIN
      SET @ArticleCount = (SELECT COUNT(*) FROM Jitar_ChannelArticle LEFT JOIN Jitar_User ON Jitar_ChannelArticle.UserId = Jitar_User.UserId WHERE Jitar_ChannelArticle.ChannelId = @ChannelId AND Jitar_ChannelArticle.ArticleState = 1 AND Jitar_User.UnitId=@UnitId)
      SET @ResourceCount = (SELECT COUNT(*) FROM Jitar_ChannelResource LEFT JOIN Jtr_Resource ON Jtr_Resource.ResourceId = Jitar_ChannelResource.ResourceId  WHERE Jitar_ChannelResource.ChannelId = @ChannelId AND Jitar_ChannelResource.UnitId = @UnitId AND Jtr_Resource.AuditState = 0 AND Jtr_Resource.DelState = 0 AND Jtr_Resource.ShareMode > 500)
      SET @PhotoCount = (SELECT COUNT(*) FROM Jitar_ChannelPhoto LEFT JOIN B_Photo ON B_Photo.PhotoId = Jitar_ChannelPhoto.PhotoId WHERE Jitar_ChannelPhoto.ChannelId = @ChannelId AND Jitar_ChannelPhoto.UnitId = @UnitId And B_Photo.AuditState = 0 AND B_Photo.DelState = 0 And B_Photo.IsPrivateShow = 0)
      SET @VideoCount = (SELECT COUNT(*) FROM Jitar_ChannelVideo LEFT JOIN B_Video ON B_Video.VideoId = Jitar_ChannelVideo.VideoId WHERE Jitar_ChannelVideo.ChannelId = @ChannelId AND Jitar_ChannelVideo.UnitId = @UnitId AND B_Video.V_AuditState = 0)
      END
    ELSE
      BEGIN
      SET @ArticleCount = (SELECT COUNT(*) FROM Jitar_ChannelArticle LEFT JOIN Jitar_User ON Jitar_ChannelArticle.UserId = Jitar_User.UserId WHERE Jitar_ChannelArticle.ChannelId = @ChannelId AND Jitar_ChannelArticle.ArticleState = 1 AND Jitar_User.UnitId=@UnitId And Jitar_ChannelArticle.CreateDate BETWEEN @StartDate AND @EndDate)
      SET @ResourceCount = (SELECT COUNT(*) FROM Jitar_ChannelResource LEFT JOIN Jtr_Resource ON Jtr_Resource.ResourceId = Jitar_ChannelResource.ResourceId WHERE Jitar_ChannelResource.ChannelId = @ChannelId AND Jitar_ChannelResource.UnitId = @UnitId AND Jtr_Resource.CreateDate BETWEEN @StartDate AND @EndDate AND Jtr_Resource.AuditState = 0 AND Jtr_Resource.DelState = 0 AND Jtr_Resource.ShareMode > 500)
      SET @PhotoCount = (SELECT COUNT(*) FROM Jitar_ChannelPhoto LEFT JOIN B_Photo ON B_Photo.PhotoId = Jitar_ChannelPhoto.PhotoId WHERE Jitar_ChannelPhoto.ChannelId = @ChannelId AND Jitar_ChannelPhoto.UnitId = @UnitId AND B_Photo.CreateDate BETWEEN @StartDate AND @EndDate And B_Photo.AuditState = 0 AND B_Photo.DelState = 0 And B_Photo.IsPrivateShow = 0 )
      SET @VideoCount = (SELECT COUNT(*) FROM Jitar_ChannelVideo LEFT JOIN B_Video ON B_Video.VideoId = Jitar_ChannelVideo.VideoId WHERE Jitar_ChannelVideo.ChannelId = @ChannelId AND Jitar_ChannelVideo.UnitId = @UnitId AND B_Video.V_CreateDate BETWEEN @StartDate AND @EndDate AND B_Video.V_AuditState = 0)
      END
    
    SET @UserCount = (SELECT COUNT(*) FROM Jitar_ChannelUser WHERE UnitId=@UnitId)  
    UPDATE Jitar_ChannelUnitStat SET UserCount=@UserCount, ArticleCount = @ArticleCount, ResourceCount = @ResourceCount,PhotoCount=@PhotoCount,VideoCount=@VideoCount WHERE ChannelId=@ChannelId AND StatGuid=@StatGuid AND UnitId=@UnitId
    FETCH NEXT FROM ChannelUnitStat_Cursor INTO @UnitId
  END
  CLOSE ChannelUnitStat_Cursor
  DEALLOCATE ChannelUnitStat_Cursor
  
  /* 更新单位名称 */  
  UPDATE Jitar_ChannelUnitStat SET UnitTitle = (SELECT UnitTitle FROM Jitar_Unit WHERE Jitar_ChannelUnitStat.UnitId = Jitar_Unit.UnitId)
  
END
GO
/****** Object:  StoredProcedure [dbo].[statOneUser]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- 统计单个用户 
CREATE PROCEDURE [dbo].[statOneUser] ( 
  @userId   int     -- 用户ID
) 
 
AS 
  BEGIN 
    DECLARE  
      @visitArticleCount int,       -- 我的文章的被访问量 
      @visitResourceCount int,      -- 我的资源的被访问量 
      @myArticleCount int,        -- 原创文章数总数 
      @otherArticleCount int,       -- 转载文章数总数 
      @resourceCount int,         -- 资源数 
      @recommendArticleCount int,     -- 推荐文章数 
      @recommendResourceCount int,    -- 推荐资源数 
      @articleCommentCount int,       -- 我的文章被评论数 
      @articleICommentCount int,      -- 我发出的文章评论数 
      @resourceCommentCount int,      -- 我的资源被评论数 
      @resourceICommentCount int,     -- 我发出的资源评论数 
      @resourceDownloadCount int,     -- 资源下载数 
      @createGroupCount int,        -- 创建协作组数 
      @jionGroupCount int,        -- 加入协作组数 
      @photoCount int,          -- 照片数 
      @videoCount int,          -- 视频数 
      @scoreMyarticleadd varchar(10),   -- 发表原创文章得分 
      @scoreOtherarticleadd varchar(10),  -- 发表转载文章得分 
      @scorearticlercmd varchar(10),    -- 文章被推荐得分 
      @scoreresourceadd varchar(10),    -- 上载资源得分 
      @scoreresourcercmd varchar(10),   -- 资源被推荐得分 
      @scorePhotoUpload varchar(10),    -- 发布照片得分 
      @scoreVideoUpload varchar(10),    -- 上传视频得分 
      @scorecommentadd varchar(10),   -- 发表评论得分 
      @scoreArticlePunish int,      -- 文章删除的罚分 
      @scoreResourcePunish int,     -- 资源删除的罚分 
      @scorePhotoPunish int,        -- 图片删除的罚分 
      @scoreVideoPunish int,        -- 视频删除的罚分 
      @scoreCommentPunish int,      -- 评论删除的罚分 
      @PrepareCourseCount int       -- 备课数
        
      SELECT @visitArticleCount = 0
      SELECT @articleCommentCount = 0
      SELECT @myArticleCount = 0
      SELECT @otherArticleCount = 0
      SELECT @recommendArticleCount = 0
      
      DECLARE @visitCount int, @CommentCount int, @articleCount int, @typeState int, @recommendState int
      
      DECLARE artCursor CURSOR FOR SELECT IsNull(SUM(viewCount), 0) as visitCount , IsNull(SUM(commentCount), 0) as CommentCount, COUNT(ArticleId) as articleCount, typeState, recommendState
      FROM Jitar_Article 
      WHERE ((userId = @userId) AND (auditState = 0) AND (delState = 0)  AND (draftState = 0) AND (hideState = 0)) 
      GROUP BY typeState, recommendState

      OPEN artCursor
      FETCH NEXT FROM artCursor INTO @visitCount, @CommentCount, @articleCount, @typeState, @recommendState
      WHILE @@FETCH_STATUS = 0
      BEGIN
        SET @visitArticleCount = @visitArticleCount + @visitCount
        SET @articleCommentCount = @articleCommentCount + @CommentCount
        if (@typeState = 0)
          BEGIN
            SET @myArticleCount = @myArticleCount + @articleCount
          END
        if (@typeState = 1)
          BEGIN
            SET @otherArticleCount = @otherArticleCount + @articleCount
          END
        if (@recommendState = 1)
          BEGIN 
            SET @recommendArticleCount = @recommendArticleCount + @articleCount
          END
        FETCH NEXT FROM artCursor INTO  @visitCount, @CommentCount, @articleCount, @typeState, @recommendState
      END
      CLOSE artCursor 
      DEALLOCATE artCursor
    
     
      -- 我的资源的被访问量 
      SELECT @visitResourceCount = IsNull(SUM(viewCount), 0)  
      FROM Jtr_Resource  
      WHERE ((userId = @userId) AND (auditState = 0) AND (delState = 0)) 
    
      SELECT @resourceCount = 0
      SELECT @resourceCommentCount = 0
      SELECT @recommendResourceCount = 0
      SELECT @resourceDownloadCount = 0
  
      DECLARE @resCount int, @rescommentCount int, @resresourceDownloadCount int 
    
      -- 资源数 ,-- 我的资源被评论数   -- 资源下载数  -- 推荐资源数 
      DECLARE resCursor CURSOR FOR SELECT COUNT(ResourceId) as resourceCount, IsNull(SUM(commentCount), 0) as resourceCommentCount, IsNull(SUM(downloadCount), 0) as resourceDownloadCount, recommendState
      FROM Jtr_Resource  
      WHERE ((userId = @userId) AND (auditState = 0) AND (delState = 0) AND (shareMode >= 500)) 
      GROUP BY recommendState
      
      OPEN resCursor
      FETCH NEXT FROM resCursor INTO @resCount, @rescommentCount, @resresourceDownloadCount, @recommendState
      WHILE @@FETCH_STATUS = 0
      BEGIN
        SET @resourceCount = @resourceCount + @resCount
        SET @resourceCommentCount = @resourceCommentCount + @rescommentCount
        SET @resourceDownloadCount = @resourceDownloadCount + @resresourceDownloadCount
        IF (@recommendState = 1)
        BEGIN
          SET @recommendResourceCount = @recommendResourceCount + @resCount
        END
        FETCH NEXT FROM resCursor INTO @resCount, @rescommentCount, @resresourceDownloadCount, @recommendState
      END
      CLOSE resCursor 
      DEALLOCATE resCursor
  
      -- 我发出的文章评论数 
      SELECT @articleICommentCount = COUNT(*) FROM B_Comment WHERE ((userId = @userId) AND (objType = 3))
      
      -- 我发出的资源评论数 
      SELECT @resourceICommentCount = COUNT(*) FROM B_Comment WHERE ((userId = @userId) AND (objType = 12))
      
      -- 创建协作组数 
      SELECT @createGroupCount = COUNT(*) FROM G_Group WHERE (createUserId = @userId)
      
      -- 加入协作组数 
      SELECT @jionGroupCount = COUNT(*) FROM G_GroupMember WHERE (userId = @userId)
      
      -- 照片数 
      SELECT @photoCount = COUNT(PhotoId) FROM B_Photo WHERE (userId = @userId)
      
      -- 视频数 
      SELECT @videoCount = COUNT(VideoId) FROM B_Video WHERE (userId = @userId)
      
      -- 删除资源的罚分 
      SELECT @scoreResourcePunish = CAST(SUM(Score) as int) from U_PunishScore Where UserId = @userId and ObjType = 12 
      select @scoreResourcePunish = isnull(@scoreResourcePunish, 0) 
     
      -- 资源总分 = ((上载资源得分 * 资源数) + (资源被推荐得分 * 推荐资源数)) 
      SELECT @scoreresourceadd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.resource.add' 
      SELECT @scoreresourcercmd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.resource.rcmd' 
      
      -- 删除文章的罚分 
      SELECT @scoreArticlePunish = CAST(SUM(Score) as int) FROM U_PunishScore WHERE UserId = @userId AND ObjType = 3 
      SELECT @scoreArticlePunish = isnull(@scoreArticlePunish, 0) 
     
      -- 文章总分 = 发表原创文章得分*原创文章数 + 发表转载文章得分*转载文章数 + 文章被推荐得分*推荐文章数 
      SELECT @scoreMyarticleadd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.my.article.add' 
      SELECT @scoreOtherarticleadd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.other.article.add' 
      SELECT @scorearticlercmd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.article.rcmd' 
      
      -- 删除评论的罚分 
      SELECT @scoreCommentPunish = CAST(SUM(Score) as int) from U_PunishScore Where UserId = @userId and ObjType = 16 
      SELECT @scoreCommentPunish = isnull(@scoreCommentPunish, 0) 
     
      -- 删除图片的罚分 
      SELECT @scorePhotoPunish = CAST(SUM(Score) as int) from U_PunishScore Where UserId = @userId and ObjType = 11 
      SELECT @scorePhotoPunish = isnull(@scorePhotoPunish, 0) 
     
      -- 删除视频的罚分 
      SELECT @scoreVideoPunish = CAST(SUM(Score) as int) from U_PunishScore Where UserId = @userId and ObjType = 17 
      SELECT @scoreVideoPunish = isnull(@scoreVideoPunish, 0) 
     
      -- 评论总分 = (我发出的文章评论数 + 我发出的资源评论数) * 发表评论得分 
      SELECT @scorecommentadd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.comment.add' 
      
      -- 图片总分 = (上传图片得分 * 上传图片数 ) 
      SELECT @scorePhotoUpload = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.photo.upload' 
     
      -- 视频总分 = (上传视频得分 * 上传视频数 ) 
      SELECT @scoreVideoUpload = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.video.upload' 
      
      --统计主备数
      Select @PrepareCourseCount=count(LeaderId) from C_PrepareCourse Where LeaderId=@userId  and Status=1
      Select @PrepareCourseCount=isnull(@PrepareCourseCount, 0)
      
      -- 更新 
      UPDATE Jitar_User SET visitArticleCount = @visitArticleCount, visitResourceCount = @visitResourceCount, myArticleCount = @myArticleCount,
        otherArticleCount = @otherArticleCount,recommendArticleCount = @recommendArticleCount,articleCommentCount = @articleCommentCount,
        articleICommentCount = @articleICommentCount,resourceCount = @resourceCount,recommendResourceCount = @recommendResourceCount,
        resourceCommentCount = @resourceCommentCount,resourceICommentCount = @resourceICommentCount,resourceDownloadCount = @resourceDownloadCount,
        createGroupCount = @createGroupCount,jionGroupCount = @jionGroupCount,photoCount = @photoCount,videoCount = @videoCount,
        articleScore = CAST(@scoreMyarticleadd as int) * @myArticleCount + CAST(@scoreOtherarticleadd as int) * @otherArticleCount +  CAST(@scorearticlercmd as int) * @recommendArticleCount, 
        ArticlePunishScore = @scoreArticlePunish * -1 ,resourceScore = CAST(@scoreresourceadd as int) * @resourceCount + CAST(@scoreresourcercmd as int) * @recommendResourceCount,
        ResourcePunishScore = @scoreResourcePunish * -1 ,commentScore = @scorecommentadd * @articleICommentCount + @scorecommentadd * @resourceICommentCount, 
        CommentPunishScore = @scoreCommentPunish * -1, PhotoPunishScore = @scorePhotoPunish * -1 ,photoScore = @scorePhotoUpload * @photoCount,
        videoScore = @scoreVideoUpload * @videoCount, videoPunishScore = @scoreVideoPunish * -1 ,PrepareCourseCount=@PrepareCourseCount
      WHERE userId = @userId 
      
      DECLARE @PunishScore int 
      SELECT @PunishScore = CAST(SUM(Score) as int) from U_PunishScore Where UserId = @userId
      SELECT @PunishScore = isnull(@PunishScore, 0) 
      -- 统计用户的所有得分 
      UPDATE Jitar_User SET UserScore = articleScore + resourceScore + commentScore + videoScore + photoScore + (-1*@PunishScore)
      UPDATE Jitar_User SET ArticleCount = MyArticleCount + OtherArticleCount
            
    END
GO
/****** Object:  StoredProcedure [dbo].[statAllUser]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[statAllUser] 
AS 
  BEGIN 
    DECLARE @PunishScore int 
    DECLARE @userId INT, @scoreMyarticleadd VARCHAR(10), @scoreOtherarticleadd VARCHAR(10), @scorearticlercmd VARCHAR(10), @scoreresourceadd VARCHAR(10), @scoreresourcercmd VARCHAR(10), @scorePhotoUpload VARCHAR(10), @scoreVideoUpload VARCHAR(10), @scorecommentadd VARCHAR(10)
    SELECT @scoreMyarticleadd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.my.article.add'
    SELECT @scoreOtherarticleadd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.other.article.add'
    SELECT @scorearticlercmd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.article.rcmd'
    SELECT @scoreresourceadd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.resource.add'
    SELECT @scoreresourcercmd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.resource.rcmd'
    SELECT @scorecommentadd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.comment.add' 
    SELECT @scorePhotoUpload = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.photo.upload' 
    SELECT @scoreVideoUpload = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.video.upload' 
    DECLARE user_cursor CURSOR FOR SELECT userId FROM Jitar_User 
    OPEN user_cursor 
    FETCH NEXT FROM user_cursor INTO @userId 
    WHILE (@@FETCH_STATUS = 0) 
      BEGIN
        UPDATE Jitar_User SET visitArticleCount = a.visitCount, articleCommentCount = a.commentCount FROM Jitar_User AS u, (SELECT userId, IsNull(SUM(viewCount), 0) AS visitCount, IsNull(SUM(commentCount), 0) AS commentCount FROM Jitar_Article WHERE ((auditState = 0) AND (delState = 0) AND (draftState = 0) AND (hideState = 0) AND (userId = @userId)) GROUP BY userId) AS a WHERE (u.userId = a.userId)
        UPDATE Jitar_User SET visitResourceCount = r.viewCount FROM Jitar_User AS u, (SELECT userId, IsNull(SUM(viewCount), 0) AS viewCount FROM Jtr_Resource WHERE ((auditState = 0) AND (delState = 0) AND (userId = @userId)) GROUP BY userId) AS r WHERE (u.userId = r.userId)
        UPDATE Jitar_User SET myArticleCount = (SELECT COUNT(*) FROM Jitar_Article WHERE ((auditState = 0) AND (delState = 0) AND (draftState = 0) AND (hideState = 0) AND (typeState = 0) AND (userId = @userId))) WHERE (userId = @userId)
        UPDATE Jitar_User SET otherArticleCount = (SELECT COUNT(*) FROM Jitar_Article WHERE ((auditState = 0) AND (delState = 0) AND (draftState = 0) AND (hideState = 0) AND (typeState = 1) AND (userId = @userId))) WHERE (userId = @userId)
        UPDATE Jitar_User SET resourceCount = r.resourceCount, resourceCommentCount = r.resourceCommentCount, resourceDownloadCount = r.resourceDownloadCount FROM Jitar_User AS u, (SELECT userId, COUNT(resourceId) AS resourceCount, IsNull(SUM(commentCount), 0) AS resourceCommentCount, IsNull(SUM(downloadCount), 0) AS resourceDownloadCount FROM Jtr_Resource WHERE ((auditState = 0) AND (delState = 0) AND (shareMode >= 500) AND (userId = @userId)) GROUP BY userId) AS r WHERE (u.userId = r.userId)
        UPDATE Jitar_User SET recommendArticleCount = a.articleCount FROM Jitar_User AS u, (SELECT userId, COUNT(articleId) AS articleCount FROM Jitar_Article WHERE ((auditState = 0) AND (delState = 0) AND (draftState = 0) AND (hideState = 0) AND (recommendState = 1) AND (userId = @userId)) GROUP BY userId) AS a WHERE (u.userId = a.userId)
        UPDATE Jitar_User SET recommendResourceCount = r.resourceCount FROM Jitar_User AS u, (SELECT userId, COUNT(resourceId) AS resourceCount FROM Jtr_Resource WHERE ((auditState = 0) AND (delState = 0) AND (shareMode >= 500) AND (recommendState = 1) AND (userId = @userId)) GROUP BY userId) AS r WHERE (u.userId = r.userId)
        UPDATE Jitar_User SET articleICommentCount = c.commentCount FROM Jitar_User AS u,(SELECT userId, COUNT(id) AS commentCount FROM B_Comment WHERE ((objType = 3) AND (userId = @userId)) GROUP BY userId) AS c WHERE (u.userId = c.userId)
        UPDATE Jitar_User SET resourceICommentCount = c.commentCount FROM Jitar_User AS u,(SELECT userId, COUNT(id) AS commentCount FROM B_Comment WHERE ((objType = 12) AND (userId = @userId)) GROUP BY userId) AS c WHERE (u.userId = c.userId)
        UPDATE Jitar_User SET createGroupCount = g.groupCount FROM Jitar_User AS u, (SELECT createUserId, COUNT(groupId) AS groupCount FROM G_Group WHERE (createUserId = @userId) GROUP BY createUserId) AS g WHERE (u.userId = g.createUserId)
        UPDATE Jitar_User SET jionGroupCount = gm.groupCount FROM Jitar_User AS u, (SELECT userId, COUNT(id) AS groupCount FROM G_GroupMember WHERE (userId = @userId) GROUP BY userId) AS gm WHERE (u.userId = gm.userId)
        UPDATE Jitar_User SET photoCount = p.photoCount FROM Jitar_User AS u, (SELECT userId, COUNT(photoId) AS photoCount FROM B_Photo WHERE (userId = @userId) GROUP BY userId) AS p WHERE (u.userId = p.userId)
        UPDATE Jitar_User SET videoCount = v.videoCount FROM Jitar_User AS u, (SELECT userId, COUNT(videoId) AS videoCount FROM B_Video WHERE (userId = @userId) GROUP BY userId) AS v WHERE (u.userId = v.userId)
        UPDATE Jitar_User SET prepareCourseCount = c.prepareCourseCount FROM Jitar_User AS u, (SELECT leaderId, COUNT(leaderId) AS prepareCourseCount FROM C_PrepareCourse WHERE ((status = 1) AND (leaderId = @userId)) GROUP BY leaderId) AS c WHERE (u.userId = c.leaderId)
        UPDATE Jitar_User SET articleScore = CAST(@scoreMyarticleadd AS INT) * myArticleCount + CAST(@scoreOtherarticleadd AS INT) * otherArticleCount + CAST(@scorearticlercmd AS INT) * recommendArticleCount, resourceScore = CAST(@scoreresourceadd AS INT) * resourceCount + CAST(@scoreresourcercmd AS INT) * recommendResourceCount, commentScore = @scorecommentadd * articleICommentCount + @scorecommentadd * resourceICommentCount, photoScore = @scorePhotoUpload * photoCount, videoScore = @scoreVideoUpload * videoCount WHERE (userId = @userId)
        UPDATE Jitar_User SET articlePunishScore = ps.score FROM Jitar_User AS u, (SELECT userId, -1 * CAST(SUM(score) AS INT) AS score FROM U_PunishScore WHERE ((objType = 3) AND (userId = @userId)) GROUP BY userId) AS ps WHERE (u.userId = ps.userId)
        UPDATE Jitar_User SET resourcePunishScore = ps.score FROM Jitar_User AS u, (SELECT userId, -1 * CAST(SUM(score) AS INT) AS score FROM U_PunishScore WHERE ((objType = 12) AND (userId = @userId)) GROUP BY userId) AS ps WHERE (u.userId = ps.userId)
        UPDATE Jitar_User SET commentPunishScore = ps.score FROM Jitar_User AS u, (SELECT userId, -1 * CAST(SUM(score) AS INT) AS score FROM U_PunishScore WHERE ((objType = 16) AND (userId = @userId)) GROUP BY userId) AS ps WHERE (u.userId = ps.userId)
        UPDATE Jitar_User SET photoPunishScore = ps.score FROM Jitar_User AS u, (SELECT userId, -1 * CAST(SUM(score) AS INT) AS score FROM U_PunishScore WHERE ((objType = 11) AND (userId = @userId)) GROUP BY userId) AS ps WHERE (u.userId = ps.userId)
        UPDATE Jitar_User SET videoPunishScore = ps.score FROM Jitar_User AS u, (SELECT userId, -1 * CAST(SUM(score) AS INT) AS score FROM U_PunishScore WHERE ((objType = 17) AND (userId = @userId)) GROUP BY userId) AS ps WHERE (u.userId = ps.userId)
        
        SELECT @PunishScore =  -1 * CAST(SUM(score) AS INT) FROM U_PunishScore WHERE userId = @userId
                SELECT @PunishScore = isnull(@PunishScore, 0)
        
        UPDATE Jitar_User SET articleCount = myArticleCount + otherArticleCount, userScore = articleScore + resourceScore + commentScore + photoScore + videoScore + @PunishScore WHERE (userId = @userId) 
        FETCH NEXT FROM user_cursor INTO @userId
      END
    CLOSE user_cursor
    DEALLOCATE user_cursor
  END
GO
/****** Object:  StoredProcedure [dbo].[DeleteUser]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[DeleteUser] 
  @userid int
AS
--删除该用户的资源的评论
delete from B_Comment Where ObjId in (select ResourceId from Jtr_Resource Where UserId=@userid) and ObjType=12
--删除该用户的评论
delete from B_Comment Where UserId=@userid
--删除群组中该用户的资源
delete from Jtr_GroupResource Where ResourceId in(select ResourceId from Jtr_Resource Where UserId=@userid)
--删除该用户的资源
delete from Jtr_Resource Where UserId=@userid
--删除该用户的文章的评论
delete from B_Comment Where ObjId in (select ArticleId from Jitar_Article Where UserId=@userid) and ObjType=3
--删除群组中该用户的文章
delete from G_GroupArticle Where ArticleId in (select ArticleId from Jitar_Article Where UserId=@userid) 
--删除该用户的文章
delete from Jitar_Article Where UserId=@userid
--删除该用户的照片的评论
delete from B_Comment Where ObjId in (select PhotoId from B_Photo Where UserId=@userid) and ObjType=11
--删除该用户的照片
delete from B_Photo Where UserId=@userid
--删除该用户的视频的评论
delete from B_Comment Where ObjId in (select VideoId from B_Video Where UserId=@userid) and ObjType=17
--删除该用户的视频
delete from B_Video Where UserId=@userid
--删除群组该用户的成员
Delete from G_GroupMember Where UserId=@userid
--删除好友中该用户的信息
delete from U_Friend Where UserId=@userid
delete from U_FriendThings Where UserId=@userid
--删除该用户的收藏夹
delete from U_Favorites Where favUser=@userid
--删除投票
delete from P_VoteUser Where UserId=@userid
--删除罚分信息
delete from U_PunishScore Where UserId=@userid
--删除短信息
delete from U_Message Where sendId=@userid or receiveId=@userid
--删除该用户的在线信息
delete from U_UserOnLine Where UserId=@userid
GO
/****** Object:  StoredProcedure [dbo].[VideoCate]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[VideoCate]
AS

DECLARE @CateName nvarchar(255)

DECLARE VideoCate_Cursor CURSOR FOR
SELECT V_Staple FROM B_Video Where V_Staple IS NOT NUll
OPEN VideoCate_Cursor
-- 移动游标到第一行
FETCH NEXT FROM VideoCate_Cursor INTO @CateName
-- Check @@FETCH_STATUS to see if there are any more rows to fetch.
WHILE @@FETCH_STATUS = 0
 BEGIN
   If Exists(select TOP 1 CategoryId From B_Category Where Name = @CateName And ItemType='video')
   BEGIN
   UPDATE B_Video SET CategoryId = (select TOP 1 CategoryId From B_Category Where Name = @CateName And ItemType='video') Where V_Staple=@CateName
   END
   FETCH NEXT FROM VideoCate_Cursor INTO @CateName
END
CLOSE VideoCate_Cursor
DEALLOCATE VideoCate_Cursor
GO
/****** Object:  View [dbo].[PhotoVideo]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[PhotoVideo]
AS
SELECT PhotoId AS Id, Title, CreateDate, Href AS ImgHref, 'photo' AS ObjType, UserId
FROM B_photo
UNION ALL
SELECT VideoId AS Id, v_title AS Title, v_createDate AS CreateDate, 
      V_FlvThumbNailHref AS ImgHref, 'video' AS ObjType, UserId
FROM B_video
GO
/****** Object:  StoredProcedure [dbo].[ConvertVideoStapleToId]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[ConvertVideoStapleToId]
AS

DECLARE @CateName nvarchar(255)

DECLARE VideoCate_Cursor CURSOR FOR
SELECT V_Staple FROM B_Video Where V_Staple IS NOT NUll
OPEN VideoCate_Cursor
-- 移动游标到第一行
FETCH NEXT FROM VideoCate_Cursor INTO @CateName
-- Check @@FETCH_STATUS to see if there are any more rows to fetch.
WHILE @@FETCH_STATUS = 0
 BEGIN
   If Exists(select TOP 1 CategoryId From B_Category Where Name = @CateName And ItemType='video')
   BEGIN
   UPDATE B_Video SET CategoryId = (select TOP 1 CategoryId From B_Category Where Name = @CateName And ItemType='video') Where V_Staple=@CateName
   END
   FETCH NEXT FROM VideoCate_Cursor INTO @CateName
END
CLOSE VideoCate_Cursor
DEALLOCATE VideoCate_Cursor
GO
/****** Object:  StoredProcedure [dbo].[UpdateVideoCommentCount]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UpdateVideoCommentCount] 
AS
UPDATE B_Video Set V_CommentCount=0
UPDATE B_Video SET V_CommentCount=(select Count(b.Id) from B_Video a Left Join B_Comment b On a.VideoId=b.ObjId Where b.objType=17)  from B_Video a,B_Comment b  Where a.VideoId=b.ObjId and b.objType=17
GO
/****** Object:  StoredProcedure [dbo].[UnitRank]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UnitRank]  
 @UnitId int
AS
DECLARE @UserCount int
DECLARE @ArticleCount int
DECLARE @ResourceCount int
DECLARE @PhotoCount int
DECLARE @VideoCount int
BEGIN
  SELECT @UserCount = COUNT(*)  FROM Jitar_User Where UnitId = @UnitId And UserStatus = 0
  
  SELECT @ArticleCount =  COUNT(*)  FROM Jitar_Article  Where UnitId = @UnitId And AuditState=0 and DelState=0 and HideState=0 and DraftState=0  And DateDiff(dd,CreateDate,getDate()) < 7
  
  SELECT @ResourceCount =  COUNT(*)  FROM Jtr_Resource Where UnitId = @UnitId And AuditState=0 and DelState=0 And DateDiff(dd,CreateDate,getDate()) < 7
  
  SELECT @PhotoCount = COUNT(*)  FROM B_Photo Where UnitId = @UnitId And AuditState = 0 And DelState = 0 And DateDiff(dd,CreateDate,getDate()) < 7
  
  SELECT @VideoCount = COUNT(*)  FROM B_Video Where UnitId = @UnitId And V_AuditState = 0 And V_DeleteState = 0 And DateDiff(dd,V_CreateDate,getDate()) < 7          
  If @UserCount < 1
    UPDATE Jitar_Unit SET Rank = 0 WHERE UnitId = @UnitId
  ELSE
    UPDATE Jitar_Unit SET Rank = CEILING(1000* (@ArticleCount + @ResourceCount + @PhotoCount + @VideoCount)/@UserCount)    WHERE UnitId = @UnitId
END
GO
/****** Object:  StoredProcedure [dbo].[StatJitarUnit]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[StatJitarUnit]
AS

DECLARE @UnitId int
DECLARE @UserCount int
DECLARE @ArticleCount int
DECLARE @ResourceCount int
DECLARE @VideoCount int
DECLARE @PhotoCount int

DECLARE Unit_Cursor CURSOR FOR
SELECT UnitId FROM Jitar_Unit
OPEN Unit_Cursor
-- 移动游标到第一行
FETCH NEXT FROM Unit_Cursor INTO @UnitId
-- Check @@FETCH_STATUS to see if there are any more rows to fetch.
WHILE @@FETCH_STATUS = 0
 BEGIN
 SET @UserCount = (SELECT COUNT(*) FROM Jitar_User WHERE UnitId = @UnitId And UserStatus = 0)
 SET @ArticleCount = (SELECT COUNT(*) FROM Jitar_Article WHERE UnitId = @UnitId And auditState = 0 AND delState = 0 AND  draftState = 0 AND (hideState = 0))
 SET @ResourceCount = (SELECT COUNT(*) FROM Jtr_Resource WHERE UnitId = @UnitId And auditState = 0)
 SET @VideoCount = (SELECT COUNT(*) FROM B_Video WHERE UnitId = @UnitId And V_DeleteState = 0 And V_AuditState = 0)
 SET @PhotoCount = (SELECT COUNT(*) FROM B_Photo WHERE UnitId = @UnitId And AuditState = 0 And DelState = 0 )
 PRINT '单位' + STR(@UnitId) + ' 用户数=' + STR(@UserCount) + ' 文章数='+STR(@ArticleCount)+' 资源数='+ STR(@ResourceCount) + ' 视频数='+STR(@VideoCount)+' 图片数='+STR(@PhotoCount)
 UPDATE Jitar_Unit Set UserCount = @UserCount,ArticleCount=@ArticleCount,ResourceCount=@ResourceCount,VideoCount=@VideoCount,PhotoCount = @PhotoCount WHERE UnitId=@UnitId
 FETCH NEXT FROM Unit_Cursor INTO @UnitId
END
CLOSE Unit_Cursor
DEALLOCATE Unit_Cursor
GO
/****** Object:  StoredProcedure [dbo].[statAllGroup]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[statAllGroup]
(
  @groupId  Int,      -- 群组ID
  @beginDate  Varchar(10),  -- 开始日期
  @endDate  Varchar(10)   -- 结束日期
)
AS
  BEGIN
  DECLARE @userCount int,   -- 成员数
      @articleCount int,  -- 文章数
      @resourceCount int, -- 资源数
      @topicCount int,  -- 主题数
      @discussCount int,  -- 讨论数
      @actionCount int  -- 活动数
  
  -- 成员数
  SELECT @userCount = COUNT(*) 
  FROM G_GroupMember 
  WHERE ((groupId = @groupId) AND (joinDate BETWEEN @beginDate AND @endDate))
  -- 更新
  UPDATE G_Group SET userCount = @userCount WHERE (groupId = @groupId)
  -- 文章数
  SELECT @articleCount = COUNT(*) 
  FROM G_GroupArticle 
  WHERE ((groupId = @groupId) AND (pubDate BETWEEN @beginDate AND @endDate))
  -- 更新
  UPDATE G_Group SET articleCount = @articleCount WHERE (groupId = @groupId)
  -- 资源数
  SELECT @resourceCount = COUNT(*) 
  FROM Jtr_GroupResource 
  WHERE ((groupId = @groupId) AND (pubDate BETWEEN @beginDate AND @endDate))
  -- 更新
  UPDATE G_Group SET resourceCount = @resourceCount WHERE (groupId = @groupId)
  -- 主题数
  SELECT @topicCount = COUNT(*) 
  FROM BBS_Topic 
  WHERE ((groupId = @groupId) AND (createDate BETWEEN @beginDate AND @endDate))
  -- 更新
  UPDATE G_Group SET topicCount = @topicCount WHERE (groupId = @groupId)
  -- 讨论数
  SELECT @discussCount = IsNull(SUM(replyCount), 0) 
  FROM BBS_Topic 
  WHERE ((groupId = @groupId) AND (createDate BETWEEN @beginDate AND @endDate))
  -- 更新
  UPDATE G_Group SET discussCount = @discussCount WHERE (groupId = @groupId)
  -- 活动数
  SELECT @actionCount = COUNT(*) 
  FROM A_Action 
  WHERE ((ownerId = @groupId) and (ownerType = 'group') AND (createDate BETWEEN @beginDate AND @endDate))
  -- 更新
  UPDATE G_Group SET actionCount = @actionCount WHERE (groupId = @groupId)
  
  END
GO
/****** Object:  StoredProcedure [dbo].[BestGroupArticleAndResource]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[BestGroupArticleAndResource]  AS
Update G_Group Set bestArticleCount=0
Update G_Group Set bestArticleCount=c.num from (Select Count(b.GroupId) as num ,b.GroupId  from G_Group a,G_GroupArticle b Where a.GroupId=b.GroupId and b.IsGroupBest=1 Group by b.GroupId) c,G_Group d WHere c.GroupId=d.GroupId
Update G_Group Set bestResourceCount=0
Update G_Group Set bestResourceCount=c.num from (Select Count(b.GroupId) as num ,b.GroupId  from G_Group a,Jtr_GroupResource b Where a.GroupId=b.GroupId and b.IsGroupBest=1 Group by b.GroupId) c,G_Group d WHere c.GroupId=d.GroupId
GO
/****** Object:  StoredProcedure [dbo].[StatsUnit]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[StatsUnit]
  @unitId int
AS
-- 暂时未使用的存储过程
DECLARE @sql NVARCHAR(256)
DECLARE @selfArticleCount int
DECLARE @childArticleCount int
DECLARE @selfResourceCount int
DECLARE @selfPhotoCount int
DECLARE @selfVideoCount int
DECLARE @selfUserCount int
DECLARE @childResourceCount int
DECLARE @childPhotoCount int
DECLARE @childVideoCount int
DECLARE @childUserCount int

BEGIN

  SET NOCOUNT ON;
  IF NOT EXISTS(SELECT * FROM Jitar_UnitCount WHERE UnitId=@unitId)
  BEGIN
  -- 插入一条记录
  SET @sql = 'INSERT INTO Jitar_UnitCount(UnitId) Values(' + STR(@unitId) + ')'
  EXEC(@sql)
  END
  SET @selfArticleCount = (SELECT COUNT(*) FROM Jitar_Article WHERE UnitId=@unitId And AuditState = 0 And HideState = 0 And DraftState = 0 And DelState = 0)
  SET @childArticleCount = (SELECT COUNT(*) FROM Jitar_Article WHERE UnitId<>@unitId AND UnitPathInfo LIKE '%/' + STR(@unitId) + '/%' AND ApprovedPathInfo LIKE '%/' + STR(@unitId) + '/%' And AuditState = 0 And HideState = 0 And DraftState = 0 And DelState = 0)
  SET @selfResourceCount = (SELECT COUNT(*) FROM Jtr_Resource WHERE UnitId=@unitId And AuditState = 0 And DelState = 0)
  SET @childResourceCount = (SELECT COUNT(*) FROM Jtr_Resource WHERE UnitId<>@unitId And AuditState = 0 And DelState = 0 AND UnitPathInfo LIKE '%/' + STR(@unitId) + '/%' AND ApprovedPathInfo LIKE '%/' + STR(@unitId) + '/%')
  SET @selfPhotoCount = (SELECT PhotoCount FROM Jitar_Unit WHERE UnitId=@unitId)
  SET @selfVideoCount = (SELECT VideoCount FROM Jitar_Unit WHERE UnitId=@unitId)
  SET @selfUserCount = (SELECT UserCount FROM Jitar_Unit WHERE UnitId=@unitId)
  
  SET @childPhotoCount = (SELECT SUM(PhotoCount) FROM Jitar_Unit WHERE UnitPathInfo LIKE '%/' + STR(@unitId) + '/%')
  SET @childVideoCount = (SELECT SUM(VideoCount) FROM Jitar_Unit WHERE UnitPathInfo LIKE '%/' + STR(@unitId) + '/%')
  SET @childUserCount = (SELECT SUM(UserCount) FROM Jitar_Unit WHERE UnitPathInfo LIKE '%/' + STR(@unitId) + '/%')
  --未使用的存储过程
END
GO
/****** Object:  StoredProcedure [dbo].[DeleteArticleById]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS OFF
GO
SET QUOTED_IDENTIFIER OFF
GO
CREATE PROCEDURE [dbo].[DeleteArticleById]
@articleId int,
@year int
As
DECLARE @SQL NVARCHAR(256)
DECLARE @TableName VARCHAR(256)
SET @TableName = 'Jitar_Article'
if EXISTS(SELECT * FROM [Jitar_BackYear] where [BackYearType] = 'article' And [BackYear] = @year)
SET @TableName = 'HtmlArticle' + LTRIM(STR(@year))

DELETE FROM Jitar_WeekViewCountArticle WHERE ArticleId = @articleId
DELETE FROM Jitar_WeekCommentArticle WHERE ArticleId = @articleId
DELETE FROM Jitar_SpecialSubjectArticle WHERE ArticleId = @articleId
DELETE FROM Jitar_ChannelArticle WHERE ArticleId = @articleId
DELETE FROM C_PrepareCourseArticle WHERE ArticleId = @articleId
DELETE FROM B_Comment WHERE ObjType = 3 And ObjId = @articleId
DELETE FROM G_GroupArticle WHERE ArticleId = @articleId
DELETE FROM HtmlArticleBase WHERE ArticleId = @articleId
DELETE FROM [S_TagRef] WHERE ObjectType = 3 And ObjectId = @articleId

SET @SQL = 'DELETE FROM ' + @TableName + ' WHERE ArticleId=' + STR(@articleId)
EXEC(@SQL)
GO
/****** Object:  StoredProcedure [dbo].[Get7DaysViewCountArticle]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS OFF
GO
SET QUOTED_IDENTIFIER OFF
GO
CREATE PROCEDURE [dbo].[Get7DaysViewCountArticle]

As

DELETE FROM Jitar_WeekViewCountArticle

INSERT INTO Jitar_WeekViewCountArticle([ArticleId],[Title],[CreateDate],[TypeState],[UserId],[UserIcon],[TrueName],[LoginName],[ViewCount])
SELECT a.ArticleId, a.Title,a.CreateDate,a.TypeState,u.UserId,u.UserIcon,u.TrueName,u.LoginName,c.WeekViewCount FROM Jitar_Article a,Jitar_User u,(select DISTINCT objType, objId, SUM(viewCount) AS WeekViewCount From S_ViewCount Where objType=3 and DateDiff(day,viewDate,getdate())<=7 GROUP BY objType,objId) as c Where a.userId = u.userId and c.objId=a.ArticleId And c.objType = 3 and a.auditState = 0 AND a.draftState = 0 AND a.delState = 0 AND a.hideState = 0
GO
/****** Object:  StoredProcedure [dbo].[Get7DaysCommentArticle]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS OFF
GO
SET QUOTED_IDENTIFIER OFF
GO
CREATE PROCEDURE [dbo].[Get7DaysCommentArticle]
As

DELETE FROM Jitar_WeekCommentArticle

INSERT INTO Jitar_WeekCommentArticle([ArticleId],[Title],[CreateDate],[TypeState],[UserId],[UserIcon],[TrueName],[LoginName],[CommentCount])
SELECT a.ArticleId, a.Title,a.CreateDate,a.TypeState,u.UserId,u.UserIcon,u.TrueName,u.LoginName,count(c.objId) AS commentcount FROM Jitar_Article a,Jitar_User u,(select objId From B_Comment Where objType=3 and DateDiff(day,createDate,getdate())<=7 ) as c Where a.userId = u.userId and c.objId=a.articleId and  a.auditState = 0 AND a.draftState = 0 AND a.delState = 0 AND a.hideState = 0 GROUP BY a.articleId, a.title, a.createDate, a.articleAbstract, u.userId, u.userIcon, u.TrueName, u.loginName,a.typeState
GO
/****** Object:  StoredProcedure [dbo].[statAllUserStat]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- 创建新的存储过程 
CREATE PROCEDURE [dbo].[statAllUserStat] 
( 
  @keyvalue   Varchar(200), -- 关键字
  @beginDate  Varchar(10),  -- 开始日期 
  @endDate  Varchar(10),    -- 结束日期 
  @subjectId int,           --学科
  @gradeId int,             --学段
  @unitId int,              --单位
  @teacherType  int,        --教师类型    
  @StatGuid Varchar(40)     --当前统计的标识
) 
 
AS 
  BEGIN
  
    /** 删除过时的统计数据 **/
    DELETE FROM U_UserStat WHERE DateDiff(day,WorkDate,getDate()) > 1
    DELETE FROM U_UserStat WHERE @StatGuid = StatGuid
    DECLARE  
      @userId   Int,
      @visitArticleCount int,       -- 我的文章的被访问量 
      @visitResourceCount int,      -- 我的资源的被访问量 
      @myArticleCount int,        -- 原创文章数总数 
      @otherArticleCount int,       -- 转载文章数总数 
      @resourceCount int,         -- 资源数 
      @recommendArticleCount int,     -- 推荐文章数 
      @recommendResourceCount int,    -- 推荐资源数 
      @articleCommentCount int,       -- 我的文章被评论数 
      @articleICommentCount int,      -- 我发出的文章评论数 
      @resourceCommentCount int,      -- 我的资源被评论数 
      @resourceICommentCount int,     -- 我发出的资源评论数 
      @resourceDownloadCount int,     -- 资源下载数 
      @createGroupCount int,        -- 创建协作组数 
      @jionGroupCount int,        -- 加入协作组数 
      @photoCount int,          -- 照片数 
      @videoCount int,          -- 视频数 
      @scoreMyarticleadd varchar(10),   -- 发表原创文章得分 
      @scoreOtherarticleadd varchar(10),  -- 发表转载文章得分 
      @scorearticlercmd varchar(10),    -- 文章被推荐得分 
      @scoreresourceadd varchar(10),    -- 上载资源得分 
      @scoreresourcercmd varchar(10),   -- 资源被推荐得分 
      @scorePhotoUpload varchar(10),    -- 发布照片得分 
      @scoreVideoUpload varchar(10),    -- 上传视频得分 
      @scorecommentadd varchar(10),   -- 发表评论得分 
      
      @scoreArticlePunish int,      -- 文章删除的罚分 
      @scoreResourcePunish int,     -- 资源删除的罚分 
      @scorePhotoPunish int,        -- 图片删除的罚分 
      @scoreVideoPunish int,        -- 视频删除的罚分 
      @scoreCommentPunish int,      -- 评论删除的罚分 
      @PrepareCourseCount int
      
  -- 评论总分 = (我发出的文章评论数 + 我发出的资源评论数) * 发表评论得分 
  SELECT @scorecommentadd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.comment.add' 
  -- 图片总分 = (上传图片得分 * 上传图片数 ) 
  SELECT @scorePhotoUpload = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.photo.upload' 
  -- 视频总分 = (上传视频得分 * 上传视频数 ) 
  SELECT @scoreVideoUpload = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.video.upload' 
  -- 文章总分 = 发表原创文章得分*原创文章数 + 发表转载文章得分*转载文章数 + 文章被推荐得分*推荐文章数 
  SELECT @scoreMyarticleadd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.my.article.add' 
  SELECT @scoreOtherarticleadd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.other.article.add' 
  SELECT @scorearticlercmd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.article.rcmd' 
  
  -- 资源总分 = ((上载资源得分 * 资源数) + (资源被推荐得分 * 推荐资源数)) 
  SELECT @scoreresourceadd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.resource.add' 
  SELECT @scoreresourcercmd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.resource.rcmd' 
    
DECLARE @userSqlWhere as varchar(4000)
DECLARE @userSql as varchar(7000)
set @userSqlWhere=''
if(@keyvalue!='')
begin
  if(@userSqlWhere='')
  begin
    if(ISNUMERIC(@keyvalue)=1)
    begin
      set @userSqlWhere=' u.userId = '+@keyvalue
    end
    else
    begin
      set @userSqlWhere=' ((u.loginName LIKE ''%'+ @keyvalue +'%'') OR (u.nickName LIKE ''%'+@keyvalue+'%'') OR (u.trueName LIKE ''%'+@keyvalue+'%''))'
    end
  end
  else
  begin
    if(ISNUMERIC(@keyvalue)=1)
    begin
      set @userSqlWhere=@userSqlWhere + ' and u.userId = '+@keyvalue
    end
    else
    begin
      set @userSqlWhere=@userSqlWhere + ' and ((u.loginName LIKE ''%'+ @keyvalue +'%'') OR (u.nickName LIKE ''%'+@keyvalue+'%'') OR (u.trueName LIKE ''%'+@keyvalue+'%''))'
    end
  
  end 
end
--教师类型，要注意str的多余空格
IF @teacherType!= 0
BEGIN
 if(@userSqlWhere='')
  BEGIN
    set @userSqlWhere='u.userType LIKE ''%/' + RTRIM(LTRIM(str(@teacherType))) + '/%'''
  END
 ELSE
  BEGIN
   set @userSqlWhere=@userSqlWhere+' and u.userType LIKE ''%/' + RTRIM(LTRIM(str(@teacherType))) + '/%'''
  END
END
    
  if(@subjectId!=0)
  begin
      if(@userSqlWhere='')
      begin
        set @userSqlWhere='u.subjectId = ' + Cast(@subjectId as varchar)
      end
      else
      begin
        set @userSqlWhere=@userSqlWhere + ' and u.subjectId = ' + Cast(@subjectId as varchar)
      end 
  end       
  
  if(@gradeId!=0)
  begin
      if(@userSqlWhere='')
      begin
        set @userSqlWhere='u.gradeId = ' + Cast(@gradeId as varchar)
      end
      else
      begin
        set @userSqlWhere=@userSqlWhere + ' and u.gradeId = ' + Cast(@gradeId as varchar)
      end 
  end
  
  if(@unitId!=0)
  begin
      if(@userSqlWhere='')
      begin
        set @userSqlWhere='u.unitId = ' + Cast(@unitId as varchar)
      end
      else
      begin
        set @userSqlWhere=@userSqlWhere + ' and u.unitId = ' + Cast(@unitId as varchar)
      end 
  end 

DECLARE @sql as varchar(8000) 
DECLARE @insertsql as varchar(8000) 
DECLARE @updatesql as varchar(8000) 

set @userSql='Select u.UserId from Jitar_User u'
if(@userSqlWhere!='')
begin
  set @userSql=@userSql+' Where '+ @userSqlWhere
end

EXECUTE ('DECLARE user_cursor CURSOR FOR '+@userSql)
OPEN user_cursor 
FETCH NEXT FROM user_cursor INTO @userId
WHILE @@FETCH_STATUS = 0
Begin
    IF Not EXISTS (SELECT * FROM U_UserStat WHERE UserId=@userId And StatGuid=@StatGuid) 
    begin       
        set @sql='Insert into U_UserStat(UserId,StatGuid,LoginName,TrueName,NickName,Email,CreateDate,Gender,UnitId,BlogName,'
        set @sql=@sql+'UserStatus,UserGroupID,VisitCount,VisitArticleCount,VisitResourceCount,MyArticleCount,OtherArticleCount,RecommendArticleCount,'
        set @sql=@sql+'ArticleCommentCount,ArticleICommentCount,ResourceCount,RecommendResourceCount,ResourceCommentCount,ResourceICommentCount,ResourceDownloadCount,'
        set @sql=@sql+'CreateGroupCount,JionGroupCount,PhotoCount,VideoCount,CourseCount,TopicCount,CommentCount,UsedFileSize,UserScore,'
        set @sql=@sql+'UserClassID,PositionID,SubjectId,GradeId,ArticleScore,ResourceScore,PhotoScore,'
        set @sql=@sql+'VideoScore,CommentScore,articleCount,IDcard,QQ,ArticlePunishScore,ResourcePunishScore,CommentPunishScore,PhotoPunishScore,VideoPunishScore,'
        set @sql=@sql+'PrepareCourseCount,UserType) '
        set @sql=@sql+'select UserId,''' + @StatGuid + ''',LoginName,TrueName,NickName,Email,CreateDate,Gender,UnitId,BlogName,'
        set @sql=@sql+'UserStatus,UserGroupID,VisitCount,VisitArticleCount,VisitResourceCount,MyArticleCount,OtherArticleCount,RecommendArticleCount,'
        set @sql=@sql+'ArticleCommentCount,ArticleICommentCount,ResourceCount,RecommendResourceCount,ResourceCommentCount,ResourceICommentCount,ResourceDownloadCount,'
        set @sql=@sql+'CreateGroupCount,JionGroupCount,PhotoCount,VideoCount,CourseCount,TopicCount,CommentCount,UsedFileSize,UserScore,'
        set @sql=@sql+'UserClassID,PositionID,SubjectId,GradeId,ArticleScore,ResourceScore,PhotoScore,'
        set @sql=@sql+'VideoScore,CommentScore,articleCount,IDcard,QQ,ArticlePunishScore,ResourcePunishScore,CommentPunishScore,PhotoPunishScore,VideoPunishScore,'
        set @sql=@sql+'PrepareCourseCount,UserType from Jitar_User Where userId='+ cast(@userId as varchar)
        
        Execute(@sql)
      
    end
    else
    begin
      
        set @sql='Update U_UserStat set LoginName=u.LoginName,TrueName=u.TrueName,NickName=u.NickName,Email=u.Email,CreateDate=u.CreateDate,'
        set @sql=@sql+'Gender=u.Gender,UnitId=u.UnitId,BlogName=u.BlogName,UserStatus=u.UserStatus,'
        set @sql=@sql+'UserGroupID=u.UserGroupID,VisitCount=u.VisitCount,VisitArticleCount=u.VisitArticleCount,VisitResourceCount=u.VisitResourceCount,MyArticleCount=u.MyArticleCount,'
        set @sql=@sql+'OtherArticleCount=u.OtherArticleCount,RecommendArticleCount=u.RecommendArticleCount,ArticleCommentCount=u.ArticleCommentCount,ArticleICommentCount=u.ArticleICommentCount,'
        set @sql=@sql+'ResourceCount=u.ResourceCount,RecommendResourceCount=u.RecommendResourceCount,ResourceCommentCount=u.ResourceCommentCount,ResourceICommentCount=u.ResourceICommentCount,'
        set @sql=@sql+'ResourceDownloadCount=u.ResourceDownloadCount,CreateGroupCount=u.CreateGroupCount,JionGroupCount=u.JionGroupCount,PhotoCount=u.PhotoCount,'
        set @sql=@sql+'VideoCount=u.VideoCount,CourseCount=u.CourseCount,TopicCount=u.TopicCount,CommentCount=u.CommentCount,UsedFileSize=u.UsedFileSize,'
        set @sql=@sql+'UserScore=u.UserScore,UserClassID=u.UserClassID,PositionID=u.PositionID,'
        set @sql=@sql+'SubjectId=u.SubjectId,GradeId=u.GradeId,'
        set @sql=@sql+'ArticleScore=u.ArticleScore,ResourceScore=u.ResourceScore,PhotoScore=u.PhotoScore,VideoScore=u.VideoScore,'
        set @sql=@sql+'CommentScore=u.CommentScore,articleCount=u.articleCount,IDcard=u.IDcard,QQ=u.QQ,ArticlePunishScore=u.ArticlePunishScore,ResourcePunishScore=u.ResourcePunishScore,'
        set @sql=@sql+'CommentPunishScore=u.CommentPunishScore,PhotoPunishScore=u.PhotoPunishScore,VideoPunishScore=u.VideoPunishScore,PrepareCourseCount=u.PrepareCourseCount,'
        set @sql=@sql+'UserType=u.UserType'
        set @sql=@sql+' from U_UserStat us,Jitar_User u Where u.userId=us.userId And us.StatGuid=''' + @StatGuid + ''' and u.UserId='+ cast( @userId as varchar)
        Execute(@sql)           
          
    end
  FETCH NEXT FROM user_cursor INTO @userId
end
CLOSE user_cursor 
DEALLOCATE user_cursor
--初始化统计字段
set @sql='Update U_UserStat set visitArticleCount=0,articleCommentCount=0,myArticleCount=0,otherArticleCount=0,recommendArticleCount=0,visitResourceCount=0'
    +',resourceCount=0,resourceCommentCount=0,resourceDownloadCount=0,recommendResourceCount=0,articleICommentCount=0,resourceICommentCount=0,createGroupCount=0' 
    +',jionGroupCount=0,photoCount=0,videoCount=0,PrepareCourseCount=0,ResourcePunishScore=0,ArticlePunishScore=0,CommentPunishScore=0,PhotoPunishScore=0'
    +',videoPunishScore=0'
    --+'  FROM U_UserStat us ,('+@userSql+') x '
    --+'  WHERE (us.userId = x.userId) '
EXECUTE(@sql)
select @visitArticleCount=0
select @articleCommentCount=0
select @myArticleCount=0
select @otherArticleCount=0
select @recommendArticleCount=0
Select @resourceCount=0
select @resourceCommentCount=0
select @recommendResourceCount=0
select @resourceDownloadCount=0
DECLARE @visitCount int,@CommentCount int,@articleCount int,@typeState int,@recommendState int
set @sql='Update U_UserStat set visitArticleCount=y.visitCount,articleCommentCount=y.CommentCount from U_UserStat d,(SELECT IsNull(SUM(b.viewCount), 0) as visitCount , IsNull(SUM(b.commentCount), 0) as CommentCount, b.userId '
    +' FROM Jitar_Article b ,('+@userSql+') x '
    +' WHERE ((b.userId = x.userId) AND (b.createDate BETWEEN '''+@beginDate+''' AND '''+@endDate+''')  '
    +' AND (b.auditState = 0) AND (b.delState = 0) AND (b.draftState = 0) AND (b.hideState = 0)) Group by b.userId) y '
    +' Where d.userId=y.userId'
--更新用户的visitArticleCount和 articleCommentCount
--print @sql
EXECUTE(@sql)
set @sql='Update U_UserStat set myArticleCount=y.articleCount from U_UserStat d,(SELECT COUNT(ArticleId) as articleCount, b.userId '
    +' FROM Jitar_Article b ,('+@userSql+') x '
    +' WHERE ((b.userId = x.userId) AND (b.createDate BETWEEN '''+@beginDate+''' AND '''+@endDate+''')  '
    +' AND (b.auditState = 0) AND (b.delState = 0) AND  (b.draftState = 0) AND (b.hideState = 0) And (b.typeState=0)) Group by b.userId) y '
    +' Where d.userId=y.userId'
--更新用户的 myArticleCount
--print @sql
EXECUTE (@sql)
set @sql='Update U_UserStat set otherArticleCount=y.articleCount from U_UserStat d,(SELECT COUNT(ArticleId) as articleCount, b.userId ' 
    +' FROM Jitar_Article b ,('+@userSql+') x '
    +' WHERE ((b.userId = x.userId) AND (b.createDate BETWEEN '''+@beginDate+''' AND '''+@endDate+''')  '
    +' AND (b.auditState = 0) AND (b.delState = 0) AND (b.draftState = 0) AND (b.hideState = 0) And (b.typeState=1)) Group by b.userId) y '
    +' Where d.userId=y.userId'
--更新用户的 otherArticleCount
--print @sql
EXECUTE (@sql)
set @sql='Update U_UserStat set recommendArticleCount=y.articleCount from U_UserStat d,(SELECT COUNT(ArticleId) as articleCount, b.userId '
    +' FROM Jitar_Article b ,('+@userSql+') x '
    +' WHERE ((b.userId = x.userId) AND (b.createDate BETWEEN '''+@beginDate+''' AND '''+@endDate+''')  '
    +' AND (b.auditState = 0) AND (b.delState = 0) AND (b.draftState = 0) AND (b.hideState = 0) And (b.recommendState=1)) Group by b.userId) y '
    +' Where d.userId=y.userId'
--更新用户的 recommendArticleCount
--print @sql
EXECUTE (@sql)
set @sql='Update U_UserStat set visitResourceCount=y.viewCount from U_UserStat d,(SELECT IsNull(SUM(viewCount), 0) as viewCount, r.userId '
    +' FROM Jtr_Resource r ,('+@userSql+') x '
    +' WHERE ((r.userId = x.userId) AND (r.createDate BETWEEN '''+@beginDate+''' AND '''+@endDate+''')  '
    +' AND (r.auditState = 0) AND (r.delState = 0)) Group by r.userId) y '
    +' Where d.userId=y.userId'
--更新用户的 visitResourceCount
--print @sql
EXECUTE (@sql)
set @sql='Update U_UserStat set resourceCount=y.resourceCount,resourceCommentCount=y.resourceCommentCount,resourceDownloadCount=y.resourceDownloadCount from U_UserStat d,(SELECT COUNT(r.ResourceId) as resourceCount, IsNull(SUM(r.commentCount), 0) as resourceCommentCount, IsNull(SUM(r.downloadCount), 0) as resourceDownloadCount, r.userId '
    +' FROM Jtr_Resource r ,('+@userSql+') x '
    +' WHERE ((r.userId = x.userId) AND (r.createDate BETWEEN '''+@beginDate+''' AND '''+@endDate+''')  '
    +' AND (r.auditState = 0) AND (r.delState = 0) and (r.shareMode >= 500)) Group by r.userId) y '
    +' Where d.userId=y.userId'
--更新用户的 resourceCount,resourceCommentCount,resourceDownloadCount
--print @sql
EXECUTE (@sql)
set @sql='Update U_UserStat set recommendResourceCount=y.resourceCount from U_UserStat d,(SELECT COUNT(r.ResourceId) as resourceCount, r.userId '
    +' FROM Jtr_Resource r ,('+@userSql+') x '
    +' WHERE ((r.userId = x.userId) AND (r.createDate BETWEEN '''+@beginDate+''' AND '''+@endDate+''')  '
    +' AND (r.auditState = 0) AND (r.delState = 0) and (r.shareMode >= 500) and (r.recommendState=1)) Group by r.userId) y '
    +' Where d.userId=y.userId'
--更新用户的 recommendResourceCount
--print @sql
EXECUTE (@sql)
set @sql='Update U_UserStat set articleICommentCount=y.CommentCount from U_UserStat d,(SELECT COUNT(c.Id) as CommentCount, c.userId '
    +' FROM B_Comment c ,('+@userSql+') x '
    +' WHERE ((c.userId = x.userId) AND (c.createDate BETWEEN '''+@beginDate+''' AND '''+@endDate+''')  '
    +' AND (c.objType = 3)) Group by c.userId) y '
    +' Where d.userId=y.userId'
--更新用户的 articleICommentCount我发出的文章评论数
--print @sql
EXECUTE (@sql)
set @sql='Update U_UserStat set resourceICommentCount=y.CommentCount from U_UserStat d,(SELECT COUNT(c.Id) as CommentCount, c.userId '
    +' FROM B_Comment c ,('+@userSql+') x '
    +' WHERE ((c.userId = x.userId) AND (c.createDate BETWEEN '''+@beginDate+''' AND '''+@endDate+''')  '
    +' AND (c.objType = 12)) Group by c.userId) y '
    +' Where d.userId=y.userId'
--更新用户的 resourceICommentCount 我发出的资源评论数
--print @sql
EXECUTE (@sql)
set @sql='Update U_UserStat set createGroupCount=y.groupCount from U_UserStat d,(SELECT COUNT(g.GroupId) as groupCount, g.createUserId '
    +' FROM G_Group g ,('+@userSql+') x '
    +' WHERE ((g.createUserId = x.userId) AND (g.createDate BETWEEN '''+@beginDate+''' AND '''+@endDate+''')  '
    +' ) Group by g.createUserId) y '
    +' Where d.userId=y.createUserId'
--更新用户的 createGroupCount 创建协作组数
--print @sql
EXECUTE (@sql)
set @sql='Update U_UserStat set jionGroupCount=y.groupCount from U_UserStat d,(SELECT COUNT(g.Id) as groupCount, g.userId '
    +' FROM G_GroupMember g ,('+@userSql+') x '
    +' WHERE ((g.userId = x.userId) AND (g.joinDate BETWEEN '''+@beginDate+''' AND '''+@endDate+''')  '
    +' ) Group by g.userId) y '
    +' Where d.userId=y.userId'
--更新用户的 jionGroupCount 加入协作组数
--print @sql
EXECUTE (@sql)
  
set @sql='Update U_UserStat set photoCount=y.photoCount from U_UserStat d,(SELECT COUNT(p.PhotoId) as photoCount, p.userId '
    +' FROM B_Photo p ,('+@userSql+') x '
    +' WHERE ((p.userId = x.userId) AND (p.createDate BETWEEN '''+@beginDate+''' AND '''+@endDate+''')  '
    +' ) Group by p.userId) y '
    +' Where d.userId=y.userId'
--更新用户的 photoCount 照片数
--print @sql
EXECUTE (@sql)
  
set @sql='Update U_UserStat set videoCount=y.videoCount from U_UserStat d,(SELECT COUNT(v.VideoId) as videoCount, v.userId '
    +' FROM B_Video v ,('+@userSql+') x '
    +' WHERE ((v.userId = x.userId) AND (v.v_createDate BETWEEN '''+@beginDate+''' AND '''+@endDate+''')  '
    +' ) Group by v.userId) y '
    +' Where d.userId=y.userId'
--更新用户的 videoCount 视频数
--print @sql
EXECUTE (@sql)
  --统计主备数
set @sql='Update U_UserStat set PrepareCourseCount=y.PrepareCourseCount from U_UserStat d,(SELECT COUNT(c.LeaderId) as PrepareCourseCount, c.LeaderId '
    +' FROM C_PrepareCourse c ,('+@userSql+') x '
    +' WHERE ((c.LeaderId = x.userId) AND (c.CreateDate BETWEEN '''+@beginDate+''' AND '''+@endDate+''') AND (c.Status=1)  '
    +' ) Group by c.LeaderId) y '
    +' Where d.userId=y.LeaderId'
--更新用户的 PrepareCourseCount 主备数
--print @sql
EXECUTE (@sql)
  
  -- 删除资源的罚分 
  set @sql='Update U_UserStat set ResourcePunishScore=y.score from U_UserStat d,(SELECT -1 * CAST(SUM(p.Score) as int) as score, p.userId '
    +' FROM U_PunishScore p ,('+@userSql+') x '
    +' WHERE ((p.UserId = x.userId) AND (p.PunishDate BETWEEN '''+@beginDate+''' AND '''+@endDate+''') AND (p.ObjType=12)  '
    +' ) Group by p.userId) y '
    +' Where d.userId=y.userId'
--更新用户的 @scoreResourcePunish 删除资源的罚分
--print @sql
EXECUTE (@sql)
  -- 删除文章的罚分 
  set @sql='Update U_UserStat set ArticlePunishScore=y.score from U_UserStat d,(SELECT -1 * CAST(SUM(p.Score) as int) as score, p.userId '
    +' FROM U_PunishScore p ,('+@userSql+') x '
    +' WHERE ((p.UserId = x.userId) AND (p.PunishDate BETWEEN '''+@beginDate+''' AND '''+@endDate+''') AND (p.ObjType=3)  '
    +' ) Group by p.userId) y '
    +' Where d.userId=y.userId'
--更新用户的 @scoreArticlePunish 删除文章的罚分
--print @sql
EXECUTE (@sql)
  
  -- 删除评论的罚分 
  set @sql='Update U_UserStat set CommentPunishScore=y.score from U_UserStat d,(SELECT -1 * CAST(SUM(p.Score) as int) as score, p.userId '
    +' FROM U_PunishScore p ,('+@userSql+') x '
    +' WHERE ((p.UserId = x.userId) AND (p.PunishDate BETWEEN '''+@beginDate+''' AND '''+@endDate+''') AND (p.ObjType=16)  '
    +' ) Group by p.userId) y '
    +' Where d.userId=y.userId'
--更新用户的 @scoreCommentPunish 删除评论的罚分
--print @sql
EXECUTE (@sql)
 
  -- 删除图片的罚分 
  set @sql='Update U_UserStat set PhotoPunishScore =y.score from U_UserStat d,(SELECT -1 * CAST(SUM(p.Score) as int) as score, p.userId '
    +' FROM U_PunishScore p ,('+@userSql+') x '
    +' WHERE ((p.UserId = x.userId) AND (p.PunishDate BETWEEN '''+@beginDate+''' AND '''+@endDate+''') AND (p.ObjType=11)  '
    +' ) Group by p.userId) y '
    +' Where d.userId=y.userId'
--更新用户的 @scorePhotoPunish 删除图片的罚分
--print @sql
EXECUTE (@sql)
  
  -- 删除视频的罚分 
  set @sql='Update U_UserStat set videoPunishScore =y.score from U_UserStat d,(SELECT -1 * CAST(SUM(p.Score) as int) as score, p.userId '
    +' FROM U_PunishScore p ,('+@userSql+') x '
    +' WHERE ((p.UserId = x.userId) AND (p.PunishDate BETWEEN '''+@beginDate+''' AND '''+@endDate+''') AND (p.ObjType=17)  '
    +' ) Group by p.userId) y '
    +' Where d.userId=y.userId'
--更新用户的 @scoreVideoPunish 删除视频的罚分
--print @sql
EXECUTE (@sql)

    -- 罚分 
    set @sql='Update U_UserStat set PunishScore =y.score from U_UserStat d,(SELECT -1 * CAST(SUM(p.Score) as int) as score, p.userId '
      +' FROM U_PunishScore p ,('+@userSql+') x '
      +' WHERE ((p.UserId = x.userId) AND (p.PunishDate BETWEEN '''+@beginDate+''' AND '''+@endDate+''') '
        +' ) Group by p.userId) y '
        +' Where d.userId=y.userId'
--更新用户的 @PunishScore 总罚分
EXECUTE (@sql)
  
 
-- 更新 
UPDATE U_UserStat SET 
  articleScore = CAST(@scoreMyarticleadd as int) * myArticleCount + CAST(@scoreOtherarticleadd as int) * otherArticleCount +  CAST(@scorearticlercmd as int) * recommendArticleCount, 
  resourceScore = CAST(@scoreresourceadd as int) * resourceCount + CAST(@scoreresourcercmd as int) * recommendResourceCount,
  commentScore = @scorecommentadd * articleICommentCount + @scorecommentadd * resourceICommentCount, 
  photoScore = @scorePhotoUpload * photoCount,
  videoScore = @scoreVideoUpload * videoCount
-- 统计用户的所有得分 
UPDATE U_UserStat SET UserScore = articleScore + resourceScore + commentScore + photoScore + videoScore + PunishScore,
  ArticleCount = MyArticleCount + OtherArticleCount 

END
GO
/****** Object:  StoredProcedure [dbo].[CountYearArticleUser]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:    <Author,,Name>
-- Create date: <Create Date,,>
-- Description: 统计一个用户的所有历史文章数量
-- =============================================
CREATE PROCEDURE [dbo].[CountYearArticleUser]
@UserId int
AS
BEGIN
  DECLARE @count int
  DECLARE @HistoryMyArticleCount int
  DECLARE @HistoryOtherArticleCount int
  SET @count = 0
  SET @HistoryMyArticleCount = 0
  SET @HistoryOtherArticleCount = 0
  DECLARE @tablename varchar(256)
  DECLARE @SQL nvarchar(256)
  DECLARE @BackYear int
  DECLARE BackYear_Cursor CURSOR FOR
  SELECT BackYear FROM Jitar_BackYear Where BackYearType = 'article'
  OPEN BackYear_Cursor
  FETCH NEXT FROM BackYear_Cursor INTO @BackYear
  WHILE @@FETCH_STATUS = 0
  BEGIN
    SET @tablename = 'HtmlArticle' + LTRIM(RTRIM(STR(@BackYear)))
    SET @SQL = N'SELECT @count = COUNT(*) FROM ' + @tableName + ' WHERE UserId=' + LTRIM(RTRIM(STR(@UserId))) + ' And AuditState = 0 And HideState = 0 And DraftState = 0 And DelState = 0 And TypeState=0'
    EXECUTE sp_executesql @SQL,N'@count int OUTPUT',@count OUTPUT   
    SET @HistoryMyArticleCount = @HistoryMyArticleCount + @count
    
    SET @SQL = N'SELECT @count = COUNT(*) FROM ' + @tableName + ' WHERE UserId=' + LTRIM(RTRIM(STR(@UserId))) + ' And AuditState = 0 And HideState = 0 And DraftState = 0 And DelState = 0 And TypeState=1'
    EXECUTE sp_executesql @SQL,N'@count int OUTPUT',@count OUTPUT   
    SET @HistoryOtherArticleCount = @HistoryOtherArticleCount + @count
    
    PRINT '表：' + @tablename + '，MyArticleCount文章数：' + STR(@HistoryMyArticleCount)
    PRINT '表：' + @tablename + '，OtherArticleCount文章数：' + STR(@HistoryOtherArticleCount)
  FETCH NEXT FROM BackYear_Cursor INTO @BackYear
  END
  CLOSE BackYear_Cursor
  DEALLOCATE BackYear_Cursor
  PRINT 'MyArticleCount总文章数：' + STR(@HistoryMyArticleCount)
  PRINT 'OtherArticleCount总文章数：' + STR(@HistoryOtherArticleCount)
  UPDATE Jitar_User SET HistoryMyArticleCount = @HistoryMyArticleCount,HistoryOtherArticleCount = @HistoryOtherArticleCount WHERE UserId=@UserId
  RETURN @HistoryMyArticleCount + @HistoryOtherArticleCount
END
GO
/****** Object:  StoredProcedure [dbo].[CountYearArticleSubject]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS OFF
GO
SET QUOTED_IDENTIFIER OFF
GO
-- =============================================
-- Author:    孟宪会
-- Create date: 2012-5-31
-- Description: 统计学科的历史文章数,注意只是统计历史备份表的数量，因为最新的数量每天都在统计的
-- =============================================
CREATE PROCEDURE [dbo].[CountYearArticleSubject]
@year int,
@subjectId int = 0,
@gradeId int = 0
As

DECLARE @count int
SET @count = 0
DECLARE @tablename varchar(256)
DECLARE @SQL nvarchar(256)
IF EXISTS (SELECT * FROM Jitar_BackYear WHERE BackYear = @year)
BEGIN
  SET @tableName = 'HtmlArticle' + LTRIM(RTRIM(str(@year)))
  SET @SQL = N'SELECT @count = COUNT(*) FROM ' + @tableName + ' WHERE subjectId=' + STR(@subjectId) + ' And gradeId=' + STR(@gradeId)
  EXECUTE sp_executesql @SQL,N'@count int OUTPUT',@count OUTPUT
END
-- ELSE
-- BEGIN
--  SET @count = (SELECT COUNT(*) FROM Jitar_Article WHERE YEAR(CreateDate) = @year) + ' And subjectId=' + STR(@subjectId) + ' And gradeId=' + STR(@gradeId)
-- END
RETURN @count
GO
/****** Object:  StoredProcedure [dbo].[CountYearArticleUnit]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:    <Author,,Name>
-- Create date: <Create Date,,>
-- Description: 统计单位的历史文章数,注意只是统计历史备份表的数量，因为最新的数量每天都在统计的
-- =============================================
CREATE PROCEDURE [dbo].[CountYearArticleUnit]
@year int,
@unitId int = 0
As

DECLARE @count int
SET @count = 0
DECLARE @tablename varchar(256)
DECLARE @SQL nvarchar(256)
IF EXISTS (SELECT * FROM Jitar_BackYear WHERE BackYear = @year)
BEGIN
  SET @tableName = 'HtmlArticle' + LTRIM(RTRIM(str(@year)))
  SET @SQL = N'SELECT @count = COUNT(*) FROM ' + @tableName + ' WHERE UnitId=' + STR(@unitId) + ' And AuditState = 0 And HideState = 0 And DraftState = 0 And DelState = 0'
  EXECUTE sp_executesql @SQL,N'@count int OUTPUT',@count OUTPUT
END
-- ELSE
-- BEGIN
--  SET @count = (SELECT COUNT(*) FROM Jitar_Article WHERE YEAR(CreateDate) = @year) + ' And UnitId=' + STR(@unitId) + ' And AuditState = 0 And HideState = 0 And DraftState = 0 And DelState = 0'
-- END
RETURN @count
GO
/****** Object:  StoredProcedure [dbo].[CountYearArticle]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS OFF
GO
SET QUOTED_IDENTIFIER OFF
GO
CREATE PROCEDURE [dbo].[CountYearArticle]
@year int
As

DECLARE @count int
SET @count = 0
DECLARE @tablename varchar(256)
DECLARE @SQL nvarchar(256)
IF EXISTS (SELECT * FROM Jitar_BackYear WHERE BackYear = @year)
BEGIN
  SET @tableName = 'HtmlArticle' + LTRIM(RTRIM(str(@year)))
  SET @SQL = N'SELECT @count = COUNT(*) FROM ' + @tableName
  EXECUTE sp_executesql @SQL,N'@count int OUTPUT',@count OUTPUT
  SET @SQL = 'UPDATE Jitar_BackYear SET BackYearCount = ' + STR(@count) + ' WHERE BackYear = ' + STR(@year)
  EXEC(@SQL)
END
ELSE
BEGIN
  SET @count = (SELECT COUNT(*) FROM Jitar_Article WHERE YEAR(CreateDate) = @year)
END
RETURN @count
GO
/****** Object:  StoredProcedure [dbo].[ModifyNullUnitIdArticle]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[ModifyNullUnitIdArticle]
AS

DECLARE @UserId int

DECLARE Article_Cursor CURSOR FOR
SELECT UserId FROM Jitar_Article Where UnitId Is Null
OPEN Article_Cursor
-- 移动游标到第一行
FETCH NEXT FROM Article_Cursor INTO  @UserId
-- Check @@FETCH_STATUS to see if there are any more rows to fetch.
WHILE @@FETCH_STATUS = 0
 BEGIN   
   UPDATE Jitar_Article SET OrginPath = (Select UnitPathInfo From Jitar_User Where UserId=@UserId) Where UserId=@UserId And UnitId Is Null
   UPDATE Jitar_Article SET UnitPathInfo = (Select UnitPathInfo From Jitar_User Where UserId=@UserId) Where UserId=@UserId And UnitId Is Null
   
   UPDATE Jitar_Article SET ApprovedPathInfo = (Select UnitPathInfo From Jitar_User Where UserId=@UserId)  Where UserId=@UserId And AuditState = 0
   UPDATE Jitar_Article SET RcmdPathInfo = (Select UnitPathInfo From Jitar_User Where UserId=@UserId) Where UserId=@UserId And RecommendState = 1
   FETCH NEXT FROM Article_Cursor  INTO  @UserId
END
CLOSE Article_Cursor
DEALLOCATE Article_Cursor
GO
/****** Object:  StoredProcedure [dbo].[ModifyArticle]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Batch submitted through debugger: SQLQuery3.sql|0|0|C:\Documents and Settings\Administrator\Local Settings\Temp\2\~vsA.sql
--修正没有UnitId字段值的文章

CREATE PROCEDURE [dbo].[ModifyArticle]
AS

DECLARE @UserId int
DECLARE @ArticleId int

DECLARE Article_Cursor CURSOR FOR
SELECT UserId,ArticleId FROM Jitar_Article Where UnitId Is Null Or UnitId = 0
OPEN Article_Cursor
-- 移动游标到第一行
FETCH NEXT FROM Article_Cursor INTO @UserId,@ArticleId
-- Check @@FETCH_STATUS to see if there are any more rows to fetch.
WHILE @@FETCH_STATUS = 0
 BEGIN   
   UPDATE Jitar_Article SET UnitId = (Select UnitId From Jitar_User Where UserId=@UserId) Where UserId=@UserId And ArticleId = @ArticleId
   FETCH NEXT FROM Article_Cursor INTO @UserId,@ArticleId
END
CLOSE Article_Cursor
DEALLOCATE Article_Cursor
GO
/****** Object:  StoredProcedure [dbo].[StatAllUserArticle]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[StatAllUserArticle] 
AS 
  BEGIN 
    DECLARE @userId INT
    DECLARE user_cursor CURSOR FOR SELECT userId FROM Jitar_User 
    OPEN user_cursor 
    FETCH NEXT FROM user_cursor INTO @userId 
    WHILE (@@FETCH_STATUS = 0) 
      BEGIN
        UPDATE Jitar_User SET myArticleCount = (SELECT COUNT(*) FROM HtmlArticleBase WHERE ((auditState = 0) AND (delState = 0) AND (draftState = 0) AND (hideState = 0) AND (typeState = 0) AND (userId = @userId))) WHERE (userId = @userId)
        UPDATE Jitar_User SET otherArticleCount = (SELECT COUNT(*) FROM HtmlArticleBase WHERE ((auditState = 0) AND (delState = 0) AND (draftState = 0) AND (hideState = 0) AND (typeState = 1) AND (userId = @userId))) WHERE (userId = @userId)
        FETCH NEXT FROM user_cursor INTO @userId
      END
    CLOSE user_cursor
    DEALLOCATE user_cursor
  END
GO
/****** Object:  View [dbo].[ActionUserUnit]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[ActionUserUnit]
AS
SELECT  dbo.A_ActionUser.ActionUserId, dbo.A_ActionUser.UserId, dbo.A_ActionUser.AttendUserCount, dbo.A_ActionUser.Description AS ActionUserDescription, 
        dbo.A_ActionUser.InviteUserId, dbo.A_ActionUser.IsApprove, dbo.A_ActionUser.Status AS ActionUserStatus, dbo.JItar_User.LoginName, 
        dbo.Jitar_User.TrueName, dbo.Jitar_User.NickName,  dbo.Jitar_Unit.UnitName, dbo.Jitar_Unit.UnitTitle, dbo.A_Action.Title, 
        dbo.A_Action.OwnerId, dbo.A_Action.OwnerType, dbo.A_Action.CreateUserId, dbo.A_Action.CreateDate, dbo.A_Action.ActionType, 
        dbo.A_Action.Description AS ActionDescription, dbo.A_Action.UserLimit, dbo.A_Action.StartDateTime, dbo.A_Action.FinishDateTime, 
        dbo.A_Action.AttendLimitDateTime, dbo.A_Action.Place, dbo.A_Action.AttendCount, dbo.A_Action.AttendSuccessCount, dbo.A_Action.AttendQuitCount, 
        dbo.A_Action.AttendFailCount, dbo.A_Action.Status AS ActionStatus, dbo.A_Action.Visibility, dbo.A_ActionUser.ActionId, dbo.A_Action.IsLock, 
        dbo.A_ActionUser.CreateDate AS ActionUserCreateDate
FROM dbo.A_ActionUser INNER JOIN
     dbo.Jitar_User ON dbo.A_ActionUser.UserId = dbo.Jitar_User.UserId INNER JOIN
    dbo.A_Action ON dbo.A_ActionUser.ActionId = dbo.A_Action.ActionId LEFT OUTER JOIN
    dbo.Jitar_Unit ON dbo.Jitar_User.UnitId = dbo.Jitar_Unit.UnitId
GO
/****** Object:  View [dbo].[UnitGroup]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[UnitGroup]
AS
SELECT G_Group.GroupId, G_Group.GroupTitle, G_Group.GroupName, Jitar_User.UnitId
FROM G_GroupMember INNER JOIN Jitar_User ON G_GroupMember.UserId = Jitar_User.UserId LEFT OUTER JOIN G_Group ON G_Group.GroupId = G_GroupMember.GroupId
WHERE (G_GroupMember.GroupRole = 1000) AND (G_Group.GroupState = 0)
GO
/****** Object:  StoredProcedure [dbo].[GenUnitEnName]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[GenUnitEnName]
AS
DECLARE @UnitId int
DECLARE @UnitTitle nvarchar(100)
DECLARE @UnitName varchar(100)
--先清空
print '先清空'
UPDATE Jitar_Unit Set UnitName = ''
DECLARE unit_cursor CURSOR FOR
SELECT UnitId FROM Jitar_Unit
OPEN unit_cursor
print (@@FETCH_STATUS)
-- 移动游标到第一行
FETCH NEXT FROM unit_cursor  INTO  @UnitId
-- Check @@FETCH_STATUS to see if there are any more rows to fetch.
WHILE @@FETCH_STATUS = 0
BEGIN
   Set @UnitTitle = (Select UnitTitle  From Jitar_Unit Where UnitId=@UnitId)
   Set @UnitName = (SELECT dbo.NWGetPYFirst(@UnitTitle))
   If Exists (Select UnitName From Jitar_Unit Where  UnitName=@UnitName)
   BEGIN
   UPDATE Jitar_Unit SET UnitName = @UnitName +  LTrim(str(UnitID)) Where UnitId=@UnitId
   End
   Else
   BEGIN
   UPDATE Jitar_Unit SET UnitName = @UnitName  Where UnitId=@UnitId
   End   
  print @UnitName
  FETCH NEXT FROM unit_cursor  INTO  @UnitId
END
CLOSE unit_cursor
DEALLOCATE unit_cursor
GO
/****** Object:  StoredProcedure [dbo].[GroupQuery]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[GroupQuery]
(

@StartDate varchar(50)
)
AS
SELECT * FROM G_GroupMember
GO
/****** Object:  View [dbo].[BlogCategory]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[BlogCategory]
AS
SELECT *
FROM dbo.B_Category
WHERE (ItemType = 'default')
GO
/****** Object:  View [dbo].[vPrepareCourseEdit]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[vPrepareCourseEdit]
AS
SELECT     PrepareCourseId, COUNT(PrepareCourseEditId) AS cc
FROM         dbo.C_PrepareCourseEdit
GROUP BY PrepareCourseId
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1[50] 4[25] 3) )"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "C_PrepareCourseEdit"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 125
               Right = 227
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      Begin ColumnWidths = 9
         Width = 284
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 12
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'vPrepareCourseEdit'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'USER',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'vPrepareCourseEdit'
GO
/****** Object:  View [dbo].[vPrepareCourseMember]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[vPrepareCourseMember]
AS
SELECT     PrepareCourseId, COUNT(PrepareCourseMemberId) AS cc
FROM         dbo.C_PrepareCourseMember
WHERE     (DATALENGTH(PrivateContent) > 0)
GROUP BY PrepareCourseId
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[16] 4[26] 2[16] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1[50] 4[25] 3) )"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "C_PrepareCourseMember"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 240
               Right = 354
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      Begin ColumnWidths = 9
         Width = 284
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 12
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'USER',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'vPrepareCourseMember'
GO
EXEC dbo.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'USER',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'vPrepareCourseMember'
GO
/****** Object:  StoredProcedure [dbo].[statAllUnit]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[statAllUnit] 
( 
  @unitId   Int,      -- 单位ID 
  @beginDate  Varchar(10),  -- 开始日期 
  @endDate  Varchar(10)   -- 结束日期 
) 
AS 
  BEGIN 
  DECLARE @studioCount int,         -- 工作室数 
      @articleCount int,        -- 文章数 
      @myArticleCount int,      -- 原创文章数 
      @otherArticleCount int,     -- 转载文章数 
      @recommendArticleCount int,   -- 推荐文章数 
      @resourceCount int,       -- 资源数 
      @recommendResourceCount int,  -- 推荐资源数 
      @photoCount int,        -- 图片数 
      @videoCount int,        -- 视频数 
      @totalScore int,        -- 积分总数 
      @scoreMyarticleadd varchar(10),   -- 发表原创文章得分 
      @scoreOtherarticleadd varchar(10),  -- 发表转载文章得分 
      @scorearticlercmd varchar(10),    -- 文章被推荐得分 
      @scoreresourceadd varchar(10),    -- 上载资源得分 
      @scoreresourcercmd varchar(10),   -- 资源被推荐得分 
      @scorePhotoUpload varchar(10),    -- 发布照片得分 
      @scoreVideoUpload varchar(10)   -- 上传视频得分 
 
  
  EXEC UnitRank @unitId
  -- 工作室数 
  SELECT @studioCount = COUNT(*)  
  FROM Jitar_User  
  WHERE ((unitId = @unitId) AND (createDate BETWEEN @beginDate AND @endDate)) 
  -- 更新 
  UPDATE Jitar_Unit SET UserCount = @studioCount WHERE (unitId = @unitId) 
   
  -- 文章数 
  SELECT @articleCount = COUNT(*)  
  FROM Jitar_Article  
  WHERE ((unitId = @unitId) AND (createDate BETWEEN @beginDate AND @endDate)) 
  -- 更新 
  UPDATE Jitar_Unit SET articleCount = @articleCount WHERE (unitId = @unitId) 
   
  -- 原创文章数 
  SELECT @myArticleCount = COUNT(*)  
  FROM Jitar_Article  
  WHERE ((unitId = @unitId) AND (createDate BETWEEN @beginDate AND @endDate)  
    AND (auditState = 0) AND (delState = 0) AND (draftState = 0) AND (hideState = 0)  
    AND (typeState = 0)) 
   
  -- 转载文章数 
  SELECT @otherArticleCount = COUNT(*)  
  FROM Jitar_Article  
  WHERE ((unitId = @unitId) AND (createDate BETWEEN @beginDate AND @endDate)  
    AND (auditState = 0) AND (delState = 0) AND (draftState = 0) AND (hideState = 0)  
    AND (typeState = 1)) 
       
  -- 推荐文章数 
  SELECT @recommendArticleCount = COUNT(*)  
  FROM Jitar_Article  
  WHERE ((recommendState = 1) AND (unitId = @unitId) AND (createDate BETWEEN @beginDate AND @endDate)) 
  -- 更新 
  UPDATE Jitar_Unit SET recommendArticleCount = @recommendArticleCount WHERE (unitId = @unitId) 
   
  -- 资源数 
  SELECT @resourceCount = COUNT(*)  
  FROM Jtr_Resource  
  WHERE ((unitId = @unitId) AND (createDate BETWEEN @beginDate AND @endDate)) 
  -- 更新 
  UPDATE Jitar_Unit SET resourceCount = @resourceCount WHERE (unitId = @unitId) 
   
  -- 推荐资源数 
  SELECT @recommendResourceCount = COUNT(*)  
  FROM Jtr_Resource  
  WHERE ((recommendState = 1) AND (unitId = @unitId) AND (createDate BETWEEN @beginDate AND @endDate)) 
  -- 更新 
  UPDATE Jitar_Unit SET recommendResourceCount = @recommendResourceCount WHERE (unitId = @unitId) 
       
  -- 图片数 
  SELECT @photoCount = COUNT(*)  
  FROM B_Photo  
  WHERE ((unitId = @unitId) AND (createDate BETWEEN @beginDate AND @endDate)) 
  -- 更新 
  UPDATE Jitar_Unit SET photoCount = @photoCount WHERE (unitId = @unitId) 
   
  -- 视频数 
  SELECT @videoCount = COUNT(*)  
  FROM B_Video  
  WHERE ((unitId = @unitId) AND (v_createDate BETWEEN @beginDate AND @endDate)) 
  -- 更新 
  UPDATE Jitar_Unit SET videoCount = @videoCount WHERE (unitId = @unitId) 
   
  -- 统计总积分： 
 
    -- 文章总分 = ((发表原创文章得分*原创文章数) + (发表转载文章得分*转载文章数) + (文章被推荐得分*推荐文章数)) 
    SELECT @scoreMyarticleadd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.my.article.add' 
    SELECT @scoreOtherarticleadd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.other.article.add' 
    SELECT @scorearticlercmd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.article.rcmd' 
    -- 统计 
    -- articleScore = @scoreMyarticleadd * @myArticleCount + @scoreOtherarticleadd * @otherArticleCount + @scorearticlercmd * @recommendArticleCount 
 
    -- 资源总分 = ((上载资源积分 * 资源数) + (资源被推荐积分 * 推荐资源数)) 
    SELECT @scoreresourceadd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.resource.add' 
    SELECT @scoreresourcercmd = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.resource.rcmd' 
    -- 统计 
    -- resourceScore = @scoreresourceadd * @resourceCount + @scoreresourcercmd * @recommendResourceCount 
 
    -- 图片数 = 上传图片得分 * 图片数 
    SELECT @scorePhotoUpload = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.photo.upload' 
    -- 统计 
    -- photoScore = @scorePhotoUpload * @photoCount 
 
    -- 视频数 = 上传视频得分 * 视频数 
    SELECT @scoreVideoUpload = CAST(value AS VARCHAR) FROM S_Config WHERE name = 'score.video.upload' 
    -- 统计 
    -- videoScore = @scorePhotoUpload * @videoCount 
   
  -- 更新 
  UPDATE Jitar_Unit  
  SET totalScore =  
    @scoreMyarticleadd * @myArticleCount + @scoreOtherarticleadd * @otherArticleCount + @scorearticlercmd * @recommendArticleCount +  
    @scoreresourceadd * @resourceCount + @scoreresourcercmd * @recommendResourceCount +  
    @scorePhotoUpload * @photoCount +  
    @scoreVideoUpload * @videoCount  
  WHERE (unitId = @unitId) 

  END
GO
/****** Object:  StoredProcedure [dbo].[PerformUnitStat]    Script Date: 09/16/2013 16:02:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[PerformUnitStat]
AS
DECLARE @UnitId int
DECLARE unit_cursor CURSOR FOR
SELECT UnitId FROM Jitar_Unit
OPEN unit_cursor
FETCH NEXT FROM unit_cursor
-- Check @@FETCH_STATUS to see if there are any more rows to fetch.
WHILE @@FETCH_STATUS = 0
BEGIN
   FETCH NEXT FROM unit_cursor
   INTO  @UnitId
   EXEC UnitRank @UnitId
  print @UnitId
END
CLOSE unit_cursor
DEALLOCATE unit_cursor
GO
/****** Object:  Default [DF_G_Action_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[A_Action] ADD  CONSTRAINT [DF_G_Action_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_G_Action_ActionType]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[A_Action] ADD  CONSTRAINT [DF_G_Action_ActionType]  DEFAULT (0) FOR [ActionType]
GO
/****** Object:  Default [DF_G_Action_StartDateTime]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[A_Action] ADD  CONSTRAINT [DF_G_Action_StartDateTime]  DEFAULT (getdate()) FOR [StartDateTime]
GO
/****** Object:  Default [DF_G_Action_FinishDateTime]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[A_Action] ADD  CONSTRAINT [DF_G_Action_FinishDateTime]  DEFAULT (getdate()) FOR [FinishDateTime]
GO
/****** Object:  Default [DF_G_Action_AttendLimitDateTime]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[A_Action] ADD  CONSTRAINT [DF_G_Action_AttendLimitDateTime]  DEFAULT (getdate()) FOR [AttendLimitDateTime]
GO
/****** Object:  Default [DF_G_Action_AttendCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[A_Action] ADD  CONSTRAINT [DF_G_Action_AttendCount]  DEFAULT (0) FOR [AttendCount]
GO
/****** Object:  Default [DF_G_Action_AttendSuccessCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[A_Action] ADD  CONSTRAINT [DF_G_Action_AttendSuccessCount]  DEFAULT (0) FOR [AttendSuccessCount]
GO
/****** Object:  Default [DF_G_Action_AttendSQuitCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[A_Action] ADD  CONSTRAINT [DF_G_Action_AttendSQuitCount]  DEFAULT (0) FOR [AttendQuitCount]
GO
/****** Object:  Default [DF_G_Action_AttendFailCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[A_Action] ADD  CONSTRAINT [DF_G_Action_AttendFailCount]  DEFAULT (0) FOR [AttendFailCount]
GO
/****** Object:  Default [DF_G_Action_Status]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[A_Action] ADD  CONSTRAINT [DF_G_Action_Status]  DEFAULT (0) FOR [Status]
GO
/****** Object:  Default [DF_G_Action_Visibility]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[A_Action] ADD  CONSTRAINT [DF_G_Action_Visibility]  DEFAULT (0) FOR [Visibility]
GO
/****** Object:  Default [DF_A_Action_IsLock]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[A_Action] ADD  CONSTRAINT [DF_A_Action_IsLock]  DEFAULT (0) FOR [IsLock]
GO
/****** Object:  Default [DF_G_ActionReply_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[A_ActionReply] ADD  CONSTRAINT [DF_G_ActionReply_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_A_ActionUser_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[A_ActionUser] ADD  CONSTRAINT [DF_A_ActionUser_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_G_ActionUser_AttendCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[A_ActionUser] ADD  CONSTRAINT [DF_G_ActionUser_AttendCount]  DEFAULT (0) FOR [AttendUserCount]
GO
/****** Object:  Default [DF_A_ActionUser_IsApprove]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[A_ActionUser] ADD  CONSTRAINT [DF_A_ActionUser_IsApprove]  DEFAULT (0) FOR [IsApprove]
GO
/****** Object:  Default [DF_A_ActionUser_Status]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[A_ActionUser] ADD  CONSTRAINT [DF_A_ActionUser_Status]  DEFAULT (0) FOR [Status]
GO
/****** Object:  Default [DF_B_Category_ObjectUuid]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Category] ADD  CONSTRAINT [DF_B_Category_ObjectUuid]  DEFAULT (newid()) FOR [ObjectUuid]
GO
/****** Object:  Default [DF_B_Category_ItemType]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Category] ADD  CONSTRAINT [DF_B_Category_ItemType]  DEFAULT ('') FOR [ItemType]
GO
/****** Object:  Default [DF_B_Category_ChildNum]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Category] ADD  CONSTRAINT [DF_B_Category_ChildNum]  DEFAULT (0) FOR [OrderNum]
GO
/****** Object:  Default [DF_B_Category_ChildNum_1]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Category] ADD  CONSTRAINT [DF_B_Category_ChildNum_1]  DEFAULT (0) FOR [ChildNum]
GO
/****** Object:  Default [DF_B_Category_ItemNum]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Category] ADD  CONSTRAINT [DF_B_Category_ItemNum]  DEFAULT (0) FOR [ItemNum]
GO
/****** Object:  Default [DF_B_Category_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Category] ADD  CONSTRAINT [DF_B_Category_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_B_Category_LastModified]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Category] ADD  CONSTRAINT [DF_B_Category_LastModified]  DEFAULT (getdate()) FOR [LastModified]
GO
/****** Object:  Default [DF__B_Categor__IsSys__3FD07829]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Category] ADD  DEFAULT (0) FOR [IsSystem]
GO
/****** Object:  Default [DF_B_Comment_Title]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Comment] ADD  CONSTRAINT [DF_B_Comment_Title]  DEFAULT ('') FOR [Title]
GO
/****** Object:  Default [DF_B_Comment_Audit]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Comment] ADD  CONSTRAINT [DF_B_Comment_Audit]  DEFAULT (1) FOR [Audit]
GO
/****** Object:  Default [DF_B_Comment_Star]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Comment] ADD  CONSTRAINT [DF_B_Comment_Star]  DEFAULT (0) FOR [Star]
GO
/****** Object:  Default [DF_B_Comment_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Comment] ADD  CONSTRAINT [DF_B_Comment_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_B_Comment_UserName]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Comment] ADD  CONSTRAINT [DF_B_Comment_UserName]  DEFAULT ('') FOR [UserName]
GO
/****** Object:  Default [DF_B_Comment_Ip]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Comment] ADD  CONSTRAINT [DF_B_Comment_Ip]  DEFAULT ('') FOR [Ip]
GO
/****** Object:  Default [DF_B_Photo_ObjectUuid]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Photo] ADD  CONSTRAINT [DF_B_Photo_ObjectUuid]  DEFAULT (newid()) FOR [ObjectUuid]
GO
/****** Object:  Default [DF_B_Photo_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Photo] ADD  CONSTRAINT [DF_B_Photo_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_B_Photo_LastModified]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Photo] ADD  CONSTRAINT [DF_B_Photo_LastModified]  DEFAULT (getdate()) FOR [LastModified]
GO
/****** Object:  Default [DF_B_Photo_ViewCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Photo] ADD  CONSTRAINT [DF_B_Photo_ViewCount]  DEFAULT (0) FOR [ViewCount]
GO
/****** Object:  Default [DF_B_Photo_CommentCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Photo] ADD  CONSTRAINT [DF_B_Photo_CommentCount]  DEFAULT (0) FOR [CommentCount]
GO
/****** Object:  Default [DF_B_Photo_AuditState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Photo] ADD  CONSTRAINT [DF_B_Photo_AuditState]  DEFAULT (0) FOR [AuditState]
GO
/****** Object:  Default [DF_B_Photo_DelState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Photo] ADD  CONSTRAINT [DF_B_Photo_DelState]  DEFAULT (0) FOR [DelState]
GO
/****** Object:  Default [DF_B_Photo_Href]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Photo] ADD  CONSTRAINT [DF_B_Photo_Href]  DEFAULT ('') FOR [Href]
GO
/****** Object:  Default [DF_B_Photo_Width]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Photo] ADD  CONSTRAINT [DF_B_Photo_Width]  DEFAULT (0) FOR [Width]
GO
/****** Object:  Default [DF_B_Photo_Height]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Photo] ADD  CONSTRAINT [DF_B_Photo_Height]  DEFAULT (0) FOR [Height]
GO
/****** Object:  Default [DF_B_Photo_FileSize]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Photo] ADD  CONSTRAINT [DF_B_Photo_FileSize]  DEFAULT (0) FOR [FileSize]
GO
/****** Object:  Default [DF_B_Photo_IsPrivateShow]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Photo] ADD  CONSTRAINT [DF_B_Photo_IsPrivateShow]  DEFAULT (0) FOR [IsPrivateShow]
GO
/****** Object:  Default [DF_B_Photo_SpecialSubjectId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Photo] ADD  CONSTRAINT [DF_B_Photo_SpecialSubjectId]  DEFAULT (0) FOR [SpecialSubjectId]
GO
/****** Object:  Default [DF__B_Photo__UnitId__52E34C9D]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Photo] ADD  DEFAULT (0) FOR [UnitId]
GO
/****** Object:  Default [DF_B_PhotoStaple_IsHide]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_PhotoStaple] ADD  CONSTRAINT [DF_B_PhotoStaple_IsHide]  DEFAULT (0) FOR [IsHide]
GO
/****** Object:  Default [DF_B_PhotoStaple_ParentPath]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_PhotoStaple] ADD  CONSTRAINT [DF_B_PhotoStaple_ParentPath]  DEFAULT ('/') FOR [ParentPath]
GO
/****** Object:  Default [DF_B_Staple_OrderNum]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Staple] ADD  CONSTRAINT [DF_B_Staple_OrderNum]  DEFAULT (0) FOR [OrderNum]
GO
/****** Object:  Default [DF_B_Staple_StapleType]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Staple] ADD  CONSTRAINT [DF_B_Staple_StapleType]  DEFAULT (0) FOR [StapleType]
GO
/****** Object:  Default [DF_B_Staple_Invisible]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Staple] ADD  CONSTRAINT [DF_B_Staple_Invisible]  DEFAULT (0) FOR [Invisible]
GO
/****** Object:  Default [DF_B_Staple_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Staple] ADD  CONSTRAINT [DF_B_Staple_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_B_Staple_BlogNum]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Staple] ADD  CONSTRAINT [DF_B_Staple_BlogNum]  DEFAULT (0) FOR [BlogNum]
GO
/****** Object:  Default [DF_B_Staple_Views]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Staple] ADD  CONSTRAINT [DF_B_Staple_Views]  DEFAULT (0) FOR [Views]
GO
/****** Object:  Default [DF_B_Video_ObjectUuid]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Video] ADD  CONSTRAINT [DF_B_Video_ObjectUuid]  DEFAULT (newid()) FOR [ObjectUuid]
GO
/****** Object:  Default [DF_B_Video_V_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Video] ADD  CONSTRAINT [DF_B_Video_V_CreateDate]  DEFAULT (getdate()) FOR [V_CreateDate]
GO
/****** Object:  Default [DF_B_Video_V_LastModified]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Video] ADD  CONSTRAINT [DF_B_Video_V_LastModified]  DEFAULT (getdate()) FOR [V_LastModified]
GO
/****** Object:  Default [DF_B_Video_V_ViewCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Video] ADD  CONSTRAINT [DF_B_Video_V_ViewCount]  DEFAULT (0) FOR [V_ViewCount]
GO
/****** Object:  Default [DF_B_Video_V_CommentCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Video] ADD  CONSTRAINT [DF_B_Video_V_CommentCount]  DEFAULT (0) FOR [V_CommentCount]
GO
/****** Object:  Default [DF_B_Video_V_DownloadCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Video] ADD  CONSTRAINT [DF_B_Video_V_DownloadCount]  DEFAULT (0) FOR [V_DownloadCount]
GO
/****** Object:  Default [DF_B_Video_V_DeleteState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Video] ADD  CONSTRAINT [DF_B_Video_V_DeleteState]  DEFAULT (0) FOR [V_DeleteState]
GO
/****** Object:  Default [DF_B_Video_V_AuditState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Video] ADD  CONSTRAINT [DF_B_Video_V_AuditState]  DEFAULT (0) FOR [V_AuditState]
GO
/****** Object:  Default [DF_B_Video_V_TypeState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Video] ADD  CONSTRAINT [DF_B_Video_V_TypeState]  DEFAULT (0) FOR [V_TypeState]
GO
/****** Object:  Default [DF__B_Video__UnitId__67352964]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Video] ADD  CONSTRAINT [DF__B_Video__UnitId__67352964]  DEFAULT (0) FOR [UnitId]
GO
/****** Object:  Default [DF__B_Video__gradeId__68294D9D]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Video] ADD  CONSTRAINT [DF__B_Video__gradeId__68294D9D]  DEFAULT (0) FOR [gradeId]
GO
/****** Object:  Default [DF__B_Video__subject__691D71D6]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Video] ADD  CONSTRAINT [DF__B_Video__subject__691D71D6]  DEFAULT (0) FOR [subjectId]
GO
/****** Object:  Default [DF__B_Video__Status__66EA454A]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[B_Video] ADD  DEFAULT (0) FOR [Status]
GO
/****** Object:  Default [DF_BBS_Board_Title]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[BBS_Boards] ADD  CONSTRAINT [DF_BBS_Board_Title]  DEFAULT ('') FOR [Title]
GO
/****** Object:  Default [DF_BBS_Board_VisitorCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[BBS_Boards] ADD  CONSTRAINT [DF_BBS_Board_VisitorCount]  DEFAULT (0) FOR [VisitorCount]
GO
/****** Object:  Default [DF_BBS_Board_TopicCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[BBS_Boards] ADD  CONSTRAINT [DF_BBS_Board_TopicCount]  DEFAULT (0) FOR [TopicCount]
GO
/****** Object:  Default [DF_BBS_Board_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[BBS_Boards] ADD  CONSTRAINT [DF_BBS_Board_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_BBS_Reply_TargetReply]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[BBS_Reply] ADD  CONSTRAINT [DF_BBS_Reply_TargetReply]  DEFAULT (0) FOR [TargetReply]
GO
/****** Object:  Default [DF_BBS_Reply_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[BBS_Reply] ADD  CONSTRAINT [DF_BBS_Reply_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_BBS_Reply_IsBest]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[BBS_Reply] ADD  CONSTRAINT [DF_BBS_Reply_IsBest]  DEFAULT (0) FOR [IsBest]
GO
/****** Object:  Default [DF_BBS_Reply_IsDeleted]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[BBS_Reply] ADD  CONSTRAINT [DF_BBS_Reply_IsDeleted]  DEFAULT (0) FOR [IsDeleted]
GO
/****** Object:  Default [DF_BBS_Topic_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[BBS_Topic] ADD  CONSTRAINT [DF_BBS_Topic_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_BBS_Topic_IsBest]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[BBS_Topic] ADD  CONSTRAINT [DF_BBS_Topic_IsBest]  DEFAULT (0) FOR [IsBest]
GO
/****** Object:  Default [DF_BBS_Topic_IsTop]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[BBS_Topic] ADD  CONSTRAINT [DF_BBS_Topic_IsTop]  DEFAULT (0) FOR [IsTop]
GO
/****** Object:  Default [DF_BBS_Topic_IsDeleted]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[BBS_Topic] ADD  CONSTRAINT [DF_BBS_Topic_IsDeleted]  DEFAULT (0) FOR [IsDeleted]
GO
/****** Object:  Default [DF_BBS_Topic_ReplyCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[BBS_Topic] ADD  CONSTRAINT [DF_BBS_Topic_ReplyCount]  DEFAULT (0) FOR [ReplyCount]
GO
/****** Object:  Default [DF_BBS_Topic_ViewCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[BBS_Topic] ADD  CONSTRAINT [DF_BBS_Topic_ViewCount]  DEFAULT (0) FOR [ViewCount]
GO
/****** Object:  Default [DF_BBS_Topic_Tags]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[BBS_Topic] ADD  CONSTRAINT [DF_BBS_Topic_Tags]  DEFAULT ('') FOR [Tags]
GO
/****** Object:  Default [DF_C_PrepareCourse_ObjectGuid]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourse] ADD  CONSTRAINT [DF_C_PrepareCourse_ObjectGuid]  DEFAULT (newid()) FOR [ObjectGuid]
GO
/****** Object:  Default [DF_C_PrepareCourse_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourse] ADD  CONSTRAINT [DF_C_PrepareCourse_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_C_PrepareCourse_MemberCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourse] ADD  CONSTRAINT [DF_C_PrepareCourse_MemberCount]  DEFAULT (0) FOR [MemberCount]
GO
/****** Object:  Default [DF_C_PrepareCourse_ArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourse] ADD  CONSTRAINT [DF_C_PrepareCourse_ArticleCount]  DEFAULT (0) FOR [ArticleCount]
GO
/****** Object:  Default [DF_C_PrepareCourse_ResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourse] ADD  CONSTRAINT [DF_C_PrepareCourse_ResourceCount]  DEFAULT (0) FOR [ResourceCount]
GO
/****** Object:  Default [DF_C_PrepareCourse_TopicReplyCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourse] ADD  CONSTRAINT [DF_C_PrepareCourse_TopicReplyCount]  DEFAULT (0) FOR [TopicReplyCount]
GO
/****** Object:  Default [DF_C_PrepareCourse_TopicCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourse] ADD  CONSTRAINT [DF_C_PrepareCourse_TopicCount]  DEFAULT (0) FOR [TopicCount]
GO
/****** Object:  Default [DF_C_PrepareCourse_ActionCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourse] ADD  CONSTRAINT [DF_C_PrepareCourse_ActionCount]  DEFAULT (0) FOR [ActionCount]
GO
/****** Object:  Default [DF_C_PrepareCourse_ViewCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourse] ADD  CONSTRAINT [DF_C_PrepareCourse_ViewCount]  DEFAULT (0) FOR [ViewCount]
GO
/****** Object:  Default [DF_C_PrepareCourse_Status]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourse] ADD  CONSTRAINT [DF_C_PrepareCourse_Status]  DEFAULT (0) FOR [Status]
GO
/****** Object:  Default [DF_C_PrepareCourse_LockedDateTime]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourse] ADD  CONSTRAINT [DF_C_PrepareCourse_LockedDateTime]  DEFAULT (getdate()) FOR [LockedDate]
GO
/****** Object:  Default [DF_C_PrepareCourse_LockedUserId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourse] ADD  CONSTRAINT [DF_C_PrepareCourse_LockedUserId]  DEFAULT (0) FOR [LockedUserId]
GO
/****** Object:  Default [DF_C_PrepareCourse_PrepareCourseEditId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourse] ADD  CONSTRAINT [DF_C_PrepareCourse_PrepareCourseEditId]  DEFAULT (0) FOR [PrepareCourseEditId]
GO
/****** Object:  Default [DF_C_PrepareCourse_PrepareCoursePlanId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourse] ADD  CONSTRAINT [DF_C_PrepareCourse_PrepareCoursePlanId]  DEFAULT (0) FOR [PrepareCoursePlanId]
GO
/****** Object:  Default [DF_C_PrepareCourse_PrepareCourseGenerated]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourse] ADD  CONSTRAINT [DF_C_PrepareCourse_PrepareCourseGenerated]  DEFAULT (0) FOR [PrepareCourseGenerated]
GO
/****** Object:  Default [DF_C_PrepareCourse_ItemOrder]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourse] ADD  CONSTRAINT [DF_C_PrepareCourse_ItemOrder]  DEFAULT (0) FOR [ItemOrder]
GO
/****** Object:  Default [DF__C_Prepare__Recom__056ECC6A]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourse] ADD  DEFAULT (0) FOR [RecommendState]
GO
/****** Object:  Default [DF_C_PrepareCourse_ContentType]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourse] ADD  CONSTRAINT [DF_C_PrepareCourse_ContentType]  DEFAULT (1) FOR [ContentType]
GO
/****** Object:  Default [DF_C_PrepareCourseArticle_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseArticle] ADD  CONSTRAINT [DF_C_PrepareCourseArticle_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_C_PrepareCourseArticle_ArticleTitle]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseArticle] ADD  CONSTRAINT [DF_C_PrepareCourseArticle_ArticleTitle]  DEFAULT ('无标题') FOR [Title]
GO
/****** Object:  Default [DF__C_Prepare__Login__093F5D4E]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseArticle] ADD  DEFAULT ('') FOR [LoginName]
GO
/****** Object:  Default [DF__C_Prepare__UserT__0A338187]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseArticle] ADD  DEFAULT ('') FOR [UserTrueName]
GO
/****** Object:  Default [DF__C_Prepare__Artic__0B27A5C0]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseArticle] ADD  DEFAULT (1) FOR [ArticleState]
GO
/****** Object:  Default [DF_C_PrepareCourseArticle_TypeState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseArticle] ADD  CONSTRAINT [DF_C_PrepareCourseArticle_TypeState]  DEFAULT (0) FOR [TypeState]
GO
/****** Object:  Default [DF_C_PrepareCourseEdit_EditDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseEdit] ADD  CONSTRAINT [DF_C_PrepareCourseEdit_EditDate]  DEFAULT (getdate()) FOR [EditDate]
GO
/****** Object:  Default [DF_C_PrepareCourseEdit_EditUserId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseEdit] ADD  CONSTRAINT [DF_C_PrepareCourseEdit_EditUserId]  DEFAULT (0) FOR [EditUserId]
GO
/****** Object:  Default [DF_C_PrepareCourseEdit_LockStatus]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseEdit] ADD  CONSTRAINT [DF_C_PrepareCourseEdit_LockStatus]  DEFAULT (1) FOR [LockStatus]
GO
/****** Object:  Default [DF_C_PrepareCourseMember_ReplyCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseMember] ADD  CONSTRAINT [DF_C_PrepareCourseMember_ReplyCount]  DEFAULT (0) FOR [ReplyCount]
GO
/****** Object:  Default [DF_C_PrepareCourseMember_JoinDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseMember] ADD  CONSTRAINT [DF_C_PrepareCourseMember_JoinDate]  DEFAULT (getdate()) FOR [JoinDate]
GO
/****** Object:  Default [DF_C_PrepareCourseMember_Status]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseMember] ADD  CONSTRAINT [DF_C_PrepareCourseMember_Status]  DEFAULT (0) FOR [Status]
GO
/****** Object:  Default [DF_C_PrepareCourseMember_ContentLastupdated]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseMember] ADD  CONSTRAINT [DF_C_PrepareCourseMember_ContentLastupdated]  DEFAULT (getdate()) FOR [ContentLastupdated]
GO
/****** Object:  Default [DF__C_Prepare__BestS__13BCEBC1]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseMember] ADD  DEFAULT (0) FOR [BestState]
GO
/****** Object:  Default [DF_C_PrepareCourseMember_ContentType]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseMember] ADD  CONSTRAINT [DF_C_PrepareCourseMember_ContentType]  DEFAULT (0) FOR [ContentType]
GO
/****** Object:  Default [DF_C_PrepareCoursePlan_StartDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCoursePlan] ADD  CONSTRAINT [DF_C_PrepareCoursePlan_StartDate]  DEFAULT (getdate()) FOR [StartDate]
GO
/****** Object:  Default [DF_C_PrepareCoursePlan_EndDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCoursePlan] ADD  CONSTRAINT [DF_C_PrepareCoursePlan_EndDate]  DEFAULT (getdate()) FOR [EndDate]
GO
/****** Object:  Default [DF_C_PrepareCoursePlan_DefaultPlan]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCoursePlan] ADD  CONSTRAINT [DF_C_PrepareCoursePlan_DefaultPlan]  DEFAULT (0) FOR [DefaultPlan]
GO
/****** Object:  Default [DF_C_PrepareCoursePlan_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCoursePlan] ADD  CONSTRAINT [DF_C_PrepareCoursePlan_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_C_PrepareCoursePrivateComment_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCoursePrivateComment] ADD  CONSTRAINT [DF_C_PrepareCoursePrivateComment_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_C_PrepareCourseRelated_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseRelated] ADD  CONSTRAINT [DF_C_PrepareCourseRelated_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_C_PrepareCourseResource_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseResource] ADD  CONSTRAINT [DF_C_PrepareCourseResource_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_C_PrepareCourseStage_OrderIndex]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseStage] ADD  CONSTRAINT [DF_C_PrepareCourseStage_OrderIndex]  DEFAULT (0) FOR [OrderIndex]
GO
/****** Object:  Default [DF_C_PrepareCourseTopic_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseTopic] ADD  CONSTRAINT [DF_C_PrepareCourseTopic_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_C_PrepareCourseStageReply_PrepareCourseId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseTopicReply] ADD  CONSTRAINT [DF_C_PrepareCourseStageReply_PrepareCourseId]  DEFAULT (0) FOR [PrepareCourseId]
GO
/****** Object:  Default [DF_C_PrepareCourseStageReply_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseTopicReply] ADD  CONSTRAINT [DF_C_PrepareCourseStageReply_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_C_PrepareCourseVideo_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[C_PrepareCourseVideo] ADD  CONSTRAINT [DF_C_PrepareCourseVideo_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_ContentSpace_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[ContentSpace] ADD  CONSTRAINT [DF_ContentSpace_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_ContentSpace_SpaceType]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[ContentSpace] ADD  CONSTRAINT [DF_ContentSpace_SpaceType]  DEFAULT (0) FOR [OwnerType]
GO
/****** Object:  Default [DF_ContentSpace_OwnerId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[ContentSpace] ADD  CONSTRAINT [DF_ContentSpace_OwnerId]  DEFAULT (0) FOR [OwnerId]
GO
/****** Object:  Default [DF_ContentSpace_ArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[ContentSpace] ADD  CONSTRAINT [DF_ContentSpace_ArticleCount]  DEFAULT (0) FOR [ArticleCount]
GO
/****** Object:  Default [DF__ContentSp__Paren__24E777C3]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[ContentSpace] ADD  DEFAULT ('/') FOR [ParentPath]
GO
/****** Object:  Default [DF_ContentSpaceArticle_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[ContentSpaceArticle] ADD  CONSTRAINT [DF_ContentSpaceArticle_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_ContentSpaceArticle_CreateUserLoginName]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[ContentSpaceArticle] ADD  CONSTRAINT [DF_ContentSpaceArticle_CreateUserLoginName]  DEFAULT ('') FOR [CreateUserLoginName]
GO
/****** Object:  Default [DF_ContentSpaceArticle_ContentSpaceId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[ContentSpaceArticle] ADD  CONSTRAINT [DF_ContentSpaceArticle_ContentSpaceId]  DEFAULT (0) FOR [ContentSpaceId]
GO
/****** Object:  Default [DF_ContentSpaceArticle_OwnerType]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[ContentSpaceArticle] ADD  CONSTRAINT [DF_ContentSpaceArticle_OwnerType]  DEFAULT (0) FOR [OwnerType]
GO
/****** Object:  Default [DF_ContentSpaceArticle_OwnerId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[ContentSpaceArticle] ADD  CONSTRAINT [DF_ContentSpaceArticle_OwnerId]  DEFAULT (0) FOR [OwnerId]
GO
/****** Object:  Default [DF_ContentSpaceArticle_ViewCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[ContentSpaceArticle] ADD  CONSTRAINT [DF_ContentSpaceArticle_ViewCount]  DEFAULT (0) FOR [ViewCount]
GO
/****** Object:  Default [DF_EvaluationCourse_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[EvaluationContent] ADD  CONSTRAINT [DF_EvaluationCourse_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_Evaluation_Enabled]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[EvaluationPlan] ADD  CONSTRAINT [DF_Evaluation_Enabled]  DEFAULT (1) FOR [Enabled]
GO
/****** Object:  Default [DF_EvaluationPlan_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[EvaluationPlan] ADD  CONSTRAINT [DF_EvaluationPlan_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_EvaluationTemplate_Enabled]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[EvaluationTemplate] ADD  CONSTRAINT [DF_EvaluationTemplate_Enabled]  DEFAULT (1) FOR [Enabled]
GO
/****** Object:  Default [DF_G_ChatMessage_TalkContent]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_ChatMessage] ADD  CONSTRAINT [DF_G_ChatMessage_TalkContent]  DEFAULT ('') FOR [TalkContent]
GO
/****** Object:  Default [DF_G_ChatMessage_SendDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_ChatMessage] ADD  CONSTRAINT [DF_G_ChatMessage_SendDate]  DEFAULT (getdate()) FOR [SendDate]
GO
/****** Object:  Default [DF_G_ChatMessage_IsSendAll]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_ChatMessage] ADD  CONSTRAINT [DF_G_ChatMessage_IsSendAll]  DEFAULT (0) FOR [IsSendAll]
GO
/****** Object:  Default [DF_G_ChatMessage_IsPrivate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_ChatMessage] ADD  CONSTRAINT [DF_G_ChatMessage_IsPrivate]  DEFAULT (0) FOR [IsPrivate]
GO
/****** Object:  Default [DF_G_ChatMessage_ActText]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_ChatMessage] ADD  CONSTRAINT [DF_G_ChatMessage_ActText]  DEFAULT ('') FOR [ActText]
GO
/****** Object:  Default [DF_G_ChatRoom_GroupId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_ChatRoom] ADD  CONSTRAINT [DF_G_ChatRoom_GroupId]  DEFAULT (0) FOR [GroupId]
GO
/****** Object:  Default [DF_G_ChatRoom_PrepareCourseId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_ChatRoom] ADD  CONSTRAINT [DF_G_ChatRoom_PrepareCourseId]  DEFAULT (0) FOR [PrepareCourseId]
GO
/****** Object:  Default [DF_G_ChatRoom_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_ChatRoom] ADD  CONSTRAINT [DF_G_ChatRoom_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_G_ChatUser_JoinDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_ChatUser] ADD  CONSTRAINT [DF_G_ChatUser_JoinDate]  DEFAULT (getdate()) FOR [JoinDate]
GO
/****** Object:  Default [DF_G_ChatUser_IsSay]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_ChatUser] ADD  CONSTRAINT [DF_G_ChatUser_IsSay]  DEFAULT (1) FOR [IsSay]
GO
/****** Object:  Default [DF_G_ChatUser_FontColor]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_ChatUser] ADD  CONSTRAINT [DF_G_ChatUser_FontColor]  DEFAULT ('#000000') FOR [FontColor]
GO
/****** Object:  Default [DF_G_ChatUser_FontSize]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_ChatUser] ADD  CONSTRAINT [DF_G_ChatUser_FontSize]  DEFAULT ('12px') FOR [FontSize]
GO
/****** Object:  Default [DF_G_ChatUser_IsLeave]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_ChatUser] ADD  CONSTRAINT [DF_G_ChatUser_IsLeave]  DEFAULT (0) FOR [IsLeave]
GO
/****** Object:  Default [DF_G_ChatUser_Actived]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_ChatUser] ADD  CONSTRAINT [DF_G_ChatUser_Actived]  DEFAULT (1) FOR [IsActived]
GO
/****** Object:  Default [DF_G_Group_GroupGuid]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF_G_Group_GroupGuid]  DEFAULT (newid()) FOR [GroupGuid]
GO
/****** Object:  Default [DF_G_Group_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF_G_Group_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_G_Group_IsBestGroup]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF_G_Group_IsBestGroup]  DEFAULT (0) FOR [IsBestGroup]
GO
/****** Object:  Default [DF_G_Group_IsRecommend]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF_G_Group_IsRecommend]  DEFAULT (0) FOR [IsRecommend]
GO
/****** Object:  Default [DF_G_Group_GroupState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF_G_Group_GroupState]  DEFAULT (0) FOR [GroupState]
GO
/****** Object:  Default [DF_G_Group_UserCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF_G_Group_UserCount]  DEFAULT (0) FOR [UserCount]
GO
/****** Object:  Default [DF_G_Group_ArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF_G_Group_ArticleCount]  DEFAULT (0) FOR [ArticleCount]
GO
/****** Object:  Default [DF_G_Group_TopicCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF_G_Group_TopicCount]  DEFAULT (0) FOR [TopicCount]
GO
/****** Object:  Default [DF_G_Group_DiscussCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF_G_Group_DiscussCount]  DEFAULT (0) FOR [DiscussCount]
GO
/****** Object:  Default [DF_G_Group_ActionCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF_G_Group_ActionCount]  DEFAULT (0) FOR [ActionCount]
GO
/****** Object:  Default [DF_G_Group_ResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF_G_Group_ResourceCount]  DEFAULT (0) FOR [ResourceCount]
GO
/****** Object:  Default [DF_G_Group_CourseCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF_G_Group_CourseCount]  DEFAULT (0) FOR [CourseCount]
GO
/****** Object:  Default [DF_G_Group_VisitCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF_G_Group_VisitCount]  DEFAULT (0) FOR [VisitCount]
GO
/****** Object:  Default [DF_G_Group_JoinLimit]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF_G_Group_JoinLimit]  DEFAULT (0) FOR [JoinLimit]
GO
/****** Object:  Default [DF_G_Group_JoinScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF_G_Group_JoinScore]  DEFAULT (0) FOR [JoinScore]
GO
/****** Object:  Default [DF_G_Group_GradeId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF_G_Group_GradeId]  DEFAULT (1) FOR [GradeId]
GO
/****** Object:  Default [DF__G_Group__bestArt__6A11960F]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF__G_Group__bestArt__6A11960F]  DEFAULT (0) FOR [bestArticleCount]
GO
/****** Object:  Default [DF__G_Group__bestRes__6B05BA48]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF__G_Group__bestRes__6B05BA48]  DEFAULT (0) FOR [bestResourceCount]
GO
/****** Object:  Default [DF__G_Group__PushSta__09A5DC83]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF__G_Group__PushSta__09A5DC83]  DEFAULT (0) FOR [PushState]
GO
/****** Object:  Default [DF_G_Group_ParentId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF_G_Group_ParentId]  DEFAULT (0) FOR [ParentId]
GO
/****** Object:  Default [DF_G_Group_PhotoCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF_G_Group_PhotoCount]  DEFAULT (0) FOR [PhotoCount]
GO
/****** Object:  Default [DF_G_Group_VideoCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Group] ADD  CONSTRAINT [DF_G_Group_VideoCount]  DEFAULT (0) FOR [VideoCount]
GO
/****** Object:  Default [DF_G_GroupArticle_PubDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupArticle] ADD  CONSTRAINT [DF_G_GroupArticle_PubDate]  DEFAULT (getdate()) FOR [PubDate]
GO
/****** Object:  Default [DF_G_GroupArticle_IsGroupBest]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupArticle] ADD  CONSTRAINT [DF_G_GroupArticle_IsGroupBest]  DEFAULT (0) FOR [IsGroupBest]
GO
/****** Object:  Default [DF__G_GroupAr__Artic__53A266AC]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupArticle] ADD  DEFAULT (1) FOR [ArticleState]
GO
/****** Object:  Default [DF__G_GroupAr__TypeS__54968AE5]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupArticle] ADD  DEFAULT (1) FOR [TypeState]
GO
/****** Object:  Default [DF_G_GroupUser_Status]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupMember] ADD  CONSTRAINT [DF_G_GroupUser_Status]  DEFAULT (0) FOR [Status]
GO
/****** Object:  Default [DF_G_GroupUser_GroupRole]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupMember] ADD  CONSTRAINT [DF_G_GroupUser_GroupRole]  DEFAULT (0) FOR [GroupRole]
GO
/****** Object:  Default [DF_G_GroupUser_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupMember] ADD  CONSTRAINT [DF_G_GroupUser_CreateDate]  DEFAULT (getdate()) FOR [JoinDate]
GO
/****** Object:  Default [DF_G_GroupUser_ArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupMember] ADD  CONSTRAINT [DF_G_GroupUser_ArticleCount]  DEFAULT (0) FOR [ArticleCount]
GO
/****** Object:  Default [DF_G_GroupMember_ResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupMember] ADD  CONSTRAINT [DF_G_GroupMember_ResourceCount]  DEFAULT (0) FOR [ResourceCount]
GO
/****** Object:  Default [DF_G_GroupMember_CourseCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupMember] ADD  CONSTRAINT [DF_G_GroupMember_CourseCount]  DEFAULT (0) FOR [CourseCount]
GO
/****** Object:  Default [DF_G_GroupUser_TopicCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupMember] ADD  CONSTRAINT [DF_G_GroupUser_TopicCount]  DEFAULT (0) FOR [TopicCount]
GO
/****** Object:  Default [DF_G_GroupUser_ReplyCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupMember] ADD  CONSTRAINT [DF_G_GroupUser_ReplyCount]  DEFAULT (0) FOR [ReplyCount]
GO
/****** Object:  Default [DF_G_GroupUser_ActionCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupMember] ADD  CONSTRAINT [DF_G_GroupUser_ActionCount]  DEFAULT (0) FOR [ActionCount]
GO
/****** Object:  Default [DF_G_GroupNews_Title]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupNews] ADD  CONSTRAINT [DF_G_GroupNews_Title]  DEFAULT ('') FOR [Title]
GO
/****** Object:  Default [DF_G_GroupNews_Content]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupNews] ADD  CONSTRAINT [DF_G_GroupNews_Content]  DEFAULT ('') FOR [Content]
GO
/****** Object:  Default [DF_G_GroupNews_Status]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupNews] ADD  CONSTRAINT [DF_G_GroupNews_Status]  DEFAULT (0) FOR [Status]
GO
/****** Object:  Default [DF_G_GroupNews_NewsType]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupNews] ADD  CONSTRAINT [DF_G_GroupNews_NewsType]  DEFAULT (0) FOR [NewsType]
GO
/****** Object:  Default [DF_G_GroupNews_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupNews] ADD  CONSTRAINT [DF_G_GroupNews_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_G_GroupNews_ViewCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupNews] ADD  CONSTRAINT [DF_G_GroupNews_ViewCount]  DEFAULT (0) FOR [ViewCount]
GO
/****** Object:  Default [DF_G_GroupPhoto_PubDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupPhoto] ADD  CONSTRAINT [DF_G_GroupPhoto_PubDate]  DEFAULT (getdate()) FOR [PubDate]
GO
/****** Object:  Default [DF_G_GroupPhoto_isGroupBest]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupPhoto] ADD  CONSTRAINT [DF_G_GroupPhoto_isGroupBest]  DEFAULT (0) FOR [isGroupBest]
GO
/****** Object:  Default [DF_G_GroupRelation_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupRelation] ADD  CONSTRAINT [DF_G_GroupRelation_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_G_GroupRelation_SrcAudit]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupRelation] ADD  CONSTRAINT [DF_G_GroupRelation_SrcAudit]  DEFAULT (0) FOR [SrcAudit]
GO
/****** Object:  Default [DF_G_GroupRelation_DstAudit]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupRelation] ADD  CONSTRAINT [DF_G_GroupRelation_DstAudit]  DEFAULT (0) FOR [DstAudit]
GO
/****** Object:  Default [DF_G_GroupRelation_SrcDelete]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupRelation] ADD  CONSTRAINT [DF_G_GroupRelation_SrcDelete]  DEFAULT (0) FOR [SrcDelete]
GO
/****** Object:  Default [DF_G_GroupRelation_DstDelete]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupRelation] ADD  CONSTRAINT [DF_G_GroupRelation_DstDelete]  DEFAULT (0) FOR [DstDelete]
GO
/****** Object:  Default [DF_G_GroupVideo_PubDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupVideo] ADD  CONSTRAINT [DF_G_GroupVideo_PubDate]  DEFAULT (getdate()) FOR [PubDate]
GO
/****** Object:  Default [DF_G_GroupVideo_isGroupBest]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_GroupVideo] ADD  CONSTRAINT [DF_G_GroupVideo_isGroupBest]  DEFAULT (0) FOR [isGroupBest]
GO
/****** Object:  Default [DF_G_Link_ObjectType]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Link] ADD  CONSTRAINT [DF_G_Link_ObjectType]  DEFAULT (0) FOR [ObjectType]
GO
/****** Object:  Default [DF_G_Link_createDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Link] ADD  CONSTRAINT [DF_G_Link_createDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_G_Link_LinkIcon]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[G_Link] ADD  CONSTRAINT [DF_G_Link_LinkIcon]  DEFAULT ('') FOR [LinkIcon]
GO
/****** Object:  Default [DF_Jitar_AccessControl_ObjectType]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_AccessControl] ADD  CONSTRAINT [DF_Jitar_AccessControl_ObjectType]  DEFAULT (0) FOR [ObjectType]
GO
/****** Object:  Default [DF_Jitar_AccessControl_ObjectTitle]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_AccessControl] ADD  CONSTRAINT [DF_Jitar_AccessControl_ObjectTitle]  DEFAULT ('') FOR [ObjectTitle]
GO
/****** Object:  Default [DF_B_Article_ObjectUuid]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  CONSTRAINT [DF_B_Article_ObjectUuid]  DEFAULT (newid()) FOR [ObjectUuid]
GO
/****** Object:  Default [DF_B_Article_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  CONSTRAINT [DF_B_Article_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_B_Article_LastModified]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  CONSTRAINT [DF_B_Article_LastModified]  DEFAULT (getdate()) FOR [LastModified]
GO
/****** Object:  Default [DF_B_Article_ArticleTags]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  CONSTRAINT [DF_B_Article_ArticleTags]  DEFAULT ('') FOR [ArticleTags]
GO
/****** Object:  Default [DF_B_Article_ViewCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  CONSTRAINT [DF_B_Article_ViewCount]  DEFAULT (0) FOR [ViewCount]
GO
/****** Object:  Default [DF_B_Article_CommentCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  CONSTRAINT [DF_B_Article_CommentCount]  DEFAULT (0) FOR [CommentCount]
GO
/****** Object:  Default [DF_B_Article_HideState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  CONSTRAINT [DF_B_Article_HideState]  DEFAULT (0) FOR [HideState]
GO
/****** Object:  Default [DF_B_Article_ApproveState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  CONSTRAINT [DF_B_Article_ApproveState]  DEFAULT (0) FOR [AuditState]
GO
/****** Object:  Default [DF_B_Article_TopState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  CONSTRAINT [DF_B_Article_TopState]  DEFAULT (0) FOR [TopState]
GO
/****** Object:  Default [DF_B_Article_BestState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  CONSTRAINT [DF_B_Article_BestState]  DEFAULT (0) FOR [BestState]
GO
/****** Object:  Default [DF_B_Article_DraftState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  CONSTRAINT [DF_B_Article_DraftState]  DEFAULT (0) FOR [DraftState]
GO
/****** Object:  Default [DF_B_Article_DelState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  CONSTRAINT [DF_B_Article_DelState]  DEFAULT (0) FOR [DelState]
GO
/****** Object:  Default [DF_B_Article_RecommendState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  CONSTRAINT [DF_B_Article_RecommendState]  DEFAULT (0) FOR [RecommendState]
GO
/****** Object:  Default [DF_B_Article_CommentState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  CONSTRAINT [DF_B_Article_CommentState]  DEFAULT (0) FOR [CommentState]
GO
/****** Object:  Default [DF_B_Article_Trample]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  CONSTRAINT [DF_B_Article_Trample]  DEFAULT (0) FOR [Trample]
GO
/****** Object:  Default [DF_B_Article_Digg]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  CONSTRAINT [DF_B_Article_Digg]  DEFAULT (0) FOR [Digg]
GO
/****** Object:  Default [DF_B_Article_StarCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  CONSTRAINT [DF_B_Article_StarCount]  DEFAULT (0) FOR [StarCount]
GO
/****** Object:  Default [DF_B_Article_TypeState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  CONSTRAINT [DF_B_Article_TypeState]  DEFAULT (0) FOR [TypeState]
GO
/****** Object:  Default [DF__Jitar_Art__PushS__025D5595]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  DEFAULT (0) FOR [PushState]
GO
/****** Object:  Default [DF__Jitar_Art__Orgin__035179CE]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  DEFAULT ('') FOR [OrginPath]
GO
/****** Object:  Default [DF__Jitar_Art__Login__04459E07]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  DEFAULT ('') FOR [LoginName]
GO
/****** Object:  Default [DF__Jitar_Art__Artic__0539C240]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  DEFAULT (0) FOR [ArticleId]
GO
/****** Object:  Default [DF_Jitar_Article_ArticleFormat]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  CONSTRAINT [DF_Jitar_Article_ArticleFormat]  DEFAULT (0) FOR [ArticleFormat]
GO
/****** Object:  Default [DF_Jitar_Article_WordDownload]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Article] ADD  CONSTRAINT [DF_Jitar_Article_WordDownload]  DEFAULT (0) FOR [WordDownload]
GO
/****** Object:  Default [DF__Jitar_Bac__BackY__08162EEB]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_BackYear] ADD  DEFAULT (0) FOR [BackYearCount]
GO
/****** Object:  Default [DF_FamousChannel_ChannelGuid]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Channel] ADD  CONSTRAINT [DF_FamousChannel_ChannelGuid]  DEFAULT (newid()) FOR [ChannelGuid]
GO
/****** Object:  Default [DF_Jitar_ChannelArticle_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ChannelArticle] ADD  CONSTRAINT [DF_Jitar_ChannelArticle_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_Jitar_ChannelArticle_ArticleState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ChannelArticle] ADD  CONSTRAINT [DF_Jitar_ChannelArticle_ArticleState]  DEFAULT (1) FOR [ArticleState]
GO
/****** Object:  Default [DF__Jitar_Cha__TypeS__0BE6BFCF]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ChannelArticle] ADD  DEFAULT (1) FOR [TypeState]
GO
/****** Object:  Default [DF_FamousChannelModule_PageType]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ChannelModule] ADD  CONSTRAINT [DF_FamousChannelModule_PageType]  DEFAULT ('index') FOR [PageType]
GO
/****** Object:  Default [DF_Jitar_ChannelPhoto_ViewCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ChannelPhoto] ADD  CONSTRAINT [DF_Jitar_ChannelPhoto_ViewCount]  DEFAULT (0) FOR [ViewCount]
GO
/****** Object:  Default [DF__Jitar_Cha__ViewC__7B2D9FFA]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ChannelResource] ADD  CONSTRAINT [DF__Jitar_Cha__ViewC__7B2D9FFA]  DEFAULT (0) FOR [ViewCount]
GO
/****** Object:  Default [DF_Jitar_ChannelUnitStat_UserCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ChannelUnitStat] ADD  CONSTRAINT [DF_Jitar_ChannelUnitStat_UserCount]  DEFAULT (0) FOR [UserCount]
GO
/****** Object:  Default [DF_Jitar_ChannelUnitStat_ArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ChannelUnitStat] ADD  CONSTRAINT [DF_Jitar_ChannelUnitStat_ArticleCount]  DEFAULT (0) FOR [ArticleCount]
GO
/****** Object:  Default [DF_Jitar_ChannelUnitStat_ResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ChannelUnitStat] ADD  CONSTRAINT [DF_Jitar_ChannelUnitStat_ResourceCount]  DEFAULT (0) FOR [ResourceCount]
GO
/****** Object:  Default [DF_Jitar_ChannelUnitStat_PhotoCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ChannelUnitStat] ADD  CONSTRAINT [DF_Jitar_ChannelUnitStat_PhotoCount]  DEFAULT (0) FOR [PhotoCount]
GO
/****** Object:  Default [DF_Jitar_ChannelUnitStat_VideoCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ChannelUnitStat] ADD  CONSTRAINT [DF_Jitar_ChannelUnitStat_VideoCount]  DEFAULT (0) FOR [VideoCount]
GO
/****** Object:  Default [DF_Jitar_ChannelUnitStat_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ChannelUnitStat] ADD  CONSTRAINT [DF_Jitar_ChannelUnitStat_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_Jitar_ChannelUser_UnitId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ChannelUser] ADD  CONSTRAINT [DF_Jitar_ChannelUser_UnitId]  DEFAULT (1) FOR [UnitId]
GO
/****** Object:  Default [DF_Jitar_ChannelStat_ArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ChannelUserStat] ADD  CONSTRAINT [DF_Jitar_ChannelStat_ArticleCount]  DEFAULT (0) FOR [ArticleCount]
GO
/****** Object:  Default [DF_Jitar_ChannelStat_ResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ChannelUserStat] ADD  CONSTRAINT [DF_Jitar_ChannelStat_ResourceCount]  DEFAULT (0) FOR [ResourceCount]
GO
/****** Object:  Default [DF_Jitar_ChannelStat_PhotoCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ChannelUserStat] ADD  CONSTRAINT [DF_Jitar_ChannelStat_PhotoCount]  DEFAULT (0) FOR [PhotoCount]
GO
/****** Object:  Default [DF_Jitar_ChannelStat_VideoCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ChannelUserStat] ADD  CONSTRAINT [DF_Jitar_ChannelStat_VideoCount]  DEFAULT (0) FOR [VideoCount]
GO
/****** Object:  Default [DF_Jitar_ChannelUserStat_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ChannelUserStat] ADD  CONSTRAINT [DF_Jitar_ChannelUserStat_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF__Jitar_Cha__ViewC__7D15E86C]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ChannelVideo] ADD  CONSTRAINT [DF__Jitar_Cha__ViewC__7D15E86C]  DEFAULT (0) FOR [ViewCount]
GO
/****** Object:  Default [DF_Jitar_Column_DisplayName]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Column] ADD  CONSTRAINT [DF_Jitar_Column_DisplayName]  DEFAULT ('') FOR [DisplayName]
GO
/****** Object:  Default [DF_Jitar_Column_HasPictrue]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Column] ADD  CONSTRAINT [DF_Jitar_Column_HasPictrue]  DEFAULT (0) FOR [HasPicture]
GO
/****** Object:  Default [DF_Jitar_Column_AnonymousShowContent]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Column] ADD  CONSTRAINT [DF_Jitar_Column_AnonymousShowContent]  DEFAULT (1) FOR [AnonymousShowContent]
GO
/****** Object:  Default [DF_Jitar_ColumnNews_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ColumnNews] ADD  CONSTRAINT [DF_Jitar_ColumnNews_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_Jitar_ColumnNews_ViewCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ColumnNews] ADD  CONSTRAINT [DF_Jitar_ColumnNews_ViewCount]  DEFAULT (0) FOR [ViewCount]
GO
/****** Object:  Default [DF_Jitar_ColumnNews_Published]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_ColumnNews] ADD  CONSTRAINT [DF_Jitar_ColumnNews_Published]  DEFAULT (0) FOR [Published]
GO
/****** Object:  Default [DF_Jitar_GroupQuery_ArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_GroupQuery] ADD  CONSTRAINT [DF_Jitar_GroupQuery_ArticleCount]  DEFAULT (0) FOR [ArticleCount]
GO
/****** Object:  Default [DF_Jitar_GroupQuery_BestArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_GroupQuery] ADD  CONSTRAINT [DF_Jitar_GroupQuery_BestArticleCount]  DEFAULT (0) FOR [BestArticleCount]
GO
/****** Object:  Default [DF_Jitar_GroupQuery_ResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_GroupQuery] ADD  CONSTRAINT [DF_Jitar_GroupQuery_ResourceCount]  DEFAULT (0) FOR [ResourceCount]
GO
/****** Object:  Default [DF_Jitar_GroupQuery_BestResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_GroupQuery] ADD  CONSTRAINT [DF_Jitar_GroupQuery_BestResourceCount]  DEFAULT (0) FOR [BestResourceCount]
GO
/****** Object:  Default [DF_Jitar_GroupQuery_TopicCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_GroupQuery] ADD  CONSTRAINT [DF_Jitar_GroupQuery_TopicCount]  DEFAULT (0) FOR [TopicCount]
GO
/****** Object:  Default [DF_Jitar_GroupQuery_ReplyCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_GroupQuery] ADD  CONSTRAINT [DF_Jitar_GroupQuery_ReplyCount]  DEFAULT (0) FOR [ReplyCount]
GO
/****** Object:  Default [DF_Jitar_Report_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Report] ADD  CONSTRAINT [DF_Jitar_Report_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_Jitar_Report_Status]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Report] ADD  CONSTRAINT [DF_Jitar_Report_Status]  DEFAULT (0) FOR [Status]
GO
/****** Object:  Default [DF_S_SpecialSubjectArticle_ArticleGuid]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_SpecialSubjectArticle] ADD  CONSTRAINT [DF_S_SpecialSubjectArticle_ArticleGuid]  DEFAULT (newid()) FOR [ArticleGuid]
GO
/****** Object:  Default [DF_S_SpecialSubjectArticle_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_SpecialSubjectArticle] ADD  CONSTRAINT [DF_S_SpecialSubjectArticle_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF__Jitar_Spe__Artic__7A4E8A5A]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_SpecialSubjectArticle] ADD  CONSTRAINT [DF__Jitar_Spe__Artic__7A4E8A5A]  DEFAULT (1) FOR [ArticleState]
GO
/****** Object:  Default [DF__Jitar_Spe__TypeS__2C538F61]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_SpecialSubjectArticle] ADD  DEFAULT (1) FOR [TypeState]
GO
/****** Object:  Default [DF_Jitar_Count_UserCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_TimerCount] ADD  CONSTRAINT [DF_Jitar_Count_UserCount]  DEFAULT (0) FOR [UserCount]
GO
/****** Object:  Default [DF_Jitar_Count_GroupCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_TimerCount] ADD  CONSTRAINT [DF_Jitar_Count_GroupCount]  DEFAULT (0) FOR [GroupCount]
GO
/****** Object:  Default [DF_Jitar_Count_TotalArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_TimerCount] ADD  CONSTRAINT [DF_Jitar_Count_TotalArticleCount]  DEFAULT (0) FOR [TotalArticleCount]
GO
/****** Object:  Default [DF_Jitar_Count_TotalResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_TimerCount] ADD  CONSTRAINT [DF_Jitar_Count_TotalResourceCount]  DEFAULT (0) FOR [TotalResourceCount]
GO
/****** Object:  Default [DF_Jitar_Count_CommentCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_TimerCount] ADD  CONSTRAINT [DF_Jitar_Count_CommentCount]  DEFAULT (0) FOR [CommentCount]
GO
/****** Object:  Default [DF_Jitar_Count_PhotoCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_TimerCount] ADD  CONSTRAINT [DF_Jitar_Count_PhotoCount]  DEFAULT (0) FOR [PhotoCount]
GO
/****** Object:  Default [DF_Jitar_Count_VideoCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_TimerCount] ADD  CONSTRAINT [DF_Jitar_Count_VideoCount]  DEFAULT (0) FOR [VideoCount]
GO
/****** Object:  Default [DF_Jitar_TimerCount_PrepareCourseCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_TimerCount] ADD  CONSTRAINT [DF_Jitar_TimerCount_PrepareCourseCount]  DEFAULT (0) FOR [PrepareCourseCount]
GO
/****** Object:  Default [DF_Jitar_TimerCount_TodayArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_TimerCount] ADD  CONSTRAINT [DF_Jitar_TimerCount_TodayArticleCount]  DEFAULT (0) FOR [TodayArticleCount]
GO
/****** Object:  Default [DF_Jitar_TimerCount_HistoryArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_TimerCount] ADD  CONSTRAINT [DF_Jitar_TimerCount_HistoryArticleCount]  DEFAULT (0) FOR [HistoryArticleCount]
GO
/****** Object:  Default [DF_Jitar_TimerCount_YesterdayResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_TimerCount] ADD  CONSTRAINT [DF_Jitar_TimerCount_YesterdayResourceCount]  DEFAULT (0) FOR [YesterdayResourceCount]
GO
/****** Object:  Default [DF_Jitar_TimerCount_CountDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_TimerCount] ADD  CONSTRAINT [DF_Jitar_TimerCount_CountDate]  DEFAULT (getdate()) FOR [CountDate]
GO
/****** Object:  Default [DF__Jitar_Uni__UnitG__019AA667]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Unit] ADD  CONSTRAINT [DF__Jitar_Uni__UnitG__019AA667]  DEFAULT (newid()) FOR [UnitGuid]
GO
/****** Object:  Default [DF__Jitar_Uni__HasCh__028ECAA0]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Unit] ADD  CONSTRAINT [DF__Jitar_Uni__HasCh__028ECAA0]  DEFAULT (0) FOR [HasChild]
GO
/****** Object:  Default [DF__Jitar_Uni__UserC__0382EED9]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Unit] ADD  CONSTRAINT [DF__Jitar_Uni__UserC__0382EED9]  DEFAULT (0) FOR [UserCount]
GO
/****** Object:  Default [DF__Jitar_Uni__Artic__04771312]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Unit] ADD  CONSTRAINT [DF__Jitar_Uni__Artic__04771312]  DEFAULT (0) FOR [ArticleCount]
GO
/****** Object:  Default [DF_Jitar_Unit_HistoryArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Unit] ADD  CONSTRAINT [DF_Jitar_Unit_HistoryArticleCount]  DEFAULT (0) FOR [HistoryArticleCount]
GO
/****** Object:  Default [DF__Jitar_Uni__Recom__056B374B]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Unit] ADD  CONSTRAINT [DF__Jitar_Uni__Recom__056B374B]  DEFAULT (0) FOR [RecommendArticleCount]
GO
/****** Object:  Default [DF__Jitar_Uni__Resou__065F5B84]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Unit] ADD  CONSTRAINT [DF__Jitar_Uni__Resou__065F5B84]  DEFAULT (0) FOR [ResourceCount]
GO
/****** Object:  Default [DF__Jitar_Uni__Recom__07537FBD]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Unit] ADD  CONSTRAINT [DF__Jitar_Uni__Recom__07537FBD]  DEFAULT (0) FOR [RecommendResourceCount]
GO
/****** Object:  Default [DF__Jitar_Uni__Photo__0847A3F6]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Unit] ADD  CONSTRAINT [DF__Jitar_Uni__Photo__0847A3F6]  DEFAULT (0) FOR [PhotoCount]
GO
/****** Object:  Default [DF__Jitar_Uni__Video__093BC82F]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Unit] ADD  CONSTRAINT [DF__Jitar_Uni__Video__093BC82F]  DEFAULT (0) FOR [VideoCount]
GO
/****** Object:  Default [DF__Jitar_Uni__Total__0A2FEC68]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Unit] ADD  CONSTRAINT [DF__Jitar_Uni__Total__0A2FEC68]  DEFAULT (0) FOR [TotalScore]
GO
/****** Object:  Default [DF__Jitar_Uni__ItemI__0B2410A1]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Unit] ADD  CONSTRAINT [DF__Jitar_Uni__ItemI__0B2410A1]  DEFAULT (0) FOR [ItemIndex]
GO
/****** Object:  Default [DF__Jitar_Unit__Rank__0C1834DA]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Unit] ADD  CONSTRAINT [DF__Jitar_Unit__Rank__0C1834DA]  DEFAULT (0) FOR [Rank]
GO
/****** Object:  Default [DF__Jitar_Uni__AveSc__0D0C5913]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Unit] ADD  CONSTRAINT [DF__Jitar_Uni__AveSc__0D0C5913]  DEFAULT (0) FOR [AveScore]
GO
/****** Object:  Default [DF_Jitar_Unit_DelState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_Unit] ADD  CONSTRAINT [DF_Jitar_Unit_DelState]  DEFAULT (0) FOR [DelState]
GO
/****** Object:  Default [DF_Jitar_UnitCount_UnitId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_UnitCount] ADD  CONSTRAINT [DF_Jitar_UnitCount_UnitId]  DEFAULT (0) FOR [UnitId]
GO
/****** Object:  Default [DF__Jitar_Uni__SelfH__39C9F0F1]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_UnitCount] ADD  CONSTRAINT [DF__Jitar_Uni__SelfH__39C9F0F1]  DEFAULT (0) FOR [SelfHistoryArticleCount]
GO
/****** Object:  Default [DF__Jitar_Uni__Child__3ABE152A]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_UnitCount] ADD  CONSTRAINT [DF__Jitar_Uni__Child__3ABE152A]  DEFAULT (0) FOR [ChildHistoryArticleCount]
GO
/****** Object:  Default [DF_Jitar_UnitCount_SelfArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_UnitCount] ADD  CONSTRAINT [DF_Jitar_UnitCount_SelfArticleCount]  DEFAULT (0) FOR [SelfArticleCount]
GO
/****** Object:  Default [DF_Jitar_UnitCount_ChildArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_UnitCount] ADD  CONSTRAINT [DF_Jitar_UnitCount_ChildArticleCount]  DEFAULT (0) FOR [ChildArticleCount]
GO
/****** Object:  Default [DF__Jitar_Uni__SelfR__3BB23963]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_UnitCount] ADD  CONSTRAINT [DF__Jitar_Uni__SelfR__3BB23963]  DEFAULT (0) FOR [SelfResourceCount]
GO
/****** Object:  Default [DF__Jitar_Uni__SelfP__3CA65D9C]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_UnitCount] ADD  CONSTRAINT [DF__Jitar_Uni__SelfP__3CA65D9C]  DEFAULT (0) FOR [SelfPhotoCount]
GO
/****** Object:  Default [DF__Jitar_Uni__SelfV__3D9A81D5]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_UnitCount] ADD  CONSTRAINT [DF__Jitar_Uni__SelfV__3D9A81D5]  DEFAULT (0) FOR [SelfVideoCount]
GO
/****** Object:  Default [DF__Jitar_Uni__SelfU__3E8EA60E]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_UnitCount] ADD  CONSTRAINT [DF__Jitar_Uni__SelfU__3E8EA60E]  DEFAULT (0) FOR [SelfUserCount]
GO
/****** Object:  Default [DF__Jitar_Uni__Child__3F82CA47]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_UnitCount] ADD  CONSTRAINT [DF__Jitar_Uni__Child__3F82CA47]  DEFAULT (0) FOR [ChildResourceCount]
GO
/****** Object:  Default [DF__Jitar_Uni__Child__4076EE80]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_UnitCount] ADD  CONSTRAINT [DF__Jitar_Uni__Child__4076EE80]  DEFAULT (0) FOR [ChildPhotoCount]
GO
/****** Object:  Default [DF__Jitar_Uni__Child__416B12B9]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_UnitCount] ADD  CONSTRAINT [DF__Jitar_Uni__Child__416B12B9]  DEFAULT (0) FOR [ChildVideoCount]
GO
/****** Object:  Default [DF__Jitar_Uni__Child__425F36F2]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_UnitCount] ADD  CONSTRAINT [DF__Jitar_Uni__Child__425F36F2]  DEFAULT (0) FOR [ChildUserCount]
GO
/****** Object:  Default [DF_Jitar_UnitSubject_DisplayName]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_UnitSubject] ADD  CONSTRAINT [DF_Jitar_UnitSubject_DisplayName]  DEFAULT ('') FOR [DisplayName]
GO
/****** Object:  Default [DF_Jitar_UnitType_UnitTypeGuid]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_UnitType] ADD  CONSTRAINT [DF_Jitar_UnitType_UnitTypeGuid]  DEFAULT (newid()) FOR [UnitTypeGuid]
GO
/****** Object:  Default [DF_Jitar_User_UserGuid]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_Jitar_User_UserGuid]  DEFAULT (newid()) FOR [UserGuid]
GO
/****** Object:  Default [DF_U_User_LoginName]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_LoginName]  DEFAULT ('') FOR [LoginName]
GO
/****** Object:  Default [DF_U_User_TrueName]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_TrueName]  DEFAULT ('') FOR [TrueName]
GO
/****** Object:  Default [DF_U_User_NickName]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_NickName]  DEFAULT ('') FOR [NickName]
GO
/****** Object:  Default [DF_U_User_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_Jitar_User_VirtualDirectory]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_Jitar_User_VirtualDirectory]  DEFAULT ('u') FOR [VirtualDirectory]
GO
/****** Object:  Default [DF_U_User_Gender]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_Gender]  DEFAULT (0) FOR [Gender]
GO
/****** Object:  Default [DF_U_User_UnitId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_UnitId]  DEFAULT (0) FOR [UnitId]
GO
/****** Object:  Default [DF_U_User_BlogName]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_BlogName]  DEFAULT (' 的工作室') FOR [BlogName]
GO
/****** Object:  Default [DF_U_User_UserStatus]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_UserStatus]  DEFAULT (0) FOR [UserStatus]
GO
/****** Object:  Default [DF_U_User_UserGroupID]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_UserGroupID]  DEFAULT (0) FOR [UserGroupID]
GO
/****** Object:  Default [DF_U_User_VisitCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_VisitCount]  DEFAULT (0) FOR [VisitCount]
GO
/****** Object:  Default [DF_U_User_VisitArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_VisitArticleCount]  DEFAULT (0) FOR [VisitArticleCount]
GO
/****** Object:  Default [DF_U_User_VisitResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_VisitResourceCount]  DEFAULT (0) FOR [VisitResourceCount]
GO
/****** Object:  Default [DF_U_User_ArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_ArticleCount]  DEFAULT (0) FOR [ArticleCount]
GO
/****** Object:  Default [DF_U_User_RecommendArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_RecommendArticleCount]  DEFAULT (0) FOR [RecommendArticleCount]
GO
/****** Object:  Default [DF_U_User_ArticleCommentCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_ArticleCommentCount]  DEFAULT (0) FOR [ArticleCommentCount]
GO
/****** Object:  Default [DF_U_User_ArticleCommentCount1]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_ArticleCommentCount1]  DEFAULT (0) FOR [ArticleICommentCount]
GO
/****** Object:  Default [DF_U_User_ResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_ResourceCount]  DEFAULT (0) FOR [ResourceCount]
GO
/****** Object:  Default [DF_U_User_RecommendResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_RecommendResourceCount]  DEFAULT (0) FOR [RecommendResourceCount]
GO
/****** Object:  Default [DF_U_User_ResourceCommentCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_ResourceCommentCount]  DEFAULT (0) FOR [ResourceCommentCount]
GO
/****** Object:  Default [DF_U_User_ResourceCommentCount1]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_ResourceCommentCount1]  DEFAULT (0) FOR [ResourceICommentCount]
GO
/****** Object:  Default [DF_U_User_ResourceDownloadCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_ResourceDownloadCount]  DEFAULT (0) FOR [ResourceDownloadCount]
GO
/****** Object:  Default [DF_U_User_CreateGroupCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_CreateGroupCount]  DEFAULT (0) FOR [CreateGroupCount]
GO
/****** Object:  Default [DF_U_User_JionGroupCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_JionGroupCount]  DEFAULT (0) FOR [JionGroupCount]
GO
/****** Object:  Default [DF_U_User_MessageCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_MessageCount]  DEFAULT (0) FOR [PhotoCount]
GO
/****** Object:  Default [DF_U_User_UploadFileCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_UploadFileCount]  DEFAULT (0) FOR [CourseCount]
GO
/****** Object:  Default [DF_U_User_TopicCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_TopicCount]  DEFAULT (0) FOR [TopicCount]
GO
/****** Object:  Default [DF_U_User_CommentCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_CommentCount]  DEFAULT (0) FOR [CommentCount]
GO
/****** Object:  Default [DF_U_User_UsedFileSize]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_UsedFileSize]  DEFAULT (0) FOR [UsedFileSize]
GO
/****** Object:  Default [DF_U_User_UserIcon]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_UserIcon]  DEFAULT ('images/default.gif') FOR [UserIcon]
GO
/****** Object:  Default [DF_U_User_UserScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_UserScore]  DEFAULT (0) FOR [UserScore]
GO
/****** Object:  Default [DF_U_User_UserClassID]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_UserClassID]  DEFAULT (0) FOR [UserClassID]
GO
/****** Object:  Default [DF_U_User_PositionID]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_PositionID]  DEFAULT (0) FOR [PositionID]
GO
/****** Object:  Default [DF_U_User_Usn]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_Usn]  DEFAULT (1) FOR [Usn]
GO
/****** Object:  Default [DF_U_User_ArticleScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_ArticleScore]  DEFAULT (0) FOR [ArticleScore]
GO
/****** Object:  Default [DF_U_User_ResourceScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_ResourceScore]  DEFAULT (0) FOR [ResourceScore]
GO
/****** Object:  Default [DF_U_User_CommentScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_CommentScore]  DEFAULT (0) FOR [CommentScore]
GO
/****** Object:  Default [DF_U_User_MyArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_MyArticleCount]  DEFAULT (0) FOR [MyArticleCount]
GO
/****** Object:  Default [DF_U_User_OtherArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_OtherArticleCount]  DEFAULT (0) FOR [OtherArticleCount]
GO
/****** Object:  Default [DF_U_User_ArticlePunishScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_ArticlePunishScore]  DEFAULT (0) FOR [ArticlePunishScore]
GO
/****** Object:  Default [DF_U_User_ResourcePunishScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_ResourcePunishScore]  DEFAULT (0) FOR [ResourcePunishScore]
GO
/****** Object:  Default [DF_U_User_CommentPunishScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_CommentPunishScore]  DEFAULT (0) FOR [CommentPunishScore]
GO
/****** Object:  Default [DF_U_User_PhotoPunishScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_PhotoPunishScore]  DEFAULT (0) FOR [PhotoPunishScore]
GO
/****** Object:  Default [DF_U_User_videoCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_videoCount]  DEFAULT (0) FOR [videoCount]
GO
/****** Object:  Default [DF_U_User_photoScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_photoScore]  DEFAULT (0) FOR [photoScore]
GO
/****** Object:  Default [DF_U_User_videoPunishScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_videoPunishScore]  DEFAULT (0) FOR [videoPunishScore]
GO
/****** Object:  Default [DF_U_User_videoScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_U_User_videoScore]  DEFAULT (0) FOR [videoScore]
GO
/****** Object:  Default [DF__U_User__PrepareC__6ED64B2C]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF__U_User__PrepareC__6ED64B2C]  DEFAULT (0) FOR [PrepareCourseCount]
GO
/****** Object:  Default [DF__U_User__PushStat__15178F2F]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF__U_User__PushStat__15178F2F]  DEFAULT (0) FOR [PushState]
GO
/****** Object:  Default [DF__Jitar_Use__UnitP__0FE8C5BE]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF__Jitar_Use__UnitP__0FE8C5BE]  DEFAULT ('') FOR [UnitPathInfo]
GO
/****** Object:  Default [DF__Jitar_Use__Histo__05F8DC4F]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  DEFAULT (0) FOR [HistoryMyArticleCount]
GO
/****** Object:  Default [DF__Jitar_Use__Histo__06ED0088]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  DEFAULT (0) FOR [HistoryOtherArticleCount]
GO
/****** Object:  Default [DF_Jitar_User_version]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_User] ADD  CONSTRAINT [DF_Jitar_User_version]  DEFAULT (0) FOR [version]
GO
/****** Object:  Default [DF_Jitar_UserType_IsSystem]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jitar_UserType] ADD  CONSTRAINT [DF_Jitar_UserType_IsSystem]  DEFAULT (0) FOR [IsSystem]
GO
/****** Object:  Default [DF_Jtr_GroupResource_PubDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jtr_GroupResource] ADD  CONSTRAINT [DF_Jtr_GroupResource_PubDate]  DEFAULT (getdate()) FOR [PubDate]
GO
/****** Object:  Default [DF_Jtr_GroupResource_isGroupBest]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jtr_GroupResource] ADD  CONSTRAINT [DF_Jtr_GroupResource_isGroupBest]  DEFAULT (0) FOR [isGroupBest]
GO
/****** Object:  Default [DF_Jtr_Resource_ObjectUuid]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jtr_Resource] ADD  CONSTRAINT [DF_Jtr_Resource_ObjectUuid]  DEFAULT (newid()) FOR [ObjectUuid]
GO
/****** Object:  Default [DF_Jtr_Resource_Title]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jtr_Resource] ADD  CONSTRAINT [DF_Jtr_Resource_Title]  DEFAULT ('') FOR [Title]
GO
/****** Object:  Default [DF_Jtr_Resource_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jtr_Resource] ADD  CONSTRAINT [DF_Jtr_Resource_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_Jtr_Resource_LastModified]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jtr_Resource] ADD  CONSTRAINT [DF_Jtr_Resource_LastModified]  DEFAULT (getdate()) FOR [LastModified]
GO
/****** Object:  Default [DF_Jtr_Resource_ViewCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jtr_Resource] ADD  CONSTRAINT [DF_Jtr_Resource_ViewCount]  DEFAULT (0) FOR [ViewCount]
GO
/****** Object:  Default [DF_Jtr_Resource_CommentCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jtr_Resource] ADD  CONSTRAINT [DF_Jtr_Resource_CommentCount]  DEFAULT (0) FOR [CommentCount]
GO
/****** Object:  Default [DF_Jtr_Resource_AuditState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jtr_Resource] ADD  CONSTRAINT [DF_Jtr_Resource_AuditState]  DEFAULT (0) FOR [AuditState]
GO
/****** Object:  Default [DF_Jtr_Resource_DelState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jtr_Resource] ADD  CONSTRAINT [DF_Jtr_Resource_DelState]  DEFAULT (0) FOR [DelState]
GO
/****** Object:  Default [DF_Jtr_Resource_RecommendState]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jtr_Resource] ADD  CONSTRAINT [DF_Jtr_Resource_RecommendState]  DEFAULT (0) FOR [RecommendState]
GO
/****** Object:  Default [DF_Jtr_Resource_DownloadCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jtr_Resource] ADD  CONSTRAINT [DF_Jtr_Resource_DownloadCount]  DEFAULT (0) FOR [DownloadCount]
GO
/****** Object:  Default [DF_Jtr_Resource_ShareMode]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jtr_Resource] ADD  CONSTRAINT [DF_Jtr_Resource_ShareMode]  DEFAULT (1000) FOR [ShareMode]
GO
/****** Object:  Default [DF_Jtr_Resource_Href]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jtr_Resource] ADD  CONSTRAINT [DF_Jtr_Resource_Href]  DEFAULT ('') FOR [Href]
GO
/****** Object:  Default [DF_Jtr_Resource_FileSize]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jtr_Resource] ADD  CONSTRAINT [DF_Jtr_Resource_FileSize]  DEFAULT (0) FOR [FileSize]
GO
/****** Object:  Default [DF_Jtr_Resource_IsPublishToZyk]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jtr_Resource] ADD  CONSTRAINT [DF_Jtr_Resource_IsPublishToZyk]  DEFAULT (0) FOR [IsPublishToZyk]
GO
/****** Object:  Default [DF__Jtr_Resou__UnitI__190BB0C3]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jtr_Resource] ADD  DEFAULT (0) FOR [UnitId]
GO
/****** Object:  Default [DF__Jtr_Resou__PushS__19FFD4FC]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[Jtr_Resource] ADD  DEFAULT (0) FOR [PushState]
GO
/****** Object:  Default [DF_MetaSubject_MsubjName]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[MetaSubject] ADD  CONSTRAINT [DF_MetaSubject_MsubjName]  DEFAULT ('') FOR [MsubjName]
GO
/****** Object:  Default [DF_MetaSubject_MsubjCode]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[MetaSubject] ADD  CONSTRAINT [DF_MetaSubject_MsubjCode]  DEFAULT ('') FOR [MsubjCode]
GO
/****** Object:  Default [DF_MetaSubject_OrderNum]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[MetaSubject] ADD  CONSTRAINT [DF_MetaSubject_OrderNum]  DEFAULT (0) FOR [OrderNum]
GO
/****** Object:  Default [DF_P_PortalPage_ObjType]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[P_Page] ADD  CONSTRAINT [DF_P_PortalPage_ObjType]  DEFAULT (0) FOR [ObjType]
GO
/****** Object:  Default [DF_P_PortalPage_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[P_Page] ADD  CONSTRAINT [DF_P_PortalPage_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_P_PortalPage_ItemOrder]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[P_Page] ADD  CONSTRAINT [DF_P_PortalPage_ItemOrder]  DEFAULT (1) FOR [ItemOrder]
GO
/****** Object:  Default [DF_P_Page_Skin]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[P_Page] ADD  CONSTRAINT [DF_P_Page_Skin]  DEFAULT ('skin1') FOR [Skin]
GO
/****** Object:  Default [DF_P_Topic_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[P_PlugInTopic] ADD  CONSTRAINT [DF_P_Topic_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_P_TopicReply_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[P_PlugInTopicReply] ADD  CONSTRAINT [DF_P_TopicReply_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_P_Question_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[P_Question] ADD  CONSTRAINT [DF_P_Question_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_P_QuestionAnswer_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[P_QuestionAnswer] ADD  CONSTRAINT [DF_P_QuestionAnswer_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_P_Vote_ObjectGuid]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[P_Vote] ADD  CONSTRAINT [DF_P_Vote_ObjectGuid]  DEFAULT (newid()) FOR [ObjectGuid]
GO
/****** Object:  Default [DF_P_VoteResult_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[P_VoteResult] ADD  CONSTRAINT [DF_P_VoteResult_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_PortalWebpart_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[P_Widget] ADD  CONSTRAINT [DF_PortalWebpart_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_PortalWebpart_IsVisible]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[P_Widget] ADD  CONSTRAINT [DF_PortalWebpart_IsVisible]  DEFAULT (0) FOR [IsHidden]
GO
/****** Object:  Default [DF_P_PortalWebpart_ItemIndex]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[P_Widget] ADD  CONSTRAINT [DF_P_PortalWebpart_ItemIndex]  DEFAULT (0) FOR [ItemOrder]
GO
/****** Object:  Default [DF_P_PortalWebpart_ColumnIndex]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[P_Widget] ADD  CONSTRAINT [DF_P_PortalWebpart_ColumnIndex]  DEFAULT (1) FOR [ColumnIndex]
GO
/****** Object:  Default [DF_P_PortalWebpart_RowIndex]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[P_Widget] ADD  CONSTRAINT [DF_P_PortalWebpart_RowIndex]  DEFAULT (1) FOR [RowIndex]
GO
/****** Object:  Default [DF_S_Calendar_ObjectType]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Calendar] ADD  CONSTRAINT [DF_S_Calendar_ObjectType]  DEFAULT (0) FOR [ObjectType]
GO
/****** Object:  Default [DF_S_Calendar_CreateTime]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Calendar] ADD  CONSTRAINT [DF_S_Calendar_CreateTime]  DEFAULT (getdate()) FOR [CreateTime]
GO
/****** Object:  Default [DF_S_Confitem_Type]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Config] ADD  CONSTRAINT [DF_S_Confitem_Type]  DEFAULT ('string') FOR [Type]
GO
/****** Object:  Default [DF_S_Grade_IsGrade]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Grade] ADD  CONSTRAINT [DF_S_Grade_IsGrade]  DEFAULT (0) FOR [IsGrade]
GO
/****** Object:  Default [DF_U_Placard_ObjType]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Placard] ADD  CONSTRAINT [DF_U_Placard_ObjType]  DEFAULT (0) FOR [ObjType]
GO
/****** Object:  Default [DF_U_Placard_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Placard] ADD  CONSTRAINT [DF_U_Placard_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_U_Placard_Hide]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Placard] ADD  CONSTRAINT [DF_U_Placard_Hide]  DEFAULT (0) FOR [Hide]
GO
/****** Object:  Default [DF_S_Placard_Title]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Placard] ADD  CONSTRAINT [DF_S_Placard_Title]  DEFAULT ('') FOR [Title]
GO
/****** Object:  Default [DF_S_Plugin_PluginType]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Plugin] ADD  CONSTRAINT [DF_S_Plugin_PluginType]  DEFAULT ('dd') FOR [PluginType]
GO
/****** Object:  Default [DF_S_Plugin_ItemOrder]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Plugin] ADD  CONSTRAINT [DF_S_Plugin_ItemOrder]  DEFAULT (0) FOR [ItemOrder]
GO
/****** Object:  Default [DF_S_Plugin_Enabled]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Plugin] ADD  CONSTRAINT [DF_S_Plugin_Enabled]  DEFAULT (1) FOR [Enabled]
GO
/****** Object:  Default [DF_S_SiteStat_Id]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SiteStat] ADD  CONSTRAINT [DF_S_SiteStat_Id]  DEFAULT (1) FOR [Id]
GO
/****** Object:  Default [DF_S_SiteStat_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SiteStat] ADD  CONSTRAINT [DF_S_SiteStat_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_S_SiteStat_VisitCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SiteStat] ADD  CONSTRAINT [DF_S_SiteStat_VisitCount]  DEFAULT (0) FOR [VisitCount]
GO
/****** Object:  Default [DF_S_SiteStat_UserCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SiteStat] ADD  CONSTRAINT [DF_S_SiteStat_UserCount]  DEFAULT (0) FOR [UserCount]
GO
/****** Object:  Default [DF_S_SiteStat_GroupCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SiteStat] ADD  CONSTRAINT [DF_S_SiteStat_GroupCount]  DEFAULT (0) FOR [GroupCount]
GO
/****** Object:  Default [DF_S_SiteStat_ArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SiteStat] ADD  CONSTRAINT [DF_S_SiteStat_ArticleCount]  DEFAULT (0) FOR [ArticleCount]
GO
/****** Object:  Default [DF_S_SiteStat_ResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SiteStat] ADD  CONSTRAINT [DF_S_SiteStat_ResourceCount]  DEFAULT (0) FOR [ResourceCount]
GO
/****** Object:  Default [DF_S_SiteStat_TopicCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SiteStat] ADD  CONSTRAINT [DF_S_SiteStat_TopicCount]  DEFAULT (0) FOR [TopicCount]
GO
/****** Object:  Default [DF_S_SiteStat_PhotoCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SiteStat] ADD  CONSTRAINT [DF_S_SiteStat_PhotoCount]  DEFAULT (0) FOR [PhotoCount]
GO
/****** Object:  Default [DF_S_SiteStat_TodayArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SiteStat] ADD  CONSTRAINT [DF_S_SiteStat_TodayArticleCount]  DEFAULT (0) FOR [TodayArticleCount]
GO
/****** Object:  Default [DF_S_SiteStat_YesterdayArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SiteStat] ADD  CONSTRAINT [DF_S_SiteStat_YesterdayArticleCount]  DEFAULT (0) FOR [YesterdayArticleCount]
GO
/****** Object:  Default [DF_S_SiteStat_TodayResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SiteStat] ADD  CONSTRAINT [DF_S_SiteStat_TodayResourceCount]  DEFAULT (0) FOR [TodayResourceCount]
GO
/****** Object:  Default [DF_S_SiteStat_YesterdayResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SiteStat] ADD  CONSTRAINT [DF_S_SiteStat_YesterdayResourceCount]  DEFAULT (0) FOR [YesterdayResourceCount]
GO
/****** Object:  Default [DF_S_SiteStat_CommentCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SiteStat] ADD  CONSTRAINT [DF_S_SiteStat_CommentCount]  DEFAULT (0) FOR [CommentCount]
GO
/****** Object:  Default [DF__S_SiteSta__Histo__43F60EC8]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SiteStat] ADD  DEFAULT (0) FOR [HistoryArticleCount]
GO
/****** Object:  Default [DF_S_SiteTheme_Status]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SiteTheme] ADD  CONSTRAINT [DF_S_SiteTheme_Status]  DEFAULT (0) FOR [Status]
GO
/****** Object:  Default [DF_S_SpecialSubject_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SpecialSubject] ADD  CONSTRAINT [DF_S_SpecialSubject_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_S_SpecialSubject_ExpiresDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SpecialSubject] ADD  CONSTRAINT [DF_S_SpecialSubject_ExpiresDate]  DEFAULT (getdate()) FOR [ExpiresDate]
GO
/****** Object:  Default [DF_S_SpecialSubjectPhoto_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SpecialSubjectPhoto] ADD  CONSTRAINT [DF_S_SpecialSubjectPhoto_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_S_SpecialSubjectTopic_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SpecialSubjectTopic] ADD  CONSTRAINT [DF_S_SpecialSubjectTopic_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_S_Subject_MetaSubjectId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Subject] ADD  CONSTRAINT [DF_S_Subject_MetaSubjectId]  DEFAULT (0) FOR [MetaSubjectId]
GO
/****** Object:  Default [DF_S_Subject_MetaGradeId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Subject] ADD  CONSTRAINT [DF_S_Subject_MetaGradeId]  DEFAULT (0) FOR [MetaGradeId]
GO
/****** Object:  Default [DF_S_Subject_OrderNum]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Subject] ADD  CONSTRAINT [DF_S_Subject_OrderNum]  DEFAULT (0) FOR [OrderNum]
GO
/****** Object:  Default [DF_S_Subject_SubjectCode]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Subject] ADD  CONSTRAINT [DF_S_Subject_SubjectCode]  DEFAULT ('') FOR [SubjectCode]
GO
/****** Object:  Default [DF_S_Subject_VisitCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Subject] ADD  CONSTRAINT [DF_S_Subject_VisitCount]  DEFAULT (0) FOR [VisitCount]
GO
/****** Object:  Default [DF_S_Subject_UserCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Subject] ADD  CONSTRAINT [DF_S_Subject_UserCount]  DEFAULT (0) FOR [UserCount]
GO
/****** Object:  Default [DF_S_Subject_GroupCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Subject] ADD  CONSTRAINT [DF_S_Subject_GroupCount]  DEFAULT (0) FOR [GroupCount]
GO
/****** Object:  Default [DF_S_Subject_ArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Subject] ADD  CONSTRAINT [DF_S_Subject_ArticleCount]  DEFAULT (0) FOR [ArticleCount]
GO
/****** Object:  Default [DF_S_Subject_ResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Subject] ADD  CONSTRAINT [DF_S_Subject_ResourceCount]  DEFAULT (0) FOR [ResourceCount]
GO
/****** Object:  Default [DF_S_Subject_reslibCId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Subject] ADD  CONSTRAINT [DF_S_Subject_reslibCId]  DEFAULT (0) FOR [reslibCId]
GO
/****** Object:  Default [DF_S_Subject_TodayArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Subject] ADD  CONSTRAINT [DF_S_Subject_TodayArticleCount]  DEFAULT (0) FOR [TodayArticleCount]
GO
/****** Object:  Default [DF_S_Subject_YesterdayArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Subject] ADD  CONSTRAINT [DF_S_Subject_YesterdayArticleCount]  DEFAULT (0) FOR [YesterdayArticleCount]
GO
/****** Object:  Default [DF_S_Subject_TodayResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Subject] ADD  CONSTRAINT [DF_S_Subject_TodayResourceCount]  DEFAULT (0) FOR [TodayResourceCount]
GO
/****** Object:  Default [DF_S_Subject_YesterdayResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Subject] ADD  CONSTRAINT [DF_S_Subject_YesterdayResourceCount]  DEFAULT (0) FOR [YesterdayResourceCount]
GO
/****** Object:  Default [DF__S_Subject__Subje__5708E33C]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Subject] ADD  DEFAULT (newid()) FOR [SubjectGuid]
GO
/****** Object:  Default [DF__S_Subject__Histo__57FD0775]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Subject] ADD  DEFAULT (0) FOR [HistoryArticleCount]
GO
/****** Object:  Default [DF_S_Subject_PhotoCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Subject] ADD  CONSTRAINT [DF_S_Subject_PhotoCount]  DEFAULT (0) FOR [PhotoCount]
GO
/****** Object:  Default [DF_S_Subject_VideoCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Subject] ADD  CONSTRAINT [DF_S_Subject_VideoCount]  DEFAULT (0) FOR [VideoCount]
GO
/****** Object:  Default [DF_S_Subject_PrepareCourseCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Subject] ADD  CONSTRAINT [DF_S_Subject_PrepareCourseCount]  DEFAULT (0) FOR [PrepareCourseCount]
GO
/****** Object:  Default [DF_S_Subject_ActionCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Subject] ADD  CONSTRAINT [DF_S_Subject_ActionCount]  DEFAULT (0) FOR [ActionCount]
GO
/****** Object:  Default [DF_S_SubjectWebpart_DisplayName]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SubjectWebpart] ADD  CONSTRAINT [DF_S_SubjectWebpart_DisplayName]  DEFAULT (N'ok') FOR [DisplayName]
GO
/****** Object:  Default [DF_S_SubjectWebpart_SystemModule]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SubjectWebpart] ADD  CONSTRAINT [DF_S_SubjectWebpart_SystemModule]  DEFAULT (0) FOR [SystemModule]
GO
/****** Object:  Default [DF_S_SubjectWebpart_WebpartZone]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SubjectWebpart] ADD  CONSTRAINT [DF_S_SubjectWebpart_WebpartZone]  DEFAULT (3) FOR [WebpartZone]
GO
/****** Object:  Default [DF_S_SubjectWebpart_RowIndex]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SubjectWebpart] ADD  CONSTRAINT [DF_S_SubjectWebpart_RowIndex]  DEFAULT (0) FOR [RowIndex]
GO
/****** Object:  Default [DF_S_SubjectWebpart_Visibile]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SubjectWebpart] ADD  CONSTRAINT [DF_S_SubjectWebpart_Visibile]  DEFAULT (1) FOR [Visible]
GO
/****** Object:  Default [DF__S_Subject__PartT__081299CC]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SubjectWebpart] ADD  CONSTRAINT [DF__S_Subject__PartT__081299CC]  DEFAULT (0) FOR [PartType]
GO
/****** Object:  Default [DF__S_Subject__ShowT__0906BE05]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_SubjectWebpart] ADD  CONSTRAINT [DF__S_Subject__ShowT__0906BE05]  DEFAULT (0) FOR [ShowType]
GO
/****** Object:  Default [DF_S_TagRef_OrderNum]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_TagRef] ADD  CONSTRAINT [DF_S_TagRef_OrderNum]  DEFAULT (0) FOR [OrderNum]
GO
/****** Object:  Default [DF_U_Tag_TagUuid]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Tags] ADD  CONSTRAINT [DF_U_Tag_TagUuid]  DEFAULT (newid()) FOR [TagUuid]
GO
/****** Object:  Default [DF_U_Tag_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Tags] ADD  CONSTRAINT [DF_U_Tag_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_U_Tag_RefCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Tags] ADD  CONSTRAINT [DF_U_Tag_RefCount]  DEFAULT (0) FOR [RefCount]
GO
/****** Object:  Default [DF_S_Tags_ViewCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Tags] ADD  CONSTRAINT [DF_S_Tags_ViewCount]  DEFAULT (0) FOR [ViewCount]
GO
/****** Object:  Default [DF_U_Tag_TagType]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Tags] ADD  CONSTRAINT [DF_U_Tag_TagType]  DEFAULT (0) FOR [TagType]
GO
/****** Object:  Default [DF_S_Tags_Disabled]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_Tags] ADD  CONSTRAINT [DF_S_Tags_Disabled]  DEFAULT (0) FOR [Disabled]
GO
/****** Object:  Default [DF_S_VdirMap_Path]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_VdirMap] ADD  CONSTRAINT [DF_S_VdirMap_Path]  DEFAULT ('') FOR [Path]
GO
/****** Object:  Default [DF_S_VdirMap_Enabled]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[S_VdirMap] ADD  CONSTRAINT [DF_S_VdirMap_Enabled]  DEFAULT (0) FOR [Enabled]
GO


/****** Object:  Default [DF_SiteNav_SiteNavIsShow]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[SiteNav] ADD  CONSTRAINT [DF_SiteNav_SiteNavIsShow]  DEFAULT (1) FOR [SiteNavIsShow]
GO
/****** Object:  Default [DF_SiteNav_SiteNavItemOrder]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[SiteNav] ADD  CONSTRAINT [DF_SiteNav_SiteNavItemOrder]  DEFAULT (0) FOR [SiteNavItemOrder]
GO
/****** Object:  Default [DF_SiteNav_IsExternalLinks]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[SiteNav] ADD  CONSTRAINT [DF_SiteNav_IsExternalLinks]  DEFAULT (0) FOR [IsExternalLink]
GO
/****** Object:  Default [DF_SiteNav_OwnerType_1]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[SiteNav] ADD  CONSTRAINT [DF_SiteNav_OwnerType_1]  DEFAULT (0) FOR [OwnerType]
GO
/****** Object:  Default [DF_SiteNav_OwnerId_1]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[SiteNav] ADD  CONSTRAINT [DF_SiteNav_OwnerId_1]  DEFAULT (0) FOR [OwnerId]
GO
/****** Object:  Default [DF_SiteNews_SubjectId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[SiteNews] ADD  CONSTRAINT [DF_SiteNews_SubjectId]  DEFAULT (0) FOR [SubjectId]
GO
/****** Object:  Default [DF_SiteNews_Title]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[SiteNews] ADD  CONSTRAINT [DF_SiteNews_Title]  DEFAULT ('') FOR [Title]
GO
/****** Object:  Default [DF_SiteNews_Status]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[SiteNews] ADD  CONSTRAINT [DF_SiteNews_Status]  DEFAULT (0) FOR [Status]
GO
/****** Object:  Default [DF_SiteNews_NewsType]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[SiteNews] ADD  CONSTRAINT [DF_SiteNews_NewsType]  DEFAULT (0) FOR [NewsType]
GO
/****** Object:  Default [DF_SiteNews_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[SiteNews] ADD  CONSTRAINT [DF_SiteNews_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_SiteNews_ViewCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[SiteNews] ADD  CONSTRAINT [DF_SiteNews_ViewCount]  DEFAULT (0) FOR [ViewCount]
GO
/****** Object:  Default [DF_T_NewSpecialSubject_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[T_NewSpecialSubject] ADD  CONSTRAINT [DF_T_NewSpecialSubject_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_U_Favorites_favDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_Favorites] ADD  CONSTRAINT [DF_U_Favorites_favDate]  DEFAULT (getdate()) FOR [favDate]
GO
/****** Object:  Default [DF_U_Friend_AddTime]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_Friend] ADD  CONSTRAINT [DF_U_Friend_AddTime]  DEFAULT (getdate()) FOR [AddTime]
GO
/****** Object:  Default [DF_U_Friend_IsBlack]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_Friend] ADD  CONSTRAINT [DF_U_Friend_IsBlack]  DEFAULT (0) FOR [IsBlack]
GO
/****** Object:  Default [DF_U_Incident_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_FriendThings] ADD  CONSTRAINT [DF_U_Incident_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_U_Incident_Title]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_FriendThings] ADD  CONSTRAINT [DF_U_Incident_Title]  DEFAULT ('') FOR [Title]
GO
/****** Object:  Default [DF_U_LeaveWord_Title]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_LeaveWord] ADD  CONSTRAINT [DF_U_LeaveWord_Title]  DEFAULT ('') FOR [Title]
GO
/****** Object:  Default [DF_U_LeaveWord_createDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_LeaveWord] ADD  CONSTRAINT [DF_U_LeaveWord_createDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_U_LeaveWord_ReplyTimes]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_LeaveWord] ADD  CONSTRAINT [DF_U_LeaveWord_ReplyTimes]  DEFAULT (0) FOR [ReplyTimes]
GO
/****** Object:  Default [DF_U_LeaveWord_IsAnon]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_LeaveWord] ADD  CONSTRAINT [DF_U_LeaveWord_IsAnon]  DEFAULT (0) FOR [IsAnon]
GO
/****** Object:  Default [DF_B_Message_sendtime]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_Message] ADD  CONSTRAINT [DF_B_Message_sendtime]  DEFAULT (getdate()) FOR [sendtime]
GO
/****** Object:  Default [DF_B_Message_isRead]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_Message] ADD  CONSTRAINT [DF_B_Message_isRead]  DEFAULT (0) FOR [isRead]
GO
/****** Object:  Default [DF_B_Message_isDel]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_Message] ADD  CONSTRAINT [DF_B_Message_isDel]  DEFAULT (0) FOR [isDel]
GO
/****** Object:  Default [DF_U_Message_isSenderDel]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_Message] ADD  CONSTRAINT [DF_U_Message_isSenderDel]  DEFAULT (0) FOR [isSenderDel]
GO
/****** Object:  Default [DF_B_Message_isReply]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_Message] ADD  CONSTRAINT [DF_B_Message_isReply]  DEFAULT (0) FOR [isReply]
GO
/****** Object:  Default [DF_U_PunishScore_userID]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_PunishScore] ADD  CONSTRAINT [DF_U_PunishScore_userID]  DEFAULT (0) FOR [UserId]
GO
/****** Object:  Default [DF_U_PunishScore_ObjId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_PunishScore] ADD  CONSTRAINT [DF_U_PunishScore_ObjId]  DEFAULT (0) FOR [ObjId]
GO
/****** Object:  Default [DF_U_PunishScore_PunishDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_PunishScore] ADD  CONSTRAINT [DF_U_PunishScore_PunishDate]  DEFAULT (getdate()) FOR [PunishDate]
GO
/****** Object:  Default [DF_U_PunishScore_Score]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_PunishScore] ADD  CONSTRAINT [DF_U_PunishScore_Score]  DEFAULT (0) FOR [Score]
GO
/****** Object:  Default [DF_U_PunishScore_CreateUserId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_PunishScore] ADD  CONSTRAINT [DF_U_PunishScore_CreateUserId]  DEFAULT (0) FOR [CreateUserId]
GO
/****** Object:  Default [DF_U_UserOnLineStat_Highest]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserOnLineStat] ADD  CONSTRAINT [DF_U_UserOnLineStat_Highest]  DEFAULT (0) FOR [Highest]
GO
/****** Object:  Default [DF_U_UserOnLineStat_AppearTime]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserOnLineStat] ADD  CONSTRAINT [DF_U_UserOnLineStat_AppearTime]  DEFAULT (getdate()) FOR [AppearTime]
GO
/****** Object:  Default [DF__U_UserStat__StatGuid]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__StatGuid]  DEFAULT (newid()) FOR [StatGuid]
GO
/****** Object:  Default [DF__U_UserStat__LoginName]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__LoginName]  DEFAULT ('') FOR [LoginName]
GO
/****** Object:  Default [DF__U_UserStat__TrueName]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__TrueName]  DEFAULT ('') FOR [TrueName]
GO
/****** Object:  Default [DF__U_UserStat__NickName]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__NickName]  DEFAULT ('') FOR [NickName]
GO
/****** Object:  Default [DF__U_UserStat__Gender]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__Gender]  DEFAULT (0) FOR [Gender]
GO
/****** Object:  Default [DF__U_UserStat__UnitId]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__UnitId]  DEFAULT (0) FOR [UnitId]
GO
/****** Object:  Default [DF__U_UserStat__BlogName]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__BlogName]  DEFAULT (' 的工作室') FOR [BlogName]
GO
/****** Object:  Default [DF__U_UserStat__UserStatus]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__UserStatus]  DEFAULT (0) FOR [UserStatus]
GO
/****** Object:  Default [DF__U_UserStat__UserGroupID]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__UserGroupID]  DEFAULT (0) FOR [UserGroupID]
GO
/****** Object:  Default [DF__U_UserStat__VisitCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__VisitCount]  DEFAULT (0) FOR [VisitCount]
GO
/****** Object:  Default [DF__U_UserStat__VisitArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__VisitArticleCount]  DEFAULT (0) FOR [VisitArticleCount]
GO
/****** Object:  Default [DF__U_UserStat__VisitResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__VisitResourceCount]  DEFAULT (0) FOR [VisitResourceCount]
GO
/****** Object:  Default [DF__U_UserStat__ArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__ArticleCount]  DEFAULT (0) FOR [ArticleCount]
GO
/****** Object:  Default [DF__U_UserStat__RecommendArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__RecommendArticleCount]  DEFAULT (0) FOR [RecommendArticleCount]
GO
/****** Object:  Default [DF__U_UserStat__ArticleCommentCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__ArticleCommentCount]  DEFAULT (0) FOR [ArticleCommentCount]
GO
/****** Object:  Default [DF__U_UserStat__ArticleICommentCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__ArticleICommentCount]  DEFAULT (0) FOR [ArticleICommentCount]
GO
/****** Object:  Default [DF__U_UserStat__ResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__ResourceCount]  DEFAULT (0) FOR [ResourceCount]
GO
/****** Object:  Default [DF__U_UserStat__RecommendResourceCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__RecommendResourceCount]  DEFAULT (0) FOR [RecommendResourceCount]
GO
/****** Object:  Default [DF__U_UserStat__ResourceCommentCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__ResourceCommentCount]  DEFAULT (0) FOR [ResourceCommentCount]
GO
/****** Object:  Default [DF__U_UserStat__ResourceICommentCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__ResourceICommentCount]  DEFAULT (0) FOR [ResourceICommentCount]
GO
/****** Object:  Default [DF__U_UserStat__ResourceDownloadCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__ResourceDownloadCount]  DEFAULT (0) FOR [ResourceDownloadCount]
GO
/****** Object:  Default [DF__U_UserStat__CreateGroupCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__CreateGroupCount]  DEFAULT (0) FOR [CreateGroupCount]
GO
/****** Object:  Default [DF__U_UserStat__JionGroupCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__JionGroupCount]  DEFAULT (0) FOR [JionGroupCount]
GO
/****** Object:  Default [DF__U_UserStat__PhotoCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__PhotoCount]  DEFAULT (0) FOR [PhotoCount]
GO
/****** Object:  Default [DF__U_UserStat__CourseCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__CourseCount]  DEFAULT (0) FOR [CourseCount]
GO
/****** Object:  Default [DF__U_UserStat__TopicCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__TopicCount]  DEFAULT (0) FOR [TopicCount]
GO
/****** Object:  Default [DF__U_UserStat__CommentCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__CommentCount]  DEFAULT (0) FOR [CommentCount]
GO
/****** Object:  Default [DF__U_UserStat__UsedFileSize]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__UsedFileSize]  DEFAULT (0) FOR [UsedFileSize]
GO
/****** Object:  Default [DF__U_UserStat__UserScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__UserScore]  DEFAULT (0) FOR [UserScore]
GO
/****** Object:  Default [DF__U_UserStat__UserClassID]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__UserClassID]  DEFAULT (0) FOR [UserClassID]
GO
/****** Object:  Default [DF__U_UserStat__PositionID]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__PositionID]  DEFAULT (0) FOR [PositionID]
GO
/****** Object:  Default [DF__U_UserStat__ArticleScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__ArticleScore]  DEFAULT (0) FOR [ArticleScore]
GO
/****** Object:  Default [DF__U_UserStat__ResourceScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__ResourceScore]  DEFAULT (0) FOR [ResourceScore]
GO
/****** Object:  Default [DF__U_UserStat__CommentScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__CommentScore]  DEFAULT (0) FOR [CommentScore]
GO
/****** Object:  Default [DF__U_UserStat__MyArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__MyArticleCount]  DEFAULT (0) FOR [MyArticleCount]
GO
/****** Object:  Default [DF__U_UserStat__OtherArticleCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__OtherArticleCount]  DEFAULT (0) FOR [OtherArticleCount]
GO
/****** Object:  Default [DF__U_UserStat__ArticlePunishScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__ArticlePunishScore]  DEFAULT (0) FOR [ArticlePunishScore]
GO
/****** Object:  Default [DF__U_UserStat__ResourcePunishScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__ResourcePunishScore]  DEFAULT (0) FOR [ResourcePunishScore]
GO
/****** Object:  Default [DF__U_UserStat__CommentPunishScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__CommentPunishScore]  DEFAULT (0) FOR [CommentPunishScore]
GO
/****** Object:  Default [DF__U_UserStat__PhotoPunishScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__PhotoPunishScore]  DEFAULT (0) FOR [PhotoPunishScore]
GO
/****** Object:  Default [DF__U_UserStat__videoCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__videoCount]  DEFAULT (0) FOR [videoCount]
GO
/****** Object:  Default [DF__U_UserStat__photoScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__photoScore]  DEFAULT (0) FOR [photoScore]
GO
/****** Object:  Default [DF__U_UserStat__videoPunishScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__videoPunishScore]  DEFAULT (0) FOR [videoPunishScore]
GO
/****** Object:  Default [DF__U_UserStat__videoScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__videoScore]  DEFAULT (0) FOR [videoScore]
GO
/****** Object:  Default [DF__U_UserStat__PrepareCourseCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__PrepareCourseCount]  DEFAULT (0) FOR [PrepareCourseCount]
GO
/****** Object:  Default [DF__U_UserStat__WorkDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF__U_UserStat__WorkDate]  DEFAULT (getdate()) FOR [WorkDate]
GO
/****** Object:  Default [DF_U_UserStat_PunishScore]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[U_UserStat] ADD  CONSTRAINT [DF_U_UserStat_PunishScore]  DEFAULT (0) FOR [PunishScore]
GO
/****** Object:  Default [DF__UnitNews__Create__47FC752D]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[UnitNews] ADD  CONSTRAINT [DF__UnitNews__Create__47FC752D]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF__UnitNews__ViewCo__48F09966]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[UnitNews] ADD  CONSTRAINT [DF__UnitNews__ViewCo__48F09966]  DEFAULT (0) FOR [ViewCount]
GO
/****** Object:  Default [DF__UnitNews__ItemTy__49E4BD9F]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[UnitNews] ADD  CONSTRAINT [DF__UnitNews__ItemTy__49E4BD9F]  DEFAULT (0) FOR [ItemType]
GO
/****** Object:  Default [DF_UnitNews_CreateDate]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[UnitNewsNotice] ADD  CONSTRAINT [DF_UnitNews_CreateDate]  DEFAULT (getdate()) FOR [CreateDate]
GO
/****** Object:  Default [DF_UnitNews_ViewCount]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[UnitNewsNotice] ADD  CONSTRAINT [DF_UnitNews_ViewCount]  DEFAULT (0) FOR [ViewCount]
GO
/****** Object:  Default [DF_UnitNewsNotice_ItemType]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[UnitNewsNotice] ADD  CONSTRAINT [DF_UnitNewsNotice_ItemType]  DEFAULT (0) FOR [ItemType]
GO
/****** Object:  Default [DF_UnitWebpart_DisplayName_1]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[UnitWebpart] ADD  CONSTRAINT [DF_UnitWebpart_DisplayName_1]  DEFAULT ('') FOR [DisplayName]
GO
/****** Object:  Default [DF_UnitWebpart_SystemModule]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[UnitWebpart] ADD  CONSTRAINT [DF_UnitWebpart_SystemModule]  DEFAULT (0) FOR [SystemModule]
GO
/****** Object:  Default [DF_UnitWebpart_WebpartZone]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[UnitWebpart] ADD  CONSTRAINT [DF_UnitWebpart_WebpartZone]  DEFAULT (0) FOR [WebpartZone]
GO
/****** Object:  Default [DF_UnitWebpart_RowIndex]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[UnitWebpart] ADD  CONSTRAINT [DF_UnitWebpart_RowIndex]  DEFAULT (0) FOR [RowIndex]
GO
/****** Object:  Default [DF_UnitWebpart_Visible]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[UnitWebpart] ADD  CONSTRAINT [DF_UnitWebpart_Visible]  DEFAULT (1) FOR [Visible]
GO
/****** Object:  Default [DF_UnitWebpart_PartType_1]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[UnitWebpart] ADD  CONSTRAINT [DF_UnitWebpart_PartType_1]  DEFAULT (0) FOR [PartType]
GO
/****** Object:  Default [DF_UnitWebpart_ShowType_1]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[UnitWebpart] ADD  CONSTRAINT [DF_UnitWebpart_ShowType_1]  DEFAULT (0) FOR [ShowType]
GO
/****** Object:  Default [DF_UnitWebpart_ShowCount_1]    Script Date: 09/16/2013 16:02:52 ******/
ALTER TABLE [dbo].[UnitWebpart] ADD  CONSTRAINT [DF_UnitWebpart_ShowCount_1]  DEFAULT (6) FOR [ShowCount]
GO



/*************** 初始化数据 *******************/
/****** Object:  Table [dbo].[B_Category]    Script Date: 08/30/2013 15:35:34 ******/
SET IDENTITY_INSERT [dbo].[B_Category] ON
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (1, CONVERT(TEXT, N'5C3913FA-EDFF-456A-B110-B58E2E32FAB6'), N'教育动态', CONVERT(TEXT, N'default'), NULL, CONVERT(TEXT, N'/'), 1, 0, NULL, 0, CAST(0x00009A6D00C7FE18 AS DateTime), CAST(0x00009A6D00C81795 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (2, CONVERT(TEXT, N'20BDE7FF-8228-4057-A782-DA86653673E6'), N'教育科研', CONVERT(TEXT, N'default'), NULL, CONVERT(TEXT, N'/'), 2, 0, NULL, 0, CAST(0x00009A6D00C7FE18 AS DateTime), CAST(0x00009A6D00C81795 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (3, CONVERT(TEXT, N'BAE36B35-C281-4145-AB7E-D7ACA811FC60'), N'教育杂谈', CONVERT(TEXT, N'default'), NULL, CONVERT(TEXT, N'/'), 3, 0, NULL, 0, CAST(0x00009A6D00C7FE18 AS DateTime), CAST(0x00009A6D00C81795 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (4, CONVERT(TEXT, N'C8938C89-E16D-4BBB-B1EA-4C42FC3A7347'), N'争鸣探索', CONVERT(TEXT, N'default'), NULL, CONVERT(TEXT, N'/'), 4, 0, NULL, 0, CAST(0x00009A6D00C7FE18 AS DateTime), CAST(0x00009A6D00C81795 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (5, CONVERT(TEXT, N'598A1A74-BBAD-47AC-8B0D-F9626960993F'), N'课程改革', CONVERT(TEXT, N'default'), NULL, CONVERT(TEXT, N'/'), 5, 0, NULL, 0, CAST(0x00009A6D00C7FE18 AS DateTime), CAST(0x00009A6D00C81795 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (6, CONVERT(TEXT, N'28D788E1-3D9D-4C10-BE68-940F0CDDF296'), N'课题研究', CONVERT(TEXT, N'default'), NULL, CONVERT(TEXT, N'/'), 6, 0, NULL, 0, CAST(0x00009A6D00C7FE18 AS DateTime), CAST(0x00009A6D00C81795 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (7, CONVERT(TEXT, N'BBAF7E13-B4DD-4AB3-A922-A4C9DB7EDE88'), N'教研活动', CONVERT(TEXT, N'default'), NULL, CONVERT(TEXT, N'/'), 7, 0, NULL, 0, CAST(0x00009A6D00C7FE18 AS DateTime), CAST(0x00009A6D00C81795 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (8, CONVERT(TEXT, N'047B3719-185E-491E-9921-8EC4103412C7'), N'教育管理', CONVERT(TEXT, N'default'), NULL, CONVERT(TEXT, N'/'), 8, 0, NULL, 0, CAST(0x00009A6D00C7FE18 AS DateTime), CAST(0x00009A6D00C81795 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (9, CONVERT(TEXT, N'A3ED5C1E-0879-48DC-BB8E-D4DFC440BD27'), N'班主任工作', CONVERT(TEXT, N'default'), NULL, CONVERT(TEXT, N'/'), 9, 0, NULL, 0, CAST(0x00009A6D00C7FE18 AS DateTime), CAST(0x00009A6D00C81795 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (10, CONVERT(TEXT, N'E7D8437A-F7F4-4764-A2BA-E8041F2C9018'), N'考试研究', CONVERT(TEXT, N'default'), NULL, CONVERT(TEXT, N'/'), 10, 0, NULL, 0, CAST(0x00009A6D00C7FE18 AS DateTime), CAST(0x00009A6D00C81795 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (11, CONVERT(TEXT, N'DA45B3BF-4218-459D-9562-9CC61636906F'), N'教育技术', CONVERT(TEXT, N'default'), NULL, CONVERT(TEXT, N'/'), 11, 0, NULL, 0, CAST(0x00009A6D00C7FE18 AS DateTime), CAST(0x00009A6D00C81795 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (12, CONVERT(TEXT, N'7897E9FE-37EC-4B2E-904E-CF489B01B8D2'), N'社会与生活', CONVERT(TEXT, N'default'), NULL, CONVERT(TEXT, N'/'), 12, 0, NULL, 0, CAST(0x00009A6D00C7FE18 AS DateTime), CAST(0x00009A6D00C81795 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (13, CONVERT(TEXT, N'B2D1A9C2-0FD2-421E-9D33-C122252DCE83'), N'专题征文', CONVERT(TEXT, N'default'), NULL, CONVERT(TEXT, N'/'), 13, 0, NULL, 0, CAST(0x00009A6D00C7FE18 AS DateTime), CAST(0x00009A6D00C81795 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (48, CONVERT(TEXT, N'D2494B67-4720-48A7-823A-9BB18FDDC81F'), N'教研协作组', CONVERT(TEXT, N'group'), NULL, CONVERT(TEXT, N'/'), 0, 0, N'', 0, CAST(0x00009ACB0098E50E AS DateTime), CAST(0x00009ACB0098E50E AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (49, CONVERT(TEXT, N'CA5B26A5-2F4F-411D-A092-BABF7490E279'), N'课程研讨组', CONVERT(TEXT, N'group'), NULL, CONVERT(TEXT, N'/'), 1, 0, N'', 0, CAST(0x00009ACB0098F026 AS DateTime), CAST(0x00009ACB0098F026 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (50, CONVERT(TEXT, N'9C0E602E-9AB3-4FD7-BBB1-F6AB26AB1EB5'), N'课题项目组', CONVERT(TEXT, N'group'), NULL, CONVERT(TEXT, N'/'), 2, 0, N'', 0, CAST(0x00009ACB0098F889 AS DateTime), CAST(0x00009ACB0098F889 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (51, CONVERT(TEXT, N'9DC8FE11-EA02-4B54-9CFB-C8E66426CE66'), N'课程', CONVERT(TEXT, N'resource'), NULL, CONVERT(TEXT, N'/'), 0, 0, NULL, 0, CAST(0x00009ACB00991450 AS DateTime), CAST(0x00009ACB00991450 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (52, CONVERT(TEXT, N'28F07D71-4A0E-4F4E-9C4A-DD71B3AE3884'), N'教学设计', CONVERT(TEXT, N'resource'), NULL, CONVERT(TEXT, N'/'), 1, 0, NULL, 0, CAST(0x00009ACB00991C2B AS DateTime), CAST(0x00009ACB00991C2B AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (53, CONVERT(TEXT, N'0B5F6D12-5BDF-4388-BF8D-146324848997'), N'教学课件', CONVERT(TEXT, N'resource'), NULL, CONVERT(TEXT, N'/'), 2, 0, NULL, 0, CAST(0x00009ACB0099245A AS DateTime), CAST(0x00009ACB0099245A AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (54, CONVERT(TEXT, N'2A606ADE-0881-4CAD-8E7D-821D89071C02'), N'校园', CONVERT(TEXT, N'photo'), NULL, CONVERT(TEXT, N'/'), 0, 0, NULL, 0, CAST(0x00009ACB00994F29 AS DateTime), CAST(0x00009ACB00994F29 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (55, CONVERT(TEXT, N'C265FBC4-B258-427A-A9BA-EBF60C9092CA'), N'生活', CONVERT(TEXT, N'photo'), NULL, CONVERT(TEXT, N'/'), 1, 0, NULL, 0, CAST(0x00009ACB0099555E AS DateTime), CAST(0x00009ACB0099555E AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (56, CONVERT(TEXT, N'1AD63EC3-EE50-4B1C-9682-485ADA0DE91B'), N'风景', CONVERT(TEXT, N'photo'), NULL, CONVERT(TEXT, N'/'), 2, 0, NULL, 0, CAST(0x00009ADA00CEC048 AS DateTime), CAST(0x00009ADA00CEC048 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (57, CONVERT(TEXT, N'45F6149F-D83C-47CB-B885-CB5F5B9FD86C'), N'社会', CONVERT(TEXT, N'photo'), NULL, CONVERT(TEXT, N'/'), 3, 0, NULL, 0, CAST(0x00009ADA00CED0DA AS DateTime), CAST(0x00009ADA00CED0DA AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (58, CONVERT(TEXT, N'7E94BB9B-C29C-451B-8796-3A4DA3890CC3'), N'习题与试题', CONVERT(TEXT, N'resource'), NULL, CONVERT(TEXT, N'/'), 3, 0, NULL, 0, CAST(0x00009AE000B30BC3 AS DateTime), CAST(0x00009AE000B30BC3 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (59, CONVERT(TEXT, N'4EF024BC-EF1C-4F49-968A-49C37BE0CE40'), N'媒体素材', CONVERT(TEXT, N'resource'), NULL, CONVERT(TEXT, N'/'), 4, 0, NULL, 0, CAST(0x00009AE000B3192A AS DateTime), CAST(0x00009AE000B3192A AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (60, CONVERT(TEXT, N'544C63C6-819C-413C-96EC-26229BFDD357'), N'案例', CONVERT(TEXT, N'resource'), NULL, CONVERT(TEXT, N'/'), 5, 0, NULL, 0, CAST(0x00009AE000B346D9 AS DateTime), CAST(0x00009AE000B346D9 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (61, CONVERT(TEXT, N'E7924BA4-4AE9-4C6F-B337-4DA9C1820E54'), N'文献', CONVERT(TEXT, N'resource'), NULL, CONVERT(TEXT, N'/'), 6, 0, NULL, 0, CAST(0x00009AE000B35348 AS DateTime), CAST(0x00009AE000B35348 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (62, CONVERT(TEXT, N'2CCAEFA2-9D5D-4D32-BDA7-B7D264C794D6'), N'学生作品', CONVERT(TEXT, N'resource'), NULL, CONVERT(TEXT, N'/'), 7, 0, NULL, 0, CAST(0x00009AE000B35F04 AS DateTime), CAST(0x00009AE000B35F04 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (67, CONVERT(TEXT, N'4A7A827E-A867-4A26-90AA-F72D015681DB'), N'师生交流组', CONVERT(TEXT, N'group'), NULL, CONVERT(TEXT, N'/'), 3, 0, N'', 0, CAST(0x00009AE5010DCE61 AS DateTime), CAST(0x00009AE5010DCE61 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (87, CONVERT(TEXT, N'C1E8C30C-4F4C-4FA7-A430-875C903310BF'), N'家校交流组', CONVERT(TEXT, N'group'), NULL, CONVERT(TEXT, N'/'), 4, 0, N'', 0, CAST(0x00009B2000F60734 AS DateTime), CAST(0x00009B2000F60734 AS DateTime), 0)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (88, CONVERT(TEXT, N'5E7B6405-68C5-4BCA-ABB6-ED274976EC4E'), N'集体备课组', CONVERT(TEXT, N'group'), NULL, CONVERT(TEXT, N'/'), 0, 0, N'集体备课组:一个协作组设置为集体备课组后，不允许再设置成其他分类', 0, CAST(0x0000A15000BD53B9 AS DateTime), CAST(0x0000A15000BD53B9 AS DateTime), 1)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (89, CONVERT(TEXT, N'A76BDA40-FC6C-4CA1-BA6D-57151BB71567'), N'课题研究组', CONVERT(TEXT, N'group'), NULL, CONVERT(TEXT, N'/'), 0, 0, N'课题研究组:一个协作组设置为课题研究组后，不允许再设置成其他分类', 0, CAST(0x0000A15000BD53BE AS DateTime), CAST(0x0000A15000BD53BE AS DateTime), 1)
INSERT [dbo].[B_Category] ([CategoryId], [ObjectUuid], [Name], [ItemType], [ParentId], [ParentPath], [OrderNum], [ChildNum], [Description], [ItemNum], [CreateDate], [LastModified], [IsSystem]) VALUES (90, CONVERT(TEXT, N'E42EAD2F-73A2-4D29-8CC5-7B37818F125C'), N'普通协作组', CONVERT(TEXT, N'group'), NULL, CONVERT(TEXT, N'/'), 0, 0, N'普通协作组:一个协作组设置为普通协作组后,其分类允许再设置为其他类型', 0, CAST(0x0000A15000BD53C2 AS DateTime), CAST(0x0000A15000BD53C2 AS DateTime), 1)
SET IDENTITY_INSERT [dbo].[B_Category] OFF

GO


SET IDENTITY_INSERT [dbo].[S_VdirMap] ON
INSERT [dbo].[S_VdirMap] ([Id], [Vdir], [Path], [Enabled]) VALUES (1, CONVERT(TEXT, N'v'), CONVERT(TEXT, N'C:/Temp'), 0)
SET IDENTITY_INSERT [dbo].[S_VdirMap] OFF
GO

DELETE FROM [dbo].[S_Config]
GO
SET IDENTITY_INSERT [dbo].[S_Config] ON
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (1, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'site.name'), N'教研网', CONVERT(TEXT, N'string'), N'中教启星教研网', N'网站名称', N'网站名称(纯文字,不支持Html)')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (2, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'site.title'), N'中教启星网络教研云平台', CONVERT(TEXT, N'string'), N'中教启星网络教研云平台', N'网站标题', N'网站标题(纯文字,不支持Html)')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (3, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'site.keyword'), N'教研,中教启星', CONVERT(TEXT, N'string'), NULL, N'站点关键字', NULL)
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (4, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'site.copyright'), N'
 <strong>版权所有：</strong>北京中教启星科技股份有限公司 &copy; 2014年<br />
 <strong>公司地址：</strong>北京市海淀区上地十街1号院辉煌国际3号楼1107室 <strong>邮编：</strong>100085<br />
 <strong>联系电话：</strong>400-811-2828<strong>传真：</strong>010-80756445/47/49-888<br />
 ', CONVERT(TEXT, N'string'), NULL, N'站点版权信息', NULL)
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (5, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'site.webmaster.email'), N'webmaster@chinaedustar.com', CONVERT(TEXT, N'string'), NULL, N'站长信箱', NULL)
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (6, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'user.register.enabled'), N'true', CONVERT(TEXT, N'boolean'), N'true', N'是否允许用户注册', N'= 是 表示允许注册；= 否 表示不允许注册')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (7, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'user.register.needAudit'), N'true', CONVERT(TEXT, N'boolean'), N'true', N'新用户注册是否需要管理员审核', N'= 是 表示需要审核；= 否 表示注册后即可直接登录')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (8, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'user.register.uniqueEmail'), N'true', CONVERT(TEXT, N'boolean'), N'true', N'邮件地址唯一', N'= 是 表示需要验证邮件地址唯一性；= 否 表示不需要')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (9, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'user.register.uniqueBlogName'), N'false', CONVERT(TEXT, N'boolean'), N'false', N'博客名称不允许重复', N'= 是 表示博客名称允许重复；= 否 表示博客名称不允许重复')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (10, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'group.create.needApprove'), N'true', CONVERT(TEXT, N'boolean'), N'true', N'申请群组是否需要审核', N'= 是 表示需要管理员审核；= 否 表示不需要管理员审核')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (11, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'user.register.mustSubject'), N'true', CONVERT(TEXT, N'boolean'), N'true', N'注册时是否学科必选', N'= 是 表示必须要选择一个学科；= 否 表示学科可以不选择')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (12, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'user.admin.showEmail'), N'false', CONVERT(TEXT, N'boolean'), N'false', N'是否在系统后台用户管理显示Email列', N'= 是 表示显示；= 否 表示不显示')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (13, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'user.register.mustGrade'), N'true', CONVERT(TEXT, N'boolean'), N'true', N'注册时是否学段必选', N'= 是 表示必须要选择一个学段；= 否 表示学段可以不选择')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (14, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'user.register.mustTrueName'), N'true', CONVERT(TEXT, N'boolean'), N'true', N'注册时是否实名必填', N'= 是 表示必须要填写真实姓名；= 否 表示不需要填写真实姓名')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (15, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'user.site.comment.enabled'), N'true', CONVERT(TEXT, N'boolean'), N'true', N'是否只有登录用户才能评论', N'= 是 表示必须登录用户才能评论；= 否 表示不需要登录')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (16, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'site.stop.info'), N'系统正在维护中，请稍候再访问……', CONVERT(TEXT, N'string'), N'系统正在维护中，请稍候再访问……', N'网站维护时显示的信息，管理员仍然可以登录后台。 ', N'网站维护时显示的信息，管理员仍然可以登录后台。 ')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (17, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'site.enabled'), N'true', CONVERT(TEXT, N'boolean'), N'true', N'是否启用网站', N'是否启用网站')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (18, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'profile.update.trueName.needAudit'), N'false', CONVERT(TEXT, N'boolean'), N'true', N'是否修改个人信息中的真实姓名修改要管理员审核', N'= 是 表示需要管理员审核通过才能登录；= 否 表示不需要')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (19, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'profile.update.subject.needAudit'), N'false', CONVERT(TEXT, N'boolean'), N'true', N'是否修改个人信息中的学科修改要管理员审核', N'= 是 表示需要管理员审核通过才能登录；= 否 表示不需要')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (20, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'profile.update.grade.needAudit'), N'false', CONVERT(TEXT, N'boolean'), N'true', N'是否修改个人信息中的学段修改要管理员审核', N'= 是 表示需要管理员审核通过才能登录；= 否 表示不需要')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (21, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'site.resourceUrl'), N'', CONVERT(TEXT, N'string'), N'', N'精品资源', N'学科资源网站')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (22, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'resource.uploadMaximumSize'), N'300', CONVERT(TEXT, N'string'), N'300M', N'用户空间的大小限制(单位：M)', N'用户空间的大小限制(单位：M)')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (23, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'resource.uploadAllowTypes'), N'zip,rar', CONVERT(TEXT, N'string'), N'zip,rar', N'资源上载的文件类型限制', N'允许上载的文件类型:输入文件后缀名,多个之间用逗号分开')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (24, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'score.my.article.add'), N'2', CONVERT(TEXT, N'int'), N'2', N'原创文章得分', N'用户原创文章得分')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (25, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'score.article.rcmd'), N'1', CONVERT(TEXT, N'int'), N'1', N'文章被推荐得分', N'用户的文章被推荐得分')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (26, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'score.resource.add'), N'2', CONVERT(TEXT, N'int'), N'2', N'上载资源得分', N'用户上载资源得分')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (27, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'score.resource.rcmd'), N'1', CONVERT(TEXT, N'int'), N'1', N'资源被推荐得分', N'用户上载的资源被推荐得分')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (28, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'score.comment.add'), N'2', CONVERT(TEXT, N'int'), N'1', N'发表评论得分', N'用户发表评论得分')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (29, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'resource.download'), N'false', CONVERT(TEXT, N'boolean'), N'false', N'是否允许非登陆用户下载资源', N'= 是 表示允许；= 否 表示不允许')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (30, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'user.resource.upload.limit'), N'false', CONVERT(TEXT, N'boolean'), N'false', N'是否限制用户空间的大小', N'= 是 表示需要限制(默认大小:300M)；= 否 表示不需要限制')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (31, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'score.article.adminDel'), N'1', CONVERT(TEXT, N'int'), N'1', N'文章被删除罚分', N'用户文章被管理员删除的罚分')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (32, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'score.resource.adminDel'), N'1', CONVERT(TEXT, N'int'), N'1', N'资源被删除罚分', N'用户资源被管理员删除的罚分')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (33, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'score.comment.adminDel'), N'1', CONVERT(TEXT, N'int'), N'1', N'评论被删除罚分', N'评论被管理员删除的罚分')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (34, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'user.register.mustRole'), N'true', CONVERT(TEXT, N'boolean'), N'true', N'注册时是否角色必选', N'= 是 表示必须要选择一个角色；= 否 表示角色可以不选')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (35, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'score.other.article.add'), N'0', CONVERT(TEXT, N'int'), N'0', N'转载文章得分', N'用户转载文章得分')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (36, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'user.register.mustIdCard'), N'false', CONVERT(TEXT, N'boolean'), N'false', N'注册时是否身份证号码必填', N'= 是 表示必须输入；= 否 表示可以不输入')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (37, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'site.screen.enalbed'), N'true', CONVERT(TEXT, N'string'), N'true', N'是否屏蔽系统中出现的非法词汇', N'= 是 需要屏蔽系统中出现的非法词汇；= 否 表示不需要')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (38, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'site.screen.replace'), N'***', CONVERT(TEXT, N'string'), N'***', N'用来覆盖屏蔽词的字符', N'用来覆盖屏蔽词的字符')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (39, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'site.screen.keyword'), N'法轮功,枪支弹药,办假证', CONVERT(TEXT, N'string'), N'法轮功,枪支弹药,办假证', N'非法词汇列表', N'非法词汇列表')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (40, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'score.photo.adminDel'), N'1', CONVERT(TEXT, N'int'), N'1', N'图片被删除罚分', N'用户图片被管理员删除的罚分')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (41, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'trueName.equals.nickName'), N'true', CONVERT(TEXT, N'boolean'), N'true', N'是否要求真实姓名和呢称一致', N'= 是 表示要求一致；= 否 表示不要求一致')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (42, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'site.user.online.time'), N'10', CONVERT(TEXT, N'string'), N'600', N'用户在线时长设定(单位：秒)', N'默认是10分钟')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (43, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'score.photo.upload'), N'0', CONVERT(TEXT, N'int'), N'1', N'上传照片得分', N'上传照片得分')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (44, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'score.video.upload'), N'0', CONVERT(TEXT, N'int'), N'1', N'上传视频得分', N'上传视频得分')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (45, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'score.video.adminDel'), N'0', CONVERT(TEXT, N'int'), N'1', N'视频被删除罚分', N'视频被删除罚分')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (46, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'site.resource.beforecheck'), N'false', CONVERT(TEXT, N'boolean'), N'false', N'文章、资源、视频、图片是否需要先审核再发布', N'= 是 表示先审再发；= 否 表示先发后审')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (47, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'topsite_url'), N'http://jypt.chinaedustar.com:8080/', CONVERT(TEXT, N'string'), N'', N'市平台地址', N'市平台地址')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (48, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'auto_push_up'), N'true', CONVERT(TEXT, N'boolean'), N'true', N'网站内容自动推送', N'= 是表示自动推送；否则为手动推送')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (49, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'user.site.article_show'), N'false', CONVERT(TEXT, N'boolean'), N'false', N'文章内容是否只能登录后才能查看', N'是：只有登录才能查看文章；否则，任何用户都能查看文章内容')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (50, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'site.logo'), N'', CONVERT(TEXT, N'string'), NULL, N'网站Logo', N'网站Logo，图片或者Flash。')
/**INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (51, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'site.verifyCode.enabled'), N'true', CONVERT(TEXT, N'boolean'), N'true', N'是否显示验证码', N'= 是 表示显示；= 否 表示不显示')**/
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (52, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'siteThemeUrl'), NULL, CONVERT(TEXT, N'string'), NULL, N'网站的样式地址', N'网站的样式地址')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (53, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'contact'), N'<div style=''padding:10px;line-height:160%;''>
 <strong>版权所有：</strong>北京中教启星科技股份有限公司 &copy; 2014年<br />
 <strong>电话：</strong>400-811-2828  <strong>传真：</strong>010-80756445/47/49-888 <strong>办公地址：</strong>北京市海淀区上地十街1号院辉煌国际3号楼1107室 <strong>邮编：</strong>100085
</div>', CONVERT(TEXT, N'string'), NULL, N'联系我们页面信息', N'联系我们页面信息')
INSERT [dbo].[S_Config] ([Id], [ItemType], [Name], [Value], [Type], [DefVal], [Title], [Description]) VALUES (54, CONVERT(TEXT, N'jitar'), CONVERT(TEXT, N'reportType'), N'政治,色情,违法,人身攻击,抄袭,其它,地方', CONVERT(TEXT, N'string'), N'政治,色情,违法,人身攻击,抄袭,其它,地方', N'举报类型', N'举报类型')
SET IDENTITY_INSERT [dbo].[S_Config] OFF

GO

INSERT [dbo].[U_UserOnLineStat] ([Id], [Highest], [AppearTime]) VALUES (1, 2, CONVERT(TEXT, N'2009-06-10 09:52:43'))
GO
DELETE FROM [dbo].[Jitar_UserType]
GO

SET IDENTITY_INSERT [dbo].[Jitar_UserType] ON
INSERT [dbo].[Jitar_UserType] ([TypeId], [TypeName], [IsSystem]) VALUES (1, N'名师', 1)
INSERT [dbo].[Jitar_UserType] ([TypeId], [TypeName], [IsSystem]) VALUES (2, N'推荐', 1)
INSERT [dbo].[Jitar_UserType] ([TypeId], [TypeName], [IsSystem]) VALUES (3, N'学科带头人', 1)
INSERT [dbo].[Jitar_UserType] ([TypeId], [TypeName], [IsSystem]) VALUES (4, N'教研员', 1)
INSERT [dbo].[Jitar_UserType] ([TypeId], [TypeName], [IsSystem]) VALUES (5, N'研修之星', 1)
INSERT [dbo].[Jitar_UserType] ([TypeId], [TypeName], [IsSystem]) VALUES (6, N'教师风采', 1)
SET IDENTITY_INSERT [dbo].[Jitar_UserType] OFF

GO
DELETE FROM [dbo].[S_SubjectWebpart]
GO

/****** Object:  Table [dbo].[U_Favorites]    Script Date: 08/30/2013 15:35:34 ******/
/****** Object:  Table [dbo].[S_SubjectWebpart]    Script Date: 08/30/2013 15:35:34 ******/
SET IDENTITY_INSERT [dbo].[S_SubjectWebpart] ON
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (1, N'图片新闻', N'图片新闻', 1, 1, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (2, N'教研员工作室', N'教研员工作室', 1, 1, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (3, N'名师工作室', N'名师工作室', 1, 1, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (4, N'学科带头人', N'学科带头人', 1, 1, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (5, N'友情链接', N'友情链接', 1, 1, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (6, N'学科统计', N'学科统计', 1, 1, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (7, N'学科文章', N'学科文章', 1, 1, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (8, N'学科资源', N'学科资源', 1, 1, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (9, N'工作室', N'工作室', 1, 1, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (10, N'协作组', N'协作组', 1, 1, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (11, N'教研视频', N'教研视频', 1, 1, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (12, N'提问与解答', N'提问与解答', 1, 1, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (13, N'学科动态', N'学科动态', 1, 1, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (14, N'学科公告', N'学科公告', 1, 1, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (15, N'调查投票', N'调查投票', 1, 1, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (16, N'教研活动', N'教研活动', 1, 1, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (17, N'教研专题', N'教研专题', 1, 1, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (18, N'话题讨论', N'话题讨论', 1, 1, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (19, N'图片新闻', N'图片新闻', 1, 2, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (20, N'教研员工作室', N'教研员工作室', 1, 2, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (21, N'名师工作室', N'名师工作室', 1, 2, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (22, N'学科带头人', N'学科带头人', 1, 2, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (23, N'友情链接', N'友情链接', 1, 2, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (24, N'学科统计', N'学科统计', 1, 2, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (25, N'学科文章', N'学科文章', 1, 2, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (26, N'学科资源', N'学科资源', 1, 2, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (27, N'工作室', N'工作室', 1, 2, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (28, N'协作组', N'协作组', 1, 2, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (29, N'教研视频', N'教研视频', 1, 2, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (30, N'提问与解答', N'提问与解答', 1, 2, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (31, N'学科动态', N'学科动态', 1, 2, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (32, N'学科公告', N'学科公告', 1, 2, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (33, N'调查投票', N'调查投票', 1, 2, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (34, N'教研活动', N'教研活动', 1, 2, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (35, N'教研专题', N'教研专题', 1, 2, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (36, N'话题讨论', N'话题讨论', 1, 2, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (37, N'图片新闻', N'图片新闻', 1, 3, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (38, N'教研员工作室', N'教研员工作室', 1, 3, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (39, N'名师工作室', N'名师工作室', 1, 3, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (40, N'学科带头人', N'学科带头人', 1, 3, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (41, N'友情链接', N'友情链接', 1, 3, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (42, N'学科统计', N'学科统计', 1, 3, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (43, N'学科文章', N'学科文章', 1, 3, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (44, N'学科资源', N'学科资源', 1, 3, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (45, N'工作室', N'工作室', 1, 3, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (46, N'协作组', N'协作组', 1, 3, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (47, N'教研视频', N'教研视频', 1, 3, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (48, N'提问与解答', N'提问与解答', 1, 3, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (49, N'学科动态', N'学科动态', 1, 3, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (50, N'学科公告', N'学科公告', 1, 3, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (51, N'调查投票', N'调查投票', 1, 3, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (52, N'教研活动', N'教研活动', 1, 3, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (53, N'教研专题', N'教研专题', 1, 3, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (54, N'话题讨论', N'话题讨论', 1, 3, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (55, N'图片新闻', N'图片新闻', 1, 4, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (56, N'教研员工作室', N'教研员工作室', 1, 4, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (57, N'名师工作室', N'名师工作室', 1, 4, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (58, N'学科带头人', N'学科带头人', 1, 4, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (59, N'友情链接', N'友情链接', 1, 4, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (60, N'学科统计', N'学科统计', 1, 4, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (61, N'学科文章', N'学科文章', 1, 4, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (62, N'学科资源', N'学科资源', 1, 4, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (63, N'工作室', N'工作室', 1, 4, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (64, N'协作组', N'协作组', 1, 4, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (65, N'教研视频', N'教研视频', 1, 4, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (66, N'提问与解答', N'提问与解答', 1, 4, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (67, N'学科动态', N'学科动态', 1, 4, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (68, N'学科公告', N'学科公告', 1, 4, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (69, N'调查投票', N'调查投票', 1, 4, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (70, N'教研活动', N'教研活动', 1, 4, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (71, N'教研专题', N'教研专题', 1, 4, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (72, N'话题讨论', N'话题讨论', 1, 4, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (73, N'图片新闻', N'图片新闻', 1, 5, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (74, N'教研员工作室', N'教研员工作室', 1, 5, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (75, N'名师工作室', N'名师工作室', 1, 5, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (76, N'学科带头人', N'学科带头人', 1, 5, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (77, N'友情链接', N'友情链接', 1, 5, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (78, N'学科统计', N'学科统计', 1, 5, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (79, N'学科文章', N'学科文章', 1, 5, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (80, N'学科资源', N'学科资源', 1, 5, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (81, N'工作室', N'工作室', 1, 5, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (82, N'协作组', N'协作组', 1, 5, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (83, N'教研视频', N'教研视频', 1, 5, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (84, N'提问与解答', N'提问与解答', 1, 5, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (85, N'学科动态', N'学科动态', 1, 5, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (86, N'学科公告', N'学科公告', 1, 5, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (87, N'调查投票', N'调查投票', 1, 5, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (88, N'教研活动', N'教研活动', 1, 5, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (89, N'教研专题', N'教研专题', 1, 5, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (90, N'话题讨论', N'话题讨论', 1, 5, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (91, N'图片新闻', N'图片新闻', 1, 6, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (92, N'教研员工作室', N'教研员工作室', 1, 6, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (93, N'名师工作室', N'名师工作室', 1, 6, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (94, N'学科带头人', N'学科带头人', 1, 6, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (95, N'友情链接', N'友情链接', 1, 6, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (96, N'学科统计', N'学科统计', 1, 6, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (97, N'学科文章', N'学科文章', 1, 6, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (98, N'学科资源', N'学科资源', 1, 6, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (99, N'工作室', N'工作室', 1, 6, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (100, N'协作组', N'协作组', 1, 6, 4, 10, NULL, 1, NULL, NULL, 0, 0)
GO
print 'Processed 100 total records'
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (101, N'教研视频', N'教研视频', 1, 6, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (102, N'提问与解答', N'提问与解答', 1, 6, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (103, N'学科动态', N'学科动态', 1, 6, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (104, N'学科公告', N'学科公告', 1, 6, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (105, N'调查投票', N'调查投票', 1, 6, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (106, N'教研活动', N'教研活动', 1, 6, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (107, N'教研专题', N'教研专题', 1, 6, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (108, N'话题讨论', N'话题讨论', 1, 6, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (109, N'图片新闻', N'图片新闻', 1, 7, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (110, N'教研员工作室', N'教研员工作室', 1, 7, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (111, N'名师工作室', N'名师工作室', 1, 7, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (112, N'学科带头人', N'学科带头人', 1, 7, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (113, N'友情链接', N'友情链接', 1, 7, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (114, N'学科统计', N'学科统计', 1, 7, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (115, N'学科文章', N'学科文章', 1, 7, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (116, N'学科资源', N'学科资源', 1, 7, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (117, N'工作室', N'工作室', 1, 7, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (118, N'协作组', N'协作组', 1, 7, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (119, N'教研视频', N'教研视频', 1, 7, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (120, N'提问与解答', N'提问与解答', 1, 7, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (121, N'学科动态', N'学科动态', 1, 7, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (122, N'学科公告', N'学科公告', 1, 7, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (123, N'调查投票', N'调查投票', 1, 7, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (124, N'教研活动', N'教研活动', 1, 7, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (125, N'教研专题', N'教研专题', 1, 7, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (126, N'话题讨论', N'话题讨论', 1, 7, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (127, N'图片新闻', N'图片新闻', 1, 8, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (128, N'教研员工作室', N'教研员工作室', 1, 8, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (129, N'名师工作室', N'名师工作室', 1, 8, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (130, N'学科带头人', N'学科带头人', 1, 8, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (131, N'友情链接', N'友情链接', 1, 8, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (132, N'学科统计', N'学科统计', 1, 8, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (133, N'学科文章', N'学科文章', 1, 8, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (134, N'学科资源', N'学科资源', 1, 8, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (135, N'工作室', N'工作室', 1, 8, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (136, N'协作组', N'协作组', 1, 8, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (137, N'教研视频', N'教研视频', 1, 8, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (138, N'提问与解答', N'提问与解答', 1, 8, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (139, N'学科动态', N'学科动态', 1, 8, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (140, N'学科公告', N'学科公告', 1, 8, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (141, N'调查投票', N'调查投票', 1, 8, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (142, N'教研活动', N'教研活动', 1, 8, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (143, N'教研专题', N'教研专题', 1, 8, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (144, N'话题讨论', N'话题讨论', 1, 8, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (145, N'图片新闻', N'图片新闻', 1, 9, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (146, N'教研员工作室', N'教研员工作室', 1, 9, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (147, N'名师工作室', N'名师工作室', 1, 9, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (148, N'学科带头人', N'学科带头人', 1, 9, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (149, N'友情链接', N'友情链接', 1, 9, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (150, N'学科统计', N'学科统计', 1, 9, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (151, N'学科文章', N'学科文章', 1, 9, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (152, N'学科资源', N'学科资源', 1, 9, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (153, N'工作室', N'工作室', 1, 9, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (154, N'协作组', N'协作组', 1, 9, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (155, N'教研视频', N'教研视频', 1, 9, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (156, N'提问与解答', N'提问与解答', 1, 9, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (157, N'学科动态', N'学科动态', 1, 9, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (158, N'学科公告', N'学科公告', 1, 9, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (159, N'调查投票', N'调查投票', 1, 9, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (160, N'教研活动', N'教研活动', 1, 9, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (161, N'教研专题', N'教研专题', 1, 9, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (162, N'话题讨论', N'话题讨论', 1, 9, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (163, N'图片新闻', N'图片新闻', 1, 10, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (164, N'教研员工作室', N'教研员工作室', 1, 10, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (165, N'名师工作室', N'名师工作室', 1, 10, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (166, N'学科带头人', N'学科带头人', 1, 10, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (167, N'友情链接', N'友情链接', 1, 10, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (168, N'学科统计', N'学科统计', 1, 10, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (169, N'学科文章', N'学科文章', 1, 10, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (170, N'学科资源', N'学科资源', 1, 10, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (171, N'工作室', N'工作室', 1, 10, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (172, N'协作组', N'协作组', 1, 10, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (173, N'教研视频', N'教研视频', 1, 10, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (174, N'提问与解答', N'提问与解答', 1, 10, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (175, N'学科动态', N'学科动态', 1, 10, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (176, N'学科公告', N'学科公告', 1, 10, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (177, N'调查投票', N'调查投票', 1, 10, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (178, N'教研活动', N'教研活动', 1, 10, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (179, N'教研专题', N'教研专题', 1, 10, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (180, N'话题讨论', N'话题讨论', 1, 10, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (181, N'图片新闻', N'图片新闻', 1, 11, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (182, N'教研员工作室', N'教研员工作室', 1, 11, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (183, N'名师工作室', N'名师工作室', 1, 11, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (184, N'学科带头人', N'学科带头人', 1, 11, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (185, N'友情链接', N'友情链接', 1, 11, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (186, N'学科统计', N'学科统计', 1, 11, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (187, N'学科文章', N'学科文章', 1, 11, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (188, N'学科资源', N'学科资源', 1, 11, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (189, N'工作室', N'工作室', 1, 11, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (190, N'协作组', N'协作组', 1, 11, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (191, N'教研视频', N'教研视频', 1, 11, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (192, N'提问与解答', N'提问与解答', 1, 11, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (193, N'学科动态', N'学科动态', 1, 11, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (194, N'学科公告', N'学科公告', 1, 11, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (195, N'调查投票', N'调查投票', 1, 11, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (196, N'教研活动', N'教研活动', 1, 11, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (197, N'教研专题', N'教研专题', 1, 11, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (198, N'话题讨论', N'话题讨论', 1, 11, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (199, N'图片新闻', N'图片新闻', 1, 12, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (200, N'教研员工作室', N'教研员工作室', 1, 12, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (201, N'名师工作室', N'名师工作室', 1, 12, 3, 3, NULL, 1, NULL, NULL, 0, 0)
GO
print 'Processed 200 total records'
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (202, N'学科带头人', N'学科带头人', 1, 12, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (203, N'友情链接', N'友情链接', 1, 12, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (204, N'学科统计', N'学科统计', 1, 12, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (205, N'学科文章', N'学科文章', 1, 12, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (206, N'学科资源', N'学科资源', 1, 12, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (207, N'工作室', N'工作室', 1, 12, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (208, N'协作组', N'协作组', 1, 12, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (209, N'教研视频', N'教研视频', 1, 12, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (210, N'提问与解答', N'提问与解答', 1, 12, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (211, N'学科动态', N'学科动态', 1, 12, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (212, N'学科公告', N'学科公告', 1, 12, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (213, N'调查投票', N'调查投票', 1, 12, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (214, N'教研活动', N'教研活动', 1, 12, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (215, N'教研专题', N'教研专题', 1, 12, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (216, N'话题讨论', N'话题讨论', 1, 12, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (217, N'图片新闻', N'图片新闻', 1, 13, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (218, N'教研员工作室', N'教研员工作室', 1, 13, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (219, N'名师工作室', N'名师工作室', 1, 13, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (220, N'学科带头人', N'学科带头人', 1, 13, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (221, N'友情链接', N'友情链接', 1, 13, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (222, N'学科统计', N'学科统计', 1, 13, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (223, N'学科文章', N'学科文章', 1, 13, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (224, N'学科资源', N'学科资源', 1, 13, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (225, N'工作室', N'工作室', 1, 13, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (226, N'协作组', N'协作组', 1, 13, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (227, N'教研视频', N'教研视频', 1, 13, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (228, N'提问与解答', N'提问与解答', 1, 13, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (229, N'学科动态', N'学科动态', 1, 13, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (230, N'学科公告', N'学科公告', 1, 13, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (231, N'调查投票', N'调查投票', 1, 13, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (232, N'教研活动', N'教研活动', 1, 13, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (233, N'教研专题', N'教研专题', 1, 13, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (234, N'话题讨论', N'话题讨论', 1, 13, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (235, N'图片新闻', N'图片新闻', 1, 14, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (236, N'教研员工作室', N'教研员工作室', 1, 14, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (237, N'名师工作室', N'名师工作室', 1, 14, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (238, N'学科带头人', N'学科带头人', 1, 14, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (239, N'友情链接', N'友情链接', 1, 14, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (240, N'学科统计', N'学科统计', 1, 14, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (241, N'学科文章', N'学科文章', 1, 14, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (242, N'学科资源', N'学科资源', 1, 14, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (243, N'工作室', N'工作室', 1, 14, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (244, N'协作组', N'协作组', 1, 14, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (245, N'教研视频', N'教研视频', 1, 14, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (246, N'提问与解答', N'提问与解答', 1, 14, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (247, N'学科动态', N'学科动态', 1, 14, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (248, N'学科公告', N'学科公告', 1, 14, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (249, N'调查投票', N'调查投票', 1, 14, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (250, N'教研活动', N'教研活动', 1, 14, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (251, N'教研专题', N'教研专题', 1, 14, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (252, N'话题讨论', N'话题讨论', 1, 14, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (253, N'图片新闻', N'图片新闻', 1, 15, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (254, N'教研员工作室', N'教研员工作室', 1, 15, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (255, N'名师工作室', N'名师工作室', 1, 15, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (256, N'学科带头人', N'学科带头人', 1, 15, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (257, N'友情链接', N'友情链接', 1, 15, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (258, N'学科统计', N'学科统计', 1, 15, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (259, N'学科文章', N'学科文章', 1, 15, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (260, N'学科资源', N'学科资源', 1, 15, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (261, N'工作室', N'工作室', 1, 15, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (262, N'协作组', N'协作组', 1, 15, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (263, N'教研视频', N'教研视频', 1, 15, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (264, N'提问与解答', N'提问与解答', 1, 15, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (265, N'学科动态', N'学科动态', 1, 15, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (266, N'学科公告', N'学科公告', 1, 15, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (267, N'调查投票', N'调查投票', 1, 15, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (268, N'教研活动', N'教研活动', 1, 15, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (269, N'教研专题', N'教研专题', 1, 15, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (270, N'话题讨论', N'话题讨论', 1, 15, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (271, N'图片新闻', N'图片新闻', 1, 16, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (272, N'教研员工作室', N'教研员工作室', 1, 16, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (273, N'名师工作室', N'名师工作室', 1, 16, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (274, N'学科带头人', N'学科带头人', 1, 16, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (275, N'友情链接', N'友情链接', 1, 16, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (276, N'学科统计', N'学科统计', 1, 16, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (277, N'学科文章', N'学科文章', 1, 16, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (278, N'学科资源', N'学科资源', 1, 16, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (279, N'工作室', N'工作室', 1, 16, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (280, N'协作组', N'协作组', 1, 16, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (281, N'教研视频', N'教研视频', 1, 16, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (282, N'提问与解答', N'提问与解答', 1, 16, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (283, N'学科动态', N'学科动态', 1, 16, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (284, N'学科公告', N'学科公告', 1, 16, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (285, N'调查投票', N'调查投票', 1, 16, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (286, N'教研活动', N'教研活动', 1, 16, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (287, N'教研专题', N'教研专题', 1, 16, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (288, N'话题讨论', N'话题讨论', 1, 16, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (289, N'图片新闻', N'图片新闻', 1, 17, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (290, N'教研员工作室', N'教研员工作室', 1, 17, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (291, N'名师工作室', N'名师工作室', 1, 17, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (292, N'学科带头人', N'学科带头人', 1, 17, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (293, N'友情链接', N'友情链接', 1, 17, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (294, N'学科统计', N'学科统计', 1, 17, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (295, N'学科文章', N'学科文章', 1, 17, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (296, N'学科资源', N'学科资源', 1, 17, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (297, N'工作室', N'工作室', 1, 17, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (298, N'协作组', N'协作组', 1, 17, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (299, N'教研视频', N'教研视频', 1, 17, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (300, N'提问与解答', N'提问与解答', 1, 17, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (301, N'学科动态', N'学科动态', 1, 17, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (302, N'学科公告', N'学科公告', 1, 17, 5, 14, NULL, 1, NULL, NULL, 0, 0)
GO
print 'Processed 300 total records'
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (303, N'调查投票', N'调查投票', 1, 17, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (304, N'教研活动', N'教研活动', 1, 17, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (305, N'教研专题', N'教研专题', 1, 17, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (306, N'话题讨论', N'话题讨论', 1, 17, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (307, N'图片新闻', N'图片新闻', 1, 18, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (308, N'教研员工作室', N'教研员工作室', 1, 18, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (309, N'名师工作室', N'名师工作室', 1, 18, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (310, N'学科带头人', N'学科带头人', 1, 18, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (311, N'友情链接', N'友情链接', 1, 18, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (312, N'学科统计', N'学科统计', 1, 18, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (313, N'学科文章', N'学科文章', 1, 18, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (314, N'学科资源', N'学科资源', 1, 18, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (315, N'工作室', N'工作室', 1, 18, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (316, N'协作组', N'协作组', 1, 18, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (317, N'教研视频', N'教研视频', 1, 18, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (318, N'提问与解答', N'提问与解答', 1, 18, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (319, N'学科动态', N'学科动态', 1, 18, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (320, N'学科公告', N'学科公告', 1, 18, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (321, N'调查投票', N'调查投票', 1, 18, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (322, N'教研活动', N'教研活动', 1, 18, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (323, N'教研专题', N'教研专题', 1, 18, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (324, N'话题讨论', N'话题讨论', 1, 18, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (325, N'图片新闻', N'图片新闻', 1, 19, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (326, N'教研员工作室', N'教研员工作室', 1, 19, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (327, N'名师工作室', N'名师工作室', 1, 19, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (328, N'学科带头人', N'学科带头人', 1, 19, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (329, N'友情链接', N'友情链接', 1, 19, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (330, N'学科统计', N'学科统计', 1, 19, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (331, N'学科文章', N'学科文章', 1, 19, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (332, N'学科资源', N'学科资源', 1, 19, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (333, N'工作室', N'工作室', 1, 19, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (334, N'协作组', N'协作组', 1, 19, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (335, N'教研视频', N'教研视频', 1, 19, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (336, N'提问与解答', N'提问与解答', 1, 19, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (337, N'学科动态', N'学科动态', 1, 19, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (338, N'学科公告', N'学科公告', 1, 19, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (339, N'调查投票', N'调查投票', 1, 19, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (340, N'教研活动', N'教研活动', 1, 19, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (341, N'教研专题', N'教研专题', 1, 19, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (342, N'话题讨论', N'话题讨论', 1, 19, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (343, N'图片新闻', N'图片新闻', 1, 20, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (344, N'教研员工作室', N'教研员工作室', 1, 20, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (345, N'名师工作室', N'名师工作室', 1, 20, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (346, N'学科带头人', N'学科带头人', 1, 20, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (347, N'友情链接', N'友情链接', 1, 20, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (348, N'学科统计', N'学科统计', 1, 20, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (349, N'学科文章', N'学科文章', 1, 20, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (350, N'学科资源', N'学科资源', 1, 20, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (351, N'工作室', N'工作室', 1, 20, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (352, N'协作组', N'协作组', 1, 20, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (353, N'教研视频', N'教研视频', 1, 20, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (354, N'提问与解答', N'提问与解答', 1, 20, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (355, N'学科动态', N'学科动态', 1, 20, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (356, N'学科公告', N'学科公告', 1, 20, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (357, N'调查投票', N'调查投票', 1, 20, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (358, N'教研活动', N'教研活动', 1, 20, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (359, N'教研专题', N'教研专题', 1, 20, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (360, N'话题讨论', N'话题讨论', 1, 20, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (361, N'图片新闻', N'图片新闻', 1, 21, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (362, N'教研员工作室', N'教研员工作室', 1, 21, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (363, N'名师工作室', N'名师工作室', 1, 21, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (364, N'学科带头人', N'学科带头人', 1, 21, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (365, N'友情链接', N'友情链接', 1, 21, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (366, N'学科统计', N'学科统计', 1, 21, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (367, N'学科文章', N'学科文章', 1, 21, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (368, N'学科资源', N'学科资源', 1, 21, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (369, N'工作室', N'工作室', 1, 21, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (370, N'协作组', N'协作组', 1, 21, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (371, N'教研视频', N'教研视频', 1, 21, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (372, N'提问与解答', N'提问与解答', 1, 21, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (373, N'学科动态', N'学科动态', 1, 21, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (374, N'学科公告', N'学科公告', 1, 21, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (375, N'调查投票', N'调查投票', 1, 21, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (376, N'教研活动', N'教研活动', 1, 21, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (377, N'教研专题', N'教研专题', 1, 21, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (378, N'话题讨论', N'话题讨论', 1, 21, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (379, N'图片新闻', N'图片新闻', 1, 22, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (380, N'教研员工作室', N'教研员工作室', 1, 22, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (381, N'名师工作室', N'名师工作室', 1, 22, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (382, N'学科带头人', N'学科带头人', 1, 22, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (383, N'友情链接', N'友情链接', 1, 22, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (384, N'学科统计', N'学科统计', 1, 22, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (385, N'学科文章', N'学科文章', 1, 22, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (386, N'学科资源', N'学科资源', 1, 22, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (387, N'工作室', N'工作室', 1, 22, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (388, N'协作组', N'协作组', 1, 22, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (389, N'教研视频', N'教研视频', 1, 22, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (390, N'提问与解答', N'提问与解答', 1, 22, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (391, N'学科动态', N'学科动态', 1, 22, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (392, N'学科公告', N'学科公告', 1, 22, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (393, N'调查投票', N'调查投票', 1, 22, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (394, N'教研活动', N'教研活动', 1, 22, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (395, N'教研专题', N'教研专题', 1, 22, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (396, N'话题讨论', N'话题讨论', 1, 22, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (397, N'图片新闻', N'图片新闻', 1, 23, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (398, N'教研员工作室', N'教研员工作室', 1, 23, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (399, N'名师工作室', N'名师工作室', 1, 23, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (400, N'学科带头人', N'学科带头人', 1, 23, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (401, N'友情链接', N'友情链接', 1, 23, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (402, N'学科统计', N'学科统计', 1, 23, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (403, N'学科文章', N'学科文章', 1, 23, 4, 7, NULL, 1, NULL, NULL, 0, 0)
GO
print 'Processed 400 total records'
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (404, N'学科资源', N'学科资源', 1, 23, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (405, N'工作室', N'工作室', 1, 23, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (406, N'协作组', N'协作组', 1, 23, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (407, N'教研视频', N'教研视频', 1, 23, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (408, N'提问与解答', N'提问与解答', 1, 23, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (409, N'学科动态', N'学科动态', 1, 23, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (410, N'学科公告', N'学科公告', 1, 23, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (411, N'调查投票', N'调查投票', 1, 23, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (412, N'教研活动', N'教研活动', 1, 23, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (413, N'教研专题', N'教研专题', 1, 23, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (414, N'话题讨论', N'话题讨论', 1, 23, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (415, N'图片新闻', N'图片新闻', 1, 24, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (416, N'教研员工作室', N'教研员工作室', 1, 24, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (417, N'名师工作室', N'名师工作室', 1, 24, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (418, N'学科带头人', N'学科带头人', 1, 24, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (419, N'友情链接', N'友情链接', 1, 24, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (420, N'学科统计', N'学科统计', 1, 24, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (421, N'学科文章', N'学科文章', 1, 24, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (422, N'学科资源', N'学科资源', 1, 24, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (423, N'工作室', N'工作室', 1, 24, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (424, N'协作组', N'协作组', 1, 24, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (425, N'教研视频', N'教研视频', 1, 24, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (426, N'提问与解答', N'提问与解答', 1, 24, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (427, N'学科动态', N'学科动态', 1, 24, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (428, N'学科公告', N'学科公告', 1, 24, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (429, N'调查投票', N'调查投票', 1, 24, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (430, N'教研活动', N'教研活动', 1, 24, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (431, N'教研专题', N'教研专题', 1, 24, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (432, N'话题讨论', N'话题讨论', 1, 24, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (433, N'图片新闻', N'图片新闻', 1, 25, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (434, N'教研员工作室', N'教研员工作室', 1, 25, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (435, N'名师工作室', N'名师工作室', 1, 25, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (436, N'学科带头人', N'学科带头人', 1, 25, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (437, N'友情链接', N'友情链接', 1, 25, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (438, N'学科统计', N'学科统计', 1, 25, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (439, N'学科文章', N'学科文章', 1, 25, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (440, N'学科资源', N'学科资源', 1, 25, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (441, N'工作室', N'工作室', 1, 25, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (442, N'协作组', N'协作组', 1, 25, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (443, N'教研视频', N'教研视频', 1, 25, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (444, N'提问与解答', N'提问与解答', 1, 25, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (445, N'学科动态', N'学科动态', 1, 25, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (446, N'学科公告', N'学科公告', 1, 25, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (447, N'调查投票', N'调查投票', 1, 25, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (448, N'教研活动', N'教研活动', 1, 25, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (449, N'教研专题', N'教研专题', 1, 25, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (450, N'话题讨论', N'话题讨论', 1, 25, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (451, N'图片新闻', N'图片新闻', 1, 26, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (452, N'教研员工作室', N'教研员工作室', 1, 26, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (453, N'名师工作室', N'名师工作室', 1, 26, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (454, N'学科带头人', N'学科带头人', 1, 26, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (455, N'友情链接', N'友情链接', 1, 26, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (456, N'学科统计', N'学科统计', 1, 26, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (457, N'学科文章', N'学科文章', 1, 26, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (458, N'学科资源', N'学科资源', 1, 26, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (459, N'工作室', N'工作室', 1, 26, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (460, N'协作组', N'协作组', 1, 26, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (461, N'教研视频', N'教研视频', 1, 26, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (462, N'提问与解答', N'提问与解答', 1, 26, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (463, N'学科动态', N'学科动态', 1, 26, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (464, N'学科公告', N'学科公告', 1, 26, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (465, N'调查投票', N'调查投票', 1, 26, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (466, N'教研活动', N'教研活动', 1, 26, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (467, N'教研专题', N'教研专题', 1, 26, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (468, N'话题讨论', N'话题讨论', 1, 26, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (469, N'图片新闻', N'图片新闻', 1, 27, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (470, N'教研员工作室', N'教研员工作室', 1, 27, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (471, N'名师工作室', N'名师工作室', 1, 27, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (472, N'学科带头人', N'学科带头人', 1, 27, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (473, N'友情链接', N'友情链接', 1, 27, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (474, N'学科统计', N'学科统计', 1, 27, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (475, N'学科文章', N'学科文章', 1, 27, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (476, N'学科资源', N'学科资源', 1, 27, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (477, N'工作室', N'工作室', 1, 27, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (478, N'协作组', N'协作组', 1, 27, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (479, N'教研视频', N'教研视频', 1, 27, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (480, N'提问与解答', N'提问与解答', 1, 27, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (481, N'学科动态', N'学科动态', 1, 27, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (482, N'学科公告', N'学科公告', 1, 27, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (483, N'调查投票', N'调查投票', 1, 27, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (484, N'教研活动', N'教研活动', 1, 27, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (485, N'教研专题', N'教研专题', 1, 27, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (486, N'话题讨论', N'话题讨论', 1, 27, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (487, N'图片新闻', N'图片新闻', 1, 28, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (488, N'教研员工作室', N'教研员工作室', 1, 28, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (489, N'名师工作室', N'名师工作室', 1, 28, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (490, N'学科带头人', N'学科带头人', 1, 28, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (491, N'友情链接', N'友情链接', 1, 28, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (492, N'学科统计', N'学科统计', 1, 28, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (493, N'学科文章', N'学科文章', 1, 28, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (494, N'学科资源', N'学科资源', 1, 28, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (495, N'工作室', N'工作室', 1, 28, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (496, N'协作组', N'协作组', 1, 28, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (497, N'教研视频', N'教研视频', 1, 28, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (498, N'提问与解答', N'提问与解答', 1, 28, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (499, N'学科动态', N'学科动态', 1, 28, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (500, N'学科公告', N'学科公告', 1, 28, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (501, N'调查投票', N'调查投票', 1, 28, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (502, N'教研活动', N'教研活动', 1, 28, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (503, N'教研专题', N'教研专题', 1, 28, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (504, N'话题讨论', N'话题讨论', 1, 28, 5, 18, NULL, 1, NULL, NULL, 0, 0)
GO
print 'Processed 500 total records'
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (505, N'图片新闻', N'图片新闻', 1, 29, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (506, N'教研员工作室', N'教研员工作室', 1, 29, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (507, N'名师工作室', N'名师工作室', 1, 29, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (508, N'学科带头人', N'学科带头人', 1, 29, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (509, N'友情链接', N'友情链接', 1, 29, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (510, N'学科统计', N'学科统计', 1, 29, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (511, N'学科文章', N'学科文章', 1, 29, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (512, N'学科资源', N'学科资源', 1, 29, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (513, N'工作室', N'工作室', 1, 29, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (514, N'协作组', N'协作组', 1, 29, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (515, N'教研视频', N'教研视频', 1, 29, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (516, N'提问与解答', N'提问与解答', 1, 29, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (517, N'学科动态', N'学科动态', 1, 29, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (518, N'学科公告', N'学科公告', 1, 29, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (519, N'调查投票', N'调查投票', 1, 29, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (520, N'教研活动', N'教研活动', 1, 29, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (521, N'教研专题', N'教研专题', 1, 29, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (522, N'话题讨论', N'话题讨论', 1, 29, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (523, N'图片新闻', N'图片新闻', 1, 30, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (524, N'教研员工作室', N'教研员工作室', 1, 30, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (525, N'名师工作室', N'名师工作室', 1, 30, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (526, N'学科带头人', N'学科带头人', 1, 30, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (527, N'友情链接', N'友情链接', 1, 30, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (528, N'学科统计', N'学科统计', 1, 30, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (529, N'学科文章', N'学科文章', 1, 30, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (530, N'学科资源', N'学科资源', 1, 30, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (531, N'工作室', N'工作室', 1, 30, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (532, N'协作组', N'协作组', 1, 30, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (533, N'教研视频', N'教研视频', 1, 30, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (534, N'提问与解答', N'提问与解答', 1, 30, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (535, N'学科动态', N'学科动态', 1, 30, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (536, N'学科公告', N'学科公告', 1, 30, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (537, N'调查投票', N'调查投票', 1, 30, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (538, N'教研活动', N'教研活动', 1, 30, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (539, N'教研专题', N'教研专题', 1, 30, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (540, N'话题讨论', N'话题讨论', 1, 30, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (541, N'图片新闻', N'图片新闻', 1, 31, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (542, N'教研员工作室', N'教研员工作室', 1, 31, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (543, N'名师工作室', N'名师工作室', 1, 31, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (544, N'学科带头人', N'学科带头人', 1, 31, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (545, N'友情链接', N'友情链接', 1, 31, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (546, N'学科统计', N'学科统计', 1, 31, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (547, N'学科文章', N'学科文章', 1, 31, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (548, N'学科资源', N'学科资源', 1, 31, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (549, N'工作室', N'工作室', 1, 31, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (550, N'协作组', N'协作组', 1, 31, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (551, N'教研视频', N'教研视频', 1, 31, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (552, N'提问与解答', N'提问与解答', 1, 31, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (553, N'学科动态', N'学科动态', 1, 31, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (554, N'学科公告', N'学科公告', 1, 31, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (555, N'调查投票', N'调查投票', 1, 31, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (556, N'教研活动', N'教研活动', 1, 31, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (557, N'教研专题', N'教研专题', 1, 31, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (558, N'话题讨论', N'话题讨论', 1, 31, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (559, N'图片新闻', N'图片新闻', 1, 32, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (560, N'教研员工作室', N'教研员工作室', 1, 32, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (561, N'名师工作室', N'名师工作室', 1, 32, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (562, N'学科带头人', N'学科带头人', 1, 32, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (563, N'友情链接', N'友情链接', 1, 32, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (564, N'学科统计', N'学科统计', 1, 32, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (565, N'学科文章', N'学科文章', 1, 32, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (566, N'学科资源', N'学科资源', 1, 32, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (567, N'工作室', N'工作室', 1, 32, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (568, N'协作组', N'协作组', 1, 32, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (569, N'教研视频', N'教研视频', 1, 32, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (570, N'提问与解答', N'提问与解答', 1, 32, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (571, N'学科动态', N'学科动态', 1, 32, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (572, N'学科公告', N'学科公告', 1, 32, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (573, N'调查投票', N'调查投票', 1, 32, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (574, N'教研活动', N'教研活动', 1, 32, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (575, N'教研专题', N'教研专题', 1, 32, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (576, N'话题讨论', N'话题讨论', 1, 32, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (577, N'图片新闻', N'图片新闻', 1, 33, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (578, N'教研员工作室', N'教研员工作室', 1, 33, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (579, N'名师工作室', N'名师工作室', 1, 33, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (580, N'学科带头人', N'学科带头人', 1, 33, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (581, N'友情链接', N'友情链接', 1, 33, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (582, N'学科统计', N'学科统计', 1, 33, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (583, N'学科文章', N'学科文章', 1, 33, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (584, N'学科资源', N'学科资源', 1, 33, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (585, N'工作室', N'工作室', 1, 33, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (586, N'协作组', N'协作组', 1, 33, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (587, N'教研视频', N'教研视频', 1, 33, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (588, N'提问与解答', N'提问与解答', 1, 33, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (589, N'学科动态', N'学科动态', 1, 33, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (590, N'学科公告', N'学科公告', 1, 33, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (591, N'调查投票', N'调查投票', 1, 33, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (592, N'教研活动', N'教研活动', 1, 33, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (593, N'教研专题', N'教研专题', 1, 33, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (594, N'话题讨论', N'话题讨论', 1, 33, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (595, N'图片新闻', N'图片新闻', 1, 34, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (596, N'教研员工作室', N'教研员工作室', 1, 34, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (597, N'名师工作室', N'名师工作室', 1, 34, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (598, N'学科带头人', N'学科带头人', 1, 34, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (599, N'友情链接', N'友情链接', 1, 34, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (600, N'学科统计', N'学科统计', 1, 34, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (601, N'学科文章', N'学科文章', 1, 34, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (602, N'学科资源', N'学科资源', 1, 34, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (603, N'工作室', N'工作室', 1, 34, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (604, N'协作组', N'协作组', 1, 34, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (605, N'教研视频', N'教研视频', 1, 34, 4, 11, NULL, 1, NULL, NULL, 0, 0)
GO
print 'Processed 600 total records'
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (606, N'提问与解答', N'提问与解答', 1, 34, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (607, N'学科动态', N'学科动态', 1, 34, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (608, N'学科公告', N'学科公告', 1, 34, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (609, N'调查投票', N'调查投票', 1, 34, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (610, N'教研活动', N'教研活动', 1, 34, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (611, N'教研专题', N'教研专题', 1, 34, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (612, N'话题讨论', N'话题讨论', 1, 34, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (613, N'图片新闻', N'图片新闻', 1, 35, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (614, N'教研员工作室', N'教研员工作室', 1, 35, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (615, N'名师工作室', N'名师工作室', 1, 35, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (616, N'学科带头人', N'学科带头人', 1, 35, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (617, N'友情链接', N'友情链接', 1, 35, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (618, N'学科统计', N'学科统计', 1, 35, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (619, N'学科文章', N'学科文章', 1, 35, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (620, N'学科资源', N'学科资源', 1, 35, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (621, N'工作室', N'工作室', 1, 35, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (622, N'协作组', N'协作组', 1, 35, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (623, N'教研视频', N'教研视频', 1, 35, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (624, N'提问与解答', N'提问与解答', 1, 35, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (625, N'学科动态', N'学科动态', 1, 35, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (626, N'学科公告', N'学科公告', 1, 35, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (627, N'调查投票', N'调查投票', 1, 35, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (628, N'教研活动', N'教研活动', 1, 35, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (629, N'教研专题', N'教研专题', 1, 35, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (630, N'话题讨论', N'话题讨论', 1, 35, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (631, N'图片新闻', N'图片新闻', 1, 36, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (632, N'教研员工作室', N'教研员工作室', 1, 36, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (633, N'名师工作室', N'名师工作室', 1, 36, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (634, N'学科带头人', N'学科带头人', 1, 36, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (635, N'友情链接', N'友情链接', 1, 36, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (636, N'学科统计', N'学科统计', 1, 36, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (637, N'学科文章', N'学科文章', 1, 36, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (638, N'学科资源', N'学科资源', 1, 36, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (639, N'工作室', N'工作室', 1, 36, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (640, N'协作组', N'协作组', 1, 36, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (641, N'教研视频', N'教研视频', 1, 36, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (642, N'提问与解答', N'提问与解答', 1, 36, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (643, N'学科动态', N'学科动态', 1, 36, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (644, N'学科公告', N'学科公告', 1, 36, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (645, N'调查投票', N'调查投票', 1, 36, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (646, N'教研活动', N'教研活动', 1, 36, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (647, N'教研专题', N'教研专题', 1, 36, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (648, N'话题讨论', N'话题讨论', 1, 36, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (649, N'图片新闻', N'图片新闻', 1, 37, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (650, N'教研员工作室', N'教研员工作室', 1, 37, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (651, N'名师工作室', N'名师工作室', 1, 37, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (652, N'学科带头人', N'学科带头人', 1, 37, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (653, N'友情链接', N'友情链接', 1, 37, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (654, N'学科统计', N'学科统计', 1, 37, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (655, N'学科文章', N'学科文章', 1, 37, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (656, N'学科资源', N'学科资源', 1, 37, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (657, N'工作室', N'工作室', 1, 37, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (658, N'协作组', N'协作组', 1, 37, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (659, N'教研视频', N'教研视频', 1, 37, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (660, N'提问与解答', N'提问与解答', 1, 37, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (661, N'学科动态', N'学科动态', 1, 37, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (662, N'学科公告', N'学科公告', 1, 37, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (663, N'调查投票', N'调查投票', 1, 37, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (664, N'教研活动', N'教研活动', 1, 37, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (665, N'教研专题', N'教研专题', 1, 37, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (666, N'话题讨论', N'话题讨论', 1, 37, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (667, N'图片新闻', N'图片新闻', 1, 38, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (668, N'教研员工作室', N'教研员工作室', 1, 38, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (669, N'名师工作室', N'名师工作室', 1, 38, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (670, N'学科带头人', N'学科带头人', 1, 38, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (671, N'友情链接', N'友情链接', 1, 38, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (672, N'学科统计', N'学科统计', 1, 38, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (673, N'学科文章', N'学科文章', 1, 38, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (674, N'学科资源', N'学科资源', 1, 38, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (675, N'工作室', N'工作室', 1, 38, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (676, N'协作组', N'协作组', 1, 38, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (677, N'教研视频', N'教研视频', 1, 38, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (678, N'提问与解答', N'提问与解答', 1, 38, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (679, N'学科动态', N'学科动态', 1, 38, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (680, N'学科公告', N'学科公告', 1, 38, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (681, N'调查投票', N'调查投票', 1, 38, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (682, N'教研活动', N'教研活动', 1, 38, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (683, N'教研专题', N'教研专题', 1, 38, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (684, N'话题讨论', N'话题讨论', 1, 38, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (685, N'图片新闻', N'图片新闻', 1, 39, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (686, N'教研员工作室', N'教研员工作室', 1, 39, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (687, N'名师工作室', N'名师工作室', 1, 39, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (688, N'学科带头人', N'学科带头人', 1, 39, 3, 4, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (689, N'友情链接', N'友情链接', 1, 39, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (690, N'学科统计', N'学科统计', 1, 39, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (691, N'学科文章', N'学科文章', 1, 39, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (692, N'学科资源', N'学科资源', 1, 39, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (693, N'工作室', N'工作室', 1, 39, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (694, N'协作组', N'协作组', 1, 39, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (695, N'教研视频', N'教研视频', 1, 39, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (696, N'提问与解答', N'提问与解答', 1, 39, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (697, N'学科动态', N'学科动态', 1, 39, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (698, N'学科公告', N'学科公告', 1, 39, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (699, N'调查投票', N'调查投票', 1, 39, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (700, N'教研活动', N'教研活动', 1, 39, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (701, N'教研专题', N'教研专题', 1, 39, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (702, N'话题讨论', N'话题讨论', 1, 39, 5, 18, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (703, N'图片新闻', N'图片新闻', 1, 40, 3, 1, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (704, N'教研员工作室', N'教研员工作室', 1, 40, 3, 2, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (705, N'名师工作室', N'名师工作室', 1, 40, 3, 3, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (706, N'学科带头人', N'学科带头人', 1, 40, 3, 4, NULL, 1, NULL, NULL, 0, 0)
GO
print 'Processed 700 total records'
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (707, N'友情链接', N'友情链接', 1, 40, 3, 5, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (708, N'学科统计', N'学科统计', 1, 40, 3, 6, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (709, N'学科文章', N'学科文章', 1, 40, 4, 7, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (710, N'学科资源', N'学科资源', 1, 40, 4, 8, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (711, N'工作室', N'工作室', 1, 40, 4, 9, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (712, N'协作组', N'协作组', 1, 40, 4, 10, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (713, N'教研视频', N'教研视频', 1, 40, 4, 11, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (714, N'提问与解答', N'提问与解答', 1, 40, 4, 12, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (715, N'学科动态', N'学科动态', 1, 40, 5, 13, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (716, N'学科公告', N'学科公告', 1, 40, 5, 14, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (717, N'调查投票', N'调查投票', 1, 40, 5, 15, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (718, N'教研活动', N'教研活动', 1, 40, 5, 16, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (719, N'教研专题', N'教研专题', 1, 40, 5, 17, NULL, 1, NULL, NULL, 0, 0)
INSERT [dbo].[S_SubjectWebpart] ([SubjectWebpartId], [ModuleName], [DisplayName], [SystemModule], [SubjectId], [WebpartZone], [RowIndex], [Content], [Visible], [SysCateId], [ShowCount], [PartType], [ShowType]) VALUES (720, N'话题讨论', N'话题讨论', 1, 40, 5, 18, NULL, 1, NULL, NULL, 0, 0)
SET IDENTITY_INSERT [dbo].[S_SubjectWebpart] OFF

GO

/****** Object:  Table [dbo].[P_Question]    Script Date: 08/30/2013 15:35:34 ******/
/****** Object:  Table [dbo].[MetaSubject]    Script Date: 08/30/2013 15:35:34 ******/
SET IDENTITY_INSERT [dbo].[MetaSubject] ON
INSERT [dbo].[MetaSubject] ([MsubjId], [MsubjName], [MsubjCode], [OrderNum]) VALUES (1, N'语文', N'yuwen', 1)
INSERT [dbo].[MetaSubject] ([MsubjId], [MsubjName], [MsubjCode], [OrderNum]) VALUES (2, N'数学', N'shuxue', 2)
INSERT [dbo].[MetaSubject] ([MsubjId], [MsubjName], [MsubjCode], [OrderNum]) VALUES (3, N'英语', N'english', 3)
INSERT [dbo].[MetaSubject] ([MsubjId], [MsubjName], [MsubjCode], [OrderNum]) VALUES (4, N'物理', N'wuli', 4)
INSERT [dbo].[MetaSubject] ([MsubjId], [MsubjName], [MsubjCode], [OrderNum]) VALUES (5, N'化学', N'huaxue', 5)
INSERT [dbo].[MetaSubject] ([MsubjId], [MsubjName], [MsubjCode], [OrderNum]) VALUES (6, N'生物', N'shengwu', 6)
INSERT [dbo].[MetaSubject] ([MsubjId], [MsubjName], [MsubjCode], [OrderNum]) VALUES (7, N'历史', N'lishi', 7)
INSERT [dbo].[MetaSubject] ([MsubjId], [MsubjName], [MsubjCode], [OrderNum]) VALUES (8, N'地理', N'dili', 8)
INSERT [dbo].[MetaSubject] ([MsubjId], [MsubjName], [MsubjCode], [OrderNum]) VALUES (9, N'音乐', N'yinyue', 9)
INSERT [dbo].[MetaSubject] ([MsubjId], [MsubjName], [MsubjCode], [OrderNum]) VALUES (10, N'美术', N'meishu', 10)
INSERT [dbo].[MetaSubject] ([MsubjId], [MsubjName], [MsubjCode], [OrderNum]) VALUES (11, N'信息技术', N'xinxi', 11)
INSERT [dbo].[MetaSubject] ([MsubjId], [MsubjName], [MsubjCode], [OrderNum]) VALUES (12, N'思想政治', N'zhengzhi', 12)
INSERT [dbo].[MetaSubject] ([MsubjId], [MsubjName], [MsubjCode], [OrderNum]) VALUES (13, N'体育与健康', N'tiyu', 13)
INSERT [dbo].[MetaSubject] ([MsubjId], [MsubjName], [MsubjCode], [OrderNum]) VALUES (14, N'艺术', N'yishu', 14)
INSERT [dbo].[MetaSubject] ([MsubjId], [MsubjName], [MsubjCode], [OrderNum]) VALUES (15, N'技术', N'jishu', 15)
INSERT [dbo].[MetaSubject] ([MsubjId], [MsubjName], [MsubjCode], [OrderNum]) VALUES (16, N'科学', N'kexue', 16)
INSERT [dbo].[MetaSubject] ([MsubjId], [MsubjName], [MsubjCode], [OrderNum]) VALUES (17, N'思想品德', N'sxpd', 17)
INSERT [dbo].[MetaSubject] ([MsubjId], [MsubjName], [MsubjCode], [OrderNum]) VALUES (18, N'历史与社会', N'lsysh', 18)
INSERT [dbo].[MetaSubject] ([MsubjId], [MsubjName], [MsubjCode], [OrderNum]) VALUES (19, N'品德与生活', N'pdysh', 19)
INSERT [dbo].[MetaSubject] ([MsubjId], [MsubjName], [MsubjCode], [OrderNum]) VALUES (20, N'品德与社会', N'pdyshehui', 20)
SET IDENTITY_INSERT [dbo].[MetaSubject] OFF

GO
DELETE FROM [dbo].[Jtr_ResType]
GO

/****** Object:  Table [dbo].[Jtr_ResType]    Script Date: 08/30/2013 15:35:34 ******/
SET IDENTITY_INSERT [dbo].[Jtr_ResType] ON
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (1, N'媒体素材', CONVERT(TEXT, N'media material'), 0, 1, 0, N'媒体素材是传播教学信息的学习材料单元。')
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (2, N'文本类素材', CONVERT(TEXT, N'text'), 1, 1, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (3, N'图形（图像）类素材', CONVERT(TEXT, N'picture'), 1, 2, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (4, N'视频类素材', CONVERT(TEXT, N'video'), 1, 3, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (5, N'音频类素材', CONVERT(TEXT, N'audio'), 1, 4, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (6, N'动画类素材', CONVERT(TEXT, N'animation'), 1, 5, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (7, N'量规集', CONVERT(TEXT, N'rublic set'), 0, 2, 0, N'量规库是按照一定的教育测量理论，在计算机系统中实现对某个学科学习效果评价的工具集合。')
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (8, N'题库', CONVERT(TEXT, N'exercises set'), 0, 1, 0, N'')
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (9, N'质的评价量规集', CONVERT(TEXT, N'quality rublic set'), 7, 2, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (10, N'教与学工具和模板', CONVERT(TEXT, N'Tools and Mudules for Teaching and Learning'), 8, 3, 0, N'')
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (11, N'课程设计软件', CONVERT(TEXT, N''), 10, 1, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (12, N'学习工具软件', CONVERT(TEXT, N''), 0, 2, 0, N'')
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (13, N'教学方法模板', CONVERT(TEXT, N''), 10, 3, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (14, N'课件', CONVERT(TEXT, N'Courseware'), 0, 4, 0, N'课件是对一个或几个知识点实施相对完整教学的用于教育，教学的软件。根据运行平台划分，可分为网络版的课件和单机运行的课件，网络版的课件需要能在标准浏览器中运行，并且能通过网络教学环境被大家共享；单机运行的课件可通过网络下载后在本地计算机上运行。')
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (15, N'计算机辅助课堂教学', CONVERT(TEXT, N''), 14, 1, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (16, N'练习', CONVERT(TEXT, N''), 0, 2, 0, N'')
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (17, N'模拟', CONVERT(TEXT, N''), 14, 3, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (18, N'活动', CONVERT(TEXT, N''), 14, 4, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (19, N'项目', CONVERT(TEXT, N''), 14, 5, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (20, N'实验', CONVERT(TEXT, N''), 14, 6, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (21, N'游戏', CONVERT(TEXT, N''), 14, 7, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (22, N'在线学习', CONVERT(TEXT, N''), 14, 8, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (23, N'直观教具', CONVERT(TEXT, N''), 14, 9, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (24, N'动手做实践', CONVERT(TEXT, N''), 14, 10, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (25, N'展示', CONVERT(TEXT, N''), 14, 11, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (26, N'案例', CONVERT(TEXT, N'Case'), 0, 5, 0, N'案例是指有现实指导意义和教学意义的代表性的事件或现象。')
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (27, N'教学案例', CONVERT(TEXT, N''), 26, 1, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (28, N'教案（教学设计方案）', CONVERT(TEXT, N''), 26, 2, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (29, N'学生学习作品', CONVERT(TEXT, N''), 26, 3, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (30, N'文献资料', CONVERT(TEXT, N'Literature'), 0, 6, 0, N'文献资料是指有关教育方面的政策，法规，条例，规章制度，对重大事件的记录，重要文章，书籍等。')
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (31, N'论文', CONVERT(TEXT, N''), 30, 1, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (32, N'报告', CONVERT(TEXT, N''), 30, 2, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (33, N'研究报告', CONVERT(TEXT, N''), 30, 3, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (34, N'文件', CONVERT(TEXT, N''), 30, 4, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (35, N'文献索引', CONVERT(TEXT, N''), 30, 5, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (36, N'指南', CONVERT(TEXT, N''), 30, 6, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (37, N'常见问题解答', CONVERT(TEXT, N''), 30, 7, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (38, N'课程标准和教学大纲', CONVERT(TEXT, N''), 30, 8, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (39, N'教与学的参考资料', CONVERT(TEXT, N''), 30, 9, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (40, N'系列文献资料', CONVERT(TEXT, N''), 30, 10, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (41, N'非正式稿资料', CONVERT(TEXT, N''), 30, 11, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (42, N'工具书', CONVERT(TEXT, N''), 30, 12, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (43, N'百科类资料', CONVERT(TEXT, N''), 30, 13, 0, NULL)
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (44, N'文学作品', CONVERT(TEXT, N'WXZP'), 30, 14, 0, N'')
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (45, N'课程', CONVERT(TEXT, N'course'), 0, 7, 0, N'课程是某门学科的教学内容及实施的教学活动的总和。它包括两个组成部分：按一定的教学目标，教学策略组织起来的教学内容和教学支撑环境。')
INSERT [dbo].[Jtr_ResType] ([TC_ID], [TC_Title], [TC_Code], [TC_Parent], [TC_Sort], [TC_Flag], [TC_Comments]) VALUES (46, N'索引目录', CONVERT(TEXT, N'Resource index'), 0, 8, 0, N'')
SET IDENTITY_INSERT [dbo].[Jtr_ResType] OFF

GO

DELETE FROM [dbo].[S_Grade]
GO

/****** Object:  Table [dbo].[S_Grade]    Script Date: 08/30/2013 15:35:34 ******/
INSERT [dbo].[S_Grade] ([GradeId], [GradeName], [IsGrade], [ParentId]) VALUES (3000, N'小学', 1, NULL)
INSERT [dbo].[S_Grade] ([GradeId], [GradeName], [IsGrade], [ParentId]) VALUES (3100, N'一年级', 0, NULL)
INSERT [dbo].[S_Grade] ([GradeId], [GradeName], [IsGrade], [ParentId]) VALUES (3200, N'二年级', 0, NULL)
INSERT [dbo].[S_Grade] ([GradeId], [GradeName], [IsGrade], [ParentId]) VALUES (3300, N'三年级', 0, NULL)
INSERT [dbo].[S_Grade] ([GradeId], [GradeName], [IsGrade], [ParentId]) VALUES (3400, N'四年级', 0, NULL)
INSERT [dbo].[S_Grade] ([GradeId], [GradeName], [IsGrade], [ParentId]) VALUES (3500, N'五年级', 0, NULL)
INSERT [dbo].[S_Grade] ([GradeId], [GradeName], [IsGrade], [ParentId]) VALUES (3600, N'六年级', 0, NULL)
INSERT [dbo].[S_Grade] ([GradeId], [GradeName], [IsGrade], [ParentId]) VALUES (5000, N'初中', 1, NULL)
INSERT [dbo].[S_Grade] ([GradeId], [GradeName], [IsGrade], [ParentId]) VALUES (5100, N'七年级', 0, NULL)
INSERT [dbo].[S_Grade] ([GradeId], [GradeName], [IsGrade], [ParentId]) VALUES (5200, N'八年级', 0, NULL)
INSERT [dbo].[S_Grade] ([GradeId], [GradeName], [IsGrade], [ParentId]) VALUES (5300, N'九年级', 0, NULL)
INSERT [dbo].[S_Grade] ([GradeId], [GradeName], [IsGrade], [ParentId]) VALUES (7000, N'高中', 1, NULL)
INSERT [dbo].[S_Grade] ([GradeId], [GradeName], [IsGrade], [ParentId]) VALUES (7100, N'高一', 0, NULL)
INSERT [dbo].[S_Grade] ([GradeId], [GradeName], [IsGrade], [ParentId]) VALUES (7200, N'高二', 0, NULL)
INSERT [dbo].[S_Grade] ([GradeId], [GradeName], [IsGrade], [ParentId]) VALUES (7300, N'高三', 0, NULL)
GO
INSERT [dbo].[S_SiteStat] ([Id], [CreateDate], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [TopicCount], [PhotoCount], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [CommentCount], [DateCount], [HistoryArticleCount]) VALUES (1, CAST(0x00009AAC010CCF33 AS DateTime), 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL, 0)
GO

DELETE FROM [dbo].[P_Page] 
GO

/****** Object:  Table [dbo].[P_Page]    Script Date: 08/30/2013 15:35:34 ******/
SET IDENTITY_INSERT [dbo].[P_Page] ON
INSERT [dbo].[P_Page] ([PageId], [Name], [Title], [ObjType], [ObjId], [CreateDate], [Description], [ItemOrder], [LayoutId], [Skin], [CustomSkin]) VALUES (1, CONVERT(TEXT, N'user.index'), N'$user 的博客', 100, 0, CAST(0x00009A5800A28A59 AS DateTime), N'(不要更改)系统保留的缺省个人博客首页', 0, 1, CONVERT(TEXT, N'skin1'), NULL)
INSERT [dbo].[P_Page] ([PageId], [Name], [Title], [ObjType], [ObjId], [CreateDate], [Description], [ItemOrder], [LayoutId], [Skin], [CustomSkin]) VALUES (2, CONVERT(TEXT, N'user.entry'), N'$user $entry', 100, 0, CAST(0x00009A5800A28A59 AS DateTime), N'(不要更改)系统保留的个人博客文章页', 0, 1, CONVERT(TEXT, N'skin1'), NULL)
INSERT [dbo].[P_Page] ([PageId], [Name], [Title], [ObjType], [ObjId], [CreateDate], [Description], [ItemOrder], [LayoutId], [Skin], [CustomSkin]) VALUES (3, CONVERT(TEXT, N'user.category'), N'$user $category', 100, 0, CAST(0x00009A5800A28A59 AS DateTime), N'(不要更改)系统保留的个人分类页面', 0, 1, CONVERT(TEXT, N'skin1'), NULL)
INSERT [dbo].[P_Page] ([PageId], [Name], [Title], [ObjType], [ObjId], [CreateDate], [Description], [ItemOrder], [LayoutId], [Skin], [CustomSkin]) VALUES (4, CONVERT(TEXT, N'group.index'), N'$group 协作组首页', 100, 0, CAST(0x00009A5800A28A59 AS DateTime), N'(不要更改)系统缺省的协作组首页', 0, 1, CONVERT(TEXT, N'skin1'), NULL)
INSERT [dbo].[P_Page] ([PageId], [Name], [Title], [ObjType], [ObjId], [CreateDate], [Description], [ItemOrder], [LayoutId], [Skin], [CustomSkin]) VALUES (5, CONVERT(TEXT, N'user.rescate'), N'$user $rescate 页面', 100, 0, CAST(0x00009A5800A28A59 AS DateTime), N'(不要更改)系统缺省的标签列表首页', 0, 1, CONVERT(TEXT, N'skin1'), NULL)
INSERT [dbo].[P_Page] ([PageId], [Name], [Title], [ObjType], [ObjId], [CreateDate], [Description], [ItemOrder], [LayoutId], [Skin], [CustomSkin]) VALUES (6, CONVERT(TEXT, N'user.profile'), N'$user $fullprofile', 100, 0, CAST(0x00009A5800A28A59 AS DateTime), N'(不要更改)系统缺省的标签显示页', 0, 1, CONVERT(TEXT, N'skin1'), NULL)
INSERT [dbo].[P_Page] ([PageId], [Name], [Title], [ObjType], [ObjId], [CreateDate], [Description], [ItemOrder], [LayoutId], [Skin], [CustomSkin]) VALUES (7, CONVERT(TEXT, N'@grouplist.index'), N'群组列表', 100, 0, CAST(0x00009A5800A1FED8 AS DateTime), N'(不要更改)系统缺省的群组列表显示页', 0, 1, CONVERT(TEXT, N'skin1'), NULL)
INSERT [dbo].[P_Page] ([PageId], [Name], [Title], [ObjType], [ObjId], [CreateDate], [Description], [ItemOrder], [LayoutId], [Skin], [CustomSkin]) VALUES (8, CONVERT(TEXT, N'@group.artcate'), N'$group 文章分类', 100, 0, CAST(0x00009AEB00D87681 AS DateTime), N'(不要更改)系统保留的协作组文章分类页面母版', 0, 1, CONVERT(TEXT, N'skin1'), NULL)
INSERT [dbo].[P_Page] ([PageId], [Name], [Title], [ObjType], [ObjId], [CreateDate], [Description], [ItemOrder], [LayoutId], [Skin], [CustomSkin]) VALUES (9, CONVERT(TEXT, N'@group.rescate'), N'$group 资源分类', 100, 0, CAST(0x00009AEB00D8768B AS DateTime), N'(不要更改)系统保留的协作组资源分类页面母版', 0, 1, CONVERT(TEXT, N'skin1'), NULL)
INSERT [dbo].[P_Page] ([PageId], [Name], [Title], [ObjType], [ObjId], [CreateDate], [Description], [ItemOrder], [LayoutId], [Skin], [CustomSkin]) VALUES (12, CONVERT(TEXT, N'group.article'), N'$group $article 文章', 100, 0, CAST(0x00009A8C01029085 AS DateTime), N'(不要更改)系统缺省群组文章显示页面', 0, 1, CONVERT(TEXT, N'skin1'), NULL)
INSERT [dbo].[P_Page] ([PageId], [Name], [Title], [ObjType], [ObjId], [CreateDate], [Description], [ItemOrder], [LayoutId], [Skin], [CustomSkin]) VALUES (15, CONVERT(TEXT, N'@subject.index'), N'$subject 首页', 100, 0, CAST(0x00009AA400B94EF0 AS DateTime), N'(不要更改)系统保留的学科首页模板', 0, 1, CONVERT(TEXT, N'skin1'), NULL)
INSERT [dbo].[P_Page] ([PageId], [Name], [Title], [ObjType], [ObjId], [CreateDate], [Description], [ItemOrder], [LayoutId], [Skin], [CustomSkin]) VALUES (45, CONVERT(TEXT, N'user.photos'), N'$user $allphoto', 100, 0, CAST(0x00009A5800A289C4 AS DateTime), N'(不要更改)系统缺省的标签显示页', 0, 1, CONVERT(TEXT, N'skin1'), NULL)
INSERT [dbo].[P_Page] ([PageId], [Name], [Title], [ObjType], [ObjId], [CreateDate], [Description], [ItemOrder], [LayoutId], [Skin], [CustomSkin]) VALUES (59, CONVERT(TEXT, N'index'), N'管理员工作室', 1, 1, CAST(0x00009BF500BA2B80 AS DateTime), N'Copy from: (不要更改)系统保留的缺省个人博客首页', 0, 1, CONVERT(TEXT, N'skin1'), NULL)
INSERT [dbo].[P_Page] ([PageId], [Name], [Title], [ObjType], [ObjId], [CreateDate], [Description], [ItemOrder], [LayoutId], [Skin], [CustomSkin]) VALUES (60, CONVERT(TEXT, N'user.static.index'), N'$user 的博客', 100, 0, CAST(0x00009A5800A289C4 AS DateTime), N'(不要更改)系统保留的缺省个人博客首页', 0, 1, CONVERT(TEXT, N'skin1'), NULL)
INSERT [dbo].[P_Page] ([PageId], [Name], [Title], [ObjType], [ObjId], [CreateDate], [Description], [ItemOrder], [LayoutId], [Skin], [CustomSkin]) VALUES (61, CONVERT(TEXT, N'groupkt.index'), N'$group 课题研究组首页', 100, 0, CAST(0x0000A15000BD553E AS DateTime), N'(不要更改)系统缺省的课题组首页', 0, 1, CONVERT(TEXT, N'skin1'), NULL)
SET IDENTITY_INSERT [dbo].[P_Page] OFF
GO
DELETE FROM [dbo].[SiteNav]
GO
/****** Object:  Table [dbo].[SiteNav]    Script Date: 08/30/2013 15:35:34 ******/
SET IDENTITY_INSERT [dbo].[SiteNav] ON
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (1, N'首页', CONVERT(TEXT, N'index.action'), CONVERT(TEXT, N'index'), 1, 0, 0, 0, 0)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (2, N'文章', CONVERT(TEXT, N'articles.action'), CONVERT(TEXT, N'articles'), 1, 1, 0, 0, 0)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (3, N'工作室', CONVERT(TEXT, N'blogs.action'), CONVERT(TEXT, N'blogs'), 1, 2, 0, 0, 0)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (4, N'协作组', CONVERT(TEXT, N'groups.action'), CONVERT(TEXT, N'groups'), 1, 3, 0, 0, 0)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (6, N'资源', CONVERT(TEXT, N'resources.action'), CONVERT(TEXT, N'resources'), 1, 5, 0, 0, 0)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (7, N'活动', CONVERT(TEXT, N'actions.action'), CONVERT(TEXT, N'actions'), 1, 6, 0, 0, 0)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (8, N'集备', CONVERT(TEXT, N'prepareCourse.action'), CONVERT(TEXT, N'cocourses'), 1, 7, 0, 0, 0)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (9, N'图片', CONVERT(TEXT, N'photos.action'), CONVERT(TEXT, N'gallery'), 1, 8, 0, 0, 0)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (10, N'视频', CONVERT(TEXT, N'videos.action'), CONVERT(TEXT, N'videos'), 1, 9, 0, 0, 0)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (11, N'专题', CONVERT(TEXT, N'specialSubject.action'), CONVERT(TEXT, N'special_subject'), 1, 10, 0, 0, 0)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (12, N'标签', CONVERT(TEXT, N'tags.action'), CONVERT(TEXT, N'tags'), 0, 11, 0, 0, 0)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (15, N'排行榜', CONVERT(TEXT, N'ranklist.action'), CONVERT(TEXT, N'rank'), 0, 14, 0, 0, 0)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (16, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 1)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (17, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 1)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (18, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 1)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (19, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 1)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (20, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 1)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (21, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 1)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (22, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 1)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (23, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 1)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (24, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 1)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (25, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 1)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (26, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 2)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (27, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 2)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (28, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 2)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (29, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 2)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (30, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 2)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (31, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 2)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (32, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 2)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (33, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 2)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (34, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 2)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (35, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 2)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (36, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 3)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (37, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 3)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (38, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 3)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (39, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 3)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (40, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 3)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (41, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 3)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (42, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 3)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (43, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 3)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (44, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 3)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (45, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 3)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (46, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 4)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (47, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 4)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (48, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 4)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (49, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 4)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (50, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 4)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (51, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 4)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (52, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 4)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (53, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 4)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (54, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 4)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (55, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 4)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (56, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 5)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (57, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 5)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (58, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 5)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (59, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 5)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (60, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 5)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (61, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 5)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (62, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 5)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (63, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 5)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (64, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 5)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (65, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 5)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (66, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 6)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (67, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 6)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (68, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 6)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (69, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 6)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (70, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 6)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (71, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 6)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (72, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 6)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (73, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 6)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (74, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 6)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (75, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 6)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (76, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 7)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (77, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 7)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (78, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 7)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (79, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 7)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (80, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 7)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (81, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 7)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (82, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 7)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (83, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 7)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (84, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 7)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (85, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 7)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (86, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 8)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (87, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 8)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (88, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 8)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (89, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 8)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (90, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 8)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (91, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 8)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (92, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 8)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (93, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 8)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (94, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 8)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (95, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 8)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (96, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 9)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (97, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 9)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (98, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 9)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (99, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 9)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (100, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 9)
GO
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (101, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 9)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (102, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 9)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (103, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 9)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (104, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 9)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (105, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 9)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (106, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 10)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (107, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 10)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (108, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 10)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (109, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 10)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (110, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 10)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (111, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 10)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (112, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 10)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (113, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 10)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (114, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 10)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (115, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 10)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (116, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 11)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (117, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 11)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (118, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 11)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (119, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 11)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (120, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 11)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (121, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 11)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (122, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 11)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (123, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 11)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (124, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 11)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (125, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 11)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (126, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 12)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (127, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 12)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (128, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 12)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (129, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 12)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (130, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 12)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (131, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 12)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (132, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 12)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (133, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 12)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (134, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 12)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (135, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 12)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (136, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 13)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (137, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 13)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (138, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 13)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (139, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 13)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (140, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 13)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (141, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 13)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (142, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 13)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (143, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 13)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (144, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 13)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (145, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 13)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (146, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 14)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (147, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 14)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (148, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 14)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (149, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 14)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (150, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 14)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (151, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 14)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (152, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 14)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (153, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 14)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (154, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 14)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (155, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 14)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (156, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 15)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (157, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 15)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (158, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 15)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (159, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 15)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (160, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 15)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (161, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 15)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (162, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 15)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (163, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 15)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (164, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 15)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (165, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 15)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (166, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 16)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (167, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 16)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (168, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 16)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (169, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 16)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (170, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 16)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (171, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 16)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (172, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 16)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (173, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 16)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (174, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 16)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (175, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 16)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (176, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 17)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (177, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 17)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (178, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 17)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (179, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 17)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (180, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 17)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (181, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 17)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (182, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 17)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (183, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 17)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (184, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 17)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (185, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 17)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (186, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 18)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (187, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 18)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (188, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 18)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (189, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 18)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (190, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 18)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (191, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 18)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (192, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 18)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (193, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 18)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (194, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 18)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (195, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 18)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (196, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 19)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (197, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 19)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (198, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 19)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (199, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 19)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (200, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 19)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (201, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 19)
GO
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (202, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 19)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (203, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 19)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (204, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 19)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (205, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 19)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (206, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 20)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (207, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 20)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (208, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 20)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (209, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 20)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (210, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 20)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (211, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 20)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (212, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 20)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (213, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 20)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (214, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 20)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (215, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 20)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (216, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 21)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (217, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 21)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (218, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 21)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (219, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 21)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (220, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 21)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (221, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 21)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (222, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 21)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (223, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 21)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (224, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 21)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (225, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 21)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (226, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 22)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (227, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 22)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (228, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 22)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (229, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 22)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (230, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 22)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (231, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 22)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (232, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 22)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (233, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 22)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (234, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 22)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (235, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 22)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (236, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 23)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (237, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 23)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (238, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 23)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (239, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 23)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (240, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 23)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (241, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 23)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (242, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 23)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (243, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 23)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (244, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 23)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (245, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 23)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (246, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 24)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (247, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 24)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (248, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 24)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (249, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 24)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (250, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 24)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (251, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 24)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (252, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 24)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (253, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 24)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (254, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 24)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (255, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 24)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (256, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 25)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (257, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 25)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (258, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 25)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (259, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 25)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (260, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 25)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (261, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 25)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (262, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 25)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (263, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 25)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (264, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 25)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (265, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 25)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (266, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 26)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (267, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 26)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (268, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 26)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (269, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 26)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (270, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 26)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (271, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 26)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (272, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 26)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (273, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 26)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (274, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 26)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (275, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 26)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (276, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 27)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (277, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 27)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (278, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 27)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (279, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 27)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (280, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 27)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (281, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 27)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (282, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 27)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (283, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 27)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (284, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 27)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (285, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 27)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (286, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 28)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (287, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 28)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (288, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 28)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (289, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 28)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (290, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 28)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (291, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 28)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (292, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 28)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (293, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 28)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (294, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 28)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (295, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 28)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (296, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 29)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (297, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 29)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (298, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 29)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (299, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 29)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (300, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 29)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (301, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 29)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (302, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 29)
GO

INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (303, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 29)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (304, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 29)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (305, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 29)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (306, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 30)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (307, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 30)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (308, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 30)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (309, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 30)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (310, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 30)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (311, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 30)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (312, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 30)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (313, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 30)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (314, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 30)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (315, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 30)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (316, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 31)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (317, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 31)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (318, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 31)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (319, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 31)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (320, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 31)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (321, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 31)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (322, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 31)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (323, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 31)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (324, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 31)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (325, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 31)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (326, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 32)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (327, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 32)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (328, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 32)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (329, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 32)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (330, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 32)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (331, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 32)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (332, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 32)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (333, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 32)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (334, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 32)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (335, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 32)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (336, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 33)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (337, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 33)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (338, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 33)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (339, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 33)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (340, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 33)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (341, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 33)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (342, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 33)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (343, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 33)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (344, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 33)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (345, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 33)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (346, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 34)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (347, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 34)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (348, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 34)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (349, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 34)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (350, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 34)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (351, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 34)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (352, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 34)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (353, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 34)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (354, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 34)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (355, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 34)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (356, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 35)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (357, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 35)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (358, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 35)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (359, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 35)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (360, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 35)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (361, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 35)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (362, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 35)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (363, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 35)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (364, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 35)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (365, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 35)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (366, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 36)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (367, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 36)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (368, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 36)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (369, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 36)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (370, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 36)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (371, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 36)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (372, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 36)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (373, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 36)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (374, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 36)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (375, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 36)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (376, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 37)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (377, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 37)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (378, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 37)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (379, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 37)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (380, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 37)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (381, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 37)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (382, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 37)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (383, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 37)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (384, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 37)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (385, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 37)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (386, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 38)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (387, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 38)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (388, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 38)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (389, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 38)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (390, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 38)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (391, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 38)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (392, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 38)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (393, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 38)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (394, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 38)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (395, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 38)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (396, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 39)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (397, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 39)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (398, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 39)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (399, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 39)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (400, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 39)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (401, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 39)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (402, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 39)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (403, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 39)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (404, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 39)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (405, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 39)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (406, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 2, 40)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (407, N'学科首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'subject'), 1, 1, 0, 2, 40)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (408, N'文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'article'), 1, 2, 0, 2, 40)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (409, N'资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'resource'), 1, 3, 0, 2, 40)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (410, N'工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'blog'), 1, 4, 0, 2, 40)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (411, N'协作组', CONVERT(TEXT, N'groups/'), CONVERT(TEXT, N'groups'), 1, 5, 0, 2, 40)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (412, N'集备', CONVERT(TEXT, N'preparecourse/'), CONVERT(TEXT, N'preparecourse'), 1, 6, 0, 2, 40)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (413, N'视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'video'), 1, 7, 0, 2, 40)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (414, N'活动', CONVERT(TEXT, N'activity/'), CONVERT(TEXT, N'activity'), 1, 8, 0, 2, 40)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (415, N'专题', CONVERT(TEXT, N'specialsubject/'), CONVERT(TEXT, N'specialsubject'), 1, 9, 0, 2, 40)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (416, N'总站首页', CONVERT(TEXT, N'py/home.py'), CONVERT(TEXT, N'index'), 1, 0, 0, 1, 2)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (417, N'机构首页', CONVERT(TEXT, N''), CONVERT(TEXT, N'unit'), 1, 1, 0, 1, 2)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (418, N'机构文章', CONVERT(TEXT, N'article/'), CONVERT(TEXT, N'unit_article'), 1, 2, 0, 1, 2)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (419, N'机构资源', CONVERT(TEXT, N'resource/'), CONVERT(TEXT, N'unit_resource'), 1, 3, 0, 1, 2)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (420, N'机构图片', CONVERT(TEXT, N'photo/'), CONVERT(TEXT, N'unit_photo'), 1, 4, 0, 1, 2)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (421, N'机构视频', CONVERT(TEXT, N'video/'), CONVERT(TEXT, N'unit_video'), 1, 5, 0, 1, 2)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (422, N'机构工作室', CONVERT(TEXT, N'blog/'), CONVERT(TEXT, N'unit_user'), 1, 6, 0, 1, 2)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (423, N'观课评课', CONVERT(TEXT, N'evaluations.action'), CONVERT(TEXT, N'evaluations'), 1, 12, 0, 0, 0)
INSERT [dbo].[SiteNav] ([SiteNavId], [SiteNavName], [SiteNavUrl], [CurrentNav], [SiteNavIsShow], [SiteNavItemOrder], [IsExternalLink], [OwnerType], [OwnerId]) VALUES (424, N'课题研究', CONVERT(TEXT, N'ktgroups.action'), CONVERT(TEXT, N'ktgroups'), 1, 16, 0, 0, 0)
SET IDENTITY_INSERT [dbo].[SiteNav] OFF
GO
DELETE FROM  [dbo].[U_Condition2]
GO

SET IDENTITY_INSERT [dbo].[U_Condition2] ON
INSERT [dbo].[U_Condition2] ([Id], [GroupId], [TeacherType], [TeacherTypeKeyword], [SQL_Condition]) VALUES (1, 2, CONVERT(TEXT, N'名师'), CONVERT(TEXT, N'isFamous'), CONVERT(TEXT, N'From User Where userType LIKE ''%/1/%'''))
INSERT [dbo].[U_Condition2] ([Id], [GroupId], [TeacherType], [TeacherTypeKeyword], [SQL_Condition]) VALUES (2, 2, CONVERT(TEXT, N'学科带头人'), CONVERT(TEXT, N'isExpert'), CONVERT(TEXT, N'From User Where userType LIKE ''%/3/%'''))
INSERT [dbo].[U_Condition2] ([Id], [GroupId], [TeacherType], [TeacherTypeKeyword], [SQL_Condition]) VALUES (3, 3, CONVERT(TEXT, N'教研员'), CONVERT(TEXT, N'isComissioner'), CONVERT(TEXT, N'From User Where userType LIKE ''%/4/%'''))
INSERT [dbo].[U_Condition2] ([Id], [GroupId], [TeacherType], [TeacherTypeKeyword], [SQL_Condition]) VALUES (4, 2, CONVERT(TEXT, N'推荐教师'), CONVERT(TEXT, N'isRecommend'), CONVERT(TEXT, N'From User Where userType LIKE ''%/2/%'''))
INSERT [dbo].[U_Condition2] ([Id], [GroupId], [TeacherType], [TeacherTypeKeyword], [SQL_Condition]) VALUES (5, 3, CONVERT(TEXT, N'研修之星'), CONVERT(TEXT, N'isStar'), CONVERT(TEXT, N'From User Where userType LIKE ''%/5/%'''))
INSERT [dbo].[U_Condition2] ([Id], [GroupId], [TeacherType], [TeacherTypeKeyword], [SQL_Condition]) VALUES (6, 3, CONVERT(TEXT, N'教师风采'), CONVERT(TEXT, N'isShow'), CONVERT(TEXT, N'From User Where userType LIKE ''%/6/%'''))
INSERT [dbo].[U_Condition2] ([Id], [GroupId], [TeacherType], [TeacherTypeKeyword], [SQL_Condition]) VALUES (7, 7, CONVERT(TEXT, N'管理员'), CONVERT(TEXT, N'isManager'), CONVERT(TEXT, N'From User Where adminLevel>=100 OR censorLevel>=100'))
SET IDENTITY_INSERT [dbo].[U_Condition2] OFF
GO

DELETE FROM [dbo].[U_Group]
GO

SET IDENTITY_INSERT [dbo].[U_Group] ON
INSERT [dbo].[U_Group] ([GroupId], [GroupName], [GroupInfo]) VALUES (1, CONVERT(TEXT, N'不限组'), CONVERT(TEXT, N'日发表文章和上传资源数不受限制，空间容量限300M'))
SET IDENTITY_INSERT [dbo].[U_Group] OFF

DELETE FROM [dbo].[U_GroupPower]
GO
SET IDENTITY_INSERT [dbo].[U_GroupPower] ON
INSERT [dbo].[U_GroupPower] ([GroupPowerId], [GroupId], [UploadArticleNum], [UploadResourceNum], [UploadDiskNum]) VALUES (1, 1, -1, -1, 300)
SET IDENTITY_INSERT [dbo].[U_GroupPower] OFF
GO

DELETE FROM [dbo].[U_Condition1]
GO
SET IDENTITY_INSERT [dbo].[U_Condition1] ON
INSERT [dbo].[U_Condition1] ([Id], [GroupId], [Score1], [Score2], [ConditionType]) VALUES (1, 1, 500, 500, -1)
INSERT [dbo].[U_Condition1] ([Id], [GroupId], [Score1], [Score2], [ConditionType]) VALUES (2, 1, 500, 500, 1)
SET IDENTITY_INSERT [dbo].[U_Condition1] OFF
GO


/**
 * 
DELETE FROM [dbo].[Jitar_UnitType]
GO

SET IDENTITY_INSERT [dbo].[Jitar_UnitType] ON
INSERT [dbo].[Jitar_UnitType] ([Id], [UnitTypeGuid], [UnitTypeName], [OrderNo]) VALUES (1, N'14308dee-ec83-4001-abb5-d13f12d24f4b', N'教委/教办', 0)
INSERT [dbo].[Jitar_UnitType] ([Id], [UnitTypeGuid], [UnitTypeName], [OrderNo]) VALUES (2, N'2c902381-f071-4701-9f54-1895eac4987f', N'小学', 1)
INSERT [dbo].[Jitar_UnitType] ([Id], [UnitTypeGuid], [UnitTypeName], [OrderNo]) VALUES (3, N'6403a8f8-81a7-48fd-97c9-4f771d274b6a', N'初中', 2)
INSERT [dbo].[Jitar_UnitType] ([Id], [UnitTypeGuid], [UnitTypeName], [OrderNo]) VALUES (4, N'968fb574-da77-4b78-9c94-a6cebc246b5d', N'高中', 3)
INSERT [dbo].[Jitar_UnitType] ([Id], [UnitTypeGuid], [UnitTypeName], [OrderNo]) VALUES (5, N'f37a9582-7735-4c26-b78a-80bf522f34ad', N'学前', 4)
INSERT [dbo].[Jitar_UnitType] ([Id], [UnitTypeGuid], [UnitTypeName], [OrderNo]) VALUES (6, N'26500b98-eb6d-42b4-933c-73e5507ec6c4', N'职教', 5)
INSERT [dbo].[Jitar_UnitType] ([Id], [UnitTypeGuid], [UnitTypeName], [OrderNo]) VALUES (7, N'8186ec4d-9437-4af5-94e8-6239694d6004', N'特殊', 6)
INSERT [dbo].[Jitar_UnitType] ([Id], [UnitTypeGuid], [UnitTypeName], [OrderNo]) VALUES (8, N'3070af2e-79b8-4e65-be90-5064713192f6', N'师校', 7)
INSERT [dbo].[Jitar_UnitType] ([Id], [UnitTypeGuid], [UnitTypeName], [OrderNo]) VALUES (9, N'3eadfe41-8189-4671-ac0a-4caf9d6566f9', N'教辅', 8)
SET IDENTITY_INSERT [dbo].[Jitar_UnitType] OFF
**/
GO
DELETE FROM [dbo].[S_Plugin]
GO

SET IDENTITY_INSERT [dbo].[S_Plugin] ON
INSERT [dbo].[S_Plugin] ([PluginId], [PluginName], [PluginTitle], [PluginType], [ItemOrder], [Enabled], [Icon]) VALUES (1, CONVERT(TEXT, N'questionanswer'), N'问题与解答', CONVERT(TEXT, N'system'), 0, 1, CONVERT(TEXT, N'help.gif'))
INSERT [dbo].[S_Plugin] ([PluginId], [PluginName], [PluginTitle], [PluginType], [ItemOrder], [Enabled], [Icon]) VALUES (2, CONVERT(TEXT, N'vote'), N'调查投票', CONVERT(TEXT, N'system'), 0, 1, CONVERT(TEXT, N'ico_stats.gif'))
INSERT [dbo].[S_Plugin] ([PluginId], [PluginName], [PluginTitle], [PluginType], [ItemOrder], [Enabled], [Icon]) VALUES (3, CONVERT(TEXT, N'calendarevent'), N'日历提醒', CONVERT(TEXT, N'system'), 0, 1, CONVERT(TEXT, N'datetime.gif'))
INSERT [dbo].[S_Plugin] ([PluginId], [PluginName], [PluginTitle], [PluginType], [ItemOrder], [Enabled], [Icon]) VALUES (4, CONVERT(TEXT, N'topic'), N'专题讨论', CONVERT(TEXT, N'system'), 0, 0, NULL)
SET IDENTITY_INSERT [dbo].[S_Plugin] OFF
GO

DELETE FROM [dbo].[S_Subject]
GO
SET IDENTITY_INSERT [dbo].[S_Subject] ON
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (1, 1, 7000, N'高中语文', 1, N'gzyw', 0, 0, 0, 0, 0, 527, 0, 0, 0, 0, CONVERT(TEXT, N'3643DF69-DA69-4430-80EE-B27D738BF6B4'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (2, 2, 7000, N'高中数学', 2, N'gzsx', 0, 0, 0, 0, 0, 419, 0, 0, 0, 0, CONVERT(TEXT, N'F18C2EF7-2200-46C7-99ED-398BA316F386'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (3, 3, 7000, N'高中英语', 3, N'gzyy', 0, 0, 0, 0, 0, 494, 0, 0, 0, 0, CONVERT(TEXT, N'63A0166D-4826-4779-9044-9908C2D1F09F'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (4, 4, 7000, N'高中物理', 4, N'gzwl', 0, 0, 0, 0, 0, 459, 0, 0, 0, 0, CONVERT(TEXT, N'6ED3D0B2-E6F8-4867-915B-67DFD3DEFE4A'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (5, 5, 7000, N'高中化学', 5, N'gzhx', 0, 0, 0, 0, 0, 321, 0, 0, 0, 0, CONVERT(TEXT, N'6F545C37-39C9-4F07-9159-E3BDA4E25C97'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (6, 6, 7000, N'高中生物', 6, N'gzsw', 0, 0, 0, 0, 0, 700, 0, 0, 0, 0, CONVERT(TEXT, N'491FFCCC-38B0-45FB-8463-9E3DDAFA6E08'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (7, 7, 7000, N'高中历史', 7, N'gzls', 0, 0, 0, 0, 0, 346, 0, 0, 0, 0, CONVERT(TEXT, N'BECA5471-2E62-40E1-BA03-1B9E084A9E71'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (8, 8, 7000, N'高中地理', 8, N'gzdl', 0, 0, 0, 0, 0, 391, 0, 0, 0, 0, CONVERT(TEXT, N'95AF4676-DCA4-4EE0-9CCD-078746188922'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (9, 9, 7000, N'高中音乐', 9, N'gzyyu', 0, 0, 0, 0, 0, 722, 0, 0, 0, 0, CONVERT(TEXT, N'1157EB1E-4A99-4046-8A85-99727209A543'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (10, 10, 7000, N'高中美术', 10, N'gzms', 0, 0, 0, 0, 0, 723, 0, 0, 0, 0, CONVERT(TEXT, N'F1E2FDE4-68A3-4B61-B5B9-A172C53D5B16'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (11, 14, 7000, N'高中艺术', 11, N'gzys', 0, 0, 0, 0, 0, 724, 0, 0, 0, 0, CONVERT(TEXT, N'7D18D163-BFC5-4F60-BE80-9862CA6B08C7'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (12, 15, 7000, N'高中技术', 12, N'gzjs', 0, 0, 0, 0, 0, 263, 0, 0, 0, 0, CONVERT(TEXT, N'22DFF045-C86D-4E8A-920A-CB65B18393FE'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (13, 12, 7000, N'高中思想政治', 13, N'gzsxzz', 0, 0, 0, 0, 0, 193, 0, 0, 0, 0, CONVERT(TEXT, N'775EB3C4-483A-4BB7-86AE-B61317F4366C'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (14, 11, 7000, N'高中信息技术', 14, N'gzxxjs', 0, 0, 0, 0, 0, 166, 0, 0, 0, 0, CONVERT(TEXT, N'8C4968E0-FE34-46B0-88F1-0D774B559EB0'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (15, 13, 7000, N'高中体育与健康', 15, N'gzty', 0, 0, 0, 0, 0, 741, 0, 0, 0, 0, CONVERT(TEXT, N'DA1FAFBC-9D1F-4C52-B7A1-1B35BB6E202D'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (16, 1, 5000, N'初中语文', 16, N'czyw', 0, 0, 0, 0, 0, 726, 0, 0, 0, 0, CONVERT(TEXT, N'BE56D234-F487-4AA7-A0E1-9595D70B64F6'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (17, 2, 5000, N'初中数学', 17, N'czsx', 0, 0, 0, 0, 0, 727, 0, 0, 0, 0, CONVERT(TEXT, N'3203AB6F-1638-4DD6-8241-8D0DAC25677C'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (18, 3, 5000, N'初中英语', 18, N'czyy', 0, 0, 0, 0, 0, 728, 0, 0, 0, 0, CONVERT(TEXT, N'8423A22E-8DB9-4C99-9AF8-8A328A2BCC89'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (19, 4, 5000, N'初中物理', 19, N'czwl', 0, 0, 0, 0, 0, 729, 0, 0, 0, 0, CONVERT(TEXT, N'5C4C3A5A-6E4C-4922-A143-B2E2CCB90AA5'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (20, 5, 5000, N'初中化学', 20, N'czhx', 0, 0, 0, 0, 0, 730, 0, 0, 0, 0, CONVERT(TEXT, N'6145DC6E-CD5F-45CD-9256-2C51906593A2'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (21, 6, 5000, N'初中生物', 21, N'czsw', 0, 0, 0, 0, 0, 731, 0, 0, 0, 0, CONVERT(TEXT, N'2AFCC37E-70D2-4799-8384-B290DD362A4B'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (22, 7, 5000, N'初中历史', 22, N'czls', 0, 0, 0, 0, 0, 732, 0, 0, 0, 0, CONVERT(TEXT, N'EC01707E-2872-43CB-828E-AB9B792EC6F6'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (23, 8, 5000, N'初中地理', 23, N'czdl', 0, 0, 0, 0, 0, 733, 0, 0, 0, 0, CONVERT(TEXT, N'1715BA1C-2942-4A51-B6D8-86298D698D4F'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (24, 9, 5000, N'初中音乐', 24, N'czyyue', 0, 0, 0, 0, 0, 735, 0, 0, 0, 0, CONVERT(TEXT, N'E9A334BF-B121-456F-A15D-F5EEB4676ED3'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (25, 10, 5000, N'初中美术', 25, N'czms', 0, 0, 0, 0, 0, 736, 0, 0, 0, 0, CONVERT(TEXT, N'76B6D283-CECA-4388-B904-7B2001E71F55'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (26, 14, 5000, N'初中艺术', 26, N'czys', 0, 0, 0, 0, 0, 737, 0, 0, 0, 0, CONVERT(TEXT, N'74456356-4EFF-4011-A3F9-91AA9DA2E439'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (27, 17, 5000, N'初中思想品德', 27, N'czsxpd', 0, 0, 0, 0, 0, 734, 0, 0, 0, 0, CONVERT(TEXT, N'D8AF2D3E-BDA1-4C5E-896F-C4D6A707D68A'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (28, 11, 5000, N'初中信息技术', 28, N'czxxjs', 0, 0, 0, 0, 0, 739, 0, 0, 0, 0, CONVERT(TEXT, N'8000932F-D161-4F84-AC09-F70A3264F735'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (29, 18, 5000, N'初中历史与社会', 29, N'czlssh', 0, 0, 0, 0, 0, 738, 0, 0, 0, 0, CONVERT(TEXT, N'D7BAE3C2-F73A-4627-8880-D485CB42B5AB'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (30, 13, 5000, N'初中体育与健康', 30, N'czty', 0, 0, 0, 0, 0, 741, 0, 0, 0, 0, CONVERT(TEXT, N'C47266F9-AFA2-445F-ABEB-0FD796F9F7CB'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (31, 1, 3000, N'小学语文', 31, N'xxyw', 0, 0, 0, 0, 0, 742, 0, 0, 0, 0, CONVERT(TEXT, N'7A04C9CB-0C97-4186-813E-7F52F2154A0C'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (32, 2, 3000, N'小学数学', 32, N'xxsx', 0, 0, 0, 0, 0, 743, 0, 0, 0, 0, CONVERT(TEXT, N'CB40882B-19B5-4E50-BEC5-10AB2002B18B'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (33, 3, 3000, N'小学英语', 33, N'xxyy', 0, 0, 0, 0, 0, 744, 0, 0, 0, 0, CONVERT(TEXT, N'4E8EFD8F-D418-413C-8EF3-58FC907A8293'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (34, 16, 3000, N'小学科学', 34, N'xxkx', 0, 0, 0, 0, 0, 745, 0, 0, 0, 0, CONVERT(TEXT, N'994AE2A2-DCAE-4910-A354-BEA18A927971'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (35, 9, 3000, N'小学音乐', 35, N'xxyyu', 0, 0, 0, 0, 0, 746, 0, 0, 0, 0, CONVERT(TEXT, N'3B30A946-95FD-4977-BB04-90BFCA3979A4'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (36, 10, 3000, N'小学美术', 36, N'xxms', 0, 0, 0, 0, 0, 747, 0, 0, 0, 0, CONVERT(TEXT, N'2AF8A0BB-007C-4622-8758-FA7B0272EB25'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (37, 13, 3000, N'小学体育与健康', 37, N'xxty', 0, 0, 0, 0, 0, 748, 0, 0, 0, 0, CONVERT(TEXT, N'BF8EE1DA-052D-4868-847C-1E9C448C7AE5'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (38, 19, 3000, N'小学品德与生活', 38, N'xxpd', 0, 0, 0, 0, 0, 750, 0, 0, 0, 0, CONVERT(TEXT, N'745345D1-95BB-4D78-93D6-D167EDC42D3B'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (39, 20, 3000, N'小学品德与社会', 39, N'xxsh', 0, 0, 0, 0, 0, 751, 0, 0, 0, 0, CONVERT(TEXT, N'EEB908F7-CF99-4C46-AD9E-D7D5F317CFD5'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
INSERT [dbo].[S_Subject] ([SubjectId], [MetaSubjectId], [MetaGradeId], [SubjectName], [OrderNum], [SubjectCode], [VisitCount], [UserCount], [GroupCount], [ArticleCount], [ResourceCount], [reslibCId], [TodayArticleCount], [YesterdayArticleCount], [TodayResourceCount], [YesterdayResourceCount], [SubjectGuid], [HeaderContent], [FooterContent], [TemplateName], [ThemeName], [Logo], [CustormTemplate], [ShortcutTarget], [HistoryArticleCount]) VALUES (40, 11, 3000, N'小学信息技术', 40, N'xxxxjs', 0, 0, 0, 0, 0, 749, 0, 0, 0, 0, CONVERT(TEXT, N'1067192F-0EC4-4727-AC7E-7D5CB1A5E62F'), NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0)
SET IDENTITY_INSERT [dbo].[S_Subject] OFF
GO
/****** Object:  Table [dbo].[G_GroupPhoto]    Script Date: 08/30/2013 15:35:34 ******/
/****** Object:  Table [dbo].[P_Widget]    Script Date: 08/30/2013 15:35:34 ******/
SET IDENTITY_INSERT [dbo].[P_Widget] ON
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (1, CONVERT(TEXT, N'article_content'), N'文章内容', CONVERT(TEXT, N'article_content'), CAST(0x00009A5700CC2A1D AS DateTime), 2, N'', 0, 0, 2, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (2, CONVERT(TEXT, N'profile'), N'个人信息', CONVERT(TEXT, N'profile'), CAST(0x00009A5700CC6AC0 AS DateTime), 2, N'', 0, 0, 1, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (3, CONVERT(TEXT, N'article_comments'), N'文章评论', CONVERT(TEXT, N'article_comments'), CAST(0x00009A57011FC1E5 AS DateTime), 2, N'', 0, 0, 2, 2, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (9, CONVERT(TEXT, N'new_tags'), N'最新标签', CONVERT(TEXT, N'new_tags'), CAST(0x00009A5800989777 AS DateTime), 7, N'', 0, 1, 1, 1, N'', NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (10, CONVERT(TEXT, N'profile'), N'个人信息', CONVERT(TEXT, N'profile'), CAST(0x00009A5801017392 AS DateTime), 1, N'', 0, 1, 1, 1, N'', NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (14, CONVERT(TEXT, N'user_stats'), N'统计信息', CONVERT(TEXT, N'user_stats'), CAST(0x00009A5F00FEDD6A AS DateTime), 1, N'', 0, 1, 1, 3, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (15, CONVERT(TEXT, N'joined_groups'), N'我的协作组', CONVERT(TEXT, N'joined_groups'), CAST(0x00009A6B00DDA0ED AS DateTime), 1, N'', 0, 0, 2, 4, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (16, CONVERT(TEXT, N'user_placard'), N'我的公告', CONVERT(TEXT, N'user_placard'), CAST(0x00009A7000F3295E AS DateTime), 1, N'', 0, 0, 3, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (18, CONVERT(TEXT, N'user_cate'), N'文章分类', CONVERT(TEXT, N'user_cate'), CAST(0x00009A7000F6701C AS DateTime), 1, N'', 0, 0, 1, 2, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (19, CONVERT(TEXT, N'blog_search'), N'文章搜索', CONVERT(TEXT, N'blog_search'), CAST(0x00009A7000F8652A AS DateTime), 1, N'', 0, 0, 1, 4, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (21, CONVERT(TEXT, N'friendlinks'), N'我的好友', CONVERT(TEXT, N'friendlinks'), CAST(0x00009A7000FCE7BE AS DateTime), 1, N'', 0, 0, 3, 3, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (22, CONVERT(TEXT, N'entries'), N'最新文章列表', CONVERT(TEXT, N'entries'), CAST(0x00009A7000FD4A17 AS DateTime), 1, N'', 0, 0, 2, 2, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (24, CONVERT(TEXT, N'user_leaveword'), N'我的留言', CONVERT(TEXT, N'user_leaveword'), CAST(0x00009A70010979D9 AS DateTime), 1, N'', 0, 0, 2, 3, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (25, CONVERT(TEXT, N'group_info'), N'群组信息', CONVERT(TEXT, N'group_info'), CAST(0x00009A7100A61EF2 AS DateTime), 4, N'', 0, 0, 1, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (26, CONVERT(TEXT, N'group_cate'), N'资源分类', CONVERT(TEXT, N'group_cate'), CAST(0x00009A72011E0660 AS DateTime), 4, N'', 0, 0, 1, 3, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (27, CONVERT(TEXT, N'group_stat'), N'统计信息', CONVERT(TEXT, N'group_stat'), CAST(0x00009A7700D6CB2E AS DateTime), 4, N'', 0, 0, 3, 3, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (29, CONVERT(TEXT, N'lastest_comments'), N'我的回复提示', CONVERT(TEXT, N'lastest_comments'), CAST(0x00009A7B01070936 AS DateTime), 1, N'', 0, 0, 3, 2, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (31, CONVERT(TEXT, N'profile'), N'个人信息', CONVERT(TEXT, N'profile'), CAST(0x00009A7E00993307 AS DateTime), 5, NULL, 0, 1, 1, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (32, CONVERT(TEXT, N'user_rcate'), N'资源分类', CONVERT(TEXT, N'user_rcate'), CAST(0x00009A7E00B1382A AS DateTime), 5, NULL, 0, 1, 1, 2, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (33, CONVERT(TEXT, N'profile'), N'用户信息', CONVERT(TEXT, N'profile'), CAST(0x00009A7E00B61FD3 AS DateTime), 6, NULL, 0, 1, 1, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (34, CONVERT(TEXT, N'group_activist'), N'小组活跃成员', CONVERT(TEXT, N'group_activist'), CAST(0x00009A7E0116D9DC AS DateTime), 4, N'', 0, 1, 3, 7, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (35, CONVERT(TEXT, N'group_manager'), N'协作组组长', CONVERT(TEXT, N'group_manager'), CAST(0x00009A7E011E274A AS DateTime), 4, N'', 0, 1, 1, 2, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (37, CONVERT(TEXT, N'group_newbie'), N'小组最新成员', CONVERT(TEXT, N'group_newbie'), CAST(0x00009A7E011F0FF4 AS DateTime), 4, N'', 0, 1, 3, 2, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (38, CONVERT(TEXT, N'group_placard'), N'组内公告', CONVERT(TEXT, N'group_placard'), CAST(0x00009A7F010A6C1A AS DateTime), 4, N'', 0, 1, 3, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (39, CONVERT(TEXT, N'profile'), N'个人信息', CONVERT(TEXT, N'profile'), CAST(0x00009A8001135C53 AS DateTime), 3, NULL, 0, 1, 1, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (41, CONVERT(TEXT, N'user_articles'), N'分类文章列表', CONVERT(TEXT, N'user_articles'), CAST(0x00009A800117ABF0 AS DateTime), 3, NULL, 0, 1, 2, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (43, CONVERT(TEXT, N'group_list'), N'群组列表', CONVERT(TEXT, N'group_list'), CAST(0x00009A8200FC6097 AS DateTime), 7, N'', 0, 1, 2, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (44, CONVERT(TEXT, N'group_article'), N'组内文章', CONVERT(TEXT, N'group_article'), CAST(0x00009A8900E47EE9 AS DateTime), 4, N'', 0, 1, 2, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (48, CONVERT(TEXT, N'group_leaveword'), N'组内留言', CONVERT(TEXT, N'group_leaveword'), CAST(0x00009A8900E8FCB6 AS DateTime), 4, N'', 0, 1, 2, 3, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (49, CONVERT(TEXT, N'recent_topiclist'), N'组内论坛', CONVERT(TEXT, N'recent_topiclist'), CAST(0x00009A8900E969C5 AS DateTime), 4, N'', 0, 1, 2, 2, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (50, CONVERT(TEXT, N'profile'), N'个人信息', CONVERT(TEXT, N'profile'), CAST(0x00009A8C0102AF7C AS DateTime), 12, NULL, 0, 1, 1, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (51, CONVERT(TEXT, N'article_content'), N'文章内容', CONVERT(TEXT, N'article_content'), CAST(0x00009A8C0106422B AS DateTime), 12, NULL, 0, 1, 2, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (52, CONVERT(TEXT, N'article_comments'), N'文章评论', CONVERT(TEXT, N'article_comments'), CAST(0x00009A8C010657C6 AS DateTime), 12, NULL, 0, 1, 2, 2, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (54, CONVERT(TEXT, N'group_resource'), N'组内资源', CONVERT(TEXT, N'group_resource'), CAST(0x00009A9E00F8DFC3 AS DateTime), 4, N'', 0, 1, 2, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (55, CONVERT(TEXT, N'user_cate'), N'文章分类', CONVERT(TEXT, N'user_cate'), CAST(0x00009AA100EFBCD9 AS DateTime), 2, NULL, 0, 1, 1, 3, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (57, CONVERT(TEXT, N'user_resources'), N'我的资源', CONVERT(TEXT, N'user_resources'), CAST(0x00009AA100F7D647 AS DateTime), 1, N'', 0, 1, 2, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (83, CONVERT(TEXT, N'subj_list'), N'学科列表', CONVERT(TEXT, N'subj_list'), CAST(0x00009AA600B00850 AS DateTime), 7, NULL, 0, 1, 1, 2, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (105, CONVERT(TEXT, N'user_reslist'), N'资源列表', CONVERT(TEXT, N'user_reslist'), CAST(0x00009AB8010EF3D1 AS DateTime), 5, NULL, 0, 0, 2, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (310, CONVERT(TEXT, N'blog_search'), N'博客搜索', CONVERT(TEXT, N'blog_search'), CAST(0x00009AD300F828E2 AS DateTime), 3, NULL, 0, 0, 0, 0, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (316, CONVERT(TEXT, N'user_cate'), N'文章分类', CONVERT(TEXT, N'user_cate'), CAST(0x00009AD30106F2A0 AS DateTime), 3, NULL, 0, 0, 0, 0, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (483, CONVERT(TEXT, N'profile_full'), N'用户档案', CONVERT(TEXT, N'full_profile'), CAST(0x00009ADD010E7C98 AS DateTime), 6, NULL, 0, 0, 2, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (523, CONVERT(TEXT, N'group_info'), N'协作组信息', CONVERT(TEXT, N'group_info'), CAST(0x00009AEB00D87686 AS DateTime), 8, NULL, 0, 0, 1, 0, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (524, CONVERT(TEXT, N'g_art_cate'), N'文章分类', CONVERT(TEXT, N'g_art_cate'), CAST(0x00009AEB00D87686 AS DateTime), 15, NULL, 0, 0, 1, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (525, CONVERT(TEXT, N'group_info'), N'协作组信息', CONVERT(TEXT, N'group_info'), CAST(0x00009AEB00D8768B AS DateTime), 9, NULL, 0, 0, 1, 0, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (526, CONVERT(TEXT, N'group_cate'), N'资源分类', CONVERT(TEXT, N'group_cate'), CAST(0x00009AEB00D8768B AS DateTime), 9, NULL, 0, 0, 1, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (692, CONVERT(TEXT, N'profile'), N'个人信息', CONVERT(TEXT, N'profile'), CAST(0x00009BF500BA2B80 AS DateTime), 59, N'', 0, 1, 1, 1, N'', NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (693, CONVERT(TEXT, N'user_cate'), N'文章分类', CONVERT(TEXT, N'user_cate'), CAST(0x00009BF500BA2B85 AS DateTime), 59, N'', 0, 0, 1, 2, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (694, CONVERT(TEXT, N'user_stats'), N'统计信息', CONVERT(TEXT, N'user_stats'), CAST(0x00009BF500BA2B85 AS DateTime), 59, N'', 0, 1, 1, 3, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (695, CONVERT(TEXT, N'blog_search'), N'文章搜索', CONVERT(TEXT, N'blog_search'), CAST(0x00009BF500BA2B8A AS DateTime), 59, N'', 0, 0, 1, 4, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (696, CONVERT(TEXT, N'user_resources'), N'我的资源', CONVERT(TEXT, N'user_resources'), CAST(0x00009BF500BA2B8A AS DateTime), 59, N'', 0, 1, 2, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (697, CONVERT(TEXT, N'entries'), N'最新文章列表', CONVERT(TEXT, N'entries'), CAST(0x00009BF500BA2B8A AS DateTime), 59, N'', 0, 0, 2, 2, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (698, CONVERT(TEXT, N'user_leaveword'), N'我的留言', CONVERT(TEXT, N'user_leaveword'), CAST(0x00009BF500BA2B8A AS DateTime), 59, N'', 0, 0, 2, 3, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (699, CONVERT(TEXT, N'joined_groups'), N'我的协作组', CONVERT(TEXT, N'joined_groups'), CAST(0x00009BF500BA2B8A AS DateTime), 59, N'', 0, 0, 2, 4, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (700, CONVERT(TEXT, N'user_placard'), N'我的公告', CONVERT(TEXT, N'user_placard'), CAST(0x00009BF500BA2B8A AS DateTime), 59, N'', 0, 0, 3, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (701, CONVERT(TEXT, N'lastest_comments'), N'我的回复提示', CONVERT(TEXT, N'lastest_comments'), CAST(0x00009BF500BA2B8A AS DateTime), 59, N'', 0, 0, 3, 2, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (702, CONVERT(TEXT, N'friendlinks'), N'我的好友', CONVERT(TEXT, N'friendlinks'), CAST(0x00009BF500BA2B8A AS DateTime), 59, N'', 0, 0, 3, 3, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (704, CONVERT(TEXT, N'questionanswer'), N'问题与解答', CONVERT(TEXT, N'questionanswer'), CAST(0x00009BF5014223A0 AS DateTime), 59, N'', 0, 0, 0, 0, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (705, CONVERT(TEXT, N'group_cate_article'), N'文章分类', CONVERT(TEXT, N'group_cate_article'), CAST(0x00009E3901008884 AS DateTime), 8, NULL, 0, 0, 1, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (796, CONVERT(TEXT, N'group_info'), N'课题介绍', CONVERT(TEXT, N'group_info'), CAST(0x0000A1CE00F93E8F AS DateTime), 61, N'', 0, 0, 1, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (797, CONVERT(TEXT, N'group_cate'), N'资源分类', CONVERT(TEXT, N'group_cate'), CAST(0x0000A1CE00F93EAB AS DateTime), 61, N'', 0, 0, 1, 3, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (798, CONVERT(TEXT, N'group_stat'), N'统计信息', CONVERT(TEXT, N'group_stat'), CAST(0x0000A1CE00F93EAB AS DateTime), 61, N'', 0, 0, 3, 3, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (799, CONVERT(TEXT, N'group_activist'), N'课题活跃成员', CONVERT(TEXT, N'group_activist'), CAST(0x0000A1CE00F93EAB AS DateTime), 61, N'', 0, 1, 3, 7, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (800, CONVERT(TEXT, N'group_manager'), N'负责人信息', CONVERT(TEXT, N'group_manager'), CAST(0x0000A1CE00F93EAB AS DateTime), 61, N'', 0, 1, 1, 2, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (801, CONVERT(TEXT, N'group_newbie'), N'参加者信息', CONVERT(TEXT, N'group_newbie'), CAST(0x0000A1CE00F93EAB AS DateTime), 61, N'', 0, 1, 3, 2, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (802, CONVERT(TEXT, N'group_placard'), N'课题公告', CONVERT(TEXT, N'group_placard'), CAST(0x0000A1CE00F93EAB AS DateTime), 61, N'', 0, 1, 3, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (803, CONVERT(TEXT, N'group_article'), N'课题文章', CONVERT(TEXT, N'group_article'), CAST(0x0000A1CE00F93EAB AS DateTime), 61, N'', 0, 1, 2, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (804, CONVERT(TEXT, N'group_leaveword'), N'课题留言', CONVERT(TEXT, N'group_leaveword'), CAST(0x0000A1CE00F93EAB AS DateTime), 61, N'', 0, 1, 2, 3, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (805, CONVERT(TEXT, N'recent_topiclist'), N'课题研讨', CONVERT(TEXT, N'recent_topiclist'), CAST(0x0000A1CE00F93EAB AS DateTime), 61, N'', 0, 1, 2, 2, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (806, CONVERT(TEXT, N'group_resource'), N'课题资源', CONVERT(TEXT, N'group_resource'), CAST(0x0000A1CE00F93EAB AS DateTime), 61, N'', 0, 1, 2, 1, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (807, CONVERT(TEXT, N'group_mutilcates_A'), N'开题', CONVERT(TEXT, N'group_mutilcates'), CAST(0x0000A1CE00F93EAB AS DateTime), 61, N'', 0, 0, 2, 5, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (808, CONVERT(TEXT, N'group_mutilcates_B'), N'中期', CONVERT(TEXT, N'group_mutilcates'), CAST(0x0000A1CE00F93EAB AS DateTime), 61, N'', 0, 0, 2, 6, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (809, CONVERT(TEXT, N'group_mutilcates_C'), N'结题', CONVERT(TEXT, N'group_mutilcates'), CAST(0x0000A1CE00F93EAB AS DateTime), 61, N'', 0, 0, 2, 7, NULL, NULL)
INSERT [dbo].[P_Widget] ([Id], [Name], [Title], [Module], [CreateDate], [PageId], [Data], [IsHidden], [ItemOrder], [ColumnIndex], [RowIndex], [CustomTemplate], [Icon]) VALUES (810, CONVERT(TEXT, N'group_mutilcates_D'), N'成果', CONVERT(TEXT, N'group_mutilcates'), CAST(0x0000A1CE00F93EAB AS DateTime), 61, N'', 0, 0, 2, 8, NULL, NULL)
SET IDENTITY_INSERT [dbo].[P_Widget] OFF
GO


