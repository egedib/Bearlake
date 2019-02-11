package com.example.bearlake;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class BearLakeView extends SurfaceView {

    private SurfaceHolder surfaceHolder;
    private LoopThread loopThread;
    private Handler handler;

    private Paint backgroundPaint;
    private Paint lakePaint;
    private Paint bearPaint;
    private Paint personPaint;
    private Paint bearPathPaint;

    private Path bearPath;

    public BearLakeView(Context context) {
        super(context);
        init();
    }

    public BearLakeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BearLakeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BearLakeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        int backgroundSize = Math.min(getWidth(), getHeight());
        Speeds.getInstance().setBearX((float)backgroundSize / 2);
        Speeds.getInstance().setBearY((float)backgroundSize / 2);

        backgroundPaint = new Paint();
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(Color.YELLOW);

        lakePaint = new Paint();
        lakePaint.setStyle(Paint.Style.FILL);
        lakePaint.setColor(Color.BLUE);

        bearPaint = new Paint();
        bearPaint.setStyle(Paint.Style.FILL);
        bearPaint.setColor(Color.RED);

        personPaint = new Paint();
        personPaint.setStyle(Paint.Style.FILL);
        personPaint.setColor(Color.MAGENTA);

        bearPathPaint = new Paint();
        bearPathPaint.setColor(Color.YELLOW);
        bearPathPaint.setStyle(Paint.Style.STROKE);

        bearPath = new Path();

        handler = new Handler();
        loopThread = new LoopThread(this);
        surfaceHolder = getHolder();



        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                stop();
            }
        });

    }

    private void start() {
        if (!loopThread.isRunning()) {
            loopThread.start();
            loopThread.setRunning(true);
        }
    }

    private void stop() {
        boolean retry = true;
        loopThread.setRunning(false);
        while (retry) {
            try {
                loopThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    int index = 1;
    int maxIndex = 360;

    int step = 0;
    int maxStep = 180;

    float bearStepX;
    float bearStepY;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //kiolvasas
        float oldHumanX = Speeds.getInstance().getHumanX();
        float oldHumanY = Speeds.getInstance().getHumanY();
        float oldBearX = Speeds.getInstance().getBearX();
        float oldBearY = Speeds.getInstance().getBearY();
        double oldHumanAngle = Speeds.getInstance().getHumanAngle();

        int humanSpeed = Speeds.getInstance().getHumanSpeed();
        int bearSpeed = Speeds.getInstance().getBearSpeed();

        int backgroundSize = Math.min(getWidth(), getHeight());
        canvas.drawRect(0, 0, backgroundSize, backgroundSize, backgroundPaint);

        //? fent buggol?
        if (oldBearX == 0f && oldBearY == 0f){
            oldBearX = backgroundSize / 2f;
            oldBearY = backgroundSize / 2f;
        }

        int personSize = backgroundSize / 15;
        int bearSize = backgroundSize / 15;

        int lakeSize = backgroundSize - (2 * personSize);
        canvas.drawCircle(lakeSize / 2f + personSize, lakeSize / 2f + personSize, lakeSize / 2f, lakePaint);


        int radius_main = (backgroundSize / 2) - (personSize / 2);
        int centerX = backgroundSize / 2;
        int centerY = backgroundSize / 2;
        //double startAngle = -Math.PI / 2f;
        double angle = oldHumanAngle + (humanSpeed / 50f * ((2 * Math.PI) / maxIndex));

        int personCx = (int) (centerX + Math.cos(angle) * radius_main);
        int personCy = (int) (centerY + Math.sin(angle) * radius_main);
        oldHumanX = personCx;
        oldHumanY = personCy;
        canvas.drawCircle(personCx, personCy, personSize / 2f, personPaint);

        //medve az elozo lepest nezze? most azt fogja.

        //koordgeo
        /*double humanX = radius_main * Math.cos(oldHumanAngle);
        double humanY = radius_main * Math.sin(oldHumanAngle);*/

        /*
        Norm = Sqrt((X2-X1)*(X2-X1) + (Y2-Y1)*(Y2-Y1)) // check for 0
        Dir_X = (X2 - X1) / Norm
        Dir_Y = (Y2 - Y1) / Norm
        Label_X = X1 + 20 * Dir_X
        Label_Y = Y1 + 20 * Dir_Y
         */

        /*double norm = Math.sqrt((oldHumanX-oldBearX)*(oldHumanX-oldBearX) + (oldHumanY-oldBearY) * (oldHumanY-oldBearY));
        norm = norm == 0 ? 1 : norm;
        double directionX = (oldHumanX-oldBearX) / norm;
        double directionY = (oldHumanY-oldBearY) / norm;*/

        double bearAngle = Math.atan2(oldHumanX-oldBearX, oldHumanY-oldBearY);

        double directionX = oldBearX + Math.sin(bearAngle) * bearSpeed / 15;

        double directionY = oldBearY + Math.cos(bearAngle) * bearSpeed / 15;


        /*
        //igy elole menekul
        double bearAngle = Math.atan2(oldBearY-oldHumanY, oldBearX-oldHumanX);

        double directionY = oldBearY + Math.sin(bearAngle) * bearSpeed / 10;

        double directionX = oldBearX + Math.cos(bearAngle) * bearSpeed / 10;*/

        //bearStepX = (float) (Math.cos(angle) * index);
        //bearStepX = (float) (Math.cos(angle) * bearSpeed);
        //bearStepX = (float) (oldBearX + bearSpeed / 100 * directionX);
        bearStepX = (float) directionX;
        //bearStepY = (float) (Math.sin(angle) * index);
        //bearStepY = (float) (Math.sin(angle) * bearSpeed);
        //bearStepY = (float) (oldBearY + bearSpeed / 100 * directionY);
        bearStepY = (float) directionY;
        /*float bearCx = (backgroundSize / 2f + bearStepX);
        float bearCy = (backgroundSize / 2f + bearStepY);*/
        canvas.drawCircle(bearStepX, bearStepY, bearSize / 2f, bearPaint);


        //bearPath.moveTo(bearCx, bearCy);
        bearPath.moveTo(bearStepX, bearStepY);
        //bearPath.lineTo(personCx, personCy);

        /*Speeds.getInstance().setBearX(bearCx);
        Speeds.getInstance().setBearY(bearCy);*/
        Speeds.getInstance().setBearX(bearStepX);
        Speeds.getInstance().setBearY(bearStepY);
        Speeds.getInstance().setHumanX(personCx);
        Speeds.getInstance().setHumanY(personCx);
        Speeds.getInstance().setHumanAngle(angle);
        //canvas.drawPath(bearPath, bearPathPaint);

        /*if (index < maxIndex) {
            index++;
        } else {
            index = 1;
            stop();
        }*/

    }

    public class LoopThread extends Thread {

        static final long FPS = 60;
        private BearLakeView view;
        private boolean running = false;


        public LoopThread(BearLakeView view) {
            this.view = view;
        }

        public void setRunning(boolean run) {
            running = run;
        }

        public boolean isRunning() {
            return running;
        }

        @SuppressLint("WrongCall")
        @Override
        public void run() {
            long ticksPS = 1000 / FPS;
            long startTime;
            long sleepTime;

            while (running) {

                startTime = System.currentTimeMillis();

                Canvas c = null;
                try {
                    c = view.getHolder().lockCanvas();
                    synchronized (view.getHolder()) {
                        view.onDraw(c);
                    }
                } finally {
                    if (c != null) {
                        view.getHolder().unlockCanvasAndPost(c);
                    }
                }


                sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
                try {
                    if (sleepTime > 0)
                        sleep(sleepTime);
                    else
                        sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
    }
}
