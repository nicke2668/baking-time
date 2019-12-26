package com.example.bakingtime.view;

import java.text.DecimalFormat;

import android.view.View;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class BindingAdapters {

	@BindingAdapter("decimal_text")
	public static void setDecimalText(TextView view, double value) {
		DecimalFormat formatter = new DecimalFormat("0.#");
		view.setText(formatter.format(value));
	}

	@BindingAdapter("visible_gone")
	public static void showHide(View view, boolean show) {
		view.setVisibility(show ? View.VISIBLE : View.GONE);
	}
}
