package co.id.kedai.kedaiapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.kedai.kedaiapp.databinding.ContainerCaraMendaftarBinding
import co.id.kedai.kedaiapp.model.DataCaraMendaftar

class AdapterCaraMendaftar(private val dataCaraMendaftar: List<DataCaraMendaftar>) :
    RecyclerView.Adapter<AdapterCaraMendaftar.SliderHolder>() {

    inner class SliderHolder(val binding: ContainerCaraMendaftarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataOnBoarding: DataCaraMendaftar) {
            binding.tvDeskripsi.text = dataOnBoarding.deskripsi
            binding.imgGambar.setImageResource(dataOnBoarding.gambar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderHolder {
        val binding = ContainerCaraMendaftarBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderHolder, position: Int) {
        holder.bind(dataCaraMendaftar[position])
    }

    override fun getItemCount(): Int = dataCaraMendaftar.size
}