package net.neoturbine.sors.taxes

import java.lang.IllegalStateException

data class TaxPolicy (
        val name: String,
        val brackets: Map<FilingStatus, TaxBrackets>,
        val standardDeductions: Map<FilingStatus, Int>
) {

    @TaxPolicyDsl
    class Builder(
            var name: String? = null,
            private val brackets : MutableMap<FilingStatus, TaxBrackets> = mutableMapOf(),
            private val standardDeductions : MutableMap<FilingStatus, Int> = mutableMapOf()) {
        fun build() : TaxPolicy {
            if (name == null)
                throw IllegalStateException("name of the tax policy must be specified")
            return TaxPolicy(name!!, brackets, standardDeductions)
        }

        fun taxBracketsFor(filingStatus: FilingStatus, buildTaxBrackets : TaxBrackets.Builder.() -> Unit) {
            brackets[filingStatus] = taxBrackets(buildTaxBrackets)
        }

        fun standardDeductions(vararg deductions : Pair<FilingStatus, Int>) {
            deductions.forEach { standardDeductions[it.first] = it.second }
        }
    }
}

inline fun taxPolicy(buildTaxPolicy : TaxPolicy.Builder.() -> Unit) : TaxPolicy {
    val builder = TaxPolicy.Builder()
    builder.buildTaxPolicy()
    return builder.build()
}