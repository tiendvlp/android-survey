package com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.devlogs.osg.apolo.fptsurvey.R;
import com.devlogs.osg.apolo.fptsurvey.common.helper.ScreenDimensionSupport;
import com.devlogs.osg.apolo.fptsurvey.screen.common.ObservableRecyclerViewAdapter;
import com.devlogs.osg.apolo.fptsurvey.screen.mainscreen.presentationmodel.QuickQuestionPM;

import java.util.ArrayList;
import java.util.List;

public class QuickQuestionAdapter extends ObservableRecyclerViewAdapter<QuickQuestionAdapter.Listener,  RecyclerView.ViewHolder>  {

    public interface Listener {
        void onBtnSubmitClicked (String question, int answer);
    }

    private class MyEndViewHolder extends RecyclerView.ViewHolder {

        public MyEndViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtQuestionBody;
        private RadioGroup rdgrAnswers;
        private RadioButton rbtn1;
        private RadioButton rbtn2;
        private RadioButton rbtn3;
        private Button btnSubmit;
        private int selectedAnswer =  -1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btnSubmit = itemView.findViewById(R.id.btnSubmit);
            rdgrAnswers = itemView.findViewById(R.id.rbtngrAnswer);
            txtQuestionBody = itemView.findViewById(R.id.txtQuestionBody);
            rbtn1 = itemView.findViewById(R.id.rbtnAnswer1);
            rbtn2 = itemView.findViewById(R.id.rbtnAnswer2);
            rbtn3 = itemView.findViewById(R.id.rbtnAnswer3);
        }

        private void bind (QuickQuestionPM questionPM) {
            if (selectedAnswer != -1) {
                if (selectedAnswer == 0) {
                    rdgrAnswers.check(R.id.rbtnAnswer1);
                } else if (selectedAnswer == 1) {
                    rdgrAnswers.check(R.id.rbtnAnswer2);
                } else if (selectedAnswer == 2) {
                    rdgrAnswers.check(R.id.rbtnAnswer3);
                }
            }

            rdgrAnswers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i) {
                        case R.id.rbtnAnswer1 : {selectedAnswer = 0; break;}
                        case R.id.rbtnAnswer2 : {selectedAnswer = 1; break;}
                        case R.id.rbtnAnswer3 : {selectedAnswer = 2; break;}
                    }
                }
            });

            txtQuestionBody.setText(questionPM.getQuestion());
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedAnswer == -1) {
                        Toast.makeText(itemView.getContext(), "Please select answer first", Toast.LENGTH_LONG).show();
                        return;
                    }
                    mListener.onBtnSubmitClicked(questionPM.getQuestionId(), selectedAnswer);
                }
            });
            switch (questionPM.getAnswers().size()) {
                case 1: {
                    rbtn1.setVisibility(View.VISIBLE);
                    rbtn1.setEnabled(true);
                    rbtn1.setText("A. " + questionPM.getAnswers().get(0));
                    rbtn2.setVisibility(View.INVISIBLE);
                    rbtn2.setEnabled(false);
                    rbtn3.setVisibility(View.INVISIBLE);
                    rbtn3.setEnabled(false);
                    break;
                }
                case 2: {
                    rbtn1.setVisibility(View.VISIBLE);
                    rbtn1.setEnabled(true);
                    rbtn1.setText("A. " + questionPM.getAnswers().get(0));
                    rbtn2.setVisibility(View.VISIBLE);
                    rbtn2.setText("B. " + questionPM.getAnswers().get(1));
                    rbtn2.setEnabled(true);
                    rbtn3.setVisibility(View.INVISIBLE);
                    rbtn3.setEnabled(false);
                    break;}
                case 3: {
                    rbtn1.setVisibility(View.VISIBLE);
                    rbtn1.setEnabled(true);
                    rbtn1.setText("A. " + questionPM.getAnswers().get(0));
                    rbtn2.setVisibility(View.VISIBLE);
                    rbtn2.setText("B. " + questionPM.getAnswers().get(1));
                    rbtn2.setEnabled(true);
                    rbtn3.setVisibility(View.VISIBLE);
                    rbtn3.setEnabled(true);
                    rbtn3.setText("C. " + questionPM.getAnswers().get(2));
                    break;}
                default: {throw new RuntimeException("Your answers have to be from 1 to 3");}
            }

        }

        private void updateItemSize (int position) {
            int itemWidth = (int) ((ScreenDimensionSupport.instance.getScreenSize(itemView.getContext()).getWidth())-ScreenDimensionSupport.instance.convertDpToPx(itemView.getContext(), 40));
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            params.width = itemWidth;
            if (position==0) {
                params.setMargins((int) convertDpToPx(itemView.getContext(), 20f), params.topMargin, params.rightMargin, params.bottomMargin);
            }
            if  (position == getItemCount()-1) {
                params.setMargins(params.leftMargin, params.topMargin,(int) convertDpToPx(itemView.getContext(), 20f), params.bottomMargin);
                itemView.setLayoutParams(params);
            }
            itemView.setLayoutParams(params);
        }

        private float convertDpToPx(Context context, float dp) {
            return dp * context.getResources().getDisplayMetrics().density;
        }
    }

    private Listener mListener;
    private List<QuickQuestionPM> questionPMS = new ArrayList();

    public List<QuickQuestionPM> getQuestionPMS() {
        return questionPMS;
    }

    public void setQuestionPMS(List<QuickQuestionPM> questionPMS) {
        this.questionPMS.clear();
        this.questionPMS.addAll(questionPMS);
        notifyDataSetChanged();
    }

    public QuickQuestionAdapter () {

    }

    @Override
    public void register(Listener listener) {
        mListener = listener;
    }

    @Override
    public void unRegister(Listener listener) {
        mListener =  null;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (questionPMS.size() == 0) {
//            return new MyEndViewHolder(LayoutInflater.from(parent.getContext()).inflate( R.layout.item_quickquestion_end, parent, false));
//        }
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quickquestion, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).bind(questionPMS.get(position));
            ((MyViewHolder) holder).updateItemSize(position);

        }
    }

    @Override
    public int getItemCount() {
        return questionPMS.size();
    }
}
