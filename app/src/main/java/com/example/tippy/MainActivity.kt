package com.example.tippy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*


private const val TAG="MainActivity"
private const val INITIAL_TIP_PERCENT = 15
private const val INITIAL_SHARE = 1

class MainActivity : AppCompatActivity() {
    private lateinit var etBaseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipPercentLabel: TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvTipDescription: TextView
    private lateinit var etShare: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBaseAmount=findViewById(R.id.etBaseAmount)
        seekBarTip=findViewById(R.id.seekBarTip)
        tvTipPercentLabel=findViewById(R.id.tvTipPercentLabel)
        tvTipAmount=findViewById(R.id.tvTipAmount)
        tvTotalAmount=findViewById(R.id.tvTotalAmount)
        tvTipDescription=findViewById(R.id.tvTipDescription)
        etShare=findViewById(R.id.etShare)

        updateTipDescription(INITIAL_TIP_PERCENT)
        seekBarTip.progress= INITIAL_TIP_PERCENT
        tvTipPercentLabel.text="$INITIAL_TIP_PERCENT%"

        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUSer: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                tvTipPercentLabel.text="$progress%"
                computeTipAndTotal()
                updateTipDescription(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
        etBaseAmount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged $p0")
                computeTipAndTotal()
            }

        })
        etShare.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged $p0")
                computeTipAndTotal()

            }


        })

    }

    private fun updateTipDescription(tipPercent: Int) {
        val tipDescription=when (tipPercent){
            in 0..9->"Poor"
            in 10..14->"Accepted"
            in 15..19->"Good"
            in 20..24->"Great"
            else->"Amazing"
        }
        tvTipDescription.text=tipDescription
    }

    private fun computeTipAndTotal() {
        if(etBaseAmount.text.isEmpty()){
            tvTipAmount.text=""
            tvTotalAmount.text=""
            return
        }
        if (etShare.text.isEmpty()){
            etShare.setText("$INITIAL_SHARE")
            return
        }

        val baseAmount=etBaseAmount.text.toString().toDouble()
        val tipPresent=seekBarTip.progress
        val shareAmount = etShare.text.toString().toInt()


        val tipAmount = baseAmount*tipPresent/100
        val totalAmount=(baseAmount+tipAmount)/shareAmount




        tvTipAmount.text="%.2f".format(tipAmount)
        tvTotalAmount.text="%.2f".format(totalAmount)
    }
}