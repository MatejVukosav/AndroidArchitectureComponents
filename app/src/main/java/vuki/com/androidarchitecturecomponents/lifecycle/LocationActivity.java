package vuki.com.androidarchitecturecomponents.lifecycle;

import android.Manifest;
import android.arch.lifecycle.LifecycleActivity;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import vuki.com.androidarchitecturecomponents.R;
import vuki.com.androidarchitecturecomponents.databinding.ActivityLocationActivityBinding;

public class LocationActivity extends LifecycleActivity {

    private static final int REQUEST_LOCATION_PERMISSION_CODE = 1;
    private LocationListener gpsListener = new MyLocationListener();
    ActivityLocationActivityBinding binding;

    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
            bindLocationListener();
        } else {
            Toast.makeText( this, "This sample requires location access", Toast.LENGTH_SHORT ).show();
        }

    }

    private void bindLocationListener() {
        BoundLocationManager.bindLocationListenerIn( this, gpsListener, getApplicationContext() );
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_location_activity );

        if( ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this,
                    new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION },
                    REQUEST_LOCATION_PERMISSION_CODE );
        } else {
            bindLocationListener();
        }
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged( Location location ) {
            binding.location.setText( location.getLatitude() + ", " + location.getLongitude() );
        }

        @Override
        public void onStatusChanged( String provider, int status, Bundle extras ) {

        }

        @Override
        public void onProviderEnabled( String provider ) {
            Toast.makeText( LocationActivity.this, "Provider enabled: " + provider, Toast.LENGTH_SHORT ).show();
        }

        @Override
        public void onProviderDisabled( String provider ) {

        }
    }
}
