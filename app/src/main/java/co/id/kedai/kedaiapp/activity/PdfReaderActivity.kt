package co.id.kedai.kedaiapp.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import co.id.kedai.kedaiapp.api.ApiClient
import co.id.kedai.kedaiapp.databinding.ActivityPdfReaderBinding
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.krishna.fileloader.FileLoader
import com.krishna.fileloader.builder.FileLoaderBuilder
import com.krishna.fileloader.listener.FileRequestListener
import com.krishna.fileloader.pojo.FileResponse
import com.krishna.fileloader.request.FileLoadRequest
import java.io.File

class PdfReaderActivity : AppCompatActivity(), OnLoadCompleteListener, OnPageErrorListener {

    private val sb: StringBuilder = StringBuilder()
    private lateinit var binding: ActivityPdfReaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfReaderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

        val with: FileLoaderBuilder = FileLoader.with(this)
        val path = intent.getStringExtra("path")

        sb.append(ApiClient.URL)
        sb.append(path)

        binding.fabBack.setOnClickListener { onBackPressed() }

        with.load(sb.toString(), false)
            .fromDirectory("KeDaiApp-FilesPDF", FileLoader.DIR_INTERNAL)
            .asFile(object : FileRequestListener<File> {
                override fun onLoad(request: FileLoadRequest?, response: FileResponse<File>?) {
                    try {
                        binding.pdfView.fromFile(response?.body)
                            .defaultPage(1)
                            .enableAnnotationRendering(true)
                            .onLoad(this@PdfReaderActivity)
                            .scrollHandle(DefaultScrollHandle(this@PdfReaderActivity))
                            .spacing(10)
                            .onPageError(this@PdfReaderActivity)
                            .load()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onError(request: FileLoadRequest?, t: Throwable?) {

                    Toast.makeText(this@PdfReaderActivity, "Gagal memuat", Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }

    override fun loadComplete(nbPages: Int) {
        binding.progresBar.isVisible = false

    }

    override fun onPageError(page: Int, t: Throwable?) {
        binding.progresBar.isVisible = false
        Toast.makeText(this, "Gagal memuat", Toast.LENGTH_SHORT).show()
    }

}


