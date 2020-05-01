package com.matthew.carvalhodagenais.gamecubecollector.databinders

import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.matthew.carvalhodagenais.gamecubecollector.helpers.generators.TextWatcherGenerator
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.AccessoryAddEditViewModel

class EditTextDataBinder {
    companion object {
        @JvmStatic
        @BindingAdapter("bind:textWatcher")
        fun bindTextWatcher(et: EditText, tw: TextWatcher) {
            et.addTextChangedListener(tw)
        }
    }
}