package com.example.eduh_mik.schoolconnect.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduh_mik.schoolconnect.R;
import com.example.eduh_mik.schoolconnect.adapters.GalleryAdapter;
import com.example.eduh_mik.schoolconnect.interfaces.OnFragmentInteractionListener;
import com.example.eduh_mik.schoolconnect.models.Gallery;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eduh_mik on 4/21/2018.
 */

public class GalleryFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //Unbinder unbinder;
    private OnFragmentInteractionListener mListener;
    private GalleryAdapter galleryAdapter;
    private List<Gallery> galleryList = new ArrayList<>();


    public GalleryFragment() {
        // Required empty public constructor
    }

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        prepareGalleryData(getContext());
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(getLayoutManager(getActivity()));
        galleryAdapter = new GalleryAdapter(getActivity(), galleryList);
        recyclerView.setAdapter(galleryAdapter);
        prepareGalleryData(getContext());
    }
    private void prepareGalleryData(Context context) {
        Gallery gallery = new Gallery("Prize giving", ContextCompat.getDrawable(context, R.drawable.image1));
        galleryList.add(gallery);
        gallery = new Gallery("Prize giving", ContextCompat.getDrawable(context, R.drawable.image1));
        galleryList.add(gallery);
        gallery = new Gallery("Prize giving", ContextCompat.getDrawable(context, R.drawable.image1));
        galleryList.add(gallery);
        gallery = new Gallery("Prize giving", ContextCompat.getDrawable(context, R.drawable.image1));
        galleryList.add(gallery);
        gallery = new Gallery("Prize giving", ContextCompat.getDrawable(context, R.drawable.image1));
        galleryList.add(gallery);
        gallery = new Gallery("Prize giving", ContextCompat.getDrawable(context, R.drawable.image1));
        galleryList.add(gallery);
        gallery = new Gallery("Prize giving", ContextCompat.getDrawable(context, R.drawable.image1));
        galleryList.add(gallery);
        gallery = new Gallery("Prize giving", ContextCompat.getDrawable(context, R.drawable.image1));
        galleryList.add(gallery);


        galleryAdapter.notifyDataSetChanged();
    }

    public static GridLayoutManager getLayoutManager(Context context)
    {
        GridLayoutManager l;
        if(checkIfPhone(context))
        {
            if(getScreenOrientation(context)==0){l = new GridLayoutManager(context, 3); return l;}
            if(getScreenOrientation(context)==1){l = new GridLayoutManager(context, 3); return l;}
            if(getScreenOrientation(context)==2){l = new GridLayoutManager(context, 5); return l;}
        }
        else
        {
            if(getScreenOrientation(context)==0){l = new GridLayoutManager(context, 4); return l;}
            if(getScreenOrientation(context)==1){l = new GridLayoutManager(context, 4); return l;}
            if(getScreenOrientation(context)==2){l = new GridLayoutManager(context, 5); return l;}
        }
        return  new GridLayoutManager(context, 1);

    }
    public static int getScreenOrientation(Context context)
    {
        Display getOrient=((Activity)context).getWindowManager().getDefaultDisplay();
        int orientation= Configuration.ORIENTATION_UNDEFINED;
        if(getOrient.getWidth()==getOrient.getHeight())
        {
            orientation=Configuration.ORIENTATION_SQUARE; //0 for square orientation
        }
        else
        {
            if(getOrient.getWidth()<getOrient.getHeight())
            {
                orientation=Configuration.ORIENTATION_PORTRAIT; //1 for potrait
            }
            else
            {
                orientation=Configuration.ORIENTATION_LANDSCAPE;  //2 for landscape
            }
        }
        return orientation;
    }

    public static Boolean checkIfPhone(Context context)
    {
        DisplayMetrics metrics=new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float yInches=metrics.heightPixels/metrics.ydpi;
        float xInches=metrics.widthPixels/metrics.xdpi;
        double diagonalInches=Math.sqrt(xInches*xInches+yInches*yInches);
        if(diagonalInches>6.5)  //6.5 inch device or bigger
        {
            return false;
        }
        else    //standard device
        {
            return true;
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            mListener = (OnFragmentInteractionListener) context;
        }else {
            throw new RuntimeException(context.toString()
                    + "must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
