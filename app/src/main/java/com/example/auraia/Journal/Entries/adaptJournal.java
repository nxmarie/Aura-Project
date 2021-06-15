package com.example.auraia.Journal.Entries;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auraia.Journal.JournalsIn.journalEnt;
import com.example.myapplication.R;

import java.util.LinkedList;
public class adaptJournal extends RecyclerView.Adapter<adaptJournal.adaptViewHolder> {
    private LinkedList<journalEnt> mExampleList;

    private OnJournalClickListener xlistener;

    public interface OnJournalClickListener {
        void onJournalClick(int position);
    }
    public void setOnJournalClickListener(OnJournalClickListener listener) {
        xlistener = listener;
    }

    public static class adaptViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public adaptViewHolder(View itemView, final OnJournalClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener !=null){
                        int pos = getAdapterPosition();
                        if(pos !=RecyclerView.NO_POSITION){
                            listener.onJournalClick(pos);
                        }
                    }

                }
            });
        }
    }
    public adaptJournal(LinkedList<journalEnt> l) {
        mExampleList = l;
    }
    @NonNull
    @Override
    public adaptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_card, parent, false);
        adaptViewHolder evh = new adaptViewHolder(v, xlistener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull adaptViewHolder holder, int position) {
        journalEnt currentItem = mExampleList.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getDate());
        holder.mTextView2.setText(currentItem.getsubText());

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
