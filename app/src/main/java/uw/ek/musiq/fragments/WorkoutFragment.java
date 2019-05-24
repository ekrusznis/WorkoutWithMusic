package uw.ek.musiq.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dpro.widgets.OnWeekdaysChangeListener;
import com.dpro.widgets.WeekdaysPicker;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import uw.ek.musiq.R;
import uw.ek.musiq.fragments.WorkoutDayFragments.FriWorkoutFragment;
import uw.ek.musiq.fragments.WorkoutDayFragments.MonWorkoutFragment;
import uw.ek.musiq.fragments.WorkoutDayFragments.SatWorkoutFragment;
import uw.ek.musiq.fragments.WorkoutDayFragments.SunWorkoutFragment;
import uw.ek.musiq.fragments.WorkoutDayFragments.ThurWorkoutFragment;
import uw.ek.musiq.fragments.WorkoutDayFragments.TuesWorkoutFragment;
import uw.ek.musiq.fragments.WorkoutDayFragments.WedWorkoutFragment;

public class WorkoutFragment extends Fragment {
    WeekdaysPicker widget;
    Integer currentDay;
    Fragment fragment = null;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar calendar = Calendar.getInstance();
        currentDay = calendar.get(Calendar.DAY_OF_WEEK);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_workout, container, false);

        return rootView;
    }

    public void onViewCreated (View view, Bundle savedInstanceState){
        FrameLayout frag = getView().findViewById(R.id.workoutDayContainer);
        widget = (WeekdaysPicker) getView().findViewById(R.id.weekdays);
        List<Integer> dayList = Arrays.asList(currentDay);
        widget.setSelectedDays(dayList);

        widget.setOnWeekdaysChangeListener(new OnWeekdaysChangeListener() {
            @Override
            public void onChange(View view, int clickedDayOfWeek, List<Integer> selectedDays) {
                if (clickedDayOfWeek==Calendar.MONDAY){
                    selectedDays.clear();
                    selectedDays.add(clickedDayOfWeek);
                        widget.setSelectedDays(selectedDays);
                    fragment = new MonWorkoutFragment();
                    replaceFragment(fragment);
                }  if (clickedDayOfWeek==Calendar.TUESDAY){
                    selectedDays.clear();
                    selectedDays.add(clickedDayOfWeek);
                    widget.setSelectedDays(selectedDays);
                    fragment = new TuesWorkoutFragment();
                    replaceFragment(fragment);
                }  if (clickedDayOfWeek==Calendar.WEDNESDAY){
                    selectedDays.clear();
                    selectedDays.add(clickedDayOfWeek);
                    widget.setSelectedDays(selectedDays);
                    fragment = new WedWorkoutFragment();
                    replaceFragment(fragment);
                }
                if (clickedDayOfWeek==Calendar.THURSDAY){
                    selectedDays.clear();
                    selectedDays.add(clickedDayOfWeek);
                    widget.setSelectedDays(selectedDays);
                    fragment = new ThurWorkoutFragment();
                    replaceFragment(fragment);
                }
                if (clickedDayOfWeek==Calendar.FRIDAY){
                    selectedDays.clear();
                    selectedDays.add(clickedDayOfWeek);
                    widget.setSelectedDays(selectedDays);
                    fragment = new FriWorkoutFragment();
                    replaceFragment(fragment);
                }
                if (clickedDayOfWeek==Calendar.SATURDAY){
                    selectedDays.clear();
                    selectedDays.add(clickedDayOfWeek);
                    widget.setSelectedDays(selectedDays);
                    fragment = new SatWorkoutFragment();
                    replaceFragment(fragment);
                }
                if (clickedDayOfWeek==Calendar.SUNDAY){
                    selectedDays.clear();
                    selectedDays.add(clickedDayOfWeek);
                    widget.setSelectedDays(selectedDays);
                    fragment = new SunWorkoutFragment();
                    replaceFragment(fragment);

                }

            }
        });
        switch (currentDay) {
            case Calendar.SUNDAY:
                    fragment = new SunWorkoutFragment();
                    replaceFragment(fragment);
                break;
            case Calendar.MONDAY:
                fragment = new MonWorkoutFragment();
                replaceFragment(fragment);
                break;
            case Calendar.TUESDAY:
                fragment = new TuesWorkoutFragment();
                replaceFragment(fragment);
                break;
            case Calendar.WEDNESDAY:
                fragment = new WedWorkoutFragment();
                replaceFragment(fragment);
                break;
            case Calendar.THURSDAY:
                fragment = new ThurWorkoutFragment();
                replaceFragment(fragment);
                break;
            case Calendar.FRIDAY:
                fragment = new FriWorkoutFragment();
                replaceFragment(fragment);
                break;
            case Calendar.SATURDAY:
                fragment = new SatWorkoutFragment();
                replaceFragment(fragment);
                break;

        }


    }
    public void replaceFragment(Fragment myFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.workoutDayContainer, myFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
