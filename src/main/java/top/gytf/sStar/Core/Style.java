package top.gytf.sStar.Core;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Dis
 * @create 2019/11/6 20:39
 * @project sStarGUI
 * @describe 样式
 */
public class Style {
	
	private static final String tag = "Style";
	
	public static final String FOREGROUND_COLOR = "foregroundColor";
	public static final String BACKGROUND_COLOR = "backgroundColor";
	public static final String HOVER_COLOR = "hoverColor";
	public static final String KEY_COLOR = "keyColor";
	public static final String DISABLE_COLOR = "disableColor";
	
	private Map<Class<? extends View>, Attribute> mAttributeMap;
	
	private Attribute mBaseAttribute;
	
	public Style() {
		mAttributeMap = new HashMap<>();
		mBaseAttribute = new Attribute();
		
		mBaseAttribute.set(FOREGROUND_COLOR, new Color(255, 255, 255, 255));
		mBaseAttribute.set(BACKGROUND_COLOR, new Color(0, 200, 250, 255));
		mBaseAttribute.set(HOVER_COLOR, new Color(100, 200, 250, 255));
		mBaseAttribute.set(KEY_COLOR, new Color(0, 100, 150, 255));
		mBaseAttribute.set(DISABLE_COLOR, Color.GRAY);
	}
	
	public Attribute get(Class<? extends View> cls) {
		Attribute attribute = mAttributeMap.get(cls);
		if (attribute == null) {
			attribute = new Attribute(mBaseAttribute);
			mAttributeMap.put(cls, attribute);
		}
		return attribute;
	}
	
	public Attribute getBaseAttribute() {
		return mBaseAttribute;
	}
	
}
