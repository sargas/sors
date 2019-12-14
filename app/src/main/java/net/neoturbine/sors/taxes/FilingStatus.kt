package net.neoturbine.sors.taxes

import androidx.annotation.StringRes
import net.neoturbine.sors.R

enum class FilingStatus (@StringRes val displayName: Int, @StringRes val description: Int){
    SINGLE(R.string.filing_status_single, R.string.filing_status_single_description),
    MARRIED(R.string.filing_status_married, R.string.filing_status_married_description),
}