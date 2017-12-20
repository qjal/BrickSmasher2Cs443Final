package kushalkashyap.bricksmasher;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

public class Ball {
    private final int REVERSE = -1;
    private float x;
    private float oldx;
    private float y;
    private float oldy;
    private int velX;
    private int velY;
    private Context context;
    static float ballWidth;
    static float ballheight;
    float ballleft;
    float ballright;
    float balltop;
    float ballbottom;
    int xspeed;
    int yspeed;
    int maxyspeed;
    int maxxspeed;
    int minxspeed;
    int minyspeed;
    float direction;
    int paddlewidth;



    public Ball(float xpos, float ypos, Context context) {
        xspeed = Resources.getSystem().getDisplayMetrics().widthPixels/60;
        yspeed = Resources.getSystem().getDisplayMetrics().heightPixels/60;
        velX = (int)(Math.pow(MainActivity.getDensity(),2) +xspeed);
        velY = (int)(Math.pow(MainActivity.getDensity(),2) +yspeed);
         maxyspeed = 2*yspeed;
         maxxspeed = 2*xspeed;
         minxspeed= xspeed/2;
         minyspeed = yspeed/2;
        this.context = context;
        x = xpos;
        y = ypos;
        Resources res = context.getResources();
        Bitmap ball = BitmapFactory.decodeResource(res, R.drawable.yellow);
        ballWidth =  ball.getWidth();
        ballheight = ball.getHeight();
        paddlewidth = Paddle.getwidth();
    }
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean move(int leftWall, int topWall,
                        int rightWall, int bottomWall) {
        //MOVE BALL
        oldx = x;
        oldy = y;
       x += velX;
       y += velY;

       getdirecton();



        //CHECK FOR COLLISIONS ALONG WALLS
        if( y>Paddle.ptop - ballheight && y<Paddle.pbottom+ballheight){
            if(x>Paddle.pleft-ballWidth&&x<Paddle.pright+ballWidth){
                if(x>Paddle.pleft&&x<Paddle.pleft+Paddle.width/4){
                    if (direction < 0) {
                        if (velX > minxspeed) {
                            velX = velX -(int) (velX * 0.2f);
                        }if(velY<minyspeed){
                            velY = velY - (int) (velY *0.2f);
                        }
                    }else if(direction>0){
                        if (velX < maxxspeed) {
                            velX = velX +(int) (velX * 0.2f);
                            velX*=REVERSE;
                        }if(velY< maxyspeed){
                            velY = velY +(int) (velY *0.2f);
                        }
                    }
                }else if(x>Paddle.pright-Paddle.width/4&&x<Paddle.pright){
                    if (direction < 0) {
                        if (velX < maxxspeed) {
                            velX = velX +(int) (velX * 0.2f);
                            velX*=REVERSE;
                        }if(velY<maxxspeed){
                            velY = velX + (int) (velY *0.2f);
                        }
                    }else if(direction>0){
                        if (velX > minxspeed) {
                            velX = velX -(int) (velX * 0.2f);

                        }if(velY> minyspeed){
                            velY = velY -(int) (velY *0.2f);
                        }
                    }
                }else if(x>Paddle.pleft+Paddle.width/4&&x<Paddle.pright-Paddle.width/4){
                    if(direction>0){
                        if(velX>minxspeed) {
                            velX = (int) (velX - (velX * 0.6f));
                        }
                    }else if(direction<0){
                        velX = (int) (velX + (velX * 0.6f));
                    }else{
                        velY= (int) (velY - (velY * 0.6f));
                    }

                }
                velY *= REVERSE;
                return true;
            }
        }
        if (y > bottomWall - ballheight) {
            return false;
        } else if (y < topWall + ballheight) {
            y = topWall + ballheight;
            velY *= REVERSE;
        }

        if (x > rightWall - ballWidth) {
            x = rightWall - ballWidth;
            velX *= REVERSE;
        } else if (x < leftWall + ballWidth) {
            x = leftWall + ballWidth;
            velX *= REVERSE;
        }
        return  true;
    }


    public  void resetball(int x, int y){
        velX = (int)(Math.pow(MainActivity.getDensity(),2) +xspeed);
        velY = (int)(Math.pow(MainActivity.getDensity(),2) +yspeed);
        ballleft = x/2;
        ballright = ballleft + ballWidth;
        balltop = y/2 + 450;
        ballbottom = balltop - ballheight;
    }




    public void getdirecton(){
        direction = (float)(y-oldy)/(float)(x-oldx);
    }

    public void reverse(){
        velY= velY*REVERSE;
        velX = velX*REVERSE;
    }
    public void reversex(){

        velX = velX*REVERSE;
    }
    public void reversey(){

        velY = velY*REVERSE;
    }
}
