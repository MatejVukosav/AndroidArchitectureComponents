package vuki.com.androidarchitecturecomponents.lifecycle;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import vuki.com.androidarchitecturecomponents.R;
import vuki.com.androidarchitecturecomponents.databinding.FragmentOneBinding;

/**
 * Created by Vuki on 18.6.2017..
 */

public class FragmentOne extends Fragment {

    private SeekBarViewModel seekBarViewModel;
    FragmentOneBinding binding;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState ) {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_one, container, false );
        seekBarViewModel = ViewModelProviders.of( getActivity() ).get( SeekBarViewModel.class );
        subscribeSeekBar( binding.seekBar );
        return binding.getRoot();
    }

    private void subscribeSeekBar( final SeekBar seekBar ) {

        seekBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged( SeekBar seekBar, int progress, boolean fromUser ) {
                if( fromUser ) {
                    Log.d( "SeekBar", "Progress changed" );
                    seekBarViewModel.seekBarValue.setValue( progress );
                }
            }

            @Override
            public void onStartTrackingTouch( SeekBar seekBar ) {

            }

            @Override
            public void onStopTrackingTouch( SeekBar seekBar ) {

            }
        } );

        seekBarViewModel.seekBarValue.observe( (LifecycleOwner) getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged( @Nullable Integer value ) {
                if( value != null ) {
                    seekBar.setProgress( value );
                }
            }
        } );
    }
}
