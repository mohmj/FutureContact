package com.example.futurecontact.Adapters

import com.example.futurecontact.Models.UserContactId
import com.example.futurecontact.Models.UserInformation
import com.example.futurecontact.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.contact_show.view.*

class ContactShowAdapter (var user:UserInformation): Item<ViewHolder>() {
    override fun getLayout(): Int {
       return R.layout.contact_show
    }
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.contact_show_uid_text_view.text=user.uid
        viewHolder.itemView.contact_show_email_text_view.text=user.email
        viewHolder.itemView.contact_show_name_text_view.text=user.name
    }
}