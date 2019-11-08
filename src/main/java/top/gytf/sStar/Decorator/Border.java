package top.gytf.sStar.Decorator;

import top.gytf.sStar.Core.Decorator;
import top.gytf.sStar.Core.Screen;
import top.gytf.sStar.Core.View;
import top.gytf.sStar.Input.KeyEvent;
import top.gytf.sStar.Input.MouseEvent;
import top.gytf.sStar.Other.Bounds;

import java.awt.*;

/**
 * @Author Dis
 * @create 2019/11/2 12:57
 * @project sStarGUI
 * @describe 边框装饰器
 */
public class Border implements Decorator {
	
	private static final String tag = "Border";
	
	private View mView;
	private float mSize;
	private Color mColor;
	private int mDownX, mDownY;
	private int mDownScreenWidth, mDownScreenHeight;
	private boolean mLeftDown;
	private boolean mResizeable;
	
	public Border() {
		this(20.0f, Color.WHITE);
	}
	
	public Border(float size) {
		this(size, Color.WHITE);
	}
	
	public Border(Color color) {
		this(20.0f, color);
	}
	
	public Border(float size, Color color) {
		mSize = size;
		mColor = color;
		mDownX = 0;
		mDownY = 0;
		mLeftDown = false;
		mResizeable = true;
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
		graphics.setColor(mColor);
		graphics.setStroke(new BasicStroke(mSize / 2));
		graphics.drawRect(0, 0, mView.getWidth(), mView.getHeight());
	}
	
	@Override
	public boolean handleMouseEvent(MouseEvent event) {
		if (!mResizeable) {
			return false;
		}
		//记录鼠标左键按下时的位置及窗口宽高
		if ((event.getY() >= mView.getHeight() - mSize && event.getY() <= mView.getHeight()) || (event.getX() >= mView.getWidth() - mSize && event.getX() <= mView.getWidth())) {
			if (event.getWhat() == MouseEvent.WHAT_DOWN && event.getButton() == MouseEvent.BUTTON_LEFT) {
				Screen owner = mView.getOwnerScreen();
				mDownX = event.getX();
				mDownY = event.getY();
				mDownScreenWidth = owner.getWidth();
				mDownScreenHeight = owner.getHeight();
				mLeftDown = true;
				return false;
			}
		} else if (event.getWhat() == MouseEvent.WHAT_UP) {
			mLeftDown = false;
			return false;
		}
		if (mLeftDown && event.getWhat() == MouseEvent.WHAT_DRAG) {
			Screen owner = mView.getOwnerScreen();
			if (event.getX() >= mView.getWidth() - mSize && event.getX() <= mView.getWidth()) {
				owner.setWidth(mDownScreenWidth + (event.getX() - mDownX));
			} else if (event.getY() >= mView.getHeight() - mSize && event.getY() <= mView.getHeight()) {
				owner.setHeight(mDownScreenHeight + (event.getY() - mDownY));
			}
			return true;
		}
		return false;
	}
	
	@Override
	public void handleKeyEvent(KeyEvent event) {
	}
	
	@Override
	public void onUnload() {
	
	}
	
	public boolean isResizeable() {
		return mResizeable;
	}
	
	public void setResizeable(boolean resizeable) {
		mResizeable = resizeable;
	}
}
