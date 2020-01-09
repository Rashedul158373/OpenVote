package com.example.openvote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.openvote.R;
import com.example.openvote.pojo.VotePojo;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class VoteAdapter extends RecyclerView.Adapter<VoteAdapter.VoteViewHolder>{
    private List<VotePojo> votePojos;
    Context context;
    RecyclerView recyclerView;
    private DatabaseReference databaseReference;

    private String desitionrb;


    public VoteAdapter(List<VotePojo> doctorRegistrationPojos) {
        this.votePojos = doctorRegistrationPojos;
        this.context = context;
    }

    @NonNull
    @Override
    public VoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vote_row,viewGroup,false);

        VoteViewHolder voteViewHolder = new VoteViewHolder(view);
        return voteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VoteViewHolder voteViewHolder, int i) {
        final VotePojo itemPosition = votePojos.get(i);

        voteViewHolder.userNameTV.setText(itemPosition.getUserName());
        voteViewHolder.voteDate.setText(itemPosition.getDate());
        voteViewHolder.seekbarDate.setText(itemPosition.getSeekbartime());

        voteViewHolder.titleTV.setText(itemPosition.getQuestion());

    }

    @Override
    public int getItemCount() {
        return votePojos.size();
    }



    public class VoteViewHolder extends RecyclerView.ViewHolder{

        ImageView userProfileIV, agreeCheckIV, disAgreeCheckIV;
        TextView userNameTV, voteDate, seekbarDate , titleTV, agreeMessageTV, disAgreeMessageTV;
        EditText agreecommentET, disagreeCommentET;
        RadioGroup decisionRG;
        SeekBar seekBar;
        Button submit;
        CardView cardView;

        public VoteViewHolder(@NonNull final View itemView) {
            super(itemView);

            userProfileIV = itemView.findViewById(R.id.profile_iv);
            agreeCheckIV = itemView.findViewById(R.id.agree_Check_iv);
            disAgreeCheckIV = itemView.findViewById(R.id.disagree_Check_iv);
            userNameTV = itemView.findViewById(R.id.userName_TV);
            voteDate = itemView.findViewById(R.id.create_date_TV);
            seekBar = itemView.findViewById(R.id.seekbar);
            seekbarDate = itemView.findViewById(R.id.seekbar_time);
            titleTV = itemView.findViewById(R.id.title_tv);
            agreecommentET = itemView.findViewById(R.id.agreeComment_et);
            disagreeCommentET = itemView.findViewById(R.id.disagreeComment_et);
            decisionRG = itemView.findViewById(R.id.decision_rg);
            submit = itemView.findViewById(R.id.submit_btn);
            agreeMessageTV = itemView.findViewById(R.id.agreeMessage_TV);
            disAgreeMessageTV = itemView.findViewById(R.id.disagreeMessage_TV);


            decisionRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    RadioButton rb = group.findViewById(checkedId);
                    desitionrb = rb.getText().toString();

                    if (desitionrb.equals("Agree")){

                        agreecommentET.setVisibility(View.VISIBLE);
                        submit.setVisibility(View.VISIBLE);
                        disagreeCommentET.setVisibility(View.GONE);
                        return;
                    }
                    if (desitionrb.equals("Disagree")){

                        disagreeCommentET.setVisibility(View.VISIBLE);
                        submit.setVisibility(View.VISIBLE);
                        agreecommentET.setVisibility(View.GONE);
                        return;
                    }

                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (desitionrb.equals("Agree")){

                        decisionRG.setVisibility(View.GONE);
                        submit.setVisibility(View.GONE);
                        agreecommentET.setVisibility(View.GONE);
                        disagreeCommentET.setVisibility(View.GONE);
                        agreeCheckIV.setVisibility(View.VISIBLE);
                        disAgreeCheckIV.setVisibility(View.GONE);
                        agreeMessageTV.setVisibility(View.VISIBLE);
                        disAgreeMessageTV.setVisibility(View.GONE);
                        disagreeCommentET.setVisibility(View.GONE);
                        return;
                    }
                    if (desitionrb.equals("Disagree")){

                        decisionRG.setVisibility(View.GONE);
                        submit.setVisibility(View.GONE);
                        agreecommentET.setVisibility(View.GONE);
                        disagreeCommentET.setVisibility(View.GONE);
                        agreeCheckIV.setVisibility(View.GONE);
                        disAgreeCheckIV.setVisibility(View.VISIBLE);
                        agreeMessageTV.setVisibility(View.GONE);
                        disAgreeMessageTV.setVisibility(View.VISIBLE);
                        disagreeCommentET.setVisibility(View.GONE);
                        return;
                    }
                }
            });



        }
    }
}