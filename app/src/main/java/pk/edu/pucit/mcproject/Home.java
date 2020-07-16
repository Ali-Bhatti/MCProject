package pk.edu.pucit.mcproject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.net.InetAddress;
import java.util.ArrayList;

import static pk.edu.pucit.mcproject.Data.categoryUrls;
import static pk.edu.pucit.mcproject.Data.categorys;
import static pk.edu.pucit.mcproject.Data.famousPlaces;
import static pk.edu.pucit.mcproject.Data.famousPlacesUrls;

public class Home extends Fragment {

    private RecyclerView hrv;
    private RecyclerView vrv;
    private HomeRVAdapter homeRVAdapter;
    private HomeRVAdapter homeRVAdapter1;
    ArrayList<PlaceItem> categoryItems = new ArrayList<>();
    ArrayList<PlaceItem> famousPlacesItems = new ArrayList<>();
    //public static String[] famousPlaces = {"Badshahi Mosque", "Minar-e-Pakistan", "Faisal Masjid" , "Lahore Fort","Mohenjo-Daro", "Gilgit Valley", "Hunza Valley" ,"Bahria Grand Mosque ", "Anarkli", "Masjid wazir khan", "Mochi gate", "Mazar-e-Quaid", "Naran", "Fort Road Food Street"};
    // public static String[] categorys = {"Famous Cities", "Valleys", "Sea View", "Historical Places" , "Desert"};
    ProgressBar progressBarVrv;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        progressBarVrv = view.findViewById(R.id.home_vrv_progressBar);
        if(!isConnected()){
            Toast.makeText(getContext(), "Internet Connection Problem. Check your Internet Connection.", Toast.LENGTH_SHORT).show();
            return view;
        }
        progressBarVrv.setVisibility(View.GONE);
        // First Recycler View
        hrv = view.findViewById(R.id.rv_horizontal); // hrv:horizontal recycler view
        hrv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL); // set Horizontal Orientation
        hrv.setLayoutManager(llm); // set LayoutManager to RecyclerView
        for(int i = 0 ; i < categorys.length;i++){
            categoryItems.add(new PlaceItem(categorys[i], categoryUrls[i]));
        }
        homeRVAdapter = new HomeRVAdapter(getContext(), categoryItems);
        hrv.setAdapter(homeRVAdapter);
        homeRVAdapter.setOnItemClickListener(position -> {
            Log.i("Pressed", categorys[position]);
            //changing title of appbar according to category selected
            Intent intent = new Intent(getContext(),SelectedCategory.class);
            intent.putExtra("Position",position); // passing value to activity
            startActivity(intent);
        });

        // Second Recycler View
        vrv = view.findViewById(R.id.rv_vertical); // vrv:vertical recycler view
        vrv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        vrv.setHasFixedSize(true);
        for(int i = 0 ; i < famousPlaces.length;i++){
            famousPlacesItems.add(new PlaceItem(famousPlaces[i], famousPlacesUrls[i]));
        }

        homeRVAdapter1 = new HomeRVAdapter(getContext(), famousPlacesItems);
        vrv.setAdapter(homeRVAdapter1);
        homeRVAdapter1.setOnItemClickListener(position -> {
            //here corresponding activity will be opened
            Intent intent = new Intent(getContext() , PlaceDetailActivity.class);
            intent.putExtra("AppBarTitle" , famousPlaces[position]);
            startActivity(intent);
        });

        hrv.setNestedScrollingEnabled(false);
        vrv.setNestedScrollingEnabled(false);
        //recyclerView1.setVisibility(View.VISIBLE);
        //recyclerView.setVisibility(View.GONE);

        /*  ListView listView = view.findViewById(R.id.list);
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1 , items);
        listView.setAdapter(arrayAdapter);*/
        return view;
    }
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }
    // length = true means SHORT
    // length = false means LONG
    private void makeToast(String MSG, Boolean duration) {
        Toast.makeText(getContext(), MSG, duration ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
    }
    /* @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.i("Task done: ", "onCreate");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.my_menu,menu);
        Log.i("Task done: ", "onCreateOptionsMenu");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.i("Task in progress: ", "onOptionsItemSelected");
        int id = item.getItemId();
        if(id == R.id.search_icon){
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setQueryHint("Search Place here");
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    arrayAdapter.getFilter().filter(newText);
                    return true ;
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}

