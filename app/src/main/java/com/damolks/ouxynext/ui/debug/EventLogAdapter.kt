package com.damolks.ouxynext.ui.debug

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.damolks.ouxynext.core.data.entities.EventLogEntity
import com.damolks.ouxynext.databinding.ItemEventLogBinding
import java.text.SimpleDateFormat
import java.util.Locale

class EventLogAdapter : ListAdapter<EventLogEntity, EventLogAdapter.EventLogViewHolder>(
    object : DiffUtil.ItemCallback<EventLogEntity>() {
        override fun areItemsTheSame(oldItem: EventLogEntity, newItem: EventLogEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EventLogEntity, newItem: EventLogEntity): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventLogViewHolder {
        return EventLogViewHolder(
            ItemEventLogBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EventLogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EventLogViewHolder(private val binding: ItemEventLogBinding) : 
        RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        fun bind(event: EventLogEntity) {
            binding.apply {
                moduleIdTextView.text = event.moduleId
                eventTypeTextView.text = event.eventType
                timestampTextView.text = dateFormat.format(event.timestamp)
                if (!event.data.isNullOrEmpty()) {
                    dataTextView.text = event.data.toString()
                }
            }
        }
    }
}