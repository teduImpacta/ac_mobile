package com.example.vidgi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vidgi.databinding.ActivityHomeBinding
import kotlin.concurrent.thread

class HomeActivity : AppCompatActivity() {
    private val context: Context get() = this
    private var jogos = listOf<Jogo>()
    private var REQUEST_CADASTRO = 1
    private var REQUEST_REMOVE= 2

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        configuraMenuLateral()

        binding.recyclerJogo?.layoutManager = LinearLayoutManager(context)
        binding.recyclerJogo?.itemAnimator = DefaultItemAnimator()
        binding.recyclerJogo?.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()
        taskJogos()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        (menu?.findItem(R.id.search)?.actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })

        return true
    }

    private fun configuraMenuLateral() {
        var toogle = ActionBarDrawerToggle(
            this,
            binding.layoutMenuLateral,
            binding.toolbarInclude.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        val intent = Intent(context, HomeActivity::class.java)

        binding.layoutMenuLateral.addDrawerListener(toogle)
        toogle.syncState()

        binding.menuLateral.setNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_diciplinas -> {
                    Toast.makeText(this, "Go to Home", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                }
            }
            binding.layoutMenuLateral.closeDrawer(GravityCompat.START)
            true
            }
        }

    fun taskJogos() {
        Thread {
            this.jogos = JogoService.getJogos()
            runOnUiThread {
                binding.recyclerJogo?.adapter = JogoAdapter(this.jogos) { onClickJogo(it) }
                sendNotification(this.jogos.get(0))
            }
        }.start()
    }

    fun sendNotification(game: Jogo) {
        val intent = Intent(this, HomeActivity::class.java)
        NotificationUtil.create(1, intent, "Vidgi Games", "Você tem um novo jogo: ${game.nome}")
    }

    fun onClickJogo(jogo: Jogo) {
        val intent = Intent(context, JogoActivity::class.java)

        intent.putExtra("game", jogo)
        startActivityForResult(intent, REQUEST_REMOVE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CADASTRO || requestCode == REQUEST_REMOVE) {
            taskJogos()
        }
    }
}