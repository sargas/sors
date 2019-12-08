package net.neoturbine.sors.taxes

import androidx.annotation.StringRes
import net.neoturbine.sors.R

enum class FilingStatus (@StringRes val displayName: Int){
    SINGLE(R.string.filing_status_single),
    MARRIED(R.string.filing_status_married),
}