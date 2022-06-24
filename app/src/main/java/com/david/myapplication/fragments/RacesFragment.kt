package com.david.myapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.myapplication.R
import com.david.myapplication.RacesAdapter
import com.david.myapplication.model.Race
import com.david.myapplication.model.Races
import com.david.myapplication.network.GsonRequest
import com.david.myapplication.network.RequestManager
import kotlinx.android.synthetic.main.fragment_races.*

class RacesFragment : Fragment() {

    private val url = getString(R.string.api_url_races)
    private val headers = mutableMapOf<String,String>()
    private lateinit var raceAdapter : RacesAdapter


    companion object {
        fun newInstance(): RacesFragment{
            return RacesFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_races, container, false)
    }

    private fun doRequest() {

        headers[getString(R.string.apikey_header)] = getString(R.string.apikey)

        val gsonRequest = GsonRequest(url, Races::class.java, headers,
        {
            response -> showRaces(response.races.toList())
        },
        {
            Toast.makeText(activity, getString(R.string.races_error), Toast.LENGTH_SHORT).show()
        })


        RequestManager.getInstance(requireActivity()).addToRequestQueue(gsonRequest)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showRaces(racesResponse: List<Race>) {

        raceAdapter = RacesAdapter(racesResponse)
        rvRaces.layoutManager = LinearLayoutManager(activity)
        rvRaces.adapter = raceAdapter
        raceAdapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doRequest()
    }
}