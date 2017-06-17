package vuki.com.androidarchitecturecomponents.lifecycle;

import android.arch.lifecycle.LifecycleActivity;
import android.os.Bundle;

import vuki.com.androidarchitecturecomponents.R;

public class FragmentActivity extends LifecycleActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_fragment );
    }
}
