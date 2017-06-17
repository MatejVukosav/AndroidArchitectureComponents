package vuki.com.androidarchitecturecomponents.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

/**
 * Created by Vuki on 17.6.2017..
 */

public class BoundLocationManager {

    private static final String boundLocationMgr = "BoundLocationMgr";

    public static void bindLocationListenerIn( LifecycleRegistryOwner lifecycleOwner, LocationListener listener, Context context ) {
        new BoundLocationListener( lifecycleOwner, listener, context );
    }

    @SuppressWarnings("MissingPermission")
    static class BoundLocationListener implements LifecycleObserver {

        private final Context context;
        private LocationManager locationManager;
        private final LocationListener listener;

        public BoundLocationListener( LifecycleRegistryOwner lifecycleOwner, LocationListener listener, Context context ) {
            this.context = context;
            this.listener = listener;
            lifecycleOwner.getLifecycle().addObserver( this );
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        void addLocationListener() {
            locationManager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );
            locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, listener );
            Log.d( boundLocationMgr, "Listener added" );

            //Force an update with the latest location, if available
            Location lastLocation = locationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER );
            if( lastLocation != null ) {
                listener.onLocationChanged( lastLocation );
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        void removeLocationListener() {
            if( locationManager == null ) {
                return;
            }
            locationManager.removeUpdates( listener );
            locationManager = null;
            Log.d( boundLocationMgr, "Listener removed" );
        }
    }
}
