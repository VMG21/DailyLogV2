package com.tec.dailylogv2.ui.agregarDiagnostico


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tec.dailylogv2.R
import com.tec.dailylogv2.dl.Diagnostico
import java.util.UUID

class AgregarDiagnostico : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agregar_diagnostico, container, false)
        val fecha = view.findViewById<EditText>(R.id.etFecha)
        val descripcion = view.findViewById<EditText>(R.id.etDescripcionDiag)
        val registerButton = view.findViewById<Button>(R.id.btnAgregarDiag)
        val uniqueId = UUID.randomUUID().toString();

        val userName = arguments?.getString("UserName")
        Log.d("INFO", userName.toString())

        registerButton.setOnClickListener{
            val fechadb = fecha.text.toString()
            val desc = descripcion.text.toString()

            val diagnostico = Diagnostico(fechadb, desc)

            val db = Firebase.database
            val ref = db.getReference("Diagnosticos/${userName.toString()}/$uniqueId/")
            ref.setValue(diagnostico)

            fecha.setText("")
            descripcion.setText("")
        }

        return view
    }

}