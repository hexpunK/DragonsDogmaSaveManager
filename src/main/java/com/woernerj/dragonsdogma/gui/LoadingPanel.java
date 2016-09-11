package com.woernerj.dragonsdogma.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class LoadingPanel extends JPanel {

	private static final long serialVersionUID = 4961574463286209535L;
	
	private JLabel statusText;
	
	public LoadingPanel() {
		this.setLayout(new FlowLayout());
		this.statusText = new JLabel();
		this.add(statusText);
		this.setVisible(true);
		this.setOpaque(true);
		this.setBackground(Color.RED);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.setPreferredSize(new Dimension(100, 100));
	}
	
	public void updateText(String message) {
		this.statusText.setText(message);
	}
}
