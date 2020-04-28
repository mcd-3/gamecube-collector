package com.matthew.carvalhodagenais.gamecubecollector

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.matthew.carvalhodagenais.gamecubecollector.factories.AccessoryViewModelFactory
import com.matthew.carvalhodagenais.gamecubecollector.factories.ConsoleViewModelFactory
import com.matthew.carvalhodagenais.gamecubecollector.factories.GameViewModelFactory
import com.matthew.carvalhodagenais.gamecubecollector.factories.SettingsViewModelFactory
import com.matthew.carvalhodagenais.gamecubecollector.ui.ImageSelectDialogFragment
import com.matthew.carvalhodagenais.gamecubecollector.viewmodels.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_accessory_add_edit.*
import kotlinx.android.synthetic.main.fragment_console_add_edit.*
import kotlinx.android.synthetic.main.fragment_game_add_edit.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), ImageSelectDialogFragment.ImageSelectDialogInterface {

    private lateinit var gameDetailViewModel: GameDetailViewModel
    private lateinit var gameListViewModel: GameListViewModel
    private lateinit var gameAddEditViewModel: GameAddEditViewModel
    private lateinit var consoleListViewModel: ConsoleListViewModel
    private lateinit var consoleDetailViewModel: ConsoleDetailViewModel
    private lateinit var consoleAddEditViewModel: ConsoleAddEditViewModel
    private lateinit var accessoryListViewModel: AccessoryListViewModel
    private lateinit var accessoryDetailViewModel: AccessoryDetailViewModel
    private lateinit var accessoryAddEditViewModel: AccessoryAddEditViewModel
    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {

        // Set the theme
        this.setTheme()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Initialise ViewModels
        initViewModels()

        //Set NavigationDrawer
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfig = AppBarConfiguration(setOf(
            R.id.nav_games, R.id.nav_favourites, R.id.nav_consoles, R.id.nav_accessories, R.id.nav_settings, R.id.nav_about
        ), main_activity_drawer_layout)
        setupActionBarWithNavController(navController, appBarConfig)
        main_activity_navigation_view.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

    /**
     * Changes the bitmap for the cover image
     */
    override fun changeImageBitmap(bitmap: Bitmap) {
        val imgView = when {
            findViewById<ImageView>(R.id.cover_art_image_view) != null
            -> findViewById<ImageView>(R.id.cover_art_image_view)
            findViewById<ImageView>(R.id.console_image_view) != null
            -> findViewById<ImageView>(R.id.console_image_view)
            findViewById<ImageView>(R.id.accessory_image_view) != null
            -> findViewById<ImageView>(R.id.accessory_image_view)
            else -> null
        }
        if (imgView != null) {
            Glide.with(this)
                .load(bitmap)
                .skipMemoryCache(true)
                .into(imgView)
        }
    }

    /**
     * Gets the GameDetailViewModel
     */
    fun getGameDetailViewModel(): GameDetailViewModel = gameDetailViewModel


    /**
     * Gets the GameListViewModel
     */
    fun getGameListViewModel(): GameListViewModel = gameListViewModel

    /**
     * Gets the GameAddEditViewModel
     */
    fun getGameAddEditViewModel(): GameAddEditViewModel = gameAddEditViewModel

    /**
     * Gets the ConsoleListViewModel
     */
    fun getConsoleListViewModel(): ConsoleListViewModel = consoleListViewModel

    /**
     * Gets the ConsoleDetailViewModel
     */
    fun getConsoleDetailViewModel(): ConsoleDetailViewModel = consoleDetailViewModel

    /**
     * Gets the ConsoleAddEditViewModel
     */
    fun getConsoleAddEditViewModel(): ConsoleAddEditViewModel = consoleAddEditViewModel

    /**
     * Gets the AccessoryListViewModel
     */
    fun getAccessoryListViewModel(): AccessoryListViewModel = accessoryListViewModel

    /**
     * Gets the AccessoryDetailViewModel
     */
    fun getAccessoryDetailViewModel(): AccessoryDetailViewModel = accessoryDetailViewModel

    /**
     * Gets the AccessoryAddEditViewModel
     */
    fun getAccessoryAddEditViewModel(): AccessoryAddEditViewModel = accessoryAddEditViewModel

    /**
     * Gets the SettingsViewModel
     */
    fun getSettingsViewModel(): SettingsViewModel = settingsViewModel

    /**
     * Initialises all the ViewModels associated with this activity. Each ViewModel will be used
     * in its respective Fragment
     */
    private fun initViewModels() {
        gameListViewModel = ViewModelProvider(this, GameViewModelFactory(this.application))
            .get(GameListViewModel::class.java)
        gameDetailViewModel = ViewModelProvider(this, GameViewModelFactory(this.application)
        ).get(GameDetailViewModel::class.java)
        gameAddEditViewModel = ViewModelProvider(this, GameViewModelFactory(this.application))
            .get(GameAddEditViewModel::class.java)
        consoleListViewModel = ViewModelProvider(this, ConsoleViewModelFactory(this.application))
            .get(ConsoleListViewModel::class.java)
        consoleDetailViewModel = ViewModelProvider(this, ConsoleViewModelFactory(this.application))
            .get(ConsoleDetailViewModel::class.java)
        consoleAddEditViewModel = ViewModelProvider(this, ConsoleViewModelFactory(this.application))
            .get(ConsoleAddEditViewModel::class.java)
        accessoryListViewModel = ViewModelProvider(this, AccessoryViewModelFactory(this.application))
            .get(AccessoryListViewModel::class.java)
        accessoryDetailViewModel = ViewModelProvider(this, AccessoryViewModelFactory(this.application))
            .get(AccessoryDetailViewModel::class.java)
        accessoryAddEditViewModel = ViewModelProvider(this, AccessoryViewModelFactory(this.application))
            .get(AccessoryAddEditViewModel::class.java)
        settingsViewModel = ViewModelProvider(this, SettingsViewModelFactory(this.application))
            .get(SettingsViewModel::class.java)
    }

    /**
     * Changes the theme based on SharedPreferences
     */
    private fun setTheme() {
        val sharedPrefs = getSharedPreferences(getString(R.string.shared_preference_key), Context.MODE_PRIVATE)
        val theme = sharedPrefs.getInt(
            getString(R.string.shared_preference_theme_key),
            getString(R.string.shared_preference_theme_cubed).toInt())
        when (theme) {
            getString(R.string.shared_preference_theme_cubed).toInt() -> setTheme(R.style.CubedTheme)
            getString(R.string.shared_preference_theme_cubed_dark).toInt() -> setTheme(R.style.CubedDarkTheme)
            getString(R.string.shared_preference_theme_cubed_night).toInt() -> setTheme(R.style.NightTheme)
            getString(R.string.shared_preference_theme_cubed_dolphin).toInt() -> setTheme(R.style.DolphinTheme)
            getString(R.string.shared_preference_theme_cubed_ps).toInt() -> setTheme(R.style.PlayerSelection)
            getString(R.string.shared_preference_theme_cubed_pss).toInt() -> setTheme(R.style.PlayerSelectionSilver)
            else -> setTheme(R.style.CubedTheme)
        }
    }
}
