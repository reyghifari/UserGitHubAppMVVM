package com.hann.githubappmvvm.presentation.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.hann.core.domain.model.DetailUser
import com.hann.core.domain.model.User
import com.hann.core.utils.Constants
import com.hann.githubappmvvm.R
import com.hann.githubappmvvm.databinding.ActivityDetailUserBinding
import com.hann.githubappmvvm.presentation.ui.SectionPagerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailUserBinding
    private var statusFavorite: Boolean = false
    private lateinit var userFavorite: User
    private val detailViewModel: DetailUserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        userFavorite = intent.getParcelableExtra(Constants.PARAM_USER)!!

        detailViewModel.userFavorite(userFavorite.id.toString())

        detailViewModel.state.observe(this, Observer(this::showDetailState))
        detailViewModel.favorite.observe(this){
            userFavorite = it
            statusFavorite = it.isFavorite
        }

        val sectionPagerAdapter = SectionPagerAdapter(this)
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager){
                tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        binding.btnFloatDetail.setOnClickListener {
            statusFavorite = !statusFavorite
            setStatusFavorite(statusFavorite)
            detailViewModel.setFavoriteUser(userFavorite, statusFavorite)
        }
    }

    private fun showDetailState(detail : DetailUserState){
        if (detail.isLoading){
            binding.shimmerLayoutDetail.startShimmer()
            binding.shimmerLayoutDetail.visibility = View.VISIBLE
        }
        if (detail.error.isNotBlank()){
            binding.shimmerLayoutDetail.visibility = View.GONE
            binding.viewError.root.visibility = View.VISIBLE
            binding.viewError.tvError.text = detail.error
        }
        if (detail.user != null){
            binding.shimmerLayoutDetail.stopShimmer()
            binding.shimmerLayoutDetail.visibility = View.GONE
            showDetailGame(detail.user)
        }
    }

    private fun showDetailGame(user: DetailUser?) {
        user.let {
            binding.tvNameDetail.text = user?.name
            binding.tvUsernameDetail.text = user?.login
            binding.tvFollowersDetail.text = user?.followers.toString()
            binding.tvFollowingDetail.text = user?.following.toString()
            Glide.with(this)
                .load(user?.avatar_url)
                .into(binding.imageViewDetail)

            val statusFavorite = userFavorite.isFavorite
            setStatusFavorite(statusFavorite)
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.btnFloatDetail.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite))
        } else {
            binding.btnFloatDetail.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_unfavorite))
        }
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

}