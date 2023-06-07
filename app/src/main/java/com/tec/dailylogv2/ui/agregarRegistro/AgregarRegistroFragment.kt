package com.tec.dailylogv2.ui.agregarRegistro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tec.dailylogv2.R
import com.tec.dailylogv2.databinding.FragmentAgregarRegistroBinding
import com.tec.dailylogv2.dl.Cliente
import java.util.UUID

class AgregarRegistroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agregar_registro, container, false)
        val nameEditText = view.findViewById<EditText>(R.id.etName1)
        val emailEditText = view.findViewById<EditText>(R.id.etPhone)
        val phoneEditText = view.findViewById<EditText>(R.id.etCorreo)
        val registerButton = view.findViewById<Button>(R.id.btnAgregar)

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val phone = phoneEditText.text.toString()

            val cliente = Cliente(name, email, phone)

            val db = Firebase.database
            val ref = db.getReference("Clientes/$name")
            val refDiagnostico = db.getReference("Diagnosticos/$name")
            ref.setValue(cliente)
            refDiagnostico.setValue(name)

            nameEditText.setText("")
            emailEditText.setText("")
            phoneEditText.setText("")
        }

        return view;
    }
}
