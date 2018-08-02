package com.hh.projectxx.base.util;

import com.fasterxml.jackson.databind.JavaType;

import java.util.HashMap;
import java.util.List;


public class JSON {

	private static JsonMapper mapper = JsonMapper.nonNullMapper();
	
	public static String toJson(Object object) {
		return mapper.toJson(object);
	}

	public static <T> T parseObject(String jsonString, Class<T> clazz) {
		return mapper.fromJson(jsonString, clazz);
	}
	
	public static <T> List<T> parseArray(String jsonString, Class<T> clazz) {
		JavaType jt = mapper.createCollectionType(List.class, clazz);
		return mapper.fromJson(jsonString, jt);
	}
	
	public static <K, V> HashMap<K, V> parseMap(String jsonString, Class<K> kClazz, Class<V> vClazz) {
		JavaType jt = mapper.createCollectionType(HashMap.class, kClazz, vClazz);
		return mapper.fromJson(jsonString, jt);
	}
}
