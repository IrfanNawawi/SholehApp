package id.heycoding.sholehapp.persentation.alquran.detailalquran

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.heycoding.sholehapp.databinding.FragmentDetailAlquranBinding
import id.heycoding.sholehapp.domain.model.alquran.Ayat
import id.heycoding.sholehapp.domain.model.alquran.Surah
import id.heycoding.sholehapp.persentation.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

@AndroidEntryPoint
class DetailAlquranFragment : Fragment() {

    private var _fragmentDetailAlquranBinding: FragmentDetailAlquranBinding? = null
    private val fragmentDetailAlquranBinding get() = _fragmentDetailAlquranBinding
    private val detailAlquranViewModel by viewModels<DetailAlquranViewModel>()
    private lateinit var detailAlquranAdapter: DetailAlquranAdapter
    private val listAyatAlquranData: MutableList<Ayat> = mutableListOf()
    private var valueRepeat = 3
    lateinit var numberSurah: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentDetailAlquranBinding = FragmentDetailAlquranBinding.inflate(layoutInflater, container, false)

        setupUI()
        setupObserve()
        return fragmentDetailAlquranBinding?.root
    }

    private fun setupUI() {
        detailAlquranAdapter = DetailAlquranAdapter(ArrayList())

        val bundle = this.arguments
        if (bundle != null) {
            val dataAlquran = bundle.getParcelable<Surah>("data_alquran")

            fragmentDetailAlquranBinding?.apply {
                numberSurah = dataAlquran?.nomor.toString()
                tvTitleSurah.text = dataAlquran?.nama
                tvArtiSurah.text = dataAlquran?.arti
                tvInfoSurah.text = "${dataAlquran?.type} - ${dataAlquran?.ayat} Ayat"

                rvAyatAlquran.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = detailAlquranAdapter
                    clipToPadding = false
                    clipChildren = false
                }
            }
        }
    }

    private fun setupObserve() {
        detailAlquranViewModel.getAllAyat(numberSurah)

        CoroutineScope(Dispatchers.Main).launch {
            repeat(valueRepeat) {
                detailAlquranViewModel.listAyatData.collect { value ->
                    when {
                        value.isLoading -> {
                            fragmentDetailAlquranBinding?.shimmerRvAyatAlquran?.startShimmer()
                            fragmentDetailAlquranBinding?.shimmerRvAyatAlquran?.visibility = View.VISIBLE
                        }
                        value.error.isNotBlank() -> {
                            fragmentDetailAlquranBinding?.shimmerRvAyatAlquran?.stopShimmer()
                            fragmentDetailAlquranBinding?.shimmerRvAyatAlquran?.visibility = View.GONE

                            valueRepeat = 0
                            Toast.makeText(requireContext(), value.error, Toast.LENGTH_LONG).show()
                        }
                        value.detailAlquranList.isNotEmpty() -> {
                            fragmentDetailAlquranBinding?.shimmerRvAyatAlquran?.stopShimmer()
                            fragmentDetailAlquranBinding?.shimmerRvAyatAlquran?.visibility = View.GONE

                            valueRepeat = 0
                            listAyatAlquranData.addAll(value.detailAlquranList)
                            detailAlquranAdapter.setOnAyatAlquran(listAyatAlquranData)
                        }
                    }
                    delay(1000)
                }
            }
        }
    }
}