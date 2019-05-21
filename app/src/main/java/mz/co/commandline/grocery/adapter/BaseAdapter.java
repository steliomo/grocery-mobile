package mz.co.commandline.grocery.adapter;

import android.support.v7.widget.RecyclerView;

import mz.co.commandline.grocery.Listner.ClickListner;

public abstract class BaseAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    public abstract void setItemClickListner(ClickListner listner);

}
