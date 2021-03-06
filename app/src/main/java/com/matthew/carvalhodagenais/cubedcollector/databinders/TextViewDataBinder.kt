package com.matthew.carvalhodagenais.cubedcollector.databinders

import android.app.Activity
import android.content.res.Resources
import android.graphics.Typeface
import android.util.TypedValue
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.matthew.carvalhodagenais.cubedcollector.R
import com.matthew.carvalhodagenais.cubedcollector.data.repositories.ConditionRepository
import com.matthew.carvalhodagenais.cubedcollector.data.repositories.RegionRepository
import com.matthew.carvalhodagenais.cubedcollector.helpers.DateHelper
import java.util.*

class TextViewDataBinder {
    companion object {
        @JvmStatic
        @BindingAdapter("bind:lifecycleOwner", "bind:repository", "bind:regionId")
        fun setRegionText(textView: TextView, lco: LifecycleOwner, repo: RegionRepository, regionId: Int?) {
            if (regionId != null) {
                repo.getRegionById(regionId).observe(lco, Observer {
                    textView.text = "${it.name} [${it.code}]"
                })
            } else {
                textView.text = textView.context.getString(R.string.no_region)
            }
        }

        @JvmStatic
        @BindingAdapter("bind:lifecycleOwner", "bind:repository", "bind:conditionId")
        fun setConditionText(textView: TextView, lco: LifecycleOwner, repo: ConditionRepository, conditionId: Int?) {
            if (conditionId != null) {
                repo.getConditionById(conditionId).observe(lco, Observer {
                    textView.text = "${it.name} [${it.code}]"
                })
            } else {
                textView.text = textView.context.getString(R.string.no_condition)
            }
        }

        @JvmStatic
        @BindingAdapter("bind:isModded")
        fun setIsModdedText(textView: TextView, isModded: Boolean) {
            if (isModded) {
                textView.text = textView.context.getString(R.string.yes)
            } else {
                textView.text = textView.context.getString(R.string.no)
            }
        }

        @JvmStatic
        @BindingAdapter("bind:description")
        fun setDescription(textView: TextView, description: String?) {
            if (description != null && description.trim() != "") {
                textView.text = "\" $description \""
            } else {
                textView.text = textView.context.getString(R.string.no_description_available)
            }
        }

        @JvmStatic
        @BindingAdapter("bind:date")
        fun setDateString(textView: TextView, date: Date?) {
            if (date == null) {
                textView.text = textView.context.getString(R.string.no_date)
            } else {
                textView.text = DateHelper.createDateString(date)
            }
        }

        @JvmStatic
        @BindingAdapter("bind:hasCase", "bind:hasManual")
        fun setCompletenessText(textView: TextView, hasCase: Boolean, hasManual: Boolean) {
            var str = textView.context.getString(R.string.completeness_disc)
            if (hasCase && hasManual) {
                str = textView.context.getString(R.string.completeness_cib)
            } else if (hasCase) {
                str = textView.context.getString(R.string.completeness_case)
            } else if (hasManual) {
                str = textView.context.getString(R.string.completeness_manual)
            }
            textView.text = str
        }

        @JvmStatic
        @BindingAdapter("bind:isDefault", "bind:parentActivity")
        fun setDefaultTextStyle(textView: TextView, isDefault: Boolean, act: Activity) {
            if (isDefault) {
                textView.setTypeface(null, Typeface.ITALIC)
                // get theme subtitle color
                val typedValue = TypedValue()
                val theme: Resources.Theme = act.theme
                theme.resolveAttribute(android.R.attr.subtitleTextColor, typedValue, true)
                textView.setTextColor(typedValue.data)
            }
        }
    }
}