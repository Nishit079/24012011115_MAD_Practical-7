package com.example.a24012011115_mad_practical_7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PersonAdapter(
    val personList: ArrayList<Person>,
    private val onDeleteClickListener: ((Person, Int) -> Unit)? = null
) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    constructor(
        personList: ArrayList<Person>,
        onDeleteClick: (Person) -> Unit
    ) : this(personList, { person, _ -> onDeleteClick(person) })

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PersonViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_peron, parent, false)
        return PersonViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: PersonViewHolder,
        position: Int
    ) {
        val person = personList[position]
        holder.tvName.text = person.name
        holder.tvPhone.text = person.phoneNo
        holder.tvEmail.text = person.emailId
        holder.tvAddress.text = person.address

        holder.btnDelete.setOnClickListener {
            val pos = holder.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                onDeleteClickListener?.invoke(person, pos)
            }
        }
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.textName)
        val tvPhone: TextView = itemView.findViewById(R.id.textPhone)
        val tvEmail: TextView = itemView.findViewById(R.id.textEmail)
        val tvAddress: TextView = itemView.findViewById(R.id.textAddress)
        val btnDelete: ImageButton = itemView.findViewById(R.id.buttonDelete)
    }
}
