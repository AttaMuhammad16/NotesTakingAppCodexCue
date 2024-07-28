package com.atta.notestakingapp.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.atta.notestakingapp.AppDataBase
import com.atta.notestakingapp.Notes.Note
import com.atta.notestakingapp.R
import com.atta.notestakingapp.ui.activities.FullNoteActivity
import com.atta.notestakingapp.ui.viewmodel.NotesViewModel
import com.atta.notestakingapp.util.Utils
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NoteAdapter(
    var list: ArrayList<Note>,
    var context: Context,
    var mustToolDatabase: AppDataBase,
    val notesViewModel: NotesViewModel
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    private var selectedColor: Int = 0 // Initialize with a default color

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title = itemView.findViewById<TextView>(R.id.titleTextView)
        var description = itemView.findViewById<TextView>(R.id.descriptionTextView)
        var datetv = itemView.findViewById<TextView>(R.id.datetv)
        var ln = itemView.findViewById<LinearLayout>(R.id.ln)
        var menuImg = itemView.findViewById<ImageView>(R.id.menuImg)
        var card = itemView.findViewById<MaterialCardView>(R.id.noteItemLayoutParent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.note_item_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        var data = list[position]
        holder.datetv.isSelected = true
        holder.description.text = data.description
        holder.datetv.text = data.date
        val maxLength = 8
        if (data.title.length > maxLength) {
            val truncatedText = data.title.substring(0, maxLength) + "..."
            holder.title.text = truncatedText
        } else {
            holder.title.text = data.title
        }

        val colorIndex = position % Utils.myColors().size
        val backgroundColor = context.resources.getColor(Utils.myColors()[colorIndex])
        holder.ln.setBackgroundColor(backgroundColor)

        var noteData = Note()
        holder.menuImg.setOnClickListener {
            val popupMenu = PopupMenu(context, it)
            popupMenu.menuInflater.inflate(R.menu.show_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.update -> {
                            CoroutineScope(Dispatchers.IO).launch {
                                var data = notesViewModel.getNoteById(list[position].id)
                                noteData = data
                            }

                            var builder =
                                AlertDialog.Builder(context).setView(R.layout.update_note_dialog)
                                    .show()

                            var titleEdt = builder.findViewById<EditText>(R.id.titleEdt)
                            var desEdt = builder.findViewById<EditText>(R.id.desEdt)

                            var cancelBtn = builder.findViewById<Button>(R.id.cancelBtn)
                            var saveBtn = builder.findViewById<Button>(R.id.saveBtn)

                            cancelBtn.setOnClickListener {
                                builder.dismiss()
                            }

                            titleEdt.setText(noteData.title)
                            desEdt.setText(noteData.description)

                            saveBtn.setOnClickListener {
                                var title = titleEdt.text.toString()
                                var description = desEdt.text.toString()
                                var id = list[position].id
                                var date = list[position].date
                                if (title.isEmpty()) {
                                    titleEdt.error = "Enter title."
                                } else if (description.isEmpty()) {
                                    desEdt.error = "Enter description."
                                } else {
                                    var data = Note(id, title, description, date)
                                    notesViewModel.updateNote(data)
                                    builder.dismiss()
                                }
                            }
                            return true
                        }

                        R.id.delete -> {
                            val alertDialog = AlertDialog.Builder(context)
                            alertDialog.setTitle("Alert?")
                            alertDialog.setMessage("Are you sure you want to delete this item?")
                            alertDialog.setPositiveButton("Delete") { dialog, _ ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    var title = list[position].title
                                    var des = list[position].description
                                    var id = list[position].id
                                    var date = list[position].date
                                    var dataaa = Note(id, title, des, date)
                                    notesViewModel.deleteNote(dataaa)
                                    withContext(Dispatchers.Main) {
                                        notifyItemChanged(position)
                                    }
                                }
                                dialog.dismiss()
                            }
                            alertDialog.setNegativeButton("Cancel") { dialog, _ ->
                                dialog.dismiss()
                            }
                            alertDialog.show()
                            return true
                        }

                        else -> return false
                    }
                }
            })
            popupMenu.show()
        }

        holder.card.setOnClickListener {
            selectedColor = backgroundColor
            var intent = Intent(context, FullNoteActivity::class.java)
            intent.putExtra("title", data.title)
            intent.putExtra("des", data.description)
            intent.putExtra("date", data.date)
            intent.putExtra("color", selectedColor)
            intent.putExtra("index", colorIndex)
            context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            val dataText =
                list[position].date + "\n" + list[position].title + "\n" + list[position].description
            Utils.copyContentText(dataText, context)
            Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
            true
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}