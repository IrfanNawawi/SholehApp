package id.heycoding.sholehapp.persentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.heycoding.sholehapp.R
import id.heycoding.sholehapp.databinding.FragmentHomeBinding
import id.heycoding.sholehapp.persentation.home.kajiannew.KajianNewAdapter
import id.heycoding.sholehapp.persentation.home.mainmenu.MainMenuAdapter


@AndroidEntryPoint
class HomeFragment : Fragment(), HomeCallback {

    private var _fragmentHomeBinding: FragmentHomeBinding? = null
    private val fragmentHomeBinding get() = _fragmentHomeBinding
    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var mainMenuAdapter: MainMenuAdapter
    private lateinit var kajianNewAdapter: KajianNewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        mainMenuAdapter = MainMenuAdapter(this)
        kajianNewAdapter = KajianNewAdapter()

        setupObserve()
        setupUI()
        return fragmentHomeBinding?.root
    }

    private fun setupUI() {
        fragmentHomeBinding?.apply {
            rvMenuMain.apply {
                layoutManager =
                    GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = mainMenuAdapter
                clipToPadding = false
                clipChildren = false
            }
            rvKajianNew.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = kajianNewAdapter
                clipToPadding = false
                clipChildren = false
                val snapHelper: SnapHelper = LinearSnapHelper()
                snapHelper.attachToRecyclerView(rvKajianNew)
            }
        }
    }

    private fun setupObserve() {
        homeViewModel.apply {
            val mainMenu = onMainMenu()
            val kajianNew = onKajianNew()
            mainMenuAdapter.setOnMainMenu(mainMenu)
            kajianNewAdapter.setOnKajianNew(kajianNew)
        }
    }

    override fun onNavigateMainMenu(idMenu: Int) {
        when (idMenu) {
            1 -> onNavigateAlquran()
            2 -> onNavigateSholat()
            3 -> onNavigateKiblat()
            4 -> onNavigatePuasa()
            5 -> onNavigateZakat()
            else -> onNavigateQurban()
        }
    }

    private fun onNavigateAlquran() {
        findNavController().navigate(R.id.action_homeFragment_to_alquranFragment)
    }

    private fun onNavigateSholat() {
        findNavController().navigate(R.id.action_homeFragment_to_sholatFragment)
    }

    private fun onNavigateKiblat() {
        findNavController().navigate(R.id.action_homeFragment_to_kiblatFragment)
    }

    private fun onNavigatePuasa() {
        findNavController().navigate(R.id.action_homeFragment_to_puasaFragment)
    }

    private fun onNavigateZakat() {
        findNavController().navigate(R.id.action_homeFragment_to_zakatFragment)
    }

    private fun onNavigateQurban() {
        findNavController().navigate(R.id.action_homeFragment_to_qurbanFragment)
    }

    private fun ShowMessage() {
        Snackbar.make(
            requireActivity().findViewById(R.id.coordinator_main),
            "On Development",
            Snackbar.LENGTH_SHORT
        ).show()
    }
}