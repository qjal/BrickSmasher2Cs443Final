package kushalkashyap.bricksmasher.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import kushalkashyap.bricksmasher.MainActivity;
import kushalkashyap.bricksmasher.R;

import static kushalkashyap.bricksmasher.MainActivity.HIGHSCORE;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);


        Intent intent = getIntent();
        int score = 0;
        if(intent != null){
            score = intent.getIntExtra(HIGHSCORE, 0);

        }

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



        //reference high score
        TextView tv= (TextView) findViewById(R.id.high);
        int savedScore = Integer.parseInt(tv.getText().toString());
        tv.setText(Math.max(savedScore, score)+"");

    }

}
