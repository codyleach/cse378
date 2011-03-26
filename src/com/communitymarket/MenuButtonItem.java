package com.communitymarket;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.util.AttributeSet;
import android.widget.Button;

public class MenuButtonItem extends Button {

	public static int ARROW_HEIGHT	= 8;
	public static int ARROW_WIDTH  	= 8;
	public static int STROKE_WIDTH 	= 3;
	public static int RIGHT_PADDING = 5;
	public static int DEFAULT_COLOR	= Color.DKGRAY;
	
	private Paint _paint;
	private int   _arrowWidth;
	private int   _arrowHeight;
	
	public MenuButtonItem(Context context) {
		super(context);
		init();
	}
	
	public MenuButtonItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		
		// Get user-defined attributes
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MenuButtonItem);
		setArrowColor(array.getColor(R.styleable.MenuButtonItem_arrowColor, DEFAULT_COLOR));
		setArrowWidth(array.getInt(R.styleable.MenuButtonItem_arrowWidth, ARROW_WIDTH));
		setArrowHeight(array.getInt(R.styleable.MenuButtonItem_arrowHeight, ARROW_HEIGHT));
		
		// Be kind, rewind
		array.recycle();
	}
	
	protected void init() {
		// Set up the drawing
		_paint = new Paint();
		_paint.setStyle(Paint.Style.STROKE);
		_paint.setStrokeWidth(STROKE_WIDTH);
		_paint.setStrokeCap(Cap.ROUND);
		_paint.setColor(DEFAULT_COLOR);
		_paint.setAntiAlias(true);
		
		// Set the initial arrow width and height
		_arrowWidth  = ARROW_WIDTH;
		_arrowHeight = ARROW_HEIGHT;
	}
	
	public void setArrowColor(int color) {
		_paint.setColor(color);
	}
	
	public void setArrowWidth(int width) {
		_arrowWidth = width;
	}
	
	public void setArrowHeight(int height) {
		_arrowHeight = height;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		// Set up the coordinates
		float middleY = (this.getMeasuredHeight() - this.getPaddingBottom() 
						- this.getPaddingTop()) / 2 + this.getPaddingTop();
		float middleX = this.getMeasuredWidth() - this.getPaddingRight() - RIGHT_PADDING;
		
		// Draw the lines
		canvas.drawLine(middleX, middleY, middleX - _arrowWidth, middleY - _arrowHeight, _paint);
		canvas.drawLine(middleX, middleY, middleX - _arrowWidth, middleY + _arrowHeight, _paint);
	}

}
