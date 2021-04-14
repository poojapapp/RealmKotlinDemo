package com.solace.productlistdemo



import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.solace.productlistdemo.model.Employee
import io.realm.Realm
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var text_productName: EditText
    lateinit var text_desc: EditText
    lateinit var text_category: EditText
    lateinit var text_price: EditText
    lateinit var text_marketPrice: EditText
    lateinit var btnRead: Button
    lateinit var btnAdd: Button
    lateinit var btnUpdate: Button
    lateinit var btnDelete: Button
    lateinit var textView: TextView
    lateinit var spinner_language:Spinner
    private var mAdapter: ArrayAdapter<*>? = null

    var mRealm: Realm? = null
    override fun onCreate(savedInstanceState: Bundle?) {
     super.onCreate(savedInstanceState)
     setContentView(R.layout.activity_main)
        initViews()
      //  mRealm = Realm.getDefaultInstance()
        Realm.init(this);
 }

    private fun initViews() {
        btnAdd = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener(this)
        btnRead = findViewById(R.id.btnRead)
        btnRead.setOnClickListener(this)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnUpdate.setOnClickListener(this)
        btnDelete = findViewById(R.id.btnDelete)
        btnDelete.setOnClickListener(this)
        textView = findViewById(R.id.textViewEmployees)
        text_productName = findViewById(R.id.text_productName)
        text_desc = findViewById(R.id.text_desc)
        text_category = findViewById(R.id.text_category)
        text_price = findViewById(R.id.text_price)
        text_marketPrice = findViewById(R.id.text_marketPrice)
        spinner_language = findViewById(R.id.spinner_language)
        val language: MutableList<String> = ArrayList()
        language.add("English")
        language.add("Hindi")
        mAdapter = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            language
        )
        (mAdapter as ArrayAdapter<String>).setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinner_language.adapter = mAdapter
        spinner_language.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>,
                view: View,
                i: Int,
                l: Long
            ) {
                val selectedItemText =
                    adapterView.getItemAtPosition(i) as String
                if (selectedItemText == "Hindi") {
                    //  LocaleHelper.setLocale(MainActivity.this, mLanguageCode);
                    // recreate();
                    onselectedProductName()
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                return
            }
        }
    }
    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnAdd -> addEmployee()
            R.id.btnRead -> readEmployeeRecords()
            R.id.btnUpdate -> updateEmployeeRecords()
            R.id.btnDelete -> deleteEmployeeRecord()
        }
    }
    private fun addEmployee() {
        try {
            var realm: Realm? = null
        //    realm = Realm.getDefaultInstance()
            if (realm != null) {
                realm.executeTransaction(Realm.Transaction { realm ->
                    try {
                        if (!text_productName.text.toString().trim { it <= ' ' }.isEmpty()) {
                            val employee = Employee()
                            employee.text_productName =
                                text_productName.text.toString().trim { it <= ' ' }
                            if (!text_desc.text.toString().trim { it <= ' ' }
                                    .isEmpty()
                            ) employee.text_desc =
                                text_desc.text.toString().trim { it <= ' ' }
                            if (!text_category.text.toString().trim { it <= ' ' }
                                    .isEmpty()
                            ) employee.text_category =
                                text_category.text.toString().trim { it <= ' ' }
                            if (!text_price.text.toString().trim { it <= ' ' }
                                    .isEmpty()
                            ) employee.text_price =
                                text_price.text.toString().trim { it <= ' ' }
                            if (!text_marketPrice.text.toString().trim { it <= ' ' }
                                    .isEmpty()
                            ) employee.text_marketPrice =
                                text_marketPrice.text.toString().trim { it <= ' ' }
                            realm.copyToRealm(employee)
                        }
                    } catch (e: RealmPrimaryKeyConstraintException) {
                        Toast.makeText(
                            applicationContext,
                            "Primary Key exists, Press Update instead",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        } finally {
           // realm?.close()
        }

    }
    private fun readEmployeeRecords() {
        mRealm?.executeTransaction { realm ->
            val results = realm.where(Employee::class.java).findAll()
            textView.text = ""
            for (employee in results) {
                textView.append(
                    """
                        Product Name: ${employee.text_productName.toString()}
                        Product Description: ${employee.text_desc.toString()}
                        Product Category: ${employee.text_category.toString()}
                        Product Price: ${employee.text_price.toString()}
                        Product Market Price:${employee.text_marketPrice.toString()}
                        """.trimIndent()
                )
            }
        }

    }
    private fun updateEmployeeRecords() {
        mRealm?.executeTransaction { realm ->
            if (!text_productName.text.toString().trim { it <= ' ' }.isEmpty()) {
                var employee = realm.where(Employee::class.java)
                    .equalTo(Employee.PROPERTY_NAME, text_productName.text.toString())
                    .findFirst()
                if (employee == null) {
                    employee = realm.createObject(
                        Employee::class.java,
                        text_productName.text.toString().trim { it <= ' ' }
                    )
                }
                if (!text_desc.text.toString().trim { it <= ' ' }
                        .isEmpty()
                ) employee!!.text_desc = text_desc.text.toString().trim { it <= ' ' }
                if (!text_category.text.toString().trim { it <= ' ' }
                        .isEmpty()
                ) employee!!.text_category =
                    text_category.text.toString().trim { it <= ' ' }
                if (!text_price.text.toString().trim { it <= ' ' }
                        .isEmpty()
                ) employee!!.text_price = text_price.text.toString().trim { it <= ' ' }
                if (!text_marketPrice.text.toString().trim { it <= ' ' }
                        .isEmpty()
                ) employee!!.text_marketPrice =
                    text_marketPrice.text.toString().trim { it <= ' ' }
            }
        }
    }
    private fun deleteEmployeeRecord() {
        mRealm?.executeTransaction { realm ->
            val employee = realm.where(Employee::class.java)
                .equalTo(Employee.PROPERTY_NAME, text_productName.text.toString())
                .findFirst()
            employee?.deleteFromRealm()
        }
    }
    private fun onselectedProductName() {
        mRealm?.executeTransaction { realm ->
            val results = realm.where(Employee::class.java).findAll()
            textView.text = ""
            for (employee in results) {
                textView.append(
                    """
                        Product Name: ${employee.text_productName.toString()}
                        
                        """.trimIndent()
                )
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        if (mRealm != null) {
            mRealm!!.close()
        }
    }

}