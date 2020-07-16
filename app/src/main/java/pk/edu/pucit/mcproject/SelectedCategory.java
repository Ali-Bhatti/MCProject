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

public class SelectedCategory extends AppCompatActivity {

    private String[] categorys = {"Famous Cities", "Valleys", "Sea View","Historical Places" , "Desert"};

    private String[][] details = {{"Islamabad" ,"Lahore", "Karachi",  "Faisalabad", "Multan", "Hyderabad", "Gujranwala", "Peshawar", "Quetta"},
            {"Shigar Valley", "Gilgit Valley", "Hunza Valley", "Nagar Valley", "Skardu Valley", "Rupal Valley", "Yasin Valley", "Naltar Valley", "Bagrot Valley", "Chapursan Valley"},
            {"Clifton Sea View", "Gwadar Sea View"},
            {"Begum Shahi Mosque", "Mohenjo-Daro", "Rohtas Fort", "Taxila", "Wazir Khan Mosque", "Katasraj Temples", "Lahore Fort", "Kot Diji", "Takht-i-Bhai", "Hiran Minar", "Ranikot Fort", "Nagarparkar Jain Temples", "Tomb of Jahangir"},
            {"Kharan Desert", "Thar Desert", "Cholistan Desert", "Cold Desert, Skardu"}
            };
    private String[][]detailURLs={
            {
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Faisal_Masjid_From_Damn_e_koh.jpg/800px-Faisal_Masjid_From_Damn_e_koh.jpg",
                    "https://upload.wikimedia.org/wikipedia/commons/5/5e/Minar_e_Pakistan_night_image.jpg",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/0/05/Jinnah_Mausoleum_%28cropped%29.JPG/800px-Jinnah_Mausoleum_%28cropped%29.JPG",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5e/Clocktower_Faisalabad%2C_Panorama.jpg/800px-Clocktower_Faisalabad%2C_Panorama.jpg",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/0/07/Shrine_of_Hazrat_Bahauddin_Zakariya.jpg/800px-Shrine_of_Hazrat_Bahauddin_Zakariya.jpg",
                    "https://etimg.etb2bimg.com/photo/73411431.cms",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2e/Nishan-E-Manzil_Gujranwala_20140925.jpg/800px-Nishan-E-Manzil_Gujranwala_20140925.jpg",
                    "https://pix10.agoda.net/geo/city/3168/1_3168_02.jpg?s=1920x822",
                    "https://i.pinimg.com/originals/04/c5/7c/04c57c2f617820eae4a11bbba99fc1e3.jpg"
            },

            {
                    "https://fcache1.pakwheels.com/original/4X/b/d/3/bd34e5753634d8ef483dc4f166dd76fffde2481e.jpg",
                    "https://www.youlinmagazine.com/articles/silk-road-route-gilgit-valley-2.jpg",
                    "https://media.gettyimages.com/photos/autumn-in-hunza-picture-id174463861?s=612x612",
                    "https://www.natureadventureclub.pk/blog/wp-content/uploads/2019/10/nagar-750x500.jpg",
                    "https://i2.wp.com/www.hunzaexplorers.com/wp-content/uploads/2017/12/skardu-pakistan-hunzaexplorers.jpg?fit=1800%2C1080&ssl=1",
                    "https://1.bp.blogspot.com/-jO7Hq1-Os4Y/Wb1Ffeq7_FI/AAAAAAAAC1A/yp7Gr6bF5xofEtxEzPz8K6KdaN_GDYnWACLcBGAs/s1600/Rupal%2Bvalley.jpg",
                    "https://upload.wikimedia.org/wikipedia/commons/2/2b/Noh_Bridge.jpg",
                    "https://i.dawn.com/primary/2018/08/5b728590affc7.jpg",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Bagrote_Valley_Wheat_Harvest_Chirah.JPG/300px-Bagrote_Valley_Wheat_Harvest_Chirah.JPG",
                    "https://www.ali.net.pk/wp-content/uploads/2018/01/beautiful-chapursan-valley-pakistan-1024x768.jpg"
            },

            {
                    "https://www.samaa.tv/wp-content/uploads/2016/12/IMG_20160416_190232_361-640x480.jpg",
                    "https://images.zameen.com/common/resize.php?img=7/994/sea_view_housing_scheme_14294.jpg&d=1024"
            },

            {
                    "https://c.stocksy.com/a/rbY700/z9/1801339.jpg",
                    "https://assets.atlasobscura.com/media/W1siZiIsInVwbG9hZHMvcGxhY2VfaW1hZ2VzLzYzZmQxMTIzZjFjMTM5YmU2Ml9Bbl9vdGhlcl92aWV3X29mX01vZW5qb2Rhcm8uanBnIl0sWyJwIiwiY29udmVydCIsIiJdLFsicCIsImNvbnZlcnQiLCItcXVhbGl0eSA4MSAtYXV0by1vcmllbnQiXSxbInAiLCJ0aHVtYiIsIjc4MHg1MjAjIl1d",
                    "https://pakiholic.com/wp-content/uploads/2018/01/17-Rohtas-Fort-123.jpg",
                    "https://historypak.com/wp-content/uploads/2014/07/Taxila-11.jpg",
                    "https://dailytimes.com.pk/assets/uploads/2018/07/26/Masjid_Wazeer_Khan_Lahore-1280x720.jpg",
                    "https://s01.sgp1.digitaloceanspaces.com/large/862322-77103-fqikjauxif-1513862827.jpg",
                    "https://www.thenews.com.pk/assets/uploads/updates/2020-01-10/l_596558_115001_updates.jpg",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f4/Kot_Diji_Fort_by_smn-.JPG/1200px-Kot_Diji_Fort_by_smn-.JPG",
                    "https://media-cdn.tripadvisor.com/media/photo-c/2560x500/0c/ce/c6/96/view-of-takht-i-bhai.jpg",
                    "https://media-cdn.tripadvisor.com/media/photo-c/2560x500/11/97/f0/7a/full-view-of-hiran-minar.jpg",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/Ranikot_Fort_-_The_Great_Wall_of_Sindh.jpg/337px-Ranikot_Fort_-_The_Great_Wall_of_Sindh.jpg",
                    "https://upload.wikimedia.org/wikipedia/commons/7/7c/Gori_Mandar.jpg",
                    "https://www.croozi.com/upload/loc1024/TombofJahangir2072016.jpg"
            },

            {
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cb/Libya_4985_Tadrart_Acacus_Luca_Galuzzi_2007.jpg/300px-Libya_4985_Tadrart_Acacus_Luca_Galuzzi_2007.jpg?w=640",
                    "https://static.toiimg.com/photo/68427211/3.jpg?width=748&resize=4",
                    "https://production9240.blob.core.windows.net/photos/c220cb9c-0ea9-4c34-bcfb-d791bde82c6d",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f8/Unexpected_Snow_in_Katpana_Skardu.jpg/800px-Unexpected_Snow_in_Katpana_Skardu.jpg",
            }
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
                //here corresponding activity will be opened
                if(pos == 0) {
                    Intent intent = new Intent(getApplicationContext(),City_details.class);
                    intent.putExtra("AppBarTitle",details[pos][position]);
                    intent.putExtra("position",position);
                    startActivity(intent);
                    //makeToast(details[pos][position], true);
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