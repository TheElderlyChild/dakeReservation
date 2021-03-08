package com.elderlyChild.dake.ui.orders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elderlyChild.dake.R

class OrderConfirmationActivity : AppCompatActivity() {
    
    val order = getIntent().getExtra("order") 
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirmation)
        
        initWidgets()
        initButtons()
        displayInfo()
    }
    
    private fun initWidgets(){
    }
    
    private fun displayInfo(){
    }
    
    private fun initButtons(){
    }
}
