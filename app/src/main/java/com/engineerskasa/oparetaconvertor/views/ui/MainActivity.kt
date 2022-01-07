package com.engineerskasa.oparetaconvertor.views.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.engineerskasa.oparetaconvertor.R
import com.engineerskasa.oparetaconvertor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        initViews()
    }

    private fun initViews() {
        val fiatCurrencies = listOf<String>("UGX","USD","EUR")
        val adapter = ArrayAdapter(this, R.layout.list_item_layout, fiatCurrencies)
        binding.currencyTxt.setAdapter(adapter)
    }
}