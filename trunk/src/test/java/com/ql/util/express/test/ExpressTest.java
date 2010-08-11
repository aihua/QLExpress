package com.ql.util.express.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;

import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;

public class ExpressTest {

	@org.junit.Test
	public void testDemo() throws Exception{
		String express = "10 * 10 + 1 + 2 * 3 + 5 * 2";
		ExpressRunner runner = new ExpressRunner();
		Object result = runner.execute(express, null, false, null);
		System.out.println("���ʽ���㣺" + express + " = " + result);
	}

	@org.junit.Test
	public void tes10000��() throws Exception{
		ExpressRunner runner = new ExpressRunner();
		String express = "10 * 10 + 1 + 2 * 3 + 5 * 2";
		int num = 100000;
		long start = System.currentTimeMillis();
		for(int i = 0;i< num;i++){
		   runner.execute(express, null, true,null);
		}
		System.out.println("ִ��" + num +"��\""+ express +"\" ��ʱ��"
				+ (System.currentTimeMillis() - start));
	}
	@SuppressWarnings("unchecked")
	@org.junit.Test
	public void testExpress() throws Exception{
		ExpressRunner runner = new ExpressRunner();
		runner.addOperator("love", new LoveOperator("love"));
		runner.addOperatorWithAlias("����", "in", "�û�$1��������ķ�Χ");
		runner.addOperatorWithAlias("myand", "and", "�û�$1��������ķ�Χ");
		runner.addFunction("�ۼ�", new GroupOperator("�ۼ�"));
		runner.addFunction("group", new GroupOperator("group"));
		runner.addFunctionOfClassMethod("isVIP", BeanExample.class.getName(),
				"isVIP", new String[] { "String" }, "$1����VIP�û�");
		runner.addFunctionOfClassMethod("ȡ����ֵ", Math.class.getName(), "abs",
				new String[] { "double" }, null);
		runner.addFunctionOfClassMethod("ת��Ϊ��д", BeanExample.class.getName(),
				"upper", new String[] { "String" }, null);		
		runner.addFunctionOfClassMethod("testLong", BeanExample.class.getName(),
				"testLong", new String[] { "long" }, null);		
		
		String[][] expressTest = new String[][] {
				{ "System.out.println(\"ss\")", "null" },
				{"unionName = new com.ql.util.express.test.BeanExample(\"����\").unionName(\"����\")",
						"����-����" }, 
						{ "group(2,3,4)", "9" },
						{ "ȡ����ֵ(-5.0)", "5.0" },
				{ "max(2,3,4,10)", "10" },
				{ "max(3,2) + ת��Ϊ��д(\"abc\")", "3ABC" },
				{ "c = 1000 + 2000", "3000" },
				{ "b = �ۼ�(1,2,3)+�ۼ�(4,5,6)", "21" },
				{ "�������� and �����û� ", "true" },
				{ "new String(\"12345\").length()" ,"5"},
				{ "'a' love 'b' love 'c' love 'd'", "d{c{b{a}b}c}d" },
				{ "10 * (10 + 1) + 2 * (3 + 5) * 2", "142" },
				{ "( 2  ���� (4,3,5)) or isVIP(\"qhlhl2010@gmail.com\") or  isVIP(\"qhlhl2010@gmail.com\")", "false" },
				{" 1!=1 and 2==2 and 1 == 2","false"},
				{" 1==1 or 2==2 and 1 == 2","true"},
				{ "abc == 1", "true" },
				{ "testLong(abc)", "toString:1" },
				};
		IExpressContext expressContext = new ExpressContextExample(null);
		expressContext.put("b", new Integer(200));
		expressContext.put("c", new Integer(300));
		expressContext.put("d", new Integer(400));
		expressContext.put("bean", new BeanExample());
		expressContext.put("abc",1l);
		
		for (int point = 0; point < expressTest.length; point++) {
			String expressStr = expressTest[point][0];
			List errorList = new ArrayList();
			 Object result =runner.execute(expressStr,errorList,true,expressContext);
			if (expressTest[point][1].equalsIgnoreCase("null")
					&& result != null
					|| result != null
					&& expressTest[point][1].equalsIgnoreCase(result							
							.toString()) == false) {
				throw new Exception("�������,��������Ԥ�ڵĲ�ƥ��:" + expressStr + " = " + result + "��������ֵ�ǣ�" + expressTest[point][1]);
			}
			System.out.println("Example " + point + " : " + expressStr + " =  " + result);
			if(errorList.size() > 0){
			   System.out.println("\t\tϵͳ����Ĵ�����ʾ��Ϣ:" + errorList);
			}
		}
		System.out.println(expressContext);
	 }
	
	@org.junit.Test
	public void testReadme() throws Exception{		
		System.out.println("------------------------------");
		System.out.println("------------------------------");
		System.out.println("���Ķ�   QLExpressReadme.xml");
		System.out.println("���Ķ�   QLExpressReadme.xml");
		System.out.println("���Ķ�   QLExpressReadme.xml");
		System.out.println("���Ķ�   QLExpressReadme.xml");
		System.out.println("------------------------------");
		System.out.println("------------------------------");
	
	}
}
