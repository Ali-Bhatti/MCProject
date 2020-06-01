package pk.edu.pucit.mcproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeRVAdapter extends RecyclerView.Adapter<HomeRVAdapter.ViewHolder> {
    private Context context;

    private String [] placeImgs;
    private String [] placeNames;
    HomeRVAdapter(Context context, String[] placeNames){
        this.context = context;
        this.placeNames = placeNames;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Layout Inflation is done
        View rowView = LayoutInflater.from(context).inflate(R.layout.places_category_layout ,null);
        Log.i("Inflater:" ,"Done" );
        return new HomeRVAdapter.ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.placeName.setText(placeNames[position]);
    }

    @Override
    public int getItemCount() {
        return placeNames.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView placeImg;
        public TextView placeName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImg = itemView.findViewById(R.id.placeImg);
            placeName = itemView.findViewById(R.id.placeName);
        }
    }
}
