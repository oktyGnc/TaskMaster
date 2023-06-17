package com.example.taskmaster


import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity(), AddTaskFragment.AddTaskListener {

    private lateinit var taskListLayout: LinearLayout
    private lateinit var addTaskButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        taskListLayout = findViewById(R.id.taskList)
        addTaskButton = findViewById(R.id.addTaskButton)
        addTaskButton.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun filterTasks(query: String) {
        val queryLowerCase = query.toLowerCase()
        for (i in 0 until taskListLayout.childCount) {
            val cardView = taskListLayout.getChildAt(i) as CardView
            val titleTextView = cardView.findViewById<TextView>(R.id.titleTextView)
            val title = titleTextView.text.toString().toLowerCase()
            if (title.contains(queryLowerCase)) {
                cardView.visibility = View.VISIBLE
            } else {
                cardView.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Arama sorgusu değiştiğinde yapılacak işlemleri burada gerçekleştirin
                filterTasks(newText)
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                // Arama iconuna tıklandığında ne yapılacağını burada belirleyin
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onTaskAdded(title: String, description: String, date: String, katagori: String) {

        if (title.isEmpty() || description.isEmpty() || date.isEmpty() || katagori.isEmpty()) {
            // İçerik boş, Toast mesajı göster
            Toast.makeText(this, "Görev bilgileri eksik", Toast.LENGTH_SHORT).show()
            return
        }

        val cardView = layoutInflater.inflate(R.layout.task_cardview, taskListLayout, false)

        val titleTextView: TextView = cardView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = cardView.findViewById(R.id.descriptionTextView)
        val dateTextView: TextView = cardView.findViewById(R.id.dateTextView)
        val katagoriText: TextView = cardView.findViewById(R.id.katagoriText)

        titleTextView.text = title
        descriptionTextView.text = description
        dateTextView.text = "Bitiş Tarihi : $date"
        katagoriText.text = "Katagori : $katagori"

        taskListLayout.addView(cardView)

        val deleteImageView: ImageView = cardView.findViewById(R.id.deleteImageView)
        val checkImageView : ImageView = cardView.findViewById(R.id.check)
        deleteImageView.setOnClickListener {
            taskListLayout.removeView(cardView)
        }

        checkImageView.setOnClickListener {
            titleTextView.paintFlags = titleTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            descriptionTextView.paintFlags = descriptionTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            dateTextView.paintFlags = dateTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            katagoriText.paintFlags = katagoriText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }



    }


    private fun showAddTaskDialog() {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        val addTaskFragment = AddTaskFragment()
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(android.R.id.content, addTaskFragment).commit()
    }


}
