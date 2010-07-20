package com.ql.util.express;

import java.math.BigDecimal;

/**
 * �������к�������
 * @author qhlhl2010@gmail.com
 *
 */
public class OperatorOfNumber {
	public static final class Add {
	
		public static Object execute(Object op1, Object op2) throws Exception {
			
			Object result = null;
			boolean flag1;
			boolean flag2;
			boolean flag3;
			boolean flag4;
			boolean flag5;
			boolean flag = flag1 = flag2 = flag3 = flag4 = flag5 = false;
			if (op1 instanceof String || op2 instanceof String) {
				if (op1 == null)
					op1 = "null";
				if (op2 == null)
					op2 = "null";

				return op1.toString() + op2.toString();
			}

			if (op1 instanceof String)
				flag = true;
			else if (op1 instanceof Long)
				flag2 = true;
			else if (op1 instanceof Integer)
				flag2 = true;
			else if (op1 instanceof Double)
				flag4 = true;
			if (op2 instanceof String)
				flag1 = true;
			else if (op2 instanceof Long)
				flag3 = true;
			else if (op2 instanceof Integer)
				flag3 = true;
			else if (op2 instanceof Double)
				flag5 = true;
			if (flag)
				result = (String) op1 + op2;
			else if (flag2 && (flag3 || flag1)) {
				long l = 0L;
				try {
					if (flag2) {
						if (op1 instanceof Long)
							l = ((Long) op1).longValue();
						else
							l = ((Integer) op1).longValue();
					} else {
						l = Long.parseLong((String) op1);
					}
					if (flag3) {
						if (op2 instanceof Long)
							l += ((Long) op2).longValue();
						else
							l += ((Integer) op2).longValue();
					} else {
						l += Long.parseLong((String) op2);
					}
				} catch (NumberFormatException e) {
					throw new Exception("NumberFormatException �� "
							+ e.getMessage());
				}
				result = new Long(l);
			} else if (flag4 && (flag3 || flag5 || flag1) || flag5 && flag2) {
				double d = 0.0D;
				try {
					BigDecimal decimal = new BigDecimal(op1.toString());
					decimal = decimal.add(new BigDecimal(op2.toString()));
					d = decimal.doubleValue();
				} catch (NumberFormatException e) {
					throw new Exception("NumberFormatException �� "
							+ e.getMessage());
				}
				result = new Double(d);
			} else {
				throw new Exception("�������ʹ������");
			}
			return result;
		}
	}

	public static final class Subtract{
	 public  static  Object execute(Object op1,Object op2) throws Exception{
	        Object result = null;
	        boolean flag1;
	        boolean flag2;
	        boolean flag3;
	        boolean flag4;
	        boolean flag5;
	        boolean flag = flag1 = flag2 = flag3 = flag4 = flag5 = false;
	        if(op1 instanceof String)
	            flag = true;
	        else
	        if(op1 instanceof Long)
	            flag2 = true;
	        else
	        if(op1 instanceof Integer)
	            flag2 = true;
	        else
	        if(op1 instanceof Double)
	            flag4 = true;
	        if(op2 instanceof String)
	            flag1 = true;
	        else
	        if(op2 instanceof Long)
	            flag3 = true;
	        else
	        if(op2 instanceof Integer)
	            flag3 = true;
	        else
	        if(op2 instanceof Double)
	            flag5 = true;
	        if(flag2 && (flag3 || flag1) || flag3 && flag)
	        {
	            long l = 0L;
	            try
	            {
	                if(flag2)
	                {
	                    if(op1 instanceof Integer)
	                        l = ((Integer)op1).longValue();
	                    else
	                        l = ((Long)op1).longValue();
	                } else
	                {
	                    l = Long.parseLong((String)op1);
	                }
	                if(flag3)
	                {
	                    if(op2 instanceof Integer)
	                        l -= ((Integer)op2).longValue();
	                    else
	                        l -= ((Long)op2).longValue();
	                } else
	                {
	                    l -= Long.parseLong((String)op2);
	                }
	            }catch(NumberFormatException e) {
	              throw new Exception("NumberFormatException �� " + e.getMessage());
	           }
	            result = new Long(l);
	        } else
	        if(flag4 && (flag3 || flag5 || flag1) || flag5 && (flag2 || flag))
	        {
	            double d = 0.0D;
	            try
	            {
	                if(flag4)
	                    d = ((Double)op1).doubleValue();
	                else
	                if(flag2)
	                {
	                    if(op1 instanceof Integer)
	                        d = ((Integer)op1).doubleValue();
	                    else
	                        d = ((Long)op1).doubleValue();
	                } else
	                {
	                    d = Double.parseDouble((String)op1);
	                }
	                if(flag5)
	                    d -= ((Double)op2).doubleValue();
	                else
	                if(flag3)
	                {
	                    if(op2 instanceof Integer)
	                        d -= ((Integer)op2).doubleValue();
	                    else
	                        d -= ((Long)op2).doubleValue();
	                } else
	                {
	                    d -= Double.parseDouble((String)op2);
	                }
	            }catch(NumberFormatException e) {
	              throw new Exception("NumberFormatException �� " + e.getMessage());
	           }
	            result = new Double(d);
	        } else
	        {
	        	throw new Exception("�������ʹ������");
	        }
	        return result;
	    }

	}

	public static final class Multiply{

	    public static Object execute(Object op1,Object op2) throws Exception {
	        Object result = null;
	        boolean flag1;
	        boolean flag2;
	        boolean flag3;
	        boolean flag4;
	        boolean flag5;
	        boolean flag = flag1 = flag2 = flag3 = flag4 = flag5 = false;

	        if(op1 instanceof String)
	            flag = true;
	        else
	        if(op1 instanceof Long)
	            flag2 = true;
	        else
	        if(op1 instanceof Integer)
	            flag2 = true;
	        else
	        if(op1 instanceof Double)
	            flag4 = true;
	        if(op2 instanceof String)
	            flag1 = true;
	        else
	        if(op2 instanceof Long)
	            flag3 = true;
	        else
	        if(op2 instanceof Integer)
	            flag3 = true;
	        else
	        if(op2 instanceof Double)
	            flag5 = true;
	        if(flag2 && (flag3 || flag1) || flag3 && flag)
	        {
	            long l = 0L;
	            try
	            {
	                if(flag2)
	                {
	                    if(op1 instanceof Long)
	                        l = ((Long)op1).longValue();
	                    else
	                        l = ((Integer)op1).longValue();
	                } else
	                {
	                    l = Long.parseLong((String)op1);
	                }
	                if(flag3)
	                {
	                    if(op2 instanceof Long)
	                        l *= ((Long)op2).longValue();
	                    else
	                        l *= ((Integer)op2).longValue();
	                } else
	                {
	                    l *= Long.parseLong((String)op2);
	                }
	            }catch(NumberFormatException e) {
	              throw new Exception("NumberFormatException �� " + e.getMessage());
	           }
	            result = new Long(l);
	        } else
	        if(flag4 && (flag3 || flag5 || flag1) || flag5 && (flag2 || flag))
	        {
	            double d = 0.0D;
	            try
	            {
	                if(flag4)
	                    d = ((Double)op1).doubleValue();
	                else
	                if(flag2)
	                {
	                    if(op1 instanceof Long)
	                        d = ((Long)op1).doubleValue();
	                    else
	                        d = ((Integer)op1).doubleValue();
	                } else
	                {
	                    d = Double.parseDouble((String)op1);
	                }
	                if(flag5)
	                    d *= ((Double)op2).doubleValue();
	                else
	                if(flag3)
	                {
	                    if(op2 instanceof Long)
	                        d *= ((Long)op2).doubleValue();
	                    else
	                        d *= ((Integer)op2).doubleValue();
	                } else
	                {
	                    d *= Double.parseDouble((String)op2);
	                }
	            }catch(NumberFormatException e) {
	              throw new Exception("NumberFormatException �� " + e.getMessage());
	           }
	            result = new Double(d);
	        } else
	        {
	        	throw new Exception("�������ʹ������");
	        }
	        return result;
	    }

	    public String toString()
	    {
	        return "*";
	    }
	}

 public static final class Divide
	{

	   public static Object execute(Object op1,Object op2) throws Exception{
	        boolean flag1;
	        boolean flag2;
	        boolean flag3;
	        boolean flag4;
	        boolean flag5;
	        boolean flag = flag1 = flag2 = flag3 = flag4 = flag5 = false;

	        if(op1 instanceof String)
	            flag = true;
	        else
	        if(op1 instanceof Long)
	            flag2 = true;
	        else
	        if(op1 instanceof Integer)
	            flag2 = true;
	        else
	        if(op1 instanceof Double)
	            flag4 = true;
	        if(op2 instanceof String)
	            flag1 = true;
	        else
	        if(op2 instanceof Long)
	            flag3 = true;
	        else
	        if(op2 instanceof Integer)
	            flag3 = true;
	        else
	        if(op2 instanceof Double)
	            flag5 = true;
	        Object obj;
	        if(flag2 && (flag3 || flag1) || flag3 && flag)
	        {
	            long l = 0L;
	            try
	            {
	                if(flag2)
	                {
	                    if(op1 instanceof Long)
	                        l = ((Long)op1).longValue();
	                    else
	                        l = ((Integer)op1).longValue();
	                } else
	                {
	                    l = Long.parseLong((String)op1);
	                }
	                if(flag3)
	                {
	                    if(op2 instanceof Long)
	                        l /= ((Long)op2).longValue();
	                    else
	                        l /= ((Integer)op2).longValue();
	                } else
	                {
	                    l /= Long.parseLong((String)op2);
	                }
	            }
	            catch(NumberFormatException e) {
	                throw new Exception("NumberFormatException �� " + e.getMessage());
	            }

	            obj = new Long(l);
	        } else
	        if(flag4 && (flag3 || flag5 || flag1) || flag5 && (flag2 || flag))
	        {
	            double d = 0.0D;
	            try
	            {
	                if(flag4)
	                    d = ((Double)op1).doubleValue();
	                else
	                if(flag2)
	                {
	                    if(op1 instanceof Long)
	                        d = ((Long)op1).doubleValue();
	                    else
	                        d = ((Integer)op1).doubleValue();
	                } else
	                {
	                    d = Double.parseDouble((String)op1);
	                }
	                if(flag5)
	                    d /= ((Double)op2).doubleValue();
	                else
	                if(flag3)
	                {
	                    if(op2 instanceof Long)
	                        d /= ((Long)op2).doubleValue();
	                    else
	                        d /= ((Integer)op2).doubleValue();
	                } else
	                {
	                    d /= Double.parseDouble((String)op2);
	                }
	            }
	            catch(NumberFormatException e) {
	               throw new Exception("NumberFormatException �� " + e.getMessage());
	           }

	            obj = new Double(d);
	        } else
	        {
	            throw new Exception("�������ʹ������");
	        }
	        return obj;
	    }
	}

 public static final class Modulo
 {
    public static Object execute(Object op1,Object op2) throws Exception{
         Object result = null;
         boolean flag1;
         boolean flag2;
         boolean flag3;
         boolean flag4;
         boolean flag5;
         boolean flag = flag1 = flag2 = flag3 = flag4 = flag5 = false;

         if(op1 instanceof String)
             flag = true;
         else
         if(op1 instanceof Long)
             flag2 = true;
         else
         if(op1 instanceof Integer)
             flag2 = true;
         else
         if(op1 instanceof Double)
             flag4 = true;
         if(op2 instanceof String)
             flag1 = true;
         else
         if(op2 instanceof Long)
             flag3 = true;
         else
         if(op2 instanceof Integer)
             flag3 = true;
         else
         if(op2 instanceof Double)
             flag5 = true;
         if(flag2 && (flag3 || flag1) || flag3 && flag)
         {
             long l = 0L;
             try
             {
                 if(flag2)
                 {
                     if(op1 instanceof Long)
                         l = ((Long)op1).longValue();
                     else
                         l = ((Integer)op1).longValue();
                 } else
                 {
                     l = Long.parseLong((String)op1);
                 }
                 if(flag3)
                 {
                     if(op2 instanceof Long)
                         l %= ((Long)op2).longValue();
                     else
                         l %= ((Integer)op2).longValue();
                 } else
                 {
                     l %= Long.parseLong((String)op2);
                 }
             }catch(NumberFormatException e) {
               throw new Exception("NumberFormatException �� " + e.getMessage());
            }
             result = new Long(l);
         } else
         if(flag4 && (flag3 || flag5 || flag1) || flag5 && (flag2 || flag))
         {
             double d = 0.0D;
             try
             {
                 if(flag4)
                     d = ((Double)op1).doubleValue();
                 else
                 if(flag2)
                 {
                     if(op1 instanceof Long)
                         d = ((Long)op1).doubleValue();
                     else
                         d = ((Integer)op1).doubleValue();
                 } else
                 {
                     d = Double.parseDouble((String)op1);
                 }
                 if(flag5)
                     d %= ((Double)op2).doubleValue();
                 else
                 if(flag3)
                 {
                     if(op2 instanceof Long)
                         d %= ((Long)op2).doubleValue();
                     else
                         d %= ((Integer)op2).doubleValue();
                 } else
                 {
                     d %= Double.parseDouble((String)op2);
                 }
             } catch(NumberFormatException e) {
             throw new Exception("NumberFormatException �� " + e.getMessage());
            }
             result = new Double(d);
         } else
         {
         	throw new Exception("�������ʹ������");
         }
         return result;
     }
 }

 public static final class Arith {

   private static final int DEF_DIV_SCALE = 10;

  
   private Arith() {
   }

   /**
    * �ṩ���_�ļӷ��\�㡣
    * @param v1 ���Ӕ�
    * @param v2 �Ӕ�
    * @return �ɂ������ĺ�
    */
   public static double add(double v1, double v2) {
     BigDecimal b1 = new BigDecimal(Double.toString(v1));
     BigDecimal b2 = new BigDecimal(Double.toString(v2));
     return b1.add(b2).doubleValue();
   }

   /**
    * �ṩ���_�Ĝp���\�㡣
    * @param v1 ���p��
    * @param v2 �p��
    * @return �ɂ������Ĳ�
    */
   public static double sub(double v1, double v2) {
     BigDecimal b1 = new BigDecimal(Double.toString(v1));
     BigDecimal b2 = new BigDecimal(Double.toString(v2));
     return b1.subtract(b2).doubleValue();
   }

   /**
    * �ṩ���_�ĳ˷��\�㡣
    * @param v1 ���˔�
    * @param v2 �˔�
    * @return �ɂ������ķe
    */
   public static double mul(double v1, double v2) {
     BigDecimal b1 = new BigDecimal(Double.toString(v1));
     BigDecimal b2 = new BigDecimal(Double.toString(v2));
     return b1.multiply(b2).doubleValue();
   }

   /**
    * �ṩ�����������_�ĳ����\�㣬���l�������M����r�r�����_��
    * С���c����10λԪ������Ĕ����Ē����롣
    * @param v1 ������
    * @param v2 ����
    * @return �ɂ���������
    */
   public static double div(double v1, double v2) {
     return div(v1, v2, DEF_DIV_SCALE);
   }

   /**
    * �ṩ�����������_�ĳ����\�㡣���l�������M����r�r����scale����ָ
    * �����ȣ�����Ĕ����Ē����롣
    * @param v1 ������
    * @param v2 ����
    * @param scale ��ʾ��ʾ��Ҫ���_��С���c�����λ��
    * @return �ɂ���������
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
    * �ṩ���_��С��λ�Ē�����̎��
    * @param v ��Ҫ�Ē�����Ĕ�λ
    * @param scale С���c�ᱣ���λ
    * @return �Ē�������ĽY��
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