package com.carbit3333333.recordervoices.listRecord

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.carbit3333333.recordervoices.R
import com.carbit3333333.recordervoices.database.RecordingItem
import com.carbit3333333.recordervoices.player.PlayerFragment
import com.carbit3333333.recordervoices.removeDialog.RemoveDialogFragment
import java.io.File
import java.lang.Exception
import java.util.concurrent.TimeUnit

class ListRecordAdapter : RecyclerView.Adapter<ListRecordAdapter.ViewHolder>() {
    var data = listOf<RecordingItem>()
        set(value) {
            field = value
        notifyDataSetChanged()
        }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var vName: TextView = itemView.findViewById(R.id.file_name_text)
        var vLenght: TextView = itemView.findViewById(R.id.file_length_text)
        var cardView: CardView = itemView.findViewById(R.id.card_view)

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutinflater = LayoutInflater.from(parent.context)
                val view: View = layoutinflater.inflate(R.layout.list_item_record, parent, false)
                return ViewHolder(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context: Context = holder.itemView.context
        val recordingItem = data[position]
        val itemDutation: Long = recordingItem.lenght
        val minutes = TimeUnit.MILLISECONDS.toMinutes(itemDutation)
        val seconds =
            TimeUnit.MILLISECONDS.toSeconds(itemDutation) - TimeUnit.MINUTES.toSeconds(minutes)

        holder.vName.text = recordingItem.name
        holder.vLenght.text = String.format("%02d:%02d", minutes, seconds)
        holder.cardView.setOnClickListener{
            val filePath = recordingItem.filePath

            val file = File(filePath)
            if (file.exists()){
                try {
                    playRecord(filePath, context)
                }catch (e: Exception){

                }
            }else{
                Toast.makeText(context, "Аудиофайл не найден", Toast.LENGTH_LONG).show()
            }
        }
        holder.cardView.setOnLongClickListener {
            removeItemDialog(recordingItem, context)
            false
        }
    }
    private fun playRecord(filePath: String, context: Context){
        Log.e("ADAPTER playRecord","$filePath")
        val playerFragment: PlayerFragment = PlayerFragment().newInstance(filePath)
        val transaction: FragmentTransaction = (context as FragmentActivity)
            .supportFragmentManager
            .beginTransaction()
        playerFragment.show(transaction, "dialog_playback")
    }
    private fun removeItemDialog(recordingItem: RecordingItem, context: Context?){
        val removeDialogFragment: RemoveDialogFragment = RemoveDialogFragment().newInstance(recordingItem.id,
            recordingItem.filePath)
        val transaction: FragmentTransaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
        removeDialogFragment.show(transaction, "dialog_remove")
    }
}