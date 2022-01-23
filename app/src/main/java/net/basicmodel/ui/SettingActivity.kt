package net.basicmodel.ui

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.nightonke.jellytogglebutton.State
import com.yuan.storage.Store
import kotlinx.android.synthetic.main.activity_setting.*
import net.basicmodel.R

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val defaultType = Store.get("type","")
        if (!TextUtils.isEmpty(defaultType)){
            when(defaultType){
                "normal"->{
                    normal.isChecked = true
                    hybird.isChecked = false
                    sat.isChecked = false
                    terrain.isChecked = false
                }
                "hybird" ->{
                    normal.isChecked = false
                    hybird.isChecked = true
                    sat.isChecked = false
                    terrain.isChecked = false
                }
                "sat" -> {
                    normal.isChecked = false
                    hybird.isChecked = false
                    sat.isChecked = true
                    terrain.isChecked = false
                }
                "terrain" -> {
                    normal.isChecked = false
                    hybird.isChecked = false
                    sat.isChecked = false
                    terrain.isChecked = true
                }
            }
        }
        normal.setOnStateChangeListener { _, state, _ ->

            if (state.equals(State.RIGHT)) {
                //打开
                hybird.isChecked = false
                sat.isChecked = false
                terrain.isChecked = false
                Store.put("type","normal")
            }
        }
        hybird.setOnStateChangeListener { _, state, _ ->

            if (state.equals(State.RIGHT)) {
                //打开
                normal.isChecked = false
                sat.isChecked = false
                terrain.isChecked = false
                Store.put("type","hybird")
            }
        }
        sat.setOnStateChangeListener { _, state, _ ->

            if (state.equals(State.RIGHT)) {
                //打开
                normal.isChecked = false
                hybird.isChecked = false
                terrain.isChecked = false
                Store.put("type","sat")
            }
        }
        terrain.setOnStateChangeListener { _, state, _ ->
            if (state.equals(State.RIGHT)) {
                //打开
                normal.isChecked = false
                hybird.isChecked = false
                sat.isChecked = false
                Store.put("type","terrain")
            }
        }
    }
}