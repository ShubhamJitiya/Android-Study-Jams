package com.shubhamjitiya.god.util

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shubhamjitiya.god.R
import com.shubhamjitiya.god.databinding.FragmentNotesBottomSheetBinding

class NoteBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentNotesBottomSheetBinding? = null
    private val binding get() = _binding!!

    var selectedColor = "#171C26"

    companion object {
        var noteId = -1
        fun newInstance(id: Int): NoteBottomSheetFragment {
            val args = Bundle()
            val fragment = NoteBottomSheetFragment()
            fragment.arguments = args
            noteId = id
            return fragment
        }
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val view = LayoutInflater.from(context).inflate(R.layout.fragment_notes_bottom_sheet, null)
        dialog.setContentView(view)

        val param = (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams

        val behavior = param.behavior

        if (behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    TODO("Not yet implemented")
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    var state = ""
                    when (newState) {
                        BottomSheetBehavior.STATE_DRAGGING -> {
                            state = "DRAGGING"
                        }
                        BottomSheetBehavior.STATE_SETTLING -> {
                            state = "SETTLING"
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            state = "EXPANDED"
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            state = "COLLAPSED"
                        }

                        BottomSheetBehavior.STATE_HIDDEN -> {
                            state = "HIDDEN"
                            dismiss()
                            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        }

                    }
                }

            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBottomSheetBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (noteId != -1) {
            binding.layoutDeleteNote.visibility = View.VISIBLE
        } else {
            binding.layoutDeleteNote.visibility = View.GONE
        }
        setListener()
    }

    private fun setListener() {
        binding.fNote1.setOnClickListener {

            binding.imgNote1.setImageResource(R.drawable.ic_tick)
            binding.imgNote2.setImageResource(0)
            binding.imgNote3.setImageResource(0)
            binding.imgNote4.setImageResource(0)
            binding.imgNote5.setImageResource(0)
            binding.imgNote6.setImageResource(0)
            binding.imgNote7.setImageResource(0)
            selectedColor = "#FF0000"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Red")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        }
        binding.fNote2.setOnClickListener {
            binding.imgNote1.setImageResource(0)
            binding.imgNote2.setImageResource(R.drawable.ic_tick)
            binding.imgNote3.setImageResource(0)
            binding.imgNote4.setImageResource(0)
            binding.imgNote5.setImageResource(0)
            binding.imgNote6.setImageResource(0)
            binding.imgNote7.setImageResource(0)
            selectedColor = "#00FFFF"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Cyan")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        }

        binding.fNote3.setOnClickListener {
            binding.imgNote1.setImageResource(0)
            binding.imgNote2.setImageResource(0)
            binding.imgNote3.setImageResource(R.drawable.ic_tick)
            binding.imgNote4.setImageResource(0)
            binding.imgNote5.setImageResource(0)
            binding.imgNote6.setImageResource(0)
            binding.imgNote7.setImageResource(0)
            selectedColor = "#FFFF00"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Yellow")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        }

        binding.fNote4.setOnClickListener {
            binding.imgNote1.setImageResource(0)
            binding.imgNote2.setImageResource(0)
            binding.imgNote3.setImageResource(0)
            binding.imgNote4.setImageResource(R.drawable.ic_tick)
            binding.imgNote5.setImageResource(0)
            binding.imgNote6.setImageResource(0)
            binding.imgNote7.setImageResource(0)
            selectedColor = "#00FF00"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Green")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

        }

        binding.fNote5.setOnClickListener {
            binding.imgNote1.setImageResource(0)
            binding.imgNote2.setImageResource(0)
            binding.imgNote3.setImageResource(0)
            binding.imgNote4.setImageResource(0)
            binding.imgNote5.setImageResource(R.drawable.ic_tick)
            binding.imgNote6.setImageResource(0)
            binding.imgNote7.setImageResource(0)
            selectedColor = "#0000FF"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Blue")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        binding.fNote6.setOnClickListener {

            binding.imgNote1.setImageResource(0)
            binding.imgNote2.setImageResource(0)
            binding.imgNote3.setImageResource(0)
            binding.imgNote4.setImageResource(0)
            binding.imgNote5.setImageResource(0)
            binding.imgNote6.setImageResource(R.drawable.ic_tick)
            binding.imgNote7.setImageResource(0)
            selectedColor = "#FF00FF"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Magenta")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        binding.fNote7.setOnClickListener {
            binding.imgNote1.setImageResource(0)
            binding.imgNote2.setImageResource(0)
            binding.imgNote3.setImageResource(0)
            binding.imgNote4.setImageResource(0)
            binding.imgNote5.setImageResource(0)
            binding.imgNote6.setImageResource(0)
            binding.imgNote7.setImageResource(R.drawable.ic_tick)
            selectedColor = "#202734"

            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Black")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        binding.layoutImage.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "Image")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()
        }
        binding.layoutWebUrl.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "WebUrl")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()
        }
        binding.layoutDeleteNote.setOnClickListener {
            val intent = Intent("bottom_sheet_action")
            intent.putExtra("action", "DeleteNote")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            dismiss()
        }


    }

}