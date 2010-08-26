package com.ql.util.express.test;

import org.junit.Assert;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.InstructionSet;

public class IfTest extends ExpressRunner {
	@org.junit.Test
	public void testDemo() throws Exception{
		IfTest runner = new IfTest();
		runner.addOperatorWithAlias("�����", "macro", null);
		DefaultContext<String, Object>  context = new DefaultContext<String, Object>();		
		context.put("bean", new BeanExample("qhlhl2010@gmail.com"));
		context.put("name","xuannn");
		String[] sets =  new String[]{
				"def int qh = 1;",	
				"qh = qh + 10;",
				"�����  �ͷ�   {qh = qh + 100 };",
				"t = �ͷ�;",
				"qh = qh + 1000;"
		};
		Object r = runner.execute(sets, null, true, context, null, true);
		
		System.out.println(r);
		System.out.println(context);
		Assert.assertTrue("����ʵ�� ����", r.toString().equalsIgnoreCase("1111"));
	}
}
