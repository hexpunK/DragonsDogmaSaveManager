package com.woernerj.dragonsdogma.gui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	private static final Logger LOG = LogManager.getLogger(Main.class);

	public static void main(String... args) {
		setupProperties();
		SwingUtilities.invokeLater(() -> {
			BaseFrame mainWindow = BaseFrame.getInstance();
			mainWindow.setVisible(true);
		});
	}

	private static void setupProperties() {
		try {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", BaseFrame.WINDOW_DOCK_TITLE);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException e) {
			LOG.error("Could not get the system look and feel class name", e);
		}
		catch (InstantiationException | IllegalAccessException e) {
			LOG.catching(e);
		}
		catch (UnsupportedLookAndFeelException e) {
			LOG.error("Look and feel not supported by this platform", e);
		}
	}
}
