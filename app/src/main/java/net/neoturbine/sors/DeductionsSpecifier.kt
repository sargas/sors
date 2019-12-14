package net.neoturbine.sors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController


class DeductionsSpecifier : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) : View? =
            inflater.inflate(R.layout.deductions_specifier_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.next_button).setOnClickListener { button ->
            val action = DeductionsSpecifierDirections.actionDeductionsSpecifierToCalculatedTaxDisplay()
            button.findNavController().navigate(action)
        }
    }
}
