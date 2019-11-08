package top.gytf.sStar.Core;

import top.gytf.sStar.Input.KeyEvent;
import top.gytf.sStar.Input.MouseEvent;
import top.gytf.sStar.Style.DefaultStyle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Dis
 * @create 2019/10/25 16:36
 * @project sStarGUI
 * @describe 管控整个GUI系统
 */
public final class sStarGui extends Thread {
	
	private static final String tag = "sStarGui";
	
	private BufferedImage mResultImage;
	private Canvas mCanvas;
	private List<Screen> mScreenList;
	private Style mStyle;
	private int mFPS;
	private boolean mRunning;
	private int mScreenShowX, mScreenShowY;
	
	public sStarGui(Canvas canvas) throws NullPointerException {
		if (canvas == null) {
			throw new NullPointerException("Canvas is null");
		}
		
		this.mCanvas = canvas;
		this.mScreenList = new ArrayList<>();
		this.mStyle = new DefaultStyle();
		this.mFPS = 60;
		this.mScreenShowX = this.mScreenShowY = 0;
		start();
	}
	
	@Override
	public void run() {
		super.run();
		
		// 计算出指定FPS数值中，每帧需要多少时间
		long fpsTime = (long) ((1000d / mFPS) * 1000000);
		// 绘制图像前的时间戳
		long now = 0;
		// 每次绘制图像耗时（毫秒）
		long total = 0;
		
		mRunning = true;
		
		while (mRunning) {
			
			now = System.nanoTime();
			
			// 绘制图像
			repaint();
			
			try {
				// 除去绘制之后还需要休眠的时间
				total = System.nanoTime() - now;
//				System.out.println(total / 1000000);
				if (total > fpsTime) {
					// 如果本次绘制时间超过每帧需要绘制的时间，则直接继续绘制
					continue;
				}
				Thread.sleep((fpsTime - total) / 1000000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			while ((System.nanoTime() - now) < fpsTime) {
				// 使用循环，精确控制每帧绘制时长
				System.nanoTime();
			}
			
		}
		
	}
	
	//启动窗口
	public void startScreen(Class<? extends Screen> screenCls) {
		startScreenSetPosition(screenCls, mScreenShowX + (int) (getCanvas().getWidth() * 0.1f), mScreenShowY + (int) (getCanvas().getHeight() * 0.1f));
		offsetScreenDefaultPosition();
	}
	public void startScreenSetPosition(Class<? extends Screen> screenCls, int x, int y) {
		startScreen(screenCls, x, y, getCanvas().getWidth() / 3, getCanvas().getHeight() / 2);
	}
	public void startScreenSetSize(Class<? extends Screen> screenCls, int width, int height) {
		startScreen(screenCls, mScreenShowX + (int) (getCanvas().getWidth() * 0.1f), mScreenShowY + (int) (getCanvas().getHeight() * 0.1f), width, height);
		offsetScreenDefaultPosition();
	}
	public void startScreen(Class<? extends Screen> screenCls, int x, int y, int width, int height) {
		Screen screen = null;
		try {
			screen = screenCls.getConstructor(sStarGui.class).newInstance(this);
		} catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
			e.printStackTrace();
			return;
		}
		screen.setPosition(x, y);
		screen.setSize(width, height);
		addScreenToTop(screen);
		screen.onStart();
		screen.onFocus();
		repaint();
	}
	
	//偏移窗口默认位置
	private void offsetScreenDefaultPosition() {
		mScreenShowX += 20;
		mScreenShowY += 20;
		if (mScreenShowX >= (int) (mCanvas.getWidth() * 0.7f) || mScreenShowY >= (int) (mCanvas.getHeight() * 0.7f)) {
			mScreenShowX = 0;
			mScreenShowY = 0;
		}
	}
	
	//停止窗口
	void stopScreen(Screen screen) {
		if (removeScreen(screen)) {
			screen.onStop();
			repaint();
		}
	}
	
	//绘制结果图像并向Canvas输出
	void repaint() {
		int width = mCanvas.getWidth();
		int height = mCanvas.getHeight();
		
		if (mResultImage == null || mResultImage.getWidth() != width || mResultImage.getHeight() != height) {
			mResultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		}
		
		Graphics graphics = mResultImage.getGraphics();
		graphics.clearRect(0, 0, width, height);
		synchronized (tag) {
			for (int i = mScreenList.size() - 1; i >= 0; i--) {
				Screen screen = mScreenList.get(i);
				screen.draw(graphics.create(screen.getX(), screen.getY(), screen.getWidth(), screen.getHeight()));
			}
		}
		
		mCanvas.onDraw(mResultImage);
	}
	
	//输入鼠标
	public void input(MouseEvent event) {
		synchronized (tag) {
			for (int i = 0; i < mScreenList.size(); i++) {
				Screen screen = mScreenList.get(i);
				if (screen.getBounds().inBounds(event.getX(), event.getY())) {
					if (event.getWhat() == MouseEvent.WHAT_DOWN && event.getButton() == MouseEvent.BUTTON_LEFT) {
						mScreenList.remove(i);
						mScreenList.add(0, screen);
						screen.onFocus();
					}
					event.setX(event.getX() - screen.getX());
					event.setY(event.getY() - screen.getY());
					screen.handleMouseInput(event);
					break;
				}
			}
		}
	}
	
	public void input(KeyEvent event) {
		synchronized (tag) {
			Screen screen = getScreenFromTop();
			if (screen != null) {
				screen.handleKeyInput(event);
			}
		}
	}
	
	//获取窗口
	private Screen getScreenFromTop() {
		if (mScreenList.size() == 0) {
			return null;
		}
		synchronized (tag) {
			return mScreenList.get(0);
		}
	}
	
	//添加窗口
	private void addScreen(Screen screen, int index) {
		if (screen == null || index < 0 || index > mScreenList.size()) {
			return;
		}
		synchronized (tag) {
			if (mScreenList.contains(screen)) {
				return;
			}
			mScreenList.add(index, screen);
		}
	}
	
	private void addScreenToTop(Screen screen) {
		addScreen(screen, 0);
	}
	
	private void addScreen(Screen screen) {
		addScreen(screen, mScreenList.size());
	}
	
	//移除窗口
	private boolean removeScreen(Screen screen) {
		if (screen == null) {
			return false;
		}
		synchronized (tag) {
			return mScreenList.remove(screen);
		}
	}
	
	private void removeScreen(int index) {
		if (index < 0 || index >= mScreenList.size()) {
			return;
		}
		synchronized (tag) {
			mScreenList.remove(index);
		}
	}
	
	private void removeScreenFromTop() {
		if (mScreenList.size() == 0) {
			return;
		}
		synchronized (tag) {
			mScreenList.remove(0);
		}
	}
	
	//提供Canvas
	public Canvas getCanvas() {
		return mCanvas;
	}
	
	public int getFPS() {
		return mFPS;
	}
	
	public void setFPS(int FPS) {
		mFPS = FPS;
	}
	
	public boolean isRunning() {
		return mRunning;
	}
	
	public void postStop() {
		mRunning = false;
	}
	
	public Style getStyle() {
		return mStyle;
	}
	
}