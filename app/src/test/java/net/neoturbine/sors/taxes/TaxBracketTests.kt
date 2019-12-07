package net.neoturbine.sors.taxes

import net.neoturbine.sors.Income
import net.neoturbine.sors.IncomeCalculator
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import kotlin.math.roundToInt

class TaxBracketTests {
    private var standardDeduction = 0
    private val bracket = taxBrackets {
        10 % (0..9700)
        12 % (9701..39_475)
        22 % (39_476..84_200)
        24 % (84_201..Int.MAX_VALUE)
    }

    @Test
    fun `No income owed`() {
        val income = IncomeCalculator.fromTaxableIncome(0)
        assertThat(taxesOwedFor(income)).isEqualTo(0)
    }

    @Test
    fun `Lowest Tax Bracket`() {
        val income = IncomeCalculator.fromTaxableIncome(9699)
        assertThat(taxesOwedFor(income)).isEqualTo(970)
    }

    @Test
    fun `Over Two Tax Brackets`() {
        val income = IncomeCalculator.fromTaxableIncome(20_000)
        assertThat(taxesOwedFor(income))
                .isEqualTo(((9700 * 0.1) + (20_000-9700)*0.12).roundToInt())
    }

    @Test
    fun `In Highest Tax Bracket`() {
        val income = IncomeCalculator.fromTaxableIncome(100_000)
        assertThat(taxesOwedFor(income))
                .isEqualTo(((9700 * 0.1) + (39_475-9701)*0.12
                        + (84_200-39_476) * 0.22 + (100_000 - 84_201)*0.24).roundToInt())
    }

    @Test
    fun `Income Less Than Standard Deduction`() {
        val income = IncomeCalculator.fromTaxableIncome(1_000)
        standardDeduction = 2_000
        assertThat(taxesOwedFor(income)).isEqualTo(0)
    }

    @Test
    fun `Subtracts Standard Deduction`() {
        val income = IncomeCalculator.fromTaxableIncome(100_000)
        standardDeduction = 30_000
        assertThat(taxesOwedFor(income))
                .isEqualTo(((9700 * 0.1) + (39_475-9701)*0.12
                        + (100_000 - 30_000 -39_476) * 0.22).roundToInt())
    }

    private fun taxesOwedFor(income: Income) =
            TaxSubmission(bracket, income, standardDeduction).taxesOwed
}
