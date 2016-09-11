package com.woernerj.dragonsdogma.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Window.Type;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang3.tuple.Pair;

import com.woernerj.dragonsdogma.bo.types.CharacterData;
import com.woernerj.dragonsdogma.bo.types.Job;
import com.woernerj.dragonsdogma.gui.EffectLabel.Effects;

public class JobPanel extends JPanel implements MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 2674368372235108363L;
	private static final Map<Job, Image> JOB_ICONS;
	private static final Image VOCATION_LEVEL_EARNED;
	private static final Image VOCATION_LEVEL_UNEARNED;
	
	private final CharacterData characterData;
	private JDialog hoverPanel;
	
	static {
		JOB_ICONS = new HashMap<>();
		final ClassLoader classLoader = JobPanel.class.getClassLoader();
		final String filePathTemplate = "images/job_icons/%d.png";
		
		for (Job job : Job.values()) {
			String filePath = String.format(filePathTemplate, job.id);
			URL fileUrl = classLoader.getResource(filePath);
			JOB_ICONS.put(job, new ImageIcon(fileUrl).getImage());
		}
		URL vocationLevelEarnedUrl = classLoader.getResource("images/vocation_level_earned.png");
		VOCATION_LEVEL_EARNED = new ImageIcon(vocationLevelEarnedUrl).getImage();
		URL vocationLevelunEarnedUrl = classLoader.getResource("images/vocation_level_unearned.png");
		VOCATION_LEVEL_UNEARNED = new ImageIcon(vocationLevelunEarnedUrl).getImage();
	}
	
	public JobPanel(CharacterData characterData) {
		this.characterData = characterData;
		setupGui();
	}
	
	private void setupGui() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		
		Job job = characterData.getJob();
		byte jobLevel = characterData.getJobLevels().get(job);
		Pair<Long, Long> jobExperience = characterData.getJobExperience().get(job);
		
		Image jobImg = getScaledImage(JOB_ICONS.get(job), 64, 64);
		JLabel iconLabel = new JLabel(new ImageIcon(jobImg));
		iconLabel.setSize(32, 32);
		c.gridheight = 2;
		c.gridwidth = 2;
		this.add(iconLabel, c);
		
		c.gridx = GridBagConstraints.RELATIVE;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(5, 0, 5, 0);
		
		for (int i = 0; i < 10; i++) {
			Image img;
			if (i < jobLevel) {
				img = getScaledImage(VOCATION_LEVEL_EARNED, 32, 32);
				
			} else {
				img = getScaledImage(VOCATION_LEVEL_UNEARNED, 32, 32);
			}
			this.add(new JLabel(new ImageIcon(img)), c);
		}
		
		c.gridy = 1;
		c.gridx = 2;
		c.gridwidth = 5;
		
		JPanel experienceEmpty = new JPanel();
		experienceEmpty.setBackground(Color.DARK_GRAY);
		JPanel experienceFill = new JPanel();
		experienceFill.setBackground(Color.BLUE);
		if (jobExperience.getRight() <= 0) {
			this.add(experienceFill, c);
		} else {
			double diff = jobExperience.getRight().doubleValue() / jobExperience.getLeft().doubleValue();
			c.weightx = diff;
			this.add(experienceFill, c);
			c.gridx = GridBagConstraints.RELATIVE;
			c.weightx = GridBagConstraints.REMAINDER;
			this.add(experienceEmpty, c);
		}
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setVisible(true);
	}
	
	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (this.hoverPanel.isVisible()) {
			this.hoverPanel.setLocation(e.getXOnScreen() + 5, e.getYOnScreen() + 5);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Auto-generated method stub
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
		if (this.hoverPanel == null) {
			this.hoverPanel = createHoverPopup();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (this.hoverPanel != null) {
			this.hoverPanel.dispose();
			this.hoverPanel = null;
		}
	}
	
	private JDialog createHoverPopup() {
		Font iconFont = new Font("Arial", Font.BOLD, 32);
		
		JDialog newDialog = new JDialog();
		newDialog.setVisible(false);
		newDialog.setLayout(new GridBagLayout());
		newDialog.getRootPane().setBorder(new TitledBorder(null, "All Jobs", TitledBorder.CENTER, TitledBorder.ABOVE_TOP));
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		int index = 1;
		for (Job j : Job.values()) {
			Image jobImg = getScaledImage(JOB_ICONS.get(j), 64, 64);
			
			byte jobLevel = this.characterData.getJobLevels().get(j);
			EffectLabel jobLevelLabel = new EffectLabel();
			jobLevelLabel.enableEffect(Effects.IMAGE_BACKGROUND, Effects.OUTLINE);
			jobLevelLabel.setBackgroundImage(jobImg);
			jobLevelLabel.setForeground(new Color(200, 200, 200));
			jobLevelLabel.setText(String.valueOf(jobLevel));
			jobLevelLabel.setOutlineSize(2f);
			jobLevelLabel.setHorizontalAlignment(JLabel.CENTER);
			jobLevelLabel.setVerticalAlignment(JLabel.CENTER);
			jobLevelLabel.setPreferredSize(new Dimension(64, 64));
			jobLevelLabel.setFont(iconFont);
			
			newDialog.add(jobLevelLabel, c);
			
			c.gridx = index % 3;
			c.gridy = Math.floorDiv(index, 3);
			index++;
		}
		newDialog.revalidate();
		newDialog.setUndecorated(true);
		newDialog.setFocusableWindowState(false);
		newDialog.setAlwaysOnTop(true);
		newDialog.setType(Type.POPUP);
		newDialog.pack();
		newDialog.setVisible(true);
		
		return newDialog;
	}
}
