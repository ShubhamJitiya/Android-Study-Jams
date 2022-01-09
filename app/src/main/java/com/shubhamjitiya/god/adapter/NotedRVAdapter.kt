package com.shubhamjitiya.god.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.shubhamjitiya.god.R
import com.shubhamjitiya.god.entities.Note

class NotedRVAdapter(private val context: Context, private val listener: Context?) :
    RecyclerView.Adapter<NotedRVAdapter.NoteViewHolder>() {
    //    private var allNotes = ArrayList<Note>()
    private var allNotes = emptyList<Note>()
    var onItemClick: ((Note) -> Unit)? = null


    inner class NoteViewHolder(inflate: View) : RecyclerView.ViewHolder(inflate) {
        val cardView: CardView = inflate.findViewById(R.id.cardView)
        val imgNote: RoundedImageView = inflate.findViewById(R.id.imgNote)
        val tvTitle: TextView = inflate.findViewById(R.id.tvTitle)
        val tvWebLink: TextView = inflate.findViewById(R.id.tvWebLink)
        val tvStatus: TextView = inflate.findViewById(R.id.tvStatus)
        val tvDesc: TextView = inflate.findViewById(R.id.tvDesc)
        val tvDate: TextView = inflate.findViewById(R.id.tvDate)
        val tvTime: TextView = inflate.findViewById(R.id.tvTime)
        val checkBox: CheckBox = inflate.findViewById(R.id.checkBox)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val viewHolder =
            NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note, parent, false))

        return viewHolder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = allNotes[position]
        holder.tvTitle.text = currentNote.noteTitle

        holder.tvWebLink.text = R.string.web_url.toString()
        holder.tvStatus.text = currentNote.noteStatus.toString()

        holder.tvDesc.text = currentNote.noteDescription
        holder.tvDate.text = String.format("Date: %s", currentNote.date)
        holder.tvTime.text = String.format("Time: %s", currentNote.time)

        holder.checkBox.isChecked = !currentNote.noteStatus.equals("Incomplete")

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(allNotes[position])
        }
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }
//
//    fun updateList(newList: List<Note>) {
//        allNotes.clear()
//        allNotes.addAll(newList)
//        notifyDataSetChanged()
//    }

    fun setData(notes: List<Note>) {
        this.allNotes = notes
        notifyDataSetChanged()
    }
}

interface INotesRVAdapter {
    fun onItemClicked(note: Note)
}