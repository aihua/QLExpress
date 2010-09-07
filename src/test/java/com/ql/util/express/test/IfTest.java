package com.ql.util.express.test;

import org.junit.Assert;
import org.junit.Test;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

public class IfTest {

	
	@Test
	public void testMacro() throws Exception{		
		//String express ="a = 1.0 ; return (int)a + 1";
		String express =" int a = 1 ; return a + 1";
		ExpressRunner runner = new ExpressRunner();
		DefaultContext<String, Object>  context = new DefaultContext<String, Object>();	
		Object r = runner.execute(express, null, false, context,null,true);
		System.out.println(r);
		System.out.println(context);		
	}	
	@Test
	public void test_�Զ��庯��() throws Exception{		
		String express ="���庯��  �ݹ�(int a,int b){" +
				" if(a == 1)then{ " +
				"   return 1;" +
				"  }else{ " +
				"     return �ݹ�(a - 1,b) *  a;" +
				"  } " +
				"}; " +
				"�ݹ�(10,10);";
		ExpressRunner runner = new ExpressRunner();

		runner.addOperatorWithAlias("���庯��", "function",null);
		DefaultContext<String, Object>  context = new DefaultContext<String, Object>();		
		Object r = runner.execute(express, null, true, context,null,true);
		long start = System.currentTimeMillis();
		int num = 100;
		for(int i = 0;i< num;i++){
		   runner.execute(express, null, true,null);
		}
		System.out.println("ִ��" + num +"��\""+ express +"\" ��ʱ��"
				+ (System.currentTimeMillis() - start));
		
		Assert.assertTrue("�Զ��庯�� ����", r.toString().equals("3628800"));
	}	
}
