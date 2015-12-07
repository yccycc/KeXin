package com.yctech.kexin;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;

public class NumSetListPreference extends ListPreference
{
        public NumSetListPreference(Context context, AttributeSet attrs) {
                super(context, attrs);
                setValueIndex(3);
        }

        @Override
        protected void onPrepareDialogBuilder(Builder builder) {
                super.onPrepareDialogBuilder(builder);
        }
}