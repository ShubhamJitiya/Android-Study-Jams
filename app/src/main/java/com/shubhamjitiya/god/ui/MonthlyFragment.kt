package com.shubhamjitiya.god.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shubhamjitiya.god.R
import com.shubhamjitiya.god.adapter.NotedRVAdapter
import com.shubhamjitiya.god.databinding.FragmentMonthlyBinding
import com.shubhamjitiya.god.entities.Note
import com.shubhamjitiya.god.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class MonthlyFragment : Fragment() {
    private var _binding: FragmentMonthlyBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NoteViewModel
    var currentDate: String? = null
    var currentTime: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMonthlyBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = context?.let { NotedRVAdapter(it, context) }
        binding.recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        viewModel.allNotes.observe(viewLifecycleOwner, { notes ->
            adapter?.setData(notes)
        })

        binding.fabBtnCreateNote.setOnClickListener {
            showAddNoteDialog()
        }
        if (adapter != null) {
            adapter.onItemClick = { notes -> showActionDialog(notes) }
        }
        return view
    }


    private fun showAddNoteDialog() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_add_new_note)
        dialog.setCancelable(true)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        val etNoteTitle: EditText = dialog.findViewById(R.id.etNoteTitle)
        val etNoteDescription: EditText = dialog.findViewById(R.id.etNoteDescription)

        dialog.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btnAddNote).setOnClickListener {

            if (inputCheck(etNoteTitle.text.toString(), etNoteDescription.text.toString())) {
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                currentDate = sdf.format(Date()).split(" ")[0]
                currentTime = sdf.format(Date()).split(" ")[1]

                val notes = Note(
                    0,
                    etNoteTitle.text.toString(),
                    etNoteDescription.text.toString(),
                    noteStatus = "Incomplete",
                    date = currentDate.toString(),
                    time = currentTime.toString()
                )
                viewModel.insertNote(notes)
                Toast.makeText(context, "Task added successfully", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(context, "Please enter data", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
        dialog.window!!.attributes = layoutParams

    }

    private fun inputCheck(noteTitle: String, noteDescription: String): Boolean {
        return !(TextUtils.isEmpty(noteTitle) && TextUtils.isEmpty(noteDescription))
    }

    private fun showActionDialog(notes: Note) {
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton("Delete") { _, _ ->
            viewModel.deleteNote(notes)
            Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Update") { _, _ ->
            showUpdateDialog(notes)
        }
        builder.setNeutralButton("Cancel") { _, _ ->
            Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show()
        }
        builder.setTitle("Select Action")
        builder.create().show()
    }

    private fun showUpdateDialog(notes: Note) {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_update_note)
        dialog.setCancelable(true)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        val etNoteTitle: EditText = dialog.findViewById(R.id.etNoteTitle)
        val etNoteDescription: EditText = dialog.findViewById(R.id.etNoteDescription)

        etNoteTitle.setText(notes.noteTitle)
        etNoteDescription.setText(notes.noteDescription)

        dialog.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.btnUpdate).setOnClickListener {

            if (inputCheck(etNoteTitle.text.toString(), etNoteDescription.text.toString())) {
                val notes =
                    Note(
                        notes.id,
                        etNoteTitle.text.toString(),
                        etNoteDescription.text.toString(),
                        noteStatus = "Incomplete",
                        date = currentDate.toString(),
                        time = currentTime.toString()
                    )
                viewModel.updateData(notes)
                Toast.makeText(context, "Notes Updated", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(context, "Please enter data", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.findViewById<Button>(R.id.btnMarkAsDone).setOnClickListener {
            if (inputCheck(etNoteTitle.text.toString(), etNoteDescription.text.toString())) {
                val notes =
                    Note(
                        notes.id,
                        etNoteTitle.text.toString(),
                        etNoteDescription.text.toString(),
                        noteStatus = "Complete",
                        date = currentDate.toString(),
                        time = currentTime.toString()
                    )
                Toast.makeText(context, "Task successfully completed !", Toast.LENGTH_SHORT).show()
                viewModel.updateData(notes)
                dialog.dismiss()
            } else {
                Toast.makeText(context, "Please enter data", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
        dialog.window!!.attributes = layoutParams
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}