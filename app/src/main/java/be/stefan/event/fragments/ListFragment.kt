package be.stefan.event.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import be.stefan.event.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var v : View = inflater.inflate(R.layout.fragment_list, container, false)
        val btAdd = v.findViewById(R.id.bt_add) as FloatingActionButton
        btAdd.setOnClickListener { openAdd() }

        return v
    }

    private fun openAdd() {
        //Toast.makeText(context,"this is toast message",Toast.LENGTH_LONG).show()

        val addFragment = AddFragment.newInstance()
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.slide_out_right
            )
            .addToBackStack(null)
            .replace(R.id.container_fragment, addFragment)
            .commit()

    }

    companion object {
        fun newInstance() = ListFragment()
    }
}