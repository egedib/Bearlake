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

        int backgroundSize = Math.min(getWidth(), getHeight());
        canvas.drawRect(0, 0, backgroundSize, backgroundSize, backgroundPaint);

        int personSize = backgroundSize / 10;
        int bearSize = backgroundSize / 10;

        int lakeSize = backgroundSize - (2 * personSize);
        canvas.drawCircle(lakeSize / 2f + personSize, lakeSize / 2f + personSize, lakeSize / 2f, lakePaint);


        int radius_main = (backgroundSize / 2) - (personSize / 2);
        int centerX = backgroundSize / 2;
        int centerY = backgroundSize / 2;
        double startAngle = -Math.PI / 2f;
        double angle = startAngle + (index * ((2 * Math.PI) / maxIndex));

        int personCx = (int) (centerX + Math.cos(angle) * radius_main);
        int personCy = (int) (centerY + Math.sin(angle) * radius_main);
        canvas.drawCircle(personCx, personCy, personSize / 2f, personPaint);


        bearStepX = (float) (Math.cos(angle) * index);
        bearStepY = (float) (Math.sin(angle) * index);
        float bearCx = (backgroundSize / 2f + bearStepX);
        float bearCy = (backgroundSize / 2f + bearStepY);
        canvas.drawCircle(bearCx, bearCy, bearSize / 2f, bearPaint);


        bearPath.moveTo(bearCx, bearCy);
        bearPath.lineTo(personCx, personCy);
        canvas.drawPath(bearPath, bearPathPaint);

        if (index < maxIndex) {
            index++;
        } else {
            index = 1;
            stop();
        }

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
