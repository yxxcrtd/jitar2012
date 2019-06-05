package cn.edustar.jitar.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import cn.edustar.jitar.ex.XmldocException;

/**
 * 使用 dom4j 操作 xml 文档的辅助类
 * 
 *
 */
public class XmlUtil {
	/**
	 * 从指定文件加载 XML 文档
	 * 
	 * @param fileName
	 * @return
	 * @exception XmldocException - 操作 XML 文档发生错误的时候产生。
	 */
	public static Document loadXml(String fileName) throws XmldocException {
		try {
			SAXReader reader = new SAXReader();
			//reader.setEncoding("UTF-8");
			Document xmldoc = reader.read(new File(fileName));
			return xmldoc;
		} catch (DocumentException ex) {
			throw new XmldocException("不能加载 XML 文档从 " + fileName, ex);
		}
	}
	
	/**
	 * 将指定的 XML 文档保存到指定文件
	 * 
	 * @param xmldoc
	 * @param fileName
	 * @throws XmldocException
	 */
	public static final void saveXml(Document xmldoc, String fileName) throws XmldocException {
		java.io.FileWriter writer = null;
		try {
			writer = new java.io.FileWriter(fileName);
			xmldoc.write(writer);
			writer.close();
		} catch (java.io.IOException ex) {
			throw new XmldocException("不能保存 XML 文档到文件 " + fileName, ex);
		} finally {
			if (writer != null) {
				try { writer.close(); } catch(java.io.IOException ex) { } // ignore ex
			}
		}
	}
	
	/**
	 * @param xmldoc
	 * @param fileName
	 * @throws XmldocException
	 */
	public static final void saveXml_UTF8(Document xmldoc, String fileName) throws XmldocException {
		try {			
			@SuppressWarnings("unused")
			StringBuffer buf1 = new StringBuffer();
			OutputStream os1= new FileOutputStream(fileName);
			OutputStreamWriter osw1 = new OutputStreamWriter(os1,"UTF-8");
			BufferedWriter fw1=new BufferedWriter(osw1);
			fw1.write(new String(xmldoc.asXML().getBytes("UTF-8"),"UTF-8"));
			fw1.flush();
			fw1.close();
			fw1=null;
		} catch (java.io.IOException ex) {
			throw new XmldocException("不能保存 XML 文档到文件 " + fileName, ex);
		} 
	}

	
	/**
	 * 给指定文档的指定节点修改/添加文字内容
	 * 
	 * @param xmldoc
	 * @param nodeXpath
	 * @param nodeText
	 */
	public static final void setNodeText(Document xmldoc, String nodeXpath, String nodeText) {
		if (xmldoc == null || nodeXpath == null) return;
		Node node = xmldoc.selectSingleNode(nodeXpath);
		if (node == null) {
			// 如果节点不存在则创建
			node = xmldoc.getRootElement().addElement(nodeXpath);
		}
		if (node.getNodeType() != Node.ELEMENT_NODE) return;
		
		Element elem = (Element)node;
		elem.clearContent();
		elem.addCDATA(nodeText);
	}

	/**
	 * 得到指定节点的文字内容
	 * 
	 * @param xmldoc
	 * @param nodeXpath
	 * @return
	 */
	public static final String getNodeText(Document xmldoc, String nodeXpath) {
		if (xmldoc == null || nodeXpath == null) return null;
		Node node = xmldoc.selectSingleNode(nodeXpath);
		if (node == null) return null;
		return node.getText();
	}
	
}
