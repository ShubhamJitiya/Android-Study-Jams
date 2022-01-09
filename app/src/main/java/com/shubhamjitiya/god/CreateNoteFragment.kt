package com.shubhamjitiya.god

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.shubhamjitiya.god.database.NotesDatabase
import com.shubhamjitiya.god.databinding.FragmentCreateNoteBinding
import com.shubhamjitiya.god.entities.Notes
import com.shubhamjitiya.god.util.NoteBottomSheetFragment
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat
import java.util.*


class CreateNoteFragment : BaseFragment(), EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {
    private var _binding: FragmentCreateNoteBinding? = null
    private val binding get() = _binding!!

    var selectedColor = "#171C26"
    var selectedColorDb = "#171C26"
    var currentDate: String? = null
    private var READ_STORAGE_PERM = 123
    private var REQUEST_CODE_IMAGE = 456
    private var selectedImagePath = ""
    private var webLink = ""
    private var noteId = -1
    private var defaultStatus = "false"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        noteId = requireArguments().getInt("noteId", -1)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (noteId != -1) {

            try {
                launch {
                    context?.let {
                        var notes = NotesDatabase.getDatabase(it).noteDao().getSpecificNote(noteId)
                        binding.colorView.setBackgroundColor(Color.parseColor(notes.color))
                        binding.etNoteTitle.setText(notes.title)
                        binding.etNoteSubTitle.setText(notes.subTitle)
                        binding.etNoteDesc.setText(notes.noteText)

                        if (notes.noteText == defaultStatus)
                            binding.tvShowStatus.setText(R.string.incomplete)
                        else (notes.noteText == defaultStatus)
                        binding.tvShowStatus.setText(R.string.complete)


                        if (notes.imgPath != "") {
                            selectedImagePath = notes.imgPath!!
                            binding.imgNote.setImageBitmap(BitmapFactory.decodeFile(notes.imgPath))
                            binding.layoutImage.visibility = View.VISIBLE
                            binding.imgNote.visibility = View.VISIBLE
                            binding.imgDelete.visibility = View.VISIBLE
                        } else {
                            binding.layoutImage.visibility = View.GONE
                            binding.imgNote.visibility = View.GONE
                            binding.imgDelete.visibility = View.GONE
                        }

                        if (notes.webLink != "") {
                            webLink = notes.webLink!!
                            binding.tvWebLink.text = notes.webLink
                            binding.layoutWebUrl.visibility = View.VISIBLE
                            binding.etWebLink.setText(notes.webLink)
                            binding.imgUrlDelete.visibility = View.VISIBLE
                        } else {
                            binding.imgUrlDelete.visibility = View.GONE
                            binding.layoutWebUrl.visibility = View.GONE
                        }
                    }
                }
            } catch (e: Exception) {
            }
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            BroadcastReceiver, IntentFilter("bottom_sheet_action")
        )

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

        currentDate = sdf.format(Date())
        binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

        binding.tvDateTime.text = currentDate
        binding.tvDateTime.text = String.format(
            "Date: ${
                currentDate.toString().split(" ")[0]
            } \nTime: ${currentDate.toString().split(" ")[1]}"
        )

        binding.tvShowStatus.text = defaultStatus

        binding.imgDone.setOnClickListener {
            if (noteId != -1) {
                updateNote()
            } else {
                saveNote()
            }
        }

        binding.imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.imgMore.setOnClickListener {
            var noteBottomSheetFragment = NoteBottomSheetFragment.newInstance(noteId)
            noteBottomSheetFragment.show(
                requireActivity().supportFragmentManager,
                "Note Bottom Sheet Fragment"
            )
        }

        binding.imgDelete.setOnClickListener {
            selectedImagePath = ""
            binding.layoutImage.visibility = View.GONE
        }

        binding.btnOk.setOnClickListener {
            if (binding.etWebLink.text.toString().trim().isNotEmpty()) {
                checkWebUrl()
            } else {
                Toast.makeText(requireContext(), "Url is Required", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCancel.setOnClickListener {
            if (noteId != -1) {
                binding.tvWebLink.visibility = View.VISIBLE
                binding.layoutWebUrl.visibility = View.GONE
            } else {
                binding.layoutWebUrl.visibility = View.GONE
            }
        }

        binding.imgUrlDelete.setOnClickListener {
            webLink = ""
            binding.tvWebLink.visibility = View.GONE
            binding.imgUrlDelete.visibility = View.GONE
            binding.layoutWebUrl.visibility = View.GONE
        }

        binding.tvWebLink.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(binding.etWebLink.text.toString()))
            startActivity(intent)
        }
    }

    private fun updateNote() {
        launch {

            context?.let {
                var notes = NotesDatabase.getDatabase(it).noteDao().getSpecificNote(noteId)

                notes.title = binding.etNoteTitle.text.toString()
                notes.subTitle = binding.etNoteSubTitle.text.toString()
                notes.noteText = binding.etNoteDesc.text.toString()
                notes.dateTime = currentDate
                notes.color = selectedColorDb
                notes.imgPath = selectedImagePath
                notes.webLink = webLink
                notes.status = defaultStatus

                NotesDatabase.getDatabase(it).noteDao().updateNote(notes)
                binding.etNoteTitle.setText("")
                binding.etNoteSubTitle.setText("")
                binding.etNoteDesc.setText("")
                binding.layoutImage.visibility = View.GONE
                binding.imgNote.visibility = View.GONE
                binding.tvWebLink.visibility = View.GONE
                binding.tvShowStatus.visibility = View.GONE
                requireActivity().supportFragmentManager.popBackStack()


            }
        }
    }

    private fun saveNote() {

        if (binding.etNoteTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Title is Required", Toast.LENGTH_SHORT).show()
        } else if (binding.etNoteSubTitle.text.isNullOrEmpty()) {

            Toast.makeText(context, "Note Sub Title is Required", Toast.LENGTH_SHORT).show()
        } else if (binding.etNoteDesc.text.isNullOrEmpty()) {

            Toast.makeText(context, "Note Description is Required", Toast.LENGTH_SHORT).show()
        } else {

            launch {
                var notes = Notes()
                notes.title = binding.etNoteTitle.text.toString()
                notes.subTitle = binding.etNoteSubTitle.text.toString()
                notes.noteText = binding.etNoteDesc.text.toString()
                notes.dateTime = currentDate
                notes.color = selectedColorDb
                notes.imgPath = selectedImagePath
                notes.webLink = webLink
                notes.status = defaultStatus
                context?.let {
                    NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                    binding.etNoteTitle.setText("")
                    binding.etNoteSubTitle.setText("")
                    binding.etNoteDesc.setText("")
                    binding.layoutImage.visibility = View.GONE
                    binding.imgNote.visibility = View.GONE
                    binding.tvWebLink.visibility = View.GONE
                    binding.tvShowStatus.visibility = View.GONE
                    //Uncheck checkbox default
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }

    }

    private fun deleteNote() {
        launch {
            context?.let {
                NotesDatabase.getDatabase(it).noteDao().deleteSpecificNote(noteId)
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun checkWebUrl() {
        if (Patterns.WEB_URL.matcher(binding.etWebLink.text.toString()).matches()) {
            binding.layoutWebUrl.visibility = View.GONE
            binding.etWebLink.isEnabled = false
            webLink = binding.etWebLink.text.toString()
            binding.tvWebLink.visibility = View.VISIBLE
            binding.tvWebLink.text = binding.etWebLink.text.toString()
        } else {
            Toast.makeText(requireContext(), "Url is not valid", Toast.LENGTH_SHORT).show()
        }
    }


    private val BroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {

            var actionColor = p1!!.getStringExtra("action")

            when (actionColor!!) {

                "Red" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    selectedColorDb = p1.getStringExtra("action")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }

                "Cyan" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    selectedColorDb = p1.getStringExtra("action")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }


                "Yellow" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    selectedColorDb = p1.getStringExtra("action")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }

                "Green" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    selectedColorDb = p1.getStringExtra("action")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }


                "Blue" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    selectedColorDb = p1.getStringExtra("action")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }


                "Magenta" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    selectedColorDb = p1.getStringExtra("action")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }


                "Black" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    selectedColorDb = p1.getStringExtra("action")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }

                "Image" -> {
                    readStorageTask()
                    binding.layoutWebUrl.visibility = View.GONE
                }

                "WebUrl" -> {
                    binding.layoutWebUrl.visibility = View.VISIBLE
                }
                "DeleteNote" -> {
                    //delete note
                    deleteNote()
                }


                else -> {
                    binding.layoutImage.visibility = View.GONE
                    binding.imgNote.visibility = View.GONE
                    binding.layoutWebUrl.visibility = View.GONE
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }
            }
        }

    }

    override fun onDestroy() {

        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(BroadcastReceiver)
        super.onDestroy()

    }


    private fun hasReadStoragePerm(): Boolean {
        return EasyPermissions.hasPermissions(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    private fun readStorageTask() {
        if (hasReadStoragePerm()) {
            pickImageFromGallery()
        } else {
            EasyPermissions.requestPermissions(
                requireActivity(),
                getString(R.string.storage_permission_text),
                READ_STORAGE_PERM,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun pickImageFromGallery() {
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(intent, REQUEST_CODE_IMAGE)
        }
    }

    private fun getPathFromUri(contentUri: Uri): String? {
        var filePath: String? = null
        var cursor = requireActivity().contentResolver.query(contentUri, null, null, null, null)
        if (cursor == null) {
            filePath = contentUri.path
        } else {
            cursor.moveToFirst()
            var index = cursor.getColumnIndex("_data")
            filePath = cursor.getString(index)
            cursor.close()
        }
        return filePath
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                var selectedImageUrl = data.data
                if (selectedImageUrl != null) {
                    try {
                        var inputStream =
                            requireActivity().contentResolver.openInputStream(selectedImageUrl)
                        var bitmap = BitmapFactory.decodeStream(inputStream)
                        binding.imgNote.setImageBitmap(bitmap)
                        binding.imgNote.visibility = View.VISIBLE
                        binding.layoutImage.visibility = View.VISIBLE

                        selectedImagePath = getPathFromUri(selectedImageUrl)!!
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            requireActivity()
        )
    }


    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(requireActivity(), perms)) {
            AppSettingsDialog.Builder(requireActivity()).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onRationaleDenied(requestCode: Int) {

    }

    override fun onRationaleAccepted(requestCode: Int) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}