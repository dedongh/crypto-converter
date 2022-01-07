package com.engineerskasa.oparetaconvertor.views.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.engineerskasa.oparetaconvertor.R
import com.engineerskasa.oparetaconvertor.databinding.ConverterListItemLayoutBinding
import com.engineerskasa.oparetaconvertor.model.OfflineCurrency
import timber.log.Timber
import java.util.ArrayList

class CurrencyRecyclerAdapter(val context: Context) :
    ListAdapter<OfflineCurrency, CurrencyRecyclerAdapter.CurrencyViewHolder>(DIFF_UTIL_CALLBACK) {

    class CurrencyViewHolder(private val binding: ConverterListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(_currency: OfflineCurrency, position: Int, context: Context,) {
            binding.apply {
                currency = _currency
                currencyPrice.text =  _currency.price.toString()
            }
            if (position % 2 == 0) {
                binding.parentLayout.setBackgroundColor(ContextCompat.getColor(
                    context,
                    R.color.grey
                ))
            }
        }
    }

    companion object {
        val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<OfflineCurrency>() {
            override fun areItemsTheSame(
                oldItem: OfflineCurrency,
                newItem: OfflineCurrency
            ): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(
                oldItem: OfflineCurrency,
                newItem: OfflineCurrency
            ): Boolean =
                oldItem == newItem


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencyViewHolder {
        DataBindingUtil.inflate<ConverterListItemLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.converter_list_item_layout,
            parent,
            false
        ).also {
            return CurrencyViewHolder(it)
        }
    }

    override fun onBindViewHolder(
        holder: CurrencyViewHolder,
        position: Int
    ) {
        val currency = getItem(position)
        holder.bind(currency, position, context)
    }

    fun setCurrencies(currencies: List<OfflineCurrency>) {
        submitList(currencies)
    }
}
