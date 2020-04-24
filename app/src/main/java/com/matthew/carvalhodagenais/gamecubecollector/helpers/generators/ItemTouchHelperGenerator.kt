package com.matthew.carvalhodagenais.gamecubecollector.helpers.generators

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.adapters.ConsoleListRecyclerAdapter
import com.matthew.carvalhodagenais.gamecubecollector.adapters.GameListRecyclerAdapter
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.ConsoleListViewModel
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.GameListViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class ItemTouchHelperGenerator(context: Context) {

    private lateinit var gameRecyclerAdapter: GameListRecyclerAdapter
    private lateinit var consoleRecyclerAdapter: ConsoleListRecyclerAdapter
    private lateinit var gameListViewModel: GameListViewModel
    private lateinit var consoleListViewModel: ConsoleListViewModel

    fun generate(adapter: GameListRecyclerAdapter, viewModel: GameListViewModel): ItemTouchHelper.SimpleCallback {
        gameRecyclerAdapter = adapter
        gameListViewModel = viewModel
        return gameRecyclerViewTouchHelper
    }

    fun generate(adapter: ConsoleListRecyclerAdapter, viewModel: ConsoleListViewModel): ItemTouchHelper.SimpleCallback {
        consoleRecyclerAdapter = adapter
        consoleListViewModel = viewModel
        return consoleRecyclerViewTouchHelper
    }

    /**
     * ItemTouchHelper used to swipe RecyclerView consoles
     * and create background/child views under each item
     */
    private var consoleRecyclerViewTouchHelper = object: ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (direction == ItemTouchHelper.RIGHT) {
                //Get console from viewholder and delete
                consoleListViewModel.delete(consoleRecyclerAdapter.getConsole(viewHolder.adapterPosition)).also {
                    consoleRecyclerAdapter.notifyDataSetChanged()
                }
            }
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            RecyclerViewSwipeDecorator.Builder(
                c,
                recyclerView,
                viewHolder,
                dX,
                dY,
                actionState,
                isCurrentlyActive
            ).addSwipeRightBackgroundColor(context.getColor(R.color.red))
                .setSwipeRightLabelColor(Color.WHITE)
                .addSwipeRightLabel(context.getString(R.string.ith_delete))
                .addSwipeRightActionIcon(R.drawable.ic_delete_white_32dp)
                .create()
                .decorate()
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    /**
     * ItemTouchHelper used to swipe RecyclerView games
     * and create background/child views under each item
     */
    private var gameRecyclerViewTouchHelper = object: ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (direction == ItemTouchHelper.RIGHT) {
                //Get game from viewholder and delete
                gameListViewModel.delete(gameRecyclerAdapter.getGame(viewHolder.adapterPosition)).also {
                    gameRecyclerAdapter.notifyDataSetChanged()
                }
            }
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                ).addSwipeRightBackgroundColor(context.getColor(R.color.red))
                .setSwipeRightLabelColor(Color.WHITE)
                .addSwipeRightLabel(context.getString(R.string.ith_delete))
                .addSwipeRightActionIcon(R.drawable.ic_delete_white_32dp)
                .create()
                .decorate()
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }
}