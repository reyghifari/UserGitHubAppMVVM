package com.hann.githubappmvvm.presentation.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hann.core.ui.UserAdapter
import com.hann.core.utils.Constants
import com.hann.githubappmvvm.databinding.FragmentFollowBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var adapter: UserAdapter
    private val viewModel: FollowViewModel by viewModel()
    private var getUsername : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        getUsername = activity?.intent?.getStringExtra(Constants.PARAM_USERNAME)

        initRecyclerView()
        showData(index)
    }

    private fun showData(index:Int?) {
        when(index){
            1 -> {
                getUsername?.let { viewModel.getFollower(it) }
                showList()
            }
            2 -> {
                getUsername?.let { viewModel.getFollowing(it) }
                showList()
            }
        }

    }

    private fun showList(){
        viewModel.state.observe(viewLifecycleOwner){
                users ->
            if (users.isLoading){
                binding.shimmerLayoutFollow.startShimmer()
                binding.shimmerLayoutFollow.visibility = View.VISIBLE
            }
            if (users.error.isNotBlank()){
                binding.shimmerLayoutFollow.visibility = View.GONE
                binding.viewErrorFollow.root.visibility = View.VISIBLE
                binding.viewErrorFollow.tvError.text = users.error
            }
            if (users.users.isNotEmpty()){
                binding.shimmerLayoutFollow.stopShimmer()
                binding.shimmerLayoutFollow.visibility = View.GONE
                adapter.setData(users.users)
            }
        }
    }

    private fun initRecyclerView(){
        adapter = UserAdapter()
        binding.rvUserFollow.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUserFollow.adapter = adapter
        binding.rvUserFollow.setHasFixedSize(false)
    }

    companion object{
        const val ARG_SECTION_NUMBER = "section_number"
    }

}