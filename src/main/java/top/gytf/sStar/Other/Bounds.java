package top.gytf.sStar.Other;

import org.w3c.dom.css.Rect;

import java.awt.*;

/**
 * @Author Dis
 * @create 2019/10/25 17:13
 * @project sStarGUI
 * @describe 表示范围
 */
public class Bounds {
	
	private static final String tag = "Bounds";
	
	public int x, y, width, height;
	
	public Bounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Bounds() {
	
	}
	
	public Bounds(Rectangle clipBounds) {
		this.x = clipBounds.x;
		this.y = clipBounds.y;
		this.width = clipBounds.width;
		this.height = clipBounds.height;
	}
	
	public boolean inBounds(int x, int y) {
		return (x >= this.x && x <= this.x + this.width) && (y >= this.y && y <= this.y + this.height);
	}
	
	public boolean isIntersect(Bounds other) {
		int thisRight = x + width;
		int thisBottom = y + height;
		int otherRight = other.x + other.width;
		int otherBottom = other.y + other.height;
		return !((thisRight < other.x) && (thisBottom > other.y) && (this.x < otherRight) && (this.y > otherBottom));
	}
	
	public int bottom() {
		return y + height;
	}
	
	public int right() {
		return x + width;
	}
	
	@Override
	public String toString() {
		return "pos(" + x + ", " + y + "), size(" + width + ", " + height + ")";
	}
}
