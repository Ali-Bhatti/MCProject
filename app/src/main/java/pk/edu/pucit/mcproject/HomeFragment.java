package pk.edu.pucit.mcproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {
    private String[] famousPlaces = {"Badshahi Mosque", "Minar-e-Pakistan", "Shahi Qala", "Behria Grand Mosque", "Anarkli", "Masjid wazir khan", "Mochi gate", "Hunza vali", "Naran", "Lahore Food-Street"};
    //private String[] categorys = {"Capitals", "Food", "Mountain Areas", "Sea View", "Desert", "Cultural Places", "Ancient Places"};
    private String[] categorys = {"Famous Cities", "Valleys", "Sea View", "Desert", "Historical Places"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // First Recycler View
        RecyclerView hrv = view.findViewById(R.id.rv_horizontal); // hrv:horizontal recycler view
        hrv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL); // set Horizontal Orientation
        hrv.setLayoutManager(llm); // set LayoutManager to RecyclerView

        HomeRVAdapter homeRVAdapter = new HomeRVAdapter(getContext(), categorys);
        hrv.setAdapter(homeRVAdapter);
        homeRVAdapter.setOnItemClickListener(new HomeRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.i("Pressed",categorys[position]);
                loadFragment(new SelectedCategoryFragment(position));
            }
        });

        // Second Recycler View
        RecyclerView vrv = view.findViewById(R.id.rv_vertical); // vrv:vertical recycler view
        vrv.setLayoutManager(new GridLayoutManager(getContext() , 3));
        vrv.setHasFixedSize(true);
        HomeRVAdapter homeRVAdapter1 = new HomeRVAdapter(getContext() , famousPlaces);
        vrv.setAdapter(homeRVAdapter1);
        homeRVAdapter1.setOnItemClickListener(new HomeRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //here corresponding activity will be opened
                makeToast(famousPlaces[position], true);
            }
        });

        hrv.setNestedScrollingEnabled(false);
        vrv.setNestedScrollingEnabled(false);
//        recyclerView1.setVisibility(View.VISIBLE);
//        recyclerView.setVisibility(View.GONE);

    /*  ListView listView = view.findViewById(R.id.list);
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1 , items);
        listView.setAdapter(arrayAdapter);*/

        return view;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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