package com.ikkoy.interntestapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.ikkoy.interntestapp.databinding.ListUserBinding
import com.ikkoy.interntestapp.network.model.User

class UserAdapter(private val listUser: List<User>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ViewHolder(var binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUser[position]
        with(holder.binding) {
            userName.text = buildString {
                append(user.firstName)
                append(" ")
                append(user.lastName)
            }
            userEmail.text = user.email
            Glide.with(root.context)
                .load(user.avatar)
                .transform(CircleCrop())
                .into(userPhotos)
            root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

}