package net.neoturbine.sors.taxes

import net.neoturbine.sors.income.Income
import java.lang.Integer.min
import kotlin.math.roundToInt

class TaxSubmission(private val taxBrackets : TaxBrackets, private val income: Income, private val standardDeduction : Int) {
    val taxesOwed : Int
        get() {
            var total = 0.0
            val taxableIncome = income.taxableIncome - standardDeduction

            for (bracket in taxBrackets.brackets) {
                if (taxableIncome < bracket.incomeRange.first) continue
                val dollarsInBracket = min(
                        taxableIncome - bracket.incomeRange.first,
                        bracket.incomeRange.let { it.last - it.first })
                total += dollarsInBracket * bracket.taxRate/100.0
            }
            return total.roundToInt()
        }
}