package com.lubuntum.guesswhoapp.cards;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.HttpUrlFetcher;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CardLoader {
    public static final String LOAD_URL = "https://drive.google.com/uc?export=download&id=%s";
    public static final String CARDS_DATA_ID = "1O5Wwq-oDvWM370keNseHYL3D-Ni62CYi";
    public static final String CARDS_FILE = "Characters.json";
    public static final String FOLDER_ID = "14774xsdT4ueBGQw0dC55uPg7lqhLsPW2";
    public static List<Card> cardsList;
    public static List<Card> originalList;
    public static Card currentCard;
    public static void loadCardsData(Context context){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://drive.google.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CardsAPIService apiService = retrofit.create(CardsAPIService.class);
        Call<List<Card>> call = apiService.getCards(CARDS_DATA_ID);
        call.enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.d("Response Error", "Can't retrieve json data");
                    return;
                }
                Toast.makeText(context, "Можете начитать игру!", Toast.LENGTH_SHORT).show();
                cardsList =  response.body();
                originalList = new LinkedList<>();
                for(Card card : cardsList) originalList.add(card.copy());
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    /*
    public static void getCardListFile() throws IOException {
        Drive driveService = getDriveService();
        new Thread(()->{
            try {
                FileList res = driveService.files().list()
                        .setQ(String.format("'%s' in parents and name='%s'",FOLDER_ID, CARDS_FILE))
                        .setSpaces("drive")
                        .execute();
                List<File> files = res.getFiles();
                if (files == null || files.isEmpty()) return;
                String fileId = files.get(0).getId();
                //load file
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    private static Drive getDriveService() throws IOException {
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        GoogleCredential credential = GoogleCredential.fromStream(
                        new FileInputStream("https://drive.google.com/file/d/1O5Wwq-oDvWM370keNseHYL3D-Ni62CYi/view?usp=drive_link"))
                .createScoped(Collections.singleton("https://www.googleapis.com/auth/drive.readonly"));

        return new Drive.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName("com.lubuntum.guesswhoapp")
                .build();
    }

     */
    public static void loadCardImageById(Context context, Card card, ImageView imageView, ProgressBar progressBar){
        String imageUrl = String.format(LOAD_URL, card.imageId);
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(imageUrl)
                .override(300, 450)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }

}
