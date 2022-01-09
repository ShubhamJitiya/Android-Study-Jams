package com.shubhamjitiya.god.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.shubhamjitiya.god.BaseFragment
import com.shubhamjitiya.god.CreateNoteFragment
import com.shubhamjitiya.god.R
import com.shubhamjitiya.god.adapter.NotesAdapter
import com.shubhamjitiya.god.database.NotesDatabase
import com.shubhamjitiya.god.databinding.FragmentTopBinding
import com.shubhamjitiya.god.entities.Notes
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class TopFragment : BaseFragment() {
    private var _binding: FragmentTopBinding? = null
    private val binding get() = _binding!!

    var arrNotes = ArrayList<Notes>()
    var notesAdapter: NotesAdapter = NotesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopBinding.inflate(inflater, container, false)
        val view = binding.root

        //CODE HERE
        // Inflate the layout for this fragment
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AllFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.setHasFixedSize(true)


        binding.recyclerView.layoutManager = LinearLayoutManager(context)
//            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                var notes = NotesDatabase.getDatabase(it).noteDao().getAllNotes()
                notesAdapter!!.setData(notes)
                arrNotes = notes as ArrayList<Notes>
                binding.recyclerView.adapter = notesAdapter

                //Shoe priority wise - RED for now
                var tempArr = ArrayList<Notes>()

                for (arr in arrNotes) {
                    if (arr.color!!.toLowerCase(Locale.getDefault()).contains(
                            "Red".toLowerCase(Locale.getDefault())
                        )
                    ) {
                        tempArr.add(arr)
                    }
                    if (arr.color!!.toLowerCase(Locale.getDefault()).contains(
                            "Yellow".toLowerCase(Locale.getDefault())
                        )
                    ) {
                        tempArr.add(arr)
                    }
                    if (arr.color!!.toLowerCase(Locale.getDefault()).contains(
                            "Cyan".toLowerCase(Locale.getDefault())
                        )
                    ) {
                        tempArr.add(arr)
                    }
                }

                notesAdapter.setData(tempArr)
                notesAdapter.notifyDataSetChanged()
            }
        }

        notesAdapter!!.setOnClickListener(onClicked)

        binding.fabBtnCreateNote.setOnClickListener {
            replaceFragment(CreateNoteFragment.newInstance(), false)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                var tempArr = ArrayList<Notes>()

                for (arr in arrNotes) {
                    if (arr.title!!.toLowerCase(Locale.getDefault())
                            .contains(p0.toString().toLowerCase(Locale.getDefault()))
                    ) {
                        tempArr.add(arr)
                    }
                }

                notesAdapter.setData(tempArr)
                notesAdapter.notifyDataSetChanged()
                return true
            }

        })


    }


    private val onClicked = object : NotesAdapter.OnItemClickListener {
        override fun onClicked(notesId: Int) {


            var fragment: Fragment
            var bundle = Bundle()
            bundle.putInt("noteId", notesId)
            fragment = CreateNoteFragment.newInstance()
            fragment.arguments = bundle

            replaceFragment(fragment, false)
        }

    }


    fun replaceFragment(fragment: Fragment, istransition: Boolean) {
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        if (istransition) {
            fragmentTransition.setCustomAnimations(
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left
            )
        }
        fragmentTransition.replace(R.id.fragmentContainer, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()
    }

//    override fun onDestroy() {
//
//        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(BroadcastReceiver)
//        super.onDestroy()
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}