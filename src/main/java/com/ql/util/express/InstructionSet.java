package com.ql.util.express;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class CallResult{
	Object returnValue;
	boolean isExit;
}

/**
 * ���ʽִ�б�����γɵ�ָ���
 * @author qhlhl2010@gmail.com
 *
 */
@SuppressWarnings("unchecked")
public class InstructionSet {
  private int maxStackSize  = -1;
  private List<Instruction> list = new ArrayList<Instruction>();
  /**
   * �����ͺ궨��
   */
  private Map<String,FunctionInstructionSet> functionDefine = new HashMap<String,FunctionInstructionSet>();
  
  /**
   * ����ִ��ָ��ϣ�ָ�����Թ��� �����ͺ���
   * @param sets
   * @param aOperatorManager
   * @param aContext
   * @param errorList
   * @param aFunctionCacheMananger
   * @param isTrace
   * @return
   * @throws Exception
   */
  protected static Object execute(InstructionSet[] sets,
	IExpressContext aContext, List errorList,
	FuncitonCacheManager aFunctionCacheMananger, boolean isTrace)
	throws Exception {
	  InstructionSetContext context = new InstructionSetContext<String, Object>(
				aContext, null, aFunctionCacheMananger);;
	  RunEnvironment environmen = null;
	  Object result =null;
	  for(int i=0;i< sets.length;i++){
		 InstructionSet tmpSet = sets[i];
		environmen = new RunEnvironment(tmpSet.maxStackSize, tmpSet,
				(InstructionSetContext) context, isTrace);
		context.setEnvironmen(environmen);
		CallResult tempResult = tmpSet.excuteInner(environmen, context, errorList, i == sets.length - 1 );
		if(tempResult.isExit == true){
			result =  tempResult.returnValue;
			break;
		}		
	  }	
		if (aFunctionCacheMananger == null) {
			// �����ָ��������Ļ��棬�����
			context.clearFuncitonCacheManager();
		}
	  return result;

  }
  /**
   * ִ�����еĲ���ָ��
   * @param context
   * @param errorList
   * @return
   * @throws Exception
   */
	public CallResult excuteInner(RunEnvironment environmen,InstructionSetContext context,List errorList,boolean isLast)
			throws Exception {
		Instruction instruction;
		//������export����������
		for(FunctionInstructionSet item : this.functionDefine.values()){
			context.addSymbol(item.name, item.instructionSet);
		}
		
		boolean isReturnExit = false;
		while (environmen.getProgramPoint() < this.list.size()) {
			if (environmen.isExit() == true) {
				isReturnExit = true;
				break;
			}
			instruction = this.list.get(environmen.getProgramPoint());
			instruction.execute(environmen, errorList);
		}
		if (isReturnExit == false && isLast == true) {// ����ִ�������е�ָ�������Ĵ���
			if (environmen.getDataStackSize() > 0) {
				environmen.setReturnValue(environmen.pop().getObject(context));
			}
			isReturnExit = true;
		}
		if (environmen.getDataStackSize() > 1) {
			throw new Exception("�ڱ��ʽִ����Ϻ󣬶�ջ�л����ڶ������");
		}
		CallResult result = new CallResult();
		result.returnValue = environmen.getReturnValue();
		result.isExit = isReturnExit;
		return result;
	}
  
  public void addMacroDefine(String macroName,FunctionInstructionSet iset){
	  this.functionDefine.put(macroName, iset);
  }
  public FunctionInstructionSet getMacroDefine(String macroName){
	  return this.functionDefine.get(macroName);
  }
  
  /**
   * ��Ӳ�������ջָ��
   * @param operateData
   * @param stackSize
   */
  public void addLoadDataInstruction(OperateData operateData,int stackSize){
	  this.list.add(new InstructionLoadData(operateData));
	  if(stackSize > this.maxStackSize){
		  this.maxStackSize = stackSize;
	  }	  
  }
  
  public void addLoadAttrInstruction(OperateDataAttr operateData,int stackSize){
	  this.list.add(new InstructionLoadAttr(operateData.name));
	  if(stackSize > this.maxStackSize){
		  this.maxStackSize = stackSize;
	  }	  
  }  
/**
 * ��Ӳ�������ָ��
 * @param operator
 * @param aOpDataNumber
 * @param stackSize
 */
  public void addOperatorInstruction(OperatorBase operator,int aOpDataNumber,int stackSize){
	  this.list.add(new InstructionOperator(operator,aOpDataNumber));
	  if(stackSize > this.maxStackSize){
		  this.maxStackSize = stackSize;
	  }
  }
  public void insertInstruction(int point,Instruction instruction){
	  this.list.add(point,instruction);
  } 
  public Instruction getInstruction(int point){
	  return this.list.get(point);
  }
  public int getCurrentPoint(){
	  return this.list.size() - 1; 
  }
  public String toString(){
	  
	  StringBuffer buffer = new StringBuffer();
	  //����궨��
	  for(FunctionInstructionSet set : this.functionDefine.values()){
		  buffer.append("macro ��" + set.name).append("\n");
		  buffer.append(set.instructionSet);
	  }	  
	  buffer.append("ָ��� ������ݳ���:" + (this.maxStackSize)).append("\n");
	  for(int i=0;i<list.size();i++){
		  buffer.append(i + 1).append(":").append(list.get(i)).append("\n");
	  }
	  return buffer.toString();
  }
  
}

/**
 * ִ�е����л���
 * @author qhlhl2010@gmail.com
 *
 */
@SuppressWarnings("unchecked")
class RunEnvironment {
    private boolean isTrace = false;	
	private int point = -1;
    private int programPoint = 0;
	private OperateData[] dataContainer;
	
	private boolean isExit = false;
	private Object returnValue = null; 
	
	private InstructionSet instructionSet;
	private InstructionSetContext context;
	
	
	public RunEnvironment(int aStackSize,InstructionSet aInstructionSet,InstructionSetContext aContext,boolean aIsTrace){
		if(aStackSize <0){
			aStackSize =0;
		}
		dataContainer = new OperateData[aStackSize];
		this.instructionSet = aInstructionSet;
		this.context = aContext;
		this.isTrace = aIsTrace;
	}
	
	
	public InstructionSet getInstructionSet() {
		return instructionSet;
	}


	public InstructionSetContext getContext(){
		return this.context;
	}
	public void setContext(InstructionSetContext aContext){
		this.context = aContext;
	}

	public boolean isExit() {
		return isExit;
	}
	public Object getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(Object value){
		this.returnValue = value;
	}
	public void quitExpress(Object aReturnValue){
		this.isExit = true;
		this.returnValue = aReturnValue;
	}
	public boolean isTrace(){
		return this.isTrace;
	}
	public int getProgramPoint() {
		return programPoint;
	}
	public void programPointAddOne() {
		programPoint ++ ;
	}
    public int getDataStackSize(){
    	return this.point + 1;
    }
	public void push(OperateData data){
		this.point++;
		if(this.point >= this.dataContainer.length){
		   ensureCapacity(this.point + 1);
		}
		this.dataContainer[point] = data;
	}
	public OperateData peek(){
		if(point <0){
			throw new RuntimeException("ϵͳ�쳣����ջָ�����");
		}
		return this.dataContainer[point];		
	}
	public OperateData pop(){
		if(point <0)
			throw new RuntimeException("ϵͳ�쳣����ջָ�����");
		OperateData result = this.dataContainer[point];
		this.point--;
		return result;
	}
	public void clearDataStack(){
		this.point = -1;
	}
	public void gotoWithOffset(int aOffset ){
		this.programPoint = this.programPoint + aOffset;
	}

	public OperateData[] popArray(InstructionSetContext context,int len) throws Exception {
		OperateData[] result = new OperateData[len];
		int start = point - len + 1;
		if(start <0){
			throw new Exception("��ջ�����������ʽ�Ƿ����");
		}
		for (int i = 0 ; i < len; i++) {
			result[i] = this.dataContainer[start + i];
			if(void.class.equals(result[i].getType(context))){
				throw new Exception("void ���ܲ����κβ�������,����ʹ���ڱ��ʽ��ʹ����û�з���ֵ�ĺ���,���߷�֧��������if���");
			}
		}
		point = point - len;
		return result;
	}

	public void ensureCapacity(int minCapacity) {
		int oldCapacity = this.dataContainer.length;
		if (minCapacity > oldCapacity) {
			int newCapacity = (oldCapacity * 3) / 2 + 1;
			if (newCapacity < minCapacity){
				newCapacity = minCapacity;
			}
			this.dataContainer = Arrays.copyOf(this.dataContainer, newCapacity);
		}
	}
}
@SuppressWarnings("unchecked")
abstract class Instruction{
	protected static final Log log = LogFactory.getLog(Instruction.class);	
	public abstract void execute(RunEnvironment environment,List errorList)throws Exception;
}
/**
 * װ�����ݵ���ջ
 * @author qhlhl2010@gmail.com
 *
 */
@SuppressWarnings("unchecked")
class InstructionLoadData extends Instruction{
    OperateData operateData;
    InstructionLoadData(OperateData aOperateData){
    	this.operateData = aOperateData;
    }

	public void execute(RunEnvironment environment,List errorList)throws Exception{
		if(environment.isTrace()){			
			if(this.operateData instanceof OperateDataAttr){
				log.debug(this +":" + this.operateData.getObject(environment.getContext()));
			}else{
				log.debug(this );
			}			
		}
		environment.push(this.operateData);
		environment.programPointAddOne();
	}
	public String toString(){
	  if(this.operateData instanceof OperateDataAttr){
		  return "LoadData attr:" +this.operateData.toString();	
	  }else{
		  return "LoadData " +this.operateData.toString();	
	  }
	}	
}

@SuppressWarnings("unchecked")
class InstructionLoadAttr extends Instruction{
    String attrName;
    InstructionLoadAttr(String aName){
    	this.attrName = aName;
    }
	public void execute(RunEnvironment environment,List errorList)throws Exception{
		Object o = environment.getContext().getSymbol(this.attrName);
		if(o != null && o instanceof InstructionSet){//�Ǻ�������ִ��
			if(environment.isTrace()){
				log.debug("ָ��ת���� LoadAttr -- >CallMacro ");						
			}
			new InstructionCallMacro(this.attrName).execute(environment, errorList);
		}else{
			if(environment.isTrace()){
				log.debug(this +":" + environment.getContext().get(this.attrName));						
			}
		    environment.push((OperateDataAttr)environment.getContext().getSymbol(this.attrName));
		    environment.programPointAddOne();
		}
	}
	public String toString(){
		  return "LoadAttr:" +this.attrName;	
	}
}

class InstructionClearDataStack extends Instruction{
	InstructionClearDataStack(){
    }
	@SuppressWarnings("unchecked")
	public void execute(RunEnvironment environment,List errorList)throws Exception{
		//Ŀǰ��ģʽ������Ҫִ���κβ���
		if(environment.isTrace()){
			log.debug(this);
		}
		environment.clearDataStack();
		environment.programPointAddOne();
	}
	public String toString(){
	  return "clearDataStack";	
	}
}

/**
 * ��ʼһ���µ�������
 * @author xuannan
 *
 */
class InstructionOpenNewArea extends Instruction{
	InstructionOpenNewArea(){
    }
	@SuppressWarnings("unchecked")
	public void execute(RunEnvironment environment,List errorList)throws Exception{
		//Ŀǰ��ģʽ������Ҫִ���κβ���
		if(environment.isTrace()){
			log.debug(this);
		}
		environment.setContext(new InstructionSetContext<String,Object>(environment.getContext(),environment,
				 environment.getContext().getFunctionCachManagerNoCreate()));
		environment.programPointAddOne();
	}
	public String toString(){
	  return "openNewArea";	
	}
}

class InstructionCallMacro extends Instruction{
	String name;
	InstructionCallMacro(String aName){
		this.name = aName;
    }
	@SuppressWarnings("unchecked")
	public void execute(RunEnvironment environment,List errorList)throws Exception{
		//Ŀǰ��ģʽ������Ҫִ���κβ���
		if(environment.isTrace()){
			log.debug(this);
		}
		Object functionSet = environment.getContext().getSymbol(this.name);
		
		Object result =InstructionSet.execute(new InstructionSet[]{(InstructionSet)functionSet},
				environment.getContext(), errorList, environment.getContext().getFunctionCachManagerNoCreate(),
				environment.isTrace());
		environment.push(new OperateData(result,null));
		
		environment.programPointAddOne();
	}
	public String toString(){
	  return "call macro " + this.name ;	
	}
}
/**
 * 
 * �ر�һ��������
 * @author xuannan
 *
 */
class InstructionCloseNewArea extends Instruction{
	InstructionCloseNewArea(){
    }
	@SuppressWarnings("unchecked")
	public void execute(RunEnvironment environment,List errorList)throws Exception{
		//Ŀǰ��ģʽ������Ҫִ���κβ���
		if(environment.isTrace()){
			log.debug(this);
		}
		environment.setContext((InstructionSetContext)environment.getContext().getParent());
		environment.programPointAddOne();
	}
	public String toString(){
	  return "closeNewArea";	
	}
}

class InstructionCachFuncitonCall extends Instruction{
	InstructionCachFuncitonCall(){
    }
	@SuppressWarnings("unchecked")
	public void execute(RunEnvironment environment,List errorList)throws Exception{
		//Ŀǰ��ģʽ������Ҫִ���κβ���
		if(environment.isTrace()){
			log.debug(this);
		}
		environment.getContext().startFunctionCallCache();
		environment.programPointAddOne();
	}
	public String toString(){
	  return "cacheFunctionCall";	
	}
}

class InstructionReturn extends Instruction{
    InstructionReturn(){
    }
	@SuppressWarnings("unchecked")
	public void execute(RunEnvironment environment,List errorList)throws Exception{
		//Ŀǰ��ģʽ������Ҫִ���κβ���
		if(environment.isTrace()){
			log.debug(this);
		}
		if(environment.getDataStackSize() >= 1){
			
		   environment.quitExpress(environment.pop().getObject(environment.getContext()));
		}else{
		   environment.quitExpress(null);
		}
		environment.programPointAddOne();
	}
	public String toString(){
	  return "return ";	
	}
	
}
class InstructionGoToWithCondition extends Instruction{
	/**
	 * ��תָ���ƫ����
	 */
    int offset;
    boolean condition;
    boolean isPopStackData;
    public InstructionGoToWithCondition(boolean aCondition,int aOffset,boolean aIsPopStackData){
    	this.offset = aOffset;
    	this.condition = aCondition;
    	this.isPopStackData = aIsPopStackData;
    }

	@SuppressWarnings("unchecked")
	public void execute(RunEnvironment environment,List errorList)throws Exception{

		Object o = null;
		if(this.isPopStackData == false){
		    o = environment.peek().getObject(environment.getContext());	
		}else{
			o = environment.pop().getObject(environment.getContext());	
		}
		if(o != null && o instanceof Boolean){
			if(((Boolean)o).booleanValue() == this.condition){
				if(environment.isTrace()){
					log.debug("goto +" + this.offset);
				}
				environment.gotoWithOffset(this.offset);
			}else{
				if(environment.isTrace()){
					log.debug("programPoint ++ ");
				}
				environment.programPointAddOne();
			}
		}else{
			throw new Exception("ָ�����:" + o + " ����Boolean");
		}
	}
	public String toString(){
	  String result = "GoToIf[" + this.condition +",isPop=" + this.isPopStackData +"] " ;
	  if(this.offset >=0){
		  result = result +"+";
	  }
	  result = result + this.offset;
	  return result;
	}
}
class InstructionGoTo extends Instruction{
	/**
	 * ��תָ���ƫ����
	 */
    int offset;
    boolean condition;
    public InstructionGoTo(int aOffset){
    	this.offset = aOffset;
    }

	@SuppressWarnings("unchecked")
	public void execute(RunEnvironment environment,List errorList) throws Exception {
		if(environment.isTrace()){
			log.debug(this);
		}
		environment.gotoWithOffset(this.offset);
	}
	public String toString(){
	  String result = "GoTo ";
	  if(this.offset >=0){
		  result = result +"+";
	  }
	  result = result + this.offset;
	  return result;
	}
}

/**
 * �������ָ������Ը���ÿ�����ͽ�����չ�������ϵͳ�ڵ�ָ���ϵ�ִ��Ч��
 * @author qhlhl2010@gmail.com
 *
 */
@SuppressWarnings("unchecked")
class InstructionOperator extends Instruction{
	OperatorBase operator;
	int opDataNumber;
	public InstructionOperator(OperatorBase aOperator,int aOpDataNumber){
	  this.operator = aOperator;
	  this.opDataNumber =aOpDataNumber;
	}
	public void execute(RunEnvironment environment,List errorList) throws Exception{		
		OperateData[] parameters = environment.popArray(environment.getContext(),this.opDataNumber);		
		if(environment.isTrace()){
			String str = this.operator.toString() + "(";
			for(int i=0;i<parameters.length;i++){
				if(i > 0){
					str = str + ",";
				}
				if(parameters[i] instanceof OperateDataAttr){
					str = str + parameters[i] + ":" + parameters[i].getObject(environment.getContext());
				}else{
				   str = str + parameters[i];
				}
			}
			str = str + ")";
			log.debug(str);
		}
		
		OperateData result = this.operator.execute(environment.getContext(),parameters, errorList);
		environment.push(result);
		environment.programPointAddOne();
	}
	public String toString(){
		String result = "OP : " + this.operator.toString() +  " OPNUMBER[" + this.opDataNumber +"]";
		return result;
	}
}	
class FunctionInstructionSet{
	String name;
	String type;
	InstructionSet instructionSet;
	public FunctionInstructionSet(String aName,String aType,InstructionSet aInstructionSet){
		this.name = aName;
		this.type = aType;
		this.instructionSet = aInstructionSet;		
	}
}
	