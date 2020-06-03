package pk.edu.pucit.mcproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectedCategoryFragment extends Fragment {
    private String[] categorys = {"Famous Cities", "Valleys", "Sea View", "Desert", "Historical Places"};
    private String[][] details ={{"Karachi","Lahore","Faisalabad","Multan","Hyderabad","Gujranwala","Peshawar","Quetta","Islamabad"},
                                {"Shigar Valley","Gilgit Valley","Hunza Valley" ,"Nagar Valley","Skardu Valley","Rupal Valley","Yasin Valley","Naltar Valley","Bagrot Valley","Chiporsun Valley"},
                                {"Karachi SeaView","Balochistan SeaView"},
                                {"Kharan Desert","Thar Desert","Cholistan Desert","Cold Desert, Skardu"},
                                {"Begum Shahi Mosque","Mohenjo-Daro","Rohtas Fort","Taxila","Wazir Khan Mosque","Katasraj Temples","Lahore Fort","Kot Diji","Takht-i-Bhai","Hiran Minar","Ranikot Fort","Nagarparkar Jain Temples","Tomb of Jahangir"}};
    final private int position;
    //constructor
    SelectedCategoryFragment(int position){
        this.position = position;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected_category , container,false);
        //Recycler View
        RecyclerView vrv = view.findViewById(R.id.rv_vertical); // vrv:vertical recycler view
        vrv.setLayoutManager(new GridLayoutManager(getContext() , 3));
        vrv.setHasFixedSize(true);
        HomeRVAdapter homeRVAdapter1 = new HomeRVAdapter(getContext() , details[position]);
        vrv.setAdapter(homeRVAdapter1);
        homeRVAdapter1.setOnItemClickListener(new HomeRVAdapter.OnItemClickListener() {
            int pos = position;
            @Override
            public void onItemClick(int position) {
                //here corresponding activity will be opened
                makeToast(details[pos][position], true);
            }
        });
        
        return view;
    }
    private void makeToast(String MSG, Boolean duration) {
        Toast.makeText(getContext(), MSG, duration ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
    }
}
