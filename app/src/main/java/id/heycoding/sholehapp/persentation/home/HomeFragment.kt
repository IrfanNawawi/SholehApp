package id.heycoding.sholehapp.persentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.heycoding.sholehapp.R
import id.heycoding.sholehapp.databinding.FragmentHomeBinding
import id.heycoding.sholehapp.persentation.home.mainmenu.MainMenuAdapter


@AndroidEntryPoint
class HomeFragment : Fragment(), HomeCallback {

    private var _fragmentHomeBinding: FragmentHomeBinding? = null
    private val fragmentHomeBinding get() = _fragmentHomeBinding
    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var mainMenuAdapter: MainMenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        mainMenuAdapter = MainMenuAdapter(this)

        return fragmentHomeBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserve()
        setupUI()
    }

    private fun setupUI() {
        fragmentHomeBinding?.apply {
            rvMenuMain.apply {
                layoutManager =
                    GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = mainMenuAdapter
                clipToPadding = false
                clipChildren = false
            }
        }
    }

    private fun setupObserve() {
        homeViewModel.apply {
            val mainMenu = onMainMenu()
            mainMenuAdapter.setOnMainMenu(mainMenu)
        }
    }

    override fun onNavigateMainMenu(idMenu: Int) {
        when (idMenu) {
            1 -> onNavigateAlquran()
            2 -> onNavigateSholat()
            3 -> onNavigateKiblat()
            4 -> onNavigatePuasa()
            5 -> onNavigateZakat()
            6 -> onNavigateQurban()
        }
    }

    private fun onNavigateAlquran() {
        findNavController().navigate(R.id.action_homeFragment_to_alquranFragment)
    }

    private fun onNavigateSholat() {
        findNavController().navigate(R.id.action_homeFragment_to_sholatFragment)
    }

    private fun onNavigateKiblat() {
        ShowMessage()
//        findNavController().navigate(R.id.action_homeFragment_to_kiblatFragment)
    }

    private fun onNavigatePuasa() {
        ShowMessage()
//        findNavController().navigate(R.id.action_homeFragment_to_puasaFragment)
    }

    private fun onNavigateZakat() {
        findNavController().navigate(R.id.action_homeFragment_to_zakatFragment)
    }

    private fun onNavigateQurban() {
        ShowMessage()
//        findNavController().navigate(R.id.action_homeFragment_to_qurbanFragment)
    }

    private fun ShowMessage() {
        Snackbar.make(
            requireActivity().findViewById(R.id.coordinator_main),
            "On Development",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentHomeBinding = null
    }
}