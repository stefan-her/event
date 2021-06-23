package be.stefan.event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import be.stefan.event.db.EventDao
import be.stefan.event.fragments.ListFragment
import be.stefan.event.fragments.WelcomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

    }

    private fun init() {
        val welcomeFragment = WelcomeFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.container_fragment, welcomeFragment)
            .commit()

        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {

                val listFragment = ListFragment.newInstance()
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        android.R.anim.slide_in_left,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.slide_out_right
                    )
                    .replace(R.id.container_fragment, listFragment)
                    .commit()
            }
        }.start()
    }
}