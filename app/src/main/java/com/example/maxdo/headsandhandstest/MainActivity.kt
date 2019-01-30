package com.example.maxdo.headsandhandstest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val passwordEndHelpDrawable = ContextCompat.getDrawable(this, R.drawable.help)




        passwordEt.setCompoundDrawablesWithIntrinsicBounds(null, null, passwordEndHelpDrawable, null)
    }
}
