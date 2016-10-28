package com.judy.notesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;;
import java.util.List;

/**
 * Created by Judy T Raj on 25-10-2016.
 */

public class myAdapter extends ArrayAdapter<Note>{

    private static class ViewHolder {
        private TextView itemView;
    }

    public myAdapter(Context context, int textViewResourceId, List<Note> items) {
        super(context, textViewResourceId, items);
    }
    ViewHolder viewHolder;

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.row_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemView = (TextView) convertView.findViewById(R.id.text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Note item = getItem(position);
        if (item!= null) {

            viewHolder.itemView.setText(String.format("%s", item.note));
        }

        return convertView;
    }

}
