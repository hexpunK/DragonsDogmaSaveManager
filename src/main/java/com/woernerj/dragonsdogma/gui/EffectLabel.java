package com.woernerj.dragonsdogma.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.EnumSet;

import javax.swing.JLabel;

import org.apache.commons.lang3.tuple.Pair;

public class EffectLabel extends JLabel {
	
	private static final long serialVersionUID = -6453295869737365704L;

	public static enum Effects {
		OUTLINE,
		GRADIENT_FILL,
		TRANSPARENT_FILL,
		IMAGE_BACKGROUND,
		IMAGE_FILL,
		;
	}
	
	private EnumSet<Effects> effects;
	private Pair<Float, Float> gradientStart;
	private Pair<Float, Float> gradientEnd;
	private Color gradientStartColor;
	private Color gradientEndColor;
	private boolean gradientCyclic;
	private float outlineSize;
	private Color outlineColor;
	private Image backgroundImage;
	private Image fillImage;
	private float alpha;
	
	public EffectLabel() {
		super();
		this.effects = EnumSet.noneOf(Effects.class);
		this.gradientStart = Pair.of(0f, 0f);
		this.gradientEnd = Pair.of(1f, 1f);
		this.gradientStartColor = Color.WHITE;
		this.gradientEndColor = Color.BLACK;
		this.gradientCyclic = false;
		this.outlineSize = 0f;
		this.outlineColor = Color.BLACK;
		this.alpha = 1f;
	}
	
	@Override
	public String getName() {
		return "EffectLabel";
	}
	
	public void enableEffect(Effects...effect) {
		this.effects.addAll(Arrays.asList(effect));
	}
	
	public void disableEffect(Effects...effect) {
		this.effects.removeAll(Arrays.asList(effect));
	}
	
	public void setGradientStart(float x, float y) {
		this.gradientStart = Pair.of(x, y);
	}
	
	public void setGradientEnd(float x, float y) {
		this.gradientEnd = Pair.of(x, y);
	}
	
	public void setGradientStartColor(Color gradientStartColor) {
		this.gradientStartColor = gradientStartColor;
	}
	
	public void setGradientEndColor(Color gradientEndColor) {
		this.gradientEndColor = gradientEndColor;
	}
	
	public void setGradientCyclic(boolean gradientCyclic) {
		this.gradientCyclic = gradientCyclic;
	}
	
	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}
	
	public void setOutlineSize(float outlineSize) {
		this.outlineSize = outlineSize;
	}
	
	public void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	
	public void setFillImage(Image fillImage) {
		this.fillImage = fillImage;
	}
	
	public void setAlphaLevel(float alphaLevel) {
		this.alpha = alphaLevel;
	}
	
	public Pair<Float, Float> getGradientStart() {
		return this.gradientStart;
	}
	
	public Pair<Float, Float> getGradientEnd() {
		return this.gradientEnd;
	}
	
	public Color getGradientStartColor() {
		return this.gradientStartColor;
	}
	
	public Color getGradientEndColor() {
		return this.gradientEndColor;
	}
	
	public boolean isGradientCyclic() {
		return this.gradientCyclic;
	}
	
	public Color getOutlineColor() {
		return this.outlineColor;
	}
	
	public float getOutlineSize() {
		return this.outlineSize;
	}
	
	public Image getBackgroundImage() {
		return this.backgroundImage;
	}
	
	public Image getFillImage() {
		return this.fillImage;
	}
	
	public float getAlphaLevel() {
		return this.alpha;
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D graphics = (Graphics2D)g;
		
		graphics.setRenderingHint(
	            RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (effects.contains(Effects.IMAGE_BACKGROUND)) {
			graphics.drawImage(backgroundImage, 0, 0, null);
		} else {
			graphics.setColor(getBackground());
			graphics.fillRect(0, 0, getWidth(), getHeight());
		}
		
		GlyphVector glyphs = getFont().createGlyphVector(graphics.getFontRenderContext(), getText());
		
		int offsetX = 0;
		int offsetY = 0;
		switch (getVerticalAlignment()) {
		case TOP:
			offsetY = (int) Math.floor(glyphs.getVisualBounds().getHeight());
			break;
		case CENTER:
			offsetY = (int) Math.floor(getHeight() / 2);
			break;
		case BOTTOM:
		case SOUTH:
			offsetY = (int) Math.floor(getHeight());
			break;
		}
		switch (getHorizontalAlignment()) {
		case CENTER:
			double textMidPos = glyphs.getVisualBounds().getWidth() / 2;
			double halfWidth = getWidth() / 2.0;
			offsetX = (int) Math.floor(halfWidth - textMidPos);
			break;
		case RIGHT:
		case EAST:
			offsetX = (int) Math.floor(getWidth());
			break;
		}
		g.translate(offsetX, offsetY);
		for (int i = 0; i < getText().length(); i++) {
			Shape glyphShape = glyphs.getGlyphOutline(i);
			
			if (effects.contains(Effects.GRADIENT_FILL)) {
				Pair<Float, Float> start = getGradientStart();
				Pair<Float, Float> end = getGradientStart();
				Color startCol = getGradientStartColor();
				Color endCol = getGradientEndColor();
				if (effects.contains(Effects.TRANSPARENT_FILL)) {
					startCol = new Color(startCol.getRed()/255f, startCol.getGreen()/255f, startCol.getBlue()/255f, getAlphaLevel());
					endCol = new Color(startCol.getRed()/255f, startCol.getGreen()/255f, startCol.getBlue()/255f, getAlphaLevel());
				}
				Paint p = new GradientPaint(
						(float)(glyphShape.getBounds().getWidth() * start.getLeft()), 
						(float)(glyphShape.getBounds().getHeight() * start.getRight()), 
						startCol, 
						(float)(glyphShape.getBounds().getWidth() * end.getLeft()), 
						(float)(glyphShape.getBounds().getHeight() * end.getRight()), 
						endCol, 
						isGradientCyclic());
				graphics.setPaint(p);
			} else if (effects.contains(Effects.IMAGE_FILL)) {
				int imageWidth = fillImage.getWidth(null);
				int imageHeight = fillImage.getHeight(null);
				BufferedImage img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
				Graphics2D imgG = img.createGraphics();
				imgG.drawImage(fillImage, 0, 0, null);
				graphics.setPaint(new TexturePaint(img, new Rectangle(0, 0, imageWidth, imageHeight)));
			} else {
				Color c = getForeground();
				if (effects.contains(Effects.TRANSPARENT_FILL)) {
					c = new Color(c.getRed()/255f, c.getGreen()/255f, c.getBlue()/255f, getAlphaLevel());
				}
				graphics.setPaint(c);
			}
			graphics.fill(glyphShape);
			
			if (effects.contains(Effects.OUTLINE)) {
				graphics.setColor(getOutlineColor());
				graphics.setStroke(new BasicStroke(outlineSize));
				graphics.draw(glyphShape);
			}
		}
	}
}
