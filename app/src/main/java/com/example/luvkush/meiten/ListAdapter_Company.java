package com.example.luvkush.meiten;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter_Company extends BaseAdapter {

    Context context;
    List<CompanyStructure> valueList;
    public ListAdapter_Company(List<CompanyStructure> listValue, Context context)
    {
        this.context = context;
        this.valueList = listValue;
    }

    @Override
    public int getCount()
    {
        return this.valueList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.valueList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewItem viewItem = null;
        if(convertView == null)
        {
            viewItem = new ViewItem();
            LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInfiater.inflate(R.layout.company_structure, null);

            viewItem.txtID = (TextView) convertView.findViewById(R.id.adapter_text_ID);
            viewItem.txtTitle = (TextView) convertView.findViewById(R.id.adapter_text_title);
            viewItem.txtDescription = (TextView) convertView.findViewById(R.id.adapter_text_description);
            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItem) convertView.getTag();
        }

        viewItem.txtID.setText(valueList.get(position).ID);
        viewItem.txtTitle.setText(valueList.get(position).CompanyName);
        viewItem.txtDescription.setText(valueList.get(position).CompanyDetails);

        return convertView;
    }
}

class ViewItem
{
    TextView txtID;
    TextView txtTitle;
    TextView txtDescription;
}
