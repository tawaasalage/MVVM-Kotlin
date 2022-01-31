package com.apex.samplekotlin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.apex.samplekotlin.R
import com.apex.samplekotlin.data.model.Blog
import com.apex.samplekotlin.ui.adapter.NoteRecyclerAdapter
import com.apex.samplekotlin.ui.viewmodel.MainViewModel
import com.apex.samplekotlin.ui.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var viewManager = LinearLayoutManager(this)
    private lateinit var viewModel: MainViewModel
    private lateinit var but: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factory = MainViewModelFactory()
        viewModel = ViewModelProviders.of(this,factory).get(MainViewModel::class.java)
        but = findViewById(R.id.button)
        but.setOnClickListener {
            addData()
        }

        initialiseAdapter()
    }

    private fun initialiseAdapter(){
        recycler.layoutManager = viewManager
        observeData()
    }

    fun observeData(){
        viewModel.lst.observe(this, Observer{
            Log.i("data",it.toString())
            recycler.adapter= NoteRecyclerAdapter(viewModel, it, this)
        })
    }


    fun addData(){
        var title=titletxt.text.toString()
        if(title.isNullOrBlank()){
            Toast.makeText(this,"Enter value!", Toast.LENGTH_LONG).show()
        }else{
            var blog= Blog(title)
            viewModel.add(blog)
            titletxt.text.clear()
            recycler.adapter?.notifyDataSetChanged()
        }

    }
}
