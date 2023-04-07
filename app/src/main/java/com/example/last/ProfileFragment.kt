package com.example.last

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import android.widget.CalendarView.OnDateChangeListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.last.calendermemo.adapter.MemoAdapter
import com.example.last.calendermemo.db.DBLoader
import java.text.SimpleDateFormat
import java.util.*


class ProfileFragment : Fragment() {

    private lateinit var adapter: MemoAdapter
    private lateinit var calendarView: CalendarView
    private lateinit var text_msg: TextView
    private var selectday = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View?
    {
        val root =  inflater.inflate(R.layout.fragment_profile, container, false)
        return root
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarView = view.findViewById<CalendarView>(R.id.calendar_view)
        text_msg = view.findViewById(R.id.text_msg)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = MemoAdapter(requireContext())
        recyclerView.adapter = adapter

        calendarView.setOnDateChangeListener(object :OnDateChangeListener {
            override fun onSelectedDayChange(p0: CalendarView, p1: Int, p2: Int, p3: Int) {
               if(selectday.equals(String.format("%04d/%02d/%02d", p1, p2 + 1, p3))) {
                   val date = p1.toString() + "/" + p2.toString() + "/" +p3.toString()
                   val intent = Intent(requireContext(), MemoActivity::class.java)
                   intent.putExtra("date",date)
                   startActivity(intent)
               } else {
                   val calendar = Calendar.getInstance()
                   calendar.set(p1, p2 ,p3)
                   changeList(calendar)
                   selectday = String.format("%04d/%02d/%02d", p1 ,p2 + 1, p3)
               }
            }

        })



        val date = Date()
        date.time = calendarView.date
        selectday = SimpleDateFormat("yyyy/MM/dd").format(date)
        adapter.setList(
            DBLoader(requireContext()).memoList(
                calendarView.date.toString().substring(0, 6).toLong()
            )
        )

    }

    override fun onResume() {
        super.onResume()
        val date = selectday.split("/")
        val calendar = Calendar.getInstance()
        calendar.set(date[0].toInt(), date[1].toInt() - 1, date[2].toInt())
        changeList(calendar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_calendar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_calendar -> {
                val date = selectday.split("/")
                DatePickerDialog(requireContext(), object  :
                    DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                        val calendar = Calendar.getInstance()
                        calendar.set(p1,p2,p3)
                        calendarView.setDate(calendar.timeInMillis, true, false)
                    }
                },date[0].toInt(),date[1].toInt(), date[2].toInt()).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun changeList(calendar: Calendar) {
        val day = calendar.timeInMillis.toString().substring(0, 6)
        adapter.setList(DBLoader(requireContext()).memoList(day.toLong()))
        if(adapter.itemCount > 0) {
            text_msg.visibility = View.INVISIBLE
        } else {
            text_msg.visibility = View.VISIBLE
        }
    }
}