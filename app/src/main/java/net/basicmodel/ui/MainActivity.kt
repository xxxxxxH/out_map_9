package net.basicmodel.ui

import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.yuan.storage.Store
import kotlinx.android.synthetic.main.activity_main.*
import net.basicmodel.R
import net.basicmodel.utils.CommonUtils


@SuppressLint("MissingPermission")
class MainActivity : AppCompatActivity() {
    private var map: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Store.init(this,"data")
        initMap()
        initOptionButton()
    }


    private fun initMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapview) as SupportMapFragment?
        mapFragment!!.getMapAsync {
            map = it
            map!!.uiSettings.isZoomControlsEnabled = true
            map!!.isMyLocationEnabled = true
            map!!.uiSettings.isMyLocationButtonEnabled = true
            CommonUtils.setMapType(map)
            val location = CommonUtils.getLocation(this)
            val lat = location.latitude //纬度30
            val lgt = location.longitude //经度104
            if (lat != 0.0 && lgt != 0.0) {
                map?.let { it1 ->
                    it1.animateCamera(
                        CameraUpdateFactory.newCameraPosition(
                            CommonUtils.getCameraPosition(
                                lat,
                                lgt
                            )
                        ), 1000, null
                    )
                    it1.addMarker(
                        MarkerOptions()
                            .position(LatLng(lat, lgt)).title("On Your Location")
                    )
                    it1.setOnMyLocationButtonClickListener {
                        it1.animateCamera(
                            CameraUpdateFactory.newCameraPosition(
                                CommonUtils.getCameraPosition(
                                    lat,
                                    lgt
                                )
                            ), 1000, null
                        )
                        false
                    }
                }
            }
        }
    }

    private fun initOptionButton() {
        fb.setOnClickListener {
            createSheet()
        }
    }

    override fun onResume() {
        super.onResume()
        CommonUtils.setMapType(map)
    }

    fun createSheet() {
        BottomSheetBuilder(this, R.style.AppTheme_BottomSheetDialog)
            .setMode(BottomSheetBuilder.MODE_LIST)
            .setMenu(R.menu.option_menu)
            .setIconTintColor(Color.BLACK)
            .setItemClickListener {
                when (it.itemId) {
                    R.id.setting -> {
                        startActivity(Intent(this,SettingActivity::class.java))
                    }
                    R.id.share -> {
                        CommonUtils.share(this)
                    }
                    R.id.near -> {
                        createSearchSpinner()
                    }
                    R.id.travel -> {
                        val data = CommonUtils.initTravelData()
                        val randomData = data[(Math.random()*(data.size-0)).toInt()]
                        val lgt = randomData.split(",")[0].toDouble()
                        val lat = randomData.split(",")[1].toDouble()
                        map!!.animateCamera(
                            CameraUpdateFactory.newCameraPosition(
                                CommonUtils.getCameraPosition(
                                    lat,
                                    lgt
                                )
                            ), 1000, null
                        )
                    }
                    R.id.inter -> {
                        startActivity(Intent(this,InteractiveActivity::class.java))
                    }
                }
            }.createDialog().show()
    }

    private fun createSearchSpinner() {
        val spinnerDialog = SpinnerDialog(
            this,
            CommonUtils.initNearbyData(),
            "Select or Search",
            R.style.DialogAnimations_SmileWindow,
            "Close"
        )
        spinnerDialog.setCancellable(true)
        spinnerDialog.setShowKeyboard(false)
        spinnerDialog.showSpinerDialog()
        spinnerDialog.bindOnSpinerListener { item, _ ->
            startSearch(item)
            spinnerDialog.closeSpinerDialog()
        }
    }

    fun startSearch(key: String) {
        if (TextUtils.isEmpty(key))
            return
        try {
            if (TextUtils.isEmpty(key))
                return
            val i = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    "http://maps.google.com/maps?q=${key}&hl=en"
                )
            )
            i.setPackage("com.google.android.apps.maps")
            startActivity(i)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}