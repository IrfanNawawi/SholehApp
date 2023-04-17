package id.heycoding.sholehapp.persentation.zakat

import id.heycoding.sholehapp.domain.model.zakat.Zakat

data class ZakatState(
    val isLoading: Boolean = false,
    val zakatPaymentList: List<Zakat> = emptyList(),
    val zakatPaymentInsert: String = "",
    val error: String = "",

    val zakatSumJumlahJiwa: String = "",
    val zakatSumBerasZakat: String = "",
    val zakatSumUangZakat: String = "",
    val zakatSumInfakZakat: String = ""
)