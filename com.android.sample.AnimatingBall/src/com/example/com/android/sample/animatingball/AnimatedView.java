package com.example.com.android.sample.animatingball;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AnimatedView extends ImageView {

	private final float BASE_X_COORDINATE = 30;
	private final float BASE_Y_COORDINATE = 60;
	private final int FRAME_RATE = 30;
	
	private int xVelocity;
	private int yVelocity;
	private Handler h;
	
	private RectF ballBounds; 									
	private Paint paint; 											

	private float ballRadius = 50; 
	private float ballX = ballRadius + BASE_X_COORDINATE;							
	private float ballY = ballRadius + BASE_Y_COORDINATE;
	private float shrinkFactor =-1;
	
	private int color = 0;
	private int ALPHA = 255;
	private int RED = 255;
	private int GREEN = 0;
	private int BLUE = 0;
	
	
	public AnimatedView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if( context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			xVelocity = 5;
			yVelocity = 10;
		} else {
			xVelocity = 10;
			yVelocity = 5;
		}
		h = new Handler();
		ballBounds = new RectF();
		paint = new Paint();
		color = Color.argb(ALPHA, RED, GREEN, BLUE);
	}

	private Runnable r = new Runnable() {
		@Override
		public void run() {
			invalidate();
			shrinkFactor +=1;
			GREEN += 3;
			BLUE += 3;
			color = Color.argb(ALPHA, RED, GREEN, BLUE);
		}
	};

	protected void onDraw(Canvas c) {
		ballBounds.set(ballX-ballRadius, ballY-ballRadius, ballX+ballRadius-shrinkFactor, ballY+ballRadius-shrinkFactor);  
		paint.setColor(color);  
     	 c.drawOval(ballBounds, paint);  
      
		if (ballX < 0 && ballY < 0) {
			ballX= 0;
			ballY = 0;
		} else {
			ballX +=xVelocity;
			ballY +=yVelocity;
			if ((ballX > this.getWidth() - ballBounds.width()) || (ballX < 0)) {
				xVelocity = xVelocity * -1;
			}
			if ((ballY > this.getHeight() - ballBounds.height()) || (ballY < 0)) {
				ballRadius = 40;
				ballX = ballRadius + BASE_X_COORDINATE;
				ballY = ballRadius + BASE_Y_COORDINATE;
				shrinkFactor = -1;
				GREEN = 0;
				BLUE = 0;
			}
		}
		h.postDelayed(r, FRAME_RATE);
	}
	
}