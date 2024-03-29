package edu.jcurley217ucla.nock

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ScoringRoundRecyclerAdapter(private var scoringRound: ScoringRound, onEndListener: ViewHolder.OnEndListener): RecyclerView.Adapter<ScoringRoundRecyclerAdapter.ViewHolder>() {

    val monEndListener = onEndListener

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return ViewHolder(inflater,p0,monEndListener)
    }

    override fun getItemCount(): Int {
        return scoringRound.ends
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(scoringRound,p1)
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup, onEndListener: OnEndListener) : RecyclerView.ViewHolder(inflater.inflate(R.layout.scores_adapter_view_layout, parent, false)), View.OnClickListener {
        override fun onClick(v: View?) {
            monEndListener.onEndClick(adapterPosition)
        }

        private var end: TextView? = null
        private var scores: TextView? = null
        private var endTotal: TextView? = null
        private var runningTotal: TextView? = null
        lateinit var monEndListener: OnEndListener

        init {
            end = itemView.findViewById(R.id.endNumber)
            scores = itemView.findViewById(R.id.arrowValues)
            endTotal = itemView.findViewById(R.id.endTotal)
            runningTotal = itemView.findViewById(R.id.runningTotal)
            monEndListener = onEndListener

            itemView.setOnClickListener(this)
        }

        fun bind(scoringRound: ScoringRound, curEnd: Int)
        {
            end?.text = (curEnd+1).toString()
            scores?.text = scoringRound.scores[curEnd].joinToString(separator = " ")
            endTotal?.text = scoringRound.computeEndTotal(curEnd).toString()
            runningTotal?.text = scoringRound.computeRunningTotal(curEnd).toString()
        }

        interface OnEndListener {
            fun onEndClick(position: Int)
        }
    }


}