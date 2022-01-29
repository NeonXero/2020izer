package net.neonlotus.activities2022.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import net.neonlotus.activities2022.R
import net.neonlotus.activities2022.model.Blog
import net.neonlotus.activities2022.viewModel.MainViewModel


class NoteRecyclerAdapter(
    val viewModel: MainViewModel,
    val arrayList: ArrayList<Blog>,
    val context: Context
) : RecyclerView.Adapter<NoteRecyclerAdapter.NotesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        //val binding = ElementListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        var root = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return NotesViewHolder(root)
    }

    override fun onBindViewHolder(holder: NoteRecyclerAdapter.NotesViewHolder, position: Int) {
        holder.bind(arrayList.get(position))
    }

    override fun getItemCount(): Int {
        if (arrayList.size == 0) {
            Toast.makeText(context, "List is empty", Toast.LENGTH_LONG).show()
        } else {

        }
        return arrayList.size
    }


    inner class NotesViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding) {
        fun bind(blog: Blog) {
            binding.title.text = blog.title
            binding.delete.setOnClickListener {
                viewModel.remove(blog)
                notifyItemRemoved(arrayList.indexOf(blog))
            }
        }

    }
//I give up for now UGH
}