package com.woernerj.dragonsdogma.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.woernerj.dragonsdogma.SteamAccountManager;

public class DragonsDogmaSaveManager extends JFrame {

	public static final int DD_GAME_ID = 367500;
	public static final String DEFAULT_DATA_STORE = System.getProperty("user.home") + File.separator + ".ddsavemanager";
	public static final String APP_TITLE = "Dragons Dogma Save Manager";
	
	private static final long serialVersionUID = -6440877608302929783L;
	private static final Logger LOG = LogManager.getLogger(DragonsDogmaSaveManager.class);
	private static final String APP_TITLE_USER = " - %s";
	
	private String steamPath;
	private String saveDataLocation;
	private Map<String, Integer> steamAccounts;
	private String activeUser;
	private List<CharacterInfoPanel> storedPlayerInfos;
	
	private JPanel activeSavePanel;
	private JPanel otherSavesPanel;

	public DragonsDogmaSaveManager() {
		this.storedPlayerInfos = new ArrayList<>();
		
		setLookAndFeel();
		getSteamAccounts();
		setupGui();
	}
		
	public void locateSaves(Integer activeUser) {
		File userSaveFolder = new File(this.steamPath, "userdata");
		userSaveFolder = new File(userSaveFolder, Integer.toString(activeUser));
		File gameFolder = new File(userSaveFolder, Integer.toString(DD_GAME_ID));
		File saveFolder = new File(gameFolder, "remote");
		if (!saveFolder.exists()) {
			LOG.error("No Dragons Dogma save data folder found");
			return;
		}
		Optional<File> activeSave = Arrays.stream(saveFolder.listFiles())
			.filter(f -> f.getName().equalsIgnoreCase("ddda.sav"))
			.findFirst();
		
		List<File> storedSaves = new ArrayList<>();
		Preferences prefs = Preferences.userNodeForPackage(DragonsDogmaSaveManager.class);
		this.saveDataLocation = prefs.get("savelocation", null);
		if (StringUtils.isBlank(this.saveDataLocation)) {
			this.saveDataLocation = DEFAULT_DATA_STORE;
			prefs.put("savelocation", this.saveDataLocation);
		}
		
		File dataStore = new File(this.saveDataLocation);
		if (!dataStore.exists() && !dataStore.mkdirs()) {
			LOG.error("Failed to create data store");
		}
		File saveStore = new File(dataStore, "saves");
		if (!saveStore.exists() && !saveStore.mkdirs()) {
			LOG.error("Failed to create save store");
		}
		
		storedSaves = Arrays.stream(saveStore.list(FileFileFilter.FILE))
				.parallel()
				.filter(s -> StringUtils.isNotBlank(s))
				.map(s -> new File(s))
				.filter(f -> f.exists())
				.collect(Collectors.toList());
		
		// Generate the list of player info panels
		this.storedPlayerInfos = storedSaves
				.stream()
				.map(f -> new CharacterInfoPanel(this, f.toPath()))
				.collect(Collectors.toList());
		activeSave.ifPresent(file -> {
			this.storedPlayerInfos.add(new CharacterInfoPanel(this, file.toPath()));
		});
		
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			LOG.warn("Failed to save perferences", e);
		}
		
		updateGui();
	}
	
	private void setupGui() {
		this.setSize(500, 200);
		this.setTitle(APP_TITLE);
		this.setLayout(new GridBagLayout());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.ipady = 100;
		
		this.activeSavePanel = new JPanel();
		this.activeSavePanel.setLayout(new BorderLayout());
		this.activeSavePanel.setBackground(Color.BLUE);
		
		JLabel activeLabel = new JLabel("Active Save:");
		this.activeSavePanel.add(activeLabel);
		
		this.otherSavesPanel = new JPanel();
		this.otherSavesPanel.setLayout(new BorderLayout());
		this.otherSavesPanel.setBackground(Color.GREEN);
		
		JLabel otherSavesLabel = new JLabel("Managed Saves:");
		this.otherSavesPanel.add(otherSavesLabel);
		
		JScrollPane innerScroller = new JScrollPane(otherSavesPanel);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu userMenu = new JMenu("User");
		
		JMenuItem lastItem = null;
		for (String userAccount : steamAccounts.keySet()) {
			JMenuItem tmpItem = new JMenuItem(userAccount);
			tmpItem.addActionListener(action -> {
				String selectedUser = ((JMenuItem)action.getSource()).getText();
				if (StringUtils.equals(activeUser, selectedUser)) return;
				
				this.activeUser = selectedUser;
				this.setTitle(String.format(APP_TITLE + APP_TITLE_USER, this.activeUser));
				locateSaves(this.steamAccounts.get(this.activeUser));
				updateGui();
			});
			lastItem = tmpItem;
			userMenu.add(tmpItem);
		}
		lastItem.doClick();
		menuBar.add(userMenu);
		this.setJMenuBar(menuBar);
		
		this.add(this.activeSavePanel, constraints);
		
		constraints.gridy = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weighty = 1;
		this.add(innerScroller, constraints);
		
		this.setVisible(true);
	}
	
	public void updateGui() {
		this.activeSavePanel.removeAll();
		this.otherSavesPanel.removeAll();
		for (CharacterInfoPanel infoPanel : this.storedPlayerInfos) {
			if (infoPanel.isActiveSave()) {
				this.activeSavePanel.add(infoPanel);
			} else {
				this.otherSavesPanel.add(infoPanel);
			}
		}
		
		this.revalidate();
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
	
	private void getSteamAccounts() {
		SteamAccountManager accountManager = new SteamAccountManager();
		this.steamAccounts = accountManager.getSteamAccounts();
		this.steamPath = accountManager.getSteamPath();
	}
	
	public static void main(String...args) {
		SwingUtilities.invokeLater(() -> {
			new DragonsDogmaSaveManager();
		});
	}
}
