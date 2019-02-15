package com.rasel.newsviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class SlidePagerAdapter extends PagerAdapter {
    private Context context;

    SlidePagerAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return title.length;
    }

    private String[] title = {"FIND RESTAURANT", "PICK THE BEST", "CHOOSE YOUR MEAL"};
    private String[] description = {"Find the best restaurant in your neighborhood",
            "Pick the right place using trusted rating and reviews", "Easily find the type of food your're craving"};

    private int[] imageList = {R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_background,  R.drawable.ic_launcher_foreground};

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);
        ImageView imageView = view.findViewById(R.id.slideImageView);
        TextView textView = view.findViewById(R.id.slideTitle);

        imageView.setImageResource(imageList[position]);
        textView.setText(title[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
