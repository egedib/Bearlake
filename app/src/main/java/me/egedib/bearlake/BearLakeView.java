package me.egedib.bearlake;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BearLakeView extends SurfaceView {

    private LoopThread loopThread;
    private Paint backgroundPaint;
    private Paint lakePaint;
    private Paint bearPaint;
    private Paint personPaint;

    private Path bearPath;

    private ViewListener listener;

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

    public void setListener(ViewListener listener){
        this.listener = listener;
    }

    private void init() {
        int backgroundSize = Math.min(getWidth(), getHeight());
        Speeds.getInstance().reset();
        Speeds.getInstance().setBearX((float)backgroundSize / 2 * Speeds.getInstance().getBearX() / 100 );
        Speeds.getInstance().setBearY((float)backgroundSize / 2 * Speeds.getInstance().getBearY() / 100 );

        backgroundPaint = new Paint();
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(Color.YELLOW);

        lakePaint = new Paint();
        lakePaint.setStyle(Paint.Style.FILL);
        lakePaint.setColor(Color.BLUE);

        bearPaint = new Paint();
        bearPaint.setStyle(Paint.Style.FILL);
        bearPaint.setColor(Color.rgb(139,69,19));

        personPaint = new Paint();
        personPaint.setStyle(Paint.Style.FILL);
        personPaint.setColor(Color.rgb(105,105,105));

        Paint bearPathPaint = new Paint();
        bearPathPaint.setColor(Color.YELLOW);
        bearPathPaint.setStyle(Paint.Style.STROKE);

        bearPath = new Path();

        loopThread = new LoopThread(this);
        SurfaceHolder surfaceHolder = getHolder();

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
        Speeds.getInstance().setCoordPairs(new ArrayList<>());
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

    int maxIndex = 360;

    float bearStepX;
    float bearStepY;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //kiolvasas
        float oldHumanX;
        float oldHumanY;
        float oldBearX = Speeds.getInstance().getBearX();
        float oldBearY = Speeds.getInstance().getBearY();
        double oldHumanAngle = Speeds.getInstance().getHumanAngle();

        int humanSpeed = Speeds.getInstance().getHumanSpeed();
        int bearSpeed = Speeds.getInstance().getBearSpeed();

        int backgroundSize = Math.min(getWidth(), getHeight());
        canvas.drawRect(0, 0, backgroundSize, backgroundSize, backgroundPaint);

        //? fent buggol?
        if (oldBearX == 0f && oldBearY == 0f){
            oldBearX = backgroundSize / 2f /* Speeds.getInstance().getBearX() / 100*/ ;
            oldBearY = backgroundSize / 2f /* Speeds.getInstance().getBearX() / 100*/ ;
        }

        int personSize = backgroundSize / 15;
        int bearSize = backgroundSize / 15;

        int lakeSize = backgroundSize - (2 * personSize);
        canvas.drawCircle(lakeSize / 2f + personSize, lakeSize / 2f + personSize, lakeSize / 2f, lakePaint);

        int radius_main = (backgroundSize / 2) - (personSize / 2);
        int centerX = backgroundSize / 2;
        int centerY = backgroundSize / 2;
        double angle = oldHumanAngle + (humanSpeed / 50f * ((2 * Math.PI) / maxIndex));

        int personCx = (int) (centerX + Math.cos(angle) * radius_main);
        int personCy = (int) (centerY + Math.sin(angle) * radius_main);
        //ha a mostani lépest nézze a medve, akkor kikommentelni!
        oldHumanX = personCx;
        oldHumanY = personCy;
        canvas.drawCircle(personCx, personCy, personSize / 2f, personPaint);

        double bearAngle = Math.atan2(oldHumanX-oldBearX, oldHumanY-oldBearY);
        double directionX = oldBearX + Math.sin(bearAngle) * bearSpeed / 15;
        double directionY = oldBearY + Math.cos(bearAngle) * bearSpeed / 15;

        bearStepX = (float) directionX;
        bearStepY = (float) directionY;
        canvas.drawCircle(bearStepX, bearStepY, bearSize / 2f, bearPaint);

        bearPath.moveTo(bearStepX, bearStepY);

        Speeds.getInstance().setBearX(bearStepX);
        Speeds.getInstance().setBearY(bearStepY);
        Speeds.getInstance().setHumanAngle(angle);

        CoordPairs coordPairs = new CoordPairs(personCx, personCy, bearStepX, bearStepY);
        Speeds.getInstance().addToCoordPairs(coordPairs);

        if (Math.abs(bearStepX - personCx) < 5 && Math.abs(bearStepY - personCy) < 5 ){
            if(listener != null){
                listener.showMessage("Elkapta!");
            }
            loopThread.setRunning(false);
        }

    }

    public static class LoopThread extends Thread {

        static final long FPS = 60;
        private final BearLakeView view;
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
            Looper.prepare();



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
            this.running = false;
        }
    }
}
