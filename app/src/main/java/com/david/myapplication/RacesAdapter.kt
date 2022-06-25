package com.david.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.myapplication.databinding.ItemRaceBinding
import com.david.myapplication.model.Race

class RacesAdapter (private val races: List<Race>) :
    RecyclerView.Adapter<RacesAdapter.RaceViewHolder>(){

    override fun onBindViewHolder(holderRace: RaceViewHolder, position: Int)
    {
        val item = races[position]
        holderRace.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RaceViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_race, parent, false)
    )

    override fun getItemCount() = races.size

    class RaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemRaceBinding.bind(view)

        fun bind(race: Race) {
            binding.tvCountry.text = race.competition.location.country
            binding.tvName.text = race.competition.name
            binding.tvLaps.text = (race.laps.total).toString().plus(" laps")
            binding.tvSeason.text = (race.season).toString()

            binding.ivFavorite.setOnClickListener{
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_on_24)
            }
        }
    }
}