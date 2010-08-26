package com.ql.util.express.test;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressLoader;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.InstructionSet;

public class IfTest extends ExpressRunner {
	@org.junit.Test
	public void testDemo() throws Exception{
		ExpressRunner runner = new ExpressRunner();
		runner.addOperatorWithAlias("�����", "macro", null);
		ExpressLoader loader = new ExpressLoader(runner);
		loader.parseInstructionSet("����", "def int qh = 100;",true);
		loader.parseInstructionSet("�ۼ�", " qh = qh + 100;",true);
		loader.parseInstructionSet("ִ��", "call \"�ۼ�\"; call \"�ۼ�\";",true);
		DefaultContext<String, Object>  context = new DefaultContext<String, Object>();		
		context.put("bean", new BeanExample("qhlhl2010@gmail.com"));
		context.put("name","xuannn");
		
		Object r = runner.execute(new InstructionSet[]{
				loader.getInstructionSet("����"),
				loader.getInstructionSet("ִ��"),
				loader.getInstructionSet("ִ��")
		}, loader, context, null, null, true);
		
		System.out.println(r);
		System.out.println(context);
	
	}
}
