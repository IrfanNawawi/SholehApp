package id.heycoding.sholehapp.persentation.alquran

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.heycoding.sholehapp.R
import id.heycoding.sholehapp.databinding.FragmentAlquranBinding
import id.heycoding.sholehapp.domain.model.alquran.Surah
import id.heycoding.sholehapp.persentation.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AlquranFragment : Fragment(), AlquranCallback {

    private var _fragmentAlquranBinding: FragmentAlquranBinding? = null
    private val fragmentAlquranBinding get() = _fragmentAlquranBinding
    private val alquranViewModel by viewModels<AlquranViewModel>()
    private lateinit var alquranAdapter: AlquranAdapter
    private val listSurahAlquranData = arrayListOf<Surah>()
    private var valueRepeat = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentAlquranBinding = FragmentAlquranBinding.inflate(layoutInflater, container, false)

        alquranViewModel.getAllSurah()

        setupObserve()
        setupUI()
        return fragmentAlquranBinding?.root
    }

    private fun setupObserve() {
        CoroutineScope(Dispatchers.Main).launch {
            repeat(valueRepeat) {
                alquranViewModel.listSurahData.collect { value ->
                    when {
                        value.isLoading -> {
                            fragmentAlquranBinding?.shimmerRvAlquran?.startShimmer()
                            fragmentAlquranBinding?.shimmerRvAlquran?.visibility = View.VISIBLE
                        }
                        value.error.isNotBlank() -> {
                            fragmentAlquranBinding?.shimmerRvAlquran?.stopShimmer()
                            fragmentAlquranBinding?.shimmerRvAlquran?.visibility = View.GONE

                            valueRepeat = 0
                            Toast.makeText(requireContext(), value.error, Toast.LENGTH_LONG).show()
                        }
                        value.alquranList.isNotEmpty() -> {
                            fragmentAlquranBinding?.shimmerRvAlquran?.stopShimmer()
                            fragmentAlquranBinding?.shimmerRvAlquran?.visibility = View.GONE

                            valueRepeat = 0
                            listSurahAlquranData.clear()
                            listSurahAlquranData.addAll(value.alquranList)
                            alquranAdapter.notifyDataSetChanged()
                            alquranAdapter.setOnSurahAlquran(listSurahAlquranData)
                        }
                    }
                    delay(1000)
                }
            }
        }
    }

    private fun setupUI() {
        alquranAdapter = AlquranAdapter(ArrayList(), this)

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

    override fun onDetailSurahAlquran(surah: Surah) {
        val bundle = Bundle()
        bundle.putParcelable("data_alquran", surah)
        findNavController().navigate(R.id.action_alquranFragment_to_detailAlquranFragment, bundle)
    }
}
