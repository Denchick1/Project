package com.example.root.metr.adapters;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.root.metr.R;
import com.example.root.metr.databinding.CardEvacuatorBinding;
import com.example.root.metr.models.DTO.Wrecker;
import com.example.root.metr.root.GlideApp;
import com.example.root.metr.screens.garage.interfaces.WreckerClickListener;
import com.example.root.metr.utils.DiffCallback;

import java.util.ArrayList;
import java.util.List;

public class EvacuatorRecyclerAdapter extends RecyclerView.Adapter<EvacuatorRecyclerAdapter.ViewHolder>{

    private List<Wrecker> list;
    private Context context;
    private WreckerClickListener wreckerClickListener=WreckerClickListener.empty();

    public EvacuatorRecyclerAdapter() {
        list=new ArrayList<>();
    }

    public void setWreckerClickListener(WreckerClickListener wreckerClickListener) {
        this.wreckerClickListener = wreckerClickListener;
    }

    public void setList(List<Wrecker> list) {
            List<Wrecker> wreckers=new ArrayList<>();
            wreckers.addAll(this.list);
            list.removeAll(this.list);
            this.list.addAll(list);
            // notifyDataSetChanged();
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback<>(this.list, wreckers));
            diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_evacuator,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String url=list.get(position).getUrl_avatar();
        String number =list.get(position).getNumber_auto();
        GlideApp.with(context).load(url).error(R.drawable.interface_1).into(holder.binding.ivEvacuator);
        holder.binding.tvNumberEvacuator.setText(number);
        holder.binding.tvNameMark.setSingleLine(true);
        holder.binding.getRoot().setOnClickListener(view -> wreckerClickListener.click(list.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardEvacuatorBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding= DataBindingUtil.bind(itemView);
        }
    }
}
