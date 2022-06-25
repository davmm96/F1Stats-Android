package com.david.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.david.myapplication.adapters.FavoritesAdapter
import com.david.myapplication.R
import com.david.myapplication.db.FavoriteRacesDatabase
import com.david.myapplication.model.FavoriteRace
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesFragment: Fragment() {

    private lateinit var db: FavoriteRacesDatabase

    companion object {
        fun newInstance(): FavoritesFragment{
            return FavoritesFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFavoriteRaces()
    }

    private fun loadFavoriteRaces() {
        GlobalScope.launch (Dispatchers.IO){
            db = Room.databaseBuilder(
                requireActivity(),
                FavoriteRacesDatabase::class.java, "favoritesDB"
            ).build()

            val races: List<FavoriteRace> = db.favoriteRacesDao().getAll()

            withContext(Dispatchers.Main) {
                rvFavorites.layoutManager = LinearLayoutManager(activity)
                rvFavorites.adapter = FavoritesAdapter(races) { favoriteRace, _ ->
                    GlobalScope.launch (Dispatchers.IO)
                    {
                        db.favoriteRacesDao().delete(favoriteRace)
                        (rvFavorites.adapter as FavoritesAdapter).updateItems(
                            db.favoriteRacesDao().getAll()
                        )
                        withContext(Dispatchers.Main) {
                            (rvFavorites.adapter as FavoritesAdapter).notifyDataSetChanged()
                            Toast.makeText(activity, getString(R.string.race_deleted), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}