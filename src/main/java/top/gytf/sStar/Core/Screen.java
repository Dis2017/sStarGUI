package top.gytf.sStar.Core;

import top.gytf.sStar.Input.KeyEvent;
import top.gytf.sStar.Input.MouseEvent;
import top.gytf.sStar.Layout.LinearLayout;
import top.gytf.sStar.Other.Bounds;
import top.gytf.sStar.Other.MeasureMode;
import top.gytf.sStar.View.RootView;
import top.gytf.sStar.View.TitleBar;

import java.awt.*;

/**
 * @Author Dis
 * @create 2019/10/25 16:48
 * @project sStarGUI
 * @describe 荧幕，管理显示的单位，类似窗口
 */
public abstract class Screen {
	
	private static final String tag = "Screen";
	
	private static final String ROOT_VIEW_GROUP_ID = "ROOT_VIEW_GROUP_ID";
	
	//启动时调用
	protected abstract void onStart();
	//关闭时调用
	protected abstract void onStop();
	//焦点时调用
	protected abstract void onFocus();
	
	private sStarGui mGui;
	private Bounds mBounds;
	private RootView mRootView;
	
	public Screen(sStarGui gui) {
		this.mGui = gui;
		this.mBounds = new Bounds(0, 0, 0, 0);
		this.mRootView = new RootView(ROOT_VIEW_GROUP_ID, this);
	}
	
	//绘制时地调用
	void draw(Graphics graphics) {
		mRootView.measure(getWidth(), MeasureMode.fill_parent, getHeight(), MeasureMode.fill_parent, graphics);
		mRootView.draw(graphics);
	}
	
	//设置内容视图
	protected void setContentView(View view) {
		mRootView.setContentView(view);
	}
	
	//处理鼠标输入
	public void handleMouseInput(MouseEvent event) {
		mRootView.handleMouseInput(event);
	}
	//处理键盘输入
	public void handleKeyInput(KeyEvent event) {
		mRootView.handleKeyInput(event);
	}
	
	//设置大小
	public void setWidth(int width) {
		mBounds.width = width;
		mRootView.setMeasured(false);
	}
	public void setHeight(int height) {
		mBounds.height = height;
		mRootView.setMeasured(false);
	}
	protected void setSize(int width, int height) {
		mBounds.width = width;
		mBounds.height = height;
		mRootView.setMeasured(false);
	}
	//设置位置
	public void setX(int x) {
		mBounds.x = x;
	}
	public void setY(int y) {
		mBounds.y = y;
	}
	protected void setPosition(int x, int y) {
		mBounds.x = x;
		mBounds.y = y;
	}
	
	//提供大小
	public int getWidth() {
		return mBounds.width;
	}
	public int getHeight() {
		return mBounds.height;
	}
	//提供位置
	public int getX() {
		return mBounds.x;
	}
	public int getY() {
		return mBounds.y;
	}
	//提供窗口范围
	public Bounds getBounds() {
		return mBounds;
	}
	
	//提供停止的接口
	public void stop() {
		mGui.stopScreen(this);
	}
	
	//提供重绘接口
	protected void repaint() {
		mGui.repaint();
	}
	
	
	//提供所属sStarGui
	public sStarGui getGui() {
		return mGui;
	}
	
	//标题栏
	public TitleBar getTitleBar() {
		return mRootView.getTitleBar();
	}
	public void setTitleBar(TitleBar titleBar) {
		mRootView.setTitleBar(titleBar);
	}
	
	//背景
	public void setBackground(Color color) {
		mRootView.setBackgroundColor(color);
	}
	public Color getBackgroundColor() {
		return mRootView.getBackgroundColor();
	}
	public void setBackground(Image image) {
		mRootView.setBackgroundImage(image);
	}
	public Image getBackgroundImage() {
		return mRootView.getBackgroundImage();
	}
	
	//Margin、Padding
	public Bounds getMargin() {
		return mRootView.getMarginBounds();
	}
	public Bounds getPadding() {
		return mRootView.getPaddingBounds();
	}
	
	protected RootView getRootView() {
		return mRootView;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
