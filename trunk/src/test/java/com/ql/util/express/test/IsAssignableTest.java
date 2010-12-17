package com.ql.util.express.test;

import org.junit.Assert;
import org.junit.Test;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.ExpressUtil;

public class IsAssignableTest {
	@Test
	public void testABC() throws Exception {
		Assert.assertTrue("��������ת���жϴ���",ExpressUtil.isAssignable(long.class, int.class) == true);
		Assert.assertTrue("��������ת���жϴ���",ExpressUtil.isAssignable(Long.class, int.class) == true);
		Assert.assertTrue("��������ת���жϴ���",ExpressUtil.isAssignable(Long.class, Integer.class) == true);
		Assert.assertTrue("��������ת���жϴ���",ExpressUtil.isAssignable(java.util.List.class,java.util.AbstractList.class) == true);

		String express = "bean.testLong(p)";
		ExpressRunner runner = new ExpressRunner(true);
		DefaultContext<String, Object> context = new DefaultContext<String, Object>();
		context.put("bean",new BeanExample());
		context.put("p",100);
		
		 Object r = runner.execute(express, context, null, false, true);
		System.out.println(r);
		Assert.assertTrue("��������ת������",r.toString().equalsIgnoreCase("toString:100"));
	}
}
