package pk.edu.pucit.mcproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PlaceDetailActivity extends AppCompatActivity {
    private ViewPager placeimageviewPager;
    private TabLayout viewPagerindicator;
    private String AppBarName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        //setting your own toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        //Setting back button of appbar
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow); // Set the icon
        // Back Icon click listener
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //to get value passed to activity
        Bundle b = getIntent().getExtras();
        if (b != null)
            AppBarName = b.getString("AppBarTitle");
        Objects.requireNonNull(getSupportActionBar()).setTitle(AppBarName);

        placeimageviewPager = findViewById(R.id.PlaceViewPager);
        viewPagerindicator = findViewById(R.id.view_pager_indicator);
        List<Integer> PlaceImages = new ArrayList<>();
        PlaceImages.add(R.drawable.image1);
        PlaceImages.add((R.drawable.image2));
        PlaceImages.add((R.drawable.image4));

        PlaceImageAdapter placeImageAdapter = new PlaceImageAdapter(PlaceImages);
        placeimageviewPager.setAdapter(placeImageAdapter);
        viewPagerindicator.setupWithViewPager(placeimageviewPager, true);


    }

    public void openGoogleMap(final View view) {
        final String placeName = AppBarName;

        // Runtime Permission for location
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        switch (view.getId()) {
                            case R.id.imgBtnDirection:
                                LoadGoogleMap(placeName);
                                break;
                            case R.id.imgBtnResturant:
                                LoadGoogleMap(placeName, true);
                                break;
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast toast = Toast.makeText(getApplicationContext(), Html.fromHtml("Permission to access location is required to open <b>Google Map</b>"), Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private void LoadGoogleMap(String placeName, boolean isResturant) {
        Uri gmmIntentUri = isResturant ? Uri.parse("geo:0,0?q=restaurants " + placeName + ", Pakistan") : Uri.parse("google.navigation:q=" + placeName + ", Pakistan");
        loadMapHelper(gmmIntentUri);
    }

    private void LoadGoogleMap(String placeName) {
        LoadGoogleMap(placeName, false);
    }

    private void loadMapHelper(Uri gmmIntentUri) {
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
