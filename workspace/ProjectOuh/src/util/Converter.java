package util;

import java.awt.Color;

import com.jme3.math.ColorRGBA;

/**
 * @author Bender
 * You can add converters to this class, which you might need serval times.
 */
public class Converter {

	public static ColorRGBA ColorToColorRGBA(Color color) {
		return new ColorRGBA(color.getRed()/255f,color.getGreen()/255f, color.getBlue()/255f, color.getAlpha()/255f);
		
	}
	
}
