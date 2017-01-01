package com.example.friendradar;

import java.util.List;

import com.example.friendradar.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class PeopleListAdapter extends ArrayAdapter<People> {
	private int resourceID;
	private List<People> peoplelist;

	public PeopleListAdapter(Context context, int resource,List<People> peoplelist) {
		super(context, resource, peoplelist);
		resourceID = resource;
		this.peoplelist=peoplelist;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		People people = getItem(position);
		View view;
		ViewHolder viewholder;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceID, null);

			viewholder = new ViewHolder();
			viewholder.name = (TextView) view.findViewById(R.id.name_cell);
			viewholder.button_delete = (Button) view
					.findViewById(R.id.delete_button_cell);
			view.setTag(viewholder);
		} else {
			view = convertView;
			viewholder = (ViewHolder) view.getTag();
		}
		viewholder.name.setText(people.getName());
		viewholder.button_delete.setOnClickListener(new OnClickListener() {
			@SuppressLint("InflateParams")
			@Override
			public void onClick(View arg0) {
				View dialogview = null;
				Button ok;
				Button close;
				TextView phonenum;

				if (resourceID == R.layout.friends_list_item) {
					dialogview = LayoutInflater.from(getContext()).inflate(
							R.layout.dialog_delete_friend, null);
				} else if (resourceID == R.layout.enemies_list_item) {
					dialogview = LayoutInflater.from(getContext()).inflate(
							R.layout.dialog_delete_enemy, null);
				}
				ok = (Button) dialogview.findViewById(R.id.btn_dialog_ok);
				close = (Button) dialogview.findViewById(R.id.btn_dialog_close);
				phonenum = (TextView) dialogview.findViewById(R.id.txt_number);

				AlertDialog.Builder builder = new AlertDialog.Builder(
						getContext());
				builder.setView(dialogview);
				final AlertDialog dialog = builder.create();

				phonenum.setText(peoplelist.get(position).getName() + ": "
						+ peoplelist.get(position).getPhoneNum());

				close.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
					}
				});

				ok.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						peoplelist.remove(position);
						notifyDataSetChanged();
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		});
		return view;
	}
}

class ViewHolder {
	TextView name;
	Button button_delete;
}