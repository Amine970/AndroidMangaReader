package com.example.mangareader.view.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mangareader.R;
import com.example.mangareader.model.Manga;
import com.example.mangareader.model.MangaDao;
import com.example.mangareader.model.MangaRoomDatabase;
import com.example.mangareader.viewmodel.MangaViewModel;
import com.example.mangareader.viewmodel.MangasDetailsViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MangaDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MangaDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MangaDetailsFragment extends Fragment {

    //private  String chapterIdChoosed;
    private Manga mangaChoosed;
    OnFragmentInteractionListener listener;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = "debugging";
    private ImageView image;
    private TextView title;
    private TextView author;
    private TextView description;
    private MangasDetailsViewModel mangasDetailsViewModel;
    private CompositeDisposable disposable;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //disposable =
        //Log.i(TAG, "onViewCreated: title manga choosed -> " + mangaChoosed.getTitle() + " author  -> " + mangaChoosed.getAuthor() );
        image = view.findViewById(R.id.mangaDetail_image);
        title = view.findViewById(R.id.mangaDetail_title);
        author = view.findViewById(R.id.mangaDetail_author);
        description = view.findViewById(R.id.mangaDetail_descriptionText);
        MangasDetailsViewModelFactory mangasDetailsViewModelFactory = new MangasDetailsViewModelFactory(getActivity().getApplication(), mangaChoosed);
        mangasDetailsViewModel = ViewModelProviders.of(this, mangasDetailsViewModelFactory).get(MangasDetailsViewModel.class);
        mangasDetailsViewModel.getMangaById().observe(this, new Observer<Manga>() {
            @Override
            public void onChanged(Manga manga) {
                title.setText(manga.getTitle());
                author.setText(manga.getAuthor());
                Picasso.get()
                        .load(manga.getImage())
                        .placeholder(R.drawable.ic_manga_placeholder)
                        .fit()
                        .into(image);
                description.setText(manga.getDescription());
            }
        });

        /*
        Observable<List<Manga>> mangaObservable = Observable.fromCallable(new Callable<List<Manga>>() {

            @Override
            public List<Manga> call() throws Exception {
                return null;
            }
        });
         */
    }

    private OnFragmentInteractionListener mListener;

    public MangaDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MangaDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MangaDetailsFragment newInstance(String param1, String param2) {
        MangaDetailsFragment fragment = new MangaDetailsFragment();
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
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
            mangaChoosed = getArguments().getParcelable("Manga");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manga_details, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onMangaClicked(String id) {
        if (mListener != null) {
            mListener.onFragmentInteraction(id);
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
        Log.i(TAG, "onDetach: ici");
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
        void onFragmentInteraction(String id);
    }
}
