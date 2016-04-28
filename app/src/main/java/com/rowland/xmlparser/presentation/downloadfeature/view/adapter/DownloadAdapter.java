package com.rowland.xmlparser.presentation.downloadfeature.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rowland.xmlparser.R;
import com.rowland.xmlparser.presentation.downloadfeature.model.DownloadModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter that manages a collection of {@link DownloadModel}.
 */
public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.DownloadViewHolder> {

    // Class log identifier
    public final static String LOG_TAG = DownloadAdapter.class.getSimpleName();

    public interface OnItemClickListener {
        void onDownloadItemClicked(String downloadModel);
    }

    private List<String> downloadsCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    @Inject
    public DownloadAdapter(Context context) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.downloadsCollection = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.downloadsCollection != null) ? this.downloadsCollection.size() : 0;
    }

    @Override
    public DownloadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.row_download, parent, false);
        return new DownloadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DownloadViewHolder holder, final int position) {
        final String downloadModel = this.downloadsCollection.get(position);
        holder.textViewTitle.setText(downloadModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DownloadAdapter.this.onItemClickListener != null) {
                    DownloadAdapter.this.onItemClickListener.onDownloadItemClicked(downloadModel);
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setDownloadsCollection(Collection<String> downloadsCollection) {
        this.validateDownloadsCollection(downloadsCollection);
        this.downloadsCollection = (List<String>) downloadsCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateDownloadsCollection(Collection<String> downloadsCollection) {
        if (downloadsCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class DownloadViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        TextView textViewTitle;

        public DownloadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
