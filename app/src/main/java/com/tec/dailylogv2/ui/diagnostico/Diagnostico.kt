package com.tec.dailylogv2.ui.diagnostico

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tec.dailylogv2.R

class Diagnostico : Fragment(R.layout.fragment_diagnostico) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_diagnostico, container, false)

        val userName = arguments?.getString("userName")
        val bundle = Bundle()
        bundle.putString("UserName", userName)

        Log.d("INFO", userName.toString())

        val navToAgregarDiagnostico = view.findViewById<Button>(R.id.btnNavAgregarDiag)


        navToAgregarDiagnostico.setOnClickListener{
            findNavController().navigate(R.id.nav_agregar_diagnostico, bundle)
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }
}