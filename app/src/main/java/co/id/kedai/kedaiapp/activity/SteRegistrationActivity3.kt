package co.id.kedai.kedaiapp.activity

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.id.kedai.kedaiapp.R
import co.id.kedai.kedaiapp.api.ApiClient
import co.id.kedai.kedaiapp.databinding.ActivitySteRegistration3Binding
import co.id.kedai.kedaiapp.model.DaftarResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SteRegistrationActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivitySteRegistration3Binding
    private val itemKampus = listOf(
        "Universitas Dipa Makassar",
        "Universitas Hasanuddin",
        "Universitas Negeri Makassar",
        "Universitas Bosowa",
        "Universitas Atma Jaya",
        "STMIK Akba Makassar",
        "Universitas Kristen Indonesia Paulus",
        "Universitas Fajar",
        "Politeknik Negeri Ujung Pandang",
        "Universitas Muhammadiyah Makassar",
        "Universitas Teknologi Sulawesi",
        "STIM Nitro Makassar",
        "STMIK Handayani Makassar",
        "STMIK Kharisma Makassar",
        "Politeknik Informatika Makassar",
        "STMIK Profesional Makassar",
        "Universitas Islam Makassar",
        "Universitas Megarezky",
        "STKIP YPUP Makassar",
        "Politeknik ATI Makassar",
        "Politeknik Negeri Media Kreatif",
        "STIE Nobel Indonesia",
        "Universitas Islam Negeri Alauddin Makassar",
        "STIM LPI Makassar"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySteRegistration3Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.title = "Pendaftaran STE"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val namaLengkap = intent.getStringExtra("namaLengkap2")
        val tempatLahir = intent.getStringExtra("tempatLahir2")
        val tanggalLahir = intent.getStringExtra("tanggalLahir2")
        val alamat = intent.getStringExtra("alamat2")
        val jenisKelamin = intent.getStringExtra("jenisKelamin2")
        val golonganDarah = intent.getStringExtra("golonganDarah2")
        val noTelepon = intent.getStringExtra("noTelepon")
        val email = intent.getStringExtra("email")

        val adapterkampus = ArrayAdapter(this, R.layout.list_dropdown, itemKampus)

        binding.inputKampus.setAdapter(adapterkampus)

        binding.btnDaftar.setOnClickListener {

            if (binding.inputKampus.text.toString().isEmpty()
                || binding.inputAlasan.text.toString().isEmpty()
            ) Toast.makeText(this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show()

            when {
                binding.inputKampus.text.toString() == "" -> binding.kampus.error = " "
                binding.inputAlasan.text.toString() == "" -> binding.alasan.error = " "
                binding.inputAlasan.text.toString().length < 15 -> binding.alasan.error =
                    "Minimal 15 karakter"
                else -> {
                    val asalKampus = binding.inputKampus.text.toString()
                    val alasanSte = binding.inputAlasan.text.toString()

                    steRegistrationPost(
                        namaLengkap,
                        tempatLahir,
                        tanggalLahir,
                        alamat,
                        jenisKelamin,
                        golonganDarah,
                        noTelepon,
                        email,
                        asalKampus,
                        alasanSte
                    )
                }
            }
        }
    }

    private fun steRegistrationPost(
        namaLengkap: String?,
        tempatLahir: String?,
        tanggalLahir: String?,
        alamat: String?,
        jenisKelamin: String?,
        golonganDarah: String?,
        noTelepon: String?,
        email: String?,
        asalKampus: String,
        alasanSte: String
    ) {
        try {
            ApiClient.instancesSte.steRegistration(
                namaLengkap.toString(),
                tempatLahir.toString(),
                tanggalLahir.toString(),
                jenisKelamin.toString(),
                golonganDarah.toString(),
                noTelepon.toString(),
                email.toString(),
                alamat.toString(),
                asalKampus,
                alasanSte
            ).enqueue(object : Callback<DaftarResponse> {
                override fun onResponse(
                    call: Call<DaftarResponse>,
                    response: Response<DaftarResponse>
                ) {

                    Log.e("daftar ", namaLengkap.toString())
                    Log.e("daftar ", tempatLahir.toString())
                    Log.e("daftar ", tanggalLahir.toString())
                    Log.e("daftar ", jenisKelamin.toString())
                    Log.e("daftar ", golonganDarah.toString())
                    Log.e("daftar ", noTelepon.toString())
                    Log.e("daftar ", email.toString())
                    Log.e("daftar ", alamat.toString())
                    Log.e("daftar ", asalKampus)
                    Log.e("daftar ", alasanSte)

                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@SteRegistrationActivity3,
                            response.body()?.id_peserta.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("try ", "success -> ${response.body()}")
                    } else Log.e("try ", "else -> ${response.message()}")
                }

                override fun onFailure(call: Call<DaftarResponse>, t: Throwable) {
                    Log.e("try ", "onfailure[tion -> $t")
                }
            })
        } catch (e: Exception) {
            Log.e("try ", "exception -> $e")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}