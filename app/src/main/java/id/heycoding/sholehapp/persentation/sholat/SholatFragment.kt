package id.heycoding.sholehapp.persentation.sholat

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.heycoding.sholehapp.R
import id.heycoding.sholehapp.databinding.FragmentSholatBinding
import id.heycoding.sholehapp.domain.model.sholat.JadwalSholat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class SholatFragment : Fragment() {

    private var _fragmentSholatBinding: FragmentSholatBinding? = null
    private val fragmentSholatBinding get() = _fragmentSholatBinding
    private val sholatViewModel by viewModels<SholatViewModel>()
    private val listIdKota: MutableList<String> = mutableListOf()
    private val listKota: MutableList<String> = mutableListOf()
    private val listJadwalSholatData = arrayListOf<JadwalSholat>()
    private val calendarSholat: Calendar = Calendar.getInstance()
    private lateinit var sholatAdapter: SholatAdapter
    private var valueRepeat = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentSholatBinding = FragmentSholatBinding.inflate(layoutInflater, container, false)

        setupUI()
        setupObserve()
        return fragmentSholatBinding?.root
    }

    private fun setupUI() {
        sholatAdapter = SholatAdapter(ArrayList())

        fragmentSholatBinding?.apply {
            autoCity.isEnabled = false
            Log.d("dapet coy 2", fragmentSholatBinding?.tvTanggalSholatHidden?.text.toString())
            autoCity.setOnItemClickListener { parent, view, position, id ->
                sholatViewModel.getAllJadwalSholat(
                    listIdKota[position],
                    fragmentSholatBinding?.tvTanggalSholatHidden?.text.toString()
                )

            }

            val dateSetListener =
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    calendarSholat.set(Calendar.YEAR, year)
                    calendarSholat.set(Calendar.MONTH, monthOfYear)
                    calendarSholat.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateLabel()
                }

            edtTanggalSholat.setOnClickListener {
                DatePickerDialog(
                    requireContext(), dateSetListener,
                    calendarSholat.get(Calendar.YEAR),
                    calendarSholat.get(Calendar.MONTH),
                    calendarSholat.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            rvJadwalSholat.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = sholatAdapter
                clipToPadding = false
                clipChildren = false
            }
        }
    }

    private fun showMessage(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }

    private fun setupObserve() {
        CoroutineScope(Dispatchers.Main).launch {
            repeat(valueRepeat) {
                sholatViewModel.listJadwalSholatData.collect { value ->
                    when {
                        value.isLoading -> {
                            fragmentSholatBinding?.shimmerRvSholat?.startShimmer()
                            fragmentSholatBinding?.shimmerRvSholat?.visibility = View.VISIBLE
                        }
                        value.error.isNotBlank() -> {
                            fragmentSholatBinding?.shimmerRvSholat?.stopShimmer()
                            fragmentSholatBinding?.shimmerRvSholat?.visibility = View.GONE

                            valueRepeat = 0
                            Toast.makeText(requireContext(), value.error, Toast.LENGTH_SHORT).show()
                        }
                        value.kotaSholatList.isNotEmpty() -> {
                            fragmentSholatBinding?.shimmerRvSholat?.stopShimmer()
                            fragmentSholatBinding?.shimmerRvSholat?.visibility = View.GONE

                            valueRepeat = 0

                            listIdKota.clear()
                            listKota.clear()
                            value.kotaSholatList.map {
                                listIdKota.add(it.id)
                                listKota.add(it.nama)
                            }

                            val adapterSholat = ArrayAdapter(
                                requireContext(),
                                R.layout.item_list_city,
                                listKota
                            )
                            fragmentSholatBinding?.autoCity?.setAdapter(adapterSholat)
                        }
                        value.jadwalSholatList.isNotEmpty() -> {
                            fragmentSholatBinding?.shimmerRvSholat?.stopShimmer()
                            fragmentSholatBinding?.shimmerRvSholat?.visibility = View.GONE

                            valueRepeat = 0
                            listJadwalSholatData.clear()
                            listJadwalSholatData.addAll(value.jadwalSholatList)
                            sholatAdapter.notifyDataSetChanged()
                            sholatAdapter.setOnJadwalSholat(listJadwalSholatData)
                        }
                    }
                    delay(1000)
                }
            }
        }
    }

    private fun updateLabel() {
        val formatView = "dd MMMM yyyy"
        val formatFetch = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(formatView, Locale.US)
        val dateFormatFetch = SimpleDateFormat(formatFetch, Locale.US)
        fragmentSholatBinding?.edtTanggalSholat?.setText(dateFormat.format(calendarSholat.time))
        fragmentSholatBinding?.tvTanggalSholatHidden?.text =
            dateFormatFetch.format(calendarSholat.time)

        if (fragmentSholatBinding?.edtTanggalSholat?.text.toString().isNotEmpty()) {
            fragmentSholatBinding?.autoCity?.isEnabled = true
            sholatViewModel.getAllKota()
            Log.d("dapet coy", fragmentSholatBinding?.tvTanggalSholatHidden?.text.toString())
        } else {
            showMessage("Silahkan isi dulu tanggal")
        }
    }
}