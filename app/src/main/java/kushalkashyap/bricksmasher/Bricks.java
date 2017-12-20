package kushalkashyap.bricksmasher;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by kushalqjal on 12/3/2017.
 */

public class Bricks {
    private Bitmap brick;
    private  boolean isvisible;
    float brleft ;
    float brright;
    float brtop;
    float brbottom;
    float height;

//to check the visibility

    public boolean isvisible() {
        return isvisible;
    }

    public void setinvisible() {
        this.isvisible = false;
    }

    public void setvisible() {
        this.isvisible = true;
    }

    public Bricks(int xpos, int ypos, Context context){
        int padding = 1;
        isvisible = true;
        Resources res = context.getResources();
        brick = BitmapFactory.decodeResource(res, R.drawable.brick);
        brleft = xpos+padding;
        brtop = ypos+padding;
        brright= xpos+ brick.getWidth()+padding;
        brbottom = ypos + brick.getHeight()+padding;
        height = brick.getHeight();

    }

    //return the brick image
    public  Bitmap getBrick (){
        return this.brick;
    }

// retunr the four co-ordinates
    public float getBrleft() {
        return brleft;
    }

    public float getBrright() {
        return brright;
    }

    public float getBrtop() {
        return brtop;
    }


}
