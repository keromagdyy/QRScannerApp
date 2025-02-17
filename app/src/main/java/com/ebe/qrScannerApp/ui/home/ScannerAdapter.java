package com.ebe.qrScannerApp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.ebe.qrScannerApp.data.model.ScanModel;
import com.ebe.qrScannerApp.databinding.LayoutScanItemBinding;
import java.util.ArrayList;
import java.util.List;

public class ScannerAdapter extends RecyclerView.Adapter<ScannerAdapter.ViewHolder> {

    private List<ScanModel> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public ScannerAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<ScanModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutScanItemBinding binding = LayoutScanItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ScanModel item = list.get(position);
        holder.bind(item, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(ScanModel item, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LayoutScanItemBinding binding;

        public ViewHolder(LayoutScanItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ScanModel item, int position) {
            binding.txtContent.setText(item.getContent());
            binding.txtType.setText(item.getType());
            binding.txtDateTime.setText(item.getDateTime());

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(item, position);
                }
            });
        }
    }
}
