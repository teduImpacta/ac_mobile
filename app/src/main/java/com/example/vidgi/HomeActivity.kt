package com.example.vidgi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import com.example.vidgi.databinding.ActivityHomeBinding
import kotlin.concurrent.thread

class HomeActivity : AppCompatActivity() {
    private val context: Context get() = this
    private var jogos = listOf<Jogo>()

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        configuraMenuLateral()
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

        binding.layoutMenuLateral.addDrawerListener(toogle)
        toogle.syncState()

        binding.menuLateral.setNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_diciplinas -> {
                    Toast.makeText(this, "Clickou", Toast.LENGTH_SHORT).show()
                }
            }
            true
            }
        }

    fun taskJogos() {
        Thread {
            this.jogos = JogoService.getJogos()
            runOnUiThread {
                binding.recyclerJogo?.adapter = JogoAdapter(this.jogos) { onClickJogo(it) }
            }
        }.start()
    }

    fun onClickJogo(jogo: Jogo) {
        val intent = Intent(context, JogoActivity::class.java)

        intent.putExtra("game", jogo)
        startActivity(intent)
    }
}