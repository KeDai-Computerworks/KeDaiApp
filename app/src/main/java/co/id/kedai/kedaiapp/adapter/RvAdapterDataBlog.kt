package co.id.kedai.kedaiapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import co.id.kedai.kedaiapp.R
import co.id.kedai.kedaiapp.activity.WebViewActivity
import co.id.kedai.kedaiapp.api.ApiClient
import co.id.kedai.kedaiapp.model.DataResult
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
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

                img_blog.isVisible = false

                Picasso.with(context)
                    .load(sb.toString())
                    .into(img_blog, object : Callback {
                        override fun onSuccess() {
                            img_blog.isVisible = true
                        }

                        override fun onError() {}
                    })

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

    fun addBlog(list: List<DataResult?>) {
        for (add in list) {
            add?.let { blogList.add(it) }
        }
        notifyDataSetChanged()

    }
}


