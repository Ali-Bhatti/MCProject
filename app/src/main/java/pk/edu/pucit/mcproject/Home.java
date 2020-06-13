package pk.edu.pucit.mcproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String[] famousPlaces = {"Badshahi Mosque", "Minar-e-Pakistan", "Shahi Qala", "Behria Grand Mosque", "Anarkli", "Masjid wazir khan", "Mochi gate", "Hunza vali", "Naran", "Lahore Food-Street"};
    //private String[] categorys = {"Capitals", "Food", "Mountain Areas", "Sea View", "Desert", "Cultural Places", "Ancient Places"};
    private String[] categorys = {"Famous Cities", "Valleys", "Sea View", "Desert", "Historical Places"};


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
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
                Log.i("Pressed", categorys[position]);
                //changing title of appbar according to category selected
                Intent intent = new Intent(getContext(),SelectedCategory.class);
                intent.putExtra("Position",position); // passing value to activity
                startActivity(intent);
            }
        });

        // Second Recycler View
        RecyclerView vrv = view.findViewById(R.id.rv_vertical); // vrv:vertical recycler view
        vrv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        vrv.setHasFixedSize(true);
        HomeRVAdapter homeRVAdapter1 = new HomeRVAdapter(getContext(), famousPlaces);
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
        //recyclerView1.setVisibility(View.VISIBLE);
        //recyclerView.setVisibility(View.GONE);

        /*  ListView listView = view.findViewById(R.id.list);
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1 , items);
        listView.setAdapter(arrayAdapter);*/
        return view;
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
