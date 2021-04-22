package co.id.kedai.kedaiapp.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.id.kedai.kedaiapp.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = "Tentang KeDaiApp"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.tvFb.setOnClickListener {
            openSocial(
                "fb://page/1578525432418010",
                "com.facebook.katana",
                "https://www.facebook.com/KeDaiComputerworksPage"
            )
        }
        binding.tvIg.setOnClickListener {
            openSocial(
                "http://instagram.com/kdcomputerworks",
                "com.instagram.android",
                "http://instagram.com/kdcomputerworks"
            )
        }
        binding.tvTw.setOnClickListener {
            openSocial(
                "twitter://user?user_id=328869012",
                "com.twitter.android",
                "https://twitter.com/KDComputerworks"
            )
        }
        binding.tvYt.setOnClickListener {
            openSocial(
                "http://www.youtube.com/channel/UCp0sX1OsrnxKYjYjVQuvA4w",
                "com.google.android.youtube",
                "http://www.youtube.com/channel/UCp0sX1OsrnxKYjYjVQuvA4w"
            )
        }
    }

    private fun openSocial(str: String, str2: String, profileUrl: String) {
        val intent = Intent("android.intent.action.VIEW", Uri.parse(str))
        intent.setPackage(str2)
        try {
            startActivity(intent)
        } catch (unused: ActivityNotFoundException) {
            startActivity(Intent("android.intent.action.VIEW", Uri.parse(profileUrl)))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}