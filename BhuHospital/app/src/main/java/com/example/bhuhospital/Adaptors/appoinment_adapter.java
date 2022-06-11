package com.example.bhuhospital.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bhuhospital.R;
import com.example.bhuhospital.Room.appoinment_entity;

import java.util.ArrayList;
import java.util.List;

public class appoinment_adapter extends RecyclerView.Adapter<appoinment_adapter.myViewHolder> {

    List<appoinment_entity> dataset = new ArrayList<>();
    public appoinment_adapter()
    {

    }


    public void setDataset(List<appoinment_entity> data)
    {this.dataset = data;notifyDataSetChanged();}

    public appoinment_entity getNoteAt(int pos)
    {return dataset.get(pos);}
    public class myViewHolder extends RecyclerView.ViewHolder
    {
        private TextView doc_name;
        private TextView date,isPending,symptoms,patient_name;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            doc_name = itemView.findViewById(R.id.view_docName);
            date = itemView.findViewById(R.id.view_date);
            symptoms = itemView.findViewById(R.id.view_symptoms);
            patient_name = itemView.findViewById(R.id.view_patient_name);
            isPending = itemView.findViewById(R.id.view_pending);
//            find ids
//            incomplete
        }
        public void bind(appoinment_entity note)
        {
            doc_name.setText(note.getName_of_doc());
            date.setText(note.getDate().toString());
            symptoms.setText(note.getSymptoms());
            patient_name.setText(note.getName_of_patinet());
            int is = note.isPending();
            if(is==0)
                isPending.setText("Pending ");
            else
                isPending.setText("Completed");

        }

    };



//    methods
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout_patient,parent,false);
        return new appoinment_adapter.myViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        appoinment_entity note = dataset.get(position);
        holder.bind(note);
    }



    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
