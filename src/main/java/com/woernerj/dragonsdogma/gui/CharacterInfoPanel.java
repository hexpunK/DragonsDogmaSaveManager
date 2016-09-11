package com.woernerj.dragonsdogma.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.prefs.Preferences;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;

import com.woernerj.dragonsdogma.bo.CompressionProgressCallback;
import com.woernerj.dragonsdogma.bo.DDSave;
import com.woernerj.dragonsdogma.bo.SaveDataCallback;
import com.woernerj.dragonsdogma.util.DDSaveLoader;

public class CharacterInfoPanel extends JPanel implements SaveDataCallback, CompressionProgressCallback, MouseListener {

	private static final long serialVersionUID = -6989614568134516038L;
	
	private DragonsDogmaSaveManager parent;
	
	private JPanel mainPanel;
	private LoadingPanel loadingPanel;
	
	private Path saveFile;
	private int crc32;
	
	public CharacterInfoPanel(DragonsDogmaSaveManager parent, Path saveFile) {
		this.parent = parent;
		this.saveFile = saveFile;
		setupGui();
		load();
	}
	
	public void load() {
		Runnable task = () -> {
			DDSaveLoader loader = new DDSaveLoader();
			loader.setCompressionCallback(CharacterInfoPanel.this);
			loader.setSaveDataCallback(CharacterInfoPanel.this);
			try (FileInputStream fis = new FileInputStream(saveFile.toFile())) {
				loader.loadSave(fis);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		this.loadingPanel = new LoadingPanel();
		this.remove(mainPanel);
		this.add(this.loadingPanel, BorderLayout.CENTER);
		this.revalidate();
		
		new Thread(task).start();
	}
	
	public boolean isActiveSave() {
		return this.saveFile.getFileName().toString().equalsIgnoreCase("ddda.sav");
	}
	
	public boolean backupSave() {
		if (!isActiveSave()) return true;
		
		Preferences prefs = Preferences.userNodeForPackage(DragonsDogmaSaveManager.class);
		String saveDataLocation = prefs.get("savelocation", null) + "saves";
		if (StringUtils.isBlank(saveDataLocation)) {
			return false;
		}
		
		File saveLocation = new File(saveDataLocation);
		File backupFile = new File(saveLocation, String.valueOf(this.crc32));
		
		CopyOption[] copyOptions = new CopyOption[0];
		if (backupFile.exists()) {
			int answer = JOptionPane.showConfirmDialog(this, "This save has already been backed up.\nOverwrite anyway?", "File Exists", JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION) {
				// Check to see if the user wants to overwrite this file
				copyOptions = ArrayUtils.add(copyOptions, StandardCopyOption.REPLACE_EXISTING);
			} else {
				return true; // Save already backed up, whatever
			}
		}
		try {
			Files.copy(saveFile, backupFile.toPath(), copyOptions);
		} catch (IOException e) {
			return false;
		}
		
		this.saveFile = backupFile.toPath();
		return true;
	}
	
	public boolean makeActive() {
		if (isActiveSave()) return true;
		
		Preferences prefs = Preferences.userNodeForPackage(DragonsDogmaSaveManager.class);
		String saveDataLocation = prefs.get("savelocation", null) + "saves";
		if (StringUtils.isBlank(saveDataLocation)) {
			return false;
		}
		
		File saveLocation = new File(saveDataLocation);
		File activeFile = new File(saveLocation, "DDDA.SAV");
		
		CopyOption[] copyOptions = new CopyOption[0];
		if (activeFile.exists()) {
			
		}
		try {
			Files.copy(saveFile, activeFile.toPath(), copyOptions);
		} catch (IOException e) {
			return false;
		}
		
		this.saveFile = activeFile.toPath();
		return true;
	}
	
	private void setupGui() {
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(200, 100));
		
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new GridBagLayout());
		
		this.mainPanel.setVisible(true);
		this.mainPanel.setOpaque(true);
		
		this.mainPanel.addMouseListener(this);
		this.setVisible(true);
	}

	@Override
	public void update(double perc) {
		this.loadingPanel.updateText(String.format("Decompressing...\t%.2f", perc * 100.0));
	}

	@Override
	public void progressChanged(Level logLevel, String message) {
		this.loadingPanel.updateText(message);
	}
	
	@Override
	public void loadCompleted(DDSave data) {
		this.crc32 = data.getHeader().getChecksum();
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		JLabel levelLabel = new JLabel(String.valueOf(data.getPlayerData().getCharacterData().getLevel()));
		levelLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		levelLabel.setAlignmentY(JLabel.CENTER_ALIGNMENT);
		this.mainPanel.add(levelLabel, c);
		
		c.gridx = 1;
		JLabel nameLabel = new JLabel(data.getPlayerData().getEditData().getName());
		this.mainPanel.add(nameLabel, c);
		
		c.gridy = 1;
		c.weighty = GridBagConstraints.REMAINDER;
		this.mainPanel.add(new JobPanel(data.getPlayerData().getCharacterData()), c);

		this.remove(loadingPanel);
		this.add(mainPanel, BorderLayout.CENTER);
		this.revalidate();
		this.loadingPanel = null;
	}

	@Override
	public void onCompressionError(Throwable cause) {
		// TODO Auto-generated method stub
		CompressionProgressCallback.super.onCompressionError(cause);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (isActiveSave()) {
			backupSave();
		} else {
			makeActive();
		}
		parent.updateGui();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Auto-generated method stub	
	}
}
