package top.gytf.sStar.View;

import top.gytf.sStar.Input.MouseEvent;
import top.gytf.sStar.Core.Screen;
import top.gytf.sStar.Core.View;
import top.gytf.sStar.Other.Align;
import top.gytf.sStar.Other.MeasureMode;
import top.gytf.sStar.Other.ScaleMode;

import java.awt.*;

/**
 * @Author Dis
 * @create 2019/10/27 8:27
 * @project sStarGUI
 * @describe 图片视图
 */
public class ImageView extends View {

	private static final String tag = "ImageView";
	
	public static final String NAME_IMAGE = tag + "NameImage";
	public static final String NAME_VERTICAL_ALIGN = tag + "NAME_VERTICAL_ALIGN";
	public static final String NAME_HORIZONTAL_ALIGN = tag + "NAME_HORIZONTAL_ALIGN";
	public static final String NAME_SCALE_MODE = tag + "NAME_SCALE_MODE";
	
//	private Image mImage;
//	private Align mVerticalAlign;
//	private Align mHorizontalAlign;
//	private ScaleMode mScaleMode;
	
	public ImageView(String id, Screen ownerScreen) {
		super(id, ownerScreen);
//		mImage = null;
//		mVerticalAlign = Align.Center;
//		mHorizontalAlign = Align.Center;
//		mScaleMode = ScaleMode.Fill;
		setWidthMeasureMode(MeasureMode.wrap_content);
		setHeightMeasureMode(MeasureMode.wrap_content);
	}
	
	@Override
	public void onMeasure(int maxWidth, MeasureMode fatherWidthMeasureMode, int maxHeight, MeasureMode fatherHeightMeasureMode, Graphics graphics) {
//		super.onMeasure(maxWidth, fatherWidthMeasureMode, maxHeight, fatherHeightMeasureMode, graphics);
		Image image = getImage();
		if (image == null) {
			setMeasureSize(0, maxWidth, 0, maxHeight, fatherWidthMeasureMode, fatherHeightMeasureMode);
		} else {
			setMeasureSize(image.getWidth(null), maxWidth, image.getHeight(null), maxHeight, fatherWidthMeasureMode, fatherHeightMeasureMode);
		}
	}
	
	@Override
	protected void onDraw(Graphics graphics) {
		super.onDraw(graphics);
		
		Image image = getImage();
		ScaleMode scaleMode = getScaleMode();
		Align verticalAlign = getVerticalAlign();
		Align horizontalAlign = getHorizontalAlign();
		
		if (image == null) {
			return;
		}
		int x = 0, y = 0;
		int width = getContentWidth(), height = getContentHeight();
		
		//依据缩放模式调节显示尺寸
		if (scaleMode == ScaleMode.Normal) {
			width = image.getWidth(null);
			height = image.getHeight(null);
		} else if (scaleMode == ScaleMode.Adapt) {
			float proportion = (float) (image.getWidth(null) / image.getHeight(null));
			int size = Math.min(getContentWidth(), getContentHeight());
			width = (int) (size / proportion);
			height = (int) (size * proportion);
		}
		
		//依据对齐模式调节X、Y
		switch (horizontalAlign) {
			case Center: {
				x = (getContentWidth() - width) / 2;
			} break;
			case Right: {
				x = getContentWidth() - width;
			} break;
		}
		switch (verticalAlign) {
			case Center: {
				y = (getContentHeight() - height) / 2;
			} break;
			case Bottom: {
				y = getContentHeight() - height;
			} break;
		}
		
		graphics.drawImage(image, x, y, width, height, null);
	}
	
	public Image getImage() {
		return getAttribute().get(NAME_IMAGE, null, true);
	}
	
	public void setImage(Image image) {
		getAttribute().set(NAME_IMAGE, image);
	}
	
	public Align getVerticalAlign() {
		return getAttribute().get(NAME_VERTICAL_ALIGN, Align.Top, true);
	}
	
	public void setVerticalAlign(Align verticalAlign) {
		getAttribute().set(NAME_VERTICAL_ALIGN, verticalAlign);
	}
	
	public Align getHorizontalAlign() {
		return getAttribute().get(NAME_HORIZONTAL_ALIGN, Align.Left, true);
	}
	
	public void setHorizontalAlign(Align horizontalAlign) {
		getAttribute().set(NAME_HORIZONTAL_ALIGN, horizontalAlign);
	}
	
	public ScaleMode getScaleMode() {
		return getAttribute().get(NAME_SCALE_MODE, ScaleMode.Adapt, true);
	}
	
	public void setScaleMode(ScaleMode scaleMode) {
		getAttribute().set(NAME_SCALE_MODE, scaleMode);
	}
	
}
