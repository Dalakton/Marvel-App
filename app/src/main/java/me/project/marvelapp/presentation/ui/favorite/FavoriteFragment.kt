package me.project.marvelapp.presentation.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import me.project.marvelapp.R
import me.project.marvelapp.databinding.FragmentFavoriteBinding
import me.project.marvelapp.presentation.adapters.CharacterAdapter
import me.project.marvelapp.presentation.state.ResourceState
import me.project.marvelapp.util.hide
import me.project.marvelapp.util.show
import me.project.marvelapp.util.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by viewModel()
    private val characterAdapter by lazy { CharacterAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        clickAdapter()
        observer()
    }

    private fun observer() = lifecycleScope.launch {
        viewModel.favorites.collect { resultListCharacterModel ->
            when (resultListCharacterModel) {
                is ResourceState.Sucess -> {
                    resultListCharacterModel.data?.let {
                        binding.tvEmptyList.hide()
                        characterAdapter.characters = it.toList()
                    }

                }
                is ResourceState.Empty -> {
                    binding.tvEmptyList.show()
                }
                else -> {}
            }

        }
    }

    private fun clickAdapter() {
        characterAdapter.setOnClickListener { characterModel ->
            val action =
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(characterModel)
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() = with(binding) {
        rvFavoriteCharacter.apply {
            adapter = characterAdapter
            layoutManager = LinearLayoutManager(context)
        }
        ItemTouchHelper(itemTouchHelperCallback()).attachToRecyclerView(rvFavoriteCharacter)

    }

    private fun itemTouchHelperCallback(): ItemTouchHelper.SimpleCallback {
        return object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val character = characterAdapter.getCharacterposition(viewHolder.adapterPosition)
                viewModel.delete(character).also {
                    toast(getString(R.string.message_delete_character))
                }


            }

        }
    }

}