package top.gytf.sStar.View;

import top.gytf.sStar.Core.Screen;
import top.gytf.sStar.Core.View;
import top.gytf.sStar.Decorator.Border;
import top.gytf.sStar.Layout.LinearLayout;
import top.gytf.sStar.Other.Gravity;
import top.gytf.sStar.Other.MeasureMode;

import java.awt.*;

/**
 * @Author Dis
 * @create 2019/11/2 12:03
 * @project sStarGUI
 * @describe Screen的根视图
 */
public class RootView extends LinearLayout {
	
	private static final String tag = "RootView";
	
	private static final String TITLE_BAR_ID = "TITLE_BAR_ID";
	private static final String CONTENT_VIEW_ID = "CONTENT_VIEW_ID";
	
	private TitleBar mTitleBar;
	private View mContentView;
	private View mFocusView;
	
	public RootView(String id, Screen ownerScreen) {
		super(id, ownerScreen);
		
		//充满父空间
		setWidthMeasureMode(MeasureMode.fill_parent);
		setHeightMeasureMode(MeasureMode.fill_parent);
		//垂直排列
		setGravity(Gravity.Vertical);
		
		mTitleBar = new TitleBar(TitleBar.TITLE_VIEW_ID, ownerScreen);
		mContentView = null;
		
		addChild(mTitleBar);
		
//		addTopDecorator(new Border());
	}
	
	public TitleBar getTitleBar() {
		return mTitleBar;
	}
	
	public View getContentView() {
		return mContentView;
	}
	
	public void setContentView(View contentView) {
		View old = getViewList().findViewByID(CONTENT_VIEW_ID, View.class);
		if (old != null) {
			getViewList().remove(old);
		}
		if (contentView == null) {
			return;
		}
		getViewList().add(contentView);
		mContentView = contentView;
	}
	
	public void setTitleBar(TitleBar titleBar) {
		getViewList().remove(mTitleBar);
		mTitleBar = titleBar;
		getViewList().add(0, titleBar);
	}
	
	public View getFocusView() {
		return mFocusView;
	}
	
	public void setFocusView(View focusView) {
		if (!focusView.isFocusable() || focusView == mFocusView) {
			return;
		}
		if (mFocusView != null) {
			mFocusView.setFocus(false);
		}
		mFocusView = focusView;
		mFocusView.setFocus(true);
	}
	
}
