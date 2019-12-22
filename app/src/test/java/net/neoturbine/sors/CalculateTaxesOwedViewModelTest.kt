package net.neoturbine.sors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import net.neoturbine.sors.taxes.FilingStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test

class CalculateTaxesOwedViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val viewModel: CalculateTaxesOwedViewModel by lazy {
        CalculateTaxesOwedViewModel()
    }

    @Test
    fun `Starts With Nothing`() {
        assertThat(viewModel.filingStatus.value).isNull()
        assertThat(viewModel.calculatedTaxesOwed.value).isNull()
    }

    @Test
    fun `Recalculates Taxes`() {
        viewModel.taxableIncome.value = "1234"
        viewModel.filingStatus.value = FilingStatus.SINGLE
        assertThat(viewModel.calculatedTaxesOwed.observedValue).isEqualTo("0")

        viewModel.taxableIncome.value = "100000"
        assertThat(viewModel.calculatedTaxesOwed.observedValue).isEqualTo("15294")

        viewModel.filingStatus.value = FilingStatus.MARRIED
        assertThat(viewModel.calculatedTaxesOwed.observedValue).isEqualTo("8732")
    }
}