package co.id.kedai.kedaiapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.id.kedai.kedaiapp.adapter.RvAdapterDataEbook
import co.id.kedai.kedaiapp.api.ApiClient
import co.id.kedai.kedaiapp.databinding.FragmentEbookBinding
import co.id.kedai.kedaiapp.model.DataResponse
import co.id.kedai.kedaiapp.model.DataResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EbookFragment : Fragment() {

    private var _binding: FragmentEbookBinding? = null
    private val binding get() = _binding!!
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: RvAdapterDataEbook
    val dataEbook: ArrayList<DataResult> = ArrayList()

    var visibleItemCount = 0
    var totalItemCount = 0
    var pastVisibleItems = 0
    val viewThreshold = 5

    var isLoading = true
    var previousTotal = 0
    var pageNumber = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (activity != null && isAdded) {
            // Inflate the layout for this fragment
            _binding = FragmentEbookBinding.inflate(inflater, container, false)
            return binding.root
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null && isAdded) {

            layoutManager = LinearLayoutManager(activity?.applicationContext)
            binding.rvEbook.layoutManager = layoutManager
            binding.shimmerEbook.startShimmer()

            showDataResponse()

            binding.swipeRefresh.setOnRefreshListener {
                isLoading = true
                previousTotal = 0
                pageNumber = 1
                dataEbook.clear()
                showDataResponse()
            }


            binding.rvEbook.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisibleItems = layoutManager.findFirstCompletelyVisibleItemPosition()

                    if (dy > 0) {
                        if (isLoading && totalItemCount > previousTotal) {
                            isLoading = false
                            previousTotal = totalItemCount
                            Log.e("dataKosong", "$totalItemCount")
                        }
                        if (!isLoading && totalItemCount - visibleItemCount <= pastVisibleItems + viewThreshold
                        ) {
                            pageNumber += 1
                            loadPage(pageNumber)
                            isLoading = true
                        }
                    }
                }
            })
        }
    }

    private fun loadPage(pageNumber: Int) {
        if (activity != null && isAdded) {
            ApiClient.instances.getDataEbook(pageNumber)
                .enqueue(object : Callback<DataResponse> {
                    override fun onResponse(
                        call: Call<DataResponse>,
                        response: Response<DataResponse>
                    ) {
                        if (response.isSuccessful && response.body()?.data.toString() != "[]") {
                            response.body()?.let {
                                adapter.addEbook(it.data)
                            }
                            binding.swipeRefresh.isRefreshing = false
                        } else dataEbook.clear()
                        binding.swipeRefresh.isRefreshing = false
                    }

                    override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                        dataEbook.clear()
                        binding.swipeRefresh.isRefreshing = false
                    }
                })
        }
    }

    private fun showDataResponse() {
        if (activity != null && isAdded) {
            ApiClient.instances.getDataEbook(1).enqueue(object : Callback<DataResponse> {
                override fun onResponse(
                    call: Call<DataResponse>,
                    response: Response<DataResponse>
                ) {
                    adapter = RvAdapterDataEbook(response.body()!!.data)
                    if (isAdded) {
                        if (response.isSuccessful) {
                            binding.rvEbook.adapter = adapter
                            binding.rvEbook.isVisible = true
                            binding.swipeRefresh.isRefreshing = false
                            binding.shimmerEbook.stopShimmer()
                            binding.shimmerEbook.isVisible = false
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
    }

    fun errorPage() {
        dataEbook.clear()
        binding.rvEbook.isVisible = false
        binding.swipeRefresh.isRefreshing = false
        binding.shimmerEbook.stopShimmer()
        binding.shimmerEbook.isVisible = false
        binding.tvError.isVisible = true
        binding.imgError.isVisible = true
    }
}
