package com.matthew.carvalhodagenais.gamecubecollector.ui

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.matthew.carvalhodagenais.gamecubecollector.MainActivity
import com.matthew.carvalhodagenais.gamecubecollector.R
import com.matthew.carvalhodagenais.gamecubecollector.adapters.ConsoleListRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_console_list.*

class ConsoleListFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_console_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val recyclerAdapter = ConsoleListRecyclerAdapter()
        console_list_recycler_view.apply {
            layoutManager = LinearLayoutManager(requireActivity().applicationContext)
            adapter = recyclerAdapter
        }

        (activity as MainActivity).getConsoleListViewModel().getAllConsoles()
            .observe(viewLifecycleOwner, Observer {
                recyclerAdapter.submitList(it)
            })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
        requireActivity().menuInflater.inflate(R.menu.menu_console_list, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.menu_add -> {
            val action =
                ConsoleListFragmentDirections.actionNavConsolesToConsoleAddEditFragment(
                    ConsoleAddEditFragment.ADD_REQUEST
                )
            // TODO: Replace this once ConsoleAddEditViewModel() is done.
            //(activity as MainActivity).getGameAddEditViewModel().clearCurrentlySelectedGame()
            findNavController().navigate(action)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}