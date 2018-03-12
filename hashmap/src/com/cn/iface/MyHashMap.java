package com.cn.iface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyHashMap<K,V> implements MyMap<K, V>{
	
	//hashMap的默认长度是16
	private static int defaultLength = 16;
	
	//默认负载因子是0.75
	private static double defaultLoader = 0.75;
	
	private Entry<K,V>[] table = null;
	
	private int size = 0;
	
	//自己指定hashmap的容器
	public  MyHashMap(int length,double loader){
		defaultLength = length;
		defaultLoader = loader;
		table = new Entry[defaultLength];
	}
	
	//取hashmao默认的大小
	public  MyHashMap(){
		this(defaultLength,defaultLoader);
	}

	@Override
	public V put(K k, V v) {
		
		//判断size是否达到了一个扩容的标准
		if(size >= defaultLength * defaultLoader){
			upSize();  //扩容
		}
		// 1.创建一个hash函数,根据key和hash函数算出数组下标
		int index = getIndex(k);
		
		Entry<K, V> entry = table[index];
		
		if(entry == null){
			//如果entry为null，说明table的index位置上没有元素
			table[index] = newEntry(k, v, null);
			size++;
		}else{
			//如果entry不为null,说明table的index位置上有元素，那么就要进行一个替换,然后next指针指向老数据
			table[index] = newEntry(k, v, entry);
		}
		return table[index].getValue();
	}
	
	//扩容,默认扩大一倍
	private void upSize(){
		Entry<K,V>[] newTable = new Entry[2 * defaultLength];
		//新创建数组以后,以前老数组里面的元素要对新数组进行再散列
		againHash(newTable);
	}
	
	private void againHash(Entry<K,V>[] newTable){
		
		List<Entry<K,V>> list = new ArrayList<Entry<K,V>>();
		
		for (int i = 0; i < table.length; i++) {
			//先判断是否有元素 
			if(table[i] == null){
				continue;
			}
			findEntryByNext(table[i],list);
		}
		
		if(list.size() > 0){
			//要进行一个新数组的再散列
			size = 0;
			defaultLength = defaultLength * 2;
			table = newTable;
			
			for (Entry<K, V> entry : list) {
				if(entry.next != null){
					entry.next = null;
				}
				put(entry.getKey(), entry.getValue());
			}
		}
		
	}
	
	private void findEntryByNext(Entry<K,V> entry,List<Entry<K,V>> list){
		if(entry != null && entry.next != null){
			list.add(entry);
			//递归,取链表中所有的数据
			findEntryByNext(entry.next, list);
		}else{
			list.add(entry);
		}
	}
	
	private Entry<K,V> newEntry(K k,V v,Entry<K,V> next){
		return new Entry(k,v,next);
	}
	
	private int getIndex(K k){
		int m = defaultLength;
		int index = k.hashCode() % m;
		return index >= 0 ? index : -index;
	}

	@Override
	public V get(K k) {
		
		// 1.创建一个hash函数,根据key和hash函数算出数组下标
		int index = getIndex(k);
		
		if(table[index] == null){
			return null;
		}
		
		return findValueByEqualKey(k,table[index]);
		
	}
	
	public V findValueByEqualKey(K k,Entry<K,V> entry){
		
		if(k == entry.getKey() || k.equals(entry.getKey())){
			return entry.getValue();
		}else{
			if(entry.next != null){
				return findValueByEqualKey(k,entry.next);
			}
		}
		
		return null;
	}

	@Override
	public int size() {
		return size;
	}

	class Entry<K,V> implements MyMap.Entry<K, V>{
		
		K k;
		V v;
		Entry<K,V> next;
		
		public Entry(K k,V v,Entry<K,V> next){
			this.k = k;
			this.v = v;
			this.next = next;
		}

		@Override
		public K getKey() {
			return k;
		}

		@Override
		public V getValue() {
			return v;
		}
		
	}
	
}
