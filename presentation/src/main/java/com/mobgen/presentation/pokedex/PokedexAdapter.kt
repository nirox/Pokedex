package com.mobgen.presentation.pokedex

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mobgen.presentation.R
import com.mobgen.presentation.ViewHolder
import kotlinx.android.synthetic.main.item_grid_pokedex.view.*

class PokedexAdapter(
    private val typeAdapter: Int,
    private var list: List<PokedexViewModel.PokemonBindView>,
    private val listener: OnClickItemListener
) :
    RecyclerView.Adapter<PokedexAdapter.DataViewHolder>() {

    companion object {
        const val TYPE_GRID = 0
        const val TYPE_LIST = 1
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int) = DataViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(
            if (typeAdapter == TYPE_GRID) R.layout.item_grid_pokedex else R.layout.item_list_pokedex, viewGroup, false
        ), listener
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(dataViewHolder: DataViewHolder, position: Int) {
        dataViewHolder.bind(list[dataViewHolder.adapterPosition])
    }

    fun setData(list: List<PokedexViewModel.PokemonBindView>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class DataViewHolder(itemView: View, listener: OnClickItemListener) : RecyclerView.ViewHolder(itemView),
        ViewHolder<PokedexViewModel.PokemonBindView> {

        init {
            itemView.setOnClickListener {
                listener.onClickItem(list[adapterPosition].id)
            }
        }

        override fun bind(value: PokedexViewModel.PokemonBindView) {
            Glide.with(itemView.context).load(value.image).apply {
                RequestOptions.circleCropTransform()
                RequestOptions.centerCropTransform()
            }
                .into(itemView.image)
            itemView.name.text = value.name
        }

    }

    interface OnClickItemListener {
        fun onClickItem(id: Long)
    }
}