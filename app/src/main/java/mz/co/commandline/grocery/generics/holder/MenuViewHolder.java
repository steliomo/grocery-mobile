package mz.co.commandline.grocery.generics.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import mz.co.commandline.grocery.generics.listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.menu.MenuItem;

public class MenuViewHolder extends BaseViewHolder<MenuItem> {

    @BindView(R.id.main_menu_image_icon)
    ImageView imageView;

    @BindView(R.id.main_menu_title)
    TextView textView;

    private MenuItem menuItem;

    private ClickListner listner;

    public MenuViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(MenuItem menuItem) {
        this.menuItem = menuItem;
        imageView.setImageResource(menuItem.getIconId());
        textView.setText(menuItem.getTitle());
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClickListner(menuItem);
    }

    @Override
    public boolean onLongClick(View view) {
        return true;
    }
}
