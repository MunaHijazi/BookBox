package com.example.pp_ff.SendNotification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAwjDOJkw:APA91bESJReULmQy0HDPWmCjZ9wmyzQAg5kaUFsluO5Xp-FRvK6LInT4_WGOThTkymvb2mx2WqM9xNulPuYOnoy1lrms2agvu7v8-dN1h_Y9J4nA5blRchypOIdeNnwQtS_TPEpjhgdA"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body NotificationSender body);
}
