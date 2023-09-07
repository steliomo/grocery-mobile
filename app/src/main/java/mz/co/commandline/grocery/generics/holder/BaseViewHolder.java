package mz.co.commandline.grocery.generics.holder;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import android.view.View;

import butterknife.ButterKnife;
import mz.co.commandline.grocery.generics.listner.ClickListner;
import mz.co.commandline.grocery.generics.listner.LongClickListner;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public BaseViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);

        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
    }

    public BaseViewHolder(ViewBinding binding) {
        super(binding.getRoot());

        binding.getRoot().setOnClickListener(this);
        binding.getRoot().setOnLongClickListener(this);
    }

    public abstract void bind(T t);

    public abstract void setItemClickListner(ClickListner listner);

    public void setItemLongClickListner(LongClickListner listner) {
        throw new RuntimeException("implement LongClickListner");
    }
}
