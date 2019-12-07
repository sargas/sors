package net.neoturbine.sors

class IncomeCalculator {
    companion object {
        fun fromTaxableIncome(taxableIncome : Int) : Income =
                IncomeFromTaxableIncome(taxableIncome)
    }

    private class IncomeFromTaxableIncome(override val taxableIncome : Int) : Income
}

