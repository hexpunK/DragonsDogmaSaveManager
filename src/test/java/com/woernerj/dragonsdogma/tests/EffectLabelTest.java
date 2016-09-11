package com.woernerj.dragonsdogma.tests;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.woernerj.dragonsdogma.gui.EffectLabel;
import com.woernerj.dragonsdogma.gui.EffectLabel.Effects;

public class EffectLabelTest extends JFrame {

	private static final long serialVersionUID = 8384199791959325584L;

	public EffectLabelTest() {
		this.setLayout(new FlowLayout());
		EffectLabel label1 = new EffectLabel();
		label1.setText("qwerty");
		label1.setPreferredSize(new Dimension(128, 64));
		label1.setBackground(Color.DARK_GRAY);
		label1.setForeground(Color.WHITE);
		label1.setVerticalAlignment(JLabel.CENTER);
		label1.setHorizontalAlignment(JLabel.CENTER);
		this.add(label1);
		
		EffectLabel label2 = new EffectLabel();
		label2.setText("qwerty");
		label2.setSize(128, 32);
		label2.enableEffect(Effects.GRADIENT_FILL);
		label2.setGradientStart(0f, 0f);
		label2.setGradientEnd(1f, 1f);
		label2.setGradientStartColor(Color.RED);
		label2.setGradientEndColor(Color.BLUE);
		label2.setGradientCyclic(true);
		this.add(label2);
		
		this.setTitle("EffectLabel Tester");
		this.pack();
		this.setLocationByPlatform(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(100, 100);
	}
	
	public static void main(String...args) {
		SwingUtilities.invokeLater(() -> {
			new EffectLabelTest();
		});
	}
}
