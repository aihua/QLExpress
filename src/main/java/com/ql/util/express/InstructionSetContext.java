package com.ql.util.express;

import java.util.HashMap;

@SuppressWarnings("serial")
class InstructionSetContext<K,V> extends HashMap<K,V> implements IExpressContext<K,V> {
	IExpressContext<K,V> parent = null;
	public InstructionSetContext(IExpressContext<K,V> aParent){
		parent = aParent;
	}
	public IExpressContext<K,V> getParent(){
		return  this.parent;
	}
	public V get(Object key){
		if(super.containsKey(key)){
			return super.get(key);
		}else if(this.parent != null){
			return this.parent.get(key);
		}
		return null;
	}

	public void putKeyDefine(K key){
		super.put(key,null);
	}
	public V put(K key, V value){
		if(super.containsKey(key) ){
			return super.put(key,value);
		}else if(this.parent != null){
			return this.parent.put(key,value);
		}else{
			throw new RuntimeException("û�ж���ֲ�������" + key +",����û��ȫ��������");
		}
	}
}
