package com.student.findmeaning.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.student.findmeaning.Models.Phonetic;
import com.student.findmeaning.R;
import com.student.findmeaning.ViewHolders.PhoneticVH;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PhoneticAdapter extends RecyclerView.Adapter<PhoneticVH> {
    private final Context context;
    private List<Phonetic> phoneticList;
    private MediaPlayer player;
    private TextToSpeech textToSpeech;

    public PhoneticAdapter(Context context, List<Phonetic> phoneticList) {
        this.context = context;
        this.textToSpeech = new TextToSpeech(context, i -> {
            if (i != TextToSpeech.SUCCESS) {
                Log.e("PhoneticAdapter", "Error initializing TTS");
            }else{
//                textToSpeech.setLanguage(Locale.ENGLISH);
                int result = textToSpeech.setLanguage(Locale.ENGLISH);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("PhoneticAdapter", "Language not supported");
                }

            }
        });
//        constructor calls the setData() method to filter the data and display only the data that has both audio and text.
        setData(phoneticList);
    }


    @NonNull
    @Override
    public PhoneticVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhoneticVH(LayoutInflater.from(context).inflate(R.layout.phonetic_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneticVH holder, @SuppressLint("RecyclerView") int position) {
        String audioUrl = phoneticList.get(position).getAudio();
        holder.phonetic_text.setText(phoneticList.get(position).getText());
        // Add a click listener to the whole view to stop the audio when clicking anywhere except scrolling
         holder.itemView.setOnClickListener(v -> {
            if (player != null) {
                player.pause();
            }
        });

        holder.phonetic_audio.setOnClickListener(view -> {
            if (player != null) {
                player.release();
            }
            player = new MediaPlayer();
            try {
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();
                player.setAudioAttributes(audioAttributes);
                player.setDataSource(audioUrl);
                player.setOnPreparedListener(MediaPlayer::start);
                player.setOnErrorListener((mediaPlayer, i, i1) -> true);
                player.prepareAsync();
                player.start();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, R.string.audio_fail, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (phoneticList == null) {
            return 0;
        }
        return phoneticList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Phonetic> phoneticList) {
//        The setData() method iterates through the original phoneticList, checks each item for audio and text, and adds it to a new filteredList if it has both audio and text.
//        The new filteredList is then used as the data source for the adapter.
        List<Phonetic> filterList = new ArrayList<>();
        for (int i = 0; i <phoneticList.size(); i++){
            Phonetic phonetic = phoneticList.get(i);
//            Note: need to have a hasAudio() and hasText() method in Phonetic class that returns true if the data has audio and text, respectively.
            if (phonetic.hasAudio() && phonetic.hasText()){
                filterList.add(phonetic);
            }
        }
        this.phoneticList = filterList;
        notifyDataSetChanged();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        release();
    }

    private void release() {
        if (player != null){
            player.release();
        }
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
