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
import uw.ek.musiq.fragments.DietDayFragments.FriDietFragment;
import uw.ek.musiq.fragments.DietDayFragments.MonDietFragment;
import uw.ek.musiq.fragments.DietDayFragments.SatDietFragment;
import uw.ek.musiq.fragments.DietDayFragments.SunDietFragment;
import uw.ek.musiq.fragments.DietDayFragments.ThursDietFragment;
import uw.ek.musiq.fragments.DietDayFragments.TuesDietFragment;
import uw.ek.musiq.fragments.DietDayFragments.WedDietFragment;
import uw.ek.musiq.fragments.WorkoutDayFragments.FriWorkoutFragment;
import uw.ek.musiq.fragments.WorkoutDayFragments.MonWorkoutFragment;
import uw.ek.musiq.fragments.WorkoutDayFragments.SatWorkoutFragment;
import uw.ek.musiq.fragments.WorkoutDayFragments.SunWorkoutFragment;
import uw.ek.musiq.fragments.WorkoutDayFragments.ThurWorkoutFragment;
import uw.ek.musiq.fragments.WorkoutDayFragments.TuesWorkoutFragment;
import uw.ek.musiq.fragments.WorkoutDayFragments.WedWorkoutFragment;

public class DietFragment extends Fragment {
    WeekdaysPicker widget;
    Integer currentDay;
    Fragment fragment = null;

    public DietFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_diet, container, false);

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
                    fragment = new MonDietFragment();
                    replaceFragment(fragment);
                }  if (clickedDayOfWeek==Calendar.TUESDAY){
                    selectedDays.clear();
                    selectedDays.add(clickedDayOfWeek);
                    widget.setSelectedDays(selectedDays);
                    fragment = new TuesDietFragment();
                    replaceFragment(fragment);
                }  if (clickedDayOfWeek==Calendar.WEDNESDAY){
                    selectedDays.clear();
                    selectedDays.add(clickedDayOfWeek);
                    widget.setSelectedDays(selectedDays);
                    fragment = new WedDietFragment();
                    replaceFragment(fragment);
                }
                if (clickedDayOfWeek==Calendar.THURSDAY){
                    selectedDays.clear();
                    selectedDays.add(clickedDayOfWeek);
                    widget.setSelectedDays(selectedDays);
                    fragment = new ThursDietFragment();
                    replaceFragment(fragment);
                }
                if (clickedDayOfWeek==Calendar.FRIDAY){
                    selectedDays.clear();
                    selectedDays.add(clickedDayOfWeek);
                    widget.setSelectedDays(selectedDays);
                    fragment = new FriDietFragment();
                    replaceFragment(fragment);
                }
                if (clickedDayOfWeek==Calendar.SATURDAY){
                    selectedDays.clear();
                    selectedDays.add(clickedDayOfWeek);
                    widget.setSelectedDays(selectedDays);
                    fragment = new SatDietFragment();
                    replaceFragment(fragment);
                }
                if (clickedDayOfWeek==Calendar.SUNDAY){
                    selectedDays.clear();
                    selectedDays.add(clickedDayOfWeek);
                    widget.setSelectedDays(selectedDays);
                    fragment = new SunDietFragment();
                    replaceFragment(fragment);

                }
            }
        });
        switch (currentDay) {
            case Calendar.SUNDAY:
                fragment = new SunDietFragment();
                replaceFragment(fragment);
                break;
            case Calendar.MONDAY:
                fragment = new MonDietFragment();
                replaceFragment(fragment);
                break;
            case Calendar.TUESDAY:
                fragment = new TuesDietFragment();
                replaceFragment(fragment);
                break;
            case Calendar.WEDNESDAY:
                fragment = new WedDietFragment();
                replaceFragment(fragment);
                break;
            case Calendar.THURSDAY:
                fragment = new ThursDietFragment();
                replaceFragment(fragment);
                break;
            case Calendar.FRIDAY:
                fragment = new FriDietFragment();
                replaceFragment(fragment);
                break;
            case Calendar.SATURDAY:
                fragment = new SatDietFragment();
                replaceFragment(fragment);
                break;

        }


    }
    public void replaceFragment(Fragment myFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.dietDayContainer, myFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
