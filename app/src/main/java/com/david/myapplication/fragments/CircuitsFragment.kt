package com.david.myapplication.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.david.myapplication.CircuitsAdapter
import com.david.myapplication.R
import com.david.myapplication.RacesAdapter
import com.david.myapplication.db.FavoriteRacesDatabase
import com.david.myapplication.model.CircuitData
import com.david.myapplication.model.Circuits
import com.david.myapplication.model.Race
import com.david.myapplication.model.Races
import com.david.myapplication.network.GsonRequest
import com.david.myapplication.network.RequestManager
import kotlinx.android.synthetic.main.fragment_circuits.*
import kotlinx.android.synthetic.main.fragment_races.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class CircuitsFragment: Fragment() {

    //private val url = "https://v1.formula-1.api-sports.io/"
    private val url = "https://v1.formula-1.api-sports.io/circuits"
    private val WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 1
    private val headers = mutableMapOf<String,String>()


    companion object {
        fun newInstance(): CircuitsFragment{
            return CircuitsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_circuits, container, false)
    }

    private fun doRequest() {

        headers[getString(R.string.apikey_header)] = getString(R.string.apikey)

        val gsonRequest = GsonRequest(url, Circuits::class.java, headers,
            {
                    response -> showRaces(response.circuits.toList())
            },
            {
                Toast.makeText(activity, getString(R.string.races_error), Toast.LENGTH_SHORT).show()
            })

        RequestManager.getInstance(requireActivity()).addToRequestQueue(gsonRequest)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showRaces(circuitsResponse: List<CircuitData>)
    {
        rvCircuits.layoutManager = LinearLayoutManager(activity)
        rvCircuits.adapter = CircuitsAdapter(circuitsResponse){ urlImage: String, circuitName: String ->
            checkPermissions(urlImage, circuitName)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun checkPermissions(url: String, circuitName: String) {

        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                AlertDialog.Builder(requireActivity())
                    .setTitle("Permission required")
                    .setMessage("Permission required to save photos from the Web.")
                    .setPositiveButton("Accept") { _, _ ->
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            WRITE_EXTERNAL_STORAGE_PERMISSION_CODE
                        )
                    }
                    .setNegativeButton("Deny") { dialog, _ -> dialog.cancel() }
                    .show()
            } else
            {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            }
        }
        else {
            downloadImage(url, circuitName)
        }
    }

    private fun downloadImage(url: String, circuitName: String) {

        val directory = File(Environment.DIRECTORY_PICTURES)

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val downloadManager = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val downloadUri = Uri.parse(url)

        val request = DownloadManager.Request(downloadUri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(circuitName)
                .setDestinationInExternalPublicDir(
                    directory.toString(),
                    "$circuitName.jpeg"
                )
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        }

        downloadManager.enqueue(request)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doRequest()
    }


}