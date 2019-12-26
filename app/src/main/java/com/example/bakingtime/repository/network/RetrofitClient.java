package com.example.bakingtime.repository.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitClient {

	private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
	private static Retrofit INSTANCE;

	static Retrofit getInstance() {
		if (INSTANCE == null) {

			HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
			interceptor.level(HttpLoggingInterceptor.Level.BODY);
			OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

			INSTANCE = new Retrofit.Builder()
					.baseUrl(BASE_URL)
					.client(client)
					.addConverterFactory(GsonConverterFactory.create())
					.build();
		}
		return INSTANCE;
	}

	private RetrofitClient() {
	}
}