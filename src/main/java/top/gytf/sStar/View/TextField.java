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
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * @Author Dis
 * @create 2019/11/8 19:42
 * @project sStarGUI
 * @describe 文本框
 */
public class TextField extends View {
	
	private static final String tag = "TextField";
	
	public static final String NAME_BOTTOM_LINE_COLOR_FOCUS = tag + "NAME_BOTTOM_LINE_COLOR_FOCUS";
	public static final String NAME_BOTTOM_LINE_SIZE_FOCUS = tag + "NAME_BOTTOM_LINE_SIZE_FOCUS";
	public static final String NAME_BOTTOM_LINE_COLOR_UN_FOCUS = tag + "NAME_BOTTOM_LINE_COLOR_UN_FOCUS";
	public static final String NAME_BOTTOM_LINE_SIZE_UN_FOCUS = tag + "NAME_BOTTOM_LINE_SIZE_UN_FOCUS";
	
	public static final String NAME_TIPS_SIZE_FOCUS = tag + "NAME_TIPS_SIZE_FOCUS";
	public static final String NAME_TIPS_COLOR_FOCUS = tag + "NAME_TIPS_COLOR_FOCUS";
	public static final String NAME_TIPS_SIZE_UN_FOCUS = tag + "NAME_TIPS_SIZE_UN_FOCUS";
	public static final String NAME_TIPS_COLOR_UN_FOCUS = tag + "NAME_TIPS_COLOR_UN_FOCUS";
	
	public static final String NAME_SHOW_TEXT_LENGTH = tag + "NAME_SHOW_TEXT_LENGTH";
	public static final String NAME_SHOW_MAX_LENGTH = tag + "NAME_SHOW_MAX_LENGTH";
	public static final String NAME_TEXT_ALIGN = tag + "NAME_TEXT_ALIGN";
	public static final String NAME_FONT = tag + "NAME_FONT";
	public static final String NAME_FONT_COLOR = tag + "NAME_FONT_COLOR";
	public static final String NAME_FONT_SIZE = tag + "NAME_FONT_SIZE";
	public static final String NAME_MAX_LENGTH = tag + "NAME_MAX_LENGTH";
	public static final String NAME_TEXT = tag + "NAME_TEXT";
	public static final String NAME_TIPS_FONT = tag + "NAME_TIPS_FONT";
	public static final String NAME_SHOW_CURSOR = tag + "NAME_SHOW_CURSOR";
	public static final String NAME_CURSOR_COLOR = tag + "NAME_CURSOR_COLOR";
	public static final String NAME_CURSOR_INDEX = tag + "NAME_CURSOR_INDEX";
	public static final String NAME_CURSOR_TWINKLE_FRAME = tag + "NAME_CURSOR_TWINKLE_FRAME";
	public static final String NAME_SELECT_COUNT = tag + "NAME_SELECT_COUNT";
	public static final String NAME_SELECT_BACKGROUND_COLOR = tag + "NAME_SELECT_BACKGROUND_COLOR";
	public static final String NAME_SELECT_FOREGROUND_COLOR = tag + "NAME_SELECT_FOREGROUND_COLOR";
	
//	private boolean mShowTextLength;
//	private boolean mShowMaxLength;
//	private Align mTextAlign;
//	private Font mFont;
//	private Color mFontColor;
//	private float mFontSize;
//	private int mMaxLength;
//	private String mText;
//	private Font mTipsFont;
//	private boolean mShowCursor;
//	private Color mCursorColor;
//	private int mCursorIndex;
//	private int mSelectCount;
//	private Color mSelectForegroundColor;
//	private Color mSelectBackgroundColor;
//	private int mCursorTwinkleFrame;
	
	private FontMetrics mTextMetrics;
	private int mShowTextLeftOffset;
	private int mDownX, mDownY;
	private boolean mDown;
	private int mFrameCount;
	private int mDrawTextAreaWidth;
	
	public TextField(String id, Screen ownerScreen) {
		super(id, ownerScreen);
		
//		mShowTextLength = true;
//		mShowMaxLength = true;
//		mTextAlign = Align.Left;
//		mFontColor = Color.WHITE;
//		mFont = null;
//		mFontSize = 16;
//		mText = "";
//		mMaxLength = 20;

//		mTipsFont = null;
//		mShowCursor = true;
//		mCursorColor = Color.BLACK;
//		mCursorIndex = 0;
//		mSelectCount = 0;
//		mSelectForegroundColor =  new Color(0, 200, 250);
//		mSelectBackgroundColor = Color.WHITE;
//		mCursorTwinkleFrame = 15;
		
		mTextMetrics = null;
		mShowTextLeftOffset = 0;
		mDownX = mDownY = 0;
		mDown = false;
		mDrawTextAreaWidth = 0;
		mFrameCount = 0;
		
		setWidthMeasureMode(MeasureMode.wrap_content);
		setHeightMeasureMode(MeasureMode.wrap_content);
		
		setMinWidth(240);
		setMinHeight(40);
	}
	
	@Override
	public void onMeasure(int maxWidth, MeasureMode fatherWidthMeasureMode, int maxHeight, MeasureMode fatherHeightMeasureMode, Graphics graphics) {
//		super.onMeasure(maxWidth, fatherWidthMeasureMode, maxHeight, fatherHeightMeasureMode, graphics);
		Font font = getFont();
		Font tipsFont = getTipsFont();
		float fontSize = getFontSize();
		float tipsSize = getTipsSize();
		String text = getText();
		int mMaxLength = getMaxLength();
		if (font != null) {
			graphics.setFont(font.deriveFont(fontSize));
		} else {
			graphics.setFont(graphics.getFont().deriveFont(fontSize));
		}
		mTextMetrics = graphics.getFontMetrics();
		int width = mTextMetrics.stringWidth(text);
		int height = mTextMetrics.getHeight();
		
		if (tipsFont != null) {
			graphics.setFont(tipsFont.deriveFont(tipsSize));
		} else {
			graphics.setFont(graphics.getFont().deriveFont(tipsSize));
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
		
		String text = getText();
		boolean showCursor = isShowCursor();
		int maxLength = getMaxLength();
		int cursorIndex = getCursorIndex();
		int selectCount = getSelectCount();
		int cursorTwinkleFrame = getCursorTwinkleFrame();
		Color cursorColor = getCursorColor();
		Color fontColor = getFontColor();
		Color selectBackgroundColor = getSelectBackgroundColor();
		Color selectForegroundColor = getSelectForegroundColor();
		Font font = getFont();
		Font tipsFont = getTipsFont();
		float fontSize = getFontSize();
		Align textAlign = getTextAlign();
		
		if (tipsFont != null) {
			graphics.setFont(tipsFont.deriveFont(getTipsSize()));
		} else {
			graphics.setFont(graphics.getFont().deriveFont(getTipsSize()));
		}
		FontMetrics metrics = graphics.getFontMetrics();
		int width = getContentWidth();
		
		//绘制字数（及可用字数）
		String tipsText = (isShowTextLength() ? String.valueOf(text.length()) : "") + (isShowTextLength() && isShowMaxLength() ? " / " : "") + (isShowMaxLength() ? maxLength : "");
		graphics.setColor(getTipsColor());
		width -= metrics.stringWidth(tipsText);
		graphics.drawString(tipsText, width, (getHeight() + metrics.getAscent()) / 2);
		
		//绘制文字
		mDrawTextAreaWidth = width;
		graphics = graphics.create(0, 0, mDrawTextAreaWidth, getContentHeight());
		graphics.setColor(fontColor);
		if (font != null) {
			graphics.setFont(font.deriveFont(fontSize));
		} else {
			graphics.setFont(graphics.getFont().deriveFont(fontSize));
		}
		mTextMetrics = graphics.getFontMetrics();
		//文字绘制时的x
		int x;
		//文字宽度未超过可绘制位置的宽度
		if (mTextMetrics.stringWidth(text) < mDrawTextAreaWidth) {
			//应用重力
			switch (textAlign) {
				case Center: {
					x = (mDrawTextAreaWidth - mTextMetrics.stringWidth(text)) / 2 - 1;
				} break;
				case Right: {
					x = mDrawTextAreaWidth - mTextMetrics.stringWidth(text) - 1;
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
		int cx = mTextMetrics.stringWidth(text.substring(0, cursorIndex)) + x;
		if (cx <= 0 && cursorIndex > 0) {
			cx = mTextMetrics.stringWidth(text.substring(cursorIndex - 1, cursorIndex));
			x += cx;
		}
		graphics.drawString(text, x, (getContentHeight() + mTextMetrics.getAscent()) / 2);
		
		int sw;
		int sx = cx;
		String selText;
		//绘制选中
		graphics.setColor(selectBackgroundColor);
		if (selectCount >= 0) {
			selText = text.substring(cursorIndex, cursorIndex + selectCount);
			sw = mTextMetrics.stringWidth(selText);
			graphics.fillRect(sx, (getContentHeight() - mTextMetrics.getAscent()) / 2, sw, mTextMetrics.getAscent() / 5 * 8);
		} else {
			selText = text.substring(cursorIndex + selectCount, cursorIndex);
			sw = mTextMetrics.stringWidth(selText);
			sx -= sw;
			graphics.fillRect(sx, (getContentHeight() - mTextMetrics.getAscent()) / 2, sw, mTextMetrics.getAscent() / 5 * 8);
		}
		graphics.setColor(selectForegroundColor);
		graphics.drawString(selText, sx, (getContentHeight() + mTextMetrics.getAscent()) / 2);
		if (mFrameCount < cursorTwinkleFrame) {
			//绘制光标
			if (showCursor && isFocus()) {
				graphics.setColor(cursorColor);
				graphics.drawLine(cx, (getContentHeight() - mTextMetrics.getAscent()) / 2, cx, mTextMetrics.getAscent() * 2);
			}
			mFrameCount++;
		} else if (mFrameCount < cursorTwinkleFrame * 2) {
			mFrameCount++;
		} else {
			mFrameCount = 0;
		}
	}
	
	@Override
	public void handleMouseInput(MouseEvent event) {
		super.handleMouseInput(event);
		
		String text = getText();
		int selectCount;
		int cursorIndex;
		
		int width = mTextMetrics.stringWidth(text);
		//按下时设置光标位置
		if (event.getWhat() == MouseEvent.WHAT_DOWN) {
			//记录按下的位置为拖动做铺垫
			mDownX = event.getX();
			mDownY = event.getY();
			mDown = true;
			//取消选中
			selectCount = 0;
			//设置光标位置
			if (mTextMetrics != null) {
				if (mDownX >= getContentX() && mDownX <= getContentX() + getContentWidth() && mDownY >= getContentY() && mDownY <= getContentY() + getContentHeight()) {
					setCursorIndex(Math.min((int) (((float) (mDownX + mShowTextLeftOffset) / width) * text.length()), text.length()));
				}
			}
			setSelectCount(selectCount);
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
				int nowIndex = Math.min((int) (((float) (x + mShowTextLeftOffset) / width) * text.length()), text.length());
				int downIndex = Math.min((int) (((float) (mDownX + mShowTextLeftOffset) / width) * text.length()), text.length());
				cursorIndex = nowIndex;
				selectCount = downIndex - nowIndex;
				setSelectCount(selectCount);
				setCursorIndex(cursorIndex);
			}
		}
		//单击2的倍数次可选择全部
		else if (event.getWhat() == MouseEvent.WHAT_CLICK && event.getClickCount() % 2 == 0) {
			cursorIndex = 0;
			selectCount = text.length();
			setSelectCount(selectCount);
			setCursorIndex(cursorIndex);
		}
	}
	
	@Override
	public void handleKeyInput(KeyEvent event) {
		super.handleKeyInput(event);
		
		String text = getText();
		int cursorIndex = getCursorIndex();
		int selectCount = getSelectCount();
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
					cursorIndex--;
					//小于0？
					if (cursorIndex < 0) {
						//复位且标记将跳过选中
						cursorIndex = 0;
						flag = false;
					}
					//判断是否选中（Shift）
					if (event.getKeyEvent().isShiftDown() && flag) {
						selectCount++;
					} else if (!event.getKeyEvent().isShiftDown()) {
						selectCount = 0;
					}
					//判断是否Ctrl快速移动
					if (event.getKeyEvent().isControlDown()) {
						selectCount += event.getKeyEvent().isShiftDown() ? cursorIndex : 0;
						cursorIndex = 0;
					}
					setSelectCount(selectCount);
					setCursorIndex(cursorIndex);
				} break;
				//方向右
				case java.awt.event.KeyEvent.VK_RIGHT: {
					//移动光标
					cursorIndex++;
					//大于文本长度？
					if (cursorIndex > text.length()) {
						//复位且标记将跳过选中
						cursorIndex = text.length();
						flag = false;
					}
					//判断是否选中（Shift）
					if (event.getKeyEvent().isShiftDown() && flag) {
						selectCount--;
					} else if (!event.getKeyEvent().isShiftDown()) {
						selectCount = 0;
					}
					//判断是否Ctrl快速移动
					if (event.getKeyEvent().isControlDown()) {
						selectCount -= event.getKeyEvent().isShiftDown() ? text.length() - cursorIndex : 0;
						cursorIndex = text.length();
					}
					setSelectCount(selectCount);
					setCursorIndex(cursorIndex);
				} break;
				//Backspace删除
				case java.awt.event.KeyEvent.VK_BACK_SPACE: {
					if (selectCount == 0 && cursorIndex > 0) {
						//删除单个
						if (!event.getKeyEvent().isControlDown()) {
							cursorIndex--;
							text = text.substring(0, cursorIndex) + text.substring(cursorIndex + 1);
						}
						//删除光标之前
						else {
							text = text.substring(cursorIndex);
							cursorIndex = 0;
						}
						setText(text);
						setCursorIndex(cursorIndex);
					} else {
						//删除选中文本
						deleteSelText();
					}
				} break;
				//Ctrl + c复制选中
				case java.awt.event.KeyEvent.VK_C: {
					if (event.getKeyEvent().isControlDown() && selectCount != 0) {
						String selText = getSelText();
						Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
						Transferable trans = new StringSelection(selText);
						clipboard.setContents(trans, null);
					}
				} break;
				//Ctrl + c粘贴
				case java.awt.event.KeyEvent.VK_V: {
					if (event.getKeyEvent().isControlDown()) {
						Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
						Transferable trans = clipboard.getContents(null);
						if (trans != null) {
							// 判断剪贴板中的内容是否支持文本
							if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
								try {
									// 获取剪贴板中的文本内容
									String cpyText = (String) trans.getTransferData(DataFlavor.stringFlavor);
									if (selectCount != 0) {
										deleteSelText();
									}
									//插入
									insertText(cpyText);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				} break;
				//Ctrl + a全选
				case java.awt.event.KeyEvent.VK_A: {
					if (event.getKeyEvent().isControlDown()) {
						cursorIndex = 0;
						selectCount = text.length();
						setSelectCount(selectCount);
						setCursorIndex(cursorIndex);
					}
				} break;
			}
		} else if (event.getState() == KeyEvent.STATE_TYPED) {
			//过滤特殊字符（接收数字或字母）及限制输入
			if (!(isChinese(event.getKeyEvent().getKeyChar()) || isPunctuation(event.getKeyEvent().getKeyChar()) || isNumber(event) || isLetter(event))) {
				return;
			}
			//先删除所选文本
			if (selectCount != 0) {
				deleteSelText();
			}
			//插入
			insertText(String.valueOf(event.getKeyEvent().getKeyChar()));
		}
		
		//移动显示的定位
		resetShowTextLeftOffset();
	}
	
	private void insertText(String it) {
		String text = getText();
		int maxLength = getMaxLength();
		int cursorIndex = getCursorIndex();
		
		if (text.length() + it.length() > maxLength) {
			it = it.substring(0, maxLength - text.length());
		}
		String left = text.substring(0, cursorIndex);
		text = left + it + text.substring(cursorIndex);
		setText(text);
		//移动光标
		cursorIndex += it.length();
		setCursorIndex(cursorIndex);
	}
	
	private void resetShowTextLeftOffset() {
		String text = getText();
		int cursorIndex = getCursorIndex();
		int cursorBeforeWidth = mTextMetrics.stringWidth(text.substring(0, cursorIndex));
		int cursorX = cursorBeforeWidth - mShowTextLeftOffset;
		if (mTextMetrics != null && cursorX > mDrawTextAreaWidth) {
			mShowTextLeftOffset = cursorBeforeWidth - mDrawTextAreaWidth;
		} else if (mTextMetrics != null && cursorX < 0) {
			//-1为了留出光标位置
			mShowTextLeftOffset = cursorBeforeWidth - 1;
		}
	}
	
	private void deleteSelText() {
		String text = getText();
		int selectCount = getSelectCount();
		int cursorIndex = getCursorIndex();
		if (selectCount > 0) {
			text = text.substring(0, cursorIndex) + text.substring(cursorIndex + selectCount);
		} else if (selectCount < 0){
			text = text.substring(0, cursorIndex + selectCount) + text.substring(cursorIndex);
			cursorIndex += selectCount;
		}
		setText(text);
		selectCount = 0;
		cursorIndex = Math.min(cursorIndex, text.length());
		setCursorIndex(cursorIndex);
		setSelectCount(selectCount);
	}
	
	private String getSelText() {
		String text = getText();
		String selText;
		int cursorIndex = getCursorIndex();
		int selectCount = getSelectCount();
		if (selectCount >= 0) {
			selText = text.substring(cursorIndex, cursorIndex + selectCount);
		} else {
			selText = text.substring(cursorIndex + selectCount, cursorIndex);
		}
		return selText;
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
	
	private boolean isPunctuation(char ch) {
		String punctuationList = " ,./?'\";:|\\+_-=)(*&^%$#@!~`<>[]{}";
		return punctuationList.indexOf(ch) != -1;
	}
	
	private int getBottomLineSize() {
		return isFocus() ? getFocusBottomLineSize() : getUnFocusBottomLineSize();
	}
	
	private Color getBottomLineColor() {
		return isFocus() ? getFocusBottomLineColor() : getUnFocusBottomLineColor();
	}
	
	public Color getUnFocusBottomLineColor() {
		Attribute baseAttribute = getOwnerScreen().getGui().getStyle().getBaseAttribute();
		return getAttribute().get(NAME_BOTTOM_LINE_COLOR_UN_FOCUS, baseAttribute.get(Style.BACKGROUND_COLOR, getBackgroundColor(), false), true);
	}
	
	public void setUnFocusBottomLineColor(Color bottomLineColor) {
		getAttribute().set(NAME_BOTTOM_LINE_COLOR_UN_FOCUS, bottomLineColor);
	}
	
	public int getUnFocusBottomLineSize() {
		Attribute baseAttribute = getOwnerScreen().getGui().getStyle().getBaseAttribute();
		return getAttribute().get(NAME_BOTTOM_LINE_SIZE_UN_FOCUS, 1, true);
	}
	
	public void setUnFocusBottomLineSize(int bottomLineSize) {
		getAttribute().set(NAME_BOTTOM_LINE_SIZE_UN_FOCUS, bottomLineSize);
	}
	
	public Color getFocusBottomLineColor() {
		Attribute baseAttribute = getOwnerScreen().getGui().getStyle().getBaseAttribute();
		return getAttribute().get(NAME_BOTTOM_LINE_COLOR_FOCUS, baseAttribute.get(Style.BACKGROUND_COLOR, getBackgroundColor(), false), true);
	}
	
	private void setFocusBottomLineColor(Color bottomLineColor) {
		getAttribute().set(NAME_BOTTOM_LINE_COLOR_FOCUS, bottomLineColor);
	}
	
	public int getFocusBottomLineSize() {
		Attribute baseAttribute = getOwnerScreen().getGui().getStyle().getBaseAttribute();
		return getAttribute().get(NAME_BOTTOM_LINE_SIZE_FOCUS, 2, true);
	}
	
	private void setFocusBottomLineSize(int bottomLineSize) {
		getAttribute().set(NAME_BOTTOM_LINE_SIZE_FOCUS, bottomLineSize);
	}
	
	private float getTipsSize() {
		return isFocus() ? getFocusTipsSize() : getUnFocusTipsSize();
	}
	
	private Color getTipsColor() {
		return isFocus() ? getFocusTipsColor() : getUnFocusTipsColor();
	}
	
	public Color getUnFocusTipsColor() {
		Attribute baseAttribute = getOwnerScreen().getGui().getStyle().getBaseAttribute();
		return getAttribute().get(NAME_TIPS_COLOR_UN_FOCUS, baseAttribute.get(Style.BACKGROUND_COLOR, getBackgroundColor(), false), true);
	}
	
	public void setUnFocusTipsColor(Color tipsColor) {
		getAttribute().set(NAME_TIPS_COLOR_UN_FOCUS, tipsColor);
	}
	
	public float getUnFocusTipsSize() {
		Attribute baseAttribute = getOwnerScreen().getGui().getStyle().getBaseAttribute();
		return getAttribute().get(NAME_TIPS_SIZE_UN_FOCUS, 12f, true);
	}
	
	public void setUnFocusTipsSize(int tipsSize) {
		getAttribute().set(NAME_TIPS_SIZE_UN_FOCUS, tipsSize);
	}
	
	public Color getFocusTipsColor() {
		Attribute baseAttribute = getOwnerScreen().getGui().getStyle().getBaseAttribute();
		return getAttribute().get(NAME_TIPS_COLOR_FOCUS, baseAttribute.get(Style.BACKGROUND_COLOR, getBackgroundColor(), false), true);
	}
	
	private void setFocusTipsColor(Color tipsColor) {
		getAttribute().set(NAME_TIPS_COLOR_FOCUS, tipsColor);
	}
	
	public float getFocusTipsSize() {
		Attribute baseAttribute = getOwnerScreen().getGui().getStyle().getBaseAttribute();
		return getAttribute().get(NAME_TIPS_SIZE_FOCUS, 12f, true);
	}
	
	private void setFocusTipsSize(int tipsSize) {
		getAttribute().set(NAME_TIPS_SIZE_FOCUS, tipsSize);
	}
	
	public int getMaxLength() {
		return getAttribute().get(NAME_MAX_LENGTH, 20, true);
	}
	
	public void setMaxLength(int maxLength) {
		getAttribute().set(NAME_MAX_LENGTH, maxLength);
	}
	
	public String getText() {
		return getAttribute().get(NAME_TEXT, "", true);
	}
	
	public void setText(String text) {
		getAttribute().set(NAME_TEXT, text);
	}
	
	public Font getFont() {
		return getAttribute().get(NAME_FONT, null, true);
	}
	
	public void setFont(Font font) {
		getAttribute().set(NAME_FONT, font);
	}
	
	public float getFontSize() {
		return getAttribute().get(NAME_FONT_SIZE, 16f, true);
	}
	
	public void setFontSize(float fontSize) {
		getAttribute().set(NAME_FONT_SIZE, fontSize);
	}
	
	public boolean isShowTextLength() {
		return getAttribute().get(NAME_SHOW_TEXT_LENGTH, true, true);
	}
	
	public void setShowTextLength(boolean showTextLength) {
		getAttribute().set(NAME_SHOW_TEXT_LENGTH, showTextLength);
	}
	
	public boolean isShowMaxLength() {
		return getAttribute().get(NAME_SHOW_MAX_LENGTH, true, true);
	}
	
	public void setShowMaxLength(boolean showMaxLength) {
		getAttribute().set(NAME_SHOW_MAX_LENGTH, showMaxLength);
	}
	
	
	public Color getFontColor() {
		return getAttribute().get(NAME_FONT_COLOR, getForegroundColor(), true);
	}
	
	public void setFontColor(Color fontColor) {
		getAttribute().set(NAME_FONT_COLOR, fontColor);
	}
	
	public Font getTipsFont() {
		return getAttribute().get(NAME_TIPS_FONT, null, true);
	}
	
	public void setTipsFont(Font tipsFont) {
		getAttribute().set(NAME_TIPS_FONT, tipsFont);
	}
	
	public boolean isShowCursor() {
		return getAttribute().get(NAME_SHOW_CURSOR, true, false);
	}
	
	public void setShowCursor(boolean showCursor) {
		getAttribute().set(NAME_SHOW_CURSOR, showCursor);
	}
	
	public Color getCursorColor() {
		return getAttribute().get(NAME_CURSOR_COLOR, Color.BLACK, true);
	}
	
	public void setCursorColor(Color cursorColor) {
		getAttribute().set(NAME_CURSOR_COLOR, cursorColor);
	}
	
	public int getCursorIndex() {
		return getAttribute().get(NAME_CURSOR_INDEX, 0, true);
	}
	
	public void setCursorIndex(int cursorIndex) {
		getAttribute().set(NAME_CURSOR_INDEX, cursorIndex);
	}
	
	public int getSelectCount() {
		return getAttribute().get(NAME_SELECT_COUNT, 0, true);
	}
	
	public void setSelectCount(int selectCount) {
		getAttribute().set(NAME_SELECT_COUNT, selectCount);
	}
	
	public Color getSelectForegroundColor() {
		Attribute baseAttribute = getOwnerScreen().getGui().getStyle().getBaseAttribute();
		return getAttribute().get(NAME_SELECT_FOREGROUND_COLOR, baseAttribute.get(Style.BACKGROUND_COLOR, Color.BLACK, false), true);
	}
	
	public void setSelectForegroundColor(Color selectForegroundColor) {
		getAttribute().set(NAME_SELECT_FOREGROUND_COLOR, selectForegroundColor);
	}
	
	public Color getSelectBackgroundColor() {
		Attribute baseAttribute = getOwnerScreen().getGui().getStyle().getBaseAttribute();
		return getAttribute().get(NAME_SELECT_BACKGROUND_COLOR, baseAttribute.get(Style.FOREGROUND_COLOR, Color.WHITE, false), true);
	}
	
	public void setSelectBackgroundColor(Color selectBackgroundColor) {
		getAttribute().set(NAME_SELECT_BACKGROUND_COLOR, selectBackgroundColor);
	}
	
	public int getCursorTwinkleFrame() {
		return getAttribute().get(NAME_CURSOR_TWINKLE_FRAME, 15, true);
	}
	
	public void setCursorTwinkleFrame(int cursorTwinkleFrame) {
		getAttribute().set(NAME_CURSOR_TWINKLE_FRAME, cursorTwinkleFrame);
	}
	
	private Align getTextAlign() {
		return getAttribute().get(NAME_TEXT_ALIGN, Align.Left, true);
	}
	
}
