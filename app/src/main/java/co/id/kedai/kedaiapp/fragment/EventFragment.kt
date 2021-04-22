package co.id.kedai.kedaiapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.id.kedai.kedaiapp.adapter.RvAdapterDataEvent
import co.id.kedai.kedaiapp.api.ApiClient
import co.id.kedai.kedaiapp.databinding.FragmentEventBinding
import co.id.kedai.kedaiapp.model.DataResponse
import co.id.kedai.kedaiapp.model.DataResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventFragment : Fragment() {
    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: RvAdapterDataEvent
    val dataEvent: ArrayList<DataResult> = ArrayList()
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
        // Inflate the layout for this fragment
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.rvEvent.layoutManager = layoutManager
        binding.shimmerEvent.startShimmer()

        showDataEvent()

        binding.swipeRefresh.setOnRefreshListener {
            isLoading = true
            previousTotal = 0
            pageNumber = 1
            dataEvent.clear()
            showDataEvent()
        }
        binding.rvEvent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

    private fun loadPage(pageNumber: Int) {
        ApiClient.instances.getDataEvent(pageNumber)
            .enqueue(object : Callback<DataResponse> {
                override fun onResponse(
                    call: Call<DataResponse>,
                    response: Response<DataResponse>
                ) {
                    if (response.isSuccessful && response.body()?.data.toString() != "[]") {
                        adapter.addEvent(response.body()?.data!!)
                    } else dataEvent.clear()
                    binding.swipeRefresh.isRefreshing = false
                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    dataEvent.clear()
                    binding.swipeRefresh.isRefreshing = false
                }
            })
    }

    private fun showDataEvent() {
        ApiClient.instances.getDataEvent(pageNumber)
            .enqueue(object : Callback<DataResponse> {
                override fun onResponse(
                    call: Call<DataResponse>,
                    response: Response<DataResponse>
                ) {
                    if (isAdded) {
                        if (response.isSuccessful) {
                            adapter = RvAdapterDataEvent(response.body()!!.data)
                            binding.rvEvent.adapter = adapter
                            adapter.notifyDataSetChanged()
                            binding.rvEvent.isVisible = true
                            binding.swipeRefresh.isRefreshing = false
                            binding.shimmerEvent.stopShimmer()
                            binding.shimmerEvent.isVisible = false
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

    private fun errorPage() {
        dataEvent.clear()
        binding.rvEvent.isVisible = false
        binding.swipeRefresh.isRefreshing = false
        binding.shimmerEvent.stopShimmer()
        binding.shimmerEvent.isVisible = false
        binding.tvError.isVisible = true
        binding.imgError.isVisible = true
    }

    override fun onResume() {
        super.onResume()
        isLoading = true
        previousTotal = 0
        pageNumber = 1
        dataEvent.clear()
        showDataEvent()
    }
}