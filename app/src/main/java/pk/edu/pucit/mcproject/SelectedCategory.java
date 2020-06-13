package pk.edu.pucit.mcproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.Objects;

public class SelectedCategory extends AppCompatActivity {

    private String[] categorys = {"Famous Cities", "Valleys", "Sea View", "Desert", "Historical Places"};
    private String[][] details = {{"Karachi", "Lahore", "Faisalabad", "Multan", "Hyderabad", "Gujranwala", "Peshawar", "Quetta", "Islamabad"},
            {"Shigar Valley", "Gilgit Valley", "Hunza Valley", "Nagar Valley", "Skardu Valley", "Rupal Valley", "Yasin Valley", "Naltar Valley", "Bagrot Valley", "Chiporsun Valley"},
            {"Karachi SeaView", "Balochistan SeaView"},
            {"Kharan Desert", "Thar Desert", "Cholistan Desert", "Cold Desert, Skardu"},
            {"Begum Shahi Mosque", "Mohenjo-Daro", "Rohtas Fort", "Taxila", "Wazir Khan Mosque", "Katasraj Temples", "Lahore Fort", "Kot Diji", "Takht-i-Bhai", "Hiran Minar", "Ranikot Fort", "Nagarparkar Jain Temples", "Tomb of Jahangir"}};
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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
                makeToast(details[pos][position], true);
            }
        });

    }
    private void makeToast(String MSG, Boolean duration) {
        Toast.makeText(this, MSG, duration ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
    }
}