package tw.pitawanpor.twschool;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class StudentService extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String[] loinString;
    private TextView nameTextView , roomTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_layout);

        //Bind Widget
        nameTextView = (TextView) findViewById(R.id.textView13);
        roomTextView = (TextView) findViewById(R.id.textView14);

        //Show View
        loinString = getIntent().getStringArrayExtra("Login");
        nameTextView.setText(loinString[1] + " " + loinString [2]);
        roomTextView.setText("Student room ==>" + loinString[4]);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }   //Main Method

    public void clickEditStudent(View view) {

    }

    public void clickExitStudent(View view) {
        finish();
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //for Setup Center Map
        final double twLat = 15.350713;   //Lattitude of tw
        final double twLng = 100.491972;
        LatLng latLng = new LatLng(twLat, twLng);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));

        //For Marker School
        mMap.addMarker(new MarkerOptions()
        .position(latLng)
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.tw_logo48))
        .title("โรงเรียนตากฟ้าวิชาประสิทธิ์")
        .snippet("Smart Clever"));

    }//onMap

}   //Main Class
