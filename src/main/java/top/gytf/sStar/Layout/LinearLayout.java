package top.gytf.sStar.Layout;

import top.gytf.sStar.Core.Screen;
import top.gytf.sStar.Core.View;
import top.gytf.sStar.Core.ViewGroup;
import top.gytf.sStar.Other.Align;
import top.gytf.sStar.Other.Gravity;
import top.gytf.sStar.Other.MeasureMode;

import java.awt.*;

/**
 * @Author Dis
 * @create 2019/10/26 9:58
 * @project sStarGUI
 * @describe
 */
public class LinearLayout extends ViewGroup {
	
	private static final String tag = "LinearLayout";
	
	private Gravity mGravity;
	private Align mVerticalAlign;
	private Align mHorizontalAlign;
	private int mViewsHeight, mViewsWidth;
	
	public LinearLayout(String id, Screen ownerScreen) {
		super(id, ownerScreen);
		mGravity = Gravity.Vertical;
		mVerticalAlign = Align.Top;
		mHorizontalAlign = Align.Left;
		mViewsWidth = 0;
		mViewsHeight = 0;
		setWidthMeasureMode(MeasureMode.wrap_content);
		setHeightMeasureMode(MeasureMode.wrap_content);
	}
	
	@Override
	protected void onMeasureChild(int maxWidth, MeasureMode fatherWidthMeasureMode, int maxHeight, MeasureMode fatherHeightMeasureMode, Graphics graphics) {
//		super.onMeasureChild(maxWidth, fatherWidthMeasureMode, maxHeight, fatherHeightMeasureMode, graphics);
		synchronized (getSynchronizedTag()) {
			//子控件所使用的最大宽高
			int measureWidth = 0, measureHeight = 0;
			mViewsWidth = mViewsHeight = 0;
			
			//测量子视图大小并计算自身大小
			for (View view : getViewList()) {
				if (!view.isVisible()) {
					continue;
				}
				//测量子大小
				//水平与垂直的可用空间是分开计算的
				if (mGravity == Gravity.Vertical) {
					view.measure(maxWidth - getPaddingBounds().x - getPaddingBounds().width - view.getMarginBounds().x - view.getMarginBounds().width, getWidthMeasureMode(), maxHeight - getPaddingBounds().y - getPaddingBounds().height - view.getMarginBounds().y - view.getMarginBounds().height - mViewsHeight, getHeightMeasureMode(), graphics);
				} else {
					view.measure(maxWidth - getPaddingBounds().x - getPaddingBounds().width - view.getMarginBounds().x - view.getMarginBounds().width - mViewsWidth, getWidthMeasureMode(), maxHeight - getPaddingBounds().y - getPaddingBounds().height - view.getMarginBounds().y - view.getMarginBounds().height, getHeightMeasureMode(), graphics);
				}
				//统计
				measureWidth = Math.max(measureWidth, view.getMarginBounds().x + view.getWidth() + view.getMarginBounds().width);
				mViewsWidth += view.getMarginBounds().x + view.getWidth() + view.getMarginBounds().width;
				measureHeight = Math.max(measureHeight, view.getMarginBounds().y + view.getHeight() + view.getMarginBounds().height);
				mViewsHeight += view.getMarginBounds().y + view.getHeight() + view.getMarginBounds().height;
			}
			
			//设置自身大小
			if (mGravity == Gravity.Vertical) {
				setMeasureSize(measureWidth + getPaddingBounds().x + getPaddingBounds().width, maxWidth, mViewsHeight + getPaddingBounds().y + getPaddingBounds().height, maxHeight, fatherWidthMeasureMode, fatherHeightMeasureMode);
			} else {
				setMeasureSize(mViewsWidth + getPaddingBounds().x + getPaddingBounds().width, maxWidth, measureHeight + getPaddingBounds().y + getPaddingBounds().height, maxHeight, fatherWidthMeasureMode, fatherHeightMeasureMode);
			}
		}
	}
	
	@Override
	protected void onLayoutChild() {
//		super.onLayoutChild();
		synchronized (getSynchronizedTag()) {
			//布局时使用的宽高
			int layoutWidth = 0, layoutHeight = 0;
			//布局与应用Align子视图
			for (View view : getViewList()) {
				if (!view.isVisible()) {
					continue;
				}
				//应用Align
				int x = view.getMarginBounds().x, y = view.getMarginBounds().y;
				
				//假设横纵上都没重力，计算此时应用Align后坐标
				switch (mHorizontalAlign) {
					case Center: {
						x = (getWidth() - view.getWidth()) / 2;
					} break;
					case Right: {
						x = getWidth() - view.getWidth() - view.getMarginBounds().x;
					} break;
				}
				switch (mVerticalAlign) {
					case Center: {
						y = (getHeight() - view.getHeight()) / 2;
					} break;
					case Bottom: {
						y = getHeight() - view.getHeight() - view.getMarginBounds().y;
					} break;
				}
				
				//重新计算重力影响后有重力这一轴上的坐标（应用重力后将有重力的一轴上把子的这一轴设置到上一子的下方或右方）
				if (mGravity == Gravity.Vertical) {
					switch (mVerticalAlign) {
						case Top: {
							y = layoutHeight + view.getMarginBounds().y;
						} break;
						case Center: {
							y = (getContentHeight() - mViewsHeight) / 2 + layoutHeight + view.getMarginBounds().y;
						} break;
						case Bottom: {
							y = (getContentHeight() - mViewsHeight) + layoutHeight + view.getMarginBounds().y;
						} break;
					}
				} else {
					switch (mHorizontalAlign) {
						case Left: {
							x = layoutWidth + view.getMarginBounds().x;
						} break;
						case Center: {
							x = (getContentWidth() - mViewsWidth) / 2 + layoutWidth + view.getMarginBounds().x;
						} break;
						case Right: {
							x = (getContentWidth() - mViewsWidth) + layoutWidth + view.getMarginBounds().x;
						} break;
					}
				}
				
				//设置位置
				view.setPosition(x, y);
				
				//统计
				layoutWidth += view.getMarginBounds().x + view.getWidth() + view.getMarginBounds().width;
				layoutHeight += view.getMarginBounds().y + view.getHeight() + view.getMarginBounds().height;;
			}
		}
	}
	
	public Gravity getGravity() {
		return mGravity;
	}
	
	public void setGravity(Gravity gravity) {
		mGravity = gravity;
	}
	
	public Align getVerticalAlign() {
		return mVerticalAlign;
	}
	
	public void setVerticalAlign(Align verticalAlign) {
		mVerticalAlign = verticalAlign;
	}
	
	public Align getHorizontalAlign() {
		return mHorizontalAlign;
	}
	
	public void setHorizontalAlign(Align horizontalAlign) {
		mHorizontalAlign = horizontalAlign;
	}
}
