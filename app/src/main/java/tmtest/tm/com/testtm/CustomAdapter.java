package tmtest.tm.com.testtm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<WebsiteModel> implements View.OnClickListener{

    private ArrayList<WebsiteModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtDate;
        TextView txtVisit;
    }

    public CustomAdapter(ArrayList<WebsiteModel> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        WebsiteModel dataModel=(WebsiteModel)object;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        WebsiteModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent,false);

            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.date);
            viewHolder.txtVisit = (TextView) convertView.findViewById(R.id.visit);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }



        viewHolder.txtName.setText(dataModel.getWebsite_name());
        viewHolder.txtDate.setText(dataModel.getVisit_date());
        viewHolder.txtVisit.setText(dataModel.getTotal_visits()+"");
        // Return the completed view to render on screen
        return convertView;
    }
}