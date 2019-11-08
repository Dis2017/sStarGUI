package top.gytf.sStar.Core;

import java.awt.image.BufferedImage;

/**
 * @Author Dis
 * @create 2019/10/25 16:43
 * @project sStarGUI
 * @describe 绘制的地方（暴露给使用者的接口，用于将结果图像输出）
 */
public interface Canvas {
	
	//绘制时（img是结果图像）
	void onDraw(BufferedImage img);
	int getWidth();
	int getHeight();
	
}
