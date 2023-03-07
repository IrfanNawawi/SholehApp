package id.heycoding.sholehapp.persentation.alquran

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.heycoding.sholehapp.databinding.FragmentAlquranBinding
import id.heycoding.sholehapp.domain.model.Surah
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AlquranFragment : Fragment() {

    private var _fragmentAlquranBinding: FragmentAlquranBinding? = null
    private val fragmentAlquranBinding get() = _fragmentAlquranBinding
    private val alquranViewModel by viewModels<AlquranViewModel>()
    private lateinit var alquranAdapter: AlquranAdapter
    private val listAlquranData = arrayListOf<Surah>()
    private var valueRepeat = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentAlquranBinding = FragmentAlquranBinding.inflate(layoutInflater, container, false)

        setupObserve()
        setupUI()
        return fragmentAlquranBinding?.root
    }

    private fun setupObserve() {
        alquranViewModel.getAllSurah()

        CoroutineScope(Dispatchers.Main).launch {
            repeat(valueRepeat) {
                alquranViewModel._listSurahData.collect { value ->
                    when {
                        value.isLoading -> {
//                            progressBar.visibility = View.VISIBLE
                        }
                        value.error.isNotBlank() -> {
//                            progressBar.visibility = View.GONE
                            valueRepeat = 0
                            Toast.makeText(requireContext(), value.error, Toast.LENGTH_LONG).show()
                        }
                        value.alquranList.isNotEmpty() -> {
//                            progressBar.visibility = View.GONE
                            valueRepeat = 0
                            listAlquranData.addAll(value.alquranList)
                            alquranAdapter.setOnSurahAlquran(listAlquranData)
                        }
                    }
                    delay(1000)
                }
            }
        }
    }

    private fun setupUI() {
        alquranAdapter = AlquranAdapter(ArrayList())

        fragmentAlquranBinding?.apply {
            rvSurahAlquran.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = alquranAdapter
                clipToPadding = false
                clipChildren = false
            }
        }
    }
}
