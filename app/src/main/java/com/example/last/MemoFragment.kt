package com.example.last

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.last.calendermemo.adapter.MemoAdapter
import com.example.last.calendermemo.db.DBLoader
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MemoFragment : Fragment() {

    private lateinit var adapter: MemoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_memo, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAdd = view.findViewById<FloatingActionButton>(R.id.btn_add)
        btnAdd.setOnClickListener {
            startActivity(Intent(requireContext(), MemoActivity::class.java))
        }


        adapter = MemoAdapter(requireContext())
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
   }

    override fun onResume() {
        super.onResume()
        adapter.setList(DBLoader(requireContext()).memoList(null))
    }
}