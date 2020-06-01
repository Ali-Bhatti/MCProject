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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {
    private ArrayAdapter<String> arrayAdapter;
    private String [] items = {"Badshahi Mosque","Minar-e-Pakistan","Shahi Qala","Behria Grand Mosque","Anarkli","Masjid wazir khan","Mochi gate","Hunza vali","Naran", "Lahore Food-Street"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext() , 3));

        HomeRVAdapter homeRVAdapter = new HomeRVAdapter(getContext(),items);
        recyclerView.setAdapter(homeRVAdapter);

    /*  ListView listView = view.findViewById(R.id.list);
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1 , items);
        listView.setAdapter(arrayAdapter);*/

        return view;
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
