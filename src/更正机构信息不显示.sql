IF  EXISTS (SELECT * FROM sysobjects WHERE id = OBJECT_ID(N'[dbo].[UpdateAllUnitPathInfo]') AND type in (N'P', N'PC'))
DROP PROCEDURE [dbo].[UpdateAllUnitPathInfo]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sysobjects WHERE id = OBJECT_ID(N'[dbo].[UpdateAllUnitPathInfo]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
CREATE PROCEDURE [dbo].[UpdateAllUnitPathInfo]

AS
BEGIN
DECLARE @ParentPathInfo VARCHAR(255)
DECLARE @ParentId int
DECLARE @UnitId int

---根机构，注意ParentId是否0还是null
SET @UnitId = (SELECT TOP 1 UnitId FROM Jitar_Unit WHERE ParentId=0)
PRINT @UnitId
UPDATE Jitar_Unit SET UnitPathInfo = ''/'' + RTRIM(LTRIM(STR(@UnitId))) + ''/'' WHERE ParentId = 0

DECLARE Unit_Cursor CURSOR FOR
SELECT DISTINCT(ParentId) FROM Jitar_Unit WHERE ParentId > 0 ORDER BY ParentId ASC
OPEN Unit_Cursor
-- 移动游标到第一行
FETCH NEXT FROM Unit_Cursor INTO @ParentId
WHILE @@FETCH_STATUS = 0
 BEGIN
   --先查到parentPathInfo
   If EXISTS(SELECT TOP 1 UnitPathInfo FROM Jitar_Unit WHERE UnitId=@ParentId)
   BEGIN
     SET @ParentPathInfo = (SELECT TOP 1 UnitPathInfo FROM Jitar_Unit WHERE UnitId=@ParentId) 
     --为了保证重新生成，则先执行清空
     UPDATE Jitar_Unit SET UnitPathInfo = '''' WHERE ParentId = @ParentId
     UPDATE Jitar_Unit SET UnitPathInfo = @ParentPathInfo WHERE ParentId = @ParentId
     UPDATE Jitar_Unit SET UnitPathInfo = UnitPathInfo + RTRIM(LTRIM(STR(UnitId))) + ''/'' WHERE ParentId = @ParentId And( UnitPathInfo NOT LIKE ''%/'' + RTRIM(LTRIM(STR(UnitId))) + ''/%'')
   END
   FETCH NEXT FROM Unit_Cursor INTO @ParentId
  END
  CLOSE Unit_Cursor
  DEALLOCATE Unit_Cursor
    
  --修正HasChild
  DECLARE Unit2_Cursor CURSOR FOR 
  SELECT UnitId FROM Jitar_Unit

  OPEN Unit2_Cursor
  -- 移动游标到第一行
  FETCH NEXT FROM Unit2_Cursor INTO @UnitId
  WHILE @@FETCH_STATUS = 0
  BEGIN
   --先查到parentPathInfo
   If EXISTS(SELECT TOP 1 * FROM Jitar_Unit WHERE ParentId=@UnitId)
   BEGIN
    UPDATE Jitar_Unit SET HasChild = 1 WHERE UnitId = @UnitId
   END
   FETCH NEXT FROM Unit2_Cursor INTO @UnitId
  END
  CLOSE Unit2_Cursor
  DEALLOCATE Unit2_Cursor
  
END

' 
END
GO


EXECUTE UpdateAllUnitPathInfo

GO

DROP PROCEDURE [dbo].[UpdateAllUnitPathInfo]
GO

--修正用户信息
UPDATE Jitar_User SET UnitPathInfo = (SELECT TOP 1 UnitPathInfo FROM Jitar_Unit WHERE Jitar_Unit.UnitId = Jitar_User.UnitId)
GO
--修正文章信息
  
UPDATE Jitar_Article SET OrginPath = (SELECT TOP 1 UnitPathInfo FROM Jitar_User WHERE Jitar_User.UserId = Jitar_Article.UserId)
GO

UPDATE Jitar_Article SET UnitPathInfo = (SELECT TOP 1 UnitPathInfo FROM Jitar_User WHERE Jitar_User.UserId = Jitar_Article.UserId)
GO

UPDATE Jitar_Article SET ApprovedPathInfo = (SELECT TOP 1 UnitPathInfo FROM Jitar_User WHERE Jitar_User.UserId = Jitar_Article.UserId) WHERE Jitar_Article.ApprovedPathInfo IS NOT NULL
GO

UPDATE Jtr_Resource SET ApprovedPathInfo = (SELECT TOP 1 UnitPathInfo FROM Jitar_User WHERE Jitar_User.UserId = Jtr_Resource.UserId) WHERE Jtr_Resource.ApprovedPathInfo IS NOT NULL
GO

UPDATE Jtr_Resource SET UnitPathInfo = (SELECT TOP 1 UnitPathInfo FROM Jitar_User WHERE Jitar_User.UserId = Jtr_Resource.UserId) WHERE Jtr_Resource.UnitPathInfo IS NOT NULL
GO

UPDATE Jtr_Resource SET OrginPathInfo = (SELECT TOP 1 UnitPathInfo FROM Jitar_User WHERE Jitar_User.UserId = Jtr_Resource.UserId) WHERE Jtr_Resource.OrginPathInfo IS NOT NULL
GO

