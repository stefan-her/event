package be.stefan.event.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import be.stefan.event.R
import be.stefan.event.adapters.EventListAdapter
import be.stefan.event.db.EventDao
import be.stefan.event.models.Event
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

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
        val list : MutableList<Event>? = dao.allItems()
        dao.close()

        if (list != null) {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            ContextCompat.getDrawable(requireContext(), R.drawable.diviser)?.let {
                dividerItemDecoration.setDrawable(it)
            };
            recyclerView.addItemDecoration(dividerItemDecoration)


            val adapter = EventListAdapter(list)
            recyclerView.adapter = adapter


            val itemTouchHelperCallback =
                object :
                    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ) = false

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        //Toast.makeText(context,"this is toast message",Toast.LENGTH_LONG).show()

//                        val position = viewHolder.adapterPosition
//
//                        Snackbar.make(
//                            coordinator_layout, // The ID of your coordinator_layout
//                            getString(R.string.delete),
//                            Snackbar.LENGTH_LONG
//                        ).apply {
//                            setAction("UNDO") {
//                                noteViewModel.create(note)
//                                // If you're not using LiveData you might need to tell the adapter
//                                // that an item was inserted: notifyItemInserted(position);
//                                recyclerView.scrollToPosition(position)
//                            }
//                            setActionTextColor(Color.YELLOW)
//                        }.show()


                    }
                }
            ItemTouchHelper(itemTouchHelperCallback).apply {
                attachToRecyclerView(recyclerView)
            }





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