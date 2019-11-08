package top.gytf.sStar.Input;

/**
 * @Author Dis
 * @create 2019/11/1 19:01
 * @project sStarGUI
 * @describe
 */
public class MouseEvent {
	
	private static final String tag = "MouseEvent";
	
	public static final int WHAT_MOVE = 0;
	public static final int WHAT_DOWN = 1;
	public static final int WHAT_DRAG = 2;
	public static final int WHAT_UP = 3;
	public static final int WHAT_CLICK = 4;
	public static final int WHAT_WHEEL = 5;
	public static final int WHAT_ENTER = 6;
	public static final int WHAT_EXIT = 7;
	
	public static final int BUTTON_LEFT = 1;
	public static final int BUTTON_MIDDLE = 2;
	public static final int BUTTON_RIGHT = 3;
	
	private int mX, mY;
	private int mClickCount;
	private int mButton;
	private int mWhat;
	
	public MouseEvent(int what, int button, int x, int y, int clickCount) {
		mX = x;
		mY = y;
		mClickCount = clickCount;
		mButton = button;
		mWhat = what;
	}
	
	public int getClickCount() {
		return mClickCount;
	}
	
	public void setClickCount(int clickCount) {
		mClickCount = clickCount;
	}
	
	public int getY() {
		return mY;
	}
	
	public void setY(int y) {
		mY = y;
	}
	
	public int getX() {
		return mX;
	}
	
	public void setX(int x) {
		mX = x;
	}
	
	public int getWhat() {
		return mWhat;
	}
	
	public void setWhat(int what) {
		mWhat = what;
	}
	
	public int getButton() {
		return mButton;
	}
	
	@Override
	public String toString() {
		return "(" + mX + ", " + mY + "), What:" + mWhat + " Button: " + mButton;
	}
}
