package com.matthew.carvalhodagenais.gamecubecollector.databinders

import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.ConditionRepository
import com.matthew.carvalhodagenais.gamecubecollector.data.repositories.RegionRepository

class SpinnerDataBinder {
    companion object {
        private const val INDEX_MIN_ID = 1

        @JvmStatic
        @BindingAdapter("bind:repository", "bind:lifecycleOwner", "bind:defaultSelection")
        fun setSpinnerRegionEntries(
            spinner: Spinner,
            repo: RegionRepository,
            lco: LifecycleOwner,
            defaultSelection: Int
        ) {
            val list = mutableListOf<String>()
            val adapter = ArrayAdapter(spinner.context, android.R.layout.simple_spinner_item, list)
            repo.getRegionCodes().observe(lco, Observer { items ->
                items.forEach {
                    list.add(it)
                }
                adapter.notifyDataSetChanged()
                spinner.setSelection(defaultSelection - INDEX_MIN_ID)
            })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        @JvmStatic
        @BindingAdapter("bind:repository", "bind:lifecycleOwner", "bind:defaultSelection", "bind:typeID")
        fun setSpinnerConditionEntries(
            spinner: Spinner,
            repo: ConditionRepository,
            lco: LifecycleOwner,
            defaultSelection: Int,
            type: Int
        ) {
            val list = mutableListOf<String>()
            val adapter = ArrayAdapter(spinner.context, android.R.layout.simple_spinner_item, list)
            repo.getConditionCodes(type).observe(lco, Observer { items ->
                items.forEach {
                    list.add(it)
                }
                adapter.notifyDataSetChanged()
                spinner.setSelection(defaultSelection - INDEX_MIN_ID)
            })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }
}