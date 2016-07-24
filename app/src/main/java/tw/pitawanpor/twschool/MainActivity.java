package tw.pitawanpor.twschool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Explicit
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        userEditText = (EditText) findViewById(R.id.editText6);
        passwordEditText = (EditText) findViewById(R.id.editText7);

    }   // Main Method


    public void clickSignIn (View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        // Check Space
        if (userString.equals("") || passwordString.equals("")) {
            //Have Space
            Toast.makeText(this, "Have Space ! Please All Every Blank",
                    Toast.LENGTH_SHORT).show();

        } else {
            // No Space

        } // if

    }   // clickSignIn

    //Get Event from Click
    public void clickSignUpMain(View view) {
    startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }
}   // Main Class นี่คือคลาสหลัก
