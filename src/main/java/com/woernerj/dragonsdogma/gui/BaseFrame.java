package com.woernerj.dragonsdogma.gui;

import javax.swing.JFrame;

public class BaseFrame extends JFrame {

	public static final String WINDOW_TITLE = "Dragons Dogma Save Manager";
	public static final String WINDOW_DOCK_TITLE = "DD Save Manager";
	
	private static final long serialVersionUID = -940522937994551608L;
	
	private static BaseFrame INSTANCE;

	private BaseFrame() {
		this.setupGui();
	}
	
	public void setupGui() {
		this.setTitle(BaseFrame.WINDOW_TITLE);
		this.setSize(500, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static BaseFrame getInstance() {
		if (BaseFrame.INSTANCE == null) {
			synchronized (BaseFrame.class) {
				if (BaseFrame.INSTANCE == null) {
					BaseFrame.INSTANCE = new BaseFrame();
				}
			}
		}
		return BaseFrame.INSTANCE;
	}
}
