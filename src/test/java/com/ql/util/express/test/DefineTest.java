package com.ql.util.express.test;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.junit.Assert;
import org.junit.Test;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressLoader;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.FuncitonCacheManager;
import com.ql.util.express.IExpressContext;
import com.ql.util.express.InstructionSet;

public class DefineTest {
	@org.junit.Test
	public void testDefExpressInner() throws Exception{
		String express = "定义变量 int qh = 1 + 1";
		DefaultContext<String, Object>  context = new DefaultContext<String, Object>();
		ExpressRunner runner = new ExpressRunner();	
		runner.addOperatorWithAlias("定义变量", "def", null);
		context.put("qh",100);
		Object r = runner.execute(express,context, null, false,false);
		Assert.assertTrue("表达式变量作用域错误", r.toString().equalsIgnoreCase("2"));
		Assert.assertTrue("表达式变量作用域错误", context.get("qh").toString().equalsIgnoreCase("100"));
	}		
	
	@org.junit.Test
	public void testDefUserContext() throws Exception{
		String express = "qh = 1 + 1";
		DefaultContext<String, Object>  context = new DefaultContext<String, Object>();
		ExpressRunner runner = new ExpressRunner();
		context.put("qh",100);
		Object r = runner.execute(express,context, null, false,false);
		Assert.assertTrue("表达式变量作用域错误", r.toString().equalsIgnoreCase("2"));
		Assert.assertTrue("表达式变量作用域错误", context.get("qh").toString().equalsIgnoreCase("2"));
	}
	@org.junit.Test
	public void testAlias() throws Exception {
		String express = " 定义别名 qh example.child ; "
				+ "{定义别名 qh example.child.a;" + " qh =qh + \"-ssss\";" + "};"
				+ " qh.a = qh.a +\"-qh\";" + " return example.child.a";
		ExpressRunner runner = new ExpressRunner();
		runner.addOperatorWithAlias("定义别名", "alias", null);
		DefaultContext<String, Object> context = new DefaultContext<String, Object>();
		context.put("example", new BeanExample());
		runner.addOperatorWithAlias("如果", "if", null);
		runner.addOperatorWithAlias("则", "then", null);
		runner.addOperatorWithAlias("否则", "else", null);
		Object r = runner.execute(express,context, null, false,false);
		Assert.assertTrue("别名实现 错误", r.toString().equalsIgnoreCase("qh-ssss-qh"));
		Assert.assertTrue("别名实现 错误", ((BeanExample) context.get("example")).child.a.toString().equalsIgnoreCase("qh-ssss-qh"));
	}
	@org.junit.Test
	public void testMacro() throws Exception{
		String express ="定义宏  惩罚   {cache bean.unionName(name)}; 惩罚; return  惩罚";
		ExpressRunner runner = new ExpressRunner();
		runner.addOperatorWithAlias("定义宏", "macro", null);
		DefaultContext<String, Object>  context = new DefaultContext<String, Object>();		
		context.put("bean", new BeanExample("qhlhl2010@gmail.com"));
		context.put("name","xuannn");
		Object r = runner.execute(express,context, null, false,false);
		Assert.assertTrue("别名宏 错误", r.toString().equalsIgnoreCase("qhlhl2010@gmail.com-xuannn"));
		System.out.println(r);
	}	
	@Test
	public void test_自定义函数() throws Exception{		
		String express ="定义函数  递归(int a){" +
				" if(a == 1)then{ " +
				"   return 1;" +
				"  }else{ " +
				"     return 递归(a - 1) *  a;" +
				"  } " +
				"}; " +
				"递归(10);";
		ExpressRunner runner = new ExpressRunner();
		runner.addOperatorWithAlias("定义函数", "function",null);
		DefaultContext<String, Object>  context = new DefaultContext<String, Object>();		
		Object r = runner.execute(express,context, null, true,false);
		Assert.assertTrue("自定义函数 错误", r.toString().equals("3628800"));
	}	
	@org.junit.Test
	public void testProperty() throws Exception{
		//String express =" cache isVIP(\"qh\") ;  cache isVIP(\"xuannan\") cache isVIP(\"qh\") ;";
		
		String express =" example.child.a = \"ssssssss\";" +
				" map.name =\"ffff\";" +
				"return map.name;";
		ExpressRunner runner = new ExpressRunner();
		DefaultContext<String, Object>  context = new DefaultContext<String, Object>();
		context.put("example", new BeanExample("张三"));
		context.put("map",new HashMap());
		runner.addFunctionOfClassMethod("isVIP", BeanExample.class.getName(),
				"isVIP", new String[] { "String" }, "$1不是VIP用户");
		FuncitonCacheManager mananger = new FuncitonCacheManager();
		Object r = runner.execute(express,context, null, false,false);
		Assert.assertTrue("属性操作错误", r.toString().equalsIgnoreCase("ffff"));
		Assert.assertTrue("属性操作错误", ((BeanExample)context.get("example")).child.a.toString().equalsIgnoreCase("ssssssss"));		
	}	
	@org.junit.Test
	public void test批量执行指令() throws Exception{
		ExpressRunner runner = new ExpressRunner();
		runner.addOperatorWithAlias("定义宏", "macro", null);
		DefaultContext<String, Object>  context = new DefaultContext<String, Object>();		
		context.put("bean", new BeanExample("qhlhl2010@gmail.com"));
		context.put("name","xuannn");
		InstructionSet[] sets =  new InstructionSet[]{
				runner.parseInstructionSet("def int qh = 1;"),
				runner.parseInstructionSet("qh = qh + 10;"),
				runner.parseInstructionSet("定义宏  惩罚   {qh = qh + 100 };"),
				runner.parseInstructionSet("惩罚;"),
				runner.parseInstructionSet("qh = qh + 1000;"),
		};
		Object r = runner.execute(sets, null, context, null,null, true,false,null);
//		 public Object execute(InstructionSet[] instructionSets,ExpressLoader loader,IExpressContext context,
//				  List errorList,FuncitonCacheManager aFunctionCacheMananger,boolean isTrace,boolean isCatchException,
//					Log aLog);
		Assert.assertTrue("别名实现 错误", r.toString().equalsIgnoreCase("1111"));
//		System.out.println(r);
//		System.out.println(context);
	}	
	@org.junit.Test
	public void test调用其他脚本() throws Exception{
		ExpressRunner runner = new ExpressRunner();
		runner.addOperatorWithAlias("定义宏", "macro", null);
		ExpressLoader loader = new ExpressLoader(runner);
		loader.parseInstructionSet("定义", "def int qh = 100;");
		loader.parseInstructionSet("累加", "qh = qh + 100;");
		loader.parseInstructionSet("执行", "call \"累加\"; call \"累加\";");
		loader.parseInstructionSet("返回", "return qh;");
		DefaultContext<String, Object>  context = new DefaultContext<String, Object>();		
		context.put("bean", new BeanExample("qhlhl2010@gmail.com"));
		context.put("name","xuannn");
		Object r = runner.execute(new InstructionSet[]{
				loader.getInstructionSet("定义"),
				loader.getInstructionSet("执行"),
				loader.getInstructionSet("执行"),
				loader.getInstructionSet("返回")				
		}, loader, context, null, null, true,false,null);
		
		System.out.println(r);
		Assert.assertTrue("别名实现 错误", r.toString().equalsIgnoreCase("500"));
	
	}	
	@org.junit.Test
	public  void test_循环() throws Exception{
		long s = System.currentTimeMillis();
		String express ="qh = 0; 循环(int i = 1;  i<=10;i = i + 1){ if(i > 5) then{ 终止;}; " +
				"循环(def int j=0;j<10;j= j+1){  " +
				"    if(j > 5)then{" +
				"       终止;" +
				"    }; " +
				"    qh = qh + j;" +
				//"   打印(i +\":\" + j+ \":\" +qh);"+
				" };  " +
				"};" +
				"return qh;";
		ExpressRunner runner = new ExpressRunner();		
		runner.addOperatorWithAlias("循环", "for",null);
		runner.addOperatorWithAlias("继续", "continue",null);
		runner.addOperatorWithAlias("终止", "break",null);
		runner.addFunctionOfServiceMethod("打印", System.out, "println", new String[]{Object.class.getName()}, null);
		DefaultContext<String, Object>  context = new DefaultContext<String, Object>();		
		context.put("bean", new BeanExample("qhlhl2010@gmail.com"));
		context.put("name","xuannn");		
		int count = 1;
		s = System.currentTimeMillis();
		Object r = runner.execute(express,context, null, false,false);

		System.out.println("编译耗时：" + (System.currentTimeMillis() - s));
		
		for(int i=0;i<count;i++){
			r = runner.execute(express,context, null, false,false);
			Assert.assertTrue("循环处理错误", r.toString().equals("75"));
		}
		System.out.println("执行耗时：" + (System.currentTimeMillis() - s));		
		System.out.println(context);	
	}	
}
