package com.cmckenzie.simpletodo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.text.TextWatcher;
import android.text.Editable;
import android.content.Intent;
import android.widget.TextView;

public class EditItemActivity extends AppCompatActivity {
    EditText listText;
    EditText newText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String listItem = getIntent().getStringExtra("listText");
        listText = (EditText) findViewById(R.id.editText);
        listText.setText(listItem, TextView.BufferType.EDITABLE);
        listText.setSelection(listText.getText().length());
    }

    public void sendItBack(View v) {
        newText = (EditText) findViewById(R.id.editText);
        Intent i = new Intent();
        i.putExtra("newitem", newText.getText().toString());
        setResult(RESULT_OK, i);
        finish();
    }


}
