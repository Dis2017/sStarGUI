package top.gytf.sStar.View;

import top.gytf.sStar.Core.Screen;
import top.gytf.sStar.Core.Style;
import top.gytf.sStar.Input.MouseEvent;
import top.gytf.sStar.Other.Bounds;
import top.gytf.sStar.Other.ClickListener;

import java.awt.*;

/**
 * @Author Dis
 * @create 2019/11/2 20:53
 * @project sStarGUI
 * @describe 按钮视图
 */
public class ButtonView extends TextView {

	private static final String tag = "ButtonView";
	
	public static final int STATE_NORMAL = 0;
	public static final int STATE_PRESS = 1;
	public static final int STATE_HOVER = 2;
	public static final int STATE_DISABLE = 3;
	
	public static final String NAME_NORMAL_BACKGROUND_COLOR = tag + "NAME_NORMAL_BACKGROUND_COLOR";
	public static final String NAME_NORMAL_FOREGROUND_COLOR = tag + "NAME_NORMAL_FOREGROUND_COLOR";
	public static final String NAME_NORMAL_BACKGROUND_IMAGE = tag + "NAME_NORMAL_BACKGROUND_IMAGE";
	
	public static final String NAME_PRESS_BACKGROUND_COLOR = tag +  "NAME_PRESS_BACKGROUND_COLOR";
	public static final String NAME_PRESS_FOREGROUND_COLOR = tag + "NAME_PRESS_FOREGROUND_COLOR";
	public static final String NAME_PRESS_BACKGROUND_IMAGE = tag + "NAME_PRESS_BACKGROUND_IMAGE";
	
	public static final String NAME_HOVER_BACKGROUND_COLOR = tag + "NAME_HOVER_BACKGROUND_COLOR";
	public static final String NAME_HOVER_FOREGROUND_COLOR = tag + "NAME_HOVER_FOREGROUND_COLOR";
	public static final String NAME_HOVER_BACKGROUND_IMAGE = tag + "NAME_HOVER_BACKGROUND_IMAGE";
	
	public static final String NAME_DISABLE_BACKGROUND_COLOR = tag + "NAME_DISABLE_BACKGROUND_COLOR";
	public static final String NAME_DISABLE_FOREGROUND_COLOR = tag + "NAME_DISABLE_FOREGROUND_COLOR";
	public static final String NAME_DISABLE_BACKGROUND_IMAGE = tag + "NAME_DISABLE_BACKGROUND_IMAGE";
	
	private ClickListener mClickListener;
	private boolean mEnable;
	private boolean mHover;
	private int mState;
	
	public ButtonView(String id, Screen ownerScreen) {
		super(id, ownerScreen);
		
		mEnable = true;
		mHover = false;
		changeState(STATE_NORMAL);
		
		getAttribute().set(Style.BACKGROUND_COLOR, getOwnerScreen().getGui().getStyle().getBaseAttribute().get(Style.BACKGROUND_COLOR, Color.BLACK, false));
		setPaddingBounds(new Bounds(80, 5, 80, 5));
	}
	
	@Override
	protected void onDrawBackground(Graphics graphics) {
//		super.onDrawBackground(graphics);
		if (getBackgroundImage() != null) {
			super.onDrawBackground(graphics);
		} else {
			int size = Math.min(getWidth(), getHeight());
			graphics.fillRoundRect(0, 0, getWidth(), getHeight(), size, size);
		}
	}
	
	@Override
	public void handleMouseInput(MouseEvent event) {

		super.handleMouseInput(event);
		
		if (!mEnable) {
			return;
		}
		
		switch (event.getWhat()) {
			case MouseEvent.WHAT_DOWN: {
				changeState(STATE_PRESS);
			} break;
			case MouseEvent.WHAT_UP: {
				changeState(mHover ? STATE_HOVER : STATE_NORMAL);
			} break;
			case MouseEvent.WHAT_ENTER: {
				mHover = true;
				changeState(STATE_HOVER);
			} break;
			case MouseEvent.WHAT_EXIT: {
				mHover = false;
				changeState(STATE_NORMAL);
			} break;
		}
		
		if (event.getWhat() == MouseEvent.WHAT_CLICK && mClickListener != null) {
			mClickListener.onClick(event);
		}
		
	}
	
	private void changeState(int state) {
		mState = state;
		Color color;
		Image image;
		switch (state) {
			case STATE_NORMAL: {
				color = getOwnerScreen().getGui().getStyle().getBaseAttribute().get(Style.BACKGROUND_COLOR, getBackgroundColor(), false);
				setBackgroundColor(getAttribute().get(NAME_NORMAL_BACKGROUND_COLOR, color, false));
				setBackgroundImage(getAttribute().get(NAME_NORMAL_BACKGROUND_IMAGE, getBackgroundImage(), false));
				setForegroundColor(getAttribute().get(NAME_NORMAL_FOREGROUND_COLOR, getForegroundColor(), false));
			} break;
			case STATE_HOVER: {
				color = getOwnerScreen().getGui().getStyle().getBaseAttribute().get(Style.HOVER_COLOR, getBackgroundColor(), false);
				setBackgroundColor(getAttribute().get(NAME_HOVER_BACKGROUND_COLOR, color, false));
				image = getAttribute().get(NAME_NORMAL_BACKGROUND_IMAGE, getBackgroundImage(), false);
				setBackgroundImage(getAttribute().get(NAME_HOVER_BACKGROUND_IMAGE, image, false));
				setForegroundColor(getAttribute().get(NAME_HOVER_FOREGROUND_COLOR, getForegroundColor(), false));
			} break;
			case STATE_PRESS: {
				color = getOwnerScreen().getGui().getStyle().getBaseAttribute().get(Style.KEY_COLOR, getBackgroundColor(), false);
				setBackgroundColor(getAttribute().get(NAME_PRESS_BACKGROUND_COLOR, color, false));
				image = getAttribute().get(NAME_NORMAL_BACKGROUND_IMAGE, getBackgroundImage(), false);
				image = getAttribute().get(NAME_HOVER_BACKGROUND_IMAGE, image, false);
				setBackgroundImage(getAttribute().get(NAME_PRESS_BACKGROUND_IMAGE, image, false));
				setForegroundColor(getAttribute().get(NAME_PRESS_FOREGROUND_COLOR, getForegroundColor(), false));
			} break;
			case STATE_DISABLE: {
				color = getOwnerScreen().getGui().getStyle().getBaseAttribute().get(Style.DISABLE_COLOR, getBackgroundColor(), false);
				setBackgroundColor(getAttribute().get(NAME_DISABLE_BACKGROUND_COLOR, color, false));
				setBackgroundImage(getAttribute().get(NAME_DISABLE_BACKGROUND_IMAGE, getBackgroundImage(), false));
				setForegroundColor(getAttribute().get(NAME_DISABLE_FOREGROUND_COLOR, getForegroundColor(), false));
			} break;
		}
	}
	
	public ClickListener getClickListener() {
		return mClickListener;
	}
	
	public void setClickListener(ClickListener listener) {
		mClickListener = listener;
	}
	
	public boolean isEnable() {
		return mEnable;
	}
	
	public void setEnable(boolean enable) {
		mEnable = enable;
		if (!mEnable) {
			mHover = false;
			changeState(STATE_DISABLE);
		} else {
			changeState(STATE_NORMAL);
		}
	}
	
	public void applyChange() {
		changeState(mState);
	}
	
}
