package com.example.mangareader.view.ui;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mangareader.R;
import com.example.mangareader.model.Chapter;
import com.example.mangareader.model.ChapterDao;
import com.example.mangareader.model.MangaDao;
import com.example.mangareader.view.adapter.ChaptersListAdapter;
import com.example.mangareader.viewmodel.ChaptersViewModel;
import com.example.mangareader.viewmodel.ChaptersViewModelFactory;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

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
    private Observable<LiveData<List<Chapter>>> chapters;
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

        ChaptersViewModelFactory chaptersViewModelFactory = new ChaptersViewModelFactory(getActivity().getApplication(), mangaID);
        chaptersViewModel = ViewModelProviders.of(this, chaptersViewModelFactory).get(ChaptersViewModel.class);
        disposable = chaptersViewModel.getDisposables();
        if(mangaID != null)
            chapters = chaptersViewModel.getChaptersByMangaIDObservable();
        else
            chapters = chaptersViewModel.getRecentChaptersObservable();
        Log.i(TAG, "onViewCreated: subscribing !!!!!!!!");
        chapters
                //.delay(2000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                //.debounce(10000, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<LiveData<List<Chapter>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onNext(LiveData<List<Chapter>> listLiveData) {
                Log.i(TAG, "onNext: ->");
                listLiveData.observe(getActivity(), new androidx.lifecycle.Observer<List<Chapter>>() {
                    @Override
                    public void onChanged(List<Chapter> chapters) {
                        //Log.i(TAG, "onChanged: chapters fragment -> nbchaps ->  " + (chapters != null ? chapters.size() : 0));
                        //Log.i(TAG, "onChanged: Am I on Main Thread ? -> " + (Looper.myLooper() == Looper.getMainLooper()));
                        //if(chapters.size() < 100)
                        //if(chapters.size() == 691) {
                            //Log.i(TAG, "onChanged: got all, setting chapters in recyclerview");
                            adapter.setChapters(chapters);
                            //Log.i(TAG, "onChanged: ok c'est set");
                       // }
                            
                        //Thread.sleep(500);
                    }
                });
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
            }
        });

        //else
            //chapters = chaptersViewModel.getRecentChapters();
        /*if(chapters != null) {
            chapters.observe(this, new Observer<List<Chapter>>() {
                @Override
                public void onChanged(List<Chapter> chapters) {
                    Log.i(TAG, "onChanged: chapters fragment -> nbchaps ->  " + (chapters != null ? chapters.size() : 0));
                    Log.i(TAG, "onChanged: Am I on Main Thread ? -> " + (Looper.myLooper() == Looper.getMainLooper()));
                    adapter.setChapters(chapters);
                }
            });
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recent_chapters, container, false);
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
