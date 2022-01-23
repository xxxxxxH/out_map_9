package net.basicmodel.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hanuor.pearl.Pearl
import net.basicmodel.R
import net.basicmodel.entity.DataEntity

class InterAdapter(data: MutableList<DataEntity>) :
    BaseQuickAdapter<DataEntity, BaseViewHolder>(R.layout.item_inter, data) {
    override fun convert(holder: BaseViewHolder, item: DataEntity) {
        Pearl.imageLoader(context, item.imageUrl, holder.getView(R.id.image), R.mipmap.ic_launcher)
        holder.setText(R.id.itemTv, item.title)
    }
}