package edu.jcurley217ucla.nock

import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.DecimalFormat

class ViewPresetsRecyclerAdapter(private var presetList: ArrayList<Preset>, onEndListener: ViewHolder.OnEndListener): RecyclerView.Adapter<ViewPresetsRecyclerAdapter.ViewHolder>() {

    val monEndListener = onEndListener
    lateinit var dbHelper : DatabaseHelper
    lateinit var recentlyDeletedPreset: Preset
    var recentlyDeletedPosition: Int = -1

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        dbHelper = DatabaseHelper(p0.context)
        return ViewHolder(inflater,p0,monEndListener)
    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder) {
        recentlyDeletedPosition=viewHolder.adapterPosition
        recentlyDeletedPreset=presetList.get(recentlyDeletedPosition)

        dbHelper.deletePreset(presetList.get(viewHolder.adapterPosition).id)
        presetList.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)

        Snackbar.make(viewHolder.itemView, "Scoring Round removed.", Snackbar.LENGTH_LONG).setAction("UNDO") {
            dbHelper.insertPreset(recentlyDeletedPreset)
            presetList.add(recentlyDeletedPosition, recentlyDeletedPreset)
            notifyItemInserted(recentlyDeletedPosition)
        }.show()
    }

    override fun getItemCount(): Int {
        return presetList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(presetList,p1)
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup, onEndListener: OnEndListener) : RecyclerView.ViewHolder(inflater.inflate(R.layout.preset_adapter_view_layout, parent, false)), View.OnClickListener {
        override fun onClick(v: View?) {
            monEndListener.onEndClick(adapterPosition)
        }

        private var division: TextView? = null
        private var distance: TextView? = null
        private var targetSize: TextView? = null
        private var numEnds: TextView? = null
        private var arrowsPerEnd: TextView? = null
        var monEndListener: OnEndListener

        init {
            division = itemView.findViewById(R.id.division)
            distance = itemView.findViewById(R.id.distance)
            targetSize = itemView.findViewById(R.id.targetSize)
            numEnds = itemView.findViewById(R.id.ends)
            arrowsPerEnd = itemView.findViewById(R.id.arrowsPerEnd)
            monEndListener = onEndListener

            itemView.setOnClickListener(this)
        }

        fun bind(presetList: ArrayList<Preset>, index: Int)
        {
            division?.text = presetList[index].division
            distance?.text = presetList[index].distance
            targetSize?.text = presetList[index].targetSize
            numEnds?.text = presetList[index].ends.toString()
            arrowsPerEnd?.text = presetList[index].arrowsPerEnd.toString()
        }

        interface OnEndListener {
            fun onEndClick(position: Int)
        }
    }


}