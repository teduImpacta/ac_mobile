package com.example.vidgi

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class JogoAdapter(
    val jogos: List<Jogo>,
    val onClick: (Jogo) -> Unit
): RecyclerView.Adapter<JogoAdapter.JogosViewHolder>() {
    class  JogosViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cardName: TextView
        val cardDesc: TextView
        val cardImg: ImageView
        var cardProgress: ProgressBar
        var cardView: CardView

        init {
            cardName = view.findViewById<TextView>(R.id.cardNome)
            cardDesc = view.findViewById<TextView>(R.id.cardDescription)
            cardImg = view.findViewById<ImageView>(R.id.cardImg)
            cardProgress = view.findViewById<ProgressBar>(R.id.cardProgress)
            cardView = view.findViewById<CardView>(R.id.cardJogo)
        }
    }

    override fun getItemCount() = this.jogos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JogosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_jogo, parent, false)
        val holder = JogosViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: JogosViewHolder, position: Int) {
        val context = holder.itemView.context
        val jogo = jogos[position]

        holder.cardName.text = jogo.nome
        holder.cardDesc.text = jogo.descricao
        holder.cardProgress.visibility = View.VISIBLE

        Log.d("ad", "Jogo " + jogo.nome)

        Picasso.with(context).load(jogo.url).fit().into(holder.cardImg,
            object: com.squareup.picasso.Callback{
                override fun onSuccess() {
                    holder.cardProgress.visibility = View.GONE
                }

                override fun onError() {
                    holder.cardProgress.visibility = View.GONE
                }
            }
            )

        holder.itemView.setOnClickListener { onClick(jogo) }
    }
}