package com.example.trip_planner_andrid_app

import android.content.Intent
import android.graphics.Color
import android.graphics.RectF
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils.substring
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trip_planner_andrid_app.flights.data.CovidApiResults
import com.example.trip_planner_andrid_app.flights.data.SkyscannerResults
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder
import com.mapbox.geojson.Feature
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.FillLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillOpacity
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import kotlinx.android.synthetic.main.covid_bottomsheet_fragment.*
import okhttp3.*
import java.io.IOException
import kotlinx.android.synthetic.main.map_activity.*
import java.util.*


class MapActivity : AppCompatActivity(), MapboxMap.OnMapClickListener, MapView.OnDidFinishLoadingStyleListener {

    private var mapView: MapView? = null
    private var mapboxMap: MapboxMap? = null
    private val layerId = "country-boundaries"
    private var selectedSource: GeoJsonSource? = null;
    private var selectedArea: FillLayer? = null
    private var bottomSheetDialog: BottomSheetDialog? = null
    private var countryName: TextView? = null
    private var casesToday: TextView? = null
    private var deathsToday: TextView? = null
    private var recoveredToday: TextView? = null
    private var strefa: TextView? = null
    private var info: TextView? = null
    private var smallInfoText: TextView? = null
    private var countryData: CovidApiResults.CountryData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.covid_bottomsheet_fragment, null)
        bottomSheetDialog?.setContentView(bottomSheetView)
        countryName = bottomSheetView.findViewById(R.id.countryName) as? TextView
        deathsToday = bottomSheetView.findViewById(R.id.deathsToday) as? TextView
        recoveredToday = bottomSheetView.findViewById(R.id.recoveredToday) as? TextView
        casesToday = bottomSheetView.findViewById(R.id.casesToday) as? TextView
        strefa = bottomSheetView.findViewById(R.id.strefa) as? TextView
        smallInfoText = bottomSheetView.findViewById(R.id.smallInfoText) as? TextView
        info = bottomSheetView.findViewById(R.id.info) as? TextView
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))

        setContentView(R.layout.map_activity)

        mapView = findViewById(R.id.mapView)

//        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.Builder().fromUri(getString(R.string.mapbox_style_url))) {
                this.mapboxMap = mapboxMap
                this.mapboxMap?.addOnMapClickListener(this)
                mapboxMap.uiSettings.isCompassEnabled = true
                mapboxMap.uiSettings.setCompassFadeFacingNorth(false)
            }
        }

        mapView?.addOnDidFinishLoadingStyleListener(this)
    }

    override fun onMapClick(point: LatLng): Boolean {
        val pointf = mapboxMap!!.projection.toScreenLocation(point)
        val rectF = RectF(pointf.x - 10, pointf.y - 10, pointf.x + 10, pointf.y + 10)
        var featureList: List<Feature>

        featureList = mapboxMap!!.queryRenderedFeatures(rectF, "$layerId-red")
        if (displayFeatures(featureList, "Duże ograniczenia")) return true

        featureList = mapboxMap!!.queryRenderedFeatures(rectF, "$layerId-green")
        if (displayFeatures(featureList, "Niewielkie ograniczenia")) return true

        featureList = mapboxMap!!.queryRenderedFeatures(rectF, "$layerId-yellow")
        if (displayFeatures(featureList, "Umiarkowane ograniczenia")) return true

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
                callRequest(buildRequest(country), createClient())
                println(feature)
                if (selectedSource != null) {
                    mapboxMap?.style?.removeLayer(selectedArea!!)
                    mapboxMap?.style?.removeSource(selectedSource!!)
                }
                selectedSource = GeoJsonSource("selected", feature)
                mapboxMap?.style?.addSource(selectedSource!!)
                selectedArea = FillLayer("selected-fill", "selected")
                selectedArea?.setProperties(fillColor(Color.parseColor("#ff0088")), fillOpacity(0.4f))
                mapboxMap?.style?.addLayer(selectedArea!!)

                countryName?.text = country
                strefa?.text = string
                if (string == "Duże ograniczenia") {
                    info?.text = "Podróżowanie do tego miejsca może zostać zawieszone, kraj może być zamknięty lub wjazd może być dostępny tylko dla obywateli, którzy spełniają określone wymagania."
                } else if (string == "Umiarkowane ograniczenia") {
                    info?.text = "Podróżowanie do tego miejsca jest możliwe, jeśli spełniasz określone wymagania (np. może być konieczne wykonanie testu na COVID-19). Po powrocie lub przyjeździe może obowiązywać kwarantanna."
                } else if (string == "Niewielkie ograniczenia") {
                    info?.text = "Możesz podróżować do tego miejsca – nie obowiązuje tam kwarantanna po przyjeździe ani po powrocie."
                } else info?.text = "Nie mamy danych o tym miejscu. Sprawdź informacje przygotowane przez władze i najnowsze komunikaty dla podróżnych."
                Handler().postDelayed({
                    bottomSheetDialog?.show()
                }, 300)

                //Toast.makeText(this@MapActivity, "$country - $string", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }


    private fun createClient(): OkHttpClient {
        return OkHttpClient()
    }

    private fun buildRequest(country: String): Request {
        return Request.Builder()
            .url("https://disease.sh/v3/covid-19/countries/$country?strict=true")
            .get()
            .build();
    }

    private fun callRequest(request: Request, client: OkHttpClient) {
        client.newCall(request).enqueue(
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("Failed")
                }

                override fun onResponse(call: Call, response: Response) {
                    response.body?.string()?.let { fetchJsonToDataClass(it) }
                }
            })
    }

    private fun fetchJsonToDataClass(body: String) {
        countryData = GsonBuilder().create().fromJson(body, CovidApiResults.CountryData::class.java)

        if (countryData?.todayCases == 0 && countryData?.todayDeaths == 0 && countryData?.todayRecovered == 0) {
            smallInfoText?.text = "Nie zaktualizowano informacji z ostatniej doby"
            casesToday?.text = "-"
            deathsToday?.text = "-"
            recoveredToday?.text = "-"
        } else {
            casesToday?.text = countryData?.todayCases.toString()
            deathsToday?.text = countryData?.todayDeaths.toString()
            recoveredToday?.text = countryData?.todayRecovered.toString()
        }
    }


    @Suppress("DEPRECATION")

    private fun showProgressBar(show: Boolean) {
        if (show) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    @Suppress("DEPRECATION")

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

    override fun onDidFinishLoadingStyle() {
        val position = CameraPosition.Builder()
            .target(LatLng(52.00000, 19.00000))
            .zoom(2.0)
            .tilt(0.0)
            .build()
        mapboxMap?.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000)
        Handler().postDelayed({
            showProgressBar(false)
        }, 1000)
    }
}
