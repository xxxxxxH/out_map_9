package net.basicmodel.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import kotlinx.android.synthetic.main.activity_details.*
import me.originqiu.library.CallBuilder
import me.originqiu.library.Thunder
import net.basicmodel.R
import net.basicmodel.adapter.DetailsAdapter
import net.basicmodel.entity.DataEntity

class DetailsActivity : AppCompatActivity() {
    private val originEntity: DataEntity by lazy {
        intent.getSerializableExtra("data") as DataEntity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val key = originEntity.key
        val url = "https://www.google.com/streetview/feed/gallery/collection/$key.json"
        Thunder.with(this)
            .assign(
                CallBuilder().url(url)
                    .buildGet()
            )
            .finish { response ->
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
                    val entity1: DataEntity =
                        JSON.parseObject(json.toString(), DataEntity::class.java)
                    entity1.pannoId = entity1.panoid
                    if (originEntity.fife) {
                        entity1.imageUrl =
                            "https://lh4.googleusercontent.com/" + entity1.pannoId + "/w400-h300-fo90-ya0-pi0/"
                    } else {
                        entity1.imageUrl =
                            "https://geo0.ggpht.com/cbk?output=thumbnail&thumb=2&panoid=" + entity1.panoid
                    }
                    data.add(entity1)

                } while (it.hasNext())
                loading.visibility = View.GONE
                val adapter = DetailsAdapter(data)
                recycler.layoutManager = LinearLayoutManager(this)
                recycler.adapter = adapter
                adapter.setOnItemClickListener { adapter1, _, position ->
                    val url2 = (adapter1.data[position] as DataEntity).imageUrl
                    val i = Intent(this, PreviewActivity::class.java)
                    i.putExtra("url", url2)
                    startActivity(i)
                }
            }
            .broken {
                // handle exception here
                loading.visibility = View.GONE
                Log.i("xxxxxxH", it.toString())
            }
            .execute()
    }
}