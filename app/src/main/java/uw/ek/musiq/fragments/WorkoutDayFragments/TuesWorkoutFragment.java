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

public class TuesWorkoutFragment extends Fragment {
    ImageButton tuejrbutton, tueKBSwings, singpushpress, pushups;

    public TuesWorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_workout_tuesday, container, false);
        return rootView;
    }

    public void onViewCreated (View view, Bundle savedInstanceState) {
        tuejrbutton = getView().findViewById(R.id.tueJRbutton);
        tuejrbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminDialog(v.getContext(), "Jump Rope", R.raw.jumprope);
            }
        });
        tueKBSwings = getView().findViewById(R.id.tueKBswing);
        tueKBSwings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminDialog(v.getContext(), "Kettlebell Swings", R.raw.kbswings);
            }
        });
        singpushpress = getView().findViewById(R.id.pparm);
        singpushpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminDialog(v.getContext(), "Single Arm Push Press", R.raw.kbpushpress);
            }
        });
        pushups = getView().findViewById(R.id.pubutton);
        pushups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminDialog(v.getContext(), "Pushups", R.raw.pushup);
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

