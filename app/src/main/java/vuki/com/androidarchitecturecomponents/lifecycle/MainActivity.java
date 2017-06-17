package vuki.com.androidarchitecturecomponents.lifecycle;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import vuki.com.androidarchitecturecomponents.R;
import vuki.com.androidarchitecturecomponents.databinding.ActivityMainBinding;

/**
 * Lifecycle main activity https://codelabs.developers.google.com/codelabs/android-lifecycles/#0
 */
public class MainActivity extends LifecycleActivity {

    ActivityMainBinding binding;
    private LiveDataTimerViewModel liveDataTimerViewModel;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );

        liveDataTimerViewModel = ViewModelProviders.of( this ).get( LiveDataTimerViewModel.class );
        subscribe();
    }

    private void subscribe() {
        final Observer<Long> elapsedTimeObserver = new Observer<Long>() {
            @Override
            public void onChanged( @Nullable Long aLong ) {
                String newText = MainActivity.this.getResources().getString( R.string.seconds, aLong );
                binding.timerTextview.setText( newText );
                Log.d( MainActivity.class.getCanonicalName(), "Updating timer" );
            }
        };

        liveDataTimerViewModel.getElapsedTime().observe( this, elapsedTimeObserver );
    }

    private void setChronometer() {
        binding.chronometer.setVisibility( View.VISIBLE );
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
