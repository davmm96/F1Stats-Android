package com.david.myapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.david.myapplication.BuildConfig
import com.david.myapplication.R
import com.david.myapplication.adapters.RacesAdapter
import com.david.myapplication.db.FavoriteRacesDatabase
import com.david.myapplication.model.Race
import com.david.myapplication.model.Races
import com.david.myapplication.network.GsonRequest
import com.david.myapplication.network.RequestManager
import kotlinx.android.synthetic.main.fragment_races.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RacesFragment : Fragment() {

    private val headers = mutableMapOf<String,String>()
    private lateinit var db: FavoriteRacesDatabase




    companion object {
        fun newInstance(): RacesFragment{
            return RacesFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_races, container, false)
    }

    private fun doRequest() {

        headers[BuildConfig.API_HEADER] = BuildConfig.API_KEY

        val gsonRequest = GsonRequest(BuildConfig.API_URL_RACES, Races::class.java, headers,
        {
            response -> showRaces(response.races.toList())
        },
        {
            Toast.makeText(activity, getString(R.string.data_error), Toast.LENGTH_SHORT).show()
        })

        RequestManager.getInstance(requireActivity()).addToRequestQueue(gsonRequest)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showRaces(racesResponse: List<Race>)
    {
        GlobalScope.launch (Dispatchers.IO){
            db = Room.databaseBuilder(
                requireActivity(),
                FavoriteRacesDatabase::class.java, "favoritesDB"
            ).build()

            val favRacesIds: List<Int> = db.favoriteRacesDao().getAllIds()

            withContext(Dispatchers.Main) {
                rvRaces.layoutManager = LinearLayoutManager(activity)
                rvRaces.adapter = RacesAdapter(racesResponse, favRacesIds) { favoriteRace ->
                    GlobalScope.launch (Dispatchers.IO)
                    {
                        db.favoriteRacesDao().insert(favoriteRace)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(activity, getString(R.string.race_added), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doRequest()
    }
}