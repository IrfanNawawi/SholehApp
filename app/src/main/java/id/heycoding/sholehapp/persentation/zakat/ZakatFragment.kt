package id.heycoding.sholehapp.persentation.zakat

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import id.heycoding.sholehapp.R
import id.heycoding.sholehapp.databinding.FragmentZakatBinding
import id.heycoding.sholehapp.domain.model.zakat.Zakat
import id.heycoding.sholehapp.utils.Helper
import id.heycoding.sholehapp.utils.Helper.getCurrentDate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class ZakatFragment : Fragment() {

    private var _fragmentZakatBinding: FragmentZakatBinding? = null
    private val fragmentZakatBinding get() = _fragmentZakatBinding
    private val zakatViewModel by viewModels<ZakatViewModel>()
    private lateinit var zakatAdapter: ZakatAdapter

    private val itemRT = (1..7).toList().toTypedArray()
    private val itemJumlah = (1..10).toList().toTypedArray()
    private val itemZakat = arrayOf("Beras", "Uang")
    private var zakatInsertData: MutableList<Zakat> = mutableListOf()
    private var listZakatData: MutableList<Zakat> = mutableListOf()

    private lateinit var dataRT: String
    private lateinit var dataJumlah: String
    private lateinit var dataZakat: String
    private lateinit var zakatBeras: String
    private lateinit var zakatUang: String

    private companion object {
        private const val TAG = "PERMISSION_TAG"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentZakatBinding = FragmentZakatBinding.inflate(layoutInflater, container, false)

        startPermissionRequest()

        zakatAdapter = ZakatAdapter()

        return fragmentZakatBinding?.root
    }

    private fun startPermissionRequest() {
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        requestPermissionLauncher.launch(permissions)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        var allGranted = true
        for (isGranted in result.values) {
            Log.d(TAG, "Request Permission -> isGranted $isGranted")
            allGranted = allGranted && isGranted
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            if (allGranted) {
                Log.d(TAG, "Request Permission Granted")
            } else {
                Log.d(TAG, "Request Permission Denied")
                showMessage("Request Permission Denied")
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        zakatViewModel.apply {
            getAllPaymentZakat()
            getSumJumlahJiwa()
            getSumBerasZakat()
            getSumUangZakat()
            getSumInfakZakat()
        }

        setupUI()
        setupObserve()
    }

    private fun setupUI() {
        fragmentZakatBinding?.apply {
            tvZakatExport.setOnClickListener {
                createXlsx(listZakatData)
            }
            rvZakatPaymentList.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = zakatAdapter
                clipToPadding = false
                clipChildren = false
            }
            imgZakatInput.setOnClickListener {
                val view = layoutInflater.inflate(R.layout.popup_input_zakat, null)
                val dialog = BottomSheetDialog(requireContext()).apply {
                    setContentView(view)
                    behavior.peekHeight = resources.displayMetrics.heightPixels / 1
                    show()
                }

                val ofcListener: OnFocusChangeListener = MyFocusChangeListener()

                val edtUsername: EditText = view.findViewById(R.id.edt_name)
                val autoRT: AutoCompleteTextView = view.findViewById(R.id.auto_rt)
                val autoSum: AutoCompleteTextView = view.findViewById(R.id.auto_sum)
                val autoZakat: AutoCompleteTextView = view.findViewById(R.id.auto_zakat)
                val edtMoney: EditText = view.findViewById(R.id.edt_money)
                val edtRice: EditText = view.findViewById(R.id.edt_rice)
                val edtInfak: EditText = view.findViewById(R.id.edt_infak)
                val tvSaveZakat: TextView = view.findViewById(R.id.tv_save_zakat)

                edtUsername.onFocusChangeListener = ofcListener
                edtInfak.onFocusChangeListener = ofcListener

                val adapterRT = ArrayAdapter(
                    requireContext(),
                    R.layout.item_list_city,
                    itemRT
                )
                autoRT.setAdapter(adapterRT)
                autoRT.setOnItemClickListener { _, _, positionRT, _ ->
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
                    val username = edtUsername.text.toString().trim()
                    val rt = autoRT.text.toString().trim()
                    val sum = autoSum.text.toString().trim()
                    val zakat = autoZakat.text.toString().trim()
                    val rice = edtRice.text.toString().trim()
                    val money = edtMoney.text.toString().trim()
                    val infak = edtInfak.text.toString().trim()

                    if (username.isEmpty()) {
                        edtUsername.error = context?.getString(R.string.txt_column_not_blank)
                        edtUsername.requestFocus()
                        return@setOnClickListener
                    }

                    if (rt.isEmpty()) {
                        autoRT.error = context?.getString(R.string.txt_column_not_blank)
                        autoRT.requestFocus()
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
                            createdDate = getCurrentDate()
                        )
                    )

                    zakatViewModel.apply {
                        insertPaymentZakat(zakatInsertData)
                    }
                    dialog.dismiss()
                }
            }
        }
    }

    private fun setupObserve() {
        zakatViewModel.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        paymentZakatData.collect { value ->
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
                                value.zakatPaymentList.isNotEmpty() -> {
                                    listZakatData.clear()
                                    listZakatData.addAll(value.zakatPaymentList)
                                    zakatAdapter.setOnPaymentListZakat(listZakatData)
                                    fragmentZakatBinding?.tvZakatExport?.visibility = VISIBLE
                                }
                            }
                            delay(1000)
                        }
                    }
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
                    launch {
                        sumJumlahJiwaData.collect { value ->
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
                                value.zakatSumJumlahJiwa.isNotEmpty() -> {
                                    fragmentZakatBinding?.tvZakatJumlahJiwa?.text =
                                        value.zakatSumJumlahJiwa.plus(" Orang")
                                }
                            }
                            delay(1000)
                        }
                    }
                    launch {
                        sumBerasZakatData.collect { value ->
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
                                value.zakatSumBerasZakat.isNotEmpty() -> {
                                    fragmentZakatBinding?.tvZakatRice?.text =
                                        value.zakatSumBerasZakat.plus(" Liter")
                                }
                            }
                            delay(1000)
                        }
                    }
                    launch {
                        sumUangZakatData.collect { value ->
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
                                value.zakatSumUangZakat.isNotEmpty() -> {
                                    fragmentZakatBinding?.tvZakatMoney?.text =
                                        Helper.rupiahFormat(value.zakatSumUangZakat.toInt())
                                }
                            }
                            delay(1000)
                        }
                    }
                    launch {
                        sumInfakZakatData.collect { value ->
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
                                value.zakatSumInfakZakat.isNotEmpty() -> {
                                    fragmentZakatBinding?.tvZakatInfak?.text =
                                        Helper.rupiahFormat(value.zakatSumInfakZakat.toInt())
                                }
                            }
                            delay(1000)
                        }
                    }
                }
            }
        }
    }

    private class MyFocusChangeListener : OnFocusChangeListener {
        override fun onFocusChange(v: View, hasFocus: Boolean) {
            val imm =
                v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(v.windowToken, 0)

        }
    }

    private fun createXlsx(dataZakat: List<Zakat>) {
        try {
            val strDate =
                SimpleDateFormat("dd-MM-yyyy HH-mm-ss", Locale.getDefault()).format(Date())
            val root = File(
                Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "SholehApp"
            )
            if (!root.exists()) root.mkdirs()
            val path = File(root, "/SholehApp Report Zakat $strDate.xlsx")
            val workbook = XSSFWorkbook()
            val outputStream = FileOutputStream(path)
            val headerStyle = workbook.createCellStyle()
            headerStyle.setAlignment(HorizontalAlignment.CENTER)
            headerStyle.fillForegroundColor = IndexedColors.BLUE_GREY.getIndex()
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND)
            headerStyle.setBorderTop(BorderStyle.MEDIUM)
            headerStyle.setBorderBottom(BorderStyle.MEDIUM)
            headerStyle.setBorderRight(BorderStyle.MEDIUM)
            headerStyle.setBorderLeft(BorderStyle.MEDIUM)
            val font = workbook.createFont()
            font.fontHeightInPoints = 12.toShort()
            font.color = IndexedColors.WHITE.getIndex()
            font.bold = true
            headerStyle.setFont(font)
            val sheet = workbook.createSheet("Data Zakat")
            var row = sheet.createRow(0)

            var cell = row.createCell(0)
            cell.setCellValue("Nama")
            cell.cellStyle = headerStyle

            cell = row.createCell(1)
            cell.setCellValue("RT")
            cell.cellStyle = headerStyle

            cell = row.createCell(2)
            cell.setCellValue("Tipe Zakat")
            cell.cellStyle = headerStyle

            cell = row.createCell(3)
            cell.setCellValue("Jumlah Jiwa")
            cell.cellStyle = headerStyle

            cell = row.createCell(4)
            cell.setCellValue("Zakat Beras")
            cell.cellStyle = headerStyle

            cell = row.createCell(5)
            cell.setCellValue("Zakat Uang")
            cell.cellStyle = headerStyle

            cell = row.createCell(6)
            cell.setCellValue("Infak")
            cell.cellStyle = headerStyle

            cell = row.createCell(7)
            cell.setCellValue("Tanggal")
            cell.cellStyle = headerStyle

            for (i in dataZakat.indices) {
                row = sheet.createRow(i + 1)
                cell = row.createCell(0)
                cell.setCellValue(dataZakat[i].nama)
                sheet.setColumnWidth(0, (dataZakat[i].nama.length + 30) * 256)
                cell = row.createCell(1)
                cell.setCellValue(dataZakat[i].rt)
                sheet.setColumnWidth(1, (dataZakat[i].rt.length + 30) * 256)
                cell = row.createCell(2)
                cell.setCellValue(dataZakat[i].typeZakat)
                sheet.setColumnWidth(2, (dataZakat[i].typeZakat.length + 30) * 256)
                cell = row.createCell(3)
                cell.setCellValue(dataZakat[i].jumlahJiwa)
                sheet.setColumnWidth(3, (dataZakat[i].jumlahJiwa.length + 30) * 256)
                cell = row.createCell(4)
                cell.setCellValue(dataZakat[i].berasZakat)
                sheet.setColumnWidth(4, (dataZakat[i].berasZakat.length + 30) * 256)
                cell = row.createCell(5)
                cell.setCellValue(dataZakat[i].uangZakat)
                sheet.setColumnWidth(5, (dataZakat[i].uangZakat.length + 30) * 256)
                cell = row.createCell(6)
                cell.setCellValue(dataZakat[i].infak)
                sheet.setColumnWidth(6, (dataZakat[i].infak.length + 30) * 256)
                cell = row.createCell(7)
                cell.setCellValue(dataZakat[i].createdDate)
                sheet.setColumnWidth(7, (dataZakat[i].createdDate.length + 30) * 256)
            }
            workbook.write(outputStream)
            outputStream.close()
            showMessage("Data berhasil di ekspor!")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentZakatBinding = null
    }
}