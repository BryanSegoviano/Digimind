package segoviano.bryan.digimind

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import segoviano.bryan.digimind.ui.Task
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import segoviano.bryan.digimind.ui.home.HomeFragment

class AdaptadorTareas: BaseAdapter {

    lateinit var context: Context
    var tasks: ArrayList<Task> = ArrayList<Task>()

    constructor(context: Context, tasks: ArrayList<Task>){
        this.context = context
        this.tasks = tasks
    }

    override fun getCount(): Int {
        return this.tasks.size
    }

    override fun getItem(p0: Int): Any {
       return this.tasks[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var inflador = LayoutInflater.from(this.context)
        var vista = inflador.inflate(R.layout.task_view, null)

        var task = this.tasks[p0]

        val tv_titulo: TextView = vista.findViewById(R.id.tv_title)
        val tv_tiempo: TextView = vista.findViewById(R.id.tv_time)
        val tv_dia: TextView = vista.findViewById(R.id.tv_days)

        tv_titulo.setText(task.title)
        tv_dia.setText(task.day)
        tv_tiempo.setText(task.time)

        vista.setOnClickListener{
            eliminar(task)
        }

        return vista
    }

    fun eliminar(task: Task){
        val alertDialog: AlertDialog? = this.context?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.ok_button,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User clicked OK button
                        HomeFragment.tasks.remove(task)
                        guardarGson()
                        HomeFragment.adaptador.notifyDataSetChanged()
                        Toast.makeText(this.context, R.string.msg_deleted, Toast.LENGTH_SHORT).show()
                    })
                setNegativeButton(R.string.cancel_button,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            }
            // Set other dialog properties
            builder?.setMessage(R.string.msg)
                .setTitle(R.string.title)
            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.show()
    }

    fun guardarGson(){
        val preferencias = this.context?.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val editor = preferencias?.edit()
        val gson: Gson = Gson()
        var json = gson.toJson(HomeFragment.tasks)
        editor?.putString("tareas", json)
        editor?.apply()
    }

}