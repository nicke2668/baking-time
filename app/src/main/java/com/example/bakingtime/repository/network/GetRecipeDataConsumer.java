package com.example.bakingtime.repository.network;

import com.example.bakingtime.repository.GetRecipeDataService;

/**
 * API for consumers of this Retrofit instance
 *
 * @author Nick Emerson
 */
public interface GetRecipeDataConsumer {

	default GetRecipeDataService getService() {
		return RetrofitClient.getInstance().create(GetRecipeDataService.class);
	}
}
