package co.id.kedai.kedaiapp.model


data class DaftarResponse(
    val error: Boolean,
    val id_peserta: String,
    val message: String,
    val pdf: String, //cekPembayaran
    )
