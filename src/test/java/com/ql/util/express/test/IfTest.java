package com.ql.util.express.test;

import java.util.HashMap;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.FuncitonCacheManager;

public class IfTest extends ExpressRunner {
	@org.junit.Test
	public void testDemo() throws Exception{
		String express ="�����  �ͷ�   {cache bean.unionName(name)}; �ͷ�; return  �ͷ�";
		IfTest runner = new IfTest();
		runner.addOperatorWithAlias("�����", "macro", null);
		DefaultContext<String, Object>  context = new DefaultContext<String, Object>();		
		context.put("bean", new BeanExample("qhlhl2010@gmail.com"));
		context.put("name","xuannn");
		Object r = runner.execute(express, null, false, context,null,true);
		System.out.println(r);
		System.out.println(context);
	}
}
