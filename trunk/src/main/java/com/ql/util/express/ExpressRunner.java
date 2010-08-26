/**
 *
 * <p>Title:Operator </p>
 * <p>Description:���ʽ��������� </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: </p>
 * @author ǽ��
 * @version 1.0
 */

package com.ql.util.express;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * ���ʽ���������
 * 
 * @author qhlhl2010@gmail.com

 * ����֪��   a love b = ? �� ���������ⶨ���Լ��Ĳ��������� �� ��ʹ��QLExpress���߰�
 * 
 * ������ʽ��Ա�ļ��㹤�ߣ��ŵ���Ҫ�����ڣ�
      A������ҪԤ�ȼ��ؿ�����Ҫ����������ֵ
      B�� �û����Ը���ҵ����Ҫ�Զ���������źͺ��� 
      C������ͬ������жϴ�����Ϣ�����������ҵ��ϵͳ�ڹ����жϵ�ʹ�ó����µ��û����顣����ҵ��ϵͳ��صĴ�����롣
   
       ��Ҫ��;��һЩҵ����������жϣ�ͬʱ��Ҫ�����صĴ�����Ϣ

 * ���Hello������
 * 
 *		String express = "10 * 10 + 1 + 2 * 3 + 5 * 2";
 *		ExpressRunner runner = new ExpressRunner();
 *		Object result = runner.execute(express, null, false, null);
 *		System.out.println("���ʽ���㣺" + express + " = " + result);
 *
 * ����������
 *		ExpressRunner runner = new ExpressRunner();
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

      ����������������棬 ִ���������ʽ�����
 *      Example 0 : System.out.println("ss") =  null
 *      Example 1 : unionName = new com.ql.util.express.test.BeanExample("����").unionName("����") =  ����-����
 *      Example 2 : group(2,3,4) =  9
 *      Example 3 : ȡ����ֵ(-5.0) =  5.0
 *      Example 4 : max(2,3,4,10) =  10
 *      Example 5 :  max(3,2) + ת��Ϊ��д("abc") =  3ABC
 *      Example 6 :  c = 1000 + 2000 =  3000
 *      Example 7 : b = �ۼ�(1,2,3)+�ۼ�(4,5,6) =  21
 *      Example 8 : �������� and �����û�  =  true
 *      Example 9 : 'a' love 'b' love 'c' love 'd' =  d{c{b{a}b}c}d
 *      Example 10 :  10 * 10 + 1 + 2 * 3 + 5 * 2 =  117
 *      Example 11 :  ( (1+1) ���� (4,3,5)) and isVIP("����") =  false
 *      		ϵͳ����Ĵ�����ʾ��Ϣ:[�û� 2 ��������ķ�Χ,  ���� ����VIP�û�]
 *      
 * 
 * ���ʽ֧�ָ�����
 * 1��������java�﷨��
   * ����A���������� : 10 * 10 + 1 + 2 * 3 + 5 * 2
 *����B��boolean����: 3 > 2 and 2 > 3
 *    C���������󣬶��󷽷����ã���̬��������:new com.ql.util.express.test.BeanExample("����").unionName("����")
 *    D��������ֵ��a = 3 + 5
 *    F��֧�� in,max,min:  (a in (1,2,4)) and (b in("abc","bcd","efg"))
 * 2���ṩ���ʽ�����ģ����Ե�ֵ����Ҫ�ڳ�ʼ��ʱ��ȫ�����룬
 *    �����ڱ��ʽ�����ʱ����Ҫʲô��Ϣ��ͨ�������Ľӿڻ�ȡ 
 *    ������Ϊ��֪����������󣬶����������аѿ�����Ҫ�����ݶ����롣
 *    runner.execute("�������� and �����û�",errorList,true,expressContext)
 *    "��������"��"�����û�"������������Ҫ��ʱ��ͨ���ӿ�ȥ��ȡ��
 * 3�����Խ�������ֱ�Ӵ洢���������й�����ҵ��ʹ�á����磺
 *       runner.execute("c = 1000 + 2000",errorList,true,expressContext);
 *       ����expressContext�л�����һ������c=3000��Ҳ������expressContextʵ��ֱ�ӵ����ݿ�����ȡ�
 * 4�� ���Խ����Spring����ķ���ӳ��Ϊ���ʽ�����еı�������������ҵ����Ա�����������á����磺
 *    �� Math.abs() ӳ��Ϊ "ȡ����ֵ"�� ��  "ȡ����ֵ(-5.0)" = "5.0"
 *    runner.addFunctionOfClassMethod("ȡ����ֵ", Math.class.getName(), "abs",new String[] { "double" }, null);
 * 5������Ϊ�Ѿ����ڵ�boolean��������������ñ��������Ӵ�����Ϣͬ��������ڼ�����Ϊfalse��ʱ��ͬʱ���ش�����Ϣ�����磺
 *    runner.addOperatorWithAlias("����", "in", "�û�$1��������ķ�Χ")��
 *    �û��Զ���ĺ���ͬ��Ҳ�������ô�����Ϣ�����磺
	  runner.addFunctionOfClassMethod("isOk", BeanExample.class.getName(),"isOk", new String[] { "String" }, "$1 ����VIP�û�");
	 
 *    ���ڵ���List errorList = new ArrayList();
 *			 Object result =runner.execute("( (1+1) ���� (4,3,5)) and isOk("����")",errorList,true,null);
	     ִ�н�� result = false.ͬʱ��errorList�л��᷵��2������ԭ��
	      1��"�û� 2 ��������ķ�Χ"��2������ ����VIP�û�	  
	    ����ҵ��ϵͳ��Ҫ���й�����㣬ͬʱ��Ҫ����
			 
 * 6�������Զ�����㺯�������磺
 *    �Զ�һ���������� group��
 *    class GroupOperator extends Operator {
	  public GroupOperator(String aName) {
		this.name= aName;
	   }
	  public OperateData executeInner(IExpressContext context, OperateData[] list) throws Exception {
		Object result = new Integer(0);
		for (int i = 0; i < list.length; i++) {
			result = OperatorOfNumber.Add.execute(result, list[i]
					.getObject(context));
		}
		return new OperateData(result, result.getClass());
	  }
     }
          Ȼ�����ӵ��������棺
     runner.addFunction("�ۼ�", new GroupOperator("�ۼ�"));
	 runner.addFunction("group", new GroupOperator("group"));
          �� ִ�У�group(2,3,4)  = 9 ,�ۼ�(1,2,3)+�ۼ�(4,5,6)=21
 * 7�������Զ����µĲ�������  ���Զ���Ĳ����������ȼ�����Ϊ��ߡ����� ��
 *   �Զ�һ���������� love��
 *   class LoveOperator extends Operator {	
	public LoveOperator(String aName) {
		this.name= aName;
	}
	public OperateData executeInner(IExpressContext context, OperateData[] list)
			throws Exception {
		String op1 = list[0].getObject(context).toString();
		String op2 = list[1].getObject(context).toString();
		String result = op2 +"{" + op1 + "}" + op2;		
		return new OperateData(result, result.getClass());
	}
   }
         Ȼ�����ӵ��������棺
      runner.addOperator("love", new LoveOperator("love"));
        �� ִ�У�'a' love 'b' love 'c' love 'd' = "d{c{b{a}b}c}d"
        
  8������������û��Ԥ���������£� ִ��10���  "10 * 10 + 1 + 2 * 3 + 5 * 2" ��ʱ��3.187��
             runner.execute("10 * 10 + 1 + 2 * 3 + 5 * 2", null, false,null);
            �ڴ� Ԥ���뻺�濪�ص�����£� ִ��10��� "10 * 10 + 1 + 2 * 3 + 5 * 2" ��ʱ��  0.171��
             runner.execute("10 * 10 + 1 + 2 * 3 + 5 * 2", null, true,null);       
            �����������̰߳�ȫ�ġ���ҵ��ϵͳ��ʵ��ʹ�ù�����Ӧ�ô򿪻���Ԥ����Ŀ��أ����ܻ���ӡ�
             ���Ե���clearExpressCache()������� ��
           �ڿ������ص�����£��Ỻ�����������ִ��ָ��������ʾ��
           �������ַ����������ʷ��������﷨�����Ȳ��裬�򵥶Ա�һ�£������30�����ٶȣ�
        1:LoadData 10
		2:LoadData 10
		3:OP : * OPNUMBER[2] 
		4:LoadData 1
		5:OP : + OPNUMBER[2] 
		6:LoadData 2
		7:LoadData 3
		8:OP : * OPNUMBER[2] 
		9:OP : + OPNUMBER[2] 
		10:LoadData 5
		11:LoadData 2
		12:OP : * OPNUMBER[2] 
		13:OP : + OPNUMBER[2]     
   9��������ʽ��Ա�ļ��㹤�ߣ��е���Ҫ�����ڣ�
      A������ҪԤ�ȼ��ؿ�����Ҫ����������ֵ
      B�� �û����Ը���ҵ����Ҫ�Զ���������źͺ��� 
      C������ͬ������жϴ�����Ϣ�����������ҵ��ϵͳ�ڹ����жϵ�ʹ�ó����µ��û����顣����ҵ��ϵͳ��صĴ�����롣
   10���������Խ�һ���Ż��ĵط���
      A�����еĵ��ʲ�⡢�ʷ��������﷨���������Լ�д��ɽկ�棬����������������Ŀ�Դ���ߡ�
      B���Ż�����Ĳ���ָ���ߵ����������ŵ�����Ч��
      C�������򵥵���չ֧���Զ������Ƭ�ε����С�               
 */
@SuppressWarnings("unchecked")
public class ExpressRunner
{

  private static final Log log = LogFactory.getLog(ExpressRunner.class);
  private Map<String,InstructionSet> expressInstructionSetCache = new HashMap<String,InstructionSet>();
  
  protected OperatorManager m_operatorManager =  new OperatorManager();
  protected ExpressImport m_import = new ExpressImport();
  protected Map m_cacheOracleParseString = new HashMap();
  public ExpressRunner(){ }

  /**
	 * Ϊ�Ѿ����ڵĲ����������������Ҫ���ڶ��岻ͬ�Ĵ�����Ϣ���������  addOperatorWithAlias("����","in","�û������б���")
	 * 
	 * @param aAliasName
	 *            �������ű���
	 * @param name
	 *            ԭʼ�������ű���
	 * @param errorInfo
	 *            ������ִ�н��Ϊfalse��ʱ������Ĵ�����Ϣ
	 * @throws Exception
	 */
	public void addOperatorWithAlias(String aAliasName, String name,
			String errorInfo) throws Exception {
		this.m_operatorManager
				.addOperatorWithAlias(aAliasName, name, errorInfo);
	}
    /**
     * �û�����Ĳ����������ȼ�������Ϊ���,������Ϊ����
     * @param name 
     * @param op
     */
    public void addOperator(String name,Operator op){
    	this.m_operatorManager.addOperator(name,op);
    }
    /**
     * �����û��Ĳ�������������group,��������
     * addFunction("group",new GroupOperator("group"))
     * ����ʽ �� group(2,3,4) ִ�н��   9
     * �����ඨ�����£�
     * public class GroupOperator extends Operator {
	   public GroupOperator(String aName) {
		this.name =aName;
	   }
	   public OperateData execute(Operation parent, OperateData[] list,List errorList)
			throws Exception {
		Object result = new Integer(0);
		for (int i = 0; i < list.length; i++) {
			result = OperatorOfNumber.Add.execute(result, list[i]
					.getObject(parent));
		}
		return new OperateData(result, result.getClass());
	    }
      }
     * @param name ������������
     * @param op �Զ��Ĳ�������
     */
    public void addFunction(String name, Operator op) {
    	this.m_operatorManager.addFunction(name, op);
    };
    
    /**
     * ���һ����ĺ������壬���磺Math.abs(double) ӳ��Ϊ���ʽ�е� "ȡ����ֵ(-5.0)"
     * @param name ��������
     * @param aClassName ������
     * @param aFunctionName ���еķ�������
     * @param aParameterTypes �����Ĳ�����������
     * @param errorInfo �������ִ�еĽ����false����Ҫ����Ĵ�����Ϣ
     * @throws Exception
     */
    public void addFunctionOfClassMethod(String name,String aClassName, String aFunctionName,
                        String[] aParameterTypes,String errorInfo) throws Exception {
    	this.m_operatorManager.addFunctionOfClassMethod(name, aClassName, aFunctionName, aParameterTypes, errorInfo);
    }
    /**
     * ���ڽ�һ���û��Լ�����Ķ���(����Spring����)����ת��Ϊһ�����ʽ����ĺ���
     * @param name
     * @param aServiceObject
     * @param aFunctionName
     * @param aParameterTypes
     * @param errorInfo
     * @throws Exception
     */
    public void addFunctionOfServiceMethod(String name,Object aServiceObject, String aFunctionName,
                        String[] aParameterTypes,String errorInfo ) throws Exception {
    	this.m_operatorManager.addFunctionOfServiceMethod(name, aServiceObject, aFunctionName, aParameterTypes, errorInfo);
    }
 

  protected Object[] getOpObjectList(String[] tmpList)  throws Exception
  {
   
    List  list = new ArrayList();
    int point=0;
    while(point <tmpList.length){
      String name = tmpList[point];
      if (name.equals("new") == true) {
        list.add(new ExpressItemNew());
        point = point + 1;
      }else if(tmpList[point].equals(".")){
         if(tmpList[point + 2].equals("(") == true){
           list.add(new ExpressItemMethod("method",tmpList[point + 1]));
         }else{
           list.add(new ExpressItemField("field",tmpList[point + 1]));
         }
         point = point + 2;
      }else if (this.m_operatorManager.isOperator(name)) { //�ж��Ƿ������
      	if(name.equals("-")
      			&& (list.size() ==0 || list.size()>0 && list.get(list.size() -1 ) instanceof ExpressItem)
      	    && (point <tmpList.length -1) 
      	    && ( tmpList[point + 1].charAt(0) >='0'
      	    	   && tmpList[point + 1].charAt(0) <='9'
      	    	  ||tmpList[point + 1].charAt(0)=='.')){ //�Ը����������⴦��
      		tmpList[point + 1] = '-' + tmpList[point + 1];
      		point = point + 1;
      		
      	}else{
          list.add(new ExpressItem(name,this.m_operatorManager.getOperatorRealName(name)));
          point = point + 1;
      	}
      }else if (name.charAt(0) == ':') {
    	throw new Exception("���ʽ���Ѿ�ȥ���Բ�����֧��");  
       // list.add(new OperateDataParameter(name.substring(1)));
       // point = point + 1;
      }else if (name.charAt(0) == '\'' || name.charAt(0) == '"') {
        list.add(new OperateData(name.substring(1, name.length() - 1), String.class));
        point = point + 1;
      } else if ( (name.charAt(0) >= '0' && name.charAt(0) <= '9') ||
               (name.charAt(0) == '.') || name.charAt(0)=='-') { //����       	
       if (name.endsWith("L") || name.endsWith("l")) {//long
         list.add(new OperateData(new Long(name.substring(0,name.length() - 1)), Long.TYPE));
       }else {
         boolean isFind = false;
         for (int i = 0; i < name.length(); i++){
           if (name.charAt(i) == '.'){//double
             list.add(new OperateData(new Double(name), Double.TYPE));
             isFind = true;
             break;
           }
         }
         if (isFind == false){//int
           list.add(new OperateData(new Integer(name), Integer.TYPE));
         }
       }
       
       point = point + 1;
     }else if(name.toLowerCase().equals("true")){
       list.add(new OperateData(Boolean.valueOf(true),Boolean.TYPE));
       point = point + 1;
     }else if (name.toLowerCase().equals("false")){
       list.add(new OperateData(Boolean.valueOf(false), Boolean.TYPE));
       point = point + 1;
      }else{
        boolean isClass = false;
        int j = point;
        Class tmpClass = null;
        String tmpStr="";
        while (j < tmpList.length) {
          tmpStr = tmpStr + tmpList[j];
          tmpClass = this.m_import.getClass(tmpStr);
          if (tmpClass != null) {
            point = j + 1;
            isClass = true;
            break;
          }
          if(j < tmpList.length - 1 && tmpList[j+1].equals(".")==true){
            tmpStr = tmpStr + tmpList[j+1];
            j = j + 2;
            continue;
          }else{
            break;
          }
        }

        if(isClass == true){
          //�������Ͳ���(Integer);
          //ǰ����������������
          if(list.size() >0 && point < tmpList.length && list.get(list.size() -1) instanceof ExpressItem
             && ((ExpressItem)list.get(list.size() -1)).name.equals("(")==true
             && tmpList[point].equals(")")==true){
            //�Ƴ�ǰһ������������
            list.remove(list.size() -1 );
            list.add(new OperatorClass(tmpStr,tmpClass));
            list.add(new ExpressItem("cast"));
            point = point + 1;//���ڴ����һ��������
          }else{//�������Ͳ���
            list.add(new OperatorClass(tmpStr,tmpClass));
          }
        }else{
        	//�� def��alias ����ĵ�һ�����������ַ�������
        	if(list.size() >=1 && list.get(list.size() -1 ) instanceof ExpressItem
        	         && this.m_operatorManager.getRealName(((ExpressItem)list.get(list.size() -1 )).name).equalsIgnoreCase("alias")
        	        ){
        		list.add(new OperateData(name,String.class));
        	}else if(list.size() >=1 && list.get(list.size() -1 ) instanceof ExpressItem
              	   &&  this.m_operatorManager.getRealName(((ExpressItem)list.get(list.size() -1 )).name).equalsIgnoreCase("macro")
              	   ){	
         		list.add(new OperateData(name,String.class));
        	}else if(list.size() >=2 && list.get(list.size() -2 ) instanceof ExpressItem
             	   &&  this.m_operatorManager.getRealName(((ExpressItem)list.get(list.size() -2 )).name).equalsIgnoreCase("def")
             	   ){	
        		list.add(new OperateData(name,String.class));
        	}else{
                list.add(new OperateDataAttr(name));
        	}
           point = point + 1;
        }
      }
    }
    return list.toArray();
  }
  
  /**
   * ͨ��"{","}",";"�ѳ����ֿ�
   * @param list
   * @return
   * @throws Exception
   */
  protected ExpressTreeNodeRoot getCResult(Object[] list) throws Exception{
	  Stack<List<Object>> stackList = new Stack<List<Object>> ();
	  Stack<ExpressTreeNodeRoot> nodeStack = new Stack<ExpressTreeNodeRoot> ();
	  nodeStack.push(new ExpressTreeNodeRoot("ROOT"));
	  stackList.push(new ArrayList<Object>());
	  for(int i=0;i< list.length;i++){
		  if(list[i] instanceof ExpressItem &&  ((ExpressItem)list[i]).name.equals(";")){//����һ���µ����
			  stackList.peek().add(list[i]);
			  for(ExpressTreeNode item:getCResultOne(stackList.peek().toArray())){
			      nodeStack.peek().addChild(item);
			  }
			  stackList.peek().clear();
		  }else if(list[i] instanceof ExpressItem &&  ((ExpressItem)list[i]).name.equals("{")){//����һ���µ����
			  nodeStack.push(new ExpressTreeNodeRoot("{}"));
			  stackList.push(new ArrayList<Object>());
		  }else if(list[i] instanceof ExpressItem &&  ((ExpressItem)list[i]).name.equals("}")){//����һ���µ����
			  //���� } ǰ��û�� ;��Ҳ��Ϊһ����ɵ���䴦��
			  if(false == (list[i - 1] instanceof ExpressItem == true &&  ((ExpressItem)list[i - 1]).name.equals(";")))
			  {   stackList.peek().add(new ExpressItem(";"));
			      for(ExpressTreeNode item:getCResultOne(stackList.peek().toArray())){
			          nodeStack.peek().addChild(item);
			      }
		      }
			  //���� }
			  stackList.peek().clear();
			  stackList.pop();
			  stackList.peek().add(nodeStack.pop());
		  }else{
			  stackList.peek().add(list[i]);
		  }
	  }
	  ExpressTreeNodeRoot result = nodeStack.pop();
	  if(log.isDebugEnabled()){
	     this.printTreeNode(result, 1);
	  }
	  return result;
  }
   
  protected ExpressTreeNode[] getCResultOne(Object[] list) throws Exception
  {
	try{
    if (list == null){
      throw new Exception("���ʽ����Ϊ��");
    }  
    List<ExpressTreeNode> result = new ArrayList<ExpressTreeNode>();
    Stack sop = new Stack();
    Stack sdata = new Stack();
    Stack sOpDataNumber = new Stack();
    sop.push(new ExpressItem(";"));
    int i =0;
    while (i < list.length)
    {
      if(list[i] instanceof ExpressTreeNodeRoot){
    	  sdata.push(list[i]);
    	  i++;
      }else if (list[i] instanceof OperateData){  
    	 ((OperateData)list[i]).setMaxStackSize(sdata.size());
         sdata.push(list[i]);
         i++;
      }
      else if (list[i] instanceof ExpressItem)
      {
         ExpressItem op1 = (ExpressItem)sop.peek();
         ExpressItem op2 = (ExpressItem)list[i] ;
         int op  =m_operatorManager.compareOp(op1.name,op2.name);
         if (op2.name.equals("(")){
            sOpDataNumber.push(Integer.valueOf(1));            	
         }   

         switch(op)
         {  case (0):
                    sop.push(list[i]);
                    i++;
                    break;
            case (6):// { �� } ����
            	sop.pop();
            	i++;
            	break;
            case (2):// ( �� ) ����
                     sop.pop();
                     if(this.isNAddOneParameterCount( (ExpressItem)sop.peek())){
                       ((ExpressItem) sop.peek()).opDataNumber = 1 + ( (Integer)sOpDataNumber.pop()).intValue();
                     }else{
                       ((ExpressItem) sop.peek()).opDataNumber = ( (Integer)sOpDataNumber.pop()).intValue();
                     }
                     if (list[i - 1] instanceof ExpressItem && ((ExpressItem)list[i - 1]).name.equals("(") == true) {
                       ((ExpressItem) sop.peek()).opDataNumber = ((ExpressItem) sop.peek()).opDataNumber - 1;
                     }
                     i++;
                     break;
            case(4):// if �� then
            	op1.opDataNumber = 2;
                i++;
            	break;
            case(5):// if �� then 
            	if(list[i -1] instanceof ExpressItem &&  ((ExpressItem)list[i -1]).name.equalsIgnoreCase("then") == true){
            		 //��if () then else ������� ����һ�� void ����������������Ĵ����ж�
            	   sdata.push(new OperateData(null,void.class));            	     
            	}
            	op1.opDataNumber = 3;
                i++;
            	break;
            case (3):
						if (sop.size() > 1 || sdata.size() > 1) {
							throw new Exception("���ʽ���ô������麯�������Ƿ�ƥ��");
						}
						ExpressTreeNode tmpResultNode = (ExpressTreeNode) sdata.pop();
						if (tmpResultNode instanceof MyPlace) {
							tmpResultNode = ((MyPlace) tmpResultNode).op;
						}
						result.add(tmpResultNode);
						return result.toArray(new ExpressTreeNode[0]);
            case(1) :
               sop.pop();//�׳���ջ���Ĳ�������
               if (op1.name.equals(",")){
                   int TmpInt = ((Integer)sOpDataNumber.pop()).intValue();
                   TmpInt = TmpInt + 1;
                   sOpDataNumber.push(Integer.valueOf(TmpInt));
               }else{
                   // Ϊ���жϱ��ʽ���﷨�Ƿ���ȷ����Ҫ�Բ�����ջ���д�����֧��(2,3,4)ִ�н��Ϊ4����ʽ
                   int opDataNumber =m_operatorManager.getDataMember(op1.name);
                   if (opDataNumber <0){
                     opDataNumber = op1.opDataNumber;
                   } 
                   if(op1.name.equalsIgnoreCase("return") == true ){
                	   opDataNumber = sdata.size(); 
                	   if(opDataNumber > 1){
                		   opDataNumber = 1; 
                	   }
                   }
                   op1.opDataNumber = opDataNumber;
                   op1.setMaxStackSize(sdata.size());
                   List<ExpressTreeNode> tmpList = new ArrayList<ExpressTreeNode>();         
                   for(int point =0;point < opDataNumber;point++){
                  	 //ȷ���������ŵĲ�����
                  	 ExpressTreeNode tmpNode = (ExpressTreeNode)sdata.pop();
                  	 if(tmpNode instanceof MyPlace){
                  		 tmpNode = ((MyPlace)tmpNode).op;
                  	 }
                  	 tmpNode.setParent(op1);
                     tmpList.add(0,tmpNode);
                   }
                   op1.setChildren(tmpList.toArray(new ExpressTreeNode[0]));
                   if(op1.getChildren().length <=0 ){
                	   result.add(op1);
                   }else{
                      sdata.push(new MyPlace(op1));
                   }
               }
               break;
         }
      }
    }
	  }catch(Exception e){
		  throw new Exception("���ʽ�﷨��������[" + e.getMessage() +"] ���飺[" + getPrintInfo2(list," ")+"]",e);
	  }
	throw new Exception("û���ܹ�������ȷ�Ľ������ʽ");
    
  }
  protected ExpressTreeNode getCResultBak(Object[] list) throws Exception
  {
	try{
    if (list == null){
      throw new Exception("���ʽ����Ϊ��");
    }  
    Stack sop = new Stack();
    Stack sdata = new Stack();
    Stack sOpDataNumber = new Stack();
    sop.push(new ExpressItem(";"));
    int i =0;
    while (i < list.length)
    {
      if (list[i] instanceof OperateData)
      {  
    	 ((OperateData)list[i]).setMaxStackSize(sdata.size());
         sdata.push(list[i]);
         i++;
      }
      else if (list[i] instanceof ExpressItem)
      {
         ExpressItem op1 = (ExpressItem)sop.peek();
         ExpressItem op2 = (ExpressItem)list[i] ;
         int op  =m_operatorManager.compareOp(op1.name,op2.name);
         if (op2.name.equals("(")){
            sOpDataNumber.push(Integer.valueOf(1));            	
         }   

         switch(op)
         {  case (0):
                    sop.push(list[i]);
                    i++;
                    break;
            case (2):// ( �� ) ����
                     sop.pop();
                     if(this.isNAddOneParameterCount( (ExpressItem)sop.peek())){
                       ((ExpressItem) sop.peek()).opDataNumber = 1 + ( (Integer)sOpDataNumber.pop()).intValue();
                     }else{
                       ((ExpressItem) sop.peek()).opDataNumber = ( (Integer)sOpDataNumber.pop()).intValue();
                     }
                     if (list[i - 1] instanceof ExpressItem && ((ExpressItem)list[i - 1]).name.equals("(") == true) {
                       ((ExpressItem) sop.peek()).opDataNumber = ((ExpressItem) sop.peek()).opDataNumber - 1;
                     }
                     i++;
                     break;
            case(4):// if �� then
            	op1.opDataNumber = 2;
                i++;
            	break;
            case(5):// if �� then 
            	if(list[i -1] instanceof ExpressItem &&  ((ExpressItem)list[i -1]).name.equalsIgnoreCase("then") == true){
            		 //��if () then else ������� ����һ�� void ����������������Ĵ����ж�
            	   sdata.push(new OperateData(null,void.class));            	     
            	}
            	op1.opDataNumber = 3;
                i++;
            	break;
            case (3):
						if (sop.size() > 1 || sdata.size() > 1) {
							throw new Exception("���ʽ���ô������麯�������Ƿ�ƥ��");
						}
						ExpressTreeNode rootNode = (ExpressTreeNode)sdata.pop();
						if (rootNode instanceof MyPlace) {
							rootNode = ((MyPlace) rootNode).op;
						}
						return rootNode;
            case(1) :
               sop.pop();//�׳���ջ���Ĳ�������
               if (op1.name.equals(",")){
                   int TmpInt = ((Integer)sOpDataNumber.pop()).intValue();
                   TmpInt = TmpInt + 1;
                   sOpDataNumber.push(Integer.valueOf(TmpInt));
               }else{
                   // Ϊ���жϱ��ʽ���﷨�Ƿ���ȷ����Ҫ�Բ�����ջ���д�����֧��(2,3,4)ִ�н��Ϊ4����ʽ
                   int opDataNumber =m_operatorManager.getDataMember(op1.name);
                   if (opDataNumber <0){
                     opDataNumber = op1.opDataNumber;
                   } 
                   op1.opDataNumber = opDataNumber;
                   op1.setMaxStackSize(sdata.size());
                   List<ExpressTreeNode> tmpList = new ArrayList<ExpressTreeNode>();         
                   for(int point =0;point < opDataNumber;point++){
                  	 //ȷ���������ŵĲ�����
                  	 ExpressTreeNode tmpNode = (ExpressTreeNode)sdata.pop();
                  	 if(tmpNode instanceof MyPlace){
                  		 tmpNode = ((MyPlace)tmpNode).op;
                  	 }
                  	 tmpNode.setParent(op1);
                     tmpList.add(0,tmpNode);
                   }
                   op1.setChildren(tmpList.toArray(new ExpressTreeNode[0]));
                   sdata.push(new MyPlace(op1));
               }
               break;
         }
      }
    }
	  }catch(Exception e){
		  throw new Exception("���ʽ�﷨��������[" + e.getMessage() +"] ���飺[" + getPrintInfo2(list," ")+"]",e);
	  }
    return null;
  }
  protected InstructionSet createInstructionSet(ExpressTreeNodeRoot root)throws Exception {
		InstructionSet result = new InstructionSet();
  	    createInstructionSetPrivate(result,root,true);
		return result;
	}
	protected boolean createInstructionSetPrivate(InstructionSet result,ExpressTreeNode node,boolean isRoot)throws Exception {
		boolean returnVal = false;
		if(node instanceof OperateDataAttr){
			FunctionInstructionSet functionSet = result.getMacroDefine(((OperateDataAttr) node).getName());
			if(functionSet != null){//�Ǻ궨��
				result.insertInstruction(result.getCurrentPoint()+1, new InstructionCallMacro(((OperateDataAttr) node).getName()));
			}else{
			  result.addLoadAttrInstruction((OperateDataAttr)node,node.getMaxStackSize());	
			  if(node.getChildren() != null){
				  throw new Exception("���ʽ���ô���");
			  }
			}  
		}else if(node instanceof OperateData){			  	
		  result.addLoadDataInstruction((OperateData)node,node.getMaxStackSize());	
		  if(node.getChildren() != null){
			  throw new Exception("���ʽ���ô���");
		  }
		}else if(node instanceof ExpressItem && ((ExpressItem)node).name.equalsIgnoreCase("cache")){			  	
			result.insertInstruction(result.getCurrentPoint()+1, new InstructionCachFuncitonCall());
		}else if(node instanceof ExpressItem && ((ExpressItem)node).name.equalsIgnoreCase("macro")){
			createInstructionSetForMacro(result,(ExpressItem)node);
		}else if(node instanceof ExpressItem && ((ExpressItem)node).name.equalsIgnoreCase("call")){
			for(ExpressTreeNode tmpNode :node.getChildren()){
				createInstructionSetPrivate(result,tmpNode,false);					
			}
			result.insertInstruction(result.getCurrentPoint()+1, new InstructionCallFunction());
		}else if(node instanceof ExpressItem && ((ExpressItem)node).name.equalsIgnoreCase("if")){
			returnVal = createInstructionSetForIf(result,(ExpressItem)node);			
		}else if(node instanceof ExpressTreeNodeRoot){
			int tmpPoint = result.getCurrentPoint()+1;
			boolean hasDef = false;
			for(ExpressTreeNode tmpNode : ((ExpressTreeNodeRoot)node).getChildren()){
				if(result.getCurrentPoint() >=0 && ( result.getInstruction(result.getCurrentPoint()) instanceof InstructionClearDataStack == false)){
				   result.insertInstruction(result.getCurrentPoint()+1, new InstructionClearDataStack());
				}
				boolean tmpHas =   createInstructionSetPrivate(result,tmpNode,false);
				hasDef = hasDef || tmpHas;
			}
			if(hasDef == true && isRoot == false){
			    result.insertInstruction(tmpPoint, new InstructionOpenNewArea());			
			    result.insertInstruction(result.getCurrentPoint()+1, new InstructionCloseNewArea());
			}
			returnVal = false;
		}else if(node instanceof ExpressItem){
			ExpressItem tmpExpressItem = (ExpressItem)node;
			OperatorBase op = m_operatorManager.newInstance(tmpExpressItem);
			ExpressTreeNode[] children = node.getChildren();
			int [] finishPoint = new int[children.length];
			for(int i =0;i < children.length;i++){
				ExpressTreeNode tmpNode = children[i];
				boolean tmpHas =  createInstructionSetPrivate(result,tmpNode,false);
				returnVal = returnVal || tmpHas;
				finishPoint[i] = result.getCurrentPoint();
			}
			if(op instanceof OperatorReturn){
				result.insertInstruction(result.getCurrentPoint()+1,new InstructionReturn());	
			}else{	
			   result.addOperatorInstruction(op,tmpExpressItem.opDataNumber,tmpExpressItem.getMaxStackSize());
				if(op instanceof OperatorAnd){
					result.insertInstruction(finishPoint[0]+1,new InstructionGoToWithCondition(false,result.getCurrentPoint() - finishPoint[0] + 1,false));
				}else if(op instanceof OperatorOr){
					result.insertInstruction(finishPoint[0]+1,new InstructionGoToWithCondition(true,result.getCurrentPoint() - finishPoint[0] + 1,false));
				}else if(op instanceof OperatorDef || op instanceof OperatorAlias){
					returnVal = true;
				}
			}
		}else{
			throw new Exception("��֧�ֵ���������:" + node.getClass());
		}
		return returnVal;
	}
	/**
	 * ������ָ��
	 * @param result
	 * @param node
	 * @return
	 * @throws Exception
	 */
	protected boolean createInstructionSetForMacro(InstructionSet result,ExpressItem node)throws Exception {
		String macroName =(String)((OperateData)node.getChildren()[0]).dataObject;
		ExpressTreeNodeRoot macroRoot = new ExpressTreeNodeRoot("macro-" + macroName);
		macroRoot.addChild(node.getChildren()[1]);
		InstructionSet macroInstructionSet = this.createInstructionSet(macroRoot);
		result.addMacroDefine(macroName, new FunctionInstructionSet(macroName,"macro",macroInstructionSet));
		return false;
	}

	/**
	 * ���� if ��ָ���
	 * @param result
	 * @param node
	 * @throws Exception
	 */
	protected boolean createInstructionSetForIf(InstructionSet result,ExpressItem node)throws Exception {
    	ExpressTreeNode[] children = node.getChildren();
    	if(children.length < 2){
    		throw new Exception("if ������������Ҫ2�������� " );
    	}else if (children.length == 2) {
    		//����һ����֧
    		ExpressTreeNode[] oldChilder =  children;
    		children = new ExpressTreeNode[3];
    		children[0] = oldChilder[0];
    		children[1] = oldChilder[1];
    		children[2] = new OperateData(null,void.class);    			
    	}else if(children.length > 3){
    		throw new Exception("if ���������ֻ��3�������� " );
    	}
		int [] finishPoint = new int[children.length];
   		boolean r1 = createInstructionSetPrivate(result,children[0],false);//condition	
		finishPoint[0] = result.getCurrentPoint();
		boolean r2 = createInstructionSetPrivate(result,children[1],false);//true		
		result.insertInstruction(finishPoint[0]+1,new InstructionGoToWithCondition(false,result.getCurrentPoint() - finishPoint[0] + 2,true));
		finishPoint[1] = result.getCurrentPoint();
		boolean r3 = createInstructionSetPrivate(result,children[2],false);//false
		result.insertInstruction(finishPoint[1]+1,new InstructionGoTo(result.getCurrentPoint() - finishPoint[1] + 1));  		
        return r1 || r2 || r3;
	}
    
	protected void printTreeNode(ExpressTreeNode node, int level) {
		for (int i = 0; i < level; i++) {
			System.out.print("   ");
		}
		
		System.out.println(node);
		ExpressTreeNode[] children = node.getChildren();
		if (children == null) {
			return;
		}
		for (ExpressTreeNode item : children) {
			printTreeNode(item, level + 1);
		}
	}

	protected boolean isNAddOneParameterCount(ExpressItem operatorItem){
	  if(operatorItem instanceof ExpressItemMethod || operatorItem instanceof ExpressItemNew)
		  return true;
	  return this.m_operatorManager.isNAddOneParameterCount(operatorItem.name);
  }
  protected String getPrintInfo(Object[] list,String splitOp){
  	StringBuffer buffer = new StringBuffer();
	for(int i=0;i<list.length;i++){
		if(i > 0){buffer.append(splitOp);}
		buffer.append("{" + list[i] +"}");
	}
	return buffer.toString();
  }
  protected String getPrintInfo2(Object[] list,String splitOp){
	  	StringBuffer buffer = new StringBuffer();
		for(int i=0;i<list.length;i++){
			if(i > 0){buffer.append(splitOp);}
			buffer.append(list[i]);
		}
		return buffer.toString();
	  }

  protected ExpressTreeNodeRoot parseCResult(String condition)throws Exception{

		String[] tmpList = parse(condition);
	    if(log.isDebugEnabled()){
	    	log.debug("ִ�еı��ʽ��" + condition);	    	
	    	log.debug("���ʷֽ���:" + getPrintInfo(tmpList,","));
	    }
	    Object[] tmpObjectList = getOpObjectList(tmpList);
	    if(log.isDebugEnabled()){
	    	log.debug("�﷨�ֽ���:" + getPrintInfo(tmpObjectList,","));
	    }
	    ExpressTreeNodeRoot result = getCResult(tmpObjectList);
//	    if(log.isDebugEnabled()){
//	    	log.debug("��׺���ʽ:" + getPrintInfo(result,","));
//	    }
	    return result;
}
  public InstructionSet parseInstructionSet(String condition)throws Exception{
	  InstructionSet result = createInstructionSet(this.parseCResult(condition));
	  if(log.isDebugEnabled()){
	    	log.debug("���ɵ�ָ�:\n" + result);
	    }
	  return result;
	  
  }
  
  public Object execute(String expressString,List errorList,boolean isCache,IExpressContext context) throws Exception{
	  return execute(expressString,errorList,isCache,context,null,false);
  }

  public Object execute(String expressString,List errorList,boolean isCache,IExpressContext context,FuncitonCacheManager aFunctionCacheMananger,boolean isTrace) throws Exception{
	 return  this.execute(new String[]{expressString}, errorList, isCache, context, aFunctionCacheMananger, isTrace);
  }
  public Object execute(String[] expressString,List errorList,boolean isCache,IExpressContext context,FuncitonCacheManager aFunctionCacheMananger,boolean isTrace) throws Exception{
		InstructionSet[] parseResult = new InstructionSet[expressString.length];
		for (int i = 0; i < parseResult.length; i++) {
			if (isCache == true) {
				parseResult[i] = expressInstructionSetCache.get(expressString[i]);
				if (parseResult[i] == null) {
					synchronized (expressInstructionSetCache) {
						parseResult[i] = expressInstructionSetCache
								.get(expressString[i]);
						if (parseResult[i] == null) {
							parseResult[i] = this
									.parseInstructionSet(expressString[i]);
							expressInstructionSetCache.put(expressString[i],
									parseResult[i]);
							// System.out.println(parseResult);
						}
					}
				}
			} else {
				parseResult[i] = this.parseInstructionSet(expressString[i]);
			}
		}
	  return this.execute(parseResult,null,context, errorList, aFunctionCacheMananger, isTrace);
 } 
  public Object execute(InstructionSet[] instructionSets,ExpressLoader loader,IExpressContext context,
		  List errorList,FuncitonCacheManager aFunctionCacheMananger,boolean isTrace) throws Exception{
	 return  InstructionSet.execute(instructionSets,loader,context, errorList, aFunctionCacheMananger, isTrace);
  }

  /**
   * �������
   */
  public void clearExpressCache(){
	  this.expressInstructionSetCache.clear();
  }
  
  /**
   * ִ��Ԥ�����ı��ʽ
   * @param context ���������Ҫ�������ģ����û�����ԣ�������null
   * @param list Ԥ�����ı��ʽ
   * @param errorList ���������Ϣ��List
   * @return ����Ľ��
   * @throws Exception
   */
  protected final Object executeWithPreCompile(InstructionSetContext context,Object[] expressItems,List errorList) throws Exception
  {
    if (expressItems == null)       
    	return null;
    Stack sdata = new Stack();
    int i =0;
    while (i < expressItems.length)
    {
      if (expressItems[i] instanceof OperateData)
      {
         sdata.push(expressItems[i]);
         i++;
      }else if (expressItems[i] instanceof ExpressItem){
         ExpressItem opItem = (ExpressItem)expressItems[i];
         OperatorBase op = m_operatorManager.newInstance(opItem);
         int opDataNumber =m_operatorManager.getDataMember(op.getName());
         if (opDataNumber <0)
           opDataNumber = opItem.opDataNumber;

         OperateData[] parameterList  = null;
         if (opDataNumber >=0)
            parameterList = new OperateData[opDataNumber];

         for(int index = opDataNumber - 1;index>=0;index--){
           parameterList[index] = (OperateData)sdata.pop();
         }
         OperateData TmpResult =op.execute(context,parameterList,errorList);
         sdata.push(TmpResult);
         i++;
      }
    }
    return ((OperateData)sdata.pop()).getObject(context);    

  }
  
  protected final ExpressTreeNode preCompileOperatorTree(Object[] expressItems) throws Exception
  { 
    if (expressItems == null){    
    	throw new Exception("���ʽ����Ϊ��");
    }
    Stack sdata = new Stack();
    int i =0;
    while (i < expressItems.length)
    {
      if (expressItems[i] instanceof OperateData)
      {
         sdata.push(expressItems[i]);
         i++;
      }else if (expressItems[i] instanceof ExpressItem){
         ExpressItem opItem = (ExpressItem)expressItems[i];

         //����׼ȷ�Ĳ���������
         int opDataNumber =m_operatorManager.getDataMember(opItem.name);
         if (opDataNumber <0){
           opDataNumber = opItem.opDataNumber;
         }
         opItem.opDataNumber = opDataNumber;         
         
         List<ExpressTreeNode> tmpList = new ArrayList<ExpressTreeNode>();         
         for(int index = opDataNumber - 1;index>=0;index--){
        	 //ȷ���������ŵĲ�����
        	 ExpressTreeNode tmpNode = (ExpressTreeNode)sdata.pop();
        	 if(tmpNode instanceof MyPlace){
        		 tmpNode = ((MyPlace)tmpNode).op;
        	 }
        	 tmpNode.setParent(opItem);
             tmpList.add(0,tmpNode);
         }
         opItem.setChildren(tmpList.toArray(new ExpressTreeNode[0]));
         sdata.push(new MyPlace(opItem));
         i++;
      }
    }
    ExpressTreeNode rootNode = (ExpressTreeNode)sdata.pop();
    if(rootNode instanceof MyPlace){
    	rootNode = ((MyPlace)rootNode).op;
    }
    return rootNode;
  }

   private  void splitOperator(String opStr,ArrayList ObjList){
     while (opStr.length() >0){ //����������ִ�
          boolean isFind = false;
          int index =opStr.length();
          while (index > 0)
          {
              if (this.m_operatorManager.isOperator(opStr.substring(0,index)) == true)
              {
                 ObjList.add(opStr.substring(0,index));
                 opStr = opStr.substring(index);
                 isFind = true;
                 break;
              }
              else
                index = index - 1;
          }
          if(isFind == false)
             opStr = opStr.substring(1);
     }
  }
   protected boolean isNumber(String str){
    if(str == null || str.equals(""))
      return false;
    char c = str.charAt(0);
    if (c >= '0' && c <= '9') { //����
      return true;
    }
    else {
      return false;
    }
  }
  /**
   * �ı�������������.����Ϊ�������Ŵ���
   * @param str String
   * @throws Exception
   * @return String[]
   */
   protected String[] parse(String str) throws Exception
  {

    if (str == null)
       return new String[0];
    str = str.trim();
    if(str.endsWith(";") == false ){
      str = str +";";
    }
//    if(str.indexOf(';') == str.length() -1 && str.indexOf("return") < 0){
//    	str =  "return " + str;
//    }
    String tmpWord ="";
    String tmpOpStr ="";
    char c;
    ArrayList list = new ArrayList();
    int i= 0;
    while(i<str.length())
    {
       c = str.charAt(i);
      if (c=='"' || c=='\''){
        int index = str.indexOf(c,i + 1);
        if (index < 0)
        	throw new Exception("�ַ���û�йر�");
        //�Ƚ�������������У�����������
        splitOperator(tmpOpStr,list);
        tmpOpStr="";
        if (tmpWord.length() >0){
            list.add(tmpWord);
            tmpWord  = "";
        }
        list.add(str.substring(i,index + 1));
        i = index + 1;
      }else if (((c >='0') && (c <='9'))
            || ((c >='a') && (c <='z'))
            || ((c >='A') && (c <='Z'))
            || (c=='\'')
            || (c=='$')
            || (c==':')
            || (c=='_')
            || (c > 127))  //��׼�ַ�
       {
           tmpWord = tmpWord + c;
           i = i + 1;
           splitOperator(tmpOpStr,list);
           tmpOpStr = "";
       }else if(c=='.' && this.isNumber(tmpWord) == true){
           //�����֣������ݴ���
           tmpWord = tmpWord + c;
           i = i + 1;
           splitOperator(tmpOpStr,list);
           tmpOpStr = "";
       }else //���������ָ��
       {
         tmpOpStr = tmpOpStr + c;
         i = i + 1;
         if (tmpWord.length() >0)
         {    list.add(tmpWord);
              tmpWord = "";
         }
       }
    }

    if (tmpWord.length() >0)
    {    list.add(tmpWord);
         tmpWord = "";
    }
    splitOperator(tmpOpStr,list);

    String result[] = new String[list.size()];
    list.toArray(result);
    return result;
  }

  public void addPackage(String aPackageName){
    this.m_import.addPackage(aPackageName);
  }
  public void removePackage(String aPackageName){
    this.m_import.removePackage(aPackageName);
  }
  public void resetPackages(){
    this.m_import.resetPackages();
  }

}




 class ExpressItem extends ExpressTreeNodeImple {
   protected String name;
   protected String aliasName;
   protected int opDataNumber;
 //  public int point = -1;
   
   
   public ExpressItem(String aName){
	   this(aName,aName);
   }
   public ExpressItem(String aAliasName, String aName){
     name = aName;
     this.aliasName = aAliasName;
     this.opDataNumber = 0;
   }
   public String toString()
   {
	   return this.aliasName;
   }
   public String getAliasName(){
	   return this.aliasName;
   }
 }
 class ExpressItemNew extends ExpressItem{
   public ExpressItemNew(){
     super("new");
   }
 }

 class ExpressItemField extends ExpressItem{
   protected String fieldName;
   public ExpressItemField(String aName,String aFieldName){
     super(aName);
     this.fieldName = aFieldName;
   }
   public String toString()
   {
     return "Operator:" + name +":" + this.fieldName+ " OperandNumber:" + this.opDataNumber;
   }
 }
 class ExpressItemMethod extends ExpressItem{
   protected String methodName;
   public ExpressItemMethod(String aName,String aMethodName){
     super(aName);
     this.methodName = aMethodName;
   }
   public String toString()
   {
     return "Operator:" + name +":" + this.methodName+ " OperandNumber:" + this.opDataNumber;
   }

 }
 /**
  * �������
  * @author xuannan
  *
  */
 class ExpressItemDef extends ExpressItem{
	   protected String type;
	   protected String varName;	   
	   public ExpressItemDef(String aAliasName,String aName,String aType,String aVarName){
	     super(aAliasName,aName);
	     this.type = aType;
	     this.varName = aVarName;
	   }
	   public String toString()
	   {
	     return "Operator:" + this.aliasName +":" + this.type+ " name:" + this.varName;
	   }

	 }
 @SuppressWarnings("unchecked")
 class ExpressImport {

 	protected List m_packages = new ArrayList();

 	public ExpressImport() {
 		this.resetPackages();
 	}

 	public void addPackage(String aPackageName) {
 		this.m_packages.add(aPackageName);
 	}

 	public void removePackage(String aPackageName) {
 		this.m_packages.remove(aPackageName);
 	}

 	public void resetPackages() {
 		this.m_packages.clear();
 		m_packages.add("java.lang");
 		m_packages.add("java.util");
 	}

 	public Class getClass(String name) {
 		Class result = null;
 		// ���������б�������ֱ�Ӷ�λ
 		if (name.indexOf(".") >= 0) {
 			try {
 				result = Class.forName(name);
 			} catch (ClassNotFoundException ex) {
 			}
 			return result;
 		}
 		if (Integer.TYPE.getName().equals(name) == true)
 			return Integer.TYPE;
 		if (Short.TYPE.getName().equals(name) == true)
 			return Short.TYPE;
 		if (Long.TYPE.getName().equals(name) == true)
 			return Long.TYPE;
 		if (Double.TYPE.getName().equals(name) == true)
 			return Double.TYPE;
 		if (Float.TYPE.getName().equals(name) == true)
 			return Float.TYPE;
 		if (Byte.TYPE.getName().equals(name) == true)
 			return Byte.TYPE;
 		if (Character.TYPE.getName().equals(name) == true)
 			return Character.TYPE;
 		if (Boolean.TYPE.getName().equals(name) == true)
 			return Boolean.TYPE;

 		for (int i = 0; i < m_packages.size(); i++) {
 			String tmp = (String) m_packages.get(i) + "." + name;
 			try {
 				result = Class.forName(tmp);
 			} catch (ClassNotFoundException ex) {
 				//
 			}
 			if (result != null) {
 				return result;
 			}
 		}
 		return null;
 	}
 }
