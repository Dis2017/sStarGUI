package top.gytf.sStar.Core;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Dis
 * @create 2019/11/1 16:26
 * @project sStarGUI
 * @describe 存储属性
 */
public class Attribute {
	
	private static final String tag = "Attribute";
	
	private Map<String, Object> mData;
	
	public Attribute() {
		mData = new HashMap<>();
	}
	
	public Attribute(Attribute other) {
		mData = new HashMap<>();
		mData.putAll(other.mData);
	}
	
	public void set(String name, Object value) {
		mData.put(name, value);
	}
	public <T> T get(String name, T defValue, boolean isSet) {
		synchronized (tag) {
			T obj = (T) mData.get(name);
			if (obj == null) {
				obj = defValue;
				if (isSet) {
					mData.put(name, defValue);
				}
			}
			return obj;
		}
	}
	
}
