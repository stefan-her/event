package be.stefan.event

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentTransaction
import be.stefan.event.db.EventDao
import be.stefan.event.fragments.ListFragment
import be.stefan.event.fragments.WelcomeFragment
import be.stefan.event.models.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

/*
        CoroutineScope(IO).launch {
            while(true) {
                updateDb()
                delay(10000)
            }
        }*/
    }

    private suspend fun updateDb() {
        val dao = EventDao(applicationContext)
        dao.openReadable()
        val list : MutableList<Long>? = dao.selectOld()
        dao.close()

        if (list != null && list.size > 0) {
            Log.v("--->", "nb: $list")

            // todo requete de suppression
            // creer un evnt pour signaler la mise a jour (dans listfragment!)
        }
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