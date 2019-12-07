package net.neoturbine.sors.taxes

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.Test

class TaxPolicyTests {
    @Test
    fun `Build Tax Policy`() {
        val taxPolicy = taxPolicy {
            name = "2017"
            taxBracketsFor(FilingStatus.SINGLE) {
                10 % (0..9700)
                12 % (9701..39_475)
                22 % (39_476..84_200)
                24 % (84_201..Int.MAX_VALUE)
            }
            taxBracketsFor(FilingStatus.MARRIED) {
                10 % (0..19_400)
                12 % (19_401..78_950)
                22 % (78_951..Int.MAX_VALUE)
            }
            standardDeductions(
                    FilingStatus.SINGLE to 12_000,
                    FilingStatus.MARRIED to 24_000
            )
        }
        assertThat(taxPolicy.name).isEqualTo("2017")
        assertThat(taxPolicy.brackets)
                .containsKeys(FilingStatus.SINGLE, FilingStatus.MARRIED)
                .extractingFromEntries { it.value.brackets.size }
                .contains(4, 3)
        assertThat(taxPolicy.standardDeductions).contains(
                entry(FilingStatus.SINGLE, 12_000),
                entry(FilingStatus.MARRIED, 24_000)
        )

    }
}