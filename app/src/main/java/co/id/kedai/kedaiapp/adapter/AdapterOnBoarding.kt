package co.id.kedai.kedaiapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.kedai.kedaiapp.R
import co.id.kedai.kedaiapp.model.DataOnBoarding
import kotlinx.android.synthetic.main.container_on_boarding.view.*

class AdapterOnBoarding(private val dataOnBoarding: List<DataOnBoarding>) :
    RecyclerView.Adapter<AdapterOnBoarding.SliderHolder>() {

    class SliderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(dataOnBoarding: DataOnBoarding) {
            with(itemView) {
                tvTitle.text = dataOnBoarding.judul
                tvDeskripsi.text = dataOnBoarding.deskripsi
                imgGambar.setImageResource(dataOnBoarding.gambar)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.container_on_boarding, parent, false)
        return SliderHolder(view)
    }

    override fun onBindViewHolder(holder: SliderHolder, position: Int) {
        holder.bind(dataOnBoarding[position])
    }

    override fun getItemCount(): Int = dataOnBoarding.size
}