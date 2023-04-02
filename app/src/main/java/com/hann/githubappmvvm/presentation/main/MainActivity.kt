package com.hann.githubappmvvm.presentation.main

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hann.core.ui.UserAdapter
import com.hann.core.utils.Constants
import com.hann.githubappmvvm.R
import com.hann.githubappmvvm.databinding.ActivityMainBinding
import com.hann.githubappmvvm.presentation.detail.DetailUserActivity
import com.hann.githubappmvvm.presentation.setting.SettingActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var userAdapter: UserAdapter
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                mainViewModel.isLoadingSplash.value
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()

        mainViewModel.state.observe(this, Observer(this::setData))
    }

    private fun setData(user : UserListState){
            if (user.isLoading){
                binding.shimmerLayout.visibility = View.VISIBLE
                binding.shimmerLayout.startShimmer()
                binding.rvUser.visibility = View.GONE
            }
            if (user.error.isNotBlank()){
                binding.rvUser.visibility = View.GONE
                binding.shimmerLayout.visibility = View.GONE
                binding.viewError.root.visibility = View.VISIBLE
            }
        if (user.users.isNotEmpty()){
            binding.shimmerLayout.stopShimmer()
            binding.shimmerLayout.visibility = View.GONE
            binding.rvUser.visibility = View.VISIBLE
            userAdapter.setData(user.users)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        val search = menu?.findItem(R.id.search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_settings -> {
                val intent = Intent(applicationContext, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_favorite -> {
                val uri = Uri.parse("githubapp://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        lifecycleScope.launch {
            delay(500)
            searchUser(newText)
        }
        return true
    }

    private fun searchUser(query: String?){
        if(query.isNullOrEmpty()) {
            return
        }
        mainViewModel.getUsers(query)
    }

    private fun initRecyclerView(){
        userAdapter = UserAdapter()
        binding.rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvUser.adapter = userAdapter
        binding.rvUser.setHasFixedSize(false)
        userAdapter.onItemClick = {
            val intent = Intent(this, DetailUserActivity::class.java)
            intent.putExtra(Constants.PARAM_USERNAME, it.login)
            intent.putExtra(Constants.PARAM_USER, it)
            startActivity(intent)
        }
    }

}

