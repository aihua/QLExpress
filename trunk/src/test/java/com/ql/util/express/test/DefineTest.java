package com.ql.util.express.test;

import java.util.HashMap;

import org.junit.Assert;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.FuncitonCacheManager;
import com.ql.util.express.InstructionSet;

public class DefineTest {
	@org.junit.Test
	public void testDefExpressInner() throws Exception{
		String express = "������� int qh = 1 + 1";
		DefaultContext<String, Object>  context = new DefaultContext<String, Object>();
		IfTest runner = new IfTest();	
		runner.addOperatorWithAlias("�������", "def", null);
		context.put("qh",100);
		Object r = runner.execute(express, null, false, context);
		Assert.assertTrue("���ʽ�������������", r.toString().equalsIgnoreCase("2"));
		Assert.assertTrue("���ʽ�������������", context.get("qh").toString().equalsIgnoreCase("100"));
	}		
	
	@org.junit.Test
	public void testDefUserContext() throws Exception{
		String express = "qh = 1 + 1";
		DefaultContext<String, Object>  context = new DefaultContext<String, Object>();
		IfTest runner = new IfTest();
		context.put("qh",100);
		Object r = runner.execute(express, null, false, context);
		Assert.assertTrue("���ʽ�������������", r.toString().equalsIgnoreCase("2"));
		Assert.assertTrue("���ʽ�������������", context.get("qh").toString().equalsIgnoreCase("2"));
	}
	@org.junit.Test
	public void testAlias() throws Exception {
		String express = " ������� qh example.child ; "
				+ "{������� qh example.child.a;" + " qh =qh + \"-ssss\";" + "};"
				+ " qh.a = qh.a +\"-qh\";" + " return example.child.a";
		IfTest runner = new IfTest();
		runner.addOperatorWithAlias("�������", "alias", null);
		DefaultContext<String, Object> context = new DefaultContext<String, Object>();
		context.put("example", new BeanExample());
		runner.addOperatorWithAlias("���", "if", null);
		runner.addOperatorWithAlias("��", "then", null);
		runner.addOperatorWithAlias("����", "else", null);
		Object r = runner.execute(express, null, false, context,null, true);
		Assert.assertTrue("����ʵ�� ����", r.toString().equalsIgnoreCase("qh-ssss-qh"));
		Assert.assertTrue("����ʵ�� ����", ((BeanExample) context.get("example")).child.a.toString().equalsIgnoreCase("qh-ssss-qh"));
	}
	@org.junit.Test
	public void testMacro() throws Exception{
		String express ="�����  �ͷ�   {cache bean.unionName(name)}; �ͷ�; return  �ͷ�";
		IfTest runner = new IfTest();
		runner.addOperatorWithAlias("�����", "macro", null);
		DefaultContext<String, Object>  context = new DefaultContext<String, Object>();		
		context.put("bean", new BeanExample("qhlhl2010@gmail.com"));
		context.put("name","xuannn");
		Object r = runner.execute(express, null, false, context,null,true);
		Assert.assertTrue("������ ����", r.toString().equalsIgnoreCase("qhlhl2010@gmail.com-xuannn"));
		System.out.println(r);
	}	
	@org.junit.Test
	public void testProperty() throws Exception{
		//String express =" cache isVIP(\"qh\") ;  cache isVIP(\"xuannan\") cache isVIP(\"qh\") ;";
		
		String express =" example.child.a = \"ssssssss\";" +
				" map.name =\"ffff\";" +
				"return map.name;";
		IfTest runner = new IfTest();
		DefaultContext<String, Object>  context = new DefaultContext<String, Object>();
		context.put("example", new BeanExample("����"));
		context.put("map",new HashMap());
		runner.addFunctionOfClassMethod("isVIP", BeanExample.class.getName(),
				"isVIP", new String[] { "String" }, "$1����VIP�û�");
		FuncitonCacheManager mananger = new FuncitonCacheManager();
		Object r = runner.execute(express, null, false, context,mananger,true);
		Assert.assertTrue("���Բ�������", r.toString().equalsIgnoreCase("ffff"));
		Assert.assertTrue("���Բ�������", ((BeanExample)context.get("example")).child.a.toString().equalsIgnoreCase("ssssssss"));		
	}	
	@org.junit.Test
	public void test����ִ��ָ��() throws Exception{
		IfTest runner = new IfTest();
		runner.addOperatorWithAlias("�����", "macro", null);
		DefaultContext<String, Object>  context = new DefaultContext<String, Object>();		
		context.put("bean", new BeanExample("qhlhl2010@gmail.com"));
		context.put("name","xuannn");
		String[] sets =  new String[]{
				"def int qh = 1;",	
				"qh = qh + 10;",
				"�����  �ͷ�   {qh = qh + 100 };",
				"�ͷ�;",
				"qh = qh + 1000;"
		};
		Object r = runner.execute(sets, null, true, context, null, true);
		Assert.assertTrue("����ʵ�� ����", r.toString().equalsIgnoreCase("1111"));
//		System.out.println(r);
//		System.out.println(context);
	}	
}
