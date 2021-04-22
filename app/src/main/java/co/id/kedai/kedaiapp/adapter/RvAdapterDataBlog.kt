package co.id.kedai.kedaiapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.kedai.kedaiapp.activity.WebViewActivity
import co.id.kedai.kedaiapp.api.ApiClient
import co.id.kedai.kedaiapp.databinding.ItemBlogBinding
import co.id.kedai.kedaiapp.model.DataResult
import com.bumptech.glide.Glide


class RvAdapterDataBlog(private val blogList: ArrayList<DataResult>) :
    RecyclerView.Adapter<RvAdapterDataBlog.BlogViewHolder>() {

    inner class BlogViewHolder(val binding: ItemBlogBinding) : RecyclerView.ViewHolder(binding.root) {
        private val sb: StringBuilder = StringBuilder()
        fun bind(dataResult: DataResult) {
            binding.tvTitleBlog.text = dataResult.title
            binding.tvCategoryBlog.text = dataResult.category
            binding.tvDateBlog.text = dataResult.created_at

            sb.append(ApiClient.URL)
            sb.append(dataResult.thumbnail)

            Glide.with(itemView)
                .load(sb.toString())
                .into(binding.imgBlog)
            sb.setLength(0)

            binding.cvBlog.setOnClickListener {
                val intent = Intent(itemView.context, WebViewActivity::class.java)
                intent.putExtra("category", "posts/")
                intent.putExtra("id", dataResult.id.toString())
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val binding = ItemBlogBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return BlogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        blogList[position].let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return blogList.size
    }

    fun addBlog(list: ArrayList<DataResult>) {
        blogList.addAll(list)
        notifyDataSetChanged()
    }

}

