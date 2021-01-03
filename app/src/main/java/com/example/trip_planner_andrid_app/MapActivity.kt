package com.example.trip_planner_andrid_app

import android.graphics.RectF
import android.os.Bundle
import android.text.TextUtils.substring
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Feature
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style

class MapActivity : AppCompatActivity(), MapboxMap.OnMapClickListener {

    private var mapView: MapView? = null
    private var mapboxMap: MapboxMap? = null
    private val layerId = "country-boundaries"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))

        setContentView(R.layout.map_activity)

        mapView = findViewById(R.id.mapView)

//        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync { mapboxMap ->
            val style = Style.Builder().fromUri(getString(R.string.mapbox_style_url))
            mapboxMap.setStyle(style) {
                this.mapboxMap = mapboxMap
                this.mapboxMap?.addOnMapClickListener(this)
            }
        }
    }

    override fun onMapClick(point: LatLng): Boolean {
        val pointf = mapboxMap!!.projection.toScreenLocation(point)
        val rectF = RectF(pointf.x - 10, pointf.y - 10, pointf.x + 10, pointf.y + 10)
        var featureList: List<Feature>

        featureList = mapboxMap!!.queryRenderedFeatures(rectF, "$layerId-red")
        if (displayFeatures(featureList, "Strefa czerwona")) return true

        featureList = mapboxMap!!.queryRenderedFeatures(rectF, "$layerId-green")
        if (displayFeatures(featureList, "Strefa zielona")) return true

        featureList = mapboxMap!!.queryRenderedFeatures(rectF, "$layerId-yellow")
        if (displayFeatures(featureList, "Strefa żółta")) return true

        featureList = mapboxMap!!.queryRenderedFeatures(rectF, layerId)
        if (displayFeatures(featureList, "Brak danych")) return true

        Toast.makeText(this@MapActivity, "Brak danych", Toast.LENGTH_SHORT).show()

        return false
    }

    private fun displayFeatures(featureList: List<Feature>, string: String): Boolean {
        if (featureList.isNotEmpty()) {
            for (feature in featureList) {
                var country = feature.properties()?.get("name_en").toString()
                country = substring(country, 1, country.length - 1)
                Toast.makeText(this@MapActivity, "$country - $string", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }

    private fun hideSystemUI() {
        this.window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }



    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }
}
