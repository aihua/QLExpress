package com.ql.util.express;

import java.math.BigDecimal;

/**
 * 数字运行函数集合
 * @author qhlhl2010@gmail.com
 *
 */
public class OperatorOfNumber {
	
	public static void main(String[] args) throws Exception{
		Object o1 = new Integer(1000);
		Object o2 = new Long(3);
		Object o;
		o = OperatorOfNumber.Add.execute(o1,o2);
		System.out.println(o.getClass() + ":" + o);
		o = OperatorOfNumber.Subtract.execute(o1,o2);
		System.out.println(o.getClass() + ":" + o);
		o = OperatorOfNumber.Multiply.execute(o1,o2);
		System.out.println(o.getClass() + ":" + o);
		o = OperatorOfNumber.Divide.execute(o1,o2);
		System.out.println(o.getClass() + ":" + o);
		o = OperatorOfNumber.Modulo.execute(o1,o2);
		System.out.println(o.getClass() + ":" + o);
		
	}
	
    public static int getSeq(Class aClass){
    	if(aClass.equals(Byte.class)) return 1;
    	if(aClass.equals(Short.class)) return 2;
    	if(aClass.equals(Integer.class)) return 3;
    	if(aClass.equals(Long.class)) return 4;
    	if(aClass.equals(Float.class)) return 5;
    	if(aClass.equals(Double.class)) return 6;
         return -1;
    }
	public static final class Add {
		public static Object execute(Object op1, Object op2) throws Exception {
			if(op1 == null){
				op1 = "null";
			}
			if(op2 == null){
				op2 = "null";
			}
			if (op1 instanceof String || op2 instanceof String) {				
				return op1.toString() + op2.toString();
			}
			if(op1 instanceof Number == false){
				throw new Exception("数据类型错误:" + op1.getClass().getName() + "不能执行 \"+\"操作");
			}
			if(op2 instanceof Number == false){
				throw new Exception("数据类型错误:" + op2.getClass().getName() + "不能执行 \"+\"操作");
			}
			int type1 = getSeq(op1.getClass());
			int type2 = getSeq(op2.getClass());
			int type = type1 >  type2 ? type1:type2;
			if(type == 1) return ((Number)op1).byteValue() + ((Number)op2).byteValue();
			if(type == 2) return ((Number)op1).shortValue() + ((Number)op2).shortValue();
			if(type == 3) return ((Number)op1).intValue() + ((Number)op2).intValue();
			if(type == 4) return ((Number)op1).longValue() + ((Number)op2).longValue();
			if(type == 5) return ((Number)op1).floatValue() + ((Number)op2).floatValue();
			if(type == 6) return ((Number)op1).doubleValue() + ((Number)op2).doubleValue();
			throw new Exception("不支持的对象执行了\"+\"操作");
		}
	}

	public static final class Subtract{
	 public  static  Object execute(Object op1,Object op2) throws Exception{
			if(op1 instanceof Number == false){
				throw new Exception("数据类型错误:" + op1.getClass().getName() + "不能执行 \"-\"操作");
			}
			if(op2 instanceof Number == false){
				throw new Exception("数据类型错误:" + op2.getClass().getName() + "不能执行 \"-\"操作");
			}
			int type1 = getSeq(op1.getClass());
			int type2 = getSeq(op2.getClass());
			int type = type1 >  type2 ? type1:type2;
			if(type == 1) return ((Number)op1).byteValue() - ((Number)op2).byteValue();
			if(type == 2) return ((Number)op1).shortValue() - ((Number)op2).shortValue();
			if(type == 3) return ((Number)op1).intValue() - ((Number)op2).intValue();
			if(type == 4) return ((Number)op1).longValue() - ((Number)op2).longValue();
			if(type == 5) return ((Number)op1).floatValue() - ((Number)op2).floatValue();
			if(type == 6) return ((Number)op1).doubleValue() - ((Number)op2).doubleValue();
			throw new Exception("不支持的对象执行了\"-\"操作");
	    }
	}

	public static final class Multiply{

	    public static Object execute(Object op1,Object op2) throws Exception {
			if(op1 instanceof Number == false){
				throw new Exception("数据类型错误:" + op1.getClass().getName() + "不能执行 \"*\"操作");
			}
			if(op2 instanceof Number == false){
				throw new Exception("数据类型错误:" + op2.getClass().getName() + "不能执行 \"*\"操作");
			}
			int type1 = getSeq(op1.getClass());
			int type2 = getSeq(op2.getClass());
			int type = type1 >  type2 ? type1:type2;
			if(type == 1) return ((Number)op1).byteValue() * ((Number)op2).byteValue();
			if(type == 2) return ((Number)op1).shortValue() * ((Number)op2).shortValue();
			if(type == 3) return ((Number)op1).intValue() * ((Number)op2).intValue();
			if(type == 4) return ((Number)op1).longValue() * ((Number)op2).longValue();
			if(type == 5) return ((Number)op1).floatValue() * ((Number)op2).floatValue();
			if(type == 6) return ((Number)op1).doubleValue() * ((Number)op2).doubleValue();
			throw new Exception("不支持的对象执行了\"*\"操作");
	    }
	}

 public static final class Divide
	{
	   public static Object execute(Object op1,Object op2) throws Exception{
		   if(op1 instanceof Number == false){
				throw new Exception("数据类型错误:" + op1.getClass().getName() + "不能执行 \"/\"操作");
			}
			if(op2 instanceof Number == false){
				throw new Exception("数据类型错误:" + op2.getClass().getName() + "不能执行 \"/\"操作");
			}
			int type1 = getSeq(op1.getClass());
			int type2 = getSeq(op2.getClass());
			int type = type1 >  type2 ? type1:type2;
			if(type == 1) return ((Number)op1).byteValue() / ((Number)op2).byteValue();
			if(type == 2) return ((Number)op1).shortValue() / ((Number)op2).shortValue();
			if(type == 3) return ((Number)op1).intValue() / ((Number)op2).intValue();
			if(type == 4) return ((Number)op1).longValue() / ((Number)op2).longValue();
			if(type == 5) return ((Number)op1).floatValue() / ((Number)op2).floatValue();
			if(type == 6) return ((Number)op1).doubleValue() / ((Number)op2).doubleValue();
			throw new Exception("不支持的对象执行了\"/\"操作");
	    }
	}

 public static final class Modulo
 {
    public static Object execute(Object op1,Object op2) throws Exception{
		   if(op1 instanceof Number == false){
				throw new Exception("数据类型错误:" + op1.getClass().getName() + "不能执行 \"mod\"操作");
			}
			if(op2 instanceof Number == false){
				throw new Exception("数据类型错误:" + op2.getClass().getName() + "不能执行 \"mod\"操作");
			}
			int type1 = getSeq(op1.getClass());
			int type2 = getSeq(op2.getClass());
			int type = type1 >  type2 ? type1:type2;
			if(type == 1) return ((Number)op1).byteValue() % ((Number)op2).byteValue();
			if(type == 2) return ((Number)op1).shortValue() % ((Number)op2).shortValue();
			if(type == 3) return ((Number)op1).intValue() % ((Number)op2).intValue();
			if(type == 4) return ((Number)op1).longValue() % ((Number)op2).longValue();
			throw new Exception("不支持的对象执行了\"mod\"操作");
     }
 }
 public static final class Arith {

   private static final int DEF_DIV_SCALE = 10;
   private Arith() {
   }

   /**
    * 提供精確的加法運算。
    * @param v1 被加數
    * @param v2 加數
    * @return 兩個參數的和
    */
   public static double add(double v1, double v2) {
     BigDecimal b1 = new BigDecimal(Double.toString(v1));
     BigDecimal b2 = new BigDecimal(Double.toString(v2));
     return b1.add(b2).doubleValue();
   }

   /**
    * 提供精確的減法運算。
    * @param v1 被減數
    * @param v2 減數
    * @return 兩個參數的差
    */
   public static double sub(double v1, double v2) {
     BigDecimal b1 = new BigDecimal(Double.toString(v1));
     BigDecimal b2 = new BigDecimal(Double.toString(v2));
     return b1.subtract(b2).doubleValue();
   }

   /**
    * 提供精確的乘法運算。
    * @param v1 被乘數
    * @param v2 乘數
    * @return 兩個參數的積
    */
   public static double mul(double v1, double v2) {
     BigDecimal b1 = new BigDecimal(Double.toString(v1));
     BigDecimal b2 = new BigDecimal(Double.toString(v2));
     return b1.multiply(b2).doubleValue();
   }

   /**
    * 提供（相對）精確的除法運算，當發生除不盡的情況時，精確到
    * 小數點以後10位元，以後的數字四捨五入。
    * @param v1 被除數
    * @param v2 除數
    * @return 兩個參數的商
    */
   public static double div(double v1, double v2) {
     return div(v1, v2, DEF_DIV_SCALE);
   }

   /**
    * 提供（相對）精確的除法運算。當發生除不盡的情況時，由scale參數指
    * 定精度，以後的數字四捨五入。
    * @param v1 被除數
    * @param v2 除數
    * @param scale 表示表示需要精確到小數點以後幾位。
    * @return 兩個參數的商
    */
   public static double div(double v1, double v2, int scale) {
     if (scale < 0) {
       throw new IllegalArgumentException(
           "The scale must be a positive integer or zero");
     }
     BigDecimal b1 = new BigDecimal(Double.toString(v1));
     BigDecimal b2 = new BigDecimal(Double.toString(v2));
     return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
   }

   /**
    * 提供精確的小數位四捨五入處理。
    * @param v 需要四捨五入的數位
    * @param scale 小數點後保留幾位
    * @return 四捨五入後的結果
    */
   public static double round(double v, int scale) {
     if (scale < 0) {
       throw new IllegalArgumentException(
           "The scale must be a positive integer or zero");
     }
     BigDecimal b = new BigDecimal(Double.toString(v));
     BigDecimal one = new BigDecimal("1");
     return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
   }
 }
}