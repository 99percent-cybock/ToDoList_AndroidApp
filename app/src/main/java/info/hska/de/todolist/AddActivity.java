package info.hska.de.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "info.hska.de.todolist.TITLE";
    public static final String EXTRA_TEXT = "info.hska.de.todolist.TEXT";
    public static boolean comesFromAdd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtodo);
    }

    public void todoSwitchView(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        EditText editTitle = (EditText) findViewById(R.id.title_input);
        String title = editTitle.getText().toString();
        intent.putExtra(EXTRA_TITLE, title);

        EditText editText = (EditText) findViewById(R.id.text_input);
        String text = editText.getText().toString();
        intent.putExtra(EXTRA_TEXT, text);
        comesFromAdd = true;
        startActivity(intent);
    }
}
