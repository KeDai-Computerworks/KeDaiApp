package co.id.kedai.kedaiapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import co.id.kedai.kedaiapp.activity.AboutActivity
import co.id.kedai.kedaiapp.databinding.ActivityMainBinding
import co.id.kedai.kedaiapp.fragment.BerandaFragment
import co.id.kedai.kedaiapp.fragment.EbookFragment
import co.id.kedai.kedaiapp.fragment.EventFragment
import co.id.kedai.kedaiapp.fragment.SteFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bottomNav.setItemSelected(R.id.navBeranda)
        loadFragment(BerandaFragment())

        binding.bottomNav.setOnItemSelectedListener { id ->
            when (id) {
                R.id.navBeranda -> loadFragment(BerandaFragment())
                R.id.navEvent -> loadFragment(EventFragment())
                R.id.navEbook -> loadFragment(EbookFragment())
                R.id.navSte -> loadFragment(SteFragment())
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayoutMain, fragment)
            commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.about_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toAbout -> startActivity(Intent(this, AboutActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}