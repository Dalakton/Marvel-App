package me.project.marvelapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.project.marvelapp.databinding.ItemComicBinding
import me.project.marvelapp.domain.model.comic.ComicModel
import me.project.marvelapp.util.loadImage

class ComicAdapter : RecyclerView.Adapter<ComicAdapter.ComicViewHolder>() {

    inner class ComicViewHolder(val binding: ItemComicBinding) :
        RecyclerView.ViewHolder(binding.root)

    //DiffUtil ele vai calcular a diferença entre 2 listas para atualizar com eficiencia a lista
    //exibida pela recyclerView

    private val differCallback = object : DiffUtil.ItemCallback<ComicModel>() {

        // este metodo vai ser chamado para verifica se os itens novos e antigos são iguais
        override fun areItemsTheSame(oldItem: ComicModel, newItem: ComicModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        // este metodo vai ser chaado para verificar se os itens antigos e os novos representam o
        // o mesmo item
        override fun areContentsTheSame(oldItem: ComicModel, newItem: ComicModel): Boolean {
            return oldItem.id == newItem.id && oldItem.title == newItem.title && oldItem.description ==
                    newItem.description && oldItem.thumbnail.path == newItem.thumbnail.path &&
                    oldItem.thumbnail.extension == newItem.thumbnail.extension
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    var comics: List<ComicModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        return ComicViewHolder(
            ItemComicBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = comics.size


    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        val comic = comics[position]
        holder.binding.apply {
            tvNameComic.text = comic.title
            tvDescriptionComic.text = comic.description

            Glide.with(holder.itemView.context)
                .load("${comic.thumbnail.path.replace("http" , "https")}.${comic.thumbnail.extension}")
                .into(imageComic)

        }
    }
}