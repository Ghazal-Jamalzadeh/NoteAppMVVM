package com.jmzd.ghazal.noteappmvvm.ui.main.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jmzd.ghazal.noteappmvvm.data.model.NoteEntity
import com.jmzd.ghazal.noteappmvvm.databinding.FragmentNoteBinding
import com.jmzd.ghazal.noteappmvvm.utils.BUNDLE_ID
import com.jmzd.ghazal.noteappmvvm.utils.EDIT
import com.jmzd.ghazal.noteappmvvm.utils.NEW
import com.jmzd.ghazal.noteappmvvm.utils.setupListWithAdapter
import com.jmzd.ghazal.noteappmvvm.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class NoteFragment : BottomSheetDialogFragment() {

    //binding
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding

    //ViewModel
    private val viewModel: NoteViewModel by viewModels()

    @Inject
    lateinit var entity: NoteEntity

    //spinners
    private val categoriesList: MutableList<String> = mutableListOf()
    private val prioriesList: MutableList<String> = mutableListOf()
    private var category = ""
    private var priority = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init views
        binding?.apply {

            //Close
            closeImg.setOnClickListener { dismiss() }

            //Spinner Category
            viewModel.loadCategoriesData()
            viewModel.categoriesList.observe(viewLifecycleOwner) {
                categoriesList.addAll(it)
                categoriesSpinner.setupListWithAdapter(it) { itItem -> category = itItem }
            }
            //Spinner priority
            viewModel.loadPrioritiesData()
            viewModel.prioritiesList.observe(viewLifecycleOwner) {
                prioriesList.addAll(it)
                prioritySpinner.setupListWithAdapter(it) { itItem -> priority = itItem }
            }
            //Click
            saveNote.setOnClickListener {
                val title = titleEdt.text.toString()
                val desc = descEdt.text.toString()
                entity.id = 0
                entity.title = title
                entity.desc = desc
                entity.category = category
                entity.priority = priority

                if (title.isNotEmpty() && desc.isNotEmpty()) {
                    viewModel.saveEditNote(false, entity)

//                this@NoteFragment.dismiss()
                    dismiss()
                }

            }

        }
    }

    override fun onStop() {
        super.onStop()
        _binding = null
    }

}