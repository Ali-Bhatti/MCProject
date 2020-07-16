package pk.edu.pucit.mcproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static pk.edu.pucit.mcproject.Data.famousPlacesOfCites;
import static pk.edu.pucit.mcproject.Data.famousPlacesOfCitesUrls;
import static pk.edu.pucit.mcproject.Home.isConnected;

public class City_details extends AppCompatActivity {
    public static int IMAGE_DISPLAY_COUNT = 5;


    private TabLayout viewPagerindicator;
    //private String[] cites = {"Islamabad" ,"Lahore", "Karachi",  "Faisalabad", "Multan", "Hyderabad", "Gujranwala", "Peshawar", "Quetta"};
    /*private String[] []famousPlacesOfCites = {
            {"Faisal Masjid","Centaurus","Daman-e-Koh","The Monal Restaurant","Islamabad Zoo" ,"Lok Virsa Museum","Margalla Hills","Rawal Lake"},
            {"Badshahi Mosque", "Minar-e-Pakistan", "Lahore Fort", " Bahria Grand Mosque ", "Anarkli Bazar", "Masjid wazir khan", "Mochi gate", "Fort Road Food Street"},
            {"National Museum Karachi","Mazar-e-Quaid","Jehangir Kothari Parade","Masjid-e-Tooba","Clifton Beach","Aziz Bhatti Park","Baba and Bhit Islands"},
            {"Faisalabad Clock Tower","Faisalabad Railway Station","Gumti Water Fountain","Allama Iqbal Library","Lyallpur Museum"},
            {"Tomb Shah Rukne Alam","Ghanta Ghar Multan","Tomb Shah Shams Tabrez","Qasim Bagh"},
            {"Market Tower (Ghanta Ghar)", "Mukkhi House" ,"Eidgah Masjid","Pacco Qillo","Tombs of Mirs"},
            {"Ranjeet Singh Haveli" ,"Darziyan Vali Kothi"},
            {"Bala Hisar Fort","Peshawar Museum","Masjid Mahabat Khan","Jamrud Fort","Bab-e-Khyber","Sir Cunningham Clock Tower","Peshawar Zoo","Kanishka Stupa Fort"},
            {"Hanna Lake","Hanna-Urak Waterfall","Askari Park Quetta", "Murdar Mountain"}
            };*/
    private ViewPager placeimageviewPager;
    private String AppBarName = "";
    private String encoding = "UTF-8";
    private ProgressBar progressBar;
    private TextView txtWikiData;
    private String keyword;
    private NestedScrollView nestedScrollView;
    private RecyclerView vrv;
    private HomeRVAdapter homeRVAdapter;
    ArrayList<PlaceItem> citiesFamousPlacesItems = new ArrayList<>();
    ArrayList<String> PlaceImageUrls;
    private int position;
    private PlaceImageAdapter placeImageAdapter;
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
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        //to get value passed to activity
        Bundle b = getIntent().getExtras();

        if(b != null) {
            AppBarName = b.getString("AppBarTitle");
            position = b.getInt("position");
        }
        getSupportActionBar().setTitle(AppBarName);


        placeimageviewPager=findViewById(R.id.PlaceViewPager);
        viewPagerindicator=findViewById(R.id.view_pager_indicator);
        PlaceImageUrls=new ArrayList<>();
        placeImageAdapter=new PlaceImageAdapter(PlaceImageUrls);
        placeimageviewPager.setAdapter(placeImageAdapter);
        String url = "https://bing-image-search1.p.rapidapi.com/images/search?q=" + AppBarName.replace(" ", "%20");
        OkHttpHandler okHttpHandler= new OkHttpHandler();
        okHttpHandler.execute(url);

        viewPagerindicator.setupWithViewPager(placeimageviewPager,true);


        //show famous places in a city
        vrv = findViewById(R.id.rv_vertical1); // vrv:vertical recycler view
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this, 3);
        vrv.setLayoutManager(gridLayoutManager);
        vrv.setHasFixedSize(true);

        for(int i = 0 ; i < famousPlacesOfCites[position].length;i++){
            citiesFamousPlacesItems.add(new PlaceItem(famousPlacesOfCites[position][i], famousPlacesOfCitesUrls[position][i]));
        }

        homeRVAdapter = new HomeRVAdapter(this, citiesFamousPlacesItems);
        vrv.setAdapter(homeRVAdapter);
        homeRVAdapter.setOnItemClickListener(new HomeRVAdapter.OnItemClickListener() {
            int pos = position;
            @Override
            public void onItemClick(int position) {
                if(!isConnected(getApplicationContext())){
                    Toast.makeText(getApplicationContext(), "Internet Connection Problem.\n Check your Internet Connection.", Toast.LENGTH_SHORT).show();
                }else {
                    //here corresponding activity will be opened
                    Intent intent = new Intent(getApplicationContext(), PlaceDetailActivity.class);
                    intent.putExtra("AppBarTitle", famousPlacesOfCites[pos][position]);
                    startActivity(intent);
                }

            }
        });
        vrv.setNestedScrollingEnabled(false);

        txtWikiData = findViewById(R.id.txt_city_detail);
        nestedScrollView = findViewById(R.id.nested_scroll);
        // Enabling Scroll on "TextView" which is inside the "Nested Scroll View"
        txtWikiData.setMovementMethod(new ScrollingMovementMethod());
        nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                txtWikiData.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        txtWikiData.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                txtWikiData.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });



        progressBar = findViewById(R.id.progressBar2);
        keyword = AppBarName;

        String WIKIPEDIA_URL = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=" + keyword;
        // Start AsyncTask
        Content content = new Content();
        content.execute(WIKIPEDIA_URL);

    }


    private class Content extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            //Toast.makeText(getApplicationContext(), "Fetching Data. Please wait.", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String[] params) {
            try {
                if(!isInternetAvailable()){
                    return "No Internet Connection";
                }
                String sURL = params[0];

                URL url = new URL(sURL);        // Convert String URL to java.net.URL
                // Connection: to Wikipedia API
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, encoding);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String wikiData = stringBuilder.toString();
                // Parse JSON Data
                return parseJSONData(wikiData);

            } catch (IOException e) {
                e.printStackTrace();
                PrintOnLog("Error", e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String formattedData) {
            super.onPostExecute(formattedData);
            progressBar.setVisibility(View.GONE);
            if(formattedData.equals("No Internet Connection")){
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Internet Connection Problem.\n Check your Internet Connection.", Toast.LENGTH_SHORT).show();
            }else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // HTML Data
                    txtWikiData.setText(Html.fromHtml
                            (formattedData, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    // HTML Data
                    Spanned data = Html.fromHtml(formattedData);
                    txtWikiData.setText(data);
                }
            }
        }
    }

    private String parseJSONData(String wikiData) {
        try {
            // Convert String JSON (wikiData) to JSON Object
            JSONObject rootJSON = new JSONObject(wikiData);
            JSONObject query = rootJSON.getJSONObject("query");
            JSONObject pages = query.getJSONObject("pages");
            JSONObject number = pages.getJSONObject(pages.keys().next());

            return number.getString("extract");
        } catch (JSONException json) {
            json.printStackTrace();
        }

        return null;
    }

    private void PrintOnLog(String tag , String message){
        Log.i(tag,message);
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

    public class OkHttpHandler extends AsyncTask<String , Void , String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            okhttp3.Request.Builder builder  = new okhttp3.Request.Builder()
                    .addHeader("x-rapidapi-host", "bing-image-search1.p.rapidapi.com")
                    .addHeader("x-rapidapi-key", "6f4a09b0b9msh9683c62eff965e5p16d1c3jsn5a261aa97615")
                    .url(params[0]);
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                // making a json object
                JSONObject jsonObject = new JSONObject(s);
                // getting an array of json objects with key "value"
                JSONArray value = jsonObject.getJSONArray("value");
                //extracting one JSON object from JSON Array
                for (int i = 0; i < IMAGE_DISPLAY_COUNT; i++) {
                    JSONObject obj1 = value.getJSONObject(i);
                    String Url = obj1.getString("contentUrl");
                    PlaceImageUrls.add(Url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            placeImageAdapter.notifyDataSetChanged();

        }
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

    }
}