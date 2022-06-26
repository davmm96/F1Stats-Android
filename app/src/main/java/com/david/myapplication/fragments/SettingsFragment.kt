package com.david.myapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.david.myapplication.R
import com.david.myapplication.background.MusicService
import com.david.myapplication.background.PreferencesManager
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment: Fragment() {

    companion object {
        fun newInstance(): SettingsFragment{
            return SettingsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!PreferencesManager(requireActivity()).isMusicEnabled)
            switchButton.toggle()

        switchButton.setOnCheckedChangeListener{ _, isChecked ->
            toggleMusic(isChecked)
        }
    }

    private fun toggleMusic(isChecked: Boolean) {

        PreferencesManager(requireActivity()).isMusicEnabled = isChecked

        if (isChecked)
        {
            requireActivity().startService(Intent(requireActivity(), MusicService::class.java))
        }
        else
        {
            requireActivity().stopService(Intent(requireActivity(), MusicService::class.java))
        }
    }
}



