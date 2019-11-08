package top.gytf.sStar.Core;

import top.gytf.sStar.Input.KeyEvent;
import top.gytf.sStar.Input.MouseEvent;
import top.gytf.sStar.Other.MeasureMode;
import top.gytf.sStar.View.ButtonView;

import java.awt.*;

/**
 * @Author Dis
 * @create 2019/10/26 9:26
 * @project sStarGUI
 * @describe 管理一组视图
 */
public class ViewGroup extends View {
	
	private static final String tag = "ViewGroup";
	
	private ViewList mViewList;
	private int mOldMouseX, mOldMouseY;
	
	public ViewGroup(String id, Screen ownerScreen) {
		super(id, ownerScreen);
		this.mViewList = new ViewList(this);
		this.mOldMouseX = 0;
		this.mOldMouseY = 0;
		setWidthMeasureMode(MeasureMode.wrap_content);
		setHeightMeasureMode(MeasureMode.wrap_content);
//		setBackgroundColor(new Color(0, 0, 0, 0));
//		setMarginBounds(new Bounds(5, 5, 5, 5));
	}
	
	@Override
	public final void handleMouseInput(MouseEvent event) {
		super.handleMouseInput(event);
		//发送Enter及Exit
		View oldView = mViewList.getViewByPosition(mOldMouseX, mOldMouseY), nowView = mViewList.getViewByPosition(event.getX(), event.getY());
		if (oldView == null && nowView != null) {
			nowView.handleMouseInput(new MouseEvent(MouseEvent.WHAT_ENTER, 0, 0, 0, 0));
		} else if (oldView != null && nowView == null) {
			oldView.handleMouseInput(new MouseEvent(MouseEvent.WHAT_EXIT, 0, 0, 0, 0));
		} else if (oldView != null && oldView != nowView) {
			nowView.handleMouseInput(new MouseEvent(MouseEvent.WHAT_ENTER, 0, 0, 0, 0));
			oldView.handleMouseInput(new MouseEvent(MouseEvent.WHAT_EXIT, 0, 0, 0, 0));
		}
		//记录
		if (event.getWhat() == MouseEvent.WHAT_MOVE) {
			mOldMouseX = event.getX();
			mOldMouseY = event.getY();
		}
		//处理收到的
		if (!onMouseEvent(event)) {
			synchronized (getSynchronizedTag()) {
				for (int i = mViewList.size() - 1; i >= 0; i--) {
					View view = mViewList.get(i);
					if (!view.isVisible()) {
						continue;
					}
					if (view.getBounds().inBounds(event.getX(), event.getY())) {
						event.setX(event.getX() - view.getX());
						event.setY(event.getY() - view.getY());
						view.handleMouseInput(event);
						break;
					}
				}
			}
		}
	}
	
	@Override
	public final void handleKeyInput(KeyEvent event) {
		super.handleKeyInput(event);
		onKeyEvent(event);
		synchronized (getSynchronizedTag()) {
			for (View view : mViewList) {
				view.handleKeyInput(event);
			}
		}
	}
	
	protected void onKeyEvent(KeyEvent keyEvent) {
		if (getKeyListener() != null) {
			getKeyListener().onKeyEvent(keyEvent);
		}
	}
	
	protected boolean onMouseEvent(MouseEvent event) {
		if (getMouseListener() != null) {
			getMouseListener().onMouseEvent(event);
		}
		return false;
	}
	
	@Override
	public final void onMeasure(int maxWidth, MeasureMode fatherWidthMeasureMode, int maxHeight, MeasureMode fatherHeightMeasureMode, Graphics graphics) {
//		super.onMeasure(maxWidth, maxHeight, graphics);
		synchronized (getSynchronizedTag()) {
			int width = 0, height = 0;
			onMeasureChild(maxWidth, fatherWidthMeasureMode, maxHeight, fatherHeightMeasureMode, graphics);
			onLayoutChild();
		}
	}
	
	protected void onMeasureChild(int maxWidth, MeasureMode fatherWidthMeasureMode, int maxHeight, MeasureMode fatherHeightMeasureMode, Graphics graphics) {
		for (View view : mViewList) {
			if (!view.isVisible()) {
				continue;
			}
			view.measure(maxWidth - getPaddingBounds().x - getPaddingBounds().width - view.getMarginBounds().x - view.getMarginBounds().width, getWidthMeasureMode(), maxHeight - getPaddingBounds().y - getPaddingBounds().height - view.getMarginBounds().y - view.getMarginBounds().height, getHeightMeasureMode(), graphics);
		}
	}
	
	protected void onLayoutChild() {
		for (View view : mViewList) {
			if (!view.isVisible()) {
				continue;
			}
			view.setPosition(view.getMarginBounds().x, view.getMarginBounds().y);
		}
	}
	
	@Override
	protected void onDraw(Graphics graphics) {
		super.onDraw(graphics);
		synchronized (getSynchronizedTag()) {
			for (View view : mViewList)  {
				if (!view.isVisible()) {
					continue;
				}
				view.draw(graphics.create(view.getX(), view.getY(), view.getWidth(), view.getHeight()));
			}
		}
	}
	
	public void addChild(View view) {
		mViewList.add(view);
	}
	
	public void removeChild(View view) {
		mViewList.remove(view);
	}
	
	public void clearChild() {
	    mViewList.clear();
	}
	
	protected ViewList getViewList() {
		return mViewList;
	}
	
	protected String getSynchronizedTag() {
		return ViewList.tag;
	}
	
	@Override
	public void setMeasured(boolean measured) {
		super.setMeasured(measured);
		synchronized (getSynchronizedTag()) {
			for (View view : getViewList()) {
				view.setMeasured(measured);
			}
		}
	}
	
}
