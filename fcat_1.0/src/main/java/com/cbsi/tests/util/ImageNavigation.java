package com.cbsi.tests.util;

import java.io.File;
import java.io.IOException;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.Screen;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.robot.desktop.DesktopScreen;

public class ImageNavigation {
	
	private static Keyboard keyboard = new DesktopKeyboard();
	private static Mouse mouse = new DesktopMouse();
	
	private ScreenRegion screenRegion= null;
	
	public ImageNavigation(){
		
	}
	
	String ImageResources = "src/main/resources/ImageResources/";
	public void clickImageTarget(String fileName){
		File file = new File(ImageResources + fileName + ".png");
	
		System.out.println(file.getAbsolutePath());
		Target target = new ImageTarget(file);
		target.setMinScore(0.6);
		screenRegion = new DesktopScreenRegion();
		screenRegion.wait(target, 10);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ScreenLocation srl = screenRegion.find(target).getCenter();
		mouse.click(srl);
		mouse.click(srl);
		
	}
	
	public void clickOk(){
		clickImageTarget("OK");
	}

	
}
