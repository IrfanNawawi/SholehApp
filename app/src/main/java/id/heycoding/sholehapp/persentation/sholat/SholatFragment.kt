package id.heycoding.sholehapp.persentation.sholat

import android.app.DatePickerDialog
import android.os.Bundle
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
import id.heycoding.sholehapp.domain.model.sholat.PrayerSchedule
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
    private lateinit var sholatAdapter: SholatAdapter
    private val listIdKota: MutableList<String> = mutableListOf()
    private val listKota: MutableList<String> = mutableListOf()
    private var listJadwalData: MutableList<PrayerSchedule> = mutableListOf()
    private val calendarSholat: Calendar = Calendar.getInstance()
    private var valueRepeat = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentSholatBinding = FragmentSholatBinding.inflate(layoutInflater, container, false)

        sholatAdapter = SholatAdapter()

        return fragmentSholatBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObserve()
    }

    private fun setupUI() {
        fragmentSholatBinding?.apply {
            autoCity.isEnabled = false
            autoCity.setOnItemClickListener { _, _, position, _ ->
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

    private fun setupObserve() {
        sholatViewModel.apply {
            CoroutineScope(Dispatchers.Main).launch {
                repeat(valueRepeat) {
                    listJadwalSholatData.collect { value ->
                        when {
                            value.isLoading -> {
                                fragmentSholatBinding?.shimmerRvSholat?.startShimmer()
                                fragmentSholatBinding?.fmJadwalSholat?.visibility = View.GONE
                                fragmentSholatBinding?.shimmerRvSholat?.visibility = View.VISIBLE
                            }
                            value.error.isNotBlank() -> {
                                fragmentSholatBinding?.shimmerRvSholat?.stopShimmer()
                                fragmentSholatBinding?.fmJadwalSholat?.visibility = View.VISIBLE
                                fragmentSholatBinding?.shimmerRvSholat?.visibility = View.GONE

                                valueRepeat = 0
                                Toast.makeText(requireContext(), value.error, Toast.LENGTH_SHORT)
                                    .show()
                            }
                            value.cityPrayerList.isNotEmpty() -> {
                                fragmentSholatBinding?.shimmerRvSholat?.stopShimmer()
                                fragmentSholatBinding?.fmJadwalSholat?.visibility = View.VISIBLE
                                fragmentSholatBinding?.shimmerRvSholat?.visibility = View.GONE

                                valueRepeat = 0

                                listIdKota.clear()
                                listKota.clear()
                                value.cityPrayerList.map {
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
                            value.prayerScheduleList.isNotEmpty() -> {
                                fragmentSholatBinding?.shimmerRvSholat?.stopShimmer()
                                fragmentSholatBinding?.fmJadwalSholat?.visibility = View.VISIBLE
                                fragmentSholatBinding?.imgNoData?.visibility = View.GONE
                                fragmentSholatBinding?.shimmerRvSholat?.visibility = View.GONE

                                valueRepeat = 0
                                listJadwalData.clear()
                                listJadwalData.addAll(value.prayerScheduleList)
                                sholatAdapter.setOnJadwalSholat(listJadwalData)
                            }
                        }
                        delay(1000)
                    }
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
            fragmentSholatBinding?.layoutAutocity?.visibility = View.VISIBLE
            sholatViewModel.getAllKota()
        } else {
            showMessage("Silahkan isi dulu tanggal")
        }
    }

    private fun showMessage(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentSholatBinding = null
    }
}