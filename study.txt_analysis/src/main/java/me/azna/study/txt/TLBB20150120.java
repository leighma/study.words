package me.azna.study.txt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class TLBB20150120 {
	private String strFile = "E:/·leighma/copyout/0127/天龙八部（世纪新修版）.txt";
	private String nameFile = "E:/·leighma/copyout/0127/天龙八部人名20150127.txt";
	private List<String> list = null;

	public static void main(String[] args) throws IOException {
		TLBB20150120 test = new TLBB20150120();
		String content = FileUtils.readFileToString(new File(test.strFile),
				Charset.forName("GBK"));
		test.init();
		test.analysis(content);
		// test.showList();
		test.t2();

	}

	public void init() {
		this.list = new ArrayList<String>();
	}

	public void analysis(String content) {
		String mark = "^[一,二,三,四,五,六,七,八,九,十]{1,3}";
		mark = "^[一二三四五六七八九十]{1,3}";
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

	/**
	 * 词频统计，人名在章节中出现的次数
	 * @param content
	 * @param word
	 * @return 词频
	 */
	public int ciPin(String content, String word) {
		int count = 0;
		String reg = "(" + word + ")";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(content);
		// 没有一次匹配的方法么？
		while (m.find()) {
			count++;
		}
		return count;
	}

	/**
	 * 人名在全书中每章出现的词频汇总，最后一位带了汇总数字，以","分隔
	 * @param name
	 * @return 名词频串
	 */
	public String t1(String name) {
		StringBuilder sb = new StringBuilder();
		sb.append(name).append(",");
		int sum = 0;
		for (String str : list) {
			int count = ciPin(str, name);
			sb.append(count).append(",");
			sum += count;
		}
		sb.append(sum);
		return sb.toString();
	}

	/**
	 * 读入人名文件，统计每个人名的出现频率
	 * TODO 应该变成内部的collection，list，如果还有其他用途的话
	 * TODO 也可以一边读一边写文件
	 * @throws IOException
	 */
	public void t2() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				new File(nameFile)));
		String strLine = null;
		try {
			while ((strLine = br.readLine()) != null) {
				t1(strLine);
				// TODO 这里可以写一行，直接到文件，生成csv文件
			}
		} finally {
			if (br != null)
				br.close();
		}
	}

	public void showList() {
		System.out.println(list.size());
		for (String str : list) {
			System.out.println(str.substring(0, 5) + " " + str.length());
		}
	}

}
