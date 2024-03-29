package edu.jcurley217ucla.nock

import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.DecimalFormat

class PreviousScoringRecyclerAdapter(private var scoringRoundList: ArrayList<ScoringRound>, onEndListener: ViewHolder.OnEndListener): RecyclerView.Adapter<PreviousScoringRecyclerAdapter.ViewHolder>() {

    val monEndListener = onEndListener
    lateinit var dbHelper: DatabaseHelper
    lateinit var recentlyDeletedScoringRound: ScoringRound
    var recentlyDeletedPosition: Int = -1

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        dbHelper = DatabaseHelper(p0.context)
        return ViewHolder(inflater,p0,monEndListener)
    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder) {
        recentlyDeletedPosition = viewHolder.adapterPosition
        recentlyDeletedScoringRound = scoringRoundList.get(viewHolder.adapterPosition)

        dbHelper.deleteScoringRound(scoringRoundList.get(viewHolder.adapterPosition).id)
        scoringRoundList.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)

        Snackbar.make(viewHolder.itemView, "Scoring Round removed.", Snackbar.LENGTH_LONG).setAction("UNDO") {
            dbHelper.insertScoringRound(recentlyDeletedScoringRound)
            scoringRoundList.add(recentlyDeletedPosition, recentlyDeletedScoringRound)
            notifyItemInserted(recentlyDeletedPosition)
        }.show()
    }

    override fun getItemCount(): Int {
        return scoringRoundList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(scoringRoundList,p1)
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup, onEndListener: OnEndListener) : RecyclerView.ViewHolder(inflater.inflate(R.layout.previous_scores_adapter_view_layout, parent, false)), View.OnClickListener {
        override fun onClick(v: View?) {
            monEndListener.onEndClick(adapterPosition)
        }

        private var date: TextView? = null
        private var totalScore: TextView? = null
        private var averageArrow: TextView? = null
        private var division: TextView? = null
        private var distance: TextView? = null
        private var targetSize: TextView? = null
        private var numEnds: TextView? = null
        private var arrowsPerEnd: TextView? = null
        lateinit var monEndListener: OnEndListener

        init {
            date = itemView.findViewById(R.id.date)
            totalScore = itemView.findViewById(R.id.totalScore)
            averageArrow = itemView.findViewById(R.id.averageArrow)
            division = itemView.findViewById(R.id.division)
            distance = itemView.findViewById(R.id.distance)
            targetSize = itemView.findViewById(R.id.targetSize)
            numEnds = itemView.findViewById(R.id.numEnds)
            arrowsPerEnd = itemView.findViewById(R.id.arrowsPerEnd)
            monEndListener = onEndListener

            itemView.setOnClickListener(this)
        }

        fun bind(scoringRoundList: ArrayList<ScoringRound>, index: Int)
        {
            date?.text = scoringRoundList[index].date
            totalScore?.text = scoringRoundList[index].computeRunningTotal(scoringRoundList[index].ends-1).toString()
            var computedScore = (totalScore!!.text.toString().toInt()/(scoringRoundList[index].ends.toDouble()*scoringRoundList[index].arrowsPerEnd))
            val df = DecimalFormat("#.00")
            val finalScore= df.format(computedScore)
            averageArrow?.text = finalScore.toString()
            division?.text = scoringRoundList[index].division
            distance?.text = scoringRoundList[index].distance
            targetSize?.text = scoringRoundList[index].targetSize
            numEnds?.text = scoringRoundList[index].ends.toString()
            arrowsPerEnd?.text = scoringRoundList[index].arrowsPerEnd.toString()
        }

        interface OnEndListener {
            fun onEndClick(position: Int)
        }
    }


}