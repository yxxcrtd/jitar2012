package cn.edustar.jitar.converter;

import java.io.File;
import java.io.IOException;


public class SWFToolsSWFConverter implements SWFConverter{

	private String PDF2SWF_PATH = "E:\\SWFTools\\pdf2swf.exe";
		
	public SWFToolsSWFConverter()
	{
	}
	
	public SWFToolsSWFConverter(String pdf2swfPath)
	{
		this.PDF2SWF_PATH = pdf2swfPath;
	}
	public void convert2SWF(String inputFile, String swfFile) {
		File pdfFile = new File(inputFile);
		File outFile = new File(swfFile);
		if(!inputFile.endsWith(".pdf")){
			System.out.println("文件格式非PDF！");
			return ;
		}
		if(!pdfFile.exists()){
			System.out.println("PDF文件不存在！");
			return ;
		}
		if(outFile.exists()){
			outFile.delete();
		}
		String command = PDF2SWF_PATH +" \""+inputFile+"\" -o \""+swfFile+"\" -T 9 -f";
		try {
			//System.out.println("开始转换文档: "+inputFile);
			Runtime.getRuntime().exec(command);
			//System.out.println("成功转换为SWF文件！");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("转换文档为swf文件失败！");
		}
		
	}

	@Override
	public void convert2SWF(String inputFile) {
		String swfFile = FileUtils.getFilePrefix(inputFile)+".swf";
		convert2SWF(inputFile,swfFile);
	}

}
