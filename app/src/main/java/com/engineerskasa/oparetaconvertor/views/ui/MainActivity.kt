package com.engineerskasa.oparetaconvertor.views.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.engineerskasa.oparetaconvertor.R
import com.engineerskasa.oparetaconvertor.databinding.ActivityMainBinding
import com.engineerskasa.oparetaconvertor.util.showErrorMessage
import com.engineerskasa.oparetaconvertor.viewmodel.CurrencyViewModel
import com.engineerskasa.oparetaconvertor.views.adapters.CurrencyRecyclerAdapter
import com.engineerskasa.oparetaconvertor.views.dialogs.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var selectedCurrency: String = "USD"
    private val viewModel: CurrencyViewModel by viewModels()
    private val loadingDialog = LoadingDialog(this)
    private lateinit var currencyAdapter: CurrencyRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        initViews()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.userLoader.observe(this, { isLoading ->
            isLoading.let {
                if (it) {
                    loadingDialog.startProgressDialog()
                } else {
                    loadingDialog.dismissDialog()
                }
            }
        })
        viewModel.userErrors.observe(this, { isError ->
            isError.let { e ->
                Timber.e("initViewModel: ${e.localizedMessage}")
                showErrorMessage(binding.parentLayout, e)
            }
        })

        viewModel.currencies.observe(this, {currencies ->
            currencyAdapter.setCurrencies(currencies)
        })
    }

    private fun initViews() {
        val fiatCurrencies = listOf<String>("UGX","USD","EUR")
        val arrayAdapter = ArrayAdapter(this, R.layout.list_item_layout, fiatCurrencies)
        binding.currencyTxt.setAdapter(arrayAdapter)

        binding.currencyTxt.setOnItemClickListener { _, _, position, _ ->
           selectedCurrency = arrayAdapter.getItem(position) ?: ""
            viewModel.getCurrencyDataSource(selectedCurrency)
        }

        currencyAdapter = CurrencyRecyclerAdapter(this)
        binding.converterRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = currencyAdapter
        }

        binding.amount.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty())
                viewModel.convertAmount(text.toString().toDouble())
            else
                viewModel.convertAmount(1.0)
        }
    }
}