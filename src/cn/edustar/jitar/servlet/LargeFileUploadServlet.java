package cn.edustar.jitar.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * @author yxx
 */
public class LargeFileUploadServlet extends HttpServlet {

	/** serialVersionUID */
	private static final long serialVersionUID = -7431314770747340663L;
	/**
	 * Constructor of the object.
	 */
	public LargeFileUploadServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}


	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		/*
		 * 这里有验证是否登录的过程吗？？
		 * 要是没有，暂时停用
		 */
		
		if(true)
		{
			return;
		}
		
		
		boolean bLastChunk = false;
		int numChunk = 0;
		String username =null;
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		request.setCharacterEncoding("UTF-8");
		try {
			Enumeration paraNames = request.getParameterNames();
			//out.println("Parameters: ");
			String pname;
			String pvalue;
			while (paraNames.hasMoreElements()) {
				pname = (String) paraNames.nextElement();
				pvalue = request.getParameter(pname);
				//out.println("" + pname + " = " + pvalue);
				if (pname.equals("jufinal")) {
					bLastChunk = pvalue.equals("1");
				} else if (pname.equals("jupart")) {
					numChunk = Integer.parseInt(pvalue);
				}
				else if (pname.equals("user")) {
					username = pvalue;
				}
			}
			String ourTempDirectory = getServletContext().getRealPath("/");
			
			//验证是否是分布式资源上载
			String fileUserConfigPath = getServletContext().getInitParameter("userPath");
			if(fileUserConfigPath=="" || fileUserConfigPath==null)
				ourTempDirectory=ourTempDirectory+"user\\"+username+"\\resource\\";
			else
			{
				if(!fileUserConfigPath.endsWith("\\"))
					fileUserConfigPath=fileUserConfigPath+"\\";
				ourTempDirectory=fileUserConfigPath+"user\\"+username+"\\resource\\";
			}
			
			//ourTempDirectory=ourTempDirectory+"user\\"+username+"\\resource\\";
			//out.println(ourTempDirectory);
			int ourMaxMemorySize = 10000000;
			int ourMaxRequestSize = 2000000000;
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(ourMaxMemorySize);
			factory.setRepository(new File(ourTempDirectory));
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(ourMaxRequestSize);
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			FileItem fileItem;
			File fout;
			out.println(" Let's read input files ...");
			while (iter.hasNext())
			{
				fileItem = (FileItem) iter.next();
				if (fileItem.isFormField()) {
					out.println(" (form field) "+ fileItem.getFieldName() + " = "+ fileItem.getString());
					if (fileItem.getFieldName().equals("md5sum[]")) {
						out.println(" ------------------------------ ");
						//System.out.println(" ------------------------------ ");
					}
				} else {
					out.println("FieldName: "+ fileItem.getFieldName());
					out.println("File Name: "+ fileItem.getName());
					out.println("ContentType: "+ fileItem.getContentType());
					out.println("Size (Bytes): "+ fileItem.getSize());
					/**
					System.out.println("FieldName: "+ fileItem.getFieldName());
					System.out.println("File Name: "+ fileItem.getName());
					System.out.println("ContentType: "+ fileItem.getContentType());
					System.out.println("Size (Bytes): "+ fileItem.getSize());
					**/
					String uploadedFilename = fileItem.getName()+ (numChunk > 0 ? ".part" + numChunk : "");
					fout = new File(ourTempDirectory+ (new File(uploadedFilename)).getName());
					out.println("File Out: "+ fout.toString());
					/**
					System.out.println("File Out: "+ fout.toString());
					**/
					fileItem.write(fout);
					out.println("Let's write a status, to finish the server response :");
					out.println("SUCCESS");
					out.println("End of server treatment ");
					if (bLastChunk) {
						
						out.println("Last chunk received: let's rebuild the complete file" +" ("+ fileItem.getName() + ")");
						FileInputStream fis;
						FileOutputStream fos = new FileOutputStream(ourTempDirectory + fileItem.getName());
						int nbBytes;
						byte[] byteBuff = new byte[1024];
						String filename;
						for (int i = 1; i <= numChunk; i += 1) {
							filename = fileItem.getName() + ".part" + i;
							fis = new FileInputStream(ourTempDirectory+ filename);
							while ((nbBytes = fis.read(byteBuff)) >= 0) {
								fos.write(byteBuff, 0, nbBytes);
							}
							fis.close();
							File file= new File(ourTempDirectory+filename);
							file.delete();

						}
						fos.close();
						
					}
					fileItem.delete();
				}
			}
		
			
		} catch (Exception e) {
			out.println("ERROR: Exception e = " + e.toString());
			
		}
	}
	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
