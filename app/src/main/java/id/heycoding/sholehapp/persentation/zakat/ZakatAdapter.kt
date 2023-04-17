package id.heycoding.sholehapp.persentation.zakat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.sholehapp.R
import id.heycoding.sholehapp.databinding.ItemZakatBinding
import id.heycoding.sholehapp.domain.model.zakat.Zakat
import id.heycoding.sholehapp.utils.Helper

class ZakatAdapter : RecyclerView.Adapter<ZakatAdapter.ViewHolder>() {
    private val listZakatPayment = ArrayList<Zakat>()

    inner class ViewHolder(private val binding: ItemZakatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(zakat: Zakat) {
            binding.apply {
                when (zakat.typeZakat) {
                    "Beras" -> {
                        Glide.with(itemView.context).load(R.drawable.ic_rice).into(imgZakatType)
                        tvZakatTotal.text = Helper.riceFormat(zakat.berasZakat.toDouble())
                    }
                    "Uang" -> {
                        Glide.with(itemView.context).load(R.drawable.ic_money).into(imgZakatType)
                        tvZakatTotal.text = Helper.rupiahFormat(zakat.uangZakat.toInt())
                    }
                }

                tvZakatName.text = zakat.nama
                tvZakatDate.text = Helper.dateFormat(zakat.createdDate).toString()
                tvZakatPerson.text = zakat.jumlahJiwa.plus(" Jiwa")
            }
        }
    }

    fun setOnPaymentListZakat(zakat: List<Zakat>) {
        listZakatPayment.clear()
        listZakatPayment.addAll(zakat)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemZakatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listZakatPayment[position])
    }

    override fun getItemCount(): Int = listZakatPayment.size
}