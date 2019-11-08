package top.gytf.sStar.View;

import top.gytf.sStar.Core.Attribute;
import top.gytf.sStar.Core.Screen;
import top.gytf.sStar.Core.Style;
import top.gytf.sStar.Core.View;
import top.gytf.sStar.Input.KeyEvent;
import top.gytf.sStar.Input.MouseEvent;
import top.gytf.sStar.Other.Align;
import top.gytf.sStar.Other.MeasureMode;

import java.awt.*;

/**
 * @Author Dis
 * @create 2019/11/8 19:42
 * @project sStarGUI
 * @describe 文本框
 */
public class TextField extends View {
	
	private static final String tag = "TextField";
	
	public static final String NAME_BOTTOM_LINE_COLOR = tag + "NAME_BOTTOM_LINE_COLOR";
	public static final String NAME_BOTTOM_LINE_SIZE = tag + "NAME_BOTTOM_LINE_SIZE";
	
	private boolean mShowTextLength;
	private boolean mShowMaxLength;
	private Align mTextAlign;
	private Font mFont;
	private Color mFontColor;
	private float mFontSize;
	private int mMaxLength;
	private String mText;
	private Color mTipsColor;
	private Font mTipsFont;
	private float mTipsFontSize;
	private boolean mShowCursor;
	private Color mCursorColor;
	private int mCursorIndex;
	
	private String mTipsText;
	
	public TextField(String id, Screen ownerScreen) {
		super(id, ownerScreen);
		
		setBottomLineColor(Color.GRAY);
		setBottomLineSize(1);
		mShowTextLength = true;
		mShowMaxLength = true;
		mTextAlign = Align.Left;
		mFontColor = Color.WHITE;
		mFont = null;
		mFontSize = 16;
		mText = "";
		mMaxLength = 100;
		mTipsColor = new Color(0, 200, 250);
		mTipsFont = null;
		mTipsFontSize = 12;
		mShowCursor = true;
		mCursorColor = Color.BLACK;
		mCursorIndex = 0;
		
		mTipsText = "";
		
		setText("123456");
		
		setWidthMeasureMode(MeasureMode.wrap_content);
		setHeightMeasureMode(MeasureMode.wrap_content);
		
		setMinWidth(240);
		setMinHeight(40);
	}
	
	@Override
	public void handleMouseInput(MouseEvent event) {
		super.handleMouseInput(event);
	}
	
	@Override
	protected void onFocus() {
		super.onFocus();
		System.out.println("on Focus");
		setBottomLineColor(new Color(0, 200, 250));
		setBottomLineSize(2);
	}
	
	@Override
	protected void onLostFocus() {
		super.onLostFocus();
		System.out.println("on Lost Focus");
		setBottomLineColor(Color.GRAY);
		setBottomLineSize(1);
	}
	
	@Override
	public void onMeasure(int maxWidth, MeasureMode fatherWidthMeasureMode, int maxHeight, MeasureMode fatherHeightMeasureMode, Graphics graphics) {
//		super.onMeasure(maxWidth, fatherWidthMeasureMode, maxHeight, fatherHeightMeasureMode, graphics);
		if (mFont != null) {
			graphics.setFont(mFont.deriveFont(mFontSize));
		} else {
			graphics.setFont(graphics.getFont().deriveFont(mFontSize));
		}
		FontMetrics metrics = graphics.getFontMetrics();
		int width = metrics.stringWidth(mText);
		int height = metrics.getHeight();
		
		if (mTipsFont != null) {
			graphics.setFont(mTipsFont.deriveFont(mTipsFontSize));
		} else {
			graphics.setFont(graphics.getFont().deriveFont(mTipsFontSize));
		}
		metrics = graphics.getFontMetrics();
		height = Math.max(height, metrics.getHeight());
		width += height / 2;
		setMeasureSize( width + 2 * metrics.stringWidth(String.valueOf(mMaxLength) + " / "), maxWidth, height + getBottomLineSize(), maxHeight, fatherWidthMeasureMode, fatherHeightMeasureMode);
	}
	
	@Override
	protected void onDrawBackground(Graphics graphics) {
		super.onDrawBackground(graphics);
		
		//绘制底部线
		int size = getBottomLineSize();
		graphics.setColor(getBottomLineColor());
		graphics.fillRect(0, getHeight() - size, getWidth(), size);
	}
	
	@Override
	protected void onDraw(Graphics graphics) {
		super.onDraw(graphics);
		
		if (mTipsFont != null) {
			graphics.setFont(mTipsFont.deriveFont(mTipsFontSize));
		} else {
			graphics.setFont(graphics.getFont().deriveFont(mTipsFontSize));
		}
		FontMetrics metrics = graphics.getFontMetrics();
		int width = getContentWidth();
		
		//绘制字数（及可用字数）
		mTipsText = (isShowTextLength() ? String.valueOf(mText.length()) : "") + (isShowMaxLength() ? " / " + mMaxLength : "");
		graphics.setColor(mTipsColor);
		width -= metrics.stringWidth(mTipsText);
		graphics.drawString(mTipsText, width, (getHeight() + metrics.getAscent()) / 2);
		
		//绘制文字
		graphics.setColor(mFontColor);
		if (mFont != null) {
			graphics.setFont(mFont.deriveFont(mFontSize));
		} else {
			graphics.setFont(graphics.getFont().deriveFont(mFontSize));
		}
		metrics = graphics.getFontMetrics();
		int x = 0;
		switch (mTextAlign) {
			case Center: {
				x = (width - metrics.stringWidth(mText)) / 2;
			} break;
			case Right: {
				x = width - metrics.stringWidth(mText);
			} break;
		}
		graphics.drawString(mText, x, (getContentHeight() + metrics.getAscent()) / 2);
		
		//绘制光标
		if (mShowCursor) {
			graphics.setColor(mCursorColor);
			int cx = metrics.stringWidth(mText.substring(0, mCursorIndex));
			graphics.drawLine(cx, (getContentHeight() - metrics.getAscent()) / 2, cx, metrics.getAscent() * 2);
		}
	}
	
	@Override
	public void handleKeyInput(KeyEvent event) {
		super.handleKeyInput(event);
		
		if (!isFocus()) {
			return;
		}
		
		if (event.getState() == KeyEvent.STATE_DOWN) {
			switch (event.getKeyEvent().getKeyCode()) {
				case java.awt.event.KeyEvent.VK_LEFT: {
					mCursorIndex = Math.max(mCursorIndex - 1, 0);
				}
				break;
				case java.awt.event.KeyEvent.VK_RIGHT: {
					mCursorIndex = Math.min(mCursorIndex + 1, mText.length());
				}
				break;
				case java.awt.event.KeyEvent.VK_BACK_SPACE: {
					mCursorIndex -= mCursorIndex > 0 ? 1 : 0;
					mText = mText.substring(0, mCursorIndex);
				}
				break;
				default: {
					if (mText.length() >= mMaxLength) {
						return;
					}
					String left = mText.substring(0, mCursorIndex);
					mText = left + event.getKeyEvent().getKeyChar() + mText.substring(mCursorIndex, mText.length());
					mCursorIndex++;
				}
			}
		}
		
	}
	
	public Color getBottomLineColor() {
		Attribute baseAttribute = getOwnerScreen().getGui().getStyle().getBaseAttribute();
		return getAttribute().get(NAME_BOTTOM_LINE_COLOR, baseAttribute.get(Style.BACKGROUND_COLOR, getBackgroundColor(), false), true);
	}
	
	public void setBottomLineColor(Color bottomLineColor) {
		getAttribute().set(NAME_BOTTOM_LINE_COLOR, bottomLineColor);
	}
	
	public int getBottomLineSize() {
		Attribute baseAttribute = getOwnerScreen().getGui().getStyle().getBaseAttribute();
		return getAttribute().get(NAME_BOTTOM_LINE_SIZE, 1, true);
	}
	public void setBottomLineSize(int bottomLineSize) {
		getAttribute().set(NAME_BOTTOM_LINE_SIZE, bottomLineSize);
	}
	
	public int getMaxLength() {
		return mMaxLength;
	}
	
	public void setMaxLength(int maxLength) {
		mMaxLength = maxLength;
	}
	
	public String getText() {
		return mText;
	}
	
	public void setText(String text) {
		mText = text;
		mCursorIndex = mText.length();
	}
	
	public Font getFont() {
		return mFont;
	}
	
	public void setFont(Font font) {
		mFont = font;
	}
	
	public float getFontSize() {
		return mFontSize;
	}
	
	public void setFontSize(float fontSize) {
		mFontSize = fontSize;
	}
	
	public boolean isShowTextLength() {
		return mShowTextLength;
	}
	
	public void setShowTextLength(boolean showTextLength) {
		mShowTextLength = showTextLength;
	}
	
	public boolean isShowMaxLength() {
		return mShowMaxLength;
	}
	
	public void setShowMaxLength(boolean showMaxLength) {
		mShowMaxLength = showMaxLength;
	}
	
	public Color getTipsColor() {
		return mTipsColor;
	}
	
	public void setTipsColor(Color tipsColor) {
		mTipsColor = tipsColor;
	}
	
	public float getTipsFontSize() {
		return mTipsFontSize;
	}
	
	public void setTipsFontSize(float tipsFontSize) {
		mTipsFontSize = tipsFontSize;
	}
	
	public Font getTipsFont() {
		return mTipsFont;
	}
	
	public void setTipsFont(Font tipsFont) {
		mTipsFont = tipsFont;
	}
	
	public Color getFontColor() {
		return mFontColor;
	}
	
	public void setFontColor(Color fontColor) {
		mFontColor = fontColor;
	}
	
	public boolean isShowCursor() {
		return mShowCursor;
	}
	
	public void setShowCursor(boolean showCursor) {
		mShowCursor = showCursor;
	}
	
	public Color getCursorColor() {
		return mCursorColor;
	}
	
	public void setCursorColor(Color cursorColor) {
		mCursorColor = cursorColor;
	}
}
