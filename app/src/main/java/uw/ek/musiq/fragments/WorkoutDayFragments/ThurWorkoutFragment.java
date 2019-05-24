package uw.ek.musiq.fragments.WorkoutDayFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.cunoraz.gifview.library.GifView;

import uw.ek.musiq.R;

public class ThurWorkoutFragment extends Fragment {
    ImageButton thurJRButton;
    ImageButton thurFlutterKick;

    public ThurWorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_workout_thursday, container, false);
        return rootView;
    }

    public void onViewCreated (View view, Bundle savedInstanceState) {
        thurFlutterKick = getView().findViewById(R.id.flutterButton);
        thurFlutterKick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminDialog(v.getContext(), "Flutter Kicks", R.raw.flutterkicks);
            }
        });
        thurJRButton = getView().findViewById(R.id.thurjrButton);
        thurJRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminDialog(v.getContext(), "Jump Rope", R.raw.jumprope);
            }
        });

    }

    private void adminDialog(final Context c, String title, int gif) {
        Log.i("adminDialog", "here");

        final GifView gifview = new GifView(c);
        gifview.setGifResource(gif);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle(title)
                .setView(gifview)
                .setPositiveButton("OK", null)
                .create();
        dialog.show();
    }

}



