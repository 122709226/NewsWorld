package com.example.newapp;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;



/**
 * 想用 PreferenceFragment做设置界面 后面 由于监听事件等原因启未用 
 * */
public  class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
		
	public static final String KEY_PREF_SYNC_CONN = "out9527";
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        // Load the preferences from an XML resource
	        addPreferencesFromResource(R.xml.preferences);
		    final SharedPreferences userData=SettingsFragment.this.getActivity().getSharedPreferences("userDate", 0);
	        View viewSeting =LayoutInflater.from(SettingsFragment.this.getActivity()).inflate(R.layout.activity_setting, null);
	        //退出登录
	        TextView outLoging = (TextView)viewSeting.findViewById(R.id.outof_loging);
	        outLoging.setClickable(true);
	        outLoging.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					  Editor editor =  userData.edit();
			          editor.clear();
			          editor.commit();		
				}
			});
	 
	 
	 }

	 
	 
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {

				 if (key.equals(KEY_PREF_SYNC_CONN) ){
			          Preference connectionPref = findPreference(key);
			            // Set summary to be the user-description for the selected value

			         // connectionPref.setSummary(sharedPreferences.getString(key, ""));
			        }
	}
	
}
