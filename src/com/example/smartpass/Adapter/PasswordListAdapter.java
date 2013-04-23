package com.example.smartpass.Adapter;

import java.util.ArrayList;

import com.example.smartpass.Classes.*;
import com.example.smartpass.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class PasswordListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<ExpandListGroup> groups;

	public PasswordListAdapter(Context context,
			ArrayList<ExpandListGroup> groups) {
		this.context = context;
		this.groups = groups;
	}

	public void addItem(ExpandListChild item, ExpandListGroup group) {
		if (!groups.contains(group)) {
			groups.add(group);
		}
		int index = groups.indexOf(group);
		ArrayList<ExpandListChild> ch = groups.get(index).getItems();
		ch.add(item);
		groups.get(index).setItems(ch);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<ExpandListChild> chList = groups.get(groupPosition)
				.getItems();
		return chList.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View view, ViewGroup parent) {
		Typeface sintRegFont = Typeface.createFromAsset(context.getAssets(),
				"fonts/SintonyRegular.ttf");
		ExpandListChild child = (ExpandListChild) getChild(groupPosition,
				childPosition);
		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.expandlist_child_item, null);
		}
		TextView tv = (TextView) view.findViewById(R.id.tvChild);
		tv.setTypeface(sintRegFont);
		tv.setText(child.getName().toString());
		tv.setTag(child.getTag());
		tv.setPadding(0, 5, 0, 5);
		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		ArrayList<ExpandListChild> chList = groups.get(groupPosition)
				.getItems();

		return chList.size();

	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isLastChild, View view,
			ViewGroup parent) {
		ExpandListGroup group = (ExpandListGroup) getGroup(groupPosition);
		Typeface sintRegFont = Typeface.createFromAsset(context.getAssets(),
				"fonts/SintonyRegular.ttf");
		if (view == null) {
			LayoutInflater inf = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inf.inflate(R.layout.expandlist_group_item, null);
		}
		TextView tv = (TextView) view.findViewById(R.id.tvGroup);
		tv.setTypeface(sintRegFont);
		tv.setText(group.getName());
		tv.setPadding(0, 10, 0, 10);
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

}