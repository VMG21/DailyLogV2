package com.tec.dailylogv2.ui.registros

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.tec.dailylogv2.R
import com.tec.dailylogv2.dl.Cliente

class RegistrosFragment : Fragment(R.layout.fragment_registros) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerView.Adapter<*>
    private val usersList: MutableList<Cliente> = mutableListOf()
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registros, container, false)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rvLista)

        adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.cliente_item, parent, false)
                return object : RecyclerView.ViewHolder(view) {}
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val user = usersList[position]
                holder.itemView.findViewById<TextView>(R.id.tvName).text = user.name
                holder.itemView.findViewById<TextView>(R.id.tvEmail).text = user.email
                holder.itemView.findViewById<TextView>(R.id.tvPhone).text = user.phone

                val deleteButton = holder.itemView.findViewById<ImageButton>(R.id.imgbtnDelete)
                deleteButton.setOnClickListener {
                    showDeleteConfirmationDialog(user)
                }
            }

            override fun getItemCount(): Int = usersList.size
        }

//        val agregarCliente = view.findViewById<Button>(R.id.btnAddCliente)
//        agregarCliente.setOnClickListener(){
//            navigateToAgregarRegistro()
//        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@RegistrosFragment.adapter
        }

        val ref = FirebaseDatabase.getInstance().getReference("Clientes")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                usersList.clear()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(Cliente::class.java)
                    user?.let { usersList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        val searchEditText = view.findViewById<EditText>(R.id.etBuscar)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                filterUsers(query)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }

//    private fun navigateToAgregarRegistro() {
//        val navController = findNavController()
//        navController.navigate(R.id.nav_agregar)
//    }

    private fun filterUsers(query: String) {
        val filteredList = usersList.filter { user ->
            user.name?.contains(query, ignoreCase = true) ?: true
        }
        adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.cliente_item, parent, false)
                return object : RecyclerView.ViewHolder(view) {}
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val user = filteredList[position]
                holder.itemView.findViewById<TextView>(R.id.tvName).text = user.name
                holder.itemView.findViewById<TextView>(R.id.tvEmail).text = user.email
                holder.itemView.findViewById<TextView>(R.id.tvPhone).text = user.phone

                val deleteButton = holder.itemView.findViewById<ImageButton>(R.id.imgbtnDelete)
                deleteButton.setOnClickListener {
                    showDeleteConfirmationDialog(user)
                }
            }

            override fun getItemCount(): Int = filteredList.size
        }

        recyclerView.adapter = adapter
    }

    private fun showDeleteConfirmationDialog(user: Cliente) {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("ELIMINAR CLIENTE")
            .setMessage("Estas seguro que quieres eliminar este cliente?")
            .setPositiveButton("Eliminar") { _, _ ->
                deleteUser(user)
            }
            .setNegativeButton("Cancelar", null)
            .create()

        alertDialog.show()
    }

    private fun deleteUser(user: Cliente) {
        val userRef = FirebaseDatabase.getInstance().getReference("Clientes").child(user.name.toString())
        val diagUserRef = FirebaseDatabase.getInstance().getReference("Diagnosticos").child(user.name.toString())
        userRef.removeValue()
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Usuario eliminado de forma satisfactoria", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Hubo en error al eliminar al usuario", Toast.LENGTH_SHORT).show()
            }
        diagUserRef.removeValue()
    }
}