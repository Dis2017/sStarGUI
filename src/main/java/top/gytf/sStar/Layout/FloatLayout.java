package top.gytf.sStar.Layout;

import top.gytf.sStar.Core.Screen;
import top.gytf.sStar.Core.View;
import top.gytf.sStar.Core.ViewGroup;
import top.gytf.sStar.Other.Align;
import top.gytf.sStar.Other.MeasureMode;

import java.awt.*;

/**
 * @Author Dis
 * @create 2019/11/2 15:56
 * @project sStarGUI
 * @describe 浮动布局
 */
public class FloatLayout extends ViewGroup {
	
	private static final String tag = "FloatLayout";
	
	public static final String NAME_VERTICAL_ALIGN = tag + "NAME_VERTICAL_ALIGN";
	public static final String NAME_HORIZONTAL_ALIGN = tag + "NAME_HORIZONTAL_ALIGN";
	
	public FloatLayout(String id, Screen ownerScreen) {
		super(id, ownerScreen);
	}
	
	@Override
	protected void onMeasureChild(int maxWidth, MeasureMode fatherWidthMeasureMode, int maxHeight, MeasureMode fatherHeightMeasureMode, Graphics graphics) {
//		super.onMeasureChild(maxWidth, fatherWidthMeasureMode, maxHeight, fatherHeightMeasureMode, graphics);
		
		//测量子
		synchronized (getSynchronizedTag()) {
			
			Align verticalAlign, horizontalAlign;
			int x = 0, y = 0;
			int measureWidth = 0, measureHeight = 0;
			int[][] widths = new int[3][3];
			int[][] heights = new int[3][3];
			
			for (View view : getViewList()) {
				
				//获取浮动方式
				horizontalAlign = view.getAttribute().get(NAME_HORIZONTAL_ALIGN, Align.Left, true);
				verticalAlign = view.getAttribute().get(NAME_VERTICAL_ALIGN, Align.Top, true);
				
				//测量
				view.measure(maxWidth - getPaddingBounds().x - getPaddingBounds().width - view.getMarginBounds().x - view.getMarginBounds().width, getWidthMeasureMode(), maxHeight - getPaddingBounds().y - getPaddingBounds().height - view.getMarginBounds().y - view.getMarginBounds().height, getHeightMeasureMode(), graphics);
				
				//统计
				switch (verticalAlign) {
					case Top: {
						y = 0;
					} break;
					case Center: {
						y = 1;
					} break;
					case Bottom: {
						y = 2;
					} break;
				}
				switch (horizontalAlign) {
					case Left: {
						x = 0;
					} break;
					case Center: {
						x = 1;
					} break;
					case Right: {
						x = 2;
					} break;
				}
				widths[y][x] = Math.max(widths[y][x], view.getWidth() + view.getMarginBounds().x + view.getMarginBounds().width);
				heights[y][x] = Math.max(heights[y][x], view.getHeight() + view.getMarginBounds().y + view.getMarginBounds().height);
				
			}
			
			//自身大小
			for (y = 0; y < 3; y++) {
				measureWidth = Math.max(measureWidth, widths[y][0] + widths[y][1] + widths[y][2] + getPaddingBounds().x + getPaddingBounds().width);
			}
			for (x = 0; x < 3; x++) {
				measureHeight = Math.max(measureHeight, heights[0][x] + heights[1][x] + heights[2][x] + getPaddingBounds().y + getPaddingBounds().height);
			}
			setMeasureSize(measureWidth, maxWidth, measureHeight, maxHeight, fatherWidthMeasureMode, fatherHeightMeasureMode);
			
		}
		
	}
	
	@Override
	protected void onLayoutChild() {
//		super.onLayoutChild();
		synchronized (tag) {
			Align verticalAlign, horizontalAlign;
			for (View view : getViewList()) {
				
				int x = view.getMarginBounds().x, y = view.getMarginBounds().y;
				
				//获取浮动方式
				horizontalAlign = view.getAttribute().get(NAME_HORIZONTAL_ALIGN, Align.Left, true);
				verticalAlign = view.getAttribute().get(NAME_VERTICAL_ALIGN, Align.Top, true);
				
				switch (verticalAlign) {
					case Center: {
						y = (getContentHeight() - view.getHeight()) / 2;
					} break;
					case Bottom: {
						y = (getContentHeight() - view.getHeight()) - view.getMarginBounds().height;
					} break;
				}
				switch (horizontalAlign) {
					case Center: {
						x = (getContentWidth() - view.getWidth()) / 2;
					} break;
					case Right: {
						x = (getContentWidth() - view.getWidth()) - view.getMarginBounds().width;
					} break;
				}
				
				view.setPosition(x, y);
			}
		}
	}
	
}
