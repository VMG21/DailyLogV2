package com.tec.dailylogv2.ui.diagnostico

import android.os.Bundle
import android.util.Log
import android.util.LogPrinter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tec.dailylogv2.R
import java.util.logging.Logger

class Diagnostico : Fragment(R.layout.fragment_diagnostico) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_diagnostico, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userName = arguments?.getString("userName")
        Log.d("INFO", userName.toString())



    }
}