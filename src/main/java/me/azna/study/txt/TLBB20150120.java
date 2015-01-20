package me.azna.study.txt;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class TLBB20150120 {
	private String strFile = "E:/·leighma/copyout/0120/天龙八部（世纪新修版）.txt";
	private List<String> list = null;

	public static void main(String[] args) throws IOException {
		TLBB20150120 test = new TLBB20150120();
		String content = FileUtils.readFileToString(new File(test.strFile),
				Charset.forName("GBK"));
		test.init();
		test.analysis(content);
//		test.showList();
		test.t1();

	}

	public void init() {
		this.list = new ArrayList<String>();
	}

	public void analysis(String content) {
		String mark = "^[一,二,三,四,五,六,七,八,九,十]{1,3}";
		// reg = "^[一,二,三,四,五,六,七,八,九,十]{1,3}(.*?)^[一,二,三,四,五,六,七,八,九,十]{1,3}";
		// 上面这个没加后向引用，匹配到的一条会占用后面的编号，所以整体智能匹配到一半的章节，
		String reg = mark + "(.+?)(?=" + mark + ")" + "|" + mark
				+ "(.+?)(?=（全书完）)";
		Pattern p = Pattern.compile(reg, Pattern.MULTILINE | Pattern.DOTALL);
		Matcher m = p.matcher(content);
		while (m.find()) {
			list.add(m.group());
		}

	}

	public int pinlv(String content, String word) {
		int count = 0;
		String reg = "(" + word + ")";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(content);
		// 没有一次匹配的方法么？
		while (m.find()) {
			count ++;
		}
		return count;
	}

	public void t1() {
		String name = "段誉";
		for (String str : list) {
			System.out.println(str.substring(0, 5) + " " + pinlv(str, name));
		}
	}

	public void showList() {
		System.out.println(list.size());
		for (String str : list) {
			System.out.println(str.substring(0, 5) + " " + str.length());
		}
	}

}
