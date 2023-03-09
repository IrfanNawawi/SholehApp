package id.heycoding.sholehapp.persentation.alquran.detailalquran

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.heycoding.sholehapp.R
import id.heycoding.sholehapp.databinding.FragmentDetailAlquranBinding
import id.heycoding.sholehapp.domain.model.Ayat
import id.heycoding.sholehapp.domain.model.Surah
import id.heycoding.sholehapp.persentation.alquran.AlquranAdapter
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
    private val listAyatAlquranData = arrayListOf<Ayat>()
    private var valueRepeat = 3
    lateinit var numberSurah: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentDetailAlquranBinding = FragmentDetailAlquranBinding.inflate(layoutInflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

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
                tvTitleToolbar.text = dataAlquran?.nama
                tvTitleSurah.text = dataAlquran?.nama
                tvArtiSurah.text = dataAlquran?.arti
                tvInfoSurah.text = "${dataAlquran?.type} - ${dataAlquran?.ayat} Ayat"

                val mediaPlayer = MediaPlayer()

                fabPlayAlquran.setOnClickListener {
                    try {
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                        mediaPlayer.setDataSource(dataAlquran?.audio)
                        mediaPlayer.prepare()
                        mediaPlayer.start()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    fabPlayAlquran.visibility = View.GONE
                    fabStopAlquran.visibility = View.VISIBLE
                }

                fabStopAlquran.setOnClickListener {
                    mediaPlayer.stop()
                    mediaPlayer.reset()
                    fabPlayAlquran.visibility = View.VISIBLE
                    fabStopAlquran.visibility = View.GONE
                }

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
//                            progressBar.visibility = View.VISIBLE
                        }
                        value.error.isNotBlank() -> {
//                            progressBar.visibility = View.GONE
                            valueRepeat = 0
                            Toast.makeText(requireContext(), value.error, Toast.LENGTH_LONG).show()
                        }
                        value.detailAlquranList.isNotEmpty() -> {
//                            progressBar.visibility = View.GONE
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