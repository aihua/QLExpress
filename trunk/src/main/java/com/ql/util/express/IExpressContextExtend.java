package com.ql.util.express;

/**
 * ���ʽ���������ע����չ�ӿڣ���99.9%��������ò���
 * @author qhlhl2010@gmail.com
 *
 */
@SuppressWarnings("unchecked")

public interface IExpressContextExtend extends IExpressContext {
  
  /**
   * ��ȡ���Ե�Class,��Ҫ���ڶ�ִ̬�з���ʱ���ܹ����ݲ�������׼ȷ�Ļ�ȡ������
   * ���磺һ�������� f(String name),f(Object name) ����Ҫͨ���������;�ȷ��ʱ��ʹ��
   * ȱʡ��ʵ�ַ�ʽ:
   * 	public Class getClassType(String name) throws Exception {
		Object obj = this.getAttrValue(name);
		if (obj == null)
			return null;
		return obj.getClass();
	   }
	}
   * @param name
   * @return
   * @throws Exception
   */

public Class getClassType(String name) throws Exception;
}
