package com.cloudeducertios.mysharedpreference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

    private EditText etFirstName, etLastName, etPassWord;
    private Button saveBtn, loadBtn, increaseBtn, decreaseBtn;
    private TextView textResult, textScore;
    int score = 0;
    LinearLayout linearLayout;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inIt();

    }

    @SuppressLint("SetTextI18n")
    private void inIt() {
        etFirstName = findViewById(R.id.edit_text_fName);
        etLastName = findViewById(R.id.edit_text_lName);
        etPassWord = findViewById(R.id.edit_text_password);
        textResult = findViewById(R.id.text_id);
        textScore = findViewById(R.id.text_score_id);
        saveBtn = findViewById(R.id.save_btn);
        loadBtn = findViewById(R.id.load_btn);
        linearLayout = findViewById(R.id.linear_layout);
        increaseBtn = findViewById(R.id.increase_btn);
        decreaseBtn = findViewById(R.id.decrease_btn);
        saveBtn.setOnClickListener(this::onClick);
        loadBtn.setOnClickListener(this::onClick);
        increaseBtn.setOnClickListener(this::onClick);
        decreaseBtn.setOnClickListener(this::onClick);

        if (loadColor() != ContextCompat.getColor(this, R.color.red)) {
            linearLayout.setBackgroundColor(loadColor());
        }
        if (loadScore() != 0) {
            textScore.setText("Score : " + loadScore());
        }


    }

    /**
     * save button click to data save ...!
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_btn) {
            saveData();
        } else if (v.getId() == R.id.load_btn) {
            loadData();
        } else if (v.getId() == R.id.increase_btn) {
            score = score + 10;
            textScore.setText("Score : " + score);
            saveScore(score);
            Toast.makeText(this, "increase", Toast.LENGTH_SHORT).show();

        } else if (v.getId() == R.id.decrease_btn) {
            score = score - 10;
            textScore.setText("Score : " + score);
            saveScore(score);
            Toast.makeText(this, "decrease", Toast.LENGTH_SHORT).show();

        }
    }


    private void saveData() {
        /**
         * first work data convert to string.
         */
        String fName = etFirstName.getText().toString();
        String lName = etLastName.getText().toString();
        String password = etPassWord.getText().toString();

        if (fName.equals("") && (lName.equals("") && password.equals(""))) {
            Toast.makeText(this, "please input your data then  click to save", Toast.LENGTH_SHORT).show();
        } else {
            /**
             * through the shared preference data save.
             */
            SharedPreferences sharedPreferences = getSharedPreferences("fileName", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("fNameKey", fName);
            editor.putString("lNameKey", lName);
            editor.putString("passwordKey", password);
            /**
             * todo: second app open then data is clear..
             */
            etFirstName.setText("");
            etLastName.setText("");
            etPassWord.setText("");
            editor.apply();
            Toast.makeText(this, "Successfully data save.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        String fName = etFirstName.getText().toString();
        String lName = etLastName.getText().toString();
        String password = etPassWord.getText().toString();

        if (fName.equals("") && lName.equals("") && password.equals("")) {
            SharedPreferences sharedPreferences = getSharedPreferences("fileName",
                    Context.MODE_PRIVATE);
            /**
             * key found then execute the line...
             */
            if (sharedPreferences.contains("fNameKey") && sharedPreferences.contains("lNameKey")
                    && sharedPreferences.contains("passwordKey")) {
                fName = sharedPreferences.getString("fNameKey", "First name not found");
                lName = sharedPreferences.getString("lNameKey", "Last name not found");
                password = sharedPreferences.getString("passwordKey", "Password name not found");
                textResult.setText("FirstName : " + fName + "\n" +
                        "Last Name : " + lName + "\n" + "Password : " + password);
            } else {
                Toast.makeText(this, "Please input your data then click save",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }

    /**
     * increase save data
     *
     * @param score
     */
    private void saveScore(int score) {
        SharedPreferences sharedPreferences = getSharedPreferences("fileName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("inKey", score);
        editor.apply();
    }

    /**
     * save data then load score..
     *
     * @return
     */
    private int loadScore() {
        SharedPreferences sharedPreferences = getSharedPreferences("fileName", Context.MODE_PRIVATE);
        int score = sharedPreferences.getInt("inKey", 0);
        return score;
    }


    /**
     * storage setting by menu... !
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // menu layout include..
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * when menu item select then how is working...
     * then use to onOption menu item selecte..con
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /**
         * which item click then find it.
         */
        if (item.getItemId() == R.id.redColorMenuItemId) {
            /**
             * attach background this layout.
             * color storage in shared preference.
             */
            linearLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
            /**
             * this color is storage
             */
            colorStorage(ContextCompat.getColor(getApplicationContext(), R.color.green));
        } else if (item.getItemId() == R.id.greenColorMenuItemId) {
            /**
             * attach background this layout.
             * color storage in shared preference.
             */
            linearLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
            /**
             * this color is storage
             */
            colorStorage(ContextCompat.getColor(getApplicationContext(), R.color.black));
        }
        if (item.getItemId() == R.id.yellowColorMenuItemId) {
            /**
             * attach background this layout.
             * color storage in shared preference.
             */
            linearLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
            /**
             * this color is storage
             */
            colorStorage(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * color storage to shared preference....
     *
     * @param color
     */
    private void colorStorage(int color) {
        SharedPreferences sharedPreferences = getSharedPreferences("colorStorage", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("colorKey", color);
        editor.apply();
    }

    /**
     * load color and app again open then select was color...
     *
     * @return
     */
    private int loadColor() {
        SharedPreferences sharedPreferences = getSharedPreferences("colorStorage", MODE_PRIVATE);
        int color = sharedPreferences.getInt("colorKey", ContextCompat.getColor(this, R.color.red));
        return color;
    }


    /**
     * Take the button then working..
     * we would set color status bar , action bar , everything place..
     */

}

