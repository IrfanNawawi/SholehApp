package id.heycoding.sholehapp.persentation.alquran

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.heycoding.sholehapp.databinding.ItemSurahAlquranBinding
import id.heycoding.sholehapp.domain.model.Surah

class AlquranAdapter(var listAlquran: ArrayList<Surah>) : RecyclerView.Adapter<AlquranAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemSurahAlquranBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(surah: Surah) {
            binding.apply {
                tvNumber.text = surah.nomor
                tvSurahLatin.text = surah.nama
                tvSurahArab.text = surah.asma
                tvInfo.text = "${surah.type} - ${surah.ayat} Ayat"

                itemView.setOnClickListener {

                }
            }
        }
    }

    fun setOnSurahAlquran(surah: List<Surah>) {
        listAlquran.clear()
        listAlquran.addAll(surah)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSurahAlquranBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listAlquran[position])
    }

    override fun getItemCount(): Int = listAlquran.size
}