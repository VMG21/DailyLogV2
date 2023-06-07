package com.tec.dailylogv2.ui.diagnostico

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tec.dailylogv2.R
import com.tec.dailylogv2.dl.Diagnostico

class Diagnostico : Fragment(R.layout.fragment_diagnostico) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerView.Adapter<*>
    private val diagnosticList: MutableList<Diagnostico> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_diagnostico, container, false)
        val navController = findNavController()

        val userName = arguments?.getString("userName")
        val bundle = Bundle()
        bundle.putString("UserName", userName)

        Log.d("INFO", userName.toString())

        val navToAgregarDiagnostico = view.findViewById<Button>(R.id.btnNavAgregarDiag)
        navToAgregarDiagnostico.setOnClickListener{
            navController.popBackStack()
            navController.navigate(R.id.nav_agregar_diagnostico, bundle)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userName = arguments?.getString("userName")
        recyclerView = view.findViewById(R.id.rvListaDiagnostico)

        adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.diagnostico_item, parent, false)
                return object : RecyclerView.ViewHolder(view) {}
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val diagnostico = diagnosticList[position]
                holder.itemView.findViewById<TextView>(R.id.tvFecha).text = diagnostico.fecha
                holder.itemView.findViewById<TextView>(R.id.tvDescripcion).text = diagnostico.descripcion
            }

            override fun getItemCount(): Int = diagnosticList.size
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@Diagnostico.adapter
        }

        val ref = FirebaseDatabase.getInstance().getReference("Diagnosticos/$userName")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                diagnosticList.clear()
                for (diagnosticSnapshot in snapshot.children){
                    val diag = diagnosticSnapshot.getValue(Diagnostico::class.java)
                    diag?.let { diagnosticList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })

    }
}