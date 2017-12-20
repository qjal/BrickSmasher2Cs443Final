package kushalkashyap.bricksmasher;

import android.content.Context;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

/**
 * Created by kushalqjal on 12/3/2017.
 */

public class Paddle {

    // image to be used as paddle
    // length and width of paddle
   static  float height;
    // leftmost point and the topmost point of paddle, used in paddle positioning



    private float left;
    private Context context;
    static float  pleft;
   static float pright;
    static float ptop;
   static float pbottom;
   static float width;





    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public Paddle (ImageView paddle){

        left = paddle.getLeft();
        ptop = paddle.getTop();
        pright = paddle.getRight();
        pleft=left;
        pbottom = paddle.getBottom();
        width = paddle.getWidth();
        height = paddle.getHeight();


    }

    public void update(ImageView paddle){

        this.ptop = paddle.getTop();
        this.pbottom = paddle.getBottom();
        this.pleft = paddle.getLeft() ;
        this.pright= paddle.getRight();
        this.width = paddle.getWidth();


    }

    public static  int getwidth(){
        return (int) width;
    }


}
