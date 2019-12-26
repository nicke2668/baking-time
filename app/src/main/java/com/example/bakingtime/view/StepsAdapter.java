package com.example.bakingtime.view;

import java.util.List;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.bakingtime.R;
import com.example.bakingtime.databinding.ItemStepBinding;
import com.example.bakingtime.model.Step;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder> {

	class StepsAdapterViewHolder extends RecyclerView.ViewHolder {

		final ItemStepBinding binding;

		StepsAdapterViewHolder(ItemStepBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		void bind(Step step) {
			binding.setStep(step);
			binding.setWatchCallback(callback);
		}
	}

	private final WatchVideoCallback callback;
	private List<Step> list;

	StepsAdapter(WatchVideoCallback callback) {
		this.callback = callback;
	}

	@Override
	public int getItemCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public void onBindViewHolder(@NonNull StepsAdapterViewHolder holder, int position) {
		holder.bind(list.get(position));
	}

	@NonNull
	@Override
	public StepsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		ItemStepBinding binding =
				DataBindingUtil.inflate(layoutInflater, R.layout.item_step, parent, false);
		return new StepsAdapterViewHolder(binding);
	}

	public void setList(List<Step> list) {
		this.list = list;
		notifyDataSetChanged();
	}
}
