package com.carbit3333333.recordervoices.removeDialog

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.carbit3333333.recordervoices.R
import com.carbit3333333.recordervoices.database.RecordDataBase

class RemoveDialogFragment : DialogFragment() {
    private lateinit var viewModel: RemoveDialogViewModel
    companion object{
        private const val ARG_ITEM_PATH = "recording_item_path"
        private const val ARG_ITEM_ID = "recording_item_id"
    }
    fun newInstance(itemId: Long, itemPath: String?): RemoveDialogFragment{
        val f = RemoveDialogFragment()
        val b = Bundle()
        b.putLong(ARG_ITEM_ID, itemId)
        b.putString(ARG_ITEM_PATH, itemPath)
        f.arguments = b
        return f
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val application = requireNotNull(this.activity).application
        val dataBase = RecordDataBase.getInstance(application).recordDataBaseDao
        val viewModelFactory = RemoveViewModelFactory(
            dataBase,
            application
        )
        viewModel = ViewModelProvider(this, viewModelFactory).get(RemoveDialogViewModel::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val itemPath = arguments?.getString(ARG_ITEM_PATH)
        val itemId = arguments?.getLong(ARG_ITEM_ID)
        return AlertDialog.Builder(requireActivity())
            .setTitle(R.string.dialog_title_delete)
            .setMessage(R.string.dialog_text_delete)
            .setPositiveButton(R.string.dialog_action_yes){
                dialog, which ->
                try {
                    itemId?.let {viewModel.removeItem(it) }
                    itemPath?.let { viewModel.removeFile(it) }
                }catch (e: Exception){
                    Log.e("deleteDialog", "exeption", e)
                }
                dialog.cancel()
            }.setNegativeButton(
                R.string.dialog_action_no,{
                    dialog, which ->
                    dialog.cancel()
                }
            ).create()
    }
}
