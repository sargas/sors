package net.neoturbine.sors.income

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class IncomeCalculatorTests {
    @Test
    fun `From Taxable Income`() {
        val income = IncomeCalculator.fromTaxableIncome(4000)
        assertThat(income.taxableIncome).isEqualTo(4000)
    }
}