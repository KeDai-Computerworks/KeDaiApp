package co.id.kedai.kedaiapp.activity

import android.Manifest
import android.app.DownloadManager
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import co.id.kedai.kedaiapp.api.ApiClient
import co.id.kedai.kedaiapp.databinding.ActivityCheckPaidBinding
import co.id.kedai.kedaiapp.model.DaftarResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckPaidActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckPaidBinding
    private lateinit var downloadManager: DownloadManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckPaidBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.title = "Cek Pembayaran"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                100
            )
        }

        binding.btnCek.setOnClickListener {
            val regis = binding.inputNoRegistrasi.text.toString()
            binding.btnCek.startAnimation()
            cekPaid(regis)
        }
    }

    private fun cekPaid(regis: String) {
        ApiClient.instancesSte.steDownload(regis)
            .enqueue(object : Callback<DaftarResponse> {
                override fun onResponse(
                    call: Call<DaftarResponse>,
                    response: Response<DaftarResponse>
                ) {
                    if (response.body()?.pdf != null) {
                        val linkPdf = response.body()?.pdf.toString()

                        binding.btnCek.revertAnimation()

                        binding.btnDownlaod.isVisible = true
                        binding.btnDownlaod.setOnClickListener {
                            binding.btnDownlaod.startAnimation()
                            downloadPdf(linkPdf)
                            GlobalScope.launch(context = Dispatchers.Main) {
                                delay(2000)
                                binding.btnDownlaod.revertAnimation()
                            }
                        }
                    } else {
                        binding.btnCek.revertAnimation()
                        Toast.makeText(
                            this@CheckPaidActivity,
                            response.body()?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<DaftarResponse>, t: Throwable) {
                    binding.btnCek.revertAnimation()
                    binding.btnDownlaod.isVisible = false
                    Toast.makeText(
                        this@CheckPaidActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun downloadPdf(linkPdf: String) {
        downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val uri: Uri = Uri.parse(linkPdf)
        val separated: List<String> = linkPdf.split("/")
        val myFile = separated[separated.size - 1]
        val request: DownloadManager.Request = DownloadManager.Request(uri)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            myFile
        )
        downloadManager.enqueue(request)
        Toast.makeText(this, "Periksa file unduhan anda", Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permisi ditolak", Toast.LENGTH_SHORT).show()
        }
    }
}
