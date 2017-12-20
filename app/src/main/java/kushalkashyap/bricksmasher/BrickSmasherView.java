package kushalkashyap.bricksmasher;


import android.content.Context;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;


public class BrickSmasherView extends SurfaceView {



    SurfaceHolder holder;

    Ball ball;
    Paint paint;
    Canvas canvas;
    MainActivity context;

    boolean ballpos = true;
    boolean brickrun = false;
    Thread back = null;
    Thread brickThread;
    boolean ballrunning = true;
    volatile boolean running = false;
    int displayX;
    int displayY;
    Bricks[]bricks = new Bricks[50];
    int score = 0;
    int lives = 3;
    int  num_bricks = 0;

    public BrickSmasherView(MainActivity context) {
        super(context);
        this.context = context;
        holder = getHolder();
        drawbricks();
        ball = new Ball(0,0,context);
        paint = new Paint();
        displayX = Resources.getSystem().getDisplayMetrics().widthPixels;
        displayY = Resources.getSystem().getDisplayMetrics().heightPixels;


    }
    public void runbackThread(){
        back = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    while (running) {
                        if(ballpos){
                          ballpos=  ball.move(0, 0, displayX, displayY);
                          if(ballpos==false){
                              lives--;
                              ball.resetball(displayX/2, displayY/2);
                              context.update(score,lives);
                              if(lives <0){
                                  resetgame();
                              }
                          }

                        }

                        draw();
                    }
                }
            }
        });
        back.start();
    }
    public void runBrickThread(){
        brickThread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    while (brickrun) {
                        if (num_bricks <= 0) {
                            num_bricks = 50;
                            for (int i = 0; i < bricks.length; i++) {
                                bricks[i].setvisible();
                            }
                        }
                        for (int i = 0; i < bricks.length; i++) {
                            if (bricks[i].isvisible()) {
                                if (ball.getY() < bricks[i].brbottom ) {
                                    if (ball.getX() > bricks[i].brleft  && ball.getX() < bricks[i].brright ) {
                                        bricks[i].setinvisible();
                                        ball.reversey();
                                        num_bricks--;
                                        score += 20;
                                        context.update(score, lives);
                                        continue;
                                    }
                                }

                                if (ball.getX() > bricks[i].brleft && ball.getX() < bricks[i].brright ) {
                                    if (ball.getY() < bricks[i].brbottom) {
                                        bricks[i].setinvisible();
                                        ball.reversex();
                                        num_bricks--;
                                        score += 20;
                                        context.update(score, lives);
                                        continue;
                                    }

                                }


                            }
                        }
                    }
                }
            }
        });
        brickThread.start();
    }




    public  void resumegame(){
        running = true;
        brickrun = true;
        runBrickThread();
       runbackThread();


    }
    public  void resetgame(){
        lives = 3;
        context.resetGame(score);
    }

    public void pausegame(){

        running = false;
        brickrun = false;
        try
        {
            back.join();
            brickThread.join();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


public void  draw(){
    if(holder.getSurface().isValid()){
        canvas = holder.lockCanvas();
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.sky),0,0,null);
        for(int j = 0; j<bricks.length; j++){
            Bricks brick = bricks[j];
            if(brick.isvisible()) {
                canvas.drawBitmap(brick.getBrick(), brick.getBrleft(), brick.getBrtop(), null);
            }
        }
        if(ballpos) {
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.yellow), ball.getX(), ball.getY(), null);

        }else{
            ballrunning = false;
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.yellow), displayX/2, displayY/2, null);

        }
        holder.unlockCanvasAndPost(canvas);
    }
}
    @Override
    public  boolean onTouchEvent(MotionEvent ballmovement){

        if((ballmovement.getAction()&MotionEvent.ACTION_MASK)== MotionEvent.ACTION_DOWN) {
            if(ballrunning==false) {
                ballpos = true;
                ball.setY(displayY / 2);
                ball.setX(displayX / 2);
                ball.move(0, 0, displayX, displayY);
                ballrunning= true;
            }
        }
        else if((ballmovement.getAction()&MotionEvent.ACTION_MASK)==MotionEvent.ACTION_UP){

        }
        return  true;
    }




public void drawbricks(){
    Random r = new Random();


    int currleft = 0;
    int currtop =0 ;
    int n = 6;
    int j = 0;// for tracking if total number of brick to fill in width

    for(int i = 0; i<bricks.length; i++ ){
        Bricks brick = new Bricks(currleft, currtop,context);
        brick.setvisible();
        bricks[i]= brick;
        num_bricks++;
        j++;
        currleft = (int) brick.getBrright()+20;
        if(j>n){
         currleft =  r.nextInt(30) + 20;
         currtop = (int) (currtop + currleft+ brick.height);
         j = 0;
        }

    }
}


    interface Update {
        public void update(int score, int lives);
    }



}
