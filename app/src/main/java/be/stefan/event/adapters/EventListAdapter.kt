package be.stefan.event.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.stefan.event.R
import be.stefan.event.models.Event

class EventListAdapter(private val list: List<Event>) : RecyclerView.Adapter<EventListAdapter.ViewHolder>() {

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        val tvTitle = v.findViewById(R.id.tv_title) as TextView
        val tvTime = v.findViewById(R.id.tv_time) as TextView
        val tvDesc = v.findViewById(R.id.tv_desc) as TextView
        var tvAddress = v.findViewById(R.id.tv_address) as TextView
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item : Event = list[position]
        holder.tvTitle.text = item.title
        holder.tvTime.text = item.time
        holder.tvDesc.text = item.desc
        holder.tvAddress.text = item.address
    }

    override fun getItemCount(): Int = list.size
}