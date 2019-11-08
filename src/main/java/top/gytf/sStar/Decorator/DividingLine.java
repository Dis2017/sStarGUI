package top.gytf.sStar.Decorator;

import top.gytf.sStar.Core.Decorator;
import top.gytf.sStar.Core.View;
import top.gytf.sStar.Input.KeyEvent;
import top.gytf.sStar.Input.MouseEvent;
import top.gytf.sStar.Other.Align;
import top.gytf.sStar.Other.Bounds;

import java.awt.*;

/**
 * @Author Dis
 * @create 2019/11/6 21:41
 * @project sStarGUI
 * @describe 分割线
 */
public class DividingLine implements Decorator {
	
	private static final String tag = "DividingLine";
	
	private View mView;
	private Align mAlign;
	private int mSize;
	private Color mColor;
	
	public DividingLine(Align align, int size, Color color) {
		mAlign = align;
		mSize = size;
		mColor = color;
	}
	
	@Override
	public void onLoad(View view) {
		mView = view;
	}
	
	@Override
	public Bounds getBounds() {
		return new Bounds(0, 0, mView.getWidth(), mView.getHeight());
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		int x = 0, y = 0;
		int w = (int) graphics.getClipBounds().getWidth(), h = (int) graphics.getClipBounds().getHeight();
		
		switch (mAlign) {
			
			case Left: {
				w = mSize;
			} break;
			
			case Right: {
				x = w - mSize;
				w = mSize;
			} break;
			
			case Top: {
				h = mSize;
			} break;
			
			case Bottom: {
				y = h - mSize;
				h = mSize;
			} break;
			
		}
		
		graphics.setColor(mColor);
		graphics.fillRect(x, y, w, h);
		
	}
	
	@Override
	public boolean handleMouseEvent(MouseEvent event) {
		return false;
	}
	
	@Override
	public void handleKeyEvent(KeyEvent event) {
	
	}
	
	@Override
	public void onUnload() {
	
	}
	
	public Align getAlign() {
		return mAlign;
	}
	
	public void setAlign(Align align) {
		mAlign = align;
	}
	
	public int getSize() {
		return mSize;
	}
	
	public void setSize(int size) {
		mSize = size;
	}
	
	public Color getColor() {
		return mColor;
	}
	
	public void setColor(Color color) {
		mColor = color;
	}
	
}
