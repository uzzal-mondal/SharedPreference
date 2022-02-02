package com.cloudeducertios.mysharedpreference;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

/**
 * Created by Android Dev on 11-Aug-21 Aug, 2021
 */
public class SharePrepActivity extends AppCompatActivity implements View.OnClickListener {
    EditText nameEt, passEt;
    Button saveBtn, loadBtn, increaseButton, decreaseButton;
    TextView textShow, textScore;
    LinearLayout linearLayout;
    int score = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        inIt();
    }

    @SuppressLint("SetTextI18n")
    private void inIt() {
        nameEt = findViewById(R.id.edit_text_fName);
        passEt = findViewById(R.id.edit_text_password);
        saveBtn = findViewById(R.id.save_btn);
        loadBtn = findViewById(R.id.load_btn);
        increaseButton = findViewById(R.id.increase_btn);
        decreaseButton = findViewById(R.id.decrease_btn);
        textShow = findViewById(R.id.text_id);
        textScore = findViewById(R.id.text_score_id);
        linearLayout = findViewById(R.id.linear_layout);

        saveBtn.setOnClickListener(this::onClick);
        loadBtn.setOnClickListener(this::onClick);
        increaseButton.setOnClickListener(this::onClick);
        decreaseButton.setOnClickListener(this::onClick);

        //todo: when a activity finish then again call then showing game score.
        if (loadScore() != 0) {
            textScore.setText("Score: " + loadScore());
        }

        //todo: when I am select with store background color.
        if (loadColor() != ContextCompat.getColor(this, R.color.design_default_color_on_primary)) {
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
         * if the user get the data we are easily write or save the data.
         * Todo: shared preference data storage or  write..
         * myDetails is - database name.
         */
        String userName = nameEt.getText().toString();
        String userPass = passEt.getText().toString();
        /**
         * when user first of all don't put the data then request to user put the data.
         */
        if (userName.equals("") && userPass.equals("")) {
            Toast.makeText(this, "please enter some data.", Toast.LENGTH_SHORT).show();
        } else {
            /**
             * Todo: shared preference data storage or write..
             * Shared preference data saved.
             * myDetails is - database name.
             * 1.just key, 2.Mode private - data don't access.
             * sharedPreferences.edit() - for work data write.
             * editor er dara data put korbo.
             */
            SharedPreferences sharedPreferences =
                    getSharedPreferences("myDetails", Context.MODE_PRIVATE);
            @SuppressLint("CommitPrefEdits")
            /**
             * shared preference data have to write using must be method -  edit().
             */
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("nameKey", userName);
            editor.putString("passKey", userPass);
            //todo: data clear..
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
         * save button er todo : key dara amara data retrieve/fetch korbo. like - nameKey, passKey
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
        } else {
            Toast.makeText(this, "Key don't found...", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * save score..
     *
     * @param score
     */
    @SuppressLint("ApplySharedPref")
    private void saveScore(int score) {
        SharedPreferences sharedPreferences =
                getSharedPreferences("dataSave", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("key", score);
        editor.commit();
    }


    /**
     * save score then load score..
     */
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
            linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
            storeColor(ContextCompat.getColor(this, R.color.red));
        } else if (item.getItemId() == R.id.greenColorMenuItemId) {
            linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            storeColor(ContextCompat.getColor(this, R.color.green));
        } else if (item.getItemId() == R.id.yellowColorMenuItemId) {
            linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
            storeColor(ContextCompat.getColor(this, R.color.yellow));
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * store color.
     *
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


