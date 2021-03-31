package mz.co.commandline.grocery.generics.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import mz.co.commandline.grocery.generics.listner.ClickListner;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public BaseViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);

        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
    }

    public abstract void bind(T t);

    public abstract void setItemClickListner(ClickListner listner);
}
