package com.ecommerce2.demo.Configurations;

import com.ecommerce2.demo.Gateway.Api.FakeStoreProductApi;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {

    private final Dotenv dotenv = Dotenv.load(); // Load .env

    @Bean
    public Retrofit retrofit() {
        String baseUrl = dotenv.get("BASE_URL");

//        // Defensive check
//        if (baseUrl == null || baseUrl.isEmpty() || !baseUrl.startsWith("http")) {
//            throw new IllegalArgumentException("BASE_URL is not set correctly in the .env file.");
//        }

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Bean
    public FakeStoreProductApi fakeStoreProductApi(Retrofit retrofit) {
        return retrofit.create(FakeStoreProductApi.class);
    }
}
