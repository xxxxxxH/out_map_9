package net.basicmodel.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import kotlinx.android.synthetic.main.activity_interactive.*
import me.originqiu.library.CallBuilder
import me.originqiu.library.Thunder
import net.basicmodel.R
import net.basicmodel.adapter.InterAdapter
import net.basicmodel.entity.DataEntity
import net.basicmodel.utils.CommonUtils


class InteractiveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interactive)
        Thunder.with(this)
            .assign(
                CallBuilder().url(CommonUtils.url)
                    .buildGet()
            )
            .finish {response->
                // handle response here
                val data = ArrayList<DataEntity>()
                val map: Map<String, DataEntity> =
                    JSON.parseObject(
                        response.body,
                        object : TypeReference<Map<String, DataEntity>>() {})
                val m: Set<Map.Entry<String, DataEntity>> = map.entries
                val it: Iterator<Map.Entry<String, DataEntity>> = m.iterator()
                do {
                    val en: Map.Entry<String, DataEntity> = it.next()
                    val json = JSON.toJSON(en.value)
                    val entity: DataEntity =
                        JSON.parseObject(json.toString(), DataEntity::class.java)
                    entity.key = en.key
                    if (TextUtils.isEmpty(entity.panoid)) {
                        continue
                    } else {
                        if (entity.panoid == "LiAWseC5n46JieDt9Dkevw") {
                            continue
                        }
                    }
                    if (entity.fife) {
                        entity.imageUrl =
                            "https://lh4.googleusercontent.com/" + entity.panoid + "/w400-h300-fo90-ya0-pi0/"
                        continue
                    } else {
                        entity.imageUrl =
                            "https://geo0.ggpht.com/cbk?output=thumbnail&thumb=2&panoid=" + entity.panoid
                    }
                    data.add(entity)
                } while (it.hasNext())
                loading.visibility = View.GONE
                val adapter = InterAdapter(data)
                recycler.layoutManager = LinearLayoutManager(this)
                recycler.adapter = adapter
            }
            .broken {
                // handle exception here
                Log.i("xxxxxxH",it.toString())
            }
            .execute()
    }
}