package top.gytf.sStar.View;

import sun.awt.SunHints;
import top.gytf.sStar.Core.Screen;
import top.gytf.sStar.Core.View;
import top.gytf.sStar.Other.Align;
import top.gytf.sStar.Other.Bounds;
import top.gytf.sStar.Other.MeasureMode;

import java.awt.*;

/**
 * @Author Dis
 * @create 2019/10/26 10:07
 * @project sStarGUI
 * @describe 显示文本
 */
public class TextView extends View {
	
	private static final String tag = "TextView";
	
	public static final String NAME_FONT = tag + "NAME_FONT";
	public static final String NAME_FONT_SIZE = tag + "NAME_FONT_SIZE";
	public static final String NAME_STRING = tag + "NAME_STRING";
	public static final String NAME_VERTICAL_ALIGN = tag + "NAME_VERTICAL_ALIGN";
	public static final String NAME_HORIZONTAL_ALIGN = tag + "NAME_HORIZONTAL_ALIGN";
	
	public TextView(String id, Screen ownerScreen) {
		super(id, ownerScreen);
//		mString = "";
//		mFontSize = 18f;
//		mHorizontalAlign = Align.Center;
//		mVerticalAlign = Align.Center;
		setWidthMeasureMode(MeasureMode.wrap_content);
		setHeightMeasureMode(MeasureMode.wrap_content);
		setBackgroundColor(new Color(0, 0, 0, 0));
	}
	
	@Override
	protected void onDraw(Graphics graphics) {
		super.onDraw(graphics);
		Font font = getFont();
		float fontSize = getFontSize();
		Align horizontalAlign = getHorizontalAlign();
		Align verticalAlign = getVerticalAlign();
		String string = getString();
		//设置字体
		if (font != null) {
			graphics.setFont(font.deriveFont(fontSize));
		} else {
			graphics.setFont(graphics.getFont().deriveFont(fontSize));
		}
		//应用重力
		FontMetrics fontMetrics = graphics.getFontMetrics();
		int x = 0, y = 0;
		switch (horizontalAlign) {
			case Center: {
				x = (getContentWidth() - fontMetrics.stringWidth(string)) / 2;
			} break;
			case Right: {
				x = getContentWidth() - fontMetrics.stringWidth(string);
			} break;
		}
		switch (verticalAlign) {
			case Top: {
				y = fontMetrics.getAscent();
			} break;
			case Center: {
				y = (getContentHeight() + fontMetrics.getAscent()) / 2;
			} break;
			case Bottom: {
				y = getContentHeight() - fontMetrics.getHeight();
			} break;
		}
		//绘制
		graphics.drawString(string, x, y);
	}
	
	@Override
	public void onMeasure(int maxWidth, MeasureMode fatherWidthMeasureMode, int maxHeight, MeasureMode fatherHeightMeasureMode, Graphics graphics) {
//		super.onMeasure(maxWidth, maxHeight, graphics);
		Font font = getFont();
		float fontSize = getFontSize();
		String string = getString();
		if (font != null) {
			graphics.setFont(font.deriveFont(fontSize));
		} else {
			graphics.setFont(graphics.getFont().deriveFont(fontSize));
		}
		FontMetrics fontMetrics = graphics.getFontMetrics();
		setMeasureSize(fontMetrics.stringWidth(string) + getPaddingBounds().x + getPaddingBounds().width, maxWidth, fontMetrics.getHeight() + getPaddingBounds().y + getPaddingBounds().height, maxHeight, fatherWidthMeasureMode, fatherHeightMeasureMode);
	}
	
	public String getString() {
		return getAttribute().get(NAME_STRING, "", true);
	}
	
	public void setString(String string) {
		getAttribute().set(NAME_STRING, string);
	}
	
	public float getFontSize() {
		return getAttribute().get(NAME_FONT_SIZE, 18f, true);
	}
	
	public void setFontSize(float fontSize) {
		getAttribute().set(NAME_FONT_SIZE, fontSize);
	}
	
	public Font getFont() {
		return getAttribute().get(NAME_FONT, null, true);
	}
	
	public void setFont(Font font) {
		getAttribute().set(NAME_FONT, font);
	}
	
	public Align getHorizontalAlign() {
		return getAttribute().get(NAME_HORIZONTAL_ALIGN, Align.Left, true);
	}
	
	public void setHorizontalAlign(Align horizontalAlign) {
		getAttribute().set(NAME_HORIZONTAL_ALIGN, horizontalAlign);
	}
	
	public Align getVerticalAlign() {
		return getAttribute().get(NAME_VERTICAL_ALIGN, Align.Top, true);
	}
	
	public void setVerticalAlign(Align verticalAlign) {
		getAttribute().set(NAME_VERTICAL_ALIGN, verticalAlign);
	}
	
}
