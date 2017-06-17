package vuki.com.androidarchitecturecomponents.lifecycle;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by Vuki on 18.6.2017..
 */

public class SeekBarViewModel extends ViewModel {
    public MutableLiveData<Integer> seekBarValue = new MutableLiveData<>();
}
