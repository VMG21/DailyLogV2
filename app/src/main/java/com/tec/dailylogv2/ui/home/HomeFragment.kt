package com.tec.dailylogv2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.tec.dailylogv2.R

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val buttonClientes = view.findViewById<Button>(R.id.btnNavigatoToClientes)
        val buttonAgregar = view.findViewById<Button>(R.id.btnNavigateToAgregar)
        val buttonAbout = view.findViewById<Button>(R.id.btnAbout)
        val navController = findNavController()

        buttonClientes.setOnClickListener{
            navController.popBackStack()
            navController.navigate(R.id.nav_registros)
        }

        buttonAgregar.setOnClickListener {
            navController.popBackStack()
            navController.navigate(R.id.nav_agregar)
        }

        buttonAbout.setOnClickListener {
            navController.popBackStack()
            navController.navigate(R.id.nav_about)
        }

        return view
    }

}