package com.jmzd.ghazal.noteappmvvm.ui.main.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jmzd.ghazal.noteappmvvm.data.model.NoteEntity
import com.jmzd.ghazal.noteappmvvm.databinding.FragmentNoteBinding
import com.jmzd.ghazal.noteappmvvm.utils.*
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

    //other
    private var noteId = 0
    private var type = ""
    private var isEdit = false

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

            //Bundle
            noteId = arguments?.getInt(BUNDLE_ID) ?: 0
            //Type
//            type = if (noteId > 0) EDIT else NEW
            if (noteId > 0) {
                type = EDIT
                isEdit = true
            } else {
                isEdit = false
                type = NEW
            }

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
            //Note data
            if (type == EDIT) {
                viewModel.getData(noteId)
                viewModel.noteData.observe(viewLifecycleOwner) { itData ->
                    itData.data?.let {
                        titleEdt.setText(it.title)
                        descEdt.setText(it.desc)
                        categoriesSpinner.setSelection(categoriesList.getIndexFromList(it.category) , true ) // bool : animate (optional)
                        prioritySpinner.setSelection(prioriesList.getIndexFromList(it.priority) )
                    }
                }
            }
            //Click
            saveNote.setOnClickListener {
                val title = titleEdt.text.toString()
                val desc = descEdt.text.toString()
                entity.id = noteId
                entity.title = title
                entity.desc = desc
                entity.category = category
                entity.priority = priority

                if (title.isNotEmpty() && desc.isNotEmpty()) {

                    viewModel.saveEditNote(isEdit, entity)


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