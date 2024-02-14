package com.levimartins.artculnaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import com.levimartins.artculnaria.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {

    data class CheckBoxInfo(val checkBoxId: CheckBox, val value: Float)

    private lateinit var binding: ActivityMainBinding
    private var totalValue = 0f
    private lateinit var checkBoxInfoList: List<CheckBoxInfo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.finishButton.setOnClickListener(this)

        checkBoxInfoList = listOf(
            CheckBoxInfo(binding.foodCheck01, 18.90f),
            CheckBoxInfo(binding.foodCheck02, 24.50f),
            CheckBoxInfo(binding.foodCheck03, 28.75f),
            CheckBoxInfo(binding.foodCheck04, 26.50f),
            CheckBoxInfo(binding.foodCheck05, 25.90f),
            CheckBoxInfo(binding.foodCheck06, 60.00f),
            CheckBoxInfo(binding.foodCheck07, 45.50f),
            CheckBoxInfo(binding.drinkCheck01, 6.50f),
            CheckBoxInfo(binding.drinkCheck02, 10.00f),
            CheckBoxInfo(binding.drinkCheck03, 4.00f),
        )
        calculate()
    }

    override fun onClick(view: View?) {
        if (view != null) {
            if (view.id == R.id.finish_button) {
                if (!isValid()) {
                    Toast.makeText(
                        this,
                        "Escolha pelo menos uma opção",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    resetCheckBoxes()
                    binding.commentInput.setText("")
                    binding.amountNumber.text = "R$ 0,00"
                    Toast.makeText(
                        this,
                        "Seu pedido foi enviado para o balcão do restaurante",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun isValid(): Boolean {
        for (checkBoxInfo in checkBoxInfoList) {
            if (checkBoxInfo.checkBoxId.isChecked) {
                return true
            }
        }
        return false
    }

    private fun calculate() {
        for (checkBoxInfo in checkBoxInfoList) {
            val checkBox = checkBoxInfo.checkBoxId
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                updateTotalValue(checkBoxInfo.value, isChecked)
            }
        }
    }

    private fun updateTotalValue(value: Float, isChecked: Boolean) {
        if (isChecked) {
            totalValue += value
            updateTotalTextView()

        } else {
            totalValue -= value
            updateTotalTextView()
        }
    }

    private fun updateTotalTextView() {
        val amount = "R$ ${"%.2f".format(totalValue)}"
        binding.amountNumber.text = amount
    }

    private fun resetCheckBoxes() {
        for (checkBoxInfo in checkBoxInfoList) {
            checkBoxInfo.checkBoxId.isChecked = false
        }
    }
}