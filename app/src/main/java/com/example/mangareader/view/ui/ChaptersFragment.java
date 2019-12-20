package com.example.mangareader.view.ui;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.mangareader.R;
import com.example.mangareader.model.data.Chapter;
import com.example.mangareader.view.adapter.ChaptersListAdapter;
import com.example.mangareader.viewmodel.ChaptersViewModel;
import com.example.mangareader.viewmodel.factories.ChaptersViewModelFactory;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChaptersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChaptersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChaptersFragment extends Fragment {
    private static final String TAG = "debugging";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ChaptersViewModel chaptersViewModel;
    public ChaptersFragment() {
        // Required empty public constructor
    }

    private CompositeDisposable disposable;
    private String mangaID;
    private Observable<LiveData<PagedList<Chapter>>> chapters;
    private LiveData<List<Chapter>> myChapters;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChaptersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChaptersFragment newInstance(String param1, String param2) {
        ChaptersFragment fragment = new ChaptersFragment();
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
            mangaID = getArguments().getString("mangaID");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recent_recyclerview);
        final ChaptersListAdapter adapter = new ChaptersListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ProgressBar progressBar = view.findViewById(R.id.progressBar1);
        ChaptersViewModelFactory chaptersViewModelFactory = new ChaptersViewModelFactory(getActivity().getApplication(), mangaID);
        chaptersViewModel = ViewModelProviders.of(this, chaptersViewModelFactory).get(ChaptersViewModel.class);
        disposable = chaptersViewModel.getDisposables();
        if(mangaID != null)
            myChapters = chaptersViewModel.getChaptersByMangaID();
        else
            myChapters = chaptersViewModel.getRecentChapters();
        myChapters.observe(getActivity(), new Observer<List<Chapter>>() {
            @Override
            public void onChanged(List<Chapter> chapters) {
                Log.i(TAG, "onChanged: avec chapters " + (chapters != null ? chapters.size() : 0));
                if(chapters != null && chapters.size() >= 5 )
                    progressBar.setVisibility(View.GONE);
                adapter.setChapters(chapters);
                adapter.setOnItemClickListener(new ChaptersListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        PagesFragment pagesFragment = new PagesFragment();
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        //bundle, arguments ici
                        Bundle arguments = new Bundle();
                        arguments.putString("chapterID", chapters.get(position).getId());
                        pagesFragment.setArguments(arguments);
                        fragmentTransaction.replace(R.id.fragment_container, pagesFragment);
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
        return inflater.inflate(R.layout.fragment_chapters, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        disposable.dispose();
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
