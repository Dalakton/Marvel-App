package me.project.marvelapp.presentation.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import me.project.marvelapp.R
import me.project.marvelapp.databinding.FragmentSearchBinding
import me.project.marvelapp.presentation.adapters.CharacterAdapter
import me.project.marvelapp.presentation.state.ResourceState
import me.project.marvelapp.util.Constants.DEFAULT_QUERY
import me.project.marvelapp.util.Constants.LAST_SEARCH_QUERY
import me.project.marvelapp.util.hide
import me.project.marvelapp.util.show
import me.project.marvelapp.util.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModel()
    private val characterAdapter by lazy { CharacterAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        clickAdapter()
        // recuperando o estado da instancia trazendo o que o usuario escreveu no editText
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        searchInit(query)
        collectObserve()
    }

    // coletando o estado de retorno da pesquisa e executando suas ações com base no retorno.
    private fun collectObserve() = lifecycleScope.launch {
        viewModel.searchCharacter.collect { result ->
            when (result) {
                is ResourceState.Sucess -> {
                    binding.progressbarSearch.hide()
                    result.data?.let {
                        characterAdapter.characters = it.data.results.toList()
                    }
                }
                is ResourceState.Error -> {
                    binding.progressbarSearch.hide()
                    result.message?.let { message ->
                        toast(getString(R.string.an_error_ocurred))

                    }

                }
                is ResourceState.Loading -> {
                    binding.progressbarSearch.show()

                }
                else -> {}
            }

        }
    }

    private fun searchInit(query: String) = with(binding) {

        //actionId = indetificador da ação será fornecida qunado a tecla enter for pressionada
        // a verificaçõ abaixo é se actionId é igual o EditorInfo.IME que  é a tecla IR , se o ir
        // for clicado será feito a pesquisa.

        edSearchCharacter.setText(query)
        edSearchCharacter.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateCharacterList()
                true
            } else {
                false
            }
        }

        //ação de click na tecla enter , keyCode = é o codigo da tecla fisica que foi pressionada se
        // é o enter ou outro, o event = contém as informaçoes completas do evento , atraves dele
        // verificamos o evento que  será executada , novamente será retornada verdadeiro ou falso
        edSearchCharacter.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateCharacterList()
                true
            } else {
                false

            }
        }

    }

    // está função fará a pesquisa , pegando o palavra digita pelo o usuario
    private fun updateCharacterList() = with(binding) {
        edSearchCharacter.editableText.trim().let {
            if (it.isNotEmpty()) {
                searchQuery(it.toString())
            }
        }
    }

    private fun searchQuery(query: String) {
        viewModel.fetch(query)
    }

    // este metodo é invocado quando nossa fragmento for temporariamente destruido,
    // salvando o estado da instancia , para quando a tela rotacionada a pesquisa que está sendo feita
    // não seja destruida e apague a pesquisa do usuario
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //passando a chave LAST  , mais o valor que o usuario escreveu no editText
        outState.putString(
            LAST_SEARCH_QUERY,
            binding.edSearchCharacter.editableText.trim().toString()
        )
    }

    //ação de navegação do click dos personagens , levando para tela de detalhes
    private fun clickAdapter() {
        characterAdapter.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(it)
            findNavController().navigate(action)
        }
    }

    //configs basicas do recyclerView
    private fun setupRecyclerView() = with(binding) {
        rvSearchCharacter.apply {
            adapter = characterAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }


}