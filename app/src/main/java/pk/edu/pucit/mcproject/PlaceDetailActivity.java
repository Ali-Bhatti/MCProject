package pk.edu.pucit.mcproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class PlaceDetailActivity extends AppCompatActivity {
    private ViewPager placeimageviewPager;
    private TabLayout viewPagerindicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);


       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setActionBar(toolbar);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
