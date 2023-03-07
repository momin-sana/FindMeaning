package com.student.findmeaning.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.student.findmeaning.Models.Phonetic;
import com.student.findmeaning.R;
import com.student.findmeaning.ViewHolders.PhoneticVH;

import java.util.List;

public class PhoneticAdapter extends RecyclerView.Adapter<PhoneticVH> {
    Context context;
    private List<Phonetic> phoneticList;

    public PhoneticAdapter(Context context, List<Phonetic> phoneticList) {
        this.context = context;
        this.phoneticList = phoneticList;
    }


    @NonNull
    @Override
    public PhoneticVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhoneticVH(LayoutInflater.from(context).inflate(R.layout.phonetic_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneticVH holder, @SuppressLint("RecyclerView") int position) {

        holder.phonetic_text.setText(phoneticList.get(position).getText());
        holder.phonetic_audio.setOnClickListener(view -> {
            MediaPlayer player = new MediaPlayer();
            try{
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();

                player.setAudioAttributes(audioAttributes);
                player.setDataSource("https:" + phoneticList.get(position).getAudio());
                player.prepare();
                player.start();
            }catch (Exception e){
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

    public void setData(List<Phonetic> phoneticList) {
        this.phoneticList = phoneticList;
        notifyDataSetChanged();
    }
}
