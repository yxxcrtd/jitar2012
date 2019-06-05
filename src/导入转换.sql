
      INSERT INTO T_Task([T_Src]
      ,[T_Tar]
      ,[T_Status]
      ,[T_From]
      ,[T_Object]
      ,[T_Name]
      ,[T_Sign]
      ,[T_MStatus]
      ,[T_CreateTime]
      ,[T_RunCount]
      ,[T_IsDeleteSrc]
      ,[T_RunTime]
      ,[T_ImgSize])
       SELECT  '/root/hba/Edustar/jitar/' + Href, 
       SUBSTring('/root/hba/Edustar/jitar/' + Href,1,CHARINDEX('.','/root/hba/Edustar/jitar/' + Href)) + 'swf',
       0,
       1,
       0,
       Title,
       0,
       0,
       GETDATE(),
       0,
       0,
       GETDATE(),
       '120*100'       
       FROM jGroup2.dbo.Jtr_Resource
       WHERE (RIGHT(Href,4) = '.ppt' or RIGHT(Href,4) = '.pptx' or
       RIGHT(Href,4) = '.doc' or RIGHT(Href,4) = '.docx' or
       RIGHT(Href,4) = '.xls' or RIGHT(Href,4) = '.xlsx' ) AND
       LEN(Title) < 100
       order by downloadCount DESC