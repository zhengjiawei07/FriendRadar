package com.example.friendradar;

import java.util.ArrayList;
import java.util.List;

import com.example.friendradar.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class EnemiesListActivity extends Activity {
	ListView listview;
	Button radar;
	Button friend;
	Button add;
	ToggleButton edit_or_done;

	List<People> list;
	PeopleListAdapter peopleadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.enemies_list);

		listview = (ListView) findViewById(R.id.lvw_enemies_list);
		radar = (Button) findViewById(R.id.btn_enemies_list_radar);
		friend = (Button) findViewById(R.id.btn_enemies_list_friends);
		add = (Button) findViewById(R.id.btn_enemies_list_add);
		edit_or_done = (ToggleButton) findViewById(R.id.btn_enemies_list_edit);

		list = new ArrayList<People>();
		peopleadapter = new PeopleListAdapter(this, R.layout.enemies_list_item,
				list);

		listview.setAdapter(peopleadapter);

		radar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(EnemiesListActivity.this,
						MainActivity.class));
			}
		});

		friend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(EnemiesListActivity.this,
						FriendsListActivity.class));
			}
		});

		add.setOnClickListener(new OnClickListener() {
			@SuppressLint("InflateParams")
			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						EnemiesListActivity.this);
				View dialogview = LayoutInflater.from(EnemiesListActivity.this)
						.inflate(R.layout.dialog_add_enemy, null);
				Button close = (Button) dialogview
						.findViewById(R.id.btn_dialog_close);
				Button add = (Button) dialogview
						.findViewById(R.id.btn_dialog_ok);
				final EditText etxt_number = (EditText) dialogview
						.findViewById(R.id.txt_enemy_number);
				final EditText etxt_name = (EditText) dialogview
						.findViewById(R.id.txt_enemy_name);
				builder.setView(dialogview);
				final AlertDialog dialog = builder.create();
				close.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
					}

				});
				add.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						String name = etxt_name.getText().toString();
						String number = etxt_number.getText().toString();
						if (!name.equals("") && !number.equals("")) {
							for (People temp : list) {
								if (temp.getPhoneNum().equals(number)) {
									Toast.makeText(
											getBaseContext(),
											"The phone number you entered has already existed!",
											Toast.LENGTH_LONG).show();
									etxt_number.setText("");
									return;
								}
							}
							if (number.length() != 11
									|| !number.startsWith("1")) {
								Toast.makeText(
										getBaseContext(),
										"Please enter the correct phone number!",
										Toast.LENGTH_SHORT).show();
								etxt_number.setText("");
							} else {
								People friend = new People(name, number);
								list.add(friend);
								peopleadapter.notifyDataSetChanged();
								listview.setSelection(list
										.size());
								dialog.dismiss();
							}
						} else {
							Toast.makeText(getBaseContext(),
									"Name or number can't be null!",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
				dialog.show();
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		peopleadapter.notifyDataSetChanged();
	}
}
