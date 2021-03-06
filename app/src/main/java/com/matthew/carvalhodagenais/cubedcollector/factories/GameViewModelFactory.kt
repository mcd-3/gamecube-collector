package com.matthew.carvalhodagenais.cubedcollector.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.matthew.carvalhodagenais.cubedcollector.viewmodels.GameAddEditViewModel
import com.matthew.carvalhodagenais.cubedcollector.viewmodels.GameDetailViewModel
import com.matthew.carvalhodagenais.cubedcollector.viewmodels.GameListViewModel

class GameViewModelFactory(private val application: Application):
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass){
        when {
            isAssignableFrom(GameDetailViewModel::class.java) ->
                GameDetailViewModel(
                    application
                ) as T
            isAssignableFrom(GameAddEditViewModel::class.java) ->
                GameAddEditViewModel(
                    application
                ) as T
            isAssignableFrom(GameListViewModel::class.java) ->
                GameListViewModel(
                    application
                ) as T
            else ->
                IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}") as T
        }
    }

}