package be.stefan.event.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import be.stefan.event.R
import be.stefan.event.adapters.EventListAdapter
import be.stefan.event.db.EventDao
import be.stefan.event.models.Event
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var v : View = inflater.inflate(R.layout.fragment_list, container, false)
        val btAdd = v.findViewById(R.id.bt_add) as FloatingActionButton
        btAdd.setOnClickListener { openAdd() }
        prepareRecycleView(v)
        return v
    }

    @SuppressLint("ResourceType")
    private fun prepareRecycleView(v : View) {
        val recyclerView = v.findViewById(R.id.rv_eventlist) as RecyclerView
        recyclerView.setHasFixedSize(false)

        val dao = EventDao(requireContext())

        dao.openReadable()
        val list : List<Event>? = dao.allItems()
        dao.close()

        if (list != null) {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            ContextCompat.getDrawable(requireContext(), R.drawable.diviser)?.let {
                dividerItemDecoration.setDrawable(it)
            };
            recyclerView.addItemDecoration(dividerItemDecoration)

            val adapter = EventListAdapter(list)
            recyclerView.adapter = adapter
        }
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