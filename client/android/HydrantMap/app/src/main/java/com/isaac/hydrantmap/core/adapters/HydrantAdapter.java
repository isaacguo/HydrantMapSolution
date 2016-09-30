package com.isaac.hydrantmap.core.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.isaac.hydrantmap.R;
import com.isaac.hydrantmap.model.Hydrant;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by yueguo01 on 11/20/2015.
 */
public class HydrantAdapter extends BaseAdapter
{
    Context context;
    protected List<Hydrant> listHydrants;
    LayoutInflater inflater;

    public void toggleIsEditable()
    {
        isEditable=!isEditable;
    }


    public boolean isEditable()
    {
        return isEditable;
    }

    boolean isEditable;

    public HydrantAdapter(Context context, List<Hydrant> listHydrants)
    {
        this.listHydrants = listHydrants;
        this.context = context;
        inflater = LayoutInflater.from(context);
        isEditable=false;
    }

    @Override
    public int getCount()
    {
        return listHydrants.size();
    }

    @Override
    public Object getItem(int i)
    {
        return listHydrants.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        ViewHolder viewHolder;
        if (view == null)
        {
            viewHolder = new ViewHolder();
            view = this.inflater.inflate(R.layout.hydrant_list_item, viewGroup, false);
            viewHolder.tvHydrant_item_name = (TextView) view.findViewById(R.id.hydrant_item_name);
            viewHolder.tvHydrant_item_location_desc = (TextView) view.findViewById(R.id.hydrant_item_location_desc);
            viewHolder.tvHydrant_item_unit = (TextView) view.findViewById(R.id.hydrant_item_unit);
            viewHolder.tvHydrant_item_state = (TextView) view.findViewById(R.id.hydrant_item_state);
            viewHolder.tvHydrant_item_district = (TextView) view.findViewById(R.id.hydrant_item_district);
            viewHolder.tvHydrant_item_water_pressure = (TextView) view.findViewById(R.id.hydrant_item_water_pressure);
            viewHolder.tvHydrant_item_maintenance_date = (TextView) view.findViewById(R.id.hydrant_item_maintenance_date);
            viewHolder.tvHydrant_item_pencil = (TextView) view.findViewById(R.id.hydrant_item_edit_pencil);
            if (isEditable)
                viewHolder.tvHydrant_item_pencil.setVisibility(View.VISIBLE);
            else
                viewHolder.tvHydrant_item_pencil.setVisibility(View.INVISIBLE);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();


        Hydrant hydrant = listHydrants.get(i);
        viewHolder.tvHydrant_item_name.setText(hydrant.getName());
        viewHolder.tvHydrant_item_unit.setText(hydrant.getUnit());
        viewHolder.tvHydrant_item_district.setText(hydrant.getDistrict());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        viewHolder.tvHydrant_item_water_pressure.setText(hydrant.getWaterPressure() + " MPa");
        if (hydrant.getLastMaintenanceDate() != null)
            viewHolder.tvHydrant_item_maintenance_date.setText(formatter.format(hydrant.getLastMaintenanceDate()));


        viewHolder.tvHydrant_item_location_desc.setText(hydrant.getLocationDesc());
        viewHolder.tvHydrant_item_state.setText(hydrant.getWorkingState());
        if (hydrant.getWorkingState().equals("可用"))
            viewHolder.tvHydrant_item_state.setTextColor(Color.parseColor("#ff82be1e"));
        else
            viewHolder.tvHydrant_item_state.setTextColor(Color.parseColor("#ffda4643"));


        return view;
    }

    private class ViewHolder
    {
        TextView tvHydrant_item_name;
        TextView tvHydrant_item_location_desc;
        TextView tvHydrant_item_unit;
        TextView tvHydrant_item_state;
        TextView tvHydrant_item_district;
        TextView tvHydrant_item_water_pressure;
        TextView tvHydrant_item_maintenance_date;
        TextView tvHydrant_item_pencil;
    }
}
