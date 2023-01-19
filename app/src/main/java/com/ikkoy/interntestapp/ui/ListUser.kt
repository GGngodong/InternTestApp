package com.ikkoy.interntestapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ikkoy.interntestapp.UserPreference
import com.ikkoy.interntestapp.databinding.ActivityListUserBinding
import com.ikkoy.interntestapp.network.model.User
import com.ikkoy.interntestapp.adapter.UserAdapter

class ListUser : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: ActivityListUserBinding
    private val viewModel by viewModels<UserViewModel>()
    private lateinit var adapter: UserAdapter
    private var page = 1
    private var totalPage = 1
    private var isLoading = false
    private lateinit var  userPreference: UserPreference
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userPreference = UserPreference(this)
        layoutManager = LinearLayoutManager(this)

        binding.swipeRefresh.setOnRefreshListener(this)
        getUsers(false)

        binding.rvListUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = adapter.itemCount
                if (!isLoading && page < totalPage) {
                    if (visibleItemCount + pastVisibleItem >= total) {
                        page++
                        getUsers(false)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun getUsers(isOnRefresh: Boolean) {
        isLoading = true
        if (!isOnRefresh) binding.progressBar.visibility = View.VISIBLE
        val parameters = HashMap<String, String>()
        parameters["page"] = page.toString()
        viewModel.listUser.observe(this) {
            setUserList(it)
            binding.progressBar.visibility = View.INVISIBLE
            isLoading = false
            binding.swipeRefresh.isRefreshing = false

        }
    }

    private fun setUserList(user: List<User>) {
        adapter = UserAdapter(user)

        binding.apply {
            rvListUser.layoutManager = LinearLayoutManager(this@ListUser)
            rvListUser.adapter = adapter
            swipeRefresh.setOnRefreshListener(this@ListUser)
            rvListUser.setHasFixedSize(true)
        }

        val listUser = ArrayList<User>()
        for (users in user) {
            listUser.clear()
            listUser.addAll(user)
        }

        intent.getStringExtra(NAME).toString()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                userPreference.setSelectedName(data.firstName + " " + data.lastName)
                Intent(this@ListUser, SelectedActivity::class.java).also {
                    startActivity(it)
                }
            }
        })

    }

    companion object {
        const val NAME = "name"
    }

    override fun onRefresh() {
        page = 1
        getUsers(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
