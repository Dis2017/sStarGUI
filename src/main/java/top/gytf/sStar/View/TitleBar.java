package top.gytf.sStar.View;

import top.gytf.sStar.Core.Screen;
import top.gytf.sStar.Decorator.DividingLine;
import top.gytf.sStar.Input.MouseEvent;
import top.gytf.sStar.Layout.FloatLayout;
import top.gytf.sStar.Other.Align;
import top.gytf.sStar.Other.Bounds;
import top.gytf.sStar.Other.Gravity;
import top.gytf.sStar.Other.MeasureMode;

import java.awt.*;

/**
 * @Author Dis
 * @create 2019/10/26 11:34
 * @project sStarGUI
 * @describe
 */
public class TitleBar extends FloatLayout {

	private static final String tag = "TitleBar";
	
	public static final String TITLE_VIEW_ID = "TITLE_VIEW_ID";
	
	private TextView mTitleTv;
	private int mDownX, mDownY;
	private boolean mLeftDown;
	
	public TitleBar(String id, Screen ownerScreen) {
		super(id, ownerScreen);
		
		mDownX = 0;
		mDownY = 0;
		mLeftDown = false;
		
		setPaddingBounds(new Bounds(10, 10, 10, 10));
//		setMarginBounds(new Bounds(5, 5, 5, 5));
		setWidthMeasureMode(MeasureMode.fill_parent);
		mTitleTv = new TextView(TITLE_VIEW_ID, ownerScreen);
		mTitleTv.setMarginBounds(new Bounds(10, 0, 10, 0));
		addChild(mTitleTv);
		addBottomDecorator(new DividingLine(Align.Bottom, 1, getKeyColor()));
	}
	
	//实现鼠标左键拖动窗口
	@Override
	protected boolean onMouseEvent(MouseEvent event) {
		super.onMouseEvent(event);
		
		if (event.getWhat() == MouseEvent.WHAT_DOWN && event.getButton() == MouseEvent.BUTTON_LEFT) {
			mDownX = event.getX();
			mDownY = event.getY();
			mLeftDown = true;
			return false;
		}
		if (event.getWhat() == MouseEvent.WHAT_UP && event.getButton() == MouseEvent.BUTTON_LEFT) {
			mLeftDown = false;
			return false;
		}
		
		if (event.getWhat() == MouseEvent.WHAT_DRAG && mLeftDown) {// && event.getButton() == MouseEvent.BUTTON_LEFT) {
			Screen owner = getOwnerScreen();
			owner.setX(owner.getX() + (event.getX() - mDownX));
			owner.setY(owner.getY() + (event.getY() - mDownY));
		}
		
		
		return false;
	}
	
	public TextView getTitleTv() {
		return mTitleTv;
	}
	
	public void setTitleTv(TextView titleTv) {
		mTitleTv = titleTv;
	}
	
}
