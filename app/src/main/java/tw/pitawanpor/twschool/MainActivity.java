package tw.pitawanpor.twschool;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

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

    //Create Inner Class
    private class SynchronizeUser extends AsyncTask<Void, Void, String> {

        //Explicit
        private Context context;
        private String myUserString, myPassString, truePassString;
        private static final String urlJason = "http://swiftcodingthai.com/tw/get_user_BenjaIng.php";
        private boolean statusABoolean = true;
        private String[] loginString = new String[8];

        public SynchronizeUser(Context context,
                               String myUserString,
                               String myPassString) {
            this.context = context;
            this.myUserString = myUserString;
            this.myPassString = myPassString;
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJason).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                return null;
            }


        }   // doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("24JulyV1", "JSON ==>" + s);

            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i += 1) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (myUserString.equals(jsonObject.getString("User"))) {
                        //User True
                        statusABoolean = false;
                        truePassString = jsonObject.getString("Password");

                        loginString[0] = jsonObject.getString("id");
                        loginString[1] = jsonObject.getString("Name");
                        loginString[2] = jsonObject.getString("Surname");
                        loginString[3] = jsonObject.getString("Status");
                        loginString[4] = jsonObject.getString("Room");
                        loginString[5] = jsonObject.getString("Lat");
                        loginString[6] = jsonObject.getString("Lng");
                        loginString[7] = jsonObject.getString("QRcode");
                    }   //if



                }   //for
                if (statusABoolean) {
                    Toast.makeText(context, "No" + myUserString + "In Database",
                            Toast.LENGTH_SHORT).show();
                } else if (myPassString.equals(truePassString)) {
                    //password True



                    Toast.makeText(context, "Welcome " + loginString[1]+ " "+ loginString[2],
                            Toast.LENGTH_SHORT).show();


                    //Intent to Service
                    switch (Integer.parseInt(loginString[3])) {
                        case 0:
                            //Teacher
                            break;
                        case  1:
                            //Student
                            Intent intent = new Intent(MainActivity.this, StudentService.class);
                            intent.putExtra("Login", loginString);
                            startActivity(intent);
                            finish();

                            break;
                    }//switch
                } else {
                    //Password False
                    Toast.makeText(context, "Please Try Again Password False",
                            Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        }   // onPost

    }   //Syn Class


    public void clickSignIn(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        // Check Space
        if (userString.equals("") || passwordString.equals("")) {
            //Have Space
            Toast.makeText(this, "Have Space ! Please All Every Blank",
                    Toast.LENGTH_SHORT).show();

        } else {
            // No Space
            SynchronizeUser synchronizeUser = new SynchronizeUser(this, userString, passwordString);
            synchronizeUser.execute();
        } // if

    }   // clickSignIn

    //Get Event from Click
    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }
}   // Main Class นี่คือคลาสหลัก
