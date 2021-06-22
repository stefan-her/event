package be.stefan.event.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import be.stefan.event.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class AddFragment : Fragment() {

    val cal = Calendar.getInstance()
    lateinit var btCalendar : ImageButton
    lateinit var btHour : ImageButton
    lateinit var et_date : EditText
    lateinit var et_hour : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v : View = inflater.inflate(R.layout.fragment_add, container, false)

        btCalendar = v.findViewById(R.id.bt_calendar)
        btCalendar.setOnClickListener { openDialogBoxCalendar() }
        et_date = v.findViewById(R.id.et_date)
        et_date.hint = "--/--/----"


        btHour = v.findViewById(R.id.bt_hour)
        btHour.setOnClickListener { openDialogBoxHour() }
        et_hour = v.findViewById(R.id.et_hour)
        et_hour.hint = "--:--"

        return v

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