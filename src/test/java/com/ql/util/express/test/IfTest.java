package com.ql.util.express.test;

import com.ql.util.express.ExpressRunner;

public class IfTest extends ExpressRunner {
	@org.junit.Test
	public void testDemo() throws Exception{
		String express = " 3+ (��� 1==2 �� 4 ���� 3) +8";
		//String express = " true and false ";
		IfTest runner = new IfTest();
		runner.addOperatorWithAlias("���", "if",null);
		runner.addOperatorWithAlias("��", "then",null);
		runner.addOperatorWithAlias("����", "else",null);
		this.setTrace(true); 
		Object r = runner.execute(express, null, false, null);
		System.out.println(r);
		
	}
}
