package com.example.theflyingfish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView extends View {
    private Bitmap fish[] = new Bitmap[2];
    int fishX = 10;
    int fishY, fishSpeed, canvasWidth, canvasHeight;

    private Bitmap background;

    private Paint scorePaint = new Paint();
    private Bitmap life[] = new Bitmap[2];
    private boolean touch = false;

    private int yellowX,yellowY, yellowSpeed = 12;
    private Paint yellowPaint = new Paint();

    private int score,lifeCounterOfFish;

    private int greenX,greenY,greenSpeed = 14;
    private Paint greenPaint = new Paint();

    private int redX,redY,redSpeed = 16;
    private Paint redPaint = new Paint();

    public FlyingFishView(Context context) {
        super(context);
        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(50);
        scorePaint.setTypeface(Typeface.DEFAULT);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        fishY = 550;
        lifeCounterOfFish = 3;

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(background, 0, 0, null);

        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight() * 2;
        fishY = fishSpeed + fishY;

        if (fishY < minFishY) {
            fishY = minFishY;
        }
        if (fishY > maxFishY) {
            fishY = maxFishY;
        }
        fishSpeed = fishSpeed + 2;
        if (touch) {
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;
        }
        else {
                canvas.drawBitmap(fish[0], fishX, fishY, null);
        }

        canvas.drawCircle(yellowX,yellowY,30,yellowPaint);
        canvas.drawCircle(greenX,greenY,30,greenPaint);
        canvas.drawCircle(redX,redY,30,redPaint);

        yellowX = yellowX - yellowSpeed;
        if (hitBallChecker(yellowX, yellowY))
        {
            score = score + 10;
            yellowX = -100;
        }
        if (yellowX < 0)
        {
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY) ) + minFishY;
        }

        greenX = greenX - greenSpeed;
        if (hitBallChecker(greenX, greenY))
        {
            score = score + 20;
            greenX = -100;
        }
        if (greenX < 0)
        {
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY) ) + minFishY;
        }

        redX = redX - redSpeed;
        if (hitBallChecker(redX, redY))
        {
            redX = -100;
            lifeCounterOfFish--;

            if (lifeCounterOfFish == 0)
            {
                Intent i = new Intent(getContext(),gameOverActivity.class);
                getContext().startActivity(i);
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();
            }
        }
        if (redX < 0)
        {
           redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY) ) + minFishY;
        }

        canvas.drawText("Score : " + score, 20, 60, scorePaint);

        for (int i = 0; i < 3; i++)
        {
            int x = (int) (440 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if (i < lifeCounterOfFish)
            {
               canvas.drawBitmap(life[0], x,y,null);
            }
            else
            {
                canvas.drawBitmap(life[1], x,y,null);
            }
        }

    }

    public boolean hitBallChecker(int x, int y)
    {
        if(fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight()))
        {
            return  true;
        }
        return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;
            fishSpeed = -22;
        }
        return true;
    }
}