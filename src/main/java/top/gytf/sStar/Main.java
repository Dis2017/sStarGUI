package top.gytf.sStar;

import top.gytf.sStar.Core.sStarGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * @Author Dis
 * @create 2019/10/25 17:43
 * @project sStarGUI
 * @describe
 */
public class Main extends JFrame implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener, InputMethodListener {
	
	private static final String tag = "top.gytf.sStar.Main";
	
	private sStarGui mGui;
	private BufferedImage mImage;
	
	private Main() {
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setBounds((width - 1250) / 2, (height - 600) / 2, 1250, 600);
		this.setContentPane(new mPane());
		this.setVisible(true);
		
		this.mGui = new sStarGui(new Canvas());
		
		addMouseListener(Main.this);
		addMouseMotionListener(Main.this);
		addKeyListener(this);
		addInputMethodListener(this);
		
//		new Thread() {
//
//			@Override
//			public void run() {
//				super.run();
//				// 计算出指定FPS数值中，每帧需要多少时间
//				long fpsTime = (long) ((1000d / 60) * 1000000);
//				// 绘制图像前的时间戳
//				long now = 0;
//				// 每次绘制图像耗时（毫秒）
//				long total = 0;
//
//				while (true) {
//
//					now = System.nanoTime();
//
//					// 绘制图像
//					repaint();
//
//					try {
//						// 除去绘制之后还需要休眠的时间
//						total = System.nanoTime() - now;
////				System.out.println(total / 1000000);
//						if (total > fpsTime) {
//							// 如果本次绘制时间超过每帧需要绘制的时间，则直接继续绘制
//							continue;
//						}
//						Thread.sleep((fpsTime - total) / 1000000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//
//					while ((System.nanoTime() - now) < fpsTime) {
//						// 使用循环，精确控制每帧绘制时长
//						System.nanoTime();
//					}
//
//				}
//
//			}
//		}.start();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		top.gytf.sStar.Input.MouseEvent mouseEvent = new top.gytf.sStar.Input.MouseEvent(top.gytf.sStar.Input.MouseEvent.WHAT_CLICK,
				e.getButton(), e.getX() - getInsets().left, e.getY() - getInsets().top, e.getClickCount());
		mGui.input(mouseEvent);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		top.gytf.sStar.Input.MouseEvent mouseEvent = new top.gytf.sStar.Input.MouseEvent(top.gytf.sStar.Input.MouseEvent.WHAT_DOWN,
				e.getButton(), e.getX() - getInsets().left, e.getY() - getInsets().top, e.getClickCount());
		mGui.input(mouseEvent);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		top.gytf.sStar.Input.MouseEvent mouseEvent = new top.gytf.sStar.Input.MouseEvent(top.gytf.sStar.Input.MouseEvent.WHAT_UP,
				e.getButton(), e.getX() - getInsets().left, e.getY() - getInsets().top, e.getClickCount());
		mGui.input(mouseEvent);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		top.gytf.sStar.Input.MouseEvent mouseEvent = new top.gytf.sStar.Input.MouseEvent(top.gytf.sStar.Input.MouseEvent.WHAT_DRAG,
				e.getButton(), e.getX() - getInsets().left, e.getY() - getInsets().top, e.getClickCount());
		mGui.input(mouseEvent);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		top.gytf.sStar.Input.MouseEvent mouseEvent = new top.gytf.sStar.Input.MouseEvent(top.gytf.sStar.Input.MouseEvent.WHAT_MOVE,
				e.getButton(), e.getX() - getInsets().left, e.getY() - getInsets().top, e.getClickCount());
		mGui.input(mouseEvent);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
	
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		mGui.input(new top.gytf.sStar.Input.KeyEvent(e, top.gytf.sStar.Input.KeyEvent.STATE_TYPED));
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		mGui.input(new top.gytf.sStar.Input.KeyEvent(e, top.gytf.sStar.Input.KeyEvent.STATE_DOWN));
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		mGui.input(new top.gytf.sStar.Input.KeyEvent(e, top.gytf.sStar.Input.KeyEvent.STATE_UP));
	}
	
	@Override
	public void inputMethodTextChanged(InputMethodEvent event) {
		System.out.println("55");;
	}
	
	@Override
	public void caretPositionChanged(InputMethodEvent event) {
		System.out.println("event.paramString()");;
	
	}
	
	private class Canvas implements top.gytf.sStar.Core.Canvas {
		
		@Override
		public void onDraw(BufferedImage img) {
			mImage = img;
			repaint();
		}
		
		@Override
		public int getWidth() {
			Insets insets = Main.this.getInsets();
			return Main.this.getWidth() - insets.left - insets.right;
		}
		
		@Override
		public int getHeight() {
			Insets insets = Main.this.getInsets();
			return Main.this.getHeight() - insets.top - insets.bottom;
		}
		
	}
	
	private class mPane extends JPanel {
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			if (mImage != null) {
				g.drawImage(mImage, 0, 0, mGui.getCanvas().getWidth(), mGui.getCanvas().getHeight(), null);
			}
		}
	}
	
	public static void main(String[] args) {
		Main m = new Main();
		for (int i = 0; i < 1; i++) {
			m.mGui.startScreen(testScreen.class);
		}
	}
	
}

