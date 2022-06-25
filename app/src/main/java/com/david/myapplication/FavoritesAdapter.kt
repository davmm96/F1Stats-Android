package com.david.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.david.myapplication.databinding.ItemRaceBinding
import com.david.myapplication.model.FavoriteRace

class FavoritesAdapter(private var races: List<FavoriteRace>, val listener: (FavoriteRace, Int) -> Unit):
    RecyclerView.Adapter<FavoritesAdapter.FavoriteRaceViewHolder>() {

    override fun onBindViewHolder(holderRace: FavoriteRaceViewHolder, position: Int)
    {
        val item = races[position]
        holderRace.bind(item)

        holderRace.ivFavorite.setOnClickListener{
            listener(item, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavoriteRaceViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_race, parent, false)
    )

    override fun getItemCount() = races.size

    fun updateItems(newItems : List<FavoriteRace>){
        this.races = newItems
    }

    class FavoriteRaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemRaceBinding.bind(view)

        var tvCountry: TextView = binding.tvCountry
        var tvName: TextView = binding.tvName
        var tvLaps: TextView = binding.tvLaps
        var tvSeason: TextView = binding.tvSeason
        var ivFavorite: ImageView = binding.ivFavorite

        fun bind(race: FavoriteRace) {
            tvCountry.text = race.country
            tvName.text = race.name
            tvLaps.text = (race.laps).toString().plus(" laps")
            tvSeason.text = (race.season).toString()
            ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_on_24)
        }
    }
}