package com.tec.dailylogv2.ui.editarRegistro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tec.dailylogv2.R
import com.tec.dailylogv2.dl.Cliente

class EditarCliente : Fragment(R.layout.fragment_editar_cliente) {
    private lateinit var database: DatabaseReference
    private lateinit var userId: String
    private lateinit var name : String
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var updateButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_editar_cliente, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId = arguments?.getString("userName") ?: ""

        Log.d("INFO", userId)

        emailEditText = view.findViewById(R.id.etCorreoEdit)
        phoneEditText = view.findViewById(R.id.etPhoneEdit)

        updateButton = view.findViewById(R.id.btnAgregarEdit)

        database = FirebaseDatabase.getInstance().getReference("Clientes")

        database.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(Cliente::class.java)
                user?.let {
                    name = user.name.toString()
                    emailEditText.setText(user.email)
                    phoneEditText.setText(user.phone)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        updateButton.setOnClickListener {
            updateUser()
        }
    }

    private fun updateUser() {
        val email = emailEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()

        if (phone.isEmpty() ||email.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor introduce nombre, telefono y correo: ", Toast.LENGTH_SHORT).show()
            return
        }

        val user = Cliente(name, phone, email)

        database.child(userId).setValue(user)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "User updated successfully", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to update user", Toast.LENGTH_SHORT).show()
            }
    }
}