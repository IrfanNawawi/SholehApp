package id.heycoding.sholehapp.persentation.zakat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.heycoding.sholehapp.R
import id.heycoding.sholehapp.databinding.PopupInputZakatBinding
import id.heycoding.sholehapp.domain.model.zakat.Zakat
import id.heycoding.sholehapp.utils.Helper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ZakatInputBottomSheet : BottomSheetDialogFragment() {
    private var _popupInputZakatBinding: PopupInputZakatBinding? = null
    private val popupInputZakatBinding get() = _popupInputZakatBinding
    private val zakatViewModel by viewModels<ZakatViewModel>()
    private val itemRT = (1..7).toList().toTypedArray()
    private val itemJumlah = (1..10).toList().toTypedArray()
    private val itemZakat = arrayOf("Beras", "Uang")
    private var zakatInsertData: MutableList<Zakat> = mutableListOf()

    private lateinit var dataRT: String
    private lateinit var dataJumlah: String
    private lateinit var dataZakat: String
    private lateinit var zakatBeras: String
    private lateinit var zakatUang: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popupInputZakatBinding?.tvSaveZakat?.setOnClickListener {
            saveAction()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _popupInputZakatBinding = PopupInputZakatBinding.inflate(inflater, container, false)
        return popupInputZakatBinding?.root
    }

    private fun saveAction() {
        popupInputZakatBinding?.apply {
            val ofcListener: View.OnFocusChangeListener = MyFocusChangeListener()

            edtName.onFocusChangeListener = ofcListener
            edtInfak.onFocusChangeListener = ofcListener

            val adapterRT = ArrayAdapter(
                requireContext(),
                R.layout.item_list_city,
                itemRT
            )
            autoRt.setAdapter(adapterRT)
            autoRt.setOnItemClickListener { _, _, positionRT, _ ->
                dataRT = itemRT[positionRT].toString()
            }

            val adapterJiwa = ArrayAdapter(
                requireContext(),
                R.layout.item_list_city,
                itemJumlah
            )
            autoSum.setAdapter(adapterJiwa)
            autoSum.setOnItemClickListener { _, _, positionJiwa, _ ->
                dataJumlah = itemJumlah[positionJiwa].toString()
            }

            val adapterZakat = ArrayAdapter(
                requireContext(),
                R.layout.item_list_city,
                itemZakat
            )
            autoZakat.setAdapter(adapterZakat)
            autoZakat.setOnItemClickListener { _, _, positionZakat, _ ->
                dataZakat = itemZakat[positionZakat]

                if (dataZakat == "Beras") {
                    val sumRice = 3.5 * dataJumlah.toDouble()
                    edtRice.setText(Helper.riceFormat(sumRice))
                    edtMoney.setText(Helper.rupiahFormat(0))

                    zakatBeras = sumRice.toString()
                    zakatUang = "0"
                } else {
                    val sumMoney = 45000 * dataJumlah.toInt()
                    edtMoney.setText(Helper.rupiahFormat(sumMoney))
                    edtRice.setText(Helper.riceFormat(0.0))

                    zakatUang = sumMoney.toString()
                    zakatBeras = "0"
                }
            }

            tvSaveZakat.setOnClickListener {
                val username = edtName.text.toString().trim()
                val rt = autoRt.text.toString().trim()
                val sum = autoSum.text.toString().trim()
                val zakat = autoZakat.text.toString().trim()
                val rice = edtRice.text.toString().trim()
                val money = edtMoney.text.toString().trim()
                val infak = edtInfak.text.toString().trim()

                if (username.isEmpty()) {
                    edtName.error = context?.getString(R.string.txt_column_not_blank)
                    edtName.requestFocus()
                    return@setOnClickListener
                }

                if (rt.isEmpty()) {
                    autoRt.error = context?.getString(R.string.txt_column_not_blank)
                    autoRt.requestFocus()
                    return@setOnClickListener
                }

                if (sum.isEmpty()) {
                    autoSum.error = context?.getString(R.string.txt_column_not_blank)
                    autoSum.requestFocus()
                    return@setOnClickListener
                }

                if (zakat.isEmpty()) {
                    autoZakat.error = context?.getString(R.string.txt_column_not_blank)
                    autoZakat.requestFocus()
                    return@setOnClickListener
                }

                if (rice.isEmpty()) {
                    edtRice.error = context?.getString(R.string.txt_column_not_blank)
                    edtRice.requestFocus()
                    return@setOnClickListener
                }

                if (money.isEmpty()) {
                    edtMoney.error = context?.getString(R.string.txt_column_not_blank)
                    edtMoney.requestFocus()
                    return@setOnClickListener
                }

                if (infak.isEmpty()) {
                    edtInfak.error = context?.getString(R.string.txt_column_not_blank)
                    edtInfak.requestFocus()
                    return@setOnClickListener
                }

                zakatInsertData.add(
                    Zakat(
                        id = 0,
                        nama = username,
                        rt = rt,
                        typeZakat = zakat,
                        jumlahJiwa = sum,
                        berasZakat = zakatBeras,
                        uangZakat = zakatUang,
                        infak = infak,
                        createdDate = Helper.getCurrentDate()
                    )
                )

                zakatViewModel.apply {
                    insertPaymentZakat(zakatInsertData)
                }

                dismiss()
            }
        }
    }

    private fun setupObserve() {
        zakatViewModel.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        inputZakatData.collect { value ->
                            when {
                                value.isLoading -> {

                                }
                                value.error.isNotBlank() -> {
                                    Toast.makeText(
                                        requireContext(),
                                        value.error,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                value.zakatPaymentInsert.isNotBlank() -> {
                                    Toast.makeText(
                                        requireContext(),
                                        value.zakatPaymentInsert,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            delay(1000)
                        }
                    }
                }
            }
        }
    }

    private class MyFocusChangeListener : View.OnFocusChangeListener {
        override fun onFocusChange(v: View, hasFocus: Boolean) {
            val imm =
                v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(v.windowToken, 0)

        }
    }

}