package com.example.learnproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

/**
 * <pre>
 *     @author : hpf
 *     time   : 2020/12/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class MainRecyAdapter(private val list: MutableList<String>, private val btClick: ((Int) -> Unit)? = null) : RecyclerView.Adapter<MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_base, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bt.text = list[position]
        holder.bt.setOnClickListener {
            btClick?.invoke(position)
        }
    }
}

class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val bt = view.findViewById<Button>(R.id.itemBt)
}
