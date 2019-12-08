package net.neoturbine.sors

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import net.neoturbine.sors.databinding.CalculateTaxesOwedFragmentBinding
import net.neoturbine.sors.taxes.FilingStatus


class CalculateTaxesOwed : Fragment() {
    companion object {
        @Suppress("unused")
        fun newInstance() = CalculateTaxesOwed()
    }

    private val viewModel: CalculateTaxesOwedViewModel by lazy {
        ViewModelProviders.of(this).get(CalculateTaxesOwedViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = CalculateTaxesOwedFragmentBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        binding.filingStatusPicker.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,
                FilingStatus.values().map { getString(it.displayName) })

        return binding.root
    }

}
