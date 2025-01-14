package com.damolks.ouxynext.ui.debug

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.damolks.ouxynext.data.ModuleInfo
import com.damolks.ouxynext.databinding.ItemModuleBinding

class ModuleAdapter(
    private var modules: List<ModuleInfo>,
    private val onModuleClick: (ModuleInfo) -> Unit
) : RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder>() {

    fun updateModules(newModules: List<ModuleInfo>) {
        modules = newModules
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        val binding = ItemModuleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ModuleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        holder.bind(modules[position])
    }

    override fun getItemCount(): Int = modules.size

    inner class ModuleViewHolder(private val binding: ItemModuleBinding) : 
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onModuleClick(modules[position])
                }
            }
        }

        fun bind(module: ModuleInfo) {
            binding.apply {
                moduleNameTextView.text = module.name
                moduleVersionTextView.text = module.version
                moduleStatusTextView.text = if (module.isActive) "Actif" else "Inactif"
            }
        }
    }
}