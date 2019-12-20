package net.neoturbine.sors

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import net.neoturbine.sors.IncomeSpecifierDirections
import net.neoturbine.sors.databinding.IncomeSpecifierFragmentBinding

class IncomeSpecifier : Fragment() {
    private lateinit var vm : CalculateTaxesOwedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = activity?.run {
            ViewModelProviders.of(this)[CalculateTaxesOwedViewModel::class.java]
        } ?: throw Exception("Using filing status chooser outside of an activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = IncomeSpecifierFragmentBinding.inflate(inflater, container, false)
        binding.vm = vm

        binding.nextButton.setOnClickListener {button ->
            val action = IncomeSpecifierDirections.actionIncomeSpecifierToDeductionsSpecifier()
            button.findNavController().navigate(action)
        }
        return binding.root
    }
}
