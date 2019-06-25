package edu.jcurley217ucla.nock

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

class ScoringRoundRecyclerAdapter(private var scoringRound: ScoringRound): RecyclerView.Adapter<ScoringRoundRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val inflater = LayoutInflater.from(p0.context)
        return ViewHolder(inflater,p0)
    }

    override fun getItemCount(): Int {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return scoringRound.ends
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        p0.bind(scoringRound,p1)
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.scores_adapter_view_layout, parent, false)) {
        private var end: TextView? = null
        private var scores: TextView? = null
        private var endTotal: TextView? = null
        private var runningTotal: TextView? = null

        init {
            end = itemView.findViewById(R.id.endNumber)
            scores = itemView.findViewById(R.id.arrowValues)
            endTotal = itemView.findViewById(R.id.endTotal)
            runningTotal = itemView.findViewById(R.id.runningTotal)
        }

        fun bind(scoringRound: ScoringRound, curEnd: Int)
        {
            end?.text = (curEnd+1).toString()
            scores?.text = scoringRound.scores[curEnd].joinToString()
            endTotal?.text = scoringRound.computeEndTotal(curEnd).toString()
            runningTotal?.text = scoringRound.computeRunningTotal(curEnd).toString()
        }
    }
}