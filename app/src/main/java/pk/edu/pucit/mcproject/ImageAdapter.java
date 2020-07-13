package pk.edu.pucit.mcproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;
    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.show_images, parent, false);
        return new ImageViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.placeName.setText(uploadCurrent.getPlaceName());
        holder.category.setText(uploadCurrent.getCategory());
        holder.details.setText(uploadCurrent.getAboutPlace());
        holder.UserName.setText(uploadCurrent.getUserName());
        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return mUploads.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView placeName;
        public TextView category;
        public TextView details;
        public ImageView imageView;
        public TextView UserName;
        public ImageViewHolder(View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.Place_Name);
            category = itemView.findViewById(R.id.Category);
            details = itemView.findViewById(R.id.details);
            imageView = itemView.findViewById(R.id.image_View);
            UserName=itemView.findViewById(R.id.User_Name);
        }
    }
}
