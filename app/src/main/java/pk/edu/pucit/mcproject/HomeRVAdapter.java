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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeRVAdapter extends RecyclerView.Adapter<HomeRVAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PlaceItem> mPlaceItems;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView placeImg;
        public TextView placeName;
        public ViewHolder(@NonNull View itemView , final OnItemClickListener listener) {
            super(itemView);
            placeImg = itemView.findViewById(R.id.placeImg);
            placeName = itemView.findViewById(R.id.placeName);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
    HomeRVAdapter(Context context, ArrayList<PlaceItem> places){
        this.context = context;
        this.mPlaceItems = places;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Layout Inflation is done
        // Layout Inflation is done
        View rowView = LayoutInflater.from(context).inflate(R.layout.places_category_layout ,null);
        Log.i("Inflater:" ,"Done" );
        return new HomeRVAdapter.ViewHolder(rowView , mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlaceItem placeItem = mPlaceItems.get(position);
        holder.placeName.setText(placeItem.getPlaceName());
        String imageUrl = placeItem.getThumbnail();
        Picasso.get().load(imageUrl).fit().centerCrop().into(holder.placeImg);
    }

    @Override
    public int getItemCount() {
        return mPlaceItems.size();
    }


}
