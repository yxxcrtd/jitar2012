package cn.edustar.jitar.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

/**
 * 中文分词
 *
 * @author Yang Xinxin
 */
public class ChineseAnalyzer {	
	static File fDir = new File(File.separator);
	private static String Dest_Index_Path = fDir.toString() + "tmp";
	
	@SuppressWarnings("deprecation")
	public static String splitString(String str) {
		String chinesedetail = str;
		String string = "";
		try {
			Analyzer TextAnalyzer = new MMAnalyzer();
			IndexWriter TextIndex = new IndexWriter(Dest_Index_Path, TextAnalyzer, true);
			Document document = new Document();
			Field field_content = new Field("content", chinesedetail, Field.Store.YES, Field.Index.TOKENIZED);
			document.add(field_content);
			TextIndex.addDocument(document);
			TokenStream stream = TextAnalyzer.tokenStream("content", new StringReader(chinesedetail));
			while (true) {
				Token item = stream.next();
				if (null == item)
					break;
				if (item.termLength() > 1) {
					string += item.termText() + ",";
				}
			}
			TextIndex.optimize();
			TextIndex.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return string;
	}

}
