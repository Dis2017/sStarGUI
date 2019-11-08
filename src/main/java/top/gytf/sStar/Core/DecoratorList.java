package top.gytf.sStar.Core;

import top.gytf.sStar.Input.KeyEvent;
import top.gytf.sStar.Input.MouseEvent;
import top.gytf.sStar.Other.Bounds;

import java.awt.*;
import java.util.ArrayList;

/**
 * @Author Dis
 * @create 2019/11/2 12:49
 * @project sStarGUI
 * @describe 装饰器列表
 */
public class DecoratorList extends ArrayList<Decorator> {
	
	private static final String tag = "DecoratorList";
	
	private View mOwnerView;
	
	public DecoratorList(View view) {
		mOwnerView = view;
	}
	
	public boolean handleMouseEvent(MouseEvent event) {
		boolean use = false;
		synchronized (tag) {
			Bounds bounds;
			for (Decorator decorator : this) {
				bounds = decorator.getBounds();
				if (bounds.inBounds(event.getX(), event.getY())) {
					event.setX(event.getX() - bounds.x);
					event.setY(event.getY() - bounds.y);
					if (decorator.handleMouseEvent(event)) {
						use = true;
						break;
					}
				}
			}
		}
		return use;
	}
	
	public void handleKeyEvent(KeyEvent event) {
		synchronized (tag) {
			for (Decorator decorator : this) {
				decorator.handleKeyEvent(event);
			}
		}
	}
	
	public void drawList(Graphics graphics) {
		synchronized (tag) {
			Bounds bounds;
			for (Decorator decorator : this) {
				bounds = decorator.getBounds();
				decorator.draw((Graphics2D) graphics.create(bounds.x, bounds.y, bounds.width, bounds.height));
			}
		}
	}
	
	@Override
	public void add(int index, Decorator element) {
		synchronized (tag) {
			element.onLoad(mOwnerView);
			super.add(index, element);
		}
	}
	
	@Override
	public boolean add(Decorator decorator) {
		synchronized (tag)
		{
			decorator.onLoad(mOwnerView);
			return super.add(decorator);
		}
	}
	
	@Override
	public boolean remove(Object o) {
		synchronized (tag) {
			((Decorator) o).onUnload();
			return super.remove(o);
		}
	}
	
	@Override
	public Decorator remove(int index) {
		synchronized (tag) {
			Decorator decorator = super.remove(index);
			decorator.onUnload();
			return decorator;
		}
	}
	
	@Override
	public Decorator get(int index) {
		synchronized (tag) {
			return super.get(index);
		}
	}
	
	public <T extends Decorator> T get(Class<T> tClass) {
		synchronized (tag) {
			for (Decorator decorator : this) {
				if (decorator.getClass().equals(tClass)) {
					return (T) decorator;
				}
			}
		}
		return null;
	}
	
}
