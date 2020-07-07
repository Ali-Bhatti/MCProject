package pk.edu.pucit.mcproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class City_details extends AppCompatActivity {

    private ViewPager placeimageviewPager;
    private TabLayout viewPagerindicator;
    private String[] famousPlaces = {"Badshahi Mosque", "Minar-e-Pakistan", "Shahi Qala", "Behria Grand Mosque", "Anarkli", "Masjid wazir khan", "Mochi gate", "Hunza vali", "Naran", "Lahore Food-Street"};

    private String AppBarName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_details);

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




        //show famous places in a city

        RecyclerView vrv = findViewById(R.id.rv_vertical1); // vrv:vertical recycler view
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this, 3);
        vrv.setLayoutManager(gridLayoutManager);

        vrv.setHasFixedSize(true);
        HomeRVAdapter homeRVAdapter1 = new HomeRVAdapter(this, famousPlaces);
        vrv.setAdapter(homeRVAdapter1);
        homeRVAdapter1.setOnItemClickListener(new HomeRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //here corresponding activity will be opened
                Intent intent = new Intent(getApplicationContext(), PlaceDetailActivity.class);
                intent.putExtra("AppBarTitle" , famousPlaces[position]);
                startActivity(intent);

            }
        });

        vrv.setNestedScrollingEnabled(false);


    }



}