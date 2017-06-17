package info.hska.de.todolist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;


public class MainActivity extends AppCompatActivity {
    private ArrayList<String> titleArray;
    private ArrayList<String> textArray;
    public static final String EXTRA_TITLE = "info.hska.de.todolist.TITLE";
    public static final String EXTRA_TEXT = "info.hska.de.todolist.TEXT";
    public static final String EXTRA_POSITION = "info.hska.de.todolist.POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleArray = new ArrayList<>();
        textArray = new ArrayList<>();



        String key1 = getString(R.string.preference_file_title_key);
        SharedPreferences sharedPref_title = this.getSharedPreferences(key1, Context.MODE_PRIVATE);
        String defaultTitle = "";
        String compressed_title = sharedPref_title.getString(key1,defaultTitle);
        titleArray = decompress(compressed_title);

        String key2 = getString(R.string.preference_file_text_key);
        SharedPreferences sharedPref_text = this.getSharedPreferences(key2, Context.MODE_PRIVATE);
        String defaultText = "";
        String compressed_text = sharedPref_text.getString(key2,defaultText);
        textArray = decompress(compressed_text);

        /*
        String key2 = getString(R.string.preference_file_text_key);
        SharedPreferences sharedPref_text = this.getSharedPreferences(key2, Context.MODE_PRIVATE);
        TreeSet<String> defText = new TreeSet<>();
        Set<String> stored_text = sharedPref_text.getStringSet(key2,defText);
        titleArray.addAll(stored_text);
        */

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, titleArray);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);


        ListView.OnItemClickListener listen = new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                String title = titleArray.get(position);
                //String text = textArray.get(position);
                intent.putExtra(EXTRA_TITLE, title);
                //intent.putExtra(EXTRA_TEXT, text);
                intent.putExtra(EXTRA_POSITION, position);
                startActivity(intent);
            }};
        listView.setOnItemClickListener(listen);

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    public void refreshList(){
        if (AddActivity.comesFrom) {
            Intent intent = getIntent();
            String title = intent.getStringExtra(AddActivity.EXTRA_TITLE);
            //String text = intent.getStringExtra(AddActivity.EXTRA_TEXT);
            if (title.length() == 0) {
                title = "Default ToDo Name";
            }
            //if (text.length() == 0) {
            //    text = " ";
            //}
            titleArray.add(titleArray.size(),title);
            //textArray.add(textArray.size(),text);
            commitToStorage();
        }
        if (ViewActivity.comesFrom) {
            Intent intent = getIntent();
            int position = intent.getIntExtra(ViewActivity.EXTRA_POSITION,0);
            titleArray.remove(position);
            //textArray.remove(position);
            commitToStorage();
        }
    }

    protected String compress(ArrayList<String> list) {
        String seperator = getString(R.string.item_seperator);
        String res = "";
        for (String element : list) {
            res += (element + seperator);
        }
        return res;
    }

    protected ArrayList<String> decompress(String compressed) {
        String seperator = getString(R.string.item_seperator);
        ArrayList<String> res = new ArrayList<>();
        String[] extract = compressed.split(seperator);
        for (String element : extract) {
            if (element != null && element.length() > 0 && element.charAt(element.length() - 1) == '\\') {
                element = element.substring(0, element.length() - 1);
            }
            res.add(element);
        }
        return res;
    }


    protected void commitToStorage() {
        String compressedTitle = compress(titleArray);
        String title_key = getString(R.string.preference_file_title_key);
        SharedPreferences sharedPref_title = getSharedPreferences(title_key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_title = sharedPref_title.edit();
        editor_title.putString(title_key, compressedTitle);
        editor_title.apply();

        String compressedText = compress(textArray);
        String text_key = getString(R.string.preference_file_text_key);
        SharedPreferences sharedPref_text = getSharedPreferences(text_key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_text = sharedPref_text.edit();
        editor_text.putString(text_key, compressedText);
        editor_text.apply();

    }
        /*
        String title_key = getString(R.string.preference_file_title_key);
        SharedPreferences sharedPref_title = getSharedPreferences(title_key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_title = sharedPref_title.edit();
        Set<String> currentTitle = new TreeSet<>();
        currentTitle.addAll(titleArray);
        editor_title.putStringSet(title_key,currentTitle);
        editor_title.apply();

        String text_key = getString(R.string.preference_file_text_key);
        SharedPreferences sharedPref_text = getSharedPreferences(text_key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_text = sharedPref_text.edit();
        Set<String> currentText = new TreeSet<>();
        currentText.addAll(textArray);
        editor_text.putStringSet(text_key, currentText);
        editor_text.apply();
        */

    public void switchActivityAdd(View view) {
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        startActivity(intent);
    }
/*
    public void switchViewShowtodo(View view) {
        setContentView(R.layout.activity_showtodo);
    }*/
}
/*
public class MainActivity extends AppCompatActivity {

    private ArrayList<String> titleArray = new ArrayList<String>();

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText input = (EditText) findViewById(R.id.edit_message);
            titleArray.add(input.getText().toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, titleArray);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        button.setOnClickListener(listener);
    }
}
*/