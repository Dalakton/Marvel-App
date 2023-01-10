package me.project.marvelapp.presentation.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import me.project.marvelapp.R
import me.project.marvelapp.databinding.FragmentListCharacterBinding
import me.project.marvelapp.presentation.adapters.CharacterAdapter
import me.project.marvelapp.presentation.state.ResourceState
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListCharacterFragment : Fragment() {

    private var _binding: FragmentListCharacterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListCharacterViewModel by viewModel()
    private val characterAdapter by lazy { CharacterAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        clickAdapter()
        collectobserve()
    }

    private fun collectobserve() = lifecycleScope.launch {
        viewModel.list.collect { resources ->
            when (resources) {
                is ResourceState.Sucess -> {
                    resources.data?.let { values ->
                        binding.progressCircular.visibility = View.INVISIBLE
                        characterAdapter.characters = values.data.results.toList()
                        Log.i("List", "DEU CERTO")
                    }
                }
                is ResourceState.Error -> {
                    binding.progressCircular.visibility = View.INVISIBLE
                    resources.message?.let {
                        Toast.makeText(
                            requireContext(),
                            R.string.an_error_ocurred,
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("List", "DEU ERRO")


                    }

                }
                is ResourceState.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                else -> {

                }

            }

        }
    }

    //Navegação AO clicar em um character para a tela de detalhes , passanddo o characterModel como argumento
    private fun clickAdapter() {
        characterAdapter.setOnClickListener { characterModel ->
            val action =
                ListCharacterFragmentDirections.actionListCharacterFragmentToDetailFragment(
                    characterModel
                )
            findNavController().navigate(action)

        }
    }

    private fun setupRecycler() {
        binding.recyclerCharacters.apply {
            adapter = characterAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


}