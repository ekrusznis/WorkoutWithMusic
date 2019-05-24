package uw.ek.musiq.fragments.WorkoutDayFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uw.ek.musiq.R;

public class MonWorkoutFragment extends Fragment {
    public MonWorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_workout_monday, container, false);

        return rootView;
    }

    public void onViewCreated (View view, Bundle savedInstanceState) {

    }
}


