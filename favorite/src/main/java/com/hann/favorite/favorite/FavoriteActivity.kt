package com.hann.favorite.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hann.core.ui.UserAdapter
import com.hann.core.utils.Constants
import com.hann.favorite.di.favoriteModule
import com.hann.favorite.favorite.databinding.ActivityFavoriteBinding
import com.hann.githubappmvvm.presentation.detail.DetailUserActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private  val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)

        initRecyclerView()

        favoriteViewModel.favoriteUser.observe(this) { favoriteUser ->
            if (favoriteUser.isNotEmpty()){
                binding.emptyFavoriteFavorite.visibility = View.GONE
                binding.rvFavorite.visibility = View.VISIBLE
                userAdapter.setData(favoriteUser)
            }else{
                binding.emptyFavoriteFavorite.visibility = View.VISIBLE
                binding.rvFavorite.visibility = View.GONE
            }
        }
    }

    private fun initRecyclerView() {
        userAdapter = UserAdapter()
        binding.rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
        binding.rvFavorite.adapter = userAdapter
        binding.rvFavorite.setHasFixedSize(false)
        userAdapter.onItemClick = {
            val intent = Intent(this, DetailUserActivity::class.java)
            intent.putExtra(Constants.PARAM_USERNAME, it.login)
            intent.putExtra(Constants.PARAM_USER, it)
            startActivity(intent)
        }
    }

}