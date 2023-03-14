package id.heycoding.sholehapp.persentation.alquran.detailalquran

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.heycoding.sholehapp.databinding.ItemAyatAlquranBinding
import id.heycoding.sholehapp.domain.model.alquran.Ayat

class DetailAlquranAdapter(var listAyatAlquran: ArrayList<Ayat>) :
    RecyclerView.Adapter<DetailAlquranAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemAyatAlquranBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ayat: Ayat) {
            binding.apply {
                tvArabAyat.text = ayat.ar
                tvNumberAyat.text = ayat.nomor
                tvTranslateAyat.text = ayat.id
            }
        }
    }

    fun setOnAyatAlquran(ayat: List<Ayat>) {
        listAyatAlquran.clear()
        listAyatAlquran.addAll(ayat)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAyatAlquranBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listAyatAlquran[position])
    }

    override fun getItemCount(): Int = listAyatAlquran.size
}