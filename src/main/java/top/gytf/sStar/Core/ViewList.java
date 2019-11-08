package top.gytf.sStar.Core;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @Author Dis
 * @create 2019/10/26 9:27
 * @project sStarGUI
 * @describe 一组视图
 */
public class ViewList extends ArrayList<View> {
	
	static final String tag = "ViewList";
	
	private ViewGroup mViewGroup;
	
	ViewList(ViewGroup viewGroup) {
		mViewGroup = viewGroup;
	}
	
	@Override
	public boolean add(View view) {
		synchronized (tag) {
			boolean r = super.add(view);
			if (r) {
				mViewGroup.setMeasured(false);
			}
			return r;
		}
	}
	
	@Override
	public void add(int index, View element) {
		synchronized (tag) {
			super.add(index, element);
			mViewGroup.setMeasured(false);
		}
	}
	
	@Override
	public boolean remove(Object o) {
		synchronized (tag) {
			boolean r = super.remove(o);
			if (r) {
				mViewGroup.setMeasured(false);
			}
			return r;
		}
	}
	
	public <T extends View> T findViewByID(String id, Class<T> tClass) {
		if (id == null) {
			return null;
		}
		synchronized (tag) {
			for (View v : this) {
				if (v.getID().equals(id)) {
					return (T) v;
				}
			}
		}
		return null;
	}
	
	public View getViewByPosition(int x, int y) {
		synchronized (tag) {
			View view = null;
			for (View view1 : this) {
				if (Objects.requireNonNull(view1).getBounds().inBounds(x, y)) {
					view = view1;
					break;
				}
			}
			return view;
		}
	}
	
	@Override
	public void clear() {
		synchronized (tag) {
			super.clear();
			mViewGroup.setMeasured(false);
		}
	}
	
}
