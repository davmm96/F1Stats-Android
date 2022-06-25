package com.david.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.david.myapplication.R
import com.david.myapplication.databinding.ItemCircuitBinding
import com.david.myapplication.model.CircuitData


class CircuitsAdapter (private val circuits: List<CircuitData>, val listener: (String, String) -> Unit) :
    RecyclerView.Adapter<CircuitsAdapter.CircuitViewHolder>(){

    override fun onBindViewHolder(holderCircuit: CircuitViewHolder, position: Int)
    {
        val item = circuits[position]
        holderCircuit.bind(item)

        holderCircuit.ivDownload.setOnClickListener{
            listener(item.image, item.name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CircuitViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_circuit, parent, false)
    )

    override fun getItemCount() = circuits.size

    class CircuitViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemCircuitBinding.bind(view)

        var tvName: TextView = binding.tvName
        var tvCountry: TextView = binding.tvCountry
        var tvLength: TextView = binding.tvLengthData
        var tvLaps: TextView = binding.tvLapsData
        var tvFirst: TextView = binding.tvFirstData
        var tvRecordTime: TextView = binding.tvRecordTime
        var tvRecordDriver: TextView = binding.tvRecordDriver
        var ivDownload: ImageView = binding.ivDownload

        fun bind(circuit: CircuitData) {
            tvName.text = circuit.name
            tvCountry.text = circuit.competition.location.country
            tvLength.text = circuit.length
            tvLaps.text = (circuit.laps).toString()
            tvFirst.text = (circuit.first_grand_prix).toString()
            tvRecordTime.text = circuit.lap_record.time
            tvRecordDriver.text = circuit.lap_record.driver
        }
    }
}