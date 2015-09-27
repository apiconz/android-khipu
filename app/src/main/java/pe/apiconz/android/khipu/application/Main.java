package pe.apiconz.android.khipu.application;

import android.app.Application;

import pe.apiconz.android.khipu.model.preferences.SharedPreferenceCard;

/**
 * Created by Brian on 19/08/2015.
 */
public class Main extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferenceCard.init(getApplicationContext());
    }
}
