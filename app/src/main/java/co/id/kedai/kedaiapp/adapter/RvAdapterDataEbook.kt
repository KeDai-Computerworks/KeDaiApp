package co.id.kedai.kedaiapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.kedai.kedaiapp.R
import co.id.kedai.kedaiapp.activity.PdfReaderActivity
import co.id.kedai.kedaiapp.api.ApiClient
import co.id.kedai.kedaiapp.model.DataResult
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_ebook.view.*

class RvAdapterDataEbook(private val ebookList: ArrayList<DataResult>) :
    RecyclerView.Adapter<RvAdapterDataEbook.EbookViewHolder>() {

    class EbookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sb: StringBuilder = StringBuilder()

        fun bind(dataResult: DataResult) {
            with(itemView) {
                tv_title_ebook.text = dataResult.title
                tv_author_ebook.text = dataResult.author
                tv_category_ebook.text = dataResult.category
                tv_date_ebook.text = dataResult.created_at

                sb.append(ApiClient.URL)
                sb.append(dataResult.thumbnail)

                Glide.with(itemView)
                    .load(sb.toString())
                    .into(img_ebook)
                sb.setLength(0)

                cv_ebook.setOnClickListener {
                    val intent = Intent(itemView.context, PdfReaderActivity::class.java)
                    intent.putExtra("path", dataResult.path)
                    itemView.context.startActivity(intent)

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EbookViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_ebook, parent, false)
        return EbookViewHolder(view)
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