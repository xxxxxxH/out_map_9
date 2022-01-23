package net.basicmodel.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gjiazhe.panoramaimageview.GyroscopeObserver
import com.hanuor.pearl.Pearl
import kotlinx.android.synthetic.main.activity_preview.*
import net.basicmodel.R


class PreviewActivity : AppCompatActivity() {
    private val url: String by lazy {
        intent.getStringExtra("url") as String
    }


    private var gyroscopeObserver: GyroscopeObserver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        panoramaImageView.setEnablePanoramaMode(true)
        panoramaImageView.setEnableScrollbar(true)
        panoramaImageView.isInvertScrollDirection = false
        gyroscopeObserver = GyroscopeObserver()
        gyroscopeObserver!!.setMaxRotateRadian(Math.PI/2);
        Pearl.imageLoader(this, url, panoramaImageView, R.mipmap.ic_launcher)
        panoramaImageView.setGyroscopeObserver(gyroscopeObserver);
    }

    override fun onResume() {
        super.onResume()
        // Register GyroscopeObserver.
        gyroscopeObserver!!.register(this)
    }

    override fun onPause() {
        super.onPause()
        // Unregister GyroscopeObserver.
        gyroscopeObserver!!.unregister()
    }
}