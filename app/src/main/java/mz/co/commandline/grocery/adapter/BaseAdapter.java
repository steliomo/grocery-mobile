package mz.co.commandline.grocery.adapter;

import androidx.recyclerview.widget.RecyclerView;

import mz.co.commandline.grocery.generics.listner.ClickListner;

public abstract class BaseAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    public abstract void setItemClickListner(ClickListner listner);

}
