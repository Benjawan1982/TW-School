package tw.pitawanpor.twschool;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    //Explicit การประก่าศตัวแปร
    private EditText nameEditText,surnameEditText,roomEditText,userEditText,passwordEditText;
    private RadioGroup radioGroup;
    private RadioButton studentRadioButton, teacherRadioButton;
    private String nameString, surnameString, roomString, userString, passwordString, statusString = "1", QRcodeString;
    private static final String urlPHP = "http://swiftcodingthai.com/tw/add_user_BenjaIng.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Initial Widget
        nameEditText = (EditText)  findViewById(R.id.editText);
        surnameEditText = (EditText) findViewById(R.id.editText3);
        roomEditText = (EditText) findViewById(R.id.editText4);
        userEditText = (EditText) findViewById(R.id.editText2);
        passwordEditText = (EditText) findViewById(R.id.editText5);
        radioGroup = (RadioGroup) findViewById(R.id.ragStatus);
        studentRadioButton = (RadioButton) findViewById(R.id.radioButton);
        teacherRadioButton = (RadioButton) findViewById(R.id.radioButton2);

        //Radio Controller
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case  R.id.radioButton:
                        //for Student
                        statusString = "1";

                        break;
                    case R.id.radioButton2:
                        //for Teacher
                        statusString = "0";

                        break;

                }   // switch

            }   //onCheck
        });

    }   //Main Method

    public void clickSignUpSign(View view) {

        //Get Value From Edit Text
        nameString = nameEditText.getText().toString().trim();
        surnameString = surnameEditText.getText().toString().trim();
        roomString = roomEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check Space
        if (nameString.equals("") || surnameString.equals("") ||
                roomString.equals("") || userString.equals("") ||
                passwordString.equals("")) {
            //Have Space

            Toast.makeText(this, "write all colump", Toast.LENGTH_SHORT).show();
        } else {
            //No Space
            switch (Integer.parseInt(statusString)) {

                case  0:
                    QRcodeString = "teacher_" + userString;
                    break;
                case 1:
                    QRcodeString = "student_" + userString;
                    break;

            }   //switch

            confirmValue();
        }   //if

    }   //clickSignUp

    private void confirmValue() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.icon_cow);
        builder.setTitle("please check");
        builder.setMessage("Name = " + nameString + "\n" +
        "Surname = " + surnameString + "\n" +
        "Status = " + shotStatus (statusString) +"\n" +
        "Room =" + roomString + "\n" +
        "User =" + userString + "\n" +
        "Password =" + passwordString + "\n" +
        "QRcode = " + QRcodeString);
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                uploadValueToServer();
                dialogInterface.dismiss();
            }


        });
        builder.show();

    }   // confirmValue

    private void uploadValueToServer() {

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("Name", nameString)
                .add("Surname", surnameString)
                .add("Status", statusString)
                .add("Room", roomString)
                .add("Lat", "0")
                .add("Lng", "0")
                .add("User", userString)
                .add("Password", passwordString)
                .add("QRcode", QRcodeString)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlPHP).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                finish();
            }
        });

    }   // upload

    private String shotStatus(String statusString) {

        String result = null;

        switch (Integer.parseInt(statusString)) {
            case  0:
                result = "Teacher";
                break;
            case 1:
                result = "Student";
                break;
        }
        return result;
    }


}   //Main Class
