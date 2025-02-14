package net.neoturbine.sors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.neoturbine.sors.databinding.FragmentFilingStatusChooserListBindingImpl
import net.neoturbine.sors.taxes.FilingStatus

class FilingStatusChooserFragment : Fragment() {
    private lateinit var vm : CalculateTaxesOwedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = activity?.run {
            ViewModelProviders.of(this)[CalculateTaxesOwedViewModel::class.java]
        } ?: throw Exception("Using filing status chooser outside of an activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentFilingStatusChooserListBindingImpl.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.vm = vm
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(view.findViewById<RecyclerView>(R.id.list)) {
            layoutManager = LinearLayoutManager(context).apply {
                addItemDecoration(DividerItemDecoration(context, orientation))
            }
            adapter = MyFilingStatusChooserRecyclerViewAdapter(FilingStatus.values().asList(), vm, viewLifecycleOwner)
        }

        view.findViewById<View>(R.id.next_button).setOnClickListener {button ->
            val action = FilingStatusChooserFragmentDirections.actionFilingStatusChooserFragmentToIncomeSpecifier()
            button.findNavController().navigate(action)
        }
    }
}
