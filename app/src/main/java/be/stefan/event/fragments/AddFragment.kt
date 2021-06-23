package be.stefan.event.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import be.stefan.event.R
import be.stefan.event.db.EventDao
import be.stefan.event.models.Event
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddFragment : Fragment() {

    lateinit var et_title : EditText
    lateinit var et_desc : EditText
    lateinit var et_address : EditText

    val cal = Calendar.getInstance()
    lateinit var btCalendar : ImageButton
    lateinit var btHour : ImageButton
    lateinit var et_date : EditText
    lateinit var et_hour : EditText

    lateinit var btAdd : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v : View = inflater.inflate(R.layout.fragment_add, container, false)

        et_title = v.findViewById(R.id.et_title)
        et_desc = v.findViewById(R.id.et_desc)
        et_address = v.findViewById(R.id.et_address)

        btCalendar = v.findViewById(R.id.bt_calendar)
        btCalendar.setOnClickListener { openDialogBoxCalendar() }
        et_date = v.findViewById(R.id.et_date)
        et_date.hint = "--/--/----"
        et_date.isEnabled = false

        btHour = v.findViewById(R.id.bt_hour)
        btHour.setOnClickListener { openDialogBoxHour() }
        et_hour = v.findViewById(R.id.et_hour)
        et_hour.hint = "--:--"
        et_date.isEnabled = false

        btAdd = v.findViewById(R.id.bt_insert)
        btAdd.setOnClickListener { send() }

        return v

    }


    private fun send() {
        if(check()) {
            val item = Event(
                null,
                et_title.text.toString().trim(),
                et_desc.text.toString().trim(),
                et_date.text.toString().trim() + " " + et_hour.text.toString().trim(),
                et_address.text.toString().trim()
            )

            val dao = EventDao(requireContext())

            dao.openReadable()
            val check : Boolean = dao.searchEguals(item)
            dao.close()

            if(!check) {
                dao.openWritable()
                val id : Long = dao.insert(item)
                dao.close()
            } else {
                Toast.makeText(requireContext(), getString(R.string.doubleinsert), Toast.LENGTH_LONG).show()
            }

            val listFragment = ListFragment.newInstance()
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.fade_out,
                    android.R.anim.fade_in,
                    android.R.anim.slide_out_right
                )
                .addToBackStack(null)
                .replace(R.id.container_fragment, listFragment)
                .commit()
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun check() : Boolean {
        val a : List<EditText> = listOf(et_date, et_hour, et_address, et_desc, et_title)
        var flag : Boolean = false
        for (item in a) {
            if (item.text.toString().trim().isEmpty()) {
                item.setBackgroundColor(R.color.design_default_color_error)
                flag = true
            } else { item.setBackgroundColor(0x00000000) }
        }
        if (flag) { return false }
        return true
    }

    private fun openDialogBoxCalendar() {
        val y = cal.get(Calendar.YEAR)
        val m = cal.get(Calendar.MONTH)
        val d = cal.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), {
            view, year, month, dayOfMonth ->
            et_date.setText("$dayOfMonth/${month +1}/$year")
        }, y, m, d).show()

    }

    private fun openDialogBoxHour() {
        TimePickerDialog(requireContext(),{
            view, hourOfDay, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
            cal.set(Calendar.MINUTE, minute)
            et_hour.setText("$hourOfDay:$minute") },
        cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
    }

    companion object {
        fun newInstance() = AddFragment()
    }
}