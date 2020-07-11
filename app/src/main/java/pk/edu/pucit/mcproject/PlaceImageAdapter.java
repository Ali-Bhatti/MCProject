package pk.edu.pucit.mcproject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

class PlaceImageAdapter extends PagerAdapter {
    private List<Integer> PlaceImages;

    PlaceImageAdapter(List<Integer> placeImages) {
        PlaceImages = placeImages;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView PlaceImage=new ImageView(container.getContext());
        PlaceImage.setImageResource(PlaceImages.get(position));
        PlaceImage.setScaleType(ImageView.ScaleType.FIT_XY);
        PlaceImage.setElevation(25);
        container.addView(PlaceImage,0);
        return PlaceImage;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }

    @Override
    public int getCount() {
        return PlaceImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
}
