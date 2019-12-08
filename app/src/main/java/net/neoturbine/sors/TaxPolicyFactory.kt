package net.neoturbine.sors

import net.neoturbine.sors.taxes.FilingStatus
import net.neoturbine.sors.taxes.taxPolicy

val currentTaxPolicy = taxPolicy {
    name = "2019"
    taxBracketsFor(FilingStatus.SINGLE) {
        10% (0..9700)
        12% (9701..39_475)
        22% (39_476..84_200)
        24% (84_201..160_725)
        32% (160_726..204_100)
        35% (204_101..510_300)
        37% (510_301..Int.MAX_VALUE)
    }
    taxBracketsFor(FilingStatus.MARRIED) {
        10 % (0..19_400)
        12 % (19_401..78_950)
        22 % (78_951..168_400)
        24% (168_401..321_450)
        32% (321_451..408_200)
        35% (408_201..612_350)
        37% (612_351..Int.MAX_VALUE)
    }
    standardDeductions(
            FilingStatus.SINGLE to 12_000,
            FilingStatus.MARRIED to 24_000
    )
}