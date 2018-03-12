package com.cn.iface;

import java.util.HashMap;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		MyMap<String,String> mymap = new MyHashMap<String,String>();
		
		Long t1 = System.currentTimeMillis();
		for (int i = 0; i <1000; i++) {
			mymap.put("key"+i, "value"+i);
		}
		
		for (int i = 0; i <1000; i++) {
			System.out.println("key: " + "key" + i +"  value:"+mymap.get("key"+i));
		}
		Long t2 = System.currentTimeMillis();
		System.out.println(t2-t1);
		System.out.println("----------------------------------HashMap--------------------------------------------------------");
		
		Map<String,String> map = new HashMap<String,String>();
		
		Long t3 = System.currentTimeMillis();
		for (int i = 0; i <1000; i++) {
			map.put("key"+i, "value"+i);
		}
		
		for (int i = 0; i <1000; i++) {
			System.out.println("key: " + "key" + i +"  value:"+map.get("key"+i));
		}
		Long t4 = System.currentTimeMillis();
		System.out.println(t4-t3);
	}
}
