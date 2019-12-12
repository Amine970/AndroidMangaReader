package com.example.mangareader.view.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mangareader.R;
import com.example.mangareader.model.Manga;
import com.example.mangareader.view.adapter.MangaListAdapter;
import com.example.mangareader.viewmodel.MangaViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllMangasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllMangasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllMangasFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private MangaViewModel mangaViewModel;
    public AllMangasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllMangasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllMangasFragment newInstance(String param1, String param2) {
        AllMangasFragment fragment = new AllMangasFragment();
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
    private static final String TAG = "debugging";
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final MangaListAdapter adapter = new MangaListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mangaViewModel = ViewModelProviders.of(this).get(MangaViewModel.class);
        mangaViewModel.getAllMangas().observe(this, new Observer<List<Manga>>() {
            @Override
            public void onChanged(List<Manga> mangas) {
                adapter.setMangas(mangas);
                adapter.setOnItemClickListener(new MangaListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        //Log.i(TAG, "onItemClick: dans on changed postion envoie -> " + position + " author -> " + mangas.get(position).getAuthor());
                        MangaDetailsFragment mangaDetailsFragment = new MangaDetailsFragment();
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        Bundle arguments = new Bundle();
                        //arguments.putString("id", mangas.get(position));
                        arguments.putParcelable("Manga", mangas.get(position));
                        mangaDetailsFragment.setArguments(arguments);
                        fragmentTransaction.replace(R.id.fragment_container, mangaDetailsFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all, container, false);
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
