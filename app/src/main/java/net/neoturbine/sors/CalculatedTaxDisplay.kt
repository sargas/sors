package net.neoturbine.sors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import net.neoturbine.sors.databinding.CalculatedTaxDisplayFragmentBindingImpl


class CalculatedTaxDisplay : Fragment() {
    private lateinit var vm : CalculateTaxesOwedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = activity?.run {
            ViewModelProviders.of(this)[CalculateTaxesOwedViewModel::class.java]
        } ?: throw Exception("Using filing status chooser outside of an activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = CalculatedTaxDisplayFragmentBindingImpl.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = vm
        return binding.root
    }
}
