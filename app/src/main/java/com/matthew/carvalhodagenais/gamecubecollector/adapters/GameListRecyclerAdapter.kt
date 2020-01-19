package com.matthew.carvalhodagenais.gamecubecollector.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Game
import kotlinx.android.synthetic.main.game_item.view.*

class GameListRecyclerAdapter: ListAdapter<Game, GameListRecyclerAdapter.GameHolder>(DIFF_CALLBACK) {

    private var listener: ItemOnClickListener ?= null

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Game>() {

            override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
                return (oldItem.title == newItem.title &&
                        oldItem.developers == newItem.developers &&
                        oldItem.publishers == newItem.publishers &&
                        oldItem.releaseDate == newItem.releaseDate &&
                        oldItem.regionId == newItem.regionId &&
                        oldItem.boughtDate == newItem.boughtDate &&
                        oldItem.condition == newItem.condition &&
                        oldItem.pricePaid == newItem.pricePaid &&
                        oldItem.hasCase == newItem.hasCase &&
                        oldItem.hasManual == newItem.hasManual)
            }

            override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        val game = getItem(position)
        holder.titleTextView.text = game.title
        holder.developerTextView.text =
            if (game.developers != "" && game.developers != null) game.developers
            else holder.parentView.context.getString(R.string.no_developer_available)
        holder.yearTextView.text =
            if (game.releaseDate != null) game.releaseDate.toString()
            else holder.parentView.context.getString(R.string.no_date_available)
        holder.regionTextView.text =
            if (game.regionId != null) game.regionId.toString()
            else holder.parentView.context.getString(R.string.no_region_available)

        // Place appropriate cover art
        if (game.imagePath != "" && game.imagePath != null) {
            Glide
                .with(holder.parentView)
                .load(game.imagePath)
                .into(holder.coverImageView)
        } else {
            Glide
                .with(holder.parentView)
                .load(holder.parentView.context.getDrawable(R.drawable.no_art))
                .into(holder.coverImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.game_item, parent, false)
        return GameHolder(itemView)
    }

    inner class GameHolder(view: View): RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var parentView = view
        var titleTextView = view.title_text_view
        var developerTextView = view.developer_text_view
        var yearTextView = view.year_text_view
        var regionTextView = view.region_text_view
        var coverImageView = view.cover_art_image_view
    }

    interface ItemOnClickListener {
        fun onItemClick(game: Game)
    }

    fun setItemOnClickListener(listener: ItemOnClickListener) {
        this.listener = listener
    }


}