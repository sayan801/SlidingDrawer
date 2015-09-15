package technicise.com.demoslidingdrawerapp;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by technicise on 2/4/15.
 */
public class ListAdapter extends BaseAdapter {


    private Context activity;
    private ArrayList<DataModel> dataArray;

    public ListAdapter(Context activity, ArrayList<DataModel> dataList)
    {
        this.activity =	activity;
        dataArray = dataList;

    }


    public void notifyData(){
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataArray.size();
    }

    @Override
    public Object getItem(int position) {
        return dataArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        LayoutInflater inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;


        if(convertView == null){

            holder = new ViewHolder();

           /*==================== Get User Device Height & Width ==================*/
            DisplayMetrics metrics = activity.getResources().getDisplayMetrics();

            int Total_Width = metrics.widthPixels;
            int FinalHeight=(Total_Width/4);

           /*==================== User Device Height & Width Functionality Ends Here ==================*/


            convertView = inflater.from(activity).inflate(R.layout.community_common_tab_layout, null);

            holder.DashBoard_Tab_List_Root=(LinearLayout)convertView.findViewById(R.id.Community_Common_Root);

            holder.LL_First_Invisible=(LinearLayout)convertView.findViewById(R.id.First_Invisible_Layout);
            holder.LL_First_Invisible.getLayoutParams().width=Total_Width*5/100;
            holder.LL_First_Invisible.getLayoutParams().height=FinalHeight;

            holder.LL_UserHomeIcon=(LinearLayout)convertView.findViewById(R.id.UserHomeIconLayout);
            holder.LL_UserHomeIcon.getLayoutParams().width=Total_Width*15/100;

            holder.LL_Second_Invisible=(LinearLayout)convertView.findViewById(R.id.Second_Invisible_Layout);
            holder.LL_Second_Invisible.getLayoutParams().width=Total_Width*5/100;

            holder.LL_User_Details=(LinearLayout)convertView.findViewById(R.id.User_Details_Layout);
            holder.LL_User_Details.getLayoutParams().width= (int) (Total_Width*52.50/100);

            holder.LL_Third_Invisible=(LinearLayout)convertView.findViewById(R.id.Third_Invisible_Layout);
            holder.LL_Third_Invisible.getLayoutParams().width=Total_Width*5/100;

            holder.LL_Badge=(LinearLayout)convertView.findViewById(R.id.Badge_Layout);
            holder.LL_Badge.getLayoutParams().width= (int) (Total_Width*7.10/100);

            holder.LL_Four_Invisible=(LinearLayout)convertView.findViewById(R.id.Four_Invisible_Layout);
            holder.LL_Four_Invisible.getLayoutParams().width= (int) (Total_Width*1.875/100);

            holder.LL_Right_Arrow=(LinearLayout)convertView.findViewById(R.id.Right_Arrow_Layout);
            holder.LL_Right_Arrow.getLayoutParams().width= (int) (Total_Width*6.875/100);

            holder.LL_Five_Invisible=(LinearLayout)convertView.findViewById(R.id.Five_Invisible_Layout);
            holder.LL_Five_Invisible.getLayoutParams().width= (int) (Total_Width*1.55/100);

            holder.Member_Date = (TextView) convertView.findViewById(R.id.from);
            holder.Member_Title=(TextView)convertView.findViewById(R.id.subject);
            holder.Member_Details=(TextView)convertView.findViewById(R.id.date);
            holder.Member_Icons=(ImageView)convertView.findViewById(R.id.pic);


            holder.First_Top_Line=(ImageView)convertView.findViewById(R.id.Image_Top_Line);
            holder.First_Top_Line.setVisibility(View.INVISIBLE);

            holder.Second_Bottom_Line=(ImageView)convertView.findViewById(R.id.Image_Bottom_Line);
            holder.Second_Bottom_Line.setVisibility(View.INVISIBLE);

            holder.ImageView_Circle=(ImageView)convertView.findViewById(R.id.ImageCircle);
            holder.ImageView_Circle.setVisibility(View.INVISIBLE);

            holder.Tv_Badge_View=(TextView)convertView.findViewById(R.id.TV_BadgeNotification);
            holder.Tv_Badge_View.setVisibility(View.INVISIBLE);

            holder.Move_To_Next=(ImageView)convertView.findViewById(R.id.ImageView_Move_Next);
            holder.Move_To_Next.setVisibility(View.INVISIBLE);

            convertView.setTag(holder);


        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        String getMemberIcons =dataArray.get(position).Member_Icons;
        holder.Member_Date.setText( dataArray.get(position).Member_Date);
        holder.Member_Title.setText( dataArray.get(position).Member_Title);
        holder.Member_Details.setText(dataArray.get(position).Member_Details);
//        holder.Member_Icons.setImageDrawable(activity.getResources().getDrawable(Integer.parseInt(getMemberIcons)));

        return convertView;

    }

    public static class ViewHolder {

        TextView Member_Date,Member_Title,Member_Details;

        public LinearLayout LL_First_Invisible,LL_UserHomeIcon,LL_Second_Invisible,LL_User_Details,LL_Third_Invisible,LL_Badge,LL_Four_Invisible,
                                LL_Right_Arrow,LL_Five_Invisible,DashBoard_Tab_List_Root;

        public ImageView First_Top_Line,Second_Bottom_Line,ImageView_Circle,Move_To_Next,Member_Icons;
        public TextView Tv_Badge_View;



    }
}

