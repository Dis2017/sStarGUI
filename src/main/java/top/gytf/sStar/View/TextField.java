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
import java.util.Scanner;

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
	private FontMetrics mTextMetrics;
	private Color mTipsColor;
	private Font mTipsFont;
	private float mTipsFontSize;
	private boolean mShowCursor;
	private Color mCursorColor;
	private int mShowTextLeftOffset;
	private int mCursorIndex;
	private int mSelectCount;
	private Color mSelectForegroundColor;
	private Color mSelectBackgroundColor;
	private int mDownX, mDownY;
	private boolean mDown;
	private int mDrawTextAreaWidth;
	
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
		mTextMetrics = null;
		mText = "";
		mMaxLength = 100;
		mTipsColor = new Color(0, 200, 250);
		mTipsFont = null;
		mTipsFontSize = 12;
		mShowCursor = true;
		mCursorColor = Color.BLACK;
		mShowTextLeftOffset = 0;
		mCursorIndex = 0;
		mSelectCount = 0;
		mSelectForegroundColor = mTipsColor;
		mSelectBackgroundColor = Color.WHITE;
		mDownX = mDownY = 0;
		mDown = false;
		mDrawTextAreaWidth = 0;
		
		mTipsText = "";
		
		setText("123456");
		
		setWidthMeasureMode(MeasureMode.wrap_content);
		setHeightMeasureMode(MeasureMode.wrap_content);
		
		setMinWidth(240);
		setMinHeight(40);
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
		mTextMetrics = graphics.getFontMetrics();
		int width = mTextMetrics.stringWidth(mText);
		int height = mTextMetrics.getHeight();
		
		if (mTipsFont != null) {
			graphics.setFont(mTipsFont.deriveFont(mTipsFontSize));
		} else {
			graphics.setFont(graphics.getFont().deriveFont(mTipsFontSize));
		}
		FontMetrics metrics = graphics.getFontMetrics();
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
		mDrawTextAreaWidth = width;
		graphics = graphics.create(0, 0, mDrawTextAreaWidth, getContentHeight());
		graphics.setColor(mFontColor);
		if (mFont != null) {
			graphics.setFont(mFont.deriveFont(mFontSize));
		} else {
			graphics.setFont(graphics.getFont().deriveFont(mFontSize));
		}
		mTextMetrics = graphics.getFontMetrics();
		//文字绘制时的x
		int x;
		//文字宽度未超过可绘制位置的宽度
		if (mTextMetrics.stringWidth(mText) < mDrawTextAreaWidth) {
			//应用重力
			switch (mTextAlign) {
				case Center: {
					x = (mDrawTextAreaWidth - mTextMetrics.stringWidth(mText)) / 2;
				} break;
				case Right: {
					x = mDrawTextAreaWidth - mTextMetrics.stringWidth(mText);
				} break;
				default: {
					x = 0;
				} break;
			}
		}
		//超过了，使用mShowTextLeftOffset
		else {
			//-1为了留出光标位置
			x = -mShowTextLeftOffset - 1;
		}
		//光标位置
		int cx = mTextMetrics.stringWidth(mText.substring(0, mCursorIndex)) + x;
		if (cx <= 0 && mCursorIndex > 0) {
			cx = 0;
			x += mTextMetrics.stringWidth(mText.substring(mCursorIndex - 1, mCursorIndex));
		}
		graphics.drawString(mText, x, (getContentHeight() + mTextMetrics.getAscent()) / 2);
		
		int sw;
		int sx = cx;
		String selText;
		//绘制选中
		graphics.setColor(mSelectBackgroundColor);
		if (mSelectCount >= 0) {
			selText = mText.substring(mCursorIndex, mCursorIndex + mSelectCount);
			sw = mTextMetrics.stringWidth(selText);
			graphics.fillRect(sx, (getContentHeight() - mTextMetrics.getAscent()) / 2, sw, mTextMetrics.getAscent() / 5 * 8);
		} else {
			selText = mText.substring(mCursorIndex + mSelectCount, mCursorIndex);
			sw = mTextMetrics.stringWidth(selText);
			sx -= sw;
			graphics.fillRect(sx, (getContentHeight() - mTextMetrics.getAscent()) / 2, sw, mTextMetrics.getAscent() / 5 * 8);
		}
		graphics.setColor(mSelectForegroundColor);
		graphics.drawString(selText, sx, (getContentHeight() + mTextMetrics.getAscent()) / 2);
		//绘制光标
		if (mShowCursor) {
			graphics.setColor(mCursorColor);
			graphics.drawLine(cx, (getContentHeight() - mTextMetrics.getAscent()) / 2, cx, mTextMetrics.getAscent() * 2);
		}
	}
	
	@Override
	public void handleMouseInput(MouseEvent event) {
		super.handleMouseInput(event);
		int width = mTextMetrics.stringWidth(mText);
		//按下时设置光标位置
		if (event.getWhat() == MouseEvent.WHAT_DOWN) {
			//记录按下的位置为拖动做铺垫
			mDownX = event.getX();
			mDownY = event.getY();
			mDown = true;
			//取消选中
			mSelectCount = 0;
			//设置光标位置
			if (mTextMetrics != null) {
				if (mDownX >= getContentX() && mDownX <= getContentX() + getContentWidth() && mDownY >= getContentY() && mDownY <= getContentY() + getContentHeight()) {
					setCursorIndex(Math.min((int) (((float) (mDownX + mShowTextLeftOffset) / width) * mText.length()), mText.length()));
				}
			}
		}
		//记录弹起
		else if (event.getWhat() == MouseEvent.WHAT_UP) {
			mDown = false;
		}
		//拖动时设置选择文本及光标位置
		else if (event.getWhat() == MouseEvent.WHAT_DRAG) {
			int x = event.getX();
			int y = event.getY();
			//设置选中及移动光标
			if (x >= getContentX() && x <= getContentX() + getContentWidth() && y >= getContentY() && y <= getContentY() + getContentHeight()) {
				int nowIndex = Math.min((int) (((float) (x + mShowTextLeftOffset) / width) * mText.length()), mText.length());
				int downIndex = Math.min((int) (((float) (mDownX + mShowTextLeftOffset) / width) * mText.length()), mText.length());
				mCursorIndex = nowIndex;
				mSelectCount = downIndex - nowIndex;
			}
		}
		//单击2的倍数次可选择全部
		else if (event.getWhat() == MouseEvent.WHAT_CLICK && event.getClickCount() % 2 == 0) {
			mCursorIndex = 0;
			mSelectCount = mText.length();
		}
	}
	
	@Override
	public void handleKeyInput(KeyEvent event) {
		super.handleKeyInput(event);
		
		boolean flag = true;
		
		if (!isFocus()) {
			return;
		}
		
		if (event.getState() == KeyEvent.STATE_DOWN) {
			//判断
			switch (event.getKeyEvent().getKeyCode()) {
				//方向键左
				case java.awt.event.KeyEvent.VK_LEFT: {
					//移动光标
					mCursorIndex--;
					//小于0？
					if (mCursorIndex < 0) {
						//复位且标记将跳过选中
						mCursorIndex = 0;
						flag = false;
					}
					//判断是否选中（Shift）
					if (event.getKeyEvent().isShiftDown() && flag) {
						mSelectCount++;
					} else if (!event.getKeyEvent().isShiftDown()) {
						mSelectCount = 0;
					}
					//判断是否Ctrl快速移动
					if (event.getKeyEvent().isControlDown()) {
						mSelectCount += event.getKeyEvent().isShiftDown() ? mCursorIndex : 0;
						mCursorIndex = 0;
					}
				} break;
				case java.awt.event.KeyEvent.VK_RIGHT: {
					//移动光标
					mCursorIndex++;
					//大于文本长度？
					if (mCursorIndex > mText.length()) {
						//复位且标记将跳过选中
						mCursorIndex = mText.length();
						flag = false;
					}
					//判断是否选中（Shift）
					if (event.getKeyEvent().isShiftDown() && flag) {
						mSelectCount--;
					} else if (!event.getKeyEvent().isShiftDown()) {
						mSelectCount = 0;
					}
					//判断是否Ctrl快速移动
					if (event.getKeyEvent().isControlDown()) {
						mSelectCount -= event.getKeyEvent().isShiftDown() ? mText.length() - mCursorIndex : 0;
						mCursorIndex = mText.length();
					}
				} break;
				//Backspace删除
				case java.awt.event.KeyEvent.VK_BACK_SPACE: {
					if (mSelectCount == 0 && mCursorIndex > 0) {
						//删除单个
						mCursorIndex--;
						mText = mText.substring(0, mCursorIndex) + mText.substring(mCursorIndex + 1);
					} else {
						//删除选中文本
						deleteSelText();
					}
					//移动显示定位
					
				} break;
			}
		} else if (event.getState() == KeyEvent.STATE_TYPED) {
			//过滤特殊字符（接收数字或字母）及限制输入
			if (mText.length() >= mMaxLength || !(isChinese(event.getKeyEvent().getKeyChar()) || isNumber(event) || isLetter(event))) {
				return;
			}
			//先删除所选文本
			if (mSelectCount != 0) {
				deleteSelText();
			}
			//插入
			String left = mText.substring(0, mCursorIndex);
			mText = left + event.getKeyEvent().getKeyChar() + mText.substring(mCursorIndex, mText.length());
			//移动光标
			mCursorIndex++;
		}
		
		//移动显示的定位
		resetShowTextLeftOffset();
	}
	
	private void resetShowTextLeftOffset() {
		int cursorBeforeWidth = mTextMetrics.stringWidth(mText.substring(0, mCursorIndex));
		int cursorX = cursorBeforeWidth - mShowTextLeftOffset;
		if (mTextMetrics != null && cursorX > mDrawTextAreaWidth) {
			mShowTextLeftOffset = cursorBeforeWidth - mDrawTextAreaWidth;
			System.out.println(mShowTextLeftOffset);
		} else if (mTextMetrics != null && cursorX < 0) {
			//-1为了留出光标位置
			mShowTextLeftOffset = cursorBeforeWidth - 1;
		}
	}
	
	private void deleteSelText() {
		if (mSelectCount > 0) {
			mText = mText.substring(0, mCursorIndex) + mText.substring(mCursorIndex + mSelectCount);
		} else if (mSelectCount < 0){
			mText = mText.substring(0, mCursorIndex + mSelectCount) + mText.substring(mCursorIndex);
			mCursorIndex += mSelectCount;
		}
		mSelectCount = 0;
	}
	
	private boolean isChinese(char ch) {
		//获取此字符的UniCodeBlock
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
		// 判断中文的“号
		return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION         // 判断中文的。号
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS       // 判断中文的，号
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;
	}
	
	private boolean isLetter(KeyEvent event) {
		return (event.getKeyEvent().getKeyChar() >= 'a' && event.getKeyEvent().getKeyChar() <= 'z') || (event.getKeyEvent().getKeyChar() >= 'A' && event.getKeyEvent().getKeyChar() <= 'Z');
	}
	
	private boolean isNumber(KeyEvent event) {
		return event.getKeyEvent().getKeyChar() >= '0' && event.getKeyEvent().getKeyChar() <= '9';
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
	
	public int getCursorIndex() {
		return mCursorIndex;
	}
	
	public void setCursorIndex(int cursorIndex) {
		mCursorIndex = cursorIndex;
	}
	
	public int getSelectCount() {
		return mSelectCount;
	}
	
	public void setSelectCount(int selectCount) {
		mSelectCount = selectCount;
	}
	
	public Color getSelectForegroundColor() {
		return mSelectForegroundColor;
	}
	
	public void setSelectForegroundColor(Color selectForegroundColor) {
		mSelectForegroundColor = selectForegroundColor;
	}
	
	public Color getSelectBackgroundColor() {
		return mSelectBackgroundColor;
	}
	
	public void setSelectBackgroundColor(Color selectBackgroundColor) {
		mSelectBackgroundColor = selectBackgroundColor;
	}
}
