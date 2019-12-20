package com.example.mangareader.view.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mangareader.R;
import com.example.mangareader.model.data.Page;
import com.example.mangareader.view.adapter.ViewPagerAdapter;
import com.example.mangareader.viewmodel.PagesViewModel;
import com.example.mangareader.viewmodel.factories.PagesViewModelFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PagesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PagesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PagesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PagesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PagesFragment newInstance(String param1, String param2) {
        PagesFragment fragment = new PagesFragment();
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
            chapterID = getArguments().getString("chapterID");
        }
    }
    private String chapterID;
    private LiveData<List<Page>> pagesURLS;
    private static final String TAG = "debugging";
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: CHAPTER ID -> " + chapterID);
        ViewPager viewPager = view.findViewById(R.id.manga_pages_viewpager);

        PagesViewModelFactory pagesViewModelFactory =
                new PagesViewModelFactory(getActivity().getApplication(), chapterID);
        PagesViewModel pagesViewModel = ViewModelProviders
                        .of(this, pagesViewModelFactory)
                        .get(PagesViewModel.class);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getContext());
        viewPager.setAdapter(adapter);
        pagesURLS = pagesViewModel.getPages();
        pagesURLS.observe(getActivity(), new Observer<List<Page>>() {
            @Override
            public void onChanged(List<Page> pages) {
                adapter.setImageUrls(pages.stream()
                        .map(Page::getImageURL)
                        .collect(Collectors.toList()));
                //Log.i(TAG, "onChanged: CHANGED !!\n\n");
//                if(pages != null) {
//                    for(Page page : pages)
//                        Log.i(TAG, "onChanged: page -> " + page.getImageURL());
//                }
            }
        });


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manga_pages, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
