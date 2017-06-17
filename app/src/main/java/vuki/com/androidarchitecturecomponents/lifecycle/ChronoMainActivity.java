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
public class ChronoMainActivity extends LifecycleActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );

        setTimerLiveDataViewModel();
        setChronometer();
    }

    @SuppressWarnings("unused")
    private void setTimerLiveDataViewModel() {
        LiveDataTimerViewModel liveDataTimerViewModel = ViewModelProviders.of( this ).get( LiveDataTimerViewModel.class );
        subscribe( liveDataTimerViewModel );
    }

    private void subscribe( LiveDataTimerViewModel viewModel ) {
        final Observer<Long> elapsedTimeObserver = new Observer<Long>() {
            @Override
            public void onChanged( @Nullable Long aLong ) {
                String newText = ChronoMainActivity.this.getResources().getString( R.string.seconds, aLong );
                binding.timerTextview.setText( newText );
                Log.d( ChronoMainActivity.class.getCanonicalName(), "Updating timer" );
            }
        };

        viewModel.getElapsedTime().observe( this, elapsedTimeObserver );
    }

    @SuppressWarnings("unused")
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
