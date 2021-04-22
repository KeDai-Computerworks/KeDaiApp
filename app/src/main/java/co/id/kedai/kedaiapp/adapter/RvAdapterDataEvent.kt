package co.id.kedai.kedaiapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.kedai.kedaiapp.activity.WebViewActivity
import co.id.kedai.kedaiapp.api.ApiClient
import co.id.kedai.kedaiapp.databinding.ItemEventBinding
import co.id.kedai.kedaiapp.model.DataResult
import com.bumptech.glide.Glide

class RvAdapterDataEvent(private val eventList: ArrayList<DataResult>) :
    RecyclerView.Adapter<RvAdapterDataEvent.EventViewHolder>() {

    inner class EventViewHolder(val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        private val sb: StringBuilder = StringBuilder()

        fun bind(dataResult: DataResult) {
            binding.tvTitleEvent.text = dataResult.title
            binding.tvAuthorEvent.text = dataResult.author
            binding.tvLocationEvent.text = dataResult.location
            binding.tvDateEvent.text = dataResult.date

            sb.append(ApiClient.URL)
            sb.append(dataResult.thumbnail)

            Glide.with(itemView)
                .load(sb.toString())
                .into(binding.imgEvent)
            sb.setLength(0)

            binding.cvEvent.setOnClickListener {
                val intent = Intent(itemView.context, WebViewActivity::class.java)
                intent.putExtra("category", "events/")
                intent.putExtra("id", dataResult.id.toString())
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(eventList[position])
    }

    override fun getItemCount(): Int = eventList.size

    fun addEvent(list: ArrayList<DataResult>) {
        eventList.addAll(list)
        notifyDataSetChanged()
    }


}