package com.example.bakingtime.view;

import java.util.List;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.bakingtime.R;
import com.example.bakingtime.databinding.ItemIngredientBinding;
import com.example.bakingtime.model.Ingredient;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientAdapterViewHolder> {

	class IngredientAdapterViewHolder extends RecyclerView.ViewHolder {

		final ItemIngredientBinding binding;

		IngredientAdapterViewHolder(ItemIngredientBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		void bind(Ingredient ingredient) {
			binding.setModel(ingredient);
		}
	}

	private List<Ingredient> list;

	@Override
	public int getItemCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public void onBindViewHolder(@NonNull IngredientAdapterViewHolder holder, int position) {
		holder.bind(list.get(position));
	}

	@NonNull
	@Override
	public IngredientAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		ItemIngredientBinding binding =
				DataBindingUtil.inflate(layoutInflater, R.layout.item_ingredient, parent, false);
		return new IngredientAdapterViewHolder(binding);
	}

	public void setList(List<Ingredient> list) {
		this.list = list;
		notifyDataSetChanged();
	}
}
