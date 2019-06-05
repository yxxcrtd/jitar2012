package cn.edustar.jitar.converter;

import java.io.File;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class JacobPDFConverter implements PDFConverter {
	private static final int wdFormatPDF = 17;
	private static final int xlTypePDF = 0;
	private static final int ppSaveAsPDF = 32;
	private static final int msoTrue = -1;
	private static final int msofalse = 0;
	
	public void convert2PDF(String inputFile, String pdfFile) {
		String suffix = FileUtils.getFileSufix(inputFile);
		File file = new File(inputFile);
		if(!file.exists()){
			System.out.println( inputFile + " 文件不存在！");
			return;
		}
		if(suffix.equals("pdf")){
			System.out.println("PDF not need to convert!");
			return ;
		}
		if(suffix.equals("doc")||suffix.equals("docx")||suffix.equals("txt")){
			word2PDF(inputFile,pdfFile);
		}else if(suffix.equals("ppt")||suffix.equals("pptx")){
			ppt2PDF(inputFile,pdfFile);
		}else if(suffix.equals("xls")||suffix.equals("xlsx")){
			excel2PDF(inputFile,pdfFile);
		}else{
			System.out.println(suffix + "文件格式不支持转换!");
		}
	}

	public void convert2PDF(String inputFile) {
		String pdfFile = FileUtils.getFilePrefix(inputFile)+".pdf";
		convert2PDF(inputFile,pdfFile);
		
	}
	
	public static void word2PDF(String inputFile,String pdfFile){
		//打开word应用程序
		ActiveXComponent app = new ActiveXComponent("Word.Application");
		Dispatch docs = null;
		Dispatch doc = null;
		//设置word不可见
		app.setProperty("Visible", false);
		app.setProperty("DisplayAlerts", false);
		app.setProperty("AutomationSecurity", new Variant(3)); //禁用宏 
		try
		{
    		//获得word中所有打开的文档,返回Documents对象
    		docs = app.getProperty("Documents").toDispatch();
    		//调用Documents对象中Open方法打开文档，并返回打开的文档对象Document
    		doc = Dispatch.call(docs,
    									"Open",
    									inputFile,
    									false,
    									true
    									).toDispatch();
    		
    		
    		//调用Document对象的SaveAs方法，将文档保存为pdf格式
    		
    		Dispatch.call(doc,
    					"SaveAs",
    					pdfFile,
    					wdFormatPDF,		//word保存为pdf格式宏，值为17
    					true
    					);
    					
    		/*Dispatch.call(doc,
    				"ExportAsFixedFormat",
    				pdfFile,
    				wdFormatPDF		//word保存为pdf格式宏，值为17
    				);*/
		}
		catch(Exception ex){
		    System.out.println("转换文件 " + inputFile + " 失败:\r\n" + ex.getMessage());
		}
		finally{
            //关闭文档
		    if(doc != null)
		    {
		        Dispatch.call(doc, "Close",false);
		    }
		    doc = null;
		    docs = null;
    		//关闭word应用程序
    		app.invoke("Quit", 0);
    		app = null;
		}		
	}
	public static void excel2PDF(String inputFile,String pdfFile){
		ActiveXComponent app = new ActiveXComponent("Excel.Application");
		app.setProperty("Visible", false);
		app.setProperty("DisplayAlerts", false);
		Dispatch excels = app.getProperty("Workbooks").toDispatch();
		Dispatch excel = Dispatch.call(excels,
									"Open",
									inputFile,
									false,
									true
									).toDispatch();
		Dispatch.call(excel,
					"ExportAsFixedFormat",
					xlTypePDF,		
					pdfFile
					);
		Dispatch.call(excel, "Close",false);
		excel = null;
		excels = null;
		app.invoke("Quit");
		app = null;
		ComThread.Release();//关闭进程
	}
	
	//这个是原有的方法，但是不能关闭PowerPoint.exe进程。
	/*
	public static void ppt2PDF(String inputFile,String pdfFile){		
		ActiveXComponent app = new ActiveXComponent("PowerPoint.Application");
		//app.setProperty("Visible", msofalse);
		//app.setProperty("Visible", false);
		app.setProperty("DisplayAlerts", false);
		Dispatch ppts = app.getProperty("Presentations").toDispatch();		
		Dispatch ppt = Dispatch.call(ppts,
									"Open",
									inputFile,
									true,//ReadOnly
									true,//Untitled指定文件是否有标题
									0//WithWindow指定文件是否可见
									).toDispatch();		
		Dispatch.call(ppt,
					"SaveAs",
					pdfFile,
					ppSaveAsPDF	
					);				
		Dispatch.call(ppt, "Close");		
		app.invoke("Quit");		
	}
*/
	
	public static void ppt2PDF(String inputFile,String pdfFile){	
		//ComThread.InitMTA(); 不能加这个
		ActiveXComponent app = null;
		ActiveXComponent ppts = null;
		ActiveXComponent ppt = null;
		try
		{
			app = new ActiveXComponent("PowerPoint.Application");
			Dispatch.put(app, "Visible", new Variant(true)); 
			app.setProperty("DisplayAlerts", false);
			//Dispatch ppts = app.getProperty("Presentations").toDispatch();		
			/*Dispatch ppt = Dispatch.call(ppts,
										"Open",
										new Variant(inputFile),
										Variant.VT_TRUE,//ReadOnly
										Variant.VT_TRUE,//Untitled指定文件是否有标题
										Variant.VT_FALSE//WithWindow指定文件是否可见
										).toDispatch();*/
			ppts = app.getPropertyAsComponent("Presentations");
			ppt = ppts.invokeGetComponent("Open",new Variant(inputFile), new Variant(true), new Variant(true), new Variant(false));
			ppt.invoke("SaveAs",new Variant(pdfFile),new Variant(ppSaveAsPDF)); //ppSaveAsPDF=32
			//Dispatch.call(ppt,"SaveAs",new Variant(pdfFile),ppSaveAsPDF);				
			Dispatch.call(ppt, "Close");		
		}
		catch(Exception e)
		{
			System.out.println("转换  " + inputFile + " 失败。");
			//e.printStackTrace();
		}
		finally
		{
			ppt = null;
			ppts = null;
			if(app !=null)
			{
				app.invoke("Quit");
				app = null;
			}
		   //ComThread.Release();//不能这样加
		   //SystemUtil.killProcessByName("POWERPNT.EXE");		   
		}
	}
}
