package mz.co.commandline.grocery.adapter;

import androidx.recyclerview.widget.RecyclerView;

import mz.co.commandline.grocery.generics.listner.ClickListner;
import mz.co.commandline.grocery.generics.listner.LongClickListner;

public abstract class BaseAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    public abstract void setItemClickListner(ClickListner listner);

    public void setItemLongClickListner(LongClickListner listner) {
        throw new RuntimeException("implement LongClickListner");
    }
}
