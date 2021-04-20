package co.id.kedai.kedaiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import co.id.kedai.kedaiapp.databinding.ActivityMainBinding
import co.id.kedai.kedaiapp.fragment.BerandaFragment
import co.id.kedai.kedaiapp.fragment.EbookFragment
import co.id.kedai.kedaiapp.fragment.EventFragment
import co.id.kedai.kedaiapp.fragment.SteFragment

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

        val berandaFragment = BerandaFragment()
        val ebookFragment = EbookFragment()
        val eventFragment = EventFragment()
        val steFragment = SteFragment()

        binding?.bottomNav?.setItemSelected(R.id.navBeranda)
        loadFragment(berandaFragment)

        binding?.bottomNav?.setOnItemSelectedListener { id ->
            when (id) {
                R.id.navBeranda -> loadFragment(berandaFragment)
                R.id.navEvent -> loadFragment(eventFragment)
                R.id.navEbook -> loadFragment(ebookFragment)
                R.id.navSte -> loadFragment(steFragment)
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayoutMain, fragment)
            commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}