package com.naosim.flatbutton;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

public class ButtonStateFactory {
	
	public static StateListDrawable createDrawable(Drawable normal, Drawable pressed, Drawable disable) {
		StateListDrawable d = new StateListDrawable();
		d.addState( new int[]{ -android.R.attr.state_pressed,  android.R.attr.state_enabled }, normal );
		d.addState( new int[]{  android.R.attr.state_pressed,  android.R.attr.state_enabled }, pressed );
		d.addState( new int[]{ -android.R.attr.state_pressed, -android.R.attr.state_enabled }, disable );
		return d;
	}
	
	public static ColorStateList createColor(int normalColor, int pressedColor, int disableColor) {
		ColorStateList c = new ColorStateList(new int[][] {
				new int[]{ -android.R.attr.state_pressed,  android.R.attr.state_enabled },
				new int[]{  android.R.attr.state_pressed,  android.R.attr.state_enabled },
				new int[]{ -android.R.attr.state_pressed, -android.R.attr.state_enabled }
		}, new int[]{
				normalColor, 
				pressedColor, 
				disableColor }
		);
		return c;
	}
}
