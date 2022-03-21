package segoviano.bryan.digimind.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import segoviano.bryan.digimind.AdaptadorTareas
import segoviano.bryan.digimind.databinding.FragmentHomeBinding
import segoviano.bryan.digimind.ui.Task

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null


    companion object {
        var tasks: ArrayList<Task> = ArrayList<Task>()
        lateinit var adaptador: AdaptadorTareas
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val gridView: GridView = this.binding.gridview

        cargar_tareas()

        adaptador = AdaptadorTareas(root.context, tasks)
        gridView.adapter = adaptador

        return root
    }

    fun cargar_tareas(){
        val preferencias = this.context?.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val gson: Gson = Gson()
        var json = preferencias?.getString("tareas", null)
        val type = object: TypeToken<ArrayList<Task?>?>(){}.type
        if(json == null){
            tasks = ArrayList<Task>()
        } else {
            tasks = gson.fromJson(json, type)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}