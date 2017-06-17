package info.hska.de.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity
{
    private Intent it;
    public static final String EXTRA_POSITION = "info.hska.de.todolist.POSITION";
    public static boolean comesFrom = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtodo);

        Intent intent = getIntent();
        it = intent;
        String title = intent.getStringExtra(MainActivity.EXTRA_TITLE);
        //String text = intent.getStringExtra(MainActivity.EXTRA_TEXT);

        TextView titleView = (TextView) findViewById(R.id.selected_title);
        //TextView textView = (TextView) findViewById(R.id.selected_text);
        titleView.setText(title);
        //textView.setText(text);
    }

    public void switchActivityMain(View view) {
        Intent intent = new Intent(ViewActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void deleteSwitchView(View view) {
        Intent intent = new Intent(ViewActivity.this, MainActivity.class);
        int position = it.getIntExtra(MainActivity.EXTRA_POSITION, 0);
        intent.putExtra(EXTRA_POSITION, position);
        comesFrom = true;
        startActivity(intent);
    }
}

