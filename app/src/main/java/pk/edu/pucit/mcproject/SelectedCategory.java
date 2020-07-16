package pk.edu.pucit.mcproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import static pk.edu.pucit.mcproject.Data.categoryUrls;
import static pk.edu.pucit.mcproject.Data.categorys;
import static pk.edu.pucit.mcproject.Data.detailURLs;
import static pk.edu.pucit.mcproject.Home.isConnected;

public class SelectedCategory extends AppCompatActivity {

    private String[] categorys = {"Famous Cities", "Valleys", "Sea View","Historical Places" , "Desert"};

    private String[][] details = {{"Islamabad" ,"Lahore", "Karachi",  "Faisalabad", "Multan", "Hyderabad", "Gujranwala", "Peshawar", "Quetta"},
            {"Shigar Valley", "Gilgit Valley", "Hunza Valley", "Nagar Valley", "Skardu Valley", "Rupal Valley", "Yasin Valley", "Naltar Valley", "Bagrot Valley", "Chapursan Valley"},
            {"Clifton Sea View", "Gwadar Sea View"},
            {"Begum Shahi Mosque", "Mohenjo-Daro", "Rohtas Fort", "Taxila", "Wazir Khan Mosque", "Katasraj Temples", "Lahore Fort", "Kot Diji", "Takht-i-Bhai", "Hiran Minar", "Ranikot Fort", "Nagarparkar Jain Temples", "Tomb of Jahangir"},
            {"Kharan Desert", "Thar Desert", "Cholistan Desert", "Cold Desert, Skardu"}
            };


    private int position;
    private RecyclerView vrv;
    private HomeRVAdapter homeRVAdapter;
    ArrayList<PlaceItem> categoryDeatilItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);
        //setting your ow toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Setting back button
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        // Icon click listener
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        //to get value passed to activity
        Bundle b = getIntent().getExtras();
        if(b != null)
            position = b.getInt("Position");
        getSupportActionBar().setTitle(categorys[position]);

        //Recycler View
        vrv = findViewById(R.id.rv_vertical); // vrv:vertical recycler view
        vrv.setLayoutManager(new GridLayoutManager(this, 3));
        vrv.setHasFixedSize(true);
        for(int i = 0 ; i < details[position].length;i++){
            categoryDeatilItems.add(new PlaceItem(details[position][i], detailURLs[position][i]));
        }

        homeRVAdapter = new HomeRVAdapter(this, categoryDeatilItems);
        vrv.setAdapter(homeRVAdapter);
        homeRVAdapter.setOnItemClickListener(new HomeRVAdapter.OnItemClickListener() {
            int pos = position;

            @Override
            public void onItemClick(int position) {
                if(!isConnected(getApplicationContext())){
                    Toast.makeText(getApplicationContext(), "Internet Connection Problem.\n Check your Internet Connection.", Toast.LENGTH_SHORT).show();
                }else {
                    //here corresponding activity will be opened
                    if (pos == 0) {
                        Intent intent = new Intent(getApplicationContext(), City_details.class);
                        intent.putExtra("AppBarTitle", details[pos][position]);
                        intent.putExtra("position", position);
                        startActivity(intent);
                        //makeToast(details[pos][position], true);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), PlaceDetailActivity.class);
                        intent.putExtra("AppBarTitle", details[pos][position]);
                        startActivity(intent);
                    }
                }
            }
        });

    }

    private void makeToast(String MSG, Boolean duration) {
        Toast.makeText(this, MSG, duration ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
    }
}