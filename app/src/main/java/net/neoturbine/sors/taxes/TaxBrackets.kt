package net.neoturbine.sors.taxes

data class TaxBracket(val taxRate : Int, val incomeRange : IntRange)

data class TaxBrackets (
        val brackets : List<TaxBracket>
) {

    @TaxPolicyDsl
    class Builder(private val brackets : MutableList<TaxBracket> = mutableListOf()) {
        fun build() : TaxBrackets = TaxBrackets(brackets)

        operator fun Int.rem(incomes: IntRange) {
            brackets.add(TaxBracket(this, incomes))
        }
    }
}

inline fun taxBrackets(buildTaxBrackets : TaxBrackets.Builder.() -> Unit) : TaxBrackets {
    val builder = TaxBrackets.Builder()
    builder.buildTaxBrackets()
    return builder.build()
}