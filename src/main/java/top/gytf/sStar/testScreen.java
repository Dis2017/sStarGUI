package top.gytf.sStar;

import top.gytf.sStar.Core.*;
import top.gytf.sStar.Decorator.Border;
import top.gytf.sStar.Input.MouseEvent;
import top.gytf.sStar.Layout.FloatLayout;
import top.gytf.sStar.Layout.LinearLayout;
import top.gytf.sStar.Other.*;
import top.gytf.sStar.View.ButtonView;
import top.gytf.sStar.View.ImageView;
import top.gytf.sStar.View.TextField;
import top.gytf.sStar.View.TextView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;

/**
 * @Author Dis
 * @create 2019/10/25 17:51
 * @project sStarGUI
 * @describe
 */
public class testScreen extends Screen {
	
	private static final String tag = "testScreen";
	
	public testScreen(sStarGui gui) {
		super(gui);
	}
	
	@Override
	protected void onStart() {
		getTitleBar().getTitleTv().setString("Hello screen(你好)");
		getTitleBar().getTitleTv().setForegroundColor(Color.MAGENTA);
//		getTitleBar().getTitleTv();
//		getTitleBar().setVisible(false);
		setSize((int) (getGui().getCanvas().getHeight() * 0.8f), (int) (getGui().getCanvas().getHeight() * 0.8f));
		
		FloatLayout floatLayout = new FloatLayout("", this);
		floatLayout.setBackgroundColor(new Color(0, 0, 0, 0));
		floatLayout.setWidthMeasureMode(MeasureMode.fill_parent);
		floatLayout.setHeightMeasureMode(MeasureMode.fill_parent);
		floatLayout.setMarginBounds(new Bounds(5, 5, 5, 5));
		floatLayout.setPaddingBounds(new Bounds(5, 5, 5, 5));
//		floatLayout.setBackgroundColor(new Color(255, 255, 0, 168));
		
//		Align[][] vAlign = new Align[][] {
//				{Align.Top, Align.Top, Align.Top},
//				{Align.Center, Align.Center, Align.Center},
//				{Align.Bottom, Align.Bottom, Align.Bottom}
//		};
//		Align[][] hAlign = new Align[][] {
//				{Align.Left, Align.Center, Align.Right},
//				{Align.Left, Align.Center, Align.Right},
//				{Align.Left, Align.Center, Align.Right}
//		};
//
//		ButtonView buttonView;
//		for (int y = 0; y < 3; y++) {
//			for (int x = 0; x < 3; x++) {
//				buttonView = new ButtonView("tv" + x + ", " + y, this);
//				buttonView.setString(x + ", " + y);
//				buttonView.getAttribute().set(FloatLayout.NAME_VERTICAL_ALIGN, vAlign[y][x]);
//				buttonView.getAttribute().set(FloatLayout.NAME_HORIZONTAL_ALIGN, hAlign[y][x]);
//				buttonView.setMarginBounds(new Bounds(5, 5, 5, 5));
//				buttonView.setColor(ButtonView.WHAT_PRESS, Color.RED);
//				try {
//					buttonView.setImage(ButtonView.WHAT_NORMAL, ImageIO.read(new URL("file:F:\\Projects\\Mythology\\src\\main\\resources\\btnBg_normal.png")));
//					buttonView.setImage(ButtonView.WHAT_PRESS, ImageIO.read(new URL("file:F:\\Projects\\Mythology\\src\\main\\resources\\btnBg_pressed.png")));
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
////				buttonView.setColor(ButtonView.WHAT_HOVER, Color.GREEN);
//				floatLayout.addChild(buttonView);
//			}
//		}
		
		ButtonView buttonView = new ButtonView("BTN_VIEW_1", this);
		buttonView.setString("Test");
		buttonView.getAttribute().set(FloatLayout.NAME_HORIZONTAL_ALIGN, Align.Center);
		buttonView.getAttribute().set(FloatLayout.NAME_VERTICAL_ALIGN, Align.Center);
		buttonView.setClickListener(new ClickListener() {
			@Override
			public void onClick(MouseEvent event) {
//				buttonView.setEnable(false);
			}
		});
//		try {
//			buttonView.getAttribute().set(ButtonView.NAME_NORMAL_BACKGROUND_IMAGE, ImageIO.read(new URL("file:F:\\Projects\\Mythology\\src\\main\\resources\\btnBg_normal.png")));
//			buttonView.getAttribute().set(ButtonView.NAME_PRESS_BACKGROUND_IMAGE, ImageIO.read(new URL("file:F:\\Projects\\Mythology\\src\\main\\resources\\btnBg_pressed.png")));
//			buttonView.getAttribute().set(ButtonView.NAME_DISABLE_BACKGROUND_IMAGE, ImageIO.read(new URL("file:F:\\Projects\\Mythology\\src\\main\\resources\\btnBg_disable.png")));
//			buttonView.refresh();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		floatLayout.addChild(buttonView);
		
		ImageView imageView = new ImageView("ImageView1", this);
		imageView.getAttribute().set(FloatLayout.NAME_VERTICAL_ALIGN, Align.Top);
		imageView.getAttribute().set(FloatLayout.NAME_HORIZONTAL_ALIGN, Align.Left);
		imageView.setPresetWidth(200);
		imageView.setPresetHeight(150);
		imageView.setScaleMode(ScaleMode.Adapt);
		try {
			imageView.setImage(ImageIO.read(new URL("file:F:\\Projects\\Mythology\\src\\main\\resources\\mainBg.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		floatLayout.addChild(imageView);
		
		TextField textField = new TextField("TextField1", this);
		textField.getAttribute().set(FloatLayout.NAME_VERTICAL_ALIGN, Align.Bottom);
		textField.getAttribute().set(FloatLayout.NAME_HORIZONTAL_ALIGN, Align.Left);
		floatLayout.addChild(textField);
		
		setContentView(floatLayout);
	}
	
	@Override
	protected void onStop() {
	}
	
	@Override
	protected void onFocus() {
	
	}
	
}
