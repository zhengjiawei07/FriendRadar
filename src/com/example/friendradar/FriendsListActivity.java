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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class FriendsListActivity extends Activity {
	Button abc;
	Button enemy;
	Button add;
	ToggleButton erd;
	ListView listview;

	List<People> list;
	PeopleListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.friends_list);

		abc = (Button) findViewById(R.id.btn_friends_list_radar);
		enemy = (Button) findViewById(R.id.btn_friends_list_enemies);
		add = (Button) findViewById(R.id.btn_friends_list_add);
		erd = (ToggleButton) findViewById(R.id.btn_friends_list_edit);
		listview = (ListView) findViewById(R.id.lvw_friends_list);

		list = new ArrayList<People>();
		adapter = new PeopleListAdapter(this, R.layout.friends_list_item,
				list);

		listview.setAdapter(adapter);

		abc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(FriendsListActivity.this,
						MainActivity.class));
			}
		});

		enemy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(FriendsListActivity.this,
						EnemiesListActivity.class));
			}
		});

		add.setOnClickListener(new OnClickListener() {
			@SuppressLint("InflateParams")
			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						FriendsListActivity.this);

				View dialogview = LayoutInflater.from(FriendsListActivity.this)
						.inflate(R.layout.dialog_add_friend, null);

				Button close = (Button) dialogview
						.findViewById(R.id.btn_dialog_close);
				Button add = (Button) dialogview
						.findViewById(R.id.btn_dialog_ok);
				final EditText etxt_number = (EditText) dialogview
						.findViewById(R.id.txt_friend_number);
				final EditText etxt_name = (EditText) dialogview
						.findViewById(R.id.txt_friend_name);

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
								adapter.notifyDataSetChanged();
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
		adapter.notifyDataSetChanged();
	}
}
