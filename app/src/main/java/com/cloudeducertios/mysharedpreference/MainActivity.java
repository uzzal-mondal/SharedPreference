package com.cloudeducertios.mysharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText nameEt, passEt;
    Button saveBtn, loadBtn, increaseButton, decreaseButton;
    TextView textShow, textScore;
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

        saveBtn.setOnClickListener(this);
        loadBtn.setOnClickListener(this);
        increaseButton.setOnClickListener(this);
        decreaseButton.setOnClickListener(this);

        //todo: when open the app then show the score.
        if (loadScore() != 0) {
            textScore.setText("Score: " + loadScore());
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

        //todo: shared preference save
        SharedPreferences sharedPreferences =
                getSharedPreferences("saveData", Context.MODE_PRIVATE);
        //todo: editor
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //todo: key under save data.
        editor.putInt("key", score);
        editor.commit();
    }

    private int loadScore() {
        SharedPreferences sharedPreferences =
                getSharedPreferences("saveData", Context.MODE_PRIVATE);
        int score = sharedPreferences.getInt("key", 0);
        return score;
    }
}