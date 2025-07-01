package com.hamoda.peoplerecord.presentation.users.xml

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hamoda.peoplerecord.R
import com.hamoda.peoplerecord.domain.model.User

class UserAdapter(
    private var users: List<User>,
    private val onDeleteClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    fun submitList(newUsers: List<User>) {
        users = newUsers
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgAvatar: ImageView = itemView.findViewById(R.id.imgAvatar)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvJob: TextView = itemView.findViewById(R.id.tvJob)
        private val tvAgeGender: TextView = itemView.findViewById(R.id.tvAgeGender)
        private val btnDelete: Button = itemView.findViewById(R.id.btnDelete)

        fun bind(user: User) {
            tvName.text = user.name
            tvJob.text = user.jobTitle
            tvAgeGender.text = "age : ${user.age} | gender : ${user.gender}"
            imgAvatar.setImageResource(if (user.gender == "Male") R.drawable.ic_boy else R.drawable.ic_girl)
            btnDelete.setOnClickListener { onDeleteClick(user) }
        }
    }
}
