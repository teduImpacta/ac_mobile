package com.example.vidgi

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.example.vidgi.databinding.ActivityJogoBinding
import com.squareup.picasso.Picasso

class JogoActivity : AppCompatActivity() {
    private val context: Context get() = this
    var jogo: Jogo? = null

    private val binding by lazy {
        ActivityJogoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarInclude.toolbar)

        jogo = intent.getSerializableExtra("game") as Jogo

        binding.gameName.setText(jogo?.nome)

        Picasso.with(context).load(jogo?.url).fit().into(binding.gameImage,
        object: com.squareup.picasso.Callback{
            override fun onError() {

            }

            override fun onSuccess() {

            }
        }
            )



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item?.itemId

        if (id == R.id.action_remover) {
            AlertDialog.Builder(this)
                .setTitle("Deseja excluir?")
                .setMessage("Deseja excluir permanentemente esse game")
                .setPositiveButton("Sim") {
                    dialog, which ->
                        dialog.dismiss()
                        taskRemove()
                }.setNegativeButton("Não"){
                    dialog, which -> dialog.dismiss()
                }.create().show()
        } else if (id == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    fun taskRemove() {
        if (this.jogo != null && this.jogo is Jogo) {
            Thread {
                JogoService.delete(this.jogo as Jogo)

                runOnUiThread {
                    finish()
                }
            }.start()
        }
    }
}