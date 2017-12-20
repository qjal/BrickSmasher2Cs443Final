package kushalkashyap.bricksmasher;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kushalkashyap.bricksmasher.views.MenuActivity;


public class MainActivity extends AppCompatActivity implements BrickSmasherView.Update {

    BrickSmasherView mBrickSmasherView;
    RelativeLayout layout;
    ImageView paddleimage;
    int xDelta;
    Paddle paddle;
    Thread ballThread;
    boolean ballrunning;
    boolean ispaused;
    int screenwidth;
    int screenheight;
    int paddlebottom;
    static int density;
    public static final String HIGHSCORE = "HIGHSCORE";


   /* update the view of score and lives */
    @Override
    public void update(final int score, final int lives) {
        //

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView livesView = (TextView) findViewById(R.id.livesview);
                TextView scoreView = (TextView) findViewById(R.id.scoreview);
                scoreView.setText(score+"");
                livesView.setText(lives+"");
            }
        });

    }
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bricksmasherview);

        mBrickSmasherView = new BrickSmasherView(this);
        LinearLayout surface = (LinearLayout) findViewById(R.id.background);
        surface.addView(mBrickSmasherView);


        density = (int) getResources().getDisplayMetrics().density;
        layout = (RelativeLayout) findViewById(R.id.smasher);
        paddleimage = (ImageView) findViewById(R.id.paddle);
        paddleimage.setOnTouchListener(onTouchListener());
        paddle = new Paddle(paddleimage);
        paddlebottom = paddleimage.getBottom();

        screenheight = getWindowManager().getDefaultDisplay().getHeight();
        screenwidth = getWindowManager().getDefaultDisplay().getWidth();


    }

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {
            @SuppressLint("ClickableView")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int x = (int) event.getRawX();
                int y = (int) event.getRawY();

                if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();

                    xDelta = x - params.leftMargin;

                } else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {

                } else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_MOVE) {
                    RelativeLayout.LayoutParams myparams = (RelativeLayout.LayoutParams) v.getLayoutParams();

                    myparams.leftMargin = x - xDelta;
                    myparams.rightMargin = 0 - x;
                    v.setLayoutParams(myparams);

                }
                layout.invalidate();
                paddle.update(paddleimage);
                return true;
            }
        };


    }


    @Override
    protected void onResume() {
        super.onResume();
        mBrickSmasherView.resumegame();


    }

    @Override
    protected void onPause() {
        super.onPause();
        mBrickSmasherView.pausegame();

        ispaused = true;
        ballrunning = false;
        try {
            if(ballThread != null)
            ballThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static int getDensity() {
        return density;
    }


    public void resetGame(int score) {
        Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra(HIGHSCORE, score);
            startActivity(intent);
    }
}



