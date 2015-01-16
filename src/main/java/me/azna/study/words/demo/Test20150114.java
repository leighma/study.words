package me.azna.study.words.demo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class Test20150114 {
	private String strFile = "E:/·leighma/words/sample.txt";
	private Map<String, Integer> map = null;
	private String mark = " ";

	public static void main(String[] args) throws IOException {
		Test20150114 test = new Test20150114();
		String content = FileUtils.readFileToString(new File(test.strFile),
				Charset.forName("iso8859-1"));
//		content = "abc def ghl abc";
		test.init();
		// System.out.println(content);
		test.fenxi(content);
		// 输出
		Iterator<Map.Entry<String, Integer>> it = test.map.entrySet()
				.iterator();
		while (it.hasNext()) {
			Map.Entry<String, Integer> entry = it.next();
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}

	}

	public void init() {
		this.map = new HashMap<String, Integer>();
	}

	public void fenxi(String content) {
		int index = content.indexOf(mark);
		String word = null;
		if (index != -1) {
			word = content.substring(0, index);
			// System.out.println(word);
			putWord(word.trim());
			content = content
					.substring(index + mark.length(), content.length());
			// System.out.println(content);
			fenxi(content);
		} else {
			word = content;
			// System.out.println(word);
			putWord(word);
		}

	}

	private void putWord(String word) {
		Integer inte = map.get(word);
		int count = 1;
		if (null != inte) {
			count = inte.intValue() + 1;
		}
		map.put(word, count);
	}

}
