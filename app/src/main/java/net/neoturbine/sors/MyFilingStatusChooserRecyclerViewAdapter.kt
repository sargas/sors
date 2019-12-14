package net.neoturbine.sors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import net.neoturbine.sors.databinding.FragmentFilingStatusChooserBinding
import net.neoturbine.sors.taxes.FilingStatus

class MyFilingStatusChooserRecyclerViewAdapter(
        private val mValues: List<FilingStatus>,
        private val viewModel: CalculateTaxesOwedViewModel)
    : RecyclerView.Adapter<MyFilingStatusChooserRecyclerViewAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentFilingStatusChooserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        binding.lifecycleOwner = parent.context as LifecycleOwner
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.binding.filingStatus = item
        holder.binding.vm = viewModel

        holder.binding.root.setOnClickListener {
            viewModel.filingStatus.value = item
        }
        holder.binding.content.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && (viewModel.filingStatus.value != item))
                viewModel.filingStatus.value = item
        }

        viewModel.filingStatus.observe(holder.binding.lifecycleOwner!!, Observer<FilingStatus> { filingStatus ->
            val shouldBeChecked = (filingStatus == item)
            if (shouldBeChecked != holder.binding.content.isChecked)
                holder.binding.content.isChecked = shouldBeChecked
        })
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val binding: FragmentFilingStatusChooserBinding) : RecyclerView.ViewHolder(binding.root)
}
