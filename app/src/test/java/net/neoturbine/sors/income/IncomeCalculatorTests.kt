package net.neoturbine.sors.income

import net.neoturbine.sors.taxes.FilingStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class IncomeCalculatorTests {
    @Test
    fun fromTaxableIncome() {
        val income = IncomeCalculator.fromTaxableIncome(4000)
        assertThat(income.taxableIncome).isEqualTo(4000)
    }
}