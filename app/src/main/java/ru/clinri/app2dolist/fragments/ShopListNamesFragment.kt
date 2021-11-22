package ru.clinri.app2dolist.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import ru.clinri.app2dolist.activities.MainApp
import ru.clinri.app2dolist.databinding.FragmentShopListNamesBinding
import ru.clinri.app2dolist.db.MainViewModel
import ru.clinri.app2dolist.dialogs.NewListDialog


class ShopListNamesFragment : BaseFragment() {
    private lateinit var binding: FragmentShopListNamesBinding

    private val mainVeiwModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)

    }

    override fun onClickNew() {
        NewListDialog.showDialog(activity as AppCompatActivity,
        object : NewListDialog.Listener{
            override fun onClick(name: String) {
                Log.d("MyLog", "Name: $name")
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopListNamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun initRcView() = with(binding) {

    }

    private fun observer() { //будет следить за изменениями в БД
        mainVeiwModel.allNotes.observe(viewLifecycleOwner, {

        })
    }

    companion object {

        @JvmStatic
        fun newInstance() = ShopListNamesFragment()
    }

}