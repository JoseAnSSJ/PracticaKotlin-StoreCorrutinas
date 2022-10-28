package com.example.store.MainModule

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.store.*
import com.example.store.Common.Entity.StoreEntity
import com.example.store.Common.Utili.TypeError
import com.example.store.EditModule.EditStoreFragment
import com.example.store.MainModule.Adapter.OnClickListener
import com.example.store.MainModule.Adapter.StoreAdapter
import com.example.store.MainModule.Adapter.StoreListAdapter
import com.example.store.MainModule.ViewModel.EditSotreViewModel
import com.example.store.MainModule.ViewModel.MainViewModel
import com.example.store.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: StoreListAdapter
    private lateinit var mGridLayout: GridLayoutManager

    //MVVM
    private lateinit var mMainViewModel: MainViewModel
    private lateinit var mEditSotreViewModel: EditSotreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            launchEditFragment()
        }

        setupViewModel()
        setupRecyclerView()

    }

    private fun setupViewModel() {
        mMainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mMainViewModel.getStores().observe(this) { store ->
            binding.progressBar.visibility = View.GONE
            mAdapter.submitList(store)

        }
        mMainViewModel.showProgress().observe(this){ isShowProgress ->
            binding.progressBar.visibility =
                if(isShowProgress) View.VISIBLE else View.GONE
        }

        mMainViewModel.getTypeError().observe(this){ typeError->
            val msgRes = when(typeError){
                TypeError.GET -> R.string.main_error_get
                TypeError.DELETE -> R.string.main_error_delete
                TypeError.INSERT -> R.string.main_error_insert
                TypeError.UPDATE -> R.string.main_error_update
                else -> R.string.main_error_default
            }
            Snackbar.make(binding.root, msgRes, Snackbar.LENGTH_LONG).show()
        }

        mEditSotreViewModel = ViewModelProvider(this)[EditSotreViewModel::class.java]
        mEditSotreViewModel.getShowFab().observe(this){ isVisible ->
            if (isVisible) {
                binding.fab.show()
            } else {
                binding.fab.hide()
            }
        }


    }

    private fun launchEditFragment(storeEntity: StoreEntity = StoreEntity()) {
        mEditSotreViewModel.setShowFab(false)
        mEditSotreViewModel.setSotoreSelect(storeEntity)

        val fragment = EditStoreFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.contentMain, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun setupRecyclerView() {
        mAdapter = StoreListAdapter(this)
        mGridLayout = GridLayoutManager(this, resources.getInteger(R.integer.main_colums))
        //getAllStores()

        binding.recycler.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mEditSotreViewModel.setShowFab(true)
    }



    /*
    OnClickListenenr
    */
    override fun onClick(storeEntity: StoreEntity) {
        launchEditFragment(storeEntity)

    }

    override fun onFavoriteStore(storeEntity: StoreEntity) {
        mMainViewModel.updateStore(storeEntity)
    }

    override fun onDeleteStore(storeEntity: StoreEntity) {
        val item: Array<String> = resources.getStringArray(R.array.array_opcion_items)
        MaterialAlertDialogBuilder(this).setTitle(R.string.dialog_opcion_title)
            .setItems(item) { dialogInterface, i ->
                when (i) {
                    0 -> confirmDelete(storeEntity)
                    1 -> dial(storeEntity.phone)
                    2 -> goToWeb(storeEntity.webSite)
                }
            }.show()


    }

    private fun dial(phone: String) {
        val callIntent = Intent().apply {
            action = Intent.ACTION_DIAL
            data = Uri.parse("tel:$phone")
        }

        starIntent(callIntent)
    }

    private fun goToWeb(website: String) {
        if (website.isEmpty()) {
            Toast.makeText(this, R.string.error_no_website, Toast.LENGTH_LONG).show()
        } else {
            val webIntent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(website)
            }
            starIntent(webIntent)
        }

    }

    private fun starIntent(intent: Intent){
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, R.string.main_error_resolve, Toast.LENGTH_LONG).show()
        }
    }

    private fun confirmDelete(storeEntity: StoreEntity) {
        MaterialAlertDialogBuilder(this).setTitle(getString(R.string.dialog_delete))
            .setPositiveButton(R.string.dialog_delete_confirm) { _, _ ->
                mMainViewModel.deleteStore(storeEntity)
            }
            .setNegativeButton(R.string.dialog_cancel) { _, _ ->
                null
            }.show()

    }

}