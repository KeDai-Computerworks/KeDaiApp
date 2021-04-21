package co.id.kedai.kedaiapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.kedai.kedaiapp.R
import co.id.kedai.kedaiapp.activity.WebViewActivity
import co.id.kedai.kedaiapp.api.ApiClient
import co.id.kedai.kedaiapp.model.DataResult
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_event.view.*

class RvAdapterDataEvent(private val eventList: ArrayList<DataResult>) :
    RecyclerView.Adapter<RvAdapterDataEvent.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sb: StringBuilder = StringBuilder()

        fun bind(dataResult: DataResult) {
            with(itemView) {
                tv_title_event.text = dataResult.title
                tv_author_event.text = dataResult.author
                tv_location_event.text = dataResult.location
                tv_date_event.text = dataResult.date

                sb.append(ApiClient.URL)
                sb.append(dataResult.thumbnail)

                Glide.with(itemView)
                    .load(sb.toString())
                    .into(img_event)
                sb.setLength(0)

                cv_event.setOnClickListener {
                    val intent = Intent(itemView.context, WebViewActivity::class.java)
                    intent.putExtra("category", "events/")
                    intent.putExtra("id", dataResult.id.toString())
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
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