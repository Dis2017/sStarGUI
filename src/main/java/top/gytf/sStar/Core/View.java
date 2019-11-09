package top.gytf.sStar.Core;

import top.gytf.sStar.Input.KeyEvent;
import top.gytf.sStar.Input.KeyListener;
import top.gytf.sStar.Input.MouseEvent;
import top.gytf.sStar.Input.MouseListener;
import top.gytf.sStar.Other.Bounds;
import top.gytf.sStar.Other.MeasureMode;

import java.awt.*;
import java.util.HashMap;

/**
 * @Author Dis
 * @create 2019/10/26 8:52
 * @project sStarGUI
 * @describe 显示的最小单位
 */
public abstract class View {
	
	private static final String tag = "View";
	
//	public static final String NAME_OWNER_SCREEN = "mOwnerScreen";
//	public static final String NAME_ID = "mID";
//	public static final String NAME_BOUNDS = "mBounds";
//	//预设宽高（初始-1，大于等于0时会直接使用该值作为内容宽高）
//	public static final String NAME_PRESET_WIDTH = "mPresetWidth";
//	public static final String NAME_PRESET_HEIGHT = "mPresetHeight";
//	//测量模式（初始为fill_parent，该值会决定测量模式）
//	public static final String NAME_WIDTH_MEASURE_MODE = "mWidthMeasureMode";
//	public static final String NAME_HEIGHT_MEASURE_MODE = "mHeightMeasureMode";
//	//决定onDraw的区域
//	public static final String NAME_PADDING_BOUNDS = "mPaddingBounds";
//	//决定测量的范围
//	public static final String NAME_MARGIN_BOUNDS = "mMarginBounds";
//	//相关颜色及背景图
//	public static final String NAME_BACKGROUND_COLOR = "mBackgroundColor";
	public static final String NAME_BACKGROUND_IMAGE = "mBackgroundImage";
//	public static final String NAME_FOREGROUND_COLOR = "mForegroundColor";
//	//是否显示
//	public static final String NAME_VISIBLE = "mVisible";
//	//已测量？
//	public static final String NAME_IS_MEASURED = "mIsMeasured";
	
	private Screen mOwnerScreen;
	private String mID;
	private Bounds mBounds;
	//预设宽高（初始-1，大于等于0时会直接使用该值作为内容宽高）
	private int mPresetWidth;
	private int mPresetHeight;
	//最小宽高（无论如何的最小值）
	private int mMinWidth;
	private int mMinHeight;
	//测量模式（初始为fill_parent，该值会决定测量模式）
	private MeasureMode mWidthMeasureMode;
	private MeasureMode mHeightMeasureMode;
	//决定onDraw的区域
	private Bounds mPaddingBounds;
	//决定测量的范围
	private Bounds mMarginBounds;
	//背景
//	private Color mBackgroundColor;
//	private Image mBackgroundImage;
	//前景色
//	private Color mForegroundColor;
	//是否显示
	private boolean mVisible;
	//已测量？
	private boolean mMeasured;
	//焦点
	private boolean mFocus;
	private boolean mFocusable;
	//鼠标监听
	private MouseListener mMouseListener;
	//键盘监听
	private KeyListener mKeyListener;
	//底层装饰器列表（onDrawBackground完成后调用）
	private DecoratorList mBottomDecoratorList;
	//顶层装饰器列表（onDraw完成后调用）
	private DecoratorList mTopDecoratorList;
	private Attribute mAttribute;
	
	public View(String id, Screen ownerScreen) {
		this.mOwnerScreen = ownerScreen;
		this.mID = id;
		this.mBounds = new Bounds();
		this.mPresetWidth = -1;
		this.mPresetHeight = -1;
		this.mMinWidth = 0;
		this.mMinHeight = 0;
		this.mWidthMeasureMode = MeasureMode.fill_parent;
		this.mHeightMeasureMode = MeasureMode.fill_parent;
		this.mPaddingBounds = new Bounds();
		this.mMarginBounds = new Bounds();
//		this.mBackgroundColor = Color.WHITE;
//		this.mBackgroundImage = null;
//		this.mForegroundColor = Color.BLACK;
		this.mVisible = true;
		this.mMeasured = false;
		this.mFocus = false;
		this.mFocusable = true;
		this.mBottomDecoratorList = new DecoratorList(this);
		this.mTopDecoratorList = new DecoratorList(this);
		this.mAttribute = new Attribute(ownerScreen.getGui().getStyle().get(this.getClass()));
	}
	
	protected final void draw(Graphics graphics) {
		if (isMeasured()) {
			Graphics contentGraphics = graphics.create(getContentX(), getContentY(), getContentWidth(), getContentHeight());
			graphics.setColor(mAttribute.get(Style.BACKGROUND_COLOR, Color.BLACK, true));
			onDrawBackground(graphics);
			mBottomDecoratorList.drawList(graphics);
			graphics.setColor(mAttribute.get(Style.FOREGROUND_COLOR, Color.WHITE, true));
			onDraw(contentGraphics);
			mTopDecoratorList.drawList(contentGraphics);
		}
	}
	
	public final void measure(int maxWidth, MeasureMode fatherWidthMeasureMode, int maxHeight, MeasureMode fatherHeightMeasureMode, Graphics graphics) {
		if (!isMeasured()) {
			onMeasure(maxWidth, fatherWidthMeasureMode, maxHeight, fatherHeightMeasureMode, graphics);
			setMeasured(true);
		}
	}
	
	//处理输入
	public void handleMouseInput(MouseEvent event) {
		if (event.getWhat() == MouseEvent.WHAT_DOWN) {
			getOwnerScreen().getRootView().setFocusView(this);
		}
		if (mMouseListener != null) {
			mMouseListener.onMouseEvent(event);
		}
		if (!mTopDecoratorList.handleMouseEvent(event)) {
			mBottomDecoratorList.handleMouseEvent(event);
		}
	}
	public void handleKeyInput(KeyEvent event) {
		if (mKeyListener != null) {
			mKeyListener.onKeyEvent(event);
		}
		mTopDecoratorList.handleKeyEvent(event);
		mBottomDecoratorList.handleKeyEvent(event);
	}
	
	//测量时调用
	public void onMeasure(int maxWidth, MeasureMode fatherWidthMeasureMode, int maxHeight, MeasureMode fatherHeightMeasureMode, Graphics graphics) {
		setPosition(0, 0);
		setMeasureSize(0, maxWidth, 0, maxHeight, MeasureMode.fill_parent, MeasureMode.fill_parent);
	}
	//绘制背景
	protected void onDrawBackground(Graphics graphics) {
		Image backgroundImage = mAttribute.get(NAME_BACKGROUND_IMAGE, null, true);
		if (backgroundImage == null) {
			graphics.fillRect(0, 0, getWidth(), getHeight());
		} else {
			graphics.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
		}
	}
	
	//绘制时调用
	protected void onDraw(Graphics graphics) {
	}
	
	//焦点
	protected void onFocus() {}
	protected void onLostFocus() {}
	
	//设置位置
	public void setPosition(int x, int y) {
		Bounds bounds = getBounds();
		bounds.x = x;
		bounds.y = y;
	}
	
	//设置测量大小
	protected void setMeasureSize(int width, int maxWidth, int height, int maxHeight, MeasureMode fatherWidthMeasureMode, MeasureMode fatherHeightMeasureMode) {
		if (mMeasured) {
			return;
		}
		width = Math.min(width, maxWidth);
		height = Math.min(height, maxHeight);
		if (fatherWidthMeasureMode == MeasureMode.wrap_content) {
			setWidthMeasureMode(MeasureMode.wrap_content);
		}
		if (fatherHeightMeasureMode == MeasureMode.wrap_content) {
			setHeightMeasureMode(MeasureMode.wrap_content);
		}
		mBounds.width = Math.max(mPresetWidth >= 0 ? mPresetWidth : mWidthMeasureMode == MeasureMode.fill_parent ? maxWidth : width, mMinWidth);
		mBounds.height = Math.max(mPresetHeight >= 0 ? mPresetHeight : mHeightMeasureMode == MeasureMode.fill_parent ? maxHeight : height, mMinHeight);
	}
	
	public Screen getOwnerScreen() {
		return mOwnerScreen;
	}
	
	public String getID() {
		return mID;
	}
	
	void setId(String id) {
		mID = id;
	}
	
	public Bounds getBounds() {
		return mBounds;
	}
	
	public int getPresetWidth() {
		return mPresetWidth;
	}
	
	public int getPresetHeight() {
		return mPresetHeight;
	}
	
	public void setPresetWidth(int presetWidth) {
		mPresetWidth = presetWidth;
	}
	
	public void setPresetHeight(int presetHeight) {
		mPresetHeight = presetHeight;
	}
	
	public int getMinWidth() {
		return mMinWidth;
	}
	
	public void setMinWidth(int minWidth) {
		mMinWidth = minWidth;
	}
	
	public int getMinHeight() {
		return mMinHeight;
	}
	
	public void setMinHeight(int minHeight) {
		mMinHeight = minHeight;
	}
	
	public MeasureMode getWidthMeasureMode() {
		return mWidthMeasureMode;
	}
	
	public MeasureMode getHeightMeasureMode() {
		return mHeightMeasureMode;
	}
	
	public Bounds getPaddingBounds() {
		return mPaddingBounds;
	}
	
	public Bounds getMarginBounds() {
		return mMarginBounds;
	}
	
	public Color getBackgroundColor() {
		return mAttribute.get(Style.BACKGROUND_COLOR, Color.BLACK, true);
	}
	
	public Color getKeyColor() {
		return mAttribute.get(Style.KEY_COLOR, Color.LIGHT_GRAY, true);
	}
	
	public Image getBackgroundImage() {
		return mAttribute.get(NAME_BACKGROUND_IMAGE, null, true);
	}
	
	public void setBackgroundImage(Image backgroundImage) {
		mAttribute.set(NAME_BACKGROUND_IMAGE, backgroundImage);
	}
	
	public Color getForegroundColor() {
		return mAttribute.get(Style.FOREGROUND_COLOR, Color.WHITE, true);
	}
	
	public boolean isVisible() {
		return mVisible;
	}
	
	public void setVisible(boolean visible) {
		mVisible = visible;
	}
	
	public Attribute getAttribute() {
		return mAttribute;
	}
	
	public boolean isMeasured() {
		return mMeasured;
	}
	
	public void setMeasured(boolean measured) {
		mMeasured = measured;
	}
	
	public int getHeight() {
		return mBounds.height;
	}
	
	public int getWidth() {
		return mBounds.width;
	}
	
	public int getX() {
		return mBounds.x;
	}
	
	public int getY() {
		return mBounds.y;
	}
	
	public int getContentHeight() {
		return mBounds.height - mPaddingBounds.y - mPaddingBounds.height;
	}
	
	public int getContentWidth() {
		return mBounds.width - mPaddingBounds.x - mPaddingBounds.width;
	}
	
	public int getContentX() {
		return mPaddingBounds.x;
	}
	
	public int getContentY() {
		return mPaddingBounds.y;
	}
	
	public void setWidthMeasureMode(MeasureMode widthMeasureMode) {
		mWidthMeasureMode = widthMeasureMode;
	}
	
	public void setHeightMeasureMode(MeasureMode heightMeasureMode) {
		mHeightMeasureMode = heightMeasureMode;
	}
	
	public void setForegroundColor(Color color) {
		mAttribute.set(Style.FOREGROUND_COLOR, color);
	}
	
	public void setBackgroundColor(Color backgroundColor) {
		mAttribute.set(Style.BACKGROUND_COLOR, backgroundColor);
	}
	
	public void setKeyColor(Color keyColor) {
		mAttribute.set(Style.KEY_COLOR, keyColor);
	}
	
	public void setPaddingBounds(Bounds bounds) {
		mPaddingBounds = bounds;
	}
	
	public void setMarginBounds(Bounds bounds) {
		mMarginBounds = bounds;
	}
	
	public MouseListener getMouseListener() {
		return mMouseListener;
	}
	
	public void setMouseListener(MouseListener mouseListener) {
		mMouseListener = mouseListener;
	}
	
	public KeyListener getKeyListener() {
		return mKeyListener;
	}
	
	public void setKeyListener(KeyListener keyListener) {
		mKeyListener = keyListener;
	}
	
	public boolean addBottomDecorator(Decorator decorator) {
		return mBottomDecoratorList.add(decorator);
	}
	public void addBottomDecorator(int index, Decorator decorator) {
		mBottomDecoratorList.add(index, decorator);
	}
	public boolean removeBottomDecorator(Decorator decorator) {
		return mBottomDecoratorList.remove(decorator);
	}
	public Decorator removeBottomDecorator(int index) {
		return mBottomDecoratorList.remove(index);
	}
	public int bottomDecoratorCount() {
		return mBottomDecoratorList.size();
	}
	public <T extends Decorator> T getBottomDecorator(Class<T> cls) {
		return mBottomDecoratorList.get(cls);
	}
	public Decorator getBottomDecorator(int index) {
		return mBottomDecoratorList.get(index);
	}
	
	public boolean addTopDecorator(Decorator decorator) {
		return mTopDecoratorList.add(decorator);
	}
	public void addTopDecorator(int index, Decorator decorator) {
		mTopDecoratorList.add(index, decorator);
	}
	public boolean removeTopDecorator(Decorator decorator) {
		return mTopDecoratorList.remove(decorator);
	}
	public Decorator removeTopDecorator(int index) {
		return mTopDecoratorList.remove(index);
	}
	public int topDecoratorCount() {
		return mTopDecoratorList.size();
	}
	public <T extends Decorator> T getTopDecorator(Class<T> cls) {
		return mTopDecoratorList.get(cls);
	}
	public Decorator getTopDecorator(int index) {
		return mTopDecoratorList.get(index);
	}
	
	public boolean isFocus() {
		return mFocus;
	}
	
	public void setFocus(boolean focus) {
		if (mFocus == focus) {
			return;
		}
		mFocus = focus;
		if (mFocus) {
			onFocus();
		} else {
			onLostFocus();
		}
	}
	
	public boolean isFocusable() {
		return mFocusable;
	}
	
	public void setFocusable(boolean focusable) {
		mFocusable = focusable;
	}
}
