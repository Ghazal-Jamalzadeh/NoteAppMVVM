package com.jmzd.ghazal.noteappmvvm.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jmzd.ghazal.noteappmvvm.R
import com.jmzd.ghazal.noteappmvvm.databinding.ActivityMainBinding
import com.jmzd.ghazal.noteappmvvm.ui.main.note.NoteFragment

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //init views
        binding?.apply {
            //Note fragment
            addNoteBtn.setOnClickListener {
                NoteFragment().show(supportFragmentManager, NoteFragment().tag)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}