package vuki.com.androidarchitecturecomponents.lifecycle;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.SystemClock;

import vuki.com.androidarchitecturecomponents.R;
import vuki.com.androidarchitecturecomponents.databinding.ActivityMainBinding;

/**
 * Lifecycle main activity https://codelabs.developers.google.com/codelabs/android-lifecycles/#0
 */
public class MainActivity extends LifecycleActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );

        ChronometerViewModel chronometerViewModel = ViewModelProviders.of( this ).get( ChronometerViewModel.class );

        if( chronometerViewModel.getStartDate() == null ) {
            //if the start date is not defined, it's new ViewModel so set it
            long startTime = SystemClock.elapsedRealtime();
            chronometerViewModel.setStartDate( startTime );
            binding.chronometer.setBase( startTime );
        } else {
            //Otherwise the ViewModel has been retained, set the chronometer's base to the original starting time
            binding.chronometer.setBase( chronometerViewModel.getStartDate() );
        }

        binding.chronometer.start();

    }
}
