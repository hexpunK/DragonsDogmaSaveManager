package com.woernerj.dragonsdogma.gui;

import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.woernerj.dragonsdogma.SteamAccountManager;

public class DragonsDogmaSaveManager extends JFrame {

	private static final long serialVersionUID = -6440877608302929783L;
	private static final Logger LOG = LogManager.getLogger(DragonsDogmaSaveManager.class);
	private static final int DD_GAME_ID = 367500;
	
	private Map<Integer, String> steamAccounts;

	public DragonsDogmaSaveManager() {
		setLookAndFeel();
		SteamAccountManager accountManager = new SteamAccountManager();
		this.steamAccounts = accountManager.getSteamAccounts();
		setupGui();
	}
	
	private void setupGui() {
		this.setSize(500, 200);
		this.setTitle("Dragons Dogma Save Manager");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (IllegalAccessException | ClassNotFoundException 
				| InstantiationException | UnsupportedLookAndFeelException ex) 
		{
			LOG.warn("Could not set system look and feel", ex);
		}
	}
	
	public static void main(String...args) {
		SwingUtilities.invokeLater(() -> {
			DragonsDogmaSaveManager main = new DragonsDogmaSaveManager();
			main.setVisible(true);
		});
	}
}
