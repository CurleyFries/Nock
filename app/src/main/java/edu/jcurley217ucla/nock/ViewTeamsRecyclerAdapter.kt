package edu.jcurley217ucla.nock

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class ViewTeamsRecyclerAdapter(private var teamList: ArrayList<TeamProfile>, onEndListener: ViewHolder.OnEndListener): RecyclerView.Adapter<ViewTeamsRecyclerAdapter.ViewHolder>() {

    val monEndListener = onEndListener

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return ViewHolder(inflater,p0,monEndListener)
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(teamList,p1)
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup, onEndListener: OnEndListener) : RecyclerView.ViewHolder(inflater.inflate(R.layout.team_adapter_view_layout, parent, false)), View.OnClickListener {
        override fun onClick(v: View?) {
            monEndListener.onEndClick(adapterPosition)
        }

        private var teamLogo: ImageView? = null
        private var teamName: TextView? = null
        private var teamDesc: TextView? = null
        lateinit var monEndListener: OnEndListener

        init {
            teamLogo = itemView.findViewById(R.id.teamLogo)
            teamName = itemView.findViewById(R.id.teamName)
            teamDesc = itemView.findViewById(R.id.teamDesc)
            monEndListener = onEndListener

            itemView.setOnClickListener(this)
        }

        fun bind(teamList: ArrayList<TeamProfile>, index: Int)
        {
            teamName?.text = teamList[index].teamName
            teamDesc?.text = teamList[index].teamDesc
        }

        interface OnEndListener {
            fun onEndClick(position: Int)
        }
    }


}