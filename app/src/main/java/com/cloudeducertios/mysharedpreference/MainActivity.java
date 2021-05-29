package com.cloudeducertios.mysharedpreference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText nameEt, passEt;
    Button saveBtn, loadBtn, increaseButton, decreaseButton;
    TextView textShow, textScore;
    LinearLayout linearLayout;
    int score = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inIt();

    }

    @SuppressLint("SetTextI18n")
    private void inIt() {
        nameEt = findViewById(R.id.edit_text_name);
        passEt = findViewById(R.id.edit_text_password);
        saveBtn = findViewById(R.id.save_btn);
        loadBtn = findViewById(R.id.load_btn);
        increaseButton = findViewById(R.id.increase_btn);
        decreaseButton = findViewById(R.id.decrease_btn);
        textShow = findViewById(R.id.text_id);
        textScore = findViewById(R.id.text_score_id);
        linearLayout = findViewById(R.id.linear_layout);

        saveBtn.setOnClickListener(this);
        loadBtn.setOnClickListener(this);
        increaseButton.setOnClickListener(this);
        decreaseButton.setOnClickListener(this);

        //when a activity call then showing game score.
        if (loadScore() != 0) {
            textScore.setText("Score: " + loadScore());
        }

        // when I am select with store background color.
        if (loadColor()!= getResources().getColor(R.color.design_default_color_on_primary)){
            linearLayout.setBackgroundColor(loadColor());
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_btn) {
            saveData();

        } else if (v.getId() == R.id.load_btn) {
            loadData();

        } else if (v.getId() == R.id.increase_btn) {
            score = score + 10;
            textScore.setText("Score: " + score);
            saveScore(score);
            Toast.makeText(this, "increase", Toast.LENGTH_SHORT).show();

        } else if (v.getId() == R.id.decrease_btn) {
            score = score - 10;
            textScore.setText("Score: " + score);
            saveScore(score);
            Toast.makeText(this, "decrease", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Save data..
     */
    @SuppressLint("ApplySharedPref")
    public void saveData() {
        /**
         * first edit text data show..
         */
        String userName = nameEt.getText().toString();
        String userPass = passEt.getText().toString();
        /**
         * if the data null then - don't storage.
         * data nai, tahole store korbo, ki.
         */
        if (userName.equals("") && userPass.equals("")) {
            Toast.makeText(this, "please enter some data.", Toast.LENGTH_SHORT).show();
        } else {
            /**
             * Todo: shared preference data storage or write..
             * Shared preference data saved.
             * 1.just key, 2.Mode private - data don't access.
             * sharedPreferences.edit() - for work data write.
             * editor er dara data put korbo.
             */
            SharedPreferences sharedPreferences =
                    getSharedPreferences("myDetails", Context.MODE_PRIVATE);
            @SuppressLint("CommitPrefEdits")
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("nameKey", userName);
            editor.putString("passKey", userPass);
            //data clear..
            nameEt.setText("");
            passEt.setText("");
            editor.commit();
            Toast.makeText(this, "Data store is successfully.", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Load or Read data...
     */
    @SuppressLint("SetTextI18n")
    private void loadData() {
        /**
         * Read data or Load data.
         */
        SharedPreferences sharedPreferences =
                getSharedPreferences("myDetails", Context.MODE_PRIVATE);
        /**
         * save button er key dara amara data retrieve/fetch korbo. like - nameKey, passKey
         * sharedprep e key thakle data retrieve korbo, otherwise korbona.
         * contains method for - string key. - key ache kina.. key thakle data  load do it.
         */
        if (sharedPreferences.contains("nameKey") && sharedPreferences.contains("passKey")) {
            /**
             * key gulor jonno amara value khuje ber korte parbo..
             * in case key match na hole - data not found.
             * get string - data load / read / retrieve.
             */
            String userName = sharedPreferences.getString("nameKey", "Data not found");
            String passName = sharedPreferences.getString("passKey", "Data not found");
            textShow.setText("Name: " + userName + "\n" + "pass: " + passName);
        }

    }

    @SuppressLint("ApplySharedPref")
    private void saveScore(int score) {
        SharedPreferences sharedPreferences =
                getSharedPreferences("dataSave", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("key", score);
        editor.commit();
    }

    private int loadScore() {
        SharedPreferences sharedPreferences =
                getSharedPreferences("dataSave", Context.MODE_PRIVATE);
        int lastScore = sharedPreferences.getInt("key", 0);
        return lastScore;
    }

    /**
     * menu item here.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * on option item selected
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.redColorMenuItemId) {
            //get color then store it.
            linearLayout.setBackgroundColor(getResources().getColor(R.color.red));
            storeColor(getResources().getColor(R.color.red));
        } else if (item.getItemId() == R.id.greenColorMenuItemId) {
            linearLayout.setBackgroundColor(getResources().getColor(R.color.green));
            storeColor(getResources().getColor(R.color.green));
        } else if (item.getItemId() == R.id.yellowColorMenuItemId) {
            linearLayout.setBackgroundColor(getResources().getColor(R.color.yellow));
            storeColor(getResources().getColor(R.color.yellow));
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * store color.
     * @param color
     */
    private void storeColor(int color) {
        SharedPreferences sharedPreferences =
                getSharedPreferences("storeColor", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("key", color);
        editor.commit();
    }

    private int loadColor() {
        SharedPreferences sharedPreferences =
                getSharedPreferences("storeColor", Context.MODE_PRIVATE);
        int selectedColor = sharedPreferences.getInt("key",
                getResources().getColor(R.color.design_default_color_on_primary));

        return selectedColor;
    }
}