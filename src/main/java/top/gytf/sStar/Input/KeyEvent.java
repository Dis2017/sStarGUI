package top.gytf.sStar.Input;

/**
 * @Author Dis
 * @create 2019/11/8 21:38
 * @project sStarGUI
 * @describe
 */
public class KeyEvent {
	
	private static final String tag = "KeyEvent";
	
	public static final int STATE_DOWN = 0;
	public static final int STATE_UP = 1;
	public static final int STATE_TYPED = 2;
	
	private java.awt.event.KeyEvent mKeyEvent;
	private int mState;
	
	public KeyEvent(java.awt.event.KeyEvent keyEvent, int state) {
		mKeyEvent = keyEvent;
		this.mState = state;
	}
	
	public java.awt.event.KeyEvent getKeyEvent() {
		return mKeyEvent;
	}
	
	public void setKeyEvent(java.awt.event.KeyEvent keyEvent) {
		mKeyEvent = keyEvent;
	}
	
	public int getState() {
		return mState;
	}
	
}
