package vuki.com.androidarchitecturecomponents.lifecycle;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

/**
 * Created by Vuki on 17.6.2017..
 */

class ChronometerViewModel extends ViewModel {

    @Nullable
    private Long startDate;

    @Nullable
    Long getStartDate() {
        return startDate;
    }

    void setStartDate( final long startDate ) {
        this.startDate = startDate;
    }

}
