package net.basicmodel.ui

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kongqw.permissionslibrary.XPermissionsManager
import com.kongqw.permissionslibrary.listener.OnRequestPermissionsListener
import net.basicmodel.R

class SplashActivity : AppCompatActivity(), OnRequestPermissionsListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        XPermissionsManager.init(application)
        val permissions = ArrayList<String>()
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permissions.add(Manifest.permission.ACCESS_NETWORK_STATE)
        permissions.add(Manifest.permission.ACCESS_WIFI_STATE)
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        XPermissionsManager.checkPermissions(1,permissions,this)
    }

    override fun onPermissionsAuthorized(requestCode: Int, permissions: ArrayList<String>) {
        if (requestCode == 1)
            startActivity(Intent(this,MainActivity::class.java))
    }

    override fun onPermissionsNoAuthorization(
        requestCode: Int,
        lacksPermissions: ArrayList<String>
    ) {
        if (requestCode == 1)
            finish()
    }
}