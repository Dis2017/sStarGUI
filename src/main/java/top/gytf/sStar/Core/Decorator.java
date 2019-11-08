package top.gytf.sStar.Core;

import top.gytf.sStar.Input.KeyEvent;
import top.gytf.sStar.Input.MouseEvent;
import top.gytf.sStar.Other.Bounds;

import java.awt.*;

/**
 * @Author Dis
 * @create 2019/11/2 12:47
 * @project sStarGUI
 * @describe 装饰器
 */
public interface Decorator {
	
	void onLoad(View view);
	Bounds getBounds();
	void draw(Graphics2D graphics);
	boolean handleMouseEvent(MouseEvent event);
	void handleKeyEvent(KeyEvent event);
	void onUnload();
	
}
