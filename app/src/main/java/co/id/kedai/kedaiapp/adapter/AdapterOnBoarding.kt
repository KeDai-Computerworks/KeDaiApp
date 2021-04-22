package co.id.kedai.kedaiapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.kedai.kedaiapp.databinding.ContainerOnBoardingBinding
import co.id.kedai.kedaiapp.model.DataOnBoarding

class AdapterOnBoarding(private val dataOnBoarding: List<DataOnBoarding>) :
    RecyclerView.Adapter<AdapterOnBoarding.SliderHolder>() {

    inner class SliderHolder(val binding: ContainerOnBoardingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataOnBoarding: DataOnBoarding) {
            binding.tvTitle.text = dataOnBoarding.judul
            binding.tvDeskripsi.text = dataOnBoarding.deskripsi
            binding.imgGambar.setImageResource(dataOnBoarding.gambar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderHolder {
        val binding = ContainerOnBoardingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderHolder, position: Int) {
        holder.bind(dataOnBoarding[position])
    }

    override fun getItemCount(): Int = dataOnBoarding.size
}