package tw.pitawanpor.twschool;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TeacherService extends AppCompatActivity {


    //Explicit
    private String dateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_service);
    }   //Main Method

    public  void clickScanQRcode (View view) {


        try {

            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);



        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Please Install barcode Scaner", Toast.LENGTH_SHORT).show();
        }
    }   //clickscan

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {

                String strContent = data.getStringExtra("SCAN_RESULT");


                Log.d("25JulyV2", "Content ==>" + strContent);

                confirmValue(strContent);

            }   //if2

        }   // if1

    }   // onActivity

    private void confirmValue(String strContent) {

        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateString = dateFormat.format(calendar.getTime());
        Log.d("25JulyV2", "Date Scan ==>" + dateString);

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("id_ligin", strContent)
                .add("Date", dateString)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://swiftcodingthai.com/tw/add_library.php")
                .post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });

    }   //confirmValue
}   //Main Class
