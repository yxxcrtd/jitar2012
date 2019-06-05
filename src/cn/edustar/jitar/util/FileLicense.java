// created by JCOMGen
package cn.edustar.jitar.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileLicense {
	private static final Log log = LogFactory.getLog(FileLicense.class);

	@SuppressWarnings("unchecked")
	private Map m_kv = null;

	@SuppressWarnings("unchecked")
	public FileLicense() {
		m_kv = new HashMap();
	}

	public boolean Load(String fileName) {
		// 1. 读取文件内容。
		byte[] encry_content = ReadFile(fileName);
		if (encry_content == null) {
			return false;
		}
		//log.info("encry_content.length=" + encry_content.length);

		/*
		 * for(int i=0;i<encry_content.length;i++) {
		 * log.info(i+"="+encry_content[i]); }
		 */

		// 2. 解密文件
		String strContent = "";
		strContent = RC4_Decry(encry_content, "www.chinaedustar.com");

		// System.out.println("---------------------------------------");
		// System.out.println(strContent);
		// System.out.println("---------------------------------------");

		// ' 3. 解析文件中的内容成为一组 key:value 对。忽略掉注释。
		ParseLicense(strContent);
		return true;
	}

	// ' 解析一段正文为 key:value 对。
	private void ParseLicense(String strContent) {
		int pos = 0;
		int next_pos = 0;
		String strLine = "";
		while (true) {
			next_pos = strContent.indexOf("\n", pos);
			if (next_pos > 0) {
				strLine = strContent.substring(pos, next_pos - 1);
				strLine = strLine.trim();
				ParseLine(strLine);
				pos = next_pos + 1;
			} else {
				strLine = strContent.substring(pos);
				strLine = strLine.trim();
				ParseLine(strLine);
				break;
			}
		}
	}

	private void ParseLine(String strLine) {
		while (true) {
			if (strLine.equals("")) {
				return;
			}
			String ch = strLine.substring(0, 1);
			if (ch.equals(" ") || ch.equals("\n") || ch.equals("\r")) {
				strLine = strLine.substring(1);
			} else {
				break;
			}
			if (strLine.equals(""))
				return;
		}

		// RTrim
		while (true) {
			if (strLine.equals(""))
				return;
			String ch = strLine.substring(strLine.length() - 1);
			if (ch.equals(" ") || ch.equals("\n") || ch.equals("\r"))
				strLine = strLine.substring(0, strLine.length() - 1);
			else
				break;
			if (strLine.equals(""))
				return;
		}

		strLine = strLine.trim();
		if (strLine.equals(""))
			return;
		if (strLine.subSequence(0, 1).equals("#"))
			return;// ' 注释行
		// 解析 key=value 对
		// System.out.println(strLine);
		int pos = strLine.indexOf("=");
		// System.out.println("pos="+pos);
		if (pos == -1) {
			AddKeyValue(strLine, "");
		} else {
			AddKeyValue(strLine.substring(0, pos), strLine.substring(pos + 1));
		}
	}

	@SuppressWarnings("unchecked")
	private void AddKeyValue(String sKey, String value) {
		// System.out.println("sKey="+sKey);
		// System.out.println("value="+value);
		m_kv.put(sKey, value);
	}

	@SuppressWarnings({ "unchecked", "null" })
	public List GetAllKeys() {
		List list = null;
		if (m_kv == null)
			return list;

		int mapsize = m_kv.size();

		Iterator keyValuePairs1 = m_kv.entrySet().iterator();
		for (int i = 0; i < mapsize; i++) {
			Map.Entry entry = (Map.Entry) keyValuePairs1.next();
			Object key = entry.getKey();
			list.add(key);
		}
		return list;
	}

	public String GetValue(String strKey) {
		if (m_kv == null)
			return "";
		Object t = m_kv.get(strKey);
		if (t == null)
			return "";
		return t.toString();
	}

	private String RC4_Decry(byte[] decry, String password) {
		byte[] Key;
		Key = password.getBytes();
		byte[] result = RC4(decry, Key);
		if (result != null) {
			char[] data = getChars(result);
			String s = String.copyValueOf(data);
			return s;
		} else {
			return "";
		}
	}

	private byte[] RC4(byte[] expr, byte[] Key) {
		@SuppressWarnings("unused")
		byte[] _expr = null;
		int[] RB = new int[256];
		for (int X = 0; X <= 255; X++) {
			RB[X] = X;
		}
		int X = 0, Y = 0, Z = 0;
		int Len_password;
		Len_password = Key.length;
		for (X = 0; X <= 255; X++) {
			Y = (Y + RB[X] + Key[X % Len_password]) % 256;
			// System.out.println( "X=" + X + ",Y=" + Y);
			int Temp = RB[X];
			RB[X] = RB[Y];
			RB[Y] = Temp;
		}

		X = 0;
		Y = 0;
		Z = 0;
		int Len_expr = expr.length;
		// Debug.Print "Len_expr = " & Len_expr
		for (X = 0; X < Len_expr; X++) {
			Y = (Y + 1) % 256;
			Z = (Z + RB[Y]) % 256;
			int Temp = RB[Y];
			RB[Y] = RB[Z];
			RB[Z] = Temp;
			// System.out.println("X=" + X + ", Y=" + Y + ", Z=" + Z);
			// System.out.println("RB[X]="+RB[X]);
			// System.out.println("RB[Y]="+RB[Y]);
			// System.out.println("RB[Z]="+RB[Z]);
			// System.out.println(",RB[Y] + RB[Z]=" + (RB[Y] +
			// RB[Z])+",(RB[Y] + RB[Z])%256=" + ((RB[Y] + RB[Z])%256));
			expr[X] = (byte) (expr[X] ^ (RB[(RB[Y] + RB[Z]) % 256]));
		}

		return expr;
	}

	private byte[] ReadFile(String file) {
		File aFile = new File(file);
		FileInputStream inFile = null;
		try {
			inFile = new FileInputStream(aFile);
		} catch (FileNotFoundException ex) {
			return null;
		}
		BufferedInputStream bin = new BufferedInputStream(inFile);
		byte[] b = new byte[(int) aFile.length()];
		try {
			bin.read(b);
		} catch (java.io.IOException ex) {
			return null;
		} finally {
			if (bin != null)
				try {
					bin.close();
				} catch (java.io.IOException ex) {
				}
			if (inFile != null)
				try {
					inFile.close();
				} catch (java.io.IOException ex) {
				}
		}
		return b;
	}

	public byte[] getBytes(char[] chars) {
		Charset cs = Charset.forName("GBK");
		CharBuffer cb = CharBuffer.allocate(chars.length);
		cb.put(chars);
		cb.flip();
		ByteBuffer bb = cs.encode(cb);
		return bb.array();
	}

	public char[] getChars(byte[] bytes) {
		// System.out.println("bytes.length="+bytes.length);
		Charset cs = Charset.forName("GBK");
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.put(bytes);
		bb.flip();
		CharBuffer cb = cs.decode(bb);
		return cb.array();
	}

}
