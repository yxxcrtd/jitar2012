package cn.edustar.jitar.util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * 读取和设置ini配置文件 以“#”号开头的行被认为是注释行
 * 对文件写操作时，文件或者section或者Key不存在时，会自动新建
 *  @author 曾一迅51Testing软件测试网
 *  @version 1.0
 * 下个版本可增加删除section、key操作及得到整个section的所有key及value
 *
*/
public final class ConfigurationFileIni {
/**
	* 读取ini配置文件中变量的值
	*
	* @param filename
	*            配置文件的路径
	* @param sectionname
	*            要读取的变量所在段名称
	* @param Key
	*            要读取的变量名称
	* @param defaultValue
	*            变量名不存在时的默认值
	* @throws IOException
	*             抛出文件操作可能出现的io异常
*/
	 public static String getValue(String filename, String sectionname,String Key, String defaultValue) throws IOException {
		 boolean isInSection = false;
		 StringBuffer section = new StringBuffer().append("[").append(sectionname).append("]");
		 String line, value = defaultValue;
		 BufferedReader reader = new BufferedReader(new FileReader(filename));
	  try {
		  while ((line = reader.readLine()) != null) {
			  line = line.trim();
			  if (line.indexOf('#') == 0)
				  continue;
			  line = line.replaceAll("\r", "");
			  line = line.replaceAll("\n", "");
			  if (line == "")
				  continue;
			  if ((line.matches("\\[.*\\]")) && (isInSection))
				  break;
			  if (section.toString().equals(line)) {
				  isInSection = true;
				  continue;
			  }
			  if (line.matches(".*=.*") && (isInSection)) {
				  int i = line.indexOf('=');
				  String name = line.substring(0, i).trim();
				  if (name.equals(Key)) {
					  value = line.substring(i + 1).trim();
					  break;
				  } else {
					  continue;
				  }
			  }
		  }
	  } catch (IOException ex) {
	   throw ex;
	  } finally {
	   reader.close();
	  }
	  return value;
	 }
	 
	 /**
	  * 修改ini配置文件中变量的值
	  *
	  * @param filename
	  *            配置文件的路径
	  * @param sectionname
	  *            要修改的变量所在段名称
	 * @param Key
	  *            要修改的变量名称
	  * @param value
	  *            变量的新值
	  * @throws IOException
	  *             抛出文件操作可能出现的io异常
	*/
	 public static boolean setValue(String filename, String sectionname,	String Key, String value) throws IOException {
		 String line, strLine;
		 StringBuffer allLine = new StringBuffer();
		 boolean isInSection = false, isHaveKey = false, isModifyOK = false, isFoundSection = false;
		 StringBuffer section = new StringBuffer().append("[").append(sectionname).append("]");
		 BufferedReader reader;
		 try {
			 reader = new BufferedReader(new FileReader(filename));
		 } catch (FileNotFoundException e) {
			 File file = new File(filename);
			 if (!file.exists()) {
				 try {
					 file.createNewFile();
				 } catch (IOException ex) {
				 }
			 }
		 }
		 reader = new BufferedReader(new FileReader(filename));
	  try {
	   while ((line = reader.readLine()) != null) {
	   	line = line.replaceAll("\r", "");
	   	line = line.replaceAll("\n", "");
	   	strLine = line.trim();
	   	if (strLine.indexOf('#') == 0) {
	   		allLine.append(line).append("\r\n");
	   		continue;
	   	}
	   	if (strLine == "") {
	   		allLine.append(line).append("\r\n");
	   		continue;
	   	}
	   	if ((strLine.matches("\\[.*\\]")) && (isInSection)) {
	   		if (!isHaveKey) {
	   			allLine.append(Key).append("=").append(value).append("\r\n");
	   			isHaveKey = true;
	   		}
	   		allLine.append(line).append("\r\n");
	   		isInSection = false;
	   		continue;
	   	}
	   	if (section.toString().equals(strLine)) {
	   		isInSection = true;
	   		isFoundSection = true;
	   		allLine.append(line).append("\r\n");
	   		continue;
	   	}
	   	if (strLine.matches(".*=.*") && (isInSection)) {
	   		int i = strLine.indexOf('=');
				String name = strLine.substring(0, i).trim();
				if (name.equals(Key)) {
					allLine.append(name).append(" = ").append(value).append("\r\n");
					isHaveKey = true;
					continue;
				}
	   	}
	   	allLine.append(line).append("\r\n");
	   }
	   if (!isFoundSection) {
	    allLine.append(section.toString()).append("\r\n");
	   }
	   if (!isHaveKey) {
	    allLine.append(Key).append(" = ").append(value).append("\r\n");
	    isHaveKey = true;
	   }
	} catch (IOException ex) {
	   throw ex;
	} finally {
	   reader.close();
	}

	// 修改完成，开始覆写文件
	  BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename, false));
	  try {
		  bufferedWriter.write(allLine.toString());
		  bufferedWriter.flush();
		  isModifyOK = true;
	  } catch (IOException ex) {
		  isModifyOK = false;
	throw ex;
	} finally {
		bufferedWriter.close();
	  }
	return isModifyOK;
	}
}
