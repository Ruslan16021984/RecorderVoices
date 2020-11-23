package com.carbit3333333.recordervoices.listRecord

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.carbit3333333.recordervoices.R
import com.carbit3333333.recordervoices.database.RecordDataBase
import com.carbit3333333.recordervoices.database.RecordDataBaseDao
import com.carbit3333333.recordervoices.databinding.FragmentListRecordBinding

/**
 * A simple [Fragment] subclass.
 */
class ListRecordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentListRecordBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_list_record, container, false)
        val application = requireNotNull(this.activity).application
        var dataSource = RecordDataBase.getInstance(application).recordDataBaseDao
        val viewModelFactory = ListRecordsViewModelFactory(dataSource)
        val listRecordViewModel = ViewModelProvider(this, viewModelFactory).get(ListRecordViewModel::class.java)
        binding.listRecordViewModel = listRecordViewModel
        val adapter = ListRecordAdapter()
        binding.recyclerView.adapter = adapter
        listRecordViewModel.records.observe(viewLifecycleOwner, Observer {
            it?.let { adapter.data = it }
        })
        binding.lifecycleOwner = this
        return  binding.root
    }

}
