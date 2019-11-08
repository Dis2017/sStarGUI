package top.gytf.sStar.Style;

import top.gytf.sStar.Core.Attribute;
import top.gytf.sStar.Core.Style;
import top.gytf.sStar.Other.Align;
import top.gytf.sStar.Other.ScaleMode;
import top.gytf.sStar.View.*;
import top.gytf.sStar.View.TextField;

import java.awt.*;

import static top.gytf.sStar.Layout.FloatLayout.*;

/**
 * @Author Dis
 * @create 2019/11/6 21:22
 * @project sStarGUI
 * @describe 默认样式
 */
public class DefaultStyle extends Style {
	
	private static final String tag = "Default";
	
	public DefaultStyle() {
		
		//TextView
		Attribute attribute = get(TextView.class);
//		try {
//			attribute.set(TextView.NAME_FONT, Font.createFont(Font.PLAIN, new File("F:\\Projects\\sStarGUI\\src\\main\\resources\\hy.ttf")));
//		} catch (FontFormatException | IOException e) {
//			e.printStackTrace();
//		}
		attribute.set(TextView.NAME_FONT_SIZE, 18f);
		attribute.set(TextView.NAME_STRING, "");
		attribute.set(TextView.NAME_HORIZONTAL_ALIGN, Align.Left);
		attribute.set(NAME_VERTICAL_ALIGN, Align.Top);
		
		//ButtonView
		attribute = get(ButtonView.class);
		
		//ImageView
		attribute = get(ImageView.class);
		attribute.set(ImageView.NAME_IMAGE, null);
		attribute.set(ImageView.NAME_SCALE_MODE, ScaleMode.Adapt);
		attribute.set(ImageView.NAME_VERTICAL_ALIGN, Align.Center);
		attribute.set(ImageView.NAME_HORIZONTAL_ALIGN, Align.Center);
		
		//TextField
		attribute = get(TextField.class);
		attribute.set(Style.BACKGROUND_COLOR, new Color(0, 0, 0, 0));
		
		//RootView
		attribute = get(RootView.class);
		attribute.set(Style.BACKGROUND_COLOR, Color.BLUE);
		
		//TitleBar
		attribute = get(TitleBar.class);
		attribute.set(NAME_HORIZONTAL_ALIGN, Align.Left);
		attribute.set(NAME_VERTICAL_ALIGN, Align.Center);
		
	}
	
}
