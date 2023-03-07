package id.heycoding.sholehapp.persentation.home.kajiannew

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.sholehapp.data.source.entity.model.KajianNew
import id.heycoding.sholehapp.databinding.ItemKajianNewBinding

class KajianNewAdapter : RecyclerView.Adapter<KajianNewAdapter.ViewHolder>() {
    private val listKajianNew = ArrayList<KajianNew>()

    inner class ViewHolder(private val binding: ItemKajianNewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(kajianNew: KajianNew) {
            binding.apply {
                Glide.with(itemView.context).load(kajianNew.imageKajian).into(imgKajianHome)
                tvTitleKajianHome.text = kajianNew.titleKajian
                tvDateKajianHome.text = kajianNew.timeKajian
                tvSourceKajianHome.text = kajianNew.sourceKajian
            }
        }
    }

    fun setOnKajianNew(kajianNew: List<KajianNew>) {
        listKajianNew.clear()
        listKajianNew.addAll(kajianNew)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemKajianNewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listKajianNew[position])
    }

    override fun getItemCount(): Int = listKajianNew.size
}