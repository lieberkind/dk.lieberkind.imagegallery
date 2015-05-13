package dk.lieberkind.imagegallery;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThumbnailFragment extends Fragment {

    private ImageCache imageCache = ImageCache.getInstance();

    private ArrayList<Image> images = new ArrayList<>();

    private GridView gridView;

    private OnThumbnailClickedListener mListener;

    public ThumbnailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnThumbnailClickedListener) activity;
        } catch(ClassCastException e) {
            throw new ClassCastException("Containing activities must be instances OnThumbnailClickedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null && !imageCache.empty()) {
            images = imageCache.all();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thumbnail, container, false);

        gridView = (GridView) view.findViewById(R.id.thumbnails);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onThumbnailClicked(position);
            }
        });

        setImages(images);

        return view;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;

        ArrayList<Bitmap> bitmaps = new ArrayList<>();

        for(Image image : images) {
            bitmaps.add(image.getBitmap());
        }

        gridView.setAdapter(new ImageAdapter(getActivity(), bitmaps));
    }

    /**
     * Interface which containing activities of this fragment must implement
     */
    public interface OnThumbnailClickedListener {

        /**
         * Handle the thumbnail clicked event
         *
         * @param position The position of the thumbnail clicked in the list of thumbnails
         */
        public void onThumbnailClicked(int position);
    }

    private class ImageAdapter extends BaseAdapter {

        private ArrayList<Bitmap> mImages;

        private Context mContext;

        public ImageAdapter(Context context, ArrayList<Bitmap> images) {
            mContext = context;
            mImages = images;
        }

        @Override
        public int getCount() {
            return mImages.size();
        }

        @Override
        public Object getItem(int position) {
            return mImages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;

            if(convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView =  (ImageView) convertView;
            }

            imageView.setImageBitmap(mImages.get(position));
            return imageView;
        }
    }
}
