package de.stevensolleder.simpledo.controller;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import de.stevensolleder.simpledo.R;
import de.stevensolleder.simpledo.model.CustomDataAccessor;
import de.stevensolleder.simpledo.model.DataAccessor;
import de.stevensolleder.simpledo.model.SimpleDo;
import de.stevensolleder.simpledo.model.Time;


public class SettingsFragment extends PreferenceFragmentCompat implements PreferenceManager.OnPreferenceTreeClickListener
{
    private DataAccessor dataAccessor;

    public SettingsFragment()
    {
        dataAccessor=new CustomDataAccessor(SimpleDo.getAppContext().getSharedPreferences("settings", Context.MODE_PRIVATE));
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
    {
        addPreferencesFromResource(R.xml.settings_preference);
        findPreference("allday_reminder_time_key").setSummary(dataAccessor.getAlldayTime().toString());
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference)
    {
        String preferenceKey=preference.getKey();

        switch(preferenceKey)
        {
            case "allday_reminder_time_key":
                MaterialTimePicker materialTimePicker=new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(dataAccessor.getAlldayTime().getHour())
                        .setMinute(dataAccessor.getAlldayTime().getMinute())
                        .build();

                materialTimePicker.addOnPositiveButtonClickListener(view ->
                {
                    dataAccessor.setAlldayTime(new Time(materialTimePicker.getHour(), materialTimePicker.getMinute()));
                    preference.setSummary(dataAccessor.getAlldayTime().toString());
                });

                materialTimePicker.show(getParentFragmentManager(), null);
                materialTimePicker.getParentFragmentManager().executePendingTransactions();
                materialTimePicker.getView().<Button>findViewById(R.id.material_timepicker_ok_button).setText(SimpleDo.getAppContext().getResources().getString(R.string.apply));
                materialTimePicker.getView().<Button>findViewById(R.id.material_timepicker_cancel_button).setText(SimpleDo.getAppContext().getResources().getString(R.string.cancel));

                return true;

            case "battery_optimization_key":
                Intent batteryOptimizationIntent=new Intent(SimpleDo.getAppContext(), BatteryOptimizationActivity.class);
                startActivity(batteryOptimizationIntent);
                return true;

            case "about_key":
                Intent aboutIntent=new Intent(SimpleDo.getAppContext(), AboutActivity.class);
                startActivity(aboutIntent);
                return true;

            case "imprint_key":
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://imprint.stevensolleder.de/"));
                startActivity(intent);
                return true;

            case "open_source_licences_key":
                Intent licencesIntent=new Intent(SimpleDo.getAppContext(), LicencesActivity.class);
                startActivity(licencesIntent);
                return true;
        }

        return super.onPreferenceTreeClick(preference);
    }
}
