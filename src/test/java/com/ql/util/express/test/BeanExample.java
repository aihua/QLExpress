package com.ql.util.express.test;

public class BeanExample {
	public String name = "qhlhl2010@gmail.com";
	public BeanExample() {
	}

	public BeanExample(String aName) {
		name = aName;
	}

	public String unionName(String otherName) {
		return name + "-" + otherName;
	}

	public static String upper(String abc) {
		return abc.toUpperCase();
	}

	public static boolean isVIP(String name) {
		return false;
	}
}
