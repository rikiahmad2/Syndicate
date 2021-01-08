package com.riki.net89.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.riki.net89.Content
import com.riki.net89.R

class PostAdapter (private val list:ArrayList<Content>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>(){
    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(postResponse: Content){
            with(itemView){
                val id = "${postResponse.id_content}"
                val title = "${postResponse.title}"
                val desc = "${postResponse.text}"
                val tvId : TextView = findViewById(R.id.tvId)
                val tvTitle : TextView = findViewById(R.id.tvTitle)
                val tvDescription : TextView = findViewById(R.id.tvDescription)
                tvId.text = id
                tvTitle.text = title
                tvDescription.text = desc
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(list[position])
    }
}