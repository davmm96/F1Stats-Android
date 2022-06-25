package com.david.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.david.myapplication.R
import com.david.myapplication.databinding.ItemRaceBinding
import com.david.myapplication.model.FavoriteRace
import com.david.myapplication.model.Race

class RacesAdapter (private val races: List<Race>, private val favRacesIds: List<Int>, val listener: (FavoriteRace) -> Unit) :
    RecyclerView.Adapter<RacesAdapter.RaceViewHolder>(){

    override fun onBindViewHolder(holderRace: RaceViewHolder, position: Int)
    {
        val item = races[position]
        holderRace.bind(item, favRacesIds)

        holderRace.ivFavorite.setOnClickListener{
            holderRace.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_on_24)
            listener(FavoriteRace(item.id,
                    item.competition.location.country,
                    item.competition.name,
                    item.laps.total,
                    item.season,
                    item.circuit.image,
            true)
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RaceViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_race, parent, false)
    )

    override fun getItemCount() = races.size

    class RaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemRaceBinding.bind(view)

        var idItem: Int = -1
        var tvCountry: TextView = binding.tvCountry
        var tvName: TextView = binding.tvName
        var tvLaps: TextView = binding.tvLaps
        var tvSeason: TextView = binding.tvSeason
        var ivFavorite: ImageView = binding.ivFavorite

        fun bind(race: Race, favRacesIds: List<Int>) {
            idItem = race.id
            tvCountry.text = race.competition.location.country
            tvName.text = race.competition.name
            tvLaps.text = (race.laps.total).toString().plus(" laps")
            tvSeason.text = (race.season).toString()

            if(favRacesIds.contains(race.id))
            {
                ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_on_24)
            }
            else
            {
                ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_off_24)
            }
            
        }
    }
}