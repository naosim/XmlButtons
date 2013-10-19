package com.naosim.flatbutton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

import com.naosim.flatbutton.CustomFlatButtonDrawable.ColorSet;

public class FlatButton extends Button {
	public static final String NAME_SPACE = "http://naosim.com/naosim";
	public static final String ATTR_STYLE_COLOR = "style_color";
	private static final int COLOR_NOT_FOUND = Color.WHITE + 1; // out of 255

	private BackgroundFactory mBackgroundFactory;
	private TextColorFactory mTextColorFactory;
	private int mStyleColor = -1;

	public FlatButton(Context context) {
		super(context);
	}
	
	public FlatButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		int styleColor = attrs.getAttributeIntValue(NAME_SPACE, ATTR_STYLE_COLOR, COLOR_NOT_FOUND);
		if(styleColor != COLOR_NOT_FOUND) {
			setStyleColor(styleColor);
		}
	}
	
	@SuppressWarnings("deprecation")// for setBackgroundDrawable
	public void setStyleColor(int color) {
		if(color == COLOR_NOT_FOUND) return;
		
		mStyleColor = color;
		
		// background
		if(mBackgroundFactory == null) {
			mBackgroundFactory = new FlatBackgroundFactory();
		}
		setBackgroundDrawable(mBackgroundFactory.create(mStyleColor));
		
		// text
		if(mTextColorFactory == null) {
			mTextColorFactory = new FlatTextColorFactory();
		}
		setTextColor(mTextColorFactory.create(mStyleColor));
	}
	
	public void setBackgroundFactory(BackgroundFactory backgroundFactory) {
		mBackgroundFactory  = backgroundFactory;
		setStyleColor(mStyleColor);
	}
	
	interface BackgroundFactory {
		Drawable create(int styleColor);
	}
	
	interface TextColorFactory {
		ColorStateList create(int styleColor);
	}
	
	public static class FlatBackgroundFactory implements BackgroundFactory {

		@Override
		public Drawable create(int baseColor) {
			int highlight = ColorUtil.createHighLight(baseColor, 24);
			int dark = ColorUtil.createDark(baseColor, 24);
			
			ColorSet normal = createColorSet(baseColor);
			ColorSet pressed = createColorSet(highlight);
			ColorSet disable = createColorSet(dark);
			
			return ButtonStateFactory.createDrawable(new CustomFlatButtonDrawable(normal), new CustomFlatButtonDrawable(pressed), new CustomFlatButtonDrawable(disable));
		}
		
		public static ColorSet createColorSet(int baseColor) {
			int highlight = ColorUtil.createHighLight(baseColor, 24);
			int dark = ColorUtil.createDark(baseColor, 24);
			
			return new ColorSet(baseColor, highlight, highlight, dark, dark);
		}
	}
	
	public static class FlatTextColorFactory implements TextColorFactory{

		@Override
		public ColorStateList create(int styleColor) {
			int pressed = ColorUtil.createDark(styleColor, 48);
			int normal = ColorUtil.createDark(pressed, 48);
			int disable = ColorUtil.createDark(normal, 48);
			
			return ButtonStateFactory.createColor(normal, pressed, disable);
		}
		
	}
	
	public static class ColorUtil {
		public static int createHighLight(int baseColor, int diff) {
			return Color.rgb(in256(Color.red(baseColor) + diff), in256(Color.green(baseColor) + diff), in256(Color.blue(baseColor) + diff));
		}
		
		public static int createDark(int baseColor, int diff) {
			return createHighLight(baseColor, -diff);
		}
		
		public static int in256(int value) {
			return Math.min(Math.max(value, 0), 255);
		}
	}

}
