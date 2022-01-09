package com.shubhamjitiya.god.adapter

import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.shubhamjitiya.god.R
import com.shubhamjitiya.god.entities.Notes

class NotesAdapter :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    var listener: OnItemClickListener? = null
    var arrList = ArrayList<Notes>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_notes, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    fun setData(arrNotesList: List<Notes>) {
        arrList = arrNotesList as ArrayList<Notes>
    }

    fun setOnClickListener(listener1: OnItemClickListener) {
        listener = listener1
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

        holder.tvTitle.text = arrList[position].title
        holder.tvDesc.text = arrList[position].noteText
        holder.tvDateTime.text = String.format(
            "Date: ${
                arrList[position].dateTime?.split(" ")?.get(0)
            } \nTime: ${arrList[position].dateTime?.split(" ")?.get(1)}"
        )
        holder.status.isChecked = arrList[position].status.toBoolean()

        try {
            if (arrList[position].color != "#171C26") {
                holder.cardView.setCardBackgroundColor(Color.parseColor(arrList[position].color))
            } else {
                holder.cardView.setCardBackgroundColor(Color.parseColor(R.color.ColorLightBlack.toString()))
            }
        } catch (e: Exception) {
        }

        if (arrList[position].color == "Red") {
            holder.tvTitle.setTextColor(Color.parseColor("#FFFFFF"))
            holder.tvDesc.setTextColor(Color.parseColor("#FFFFFF"))
            holder.tvDateTime.setTextColor(Color.parseColor("#FFFFFF"))
        } else if (arrList[position].color == "Cyan") {
            holder.tvTitle.setTextColor(Color.parseColor("#000000"))
        } else if (arrList[position].color == "Yellow") {
            holder.tvTitle.setTextColor(Color.parseColor("#000000"))
        } else if (arrList[position].color == "Green") {
            holder.tvTitle.setTextColor(Color.parseColor("#000000"))
        } else if (arrList[position].color == "Blue") {
            holder.tvTitle.setTextColor(Color.parseColor("#FFFFFF"))
            holder.tvDesc.setTextColor(Color.parseColor("#FFFFFF"))
            holder.tvDateTime.setTextColor(Color.parseColor("#FFFFFF"))
        } else if (arrList[position].color == "Magenta") {
            holder.tvTitle.setTextColor(Color.parseColor("#FFFFFF"))
            holder.tvDesc.setTextColor(Color.parseColor("#FFFFFF"))
            holder.tvDateTime.setTextColor(Color.parseColor("#FFFFFF"))
        }

        if (arrList[position].imgPath != null) {
            holder.imgNote.setImageBitmap(BitmapFactory.decodeFile(arrList[position].imgPath))
            holder.imgNote.visibility = View.VISIBLE
        } else {
            holder.imgNote.visibility = View.GONE
        }

        if (arrList[position].webLink != "") {
            holder.tvWebLink.text = arrList[position].webLink
            holder.tvWebLink.visibility = View.VISIBLE
        } else {
            holder.tvWebLink.visibility = View.GONE
        }

        holder.cardView.setOnClickListener {
            listener!!.onClicked(arrList[position].id!!)
        }
    }

    class NotesViewHolder(inflate: View) : RecyclerView.ViewHolder(inflate) {
        val tvTitle: TextView = inflate.findViewById(R.id.tvTitle)
        val tvDesc: TextView = inflate.findViewById(R.id.tvDesc)
        val tvDateTime: TextView = inflate.findViewById(R.id.tvDateTime)
        val cardView: CardView = inflate.findViewById(R.id.cardView)
        val imgNote: RoundedImageView = inflate.findViewById(R.id.imgNote)
        val tvWebLink: TextView = inflate.findViewById(R.id.tvWebLink)
        val status: CheckBox = inflate.findViewById(R.id.checkBox)
    }

    interface OnItemClickListener {
        fun onClicked(noteId: Int)
    }

}