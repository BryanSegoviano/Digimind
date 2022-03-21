package segoviano.bryan.digimind.ui.dashboard

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import segoviano.bryan.digimind.R
import segoviano.bryan.digimind.databinding.FragmentDashboardBinding
import segoviano.bryan.digimind.ui.Task
import segoviano.bryan.digimind.ui.home.HomeFragment
import java.text.SimpleDateFormat
import java.util.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        this.binding.btnTime.setOnClickListener{
            this.set_time()
        }

        this.binding.btnSave.setOnClickListener {
            this.guardar()
        }

        return root
    }

    fun guardar(){
        var titulo: String = this.binding.etTask.text.toString()

        var dia: String = ""

        if(this.binding.rbDay1.isChecked){
            dia = getString(R.string.day1)
        }
        if(this.binding.rbDay2.isChecked){
            dia = getString(R.string.day2)
        }
        if(this.binding.rbDay3.isChecked){
            dia = getString(R.string.day3)
        }
        if(this.binding.rbDay4.isChecked){
            dia = getString(R.string.day4)
        }
        if(this.binding.rbDay5.isChecked){
            dia = getString(R.string.day5)
        }
        if(this.binding.rbDay6.isChecked){
            dia = getString(R.string.day6)
        }
        if(this.binding.rbDay7.isChecked){
            dia = getString(R.string.day7)
        }

        var tiempo: String = this.binding.btnTime.text.toString()

        var tarea = Task(titulo,dia,tiempo)
        HomeFragment.tasks.add(tarea)
        Toast.makeText(this.context,"Tarea agregada correctamente", Toast.LENGTH_SHORT).show()
        guardarGson()
    }

    fun guardarGson(){
        val preferencias = this.context?.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val editor = preferencias?.edit()
        val gson: Gson = Gson()
        var json = gson.toJson(HomeFragment.tasks)
        editor?.putString("tareas", json)
        editor?.apply()
    }

    fun set_time(){
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            this.binding.btnTime.text = SimpleDateFormat("HH:mm").format(cal.time)
        }
        TimePickerDialog(this.context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE), true).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}