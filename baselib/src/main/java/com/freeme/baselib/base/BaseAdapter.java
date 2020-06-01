package com.freeme.baselib.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 针对DataBinding方式的子布局
 * @param <T> 子布局的DataBinding
 * @param <G> 传入的数据类型
 * 默认子布局点击事件回传一个positoin
 * 在showData里操作布局和数据
 */
public abstract class BaseAdapter<T extends ViewDataBinding, G> extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {
    private List<G> data;
    private Context context;
    private BaseItemClick itemClick;
    public BaseAdapter(Context context){
        this.context = context;
    }

    public void setItemClick(BaseItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), parent, false));
    }


    protected abstract int getLayoutId();
    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<G> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseAdapter.ViewHolder holder, int position) {
        holder.showDataBase(position);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{
        private T binding;
        public<H extends ViewDataBinding> ViewHolder(H binding) {
            super(binding.getRoot());
            this.binding = (T)binding;
        }


        public void showDataBase(final int position) {
            showData(data.get(position), binding);
            if(itemClick != null) {
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClick.itemClick(position);
                    }
                });
            }
        }
    }

    protected abstract void showData(G g, T binding);
}
