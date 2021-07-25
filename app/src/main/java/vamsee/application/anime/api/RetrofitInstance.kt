package vamsee.application.anime.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vamsee.application.anime.utils.Constants.Companion.BASE_URL

object RetrofitInstance {

//    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//
//    httpClient.addInterceptor(new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request().newBuilder().addHeader("parameter", "value").build();
//            return chain.proceed(request);
//        }
//    });

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: SimpleApi by lazy {
        retrofit.create(SimpleApi::class.java)
    }
}