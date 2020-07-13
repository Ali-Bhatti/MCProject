package pk.edu.pucit.mcproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
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

import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class PlaceDetailActivity extends AppCompatActivity {
    private ViewPager placeImageViewPager;
    private TabLayout viewPagerIndicator;
    private String AppBarName = "";
    private Toolbar toolbar;
    private TextView txtWikiData;
    private static final String encoding = "UTF-8";
    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        //setting your own toolbar
        toolbar = findViewById(R.id.my_toolbar);
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

        placeImageViewPager = findViewById(R.id.PlaceViewPager);
        viewPagerIndicator = findViewById(R.id.view_pager_indicator);
        List<Integer> PlaceImages = new ArrayList<>();
        PlaceImages.add(R.drawable.image1);
        PlaceImages.add((R.drawable.image2));
        PlaceImages.add((R.drawable.image4));

        PlaceImageAdapter placeImageAdapter = new PlaceImageAdapter(PlaceImages);
        placeImageViewPager.setAdapter(placeImageAdapter);
        viewPagerIndicator.setupWithViewPager(placeImageViewPager, true);

        txtWikiData = findViewById(R.id.txt_city_detail);
        progressBar = findViewById(R.id.progressBar);
        nestedScrollView = findViewById(R.id.nested_scroll2);
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


        Content content = new Content();
       content.execute(AppBarName);

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

                String keyword= params[0];
                if(!isInternetAvailable()){
                    return "No Internet Connection";
                }

                //Search the google for Wikipedia Links
                Document google = Jsoup.connect("https://www.google.com/search?q=" + URLEncoder.encode(keyword + "wikipedia", encoding)).get();

                //Get the first link about Wikipedia
                String wikipediaURL = google.getElementsByTag("cite").get(0).text();
                // reading last "search keywords"
                String searchWord = wikipediaURL.substring(25);

                //Use Wikipedia API to get JSON File
                String wikipediaApiJSON = "https://www.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles="
                        + URLEncoder.encode(searchWord, encoding);

                PrintOnLog("WikiURL", wikipediaApiJSON);

                URL url = new URL(wikipediaApiJSON);        // Convert String URL to java.net.URL
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
               Toast.makeText(getApplicationContext(), formattedData, Toast.LENGTH_SHORT).show();
           }
           else {
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                   // HTML Data
                   //txtWikiData.setText(HtmlCompat.fromHtml(formattedData, HtmlCompat.FROM_HTML_MODE_LEGACY));
                   txtWikiData.setText(formattedData);
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
            String formattedData = number.getString("extract");

            PrintOnLog("Data",formattedData);
            return formattedData;
        } catch (JSONException json) {
            json.printStackTrace();
        }

        return null;
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
    private void PrintOnLog(String tag , String message){
        Log.i(tag,message);
    }

    private void LoadGoogleMap(String placeName, boolean isRestaurant) {
        Uri gmmIntentUri = isRestaurant ? Uri.parse("geo:0,0?q=restaurants " + placeName + ", Pakistan") : Uri.parse("google.navigation:q=" + placeName + ", Pakistan");
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
