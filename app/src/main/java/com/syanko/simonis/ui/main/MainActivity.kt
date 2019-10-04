package com.syanko.simonis.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.syanko.simonis.R
import com.syanko.simonis.domain.entity.User
import com.syanko.simonis.ui.form.FormFragment
import com.syanko.simonis.ui.form.content.FormContentFragment
import com.syanko.simonis.ui.form.section.FormSectionFragment
import com.syanko.simonis.ui.login.LoginActivity
import com.syanko.simonis.ui.main.equipment.EquipmentFragment
import com.syanko.simonis.ui.main.inspection.InspectionFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.content_fragment.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MenuItemClickListener, FormItemSelectedListerner {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel

    private lateinit var activeUser: User
    private var activeFormSessionId: Long = 0
    private var activeForm: MainViewObject.FormViewObject? = null

    override fun onMenuItemClicked(item: MainViewObject) {
        when (item) {
            is MainViewObject.MenuViewObject -> {
                if (item.children.isNotEmpty()) {
                    runChildFragment(item.children, item.label)
                } else {
                    runEquipmentFragment(item.id)
                }
            }
            is MainViewObject.EquipmentViewObject -> {
                runInspectionFragment(item.id)
            }
            is MainViewObject.InspectionViewObject -> {
                runFormFragment(item.id)
            }
            is MainViewObject.SectionViewObject -> {
                if (item.children.isNotEmpty()) {
                    runFormSectionFragment(item.formTitle, item.title, item.children)
                } else {
                    runFormContentFragment(
                        activeFormSessionId,
                        item.formTitle,
                        item.title,
                        item.fields
                    )
                }
            }
        }
    }

    override fun onFormSelected(item: MainViewObject.FormViewObject) {
        showDialogOpenForm(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]

        setSupportActionBar(toolbar)

        viewModel.logout.observe(this, Observer { logoutSuccess ->
            if (logoutSuccess) {
                navigateToLoginActivity()
            }
        })
        viewModel.activeUser.observe(this, Observer {
            activeUser = it
            runMainFragment()
        })
        viewModel.activeFormSession.observe(this, Observer { formSessionId ->
            if (formSessionId > 0) {
                activeFormSessionId = formSessionId
                activeForm?.let {
                    runFormSectionFragment(it.title, "", it.sections)
                }
            }
        })
        viewModel.isSessionEnded.observe(this, Observer { isEnded ->
            if (isEnded) {
                activeForm = null
                activeFormSessionId = -1
                onBackPressed()
            }
        })
        viewModel.initiateActiveUser()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = MenuInflater(this)
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                viewModel.logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (activeForm != null) {
            showDialogCloseForm()
        } else {
            super.onBackPressed()
        }
    }

    private fun navigateToLoginActivity() {
        val intent = LoginActivity.createIntent(this)
        startActivity(intent)
        finish()
    }

    private fun endFormSession() {
        viewModel.deleteActiveFormSession(activeFormSessionId)
    }

    private fun runMainFragment() {
        val fragment = MenuFragment.createFragment()
        replaceFragment(fragment, false)
    }

    private fun runChildFragment(items: List<MainViewObject.MenuViewObject>, parent: String) {
        val fragment = MenuFragment.createFragmentWithItem(items, parent)
        replaceFragment(fragment, true)
    }

    private fun runEquipmentFragment(groupId: Int) {
        val fragment = EquipmentFragment.createFragment(groupId)
        replaceFragment(fragment, true)
    }

    private fun runInspectionFragment(equipmentId: Int) {
        val fragment = InspectionFragment.createFragment(equipmentId)
        replaceFragment(fragment, true)
    }

    private fun runFormFragment(id: Int) {
        val fragment = FormFragment.createFragment(id)
        replaceFragment(fragment, true)
    }

    private fun runFormSectionFragment(
        formTitle: String,
        parent: String,
        item: List<MainViewObject.SectionViewObject>
    ) {
        val fragment = FormSectionFragment.createFragment(formTitle, parent, item)
        replaceFragment(fragment, true)
    }

    private fun runFormContentFragment(
        formSessionId: Long,
        formTitle: String,
        sectionTitle: String,
        items: List<MainViewObject.FormFieldViewObject>
    ) {
        val fragment = FormContentFragment.createFragment(
            activeUser.id,
            formSessionId,
            formTitle,
            sectionTitle,
            items
        )
        replaceFragment(fragment, true)
    }

    private fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    private fun showDialogOpenForm(item: MainViewObject.FormViewObject) {
        val dialog: AlertDialog
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Lanjutkan Inspeksi ${item.voltageName} Pada ${item.bayName}?")
        builder.setPositiveButton("Lanjutkan") { activeDialog, _ ->
            activeDialog.dismiss()
            activeForm = item
            viewModel.saveFormSession(activeUser.id, item)
            runFormSectionFragment(item.title, "", item.sections)
        }
        builder.setNegativeButton("Tutup") { activeDialog, _ ->
            activeDialog.dismiss()
        }

        dialog = builder.create()
        dialog.show()
    }

    private fun showDialogCloseForm() {
        val dialog: AlertDialog
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Batalkan Inspeksi ${activeForm!!.voltageName} Pada ${activeForm!!.bayName}?")
        builder.setPositiveButton("Lanjutkan") { activeDialog, _ ->
            activeDialog.dismiss()
            endFormSession()
        }
        builder.setNegativeButton("Tutup") { activeDialog, _ ->
            activeDialog.dismiss()
        }

        dialog = builder.create()
        dialog.show()
    }

    companion object {
        const val ACTIVE_USER_ID_EXTRA = ".active_user_id_extra"

        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}

interface MenuItemClickListener {
    fun onMenuItemClicked(item: MainViewObject)
}

interface FormItemSelectedListerner {
    fun onFormSelected(item: MainViewObject.FormViewObject)
}
