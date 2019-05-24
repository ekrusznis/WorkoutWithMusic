package uw.ek.musiq.fragments.WorkoutDayFragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cunoraz.gifview.library.GifView;

import uw.ek.musiq.AdminActivity;
import uw.ek.musiq.R;

public class FriWorkoutFragment extends Fragment {
    private ImageButton imageButton;

    public FriWorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_workout_friday, container, false);

        return rootView;
    }

    public void onViewCreated (View view, Bundle savedInstanceState) {
        imageButton = view.findViewById(R.id.imageButton);
        Log.i("onviewcreated", "here");
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("adminDialog", "here");
                adminDialog(v.getContext(), "Jump Rope");

            }
        });

    }



    private void adminDialog(final Context c, String title) {
        Log.i("adminDialog", "here");

        final GifView gifview = new GifView(c);
        gifview.setGifResource(R.raw.jumprope);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle(title)
                .setView(gifview)
                .setPositiveButton("OK", null)
                .create();
        dialog.show();
    }
}


