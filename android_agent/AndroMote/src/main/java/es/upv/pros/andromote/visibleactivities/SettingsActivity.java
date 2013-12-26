package es.upv.pros.andromote.visibleactivities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import java.util.List;

import es.upv.pros.andromote.R;

/**
 * Created by bbotella on 19/08/13.
 *
 * Pretty standar preferences activity
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    public static class MotePreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            PreferenceManager.setDefaultValues(getActivity(),
                    R.xml.pref_mote, false);

            addPreferencesFromResource(R.xml.pref_mote);
        }
    }

    public static class ComputePreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            PreferenceManager.setDefaultValues(getActivity(),
                    R.xml.pref_compute, false);
            addPreferencesFromResource(R.xml.pref_compute);
        }
    }


    public static class NotifyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            PreferenceManager.setDefaultValues(getActivity(),
                    R.xml.pref_notify, false);
            addPreferencesFromResource(R.xml.pref_notify);
        }
    }
}
