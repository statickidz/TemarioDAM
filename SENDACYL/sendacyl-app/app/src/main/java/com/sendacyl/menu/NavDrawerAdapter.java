package com.sendacyl.menu;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sendacyl.R;
import com.sendacyl.menu.model.NavItem;
import com.sendacyl.menu.ui.NavDrawerFragment;

import java.util.List;

public class NavDrawerAdapter extends
		RecyclerView.Adapter<NavDrawerAdapter.ViewHolder> {

	private List<NavItem> mData;
	private NavDrawerCallback mNavDrawerCallbacks;
	private NavDrawerFragment mContext;

	private int mSelectedPosition;
	private int mTouchedPosition;

	public NavDrawerAdapter(List<NavItem> data, NavDrawerFragment context) {
		mData = data;
		mContext = context;
	}

	public NavDrawerCallback getNavigationDrawerCallbacks() {
		return mNavDrawerCallbacks;
	}

	public void setNavigationDrawerCallbacks(NavDrawerCallback navDrawerCallback) {
		mNavDrawerCallbacks = navDrawerCallback;
	}

	@Override
	public NavDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup,
			int i) {
		if (i == 0) {
	        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_top, viewGroup, false));
	    } else if (i == 1) {
	    	return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_section, viewGroup, false));
	    }
		
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.drawer_row, viewGroup, false);
		return new ViewHolder(v);
	}
	
	@Override
	public int getItemViewType(int position) {
	    if (mData.get(position).getType() == NavItem.TOP){
	    	return 0;
	    } else if (mData.get(position).getType() == NavItem.SECTION) {
	    	return 1;
	    } else {
	    	return 2;
	    }
	}

	@Override
	public void onBindViewHolder(NavDrawerAdapter.ViewHolder viewHolder,
			final int i) {
		
			Drawable drawable = null;
			if (mData.get(i).getDrawable() != 0) {
				drawable = mContext.getResources().getDrawable(
						mData.get(i).getDrawable());
			}

			if (mData.get(i).getType() == NavItem.ITEM) {
				setText(viewHolder.textViewItem, i, drawable);
				setBackgroundColor(i, viewHolder);
			} else if (mData.get(i).getType() == NavItem.EXTRA) {
				viewHolder.textViewExtra.setVisibility(View.VISIBLE);
				viewHolder.textViewItem.setVisibility(View.GONE);

				setText(viewHolder.textViewExtra, i, drawable);
				setBackgroundColor(i, viewHolder);
				viewHolder.setIsRecyclable(false);
			} else if (mData.get(i).getType() == NavItem.SECTION) {
				setText(viewHolder.textViewSection, i, drawable);
				viewHolder.setIsRecyclable(false);
			}
	}

	private void setBackgroundColor(int i,
			NavDrawerAdapter.ViewHolder viewHolder) {
		if (mSelectedPosition == i || mTouchedPosition == i) {
			viewHolder.itemView.setBackgroundColor(viewHolder.itemView
					.getContext().getResources()
					.getColor(R.color.selected_gray));
		} else {
			viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
		}
	}

	private void setText(TextView textView, final int i, Drawable drawable) {
		textView.setText(mData.get(i).getText());

		textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null,
				null);

		textView.setOnTouchListener(new View.OnTouchListener() {
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					touchPosition(i);
					return false;
				case MotionEvent.ACTION_CANCEL:
					touchPosition(-1);
					return false;
				case MotionEvent.ACTION_MOVE:
					return false;
				case MotionEvent.ACTION_UP:
					touchPosition(-1);
					return false;
				}
				return true;
			}
		});
		textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mNavDrawerCallbacks != null)
					mNavDrawerCallbacks.onNavigationDrawerItemSelected(i,
							mData.get(i));
			}
		});
	}

	private void touchPosition(int position) {
		int lastPosition = mTouchedPosition;
		mTouchedPosition = position;
		if (lastPosition >= 0)
			notifyItemChanged(lastPosition);
		if (position >= 0)
			notifyItemChanged(position);
	}

	public void selectPosition(int position) {
		int lastPosition = mSelectedPosition;
		mSelectedPosition = position;
		notifyItemChanged(lastPosition);
		notifyItemChanged(position);
	}

	@Override
	public int getItemCount() {
		return mData != null ? mData.size() : 0;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView textViewItem;
		public TextView textViewSection;
		public TextView textViewExtra;

		public ViewHolder(View itemView) {
			super(itemView);
			textViewItem = (TextView) itemView.findViewById(R.id.item_name);
			textViewExtra = (TextView) itemView.findViewById(R.id.extra_name);
			textViewSection = (TextView) itemView
					.findViewById(R.id.section_name);
		}
	}
}
