package co.id.kedai.kedaiapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.kedai.kedaiapp.activity.PdfReaderActivity
import co.id.kedai.kedaiapp.api.ApiClient
import co.id.kedai.kedaiapp.databinding.ItemEbookBinding
import co.id.kedai.kedaiapp.model.DataResult
import com.bumptech.glide.Glide

class RvAdapterDataEbook(private val ebookList: ArrayList<DataResult>) :
    RecyclerView.Adapter<RvAdapterDataEbook.EbookViewHolder>() {

    inner class EbookViewHolder(val binding: ItemEbookBinding) : RecyclerView.ViewHolder(binding.root) {
        private val sb: StringBuilder = StringBuilder()

        fun bind(dataResult: DataResult) {
            binding.tvTitleEbook.text = dataResult.title
            binding.tvAuthorEbook.text = dataResult.author
            binding.tvCategoryEbook.text = dataResult.category
            binding.tvDateEbook.text = dataResult.created_at

            sb.append(ApiClient.URL)
            sb.append(dataResult.thumbnail)

            Glide.with(itemView)
                .load(sb.toString())
                .into(binding.imgEbook)
            sb.setLength(0)

            binding.cvEbook.setOnClickListener {
                val intent = Intent(itemView.context, PdfReaderActivity::class.java)
                intent.putExtra("path", dataResult.path)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EbookViewHolder {
        val binding = ItemEbookBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return EbookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EbookViewHolder, position: Int) {
//        dataResponse.data[position].let { holder.bind(it) }
        holder.bind(ebookList[position])
    }

    override fun getItemCount(): Int = ebookList.size

    fun addEbook(list: ArrayList<DataResult>) {
        ebookList.addAll(list)
        notifyDataSetChanged()
    }
}