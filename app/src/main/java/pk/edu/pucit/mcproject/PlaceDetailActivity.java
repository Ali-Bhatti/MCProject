package pk.edu.pucit.mcproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class PlaceDetailActivity extends AppCompatActivity {
    private ViewPager placeimageviewPager;
    private TabLayout viewPagerindicator;
    private String AppBarName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        //setting your ow toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //Setting back button of appbar
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow); // Set the icon

        // Icon click listener
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //to get value passed to activity
        Bundle b = getIntent().getExtras();

        if(b != null)
            AppBarName = b.getString("AppBarTitle");
        getSupportActionBar().setTitle(AppBarName);



        placeimageviewPager=findViewById(R.id.PlaceViewPager);
        viewPagerindicator=findViewById(R.id.view_pager_indicator);
        List<Integer> PlaceImages=new ArrayList<>();
        PlaceImages.add(R.drawable.image1);
        PlaceImages.add((R.drawable.image2));
        PlaceImages.add((R.drawable.image4));

        PlaceImageAdapter placeImageAdapter=new PlaceImageAdapter(PlaceImages);
        placeimageviewPager.setAdapter(placeImageAdapter);
        viewPagerindicator.setupWithViewPager(placeimageviewPager,true);

    }
}
