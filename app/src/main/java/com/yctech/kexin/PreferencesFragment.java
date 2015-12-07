package com.yctech.kexin;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.TextView;
import android.widget.Toast;
public class PreferencesFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
    private NumSetListPreference mNumSetLp;
    private static final String KEY_NUMSET = "numset";
    private int sel_num;
    MainActivity mainActivity;
    TextView textViewOfNumSetLp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.display_settings);
        mNumSetLp = (NumSetListPreference) findPreference(KEY_NUMSET);
        mainActivity = (MainActivity) this.getActivity();
        mNumSetLp.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final String key = preference.getKey();
        if (KEY_NUMSET.equals(key)){
            sel_num = Integer.parseInt((String) newValue);
            mainActivity.setmCount(sel_num);
            textViewOfNumSetLp = (TextView) mainActivity.findViewById(R.id.yinfu_title);
            textViewOfNumSetLp.setText("音符数:"+sel_num);
            mNumSetLp.setValueIndex(sel_num-1);
            Toast.makeText(this.getActivity(),sel_num+"",Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
