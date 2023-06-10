package com.example.taskmaster

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class AddTaskFragment : Fragment() {

    private lateinit var addTaskListener: AddTaskListener

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_task, container, false)

        val btnSave = view.findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener {

            val title = view.findViewById<EditText>(R.id.editTitle).text.toString()
            val description = view.findViewById<EditText>(R.id.editDescription).text.toString()
            val date = view.findViewById<EditText>(R.id.editTextDate).text.toString()
            val katagori = view.findViewById<EditText>(R.id.editTextKatagori).text.toString()


            addTaskListener.onTaskAdded(title, description, date, katagori)
            activity?.supportFragmentManager?.popBackStack()
        }

        return view
    }

    interface AddTaskListener {
        fun onTaskAdded(title: String, description: String, date: String, katagori: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            addTaskListener = context as AddTaskListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement AddTaskListener")
        }
    }


}