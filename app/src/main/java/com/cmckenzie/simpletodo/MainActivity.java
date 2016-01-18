package com.cmckenzie.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import android.text.TextWatcher;
import android.text.Editable;
import android.content.Intent;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> todoItems;
    static ArrayAdapter<String> aToDoAdapter;
    static ListView lvItems;
    static EditText etEditText;
    static int posClicked;
    static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        aToDoAdapter = new ArrayAdapter<String> (this,
                android.R.layout.simple_list_item_1, todoItems);
        etEditText = (EditText) findViewById(R.id.etNewItem);
        lvItems.setAdapter(aToDoAdapter);
        setupListViewListener();
    }
    public void launchItemEditView() {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("listText", todoItems.get(posClicked));
        startActivityForResult(i,REQUEST_CODE); // brings up the second activity
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Extract name value from result extras
        todoItems.add(posClicked, data.getStringExtra("newitem"));
        aToDoAdapter.notifyDataSetChanged();
        writeItems();
    }


    public void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {

        }
    }

    public void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e) {

        }
    }

    private void setupListViewListener() {
        lvItems.setOnItemClickListener( new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                posClicked = pos;
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("listText", todoItems.get(posClicked));
                startActivity(i);
            }
        });
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapter,
                                        View item, int pos, long id) {
                    todoItems.remove(pos);
                    aToDoAdapter.notifyDataSetChanged();
                    writeItems();
                    return true;
                }
        });
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        aToDoAdapter.add(itemText);
        writeItems();
        etNewItem.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
