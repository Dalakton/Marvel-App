package me.project.marvelapp.presentation.ui.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import me.project.marvelapp.R
import me.project.marvelapp.databinding.FragmentDetailBinding
import me.project.marvelapp.domain.model.character.CharacterModel
import me.project.marvelapp.presentation.adapters.ComicAdapter
import me.project.marvelapp.presentation.state.ResourceState
import me.project.marvelapp.util.hide
import me.project.marvelapp.util.limitDescription
import me.project.marvelapp.util.show
import me.project.marvelapp.util.toast
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModel()
    private val comicAdapter by lazy { ComicAdapter() }
    private lateinit var characterModel: CharacterModel
    private val args: DetailFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterModel = args.character
        viewModel.fetch(characterModel.id)
        setupRecycleView()
        onLoadedCharacter(characterModel)
        collectObserver()
        descriptionCharacter()
    }

    private fun descriptionCharacter() {
        binding.tvDescriptionCharacterDetails.setOnClickListener {
            onShowDialog(characterModel)
        }
    }

    private fun onShowDialog(characterModel: CharacterModel) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(characterModel.name)
            .setMessage(characterModel.description)
            .setNegativeButton(getString(R.string.close_dialog)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.detail.collect { result ->
            when (result) {
                is ResourceState.Sucess -> {
                    binding.progressBarDetail.hide()
                    result.data?.let { values ->
                        if (values.data.results.count() > 0) {
                            comicAdapter.comics = values.data.results.toList()
                        } else {
                            toast(getString(R.string.empty_list_comics))
                        }
                    }
                }
                is ResourceState.Error -> {
                    binding.progressBarDetail.hide()
                    result.message?.let { message ->
                        toast(message)
                    }
                }
                is ResourceState.Loading -> {
                    binding.progressBarDetail.show()
                }
                else -> {
                }
            }
        }
    }

    private fun onLoadedCharacter(characterModel: CharacterModel) = with(binding) {
        tvNameCharacterDetails.text = characterModel.name
        if (characterModel.description.isEmpty()) {
            tvDescriptionCharacterDetails.text =
                requireContext().getString(R.string.tv_empty_description)
        } else {
            tvDescriptionCharacterDetails.text = characterModel.description.limitDescription(100)
        }
        Glide.with(requireContext())
            .load(
                "${
                    characterModel.thumbnail.path.replace(
                        "http",
                        "https"
                    )
                }.${characterModel.thumbnail.extension}"
            )
            .into(imageCharacterDetails)
    }

    private fun setupRecycleView() = with(binding) {
        rvComics.apply {
            adapter = comicAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                viewModel.insert(characterModel)
                toast(getString(R.string.saved_successfully))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
