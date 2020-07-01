package pk.edu.pucit.mcproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Objects;

public class SelectedCategory extends AppCompatActivity {

    private String[] categorys = {"Famous Cities", "Valleys", "Sea View","Historical Places" , "Desert"};
    private String[][] details = {{"Karachi", "Lahore", "Faisalabad", "Multan", "Hyderabad", "Gujranwala", "Peshawar", "Quetta", "Islamabad"},
            {"Shigar Valley", "Gilgit Valley", "Hunza Valley", "Nagar Valley", "Skardu Valley", "Rupal Valley", "Yasin Valley", "Naltar Valley", "Bagrot Valley", "Chiporsun Valley"},
            {"Karachi SeaView", "Balochistan SeaView"},
            {"Begum Shahi Mosque", "Mohenjo-Daro", "Rohtas Fort", "Taxila", "Wazir Khan Mosque", "Katasraj Temples", "Lahore Fort", "Kot Diji", "Takht-i-Bhai", "Hiran Minar", "Ranikot Fort", "Nagarparkar Jain Temples", "Tomb of Jahangir"},
            {"Kharan Desert", "Thar Desert", "Cholistan Desert", "Cold Desert, Skardu"}
            };
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);
        //setting your ow toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setting back button
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow); // Set the icon

        // Icon click listener
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //to get value passed to activity
        Bundle b = getIntent().getExtras();
        if(b != null)
            position = b.getInt("Position");
        getSupportActionBar().setTitle(categorys[position]);

        //Recycler View
        RecyclerView vrv = findViewById(R.id.rv_vertical); // vrv:vertical recycler view
        vrv.setLayoutManager(new GridLayoutManager(this, 3));
        vrv.setHasFixedSize(true);
        HomeRVAdapter homeRVAdapter1 = new HomeRVAdapter(this, details[position]);
        vrv.setAdapter(homeRVAdapter1);
        homeRVAdapter1.setOnItemClickListener(new HomeRVAdapter.OnItemClickListener() {
            int pos = position;

            @Override
            public void onItemClick(int position) {
                //here corresponding activity will be opened
                if(pos == 0) {
                    makeToast(details[pos][position], true);
                }else{
                    Intent intent = new Intent(getApplicationContext(),PlaceDetailActivity.class);
                    intent.putExtra("AppBarTitle",details[pos][position]);
                    startActivity(intent);
                }
            }
        });

    }
    private void makeToast(String MSG, Boolean duration) {
        Toast.makeText(this, MSG, duration ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
    }
}