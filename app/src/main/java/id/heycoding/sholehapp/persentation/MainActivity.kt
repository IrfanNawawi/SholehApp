package id.heycoding.sholehapp.persentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import dagger.hilt.android.AndroidEntryPoint
import id.heycoding.sholehapp.R
import id.heycoding.sholehapp.databinding.ActivityMainBinding
import id.heycoding.sholehapp.persentation.home.HomeFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _activityMainBinding: ActivityMainBinding? = null
    private val activityMainBinding get() = _activityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding?.root)

        moveToFragment(HomeFragment())
    }

    fun moveToFragment(fragment: Fragment) {
        this.supportFragmentManager.beginTransaction().replace(R.id.frag_container_main, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}