package net.neoturbine.sors

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.*
import net.neoturbine.sors.taxes.FilingStatus
import net.neoturbine.sors.taxes.TaxSubmission

class CalculateTaxesOwedViewModel : ViewModel() {
    val taxableIncome = MutableLiveData<String>()
    val filingStatusPosition = MutableLiveData<Int>()
    val calculatedTaxesOwed = taxableIncome.combineWith(filingStatusPosition) { income, currentFilingStatusPosition ->
        if (income == null || income.isEmpty() || !income.isDigitsOnly() || currentFilingStatusPosition == null)
            ""
        else {
            val taxBrackets = currentTaxPolicy.brackets[FilingStatus.values()[currentFilingStatusPosition]]
                    ?: throw IllegalStateException("")
            val deduction = currentTaxPolicy.standardDeductions[FilingStatus.values()[currentFilingStatusPosition]]
                    ?: throw IllegalStateException("")
            TaxSubmission(
                    taxBrackets,
                    IncomeCalculator.fromTaxableIncome(income.toInt()),
                    deduction).taxesOwed.toString()
        }
    }

    // From https://stackoverflow.com/a/57079290
    private fun <T, K, R> LiveData<T>.combineWith(
            liveData: LiveData<K>,
            block: (T?, K?) -> R
    ): LiveData<R> {
        val result = MediatorLiveData<R>()
        result.addSource(this) {
            result.value = block.invoke(this.value, liveData.value)
        }
        result.addSource(liveData) {
            result.value = block.invoke(this.value, liveData.value)
        }
        return result
    }
}
