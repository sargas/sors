package net.neoturbine.sors

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.neoturbine.sors.income.IncomeCalculator
import net.neoturbine.sors.taxes.FilingStatus
import net.neoturbine.sors.taxes.TaxSubmission

class CalculateTaxesOwedViewModel : ViewModel() {
    val taxableIncome = MutableLiveData<String>()
    val filingStatus = MutableLiveData<FilingStatus>()

    val calculatedTaxesOwed = taxableIncome.combineWith(filingStatus) { income, currentFilingStatus ->
        if (income == null || income.isEmpty() || !income.isDigitsOnly() || currentFilingStatus == null)
            ""
        else {
            val taxBrackets = currentTaxPolicy.brackets[currentFilingStatus]
                    ?: throw IllegalStateException("")
            val deduction = currentTaxPolicy.standardDeductions[currentFilingStatus]
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
