package com.naosim.flatbutton;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.os.Build;

public class CustomFlatButtonDrawable extends Drawable {
	private PaintSet mPaintSet;
	public CustomFlatButtonDrawable(ColorSet colorSet) {
		mPaintSet = new PaintSet(colorSet);
	}
	@Override
	public void draw(Canvas canvas) {
		// background
		Rect bounds = canvas.getClipBounds();
		drawRoundRect(canvas, bounds, mPaintSet.backgroundPaint);
		
		if(Build.VERSION.SDK_INT < 18) {
			// left
			clipLeftSide(canvas, bounds);
			drawRoundRect(canvas, bounds, mPaintSet.leftBorderPaint);
			//right
			clipRightSide(canvas, bounds);
			drawRoundRect(canvas, bounds, mPaintSet.rightBorderPaint);
			// top
			clipTop(canvas, bounds);
			drawRoundRect(canvas, bounds, mPaintSet.topBorderPaint);
			// bottom
			clipBottom(canvas, bounds);
			drawRoundRect(canvas, bounds, mPaintSet.bottomBorderPaint);
			clearClip(canvas, bounds);
		} else {
			// API18だと、clip系で挙動がおかしくなるので、枠線を書くだけにする
			drawRoundRect(canvas, bounds, mPaintSet.bottomBorderPaint);
		}
	}
	
	private static final int PADDING = 1;
	private static final int RADIUS = 2;
	public static void drawRoundRect(Canvas canvas, Rect bounds, Paint paint) {
		
		canvas.drawRoundRect(
				new RectF(bounds.left + PADDING, bounds.top + PADDING, bounds.right - PADDING, bounds.bottom - PADDING), RADIUS, RADIUS, paint);
	}
		
	public void clearClip(Canvas canvas, Rect bounds) {
		canvas.clipRect(bounds, Op.REPLACE);
	}
	
	public void clipTop(Canvas canvas, Rect bounds) {
		clearClip(canvas, bounds);
		Rect r = new Rect(
				bounds.left + 1, 
				bounds.top, 
				bounds.right - 1,
				bounds.top+ 3);
		canvas.clipRect(r);
	}
	
	public void clipBottom(Canvas canvas, Rect bounds) {
		clearClip(canvas, bounds);
		Rect r = new Rect(
				bounds.left + 1, 
				bounds.bottom - 2, 
				bounds.right - 1,
				bounds.bottom);
		canvas.clipRect(r);
	}
	
	public void clipLeftSide(Canvas canvas, Rect bounds) {
		clearClip(canvas, bounds);
		Rect r = new Rect(
				bounds.left, 
				bounds.top + 2, 
				bounds.left + 2,
				bounds.bottom - 2);
		canvas.clipRect(r);
	}
	
	public void clipRightSide(Canvas canvas, Rect bounds) {
		clearClip(canvas, bounds);
		Rect r = new Rect(
				bounds.right - 2, 
				bounds.top + 2, 
				bounds.right,
				bounds.bottom - 2);
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
