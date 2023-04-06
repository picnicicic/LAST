package com.example.last

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.last.calendermemo.adapter.MemoAdapter
import com.example.last.calendermemo.db.DBLoader
import java.text.SimpleDateFormat
import java.util.*


class ProfileFragment : Fragment() {

    private lateinit var adapter: MemoAdapter
    private var selectday = "";

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View?
    {
        val root =  inflater.inflate(R.layout.fragment_profile, container, false)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendarView = view.findViewById<CalendarView>(R.id.calendar_view)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = MemoAdapter(requireContext())
        recyclerView.adapter = adapter

        calendarView.setOnDateChangeListener(object :OnDateChangeListener {
            override fun onSelectedDayChange(p0: CalendarView, p1: Int, p2: Int, p3: Int) {
               if(selectday.equals(p0.toString()+p1.toString()+p3.toString())) {
                   startActivity(Intent(requireContext(), MemoActivity::class.java))
               }
                val calendar = Calendar.getInstance()
                calendar.set(p1, p2 ,p3)
                val day = calendar.timeInMillis.toString().substring(0, 6)
                adapter.setList(DBLoader(requireContext()).memoList(day.toLong()))
            }
        })
        adapter.setList(
            DBLoader(requireContext()).memoList(
                calendarView.date.toString().substring(0, 6).toLong()))
    }
}