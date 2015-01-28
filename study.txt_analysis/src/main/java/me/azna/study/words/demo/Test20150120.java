package me.azna.study.words.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Test20150120 {
	private String strFile = "E:/·leighma/copyout/0120/3.3.txt";
	private Map<String, Integer> map = null;
	private String mark = " ";
	private String[] stops = { ".", ",", ";", "/", " " };

	public static void main(String[] args) throws IOException {
		Test20150120 test = new Test20150120();
		test.init();
		BufferedReader br = new BufferedReader(new FileReader(new File(
				test.strFile)));
		String strLine = null;
		try {
			while ((strLine = br.readLine()) != null) {
				test.analysis(strLine);
			}
		} finally {
			if (br != null)
				br.close();
		}
		// test.showMap();
		test.sort();

	}

	public void init() {
		this.map = new HashMap<String, Integer>();
	}

	public void analysis(String content) {
		int index = content.indexOf(mark);
		String word = null;
		if (index != -1) {
			word = content.substring(0, index);
			// System.out.println(word);
			putWord(word.trim());
			content = content
					.substring(index + mark.length(), content.length());
			// System.out.println(content);
			analysis(content);
		} else {
			word = content;
			// System.out.println(word);
			putWord(word);
		}

	}

	private void putWord(String word) {
		for (String ch : stops) {
			if (ch.equals(word))
				return;
			else
				word = filterWord(word, ch);
		}
		putToMap(word);
	}

	private void putToMap(String word) {
		int count = 1;
		if (map.containsKey(word)) {
			count = map.get(word) + 1;
		}
		map.put(word, count);
	}

	public String filterWord(String word, String ch) {
		if (word.lastIndexOf(ch) != -1) {
//			System.out.println("stop :" + word);
			word = word.substring(0, word.length() - 1);
		}
		return word;
	}

	public void sort() {
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(
				map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}

		});
		for(Map.Entry<String,Integer> entry:list){
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}

	public void showMap() {
		Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Integer> entry = it.next();
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}

}
