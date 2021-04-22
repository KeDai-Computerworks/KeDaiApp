package co.id.kedai.kedaiapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.id.kedai.kedaiapp.adapter.RvAdapterDataBlog
import co.id.kedai.kedaiapp.api.ApiClient
import co.id.kedai.kedaiapp.databinding.FragmentBlogBinding
import co.id.kedai.kedaiapp.model.DataResponse
import co.id.kedai.kedaiapp.model.DataResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BlogFragment(private var category: String) : Fragment() {
    private var _binding: FragmentBlogBinding? = null
    private val binding get() = _binding!!
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: RvAdapterDataBlog
    val dataBlog: ArrayList<DataResult> = ArrayList()
    var visibleItemCount = 0
    var totalItemCount = 0
    var pastVisibleItems = 0
    val viewTreshold = 5
    var isLoading = true
    var previousTotal = 0
    var pageNumber = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBlogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.rvBlog.layoutManager = layoutManager
        binding.shimmerBlog.startShimmer()

        showDataBlog()

        binding.swipeRefresh.setOnRefreshListener {
            isLoading = true
            previousTotal = 0
            pageNumber = 1
            dataBlog.clear()
            showDataBlog()
        }
        binding.rvBlog.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                visibleItemCount = layoutManager.childCount
                totalItemCount = layoutManager.itemCount
                pastVisibleItems = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (dy > 0) {
                    if (isLoading && totalItemCount > previousTotal) {
                        isLoading = false
                        previousTotal = totalItemCount
                    }
                    if (!isLoading && totalItemCount - visibleItemCount <= pastVisibleItems + viewTreshold) {
                        pageNumber += 1
                        loadPage(pageNumber)
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun loadPage(pageNumber: Int) {
        ApiClient.instances.getDataAllBlog(category, pageNumber)
            .enqueue(object : Callback<DataResponse> {
                override fun onResponse(
                    call: Call<DataResponse>,
                    response: Response<DataResponse>
                ) {
                    if (response.isSuccessful && response.body()?.data.toString() != "[]") {
                        adapter.addBlog(response.body()?.data!!)
                    } else dataBlog.clear()
                    binding.swipeRefresh.isRefreshing = false
                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    dataBlog.clear()
                    binding.swipeRefresh.isRefreshing = false
                }
            })
    }

    private fun showDataBlog() {
        ApiClient.instances.getDataAllBlog(category, pageNumber)
            .enqueue(object : Callback<DataResponse> {
                override fun onResponse(
                    call: Call<DataResponse>,
                    response: Response<DataResponse>
                ) {
                    if (isAdded) {
                        if (response.isSuccessful) {
                            adapter = RvAdapterDataBlog(response.body()!!.data)
                            binding.rvBlog.adapter = adapter
                            adapter.notifyDataSetChanged()
                            binding.rvBlog.isVisible = true
                            binding.swipeRefresh.isRefreshing = false
                            binding.shimmerBlog.stopShimmer()
                            binding.shimmerBlog.isVisible = false
                            binding.imgError.isVisible = false
                            binding.tvError.isVisible = false
                        } else {
                            errorPage()
                        }
                    } else {
                        if (isAdded) {
                            errorPage()
                        }
                    }
                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    errorPage()
                }
            })
    }

    fun errorPage() {
        dataBlog.clear()
        binding.rvBlog.isVisible = false
        binding.swipeRefresh.isRefreshing = false
        binding.shimmerBlog.stopShimmer()
        binding.shimmerBlog.isVisible = false
        binding.tvError.isVisible = true
        binding.imgError.isVisible = true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        isLoading = true
        previousTotal = 0
        pageNumber = 1
        dataBlog.clear()
        showDataBlog()
    }
}