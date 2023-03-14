package id.heycoding.sholehapp.persentation.sholat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.heycoding.sholehapp.databinding.ItemJadwalSholatBinding
import id.heycoding.sholehapp.domain.model.sholat.JadwalSholat

class SholatAdapter(var listJadwalSholat: ArrayList<JadwalSholat>) :
    RecyclerView.Adapter<SholatAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemJadwalSholatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sholat: JadwalSholat) {
            binding.apply {
                tvJamShubuhSholat.text = sholat.subuh
                tvJamSyuruqSholat.text = sholat.terbit
                tvJamDhuhaSholat.text = sholat.dhuha
                tvJamZuhurSholat.text = sholat.dzuhur
                tvJamAsharSholat.text = sholat.ashar
                tvJamMagribSholat.text = sholat.maghrib
                tvJamIsyaSholat.text = sholat.isya
                tvJamImsyakSholat.text = sholat.imsak
            }
        }
    }

    fun setOnJadwalSholat(sholat: List<JadwalSholat>) {
        listJadwalSholat.clear()
        listJadwalSholat.addAll(sholat)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemJadwalSholatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listJadwalSholat[position])
    }

    override fun getItemCount(): Int = listJadwalSholat.size
}