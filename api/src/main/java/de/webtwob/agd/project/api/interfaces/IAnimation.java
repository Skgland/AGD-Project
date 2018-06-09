package de.webtwob.agd.project.api.interfaces;

import java.awt.Graphics2D;

public interface IAnimation {
	
	/**
	 * @param frame the Frame to draw
	 * @param graphic the Graphics2D object to draw to
	 * */
	void generateFrame(long frame, @SuppressWarnings("exports") Graphics2D graphic);
	
	/**
	 * @return the length of the animation in frames
	 * */
	long getLength();
	
	double getWidth();
	
	double getHeight();

}