import androidx.cardview.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList
import com.example.trip_planner_andrid_app.R
import com.example.trip_planner_andrid_app.Seat
import java.nio.file.Files.size

class SeatIdAdapterTwoWay(private val seatIds: ArrayList<Seat>) : RecyclerView.Adapter<SeatIdAdapterTwoWay.MyViewHolderTwoWay>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderTwoWay {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.seat_ids_card_view, parent, false)
        return MyViewHolderTwoWay(view)
    }

    override fun onBindViewHolder(holder: SeatIdAdapterTwoWay.MyViewHolderTwoWay, position: Int) {
        holder.seat_id.text = seatIds[position].id
        holder.seat_class.text = seatIds[position].seatClass
    }


    override fun getItemCount(): Int {
        return seatIds.size
    }

    inner class MyViewHolderTwoWay(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var seat_id: TextView
        internal var seat_class: TextView

        init {
            seat_id = itemView.findViewById<View>(R.id.seat_ids) as TextView
            seat_class = itemView.findViewById<View>(R.id.seat_class) as TextView
        }
    }

}