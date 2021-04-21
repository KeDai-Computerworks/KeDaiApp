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
import kotlinx.android.synthetic.main.item_blog.view.*


class RvAdapterDataBlog(private val blogList: ArrayList<DataResult>) :
    RecyclerView.Adapter<RvAdapterDataBlog.BlogViewHolder>() {

    class BlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sb: StringBuilder = StringBuilder()

        fun bind(dataResult: DataResult) {
            with(itemView) {
                tv_title_blog.text = dataResult.title
                tv_category_blog.text = dataResult.category
                tv_date_blog.text = dataResult.created_at

                sb.append(ApiClient.URL)
                sb.append(dataResult.thumbnail)

                Glide.with(itemView)
                    .load(sb.toString())
                    .into(img_blog)
                sb.setLength(0)

                cv_blog.setOnClickListener {
                    val intent = Intent(itemView.context, WebViewActivity::class.java)
                    intent.putExtra("category", "posts/")
                    intent.putExtra("id", dataResult.id.toString())
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_blog, parent, false)
        return BlogViewHolder(view)
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

