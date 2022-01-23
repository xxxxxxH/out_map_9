package net.basicmodel.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.yuan.storage.Storage
import com.yuan.storage.Store
import gdut.bsx.share2.Share2
import gdut.bsx.share2.ShareContentType
import net.basicmodel.R


object CommonUtils {
    const val url = "https://www.google.com/streetview/feed/gallery/data.json"
    @SuppressLint("MissingPermission")
    fun getLocation(context: Context): Location {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) as Location
    }

    fun getCameraPosition(lat: Double, lgt: Double): CameraPosition {
        return CameraPosition.builder().target(LatLng(lat, lgt))
            .zoom(15.5f)
            .bearing(0f)
            .tilt(25f)
            .build()
    }

    fun initNearbyData(): ArrayList<String> {
        val result = ArrayList<String>()
        result.add("airport")
        result.add("atm")
        result.add("bakery")
        result.add("bank")
        result.add("bus")
        result.add("cafe")
        result.add("church")
        result.add("cloth")
        result.add("dentist")
        result.add("doctor")
        result.add("fire station")
        result.add("gas station")
        result.add("hospital")
        result.add("hotel")
        result.add("jewelry")
        result.add("mall")
        result.add("mosque")
        result.add("park")
        result.add("pet")
        result.add("pharmacy")
        result.add("police")
        result.add("post office")
        result.add("salon")
        result.add("school")
        result.add("shoe")
        result.add("stadium")
        result.add("university")
        result.add("zoo")
        return result
    }

    fun initTravelData(): ArrayList<String> {
        val result = ArrayList<String>()
        result.add("1.32,42.31")
        result.add("-60.00,-36.30")
        result.add("149.08,-35.15")
        result.add("16.22,48.12")
        result.add("4.21,50.51")
        result.add("-68.10,-16.20")
        result.add("-75.42,45.27")
        result.add("-70.40,-33.24")
        result.add("116.20,39.55")
        result.add("125.30,39.09")
        result.add("125.30,39.09")
        result.add("31.14,30.01")
        result.add("38.42,9.02")
        result.add("25.03,60.15")
        result.add("2.20,48.50")
        result.add("13.25,52.30")
        result.add("23.46,37.58")
        result.add("-51.35,64.10")
        result.add("-72.20,18.40")
        result.add("19.05,47.29")
        result.add("-21.57,64.10")
        result.add("77.13,28.37")
        result.add("-6.15,53.21")
        result.add("12.29,41.54")
        result.add("48.00,29.30")
        result.add("6.09,49.37")
        result.add("73.28,4.00")
        result.add("-99.10,19.20")
        result.add("04.54,52.23")
        result.add("174.46,-41.19")
        result.add("10.45,59.55")
        result.add("-79.25,9.00")
        result.add("-9.10,38.42")
        result.add("30.04,-1.59")
        result.add("-17.29,14.34")
        result.add("-3.45,40.25")
        result.add("7.28,46.57")
        result.add("30.28,50.30")
        result.add("-77.02,39.91")
        result.add("31.02,-17.43")
        return result
    }


    fun share(activity: Activity) {
        Share2.Builder(activity)
            .setContentType(ShareContentType.TEXT)
            .setTextContent(activity.getString(R.string.app_name))
            .setTitle("Share Map")
            .build().shareBySystem()
    }

    fun setMapType(map:GoogleMap?){
        map?.let {
            when(Store.get("type","")){
                "normal" -> {
                    it.mapType = GoogleMap.MAP_TYPE_NORMAL
                }
                "hybird" -> {
                    it.mapType = GoogleMap.MAP_TYPE_HYBRID
                }
                "sat" -> {
                    it.mapType = GoogleMap.MAP_TYPE_SATELLITE
                }
                "terrain" -> {
                    it.mapType = GoogleMap.MAP_TYPE_TERRAIN
                }
            }
        }

    }
}