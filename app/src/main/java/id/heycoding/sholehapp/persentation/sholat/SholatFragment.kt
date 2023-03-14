package id.heycoding.sholehapp.persentation.sholat

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.heycoding.sholehapp.databinding.FragmentSholatBinding
import id.heycoding.sholehapp.domain.model.alquran.Surah
import id.heycoding.sholehapp.domain.model.sholat.JadwalSholat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class SholatFragment : Fragment() {

    private var _fragmentSholatBinding: FragmentSholatBinding? = null
    private val fragmentSholatBinding get() = _fragmentSholatBinding
    private val sholatViewModel by viewModels<SholatViewModel>()
    private val listIdKota: MutableList<String> = mutableListOf()
    private val listKota: MutableList<String> = mutableListOf()
    val calendarSholat: Calendar = Calendar.getInstance()
    private lateinit var sholatAdapter: SholatAdapter
    private val listJadwalSholatData = arrayListOf<JadwalSholat>()
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
            spinnerKota.isEnabled = false
            spinnerKota.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    parent?.getItemAtPosition(position).toString()
                    if (position > 0) {
                        sholatViewModel.getAllJadwalSholat(
                            listIdKota[position],
                            fragmentSholatBinding?.edtTanggalSholat?.text.toString()
                        )
                    }
                }
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

                            val adapterSeason = ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                listKota
                            )
                            adapterSeason.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            fragmentSholatBinding?.spinnerKota?.adapter = adapterSeason
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
        val myFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        fragmentSholatBinding?.edtTanggalSholat?.setText(dateFormat.format(calendarSholat.time))

        if (fragmentSholatBinding?.edtTanggalSholat?.text.toString().isNotEmpty()) {
            fragmentSholatBinding?.spinnerKota?.isEnabled = true
            sholatViewModel.getAllKota()
        } else {
            showMessage("Silahkan isi dulu tanggal")
        }
    }
}