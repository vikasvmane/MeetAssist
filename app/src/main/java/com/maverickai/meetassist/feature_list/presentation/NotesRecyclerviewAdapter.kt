package com.maverickai.meetassist.feature_list.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.maverickai.meetassist.R
import com.maverickai.meetassist.feature_create_note.domain.model.ChatGPTResponseModel
import com.maverickai.meetassist.feature_list.domain.model.Note

class NotesRecyclerviewAdapter(
    private val dataSet: List<Note>,
    private val onNotesClickListener: OnNotesClickListener
) : RecyclerView.Adapter<NotesRecyclerviewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        var textNoteTitle: TextView = itemView.findViewById(R.id.textNotesTitle)
        var textNoteSummary: TextView = itemView.findViewById(R.id.textSummary)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_notes, viewGroup, false)

        return ViewHolder(view)
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val note = dataSet[position]
        viewHolder.textNoteTitle.text = note.title
        viewHolder.textNoteSummary.text = getSummaryText(note.summary)

        viewHolder.itemView.setOnClickListener {
            onNotesClickListener.onNoteClicked(note)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    /**
     * Formats the data based on summary
     */
    private fun getSummaryText(output: String?): String? {
        return try {
            val response = Gson().fromJson(output, ChatGPTResponseModel::class.java)
            String.format(FINAL_OUTPUT, response.summary)
        } catch (e: Exception) {
            output
        }
    }

    companion object {
        const val FINAL_OUTPUT = "Summary : %s"
    }
}