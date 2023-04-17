package id.heycoding.sholehapp.persentation.sholat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.heycoding.sholehapp.databinding.ItemJadwalSholatBinding
import id.heycoding.sholehapp.domain.model.sholat.PrayerSchedule

class SholatAdapter : RecyclerView.Adapter<SholatAdapter.ViewHolder>() {
    private val listPrayerSchedule = ArrayList<PrayerSchedule>()

    inner class ViewHolder(private val binding: ItemJadwalSholatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sholat: PrayerSchedule) {
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

    fun setOnJadwalSholat(sholat: List<PrayerSchedule>) {
        listPrayerSchedule.clear()
        listPrayerSchedule.addAll(sholat)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemJadwalSholatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPrayerSchedule[position])
    }

    override fun getItemCount(): Int = listPrayerSchedule.size
}