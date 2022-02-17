package net.neonlotus.activities2022.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.neonlotus.activities2022.R
import net.neonlotus.activities2022.retrofitexample.QuoteObject
import net.neonlotus.activities2022.viewModel.MainViewModel


class NoteRecyclerAdapter(
    val viewModel: MainViewModel,
    val arrayList: ArrayList<QuoteObject>,
    val context: Context
) : RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder>() {


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.tvTitle.text = arrayList[position].content

        viewHolder.delete.setOnClickListener {
            viewModel.remove(arrayList[position])
            notifyItemRemoved(position)
        }
    }


    override fun getItemCount(): Int {
//        if (arrayList.size == 0) {
//            Toast.makeText(context, "List is empty", Toast.LENGTH_SHORT).show()
//        }
        //Log.d("ryan","get item count ${arrayList.size}")
        return arrayList.size
    }


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView
        val delete: ImageButton

        init {
            tvTitle = view.findViewById(R.id.title)
            delete = view.findViewById(R.id.delete)
        }
    }
}