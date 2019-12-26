package com.example.bakingtime.repository.network;

import com.example.bakingtime.repository.GetRecipeDataService;

public interface GetRecipeDataConsumer {

	default GetRecipeDataService getService() {
		return RetrofitClient.getInstance().create(GetRecipeDataService.class);
	}
}
