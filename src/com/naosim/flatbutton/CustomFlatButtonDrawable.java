package com.naosim.flatbutton;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;

public class CustomFlatButtonDrawable extends Drawable {
	private PaintSet mPaintSet;
	public CustomFlatButtonDrawable(ColorSet colorSet) {
		mPaintSet = new PaintSet(colorSet);
	}
	@Override
	public void draw(Canvas canvas) {
		// background
		drawRoundRect(canvas, mPaintSet.backgroundPaint);
		
		// left
		clipLeftSide(canvas);
		drawRoundRect(canvas, mPaintSet.leftBorderPaint);
		
		//right
		clipRightSide(canvas);
		drawRoundRect(canvas, mPaintSet.rightBorderPaint);
		
		// top
		clipBottom(canvas);
		drawRoundRect(canvas, mPaintSet.bottomBorderPaint);
		
		// bottom
		clipTop(canvas);
		drawRoundRect(canvas, mPaintSet.topBorderPaint);
		clearClip(canvas);
	}
	
	private static final int PADDING = 1;
	private static final int RADIUS = 2;
	public static void drawRoundRect(Canvas canvas, Paint paint) {
		canvas.drawRoundRect(
				new RectF(PADDING, PADDING, canvas.getWidth() - PADDING, canvas.getHeight() - PADDING), RADIUS, RADIUS, paint);
	}
	
	public void clearClip(Canvas canvas) {
		canvas.clipRect(new Rect(0,  0, canvas.getWidth(), canvas.getHeight()), Op.REPLACE);
	}
	
	public void clipTop(Canvas canvas) {
		clearClip(canvas);
		Rect r = new Rect(
				1, 
				0, 
				canvas.getWidth() - 1,
				3);
		canvas.clipRect(r);
	}
	
	public void clipBottom(Canvas canvas) {
		clearClip(canvas);
		Rect r = new Rect(
				1, 
				canvas.getHeight() - 2, 
				canvas.getWidth() - 1,
				canvas.getHeight());
		canvas.clipRect(r);
	}
	
	public void clipLeftSide(Canvas canvas) {
		clearClip(canvas);
		Rect r = new Rect(
				0, 
				2, 
				2,
				canvas.getHeight() - 2);
		canvas.clipRect(r);
	}
	
	public void clipRightSide(Canvas canvas) {
		clearClip(canvas);
		Rect r = new Rect(
				canvas.getWidth() - 2, 
				2, 
				canvas.getWidth(),
				canvas.getHeight() - 2);
		canvas.clipRect(r);
	}

	@Override
	public int getOpacity() {
		return 0;
	}

	@Override
	public void setAlpha(int alpha) {
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
	}
	
	public static class ColorSet {
		public final int background;
		public final int leftBorder;
		public final int topBorder;
		public final int rightBorder;
		public final int bottomBorder;
		
		public ColorSet(int background, int leftBorder, int topBorder, int rightBorder, int bottomBorder) {
			this.background = background;
			this.leftBorder = leftBorder;
			this.topBorder = topBorder;
			this.rightBorder = rightBorder;
			this.bottomBorder = bottomBorder;
		}
	}
	
	private static class PaintSet {
		public final Paint backgroundPaint;
		public final Paint leftBorderPaint;
		public final Paint topBorderPaint;
		public final Paint rightBorderPaint;
		public final Paint bottomBorderPaint;
		
		public PaintSet(ColorSet colorSet) {
			backgroundPaint = createFillPaint(colorSet.background);
			leftBorderPaint = createStrokePaint(colorSet.leftBorder);
			topBorderPaint = createStrokePaint(colorSet.topBorder);
			rightBorderPaint = createStrokePaint(colorSet.rightBorder);
			bottomBorderPaint = createStrokePaint(colorSet.bottomBorder);
		}
		
		public Paint createFillPaint(int color) {
			Paint paint = new Paint();
			paint.setColor(color);
			paint.setStyle(Paint.Style.FILL);
			return paint;
		}
		
		public Paint createStrokePaint(int color) {
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setStrokeWidth(1);
			paint.setColor(color);
			paint.setStyle(Paint.Style.STROKE);
			return paint;
		}
	}

}
